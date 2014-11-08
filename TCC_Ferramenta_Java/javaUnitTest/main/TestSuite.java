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
import rcs.suport.util.database.mongoDB.dao.StockDaoTest;
import rcs.suport.util.database.mongoDB.dao.UserInfoDaoTest;
import rcs.suport.util.database.mongoDB.pojo.InfoStrategies;
import rcs.suport.util.database.mongoDB.pojo.InfoStrategiesTest;
import rcs.suport.util.database.mongoDB.pojo.ManagedStockTest;
import rcs.suport.util.database.mongoDB.pojo.ManagedWalletTest;
import rcs.suport.util.database.mongoDB.pojo.OrdersCreateTest;
import rcs.suport.util.database.mongoDB.pojo.UserInfoTest;
import rcs.suport.util.requests.YahooFinanceTest;

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
	OrdersCreateDaoTest.class,
	StockDaoTest.class,
	UserInfoDaoTest.class,
	InfoStrategiesTest.class,
	ManagedStockTest.class,
	ManagedWalletTest.class,
	OrdersCreateTest.class,
	UserInfoTest.class,
	YahooFinanceTest.class
	}
		)

public class TestSuite {
	
	
	

}
