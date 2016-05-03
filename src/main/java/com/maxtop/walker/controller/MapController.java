
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
import com.maxtop.walker.model.Player;
import com.maxtop.walker.model.VisibleSetting;
import com.maxtop.walker.service.PlayerService;

@RestController
@RequestMapping(value = "/map")
public class MapController {
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private VisibleSettingRepository visibleSettingRepository;
	
	@RequestMapping(value = "/{playerid}", method = RequestMethod.GET)
	public Map<String,Object> getMapElements(@PathVariable String playerid) {
		List<Map<String, Object>> elements = new ArrayList<Map<String, Object>>();
		for (Player player : playerRepository.list()) {
			String role = player.getRole();
			if ("×·²¶Õß".equals(role) || "ÌÓÍöÕß".equals(role) || "ºò²¹Õß".equals(role)) {
				VisibleSetting visibleSetting = visibleSettingRepository.getVisibleSetting(playerid, player.getPlayerid());
				if (visibleSetting == null || visibleSetting.getVisible() == 0) continue;
			}
			Map<String, Object> element = new HashMap<String, Object>();
			element.put("playerid", player.getPlayerid());
			element.put("name", player.getName());
			element.put("avatar", player.getAvatar());
			element.put("status", player.getStatus());
			element.put("role", player.getRole());
			element.put("tel", player.getTel());
			element.put("lng", player.getLng());
			element.put("lat", player.getLat());
			elements.add(element);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("data", elements);
		return map;
	}
	
	@RequestMapping(value = "/{playerid}", method = RequestMethod.POST)
	public void updateMapElement(@PathVariable String playerid, @RequestBody Map<String, Object> parameters) {
		playerService.update(playerid, parameters);
	}
	
}
