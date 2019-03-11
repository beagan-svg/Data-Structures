/************************************************************************
* Beagan Nguy
* Assignment 3
* CMPS 12B - 02
************************************************************************/
public class MyQueue implements QueueInterface {
	
	// Helper linked list class
	private class Node{
		private Object item;
		private Node next;
	}
	
	private Node top;    // begining of Queue
	private Node last;   // end of Queue
	public int size;    // number of elements of queue

	// Initializes an empty queue.
	public MyQueue(){
		top = null;
		last = null;
		size = 0;
	}

	public int size(){     // Return number of elements of queue
		return size;
	}

	public boolean isEmpty(){    // Return true if this queue is empty
		return top == null;      // Return true if top is empty
	}

	public void enqueue(Object item){ // Add item to queue
		Node oldLast = last;	// create & assign oldLast = last item before enqueue,
		last = new Node();      
		last.item = item;       //  last = item being enqueue
		last.next = null;		//  last.next is pointing to null, indicator of last queue

		if (isEmpty()){      // if queue is empty
			top = last;      // Top item is = last item
		} else {			 // if queue is not empty
			oldLast.next = last;   // point second to last item to last itme
		}
		size++;              // Increase size of queue
	}

	public Object dequeue(){
		if (isEmpty()){      // throws error if stack is empty
			throw new QueueException("Stack is Empty.");
		} else { 
			Object item = top.item;    // create an object item and assign it to top's item
			top = top.next;			   // assign top = second from top
			size--;					   // decrease number of items by 1

			if (isEmpty()){      // if queue is empty make last = nothing
				last = null;
			}
			return item;		 // return item
		}
	}

	public void dequeueAll(){	// result queue to 0
		top = null;             // make top = nothing
		last = null;            // make last = nothing
		size = 0;               // make size = 0
	}

	public Object peek(){     // Looks at top item
		if (isEmpty()){       // throws error if stack is empty
			throw new QueueException("Stack is Empty.");
		} else {
		return top.item;      // return top's item
		}
	}

	public String toString() {
		String s = "(";
		Node current = top;
		while (current != null) {
			s = s + current.item;
			if (current.next != null)
				s = s + ", ";
			current = current.next;
		}
		s = s + ")";
		return s; 	
	}

} 