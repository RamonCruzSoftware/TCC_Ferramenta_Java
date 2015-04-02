package suport.util.database.mongoDB.pojoTest;
 
import suport.util.database.mongoDB.pojo.UserInfo;
import junit.framework.Assert;

 
public class UserInfoTest {

	private UserInfo userInfo;
	 
	public void setUp() throws Exception {
		
		userInfo= new UserInfo();
		
	}

	 
	public void tearDown() throws Exception {
	}

	 
	public void testUserInfo() {
	
		Assert.assertNotNull(userInfo);
	}

	 
	public void testSetUserIndetifier() {
		
		userInfo.setUserIndetifier("userTest");
		Assert.assertEquals("userTest", userInfo.getUserIndetifier());
		
	}

	 
	public void testSetWalletValue() {
		
		userInfo.setWalletValue(10);
		Assert.assertEquals(10, userInfo.getWalletValue(),0.1);
		
	}

	 
	public void testSetWalletPercent() {
		
		userInfo.setWalletPercent(10);
		Assert.assertEquals(10, userInfo.getWalletPercent(),0.1);
		
	}

}
