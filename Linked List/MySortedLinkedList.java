public class MySortedLinkedList extends MyLinkedList {

	/* TODO 
	   define the method
	   public void add(Comparable c)
	   that, given a Comparable (an interface type for all Object subclasses that define a compareTo() method), adds it to the 
	   list in sorted order.
	*/

	  	public class Node{
	  		Comparable c;
	  		Node next;

	  		public Node (Comparable i){
	  			c = i;
	  			next = null;
	  		}
	  	}

	  	public MySortedLinkedList(){
	  		head = null;
	  	}

	  	private Node head;	  	
	   	
	   	public void add(Comparable c){
	   		Node newNode = new Node(c);
			if (head == null){         // Checks to see if list is empty
	   			head = newNode;
	   			return;
	   	
	   		} else if (c.compareTo(head.c) < 0){   // If something is in the list check to see if it belongs in front
	   			newNode.next = head;
	   			head = newNode;
	   	
	   		} else {                         // if not
	   			Node after = head.next;
	   			Node prev = head;
	   			
	   			while (after != null){
	   				if (c.compareTo(after.c) < 0){
	   					break;
	   				}
	   				prev = after;
	   				after = after.next;
	   			}
	   		
	   			newNode.next = prev.next;
	   			prev.next = newNode;
	   		}
		}
		
		public String toString(){
			StringBuilder s = new StringBuilder(100);
			Node n = head;
			while (n != null){
				s.append(n.c.toString());
				n = n.next;
			}
			return s.toString();
		}
	
	/* TODO
	   override the method
	   void add(int index, Object o)
	   so that it throws an UnsupportedOperationException with the message "Do not call add(int, Object) on MySortedLinkedList".
	   Directly adding objects at an index would mess up the sorted order.
	*/
	  	public void add(int index, Object o){
	   	  throw new UnsupportedOperationException("Do not call add(int, Object) on MySortedLinkedList");
	  }
}





















