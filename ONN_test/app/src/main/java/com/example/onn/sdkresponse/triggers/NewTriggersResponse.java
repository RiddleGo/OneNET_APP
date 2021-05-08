package com.example.onn.sdkresponse.triggers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewTriggersResponse {
	 @JsonProperty(value="trigger_id")
	    public String Tirggerid;

	    public String getTirggerid() {
	        return Tirggerid;
	    }

	    public void setTirggerid(String Tirggerid) {
	        this.Tirggerid = Tirggerid;
	    }
}
