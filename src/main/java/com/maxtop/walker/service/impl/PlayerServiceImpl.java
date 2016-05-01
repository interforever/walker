
package com.maxtop.walker.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.maxtop.walker.cache.PlayerRepository;
import com.maxtop.walker.model.Player;
import com.maxtop.walker.service.HttpClientService;
import com.maxtop.walker.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {
	
	@Value("${player.list.url:http://114.80.120.78:9999/api/v1/player/money/rank}")
	private String playerListUrl;
	
	@Value("${player.audience.count.url:http://114.80.120.78:9999/api/v1/zb/online/user}")
	private String playerAudienceCountUrl;
	
	@Value("${player.audience.avatar.url:http://114.80.120.78:9999/api/v1/online/user}")
	private String playerAudienceAvatarUrl;
	
	@Value("${player.audience.avatar.limit:20}")
	private Integer playerAudienceAvatarLimit;
	
	@Value("${audience.faker.avatar.url:http://static.youku.com/user/img/avatar/50/}")
	private String audienceFakerAvatarUrl;
	
	@Value("${audience.faker.avatar.count:57}")
	private Integer audienceFakerAvatarCount;
	
	@Autowired
	private HttpClientService httpClientService;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	public List<Player> list() {
		List<Player> players = new ArrayList<Player>();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) httpClientService.executeGetService(playerListUrl, null);
		if (!"0".equals((String) map.get("code"))) return null;
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> playerMaps = (List<Map<String, Object>>) map.get("data");
		for (Map<String, Object> playerMap : playerMaps) {
			Player player = new Player();
			player.setStatus((String) playerMap.get("status"));
			if (playerMap.get("moneyRank") != null) player.setMoneyRank(((Number) playerMap.get("moneyRank")).intValue());
			player.setTel((String) playerMap.get("tel"));
			player.setName((String) playerMap.get("name"));
			player.setTudou((String) playerMap.get("tudou"));
			player.setZbid((String) playerMap.get("zbid"));
			player.setPlayerid((String) playerMap.get("playerid"));
			player.setMoney((String) playerMap.get("money"));
			player.setZburl((String) playerMap.get("zburl"));
			player.setJail_time((String) playerMap.get("jail_time"));
			if (playerMap.get("rank") != null) player.setRank(((Number) playerMap.get("rank")).intValue());
			player.setShowForUser((String) playerMap.get("showForUser"));
			player.setRole((String) playerMap.get("role"));
			player.setAvatar((String) playerMap.get("avatar"));
			player.setLat((String) playerMap.get("lat"));
			player.setRoomId((String) playerMap.get("roomId"));
			player.setLng((String) playerMap.get("lng"));
			player.setShowForPlayer((String) playerMap.get("showForPlayer"));
			player.setDescription((String) playerMap.get("description"));
			players.add(player);
			Map<String, String> serviceParam = new HashMap<String, String>();
			serviceParam.put("zbid", player.getZbid());
			@SuppressWarnings("unchecked")
			Map<String, Object> audienceMap = (Map<String, Object>) httpClientService.executeGetService(playerAudienceCountUrl, serviceParam);
			if (((Number) audienceMap.get("code")).intValue() != 0) continue;
			@SuppressWarnings("unchecked")
			Map<String, Object> audienceCountMap = (Map<String, Object>) audienceMap.get("data");
			if (audienceCountMap.get("count") != null) player.setAudience(((Number) audienceCountMap.get("count")).intValue());
		}
		return players;
	}
	
	public List<String> getAudienceAvatars(String playerid) {
		int audienceCount = playerRepository.getById(playerid).getAudience();
		if (audienceCount < playerAudienceAvatarLimit) audienceCount = playerAudienceAvatarLimit;
		List<String> urls = new ArrayList<String>();
		for (int i = 0; i < audienceCount; i++) {
			int avatarId = (int) (Math.random() * audienceFakerAvatarCount) + 1;
			urls.add(audienceFakerAvatarUrl + avatarId + ".jpg");
		}
		return urls;
	}
	
	public void update(String playerid, Map<String, Object> parameters) {
		if (CollectionUtils.isEmpty(parameters)) return;
		Player player = playerRepository.getById(playerid);
		if (parameters.containsKey("lng")) player.setLng((String) parameters.get("lng"));
		if (parameters.containsKey("lat")) player.setLat((String) parameters.get("lat"));
		if (parameters.containsKey("role")) player.setRole((String) parameters.get("role"));
		if (parameters.containsKey("status")) player.setStatus((String) parameters.get("status"));
	}
	
}
