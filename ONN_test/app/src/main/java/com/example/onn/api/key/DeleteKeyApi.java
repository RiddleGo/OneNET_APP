package com.example.onn.api.key;

import com.example.onn.exception.OnenetApiException;
import com.example.onn.http.HttpDeleteMethod;
import com.example.onn.sdkrequest.RequestInfo.Method;
import com.example.onn.sdkresponse.BasicResponse;
import com.example.onn.sdkresponse.trriger.AbstractAPI;
import com.example.onn.utils.Config;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DeleteKeyApi extends AbstractAPI{
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String keyStr;
	private HttpDeleteMethod HttpMethod;
	
	/**
	 * @param keyStr：需要删除的apikey,String
	 * @param key：masterkey(注：只能为master-key)
	 */
	public DeleteKeyApi(String keyStr, String key) {
		this.keyStr = keyStr;
		this.key = key;
		this.method = Method.DELETE;
        Map<String, Object> headmap = new HashMap<String, Object>();
        HttpMethod = new HttpDeleteMethod(method);
        headmap.put("api-key", key);
        HttpMethod.setHeader(headmap);
        this.url = Config.getString("test.url") + "/keys" + "/" + keyStr;
        HttpMethod.setcompleteUrl(url,null);
	}


	public BasicResponse<Void> executeApi() {
		BasicResponse response = null;
		try {
			HttpResponse httpResponse = HttpMethod.execute();
			response = mapper.readValue(httpResponse.getEntity().getContent(), BasicResponse.class);
			response.setJson(mapper.writeValueAsString(response));
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
