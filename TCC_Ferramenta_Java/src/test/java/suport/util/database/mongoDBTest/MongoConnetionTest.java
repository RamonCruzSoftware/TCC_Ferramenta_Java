package suport.util.database.mongoDBTest;

import junit.framework.Assert;
import suport.util.database.mongoDB.MongoConnection;

public class MongoConnetionTest {

	private MongoConnection mongoConnetion;

	public void testGetInstance() {
		mongoConnetion = MongoConnection.getInstance();
		MongoConnection mongoConnection2 = MongoConnection.getInstance();

		Assert.assertEquals(mongoConnetion, mongoConnection2);

	}

	public void testGetDB() {

		mongoConnetion = MongoConnection.getInstance();

		Assert.assertNotNull(mongoConnetion.getDB());

	}

}
