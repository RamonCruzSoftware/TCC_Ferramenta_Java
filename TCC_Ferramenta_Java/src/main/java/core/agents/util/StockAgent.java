package core.agents.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import main.ClasseA;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import suport.financial.partternsCandleStick.CandleStick;
import suport.financial.wallet.Stock;
import suport.util.database.mongoDB.dao.StockDao;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import core.agents.ConversationsID;

public class StockAgent extends Agent {

	private static final long serialVersionUID = 1L;
	
	private Map<String, ArrayList<CandleStick>>stockCandleList;
	private Date startDate;
	private Date finishDate;
	private StockAgent stockAgent;
	
	@SuppressWarnings("deprecation")
	protected void setup() 
	{
		try 
		{
			stockAgent=this;
			startDate = new Date(2015, 1, 1);
			finishDate=new Date(2015, 3, 1);
			new HashMap<String, ArrayList<Stock>>();
			stockCandleList=new HashMap<String, ArrayList<CandleStick>>();	
			this.loadSimulationData();
			
			
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(getAID());
			DFService.register(this, dfd);
			
			addBehaviour(new SimulationBehaviour(stockAgent));
     			
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
	
	private static final long serialVersionUID = 1L;
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
				if(msg.getConversationId()==ConversationsID.SIMULATION_REQUEST)
				{
					try {
						ACLMessage reply=msg.createReply();
						reply.setConversationId(ConversationsID.SIMULATION_REQUEST);
						reply.setPerformative(ACLMessage.PROPOSE);
						reply.setContent(msg.getContent());
						reply.setContentObject(stockAgent.simulation(msg.getContent()));
						myAgent.send(reply);
						
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
				
			}break;

			default:
				break;
			}
		}
		else block();
	}
 }
 public CandleStick simulation(String codeName)
	{
		Stock stockAux=null;
		int indexToReturn=0;
		StockDao stockDao= new StockDao();
		
		CandleStick returnCandle=null;
		ArrayList<CandleStick>candleList=null;
		
		if(this.stockCandleList.containsKey(codeName))
		{
			candleList=this.stockCandleList.get(codeName);
			indexToReturn=candleList.size()-1;
			returnCandle=candleList.get(indexToReturn);
			candleList.remove(indexToReturn);	
			this.stockCandleList.remove(codeName);
			this.stockCandleList.put(codeName, candleList);
			
		}else
		{
			stockAux=stockDao.getStocksWithPricesBetweenInterval(codeName,startDate, finishDate);
			if(stockAux!=null&& stockAux.getCandleSticks().size()>0)
			{
				candleList=new ArrayList<CandleStick>();
				for(CandleStick candle:stockAux.getCandleSticks())
				{
					candleList.add(candle);
				}
			}else returnCandle=null;
		}
		return returnCandle;
	}
	public void loadSimulationData()
	{
		StockDao stockDao= new StockDao();
		ArrayList<Stock>stockList=null;
		
		stockList=stockDao.getAllStocksWithPricesBetweenInterval(startDate, finishDate);
		this.stockCandleList = new HashMap<String, ArrayList<CandleStick>>();
		if(stockList!=null && stockList.size()>0)
		{
			for(Stock stock : stockList)
			{
				if(stock.getCandleSticks().size()>0)
					this.stockCandleList.put(stock.getCodeName(), stock.getCandleSticks());
			}
		}
//		System.out.println("Carregado com "+this.stockCandleList.size());
//		for(Entry<String, ArrayList<CandleStick>>c:this.stockCandleList.entrySet())
//		{
//			System.out.println("Code "+c.getKey());
//		}
		
	}
}
