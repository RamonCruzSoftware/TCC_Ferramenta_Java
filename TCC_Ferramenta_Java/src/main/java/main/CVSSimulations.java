package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import suport.util.database.mongoDB.dao.SimulationDataDao;
import suport.util.database.mongoDB.pojo.SimulationData;

public class CVSSimulations {

	
	public void gravaArquivo() throws IOException
	{
		int i=0;
		int n=3;
		FileWriter arq = new FileWriter("/Users/ramon/Desktop/tabuada.txt");
		PrintWriter gravarArq = new PrintWriter(arq);
		gravarArq.printf("+--Resultado--+%n");
		for (i=1; i<=10; i++) 
		{
			gravarArq.printf("| %2d X %d = %2d |%n", i, n, (i*n)); 
		} 
		gravarArq.printf("+-------------+%n");
			
			arq.close();

			
	}
	public void geraCSVSimulacao2(String codeName) throws IOException
	{
		SimulationDataDao dao= new SimulationDataDao();
		
		String fileName=codeName.substring(codeName.length()-3, codeName.length());
		File arquivo= new  File("/Users/ramon/Desktop/"+fileName+".csv");
		FileWriter fw =new FileWriter(arquivo);
		BufferedWriter bw = new BufferedWriter(fw);
		
		
		StringBuilder stringBuilder= new StringBuilder();
		
		ArrayList<SimulationData> teste= dao.findDataOfSimulation(codeName);
		for(int i=0; i<teste.size();i++)
		{
			stringBuilder.append("\""+teste.get(i).getCodeName()+"\","
					+ ""+"\""+teste.get(i).getDate().getDay()+"/"+teste.get(i).getDate().getMonth()+"/"+(teste.get(i).getDate().getYear()+1900)+"\","
					+"\""+teste.get(i).getOrder()+"\","+teste.get(i).getValue()+"\n");
		}
		bw.write(stringBuilder.toString());
		bw.flush();
		bw.close();
		JOptionPane.showMessageDialog(null, stringBuilder);

	}
	public void geraCSVSimulacao(String codeName) throws IOException
	{
		String fileName=codeName.substring(codeName.length()-3, codeName.length());
		SimulationDataDao dao= new SimulationDataDao();
		System.out.println("=>"+dao.findDataOfSimulation(codeName).size());
		FileWriter arq = new FileWriter("/Users/ramon/Desktop/"+fileName+".csv");
		PrintWriter gravarArq = new PrintWriter(arq);
		
		JOptionPane.showMessageDialog(null, codeName+".SA");
		ArrayList<SimulationData> teste= dao.findDataOfSimulation(codeName);
		for(int i=0; i<teste.size();i++)
		{
			gravarArq.printf("\""+teste.get(i).getCodeName()+"\","
					+ ""+"\""+teste.get(i).getDate().getDay()+"/"+teste.get(i).getDate().getMonth()+"/"+(teste.get(i).getDate().getYear()+1900)+"\","
					+"\""+teste.get(i).getOrder()+"\","+teste.get(i).getValue()+"%n");
			
			gravarArq.printf("\""+teste.get(i).getCodeName()+"\","
					+ ""+"\""+teste.get(i).getDate().getDay()+"/"+teste.get(i).getDate().getMonth()+"/"+(teste.get(i).getDate().getYear()+1900)+"\","
					+"\""+teste.get(i).getOrder()+"\","+teste.get(i).getValue()+"%n");
			
			
		}
		

	}
	
}