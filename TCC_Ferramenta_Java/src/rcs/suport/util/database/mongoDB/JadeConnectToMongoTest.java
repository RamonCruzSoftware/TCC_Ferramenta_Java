package rcs.suport.util.database.mongoDB;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class JadeConnectToMongoTest {
	
	private DBCollection coll;
	public JadeConnectToMongoTest()
	{
		super();
		
		try{
			MongoConnection connection=MongoConnection.getInstance();
			DB db=connection.getDB();
			 this.coll=db.getCollection("cria");
			
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean createNewExpert()
	{
		boolean result=false;
		BasicDBObject where=new BasicDBObject();
		where.put("cria", "OK");
		DBCursor cursor= coll.find(where);
		//DBCursor cursor= coll.find();
		
		while (cursor.hasNext())
		{
			String value = (String)cursor.next().get("cria").toString();			
			if(value.length()>0)
				result=true;
			else result=false;
			
		}
		return result;
	}
	
}
