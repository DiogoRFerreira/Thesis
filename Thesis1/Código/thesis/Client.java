package thesis;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

public class Client {
	public static void main(String[] args) {
		try {
			Socket client = new Socket("127.0.0.1", 2000);

			ObjectInputStream inobject = new ObjectInputStream(client.getInputStream());
	        DataOutputStream os = new DataOutputStream(client.getOutputStream());

			System.out.println("Connected");
			
	        os.writeBytes("LIST\n");
	        os.flush();
	        
			System.out.println("Reading");
			
			@SuppressWarnings("unchecked")
			ConcurrentHashMap<String, Product>  list = (ConcurrentHashMap<String, Product>) inobject.readObject();
			System.out.println(list);
			
	        os.writeBytes("GET\n");
	        os.flush();
	        os.writeBytes("1\n");
	        os.flush();
	        Product product = (Product) inobject.readObject();
			System.out.println(product);
			
			os.writeBytes("IMAGE\n");
			File file = new File("images/received.png");
		
		    ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
		    byte[] bytes;
		    FileOutputStream fos = null;
			bytes = (byte[])ois.readObject();
			fos = new FileOutputStream(file);
			fos.write(bytes);
			
			fos.close();
			inobject.close();
			os.close();
			client.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
