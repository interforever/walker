
package com.maxtop.walker.service;

import java.util.Map;

public interface HttpClientService {
	
	public Object executeGetService(String uri, Map<String, String> paramMap);
	
	public Object executePostService(String uri, Map<String, Object> paramMap);
	
}
