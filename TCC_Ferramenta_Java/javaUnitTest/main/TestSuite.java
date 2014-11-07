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
import rcs.suport.util.InfoConversations;
import rcs.suport.util.database.mongoDB.MongoConnetionTest;
import rcs.suport.util.database.mongoDB.dao.InfoStrategiesDaoTest;
import rcs.suport.util.database.mongoDB.dao.ManagedStockDaoTest;
import rcs.suport.util.database.mongoDB.dao.ManagedWalletDaoTest;
import rcs.suport.util.database.mongoDB.dao.OrdersCreateDaoTest;

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
	Statistical.class,
	InfoConversations.class,
	MongoConnetionTest.class,
	InfoStrategiesDaoTest.class,
	ManagedStockDaoTest.class,
	ManagedWalletDaoTest.class,
	OrdersCreateDaoTest.class
	}
		)

public class TestSuite {
	
	
	

}
