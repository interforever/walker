
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
	
	@Value("${player.list.url}")
	private String playerListUrl;
	
	@Value("${player.info.url}")
	private String playerInfoUrl;
	
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
	
	public List<Player> list() {
		List<Player> players = new ArrayList<Player>();
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) httpClientService.executeGetService(playerListUrl, null);
		if (!"0".equals((String) map.get("code"))) return null;
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> playerMaps = (List<Map<String, Object>>) map.get("data");
		for (Map<String, Object> playerMap : playerMaps) {
			Player player = new Player();
			String status = (String) playerMap.get("status");
			if ("1".equals(status)) {
				player.setStatus("正常");
			} else if ("2".equals(status)) {
				player.setStatus("入狱");
			} else {
				player.setStatus("淘汰");
			}
			String role = (String) playerMap.get("role");
			if ("1".equals(role)) {
				player.setRole("逃亡者");
			} else if ("2".equals(role)) {
				player.setRole("追捕者");
			} else if ("3".equals(role)) {
				player.setRole("候补者");
			} else if ("4".equals(role)) {
				player.setRole("基地");
			} else if ("5".equals(role)) {
				player.setRole("监狱");
			} else if ("6".equals(role)) {
				player.setRole("赌场");
			} else if ("7".equals(role)) {
				player.setRole("安全屋");
			} else if ("8".equals(role)) {
				player.setRole("角斗场");
			} else if ("9".equals(role)) {
				player.setRole("移动商贩");
			} else if ("10".equals(role)) {
				player.setRole("天眼");
			}
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
	
	public void add(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		
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
