package suport.financial.wallet;

import jade.util.leap.Serializable;
import java.util.ArrayList;

public class Wallet implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userID;
	private double walletValue;
	private double walletRisck;
	private double wallterPercent;
	private ArrayList<Stock> stocksList;

	public Wallet() {
	}

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

	public double getWallterPercent() {
		return wallterPercent;
	}

	public void setWallterPercent(double wallterPercent) {
		this.wallterPercent = wallterPercent;
	}

	public ArrayList<Stock> getStocksList() {
		return stocksList;
	}

	public void setStocksList(ArrayList<Stock> stocksList) {
		this.stocksList = stocksList;
	}
}
