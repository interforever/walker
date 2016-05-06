
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

import com.maxtop.walker.cache.PlayerItemRepository;
import com.maxtop.walker.cache.PlayerRepository;
import com.maxtop.walker.model.Player;
import com.maxtop.walker.model.Player.Role;
import com.maxtop.walker.service.PlayerItemService;
import com.maxtop.walker.service.PlayerService;

@RestController
@RequestMapping(value = "/player")
public class PlayerController {
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private PlayerItemRepository playerItemRepository;
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private PlayerItemService playerItemService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Map<String, List<Map<String, Object>>> getList() {
		Map<String, List<Map<String, Object>>> data = new HashMap<String, List<Map<String, Object>>>();
		for (Player player : playerRepository.list()) {
			if (Role.isBuilding(Role.getByName(player.getRole()))) continue;
			List<Map<String, Object>> list = data.get(player.getRole());
			if (list == null) {
				list = new ArrayList<Map<String, Object>>();
				data.put(player.getRole(), list);
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("player", player);
			playerItemRepository.refresh();
			map.put("items", playerItemRepository.getItemsById(player.getPlayerid()));
			list.add(map);
		}
		return data;
	}
	
	@RequestMapping(value = "/building/list", method = RequestMethod.GET)
	public List<Player> getBuildings() {
		List<Player> buildings = new ArrayList<Player>();
		for (Player player : playerRepository.list()) {
			if (Role.isPlayer(Role.getByName(player.getRole()))) continue;
			buildings.add(player);
		}
		return buildings;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public void addPlayer(@RequestBody Map<String, Object> parameters) {
		playerService.add(parameters);
	}
	
	@RequestMapping(value = "/{playerid}", method = RequestMethod.DELETE)
	public void removePlayer(@PathVariable String playerid) {
		playerService.delete(playerid);
	}
	
	@RequestMapping(value = "/{playerid}", method = RequestMethod.POST)
	public void updatePlayer(@PathVariable String playerid, @RequestBody Map<String, Object> parameters) {
		playerService.update(playerid, parameters);
	}
	
	@RequestMapping(value = "/playerid/{tel}", method = RequestMethod.GET)
	public Map<String, Object> getPlayeridByTel(@PathVariable String tel) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Player player : playerRepository.list()) {
			if (tel.equals(player.getTel())) {
				map.put("tel", player.getPlayerid());
				break;
			}
		}
		return map;
	}
	
	@RequestMapping(value = "/{playerid}/{itemid}", method = RequestMethod.POST)
	public void updatePlayerItem(@PathVariable String playerid, @PathVariable String itemid, @RequestBody Map<String, Object> parameters) {
		playerItemService.update(playerid, itemid, parameters);
	}
	
	@RequestMapping(value = "/{playerid}/audience/avatar", method = RequestMethod.GET)
	public Map<String, Object> getAudienceAvatars(@PathVariable String playerid) {
		List<Map<String, Object>> avatarMaps = new ArrayList<Map<String, Object>>();
		for (String url : playerService.getAudienceAvatars(playerid)) {
			Map<String, Object> avatarMap = new HashMap<String, Object>();
			avatarMap.put("url", url);
			avatarMaps.add(avatarMap);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("avatars", avatarMaps);
		return map;
	}
	
}
