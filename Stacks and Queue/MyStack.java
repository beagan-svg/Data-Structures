/************************************************************************
* Beagan Nguy
* Assignment 3
* CMPS 12B - 02
************************************************************************/
public class MyStack implements StackInterface {

	private class Node{
		private Object item;
		private Node next;		
	}	

	private Node top; // top of stack
	public int size;  // size of stacks

	// initializes MyStack
	public MyStack(){
		top = null;
		size = 0;
	}

	// return size of stack
	public int size() {
		return size;
	}

	// return true if stack is empty
	public boolean isEmpty(){
		return top == null;
	}

	public void push(Object o){
		Node oldTop = top;	// set old top = top
		top = new Node();	 
		top.item = o;		// set top's item to o or the thing being pushed
		top.next = oldTop;	// set top's pointer to old top
		size++;
	}

	// removes top of stack
	public Object pop(){
		if (isEmpty()){
			throw new StackException("Stack is Empty.");
		} else {
			Object item = top.item;
			top = top.next;
			size--;
			return item;
		}
	}

	// returns top of stack's item
	public Object peek(){
		if (isEmpty()){
			throw new StackException("Stack is Empty.");
		}
		return top.item;
	}

	// remove all stacks
	public void popAll(){
		top = null;
		size = 0;
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