package rcs.suport.util.database.mongoDB.pojo;

import java.util.ArrayList;

import rcs.suport.financial.partternsCandleStick.CandleStick;

public class ManagedStock {
	
	  private String codeName;
	  private String sector;
	  private ArrayList<CandleStick> candleSticks;
	 
	  private CandleStick Buyed;  
	  private double profitPercent;
	  private double profitValue;
	  
	public String getCodeName() {
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

	  
	 

}
