package com.codepath.gridimagesearch;

import java.io.Serializable;

public class Filter implements Serializable{
 
	private static final long serialVersionUID = 1L;
	private String size;
	private String type;
	private String color;
	private String site;
  
  public Filter(String size, String color, String type, String site) {
  	this.setSize(size);
  	this.setColor(color);
  	this.setType(type);
  	this.setSite(site);
  }

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
}
