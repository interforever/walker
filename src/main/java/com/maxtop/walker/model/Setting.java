
package com.maxtop.walker.model;

public class Setting {
	
	private Double centerLng;
	
	private Double centerLat;
	
	private Integer radius;
	
	private String warningSwitch;
	
	private Integer warningDistance;
	
	private Double mul;
	
	private Integer add;
	
	public Double getCenterLng() {
		return centerLng;
	}
	
	public void setCenterLng(Double centerLng) {
		this.centerLng = centerLng;
	}
	
	public Double getCenterLat() {
		return centerLat;
	}
	
	public void setCenterLat(Double centerLat) {
		this.centerLat = centerLat;
	}
	
	public Integer getRadius() {
		return radius;
	}
	
	public void setRadius(Integer radius) {
		this.radius = radius;
	}
	
	public String getWarningSwitch() {
		return warningSwitch;
	}
	
	public void setWarningSwitch(String warningSwitch) {
		this.warningSwitch = warningSwitch;
	}
	
	public Integer getWarningDistance() {
		return warningDistance;
	}
	
	public void setWarningDistance(Integer warningDistance) {
		this.warningDistance = warningDistance;
	}
	
}
