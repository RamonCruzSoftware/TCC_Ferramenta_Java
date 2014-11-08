package rcs.suport.util.database.mongoDB;

import static org.junit.Assert.*;

import java.net.UnknownHostException;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.junit.Test;

public class MongoConnetionTest {

	private MongoConnection mongoConnetion;
	@Test
	public void testGetInstance() {
		 mongoConnetion= MongoConnection.getInstance();
		 MongoConnection mongoConnection2= MongoConnection.getInstance();
		 
		 Assert.assertEquals(mongoConnetion, mongoConnection2);
		 
	}

	@Test
	public void testGetDB() {
		
		mongoConnetion= MongoConnection.getInstance();
		
		Assert.assertNotNull(mongoConnetion.getDB());
		
		
	}
	
	

}
