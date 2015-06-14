package main;

import org.junit.runner.RunWith;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import suport.financial.indicatorsTest.MovingAvarangeTest;
import suport.financial.partternsCandleStickTest.BearishEngulfingTest;
import suport.financial.partternsCandleStickTest.CandlestickTest;
import suport.financial.partternsCandleStickTest.DarkCloudTest;
import suport.financial.strategyTest.MovingAvarangeExponentialStrategyTest;
import suport.financial.strategyTest.MovingAvarangeSimpleStrategyTest;
import suport.financial.wallet.Stock;
import suport.financial.walletTest.StockTest;
import suport.financial.walletTest.WalletTest;
import suport.statisticalTest.MatrixTest;
import suport.statisticalTest.StatisticalTest;
import suport.statistical.Statistical;

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
	StatisticalTest.class
	}
		)
public class TestSuite  {

}
