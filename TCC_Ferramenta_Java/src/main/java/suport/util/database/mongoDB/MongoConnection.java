package suport.util.database.mongoDB;
import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
public class MongoConnection {
	private static  String HOST="localhost";
	private static  int PORT= 27017;
	private static  String DB_NAME="TCCGrails_2_4_3";
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
				
			}catch (UnknownHostException e)
			{//TODO LOG
				e.printStackTrace();
			}
		}
		return db;
	}
	public static String getHOST() {
		return HOST;
	}
	public static void setHOST(String hOST) {
		HOST = hOST;
	}
	public static int getPORT() {
		return PORT;
	}
	public static void setPORT(int pORT) {
		PORT = pORT;
	}
	public static String getDB_NAME() {
		return DB_NAME;
	}
	public static void setDB_NAME(String dB_NAME) {
		DB_NAME = dB_NAME;
	}
}
