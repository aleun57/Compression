import java.util.LinkedList;


public class Dictionary implements DictionaryADT{
	
	//Cannot have more than 4096 Dictionary Entries because of the specified program requirements
	private final static int DictionaryLimit = 4096; 
	private int EntryCount = 0;
	
	private LinkedList<DictEntry>[] LinkedListArray; 
	
	//The Hash function that computes the appropriate position in the dictionary according to the key
	private int HashFunction(String key) {
		int result = 0;
		int counter = 0;
		
		//Application of Horner's Rule. Going through the key character by character, taking the int of the char
		//and multiplying that by a power of 41 which is determined by the position of char in string.
		//Then take that number mod the size of the dictionary to get appropriate position in dictionary.
		while (key.length() > counter) {
			char Char = key.charAt(counter);
			int IntermediateValue = (int)((int)Char * Math.pow(41, (double)counter));
			result = result + (IntermediateValue % LinkedListArray.length);
			counter += 1;
		}
		
		return (result % LinkedListArray.length);
		
	}
	
	//Returns an empty dictionary of the specified size   
	public Dictionary(int size){
		LinkedListArray = new LinkedList[size];
		int counter = 0;
		while (counter < size) {
				LinkedListArray[counter] = new LinkedList<DictEntry>(); 
				counter += 1; 
		}
	}

	//Returns an integer based on how successful the insertion of the DictEntry is
	public int insert (DictEntry pair) throws DictionaryException{
		
		int HashFuncValue = HashFunction(pair.getKey());
				
		//If the number of Entries is or larger than the limit then just return -1 and don't add any new entry.
		if (EntryCount >= DictionaryLimit) {
			return -1;
		}
		
		//If in the LinkedListArray position that corresponds to the DictEntry is empty, 
		//insert Entry into the position and return 0
		else if (LinkedListArray[HashFuncValue].isEmpty() == true) {
			LinkedListArray[HashFuncValue].add(pair);
			EntryCount += 1;
			return 0;
		}
		
		//If the LinkedList at the position in LinkedListArray isn't empty, then there has to be entries in the LinkedList
		//so traverse the entire LinkedList to see if the entry is already there
		else {
			int counter = 0;
			while (counter < LinkedListArray[HashFuncValue].size()) {
				if (pair.getKey().equals(LinkedListArray[HashFuncValue].get(counter).getKey())) {
					throw new DictionaryException("Entry is already in dictionary");
				}
				counter += 1;
			}
			//At this point the LinkedList has been entirely searched and this is a unique entry so add it 
			//to the end of the LinkedList
			LinkedListArray[HashFuncValue].add(pair);
			EntryCount += 1;
			return 1;
		}
	}

	//Removes an entry
    public void remove (String key) throws DictionaryException {
    	
    	int position = HashFunction(key);
    	int counter = 0;
    	boolean remove = false;
    	
    	//If LinkedList at the position is empty then no entry is there
    	if (LinkedListArray[position].isEmpty() == true) {
    		throw new DictionaryException("The Entry was not removed because it was not found");
    	}
    	
    	//Goes through the LinkedList at the position specified to find the matching entry
    	else {
	    	while (counter < LinkedListArray[position].size()) {
	    		if ((LinkedListArray[position].get(counter)).getKey().equals(key)) {
	    			LinkedListArray[position].remove(counter);
	    			EntryCount -= 1;
	    			remove = true;
	    		}
	    		counter += 1;
	    	}
	    	
	    	//If going through the entire LinkedList and nothing was removed, throw an exception 
	    	if (remove == false) {
	    		throw new DictionaryException("The Entry was not removed because it was not found");
	    	}
    	}
    }
    
    //Checks to see if a entry is already in the dictionary that pertains to the specified key
    public DictEntry find (String key) {    	
    	
    	int position = HashFunction(key);
    	int counter = 0; 
    	
    	//If the LinkedListArray position that corresponds to the key is empty, 
    	//the entry that pertains to the key is not in the dictionary
    	if (LinkedListArray[position].isEmpty() == true) {
    		return null;
    	}
    	
    	//If the LinkedListArray position has a LinkedList, go through the LinkedList to find an entry's key
    	//that contains a matching String key
    	while (counter < LinkedListArray[position].size()) {
    		DictEntry EntryInQuestion = LinkedListArray[position].get(counter);
    		if (EntryInQuestion.getKey().equals(key)) {
    			return EntryInQuestion;    			
    		}
    		counter += 1;    		
    	}
    	
    	//At this point the LinkedList that was at the specified LinkedListArray position
    	//does not contain the key in question. Therefore return null
    	return null;
    }
    
    //Returns the number of Elements in the Dictionary
    public int numElements() {
    	return EntryCount;
    }
}
