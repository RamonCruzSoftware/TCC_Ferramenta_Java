package rcs.core.agents;

import java.util.ArrayList;

import rcs.suport.financial.wallet.Stock;
import rcs.suport.util.database.mongoDB.dao.StockDao;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;


public class Expert extends Agent {

	private static final long serialVersionUID = 1L;
	private Expert expert;
	private ArrayList<Stock> stockList;
	private StockDao stockDao;
	
	protected void setup()
	{
		try{
			expert=this;
			stockDao=new StockDao();
			
		
			DFAgentDescription dfd =new DFAgentDescription();
			dfd.setName(getAID());
			DFService.register(this, dfd);
			
			System.out.println("Hi, I'm live , my name is "+this.getLocalName());
			
			//Conversations
			addBehaviour(new CyclicBehaviour(expert) 
			{
				@Override
				public void action() {
				
					ACLMessage msg = myAgent.receive();
					
					if(msg!=null)
					{
						try {
								if(msg.getConversationId()==ConversationsID.INIT_WORK)
								{
									
									expert.stockList=(ArrayList<Stock>)msg.getContentObject();
									
									
									if(stockList!=null)
									{
										System.out.println("Expert :"+expert.getLocalName()+" Manager sendMe these Stocks:");
										for(Stock s:stockList)
										{
											System.out.println(s.getCodeName());
										}
									}
									
								}
								if(msg.getConversationId()==ConversationsID.USER_LOGGED)
								{
									
									
								}
							
								
								
						} catch (UnreadableException e) {
						
							e.printStackTrace();
						}
					}else block();
					
				}
			});
			  
		
		}
        catch (Exception e) {
            System.out.println( "Saw exception in HostAgent: " + e );
            e.printStackTrace();
        }
	}
	
	
	
	
	protected void takeDown()
	{
		System.out.println(getLocalName()+" says: Morri");
		try
		{
			DFAgentDescription dfd=new DFAgentDescription();
			dfd.setName(getAID());
			DFService.deregister(this, dfd);
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	

}
