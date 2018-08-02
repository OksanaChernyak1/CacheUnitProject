package com.hit.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.net.Socket;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;
import com.hit.services.CacheUnitController;

public class HandleRequest<T> implements java.lang.Runnable {

	private CacheUnitController<T> cacheUnitController;
	private Socket socket;

	public HandleRequest(Socket s, CacheUnitController<T> controller) {
		this.cacheUnitController = controller;
		this.socket = s;
	}

	@Override
	public void run() {

		Request<DataModel<T>[]> request = null;

		try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

			String reqJson = null;
			try {
				reqJson = (String) in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("reqJson: " + reqJson);
			Type ref = new TypeToken<Request<DataModel<T>[]>>() {
			}.getType();
			request = new Gson().fromJson(reqJson, ref);

			String action = request.getHeaders().get("action");
			switch (action.toLowerCase()) {
			case "update":
				boolean updateResult = this.cacheUnitController.update(request.getBody());
				out.writeObject("Update Result=" + updateResult);
				break;
			case "delete":
				boolean deleteResult = this.cacheUnitController.delete(request.getBody());
				out.writeObject("Delete Result=" + deleteResult);
				break;
			case "get":
				DataModel<T>[] dms = this.cacheUnitController.get(request.getBody());
				if (dms != null) {
					out.writeObject(dms.toString());
				}else {
					out.writeObject( "Don't exist in the memory");
				}

				break;
			case "statistics":
				String statistics = this.cacheUnitController.getStatistics();
				out.writeObject(statistics);
			default:

			}
			out.flush();
			in.close();
			out.close();
		} catch (

		IOException e) {
			e.printStackTrace();
		}

	}

}
