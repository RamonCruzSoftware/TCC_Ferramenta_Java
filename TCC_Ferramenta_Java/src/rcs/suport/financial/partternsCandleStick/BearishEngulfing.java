package rcs.suport.financial.partternsCandleStick;

import java.util.ArrayList;

import rcs.suport.financial.partternsCandleStick.CandleStick;

public class BearishEngulfing extends Pattern
{
    private ArrayList<CandleStick> list;
    public BearishEngulfing(ArrayList<CandleStick> list)
    {
        this.setList(list);
    }
    public BearishEngulfing(){}
    public ArrayList<CandleStick> findCandleSticksPatterns()
    {
    	ArrayList<CandleStick> listResult =null;
    	try
    	{
    		listResult = new ArrayList<CandleStick>();
            int i=0;
            double C1,C,O,O1,H,L;
            for (i = 0; i < 5; i++)
            { 
                if ((i + 1) < getList().size())
                {
                	 C1=getList().get(i ).getClose();
                     O1=getList().get(i).getOpen();
                     C=getList().get(i + 1).getClose();
                     O=getList().get(i + 1).getOpen();
                     H=getList().get(i + 1).getHigh();
                     L=getList().get(i + 1).getLow();
                	
                     if((C1>O1)&&
                	   (((C1+O1)/2)>C)&&
                	   (O>C)&&
                	   (O>C1)&&
                	   (C<O1)&&
                	   (((O-C)/(.001+H-L))>0.6)
                	   )
                     {
                		 listResult.add(getList().get(i + 1));
                     }
                }
            }
       }catch(Exception e)
    	{//TODO LOG
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