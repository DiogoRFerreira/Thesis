package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Socket client = new Socket("137.74.194.152", 2000);

		Socket client;
		
		try {
			client = new Socket("127.0.0.1", 2000);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			
			String line = in.readLine();
			//Send data back to client
			out.println("DIOGO");
			System.out.println(line);
			
			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			LinkedList<double[]> position_history_list = new LinkedList<double[]>();
	        double[] positions = {1,2,177844};
	        position_history_list.add(positions);
	        positions[1]= 3;
	        position_history_list.add(positions);
	        positions[1]= 4;
	        position_history_list.add(positions);
	        Iterator<double[]> position_history_iterator = position_history_list.iterator();
			while (position_history_iterator.hasNext()) {
				double[] temparray = position_history_iterator.next();
				System.out.println(temparray[0]+" ,"+temparray[1]+" ,"+temparray[2]);
			}
		    oos.writeObject(position_history_list);
		    oos.close();
			
			/*System.out.println("Debug");
			 
	        DataOutputStream os = new DataOutputStream(client.getOutputStream());
	        InputStreamReader inputStream = new InputStreamReader(client.getInputStream());
			BufferedReader input = new BufferedReader(inputStream);
			
			//PrintWriter pw = new PrintWriter(client.getOutputStream(), true);

		    pw.println("DATABASE\n");
		    pw.flush();
			//os.writeBytes("DATABASE\n");
	        os.flush();
	        os.writeBytes("10011\n");
	        System.out.println("Debug2");
	        os.flush();
	        String message = input.readLine();
	        System.out.println(message);
	        //---------------------------------------------
	        os.writeBytes("DATABASE\n");
	        os.flush();
	        os.writeBytes("10012\n");
	        os.flush();
	        message = input.readLine();
	        System.out.println(message);
	        //---------------------------------------------
			os.writeBytes("\n");
	        os.writeBytes("SAVE HISTORY\n");
			os.flush();
	        os.writeBytes("MAc-1342-Time-2456\n");
	        
	        os.flush();
	        List<double[]> position_history_list = new LinkedList<double[]>();
	        double[] positions = {1,2,177844};
	        position_history_list.add(positions);
	        position_history_list.add(positions);
	        position_history_list.add(positions);
	        
		    oos.writeObject(position_history_list);
		    oos.close();
	        //---------------------------------------------
		    
		    input.close();
		    inputStream.close();
			os.close();*/
			client.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
