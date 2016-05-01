
package com.maxtop.walker.service;

import java.util.List;

import com.maxtop.walker.model.Notification;

public interface NotificationService {
	
	public List<Notification> list();
	
	public void publishNotification(String content);
	
}
