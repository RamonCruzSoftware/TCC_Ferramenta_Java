package rcs.suport.financial.partternsCandleStick;

import java.util.ArrayList;

public class BullishEngulfing extends Pattern{

	private ArrayList<CandleStick> list;
	
	public BullishEngulfing(ArrayList<CandleStick>list)
	{
		this.setList(list);
	}
	public BullishEngulfing()
	{
		
	}
	
	@Override
	public ArrayList<CandleStick> findCandleSticksPatterns()
	{
		  ArrayList<CandleStick> listResult = new ArrayList<CandleStick>();
	        
	        try
	        {
	        	double C1,C,O,O1,H,L;
	        	
	            //for (int i = 0; i < getList().size(); i++)
	            for (int i = 0; i < 5; i++)
	            {
	                if ((i + 1) < getList().size())
	                {
	                	C1=getList().get(i).getClose();
	                	O1=getList().get(i).getOpen();
	                	
	                	C=getList().get(i + 1).getClose();
	                	O=getList().get(i + 1).getOpen();
	                	H=getList().get(i + 1).getHigh();
	                	L=getList().get(i + 1).getLow();
	                	
	                    if (
	                    		(O1 > C1) 
	                    		&&
	                            (C1 < C) 
	                            &&
	                            (O< C)
	                            &&
	                            (O < C1) 
	                            &&
	                            (C > O1) 
	                            &&
	                            (((O - C) / (0.001 + H - L))> 0.6)
	                       )

	                    {
	                        listResult.add(getList().get(i+1));
	                    }

	                }

	            }
	        }catch(Exception e)
	        {
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
