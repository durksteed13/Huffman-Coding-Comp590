package encoder;

import java.util.ArrayList;
import java.util.Collections;

import decoder.DSymbol;

public class OptimalMinVarianceHuffmanTree {
	
	private ArrayList<Node> nodes;
	private Node tree;
	private ArrayList<DSymbol> treeLengths;
	
	public OptimalMinVarianceHuffmanTree(ArrayList<ESymbol> symbols) {
		treeLengths = new ArrayList<DSymbol>();
		nodes = new ArrayList<Node>();
		
		for(int i = 0; i < symbols.size(); i++) {
			nodes.add(new Node(symbols.get(i)));
		}
		
		Collections.sort(nodes);
		
		//while the ArrayList has more than one node
		//remove two nodes with lowest frequencies
		//create new node with two removed nodes as left and right children
		//add new node to the ArrayList and sort
		while(nodes.size() > 1) {
			Node one = nodes.remove(0);
			Node two = nodes.remove(0);
			Node temp = new Node(one, two);
			nodes.add(temp);
			Collections.sort(nodes);
		}
		
		//get root of Huffman tree
		tree = nodes.get(0);
		
		//calculate lengths for each character
		setLengths(tree);
	}
	
	//calculates the lengths of each node in the tree
	//needed to later make canonical tree using lengths
	public void setLengths(Node n) {
		Node current = n;
		
		if(current.left != null) {
			if(current.left.hasCharacter()) {
				treeLengths.add(new DSymbol(current.getLength()+1, current.left.getCharacter()));
			}
			
			current.left.setLength(current.getLength()+1);
			setLengths(current.left);
		}
		
		if(current.right != null) {
			if(current.right.hasCharacter()) {
				treeLengths.add(new DSymbol(current.getLength()+1, current.right.getCharacter()));
			}
			
			current.right.setLength(current.getLength()+1);
			setLengths(current.right);
		}
	}
	
	public ArrayList<DSymbol> getLengths() {
		Collections.sort(treeLengths);
		return treeLengths;
	}
}
