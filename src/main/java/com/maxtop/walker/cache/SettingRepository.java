
package com.maxtop.walker.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxtop.walker.dao.SettingDao;
import com.maxtop.walker.model.Setting;
import com.maxtop.walker.service.SettingService;

@Service
public class SettingRepository implements InitializingBean, DisposableBean {
	
	private static final Log logger = LogFactory.getLog(PlayerRepository.class);
	
	private Setting setting = null;
	
	@Autowired
	private SettingDao settingDao;
	
	@Autowired
	private SettingService settingService;
	
	private void refresh() {
		logger.info("Start refreshing settings!");
		Setting setting = settingDao.getSettings();
		if (setting == null) {
			logger.info("Refresh settings failed!");
		} else {
			clear();
			this.setting = new Setting();
			BeanUtils.copyProperties(setting, this.setting);
			logger.info("Refreshing settings successfully!");
		}
	}
	
	private void clear() {
		setting = null;
	}
	
	public Setting getSettings() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
    }
	
	public void destroy() throws Exception {
		clear();
	}
	
	public void afterPropertiesSet() throws Exception {
		refresh();
	}
	
}
