package rcs.core.agents;

import java.util.ArrayList;

import rcs.suport.financial.wallet.Stock;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;


public class Expert extends Agent {

	private static final long serialVersionUID = 1L;
	private Agent expert;
	protected void setup()
	{
		try{
			expert=this;
			
			DFAgentDescription dfd =new DFAgentDescription();
			dfd.setName(getAID());
			DFService.register(this, dfd);
			
			System.out.println("Hi, I'm live , my name is {"+this.getLocalName()+"}");
			
			//Conversations
			addBehaviour(new CyclicBehaviour() 
			{
				@Override
				public void action() {
				
					ACLMessage msg = myAgent.receive();
					
					if(msg!=null)
					{
						try {
							if(msg.getConversationId()==ConversationsID.INIT_WORK)
							{
								ArrayList<Stock> stockList;
								stockList=(ArrayList<Stock>)msg.getContentObject();
								
								System.out.println("Expert :"+expert.getLocalName()+" Manager sendMe these Stocks:"+stockList);
//								for(Stock s:stockList)
//								{
//									System.out.println(s.getCodeName());
//								}
								
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
