package rcs.suport.util;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.financial.wallet.Stock;

public class InfoConversationsTest {
	
	private InfoConversations info;

	@Test
	public void testInfoConversations()
	{
		info= new InfoConversations();
		Assert.assertEquals(InfoConversations.class, info.getClass());
	}

	@Test
	public void testInfoConversationsStringInt()
	{
		
		info= new InfoConversations("user",0);
		
		Assert.assertEquals(0, info.getUserProfile());
		Assert.assertEquals("user", info.getUserName());
		
	}

	@Test
	public void testGetUserName() 
	{
		info= new InfoConversations();
		
		info.setUserName("test");
		Assert.assertEquals("test", info.getUserName());
		
	}

	@Test
	public void testSetUserName()
	{
		info= new InfoConversations();
		
		info.setUserName("test");
		Assert.assertEquals("test", info.getUserName());
		
	}

	@Test
	public void testGetUserProfile() {
		
		info= new InfoConversations();
		
		info.setUserProfile(1);;
		Assert.assertEquals(1, info.getUserProfile());
		
	}

	@Test
	public void testSetUserProfile() 
	{
		info= new InfoConversations();
		
		info.setUserProfile(1);
		Assert.assertEquals(1, info.getUserProfile());
		
	}

	@Test
	public void testGetStockList()
	{
		info= new InfoConversations();
		ArrayList<Stock> stockList= new ArrayList<Stock>();
		
		info.setStockList(stockList);
		Assert.assertEquals(stockList.getClass(),info.getStockList().getClass());
	}

	@Test
	public void testSetStockList() {
		
		info= new InfoConversations();
		ArrayList<Stock> stockList= new ArrayList<Stock>();
		
		info.setStockList(stockList);
		Assert.assertEquals(stockList.getClass(),info.getStockList().getClass());
		
	}

}
