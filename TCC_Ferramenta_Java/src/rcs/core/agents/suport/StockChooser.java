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
	private static final int CORAJOSO=0;
	private static final int MODERADO=1;
	private static final int CONSERVADOR=2;
	
	public StockChooser(ArrayList<Stock>stockList,int userProfile)
	{
		this.setStockList(stockList);
		this.setUserProfile(userProfile);	
		this.setStockDao(new StockDao());
		this.setApprovedStockList(new ArrayList<Stock>());
		this.setRefuseStockList(new ArrayList<Stock>());
		
		try
		{
			if(this.getStockList().size()>0)
			{
				this.setStockListTemp_a(new ArrayList<Stock>());
				this.setStockListTemp_b(new ArrayList<Stock>());		
				int stop=0;
				//Getting the candleSticks of each stock
				for(Stock s:this.getStockList())
				{//TODO LOG
					//TODO Retirar esse gato de quebra de laco e resolver o problema
					stop++;
					if(stop==100)break;
					if(s.getCandleSticks()==null)s.setCandleSticks(this.getStockDao().getStockPrices_last30(s.getCodeName()));
					this.getStockListTemp_a().add(s);
				}
				//Setting the correlaction criteria by user profile 
				switch (this.getUserProfile())
				{
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
			}
		}catch(Exception e)
		{//TODO LOG
			e.printStackTrace();
		}
}
public boolean analyzeStock(Stock stockToAnalyze)
{	
		double correl=0;
		Statistical statistical= new Statistical();
		if(this.getApprovedStockList().size()>0)
		{
			for(int i=0;i<this.getApprovedStockList().size();i++)
			{
				correl=statistical.calculeCorrelationCoefficient_30(stockToAnalyze.getCandleSticks(), this.getApprovedStockList().get(i).getCandleSticks());
				if(correl<0){}
				else {}
			}
		}
		else
		{
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
	Statistical statistical= new Statistical();
	double correl_temp=0;
	if(this.getStockListTemp_a().size()>0)
	{	
		this.getApprovedStockList().add(this.getStockListTemp_a().get(0));
		this.getStockListTemp_a().remove(0);			
		 
		Stock stockTemp=getApprovedStockList().get(0);
		//TODO LOG
		System.out.println("Stock inicial aprovado "+stockTemp.getCodeName());
		System.out.println("Lista para avaliacao tem agora "+getStockListTemp_a().size()+" acoes");
		
		for(int i=0;i<getStockListTemp_a().size();i++)
		{//TODO LOG
			correl_temp=0;
			System.out.println("===Iniciar analise==");
			System.out.println("Analisando  ["+this.getStockListTemp_a().get(i).getCodeName()+"]");
			System.out.println(" Com "+this.getStockListTemp_a().get(i).getCandleSticks().size()+" candles");
			System.out.println("\n");	
			System.out.println(" Stock 1: "+stockTemp.getCodeName()+" com "+stockTemp.getCandleSticks().size()+" candles");
			System.out.println(" Stock 2: "+this.getStockListTemp_a().get(i).getCandleSticks().size()+" candles");
			
			correl_temp= statistical.calculeCorrelationCoefficient_30(stockTemp.getCandleSticks(),this.getStockListTemp_a().get(i).getCandleSticks());
			System.out.println("Correl "+correl_temp);
			if(correl_temp>0)
			{//TODO LOG
				System.out.println("::: "+getStockListTemp_a().get(i)+" Refused");
				getRefuseStockList().add(getStockListTemp_a().get(i));
			}else
			if(correl_temp<0)
			{//TODO LOG
				System.out.println("::: "+getStockListTemp_a().get(i)+" Approved");
				getApprovedStockList().add(getStockListTemp_a().get(i));
			}
		}
		result.add(approvedStockList);
		result.add(refuseStockList);
		
	}else
		{
			result.add(approvedStockList);
			result.add(refuseStockList);
		}
	switch (this.getUserProfile())
	{
	case CORAJOSO:
	{
		if(result.get(0).size()<8 && this.stockListTemp_a.size()>=8)
		{
			this.approvedStockList.clear();
			this.analyzeStockList();
		}
	}break;
	case MODERADO:
	{
		if(result.get(0).size()<13 && this.stockListTemp_a.size()>=13)
		{
			this.approvedStockList.clear();
			this.analyzeStockList();
		}
	}break;
	case CONSERVADOR:
	{
		if(result.get(0).size()<30 && this.stockListTemp_a.size()>=30)
		{
			System.out.println("\t\t\t Roda De novo ");
			this.approvedStockList.clear();
			this.analyzeStockList();
		}
	}break;
	default:
	{
		if(result.get(0).size()<10 && this.stockListTemp_a.size()>=10)
		{
			this.approvedStockList.clear();
			this.analyzeStockList();
		}
	}break;
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
