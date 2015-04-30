package rcs.suport.util.requests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.financial.wallet.Stock;

public class YahooFinance implements Runnable {

	 private String mainDirPath;
	
	 private String subDirPath_1;
	 private String subDirPath_2;
	
	 private File mkdir;
	 
	 
	 private ArrayList<Stock>stockList;
	 
	 public YahooFinance(String mainDirPath,String subDirPath_1,String subDirPath_2)
	 {
		 this.setMainDirPath(mainDirPath);
		 this.setSubDirPath_1(subDirPath_1);
		 
		 setMkdir(new File(mainDirPath+subDirPath_1));
		 this.getMkdir().mkdir();
		 
		 this.setSubDirPath_2(subDirPath_2);
		 
		 setMkdir(new File(mainDirPath+subDirPath_1+subDirPath_2));
		 this.getMkdir().mkdir();
		 
	 }
	 public YahooFinance(String mainDirPath,String subDirPath_1,String subDirPath_2,ArrayList<Stock>stockList)
	 {
		 this.setMainDirPath(mainDirPath);
		 this.setSubDirPath_1(subDirPath_1);
		 
		 setMkdir(new File(mainDirPath+subDirPath_1));
		 this.getMkdir().mkdir();
		 
		 this.setSubDirPath_2(subDirPath_2);
		 
		 setMkdir(new File(mainDirPath+subDirPath_1+subDirPath_2));
		 this.getMkdir().mkdir();
		 
	 }
	 

	public CandleStick getCurrentValue(String stockCodeName)
	{
		 String dirOutputPath=this.getMainDirPath()+this.getSubDirPath_1()+this.getSubDirPath_2()+"/"+stockCodeName;
		 String fileOutputPath=dirOutputPath+"/currentPrice_"+stockCodeName+".csv";
		 
		 CandleStick candleResult=null;
		 
		 try {

	            BufferedReader StrR = new BufferedReader(new FileReader(fileOutputPath));
	            String Str;
	            String[] TableLine = null;

	            while ((Str = StrR.readLine()) != null) 
	            {
	                // passando como parametro o divisor ",".
	                TableLine = Str.split(",");	    
	                
	                DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mma",Locale.US);	               
					Date date = (Date)format.parse(TableLine[1].substring(1, TableLine[1].length()-1)
													+" "+
													TableLine[2].substring(1, TableLine[2].length()-1));
					
				try{
						
						candleResult= new CandleStick(TableLine[3],
											TableLine[4], TableLine[5],
											TableLine[6], TableLine[7],
											date);
	                
					}catch(NumberFormatException e)
					{
						System.out.println("Error: "+e);
					}
	            }
	            // Fechamos o buffer
	            StrR.close();

	        } catch (FileNotFoundException e) 
	        {
	            e.printStackTrace();
	        } catch (IOException ex)
	        {
	            ex.printStackTrace();
	        }catch (ParseException e)
	        {
	        	e.printStackTrace();
	        	
			}catch(NumberFormatException e)
			{
				e.printStackTrace();
			}
		 	
	        return candleResult;
		
	}

	
	public ArrayList<CandleStick> getHistoricalValue(String stockCodeName) 
	{
		ArrayList<CandleStick>list=new ArrayList<CandleStick>();
		String dirOutputPath=this.getMainDirPath()+this.getSubDirPath_1()+this.getSubDirPath_2()+"/"+stockCodeName;
		String fileOutputPath=dirOutputPath+"/historicalPrice_"+stockCodeName+".csv";
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date =null;

	        try {

	            BufferedReader StrR = new BufferedReader(new FileReader(fileOutputPath));
	            String Str;
	            String[] TableLine = null;
	          
	            StrR.readLine();
	            while ((Str = StrR.readLine()) != null) 
	            {
	           	// passando como parametro o divisor ",".
		           TableLine = Str.split(",");
		           	date = (Date)format.parse(TableLine[0]);
		           	
		           
	             	list.add(new CandleStick(TableLine[1],TableLine[2],TableLine[3],TableLine[4], TableLine[5],date));

	            }
	            // Fechamos o buffer
	            StrR.close();

	        } catch (FileNotFoundException e) 
	        {
	            e.printStackTrace();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }catch (ParseException e) {
				
				e.printStackTrace();
			}
	        return list;
		
	}
	public ArrayList<Stock> loadStocksFromCsv(String stocksCsvfolderPath)
	{
		ArrayList<Stock>list=new ArrayList<Stock>();
		ArrayList<String>sectors=new ArrayList<String>();
		
		File file=new File(stocksCsvfolderPath);
		for(File f:file.listFiles())
		{
			sectors.add(f.getName());//Sector Name (csv fileName)
			
		}
		BufferedReader StrR;
		String Str;
		String[] TableLine;
		for(String s:sectors)
		{
			 try {

		            StrR = new BufferedReader(new FileReader(stocksCsvfolderPath+"/"+s));
		            TableLine = null;
		            Str=null;

		            while ((Str = StrR.readLine()) != null)
		            {
		                // passando como parametro o divisor ",".
		                TableLine = Str.split(",");	            
		                list.add(new Stock(TableLine[0]+".SA",s.substring(0, (s.length()-4))));
		                
		            }
		            // Fechamos o buffer
		            StrR.close();

		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        
		        }
			 }
		
	//FeedBack	
		System.out.println("foram encontrados  "+sectors.size()+ " Setores e "+list.size()+" Ações");
		System.out.println("Carregados os seguintes Stocks");
		for(Stock s:list)
		{
			System.out.println(s.getCodeName());
		}
		
		return list;
		
	}
	
	/**
	 * Make download of the csv file with current price of a stock
	 * @param stockCodeName
	 * @return
	 */
	public boolean storeCsvCurrentPriceStock(String stockCodeName)
	{
	  boolean returnResult=false;	
	  String urlPath = "http://finance.yahoo.com/d/quotes.csv?s="+stockCodeName+"&f=sd1t1ohgl1v&e=.csv";
	  String dirOutputPath=this.getMainDirPath()+this.getSubDirPath_1()+this.getSubDirPath_2()+"/"+stockCodeName;
	  String fileOutputPath=dirOutputPath+"/currentPrice_"+stockCodeName+".csv";
	 
	
	  
	        try {
	    
	        	URL website=new URL(urlPath);
	        	File dir=new File(dirOutputPath);  
	        	dir.mkdir();
	        	
	        	ReadableByteChannel rbc=Channels.newChannel(website.openStream());  
	        	
	      	
	        	FileOutputStream fosFileOutputStream= new FileOutputStream(fileOutputPath);
	       
	        	fosFileOutputStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
	        	
	        	//Check the new file
	        	File file = new File(fileOutputPath);
	        	returnResult=(file.exists())?true:false;
	        	
	        }catch(IOException e){   
	                 e.printStackTrace();
	                 returnResult=false;
	        } 
	         
		return returnResult;
	}
	
	
	/**
	 * Make download  the csv file with historical pricies of a stock
	 * @param stockNameCode
	 * @return
	 */
	public boolean storeCsvHistoricalPriceStock(String stockCodeName)
	{
		 boolean returnResult = false;
		 Calendar currentDate=Calendar.getInstance();

		 String urlPath = "http://real-chart.finance.yahoo.com/table.csv?s="+stockCodeName+
				 "&d="+currentDate.get(Calendar.DAY_OF_MONTH)+"&e="+currentDate.get(Calendar.MONTH)+
				 "&f="+currentDate.get(Calendar.YEAR)+"&g=d&a=0&b=3&c=2000&ignore=.csv";

		 String dirOutputPath=this.getMainDirPath()+this.getSubDirPath_1()+this.getSubDirPath_2()+"/"+stockCodeName;
		 String fileOutputPath=dirOutputPath+"/historicalPrice_"+stockCodeName+".csv";
		        
		        try {
		    
		        	URL website=new URL(urlPath);
		        	File dir=new File(dirOutputPath);  
		        	dir.mkdir();
		        	
		        	ReadableByteChannel rbc=Channels.newChannel(website.openStream());      	
		        	FileOutputStream fosFileOutputStream= new FileOutputStream(fileOutputPath);
		       
		        	fosFileOutputStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		        	
		        	//Check the new file
		        	File file = new File(fileOutputPath);
		        	returnResult=(file.exists())?true:false;
		        	
		        }catch(IOException e){   
		                 e.printStackTrace();
		        } 
		return returnResult;
	}

	

	@Override
	public void run() {
		// TODO 
		/*
		 * Implementar esse metodo com classe anonima pois 
		 * o corpo desse metodo ira variar de acordo com a 
		 * necessidade.
		 */
		
	}
	
	public boolean dropMainFolder()
	{
		File mainPath = new File(mainDirPath+subDirPath_1);
		return deleteDirectory(mainPath);
	}
	
	private  boolean deleteDirectory(File directory) 
	{
	    if(directory.exists())
	    {
	        File[] files = directory.listFiles();
	        if(null!=files)
	        {
	            for(int i=0; i<files.length; i++) 
	            {
	                if(files[i].isDirectory()) 
	                {
	                    deleteDirectory(files[i]);
	                }
	                else {
	                    files[i].delete();
	                }
	            }
	        }
	    }
	    return(directory.delete());
	}

	public String getMainDirPath() {
		return mainDirPath;
	}

	public void setMainDirPath(String mainDirPath) {
		this.mainDirPath = mainDirPath;
	}

	public String getSubDirPath_1() {
		return subDirPath_1;
	}

	public void setSubDirPath_1(String subDirPath_1) {
		this.subDirPath_1 = subDirPath_1;
	}

	public String getSubDirPath_2() {
		return subDirPath_2;
	}

	public void setSubDirPath_2(String subDirPath_2) {
		this.subDirPath_2 = subDirPath_2;
	}

	public File getMkdir() {
		return mkdir;
	}

	public void setMkdir(File mkdir) {
		this.mkdir = mkdir;
	}
	public ArrayList<Stock> getStockList() {
		return stockList;
	}
	public void setStockList(ArrayList<Stock> stockList) {
		this.stockList = stockList;
	}

}
