//DictEntry Class
public class DictEntry {
		
	private String mKey;
	private int mCode;
	
		//Constructor for an instance of the DictEntry Class
		public DictEntry(String key, int code) {
			mKey = key;
			mCode = code;
		}
		
		//Getter methods
		public String getKey() {
			return mKey;
		}
		
		public int getCode() {
			return mCode;
		}
}
