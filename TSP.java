
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;  
import java.util.List;  
import java.util.ArrayList;  
import java.util.stream.Collectors;

public class TSP {

	private JFrame frame;	//frame 					
	public static double distance[][];  //distance matrix
	public static String input; //input

	public static void main(String[] args)
	{
		TSP gui = new TSP();     //create tsp object serving as gui
		gui.frame.setVisible(true); //set to visible
		gui.frame.setResizable(false);
	}
	
	public static String solve(String input)  //method finds the order of houses to go to
	{
		List<String> arr = Arrays.asList(input.split("\n")); //splits input by each line

		int size = arr.size(); //how many orders there are
        
        distance = new double[size][size]; //distance matrix/distances between each point
        
        for(int from = 0; from<size; from++)
        {
        	for(int to = 0; to<size; to++)
        	{
				if(from == to)
				{
					distance[from][to] = 0; //the distance from a house to the same house is 0
				}
				else
				{
					String[] fromaddress = arr.get(from).split(",");
        			double fromlatitude = Double.parseDouble(fromaddress[3]); //gps north
        			double fromlongitude = Double.parseDouble(fromaddress[4]);  //gps west
        			
        			String[] toaddress = arr.get(to).split(",");
        			double tolatitude = Double.parseDouble(toaddress[3]);  //gps north
        			double tolongitude = Double.parseDouble(toaddress[4]);  //gps west
        			
        			fromlatitude = Math.toRadians(fromlatitude);
        			fromlongitude = Math.toRadians(fromlongitude);
        			tolatitude = Math.toRadians(tolatitude);
        			tolongitude = Math.toRadians(tolongitude);
        			
        			double x = 6371*Math.acos(Math.sin(fromlatitude)*Math.sin(tolatitude)+Math.cos(fromlatitude)*Math.cos(tolatitude)*Math.cos(fromlongitude- tolongitude)); //distance
        			distance[from][to] = x;
				}
        	}	
        }

		double first = Double.MAX_VALUE;  //will store distance from apache to first house
		int firstnumber = 0; //will store first house to visit (-1) as index starts at 0

		//find first house to visit from apache
		for(int i = 0; i<size; i++)
		{
			double fromlatitude = 53.38197;
			double fromlongitude = -6.59274;   //apache coordinates

			String[] toaddress = arr.get(i).split(",");
			double tolatitude = Double.parseDouble(toaddress[3]);  //gps north
			double tolongitude = Double.parseDouble(toaddress[4]);  //gps west

			double x = 6371*Math.acos(Math.sin(fromlatitude)*Math.sin(tolatitude)+Math.cos(fromlatitude)*Math.cos(tolatitude)*Math.cos(fromlongitude- tolongitude));
			if(x<first)
			{
				first = x;
				firstnumber = i;
			}
		}

		ArrayList<Integer> route = new ArrayList<>(); //used to store the route/order of houses to visit
		route.add(firstnumber); //adds the first house to be visited from starting point

		int j = firstnumber; //used to store the house just visited

		while(route.size() != size) //while there are still houses to be visited
		{
			double smallestdistance2 = Double.MAX_VALUE; //used to store the smallest distance from the house just visited to the closest house to it
			int nextcity3 = 0;
			for(int i = 0; i<arr.size(); i++)
			{
				if(distance[j][i] < smallestdistance2 && !route.contains(i) && j!= i)  //if the distance to it is smaller, not already visited and not the same house
				{
					smallestdistance2 = distance[j][i];
					nextcity3 = i;
				}
			}

			route.add(nextcity3); //add this city
			j = nextcity3;  //update j/the house just visited
		}

		for(int i = 0; i<route.size(); i++) //+1 to each element as array index starts at 0, currently each is one behind their actual order number
		{
			int x = route.get(i);
			route.set(i, x+1);
		}

		String output = route.stream().map(Object::toString).collect(Collectors.joining(",")); //joins each of the integers into a string seperated by commas
		return output;  //return the route
	}

	
	
	public TSP() //default constructor used for gui
	{
		//gui
		
		frame = new JFrame();  //frame
		frame.setBounds(100, 100, 1019, 691);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();  //panel
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 0, 1003, 652);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CS211 TSP Project");  //title
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setBounds(374, 25, 252, 100);
		panel.add(lblNewLabel);
		
		JScrollPane scroll = new JScrollPane();  //scroll
		scroll.setBounds(59, 133, 285, 408);
		panel.add(scroll);
		
		JTextArea data = new JTextArea();  //input
		scroll.setViewportView(data);
		
		JLabel datatitle = new JLabel("Enter the orders:");  //title for data/input
		datatitle.setBounds(67, 111, 102, 14);
		panel.add(datatitle);
		
		JScrollPane scrollPane_1 = new JScrollPane();  //scroll
		scrollPane_1.setBounds(288, 331, 184, -13);
		panel.add(scrollPane_1);
		
		JTextArea output = new JTextArea();  //output/solution
		output.setEditable(false);
		output.setLineWrap(true);
		output.setBounds(440, 536, 400, 105);
		panel.add(output);
		
		JLabel outputtitle = new JLabel("Solution:"); //title for output text
		outputtitle.setBounds(440, 516, 102, 14);
		panel.add(outputtitle);
		
		JButton enter = new JButton("Solve"); //enter data/solve button
		enter.setBounds(154, 553, 89, 23);
		panel.add(enter);
		
		JLabel mapicon = new JLabel("");  //map image
		ImageIcon map = new ImageIcon("map.png");
		mapicon.setIcon(map);
		mapicon.setBounds(440, 140, map.getIconWidth(), map.getIconHeight());
		panel.add(mapicon);
		
		
		JButton close = new JButton("Close"); //close button
		close.setBounds(885, 579, 89, 23);
		panel.add(close);
		
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)  //solve and present the list on screen
			{
				input = data.getText();
				String solution = solve(input);
				output.setText(solution);
			}
		});
		
		
		close.addActionListener(new ActionListener() { //close when close button pressed
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
	}
}