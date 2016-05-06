
package com.maxtop.walker.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
import com.maxtop.walker.service.HttpClientService;
import com.maxtop.walker.utils.GsonBuilderFactory;
import com.maxtop.walker.utils.MD5;

@Service
public class HttpClientServiceImpl implements HttpClientService {
	
	private static final Log logger = LogFactory.getLog(HttpClientServiceImpl.class);
	
	private static final Gson gson = GsonBuilderFactory.getInstance();
	
	@Value("${youku.http.key:0df568}")
	private String key;
	
	@Value("${youku.http.secret:fc5e03}")
	private String secret;
	
	public Object executeGetService(String uri, Map<String, String> paramMap) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		//		HttpParams params = new BasicHttpParams();
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		//		params.setParameter("_k_", key);
		//		params.setParameter("_t_", timestamp);
		//		params.setParameter("_s_", MD5.GetMD5Code(key + timestamp + secret));
		if (paramMap == null) paramMap = new HashMap<String, String>();
		paramMap.put("_k_", key);
		paramMap.put("_t_", timestamp);
		paramMap.put("_s_", MD5.GetMD5Code(key + timestamp + secret));
		if (!CollectionUtils.isEmpty(paramMap)) {
			uri += "?";
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				uri += entry.getKey() + "=" + entry.getValue() + "&";
				//				params.setParameter(entry.getKey(), entry.getValue());
			}
			uri = uri.substring(0, uri.length() - 1);
		}
		if (logger.isDebugEnabled()) logger.debug(uri);
		HttpGet httpGet = new HttpGet(uri);
		//		httpGet.setParams(params);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream instream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(instream, "UTF-8"));
			StringBuilder builder = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}
			if (logger.isDebugEnabled()) logger.debug(builder.toString());
			Object result = gson.fromJson(builder.toString(), Object.class);
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object executePostService(String uri, Map<String, Object> paramMap) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		//		HttpParams params = new BasicHttpParams();
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		//		params.setParameter("_k_", key);
		//		params.setParameter("_t_", timestamp);
		//		params.setParameter("_s_", MD5.GetMD5Code(key + timestamp + secret));
		if (paramMap == null) paramMap = new HashMap<String, Object>();
		paramMap.put("_k_", key);
		paramMap.put("_t_", timestamp);
		paramMap.put("_s_", MD5.GetMD5Code(key + timestamp + secret));
		if (!CollectionUtils.isEmpty(paramMap)) {
			uri += "?";
			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				uri += entry.getKey() + "=" + entry.getValue() + "&";
				//				params.setParameter(entry.getKey(), entry.getValue());
			}
			uri = uri.substring(0, uri.length() - 1);
		}
		if (logger.isDebugEnabled()) logger.debug(uri);
		HttpPost httpPost = new HttpPost(uri);
		//		httpPost.setParams(params);
		try {
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			InputStream instream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(instream, "UTF-8"));
			StringBuilder builder = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				builder.append(line);
				builder.append("\n");
			}
			if (logger.isDebugEnabled()) logger.debug(builder.toString());
			Object result = gson.fromJson(builder.toString(), Object.class);
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
