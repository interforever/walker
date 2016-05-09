
package com.maxtop.walker.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.maxtop.walker.model.Notification;
import com.maxtop.walker.service.HttpClientService;
import com.maxtop.walker.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Value("${setting.notification.list.url:http://api.zb.youku.com/api/v1/notification}")
	private String settingNotificationListUrl;
	
	@Value("${setting.notification.add.url:http://api.zb.youku.com/api/v1/notification/add}")
	private String settingNotificationAddUrl;
	
	@Autowired
	private HttpClientService httpClientService;
	
	public List<Notification> list() {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) httpClientService.executeGetService(settingNotificationListUrl, null);
		if (!"0".equals(map.get("code").toString())) return null;
		List<Notification> notifications = new ArrayList<Notification>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> notificationMaps = (List<Map<String, Object>>) map.get("data");
		for (Map<String, Object> notificationMap : notificationMaps) {
			Notification notification = new Notification();
			notification.setId(((Number) notificationMap.get("id")).intValue());
			notification.setContent((String) notificationMap.get("content"));
			notification.setCreate_time(new Date(((Number) notificationMap.get("create_time")).longValue() * 1000));
			notifications.add(notification);
		}
		return notifications;
	}
	
	public void publishNotification(String content) {
		if (StringUtils.isEmpty(content)) return;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("content", content);
		httpClientService.executeGetService(settingNotificationAddUrl, paramMap);
	}
	
}
