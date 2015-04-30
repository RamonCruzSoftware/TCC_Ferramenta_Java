package suport.util.database.mongoDB.pojo;

import jade.util.leap.Serializable;

public class OrdersCreate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userIndetifier;
	private int userPerfil;
	private double userValue;

	public String getUserIndetifier() {
		return userIndetifier;
	}

	public void setUserIndetifier(String userIndetifier) {
		this.userIndetifier = userIndetifier;
	}

	public int getUserPerfil() {
		return userPerfil;
	}

	public void setUserPerfil(int userPerfil) {
		this.userPerfil = userPerfil;
	}

	public double getUserValue() {
		return userValue;
	}

	public void setUserValue(double userValue) {
		this.userValue = userValue;
	}

}
