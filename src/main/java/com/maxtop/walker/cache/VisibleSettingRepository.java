
package com.maxtop.walker.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.maxtop.walker.dao.VisibleSettingDao;
import com.maxtop.walker.model.VisibleSetting;
import com.maxtop.walker.service.VisibleSettingService;

@Service
public class VisibleSettingRepository implements InitializingBean, DisposableBean {
	
	private static final Log logger = LogFactory.getLog(VisibleSettingRepository.class);
	
	private List<VisibleSetting> visibleSettings = new ArrayList<VisibleSetting>();
	
	private Map<String, Map<String, VisibleSetting>> subjectMap = new HashMap<String, Map<String, VisibleSetting>>();
	
	private Map<String, Map<String, VisibleSetting>> objectMap = new HashMap<String, Map<String, VisibleSetting>>();
	
	@Autowired
	private VisibleSettingDao visibleSettingDao;
	
	@Autowired
	private VisibleSettingService visibleSettingService;
	
	private void refresh() {
		logger.info("Start refreshing settings!");
		List<VisibleSetting> visibleSettings = visibleSettingDao.getVisibleSettings();
		if (CollectionUtils.isEmpty(visibleSettings)) {
			logger.info("Refresh settings failed!");
		} else {
			clear();
			for (VisibleSetting visibleSetting : visibleSettings) {
				String subject = visibleSetting.getSubjectId();
				String object = visibleSetting.getObjectId();
				this.visibleSettings.add(visibleSetting);
				Map<String, VisibleSetting> sObjectMap = subjectMap.get(subject);
				if (sObjectMap == null) {
					sObjectMap = new HashMap<String, VisibleSetting>();
					subjectMap.put(subject, sObjectMap);
				}
				sObjectMap.put(object, visibleSetting);
				Map<String, VisibleSetting> oSubjectMap = objectMap.get(object);
				if (oSubjectMap == null) {
					oSubjectMap = new HashMap<String, VisibleSetting>();
					objectMap.put(object, oSubjectMap);
				}
				oSubjectMap.put(subject, visibleSetting);
			}
			logger.info("Refreshing settings successfully!");
		}
	}
	
	private void clear() {
		visibleSettings.clear();
		subjectMap.clear();
		objectMap.clear();
	}
	
	public List<VisibleSetting> getVisibleSettings() {
		return visibleSettings;
	}
	
	public VisibleSetting getVisibleSetting(String subject, String object) {
		return subjectMap.get(subject).get(object);
	}
	
	public void addVisibleSetting(String subject, String object, Integer visible) {
		VisibleSetting visibleSetting = new VisibleSetting();
		visibleSetting.setSubjectId(subject);
		visibleSetting.setObjectId(object);
		visibleSetting.setVisible(visible);
		visibleSettings.add(visibleSetting);
		Map<String, VisibleSetting> sObjectMap = subjectMap.get(subject);
		if (sObjectMap == null) {
			sObjectMap = new HashMap<String, VisibleSetting>();
			subjectMap.put(subject, sObjectMap);
		}
		sObjectMap.put(object, visibleSetting);
		Map<String, VisibleSetting> oSubjectMap = objectMap.get(object);
		if (oSubjectMap == null) {
			oSubjectMap = new HashMap<String, VisibleSetting>();
			objectMap.put(object, oSubjectMap);
		}
		oSubjectMap.put(subject, visibleSetting);
	}
	
	public void destroy() throws Exception {
		clear();
	}
	
	public void afterPropertiesSet() throws Exception {
		refresh();
	}
	
}