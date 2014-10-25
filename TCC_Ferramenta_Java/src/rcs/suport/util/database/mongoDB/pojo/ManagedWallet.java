package rcs.suport.util.database.mongoDB.pojo;

import java.util.ArrayList;

import rcs.suport.financial.wallet.Stock;

public class ManagedWallet {
	
	 private String userID;
	 private double walletValue;
	 private double walletRisck;
	 private double walletProfitPercent;
	 private double walletProfitValue;
	
	 private ArrayList<Stock> stocksList;


	public String getUserID() {
		return userID;
	}


	public void setUserID(String userID) {
		this.userID = userID;
	}


	public double getWalletValue() {
		return walletValue;
	}


	public void setWalletValue(double walletValue) {
		this.walletValue = walletValue;
	}


	public double getWalletRisck() {
		return walletRisck;
	}


	public void setWalletRisck(double walletRisck) {
		this.walletRisck = walletRisck;
	}


	public double getWalletProfitPercent() {
		return walletProfitPercent;
	}


	public void setWalletProfitPercent(double walletProfitPercent) {
		this.walletProfitPercent = walletProfitPercent;
	}


	public double getWalletProfitValue() {
		return walletProfitValue;
	}


	public void setWalletProfitValue(double walletProfitValue) {
		this.walletProfitValue = walletProfitValue;
	}


	public ArrayList<Stock> getStocksList() {
		return stocksList;
	}


	public void setStocksList(ArrayList<Stock> stocksList) {
		this.stocksList = stocksList;
	}
	 
	 

}
