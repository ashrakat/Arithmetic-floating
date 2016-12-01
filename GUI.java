import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frame;
	private static JTextField textField;
	tag tags = new tag();

	/**
	 * Launch the application.
	 */
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
	
	public static void sort()
	{
		for(int i = 0 ; i < list.size() ; i++)
		{
			for(int j = 0 ; j < list.size()  ; j++)
			{
				if((int)list.get(i).getSymbol() < (int)list.get(j).getSymbol())
				{
					//System.out.println(list.get(i).getSymbol() + " " + list.get(j).getSymbol());
					Node temp = new Node();
					temp.setFreq(list.get(i).getFreq());
					temp.setLower(list.get(i).getLower());
					temp.setUpper(list.get(i).getUpper());
					temp.setSymbol(list.get(i).getSymbol());
					list.get(i).setSymbol(list.get(j).getSymbol());
					list.get(i).setLower(list.get(j).getLower());
					list.get(i).setUpper(list.get(j).getUpper());
					list.get(i).setFreq(list.get(j).getFreq());
					list.get(j).setSymbol(temp.getSymbol());
					list.get(j).setLower(temp.getLower());
					list.get(j).setUpper(temp.getUpper());
					list.get(j).setFreq(temp.getFreq());
				}
			}
		}
	}
	
	public static void Lower_Upper()
	{
		sort();
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
		FileWriter to = new FileWriter("probability.txt");
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
	
	public static void call() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblFileName = new JLabel("File Name");
		lblFileName.setBounds(22, 27, 46, 14);
		frame.getContentPane().add(lblFileName);
		
		textField = new JTextField();
		textField.setBounds(78, 24, 237, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Compress");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedReader read = null;
				String Line = null;
				String file = textField.getText();

				try {
					FileReader from = new FileReader(file);
					read = new BufferedReader(from);

					while ((Line = read.readLine()) != null) {
						word+=Line;
					}

				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "File doesn't exist");
					e1.printStackTrace();
				}
				finally {
					try {
					  if (Line != null)
						  read.close();
					 } catch (IOException ex) {
						ex.printStackTrace();
				      }
				}
			for(int i = 0 ; i < word.length(); i++)	
			  FillList(word.charAt(i));	
			
			Lower_Upper();
			try {
					tags.Compress(word, list);
					sendToFile();
					JOptionPane.showMessageDialog(null, "Done");
					list.clear();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Data cannot be compressed");
					e1.printStackTrace();
				}
			
			}
		});
		btnNewButton.setBounds(22, 178, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		
		
		JButton btnNewButton_1 = new JButton("Extract");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BufferedReader read = null;
				String Line = null;
				double code = 0.0;
			    String file = textField.getText();
			    try {
					FileReader from = new FileReader(file);
					read = new BufferedReader(from);

					Line = read.readLine();
				    code = Double.parseDouble(Line);
				    //System.out.println(Line);
				    list.clear();
				    tags.Decompress(code , list);
					
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "File doesn't exist");
					e1.printStackTrace();
				}
				finally {
					try {
					  if (Line != null)
						  read.close();
					 } catch (IOException ex) {
						ex.printStackTrace();
				      }
				}
			    
			    
			}
		});
		btnNewButton_1.setBounds(307, 178, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		
		
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				if(fc.showOpenDialog(null)  == fc.APPROVE_OPTION)
				{
				  textField.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		btnBrowse.setBounds(325, 23, 89, 23);
		frame.getContentPane().add(btnBrowse);
	}
}
