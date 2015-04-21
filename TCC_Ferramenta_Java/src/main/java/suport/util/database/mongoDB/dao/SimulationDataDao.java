package suport.util.database.mongoDB.dao;

import java.util.ArrayList;

import suport.util.database.mongoDB.MongoConnection;
import suport.util.database.mongoDB.pojo.ManagedStock;
import suport.util.database.mongoDB.pojo.SimulationData;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class SimulationDataDao {

	private DBCollection collection_simulation;
	private MongoConnection connection;
	private DB db;

	 public SimulationDataDao()
	{
		 	this.setConnection(MongoConnection.getInstance());
			this.setDb(this.getConnection().getDB());
			this.setCollection_managedStock(getDb().getCollection("JADE_Simulation"));
	}
	 
	 public boolean insertManagedStock(SimulationData simulationData)
	 {
			BasicDBObject simulation = null;
		
			try {
					simulation=new BasicDBObject("expertName", simulationData.getExpertName())
												.append("CodeName", simulationData.getCodeName())
												.append("date", simulationData.getDate())
												.append("order", simulationData.getOrder())
												.append("value", simulationData.getValue());
					
					getCollection_simulation().insert(simulation);

			} catch (Exception e) {// TODO LOG
				e.printStackTrace();
				return false;
			}
			return true;
		}
	 public ArrayList<SimulationData> findDataOfSimulation(String codeName)
	 {
		 BasicDBObject where = new BasicDBObject("CodeName",codeName);
		 ArrayList<SimulationData> simulationData=null;
		
		 try
		 {
			 DBCursor cursor = getCollection_simulation().find(where);
			 simulationData=new ArrayList<SimulationData>();
			 BasicDBObject object=null;
			 while(cursor.hasNext())
			 {
				object=(BasicDBObject) cursor.next();
				simulationData.add(
							new SimulationData(
									object.getString("expertName"),
									object.getString("order"),
									object.getDate("date"),
									object.getDouble("value"),
									object.getString("CodeName")
									));
			 }
			 
			 
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		
		 return simulationData;
	 }
	 

	public DBCollection getCollection_simulation() {
		return collection_simulation;
	}

	public void setCollection_managedStock(DBCollection collection_managedStock) {
		this.collection_simulation = collection_managedStock;
	}

	public MongoConnection getConnection() {
		return connection;
	}

	public void setConnection(MongoConnection connection) {
		this.connection = connection;
	}

	public DB getDb() {
		return db;
	}

	public void setDb(DB db) {
		this.db = db;
	}	
	
}
