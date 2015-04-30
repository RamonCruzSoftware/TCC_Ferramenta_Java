package rcs.suport.util.database.mongoDB.dao;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import rcs.suport.util.database.mongoDB.pojo.OrdersCreate;

public class OrdersCreateDaoTest {
	
	private OrdersCreateDao ordersCreateDao;

	@Before
	public void setUp() throws Exception {
		
		ordersCreateDao= new OrdersCreateDao();
	}

	@After
	public void tearDown() throws Exception {
		ordersCreateDao.dropOrderCreate("userTest");
		
	}

	@Test
	public void testOrdersCreateDao() {
		
		Assert.assertEquals(OrdersCreateDao.class, ordersCreateDao.getClass());
		Assert.assertNotNull(ordersCreateDao.getColl());
	}

	@Test
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
	@Test
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
	@Test
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
