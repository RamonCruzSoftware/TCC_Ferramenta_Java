package suport.util.database.mongoDB.daoTest;

 
import suport.util.database.mongoDB.dao.UserInfoDao;
import junit.framework.Assert;


public class UserInfoDaoTest {
	
	private UserInfoDao userInfoDao;

	 
	public void setUp() throws Exception {
		
		userInfoDao= new UserInfoDao();
		
	}

	 
	public void tearDown() throws Exception {
	}

	 
	public void testUserInfoDao() {
		
		Assert.assertEquals(userInfoDao.getClass(), UserInfoDao.class);
		Assert.assertNotNull(userInfoDao);
		Assert.assertNotNull(userInfoDao.getCollection_userInfo());
		Assert.assertNotNull(userInfoDao.getCollection_userLogged());
		Assert.assertNotNull(userInfoDao.getCollection_userUnLogged());
		
		
		
	}

 
	public void testIsUserUnLogged() {
		
		Assert.assertTrue(userInfoDao.insertUserUnLogged("userTest"));
		Assert.assertNotNull(userInfoDao.isUserUnLogged("userTest"));
		
	}

	 
	public void testUserLogged() {
		
		String user=null;
		Assert.assertTrue(userInfoDao.insertUserLogged("userTest"));
		
		user=userInfoDao.userLogged();
		Assert.assertNotNull(user);
		Assert.assertEquals(user,"userTest");
		
	}

}
