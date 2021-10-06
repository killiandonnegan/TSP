
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Arrays;  
import java.util.List;  
import java.util.ArrayList;  
import java.util.stream.Collectors;

public class window {

	private JFrame frame;									
	public static double distance[][];
	public static String input;


	public static void main(String[] args)
	{
		window gui = new window();
		gui.frame.setVisible(true);
	}
	
	
	public static String solve(String input)
	{
		List<String> arr = Arrays.asList(input.split("\n"));

		int size = arr.size();
		System.out.println("size "+size);

        //distances between each point
        distance = new double[size][size];
        for(int from = 0; from<size; from++)
        {
        	for(int to = 0; to<size; to++)
        	{
				if(from == to)
				{
					distance[from][to] = 0;
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
        			
        			double x = 6371*Math.acos(Math.sin(fromlatitude)*Math.sin(tolatitude)+Math.cos(fromlatitude)*Math.cos(tolatitude)*Math.cos(fromlongitude- tolongitude));
        			distance[from][to] = x;
				}
        	}	
        }



		double first = Double.MAX_VALUE;  //distance from apache to first house
		int firstnumber = 0;

		//find first house to visit from apache
		for(int i = 0; i<size; i++)
		{
			double fromlatitude = 53.38197;
			double fromlongitude = -6.59274;   //apache

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

		ArrayList<Integer> route = new ArrayList<>();

		route.add(firstnumber);

		boolean b = true;
		int j = firstnumber;

		while(b)
		{
			double smallestdistance2 = Double.MAX_VALUE;
			int nextcity3 = 0;
			for(int i = 0; i<arr.size(); i++)
			{
				if(distance[j][i] < smallestdistance2 && !route.contains(i) && j!= i)
				{
					smallestdistance2 = distance[j][i];
					nextcity3 = i;
				}
			}

			route.add(nextcity3);
			j = nextcity3;

			if(route.size() == size)
			{
				b = false;
			}
		}

		
			/*for(int i = 1; i<route.size()-2; i++)
		{
			if( (distance[route.get(i)][route.get(i+2)] + distance[route.get(i+2)][route.get(i+1)])  <   (distance[route.get(i)][route.get(i+1)] + distance[route.get(i+1)][route.get(i+2)]))
			{
				int temp1 = route.get(i);
				int temp2 = route.get(i+1);
				int temp3 = route.get(i+2);

				route.set(2, temp3);
				route.set(3, temp2);
			}
		}*/


		/*for(int i = 0; i<size; i++)
		{
			if(i<size-1)
			{
				System.out.print(route.get(i)+1+",");
			}
			
			else
			{
				System.out.print(route.get(i)+1);
			}

		}*/


		for(int i = 0; i<route.size(); i++) //+1 as array index starts at 0
		{
			int x = route.get(i);
			route.set(i, x+1);
		}

		String output = route.stream().map(Object::toString).collect(Collectors.joining(","));
		return output;
	}

	
	
	public window() 
	{
		initialize();
	}

	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 1019, 691);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 0, 1003, 652);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CS211 TSP Project");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNewLabel.setBounds(374, 25, 252, 100);
		panel.add(lblNewLabel);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(59, 133, 285, 408);
		panel.add(scroll);
		
		JTextArea data = new JTextArea();
		scroll.setViewportView(data);
		
		JLabel datatitle = new JLabel("Enter the orders:");
		datatitle.setBounds(67, 111, 102, 14);
		panel.add(datatitle);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(288, 331, 184, -13);
		panel.add(scrollPane_1);
		
		JTextArea output = new JTextArea();
		output.setEditable(false);
		output.setLineWrap(true);
		output.setBounds(440, 536, 400, 105);
		panel.add(output);
		
		JLabel outputtitle = new JLabel("Solution:");
		outputtitle.setBounds(440, 516, 102, 14);
		panel.add(outputtitle);
		
		JButton enter = new JButton("Solve");
		enter.setBounds(154, 553, 89, 23);
		panel.add(enter);
		
		JLabel mapicon = new JLabel("");
		ImageIcon map = new ImageIcon("map.png");
		mapicon.setIcon(map);
		mapicon.setBounds(440, 140, map.getIconWidth(), map.getIconHeight());
		panel.add(mapicon);
		
		JButton close = new JButton("New button");
		close.setBounds(885, 579, 89, 23);
		panel.add(close);
		
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				input = data.getText();
				String solution = solve(input);
				output.setText(solution);
			}
		});
	}
}
