
package com.maxtop.walker.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.maxtop.walker.cache.SettingRepository;
import com.maxtop.walker.dao.SettingDao;
import com.maxtop.walker.model.Setting;
import com.maxtop.walker.service.HttpClientService;
import com.maxtop.walker.service.SettingService;

@Service
public class SettingServiceImpl implements SettingService {
	
	@Value("${setting.update.url:http://api.zb.youku.com/api/v1/online/data/update}")
	private String settingUpdateUrl;
	
	@Autowired
	private SettingRepository settingRepository;
	
	@Autowired
	private SettingDao settingDao;
	
	@Autowired
	private HttpClientService httpClientService;
	
	public Setting getSettings() {
		return settingRepository.getSettings();
	}
	
	public void updateSettings(Map<String, Object> parameters) {
		if (parameters.containsKey("mul") || parameters.containsKey("add")) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if (parameters.containsKey("mul")) paramMap.put("mul", parameters.get("mul"));
			if (parameters.containsKey("add")) paramMap.put("add", parameters.get("add"));
			httpClientService.executePostService(settingUpdateUrl, paramMap);
		}
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
