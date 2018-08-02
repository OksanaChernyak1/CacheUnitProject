package com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

public class CacheUnit<T> {

	private IAlgoCache<Long, DataModel<T>> algo;
	private IDao<Long, DataModel<T>> dao;
	private static int swapCounter=0;

	public CacheUnit(IAlgoCache<Long, DataModel<T>> algo, IDao<Long, DataModel<T>> dao) {
		this.algo = algo;
		this.dao = dao;
	}

	public DataModel<T>[] getDataModels(Long[] ids) {
		int i = 0;
		DataModel<T> data;
		DataModel<T> tempData;
		@SuppressWarnings({ "unchecked" })
		DataModel<T>[] returnedDataModel = new DataModel[ids.length];

		for (Long Id : ids) {
			data = this.algo.getElement(Id);
			if (data == null) {
				data = dao.find(Id);
				tempData = algo.putElement(data.getId(), data);
				if (tempData != null) {
					dao.save(tempData);
					swapCounter++;
				}
			}
			returnedDataModel[i++] = data;
		}
		return returnedDataModel;

	}

	public int getSwapCounter() {

		return swapCounter;
	}

	public void updateFile(DataModel<T> model) {
		dao.save(model);
	}

}
