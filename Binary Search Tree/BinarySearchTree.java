/************************************************************************
* Beagan Nguy
* Assignment 5
* CMPS 12B - 02
************************************************************************/
public class BinarySearchTree implements BSTInterface {

	private BSTNode root;
	
	public BinarySearchTree(){
		root = null;
	}
	// BSTNode Constructor
	class BSTNode{
		String item;
		BSTNode left;
		BSTNode right;

		BSTNode(String x){
			item = x;
			left = null;
			right = null;
		}
	}
	// Return true if BST is empty, false otherwise
	public boolean isEmpty(){ // returns true if the BST is empty, false otherwise
		return root == null;
	}

	// Clear BST so that it is empty
	public void makeEmpty(){ 
		root = null;
	}
	
	// Returns a queue with the elements in order
	public MyQueue inOrder(){
		MyQueue queue = new MyQueue();
		inOrderRecursive(root, queue);
		return queue;
	} 

	// Returns a queue with the elements pre order
	public MyQueue preOrder(){
		MyQueue queue = new MyQueue();
		preOrderRecursive(root, queue);
		return queue;
	} 

	// Returns a queue with the elements post order
	public MyQueue postOrder(){
		MyQueue queue = new MyQueue();
		postOrderRecursive(root, queue);
		return queue;
	} 

	// Returns true if the BST contains the string, otherwise returns false
	public boolean contains(String s){ 
		return (recursiveSearch(root, s));
	}
	
	// If the string is already in the BST, then put will do nothing. If the string is not in
	// the BST, then put will add the string to the BST.
	// Adds a String to the BST. If the String is already in the BST, does nothing. 
	public void put(String s){ 
		BSTNode item = recursiveInsert(root, s);
		root = item;
	}
	
	// Removes the specified string from the BST, if the string cannot be found, then
	// delete does nothing
	// Removes a String from the BST. If the String isn't in the BST, does nothing. 
	public void delete(String s){ 
		BSTNode item = recursiveRemove(root, s);
		root = item;
	}

	// Helper function searches the subtree rooted at node for the String s, 
	// returning true​ if it finds it, ​false​ otherwise.
	protected boolean recursiveSearch(BSTNode node, String s) {
		if (node == null){
			return false;
		}
		else if (node.item.equals(s)){
			return true;
		}
		else if (s.compareTo(node.item) < 0){
			return(recursiveSearch(node.left, s));
		} else {
			return(recursiveSearch(node.right, s));
		}
	}

	// Helper function that inserts node
	protected BSTNode recursiveInsert(BSTNode node, String s){
		
		// Return a new BSTNode when node is empty
		if (node == null){
			return new BSTNode(s);
		}

		// Looks for appropriate place to insert
		else {
			if (s.compareTo(node.item) < 0){
				node.left = recursiveInsert(node.left, s);
			}
			else {
				node.right = recursiveInsert(node.right, s);
			}
			return node;
		}
	}

	// Helper function that removes node
	protected BSTNode recursiveRemove(BSTNode node, String s){
		if (node != null){
			if (s.compareTo(node.item) < 0){
				node.left = recursiveRemove(node.left, s);
			}
			if (s.compareTo(node.item) > 0){
				node.right = recursiveRemove(node.right, s);
			}
			if (s.compareTo(node.item) == 0){
				node = deleteNode(node);
			}
		}
		return node;
	}
	
	// Delete node
	protected BSTNode deleteNode(BSTNode node) {
		// Case 1: no children
		if (node.left == null && node.right == null){
			node = null;
		}
		// Case 2: only right child exist
		else if (node.left != null && node.right == null){
			node = node.left;
		}
		// Case 3: only left child exist
		else if (node.right != null && node.left == null){
			node = node.right;
		}

		// Case 4: Both child exist
		else{
			String temp = getSmallest(node.right);
			node.item = temp;
			node.right = recursiveRemove(node.right, temp);
		}
		return node;
	}

	// Return smallest string in the subtree
	protected String getSmallest(BSTNode node) {
		String smallest = node.item;
		while (node.left != null){
			smallest = node.left.item;
			node = node.left;
		}
		return smallest;
	}

	// Return a queue of string in-order (alphabetical)
	protected void inOrderRecursive(BSTNode node, MyQueue queue) {
		if (node != null){
			inOrderRecursive(node.left, queue);
			queue.enqueue(node.item);
			inOrderRecursive(node.right, queue);
		}
	}

	// Return a queue of string pre-order (alphabetical)
	protected void preOrderRecursive(BSTNode node, MyQueue queue) {
		if (node != null){
			queue.enqueue(node.item);
			inOrderRecursive(node.left, queue);
			inOrderRecursive(node.right, queue);
		}
	}

	// Return a queue of sring post-order
	protected void postOrderRecursive(BSTNode node, MyQueue queue) {
		if (node != null){
			inOrderRecursive(node.left, queue);
			inOrderRecursive(node.right, queue);
			queue.enqueue(node.item);	
		}
	}

	// Prints out the tree structure, using indenting to show the different levels of the tree
	public void printTreeStructure() { 
		printTree(0, root);
	}

	// Recursive helper for printTreeStructure()
	protected void printTree(int depth, BSTNode node) {
		indent(depth);
		if (node != null) {
	    	System.out.println(node.item);
	    	printTree(depth + 1, node.left);
	    	printTree(depth + 1, node.right);
	 	} 
	 	else {
	  		System.out.println("null");
	  	}
	}

	// Indents with with spaces 
	protected void indent(int depth) {
		for(int i = 0; i < depth; i++)
			System.out.print("  "); // Indents two spaces for each unit of depth
	}

	protected BSTNode balanceRecursive(String[] array, int first, int last) {
		// Declare mid variable
		int mid;

		// If array is empty return null
		if (array == null || first > last){
			return null; 
		}

		else {
			// Finds mid
			mid = (first + last)/2;

			// Create new BSTNode 
			BSTNode temp = new BSTNode(array[mid]);

			// Recursively call balanceRecursive to find left child(similar to binarySearch)
			temp.left = balanceRecursive(array, first, mid-1); // Searches left half
			
			// Recursively call balanceRecursive to find right child(similar to binarySearch)
			temp.right = balanceRecursive(array, mid+1, last); // Searches right half

			// Return BSTNode
			return temp;
		}
	} 
	public void balanceBST(){
		// Set count
		int count = 0;

		// Create queue of inOrder
		MyQueue queue = inOrder();

		// Create new queue
		MyQueue temp = new MyQueue();

		// While queue is not empty, Enqueue the dequeue elements of queue
		while(!queue.isEmpty()){
			temp.enqueue(queue.dequeue());
			count++;
		}

		// Create a string array with size count
		String[] strArray = new String[count];
		
		// While temp is not empty, fill string array with queue
		while(!temp.isEmpty()){
			strArray[strArray.length-count] = (String)temp.dequeue();
			count--;
		}
		makeEmpty();

		// Finds root.
		root = balanceRecursive(strArray, 0, strArray.length - 1);
	}
}