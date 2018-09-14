package Neda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class data {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String letters="abcdefghijklmnopqrstuvwxyz";
		final File folder = new File("C:/Ava/CleanedStacks/");
		String line;
		short c1=0;
		short c2=0;
		for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isFile()) {
	              String filename=fileEntry.getName();
	              System.out.println("filename="+ filename);
	             // String inputFile="C:/Ava/CleanedStacks/"+inputFiles[inn]+".csv";
	      		BufferedReader br=null;
	      		BufferedWriter bw=null;
	      		try{
	      			br = new BufferedReader(new FileReader(filename));
	      			bw = new BufferedWriter(new FileWriter("C:/Ava/CleanedStacksCSV/"+filename+".csv"));
	      			line=br.readLine();
	      			String[] features=line.split(" ");
	      			//1-7633
	      			
//1551
	      			byte[] f=new byte[7633];
	      			for(int i=0; i<features.length;i++){
	      				System.out.println("*"+ features[i]+"*");
	      				System.out.println("*"+ Integer.parseInt(features[i])+"*");
	      				f[Integer.parseInt(features[i])]=1;

	      			}
      				for(int i=0;i<7633;i++)
      					if(f[i]==1)
      						bw.write("1,");
      					else
      						bw.write("0,");
      				//filename-->string
      				
      				String className="aa";
      				className=String.valueOf(letters.charAt(c1))+String.valueOf(letters.charAt(c2));
      				c2++;
      				if(c2==26){
      					c2=0;
      					c1++;
      				}
      				bw.write(className);
      				bw.newLine();
	      			
	      			
	      			
	      			
	      			
	      		}catch(FileNotFoundException e){
	      			e.printStackTrace();
	      			//System.out.println("FileNotFoundException line number="+ row);
	      		}catch(IOException e){
	      			e.printStackTrace();
	      			//System.out.println("IOException line number="+ row);
	      		}finally{
	      			//System.out.println("Finally line number="+ row);
	      			if(br!=null){
	      				try{
	      					br.close();
	      				}catch(IOException e){
	      					e.printStackTrace();
	      				}
	      			}
	      		}
	        }
	    }
		
		
	}

}
