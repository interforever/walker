
package com.maxtop.walker.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maxtop.walker.model.Setting;
import com.maxtop.walker.service.SettingService;

@RestController
@RequestMapping(value = "/setting")
public class SettingController {
	
	@Autowired
	private SettingService settingService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Map<String, Object> getSettings() {
		Setting setting = settingService.getSettings();
		if (setting == null) return null;
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> centerMap = new HashMap<String, Object>();
		centerMap.put("lng", setting.getCenterLng());
		centerMap.put("lat", setting.getCenterLat());
		map.put("center", centerMap);
		map.put("radius", setting.getRadius());
		Map<String, Object> warningMap = new HashMap<String, Object>();
		warningMap.put("switch", setting.getWarningSwitch());
		warningMap.put("distance", setting.getWarningDistance());
		map.put("distanceWarning", warningMap);
		map.put("add", setting.getAdd());
		map.put("mul", setting.getMul());
		return map;
	}
	
}
