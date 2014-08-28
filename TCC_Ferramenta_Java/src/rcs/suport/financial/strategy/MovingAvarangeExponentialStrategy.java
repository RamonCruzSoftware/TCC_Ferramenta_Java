package rcs.suport.financial.strategy;

import java.util.ArrayList;
 
public class MovingAvarangeExponentialStrategy implements Strategy{

	
	private ArrayList<Double> mme3Values;
	private ArrayList<Double> mme21Values;
    rcs.suport.financial.indicators.MovingAvarange movingAvange3 ;
    rcs.suport.financial.indicators.MovingAvarange movingAvarange21 ;
	
	
	/**
	 * Put in actualMME1 the value of minor movingAvarange period and
	 * put in actualMME2 the value of major movingAvarang period.
	 * these values will be used for to start the calculus
	 * @param currentPrice
	 * @param actualMME1
	 * @param actualMME2
	 */
	public MovingAvarangeExponentialStrategy(double currentPrice ,double actualMME1,double actualMME2)
	{
		mme21Values=new ArrayList<Double>();
		mme3Values=new ArrayList<Double>();
		
		movingAvange3= new rcs.suport.financial.indicators.MovingAvarange(actualMME1,currentPrice,3);
		movingAvarange21=new rcs.suport.financial.indicators.MovingAvarange(actualMME2,currentPrice,21);
		
		mme21Values.add(movingAvarange21.exponencialAvarange());
		mme3Values.add(movingAvange3.exponencialAvarange());
		
		
	}
	
	public String makeOrder(String ticket) {
		
		String orderTicket=ticket;
		int indexPrev, indexNext;
		indexPrev=mme3Values.size()-1;
		indexNext=mme3Values.size()-2;
		
		
		if(mme21Values.get(indexPrev)>0 && mme3Values.get(indexPrev)>0 && mme3Values.size()>1)
		{
			
			if((mme3Values.get(indexNext)<mme21Values.get(indexNext))
					&&
					mme3Values.get(indexPrev)>mme21Values.get(indexPrev))
				orderTicket+=";Buy";
				

			if((mme3Values.get(indexNext)>mme21Values.get(indexNext))
					&&
					mme3Values.get(indexPrev)<mme21Values.get(indexPrev))
				orderTicket+=";Sell";
		}
		
		return orderTicket;
	}

	public void addValue(double value) {
		
		int lastIndex=mme3Values.size()-1;
		
		movingAvange3.setLastMMEandCurrentPrice(mme3Values.get(lastIndex), value);
		mme3Values.add(movingAvange3.exponencialAvarange());
		
		movingAvarange21.setLastMMEandCurrentPrice(mme21Values.get(lastIndex), value);
		mme21Values.add(movingAvarange21.exponencialAvarange());
		
	}
	

	public ArrayList<Double> getMme3Values()
	{
		return this.mme3Values;
	}
	public ArrayList<Double> getMme21Values()
	{
		return this.mme21Values;
	}


}
