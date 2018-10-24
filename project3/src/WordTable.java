import java.io.*;

public class WordTable {
	//creates a wordTree table for every letter of the alphabet
	WordTree[] table = new WordTree[26];
	int size =0;

	//assigns tables A, B, C, ... to numbers 1, 2, 3, ...
	public WordTable(){
		for ( int i = 0; i<26; i++){
			table [i] = new WordTree();
		}
	}
	//Method that takes in a word and its score and appends to a red-black tree
	public void add(String word, int score) {
		int index = word.charAt(0)-'a';
		table[index].add(word, score);
	}
	//method that takes in a word and calculates and returns its score.
	public double getScore(String word){
		int index = word.charAt(0)-'a';
		WordTree node = table[index];
		return node.getScore(word);
	}
	//checks if the red-black tree contains a certain word
	public boolean contains(String word) {
		int index = word.charAt(0)-'a';
		WordTree node = table[index];
		return node.contains(word);
	}
	//method that will generate a .words file that lists all of the words from review file with a score for that word next to it.
	public void print(PrintWriter out) { 
		WordTree node = table[0];
		for ( int i = 0; i<26; i++){
			node = table[i];
			node.print(out);
		}
	}
}
