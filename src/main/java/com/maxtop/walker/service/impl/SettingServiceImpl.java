
package com.maxtop.walker.service.impl;

import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxtop.walker.cache.SettingRepository;
import com.maxtop.walker.dao.SettingDao;
import com.maxtop.walker.model.Setting;
import com.maxtop.walker.service.SettingService;

@Service
public class SettingServiceImpl implements SettingService {
	
	@Autowired
	private SettingRepository settingRepository;
	
	@Autowired
	private SettingDao settingDao;
	
	public Setting getSettings() {
		return settingRepository.getSettings();
	}
	
	public void updateSettings(Map<String, Object> parameters) {
		Setting setting = new Setting();
		BeanUtils.copyProperties(settingRepository.getSettings(), setting);
		if (parameters.containsKey("centerLng")) setting.setCenterLng((String) parameters.get("centerLng"));
		if (parameters.containsKey("centerLat")) setting.setCenterLat((String) parameters.get("centerLat"));
		if (parameters.containsKey("radius")) setting.setRadius(((Number) parameters.get("radius")).intValue());
		if (parameters.containsKey("warningSwitch")) setting.setWarningSwitch((String) parameters.get("warningSwitch"));
		if (parameters.containsKey("warningDistance")) setting.setWarningDistance(((Number) parameters.get("warningDistance")).intValue());
		if (parameters.containsKey("mul")) setting.setMul(((Number) parameters.get("mul")).doubleValue());
		if (parameters.containsKey("add")) setting.setAdd(((Number) parameters.get("add")).intValue());
		settingDao.updateSetting(setting.getCenterLng(), setting.getCenterLat(), setting.getRadius(), setting.getWarningSwitch(), setting.getWarningDistance(), setting.getMul(), setting.getAdd());
		settingRepository.setSetting(setting);
	}
	
}
