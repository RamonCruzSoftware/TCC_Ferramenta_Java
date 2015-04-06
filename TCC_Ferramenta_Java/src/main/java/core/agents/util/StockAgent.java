package core.agents.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import suport.financial.wallet.Stock;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import core.agents.ConversationsID;

public class StockAgent extends Agent {

 private Map<String, ArrayList<Stock>> stocks;
 
	private static final long serialVersionUID = 1L;
	
	protected void setup() 
	{
		try 
		{
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
 
private ArrayList<Stock> simulationData(String codeName)
{
	if(this.stocks.containsKey(codeName))
	{
		return this.stocks.get(codeName);
	}else
	{
		
	}
	return null;
}
}
