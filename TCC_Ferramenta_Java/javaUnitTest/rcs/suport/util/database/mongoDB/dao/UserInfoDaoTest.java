package rcs.suport.util.database.mongoDB.dao;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserInfoDaoTest {
	
	private UserInfoDao userInfoDao;

	@Before
	public void setUp() throws Exception {
		
		userInfoDao= new UserInfoDao();
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUserInfoDao() {
		
		Assert.assertEquals(userInfoDao.getClass(), UserInfoDao.class);
		Assert.assertNotNull(userInfoDao);
		Assert.assertNotNull(userInfoDao.getCollection_userInfo());
		Assert.assertNotNull(userInfoDao.getCollection_userLogged());
		Assert.assertNotNull(userInfoDao.getCollection_userUnLogged());
		
		
		
	}


	@Test
	public void testIsUserUnLogged() {
		
		Assert.assertTrue(userInfoDao.insertUserUnLogged("userTest"));
		Assert.assertNotNull(userInfoDao.isUserUnLogged("userTest"));
		
	}

	@Test
	public void testUserLogged() {
		
		String user=null;
		Assert.assertTrue(userInfoDao.insertUserLogged("userTest"));
		
		user=userInfoDao.userLogged();
		Assert.assertNotNull(user);
		Assert.assertEquals(user,"userTest");
		
	}

}
