package suport.financial.partternsCandleStick;

import java.util.ArrayList;

public class BullishEngulfing extends Pattern {
	private ArrayList<CandleStick> list;
	private int limitPeriod;
	public BullishEngulfing(ArrayList<CandleStick> list) {
		this.setList(list);
		this.limitPeriod=10;
	}

	public BullishEngulfing() {
		this.limitPeriod=10;
	}

	@Override
	public ArrayList<CandleStick> findCandleSticksPatterns() {
		ArrayList<CandleStick> listResult = new ArrayList<CandleStick>();
		ArrayList<CandleStick>toAnalize= new ArrayList<CandleStick>();
		for(int i=((getList().size()-1)-this.limitPeriod);i<getList().size();i++)
		{
			toAnalize.add(getList().get(i));
		}
		setList(toAnalize);
		try {
			double C1, C, O, O1;
			for (int i = 0; i < getList().size(); i++) 
			{
				if ((i + 1) < getList().size()) {
					C1 = getList().get(i).getClose();
					O1 = getList().get(i).getOpen();
					C = getList().get(i + 1).getClose();
					O = getList().get(i + 1).getOpen();
					
					if (
							(O1 > C1)
							&&
							(C > O1) 
							&& (O < C1)
							&&
							((O1-C1)/O1)>=0.02
						) 
					{
						listResult.add(getList().get(i + 1));
					}
				}
			}
		} catch (Exception e) {// TODO LOG
			// e.printStackTrace();
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