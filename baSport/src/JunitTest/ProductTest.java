package JunitTest;

import java.util.LinkedHashMap;

import org.jboss.weld.context.ApplicationContext;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.ProductType;
import cn.itcast.bean.QueryResult;
import cn.itcast.service.product.ProductTypeService;

public class ProductTest {

	private static ClassPathXmlApplicationContext ctx;
	private static ProductTypeService productService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		try
		{
			ctx = new ClassPathXmlApplicationContext("beans.xml");
			productService = (ProductTypeService) ctx
					.getBean("productTypeServiceImpl");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Test
	public void runtest()
	{
		// JPA�Ĳ��Է�ʽ
		/*
		 * EntityManagerFactory factory=
		 * Persistence.createEntityManagerFactory("itcast"); EntityManager
		 * em=factory.createEntityManager(); em.getTransaction().begin();
		 * em.persist(new ProductType()); //�����һ��ProductType
		 * em.getTransaction().commit(); factory.close();
		 */

		// Spring�����Ĳ���
		/*
		 * ClassPathXmlApplicationContext ctx=new
		 * ClassPathXmlApplicationContext("beans.xml"); DataSource
		 * dataSource=(DataSource) ctx.getBean("dataSource");
		 * System.out.println(dataSource);
		 */

		// ҵ�񷽷�����
		/*
		 * ClassPathXmlApplicationContext ctx = new
		 * ClassPathXmlApplicationContext( "beans.xml"); ProductTypeService pro
		 * = (ProductTypeService) ctx .getBean("productTypeServiceImpl");
		 * ProductType type = new ProductType(); type.setName("�٤��Ʒ");
		 * type.setNote("�����٤��Ʒ"); pro.save(type);
		 */
	}

	/* ������visible���ɼ���delete���� */
	@Test
	public void testDelete()
	{
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"beans.xml");
		ProductTypeService pro = (ProductTypeService) ctx
				.getBean("productTypeServiceImpl");
		pro.delete(ProductType.class, 1);
	}

	@Test
	public void testSave()
	{
		for (int i = 0; i < 20; i++)
		{
			ProductType type = new ProductType();
			type.setName(i + "������Ʒ");
			type.setNote("������");
			productService.save(type);
		}
	}

	@Test
	public void testFind()
	{
		ProductType type = productService.find(ProductType.class, 1);
		Assert.assertNotEquals("��ȡ����idΪ1�ļ�¼", type);
	}

	@Test
	public void testgetScrollData()
	{
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("typeid", "asc");
		/*//�и�������������
		 * QueryResult<ProductType> qr = productService.getScrollData(
		 * ProductType.class, 0, 5, "o.visible=?1", new Object[] { true },
		 * orderby);
		 */
		/*//û�и�������������
		 * QueryResult<ProductType> qr = productService.getScrollData(
		 * ProductType.class, 0, 5, "o.visible=?1", new Object[] { true });
		 */
		// ��ȡ��������
		QueryResult<ProductType> qr = productService
				.getScrollData(ProductType.class);
		for (ProductType t : qr.getResultlist())
			System.out.println(t.getName());

	}
}
/*
 * ����:org/apache/commons/collections/map/lru/Map ȱ��commons-collections.jar��
 * 
 * jdk�Դ�һ��DataSource�İ�,��Ҫ�����.
 * 
 * com.sun.proxy.$Proxy14 cannot be cast to
 * cn.itcast.serviceImpl.ProductServiceImpl ǿת����
 */