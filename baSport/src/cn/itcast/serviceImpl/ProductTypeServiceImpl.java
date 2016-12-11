package cn.itcast.serviceImpl;


import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.product.ProductTypeService;

//相当于在beans.xml中配置一个bean,所有的方法都会使用一些默认的事物行为
@Service
@Transactional
public class ProductTypeServiceImpl extends DaoSupport implements
		ProductTypeService {
	/**
	 * 重载方法,非物理意义的删除,只是让其visible不可见
	 */
	@Override
	public <T> void delete(Class<T> entityClass, Object[] entityId) { // 删除id数组的对象
		if (entityId != null && entityId.length > 0) {
			StringBuffer jpql = new StringBuffer();
			for (int i = 0; i < entityId.length; i++)
				jpql.append("?").append(i + 2).append(","); // o.typeid
															// in(?2,?3,?4...)
			jpql.deleteCharAt(jpql.length() - 1);
			// 将visible为1的那一行改为0
			Query query = em.createQuery(
					"update ProductType o set o.visible=?1 where o.typeid in ("
							+ jpql.toString() + ")").setParameter(1, false);
			for (int i = 0; i < entityId.length; i++)
				query.setParameter(i + 2, entityId[i]);
			query.executeUpdate();
		}
	}


}
