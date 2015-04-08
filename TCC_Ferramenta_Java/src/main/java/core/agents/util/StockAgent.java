package core.agents.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import suport.financial.wallet.Stock;
import suport.util.database.mongoDB.dao.StockDao;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import core.agents.ConversationsID;

public class StockAgent extends Agent {

 private Map<String, ArrayList<Stock>> stocks;
 
	private static final long serialVersionUID = 1L;
	private Date startDate;
	private Date finishDate;
	
	@SuppressWarnings("deprecation")
	protected void setup() 
	{
		try 
		{
			startDate = new Date(2015, 1, 1);
			finishDate=new Date(2015, 3, 1);
			
			stocks=new HashMap<String, ArrayList<Stock>>();
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			DFService.register(this, dfd);
			
			
     			
		} catch (Exception e) 
		{ 
			e.printStackTrace();
			
		}
	}
	
	protected void takedown()
{
		try {
			
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			DFService.deregister(this, dfd);

			
		} catch (Exception e) 
		{
			e.printStackTrace();
			
		}
}

 private class SimulationBehaviour extends CyclicBehaviour
 {
	public SimulationBehaviour(Agent agent)
	{
		super(agent);
	}
	@Override
	public void action() 
	{
		ACLMessage msg =receive();
		if(msg!=null)
		{
			switch (msg.getPerformative()) {
			case ACLMessage.CFP:
			{
				if(msg.getConversationId()==ConversationsID.SUMULATION_REQUEST)
				{
					
				}
				
			}break;

			default:
				break;
			}
		}
		else block();
	}
 }
 
private Stock simulationData(String codeName)
{
	ArrayList<Stock>stockList=null;
	Stock stockReturn=null;
	int indexToReturn=0;
	StockDao stockDao= new StockDao();
	
	if(this.stocks.containsKey(codeName))
	{
		stockList=this.stocks.get(codeName);
		indexToReturn=stockList.size();
		stockReturn=stockList.get(indexToReturn);
		stockList.remove(indexToReturn);
		
		this.stocks.remove(codeName);
		this.stocks.put(codeName, stockList);
		
	}else
	{
		stockList=stockDao.getAllStocksWithPricesBetweenInterval(startDate, finishDate);
		if(stockList.size()>0)
		{
			indexToReturn=stockList.size();
			stockReturn=stockList.get(indexToReturn);
			stockList.remove(indexToReturn);
			this.stocks.put(codeName, stockList);
			
		}else stockReturn=null;
	}
	return stockReturn;
}
}
