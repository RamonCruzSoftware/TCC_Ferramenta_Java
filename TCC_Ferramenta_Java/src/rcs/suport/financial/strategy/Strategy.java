package rcs.suport.financial.strategy;


public interface Strategy {

	public String makeOrder(String ticket);
	public void addValue(double value);
	
}
