package com.business;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.regex.*;

public class business {

	public static void main(String[] args) {
		try
		{
			StringBuilder sb = new StringBuilder();
			String input_path = "E:\\mdtest\\business.list"; // input file
	        String output_path = "E:\\mdtest\\businessNEW.txt"; //output file
	        BufferedReader br = new BufferedReader(new FileReader(input_path));
	        OutputStream outputStream = new FileOutputStream(output_path);
	        Writer writer  = new OutputStreamWriter(outputStream);
	        String line;
	        while((line = br.readLine())!=null)
	        {
	        	if(line.contains("MV")&&(!line.contains("tv"))&&(!line.contains("{"))) // checks if the line contains a movie title by looking for MV
	        	{
	        		if(sb.length()==0) // stringbuilder is empty
	        		{
	        			sb.append(line.substring(4));
	        		}
	        		else
	        		{
	        			if(countregex("(#split#)", sb.toString()) == 3) //check if stringbuilder contains 3 repetations of #split# that would imply it has all the 3 informations budget,gross collection and opening weekend
	        			{
	        				System.out.println("sb = "+sb);// write to the new file
	        				writer.write(sb.toString()+"\r\n");
	        				sb=new StringBuilder();
                            sb.append(line.substring(4));
	        			}
	        			else
	        			{
	        				sb=new StringBuilder();
	                         sb.append(line.substring(4));
	        			}
	        		}
	        	}
	        	
	        	if((line.contains("USD"))&&(line.contains("BT")))//check if line contains budget
	        	{
	        		String txt;
	        		txt = line.substring(8);
                    txt = txt.replace(",", "");
                    txt = txt.replace(" ", "");
                    sb.append("#split#");//put a #split# to split budget from other
                    sb.append(txt);
	        	}
	        	
	        	if((line.contains("GR"))&&(line.contains("USA")))// check if line has gross collection
	        	{
	        		if(countregex("(#split#)", sb.toString())==1)// check if line has budget first
	        		{
	        			String txt;
                        int first = 7;
                        int last = line.indexOf('(')-1;
                        txt = line.substring(first, last);
                        txt = txt.replace(",", "");
                        txt = txt.replace(" ", "");
                        sb.append("#split#");
                        sb.append(txt);
                        
	        		}
	        	}
	        	
	        	if((line.contains("OW"))&&(line.contains("USA")))//check if line has opening weekend
	        	{
	        		if(countregex("(#split#)", sb.toString())==2)//check if line has gross collection already
	        		{
	        			 String txt;
                         int first = 7;
                         int last = line.indexOf('(')-1;
                         txt = line.substring(first, last);
                         txt = txt.replace(",", "");
                         txt = txt.replace(" ", "");
                         sb.append("#split#");
                         sb.append(txt);
                         System.out.println("OW = "+sb.toString());
	        		}
	        	}
	        }
	        
	        System.out.println("file copying done");
	        writer.close();
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
	}

	private static int countregex(String regex, String teststring) {
		int count = 0;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(teststring);
		while(matcher.find())
		{
			count++;
		}
		
		return count;
	}

	
	
}
