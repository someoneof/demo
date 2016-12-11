package cn.itcast.service.base;

import java.util.LinkedHashMap;

import cn.itcast.bean.QueryResult;


public interface DAO {
	
	/**
	 * 保存实体
	 * @param entity 实体Id
	 */
	public void save(Object entity);
	
	/**
	 * 更新实体
	 * @param entity 实体Id
	 */
	public void update(Object entity);
	
	/**
	 * 删除实体
	 * @param entityClass  类类型
	 * @param entityId  实体Id
	 */
	public <T> void delete(Class<T> entityClass, Object entityId);
	
	/**
	 *  删除实体数组
	 * @param entityClass  类类型
	 * @param entityId  实体Id数组
	 */
	public <T> void delete(Class<T> entityClass, Object[] entityId);
	
	/**
	 * 获取实体
	 * @param entityClass  实体类
	 * @param entityId 实体
	 * @return
	 */
	public <T> T find(Class<T> entityClass,Object entityId);
	
	/**
	 * 获取分页数据
	 * @param entityClass 实体类
	 * @param firstIndex 从第几页开始索引
	 * @param maxResult 需要获取的记录数
	 * @return
	 */
	//k:实体的属性,v:存放升序后排序,Order by key1 desc,key2 asc,LinkedHashMap用于按添加元素的顺序对元素进行排序(先添加的就在前面)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,int firstIndex,int maxResult,String wherejpql,Object[] queryParams,LinkedHashMap<String, String>orderBy);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,int firstIndex,int maxResult,LinkedHashMap<String, String>orderBy);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,int firstIndex,int maxResult,String wherejpql,Object[] queryParams);
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,int firstIndex,int maxResult);

	public <T> QueryResult<T> getScrollData(Class<T> entityClass);
}
