package rcs.suport.financial.partternsCandleStick;

import java.util.ArrayList;

import rcs.suport.financial.partternsCandleStick.CandleStick;

public class BearishEngulfing extends Pattern
{

    private ArrayList<CandleStick> list;

    public BearishEngulfing(ArrayList<CandleStick> list)
    {
        this.list = list;
    }

    public ArrayList<CandleStick> findCandleSticksPatterns()
    {
        ArrayList<CandleStick> listResult = new ArrayList<CandleStick>();
        int i=0;
        double C1,C,O,O1,H,L;
       
        for (i = 0; i < list.size(); i++)
        { 
            if ((i + 1) < list.size())
            {
            	 C1=list.get(i + 1).getClose();
                 O1=list.get(i + 1).getOpen();
                 C=list.get(i).getClose();
                 O=list.get(i).getOpen();
                 H=list.get(i).getHigh();
                 L=list.get(i).getLow();
            	if((C1>O1)&&
            	   (((C1+O1)/2)>C)&&
            	   (O>C)&&
            	   (O>C1)&&
            	   (C>O1)&&
            	   (((O-C)/(.001+H-L))>0.6)
            	   ){
            		 listResult.add(list.get(i));
            		}

            }
        }

        return listResult;
    }

   
}