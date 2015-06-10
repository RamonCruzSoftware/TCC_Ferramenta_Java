package core.agents.behaviours;

import java.io.IOException;
import java.util.ArrayList;

import core.agents.ConversationsID;
import suport.financial.wallet.Stock;
import suport.statistical.Statistical;
import suport.util.InfoConversations;
import suport.util.database.mongoDB.dao.StockDao;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class FindBestStocksAgain implements ProcedureBehaviour {

	private OneShotBehaviour findBestStocks;
	private Agent agent;
	private static final int CORAJOSO = 0;
	private static final int MODERADO = 1;
	private static final int CONSERVADOR = 2;
	private InfoConversations info;
	private StockDao stockDao = null;
	
	private  FindBestStocksAgain(){}
	public FindBestStocksAgain(Agent agent)
	{
		this.agent=agent;
		stockDao = new StockDao();
		new Statistical();
		
	}
	public void start() {
		// TODO Auto-generated method stub

	}

public void start(final ACLMessage msg) {
		
		this.findBestStocks=new OneShotBehaviour(agent)
		{
			
			private static final long serialVersionUID = 1L;
			private ACLMessage reply;
			
			@Override
			public void action() {
				
				ArrayList<Stock> stocksuggestion = null;
				int lowerLimit = 0;
				int upperLimit = 0;
				
					switch (info.getUserProfile()) {
					case CORAJOSO: {
						lowerLimit = info.getLowerPercent();
						upperLimit = info.getUpperPercent();
						int countLoop = 0;
						do {
							stocksuggestion=null;
							stocksuggestion = stockDao.getStockOrderByStandardDeviation_30(
											lowerLimit, upperLimit);
							if (lowerLimit > 0)
								lowerLimit--;
							upperLimit++;
							info.setLowerPercent(lowerLimit);
							info.setUpperPercent(upperLimit);
							countLoop++;
							if (countLoop == 20)
								break;
						} while (stocksuggestion.size() < 9);
						info.setStockList(stocksuggestion);
					}
						break;
					case MODERADO: {
						lowerLimit = info.getLowerPercent();
						upperLimit = info.getUpperPercent();
						int countLoop = 0;
						do {
							stocksuggestion=null;
							stocksuggestion = stockDao
									.getStockOrderByStandardDeviation_30(
											lowerLimit, upperLimit);
							if (lowerLimit > 0)
								lowerLimit--;
							upperLimit++;
							info.setLowerPercent(lowerLimit);
							info.setUpperPercent(upperLimit);
							countLoop++;
							if (countLoop == 20)
								break;
						} while (stocksuggestion.size() == 0);
						info.setStockList(stocksuggestion);
					}
						break;
					case CONSERVADOR: {
						if (lowerLimit > 0)
							lowerLimit--;
						upperLimit = info.getUpperPercent();
						int countLoop = 0;
						do {
							stocksuggestion=null;
							stocksuggestion = stockDao
									.getStockOrderByStandardDeviation_30(
											lowerLimit, upperLimit);
							upperLimit++;
							info.setLowerPercent(lowerLimit);
							info.setUpperPercent(upperLimit);
							countLoop++;
							if (countLoop == 20)
								break;
						} while (stocksuggestion.size() == 0);
						info.setStockList(stocksuggestion);
					}
						break;
					default:
						break;
					}
					reply = msg.createReply();
					try {
						reply.setContentObject(info);
						myAgent.send(reply);

					} catch (IOException e) {
						
						e.printStackTrace();
					}					
			}
		};

		agent.addBehaviour(findBestStocks);

	}

	public void start(Object object) {
		// TODO Auto-generated method stub

	}

	public Behaviour getBehaviour() {
		// TODO Auto-generated method stub
		return null;
	}

}
