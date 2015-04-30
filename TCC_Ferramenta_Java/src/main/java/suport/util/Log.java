package suport.util;

import java.util.Date;

import suport.util.database.mongoDB.MongoConnection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class Log {
	
	private DBCollection collection_JADE_Log;
	private MongoConnection connection;
	private DB db;
	private String logger;

	public Log(String logger) {
		try {
			
			this.logger=logger;
			this.setConnection(MongoConnection.getInstance());
			this.setDb(this.getConnection().getDB());

			this.setCollection_JADE_Log(getDb().getCollection("JADE_log"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void log(String msg)
	{
		BasicDBObject log= new BasicDBObject("level","log")
										.append("message", msg)
										.append("Time", new Date())
										.append("logger", this.logger);
		try
		{
			getCollection_JADE_Log().save(log);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
public void debug(String msg)
{
		BasicDBObject log= new BasicDBObject("level","debug")
							.append("message", msg)
							.append("Time", new Date())
							.append("logger", this.logger);
		try
		{
		getCollection_JADE_Log().save(log);
		}catch(Exception e)
		{
		e.printStackTrace();
		}
}

public void info(String msg)
{
	BasicDBObject log= new BasicDBObject("level","info")
	.append("message", msg)
	.append("Time", new Date())
	.append("logger", this.logger);
	try
	{
		getCollection_JADE_Log().save(log);
		}catch(Exception e)
		{
		e.printStackTrace();
	}
		
}
public void error(String msg)
{
	BasicDBObject log= new BasicDBObject("level","error")
	.append("message", msg)
	.append("Time", new Date())
	.append("logger", this.logger);
	try
	{
		getCollection_JADE_Log().save(log);
		}catch(Exception e)
		{
		e.printStackTrace();
	}
}

	public MongoConnection getConnection() {
		return connection;
	}

	public void setConnection(MongoConnection connection) {
		this.connection = connection;
	}

	public DBCollection getCollection_JADE_Log() {
		return collection_JADE_Log;
	}

	public void setCollection_JADE_Log(DBCollection collection_JADE_Log) {
		this.collection_JADE_Log = collection_JADE_Log;
	}

	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}

}
