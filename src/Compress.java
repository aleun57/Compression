import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Compress {
	
	public static void main(String[] args) throws IOException {
		
		//Initialize variables to read the Input File and output to a File
		BufferedInputStream in;
		in = new BufferedInputStream(new FileInputStream(args[0]));
		
		FileOutputStream outfile = new FileOutputStream("Asgn2Output3.zzz");		
		BufferedOutputStream out = new BufferedOutputStream(outfile);		
		MyOutput MyOutput = new MyOutput();
		
		//Initializes a dictionary with a size of 4099 "cells"
		//4099 is the smallest prime number that is larger than 4096
		Dictionary dict = new Dictionary(4099);
		
		//Starting the dictionary with the initial 256 ASCII entries
		int InitCount = 0;
		while (InitCount <= 255) {
			String Character = String.valueOf((char)InitCount);
			DictEntry NewEntry = new DictEntry(Character, InitCount);
			InitCount += 1;
			try{
				dict.insert(NewEntry);
				}
			catch (DictionaryException d) {
				System.out.println("Dictionary Entry already exists");
			}
		}
				
		//Now starting to perform the algorithm for the file		
		int CharAsInt;
		String EntrySeeker = "";
		
		//Read method will yield -1 once the end of the file has been reached
		while ((CharAsInt = in.read()) != -1) {
			String Character = String.valueOf((char)CharAsInt);
			
			//Concatenating the character that has been read to a String that looks for 
			//future dictionary entries to insert
			EntrySeeker = EntrySeeker + Character;
			
			//If the find method yields null value, we cannot find the EntrySeeker String in dictionary
			//and need to insert EntrySeeker into dictionary and put into the output file the code
			//for the String EntrySeeker without its last character because we know that string is in the dictionary.
			if (dict.find(EntrySeeker) == null) {
				String StringOutByte = EntrySeeker.substring(0,EntrySeeker.length()-1);
				int OutByte = (dict.find(StringOutByte)).getCode();
				DictEntry NewPair = new DictEntry(EntrySeeker, dict.numElements());
				try {
					dict.insert(NewPair);
				} catch (DictionaryException e) {
					System.out.println("Dictionary Entry already exists");
				}
				try {
					MyOutput.output(OutByte,out);
				} catch (IOException e) {
					e.printStackTrace();
				}
				//The EntrySeeker String will just be the last character it "scanned"
				EntrySeeker = Character;
				
				//End of If Statement  
			}			
		//If we can find the current String in EntrySeeker in dictionary, need to read 
		//next byte (go through the loop again) to get the "longest" string possible that isn't in the string.
		//End of While Loop			
		}
		
		//Once the read method returns -1 we have to add to the output file the last byte of output
		int OutByte = (dict.find(EntrySeeker)).getCode();	
		try {
			MyOutput.output(OutByte,out);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		//Flushes the last bits of output 
		MyOutput.flush(out);
		
		//Close Streams & File
		in.close();
		out.close();
		outfile.close();
	}	
}
