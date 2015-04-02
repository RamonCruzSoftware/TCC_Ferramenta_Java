package suport.utilTest;

import java.util.ArrayList;

import junit.framework.Assert;
import suport.financial.wallet.Stock;
import suport.util.InfoConversations;

public class InfoConversationsTest {
	
	private InfoConversations info;

	 
	public void testInfoConversations()
	{
		info= new InfoConversations();
		Assert.assertEquals(InfoConversations.class, info.getClass());
	}

	 
	public void testInfoConversationsStringInt()
	{
		
		info= new InfoConversations("user",0);
		
		Assert.assertEquals(0, info.getUserProfile());
		Assert.assertEquals("user", info.getUserName());
		
	}

	 
	public void testGetUserName() 
	{
		info= new InfoConversations();
		
		info.setUserName("test");
		Assert.assertEquals("test", info.getUserName());
		
	}

	 
	public void testSetUserName()
	{
		info= new InfoConversations();
		
		info.setUserName("test");
		Assert.assertEquals("test", info.getUserName());
		
	}

	 
	public void testGetUserProfile() {
		
		info= new InfoConversations();
		
		info.setUserProfile(1);;
		Assert.assertEquals(1, info.getUserProfile());
		
	}

	 
	public void testSetUserProfile() 
	{
		info= new InfoConversations();
		
		info.setUserProfile(1);
		Assert.assertEquals(1, info.getUserProfile());
		
	}

	 
	public void testGetStockList()
	{
		info= new InfoConversations();
		ArrayList<Stock> stockList= new ArrayList<Stock>();
		
		info.setStockList(stockList);
		Assert.assertEquals(stockList.getClass(),info.getStockList().getClass());
	}

	 
	public void testSetStockList() {
		
		info= new InfoConversations();
		ArrayList<Stock> stockList= new ArrayList<Stock>();
		
		info.setStockList(stockList);
		Assert.assertEquals(stockList.getClass(),info.getStockList().getClass());
		
	}

}
