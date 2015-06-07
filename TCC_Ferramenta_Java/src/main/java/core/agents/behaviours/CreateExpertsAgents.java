package core.agents.behaviours;

import core.agents.ConversationsID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.MessageTemplate;
import suport.financial.wallet.Wallet;
import suport.util.database.mongoDB.dao.WalletDao;
import suport.util.database.mongoDB.pojo.OrdersCreate;

public class CreateExpertsAgents implements ProcedureBehaviour{
	
	/*
	 * Recebe dados do cliente através de mensagem;
		Cria uma Carteira no Banco de Dados;
		Procura um Agente Hunter Local e Solicita Ações;
		Cria Agentes experts e divide as ações entre os agentes;
	 */
	private SequentialBehaviour sequentialBehaviour;
	private OneShotBehaviour createWallet;
	private SimpleBehaviour requestSocks;
	private OneShotBehaviour createExpertsAgents;
	private OrdersCreate orderCreate;
	
	private WalletDao walletDao;
	
	private Agent agent;
	private MessageTemplate template;
	public CreateExpertsAgents(Agent agent,OrdersCreate orderCreate)
	{
		this.agent=agent;
		this.orderCreate=orderCreate;
		this.walletDao= new WalletDao();
		
		this.template=MessageTemplate.MatchConversationId(ConversationsID.STOCKS_HUNTER_SUGGESTIONS);
		this.sequentialBehaviour= new SequentialBehaviour(agent)
		{
			public int onEnd()
			{
				System.out.println("Comportamento sequencial Concluido");
			
				return 0;
			}
		};
		buildBehaviour();
		
		
	}
	private CreateExpertsAgents(){}

	 void buildBehaviour()
	{
		this.createWallet= new OneShotBehaviour(this.agent)
		{
			
			private static final long serialVersionUID = 1L;

			@Override
			public void action() 
			{
				System.out.println("Comportamento Criar Carteira!");
				walletDao.insertWallet(new Wallet(orderCreate.getUserIndetifier(), orderCreate.getUserValue(), 0, 0));
				
			}
		};
		this.requestSocks= new SimpleBehaviour(this.agent)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private boolean retorno=false;
			@Override
			public boolean done() {
				
				return retorno;
			}
			
			@Override
			public void action() {
				
				System.out.println("Comportamento Requesitar Ativos");
				
				retorno=true;
				
			}
		};
		
		this.createExpertsAgents=new OneShotBehaviour(this.agent)
		{
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				
				System.out.println("Comportamento criar agentes especialistas");
				
			}
		};
		
	}
	/**
	 * Start the sequential Behaviour
	 */
	public void start()
	{
			this.sequentialBehaviour.addSubBehaviour(createWallet);
			this.sequentialBehaviour.addSubBehaviour(requestSocks);
			this.sequentialBehaviour.addSubBehaviour(createExpertsAgents);
			
			this.agent.addBehaviour(sequentialBehaviour);
	}
	/**
	 * 
	 * @return return the sequential Behaviour without start it
	 */
	public Behaviour getBehaviour()
	{
		this.sequentialBehaviour.addSubBehaviour(createWallet);
		this.sequentialBehaviour.addSubBehaviour(requestSocks);
		this.sequentialBehaviour.addSubBehaviour(createExpertsAgents);
		
		return this.sequentialBehaviour;
	}
	

}
