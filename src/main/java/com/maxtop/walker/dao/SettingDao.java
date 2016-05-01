
package com.maxtop.walker.dao;

import com.maxtop.walker.model.Setting;

public interface SettingDao {
	
	Setting getSettings();

	void updateSetting(String centerLng, String centerLat, Integer radius, String warningSwitch, Integer warningDistance, Double mul, Integer add);
	
}
