/************************************************************************
* Beagan Nguy
* Assignment 2
* CMPS 12B - 02
************************************************************************/

public class MyLinkedList implements ListInterface {
	
	public class Node{
		Object data;
		Node next;		
		
		public Node (Object data){				// constructor
			this.data = data;
			this.next = null;
		}

		Node (Object item, Node next){  	    // constructor
			this.data = data;
			this.next = next;
		}
	}

	private Node head;
	private int numItems; 

	public MyLinkedList(){
		head = null;
		numItems = 0;
	}
	
	public boolean isEmpty() {
		if (numItems == 0) {
			return true;
		} else {
			return false;
		}		
	}
		
	// Returns the size of the list (number of items in the list)
	public int size() {
		return numItems;
	}
		
	// Adds an Object to the list at the specified index. 
	public void add(int index, Object value) {
	if (index < 0 || index > numItems){
		throw new ListIndexOutOfBoundsException("Index " + index + " is out of range");
		} 
		numItems++;
		Node syllable = new Node(value);
		if (head == null){   // case where list is empty
			head = syllable;
			return;
		} 					
		
		Node curr = head;
		Node prev = null;

		if (index != 0){
			for (int i=0; i<index; i++){ 
				prev = curr;
				curr = curr.next;  
			}
			
			prev.next = syllable;
			syllable.next = curr;
		
		} else {                   // when index = 0
			head = syllable;
			syllable.next = curr;

		}
	}	

	
	// Removes an item from the list at the specified index. 
	public void remove(int index){
		if (index >= 0 && index <= numItems){
			if (index == 0){
				head = head.next;
			} else {
				Node current = head;
				for (int i=0; i<index-1; i++){
					current = current.next;
				}
				current.next = current.next.next;
			}
			numItems--;
		System.out.println("numItems = " + numItems);
		} else {
			throw new ListIndexOutOfBoundsException("Index " + index + " is out of range");
		}
	}
	
	// Removes all the items from the list. 
	public void removeAll(){
		head = null;
		numItems = 0;
	}

	// Returns the Object stored in the list at the specified index. 
	public Object get(int index){
		if(index >= 0 && index < numItems){
			Node current = head;
			int j = 0;
			while(j < index){
				current = current.next;
				j++;
			}
			return current.data;
		
			} else {
				throw new ListIndexOutOfBoundsException("Index " + index + " is out of bounds in get()");
			}
		}

	// Returns the index at which an Object is stored in the list, -1 if it's not in the list.
	public int find(Object o){
		Node N = head;
		int count = 0;
		
		while (N != null){
			if (N.data.equals(o)){
				return count;
			}
			else { 
				N = N.next;
				count++;
			}
		}
		return -1;
	}
}	
