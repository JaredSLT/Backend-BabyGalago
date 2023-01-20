package tech.tresearchgroup.babygalago.model.postman;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemItem{

	@SerializedName("item")
	private List<ItemItem> item;

	@SerializedName("name")
	private String name;

	@SerializedName("request")
	private Request request;

	@SerializedName("response")
	private List<Object> response;

	public List<ItemItem> getItem(){
		return item;
	}

	public String getName(){
		return name;
	}

	public Request getRequest(){
		return request;
	}

	public List<Object> getResponse(){
		return response;
	}
}