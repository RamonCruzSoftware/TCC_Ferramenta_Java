package suport.util.database.mongoDB.dao;

import suport.util.database.mongoDB.MongoConnection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class UserInfoDao {
	
	private DBCollection collection_userInfo;
	private DBCollection collection_userLogged;
	private DBCollection collection_userUnLogged;
	
	
	public  UserInfoDao()
	{
		super();
		try{
			MongoConnection connection=MongoConnection.getInstance();
			DB db=connection.getDB();
			this.setCollection_userInfo(db.getCollection("JADE_userInfo"));
			this.setCollection_userLogged(db.getCollection("JADE_userLogged"));
			this.setCollection_userUnLogged(db.getCollection("JADE_userUnLogged"));
			
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	
	public boolean insertUserUnLogged(String userName)
	{
		try
		{
			BasicDBObject user= new BasicDBObject("_id",userName);
			collection_userUnLogged.insert(user);
			
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isUserUnLogged(String userName)
	{
		boolean result=false;
		BasicDBObject keys = new BasicDBObject();
		keys.put("_id", userName);
		 
		 DBObject userUnLogged=getCollection_userUnLogged().findOne(keys);
		 
		if(userUnLogged!=null)
		{
			result=true;
			this.getCollection_userUnLogged().remove(userUnLogged);
		}
		
		return result;
	}
	
	
	public boolean insertUserLogged(String userName)
	{
		try
		{
			BasicDBObject user= new BasicDBObject("userIdentifier",userName);
			collection_userLogged.insert(user);
			
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
	}
	
	public String userLogged()
	{
		String userIdentifier=null;
		DBObject userLogged=getCollection_userLogged().findOne();
		
		if(userLogged!=null)
		{
			//TODO mudar esse campo para _id
			userIdentifier=userLogged.get("userIdentifier").toString();
			
			this.getCollection_userLogged().remove(userLogged);
		}
		
		return userIdentifier;
	}
	
	

	public DBCollection getCollection_userInfo() {
		return collection_userInfo;
	}

	public void setCollection_userInfo(DBCollection collection_userInfo) {
		this.collection_userInfo = collection_userInfo;
	}

	public DBCollection getCollection_userLogged() {
		return collection_userLogged;
	}

	public void setCollection_userLogged(DBCollection collection_userLogged) {
		this.collection_userLogged = collection_userLogged;
	}

	public DBCollection getCollection_userUnLogged() {
		return collection_userUnLogged;
	}

	public void setCollection_userUnLogged(DBCollection collection_userUnLogged) {
		this.collection_userUnLogged = collection_userUnLogged;
	}
	
	

}
