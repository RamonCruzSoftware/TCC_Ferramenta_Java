package agents.suportTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import suport.financial.partternsCandleStick.CandleStick;
import suport.financial.wallet.Stock;

import core.agents.suport.WalletManagerAuxiliary;

public class WalletManagerAuxiliaryTest {

	WalletManagerAuxiliary auxiliary;
	Map<String, ArrayList<Stock>> infoExperts;
	ArrayList<Stock> stockList;

	public void setUp() throws Exception {

		this.infoExperts = null;
		this.stockList = new ArrayList<Stock>();
		this.infoExperts = new HashMap<String, ArrayList<Stock>>();

		ArrayList<Stock> list_a = new ArrayList<Stock>();
		ArrayList<Stock> list_b = new ArrayList<Stock>();
		ArrayList<Stock> list_c = new ArrayList<Stock>();
		ArrayList<Stock> list_d = new ArrayList<Stock>();

		this.stockList = this.stockTestList(2);

		list_a.add(new Stock("Stock_a", "Test"));
		list_a.add(new Stock("Stock_a", "Test"));
		list_a.add(new Stock("Stock_a", "Test"));

		list_b.add(new Stock("Stock_b", "Test"));
		list_b.add(new Stock("Stock_b", "Test"));
		list_b.add(new Stock("Stock_b", "Test"));

		list_c.add(new Stock("Stock_c", "Test"));
		list_c.add(new Stock("Stock_c", "Test"));
		list_c.add(new Stock("Stock_c", "Test"));

		list_d.add(new Stock("Stock_d", "Test"));
		list_d.add(new Stock("Stock_d", "Test"));
		list_d.add(new Stock("Stock_d", "Test"));

		this.infoExperts.put("Expert_a", list_a);
		this.infoExperts.put("Expert_b", list_b);
		this.infoExperts.put("Expert_c", list_c);
		this.infoExperts.put("Expert_d", list_d);

		auxiliary = new WalletManagerAuxiliary(this.stockList, 1000.0, 0);

	}

	public void testCloseOrder() {

		Assert.assertEquals(250.0, auxiliary.getQuota());

		auxiliary.closeOrder("Expert_a", 1000.0);

		Assert.assertEquals(500.0, auxiliary.getQuota());
	}

	public void testApproveOrderBuy() {

		Assert.assertEquals(250.0, auxiliary.approveOrderBuy("Expert_a"));
		Assert.assertEquals(250.0, auxiliary.approveOrderBuy("Expert_b"));
		Assert.assertEquals(250.0, auxiliary.approveOrderBuy("Expert_c"));
		Assert.assertEquals(250.0, auxiliary.approveOrderBuy("Expert_d"));

	}

	public void testRefreshWalletManager() {

	}

	public void testAnalyzeStockSuggestions() {

		Assert.assertNotNull(this.stockList);

		Assert.assertEquals(4, this.auxiliary.analyzeStocksSuggestionsList()
				.get(0).size());

	}

	public void testAnalyzeStock() {
		ArrayList<CandleStick> candleList = new ArrayList<CandleStick>();
		Stock stockRefused = new Stock("Refused", "Test");

		ArrayList<CandleStick> candleList_2 = new ArrayList<CandleStick>();
		Stock stockApproved = new Stock("Approved", "Test");

		this.auxiliary.analyzeStocksSuggestionsList();

		for (int i = 0; i < 31; i++) {
			candleList.add(new CandleStick(1, 1, 1, i + 1, 10, null));
			candleList_2.add(new CandleStick(1, 1, 1, Math.random() * 100, 10,
					null));
		}

		stockRefused.setCandleSticks(candleList);
		stockApproved.setCandleSticks(candleList_2);

		Assert.assertFalse(this.auxiliary.analyzeStock(stockRefused));

	}

	private ArrayList<Stock> stockTestList(int qtdPositiveCorrel) {

		ArrayList<Stock> stockList = new ArrayList<Stock>();

		Stock stock_1 = new Stock("Test_a", "test");
		Stock stock_2 = new Stock("Test_b", "test");
		Stock stock_3 = new Stock("Test_c", "test");
		Stock stock_4 = new Stock("Test_d", "test");

		ArrayList<CandleStick> candleList_1 = new ArrayList<CandleStick>();
		ArrayList<CandleStick> candleList_2 = new ArrayList<CandleStick>();
		ArrayList<CandleStick> candleList_3 = new ArrayList<CandleStick>();
		ArrayList<CandleStick> candleList_4 = new ArrayList<CandleStick>();

		switch (qtdPositiveCorrel) {
		case 0:// Nenhuma correlacao positiva
		{
			// 1
			for (int i = 0; i < 31; i++) {
				candleList_1.add(new CandleStick(1, 1, 1, i + 1, 10, null));
			}

			// 2
			for (int i = 0; i < 31; i++) {
				candleList_2.add(new CandleStick(1, 1, 1, (i % 2 == 0 ? i
						: i * 3), 10, null));
			}

			// 3
			for (int i = 31; i > 0; i--) {
				candleList_3.add(new CandleStick(1, 1, 1, i, 10, null));
			}

			// 4
			for (int i = 0; i < 31; i++) {
				candleList_4.add(new CandleStick(1, 1, 1, 5, 10, null));
			}
		}
			break;
		case 1:// Uma correlacao positiva
		{
			// 1
			for (int i = 0; i < 31; i++) {
				candleList_1.add(new CandleStick(1, 1, 1, i + 1, 10, null));
			}

			// 2
			for (int i = 0; i < 31; i++) {
				candleList_1.add(new CandleStick(1, 1, 1, i + 1, 10, null));
			}

			// 3
			for (int i = 31; i > 0; i--) {
				candleList_3.add(new CandleStick(1, 1, 1, i, 10, null));
			}

			// 4
			for (int i = 0; i < 31; i++) {
				candleList_4.add(new CandleStick(1, 1, 1, 5, 10, null));
			}
		}
			break;
		case 2:// Duas Correlacoes positivas

		{
			// 1
			for (int i = 0; i < 31; i++) {
				candleList_1.add(new CandleStick(1, 1, 1, i + 1, 10, null));
			}

			// 2
			for (int i = 0; i < 31; i++) {
				candleList_1.add(new CandleStick(1, 1, 1, i + 1, 10, null));
			}

			// 3
			for (int i = 31; i > 0; i--) {
				candleList_3.add(new CandleStick(1, 1, 1, 5, 10, null));
			}

			// 4
			for (int i = 0; i < 31; i++) {
				candleList_4.add(new CandleStick(1, 1, 1, 5, 10, null));
			}
		}
			break;
		case 3:// Tres correlacoes positivas
		{
			// 1
			for (int i = 0; i < 31; i++) {
				candleList_1.add(new CandleStick(1, 1, 1, i + 1, 10, null));
			}

			// 2
			for (int i = 0; i < 31; i++) {
				candleList_1.add(new CandleStick(1, 1, 1, i + 1, 10, null));
			}

			// 3
			for (int i = 31; i > 0; i--) {
				candleList_3.add(new CandleStick(1, 1, 1, i, 10, null));
			}

			// 4
			for (int i = 0; i < 31; i++) {
				candleList_4.add(new CandleStick(1, 1, 1, i + 1, 10, null));
			}
		}
			break;

		default:// Duas correlacoes positivas
		{
			// 1
			for (int i = 0; i < 31; i++) {
				candleList_1.add(new CandleStick(1, 1, 1, i + 1, 10, null));
			}

			// 2
			for (int i = 0; i < 31; i++) {
				candleList_2.add(new CandleStick(1, 1, 1, i + 1, 10, null));
			}

			// 3
			for (int i = 31; i > 0; i--) {
				candleList_3.add(new CandleStick(1, 1, 1, 1, 10, null));
			}

			// 4
			for (int i = 0; i < 31; i++) {
				candleList_4.add(new CandleStick(1, 1, 1, 5, 10, null));
			}
		}
			break;
		}

		stock_1.setCandleSticks(candleList_1);
		stock_2.setCandleSticks(candleList_2);
		stock_3.setCandleSticks(candleList_3);
		stock_4.setCandleSticks(candleList_4);

		stockList.add(stock_1);
		stockList.add(stock_2);
		stockList.add(stock_3);
		stockList.add(stock_4);

		return stockList;

	}

}
