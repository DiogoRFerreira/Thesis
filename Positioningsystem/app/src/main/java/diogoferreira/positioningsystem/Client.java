package diogoferreira.positioningsystem;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

class Client {

	String connect_getVersion (String ip, int port){
		try {
			Socket client = new Socket(ip, port);

			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

			//Sends GET VERSION
			out.println("GET VERSION");
			out.flush();

			String line = in.readLine();

			out.close();
			in.close();
			client.close();

			return line;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "0";
	}

	void connect_sendHistory(String ip, int port) {
		try {
			//Get mac address
			String address = getMacAddr();

			Socket client = new Socket(ip, port);

			PrintWriter out = new PrintWriter(client.getOutputStream(), true);

			Log.i("Mac-Address", address);
			//Sends Mac-Address

			out.println(address);
			out.flush();

			ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
			//Sends History
			oos.writeObject(Database.positionhistory);
			oos.flush();
			oos.close();
			Database.positionhistory.clear();
			/*
			//Sends rssi - only to get values for results
			oos.writeObject(Database.rssihistory);
			oos.flush();
			oos.close();
			Database.rssihistory.clear();
			*/
			out.close();
			oos.close();
			client.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getMacAddr() {
		try {
			List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
			for (NetworkInterface nif : all) {
				if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

				byte[] macBytes = nif.getHardwareAddress();
				if (macBytes == null) {
					return "";
				}

				StringBuilder res1 = new StringBuilder();
				for (byte b : macBytes) {
					res1.append(String.format("%02X:",b));
				}

				if (res1.length() > 0) {
					res1.deleteCharAt(res1.length() - 1);
				}
				return res1.toString();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "02:00:00:00:00:00";
	}
}
