package core.agents.util;

import suport.financial.partternsCandleStick.CandleStick;
import suport.util.database.mongoDB.pojo.OrdersCreate;
import core.agents.ConversationsID;
import core.agents.behaviours.CommunicationBehaviour;
import core.agents.behaviours.CreateExpertsAgents;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class AgenteA extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CreateExpertsAgents behaviourCreateAgents;
	private CommunicationBehaviour communication;
	private OneShotBehaviour behaviourTest;
	private SimpleBehaviour behaviourTest2;
	
	public void setup() {
		DFAgentDescription agentDescriptor = new DFAgentDescription();
		agentDescriptor.setName(getAID());

		try {
			DFService.register(this, agentDescriptor);
			
			OrdersCreate orderCreate= new OrdersCreate();
			orderCreate.setUserIndetifier("ramon");
			orderCreate.setUserPerfil(0);
			orderCreate.setUserValue(1000);
			
			
			System.out.println("agente "+this.getName());
			behaviourCreateAgents=new CreateExpertsAgents(this, orderCreate);
			behaviourTest= new OneShotBehaviour(this)
			{
				
				@Override
				public void action() {
				
					System.out.println("One Short Woking!");
					
				}
			};
		
			communication = new CommunicationBehaviour(this);		
			communication.addConversationIdToListen(ConversationsID.CREATE_MANAGER, behaviourTest, ACLMessage.INFORM);
			communication.addConversationIdToListen(ConversationsID.CREATE_EXPERTS, behaviourCreateAgents.getBehaviour(), ACLMessage.INFORM);
			communication.start();
			
			
			
		} catch (FIPAException e) {

			e.printStackTrace();
		}
	}

	public void takeDown() 
	{

	}

}
