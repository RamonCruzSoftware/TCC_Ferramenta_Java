package suport.util.database.mongoDB.pojo;
import jade.util.leap.Serializable;

public class UserInfo  implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userIndetifier;
	 private double walletValue;
	 private double walletPercent;
	 
	 
	 
	 public UserInfo(){}
	 
	 
	public String getUserIndetifier() {
		return userIndetifier;
	}
	public void setUserIndetifier(String userIndetifier) {
		this.userIndetifier = userIndetifier;
	}


	public double getWalletValue() {
		return walletValue;
	}


	public void setWalletValue(double walletValue) {
		this.walletValue = walletValue;
	}


	public double getWalletPercent() {
		return walletPercent;
	}


	public void setWalletPercent(double walletPercent) {
		this.walletPercent = walletPercent;
	}
	
	 
	

}
