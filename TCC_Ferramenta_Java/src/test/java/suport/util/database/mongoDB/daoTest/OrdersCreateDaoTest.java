package suport.util.database.mongoDB.daoTest;

 
import junit.framework.Assert;

import suport.util.database.mongoDB.dao.OrdersCreateDao;
import suport.util.database.mongoDB.pojo.OrdersCreate;

public class OrdersCreateDaoTest {
	
	private OrdersCreateDao ordersCreateDao;

	 
	public void setUp() throws Exception {
		
		ordersCreateDao= new OrdersCreateDao();
	}
 
	public void tearDown() throws Exception {
		ordersCreateDao.dropOrderCreate("userTest");
		
	}

	 
	public void testOrdersCreateDao() {
		
		Assert.assertEquals(OrdersCreateDao.class, ordersCreateDao.getClass());
		Assert.assertNotNull(ordersCreateDao.getColl());
	}

	 
	public void testGetNewOrderCreate() {
		OrdersCreate order= new OrdersCreate();
		OrdersCreate recurvered=null;
		
		order.setUserIndetifier("userTest");
		order.setUserPerfil(0);
		order.setUserValue(10);
		
		ordersCreateDao.insertOrdersCreate(order);
		
		recurvered= ordersCreateDao.getNewOrderCreate();
		
		Assert.assertNotNull(recurvered);
		Assert.assertEquals(order.getUserIndetifier(), recurvered.getUserIndetifier());
		
		recurvered= ordersCreateDao.getNewOrderCreate();
		
		Assert.assertNull(recurvered);
		
	}
	 
	public void testInsertOrdersCreate()
	{
		OrdersCreate order= new OrdersCreate();
		OrdersCreate recurvered=null;
		
		order.setUserIndetifier("userTest");
		order.setUserPerfil(0);
		order.setUserValue(10);
		
		ordersCreateDao.insertOrdersCreate(order);
		
		recurvered= ordersCreateDao.getNewOrderCreate();
		
		Assert.assertNotNull(recurvered);
		Assert.assertEquals(order.getUserIndetifier(), recurvered.getUserIndetifier());
		
	} 
	public void testDropOrderCreate()
	{
		OrdersCreate order= new OrdersCreate();
		
		order.setUserIndetifier("userTest");
		order.setUserPerfil(0);
		order.setUserValue(10);
		
		ordersCreateDao.insertOrdersCreate(order);
	
		Assert.assertTrue(ordersCreateDao.dropOrderCreate(order.getUserIndetifier()));
	
		
	}

}
