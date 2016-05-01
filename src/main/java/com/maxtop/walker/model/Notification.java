
package com.maxtop.walker.model;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.maxtop.walker.utils.DateTimeFormatSerializer;

public class Notification {
	
	private Integer id;
	
	private String content;
	
	private Date create_time;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonSerialize(using = DateTimeFormatSerializer.class)
	public Date getCreate_time() {
		return create_time;
	}
	
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
}
