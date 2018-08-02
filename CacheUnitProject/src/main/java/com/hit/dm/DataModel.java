package com.hit.dm;
import java.io.Serializable;
import java.lang.Long;


@SuppressWarnings("serial")
public class DataModel<T> implements Serializable {

	
	private Long id;
	private T content;
	
	public DataModel(Long id, T content) {
		this.id = id;
		this.content = content;
	}
	
	   
	public T getContent() {
		return content;
	}
	public void setContent(T content) {
		this.content = content;
	}
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	
	@Override
	public String toString(){
		return "ID:"+ this.id + " content:  " + this.content;
	}
	
	
	@Override
	public boolean equals(java.lang.Object obj) {
		boolean isEqual = false;

		if (obj.getClass() != this.getClass()) {
			isEqual = false;
		} 
		else if (((DataModel<?>) obj).getContent() != this.content) {
			isEqual = false;
		} 
		else if (((DataModel<?>) obj).getId() == this.id) {
			isEqual = true;
		}
		return isEqual;
	}
	
	@Override
	public int hashCode()
	{
		return this.hashCode();
	}   
	
}
