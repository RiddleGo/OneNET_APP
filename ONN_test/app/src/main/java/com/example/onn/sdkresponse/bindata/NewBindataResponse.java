package com.example.onn.sdkresponse.bindata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewBindataResponse {
	 @JsonProperty(value="index")
	 public String index;

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
}
