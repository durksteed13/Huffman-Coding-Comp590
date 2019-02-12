package decoder;

import java.util.ArrayList;
import java.util.HashMap;

public class CanonicalTree {
	
	private Node rootNode = new Node();
	private HashMap<Character, String> codes;
	
	//assumes symbols have already been sorted by length (and lexicographically)
	public CanonicalTree(ArrayList<DSymbol> symbols) {
		
		codes = new HashMap<Character, String>();
		
		//create Canonical tree
		for(int i = 0; i < symbols.size(); i++) {
			addNode(symbols.get(i));
		}
	}
	
	//adds a DSymbol to the tree
	//always goes left if possible
	//saves code for each character when successfully added to the tree
	public void addNode(DSymbol o) {
		Node current = rootNode;
		String code = "";
		for(int i = 0; i < o.getLength(); i++) {
			if(i == o.getLength()-1) {
				Leaf placed = new Leaf(o);
				int digit = current.placeLeaf(placed);
				if(digit == 0) {
					codes.put(o.getCharacter(), code + "0");
				}
				else {
					codes.put(o.getCharacter(), code + "1");
				}
				
			}
			//create node if needed
			else if(current.left == null) {
				code += "0";
				current.left = new Node();
				current = current.left;
			}
			
			else if(!current.left.isFull() && !current.left.isLeaf()) {
				code += "0";
				current = current.left;
			}
			
			//create node if needed
			else if(current.right == null) {
				code += "1";
				current.right = new Node();
				current = current.right;
			}
			
			else {
				code += "1";
				current = current.right;
			}
		}
	}
	
	public Node getRoot() {
		return rootNode;
	}
	
	public HashMap<Character, String> getCodes() {
		return codes;
	}
}
