package rcs.suport.financial.wallet;

import java.util.ArrayList;

public class Wallet {
	private double valueWallter;
	private ArrayList<Stock>stocks;
	
	public Wallet(){}
	
	public double getValueWallter() {
		
		if(stocks.size()>0)
		{
			for (Stock stock : stocks)
			{
				valueWallter+=stock.getCurrentPrice();
			}
		}
		
		return valueWallter;
	}

	public ArrayList<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(ArrayList<Stock> stocks) {
		this.stocks = stocks;
	}
	
	
	
	

}
