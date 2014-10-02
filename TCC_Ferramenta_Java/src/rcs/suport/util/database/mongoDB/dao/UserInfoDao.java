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
	
	private DBCollection coll;
	
	public  UserInfoDao()
	{
		super();
		try{
			MongoConnection connection=MongoConnection.getInstance();
			DB db=connection.getDB();
			 this.coll=db.getCollection("userInfo");
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setNewInformationToUser(Wallet walletInfo)
	{
		BasicDBObject keys = new BasicDBObject();
		 keys.put("x", 1);
		 
		 DBCursor cursor = this.coll.find(new BasicDBObject(), keys);
		 
		 //Apaga informacoes anteriores
		 while(cursor.hasNext())
		 {
			 coll.remove(cursor.next());
		 }
		 
		 BasicDBObject newInformation = new BasicDBObject("userIdentifier", walletInfo.getUserID()).
		            append("walletValue", walletInfo.getWalletValue()).
		            append("walletPercent", walletInfo.getWallterPercent());
		 
		            
		         coll.insert(newInformation);
		
	}
	
	public String userLogged()
	{
		String userIdentifier=null;
		DBObject userLogged=coll.findOne();
		
		if(userLogged!=null)
		{
			userIdentifier=userLogged.get("userIdentifier").toString();
			
			this.coll.remove(userLogged);
		}
		
		return userIdentifier;
	}

}
