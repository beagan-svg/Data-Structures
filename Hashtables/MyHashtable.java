/*********************************************************************************
Beagan Nguy
CMPS 12B - 02
Assignment 4
*********************************************************************************/
class MyHashtable implements DictionaryInterface {

	protected int tableSize;	     // the size of the array
	protected int size;		 		 // the number of key/value stored in Hashtable
	protected MyLinkedList[] table;  // an array of MyLinkedList	

	// Initialize tableSize, table, and size
	MyHashtable (int tempTableSize){
		tableSize = tempTableSize;
		table = new MyLinkedList[tableSize];
		size = 0;
	}

	// Creates Entry Class
	class Entry{
		String key;
		Object value;

		Entry(String tempkey, Object o){
			key = tempkey;
			value = o;
		}
	}

	// returns true if the Dictionary is empty, false otherwise.
	public boolean isEmpty(){ 
		if (size == 0)
			return true;
		else
			return false; 
	}

	// Returns the number of key/value pairs stored in the dictionary.
	public int size(){ 
		return size;
	}

	// Adds a value stored under the given key. If the key has already been stored in the Dictionary, 
	// replaces the value associated with the key and returns the old value. If the key isn't in the dictionary
	// returns null. 
	public Object put(String key, Object value){
		int code = Math.abs(key.hashCode()) % tableSize;		// creates hashcodes 
		// System.out.println(code);  
		Object x = null;
		Entry toAdd = new Entry(key, value);
		MyLinkedList spot = table[code];		// ?

		// If the location in the table is null
		
		if (spot == null){
			// System.out.println("The table is null " + size);
			// table[code] = new MyLinkedList();
			// table[code].add(0, toAdd);
			spot = new MyLinkedList();
			spot.add(0, toAdd);
			table[code] = spot;
			size++;
			return x;
		
		} else {

			for(int i = 0; i < spot.size(); i++){	
				
				Entry y = (Entry)spot.get(i);

				// If the bucket is NOT empty
				if (y.key.equals(key)){
					// System.out.println("The bucket is NOT empty");	
					// x = spot.get(i);
					spot.remove(i);
					spot.add(0, toAdd);
					size++;
					return (y.value);
				
				} else {
			
				// if bucket is empty	
					// System.out.println("The bucket is empty " + size);	
					spot.add(0, toAdd);
					table[code] = spot;
					size++;
					return x;
				}		
			}
		}
		return null;
	}

	// Retrieves the value stored with the key. 
	public Object get(String key){ 
		int code = Math.abs(key.hashCode()) % tableSize;
		MyLinkedList spot = table[code];
        
        if (spot == null){ 
        	return null;
        }
 		
		for (int i = 0; i < spot.size(); i++){
			Entry x = (Entry)spot.get(i);
 
			if ((x.key).equals(key)){
				// Object x = spot.get(i).value;
				// spot.remove(i);
				// table[code] = spot;
				// size --;
				// return spot.get(i).value;
				Entry temp = (Entry)spot.get(i);
				return temp.value;
			}
		}
		return null;
	}

	public void remove(String key){ // Deletes the key/value pair stored with the given key.
		int code = Math.abs(key.hashCode()) % tableSize;
		MyLinkedList spot = table[code];

		if (spot == null){
			// do nothing
		} else {
			for (int i = 0; i < spot.size(); i++){
				Entry x = (Entry)spot.get(i);
				if (x.key.equals(key)){
					spot.remove(i);
					size--;
				}
			}
		}
	}	
	// What if that is the last key/value in the bucket do we tablesize--?

	// Empties the dictionary.
	public void clear(){
		tableSize = 0;
		table = new MyLinkedList[tableSize];
		size = 0;
	}

	// Returns an array of all the keys currently stored in the Dictionary.
	public String[] getKeys(){
		
		String[] tempKeys = new String[size];
		int count = 0;

		// iterates through the LinkedList array
		for (int i=0; i < tableSize; i++){
			
			//  If the bucket at that index is not null, 
			//	Iterate through it and return all the keys
			if (table[i] != null){

				for (int j=0; j < table[i].size(); j++){
					Entry x = (Entry)table[i].get(j);
					tempKeys[count] = x.key;
					count++;
				}
			}
		}
		return tempKeys; 
	}

	// Returns the size of the biggest bucket (most collisions) in the hashtable. 
	public int biggestBucket() {
		int biggestBucket = 0; 
		for(int i = 0; i < table.length; i++) {
			// Loop through the table looking for non-null locations. 
			if (table[i] != null) {
				// If you find a non-null location, compare the bucket size against the largest
				// bucket size found so far. If the current bucket size is bigger, set biggestBucket
				// to this new size. 
				MyLinkedList bucket = table[i];
				if (biggestBucket < bucket.size())
					biggestBucket = bucket.size();
			}
		}
		return biggestBucket; // Return the size of the biggest bucket found. 
	}

	// Returns the average bucket length. Gives a sense of how many collisions are happening overall.
	public float averageBucket() {
		float bucketCount = 0; // Number of buckets (non-null table locations)
		float bucketSizeSum = 0; // Sum of the size of all buckets
		for(int i = 0; i < table.length; i++) {
			// Loop through the table 
			if (table[i] != null) {
				// For a non-null location, increment the bucketCount and add to the bucketSizeSum
				MyLinkedList bucket = table[i];
				bucketSizeSum += bucket.size();
				bucketCount++;
			}
		}

		// Divide bucketSizeSum by the number of buckets to get an average bucket length. 
		return bucketSizeSum/bucketCount; 
	}

	public String toString() {
		String s = "";
		for(int tableIndex = 0; tableIndex < tableSize; tableIndex++) {
			if (table[tableIndex] != null) {
				MyLinkedList bucket = table[tableIndex];
				for(int listIndex = 0; listIndex < bucket.size(); listIndex++) {
					Entry e = (Entry)bucket.get(listIndex);
					s = s + "key: " + e.key + ", value: " + e.value + "\n";
				}
			}
		}
		return s; 
	}
}
