package com.hit.dao;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.hit.dm.DataModel;

public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {

	private String filePath;
	private Map<Long, DataModel<T>> fileMap;
	boolean isNewFile = true;

	public DaoFileImpl(String filePath) {
		this.filePath = filePath;
		this.fileMap = new HashMap<Long, DataModel<T>>();
		this.isNewFile = true;
	}

	@Override
	public void save(DataModel<T> entity) {
		try {
			if (isNewFile) {
				writeToFile();
			}

			readFromFile();
			if (entity != null) {
				fileMap.put(entity.getId(), entity);
				writeToFile();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(DataModel<T> entity) {
		try {
			if (entity != null) {
				if (isNewFile) {
					writeToFile();
				}

				readFromFile();
				this.fileMap.remove(entity.getId(), entity);
				writeToFile();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public DataModel<T> find(Long id) {

		DataModel<T> retValue = null;
		try {
			if (id != null) {

				if (this.isNewFile) {
					writeToFile();
				}

				readFromFile();
				retValue = this.fileMap.get(id);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return retValue;
	}

	private void writeToFile() throws IOException {

		FileOutputStream fos = new FileOutputStream(this.filePath);
		ObjectOutputStream outputStream = new ObjectOutputStream(fos);
		outputStream.writeObject(this.fileMap);
		
		if (isNewFile) {
			isNewFile = false;
		}
		outputStream.close();
		fos.close();

	}

	@SuppressWarnings("unchecked")
	private void readFromFile() throws IOException, ClassNotFoundException {

		FileInputStream fis = new FileInputStream(this.filePath);
		ObjectInputStream inputStream = new ObjectInputStream(fis);
		fileMap = (HashMap<Long, DataModel<T>>) inputStream.readObject();
		inputStream.close();
		fis.close();

	}

}
