package suport.util.database.mongoDB.pojo;

import suport.financial.partternsCandleStick.CandleStick;

public class InfoStrategies {

	private String userIdentifier;
	private String stockCodeName;
	private String strategyName;
	private String periodicity;
	private CandleStick buyed;
	private CandleStick selled;
	private double profit;

	public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

	public String getPeriodicity() {
		return periodicity;
	}

	public void setPeriodicity(String periodicity) {
		this.periodicity = periodicity;
	}

	public CandleStick getBuyed() {
		return buyed;
	}

	public void setBuyed(CandleStick buyed) {
		this.buyed = buyed;
	}

	public CandleStick getSelled() {
		return selled;
	}

	public void setSelled(CandleStick selled) {
		this.selled = selled;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public String getStockCodeName() {
		return stockCodeName;
	}

	public void setStockCodeName(String stockCodeName) {
		this.stockCodeName = stockCodeName;
	}

	public String getUserIdentifier() {
		return userIdentifier;
	}

	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}

}
