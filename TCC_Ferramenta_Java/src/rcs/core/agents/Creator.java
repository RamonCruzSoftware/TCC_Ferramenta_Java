package rcs.core.agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.PlatformController;

import java.io.IOException;

import rcs.suport.util.database.mongoDB.dao.OrdersCreateDao;
import rcs.suport.util.database.mongoDB.dao.UserInfoDao;
import rcs.suport.util.database.mongoDB.pojo.OrdersCreate;

public class Creator  extends Agent{
	
	private static final long serialVersionUID = 1L;
	private OrdersCreateDao orderCreateDao;
	private OrdersCreate newOrderCreate;
	private UserInfoDao userInfoDao;
	private String userLogged;

	protected void setup()
	{
		orderCreateDao= new OrdersCreateDao();
		
		try
		{
			
			DFAgentDescription dfd=new DFAgentDescription();
			dfd.setName(getAID());
			DFService.register(this, dfd);
			
			System.out.println("I'm live... My name is "+this.getLocalName());
			
		
			/**
			 * Este comportamento fica observando as requesicoes dos usuarios enviados pelo grails
			 * 
			 */
			addBehaviour(new ListenGrails(this,(long)10));
			
		
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
	
	
	private class ListenGrails extends TickerBehaviour
	{
		private Agent creatorAgent;
		public ListenGrails(Agent agent, Long period)
		{
			super(agent,period);
			creatorAgent=agent;
			
		}
		
		private static final long serialVersionUID = 1L;

		@Override
		protected void onTick() 
		{
			userInfoDao=new UserInfoDao();
			newOrderCreate=orderCreateDao.getNewOrderCreate();
			userLogged=userInfoDao.userLogged();
			
			if(!(newOrderCreate==null))
			{
				createManagerForUser("Manager_"+newOrderCreate.getUserIndetifier(), newOrderCreate.getUserPerfil(), newOrderCreate.getUserValue());
			}
			
			if(!(userLogged==null))
			{
				addBehaviour(new OneShotBehaviour() {
					
					private static final long serialVersionUID = 1L;
					@Override
					public void action() 
					{
						ACLMessage message=new ACLMessage(ACLMessage.INFORM);
						
						message.setLanguage(ConversationsID.LANGUAGE);
						message.setPerformative(ACLMessage.INFORM);
						message.setConversationId(ConversationsID.USER_LOGGED);
						message.setContent(userLogged);
						message.addReceiver(new AID("Manager_"+userLogged, AID.ISLOCALNAME));
									
						System.out.println("Creator says: User "+userLogged+" is logged" );
						myAgent.send(message);
						
					}
				});
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
				//Xms128m
				Object [] argument;
				argument= new Object[1];
				argument[0]="Xms1024m";
				
				AgentController  agentController=container.createNewAgent(nameAgentManager, "rcs.core.agents.Manager",argument);
				
				agentController.start();
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
				addBehaviour(new Behaviour(creatorAgent) 
				{
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
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
									
									stopBehaviour=true;
								}
							}else block();
							
						}catch (Exception e)
						{
							e.printStackTrace();
						}
						
					}
				});
				
				addBehaviour(new OneShotBehaviour() 
				{
					
					private static final long serialVersionUID = 1L;

					@Override
					public void action() {
						try {
							
							ACLMessage message = new ACLMessage(ACLMessage.INFORM);
							message.addReceiver(new AID(nameAgentManager,AID.ISLOCALNAME));
							message.setLanguage(ConversationsID.LANGUAGE);
							message.setOntology(ConversationsID.ONTOLOGY_CREATION);
							message.setConversationId(ConversationsID.CREATE_EXPERTS);
							message.setContentObject(newOrderCreate);
							myAgent.send(message);
						
						} catch (IOException e)
						{
							e.printStackTrace();
						}
		
						
					}
				});
				
				
				
		}
		
	}
	
	public void dropManagerForUser(String userIdentifier)
	{
		
	}
}
