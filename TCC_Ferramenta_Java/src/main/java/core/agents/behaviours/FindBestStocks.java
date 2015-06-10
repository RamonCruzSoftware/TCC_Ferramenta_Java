package core.agents.behaviours;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.ArrayList;

import suport.financial.wallet.Stock;
import suport.statistical.Statistical;
import suport.util.InfoConversations;
import suport.util.database.mongoDB.dao.StockDao;

public class FindBestStocks implements ProcedureBehaviour {

	private OneShotBehaviour findBestStocks;
	private Agent agent;
	private static final int CORAJOSO = 0;
	private static final int MODERADO = 1;
	private static final int CONSERVADOR = 2;
	private InfoConversations info;
	private StockDao stockDao = null;
	
	private FindBestStocks(){}
	public FindBestStocks(Agent agent)
	{
		this.agent=agent;
		stockDao = new StockDao();
		new Statistical();
		
	}
	public void start() {
	

	}

	public void start(final ACLMessage msg) {
		
		this.findBestStocks=new OneShotBehaviour(agent)
		{
			
			private static final long serialVersionUID = 1L;
			private ACLMessage reply;
			
			@Override
			public void action() {
				
				System.out.println("HUNTER->Pedido Recebido");
				ArrayList<Stock> stocksuggestion = null;
				ArrayList<Stock> stockSuggestions_aux = null;
				int lowerLimit = 0;
				int upperLimit = 0;
				try {
					
					info = (InfoConversations) msg.getContentObject();
					
				} catch (UnreadableException e1) {
					
					e1.printStackTrace();
				}
				stocksuggestion = new ArrayList<Stock>();

				switch (info.getUserProfile()) {// TODO LOG
				case CORAJOSO: {
					lowerLimit = 15;
					upperLimit = 30;
					do {
						stockSuggestions_aux = stockDao
								.getStockOrderByStandardDeviation_30(
										lowerLimit, upperLimit);
						if (lowerLimit > 0)
							lowerLimit--;
						upperLimit++;
						info.setLowerPercent(lowerLimit);
						info.setUpperPercent(upperLimit);
						stocksuggestion = stockSuggestions_aux;
					} while (stocksuggestion.size() < 9);
					info.setStockList(stocksuggestion);
				}
					break;
				case MODERADO: {
					lowerLimit = 5;
					upperLimit = 10;
					do {
						stockSuggestions_aux = stockDao
								.getStockOrderByStandardDeviation_30(
										lowerLimit, upperLimit);
						if (lowerLimit > 0)
							lowerLimit--;
						upperLimit++;
						info.setLowerPercent(lowerLimit);
						info.setUpperPercent(upperLimit);
						stocksuggestion = stockSuggestions_aux;
					} while (stocksuggestion.size() == 0);
					info.setStockList(stocksuggestion);
				}
					break;
				case CONSERVADOR: {
					if (lowerLimit > 0)
						lowerLimit--;
					upperLimit = 6;
					do {
						stockSuggestions_aux = stockDao
								.getStockOrderByStandardDeviation_30(
										lowerLimit, upperLimit);
						upperLimit++;
						info.setLowerPercent(lowerLimit);
						info.setUpperPercent(upperLimit);
						stocksuggestion = stockSuggestions_aux;
					} while (stocksuggestion.size() == 0);
					info.setStockList(stocksuggestion);
				}
					break;
				default:
					break;
				}
				
				try {
					
					reply = msg.createReply();
					reply.setPerformative(ACLMessage.PROPOSE);
					reply.setContentObject(info);
					System.out.println("HUNTER RESPONDEU");
					agent.send(reply);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
		};

		agent.addBehaviour(findBestStocks);

	}

	public void start(Object object) {
		

	}

	public Behaviour getBehaviour() {
		
		return null;
	}

}
