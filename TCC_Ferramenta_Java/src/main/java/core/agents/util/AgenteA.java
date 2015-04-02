package core.agents.util;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.MessageTemplate.MatchExpression;

public class AgenteA extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public void setup()
	{
		DFAgentDescription agentDescriptor = new DFAgentDescription();
		agentDescriptor.setName(getAID());
		
		try {
			DFService.register(this, agentDescriptor);
			
			addBehaviour(new Comportamento());
			
		} catch (FIPAException e)
		{
			
			e.printStackTrace();
		}
	}
	
	public void takeDown()
	{
		
	}
	
	private class Comportamento extends Behaviour
	{
		private static final long serialVersionUID = 1L;
		private boolean finishBehavior=false;
		private boolean aceitaProposta=false;
		private int contador=0;
		
		@Override
		public void action() 
		{
			try
			{
				
				ACLMessage mensagem= new ACLMessage(ACLMessage.CFP);
				mensagem.addReceiver(new AID("agentB",AID.ISLOCALNAME));
				mensagem.setConversationId("STOCKS");
				
				if(!aceitaProposta)
					myAgent.send(mensagem);
				
				
				
				MessageTemplate template= new MessageTemplate((MatchExpression) MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
				ACLMessage reciver= myAgent.receive(template);
				
				if(reciver!=null)
				{
					System.out.println("Recebi "+contador+" proposta(s) ");
					System.out.println("==>" +reciver.getContent());
					
					
					
					if(contador==4)
					{
						ACLMessage resposta=reciver.createReply();
						resposta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
						finishBehavior=true;
					}
					else
					{
						ACLMessage resposta=reciver.createReply();
						resposta.setPerformative(ACLMessage.REJECT_PROPOSAL);
					}
					
					contador++;
					
				}
				
				
				
				
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}

		@Override
		public boolean done() 
		{
			if(finishBehavior)System.out.println(this.getAgent().getLocalName()+": Terminei comportamento !");
			return finishBehavior;
		}
		
	}

}
