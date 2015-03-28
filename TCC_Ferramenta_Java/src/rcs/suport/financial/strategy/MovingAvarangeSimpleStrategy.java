package rcs.suport.financial.strategy;

import java.util.ArrayList;

import rcs.suport.financial.indicators.MovingAvarange;
import rcs.suport.financial.partternsCandleStick.CandleStick;

public class MovingAvarangeSimpleStrategy implements Strategy 
{
	 private MovingAvarange movingAvangeA;
	 private MovingAvarange movingAvangeB;
	 private ArrayList<Double>mmsAValues;
	 private ArrayList<Double>mmsBValues;
	
	public MovingAvarangeSimpleStrategy(int periodA,int periodB)
	{
		this.setMovingAvangeA(new MovingAvarange( periodA));
		this.setMovingAvangeB(new MovingAvarange( periodB));
		this.setMmsAValues(new ArrayList<Double>());
		this.setMmsBValues(new ArrayList<Double>());
		
	}
	@Override
	public String makeOrder() {
		String order=null;
		int index_1, index_0;	
		try
		{
			if(mmsAValues.size()>=5 && mmsBValues.size()>=5)
			{
				index_1=mmsAValues.size()-1;
				index_0=mmsAValues.size()-2;
				
				if(mmsBValues.get(index_0)>0 && mmsAValues.get(index_0)>0 && mmsAValues.size()>1)
				{
					if((mmsAValues.get(index_0)<mmsBValues.get(index_0))
							&&
							mmsAValues.get(index_1)>mmsBValues.get(index_1))
						order="Buy";
					if((mmsAValues.get(index_0)>mmsBValues.get(index_0))
							&&
							mmsAValues.get(index_1)<mmsBValues.get(index_1))
						order="Sell";
					if(
							((mmsAValues.get(index_0)<mmsBValues.get(index_0))
							&&
							(mmsAValues.get(index_1))<mmsBValues.get(index_1))
							||
							((mmsAValues.get(index_0)>mmsBValues.get(index_0))
								&&
							 (mmsAValues.get(index_1)>mmsBValues.get(index_1)))
					  )
					{
						order="nothing";
					}
				}
			}else order = "nothing";
		}catch (Exception e)
		{//TODO LOG
			e.printStackTrace();
			return "nothing";
		}
		return order;
	}
	@Override
	public void addValue(double value) 
	{
		this.movingAvangeA.addValue(value);
		this.movingAvangeB.addValue(value);	
		this.mmsAValues.add(this.movingAvangeA.simpleAvarange());
		this.mmsBValues.add(this.movingAvangeB.simpleAvarange());
	}

	public MovingAvarange getMovingAvangeA() {
		return movingAvangeA;
	}
	public void setMovingAvangeA(MovingAvarange movingAvangeA) {
		this.movingAvangeA = movingAvangeA;
	}
	public MovingAvarange getMovingAvangeB() {
		return movingAvangeB;
	}
	public void setMovingAvangeB(MovingAvarange movingAvangeB) {
		this.movingAvangeB = movingAvangeB;
	}
	public ArrayList<Double> getMmsAValues() {
		return mmsAValues;
	}
	public void setMmsAValues(ArrayList<Double> mmsAValues) {
		this.mmsAValues = mmsAValues;
	}
	public ArrayList<Double> getMmsBValues() {
		return mmsBValues;
	}
	public void setMmsBValues(ArrayList<Double> mmsBValues) {
		this.mmsBValues = mmsBValues;
	}
	@Override
	public void addCandleStick(CandleStick candleStick) {
	
	}
}