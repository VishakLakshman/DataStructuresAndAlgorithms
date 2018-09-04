
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Decoder {
	
	/*
	 * @author: Vishak Lakshman Sanjeevikani Murugesh
	 * Id: 800985356
	 * email: vsanjeev@uncc.edu
	 */
	
	private int MAX_TABLE_SIZE;
	private String STRING;
	private String CODE;
	private Integer tableLength=256;
	public String TABLE[][];
	
	/*
	 * Initialise the DECODER object
	 */
	public Decoder(Integer bitlength)
	{
		MAX_TABLE_SIZE=pow2(bitlength);
		STRING="";
		CODE="";
		TABLE=new String[MAX_TABLE_SIZE][2];
		for(int i=0;i<MAX_TABLE_SIZE;i++)
		{
			TABLE[i][1]=null;
			TABLE[i][0]=null;
		}
		initialiseTable();
	}
	
	/*
	 * Calculate the exponential of 2
	 */
	private int pow2(int e)
	{
		int result=1;
		for(int i=1;i<=e;i++)
			result*=2;
		return result;
	}
	
	/*
	 * Initialise the data TABLE for all characters from 0-255
	 */
	private void initialiseTable()
	{
		Character character=0;
		Integer code=0;
		for(int i=0;i<tableLength;i++,character++,code++)
		{
			TABLE[i][1]=character.toString();
			TABLE[i][0]=code.toString();
		}
		
	}
	
	/*
	 * checks if a CODE is present in the data TABLE
	 * returns true if present
	 */

	public boolean checkCode(String code)
	{
		if(Integer.parseInt(code) < tableLength)
				return true;
		return false;
	}
	
	/*
	 * Returns the SYMBOL for a particular CODE
	 */
	public String getString(String code)
	{
	   return TABLE[Integer.parseInt(code)][1];
	}
	
	/*
	 * Adds a new SYMBOL to data TABLE
	 */
	public void addString(String symbolstring)
	{
	  TABLE[tableLength][1]=symbolstring;
	  TABLE[tableLength][0]=tableLength.toString();
	  tableLength++;
	}
	
	/*
	 * Converts the 16-bit Binary into Integer 
	 */
	public String convertToDecimal(String input)
	{
		Integer out=Integer.parseInt(input,2);
		return out.toString();
	}
	
	/*
	 * Decodes the input LZW file into TXT output file
	 */
	public void decode(String outputfile)
	{
		File file=new File(outputfile);
		PrintWriter filewriter=null;
		Scanner filereader=null;
		ArrayList<String> input=new ArrayList<String>();
		String NEW_STRING="";
		String temp="";
		try
		{
		  filereader= new Scanner(new FileReader(file));
		  filereader.useDelimiter(" ");
		  filewriter=new PrintWriter("C:/input_decoded.txt");/* You can change the output destination here*/
		  while(filereader.hasNext())
		  {
			temp=filereader.next().toString();
			if(filereader.hasNext())
			  input.add(convertToDecimal(temp.concat(filereader.next().toString())));
		  }
		   CODE=input.get(0);
		   STRING=getString(CODE);
		   filewriter.append(STRING);
		   for(int i=1;i<input.size();i++)
		   {
			 CODE=input.get(i);
			 if(!checkCode(CODE))
			    NEW_STRING=STRING.concat(STRING.substring(0,1));
			 else
			    NEW_STRING=getString(CODE);
			 filewriter.append(NEW_STRING);
			 if(tableLength < MAX_TABLE_SIZE)
			   addString(STRING.concat(NEW_STRING.substring(0,1)));
			 STRING=NEW_STRING;
		   }
		   filewriter.close();
		}
		catch(FileNotFoundException e) 
		{
			System.out.println("File not found");
		}
	}
		
	public static void main(String args[])
    {
    	Decoder decoder=new Decoder(Integer.parseInt(args[1]));
    	decoder.decode(args[0]);
    }

}
