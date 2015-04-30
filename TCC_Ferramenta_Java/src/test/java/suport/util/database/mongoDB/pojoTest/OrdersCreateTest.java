package suport.util.database.mongoDB.pojoTest;

import suport.util.database.mongoDB.pojo.OrdersCreate;
import junit.framework.Assert;

public class OrdersCreateTest {

	private OrdersCreate ordersCreate;

	public void setUp() throws Exception {
		ordersCreate = new OrdersCreate();

	}

	public void tearDown() throws Exception {
	}

	public void testGetUserIndetifier() {

		ordersCreate.setUserIndetifier("test");
		Assert.assertEquals("test", ordersCreate.getUserIndetifier());
	}

	public void testGetUserPerfil() {

		ordersCreate.setUserPerfil(1);
		Assert.assertEquals(1, ordersCreate.getUserPerfil());
	}

	public void testGetUserValue() {

		ordersCreate.setUserValue(10);
		Assert.assertEquals(10, ordersCreate.getUserValue(), 0.1);

	}

}
