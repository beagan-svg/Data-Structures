/************************************************************************
* Beagan Nguy
* Assignment 2
* CMPS 12B - 02
************************************************************************/
import java.io.*;
import java.util.*;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RhymingDict { 	
  

	// Given a pronunciation, get the rhyme group
	// get the more *heavily emphasized vowel* and follwing syllables
	// For "tomato", this is "-ato", and not "-omato", or "-o"
	// Tomato shares a rhyming group with "potato", but not "grow"
	private static String getRhymeGroup(String line) {

		int firstSpace = line.indexOf(" "); 

		String pronunciation = line.substring(firstSpace + 1, line.length());

		int stress0 = pronunciation.indexOf("0");
		int stress1 = pronunciation.indexOf("1");
		int stress2 = pronunciation.indexOf("2");

		if (stress2 >= 0)
			return pronunciation.substring(stress2 - 2, pronunciation.length());
		if (stress1 >= 0)
			return pronunciation.substring(stress1 - 2, pronunciation.length());
		if (stress0 >= 0)
			return pronunciation.substring(stress0 - 2, pronunciation.length());
		
		// No vowels at all? ("hmmm", "mmm", "shh")
		return pronunciation;
	}

	private static String getWord(String line) {
		int firstSpace = line.indexOf(" ");

		String word = line.substring(0, firstSpace);

		return word; 
	}

	// Load the dictionary
	private static String[] loadDictionary() {
		// Load the file and read it

		String[] lines = null; // Array we'll return holding all the lines of the dictionary
		
		try {
			String path = "cmudict/cmudict-short.dict";
			// Creating an array of strings, one for each line in the file
			lines = new String(Files.readAllBytes(Paths.get(path))).split("\\r?\\n");
			
		}
		catch (IOException ex){
			ex.printStackTrace();
		}

		return lines; 
	}

	
	public static void main(String []args) {

		String[] dictionaryLines = loadDictionary(); // store each line of dic into to an array{i}

		/*MyLinkedList dictionarylist = new MyLinkedList();

		for (int i=0; i<dictionaryLines.length; i++){
			dictionarylist.add(i, dictionaryLines[i]);
			System.out.println("index " + i + "= " + dictionarylist.get(i));
		}
				*/
		// List of rhyme groups. The items in this linked list will be RhymeGroupWords. 

		/* TODO: Add in your code to load the dictionary into your linked lists. Remember that rhymeGroups is a 
		   list of RhymeGroupWords. Inside each of this objects is another linked list which is a list of words within the same
		   rhyme group. I would recommend first getting this working with MyLinkedList for both lists (rhyme groups and 
		   word lists) then get it working using MySortedLinkedList for the word groups. */

		ListInterface rhymeGroups = new MyLinkedList(); 
			
		int rGIndex = 0; 

		for (int i=0; i<dictionaryLines.length-1; i++){     // Loop through the lines in dictionary Lines
			
			String rg = (getRhymeGroup(dictionaryLines[i]));
			
			if (i == 0){								    // First Rhyme Group
				rhymeGroups.add(0, getRhymeGroup(dictionaryLines[0]));
			
			} else if (rhymeGroups.find(rg) == -1){    // If Object(Rhyme Group) is not Found 
				rGIndex++;
				rhymeGroups.add(rGIndex, rg);
			}
		}
		
		for (int i=0; i<rhymeGroups.size(); i++){       // Find same rhyme group words
			
			MySortedLinkedList RhymeGroupWords = new MySortedLinkedList();

			for (int a=0; a<dictionaryLines.length; a++){  
				String rgs = getRhymeGroup(dictionaryLines[a]);
				
				if (rhymeGroups.get(i).toString().equals(rgs)){   
					RhymeGroupWords.add(getWord(dictionaryLines[a]));
				}
			}
		}

		/* End TODO for adding dictionary in rhymeGroups. */

		// This code prints out the rhyme groups that have been loaded above. 
		for(int i =0; i < rhymeGroups.size(); i++) {
			RhymeGroupWords rg = (RhymeGroupWords) rhymeGroups.get(i);
			System.out.print(rg.getRhymeGroup() + ": ");
			System.out.println(rg.getWordList());
		} 

		/* TODO: Add the code here to iterate through pairs of arguments, testing to see if they are in the same rhyme group or not.
		*/
	}
}