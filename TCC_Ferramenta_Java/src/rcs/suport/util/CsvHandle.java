package rcs.suport.util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import rcs.suport.financial.partternsCandleStick.CandleStick;
import rcs.suport.financial.wallet.Stock;

public class CsvHandle {
	
	
    public void createCsvFile(String csv_file_path, ArrayList<CandleStick> list)
    {
        try
        {
            // Criacao de um buffer para a escrita em uma stream
            BufferedWriter StrW = new BufferedWriter(new FileWriter(csv_file_path));

            // Escrita dos dados da tabela

            StrW.write("Nome;Telefone;Idade\n");
            StrW.write("Juliana;6783-8490;23\n");
            StrW.write("Tatiana;6743-7480;45\n");
            StrW.write("Janice;6909-9380;21");

            // Fechamos o buffer
            StrW.close();

        } catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<Stock>loadStocksFromCsv(String path)
    {
    	ArrayList<Stock>list=new ArrayList<Stock>();
    	
    	File file=new File(path);
    	File files[]=file.listFiles();
    	
    	
    	for(File f : files)
    	{
    		File arquivos=f;
    		System.out.println(f.getName());
    	}
    	
    	return list;
    	
    }
    
    public ArrayList<CandleStick> readCsvFile(String csv_file_path, ArrayList<CandleStick> list)
    {
    	DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    	Date date =null;
    	try {

            BufferedReader StrR = new BufferedReader(new FileReader(csv_file_path));
            String Str;
            String[] TableLine = null;

            while ((Str = StrR.readLine()) != null) {
                // passando como parametro o divisor ",".
                TableLine = Str.split(",");
               date = (Date)format.parse(TableLine[0]);
                list.add(new CandleStick(TableLine[1],
                        TableLine[2], TableLine[3],
                        TableLine[4], TableLine[5],
                        date));
            }
            // Fechamos o buffer
            StrR.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return list;

    }


}
