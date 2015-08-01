package com.xinyou.label.domain.entities;

import java.math.BigDecimal;

/**
 *  仓库、库区、货架，库位实体类 （容器类）
 * */
public class CTN_MAIN {
	public String getCtn_main_guid() {
		return ctn_main_guid;
	}
	public void setCtn_main_guid(String ctn_main_guid) {
		this.ctn_main_guid = ctn_main_guid;
	}
	public String getCtn_main_id() {
		return ctn_main_id;
	}
	public void setCtn_main_id(String ctn_main_id) {
		this.ctn_main_id = ctn_main_id;
	}
	public int getCtn_type() {
		return ctn_type;
	}
	public void setCtn_type(int ctn_type) {
		this.ctn_type = ctn_type;
	}
	public String getParent_ctn_guid() {
		return parent_ctn_guid;
	}
	public void setParent_ctn_guid(String parent_ctn_guid) {
		this.parent_ctn_guid = parent_ctn_guid;
	}
	public int getCtn_height() {
		return ctn_height;
	}
	public void setCtn_height(int ctn_height) {
		this.ctn_height = ctn_height;
	}
	public int getCtn_width() {
		return ctn_width;
	}
	public void setCtn_width(int ctn_width) {
		this.ctn_width = ctn_width;
	}
	public int getCtn_length() {
		return ctn_length;
	}
	public void setCtn_length(int ctn_length) {
		this.ctn_length = ctn_length;
	}
	public String getCtn_baco() {
		return ctn_baco;
	}
	public void setCtn_baco(String ctn_baco) {
		this.ctn_baco = ctn_baco;
	}
	public String getCtn_name() {
		return ctn_name;
	}
	public void setCtn_name(String ctn_name) {
		this.ctn_name = ctn_name;
	}
	public int getCtn_status() {
		return ctn_status;
	}
	public void setCtn_status(int ctn_status) {
		this.ctn_status = ctn_status;
	}
	public String getItm_id() {
		return itm_id;
	}
	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
	}
	public BigDecimal getItm_qty() {
		return itm_qty;
	}
	public void setItm_qty(BigDecimal itm_qty) {
		this.itm_qty = itm_qty;
	}
	public String getWh_guid() {
		return wh_guid;
	}
	public void setWh_guid(String wh_guid) {
		this.wh_guid = wh_guid;
	}
	public String getWh_area_guid() {
		return wh_area_guid;
	}
	public void setWh_area_guid(String wh_area_guid) {
		this.wh_area_guid = wh_area_guid;
	}
	public String getWh_shelf_guid() {
		return wh_shelf_guid;
	}
	public void setWh_shelf_guid(String wh_shelf_guid) {
		this.wh_shelf_guid = wh_shelf_guid;
	}
	public String getWh_loc_guid() {
		return wh_loc_guid;
	}
	public void setWh_loc_guid(String wh_loc_guid) {
		this.wh_loc_guid = wh_loc_guid;
	}
	public String getWh_plt_guid() {
		return wh_plt_guid;
	}
	public void setWh_plt_guid(String wh_plt_guid) {
		this.wh_plt_guid = wh_plt_guid;
	}
	public String getWh_package_guid() {
		return wh_package_guid;
	}
	public void setWh_package_guid(String wh_package_guid) {
		this.wh_package_guid = wh_package_guid;
	}
	public String getLot_id() {
		return lot_id;
	}
	public void setLot_id(String lot_id) {
		this.lot_id = lot_id;
	}
	public int getBase_type() {
		return base_type;
	}
	public void setBase_type(int base_type) {
		this.base_type = base_type;
	}
	public String getBase_id() {
		return base_id;
	}
	public void setBase_id(String base_id) {
		this.base_id = base_id;
	}
	public String getBase_seqno() {
		return base_seqno;
	}
	public void setBase_seqno(String base_seqno) {
		this.base_seqno = base_seqno;
	}
	
	private String ctn_main_guid;
	private String ctn_main_id;
	private int ctn_type;
	private String parent_ctn_guid;
	private int ctn_height;
	private int ctn_width;
	private int ctn_length;
	private String ctn_baco;
	private String ctn_name;    
    private int ctn_status;
    private String itm_id;
    private BigDecimal itm_qty;
    private String wh_guid;
    private String wh_area_guid;
    private String wh_shelf_guid;
    private String wh_loc_guid;
	private String wh_plt_guid;
    private String wh_package_guid;
    private String lot_id;
	private int base_type;
	private String base_id;
	private String base_seqno;
}
