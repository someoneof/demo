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
 * 具有默认的事物行为
 * 如果有不同与默认的事物行为,就在那个方法上注解@Transactional
 */
@Transactional
public abstract class DaoSupport implements DAO {

	@PersistenceContext
	protected EntityManager em;// 运行期注入

	@Override
	public void save(Object entity)
	{
		em.persist(entity);

	}

	@Override
	public void update(Object entity)
	{
		em.merge(entity);// 把游离状态的bean数据同步回数据库
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

	/* 不能出现更改动作(readOnly),事物传播行为,不需要开事物(propagation) */
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
		// 排除visible为空的对象
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
	 * 组装order by语句
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
	 * 获取实体的名称
	 * 
	 * @param entityClass
	 *            实体类
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
	 * String entityname=getEntityName(entityClass); //得到表中所有的记录的结果集 Query query
	 * = em.createQuery("select o from "+entityname+" o");
	 * //设置从firstIndex开始,maxResult结束的结果集
	 * query.setFirstResult(firstIndex).setMaxResults(maxResult);
	 * //将从firstIndex开始,maxResult结束的结果集保存到QueryResult对象中
	 * qr.setResultlist(query.getResultList()); //返回数据表中的总记录数。query为int query =
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
 * 分页技术 1.先将数据表中的所有记录保存到一个结果集中 2.设置要查询的结果集（first,last) 3.将结果集保存到QueryResult对象中
 * 4.获取数据表中的记录总数int 5.设置结QueryResult中的total 6.反回QueryResult对象 结果集的处理: List
 * list=query.getResultList(); System.out.println(list.toString());
 */
