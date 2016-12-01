import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;


public class Main {
	static LinkedList<Node> list = new LinkedList<Node>();
	static String word = new String();
	
	public static void FillList(char c)
	{
		Node tempNode = new Node();
		tempNode.setSymbol(c);		
		if(list.isEmpty())
		{
			tempNode.setFreq(1); 
			list.add(tempNode);	
			return;
		}
		else
		{
			for(int  i = 0 ; i < list.size() ; i ++)
			{
				if(list.get(i).getSymbol() == c)
				{
					tempNode.setFreq(list.get(i).getFreq() + 1);	
					list.get(i).setFreq(tempNode.getFreq());
					return;
				}
			}
			tempNode.setFreq(1); 
			list.add(tempNode);	
		}
	}
	
	public static void Lower_Upper()
	{
		double lower = 0 ; double upper1 = 0;
		
		for(int i = 0 ; i < list.size() ; i++)
		{
		  double upper =  ((double)list.get(i).getFreq())/word.length();	
		  list.get(i).setLower(lower);
		  list.get(i).setUpper(upper + upper1);
		  lower = list.get(i).getUpper();
		  upper1 = lower; 
		  upper = list.get(i).getUpper();
		}
	}

	public static void sendToFile() throws IOException
	{
		FileWriter to = new FileWriter("probabilites.txt");
		 BufferedWriter output = new BufferedWriter(to);
		 for(int  i = 0 ; i < list.size(); i++){
			output.write(String.valueOf(list.get(i).getSymbol()));
			output.write("-");
			output.write(String.valueOf(list.get(i).getLower()));
			output.write("-");
			output.write(String.valueOf(list.get(i).getUpper()));
			output.write("-");
		 }
			output.close();
	}
	
	public static void main(String[] args) throws IOException {
		
		GUI gui = new GUI();
		gui.call();
	}
}
