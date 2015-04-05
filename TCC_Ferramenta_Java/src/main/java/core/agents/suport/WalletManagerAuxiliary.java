package core.agents.suport;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import suport.financial.wallet.Stock;
import suport.statistical.Matrix;
import suport.statistical.Statistical;
import suport.util.database.mongoDB.dao.ManagedStockDao;
import suport.util.database.mongoDB.dao.ManagedWalletDao;
import suport.util.database.mongoDB.dao.StockDao;
import suport.util.database.mongoDB.pojo.ManagedStock;
import suport.util.database.mongoDB.pojo.ManagedWallet;

public class WalletManagerAuxiliary {

	private ManagedStockDao managedStockDao;
	private ManagedWallet managedWallet;
	private ManagedWalletDao managedWalletDao;
	StockDao stockDao;
	private double quota;
	private int userProfile;
	private int poisitiveCorrelationTolerance;
	private ArrayList<Stock> stockList;
	private ArrayList<Stock> approvedStockList;
	private ArrayList<Stock> refuseStockList;
	private double risk = 0.f;
	private StockChooser stkChooser;
	String userName;
	private String AgentLocalName;
	Map<String, Double> expertsQuota;
	Map<String, ArrayList<Stock>> infoExperts;

	private static final int CORAJOSO = 0;
	private static final int MODERADO = 1;
	private static final int CONSERVADOR = 2;

	public WalletManagerAuxiliary() {
		this.stockDao = new StockDao();
	}

	public WalletManagerAuxiliary(ArrayList<Stock> stockList, double userValue,
			int userProfile) {
		this.managedWallet = new ManagedWallet();
		this.stockDao = new StockDao();
		this.managedWalletDao = new ManagedWalletDao();
		this.stockList = stockList;
		this.expertsQuota = new HashMap<String, Double>();
		this.userProfile = userProfile;

		try {
			this.managedWallet.setWalletValue(userValue);
			this.managedWallet.setUserID(userName);
			this.managedWalletDao.insertManagedWalletInfo(this.managedWallet);

		} catch (Exception e) {// TODO LOG
			e.printStackTrace();
		}
		// Setting the correlaction criteria by user profile
		switch (this.getUserProfile()) {
		case CORAJOSO:
			this.setPoisitiveCorrelationTolerance(2);
			break;
		case MODERADO:
			this.setPoisitiveCorrelationTolerance(1);
			break;
		case CONSERVADOR:
			this.setPoisitiveCorrelationTolerance(0);
			break;

		default:
			this.setPoisitiveCorrelationTolerance(1);
			break;
		}
		this.stkChooser = new StockChooser(this.stockList, this.userProfile);
	}

	public void putInfoExperts(Map<String, ArrayList<Stock>> infoExperts) {
		this.infoExperts = infoExperts;
		DecimalFormat df = new DecimalFormat("0.00");
		String quotaValue = df
				.format((this.managedWallet.getWalletValue() / this.infoExperts
						.size()));
		// Money qtd for Expert
		this.setQuota(Double.parseDouble(quotaValue));
		// Criando map para controlar quantidade de dinheiro por agente
		for (Entry<String, ArrayList<Stock>> e : this.infoExperts.entrySet()) {
			this.expertsQuota.put(e.getKey(), this.getQuota());
		}
	}

	public void closeOrder(String expertName, double value) {
		this.setQuota(this.getQuota() + value / this.infoExperts.size());
		for (Entry<String, ArrayList<Stock>> e : this.infoExperts.entrySet()) {
			this.expertsQuota.put(e.getKey(), this.getQuota());
		}
	}

	public double approveOrderBuy(String expertName) {
		double quota = 0;
		try {
			quota = this.expertsQuota.get(expertName);
			this.expertsQuota.remove(expertName);
			this.expertsQuota.put(expertName, 0.0);
		} catch (Exception e) {// TODO LOG
			e.printStackTrace();
		}
		return quota;
	}

	public boolean refreshWalletManager() {
		ArrayList<ManagedStock> managedStockStored = this.managedStockDao
				.getManagedStock(userName);
		ArrayList<Stock> stockList = new ArrayList<Stock>();
		ManagedWallet refresh = new ManagedWallet();

		if (managedStockStored != null && managedStockStored.size() > 1) {
			for (ManagedStock ms : managedStockStored) {
				stockList.add(new Stock(ms.getCodeName(), ms.getSector()));
			}
			refresh.setStocksList(stockList);
			refresh.setUserID(userName);
			return managedWalletDao.updateManagedWallet(refresh);
		} else
			return false;
	}

	public ArrayList<ArrayList<Stock>> analyzeStocksSuggestionsList() {
		return this.stkChooser.analyzeStockList();
	}

	public boolean analyzeStock(Stock stock) {
		return this.stkChooser.analyzeStock(stock);
	}

	public double calculeRisk(ArrayList<Stock> stockList) {
		int nCol = stockList.size();
		int nLin = stockList.size();
		double result = 0.f;
		try {
			Matrix variance_matrix = new Matrix(nLin, nLin);
			Statistical statistical = new Statistical();
			// Montando matriz de variancias
			for (int i = 1; i <= nLin; i++) {
				for (int j = 1; j <= nCol; j++) {
					if (i == j)
						variance_matrix.setContent(i - 1, j - 1, 0);
					else {
						if (i > j) {
							variance_matrix
									.setContent(
											i - 1,
											j - 1,
											statistical
													.calculeVariance_15BetweenTwoStocks(
															stockList
																	.get(i - 1)
																	.getCandleSticks(),
															stockList
																	.get(j - 1)
																	.getCandleSticks(),
															1 / stockList
																	.size(),
															1 / stockList
																	.size())); // calcula
							variance_matrix.setContent(j - 1, i - 1,
									variance_matrix.getContent(i, j));
						} else
							continue;
					}
				}
			}
			// Montando matriz coluna de percentual de participacao
			Matrix percentual_matrix = new Matrix(nLin, 1);
			for (int i = 1; i <= nLin; i++)
				percentual_matrix.setContent(i - 1, 1, 1 / stockList.size());
			// Montando matriz linha de retorno medio
			Matrix avarangeReturn_matrix = new Matrix(nLin, 1);
			for (int i = 1; i <= nLin; i++)
				avarangeReturn_matrix.setContent(i - 1, 1, stockList.get(i - 1)
						.getAvarangeReturn_15());
			result = Matrix.product(
					Matrix.product(Matrix.transposed(percentual_matrix),
							variance_matrix), avarangeReturn_matrix)
					.getContent(0, 0);
		} catch (Exception e) {// TODO LOG
			e.printStackTrace();
		}
		return result;
	}

	public String getAgentLocalName() {
		return AgentLocalName;
	}

	public void setAgentLocalName(String agentLocalName) {
		AgentLocalName = agentLocalName;
	}

	public double getQuota() {
		return quota;
	}

	public void setQuota(double quota) {
		this.quota = quota;
	}

	public int getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(int userProfile) {
		this.userProfile = userProfile;
	}

	public int getPoisitiveCorrelationTolerance() {
		return poisitiveCorrelationTolerance;
	}

	public void setPoisitiveCorrelationTolerance(
			int poisitiveCorrelationTolerance) {
		this.poisitiveCorrelationTolerance = poisitiveCorrelationTolerance;
	}

	public ArrayList<Stock> getStockList() {
		return stockList;
	}

	public void setStockList(ArrayList<Stock> stockList) {
		this.stockList = stockList;
	}

	public ArrayList<Stock> getApprovedStockList() {
		return approvedStockList;
	}

	public void setApprovedStockList(ArrayList<Stock> approvedStockList) {
		this.approvedStockList = approvedStockList;
	}

	public ArrayList<Stock> getRefuseStockList() {
		return refuseStockList;
	}

	public void setRefuseStockList(ArrayList<Stock> refuseStockList) {
		this.refuseStockList = refuseStockList;
	}

	public double getRisk() {
		return risk;
	}

	public void setRisk(double risk) {
		this.risk = risk;
	}

}