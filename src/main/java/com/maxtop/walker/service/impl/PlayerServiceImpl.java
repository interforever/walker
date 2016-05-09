
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
	
	@Value("${player.list.url:http://api.zb.youku.com/api/v1/player/all}")
	private String playerListUrl;
	
	@Value("${player.info.url:http://api.zb.youku.com/api/v1/player/info}")
	private String playerInfoUrl;
	
	@Value("${player.add.url:http://api.zb.youku.com/api/v1/player/add}")
	private String playerAddUrl;
	
	@Value("${player.update.url:http://api.zb.youku.com/api/v1/player/update}")
	private String playerUpdateUrl;
	
	@Value("${player.delete.url:http://api.zb.youku.com/api/v1/player/del}")
	private String playerDeleteUrl;
	
	@Value("${player.tudou.add.url:http://api.zb.youku.com/api/v1/user/tudou/add}")
	private String playerTudouAddUrl;
	
	@Value("${player.audience.count.url:http://api.zb.youku.com/api/v1/player/online/num}")
	private String playerAudienceCountUrl;
	
	@Value("${player.audience.avatar.url:http://api.zb.youku.com/api/v1/online/user}")
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
	
	@Autowired
	private PlayerItemRepository playerItemRepository;
	
	public List<Player> list() {
		List<Player> players = new ArrayList<Player>();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) httpClientService.executeGetService(playerListUrl, null);
		if (!"0".equals(map.get("code").toString())) return null;
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
			player.setShowForUser((String) playerMap.get("show_for_user"));
			player.setAvatar((String) playerMap.get("avatar"));
			player.setLat((String) playerMap.get("lat"));
			player.setRoomId((String) playerMap.get("room_id"));
			player.setLng((String) playerMap.get("lng"));
			player.setShowForPlayer((String) playerMap.get("show_for_player"));
			player.setDescription((String) playerMap.get("description"));
			players.add(player);
			player.setAudience(getPlayerAudienceCount(player.getPlayerid()));
		}
		return players;
	}
	
	public int getPlayerAudienceCount(String playerid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("playerid", playerid);
		@SuppressWarnings("unchecked")
		Map<String, Object> audienceMap = (Map<String, Object>) httpClientService.executePostService(playerAudienceCountUrl, paramMap);
		if (audienceMap == null) return 0;
		if (!audienceMap.containsKey("code")) return 0;
		if (!"0".equals(audienceMap.get("code").toString())) return 0;
		@SuppressWarnings("unchecked")
		Map<String, Object> audienceCountMap = (Map<String, Object>) audienceMap.get("data");
		if (audienceCountMap == null) return 0;
		if (!audienceCountMap.containsKey("count")) return 0;
		return ((Number) audienceCountMap.get("count")).intValue();
	}
	
	public void add(Map<String, Object> parameters) {
		Map<String, String> paramMap = new HashMap<String, String>();
		if (parameters.containsKey("playerid")) paramMap.put("playerid", (String) parameters.get("playerid"));
		if (parameters.containsKey("zbid")) paramMap.put("zbid", (String) parameters.get("zbid"));
		if (parameters.containsKey("avatar")) paramMap.put("avatar", (String) parameters.get("avatar"));
		if (parameters.containsKey("name")) paramMap.put("name", (String) parameters.get("name"));
		if (parameters.containsKey("role")) paramMap.put("role", Role.getByName((String) parameters.get("role")).getCode());
		if (parameters.containsKey("tel")) paramMap.put("tel", (String) parameters.get("tel"));
		if (parameters.containsKey("zburl")) paramMap.put("zburl", (String) parameters.get("zburl"));
		if (parameters.containsKey("room_id")) paramMap.put("room_id", (String) parameters.get("room_id"));
		if (parameters.containsKey("lng")) paramMap.put("lng", (String) parameters.get("lng"));
		if (parameters.containsKey("lat")) paramMap.put("lat", (String) parameters.get("lat"));
		@SuppressWarnings("unchecked")
		Map<String, Object> result = (Map<String, Object>) httpClientService.executeGetService(playerAddUrl, paramMap);
		if (!"0".equals(result.get("code").toString())) throw new RuntimeException((String) result.get("msg"));
		playerRepository.refresh();
		playerItemRepository.refresh();
	}
	
	public void update(String playerid, Map<String, Object> parameters) {
		if (CollectionUtils.isEmpty(parameters)) return;
		Player player = playerRepository.getById(playerid);
		if (parameters.containsKey("tudou")) {
			Map<String, Object> postParameters = new HashMap<String, Object>();
			postParameters.put("playerid", Integer.parseInt(playerid));
			postParameters.put("tudou", ((Number) parameters.get("tudou")).intValue());
			httpClientService.executePostService(playerTudouAddUrl, postParameters);
			Map<String, String> getParameters = new HashMap<String, String>();
			getParameters.put("playerid", playerid);
			@SuppressWarnings("unchecked")
			Map<String, Object> playerInfoMap = (Map<String, Object>) httpClientService.executeGetService(playerInfoUrl, getParameters);
			if ("0".equals(playerInfoMap.get("code").toString())) {
				@SuppressWarnings("unchecked")
				Map<String, Object> playerMap = (Map<String, Object>) playerInfoMap.get("data");
				player.setTudou((String) playerMap.get("tudou"));
			}
		} else {
			Map<String, Object> postParameters = new HashMap<String, Object>();
			postParameters.put("playerid", Integer.parseInt(playerid));
			if (parameters.containsKey("lng")) postParameters.put("lng", (String) parameters.get("lng"));
			if (parameters.containsKey("lat")) postParameters.put("lat", (String) parameters.get("lat"));
			if (parameters.containsKey("role")) postParameters.put("role", Role.getByName((String) parameters.get("role")).getCode());
			if (parameters.containsKey("status")) postParameters.put("status", Status.getByName((String) parameters.get("status")).getCode());
			if (parameters.containsKey("name")) postParameters.put("name", (String) parameters.get("name"));
			if (parameters.containsKey("tel")) postParameters.put("tel", (String) parameters.get("tel"));
			if (parameters.containsKey("avatar")) postParameters.put("avatar", (String) parameters.get("avatar"));
			if (parameters.containsKey("show_for_user")) postParameters.put("show_for_user", (String) parameters.get("show_for_user"));
			@SuppressWarnings("unchecked")
			Map<String, Object> result = (Map<String, Object>) httpClientService.executePostService(playerUpdateUrl, postParameters);
			if (!"0".equals(result.get("code").toString())) throw new RuntimeException((String) result.get("msg"));
			if (parameters.containsKey("lng")) player.setLng((String) parameters.get("lng"));
			if (parameters.containsKey("lat")) player.setLat((String) parameters.get("lat"));
			if (parameters.containsKey("role")) player.setRole((String) parameters.get("role"));
			if (parameters.containsKey("status")) player.setStatus((String) parameters.get("status"));
			if (parameters.containsKey("name")) player.setName((String) parameters.get("name"));
			if (parameters.containsKey("tel")) player.setTel((String) parameters.get("tel"));
			if (parameters.containsKey("avatar")) player.setAvatar((String) parameters.get("avatar"));
			if (parameters.containsKey("show_for_user")) player.setShowForUser((String) parameters.get("show_for_user"));
		}
	}
	
	public void delete(String playerid) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("playerid", playerid);
		@SuppressWarnings("unchecked")
		Map<String, Object> result = (Map<String, Object>) httpClientService.executeGetService(playerDeleteUrl, paramMap);
		if (!"0".equals(result.get("code").toString())) return;
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
