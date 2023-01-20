package tech.tresearchgroup.babygalago.model.postman;

import com.google.gson.annotations.SerializedName;

public class Raw{

	@SerializedName("language")
	private String language;

	public String getLanguage(){
		return language;
	}
}