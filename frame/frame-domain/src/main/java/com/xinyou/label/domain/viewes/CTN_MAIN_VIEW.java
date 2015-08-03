package com.xinyou.label.domain.viewes;

import java.math.BigDecimal;

public class CTN_MAIN_VIEW {
	private String ctn_main_guid;
	private String ctn_main_id;
	private String ctn_name;
	private int ctn_type;
    private String ctn_baco;
    private int ctn_status;
    
    private int parent_ctn_type;
    private String parent_ctn_guid;
	private String parent_ctn_id;
    private String parent_ctn_baco;
    
	private String itm_id;
    private String itm_name;
    private String itm_spec;
    private String itm_unit;
    private BigDecimal itm_qty;
    private BigDecimal itm_got_qty;

    private String wh_guid;
	private String wh_id;
    private String wh_name;
    private String wh_baco;
    private String wh_area_guid;
    private String wh_area_id;
    private String wh_area_baco;
    private String wh_shelf_guid;
    private String wh_shelf_id;
    private String wh_shelf_baco;
    private String wh_loc_guid;
    private String wh_loc_id;
    private String wh_loc_baco;
    private String wh_plt_guid;
    private String wh_plt_id;
    private String wh_plt_baco;
    private String wh_package_guid;
    private String wh_package_id;
    private String wh_package_baco;
    
    private int base_type;
    private String base_id;
    private String base_seqno;;
    private String lot_id;
    private String def_loc_id;
    
    private String stk_guid;
    private String updated_by;
    private String created_by;

    public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}
	public String getStk_guid() {
		return stk_guid;
	}
	public void setStk_guid(String stk_guid) {
		this.stk_guid = stk_guid;
	}
	public String getDef_loc_id() {
		return def_loc_id;
	}
	public void setDef_loc_id(String def_loc_id) {
		this.def_loc_id = def_loc_id;
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
    public String getLot_id() {
		return lot_id;
	}
	public void setLot_id(String lot_id) {
		this.lot_id = lot_id;
	}
	public String getParent_ctn_guid() {
		return parent_ctn_guid;
	}
	public void setParent_ctn_guid(String parent_ctn_guid) {
		this.parent_ctn_guid = parent_ctn_guid;
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
	public String getCtn_name() {
		return ctn_name;
	}
	public void setCtn_name(String ctn_name) {
		this.ctn_name = ctn_name;
	}
	public int getCtn_type() {
		return ctn_type;
	}
	public void setCtn_type(int ctn_type) {
		this.ctn_type = ctn_type;
	}
	public String getCtn_baco() {
		return ctn_baco;
	}
	public void setCtn_baco(String ctn_baco) {
		this.ctn_baco = ctn_baco;
	}
	public int getCtn_status() {
		return ctn_status;
	}
	public void setCtn_status(int ctn_status) {
		this.ctn_status = ctn_status;
	}
	public int getParent_ctn_type() {
		return parent_ctn_type;
	}
	public void setParent_ctn_type(int parent_ctn_type) {
		this.parent_ctn_type = parent_ctn_type;
	}
	public String getParent_ctn_id() {
		return parent_ctn_id;
	}
	public void setParent_ctn_id(String parent_ctn_id) {
		this.parent_ctn_id = parent_ctn_id;
	}
	public String getParent_ctn_baco() {
		return parent_ctn_baco;
	}
	public void setParent_ctn_baco(String parent_ctn_baco) {
		this.parent_ctn_baco = parent_ctn_baco;
	}
    public String getItm_id() {
		return itm_id;
	}
	public void setItm_id(String itm_id) {
		this.itm_id = itm_id;
	}
	public String getItm_name() {
		return itm_name;
	}
	public void setItm_name(String itm_name) {
		this.itm_name = itm_name;
	}
	public String getItm_spec() {
		return itm_spec;
	}
	public void setItm_spec(String itm_spec) {
		this.itm_spec = itm_spec;
	}
	public String getItm_unit() {
		return itm_unit;
	}
	public void setItm_unit(String itm_unit) {
		this.itm_unit = itm_unit;
	}
	public BigDecimal getItm_qty() {
		return itm_qty;
	}
	public void setItm_qty(BigDecimal itm_qty) {
		this.itm_qty = itm_qty;
	}
	public BigDecimal getItm_got_qty() {
		return itm_got_qty;
	}
	public void setItm_got_qty(BigDecimal itm_got_qty) {
		this.itm_got_qty = itm_got_qty;
	}
	public String getWh_id() {
		return wh_id;
	}
	public void setWh_id(String wh_id) {
		this.wh_id = wh_id;
	}
	public String getWh_name() {
		return wh_name;
	}
	public void setWh_name(String wh_name) {
		this.wh_name = wh_name;
	}
	public String getWh_baco() {
		return wh_baco;
	}
	public void setWh_baco(String wh_baco) {
		this.wh_baco = wh_baco;
	}
	public String getWh_area_id() {
		return wh_area_id;
	}
	public void setWh_area_id(String wh_area_id) {
		this.wh_area_id = wh_area_id;
	}
	public String getWh_area_baco() {
		return wh_area_baco;
	}
	public void setWh_area_baco(String wh_area_baco) {
		this.wh_area_baco = wh_area_baco;
	}
	public String getWh_shelf_id() {
		return wh_shelf_id;
	}
	public void setWh_shelf_id(String wh_shelf_id) {
		this.wh_shelf_id = wh_shelf_id;
	}
	public String getWh_shelf_baco() {
		return wh_shelf_baco;
	}
	public void setWh_shelf_baco(String wh_shelf_baco) {
		this.wh_shelf_baco = wh_shelf_baco;
	}
	public String getWh_loc_id() {
		return wh_loc_id;
	}
	public void setWh_loc_id(String wh_loc_id) {
		this.wh_loc_id = wh_loc_id;
	}
	public String getWh_loc_baco() {
		return wh_loc_baco;
	}
	public void setWh_loc_baco(String wh_loc_baco) {
		this.wh_loc_baco = wh_loc_baco;
	}
	public String getWh_plt_id() {
		return wh_plt_id;
	}
	public void setWh_plt_id(String wh_plt_id) {
		this.wh_plt_id = wh_plt_id;
	}
	public String getWh_plt_baco() {
		return wh_plt_baco;
	}
	public void setWh_plt_baco(String wh_plt_baco) {
		this.wh_plt_baco = wh_plt_baco;
	}
	public String getWh_package_id() {
		return wh_package_id;
	}
	public void setWh_package_id(String wh_package_id) {
		this.wh_package_id = wh_package_id;
	}
	public String getWh_package_baco() {
		return wh_package_baco;
	}
	public void setWh_package_baco(String wh_package_baco) {
		this.wh_package_baco = wh_package_baco;
	}
//	public String getOrder_no() {
//		return order_no;
//	}
//	public void setOrder_no(String order_no) {
//		this.order_no = order_no;
//	}
//	public String getBill_no() {
//		return bill_no;
//	}
//	public void setBill_no(String bill_no) {
//		this.bill_no = bill_no;
//	}
//	public String getWork_no() {
//		return work_no;
//	}
//	public void setWork_no(String work_no) {
//		this.work_no = work_no;
//	}
//	public String getRouting_no() {
//		return routing_no;
//	}
//	public void setRouting_no(String routing_no) {
//		this.routing_no = routing_no;
//	}
	
	public boolean isEmpty()
	{
		if(this.ctn_main_guid==null||this.ctn_main_guid.trim().length()==0) return true;
		return false;
	}
}
