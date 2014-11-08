package rcs.suport.util.database.mongoDB.pojo;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OrdersCreateTest {
	
	private OrdersCreate ordersCreate;

	@Before
	public void setUp() throws Exception {
		ordersCreate= new OrdersCreate();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetUserIndetifier() {
	
		ordersCreate.setUserIndetifier("test");
		Assert.assertEquals("test", ordersCreate.getUserIndetifier());
	}

	@Test
	public void testGetUserPerfil() {
		
		ordersCreate.setUserPerfil(1);
		Assert.assertEquals(1, ordersCreate.getUserPerfil());
	}

	@Test
	public void testGetUserValue() {
		
		ordersCreate.setUserValue(10);
		Assert.assertEquals(10, ordersCreate.getUserValue(),0.1);
		
	}

}
