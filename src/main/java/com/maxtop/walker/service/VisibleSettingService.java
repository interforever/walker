
package com.maxtop.walker.service;

import java.util.List;

import com.maxtop.walker.model.VisibleSetting;

public interface VisibleSettingService {
	
	public List<VisibleSetting> list();
	
	public void switchVisible(String subject, String object, Integer visible);
	
}
