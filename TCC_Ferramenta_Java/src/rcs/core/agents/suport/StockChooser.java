package rcs.core.agents.suport;

import java.util.ArrayList;

import rcs.suport.financial.wallet.Stock;
import rcs.suport.statistical.Statistical;
import rcs.suport.util.database.mongoDB.dao.StockDao;

public class StockChooser 
{
	private ArrayList<Stock> stockList;
	private ArrayList<Stock> approvedStockList;
	private ArrayList<Stock> refuseStockList;
	
	private ArrayList<Stock> stockListTemp_a;
	private ArrayList<Stock> stockListTemp_b;
	
	private int userProfile;
	private StockDao stockDao;
	
	private int poisitiveCorrelationTolerance;
	
	
	private StockChooser(){}
	
public StockChooser(ArrayList<Stock>stockList,int userProfile)
{
		this.setStockList(stockList);
		this.setUserProfile(userProfile);
		
		this.setStockDao(new StockDao());
		this.setApprovedStockList(new ArrayList<Stock>());
		this.setRefuseStockList(new ArrayList<Stock>());
		
		
		if(this.getStockList().size()>0)
		{
			this.setStockListTemp_a(new ArrayList<Stock>());
			this.setStockListTemp_b(new ArrayList<Stock>());
			
			
			//Getting the candleSticks of each stock
			for(Stock s:this.getStockList())
			{
				if(s.getCandleSticks()==null)s.setCandleSticks(this.getStockDao().getStockPrices_last30(s.getCodeName()));
		
				this.getStockListTemp_a().add(s);
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
			
		}
		
}
	
public boolean analyzeStock(Stock stockToAnalyze)
{
		boolean result=false;
		double correl=0;
		Statistical statistical= new Statistical();
		
		if(this.getStockList().size()==0)
		{
			for(int i=0;i<this.getStockList().size();i++)
			{
				correl=statistical.calculeCorrelationCoefficient_30(stockToAnalyze.getCandleSticks(), this.getApprovedStockList().get(i).getCandleSticks());
				if(correl<0)result=true;
				else result=false;
			}
		}
		else
		{
			result=true;
		}
		
		return false;
}

/**
 * This method analyze the correlation between stock in an List.
 * Case to happen some correlation above the limit for user profile,
 * the Stock whose have negative correlaction is not approved.
 * @return
 * 
 * This method return a list that contain two elements: 0-> Approved List; 1-> Refuse List.
 */

public ArrayList<ArrayList<Stock>> analyzeStockList()
{
	ArrayList<ArrayList<Stock>>result=new ArrayList<ArrayList<Stock>>();
	int limitCorrelactionCount=0;
	Statistical statistical= new Statistical();

	double correl_temp=0;
	boolean positiveCorrelation=false;
	
	if(this.getStockListTemp_a().size()>0)
	{
		
		this.getApprovedStockList().add(this.getStockListTemp_a().get(0));
		this.getStockListTemp_a().remove(0);
		
		for(int i=0;i<this.getStockListTemp_a().size();i++)
		{
			
			for(int j=0;j<this.getApprovedStockList().size();j++)
			{
				correl_temp= statistical.calculeCorrelationCoefficient_30(this.getStockListTemp_a().get(i).getCandleSticks(),this.getApprovedStockList().get(j).getCandleSticks());
				
				if(correl_temp>0)positiveCorrelation=true;
					
			
			}
			if(positiveCorrelation)
			{
				if(limitCorrelactionCount<this.getPoisitiveCorrelationTolerance())
				{
					this.getApprovedStockList().add(this.getStockListTemp_a().get(i));
					positiveCorrelation=false;
				}else
				{
					this.getRefuseStockList().add(this.getStockListTemp_a().get(i));
					positiveCorrelation=false;
				}
				
			}else
			{
				this.getApprovedStockList().add(this.getStockListTemp_a().get(i));
			}
	
		}
		
		result.add(approvedStockList);
		result.add(refuseStockList);
		this.stockListTemp_a.clear();
	}else
		{
			result.add(approvedStockList);
			result.add(refuseStockList);
		}
	
	return result;
	
	
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

public ArrayList<Stock> getStockListTemp_a() {
	return stockListTemp_a;
}

public void setStockListTemp_a(ArrayList<Stock> stockListTemp_a) {
	this.stockListTemp_a = stockListTemp_a;
}

public ArrayList<Stock> getStockListTemp_b() {
	return stockListTemp_b;
}

public void setStockListTemp_b(ArrayList<Stock> stockListTemp_b) {
	this.stockListTemp_b = stockListTemp_b;
}

public int getUserProfile() {
	return userProfile;
}

public void setUserProfile(int userProfile) {
	this.userProfile = userProfile;
}

public StockDao getStockDao() {
	return stockDao;
}

public void setStockDao(StockDao stockDao) {
	this.stockDao = stockDao;
}

public int getPoisitiveCorrelationTolerance() {
	return poisitiveCorrelationTolerance;
}

public void setPoisitiveCorrelationTolerance(int poisitiveCorrelationTolerance) {
	this.poisitiveCorrelationTolerance = poisitiveCorrelationTolerance;
}
	
}
