
package com.maxtop.walker.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private List<PlayerItem> getPlayerItemsById(String playerid) {
		List<PlayerItem> playerItems = new ArrayList<PlayerItem>();
		Map<String, String> serviceParam = new HashMap<String, String>();
		serviceParam.put("playerid", playerid);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) httpClientService.executeGetService(playerItemListUrl, serviceParam);
		if (map == null || !map.containsKey("code") || !"0".equals((String) map.get("code"))) return null;
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
}
