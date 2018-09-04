
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Encoder {
	
	/*
	 * @author: Vishak Lakshman Sanjeevikani Murugesh
	 * Id: 800985356
	 * email: vsanjeev@uncc.edu
	 */
	
	private int MAX_TABLE_SIZE;
	private String STRING;
	private String SYMBOL;
	private Integer tableLength=256;
	public String TABLE[][];
	
	/*
	 * Initialise the encoder object
	 */
	
	public Encoder(Integer bitlength)
	{
		MAX_TABLE_SIZE=pow2(bitlength);
		STRING="";
		SYMBOL="";
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
	 * checks if a symbol is present in the data TABLE
	 * returns true if present
	 */
	private boolean checkTable(String symbolstring)
	{
		for(int i=0;i<MAX_TABLE_SIZE;i++)
			if(symbolstring.equals(TABLE[i][1]))
				return true;
		return false;
	}
	
	/*
	 * Adds a new CODE to data TABLE
	 */
	private void addCode(String symbolstring)
	{
	  TABLE[tableLength][1]=symbolstring;
	  TABLE[tableLength][0]=tableLength.toString();
	  tableLength++;
	}
	
	/*
	 * Returns the CODE for a particular SYMBOL
	 */
	private String returnCode(String input)
	{
		for(int i=0;i<tableLength;i++)
		  if(input.equals(TABLE[i][1]))
			  return TABLE[i][0];
		return null;
	}
	
	/*
	 * Converts the Integer into 16-bit Binary
	 */
	private String convertToHex(String input)
	{
		int val;
		val=Integer.parseInt(input);
		return Integer.toBinaryString(0x10000 | val).substring(1);
	}
	
	/*
	 * Encodes the input TXT file into LZW output file
	 */
	public void encode(String inputfile)
	{
		File file=new File(inputfile);
		PrintWriter filewriter=null;
		Scanner filereader=null;
		String input;
		String temp=null;
		try
		{
			filereader= new Scanner(new FileReader(file));
			filewriter=new PrintWriter("C:/input_encoded.lzw");/* You can change the output destination here*/
			while(filereader.hasNext())
			{
				input=filereader.next().toString();
				for(int i=0;i<input.length();i++)
				{
				 SYMBOL=input.substring(i,i+1);
				 if(checkTable(STRING.concat(SYMBOL)))
				    STRING=STRING.concat(SYMBOL);
				 else
				 {
					 temp=convertToHex(returnCode(STRING));
					 filewriter.print(temp.substring(0,8)+" "+temp.substring(8,16)+" ");
					 if(tableLength < MAX_TABLE_SIZE)
						 addCode(STRING.concat(SYMBOL));
					 STRING=SYMBOL;
				 }
			 }
			 temp=convertToHex(returnCode(STRING));	
			 filewriter.print(temp.substring(0,8)+" "+temp.substring(8,16));
			}
			filewriter.close();
			
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
	}
	
	public static void main(String args[])
    {
    	Encoder encoder=new Encoder(Integer.parseInt(args[1]));
    	encoder.encode(args[0]);
    }

}
