package rcs.suport.util.database.mongoDB.dao;

import rcs.suport.util.database.mongoDB.MongoConnection;
import rcs.suport.util.database.mongoDB.pojo.OrdersCreate;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class OrdersCreateDao {
	private DBCollection coll;
	
	public OrdersCreateDao()
	{
		super();
		try{
			MongoConnection connection=MongoConnection.getInstance();
			DB db=connection.getDB();
			 this.coll=db.getCollection("ordersCreate");
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public OrdersCreate getNewOrderCreate()
	{
		OrdersCreate newOrder=new OrdersCreate();
		DBObject newOrderData=coll.findOne();
		
		if(newOrderData!=null)
		{
			newOrder.setUserIndetifier(newOrderData.get("userIdentifier").toString());
			newOrder.setUserPerfil(Integer.parseInt(newOrderData.get("userPerfil").toString()));
			newOrder.setUserValue(Double.parseDouble(newOrderData.get("userValue").toString()));
			
			coll.remove(newOrderData);
			return newOrder;
		}else
		return null;
	}
}

