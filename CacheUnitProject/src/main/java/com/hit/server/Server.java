package com.hit.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import com.hit.services.CacheUnitController;

public class Server implements java.util.Observer {

	private final int port = 12345;
	private ServerSocket serverSocket = null;

	public Server() {
	

	}

	public void start() {
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(port);

			while (true) {

				socket = serverSocket.accept();

				new Thread(new HandleRequest<String>(socket, new CacheUnitController<String>())).run();	
			}
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		String command = arg1.toString();

		if (command.equalsIgnoreCase("Start")) {
			System.out.println("Starting Server...");
			start();
		} else if (command.equalsIgnoreCase("Shutdown")) {
			try {
				this.serverSocket.close();
				System.out.println("Shutdown server");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
