package rcs.suport.financial.partternsCandleStick;

import java.util.ArrayList;

import rcs.suport.financial.partternsCandleStick.CandleStick;

public class DarkCloud extends Pattern
{
    ArrayList<CandleStick> list;

    public DarkCloud(ArrayList<CandleStick> list)
    {
        this.list = list;
    }

    public ArrayList<CandleStick> findCandleSticksPatterns()
    {
        ArrayList<CandleStick> listResult = new ArrayList<CandleStick>();

        for (int i = 0; i < list.size(); i++)
        {
            if ((i + 1) < list.size())
            {
                if ((list.get(i + 1).getClose() > list.get(i + 1).getOpen()) &&
                        (((list.get(i + 1).getClose() + list.get(i + 1).getOpen()) / 2) > list.get(i).getClose()) &&
                        (list.get(i).getOpen() > list.get(i).getClose()) &&
                        (list.get(i).getOpen() > list.get(i + 1).getClose()) &&
                        (list.get(i).getClose() > list.get(i + 1).getOpen()) &&
                        (((list.get(i).getOpen() - list.get(i).getClose()) / (0.001 + list.get(i).getHigh() - list.get(i)
                                .getLow()))
                        > 0.6))

                {
                    listResult.add(list.get(i));
                }

            }

        }

        return listResult;
    }

}