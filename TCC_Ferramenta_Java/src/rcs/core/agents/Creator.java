package rcs.core.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.PlatformController;

import java.io.IOException;

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
			
			
		//	new JadeConnectToMongoTest(); //Nao lembro o pq dessa linha
		
			/**
			 * This Behaviour will go create a Manager Agent for each users
			 */
			addBehaviour(new TickerBehaviour(this,10) 
			{
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
	
	
	
	/**
	 * This method create a team for each users.
	 * 
	 * @param nameAgentManager : It's a manager's name formated for Manager_+ <Use-login> 
	 * @param userPerfilType : It's the profile type of user. Can be "Conservador","Moderado" and "Corajoso"
	 * @param userValue : It's the user's value for investiments
	 */
	public void createManagerForUser(final String nameAgentManager,int userPerfilType,double userValue)
	{
		PlatformController container=getContainerController();
		try 
		{
			AgentController  agentController=container.createNewAgent(nameAgentManager, "rcs.core.agents.Manager", null);
			agentController.start();
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		

			
			
			addBehaviour(new Behaviour(this) 
			{
				boolean stopBehaviour=false;
				
				@Override
				public boolean done() 
				{
					return stopBehaviour;
				}
				
				@Override
				public void action() {
					//Send Message to New Manager
					ACLMessage message=new ACLMessage(ACLMessage.INFORM);
					message.addReceiver(new AID(nameAgentManager,AID.ISLOCALNAME));
					message.setLanguage(ConversationsID.LANGUAGE);
					message.setOntology(ConversationsID.ONTOLOGY_CREATION);
					message.setConversationId(ConversationsID.CREATE_MANAGER);
					message.setContent(ConversationsID.CREATE_MANAGER);
					
					System.out.println("Creater says for "+nameAgentManager+": "+message.getContent());
					myAgent.send(message);
					//Listener messages 
					try
					{
						ACLMessage messages=myAgent.receive();
						if(messages!=null)
						{
							if(message.getConversationId()==ConversationsID.CREATE_MANAGER)
							{
								System.out.println("Manager replies: "+message.getContent());
								stopBehaviour=true;
							}
						}else block();
						
					}catch (Exception e)
					{
						e.printStackTrace();
					}
					
				}
			});
			
			addBehaviour(new OneShotBehaviour() {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void action() {
					try {
						
						ACLMessage message = new ACLMessage(ACLMessage.INFORM);
						message.addReceiver(new AID(nameAgentManager,AID.ISLOCALNAME));
						message.setLanguage(ConversationsID.LANGUAGE);
						message.setOntology(ConversationsID.ONTOLOGY_CREATION);
						message.setConversationId(ConversationsID.INIT_WORK_EXPERTS);
						message.setContentObject(newOrderCreate);
						myAgent.send(message);
					
					} catch (IOException e) {
						e.printStackTrace();
					}
	
					
				}
			});
			
			
			/*
			addBehaviour(new CyclicBehaviour(this) 
			{
				
				
				private static final long serialVersionUID = 1L;

				@Override
				public void action() {
					
					//Send Message to New Manager
					ACLMessage message=new ACLMessage(ACLMessage.INFORM);
					message.addReceiver(new AID(nameAgentManager,AID.ISLOCALNAME));
					message.setLanguage(ConversationsID.LANGUAGE);
					message.setOntology(ConversationsID.ONTOLOGY_CREATION);
					message.setConversationId(ConversationsID.CREATE_MANAGER);
					message.setContent(ConversationsID.CREATE_MANAGER);
					
					myAgent.send(message);
					//Listener messages 
					try
					{
						ACLMessage messages=myAgent.receive();
						if(messages!=null)
						{
							if(message.getConversationId()==ConversationsID.CREATE_MANAGER)
							{
								System.out.print("Manager response: "+message.getContent());
							}
						}else block();
						
					}catch (Exception e)
					{
						e.printStackTrace();
					}

				}
				
				
			});
			
			
		*/	
		
	}
	
	public void droManagerForUser(String userIdentifier)
	{
		
	}
}
