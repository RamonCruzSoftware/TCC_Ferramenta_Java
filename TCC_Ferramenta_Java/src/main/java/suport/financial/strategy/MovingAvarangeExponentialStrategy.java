package suport.financial.strategy;

import java.util.ArrayList;
import suport.financial.partternsCandleStick.CandleStick;

public class MovingAvarangeExponentialStrategy implements Strategy {

	private ArrayList<Double> mme13Values;
	private ArrayList<Double> mme21Values;
	private double purchasedValue;
	private double value;
	private double stopLoss;
	suport.financial.indicators.MovingAvarange movingAvange13;
	suport.financial.indicators.MovingAvarange movingAvarange21;

	/**
	 * Put in actualMME1 the value of minor movingAvarange period and put in
	 * actualMME2 the value of major movingAvarang period. these values will be
	 * used for to start the calculus
	 * 
	 * @param currentPrice
	 * @param actualMME1
	 * @param actualMME2
	 */
	public MovingAvarangeExponentialStrategy(double currentPrice,double actualMME1, double actualMME2) {
		
		mme21Values = new ArrayList<Double>();
		mme13Values = new ArrayList<Double>();
		movingAvange13 = new suport.financial.indicators.MovingAvarange(actualMME1, currentPrice, 13);
		movingAvarange21 = new suport.financial.indicators.MovingAvarange(actualMME2, currentPrice, 21);
		mme21Values.add(movingAvarange21.exponencialAvarange());
		mme13Values.add(movingAvange13.exponencialAvarange());
		
		stopLoss=-0.05;

	}

	public String makeOrder() 
	{
		String order = null;
		int index_1, index_0;
		try {
			index_1 = mme13Values.size() - 1;
			index_0 = mme13Values.size() - 2;

			if 	(
					mme21Values.get(index_0) > 0 
					&& mme13Values.get(index_0) > 0
					&& mme13Values.size() > 1
					)
			{
				if (
						(mme13Values.get(index_0) < mme21Values.get(index_0))
						&& mme13Values.get(index_1) > mme21Values.get(index_1)
						)
					order = "Sell";
				if (
						(mme13Values.get(index_0) > mme21Values.get(index_0))
						&& mme13Values.get(index_1) < mme21Values.get(index_1))
					order = "Buy";
				if (
						((mme13Values.get(index_0) < mme21Values.get(index_0))
								&& (mme13Values.get(index_1)) < mme21Values.get(index_1)
						)
						|| 
						((mme13Values.get(index_0) > mme21Values.get(index_0)) 
								&& (mme13Values.get(index_1) > mme21Values.get(index_1)))
								) 
				{
					order = "nothing";
				}
				
			}
//			if(order=="Buy")purchasedValue=value;
//			if(order=="nothing" && ((value-purchasedValue)/purchasedValue)>=stopLoss) order="Sell";
//			if(order=="Sell" ) purchasedValue=0.0;
		} catch (Exception e) {// TODO LOG
			e.printStackTrace();
			return "nothing";
		}
		
		return order;
	}

	public void addValue(double value) 
	{
		int lastIndex = mme13Values.size() - 1;
		movingAvange13.setLastMMEandCurrentPrice(mme13Values.get(lastIndex),value);
		mme13Values.add(movingAvange13.exponencialAvarange());
		
		movingAvarange21.setLastMMEandCurrentPrice(mme21Values.get(lastIndex),value);
		mme21Values.add(movingAvarange21.exponencialAvarange());
		this.value=value;
	}

	public ArrayList<Double> getMme3Values() {
		return this.mme13Values;
	}

	public ArrayList<Double> getMme21Values() {
		return this.mme21Values;
	}

	public void addCandleStick(CandleStick candleStick) {

	}
}
