package rcs.core.agents;



import jade.core.AID;
import jade.core.Agent;
import jade.core.Service;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.df;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.wrapper.AgentController;
import jade.wrapper.PlatformController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;

import rcs.suport.financial.wallet.Stock;
import rcs.suport.financial.wallet.Wallet;
import rcs.suport.util.InfoConversations;
import rcs.suport.util.database.mongoDB.dao.UserInfoDao;
import rcs.suport.util.database.mongoDB.pojo.OrdersCreate;



public class Manager  extends Agent{

private static final long serialVersionUID = 1L;
private OrdersCreate user;	
private Map<String,ArrayList<Stock>> infoExperts;
private Manager manager;
private UserInfoDao userInfoDao;
private String userName;
private InfoConversations info;

protected void setup()
	{
		/*
		 * Create the hashMap with informations : FullName and an list of Stock Names
		 */
		infoExperts=new HashMap<String,ArrayList<Stock>>();
		manager=this;
		try{
			//create the agent description of ifself
			DFAgentDescription dfd=new DFAgentDescription();
			dfd.setName(getAID());
			DFService.register(this, dfd);
			
			System.out.println("I'm live... My name is "+this.getLocalName());
			
			//Conversations 
			addBehaviour(new CyclicBehaviour(manager) {
				
				@Override
				public void action() {
					
					try
					{
						ACLMessage message= myAgent.receive();
						if(message!=null)
						{
							//Create the Experts 
							if(message.getConversationId()==ConversationsID.INIT_WORK_EXPERTS)
							{
								user=(OrdersCreate)message.getContentObject();
								userName=user.getUserIndetifier();
								
								initWork(manager, user.getUserPerfil(),user.getUserValue(), "Expert_"+user.getUserIndetifier());
								System.out.println("Manager Says: It's user's informations \n Name : "+user.getUserIndetifier()
										+" Profile: "+user.getUserPerfil()+" Value: "+user.getUserValue());
								
							}
							
							//Reply Ok to create 
							if(message.getConversationId()==ConversationsID.CREATE_MANAGER)
							{
								ACLMessage reply=message.createReply();
								if(message.getContent().equalsIgnoreCase(ConversationsID.CREATE_MANAGER))
								{
									reply.setPerformative(ACLMessage.INFORM);
									reply.setContent("Ok,I'm OK!");
									myAgent.send(reply);
									System.out.println("\nManager say:Creator sayed to me - "+message.getContent());
								}
							}
							
							if(message.getConversationId()==ConversationsID.USER_LOGGED)
							{
								userInfoDao= new UserInfoDao();
								userInfoDao.userLogged();
								
								System.out.println("Manager says: Ok, initializing conversation with "+userName);
								userConversations(userName);
								
							}
								
						}else block();
						
					}catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			});
			
		
			
			
			
			
			
	/*		
			addBehaviour(new CyclicBehaviour(this) {
				
				private static final long serialVersionUID = 1L;

				@Override
				public void action() {
					
				ACLMessage message= myAgent.receive();
				if(message!=null)
				{

					try {
						//Create the expert agent
						 user=(OrdersCreate)message.getContentObject();
						createExpertAgents("Expert_"+user.getUserIndetifier(), user.getUserValue(),0);
						
					} catch (UnreadableException e) {
						e.printStackTrace();
					}
				}else block();
			  //Agents Expert are live now 
				
				
				
				
				//Testando capacidade em enviar arrayList
				ACLMessage messageExpert;
				ArrayList<String> arrayList=new ArrayList<String>();
				arrayList.add("Ramon");
				arrayList.add("Cruz");
				arrayList.add("Silva");
				
				System.out.println("Map Size : "+infoExperts.size());
				for(Entry<String, ArrayList<Stock>>expert : infoExperts.entrySet())
				{
					System.out.println("Manager Sending message");
					System.out.println(expert.getKey());
					
					try {
						messageExpert= new ACLMessage(ACLMessage.INFORM);
						messageExpert.addReceiver(new AID(expert.getKey(),AID.ISLOCALNAME));
						messageExpert.setOntology("control");
						messageExpert.setLanguage("English");
						messageExpert.setContentObject(arrayList);
						myAgent.send(messageExpert);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				//Fim teste de envio de ArrayList 
				
					
				}
			});
			
			*/
			
		}catch (Exception e)
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
			
			//kill experts 
			dropExpertAgent();
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
private void dropExpertAgent(String expertName)
{
	PlatformController container=getContainerController();
	try
	{
		if(!(infoExperts.get(expertName).isEmpty()))
		{
			AgentController agentController=container.getAgent(expertName);
			agentController.kill();
			
			//TODO transferir grupo de acoes para outro agente 
			
		}
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	
}

private void dropExpertAgent()
{
	PlatformController container=getContainerController();
	try
	{
		for(Entry<String,ArrayList<Stock>>expertAgent:infoExperts.entrySet())
		{
			System.out.println(expertAgent.getKey()+"killed");
			AgentController agentController=container.getAgent(expertAgent.getKey());
			agentController.kill();
		}
		
	}catch(Exception e)
	{
		e.printStackTrace();
	}
}

private void initWork(Agent agent,int userProfile,double userValue,String userIdentifier)
{
	
	
	manager.info=new InfoConversations(userIdentifier,userProfile);
	
	//Contact an hunter 
	//Yellow pages
	addBehaviour(new OneShotBehaviour(manager)
	{
		
		private static final long serialVersionUID = 1L;
		String hunterName;
		@Override
		public void action() {
					
			try
			{
				DFAgentDescription dfd= new DFAgentDescription();
				
				//Service 
				ServiceDescription service= new ServiceDescription();
				service.setType("StockHunter");
				service.setName("Hunter");
				
				dfd.addServices(service);
				
				//request service on yellow pages 
				DFAgentDescription[] result = DFService.search(manager, dfd);
				
				if(result !=null) hunterName=result[0].getName().getLocalName();
				
				ACLMessage hunterMessage=new ACLMessage(ACLMessage.INFORM);
				hunterMessage.addReceiver(new AID(hunterName, AID.ISLOCALNAME));
				hunterMessage.setLanguage("English");
				hunterMessage.setOntology("stocks");
				hunterMessage.setConversationId(ConversationsID.STOCKS_SUGGESTIONS);
				hunterMessage.setContentObject(manager.info);
				
				myAgent.send(hunterMessage);
				
				//Init conversations 
				manager.AgentsConversations();
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
	});
	
	

}
private void createExperts(int userProfile,String userIdentifier,ArrayList<Stock> listStocks)
{
	PlatformController container=getContainerController();
	AgentController  agentController;
	
	//Create the experts agents

	try
	{
		switch (userProfile) {
		case 0://Corajoso
		{
			for(int i=0;i<2;i++)
			{
				agentController=container.createNewAgent(userIdentifier+"["+(i+1)+"]", "rcs.core.agents.Expert", null);
				agentController.start();
				infoExperts.put(userIdentifier+"["+(i+1)+"]" , null);
			}
		}break;
		case 1://Moderado
		{
			for(int i=0;i<3;i++)
			{
				agentController=container.createNewAgent(userIdentifier+"["+(i+1)+"]", "rcs.core.agents.Expert", null);
				agentController.start();
				infoExperts.put(userIdentifier+"["+(i+1)+"]" , null);
			}
		}break;
		case 2://Conservador
		{
			for(int i=0;i<7;i++)
			{
				agentController=container.createNewAgent(userIdentifier+"["+(i+1)+"]", "rcs.core.agents.Expert", null);
				agentController.start();
				infoExperts.put(userIdentifier+"["+(i+1)+"]" , null);
			}
		}break;
		default:
		{
			for(int i=0;i<1;i++)
			{
				agentController=container.createNewAgent(userIdentifier+"["+(i+1)+"]", "rcs.core.agents.Expert", null);
				agentController.start();
				infoExperts.put(userIdentifier+"["+(i+1)+"]" , null);
			}
		}
			break;
		}
		
		//Divisao das acoes entre os especialistas 
		
		
	}catch(Exception e)
	{
		e.printStackTrace();
	}
	
	
}
private void AgentsConversations()
{
	addBehaviour(new CyclicBehaviour() {
		
		@Override
		public void action() 
		{
			try {
					ACLMessage message= myAgent.receive();
					if(message!=null)
					{
						if(message.getConversationId()==ConversationsID.STOCKS_SUGGESTIONS)
						{
							
								manager.info=(InfoConversations) message.getContentObject();
								System.out.println("Hunter recomendou "+manager.info.getStockList().size()+" Acoes");
		
							
						}
					}
			
			} catch (UnreadableException e) {
				
				e.printStackTrace();
			}
			
		}
	});
}

private void userConversations(final String userIdentifier)
{
	addBehaviour(new Behaviour() {
		UserInfoDao userInfoDao=new UserInfoDao();
		@Override
		public boolean done() {
			
			if(userInfoDao.isUserUnLogged(userIdentifier))
				return true;
			else
			return false;
			
		}
		
		@Override
		public void action() 
		{
			
			Wallet walletInfo=new Wallet();
			walletInfo.setUserID(userIdentifier);
			walletInfo.setWalletValue(100000.f);
			
			userInfoDao.setNewInformationToUser(walletInfo);
			
			
		}
	});
}


}
