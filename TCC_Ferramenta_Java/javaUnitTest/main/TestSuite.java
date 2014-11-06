package main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import rcs.suport.financial.indicators.MovingAvarangeTest;
import rcs.suport.financial.partternsCandleStick.BearishEngulfingTest;
import rcs.suport.financial.partternsCandleStick.CandlestickTest;
import rcs.suport.financial.partternsCandleStick.DarkCloudTest;
import rcs.suport.financial.strategy.MovingAvarangeExponentialStrategyTest;
import rcs.suport.financial.strategy.MovingAvarangeSimpleStrategyTest;
import rcs.suport.financial.wallet.Stock;
import rcs.suport.financial.wallet.StockTest;
import rcs.suport.financial.wallet.WalletTest;
import rcs.suport.statistical.MatrixTest;
import rcs.suport.statistical.Statistical;

@RunWith(Suite.class)
@Suite.SuiteClasses(
	{
	MovingAvarangeTest.class,
	BearishEngulfingTest.class,
	CandlestickTest.class,
	DarkCloudTest.class,
	MovingAvarangeExponentialStrategyTest.class,
	MovingAvarangeSimpleStrategyTest.class,
	StockTest.class,
	WalletTest.class,
	MatrixTest.class,
	Statistical.class
	}
		)

public class TestSuite {
	
	

}
