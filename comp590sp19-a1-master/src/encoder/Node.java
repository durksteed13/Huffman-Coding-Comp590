package encoder;

public class Node implements Comparable<Node>{
	
	public ESymbol symbol;
	public Node left;
	public Node right;
	public int height;
	private int length;
	private int frequency;
	
	public Node(ESymbol o) {
		symbol = o;
		left = null;
		right = null;
		height = 0;
		frequency = o.getFrequency();
		length = 0;
	}
	
	public Node(Node l, Node r) {
		symbol = null;
		left = l;
		right = r;
		if(l.getHeight() >= r.getHeight()) {
			height = l.getHeight()+1;
		}
		else {
			height = r.getHeight()+1;
		}
		frequency = l.getFrequency() + r.getFrequency();
		length = 0;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public boolean hasCharacter() {
		if (symbol != null) {
			return true;
		}
		return false;
	}
	
	public char getCharacter() {
		return symbol.getCharacter();
	}
	
	public void setLength(int l) {
		length = l;
	}
	
	public int getLength() {
		return length;
	}

	@Override
	public int compareTo(Node o) {
		if(this.getFrequency() == o.getFrequency()) {
			return this.getHeight() - o.getHeight();
		}
		return this.getFrequency() - o.getFrequency();
	}
}
