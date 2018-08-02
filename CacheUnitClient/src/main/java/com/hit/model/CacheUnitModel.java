package com.hit.model;

public class CacheUnitModel extends java.util.Observable
implements Model{

	CacheUnitClient cacheUnitClient=new CacheUnitClient();
	
	public CacheUnitModel() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public <T> void updateModelData(T t) {
		String response=null;
		String request=null;
		String ans="false";
		if(t.toString().equals("statistics")) {
			request = "{\"headers\":{\"action\":\"STATISTICS\"},\"body\":[]}\n";
			setChanged();
			notifyObservers(cacheUnitClient.send(request));
		}
		else
		{
			response=cacheUnitClient.send(t.toString());
			if(response.equals("Update Result=true")|| response.equals("Delete Result=true")|| response.contains("com")) {
				ans="true";
			}
			setChanged();
			notifyObservers(ans);
		}
	}

}
