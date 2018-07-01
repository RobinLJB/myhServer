package com.spark.p2p.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单权限项
 * @author yanqizheng
 *
 */
public class Menu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private int parentId;
	private String name;
	private String url;
	private int sort;
	private int status;
	private int type;
	private List<Menu> subMenu;
	private String title;
	private String icon;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getId() {
		return id;
	}
	public int getParentId() {
		return parentId;
	}
	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	public int getSort() {
		return sort;
	}
	public List<Menu> getSubMenu() {
		return subMenu;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String toString(){
		return "{"+this.id+","+this.name+","+this.url+"}";
	}
}
