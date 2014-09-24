package rcs.suport.financial.wallet;

import java.util.Date;
import java.util.Map;

public class Stock {

	private String codeName;
	private String companyShortName;
	private String sector;
	private Map<Date,Double> prices;
	private double currentPrice;
	public Stock(){}
	
	public String getCodeName() {
		return codeName;
	}
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	public String getCompanyShortName() {
		return companyShortName;
	}
	public void setCompanyShortName(String companyShortName) {
		this.companyShortName = companyShortName;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public Map<Date,Double> getPrices() {
		return prices;
	}
	public void setPrices(Map<Date,Double> prices) {
		this.prices = prices;
	}

	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	
}
