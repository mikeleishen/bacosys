package com.xinyou.frame.domain.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

@XmlRootElement(name = "ORG_NODE")
public class ORG_NODE {
	private String title;
	private String key;
	private boolean isFolder;
	private boolean isLazy;
	private String tooltip;
	private String href;
	private String icon;
	private String addClass;
	private boolean noLink;
	private boolean activate;
	private boolean focus;
	private boolean expand;
	private boolean select;
	private boolean hideCheckbox;
	private boolean unselectable;
	private String org_seqno;
	private String parent_seqno;
	private String org_id;
	private String org_name;
	private String node_img;
	private String org_desc;
	private String co_guid;
	@JsonIgnore
	private ORG_NODE parent;
	private List<ORG_NODE> children;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean getIsFolder() {
		return isFolder;
	}
	public void setIsFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}
	public boolean getIsLazy() {
		return isLazy;
	}
	public void setIsLazy(boolean isLazy) {
		this.isLazy = isLazy;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getAddClass() {
		return addClass;
	}
	public void setAddClass(String addClass) {
		this.addClass = addClass;
	}
	public boolean isNoLink() {
		return noLink;
	}
	public void setNoLink(boolean noLink) {
		this.noLink = noLink;
	}
	public boolean isActivate() {
		return activate;
	}
	public void setActivate(boolean activate) {
		this.activate = activate;
	}
	public boolean isFocus() {
		return focus;
	}
	public void setFocus(boolean focus) {
		this.focus = focus;
	}
	public boolean isExpand() {
		return expand;
	}
	public void setExpand(boolean expand) {
		this.expand = expand;
	}
	public boolean isSelect() {
		return select;
	}
	public void setSelect(boolean select) {
		this.select = select;
	}
	public boolean isHideCheckbox() {
		return hideCheckbox;
	}
	public void setHideCheckbox(boolean hideCheckbox) {
		this.hideCheckbox = hideCheckbox;
	}
	public boolean isUnselectable() {
		return unselectable;
	}
	public void setUnselectable(boolean unselectable) {
		this.unselectable = unselectable;
	}
	public String getOrg_seqno() {
		return org_seqno;
	}
	public void setOrg_seqno(String org_seqno) {
		this.org_seqno = org_seqno;
	}
	public String getParent_seqno() {
		return parent_seqno;
	}
	public void setParent_seqno(String parent_seqno) {
		this.parent_seqno = parent_seqno;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getOrg_name() {
		return org_name;
	}
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}
	public String getOrg_desc() {
		return org_desc;
	}
	public void setOrg_desc(String org_desc) {
		this.org_desc = org_desc;
	}
	public String getCo_guid() {
		return co_guid;
	}
	public void setCo_guid(String co_guid) {
		this.co_guid = co_guid;
	}
	public ORG_NODE getParent() {
		return parent;
	}
	public void setParent(ORG_NODE parent) {
		this.parent = parent;
	}
	public List<ORG_NODE> getChildren() {
		if(children==null){
			children = new ArrayList<ORG_NODE>();
		}
		return children;
	}
	public void setChildren(List<ORG_NODE> children) {
		this.children = children;
	}
	public String getNode_img() {
		return node_img;
	}
	public void setNode_img(String node_img) {
		this.node_img = node_img;
	}
}