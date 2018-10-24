import javax.xml.soap.Node;

public class HashTable {

	private int hash(int key){
		key = (key << 16) | (key >>> 16);
		key *= key;
		key = key << ((32 - power) / 2);
		key = key >>> (32 - power);
		return key;
	}

	public boolean put(int key, int value){
		double loadFactor = size/ (double)table.length;
		if(loadFactor >= .75){
			Node[]temp = table;
			table = new Node[table.length];
			power++;
			size = 0;
			for(int i = 0; i < temp.length; i++){
				Node list = temp[i];
				while(list != null){
					put(list.key, list.value);
					list = list.next();
				}
			}
		}
	}
	public boolean contains(int key){
		Node list = table
	}
	
	
	//Tree
	private static Node put(Node node, int key, Object value){
		if(node == null){
			node = new Node();
			node.key = key;
			node.value = value;
		}
		else if(key < node.key){
			node.left = put(node.left, key, value);
		
		}
		else if(key > node.key){
			node.right = put(node.right, key, value);
			
		}
		else{
			node.value = value;
		}
		return node;
	}
	
	public boolean contains(int key) { 
		return contains(root, key);
	}
	private static boolean contains(Node node, int key, int value){
		if( node == null ){
			return false;
		}
		else if( key == node.key ){
			return true;
		}
		else if(key < node.key){
			return contains(node.left, key);
		}
		else
			returns contains(node.right, key);
	}

}
