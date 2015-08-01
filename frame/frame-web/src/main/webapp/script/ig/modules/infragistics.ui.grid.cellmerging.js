/*!@license
 * Infragistics.Web.ClientUI Grid Merged Cells 13.1.20131.1012
 *
 * Copyright (c) 2011-2013 Infragistics Inc.
 *
 * http://www.infragistics.com/
 *
 * Depends on:
 * Depends on:
 *	jquery-1.4.4.js
 *	jquery.ui.core.js
 *	jquery.ui.widget.js
 *	infragistics.ui.grid.framework.js
 *	infragistics.ui.shared.js
 *	infragistics.dataSource.js
 *	infragistics.util.js
 */
if(typeof jQuery!=="function"){throw new Error("jQuery is undefined")}(function($){"use strict";$.widget("ui.igGridCellMerging",{css:{mergedCellsTop:"ui-iggrid-mergedcellstop",mergedCellsBottom:"ui-iggrid-mergedcellsbottom",mergedCell:"ui-iggrid-mergedcell"},options:{initialState:"regular"},events:{cellsMerging:"cellsMerging",cellsMerged:"cellsMerged"},_create:function(){this._first=true;this._sortingRequested=false;this._v=false},destroy:function(){this._removePaint();this._unregisterEvents();$.Widget.prototype.destroy.call(this);return this},_gridSorting:function(){this._sortingRequested=true},_gridSorted:function(){this._first=false;this._sortingRequested=false},_gridRendered:function(event,ui){var key,index,sExp=this.grid.dataSource.settings.sorting.expressions,i,j;if(this.options.initialState==="merged"&&this._first===true&&this._sortingRequested===false){for(j=0;j<this.grid._visibleColumns().length;j++){this._paintMergedCells(j)}}else{for(i=0;i<sExp.length;i++){key=sExp[i].fieldName;for(j=0;j<this.grid._visibleColumns().length;j++){if(this.grid._visibleColumns()[j].key===key){index=j;break}}if(typeof this.grid._startColIndex==="number"){index-=this.grid._startColIndex}if(index&&key){this._paintMergedCells(index,key)}}}this._first=false},_rrn:function(event,args){this._gridRendered()},_rcn:function(event,args){this._gridRendered()},_columnsCollectionModified:function(){this._gridRendered()},_paintMergedCells:function(index,key){var cells,prvCell,curCell,prvCellTxt,curCellTxt,first=true,str=false,count=0,i,args,cval,noCancel;cells=$("#"+this.grid.id()+" tbody tr>td:nth-child("+(index+1+this._getSystemColumnsCount())+")");this._addVirtualBorderCells(cells,key);prvCell=cells.eq(0);for(i=1;i<cells.length;i++){curCell=cells.eq(i);prvCellTxt=this._getComparableCellText(prvCell);curCellTxt=this._getComparableCellText(curCell);if(prvCellTxt===curCellTxt&&prvCellTxt!==cval){if(str===false){args=this._getEventArgsForCell(prvCell);noCancel=this._trigger(this.events.cellsMerging,this,args);if(noCancel!==true){cval=prvCellTxt;prvCell=curCell;continue}}cval=null;str=true;if(first===true){if(!cells.eq(i-1)[0].fictive){cells.eq(i-1).addClass(this.css.mergedCellsTop)}first=false;count++}if(!curCell[0].fictive){curCell.addClass(this.css.mergedCell)}count++}else{if(str===true){args.count=count;count=0;this._trigger(this.events.cellsMerged,this,args);cells.eq(i-1).addClass(this.css.mergedCellsBottom);str=false}first=true}prvCell=curCell}if(str===true){args.count=count;this._trigger(this.events.cellsMerged,this,args);if(!cells.eq(cells.length-1)[0].fictive){cells.eq(cells.length-1).addClass(this.css.mergedCellsBottom)}}},_addVirtualBorderCells:function(list,key){var ds=this.grid.dataSource.dataView();if(this._v===true){if(this.grid._startRowIndex>0){list.splice(0,0,{txt:String(ds[this.grid._startRowIndex-1][key]),fictive:true})}if(this.grid._startRowIndex+this.grid._virtualRowCount<this.grid.dataSource.dataView().length){list.splice(list.length,0,{txt:String(ds[this.grid._startRowIndex+this.grid._virtualRowCount][key]),fictive:true})}}},_getSystemColumnsCount:function(){var firstRow=$("#"+this.grid.id()+" tbody>tr:not([data-container='true'],[data-grouprow='true']):first");return firstRow.children("[data-parent='true'],[data-skip='true'],th").length},_getEventArgsForCell:function(cell){var args,row,rKey,rIdx,val;if(cell[0].fictive){row=null;rKey=null;rIdx=this.grid._startRowIndex?this.grid._startRowIndex-1:-1;val=cell[0].txt}else{row=cell.closest("tr");rKey=row.attr("data-id");rIdx=this._getVisibleRowIndex(row);if(rKey===""||rKey===null||rKey===undefined){rKey=rIdx}val=cell.html()}args={owner:this,row:row,rowIndex:rIdx,rowKey:rKey,grid:this.grid,value:val};return args},_getComparableCellText:function(cell){var text=cell[0].fictive?cell[0].txt:cell.html().replace(/^&nbsp;$/,"");if(this.grid.dataSource.settings.sorting.caseSensitive===false){text=text.toLowerCase()}return text},_getVisibleRowIndex:function(row){return row.closest("tbody").children("tr:not([data-container='true'],[data-grouprow='true'])").index(row)+(this.grid._startRowIndex||0)},_removePaint:function(){var dataRows=$("#"+this.grid.id()+"> tbody > tr:not([data-container='true'],[data-grouprow='true'])"),cells,i;cells=dataRows.children("td."+this.css.mergedCellsTop+",td."+this.css.mergedCell);for(i=0;i<cells.length;i++){$(cells[i]).removeClass(this.css.mergedCellsTop).removeClass(this.css.mergedCell).removeClass(this.css.mergedCellsBottom)}},_createHandlers:function(){this._gridRenderedHandler=$.proxy(this._gridRendered,this);this._sortingInitiatedHandler=$.proxy(this._gridSorting,this);this._sortingHandler=$.proxy(this._gridSorted,this);this._virtualRowsHandler=$.proxy(this._rrn,this);this._virtualColumnsHandler=$.proxy(this._rcn,this);this._columnsCollectionModifiedHandler=$.proxy(this._columnsCollectionModified,this)},_registerEvents:function(){this.grid.element.bind("ig_gridrendered",this._gridRenderedHandler);this.grid.element.bind("iggridsortingcolumnsorting",this._sortingInitiatedHandler);this.grid.element.bind("iggridsortingcolumnsorted",this._sortingHandler);this.grid.element.bind("iggridvirtualrecordsrender",this._virtualRowsHandler);this.grid.element.bind("iggridvirtualhorizontalscroll",this._virtualColumnsHandler);this.grid.element.bind("iggridcolumnscollectionmodified",this._columnsCollectionModifiedHandler)},_unregisterEvents:function(){this.grid.element.unbind("ig_gridrendered",this._gridRenderedHandler);this.grid.element.unbind("iggridsortingcolumnsorting",this._sortingInitiatedHandler);this.grid.element.unbind("iggridsortingcolumnsorted",this._sortingHandler);this.grid.element.unbind("iggridvirtualrecordsrender",this._virtualRowsHandler);this.grid.element.unbind("iggridvirtualhorizontalscroll",this._virtualColumnsHandler);this.grid.element.unbind("iggridcolumnscollectionmodified",this._columnsCollectionModifiedHandler)},_injectGrid:function(gridInstance,isRebind){this.grid=gridInstance;this._v=this.grid.options.virtualization===true||this.grid.options.rowVirtualization===true;this._createHandlers();this._registerEvents()}});$.extend($.ui.igGridCellMerging,{version:"13.1.20131.1012"})})(jQuery);