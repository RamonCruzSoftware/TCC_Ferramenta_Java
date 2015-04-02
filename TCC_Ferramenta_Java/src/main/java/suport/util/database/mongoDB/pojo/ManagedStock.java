package suport.util.database.mongoDB.pojo;

import java.util.ArrayList;

import suport.financial.partternsCandleStick.CandleStick;

public class ManagedStock {
	
	  private String userIdentifier;
	  private String codeName;
	  private String sector;
	  private ArrayList<CandleStick> candleSticks;
	  private int qtdStocksBought;
	 
	  private CandleStick Buyed; 
	  private CandleStick selled;
	  private double profitPercent;
	  private double profitValue;
	  
	public String getCodeName()
	{
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public ArrayList<CandleStick> getCandleSticks() {
		return candleSticks;
	}
	public void setCandleSticks(ArrayList<CandleStick> candleSticks) {
		this.candleSticks = candleSticks;
	}
	public CandleStick getBuyed() {
		return Buyed;
	}
	public void setBuyed(CandleStick buyed) {
		Buyed = buyed;
	}
	public double getProfitPercent() {
		return profitPercent;
	}
	public void setProfitPercent(double profitPercent) {
		this.profitPercent = profitPercent;
	}
	public double getProfitValue() {
		return profitValue;
	}
	public void setProfitValue(double profitValue) {
		this.profitValue = profitValue;
	}
	public CandleStick getSelled() {
		return selled;
	}
	public void setSelled(CandleStick selled) {
		this.selled = selled;
	}
	public String getUserIdentifier() {
		return userIdentifier;
	}
	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}
	public int getQtdStocksBought() {
		return qtdStocksBought;
	}
	public void setQtdStocksBought(int qtdStocksBought) {
		this.qtdStocksBought = qtdStocksBought;
	}
	

	  
	 

}
