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
		// JPA的测试方式
		/*
		 * EntityManagerFactory factory=
		 * Persistence.createEntityManagerFactory("itcast"); EntityManager
		 * em=factory.createEntityManager(); em.getTransaction().begin();
		 * em.persist(new ProductType()); //储存第一个ProductType
		 * em.getTransaction().commit(); factory.close();
		 */

		// Spring容器的测试
		/*
		 * ClassPathXmlApplicationContext ctx=new
		 * ClassPathXmlApplicationContext("beans.xml"); DataSource
		 * dataSource=(DataSource) ctx.getBean("dataSource");
		 * System.out.println(dataSource);
		 */

		// 业务方法测试
		/*
		 * ClassPathXmlApplicationContext ctx = new
		 * ClassPathXmlApplicationContext( "beans.xml"); ProductTypeService pro
		 * = (ProductTypeService) ctx .getBean("productTypeServiceImpl");
		 * ProductType type = new ProductType(); type.setName("瑜伽产品");
		 * type.setNote("这是瑜伽产品"); pro.save(type);
		 */
	}

	/* 测试让visible不可见的delete方法 */
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
			type.setName(i + "篮球用品");
			type.setNote("好篮球");
			productService.save(type);
		}
	}

	@Test
	public void testFind()
	{
		ProductType type = productService.find(ProductType.class, 1);
		Assert.assertNotEquals("获取不到id为1的记录", type);
	}

	@Test
	public void testgetScrollData()
	{
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("typeid", "asc");
		/*//有附加条件的排列
		 * QueryResult<ProductType> qr = productService.getScrollData(
		 * ProductType.class, 0, 5, "o.visible=?1", new Object[] { true },
		 * orderby);
		 */
		/*//没有附加条件的排列
		 * QueryResult<ProductType> qr = productService.getScrollData(
		 * ProductType.class, 0, 5, "o.visible=?1", new Object[] { true });
		 */
		// 获取所有数据
		QueryResult<ProductType> qr = productService
				.getScrollData(ProductType.class);
		for (ProductType t : qr.getResultlist())
			System.out.println(t.getName());

	}
}
/*
 * 报错:org/apache/commons/collections/map/lru/Map 缺少commons-collections.jar包
 * 
 * jdk自带一个DataSource的包,不要搞错了.
 * 
 * com.sun.proxy.$Proxy14 cannot be cast to
 * cn.itcast.serviceImpl.ProductServiceImpl 强转出错
 */