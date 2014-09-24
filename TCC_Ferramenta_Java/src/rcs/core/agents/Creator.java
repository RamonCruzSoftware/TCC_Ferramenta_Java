package rcs.core.agents;

import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.PlatformController;
import rcs.suport.util.database.mongoDB.JadeConnectToMongoTest;
import rcs.suport.util.database.mongoDB.dao.OrdersCreateDao;
import rcs.suport.util.database.mongoDB.pojo.OrdersCreate;

public class Creator  extends Agent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrdersCreateDao orderCreateDao;
	private OrdersCreate newOrderCreate;
	
	
	protected void setup()
	{
		orderCreateDao= new OrdersCreateDao();
		
		try
		{
			//create the agent description of ifself
			DFAgentDescription dfd=new DFAgentDescription();
			dfd.setName(getAID());
			DFService.register(this, dfd);
			
			System.out.println("I'm live... My name is "+this.getLocalName());
			
			
			new JadeConnectToMongoTest();
		
			addBehaviour(new TickerBehaviour(this,10) {
				
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				protected void onTick()
				{
					
					newOrderCreate=orderCreateDao.getNewOrderCreate();
					
					if(!(newOrderCreate==null))
					{
						createManagerForUser("Manager_"+newOrderCreate.getUserIndetifier(), newOrderCreate.getUserPerfil(), newOrderCreate.getUserValue());
					}	
				}
			});
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	protected void takedown()
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
	//Methods for manager the manager team
	public void createManagerForUser(final String nameAgentManager,String userPerfilType,double userValue)
	{
		PlatformController container=getContainerController();
		try {
			AgentController  agentController=container.createNewAgent(nameAgentManager, "rcs.core.agents.Manager", null);
			agentController.start();
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
			addBehaviour(new OneShotBehaviour() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void action() {
					
					
					try {
						
						ACLMessage message = new ACLMessage(ACLMessage.INFORM);
						message.addReceiver(new AID(nameAgentManager,AID.ISLOCALNAME));
						message.setLanguage("English");
						message.setOntology("create");
						message.setContentObject(newOrderCreate);
						myAgent.send(message);
						
						
					
					} catch (IOException e) {
						e.printStackTrace();
					}
	
					
				}
			});	
		
	}
	public void droManagerForUser(String userIdentifier)
	{
		
	}
}
