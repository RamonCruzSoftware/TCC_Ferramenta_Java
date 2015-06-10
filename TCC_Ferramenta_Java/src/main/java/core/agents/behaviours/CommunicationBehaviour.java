package core.agents.behaviours;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;

import core.agents.ConversationsID;


public class CommunicationBehaviour
{

	private Agent agent;
	private boolean hasArgument;
	private Map<String, ProcedureBehaviour> conversationsINFORM;
	private Map<String, ProcedureBehaviour> conversationsAGREE;
	private Map<String, ProcedureBehaviour> conversationsCFP;
	private Map<String, ProcedureBehaviour> conversationsPROPOSE;
	private Map<String, ProcedureBehaviour> conversationsREFUSE;
	private Map<String, ProcedureBehaviour> conversationsREQUEST;
	private Map<String, ProcedureBehaviour> conversationsACCEPT_PROPOSAL;
	
	private Map<Integer,HashMap<String, ProcedureBehaviour> >performatives;
	private ArrayList<String> conversarioIDSubBehaviours;
	
	private CyclicBehaviour listenBehaviour;
	public CommunicationBehaviour(Agent agent)
	{
		this.hasArgument=false;
		this.agent=agent;
		this.conversationsINFORM = new HashMap<String, ProcedureBehaviour>();
		this.conversationsAGREE = new HashMap<String, ProcedureBehaviour>();
		this.conversationsCFP = new HashMap<String, ProcedureBehaviour>();
		this.conversationsPROPOSE = new HashMap<String, ProcedureBehaviour>();
		this.conversationsREFUSE = new HashMap<String, ProcedureBehaviour>();
		this.conversationsREQUEST = new HashMap<String, ProcedureBehaviour>();
		this.conversationsACCEPT_PROPOSAL = new HashMap<String, ProcedureBehaviour>();
		
		this.performatives=new HashMap<Integer, HashMap<String,ProcedureBehaviour>>();
		
		this.performatives.put(ACLMessage.INFORM, (HashMap<String, ProcedureBehaviour>) this.conversationsINFORM);
		this.performatives.put(ACLMessage.AGREE, (HashMap<String, ProcedureBehaviour>) this.conversationsAGREE);
		this.performatives.put(ACLMessage.CFP, (HashMap<String, ProcedureBehaviour>) this.conversationsCFP);
		this.performatives.put(ACLMessage.PROPOSE, (HashMap<String, ProcedureBehaviour>) this.conversationsPROPOSE);
		this.performatives.put(ACLMessage.REFUSE, (HashMap<String, ProcedureBehaviour>) this.conversationsREFUSE);
		this.performatives.put(ACLMessage.REQUEST, (HashMap<String, ProcedureBehaviour>) this.conversationsREQUEST);
		this.performatives.put(ACLMessage.ACCEPT_PROPOSAL, (HashMap<String, ProcedureBehaviour>) this.conversationsACCEPT_PROPOSAL);
		
		this.conversarioIDSubBehaviours= new ArrayList<String>();
		
	}
	@SuppressWarnings("unused")
	private CommunicationBehaviour(){}
	
	public void addConversationIdToListen(String conversationID,ProcedureBehaviour behaviourToTake,int performative)
	{
		switch (performative)
		{
		case ACLMessage.INFORM:
		{
			this.conversationsINFORM.put(conversationID, behaviourToTake);
		}
			
			break;

		case ACLMessage.AGREE:
		{
			this.conversationsAGREE.put(conversationID, behaviourToTake);
		}
			break;
		case ACLMessage.CFP:
		{
			this.conversationsCFP.put(conversationID, behaviourToTake);
		}
			break;
		case ACLMessage.PROPOSE:
		{
			this.conversationsPROPOSE.put(conversationID, behaviourToTake);
		}
			break;
		case ACLMessage.REFUSE:
		{
			this.conversationsREFUSE.put(conversationID, behaviourToTake);
		}
			break;
		case ACLMessage.REQUEST:
		{
			this.conversationsREQUEST.put(conversationID, behaviourToTake);
		}
			break;
		case ACLMessage.ACCEPT_PROPOSAL:
		{
			this.conversationsACCEPT_PROPOSAL.put(conversationID, behaviourToTake);
		}
			break;
			
		default:
			break;
		}
		
		
	}
	public void addConversationIdToListen(String conversationID,ProcedureBehaviour behaviourToTake,int performative,boolean hasArgument)
	{
		this.hasArgument=hasArgument;
		switch (performative)
		{
		case ACLMessage.INFORM:
		{
			this.conversationsINFORM.put(conversationID, behaviourToTake);
		}
			
			break;

		case ACLMessage.AGREE:
		{
			this.conversationsAGREE.put(conversationID, behaviourToTake);
		}
			break;
		case ACLMessage.CFP:
		{
			this.conversationsCFP.put(conversationID, behaviourToTake);
		}
			break;
		case ACLMessage.PROPOSE:
		{
			this.conversationsPROPOSE.put(conversationID, behaviourToTake);
		}
			break;
		case ACLMessage.REFUSE:
		{
			this.conversationsREFUSE.put(conversationID, behaviourToTake);
		}
			break;
		case ACLMessage.REQUEST:
		{
			this.conversationsREQUEST.put(conversationID, behaviourToTake);
		}
			break;
		case ACLMessage.ACCEPT_PROPOSAL:
		{
			this.conversationsACCEPT_PROPOSAL.put(conversationID, behaviourToTake);
		}
			break;
			
		default:
			break;
		}
		
		
	}
	public void start()
	{
		this.listenBehaviour=new ListenBehaviour(this.agent, this.performatives);
		this.agent.addBehaviour(listenBehaviour);
		
		
	}
	public void removeListenBehaviour()
	{
		this.agent.removeBehaviour(listenBehaviour);
	}
	
	
	private class ListenBehaviour extends CyclicBehaviour
	{
		private static final long serialVersionUID = 1L;
		private Map<Integer,HashMap<String, ProcedureBehaviour> > conversationsId; 
		private ProcedureBehaviour behaviour;
		private MessageTemplate filter;
		private MessageTemplate a,b,c,d,e,f,g;
		private MessageTemplate groupA,groupB,groupC,groupD;
		private MessageTemplate group1,group2;
		
		
		public  ListenBehaviour(Agent agent,Map<Integer,HashMap<String, ProcedureBehaviour> > conversationsIDToListen)
		{
			super(agent);
			this.conversationsId=conversationsIDToListen;
			
			a=MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			b=MessageTemplate.MatchPerformative(ACLMessage.AGREE);
			c=MessageTemplate.MatchPerformative(ACLMessage.CFP);
			d=MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
			
			e=MessageTemplate.MatchPerformative(ACLMessage.REFUSE);
			f=MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
			g=MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
			
			groupA=MessageTemplate.or(a, b);
			groupB=MessageTemplate.or(c, d);
			
			groupC=MessageTemplate.or(e, f);
			groupD=MessageTemplate.or(g, g);
			
			group1=MessageTemplate.or(groupA, groupB);
			group2=MessageTemplate.or(groupC, groupD);
			
			filter=MessageTemplate.or(group1, group2);
					
		}
		
		@Override
		public void action() {
			
			ACLMessage msg=agent.receive(filter);
			try
			{
				if (msg!=null)
				{
					behaviour=this.conversationsId.get(msg.getPerformative()).get(msg.getConversationId());
					if(behaviour!=null)
					{
						if(hasArgument)	behaviour.start(msg);
						else behaviour.start();
					}else 
						agent.send(msg);
					
				}else block();
			}catch(Exception e)
			{
					e.printStackTrace();
			}
			
		}
		
	}

}
