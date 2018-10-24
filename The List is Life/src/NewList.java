
/*
 * James O'Brien
 * 9/5/2017
 * CS221
 * Assignment 1
 */
public class NewList {
	private static class Node {
		public String value;
		public Node next;
		Node(String v) {
			next = null;
			value = v; // string
			
		}
	}
	private Node head;
	private Node tail;
	private int size;
	
	/*
	 * Constructor:
	 * 	Sets head, and tail to null
	 * 	Assigns list size to 0 since list is empty
	 */
	public NewList() {
		head = null;
		tail = null;
		size = 0;
	}
	
	/*
	 * Add method:
	 * 	This method will add nodes to the end of the list and will 
	 * 	assign a user defined string element to each node.
	 */
	void add(String element) {	
		/*
		 * Checks if list is empty, if so adds first Node to empty list
		 * and sets head and tail to the new node. 
		 */
		if (head == null) {
			head = new Node(element);
			tail = head;
			
		}
		/*
		 * If list is not empty adds new node after tail and sets new node as
		 * new tail. Sets tail.next to null.
		 */
		else {
			tail.next = new Node(element);
			tail = tail.next;
		}
		//increments size
		size++;
	}
	
	/*
	 * Add method:
	 * 	This method will take in a user defined index and element. It will then
	 * 	parse through the list and make a new node that the index specified by
	 * 	the user. It will also add a String element to that new node.
	 */
	void add(int index, String element) {
		
	}
	
	/*
	 * addAll method:	
	 * 	This addAll method will combine two linked lists together. It can take
	 * 	can take a linkedList as a parameter and add the whole list to the tail
	 *	of the current list.
	 */
	void addAll(NewList list){
		//makes reference of current list tail
		Node ref = this.tail;
		//sets current lists tail.next to point toward the new lists head
		this.tail.next = list.head;
		//while loop that steps through new list incrementing size variable
		while (ref.next != null){
			ref = ref.next;
			size++;
		}
	}
	/*
	 * Clear method:
	 * 	Resets Head, Tail, and Size and lets garbage collection clean up
	 *	"lost" List.
	 */
	void clear(){
		head = null;
		tail = null;
		size = 0;
	}
	/*
	 * Contains method:
	 * 	contains method will loop through list until it finds a node with
	 * 	user defined element and returns true, or until it finds the end
	 * 	of the list in which case it returns false.
	 */
	boolean contains(String element){
		boolean ans = false;
		//while loop that steps through the whole list
		while(head != null){
			//check to see if a nodes value is equivalent to what the user defined
			if (head.value.equals(element))
				//if found boolean ans is set to true.
				ans = true;
			//steps head to next node for each pass of the while loop.
			head = head.next;
		}
		return ans;	
	}
	/*
	 * Get method:
	 * 	this method takes in a user defined index and returns the node's 
	 * 	element at that index.
	 */
	String get(int index){
		//assigns head of list to a temp node
		Node temp = head;
		//for loop that steps through list to get to user's indexed node
		for(int i = 0; i < index; i++){
			temp = temp.next;
		}
		//returns value of users indexed node
		return temp.value;
	}
	/*
	 * indexOf method:
	 * 	indexOf method that takes in a string argument that the user defined.
	 * 	The method steps through the linkedList checking each node until it
	 * 	either finds the first node with the same element as the user defined
	 * 	argument, or until it reaches the end of the linkedList in which case it
	 * 	returns a -1 to signify that no node in the list contains the user
	 * 	defined argument.
	 */
	int indexOf(String element){
		//makes a reference Node to be able to find the head of the list again.
		Node ref = head;
		int temp = 0;
		boolean found = false;
		//while loop that steps through list
		while (head != null){
			//check to see if node's value is equal to user defined element
			if(head.value.equals(element)){
				found = true;
				head = ref;
				return temp;
			}
			temp++;
			head = head.next;
		}
		/*
		 * checks to see if var found is still false, if yes then sets temp to
		 * -1 and returns temp to user.
		 */
		if (found = false)
			temp = -1;
		return temp;
	}
	/*
	 * isEmpty method:
	 * 	Checks to see if the linkedList is an empty list or not.
	 */
	boolean isEmpty(){
		//If head is empty then isEmpty returns true
		if(head == null)
			return true;
		//If list is not empty then isEmpty returns false
		else
			return false;
		
	}
	/*
	 * lastIndexOf method:
	 * 	This method will take in a user argument as a string and step through
	 * 	linkedList to try and find the last node with a value that matches that
	 * 	string argument. If it cannot find the string in any node then the method
	 * 	will return a -1.
	 */
	int lastIndexOf(String element){

		return size;
	}
	/*
	 * readOut method:
	 * 	This method will step through the list printing out to console
	 * 	what each node's value is and at what index the node is. Method
	 * 	is only used for testing purposes.
	 */
	void readOut() {
		Node ref = head;
		int temp = 0;
		while (head != null) {
			System.out.print(temp + " - ");
			System.out.println(head.value);
			temp++;
			head = head.next;
		}
		head = ref;
	}
	/*
	 * remove method:
	 * 	This method takes in a int index as its argument. It then steps through
	 * 	the list until it finds the user defined index and removes it from 
	 * 	the list and decrements the size of the list by one.
	 */
	void remove(int index){
		Node temp = head;
		//if index is equivalent to 0, first node in list is removed
		if(index == 0){
			head = head.next;
		}
		else {
			/*
			 * for loop here steps through list until it is one node behind
			 * the user's indexed node.
			 */
			for(int i = 0; i < index; i++) {
				//check to make sure program is at the node just before users indexed node
				if(i == (index - 1)){
					/*
					 * sets head.next to point towards the node right after the
					 * one we are getting rid of. This unlinks the node we wanted
					 * to remove and lets java garbage collection clean it up.
					 */
					head.next = head.next.next;
					//reassigns head of list again
					head = temp;
					return;
				}
				head = head.next;
			}
		}
		size--;
	}
	/*
	 * set method:
	 * 	method takes in two arguments, one int index and a String element. The
	 * 	method will step through list to get to the user defined index and
	 * 	reset the nodes value to the new String element the user also provided.
	 */
	void set(int index, String element){
		//makes a reference to head of list
		Node ref = head;
		int temp = 0;
		//while loop to step through list
		while (temp <= index) {
			//check to find the indexed node in the list
			if(temp == index)
				//sets nodes value to new user defined element
				head.value = element;
			head = head.next;
			temp++;
		}
		//sets head to front of list again.
		head = ref;
	}
	/*
	 * size method:
	 * 	This method will return how long the list is.
	 */
	int size() {
		//returns the variable size that each Node has incremented
		return size;	
	}	
}
