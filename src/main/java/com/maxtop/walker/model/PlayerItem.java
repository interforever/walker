
package com.maxtop.walker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PlayerItem {
	
	private Integer categoryId;
	
	private Integer itemId;
	
	private String headImage;
	
	private Integer finalItemId;
	
	private String title;
	
	private Integer mixAmount;
	
	private Integer usedAmount = 0;
	
	private Integer currentAmount;
	
	private Integer playerNeed;
	
	private Integer id;
	
	private Integer fundTotalAmount;
	
	private String desc;
	
	private String categoryName;
	
	private Integer price;
	
	private String picUrl;
	
	private String labelName;
	
	private String playerid;
	
	@JsonIgnore
	public Integer getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	
	public Integer getItemId() {
		return itemId;
	}
	
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	
	@JsonIgnore
	public String getHeadImage() {
		return headImage;
	}
	
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	
	@JsonIgnore
	public Integer getFinalItemId() {
		return finalItemId;
	}
	
	public void setFinalItemId(Integer finalItemId) {
		this.finalItemId = finalItemId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Integer getMixAmount() {
		return mixAmount;
	}
	
	public void setMixAmount(Integer mixAmount) {
		this.mixAmount = mixAmount;
	}
	
	public Integer getUsedAmount() {
		return usedAmount;
	}
	
	public void setUsedAmount(Integer usedAmount) {
		this.usedAmount = usedAmount;
	}
	
	public Integer getCurrentAmount() {
		return currentAmount;
	}
	
	public void setCurrentAmount(Integer currentAmount) {
		this.currentAmount = currentAmount;
	}
	
	public Integer getPlayerNeed() {
		return playerNeed;
	}
	
	public void setPlayerNeed(Integer playerNeed) {
		this.playerNeed = playerNeed;
	}
	
	@JsonIgnore
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getFundTotalAmount() {
		return fundTotalAmount;
	}
	
	public void setFundTotalAmount(Integer fundTotalAmount) {
		this.fundTotalAmount = fundTotalAmount;
	}
	
	@JsonIgnore
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@JsonIgnore
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@JsonIgnore
	public Integer getPrice() {
		return price;
	}
	
	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public String getPicUrl() {
		return picUrl;
	}
	
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	@JsonIgnore
	public String getLabelName() {
		return labelName;
	}
	
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	
	public String getPlayerid() {
		return playerid;
	}
	
	public void setPlayerid(String playerid) {
		this.playerid = playerid;
	}
	
}
