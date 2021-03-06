
package com.maxtop.walker.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.maxtop.walker.cache.VisibleSettingRepository;
import com.maxtop.walker.dao.VisibleSettingDao;
import com.maxtop.walker.model.VisibleSetting;
import com.maxtop.walker.service.PlayerService;
import com.maxtop.walker.service.VisibleSettingService;

@Service
public class VisibleSettingServiceImpl implements VisibleSettingService {
	
	@Autowired
	private VisibleSettingDao visibleSettingDao;
	
	@Autowired
	private VisibleSettingRepository visibleSettingRepository;
	
	@Autowired
	private PlayerService playerService;
	
	public List<VisibleSetting> list() {
		return visibleSettingDao.getVisibleSettings();
	}
	
	public void switchVisible(String subject, String object, Integer visible) {
		if ("-1".equals(subject)) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("show_for_user", visible.toString());
			playerService.update(object, parameters);
		}
		if (CollectionUtils.isEmpty(visibleSettingDao.getVisibleSetting(subject, object))) {
			visibleSettingDao.addVisibleSetting(subject, object, visible);
			visibleSettingRepository.addVisibleSetting(subject, object, visible);
		} else {
			visibleSettingDao.updateVisibleSetting(subject, object, visible);
			visibleSettingRepository.getVisibleSetting(subject, object).setVisible(visible);
		}
	}
	
	public void setAllVisible() {
		//		visibleSettingDao.setAllVisible();
		//		visibleSettingRepository.setAllVisible();
	}
	
	public void setAllInvisible() {
		//		visibleSettingDao.setAllInvisible();
		//		visibleSettingRepository.setAllInvisible();
	}
	
}
