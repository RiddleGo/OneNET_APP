package com.example.onn.api.mqtt;

import com.example.onn.exception.OnenetApiException;
import com.example.onn.http.HttpGetMethod;
import com.example.onn.sdkrequest.RequestInfo.Method;
import com.example.onn.sdkresponse.BasicResponse;
import com.example.onn.sdkresponse.trriger.AbstractAPI;
import com.example.onn.utils.Config;
import com.fasterxml.jackson.core.type.TypeReference;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetUserTopics  extends AbstractAPI{
	private HttpGetMethod HttpMethod;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 查询产品Topic
	 * @param key：masterkey
	 */
	public GetUserTopics(String key) {
		this.key=key;
		this.method = Method.GET;
        Map<String, Object> headmap = new HashMap<String, Object>();
        HttpMethod = new HttpGetMethod(method);
        this.url = Config.getString("test.url") +"/mqtt"+"/topic";
        headmap.put("api-key", key);
        HttpMethod.setHeader(headmap);
        HttpMethod.setcompleteUrl(url,null);
	}

	public BasicResponse<List<String>> executeApi() {
		BasicResponse response = null;	
		try {
			HttpResponse httpResponse = HttpMethod.execute();
			response = mapper.readValue(httpResponse.getEntity().getContent(), BasicResponse.class);
			response.setJson(mapper.writeValueAsString(response));
			Object newData = mapper.readValue(mapper.writeValueAsString(response.getDataInternal()), new TypeReference<List<String>>(){});
			response.setData(newData);
			return response;
		} catch (Exception e) {
			logger.error("json error {}", e.getMessage());
			throw new OnenetApiException(e.getMessage());
		}
		finally {
			try {
				HttpMethod.httpClient.close();
			} catch (Exception e) {
				logger.error("http close error: {}", e.getMessage());
				throw new OnenetApiException(e.getMessage());
			}
		}
	}
}
