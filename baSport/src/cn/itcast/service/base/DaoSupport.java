package cn.itcast.service.base;

import java.util.LinkedHashMap;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bean.QueryResult;

/*
 * ����Ĭ�ϵ�������Ϊ
 * ����в�ͬ��Ĭ�ϵ�������Ϊ,�����Ǹ�������ע��@Transactional
 */
@Transactional
public abstract class DaoSupport implements DAO {

	@PersistenceContext
	protected EntityManager em;// ������ע��

	@Override
	public void save(Object entity)
	{
		em.persist(entity);

	}

	@Override
	public void update(Object entity)
	{
		em.merge(entity);// ������״̬��bean����ͬ�������ݿ�
	}

	@Override
	public <T> void delete(Class<T> entityClass, Object entityId)
	{
		delete(entityClass, new Object[] { entityId });
	}

	@Override
	public <T> void delete(Class<T> entityClass, Object[] entityId)
	{
		for (Object id : entityId)
			em.remove(em.getReference(entityClass, entityId));
	}

	/* ���ܳ��ָ��Ķ���(readOnly),���ﴫ����Ϊ,����Ҫ������(propagation) */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@Override
	public <T> T find(Class<T> entityClass, Object entityId)
	{
		return em.find(entityClass, entityId);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@Override
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,
			int firstIndex, int maxResult, LinkedHashMap<String, String> orderBy)
	{
		return getScrollData(entityClass, firstIndex, maxResult, null, null,
				orderBy);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@Override
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,
			int firstIndex, int maxResult, String wherejpql,
			Object[] queryParams)
	{
		return getScrollData(entityClass, firstIndex, maxResult, wherejpql,
				queryParams, null);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@Override
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,
			int firstIndex, int maxResult)
	{
		return getScrollData(entityClass, firstIndex, maxResult, null, null,
				null);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@Override
	public <T> QueryResult<T> getScrollData(Class<T> entityClass)
	{
		return getScrollData(entityClass,-1,-1);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,
			int firstIndex, int maxResult, String wherejpql,
			Object[] queryParams, LinkedHashMap<String, String> orderBy)
	{
		// o.pro=?1 and o.xxx like ?2
		QueryResult qr = new QueryResult<T>();
		String entityname = getEntityName(entityClass);
		// �ų�visibleΪ�յĶ���
		Query query = em.createQuery("select o from " + entityname + " o "
				+ (wherejpql == null ? "" : "where " + wherejpql)
				+ buildOrderby(orderBy));
		// select o from ProductType o where o.visible=?1 order by o.typeid asc
		setQueryParams(query, queryParams);
		if (firstIndex != -1 && maxResult != -1)
			query.setFirstResult(firstIndex).setMaxResults(maxResult);
		qr.setResultlist(query.getResultList());
		// select count(o) from ProductType o where o.visible=?1
		query = em.createQuery("select count(o) from " + entityname + " o "
				+ (wherejpql == null ? "" : "where " + wherejpql));
		setQueryParams(query, queryParams);
		qr.setTotalRecord((Long) query.getSingleResult());
		return qr;
	}

	protected void setQueryParams(Query query, Object[] queryParams)
	{
		if (queryParams != null && queryParams.length > 0)
		{
			for (int i = 0; i < queryParams.length; i++)
				query.setParameter(i + 1, queryParams[i]);
		}
	}

	/**
	 * ��װorder by���
	 * 
	 * @param orderBy
	 * @return
	 */
	// order by o.key desc,o.key2 asc typeid,desc
	protected String buildOrderby(LinkedHashMap<String, String> orderBy)
	{
		StringBuffer orderby = new StringBuffer();
		if (orderBy != null && orderBy.size() > 0)
		{
			orderby.append(" order by ");
			for (String key : orderBy.keySet())
				// order by o.typeid desc
				orderby.append("o.").append(key).append(" ")
						.append(orderBy.get(key)).append(",");
			orderby.deleteCharAt(orderby.length() - 1);
		}
		return orderby.toString();
	}

	/**
	 * ��ȡʵ�������
	 * 
	 * @param entityClass
	 *            ʵ����
	 * @return
	 */
	protected <T> String getEntityName(Class<T> entityClass)
	{
		String entityname = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		if (entity.name() != null && !"".equals(entity.name()))
			entityname = entity.name();
		return entityname;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	 * public <T> QueryResult<T> getScrollData(Class<T> entityClass, int
	 * firstIndex, int maxResult) { QueryResult qr = new QueryResult<T>();
	 * String entityname=getEntityName(entityClass); //�õ��������еļ�¼�Ľ���� Query query
	 * = em.createQuery("select o from "+entityname+" o");
	 * //���ô�firstIndex��ʼ,maxResult�����Ľ����
	 * query.setFirstResult(firstIndex).setMaxResults(maxResult);
	 * //����firstIndex��ʼ,maxResult�����Ľ�������浽QueryResult������
	 * qr.setResultlist(query.getResultList()); //�������ݱ��е��ܼ�¼����queryΪint query =
	 * em.createQuery("select count(o) from "+entityname+" o");
	 * qr.setTotalRecord((Long) query.getSingleResult()); return qr; }
	 */

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	 * public <T> QueryResult<T> getScrollData(Class<T> entityClass, int
	 * firstIndex, int maxResult, LinkedHashMap<String, String> orderBy) {
	 * QueryResult qr = new QueryResult<T>(); String entityname =
	 * getEntityName(entityClass); Query query = em.createQuery("select o from "
	 * + entityname + " o "+buildOrderby(orderBy));
	 * query.setFirstResult(firstIndex).setMaxResults(maxResult);
	 * qr.setResultlist(query.getResultList()); query =
	 * em.createQuery("select count(o) from " + entityname + " o");
	 * qr.setTotalRecord((Long) query.getSingleResult()); return qr; }
	 */
}
/*
 * ��ҳ���� 1.�Ƚ����ݱ��е����м�¼���浽һ��������� 2.����Ҫ��ѯ�Ľ������first,last) 3.����������浽QueryResult������
 * 4.��ȡ���ݱ��еļ�¼����int 5.���ý�QueryResult�е�total 6.����QueryResult���� ������Ĵ���: List
 * list=query.getResultList(); System.out.println(list.toString());
 */
