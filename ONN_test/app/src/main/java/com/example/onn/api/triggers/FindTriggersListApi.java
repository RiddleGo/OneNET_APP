package com.example.onn.api.triggers;

import com.example.onn.exception.OnenetApiException;
import com.example.onn.http.HttpGetMethod;
import com.example.onn.sdkrequest.RequestInfo.Method;
import com.example.onn.sdkresponse.BasicResponse;
import com.example.onn.sdkresponse.triggers.TriggersList;
import com.example.onn.sdkresponse.trriger.AbstractAPI;
import com.example.onn.utils.Config;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class FindTriggersListApi extends AbstractAPI{
	private String title;
	private Integer page;
	private Integer perpage;
	private HttpGetMethod HttpMethod;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * @param title:名称（可选）,String
	 * @param page:指定页码，最大页数为10000（可选）,Integer
	 * @param perpage:指定每页输出设备个数，默认30，最多100（可选）,Integer
	 * @param key:masterkey 或者 设备apikey
	 */
	public FindTriggersListApi(String title, Integer page, Integer perpage, String key) {
		this.title = title;
		this.page = page;
		this.perpage = perpage;
		this.method = Method.GET;
		this.key = key;
        Map<String, Object> headmap = new HashMap<String, Object>();
        Map<String, Object> urlmap = new HashMap<String, Object>();
        HttpMethod = new HttpGetMethod(method);
        headmap.put("api-key", key);
        HttpMethod.setHeader(headmap);
        this.url = Config.getString("test.url") + "/triggers";
        // url参数
        if (title != null) {
            urlmap.put("title", title);
        }
        if (page != null) {
            urlmap.put("page", page);
        }
        if (perpage != null) {
            urlmap.put("per_page", perpage);
        }
        headmap.put("api-key", key);
        HttpMethod.setHeader(headmap);
        HttpMethod.setcompleteUrl(url,urlmap);
	}

	public BasicResponse<TriggersList> executeApi() {
		BasicResponse response;
//        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		try {
			HttpResponse httpResponse = HttpMethod.execute();
			response = mapper.readValue(httpResponse.getEntity().getContent(), BasicResponse.class);
			response.setJson(mapper.writeValueAsString(response));
			Object newData = mapper.readValue(mapper.writeValueAsString(response.getDataInternal()), TriggersList.class);
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