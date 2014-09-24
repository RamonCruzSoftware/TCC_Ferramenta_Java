package rcs.core.agents;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;


public class Hunter extends Agent {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hunter hunter;
	protected void setup()
	{
		try
		{
			hunter=this;
			//create the agent description of ifself
			DFAgentDescription dfd=new DFAgentDescription();
			dfd.setName(getAID());
			
			//Create an service in yellow pages
			ServiceDescription service=new ServiceDescription();
			service.setType("Hunt");
			service.setName("Hunting");
			
			
			dfd.addServices(service);
			DFService.register(this, dfd);
			
			System.out.println("I'm live... My name is "+this.getLocalName());
			
			communication(hunter);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	protected void takeDown()
	{
		System.out.println(this.getLocalName()+" says: Bye");
		try
		{
			//Unregister the agent in plataform 
			DFAgentDescription dfd=new DFAgentDescription();
			dfd.setName(getAID());
			DFService.deregister(this, dfd);
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}

private void communication(Agent agent)
{
	addBehaviour(new CyclicBehaviour(agent) {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void action() 
		{
			try
			{
				ACLMessage messages= myAgent.receive();
				if(messages!=null)
				{
					System.out.println(messages.getContent());
				}else block();
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
			
			
		}
	});
}
}
