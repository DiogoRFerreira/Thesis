package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;

public class Server extends Thread {
	
	private final static int PORT = 2000;
	private static ServerSocket server;
	private Socket socket;
	private int applicationversion = 1;
	
	public Server(Socket s) {
		this.socket = s;
	}
	
	public void run() {
		
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);		
			
			String line = in.readLine();
			System.out.println(line);
			
			if(line.equals("GET VERSION")){
				//Send version
				System.out.println(applicationversion);
				out.print(applicationversion);
				out.flush();
			}else{
				//Send data back to client
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				@SuppressWarnings("unchecked")
				LinkedList<double[]> position_history_list = (LinkedList<double[]>) ois.readObject(); 
				if(position_history_list!=null){
					FileWriter fw = new FileWriter(line+".txt", true);
					PrintWriter outfile = new PrintWriter(new BufferedWriter(fw));
					//outfile.println("X , Y ,Time");
					//outfile.flush();
					Iterator<double[]> position_history_iterator = position_history_list.iterator();
					while (position_history_iterator.hasNext()) {
						double[] temparray = position_history_iterator.next();
						outfile.println(temparray[0]+" ,"+temparray[1]+" ,"+temparray[2]);
						outfile.flush();
					}
				
				/*
				FileWriter fw = new FileWriter(line+".txt", true);
				PrintWriter outfile = new PrintWriter(new BufferedWriter(fw));
				outfile.println("RSSI 1, RSSI 2, RSSI 3, RSSI 4, RSSI 5, RSSI 6, RSSI 7, RSSI 8, RSSI 9, RSSI 10, Time");
				outfile.flush();
				Iterator<double[]> position_history_iterator = position_history_list.iterator();
				while (position_history_iterator.hasNext()) {
					double[] temparray = position_history_iterator.next();
					outfile.println(temparray[0]+" ,"+temparray[1]+" ,"+temparray[2]+" ,"+temparray[3]+" ,"+temparray[4]+" ,"+temparray[5]+" ,"+temparray[6]+" ,"+temparray[7]+" ,"+temparray[8]+" ,"+temparray[9]+" ,"+temparray[10]);
					outfile.flush();
				}*/
			
					outfile.close();
				}
			}
			
			in.close();
			out.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		try {
			server = new ServerSocket(PORT);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		while (true) {
			try {
				Socket client = server.accept();
				new Server(client).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}	
}