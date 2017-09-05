
package com.maxtop.walker.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
import com.maxtop.walker.model.Player.Role;
import com.maxtop.walker.model.VisibleSetting;
import com.maxtop.walker.service.PlayerService;

@Api(value = "/map", description = "map")
@RestController
@RequestMapping(value = "/map")
public class MapController {
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private VisibleSettingRepository visibleSettingRepository;
	
	@ApiOperation(value = "getMapElements")
	@RequestMapping(value = "/{playerid}", method = RequestMethod.GET)
	public Map<String, Object> getMapElements(@ApiParam @PathVariable String playerid) {
		List<Map<String, Object>> elements = new ArrayList<Map<String, Object>>();
		for (Player player : playerRepository.list()) {
			Role role = Player.Role.getByName(player.getRole());
			if (role == Role.ESCAPEE || role == Role.CHASER || role == Role.CANDIDATE) {
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", elements);
		return map;
	}
	
	@ApiOperation(value = "updateMapElement")
	@RequestMapping(value = "/{playerid}", method = RequestMethod.POST)
	public void updateMapElement(@ApiParam @PathVariable String playerid, @ApiParam @RequestBody Map<String, Object> parameters) {
		playerService.update(playerid, parameters);
	}
	
}
