package rcs.core.agents.suport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import rcs.suport.financial.wallet.Stock;
import rcs.suport.util.database.mongoDB.dao.ManagedStockDao;
import rcs.suport.util.database.mongoDB.dao.ManagedWalletDao;
import rcs.suport.util.database.mongoDB.dao.StockDao;
import rcs.suport.util.database.mongoDB.pojo.ManagedStock;
import rcs.suport.util.database.mongoDB.pojo.ManagedWallet;

public class WalletManagerAuxiliary {

	 ManagedStockDao managedStockDao;
	 	
	 ManagedWallet managedWallet;
	 ManagedWalletDao managedWalletDao;
	 StockDao stockDao;
	 private double quota;
	 private int userProfile;
	 private int poisitiveCorrelationTolerance;
	 private ArrayList<Stock> stockList;
	 private ArrayList<Stock> approvedStockList;
     private ArrayList<Stock> refuseStockList;
	 
	 
	 private StockChooser stkChooser;
	 
	 String userName;
	 private String AgentLocalName;
	 Map<String,Double> expertsQuota;
	 Map<String,ArrayList<Stock>> infoExperts;
	 
	 public WalletManagerAuxiliary()
	 {
		 this.stockDao= new StockDao();
	 }
	 
	 public void putInfoExperts(Map<String,ArrayList<Stock>> infoExperts)
	 {
		 this.infoExperts=infoExperts;
		 //Money qtd for Expert
		 this.setQuota(this.managedWallet.getWalletValue()/this.infoExperts.size());
		 
		 //Criando map para controlar quantidade de dinheiro por agente 
		 for(Entry<String, ArrayList<Stock>>e:this.infoExperts.entrySet())
		 {
			 this.expertsQuota.put(e.getKey(), this.getQuota());
			 
		 }
		 
	 }
	 public WalletManagerAuxiliary(ArrayList<Stock>stockList,double userValue,int userProfile)
	 {
		 this.managedWallet= new ManagedWallet();
		 this.stockDao= new StockDao();
		 this.managedWalletDao= new ManagedWalletDao();
		 this.stockList=stockList;
		 this.expertsQuota=new HashMap<String, Double>();
		 this.userProfile=userProfile;
		 
		 try
		 {
			 this.managedWallet.setWalletValue(userValue);
			
			 this.managedWallet.setUserID(userName);
			 
			 this.managedWalletDao.insertManagedWalletInfo(this.managedWallet);
			 
		 }catch(Exception e)
		 {
			e.printStackTrace(); 
		 } 
		 
		
		 
		//Setting the correlaction criteria by user profile 
			switch (this.getUserProfile())
			{
			case 0://corajoso
				
				this.setPoisitiveCorrelationTolerance(2);
				
				break;
			case 1://Moderado
				this.setPoisitiveCorrelationTolerance(1);
				break;
			case 2://Conservador
				this.setPoisitiveCorrelationTolerance(0);
				break;

			default:
				this.setPoisitiveCorrelationTolerance(1);
				break;
			}
 
			//Erro Aqui TODO
			this.stkChooser=new StockChooser(this.stockList, this.userProfile);
		 
	 }
	 
	 
	 public void closeOrder(String expertName,double value)
	 {
		 this.setQuota(this.getQuota() + value/this.infoExperts.size());
		 
		 for(Entry<String, ArrayList<Stock>>e:this.infoExperts.entrySet())
		 {
			 this.expertsQuota.put(e.getKey(), this.getQuota());
		 }
	 }
	 public double approveOrderBuy(String expertName)
	 {
		 double quota=0;
		 try
		 {
			 quota=this.expertsQuota.get(expertName);
			 this.expertsQuota.remove(expertName);
			 this.expertsQuota.put(expertName, 0.0);
			 			 
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 
		 return quota;
	 }
	
	 public boolean refreshWalletManager ()
	 {
		 ArrayList<ManagedStock> managedStockStored = this.managedStockDao.getManagedStock(userName);
		 ArrayList<Stock> stockList = new ArrayList<Stock>();
		 ManagedWallet  refresh= new ManagedWallet();
		 
		 if(managedStockStored!=null && managedStockStored.size()>1)
		 {
			 
			 for(ManagedStock ms: managedStockStored)
			 {
				 stockList.add(new Stock(ms.getCodeName(), ms.getSector()));
			 }
			 refresh.setStocksList(stockList);
			 refresh.setUserID(userName);
			 
			 return managedWalletDao.updateManagedWallet(refresh);
			  
			 
		 }else
			 
		 return false;
	 }
	 
	 public  ArrayList<ArrayList<Stock>>  analyzeStocksSuggestionsList()
	 {
		 return this.stkChooser.analyzeStockList(); 
	 }
	 
	 public boolean analyzeStock(Stock stock)
	 {
		 return this.stkChooser.analyzeStock(stock);
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

	}


