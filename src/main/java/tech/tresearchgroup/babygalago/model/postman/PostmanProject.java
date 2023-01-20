package tech.tresearchgroup.babygalago.model.postman;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostmanProject{

	@SerializedName("item")
	private List<ItemItem> item;

	@SerializedName("info")
	private Info info;

	public List<ItemItem> getItem(){
		return item;
	}

	public Info getInfo(){
		return info;
	}
}