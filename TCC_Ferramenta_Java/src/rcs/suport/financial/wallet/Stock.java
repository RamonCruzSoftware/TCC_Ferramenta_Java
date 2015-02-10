package rcs.suport.financial.wallet;

import jade.util.leap.*;
import java.lang.Comparable;
import java.util.ArrayList;
import java.util.Comparator;

import rcs.suport.financial.partternsCandleStick.CandleStick;


public class Stock implements Serializable ,Comparable<Stock>{

	
	 private static final long serialVersionUID = 1L;
	 private String codeName;
	 private String sector;
	 private ArrayList<CandleStick> candleSticks;
	 
	 private double avarangeReturn_30;
	 private double standardDeviation_30;
	 private double variance_30;
	 private double varianceCoefficient_30;
	
	 private double avarangeReturn_15;
	 private double standardDeviation_15;
	 private double variance_15;
	 private double varianceCoefficient_15;
	 
	 //For user know
	 private int suggestion;
	 private int qtd;
	 private int currentPrice;
	 
	 
	 	 
	 public Stock(){}
	 public Stock(String codeName,String sector)
	 {
		 this.codeName=codeName;
		 this.sector=sector;
	 }

	 
	 public boolean addCurrentCandleStick(CandleStick candleStick)
	 {
		 if(this.candleSticks.get(this.candleSticks.size()-1).getDate().getTime()
				 ==candleStick.getDate().getTime())
		 {
			 return false;
		 }else 
		 {
			 this.candleSticks.add(candleStick);
		 }
		 return false;
	 }
	 
	 
	 @Override
		public int compareTo(Stock o) {
			if(this.variance_15<o.variance_15)
				return -1;
			if(this.variance_15>o.variance_15)
				return 1;
			
			return 0;
		}
	 
	 
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

	public ArrayList<CandleStick> getCandleSticks()
	{
		return candleSticks;
	}

	public void setCandleSticks(ArrayList<CandleStick> candleSticks) 
	{	
		
		this.candleSticks = candleSticks;
	}

	public CandleStick getCurrentCandleStick() 
	{
		return this.candleSticks.get(this.candleSticks.size()-1);
	}

	
	public double getStandardDeviation_30() {
		return standardDeviation_30;
	}
	public void setStandardDeviation_30(double standardDeviation_30) {
		this.standardDeviation_30 = standardDeviation_30;
	}
	public double getVariance_30() {
		return variance_30;
	}
	public void setVariance_30(double variance_30) {
		this.variance_30 = variance_30;
	}
	public double getVarianceCoefficient_30() {
		return varianceCoefficient_30;
	}
	public void setVarianceCoefficient_30(double varianceCoefficient_30) {
		this.varianceCoefficient_30 = varianceCoefficient_30;
	}
	public double getStandardDeviation_15() {
		return standardDeviation_15;
	}
	public void setStandardDeviation_15(double standardDeviation_15) {
		this.standardDeviation_15 = standardDeviation_15;
	}
	public double getVariance_15() {
		return variance_15;
	}
	public void setVariance_15(double variance_15) {
		this.variance_15 = variance_15;
	}
	public double getVarianceCoefficient_15() {
		return varianceCoefficient_15;
	}
	public void setVarianceCoefficient_15(double varianceCoefficient_15) {
		this.varianceCoefficient_15 = varianceCoefficient_15;
	}
	public double getAvarangeReturn_15() {
		return avarangeReturn_15;
	}
	public void setAvarangeReturn_15(double avarangeReturn_15) {
		this.avarangeReturn_15 = avarangeReturn_15;
	}
	public double getAvarangeReturn_30() {
		return avarangeReturn_30;
	}
	public void setAvarangeReturn_30(double avarangeReturn_30) {
		this.avarangeReturn_30 = avarangeReturn_30;
	}
	public int getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(int suggestion) {
		this.suggestion = suggestion;
	}
	public int getQtd() {
		return qtd;
	}
	public void setQtd(int qtd) {
		this.qtd = qtd;
	}
	public int getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(int currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	
	
	

	
	
}
