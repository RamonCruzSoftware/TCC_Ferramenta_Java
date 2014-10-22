package rcs.suport.util.database.mongoDB.dao;

import rcs.suport.financial.wallet.Wallet;
import rcs.suport.util.database.mongoDB.MongoConnection;
import rcs.suport.util.database.mongoDB.pojo.UserInfo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
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
			this.collection_userInfo=db.getCollection("userInfo");
			this.collection_userLogged=db.getCollection("userLogged");
			this.collection_userUnLogged=db.getCollection("userUnLogged");
			
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setNewInformationToUser(Wallet walletInfo)
	{
		BasicDBObject keys = new BasicDBObject();
		 keys.put("x", 1);
		 DBCursor cursor = this.collection_userInfo.find(new BasicDBObject(), keys);
		 
		 //Apaga informacoes anteriores
		 while(cursor.hasNext())
		 {
			 collection_userInfo.remove(cursor.next());
		 }
		 
		 BasicDBObject newInformation = new BasicDBObject("userIdentifier", walletInfo.getUserID()).
		            append("walletValue", walletInfo.getWalletValue()).
		            append("walletPercent", walletInfo.getWallterPercent());
		 
		            
		         collection_userInfo.insert(newInformation);
		
	}
	
	public boolean isUserUnLogged(String userName)
	{
		boolean result=false;
		BasicDBObject keys = new BasicDBObject();
		keys.put("_id", userName);
		 
		 DBObject userUnLogged=collection_userUnLogged.findOne(keys);
		 
		if(userUnLogged!=null)
		{
			result=true;
			this.collection_userUnLogged.remove(userUnLogged);
		}
		
		return result;
	}
	
	public String userLogged()
	{
		String userIdentifier=null;
		DBObject userLogged=collection_userLogged.findOne();
		
		if(userLogged!=null)
		{
			userIdentifier=userLogged.get("userIdentifier").toString();
			
			this.collection_userLogged.remove(userLogged);
		}
		
		return userIdentifier;
	}
	
	

}
