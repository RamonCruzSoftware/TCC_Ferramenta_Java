package rcs.main;

import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.financial.wallet.Stock;
import rcs.suport.util.CsvHandle;
import rcs.suport.util.requests.YahooFinance;

public class MainClass 
{

	 public static void main(String[] args)  
	 {
		 
		String stockNameCode="POMO3.SA";
		YahooFinance stockYahoo=new YahooFinance();
		
		System.out.println(stockYahoo.storeCsvCurrentPriceStock(stockNameCode));
		System.out.println(stockYahoo.storeCsvHistoricalPriceStock(stockNameCode));
		CandleStick candle = stockYahoo.getCurrentValue(stockNameCode);
		
		System.out.println("candle: "+candle.getClose());
		System.out.println("candles List contem: "+stockYahoo.getHistoricalValue(stockNameCode).size()+ "");

		CsvHandle csvHandle=new CsvHandle();
		
		
		
		for(Stock s:stockYahoo.loadStocksFromCsv("/Users/alissonnunes/Desktop/EmpresasBolsa"))
		{
			System.out.println(stockYahoo.storeCsvCurrentPriceStock(s.getCodeName()));
			System.out.println(stockYahoo.storeCsvHistoricalPriceStock(s.getCodeName()));
		}
	 }
}
