package encoder;

public class ESymbol {
	
	private char character;
	private int frequency;
	
	public ESymbol(char c, int f) {
		this.character = c;
		this.frequency = f;
	}
	
	public char getCharacter() {
		return character;
	}
	
	public int getFrequency() {
		return frequency;
	}
}
