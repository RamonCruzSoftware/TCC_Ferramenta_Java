package core.agents.util;

import suport.financial.partternsCandleStick.CandleStick;
import core.agents.ConversationsID;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class AgentB extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setup() {
		DFAgentDescription agentDescriptor = new DFAgentDescription();
		agentDescriptor.setName(getAID());

		try {
			DFService.register(this, agentDescriptor);
			
			System.out.println("agente "+this.getName());
			addBehaviour(new TickerBehaviour(this,500)
			{
				private static final long serialVersionUID = 1L;

				@Override
				protected void onTick()
				{
					try
					{
						ACLMessage mensagem = new ACLMessage(ACLMessage.CFP);
						mensagem.addReceiver(new AID("simulator", AID.ISLOCALNAME));
						mensagem.setConversationId(ConversationsID.SIMULATION_REQUEST);
						mensagem.setContent("PETR4.SA");
						
						System.out.println("Pedido "+mensagem.getContent()+" Feito");
						myAgent.send(mensagem);
						
					}catch (Exception e)
					{
						// TODO: handle exception
					}
					
				}
			});

			addBehaviour(new CyclicBehaviour(this) 
			{
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void action() {
					ACLMessage reciver = myAgent.receive();
					
					if(reciver!=null)
					{
						if(
								reciver.getPerformative()==ACLMessage.PROPOSE 
								&&
								reciver.getConversationId()==ConversationsID.SIMULATION_REQUEST
								)
						{
							try {
								
								
								CandleStick candle =(CandleStick)reciver.getContentObject();
								String code = candle.getStockCode();
								
								System.out.println(code+" Valor recebido "+candle.getDate());
							
							} catch (UnreadableException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}else 
						block();
					
					
				}
			});
		} catch (FIPAException e) {

			e.printStackTrace();
		}
	}

	public void takeDown() 
	{

	}

//		public Comportamento(Agent agent, int time) {
//			// TODO Auto-generated constructor stub
//		}
//		public void onTicker() {
//			try {
//
//				ACLMessage mensagem = new ACLMessage(ACLMessage.CFP);
//				mensagem.addReceiver(new AID("simulator", AID.ISLOCALNAME));
//				mensagem.setConversationId(ConversationsID.SIMULATION_REQUEST);
//				mensagem.setContent("BAZA3.SA");
//
//				if (!aceitaProposta)
//					myAgent.send(mensagem);
//
//				MessageTemplate template = new MessageTemplate(
//						(MatchExpression) MessageTemplate
//								.MatchPerformative(ACLMessage.PROPOSE));
//				ACLMessage reciver = myAgent.receive(template);
//
//				if (reciver != null) {
//					System.out.println("Recebi " + contador + " proposta(s) ");
//					System.out.println("==>" + reciver.getContent());
//
//					if (contador == 4) {
//						ACLMessage resposta = reciver.createReply();
//						resposta.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
//						finishBehavior = true;
//					} else {
//						ACLMessage resposta = reciver.createReply();
//						resposta.setPerformative(ACLMessage.REJECT_PROPOSAL);
//					}
//
//					contador++;
//
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//		}
}
