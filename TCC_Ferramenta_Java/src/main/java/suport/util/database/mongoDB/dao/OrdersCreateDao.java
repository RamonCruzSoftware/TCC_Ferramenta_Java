package suport.util.database.mongoDB.dao;
import suport.util.database.mongoDB.MongoConnection;
import suport.util.database.mongoDB.pojo.OrdersCreate;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class OrdersCreateDao {
	private DBCollection coll;
	
	public OrdersCreateDao()
	{
		super();
		try{
			MongoConnection connection=MongoConnection.getInstance();
			DB db=connection.getDB();
			 this.setColl(db.getCollection("JADE_ordersCreate"));
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public OrdersCreate getNewOrderCreate()
	{
	
		OrdersCreate newOrder=new OrdersCreate();
		DBObject newOrderData=getColl().findOne();
		
		if(newOrderData!=null)
		{
			newOrder.setUserIndetifier(newOrderData.get("userIdentifier").toString());
			newOrder.setUserPerfil(Integer.parseInt(newOrderData.get("userPerfil").toString()));
			newOrder.setUserValue(Double.parseDouble(newOrderData.get("userValue").toString()));
			
			getColl().remove(newOrderData);
			return newOrder;
		}else
		return null;
	}
	
	public boolean insertOrdersCreate(OrdersCreate order)
	{
		boolean result=false;
		
		try
		{
			BasicDBObject norder= new BasicDBObject("userIdentifier",order.getUserIndetifier())
			.append("userPerfil", order.getUserPerfil())
			.append("userValue", order.getUserValue());

			coll.insert(norder);
			result=true;

		}catch(Exception e)
		{
			e.printStackTrace();
			result=false;
		}
		
		
		return result;
	}
	
	public boolean dropOrderCreate(String userIdentifier)
	{
		boolean result= false;
		
		try
		{
			BasicDBObject where = new BasicDBObject("userIdentifier",userIdentifier);
			DBCursor cursor= coll.find(where);
			
			while (cursor.hasNext())
			{
				coll.remove((BasicDBObject)cursor.next());
				result= true;
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			result= false;
		}
		
		
		return result;
	}
	public DBCollection getColl() {
		return coll;
	}

	public void setColl(DBCollection coll) {
		this.coll = coll;
	}
}

