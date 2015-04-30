package suport.util;

import jade.util.leap.Serializable;
import java.util.ArrayList;
import suport.financial.wallet.Stock;

public class InfoConversations implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userName;
	private int userProfile;
	private ArrayList<Stock> stockList;
	private int lowerPercent; // This variable can to be used when happen some
								// stock rejection
	private int upperPercent; // This variable can to be used when happen some
								// stock rejection

	// Para uso dos experts e manages
	public InfoConversations() {
	}

	public InfoConversations(String userName, int userProfile) {
		this.setUserName(userName);
		this.setUserProfile(userProfile);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(int userProfile) {
		this.userProfile = userProfile;
	}

	public ArrayList<Stock> getStockList() {
		return stockList;
	}

	public void setStockList(ArrayList<Stock> stockList) {
		this.stockList = stockList;
	}

	public int getLowerPercent() {
		return lowerPercent;
	}

	public void setLowerPercent(int lowerPercent) {
		this.lowerPercent = lowerPercent;
	}

	public int getUpperPercent() {
		return upperPercent;
	}

	public void setUpperPercent(int upperPercent) {
		this.upperPercent = upperPercent;
	}
}
