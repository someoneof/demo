package cn.itcast.serviceImpl;


import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.product.ProductTypeService;

//�൱����beans.xml������һ��bean,���еķ�������ʹ��һЩĬ�ϵ�������Ϊ
@Service
@Transactional
public class ProductTypeServiceImpl extends DaoSupport implements
		ProductTypeService {
	/**
	 * ���ط���,�����������ɾ��,ֻ������visible���ɼ�
	 */
	@Override
	public <T> void delete(Class<T> entityClass, Object[] entityId) { // ɾ��id����Ķ���
		if (entityId != null && entityId.length > 0) {
			StringBuffer jpql = new StringBuffer();
			for (int i = 0; i < entityId.length; i++)
				jpql.append("?").append(i + 2).append(","); // o.typeid
															// in(?2,?3,?4...)
			jpql.deleteCharAt(jpql.length() - 1);
			// ��visibleΪ1����һ�и�Ϊ0
			Query query = em.createQuery(
					"update ProductType o set o.visible=?1 where o.typeid in ("
							+ jpql.toString() + ")").setParameter(1, false);
			for (int i = 0; i < entityId.length; i++)
				query.setParameter(i + 2, entityId[i]);
			query.executeUpdate();
		}
	}


}
