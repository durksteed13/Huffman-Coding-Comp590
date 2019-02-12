package decoder;

public class DSymbol implements Comparable<DSymbol>{
	
	private int length;
	private char character;
	
	public DSymbol(int l, char c) {
		length = l;
		character = c;
	}
	
	public int getLength() {
		return length;
	}
	
	public char getCharacter() {
		return character;
	}

	@Override
	public int compareTo(DSymbol o) {
		if(this.length == o.length) {
			return this.character - o.character;
		}
		else {
			return this.length - o.length;
		}
	}
}
