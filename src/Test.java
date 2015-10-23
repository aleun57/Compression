
public class Test {

	
	
	
	
	
	 public static void main(String[] args) {
	        
		 String key = "";
		 int result = 0;
			int counter = 0;
			
			//Application of Horner's Rule. Going through the key character by character, taking the int of the char
			//and multiplying that by a power of 41 which is determined by the position of char in string.
			//Then take that number mod the size of the dictionary to get appropriate position in dictionary.
			while (key.length() > counter) {
				char Char = key.charAt(counter);
				int IntermediateValue = (int)((int)Char * Math.pow(41, (double)counter));
				result = result + (IntermediateValue % 4099);
				counter += 1;
			}
			
			System.out.println (result % 4099);
		 
	    }
}
