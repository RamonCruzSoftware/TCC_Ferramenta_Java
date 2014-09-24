package rcs.core.agents;

import java.util.ArrayList;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;


public class Expert extends Agent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setup()
	{
		try{
			
			DFAgentDescription dfd =new DFAgentDescription();
			dfd.setName(getAID());
			DFService.register(this, dfd);
			
			System.out.println("Hi, I'm live , my name is "+this.getLocalName());
			
			
			//testando recebimento de ArrayList como paramentro 
			  
			addBehaviour(new CyclicBehaviour(this) {
				@Override
				public void action() {
				
					ACLMessage msg = myAgent.receive();
					if(msg!=null)
					{
						try {
							ArrayList<String> arrayList;
							arrayList=(ArrayList<String>)msg.getContentObject();
							System.out.println("Manager sendMe:");
							for(String info : arrayList)
							{
								System.out.println(info);
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
