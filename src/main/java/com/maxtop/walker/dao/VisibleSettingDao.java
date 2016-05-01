
package com.maxtop.walker.dao;

import java.util.List;

import com.maxtop.walker.model.VisibleSetting;

public interface VisibleSettingDao {
	
	List<VisibleSetting> getVisibleSettings();
	
	List<VisibleSetting> getVisibleSetting(String subjectId, String objectId);
	
	void addVisibleSetting(String subjectId, String objectId, Integer visible);
	
	void updateVisibleSetting(String subjectId, String objectId, Integer visible);
	
}
