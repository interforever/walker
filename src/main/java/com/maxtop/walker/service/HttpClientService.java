
package com.maxtop.walker.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;
import com.maxtop.walker.utils.GsonBuilderFactory;
import com.maxtop.walker.utils.MD5;

@Service
public class HttpClientService {
	
	final private static String KEY = "0df568";
	
	final private static String SECRET = "fc5e03";
	
	private static final Gson gson = GsonBuilderFactory.getInstance();
	
	public Object executeService(String uri, Map<String, String> paramMap) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpParams params = new BasicHttpParams();
		String timestamp = String.valueOf(System.currentTimeMillis());
		params.setParameter("_k_", KEY);
		params.setParameter("_t_", timestamp);
		params.setParameter("_s_", MD5.GetMD5Code(KEY + timestamp + SECRET));
		if (!CollectionUtils.isEmpty(paramMap)) {
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				params.setParameter(entry.getKey(), entry.getValue());
			}
		}
		HttpGet httpGet = new HttpGet(uri);
		httpGet.setParams(params);
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
