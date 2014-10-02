package rcs.suport.util.database.mongoDB;
import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
public class MongoConnection {
	
	private static final String HOST="localhost";
	private static final int PORT= 27017;
	private static final String DB_NAME="TCCGrails_2_4_3";
	
	private static MongoConnection uniqInstance;
	private MongoClient mongo;
	private DB db;
	
	private MongoConnection()
	{
		
	}
	public static synchronized MongoConnection getInstance()
	{
		if(uniqInstance==null)
		{
			uniqInstance=new MongoConnection();
		}return uniqInstance;
	}
	
	//garante a existencia de um unico objeto mongo
	public DB getDB()
	{
		if(mongo==null)
		{
			try{
				mongo=new MongoClient(HOST,PORT);
				db = mongo.getDB(DB_NAME);
				/*
				String userName="jade";
				char[] password={'j','a','d','e'};
				
				boolean authenticate =db.authenticate(userName, password);
				System.out.println("Autenticacao :"+(authenticate?"OK":"ops") );
				
				*/
				
			}catch (UnknownHostException e)
			{
				e.printStackTrace();
			}
			
		}
		return db;
	}

}
