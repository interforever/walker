
package com.maxtop.walker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maxtop.walker.cache.PlayerRepository;
import com.maxtop.walker.cache.VisibleSettingRepository;
import com.maxtop.walker.model.Notification;
import com.maxtop.walker.model.Player;
import com.maxtop.walker.model.Player.Role;
import com.maxtop.walker.model.Setting;
import com.maxtop.walker.service.NotificationService;
import com.maxtop.walker.service.PlayerItemService;
import com.maxtop.walker.service.SettingService;
import com.maxtop.walker.service.VisibleSettingService;

@RestController
@RequestMapping(value = "/setting")
public class SettingController {
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private PlayerItemService playerItemService;
	
	@Autowired
	private SettingService settingService;
	
	@Autowired
	private VisibleSettingService visibleSettingService;
	
	@Autowired
	private VisibleSettingRepository visibleSettingRepository;
	
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
		warningMap.put("warningSwitch", setting.getWarningSwitch());
		warningMap.put("distance", setting.getWarningDistance());
		map.put("distanceWarning", warningMap);
		map.put("add", setting.getAdd());
		map.put("mul", setting.getMul());
		return map;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public void updateSettings(@RequestBody Map<String, Object> parameters) {
		settingService.updateSettings(parameters);
	}
	
	@RequestMapping(value = "/notification/", method = RequestMethod.GET)
	public List<Notification> getNotifications() {
		return notificationService.list();
	}
	
	@RequestMapping(value = "/notification/", method = RequestMethod.POST)
	public void publishNotification(@RequestBody String content) {
		notificationService.publishNotification(content);
	}
	
	@RequestMapping(value = "/visible/", method = RequestMethod.GET)
	public Map<String, Object> getVisibleSettings() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Player> players = new ArrayList<Player>();
		for (Player player : playerRepository.list()) {
			if (Role.isBuilding(Role.getByName(player.getRole()))) continue;
			players.add(player);
		}
		Player audience = new Player();
		audience.setPlayerid("-1");
		audience.setName("¹ÛÖÚ");
		players.add(audience);
		map.put("players", players);
		map.put("visibleSettings", visibleSettingRepository.getVisibleSettings());
		return map;
	}
	
	@RequestMapping(value = "/visible/{subject}/{object}/{visible}", method = RequestMethod.GET)
	public void switchVisible(@PathVariable String subject, @PathVariable String object, @PathVariable Integer visible) {
		visibleSettingService.switchVisible(subject, object, visible);
	}
	
	@RequestMapping(value = "/visible/all/on", method = RequestMethod.GET)
	public void setAllVisible() {
		visibleSettingService.setAllVisible();
	}
	
	@RequestMapping(value = "/visible/all/off", method = RequestMethod.GET)
	public void setAllInvisible() {
		visibleSettingService.setAllInvisible();
	}
	
	@RequestMapping(value = "/playeritem/", method = RequestMethod.GET)
	public Map<String, Object> getPlayerItemSettings() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Player> players = new ArrayList<Player>();
		for (Player player : playerRepository.list()) {
			if (Role.isBuilding(Role.getByName(player.getRole()))) continue;
			players.add(player);
		}
		map.put("players", players);
		map.put("items", playerItemService.getSimpleList());
		map.put("allow", playerItemService.getAllowList());
		return map;
	}
	
	@RequestMapping(value = "/playeritem/{playerid}/{itemid}/{allow}", method = RequestMethod.GET)
	public void setPlayerItemAllowable(@PathVariable String playerid, @PathVariable String itemid, @PathVariable Integer allow) {
		playerItemService.setPlayerItemAllowable(playerid, itemid, allow);
	}
	
	@RequestMapping(value = "/playeritem/all/allow", method = RequestMethod.GET)
	public void setAllPlayerItemsAllow() {
		playerItemService.setAllPlayerItemsAllow();
	}
	
	@RequestMapping(value = "/playeritem/all/disallow", method = RequestMethod.GET)
	public void setAllPlayerItemsDisallow() {
		playerItemService.setAllPlayerItemsDisallow();
	}
	
}
