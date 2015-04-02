package core.agents.util;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.MessageTemplate.*;

public class AgentB extends Agent {

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
	
	private class Comportamento extends CyclicBehaviour
	{

		
		private static final long serialVersionUID = 1L;
		private int contador =0;

		@Override
		public void action()
		{
			try
			{
				
			
			MessageTemplate pedidoDePropostas= new MessageTemplate((MatchExpression) MessageTemplate.MatchPerformative(ACLMessage.CFP));
			MessageTemplate propostasRejeitadas= new MessageTemplate((MatchExpression) MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL));
			MessageTemplate propostasAceitas = new MessageTemplate((MatchExpression) MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL));
			
			
			ACLMessage propostasMensagens =myAgent.receive(pedidoDePropostas);
			ACLMessage propostaRejeitada= myAgent.receive(propostasRejeitadas);
			ACLMessage propostaAceita= myAgent.receive(propostasAceitas);
			
			ACLMessage resposta=null;
			
			if(propostasMensagens!=null)
			{
				resposta=propostasMensagens.createReply();
				resposta.setPerformative(ACLMessage.PROPOSE);
				resposta.setContent(""+contador);
				myAgent.send(resposta);
				
				System.out.println(this.getAgent().getLocalName()+ "Recebi pedido de proposta.");
				

			}
			
			if(propostaRejeitada!=null)
			{
				contador++;
				
				resposta=propostasMensagens.createReply();
				resposta.setPerformative(ACLMessage.PROPOSE);
				resposta.setContent(""+contador);
				myAgent.send(resposta);
				
				System.out.println(this.getAgent().getLocalName()+ "Proposta rejeitada, vou enviar outra.");
			}
			
			if(propostaAceita!=null)
			{
				System.out.println(this.getAgent().getLocalName()+ "OK, agora a proposta foi aceita.");
			}
			
			}catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		
	}

}
