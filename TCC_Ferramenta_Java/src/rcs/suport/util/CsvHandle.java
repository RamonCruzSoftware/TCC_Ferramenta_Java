package rcs.suport.util;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import rcs.suport.financial.partternsCandleStick.CandleStick;

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

    public ArrayList<CandleStick> readCsvFile(String csv_file_path, ArrayList<CandleStick> list)
    {

        try {

            BufferedReader StrR = new BufferedReader(new FileReader(csv_file_path));

            String Str;
            String[] TableLine = null;

            while ((Str = StrR.readLine()) != null) {

                // passando como parametro o divisor ",".
                TableLine = Str.split(",");
                list.add(new CandleStick(TableLine[1],
                        TableLine[2], TableLine[3],
                        TableLine[4], TableLine[5],
                        TableLine[0]));

            }
            // Fechamos o buffer
            StrR.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return list;

    }


}
