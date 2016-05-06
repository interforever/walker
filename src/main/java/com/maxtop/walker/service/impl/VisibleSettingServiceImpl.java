
package com.maxtop.walker.service.impl;

import java.util.List;

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
		if (CollectionUtils.isEmpty(visibleSettingDao.getVisibleSetting(subject, object))) {
			visibleSettingDao.addVisibleSetting(subject, object, visible);
			visibleSettingRepository.addVisibleSetting(subject, object, visible);
		} else {
			visibleSettingDao.updateVisibleSetting(subject, object, visible);
			visibleSettingRepository.getVisibleSetting(subject, object).setVisible(visible);
		}
	}
	
}
