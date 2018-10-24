/* Name: James O'Brien
 * Date: 10/23/17
 * Course: CS 221
 * Assignment 4
 */
public class HashTable {

	private static class Node {
		public String key;
		public String value;
	}

	private Node[] table = new Node[101];	
	private int size = 0;
	public static final Node TOMBSTONE = new Node();   //don't need


	/***********************************************************************************************************************************************
	 * Hash method
	 ***********************************************************************************************************************************************
	 */
	private int hash(String key) {
		int hashed = (key.hashCode() & 0x7fffffff);
		return (hashed % table.length);
	}
	/***********************************************************************************************************************************************
	 * Step method
	 ***********************************************************************************************************************************************
	 */
	private int step(String key) {
		int hashed = (key.hashCode() & 0x7fffffff);
		return ((hashed % 97) + 1) % table.length;
	}
	/***********************************************************************************************************************************************
	 * nextPrime method
	 ***********************************************************************************************************************************************
	 */
	private int nextPrime(int length) {
		int doubleLength = length * 2;
		doubleLength++;

		while(!isPrime(doubleLength))
			doubleLength+=2;

		return doubleLength;
	}
	/***********************************************************************************************************************************************
	 * isPrime method
	 ***********************************************************************************************************************************************
	 */
	private boolean isPrime(int number) {
		for(int i=3; i*i <= number; i+=2){
			if(number%i == 0)
				return false;
		}
		return true;
	}	
	/***********************************************************************************************************************************************
	 * containsKey method
	 ***********************************************************************************************************************************************
	 */
	public boolean containsKey(String key) {
		int index = hash(key);

		while(table[index] != null){
			if(table[index].key.equals(key))
				return true;
			index += step(key);
			index %= table.length;
		}
		return false;
	}
	/***********************************************************************************************************************************************
	 * Put method
	 ***********************************************************************************************************************************************
	 */
	public boolean put( String key, String value ) {
		double loadFactor = size / (double)(table.length);
		//resize's and rehashes code if larger than 75%
		if(loadFactor >= 0.75){
			Node[] temp = table;
			table = new Node[nextPrime(table.length)];
			size=0;
			for(int i=0; i < temp.length; i++){
				Node list = temp[i];
				if(list != null){
					put(list.key, list.value);
				}
			}
		}

		int index = hash(key);
		int step = step(key);
		while(table[index] != null){
			if(table[index].key.equals(key)){
				table[index].value = value;
				return false;
			}
			else{
				index += step;
				index %= table.length;
			}
		}
		Node node = new Node();
		node.key = key;
		node.value = value;
		table[index] = node;
		size++;
		return true;
	}

	/***********************************************************************************************************************************************
	 * Get method
	 ***********************************************************************************************************************************************
	 */
	public String get( String key ) {
		int index = hash(key);
		int step = step(key);
		while(table[index] != null && !table[index].key.equals(key)){
			index += step;
			index %= table.length;
		}
		if(table[index] == null)
			return null;
		else
			return table[index].value;
	}
}
