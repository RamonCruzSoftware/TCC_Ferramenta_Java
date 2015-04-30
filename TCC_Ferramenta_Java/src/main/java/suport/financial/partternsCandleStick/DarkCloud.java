package suport.financial.partternsCandleStick;

import java.util.ArrayList;

import suport.financial.partternsCandleStick.CandleStick;

public class DarkCloud extends Pattern {
	private ArrayList<CandleStick> list;
	private int limitPeriod;
	public DarkCloud(ArrayList<CandleStick> list) {
		this.setList(list);
		this.limitPeriod=10;
	}

	public DarkCloud() {
		this.limitPeriod=10;
	}

	public ArrayList<CandleStick> findCandleSticksPatterns() 
	{
		ArrayList<CandleStick>toAnalize= new ArrayList<CandleStick>();
		for(int i=((getList().size()-1)-this.limitPeriod);i<getList().size();i++)
		{
			toAnalize.add(getList().get(i));
		}
		setList(toAnalize);
		
		ArrayList<CandleStick> listResult = new ArrayList<CandleStick>();

		try {
			double C1, C, O, O1, H, L;

			for (int i = 0; i < getList().size(); i++) 
			{
				if ((i + 1) < getList().size()) 
				{
					C1 = getList().get(i).getClose();
					O1 = getList().get(i).getOpen();

					C = getList().get(i + 1).getClose();
					O = getList().get(i + 1).getOpen();
					H = getList().get(i + 1).getHigh();
					L = getList().get(i + 1).getLow();

					if (
							(C1 > O1) && (((C1 + O1) / 2) > C) && (O > C)
							&& (O > C1) && (C > O1)
							&& (((O - C) / (0.001 + H - L)) > 0.6)
							&& ((C1-O1)/C1>=0.02)
						)

					{
						listResult.add(getList().get(i + 1));
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return listResult;
	}

	public ArrayList<CandleStick> getList() {
		return list;
	}

	public void setList(ArrayList<CandleStick> list) {
		this.list = list;
	}

}