
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
import com.maxtop.walker.model.Player;
import com.maxtop.walker.model.Player.Role;
import com.maxtop.walker.model.Player.Status;
import com.maxtop.walker.service.HttpClientService;
import com.maxtop.walker.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {
	
	@Value("${player.list.url}")
	private String playerListUrl;
	
	@Value("${player.info.url}")
	private String playerInfoUrl;
	
	@Value("${player.add.url}")
	private String playerAddUrl;
	
	@Value("${player.update.url}")
	private String playerUpdateUrl;
	
	@Value("${player.delete.url}")
	private String playerDeleteUrl;
	
	@Value("${player.tudou.add.url}")
	private String playerTudouAddUrl;
	
	@Value("${player.audience.count.url}")
	private String playerAudienceCountUrl;
	
	@Value("${player.audience.avatar.url}")
	private String playerAudienceAvatarUrl;
	
	@Value("${player.audience.avatar.limit}")
	private Integer playerAudienceAvatarLimit;
	
	@Value("${audience.faker.avatar.url}")
	private String audienceFakerAvatarUrl;
	
	@Value("${audience.faker.avatar.count}")
	private Integer audienceFakerAvatarCount;
	
	@Autowired
	private HttpClientService httpClientService;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private PlayerItemRepository playerItemRepository;
	
	public List<Player> list() {
		List<Player> players = new ArrayList<Player>();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) httpClientService.executeGetService(playerListUrl, null);
		if (!"0".equals((String) map.get("code"))) return null;
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> playerMaps = (List<Map<String, Object>>) map.get("data");
		for (Map<String, Object> playerMap : playerMaps) {
			Player player = new Player();
			player.setStatus(Status.getByCode((String) playerMap.get("status")).getName());
			player.setRole(Role.getByCode((String) playerMap.get("role")).getName());
			player.setTel((String) playerMap.get("tel"));
			player.setName((String) playerMap.get("name"));
			player.setTudou((String) playerMap.get("tudou"));
			if (playerMap.get("rank") != null) player.setRank(((Number) playerMap.get("rank")).intValue());
			player.setZbid((String) playerMap.get("zbid"));
			player.setPlayerid((String) playerMap.get("playerid"));
			player.setMoney((String) playerMap.get("money"));
			if (playerMap.get("money_rank") != null) player.setMoneyRank(((Number) playerMap.get("money_rank")).intValue());
			player.setZburl((String) playerMap.get("zburl"));
			player.setJail_time((String) playerMap.get("jail_time"));
			player.setShowForUser((String) playerMap.get("showForUser"));
			player.setAvatar((String) playerMap.get("avatar"));
			player.setLat((String) playerMap.get("lat"));
			player.setRoomId((String) playerMap.get("roomId"));
			player.setLng((String) playerMap.get("lng"));
			player.setShowForPlayer((String) playerMap.get("showForPlayer"));
			player.setDescription((String) playerMap.get("description"));
			players.add(player);
			player.setAudience(getPlayerAudienceCount(player.getPlayerid()));
		}
		return players;
	}
	
	private int getPlayerAudienceCount(String playerid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("playerid", playerid);
		@SuppressWarnings("unchecked")
		Map<String, Object> audienceMap = (Map<String, Object>) httpClientService.executePostService(playerAudienceCountUrl, paramMap);
		if (audienceMap == null) return 0;
		if (!audienceMap.containsKey("code")) return 0;
		if (((Number) audienceMap.get("code")).intValue() != 0) return 0;
		@SuppressWarnings("unchecked")
		Map<String, Object> audienceCountMap = (Map<String, Object>) audienceMap.get("data");
		if (audienceCountMap == null) return 0;
		if (!audienceCountMap.containsKey("count")) return 0;
		return ((Number) audienceCountMap.get("count")).intValue();
	}
	
	public void add(Map<String, Object> parameters) {
		Map<String, String> paramMap = new HashMap<String, String>();
		if (parameters.containsKey("playerid")) paramMap.put("playerid", (String) parameters.get("playerid"));
		if (parameters.containsKey("avatar")) paramMap.put("avatar", (String) parameters.get("avatar"));
		if (parameters.containsKey("name")) paramMap.put("name", (String) parameters.get("name"));
		if (parameters.containsKey("role")) paramMap.put("role", (String) parameters.get("role"));
		if (parameters.containsKey("tel")) paramMap.put("tel", (String) parameters.get("tel"));
		if (parameters.containsKey("zburl")) paramMap.put("zburl", (String) parameters.get("zburl"));
		if (parameters.containsKey("room_id")) paramMap.put("room_id", (String) parameters.get("room_id"));
		@SuppressWarnings("unchecked")
		Map<String, Object> result = (Map<String, Object>) httpClientService.executeGetService(playerAddUrl, paramMap);
		if (!"0".equals((String) result.get("code"))) throw new RuntimeException((String) result.get("msg"));
		playerRepository.refresh();
		playerItemRepository.refresh();
	}
	
	public void update(String playerid, Map<String, Object> parameters) {
		if (CollectionUtils.isEmpty(parameters)) return;
		Player player = playerRepository.getById(playerid);
		if (parameters.containsKey("lng")) player.setLng((String) parameters.get("lng"));
		if (parameters.containsKey("lat")) player.setLat((String) parameters.get("lat"));
		if (parameters.containsKey("role")) player.setRole((String) parameters.get("role"));
		if (parameters.containsKey("status")) player.setStatus((String) parameters.get("status"));
		if (parameters.containsKey("tudou")) {
			Map<String, Object> postParameters = new HashMap<String, Object>();
			postParameters.put("playerid", Integer.parseInt(playerid));
			postParameters.put("tudou", ((Number) parameters.get("tudou")).intValue());
			httpClientService.executePostService(playerTudouAddUrl, postParameters);
			Map<String, String> getParameters = new HashMap<String, String>();
			getParameters.put("playerid", playerid);
			@SuppressWarnings("unchecked")
			Map<String, Object> playerInfoMap = (Map<String, Object>) httpClientService.executeGetService(playerInfoUrl, getParameters);
			if (((Number) playerInfoMap.get("code")).intValue() == 0) {
				@SuppressWarnings("unchecked")
				Map<String, Object> playerMap = (Map<String, Object>) playerInfoMap.get("data");
				player.setTudou((String) playerMap.get("tudou"));
			}
		}
	}
	
	public void delete(String playerid) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("playerid", playerid);
		@SuppressWarnings("unchecked")
		Map<String, Object> result = (Map<String, Object>) httpClientService.executeGetService(playerDeleteUrl, paramMap);
		if (!"0".equals((String) result.get("code"))) return;
		playerRepository.removePlayer(playerid);
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
	
}
