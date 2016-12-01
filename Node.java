
public class Node {

	private char symbol;
	private int freq;
	private double lower;
	private double upper;
	
	
    public Node()
   {
	   this.lower = 0;
	   this.freq = 0;
	   this.upper= 0;
	  
		
   }
	
	public Node(Node N) {
		this.lower = N.lower;
		this.freq  =N.freq;
		this.upper = N.upper;
		this.symbol = N.symbol;
	}
	
	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}
    
	public char getSymbol() {
		return symbol;
	}
	
	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public double getLower() {
		return lower;
	}

	public void setLower(double lower2) {
		this.lower = lower2;
	}

	public double getUpper() {
		return upper;
	}

	public void setUpper(double upper2) {
		this.upper = upper2;
	}
    
}