import java.io.*;

public class WordTree{
	private static final boolean red = true;
	private static final boolean black = false;
	private static class Node{
		public String word;
		public int score;
		public int count;
		public boolean color;
		public Node left;
		public Node right;
		
		//constructor
		Node(String word, int score, int count, boolean color){
			this.word = word;
			this.score = score;
			this.count = count;
			this.color = color;
		}
	}
	Node root = null;
	//Method that checks if a node is a red node or not. Returns true or false.
	private boolean isRed(Node node){
		if (node == null) return false;
		return node.color == red;
	}
	//Method that will do a left rotation on a set of nodes
	private Node rotateLeft(Node node) {
		Node x = node.right;
		node.right = x.left;
		x.left = node;
		x.color = node.color;
		node.color = red;
		return x;
	}
	//Method that will do a right rotation on a set of nodes
	private Node rotateRight(Node node){
		Node x = node.left;
		node.left = x.right;
		x.right = node;
		x.color = node.color;
		node.color = red;
		return x;
	}
	//Method that will flip the colors on parent/child nodes to keep balance.
	private void flipColors(Node node){
		node.color = red;
		node.left.color = black;
		node.right.color = black;
	}
	//Method that takes a word and its score and add it to the red-black tree
	public void add(String word, int score){
		root = add(root,word,score);
		root.color = black;
	}
/*	Helper method for the add method. This method makes sure that the added
	nodes added in the correct order, if a rotation or a color switch is needed
	this code covers that to keep the red-black tree balanced.*/
	private  Node add (Node node, String word, int score){
		//if statement if the red-black tree is empty
		if (node == null)
			return new Node(word, score, 1, red);

		/*if / else if / else statements to determine to either add a node to the right or left
		of the parent node.*/
		int cmp = word.compareTo(node.word);
		if (cmp < 0) node.left = add(node.left, word, score);
		else if (cmp > 0) node.right = add(node.right, word, score);
		else{
			node.count++;
			node.score += score;
			return node;
		}
		//If statements to check for right, left rotations or a color flip
		if (isRed(node.right) && !isRed(node.left)) node = rotateLeft(node);
		if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
		if (isRed(node.left) && isRed(node.right)) flipColors(node);
		return node;
	}
	//Method that will return the score of a word.
	public double getScore(String word){
		return getScore(root, word);
	}
	//Helper method for getScore. 
	private static double getScore(Node node, String word){
		//If node is null, returns a score of 2
		if( node == null)
			return 2.0;
		/*else-if to check if both words are the same.
		if they are the same then it returns the nodes average score.*/
		else if ( (node.word).compareToIgnoreCase(word)==0){
			double average = (node.score / (double)node.count);
			return average;
		}
		/*else-if to check if both words are the same.
		if they aren't, and they are greater than 0, then they
		are added to the right node.*/
		else if ( (node.word).compareTo(word) < 0)
			return getScore(node.right, word);
		//else they are added to the left node.
		else{
			return getScore(node.left,word);
		}

	}
	/*Method to check if a certain word is in the
	red-black tree.*/
	public boolean contains(String word) {
		return contains(root,word);
	}
	//Helper method for the contains method.
	private static boolean contains(Node node, String word){
		//if statement to see if the tree is empty
		if (node == null)
			return false ;
		/*else-if to see if it has landed on the word.
		returns true once the word is found.*/
		else if ( (node.word).compareToIgnoreCase(word)==0)
			return true;
		/*if the word that it is looking compared to the current
		word it is at is less than 0, then it will do a 
		recursive call to the right child.*/
		else if ( (node.word).compareToIgnoreCase(word) < 0)
			return contains(node.right, word);
		/*else if greater than 0 it will do a recursive call
		to the left.*/
		else
			return contains(node.left, word);
	}
	/*Method that will write each word and its score
	out to a .words file.*/
	public void print(PrintWriter out) { 
		print(root, out);
	}
	/*Helper method for print that will take a node in
	  and print its word/score into a .words file*/
	private static void print(Node node, PrintWriter out) { 
		//if there are no nodes, returns nothing.
		if ( node == null)
			return;
		/*
		  else prints the left node first, then will print the right now
		  and so on until it has printed the entire tree.*/
		else{
			print(node.left,out);
			out.println(node.word + "\t" + node.score + "\t" +  node.count);
			print(node.right,out);
		}
	}
}
