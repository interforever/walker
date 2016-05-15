
package com.maxtop.walker.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.maxtop.walker.cache.PlayerItemRepository;
import com.maxtop.walker.cache.PlayerRepository;
import com.maxtop.walker.dao.PlayerItemDao;
import com.maxtop.walker.model.Player;
import com.maxtop.walker.model.PlayerItem;
import com.maxtop.walker.service.HttpClientService;
import com.maxtop.walker.service.PlayerItemService;

@Service
public class PlayerItemServiceImpl implements PlayerItemService {
	
	@Value("${player.item.list.url:http://api.zb.youku.com/api/v1/zb/player/fundings}")
	private String playerItemListUrl;
	
	@Value("${player.item.allow.list.url:http://api.zb.youku.com/api/v1/player/fund/allow/info}")
	private String playerItemAllowListUrl;
	
	@Value("${player.item.allow.url:http://api.zb.youku.com/api/v1/player/fund/allow}")
	private String playerItemAllowUrl;
	
	@Value("${player.item.allows.url:http://api.zb.youku.com/api/v1/player/funds/allow}")
	private String playerItemAllowsUrl;
	
	@Autowired
	private HttpClientService httpClientService;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private PlayerItemRepository playerItemRepository;
	
	@Autowired
	private PlayerItemDao playerItemDao;
	
	public Map<String, List<PlayerItem>> list() {
		Map<String, List<PlayerItem>> playerItemsMap = new HashMap<String, List<PlayerItem>>();
		for (Player player : playerRepository.list()) {
			playerItemsMap.put(player.getPlayerid(), this.getPlayerItemsById(player.getPlayerid()));
		}
		return playerItemsMap;
	}
	
	public List<Map<String, Object>> getSimpleList() {
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		for (PlayerItem playerItem : playerItemRepository.getItemsById(playerRepository.list().iterator().next().getPlayerid())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", playerItem.getTitle());
			map.put("id", playerItem.getId().toString());
			maps.add(map);
		}
		return maps;
	}
	
	public List<Map<String, Object>> getAllowList() {
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) httpClientService.executeGetService(playerItemAllowListUrl, null);
		if (!"0".equals(map.get("code").toString())) return null;
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> itemMaps = (List<Map<String, Object>>) map.get("data");
		List<Map<String, Object>> allowMaps = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> itemMap : itemMaps) {
			String id = String.valueOf(((Number) itemMap.get("id")).intValue());
			@SuppressWarnings("unchecked")
			Map<String, String> allowInfo = (Map<String, String>) itemMap.get("allow_info");
			for (Entry<String, String> entry : allowInfo.entrySet()) {
				Map<String, Object> allowMap = new HashMap<String, Object>();
				allowMap.put("id", id);
				allowMap.put("playerid", entry.getKey());
				allowMap.put("allow", entry.getValue());
				allowMaps.add(allowMap);
			}
		}
		return allowMaps;
	}
	
	private List<PlayerItem> getPlayerItemsById(String playerid) {
		List<PlayerItem> playerItems = new ArrayList<PlayerItem>();
		Map<String, String> serviceParam = new HashMap<String, String>();
		serviceParam.put("playerid", playerid);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) httpClientService.executeGetService(playerItemListUrl, serviceParam);
		if (map == null || !map.containsKey("code") || !"0".equals(map.get("code").toString())) return null;
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> playerItemCategoryMaps = (List<Map<String, Object>>) map.get("data");
		for (Map<String, Object> playerItemCategoryMap : playerItemCategoryMaps) {
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> playerItemMaps = (List<Map<String, Object>>) playerItemCategoryMap.get("fundList");
			if (!CollectionUtils.isEmpty(playerItemMaps)) {
				for (Map<String, Object> playerItemMap : playerItemMaps) {
					PlayerItem playerItem = new PlayerItem();
					if (playerItemCategoryMap.get("categoryId") != null) playerItem.setCategoryId(((Number) playerItemCategoryMap.get("categoryId")).intValue());
					if (playerItemMap.get("itemId") != null) playerItem.setItemId(((Number) playerItemMap.get("itemId")).intValue());
					playerItem.setHeadImage((String) playerItemMap.get("headImage"));
					if (playerItemMap.get("finalItemId") != null) playerItem.setFinalItemId(((Number) playerItemMap.get("finalItemId")).intValue());
					if (playerItemMap.get("mixAmount") != null) playerItem.setMixAmount(((Number) playerItemMap.get("mixAmount")).intValue());
					if (playerItemMap.get("currentAmount") != null) playerItem.setCurrentAmount(((Number) playerItemMap.get("currentAmount")).intValue());
					if (playerItemMap.get("playerNeed") != null) playerItem.setPlayerNeed(((Number) playerItemMap.get("playerNeed")).intValue());
					if (playerItemMap.get("id") != null) playerItem.setId(((Number) playerItemMap.get("id")).intValue());
					if (playerItemMap.get("fundTotalAmount") != null) playerItem.setFundTotalAmount(((Number) playerItemMap.get("fundTotalAmount")).intValue());
					playerItem.setCategoryName((String) playerItemCategoryMap.get("categoryName"));
					@SuppressWarnings("unchecked")
					Map<String, Object> playerItemInfoMap = (Map<String, Object>) playerItemMap.get("itemInfo");
					playerItem.setTitle((String) playerItemInfoMap.get("title"));
					if (playerItemInfoMap.get("price") != null) playerItem.setPrice(((Number) playerItemInfoMap.get("price")).intValue());
					playerItem.setPicUrl((String) playerItemInfoMap.get("picUrl"));
					playerItem.setLabelName((String) playerItemInfoMap.get("labelName"));
					playerItem.setDesc((String) playerItemInfoMap.get("desc"));
					playerItems.add(playerItem);
				}
			}
		}
		return playerItems;
	}
	
	public void update(String playerid, String itemid, Map<String, Object> parameters) {
		if (CollectionUtils.isEmpty(parameters)) return;
		if (parameters.containsKey("usedAmount")) {
			for (PlayerItem playerItem : playerItemRepository.getItemsById(playerid)) {
				if (!itemid.equals(playerItem.getItemId().toString())) continue;
				int usedAmount = ((Number) parameters.get("usedAmount")).intValue();
				int itemId = Integer.parseInt(itemid);
				if (CollectionUtils.isEmpty(playerItemDao.getPlayerItem(playerid, itemId))) {
					playerItemDao.addPlayerItem(playerid, itemId, usedAmount);
				} else {
					playerItemDao.updateUsedAmount(playerid, itemId, usedAmount);
				}
				playerItem.setUsedAmount(usedAmount);
				return;
			}
		}
	}
	
	public void setPlayerItemAllowable(String playerid, String itemid, Integer allow) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("playerid", playerid);
		parameters.put("fund_id", itemid);
		parameters.put("allow", allow);
		httpClientService.executePostService(playerItemAllowUrl, parameters);
	}
	
	public void setAllPlayerItemsAllow() {
		StringBuilder ids = new StringBuilder();
		for (PlayerItem playerItem : playerItemRepository.getItemsById(playerRepository.list().iterator().next().getPlayerid())) {
			ids.append(playerItem.getId()).append(",");
		}
		if (ids.length() > 0) ids.deleteCharAt(ids.length() - 1);
		for (Player player : playerRepository.list()) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("playerid", player.getPlayerid());
			parameters.put("fund_ids", ids.toString());
			parameters.put("allow", 1);
			httpClientService.executePostService(playerItemAllowsUrl, parameters);
		}
	}
	
	public void setAllPlayerItemsDisallow() {
		StringBuilder ids = new StringBuilder();
		for (PlayerItem playerItem : playerItemRepository.getItemsById(playerRepository.list().iterator().next().getPlayerid())) {
			ids.append(playerItem.getId()).append(",");
		}
		if (ids.length() > 0) ids.deleteCharAt(ids.length() - 1);
		for (Player player : playerRepository.list()) {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("playerid", player.getPlayerid());
			parameters.put("fund_ids", ids.toString());
			parameters.put("allow", 2);
			httpClientService.executePostService(playerItemAllowsUrl, parameters);
		}
	}
	
}
