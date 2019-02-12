package decoder;

public class Leaf extends Node{
	
	private DSymbol symbol;
	
	public Leaf(DSymbol o) {
		this.symbol = o;
	}
	
	public boolean isLeaf() {
		return true;
	}
	
	public char getChar() {
		return symbol.getCharacter();
	}

}
