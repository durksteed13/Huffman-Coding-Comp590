package decoder;

public class Node {
	
	public Node left;
	public Node right;
	
	public Node() {
		left = null;
		right = null;
	}
	
	//check if Node is a leaf
	public boolean isLeaf() {
		return false;
	}
	
	//check if Node is full
	public boolean isFull() {
		if(this.isLeaf()) {
			return true;
		}
		if(this.left == null || this.right == null) {
			return false;
		}
		if(this.left.isLeaf() && this.right.isLeaf()) {
			return true;
		}
		return (this.left.isFull() && this.right.isFull());
	}
	
	//add leaf to left or right child
	public int placeLeaf(Leaf l) {
		if(left == null) {
			left = l;
			return 0;
		}
		else {
			right = l;
			return 1;
		}
	}
	
	//return null char
	public char getChar() {
		return '\u0000';
	}
}
