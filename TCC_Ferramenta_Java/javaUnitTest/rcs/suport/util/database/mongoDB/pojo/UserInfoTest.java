package rcs.suport.util.database.mongoDB.pojo;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserInfoTest {

	private UserInfo userInfo;
	@Before
	public void setUp() throws Exception {
		
		userInfo= new UserInfo();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUserInfo() {
	
		Assert.assertNotNull(userInfo);
	}

	@Test
	public void testSetUserIndetifier() {
		
		userInfo.setUserIndetifier("userTest");
		Assert.assertEquals("userTest", userInfo.getUserIndetifier());
		
	}

	@Test
	public void testSetWalletValue() {
		
		userInfo.setWalletValue(10);
		Assert.assertEquals(10, userInfo.getWalletValue(),0.1);
		
	}

	@Test
	public void testSetWalletPercent() {
		
		userInfo.setWalletPercent(10);
		Assert.assertEquals(10, userInfo.getWalletPercent(),0.1);
		
	}

}
