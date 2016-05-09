
package com.maxtop.walker.model;

public class Player {
	
	private String status;
	
	private Integer moneyRank;
	
	private String tel;
	
	private String name;
	
	private String tudou;
	
	private String zbid;
	
	private String playerid;
	
	private String money;
	
	private String zburl;
	
	private String jail_time;
	
	private Integer rank;
	
	private String showForUser;
	
	private String role;
	
	private String avatar;
	
	private String lat;
	
	private String roomId;
	
	private String lng;
	
	private String showForPlayer;
	
	private String description;
	
	private Integer audience;
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getMoneyRank() {
		return moneyRank;
	}
	
	public void setMoneyRank(Integer moneyRank) {
		this.moneyRank = moneyRank;
	}
	
	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTudou() {
		return tudou;
	}
	
	public void setTudou(String tudou) {
		this.tudou = tudou;
	}
	
	public String getZbid() {
		return zbid;
	}
	
	public void setZbid(String zbid) {
		this.zbid = zbid;
	}
	
	public String getPlayerid() {
		return playerid;
	}
	
	public void setPlayerid(String playerid) {
		this.playerid = playerid;
	}
	
	public String getMoney() {
		return money;
	}
	
	public void setMoney(String money) {
		this.money = money;
	}
	
	public String getZburl() {
		return zburl;
	}
	
	public void setZburl(String zburl) {
		this.zburl = zburl;
	}
	
	public String getJail_time() {
		return jail_time;
	}
	
	public void setJail_time(String jail_time) {
		this.jail_time = jail_time;
	}
	
	public Integer getRank() {
		return rank;
	}
	
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
	public String getShowForUser() {
		return showForUser;
	}
	
	public void setShowForUser(String showForUser) {
		this.showForUser = showForUser;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getLat() {
		return lat;
	}
	
	public void setLat(String lat) {
		this.lat = lat;
	}
	
	public String getRoomId() {
		return roomId;
	}
	
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	public String getLng() {
		return lng;
	}
	
	public void setLng(String lng) {
		this.lng = lng;
	}
	
	public String getShowForPlayer() {
		return showForPlayer;
	}
	
	public void setShowForPlayer(String showForPlayer) {
		this.showForPlayer = showForPlayer;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getAudience() {
		return audience;
	}
	
	public void setAudience(Integer audience) {
		this.audience = audience;
	}
	
	public enum Role {
		
		ESCAPEE("1", "Êó"), CHASER("2", "Ã¨"), CANDIDATE("3", "ºò²¹Õß"), BASE("4", "»ùµØ"), PRISON("5", "¼àÓü"), CASINO("6", "¶Ä³¡"), SAFE_HOUSE("7", "°²È«ÎÝ"), ARENA("8", "½Ç¶·³¡"), MOBILE_TRADER("9", "ÒÆ¶¯ÉÌ··"), SKY_EYE("10", "ÌìÑÛ");
		
		private String code;
		
		private String name;
		
		Role(String code, String name) {
			this.code = code;
			this.name = name;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getName() {
			return name;
		}
		
		public static Role getByCode(String code) {
			for (Role role : Role.values()) {
				if (role.getCode().equals(code)) return role;
			}
			return null;
		}
		
		public static Role getByName(String name) {
			for (Role role : Role.values()) {
				if (role.getName().equals(name)) return role;
			}
			return null;
		}
		
		public static boolean isPlayer(Role role) {
			return role == ESCAPEE || role == CHASER || role == CANDIDATE;
		}
		
		public static boolean isBuilding(Role role) {
			return !isPlayer(role);
		}
		
	}
	
	public enum Status {
		
		NORMAL("1", "»îÔ¾×´Ì¬"), JAILED("2", "¼àÓü×´Ì¬"), ELIMINATED("3", "´ý¼¤»î×´Ì¬");
		
		private String code;
		
		private String name;
		
		Status(String code, String name) {
			this.code = code;
			this.name = name;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getName() {
			return name;
		}
		
		public static Status getByCode(String code) {
			for (Status status : Status.values()) {
				if (status.getCode().equals(code)) return status;
			}
			return null;
		}
		
		public static Status getByName(String name) {
			for (Status status : Status.values()) {
				if (status.getName().equals(name)) return status;
			}
			return null;
		}
		
	}
	
}
