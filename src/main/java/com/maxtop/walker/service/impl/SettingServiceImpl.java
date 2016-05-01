
package com.maxtop.walker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxtop.walker.cache.SettingRepository;
import com.maxtop.walker.model.Setting;
import com.maxtop.walker.service.SettingService;

@Service
public class SettingServiceImpl implements SettingService {
	
	@Autowired
	private SettingRepository settingRepository;
	
	public Setting getSettings() {
		return settingRepository.getSettings();
	}
	
}
