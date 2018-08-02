package com.hit.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class CacheUnitClient {

	private Socket serverSocket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;

	public CacheUnitClient() {
		
	}

	public String send(String request) {
		 String messageFromServer=null;
		try {
			System.out.println("Client send to server: "+request);
			serverSocket = new Socket(InetAddress.getLocalHost().getHostAddress (), 12345);
			outputStream = new ObjectOutputStream(serverSocket.getOutputStream());
			inputStream = new ObjectInputStream(serverSocket.getInputStream());
			
			outputStream.writeObject(request);
			try {
				messageFromServer=(String)inputStream.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			outputStream.close();
			inputStream.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return messageFromServer;

	}
}
