/************************************************************************
* Beagan Nguy
* Assignment 3
* CMPS 12B - 02
************************************************************************/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException; 
import java.util.Arrays;
import java.util.ArrayList;

public class Palindrome {

	static WordDictionary dictionary = new WordDictionary(); 

	
	// Get all words that can be formed starting at this index
	public static String[] getWords(String text, int index) {
		ArrayList<String> words = new ArrayList<String>();
		for (int i = 0; i <= text.length() - index; i++) {
			String maybeWord = text.substring(index, index + i);
			if (dictionary.isWord(text.substring(index, index + i))) {
				words.add(maybeWord);
			}
		}

		return words.toArray(new String[0]);
	}

	public static String stackToReverseString(MyStack stack) {

		String tempString = "";		// Create an empty string

		String space = " ";		// Creates string space

		MyStack tempStack = new MyStack();	// Create new temp stack

		int numbE = stack.size;     // numbE = # of elements in stacks

		// System.out.println("# of elements in stack: " + numbE + ", Item: " + stack);
		// System.out.println();
		for (int i=0; i<numbE; i++){    // Pop everything from the original stack onto the new stack
			tempStack.push(stack.pop());
			// System.out.println("ERRR " + tempStack);
		}
		// System.out.println();
		// System.out.println("# of elements in tempStack: " + tempStack.size + ", Item: " + tempStack);
		// System.out.println();
		for (int i=0; i<numbE; i++){    // Pop everything from the new stack back onto the original stack and creates an array of popped items
			Object x = tempStack.pop(); // takes pop items and stores into Object x
			tempString += x.toString(); // Adds Object x to tempString
			tempString += space;
			stack.push(x);				// adds Object x to stack
		}

		// System.out.println("String: " + tempString);
		// System.out.println();		
		// System.out.println("DONE: # of elements in stack: " + stack.size + ", Item: " + stack);
		// System.out.println("DONE: # of elements in tempStack: " + tempStack.size + ", Item: " + tempStack);
		// System.out.println();
		
		return tempString;
	}

	public static String reverseStringAndRemoveNonAlpha(String text) {
		MyStack newStack = new MyStack();	// creates newStack 
		String s = text;

		// System.out.println("test " + text);

		for (int i=0; i<s.length(); i++){	// iterates through each character in text
			char c = s.charAt(i);
		
			if (Character.isAlphabetic(c)){  // if alphabetic push to newstack
				newStack.push(c);
			}
		}

		// System.out.println("newStack " + newStack);
		// System.out.println();

		int numbE = newStack.size;
		
		Object x = null;
		s = "";

		for (int i=0; i<numbE; i++){	// concatenate strings in newStack
			x = newStack.pop();
			// System.out.println("Being pop: " + x);
			// System.out.println();
			s +=  x.toString();
			// System.out.println(s);
			// System.out.println();
		}
		// System.out.println(s);	
		return s;
	}



	// Returns true if the text is a palindrome, false if not.
	public static boolean isPalindrome(String text) {

		String s = text.toLowerCase();		// makes text lower case
		MyStack newStack = new MyStack();	
		MyQueue newQueue = new MyQueue();

		for (int i=0; i<s.length(); i++){	// iterates through each character in text
			char c = s.charAt(i);

			if (Character.isAlphabetic(c)){		// if alphabetic push to newStack & newQueue
				newStack.push(c);
				newQueue.enqueue(c);
			}		
		}

		// System.out.println("newStack: " + newStack.toString());
		// System.out.println("newQueue: " + newQueue.toString());

		Object x;  // temp Stack pop()
		Object y;  // temp Queue dequeue()

		String a = "";
		String b = "";

		while (newStack.size != 0 || newQueue.size != 0){
			x = newStack.pop();
			a += x.toString();      // reverses order with newStack.push
			
			y = newQueue.dequeue();
			b += y.toString();      // does not reverse
		}
		
		// System.out.println(a);
		// System.out.println(b);

		if (a.compareTo(b) == 0){    // converts x and y to strings and see if they are equals
			// System.out.println("true");
			return true;
		}
		else { 
			// System.out.println("false");
			return false;
		}
	}

	public static void explorePalindrome(String text) {
		
		String lower = text.toLowerCase();  // lower cases the text and stores into lower
		
		String reverseTxt = reverseStringAndRemoveNonAlpha(lower);	// reverses text and removes everything exept letters

		MyStack newStack = new MyStack(); // creates newStack
		
		String otext = text;	
		
		int index = 0;
		
		decomposeText(otext, reverseTxt, index, newStack);

	}

	public static void decomposeText(String originalText, String textToDecompose, int index, MyStack decomposition){
		// System.out.println("original =" + originalText + "=");
		// System.out.println("textToDecompose =" + textToDecompose + "="); 
		// System.out.println("index =" + index + "=");
		// System.out.println("decomposition =" + decomposition + "=");

		// System.out.println("");

		// Prints out decomposition in reverse if index is at the end
		if(index == textToDecompose.length()){ 
           
            System.out.println(originalText + ": " + stackToReverseString(decomposition));

	    } else{
	    	
	    	String[] mainWord = getWords(textToDecompose, index);	// stores getWords in mainWord array
	      
	     	for(int i = 0; i<mainWord.length;i++){		// iterates through mainWord array
	      	
	      		String firstWord = mainWord[i];			// make firstWord equal to mainWord at given index
	      	
	      		int wordLength = firstWord.length();	// int that will increase index by getWords
	      
	      		decomposition.push(mainWord[i]);		// pushes mainWord into decomposition (myStack)

	        	decomposeText(originalText, textToDecompose, index + wordLength, decomposition);

	      		decomposition.pop();	//	pop the last word and try again

	      	} 
	    } 
	}

	// This function looks at the arguments that are passed 
	// and decides whether to test palindromes or expand them
	public static void main(String[] args) throws IOException {

		if (args.length == 0) {
			System.out.println("ERROR: Remember to set the mode with an argument: 'test' or 'expand'");
		} else {
			String mode = args[0];

			// Default palindromes to use if none are provided
			String[] testPalindromes = {"A", "ABBA", "oh no an oboe", "salami?", "I'm alas, a salami"};
			if (args.length > 1)
				testPalindromes = Arrays.copyOfRange(args, 1, args.length);

			// Test whether the provided strings are palindromes
			if (mode.equals("test")) {

				for (int i = 0; i < testPalindromes.length; i++) {
					String text = testPalindromes[i];
					boolean result = isPalindrome(text);
					System.out.println("'" + text + "': " + result);
				}

			} else if (mode.equals("expand")) {
				for (int i = 0; i < testPalindromes.length; i++) {
	
					explorePalindrome(testPalindromes[i]);
				}	
			}
			else {
				System.out.println("unknown mode: " + mode);
			}
		}
	}
}
