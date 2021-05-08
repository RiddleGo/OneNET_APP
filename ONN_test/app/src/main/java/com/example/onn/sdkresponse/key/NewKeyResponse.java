package com.example.onn.sdkresponse.key;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewKeyResponse {
    @JsonProperty(value="key")
    public String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}


}
