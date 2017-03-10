package thesis;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import diogoferreira.myapplication.*;
public class Server extends Thread {
	
	private static ConcurrentHashMap<String, Product> productslist;
	private final static int PORT = 2000;
	private static ServerSocket server;
	private Socket socket;

	long initialtime = 0;
	
	public Server(Socket s) {
		this.socket = s;
	}
	public void sendImage(String path) throws IOException{
		File file = new File(path);
		byte[] bytes = new byte[(int) file.length()];
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
	    bis.read(bytes, 0, bytes.length);
	    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream()); 
	    oos.writeObject(bytes);
	    oos.flush();
	    bis.close();
	}
	
	public void writetoFile(PrintWriter out, String text){
		out.println(text);
	}
	
	public void dbtoFile() throws IOException{
		FileOutputStream streamOut = new FileOutputStream("files/db.txt");
		ObjectOutputStream objectoutputstream = new ObjectOutputStream(streamOut);
		objectoutputstream.writeObject(productslist);
		
		objectoutputstream.close();
		streamOut.close();
	}
	
	@SuppressWarnings("unchecked")
	public static void filetoDb() throws ClassNotFoundException, IOException{
		FileInputStream streamIn = new FileInputStream("files/db.txt");
	    ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
	    productslist = (ConcurrentHashMap<String, Product>) objectinputstream.readObject();
	    
	    objectinputstream.close();
	    streamIn.close();
	}
	
	public void run() {
		int i = 1;
		if (i==0){
			try {
				ObjectOutputStream outobject = new ObjectOutputStream(socket.getOutputStream());
				InputStreamReader inputStream = new InputStreamReader(socket.getInputStream());
	            BufferedReader input = new BufferedReader(inputStream);
	            
				System.out.println("Connected");
				String message = "";
				while(message != null){
					message = input.readLine();
					if(message !=null){
						if (message.equals("LIST")){
							System.out.println(message);
							outobject.writeObject(productslist);
						}else if(message.equals("GET")){
							String get = input.readLine();
							System.out.println(message+" "+get);
							outobject.writeObject(productslist.get(get));
						}else if(message.equals("IMAGE")){
							sendImage("images/test.png");
						}else if(message.equals("EXIT")){
							System.out.println(message);
							message = null;
						}
					}
				}
				
				//escrever num ficheiro onde andou cada peessoa
				FileWriter fw = new FileWriter("files/outfilename.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter outfile = new PrintWriter(bw);
				
				writetoFile(outfile,"text");
				
			    outfile.close();
			    bw.close();
			    fw.close();
	
			    dbtoFile();
			    
				//Close Streams and Socket
				input.close();
				inputStream.close();
				outobject.close();
				socket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e){
				e.printStackTrace();
			}	
			
		}else{//Android test
			try {
				ObjectInputStream inobject = new ObjectInputStream(socket.getInputStream());
				Objectsent object = (Objectsent) inobject.readObject();
				
				//Print Object received
				System.out.println("Received: "+object);
				/*Beacon[]  beaconArray = (Beacon[]) inobject.readObject();
				for(int j=0;j<10;j++){
					if(beaconArray[j] !=null){
						System.out.println(beaconArray[j]);
					}
				}*/
				
				if(object!=null){
					try(FileWriter fw = new FileWriter("coisas.txt", true);
						    BufferedWriter bw = new BufferedWriter(fw);
						    PrintWriter out = new PrintWriter(bw))
						{
						    out.println("RSSI:"+(int)object.beaconinfo.getRssi()+",Distance: "+object.distance+"Time difference: "+object.time);
						    //more code
						    out.flush();
						    //more code
						} catch (IOException e) {
						    //exception handling left as an exercise for the reader
						}
				}
				
				inobject.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		productslist = new ConcurrentHashMap<String, Product>();
		productslist.put("1", new Product("Shoes", "Pink shoes"));
		productslist.put("2", new Product("Jeans", "Regular jeans"));
		
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
