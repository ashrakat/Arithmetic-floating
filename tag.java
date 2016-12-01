import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class tag {

	public tag() {
		// TODO Auto-generated constructor stub
	}
	public int getIndex (char c, LinkedList<Node> lis)
	{
		for (int i = 0 ; i < lis.size() ; i++)
		{
			if(lis.get(i).getSymbol() == c)
			  {			
			   	return i;
			  }
		}
		return -1;
	}
	
	public  static void split(LinkedList<Node> lis) throws IOException
	{	
		BufferedReader read = null;
		String Line = new String();
		String wholeLine = new String();
		FileReader from = new FileReader("probability.txt");
		read = new BufferedReader(from);
		
		while ((Line = read.readLine()) != null) {
			wholeLine+=Line;
		}
        
		String[] split = wholeLine.split("-");
		
		for(int i = 0 ; i < split.length;i++)
		{
			Node temp = new Node();
			temp.setSymbol(split[i].charAt(0));
			temp.setLower(Double.parseDouble(split[i+1]));
			temp.setUpper(Double.parseDouble(split[i+2]));
			lis.add(temp);
			i+=2;
		}

	}
	
	public void Compress(String w ,LinkedList<Node> lis) throws IOException
	{
	 double low = lis.get(0).getLower() , high = 1.0 ;
	 int count = 0 , index = 0;
	 double range = (high - low) , tempLow = low;
	 do
	 {
		index = getIndex(w.charAt(count),lis);
		low =  tempLow + (range * lis.get(index).getLower());
		high = tempLow + (range * lis.get(index).getUpper());
		range = high - low;
		tempLow = low;
	 count++;	 
	 }
	 while(count < w.length());
	 double Encode = (high+low)/2;
	 System.out.println(Encode);
	 FileWriter to = new FileWriter("Compress.txt");
	 BufferedWriter output = new BufferedWriter(to);
		output.write(String.valueOf(Encode));
		output.close();
		
    FileWriter in = new FileWriter("Size.txt");
	BufferedWriter out = new BufferedWriter(in);
		out.write(String.valueOf(w.length()));
		out.close();
	}
    	
	public void Decompress(double encode , LinkedList<Node>lis) throws IOException
	{
		BufferedReader read = null;
		String Line = new String();

		FileReader from = new FileReader("Size.txt");
		read = new BufferedReader(from);
        Line = read.readLine();
        int size = Integer.parseInt(Line);
		
		String decode = new String();
		double range = 0;
		split(lis);
		int count = 0;
		
		for(int i = 0 ; i < lis.size() ; i++)
		{
			if(lis.get(i).getLower() <= encode && encode < lis.get(i).getUpper())
			{
				decode += lis.get(i).getSymbol();
				count++;
				
				if(count == size)break;
				range = lis.get(i).getUpper()-lis.get(i).getLower();
				encode = ((encode - lis.get(i).getLower())/ (range));
				i = -1;
				continue;
			}
			else 
			{
			 if(i == lis.size() - 1)
				 break;
			}
		}
		System.out.println(decode);
		FileWriter to = new FileWriter("Envoke.txt");
		 BufferedWriter output = new BufferedWriter(to);
			output.write(String.valueOf(decode));
			output.close();
	}
}
