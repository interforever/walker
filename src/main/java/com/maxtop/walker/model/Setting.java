
package com.maxtop.walker.model;

public class Setting {
	
	private String centerLng;
	
	private String centerLat;
	
	private Integer radius;
	
	private String warningSwitch;
	
	private Integer warningDistance;
	
	private Double mul;
	
	private Integer add;
	
	public String getCenterLng() {
		return centerLng;
	}
	
	public void setCenterLng(String centerLng) {
		this.centerLng = centerLng;
	}
	
	public String getCenterLat() {
		return centerLat;
	}
	
	public void setCenterLat(String centerLat) {
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
	
	public Double getMul() {
		return mul;
	}
	
	public void setMul(Double mul) {
		this.mul = mul;
	}
	
	public Integer getAdd() {
		return add;
	}
	
	public void setAdd(Integer add) {
		this.add = add;
	}
	
}
