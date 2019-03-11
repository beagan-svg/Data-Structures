/**************************************************************************
* SearchAndSort.java
* Beagan Nguy
* bnguy
* Assignment 1
* Sort a given text and find the amount of times a given word has appeared
***************************************************************************/
import java.io.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class SearchAndSort {

	// Utility function: split a string into words, 
	// making them all lowercase and ignoring all non-text characters
	public static String[] splitIntoWords(String str) {
		// Handle apostrophes: "lula's", "'top o' the mornin''"
		// Ignore any non-alphabetical characters ("1942", "1920s")
		str = str.toLowerCase();
		str = str.replaceAll("'","").replaceAll("\\s+", " ").replaceAll("[^a-zA-Z ]", " ");

		// Split on any amount of spaces
		String[] words = str.split("\\s+");
		return words;
	}

	// Load all of the words in this filename
	public static String[] createWordList(String filename) {
		try {
			String text = new String(Files.readAllBytes(Paths.get(filename)));
			return splitIntoWords(text);

		} catch (IOException ex){
			ex.printStackTrace();
		}
		return new String[0];
	}

	static int countWordsInUnsorted(String[] Array, String findword) {
		int count = 0; 
		for(String word : Array){
			if(word.equals(findword)) {
				count++;				
			}
		}
		return count;
	}

	public static void mergeSort(String[] arrayToSort, String[] tempArray, int first, int last) {
	// int first = 0
	// int last = arrayToSort.length - 1;
		
		if (first >= last) {
			return;
		}
		
		int middle = (first + last) / 2;
		
		
		mergeSort(arrayToSort, tempArray, first, middle);      // Sort left half
		mergeSort(arrayToSort, tempArray, middle + 1, last);   // Sort right half
		mergeHalves(arrayToSort, tempArray, first, last);	   // Merge Together
	}
	
	public static void mergeHalves(String[] arrayToSort, String[] tempArray, int first, int last) {
		
		int firstEnd = (last + first) / 2;		// middle 
		int lastStart = firstEnd + 1;		    // middle + 1
		int size = last - first + 1;            //total elements
		
		int left = first;			// left = first index of the left half of arrayToSort
		int right = lastStart;		// right = first index of the right half of arrayToSort
		int index = first;			// index = first index of the left half of arrayToSort
	
		while(left <= firstEnd && right <= last) {
			if (arrayToSort[left].compareToIgnoreCase(arrayToSort[right]) <= 0) {
				tempArray[index] = arrayToSort[left];  // copy into tempArray starting from left half of arrayToSort
				left++;								   // copy into tempArray starting from right half of arrayToSort
			} else {
				tempArray[index] = arrayToSort[right];
				right++;
			}	
				index++;			
		}
		System.arraycopy(arrayToSort, left, tempArray, index, firstEnd - left + 1);  // (array to be copied from, starting index of where to copy,
		System.arraycopy(arrayToSort, right, tempArray, index, last - right + 1);	 // array to copy in, start index of destination array, total # being copied)
		System.arraycopy(tempArray, first, arrayToSort, first, size);
	}

	public static int binarySearch(String[] sortedWords, String query, int startIndex, int endIndex) {
			
		int middle;
		if (startIndex > endIndex){
			return -1;
		} else {    

			middle = (startIndex + endIndex)/2;		
			
			if(sortedWords[middle].equals(query)){ 
				return middle;          
			} else if( sortedWords[middle].compareToIgnoreCase(query) < 0 ){
	            return binarySearch(sortedWords, query, middle + 1, endIndex); 
			} else { 
			    return binarySearch(sortedWords, query, startIndex, middle - 1);  
			} 
	    }
	}

	 // 1. Loops until binarySearch = -1  2. makes sure binarySearch does not equal 1 because [-1] doesnt exist 3. checks to see if next binary search = query
	public static int getSmallestIndex(String[] words, String query, int startIndex, int endIndex) {
		int i = binarySearch(words, query, startIndex, endIndex);	
		int t = binarySearch(words, query, 0, i-1);
		if (i == -1) {
			return -1;
		} else if (t == -1){
			return i;
		} else { 
			if (words[t].equals(query)) {
				i = getSmallestIndex(words, query, 0, i-1);
			}
			return i;
		}
	}
	
	public static int getLargestIndex(String[] words, String query, int startIndex, int endIndex) {
		int j = binarySearch(words, query, startIndex, endIndex);
		int t = binarySearch(words, query, j+1, (words.length)-1);
		if (j == -1) {
			return -1;
		} else if (t == -1){
			return j;
		} else {
			if(words[t].equals(query)) {
				j = getLargestIndex(words, query, j+1, (words.length)-1);
			}
			return j;
		}
	}

	public static void main(String []args) {
		
		// Create a word list from Frankenstein
		String[] allWords = createWordList("frankenstein.txt");

		// Save the arguments
		String[] queryWords = {"doctor", "frankenstein", "the", "monster", "igor", "student", "college", "lightning", "electricity", "blood", "soul"};
		int timingCount = 100;

		if (args.length > 0) {
			// There is an argument, so some different words to search for and count were passed in.
			queryWords = args[0].split(",");
		}			

		
		System.out.println("\nSEARCH AND SORT");
		System.out.println("\nSearching and counting the words " + String.join(",", queryWords));		
		
		System.out.println("\nNAIVE SEARCH:");

		
		// Record the current time
		long t0 = (new Date()).getTime();

		// Time how long it takes to run timingCount loops
		//   for countWordsInUnsorted 
		for (int j = 0; j < timingCount; j++) { 
			
			// Search for and count the words timingCount times in order to get an average time
			for (int i = 0; i < queryWords.length; i++) {

				int count = countWordsInUnsorted(allWords, queryWords[i]); // Replace the 0 in this line of code with the call to countWordsInUnsorted once you've written it

				// For the first time the words are counted, print out the value
				if (j == 0)
					System.out.println(queryWords[i] + ":" + count);
				
			}
		}

		// Record the current time
		long t1 = (new Date()).getTime();

		long timeToSeachNaive = t1 - t0;
		int searchCount = timingCount*queryWords.length;

		// Output how long the searches took, for how many searches 
		System.out.printf("%d ms for %d searches, %f ms per search\n", timeToSeachNaive, searchCount, timeToSeachNaive*1.0f/searchCount);

		// Sort the list of words
		System.out.println("\nSORTING: ");


		// Put your call to mergeSort here to sort allWords.
		String[] tempArray = new String[allWords.length];
		
		mergeSort(allWords, tempArray, 0, (allWords.length)-1);
		

		// Record the current time
		long t2 = (new Date()).getTime();

		// Output how long the sorting took
		long timeToSort = t2 - t1;
		System.out.printf("%d ms to sort %d words\n", timeToSort, allWords.length);

		// Output every 1000th word of your sorted wordlist
		int step = (int)(allWords.length*.00663 + 1);
		System.out.print("\nSORTED (every " + step + " word): ");
		for (int i = 0; i < allWords.length; i++) {
			if (i%step == 0)
				System.out.print(allWords[i] + " ");
		}
		System.out.println("\n");


		System.out.println("BINARY SEARCH:");

		// Run timingCount loops for countWordsInSorted 
		// for the first loop, output the count for each word

		for (int j = 0; j < timingCount; j++) {
			for (int i = 0; i < queryWords.length; i++) {

                int count = 0;
				int a = getSmallestIndex(allWords, queryWords[i], 0, (allWords.length)-1);
				int b = getLargestIndex(allWords, queryWords[i], 0, (allWords.length)-1); 
				
				
					if ((a==b) && (b!=-1)) {  //checks for one word found
						count = 1;
					} else if (a == -1) { // checks for 0 word found
						count = 0;
					} else {    // checks counts for word
						count = (b-a) + 1; 
					}

				// For the first one, print out the value
				if (j == 0)
					System.out.println(queryWords[i] + ":" + count);
				}
		}

		// Output how long the searches took, for how many searches 
		// (remember: searchCount = timingcount * the number of words searched. This is computed above.)

		// Record the current time
		long t3 = (new Date()).getTime();

		long timeToSeachBinary = t3 - t2;
		System.out.printf("%d ms for %d searches, %f ms per search\n", timeToSeachBinary, searchCount, timeToSeachBinary*1.0f/searchCount);
	}
}
