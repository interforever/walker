
package com.maxtop.walker.service;

import java.util.Map;

import com.maxtop.walker.model.Setting;

public interface SettingService {
	
	public Setting getSettings();

	public void updateSettings(Map<String, Object> parameters);
	
}
