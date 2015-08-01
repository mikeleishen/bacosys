package com.xinyou.frame.domain.models;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

@XmlRootElement(name = "FUN_NODE")
public class FUN_NODE {
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
	private String fun_seqno;
	private String parent_seqno;
	private String fun_id;
	private String fun_name;
	private String fun_param;
	private String fun_ass;
	private String fun_class;
	private String fun_method;
	private String fun_url;
	private String node_img;
	private String fun_desc;
	private String biz_sys_guid;
	@JsonIgnore
	private FUN_NODE parent;
	private List<FUN_NODE> children;
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
	public String getFun_seqno() {
		return fun_seqno;
	}
	public void setFun_seqno(String fun_seqno) {
		this.fun_seqno = fun_seqno;
	}
	public String getParent_seqno() {
		return parent_seqno;
	}
	public void setParent_seqno(String parent_seqno) {
		this.parent_seqno = parent_seqno;
	}
	public List<FUN_NODE> getChildren() {
		if(children==null){
			children = new ArrayList<FUN_NODE>();
		}
		return children;
	}
	public void setChildren(List<FUN_NODE> children) {
		this.children = children;
	}
	public String getFun_name() {
		return fun_name;
	}
	public void setFun_name(String fun_name) {
		this.fun_name = fun_name;
	}
	public String getFun_param() {
		return fun_param;
	}
	public void setFun_param(String fun_param) {
		this.fun_param = fun_param;
	}
	public String getFun_url() {
		return fun_url;
	}
	public void setFun_url(String fun_url) {
		this.fun_url = fun_url;
	}
	public String getFun_desc() {
		return fun_desc;
	}
	public void setFun_desc(String fun_desc) {
		this.fun_desc = fun_desc;
	}
	public String getFun_id() {
		return fun_id;
	}
	public void setFun_id(String fun_id) {
		this.fun_id = fun_id;
	}
	public String getBiz_sys_guid() {
		return biz_sys_guid;
	}
	public void setBiz_sys_guid(String biz_sys_guid) {
		this.biz_sys_guid = biz_sys_guid;
	}
	public FUN_NODE getParent() {
		return parent;
	}
	public void setParent(FUN_NODE parent) {
		this.parent = parent;
	}
	public String getNode_img() {
		return node_img;
	}
	public void setNode_img(String node_img) {
		this.node_img = node_img;
	}
	public String getFun_ass() {
		return fun_ass;
	}
	public void setFun_ass(String fun_ass) {
		this.fun_ass = fun_ass;
	}
	public String getFun_class() {
		return fun_class;
	}
	public void setFun_class(String fun_class) {
		this.fun_class = fun_class;
	}
	
	public String getFun_method() {
		return fun_method;
	}
	public void setFun_method(String fun_method) {
		this.fun_method = fun_method;
	}
}