package com.example.onn.api.dtu;

import com.example.onn.exception.OnenetApiException;
import com.example.onn.http.HttpGetMethod;
import com.example.onn.sdkrequest.RequestInfo.Method;
import com.example.onn.sdkresponse.BasicResponse;
import com.example.onn.sdkresponse.dtu.DtuParserList;
import com.example.onn.sdkresponse.trriger.AbstractAPI;
import com.example.onn.utils.Config;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class FindDtuParserList extends AbstractAPI {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String name; // 精确匹配名字（可选）
	private HttpGetMethod HttpMethod;
	/**
	 * TCP透传查询
	 * @param name： 名字,精确匹配名字（可选）,String
	 * @param key:masterkey 或者 该设备的设备apikey
	 */
	public FindDtuParserList(String name, String key) {
		this.name = name;
		this.method = Method.GET;
		this.key = key;
		Map<String, Object> headmap = new HashMap<String, Object>();
		Map<String, Object> urlmap = new HashMap<String, Object>();
		HttpMethod = new HttpGetMethod(method);
		this.url = Config.getString("test.url") + "/dtu/parser";
		if (name != null) {
			urlmap.put("name", name);
		}
		headmap.put("api-key", key);
		HttpMethod.setHeader(headmap);
		HttpMethod.setcompleteUrl(url, urlmap);
	}

	public BasicResponse<DtuParserList> executeApi() {
		BasicResponse response;
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		try {
			HttpResponse httpResponse = HttpMethod.execute();
            InputStream instreams = httpResponse.getEntity().getContent();
            String str = IOUtils.toString(instreams, "UTF-8");
            response = mapper.readValue(str, BasicResponse.class);
			response.setJson(str);
			Object newData = mapper.readValue(mapper.writeValueAsString(response.getDataInternal()), DtuParserList.class);
			response.setData(newData);
			return response;
		} catch (Exception e) {
			logger.error("error: {}" , e.getMessage());
			throw new OnenetApiException(e.getMessage());
		}finally {
			try {
				HttpMethod.httpClient.close();
			} catch (Exception e) {
				logger.error("http close error: {}", e.getMessage());
				throw new OnenetApiException(e.getMessage());
			}
		}
		

	}
}
