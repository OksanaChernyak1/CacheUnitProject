package com.hit.services;

import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

public class CacheUnitService<T> {

	private CacheUnit<T> cacheUnit;
	private final String algoName= "LRU Algorithm";
	private final int capacity=3;
	private static int reqCounter=0;
	private static int count=0;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public CacheUnitService() {
		LRUAlgoCacheImpl<Long, DataModel<T>> lru = new LRUAlgoCacheImpl<>(capacity);
		DaoFileImpl<T> daoFile = new DaoFileImpl<>("File.txt");

		this.cacheUnit = new CacheUnit<>(lru, daoFile);
		for (int i = 0; i <= 10; i++)
        {
            String value = "hello";
           daoFile.save(new DataModel(Long.valueOf(i), value));
        }
		
	}

	public boolean update(DataModel<T>[] dataModels) {
		reqCounter=reqCounter+dataModels.length;
		count++;
		DataModel<T>[] dms = getFilteredDataModels(dataModels);

		for (DataModel<T> dm : dms) {
			if (dm != null) {
				for (DataModel<T> dam : dataModels) {
					
					if (dm.getId().equals(dam.getId())) {
						dm.setContent(dam.getContent());
						break;
					}
				}

			} else {
				for (DataModel<T> dam : dataModels) {
					cacheUnit.updateFile(dam);
				}
				break;
			}
			
		}
		return dataModels.length == dms.length;
	}

	public boolean delete(DataModel<T>[] dataModels) {
		
		count++;
		reqCounter=reqCounter+dataModels.length;
		DataModel<T>[] dms = getFilteredDataModels(dataModels);

			for (DataModel<T> dm : dms) {
				
				dm.setContent(null);
			}
		return dataModels.length == dms.length;
	}

	public DataModel<T>[] get(DataModel<T>[] dataModels) {
		reqCounter=reqCounter+dataModels.length;
		count++;
		return getFilteredDataModels(dataModels);
	}

	private  DataModel<T>[] getFilteredDataModels(DataModel<T>[] dataModels) {
		Long[] ids = new Long[dataModels.length];

		for (int i = 0; i < dataModels.length; i++) {
			ids[i] = dataModels[i].getId();
		}

		return this.cacheUnit.getDataModels(ids);
	}

	public String getStatistics() {
		count++;
		return "<html>Capacity: "+ capacity +"<br>Algorithm: "+algoName+"<br>Total number of requests: "+count+"<br>Total number of DataModels(GET/DELETE/UPDATE requests): "+reqCounter+"<br>Total number of DataModel swaps(from Cache to Disk): "+cacheUnit.getSwapCounter()+"</html>";
	}
}
