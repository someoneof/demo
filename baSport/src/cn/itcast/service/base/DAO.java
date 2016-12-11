package cn.itcast.service.base;

import java.util.LinkedHashMap;

import cn.itcast.bean.QueryResult;


public interface DAO {
	
	/**
	 * ����ʵ��
	 * @param entity ʵ��Id
	 */
	public void save(Object entity);
	
	/**
	 * ����ʵ��
	 * @param entity ʵ��Id
	 */
	public void update(Object entity);
	
	/**
	 * ɾ��ʵ��
	 * @param entityClass  ������
	 * @param entityId  ʵ��Id
	 */
	public <T> void delete(Class<T> entityClass, Object entityId);
	
	/**
	 *  ɾ��ʵ������
	 * @param entityClass  ������
	 * @param entityId  ʵ��Id����
	 */
	public <T> void delete(Class<T> entityClass, Object[] entityId);
	
	/**
	 * ��ȡʵ��
	 * @param entityClass  ʵ����
	 * @param entityId ʵ��
	 * @return
	 */
	public <T> T find(Class<T> entityClass,Object entityId);
	
	/**
	 * ��ȡ��ҳ����
	 * @param entityClass ʵ����
	 * @param firstIndex �ӵڼ�ҳ��ʼ����
	 * @param maxResult ��Ҫ��ȡ�ļ�¼��
	 * @return
	 */
	//k:ʵ�������,v:������������,Order by key1 desc,key2 asc,LinkedHashMap���ڰ����Ԫ�ص�˳���Ԫ�ؽ�������(����ӵľ���ǰ��)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,int firstIndex,int maxResult,String wherejpql,Object[] queryParams,LinkedHashMap<String, String>orderBy);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,int firstIndex,int maxResult,LinkedHashMap<String, String>orderBy);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,int firstIndex,int maxResult,String wherejpql,Object[] queryParams);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,int firstIndex,int maxResult);

	public <T> QueryResult<T> getScrollData(Class<T> entityClass);
}
