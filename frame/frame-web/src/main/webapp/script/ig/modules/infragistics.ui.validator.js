/*!@license
* Infragistics.Web.ClientUI Validator localization resources 13.1.20131.1012
*
* Copyright (c) 2011-2013 Infragistics Inc.
*
* http://www.infragistics.com/
*
*/
$.ig=$.ig||{};if(!$.ig.Validator){$.ig.Validator={locale:{defaultMessage:"Please fix this field",selectMessage:"Please select a value",rangeSelectMessage:"Please select no more than {0} and not less than {1} items",minSelectMessage:"Please select at least {0} items",maxSelectMessage:"Please select no more than {0} items",rangeLengthMessage:"Please enter a value between {0} and {1} characters long",minLengthMessage:"Please enter at least {0} characters",maxLengthMessage:"Please enter no more than {0} characters",requiredMessage:"This field is required",maskMessage:"Please fill all required positions",dateFieldsMessage:"Please enter values in date fields",invalidDayMessage:"Invalid day of month. Please enter correct day",dateMessage:"Please enter a valid date",numberMessage:"Please enter a valid number",rangeMessage:"Please enter a value between {0} and {1}",minMessage:"Please enter a value greater than or equal to {0}",maxMessage:"Please enter a value less than or equal to {0}"}}}/*!@license
* Infragistics.Web.ClientUI Editors 13.1.20131.1012
*
* Copyright (c) 2011-2013 Infragistics Inc.
*
* http://www.infragistics.com/
* Depends on:
* jquery-1.4.4.js
* jquery.ui.core.js
* jquery.ui.widget.js

* Example to use:
*	<script type="text/javascript">
*	$(function () {
*		$('#text1').igValidator({ minLength: 3 });
*	});
*	</script>
*	<input id="text1" type="text" />
*/
(function($){var _id="=id",_cur=null,_submit={},_stop=function(e){try{e.preventDefault();e.stopPropagation()}catch(ex){}},_fid=function(form,fid){var id=form.id;if(!id&&fid){form._fid=fid}return id||form._fid};$.widget("ui.igValidator",{options:{showIcon:null,animationShow:null,animationHide:null,enableTargetErrorCss:null,alignment:null,keepFocus:null,onchange:null,onblur:null,formSubmit:null,onsubmit:null,bodyAsParent:true,required:false,minLength:-1,maxLength:-1,min:null,max:null,regExp:null,checkboxesName:false,locale:null,errorLabel:null,element:null,theme:null,errorMessage:null},css:{label:"ui-igvalidator ui-widget ui-state-error ui-corner-all",icon:"ui-igvalidator-icon ui-icon ui-icon-alert",target:"ui-igvalidator-target"},locale:{defaultMessage:"Please fix this field",selectMessage:"Please select a value",rangeSelectMessage:"Please select no more than {0} and not less than {1} items",minSelectMessage:"Please select at least {0} items",maxSelectMessage:"Please select no more than {0} items",rangeLengthMessage:"Please enter a value between {0} and {1} characters long",minLengthMessage:"Please enter at least {0} characters",maxLengthMessage:"Please enter no more than {0} characters",requiredMessage:"This field is required",regExpMessage:"Please fix pattern of this field",maskMessage:"Please fill all required positions",dateFieldsMessage:"Please enter values in date fields",invalidDayMessage:"Invalid day of month. Please enter correct day",dateMessage:"Please enter a valid date",numberMessage:"Please enter a valid number",rangeMessage:"Please enter a value between {0} and {1}",minMessage:"Please enter a value greater than or equal to {0}",maxMessage:"Please enter a value less than or equal to {0}"},events:{checkValue:null,validation:null,errorShowing:null,errorHiding:null,errorShown:null,errorHidden:null},_create:function(){var v,t,elem,o=this.options,me=this,def=$.ui.igValidator.defaults;for(v in o){if(o.hasOwnProperty(v)&&o[v]===null&&def[v]!==undefined){o[v]=def[v]}}elem=me.element;me._state=3;me._focTime=0;me._init0();t=elem[0].nodeName;if(t==="SELECT"){t=5}else if((t=elem[0].type)==="checkbox"){t=o.checkboxesName?6:4}else if(t==="radio"){t=6}else{t=0}me._elem=t!==6?elem:$("[name="+elem[0].name+"]").map(function(){return this.form===elem[0].form?this:null});me._t=t;if(!o.ctl&&!o.combo){me._elem.bind(me._evts={keydown:function(e){if((v=e.keyCode)<15||v>20){me._evt(e,v===9?null:me,v===9)}},change:function(e){me._evt(e,me)},cut:function(e){me._evt(e,me)},paste:function(e){me._evt(e,me)},beforecut:function(e){me._evt(e,me)},drop:function(e){me._evt(e,me)},dragend:function(e){me._evt(e,me)},blur:function(e){me._evt(e,me,1)}});if(o.element){o.element.bind(me._evtsE={mousedown:function(e){me._evt(e,me)},mouseup:function(e){me._evt(e)}})}}},_evt:function(e,me,blur){var o=this.options;if(blur&&!o.onblur||!blur&&!o.onchange){return}if(me){setTimeout(function(){me.validate(e)},20)}else{this.validate(e)}},_loc:function(key,m){var o=this.options,v=o.errorMessage||o[key+=m?"":"Message"]||(o.locale?o.locale[key]:null);v=v||($.ig&&$.ig.Validator&&$.ig.Validator.locale?$.ig.Validator.locale[key]:null);return v||this.locale[key]},_foc1:function(o){o=o.keepFocus;return o&&this._t!==6&&!(o.indexOf&&o.indexOf("n")===0)?o==="once"||o===1?1:2:null},_onTab:function(e){if(e&&e.keyCode===9&&this._foc1(this.options)===2){_stop(e);return 1}},_init0:function(end){var form,fid,obj,id=this._id,o=this.options;if(end){this._doError()}else{if(o.ctl){o.ctl._validator=this}}form=this.element[0].form;if(!form){return}fid=_fid(form,"fid");if(!id){id=this.element[0].id;if(!id){id=_id+=_id.length}this._id=id}obj=_submit[fid];if(!end&&(o.onsubmit||o.formSubmit)){if(!form._ig_onsubmit&&o.onsubmit){form._ig_onsubmit=1;$(form).submit(function(e){var f=_submit[_fid(this)];if(f){f.validate(e)}})}if(!form._ig_formsubmit&&o.formSubmit){form._ig_formsubmit=form.submit;form.submit=function(){var f=_submit[_fid(this)];if(f&&f.validate()){return}if(this._ig_formsubmit){this._ig_formsubmit()}}}if(!obj){obj=_submit[fid]={ctls:{},validate:function(e){var i,inv=false,lbl=1,ctls=this.ctls;for(i in ctls){if(ctls.hasOwnProperty(i)&&ctls[i].validate(e,lbl)){inv=true;if(!$.ui.igValidator.defaults.showAllErrorsOnSubmit){lbl=2}}}if(e&&inv){_stop(e)}return inv}}}obj.ctls[id]=this}else if(obj&&obj.ctls&&obj.ctls[id]){delete obj.ctls[id]}},getLocaleOption:function(name){return this._loc(name,1)},isMessageDisplayed:function(){return this._state>6},isValidState:function(){return!(this._state>=6||this.validate(null,null,1))},hide:function(keepCss){this._doError(null,null,keepCss?6:5)},validate:function(e,submit,check){var v,v2,val,txt=null,mes="Length",t=this._t,len=-1,o=this.options,el=this.element;if(!el||el.closest("body").length===0){return}if(t>3){mes="Select";len=val=t===5?el[0].multiple?$("option:selected",el[0]).length:el[0].selectedIndex:this._elem.filter(":checked").length}else{val=el.hasClass("ui-igcombo-nulltext")?"":el.val();len=val.length}if(o.ctl){delete o.ctl._invPlus}if(!this._trigger("checkValue",e,v2={owner:this,message:this._loc("default"),value:o.ctl?o.ctl.value():val})){if(o.ctl&&!check){o.ctl._invPlus=v2.message}return check?2:this._doError(v2.message,e,submit)}if(len===0){if(check){if(o.required){return 2}}else if(!o.required){return this._doError(null,e,submit)}}v=o.regExp;if(v&&len>0){if(!v.test){v=new RegExp(v.toString())}txt=v.test(val)?null:this._loc("regExp");if(o.ctl){o.ctl._invPlus=txt}}if(!txt&&o.required&&(len===0||!val)){txt=this._loc(t>4?"select":"required")}if(!txt){v=o.minLength;v2=o.maxLength;if(len>=0&&(v>len||v2>0&&v2<len)){if(v>0&&v2>0){txt=this._loc("range"+mes).replace("{0}",v2).replace("{1}",v)}else{txt=this._loc((v>0?"min":"max")+mes).replace("{0}",v>0?v:v2)}if(o.ctl){o.ctl._invPlus=txt}}}if(txt){return check?txt:this._doError(txt,e,submit)}if(o.ctl){v=o.ctl._doInvalid(null,9);v=v?v.message:null;return check?v:this._doError(v,e,submit)}if(!txt&&t<2&&len>0&&(v=(typeof o.min==="number"?1:0)+(typeof o.max==="number"?2:0))>0){if(isNaN(val=parseFloat(val))){txt=this._loc("number")}else if(v===3&&(val<o.min||val>o.max)){txt=this._loc("range").replace("{0}",o.min).replace("{1}",o.max)}else if(v===1&&val<o.min){txt=this._loc("min").replace("{0}",o.min)}else if(v===2&&val>o.max){txt=this._loc("max").replace("{0}",o.max)}}return check?txt:this._doError(txt,e,submit)},_xyCheck:function(stop){var shift,xyE,xy=this._xy;if(!xy){return}if(this._elem&&this._elem[0].offsetWidth){xyE=xy.e.offset();shift=xyE.left-xy.x;if(shift!==xy.x0){xy.x0=shift;this._dd.css("left",xy.xDD+shift+"px")}shift=xyE.top-xy.y;if(shift!==xy.y0){xy.y0=shift;this._dd.css("top",xy.yDD+shift+"px")}}if(stop||!xyE){delete this._xy;clearInterval(xy.i)}if(!xyE){this._doError()}},_doError:function(txt,e,submit){var marg,elem0,end,xy0,xy,arg,elem,val,ctl,align,same,aLbl,x0,y0,el,zi=1,me=this,css=me.css,x="left",y="top",state=me._state,show=txt,dd=me._lbl,o=me.options;if(!submit){submit=0}ctl=o.ctl;align=o.alignment==="bottom"?0:o.alignment==="left"?-1:1;if(!txt&&dd){txt=dd[0]._txt}if(state<6&&!show){return}same=dd&&dd[0]._txt===txt&&!me._changed;this._changed=null;if(show&&me._onTab(e)&&same){return 2}if(show&&same){if(state>5){if(_cur===me){me._focus(o,submit,e)}return 2}}if(!submit&&show&&_cur&&(new Date).getTime()-_cur._focTime<100){return 2}arg={owner:me,message:txt,invalid:!!show};if(submit<5&&!me._trigger("validation",e,arg)){return}me._state=show?6:1;elem=o.element||me.element;elem0=ctl?ctl._element:elem;if(o.enableTargetErrorCss){if(show){elem0.addClass(css.target)}else if(submit!==5){elem0.removeClass(css.target)}}if(submit===2){return 2}if(!me._trigger(show?"errorShowing":"errorHiding",e,arg)){return 1}if(!show&&!dd){return}txt=arg.message;me._state++;marg=!o.bodyAsParent;if(marg){x="marginLeft";y="marginTop"}aLbl=o.errorLabel;if(typeof aLbl==="string"){aLbl=$("body").find('[data-valmsg-for="'+aLbl+'"]').removeClass("field-validation-valid").addClass("field-validation-error")}if(aLbl&&(!aLbl.length||!aLbl.find)){aLbl=null}if(aLbl){dd=aLbl}else if(!dd){dd=me.element[0].id;if(dd){dd=' for="'+dd+'"'}dd=$("<label"+dd+"/>").addClass(css.label).css({position:"absolute",visibility:"hidden"});dd[0].unselectable="on";dd.html(txt);dd[0]._id=98;me._dd=o.theme?$("<span/>").addClass(o.theme).css("position","absolute").append(dd):dd;if(marg){MSApp.execUnsafeLocalFunction(function(){me._dd.prependTo(elem0.parent())})}else{MSApp.execUnsafeLocalFunction(function(){me._dd.appendTo($("body"))})}}me._lbl=dd;if(show){dd[0]._txt=txt;if(aLbl){dd.html(txt).show()}else{me._dd.css(x,"0px").css(y,"0px");if(o.showIcon){if(txt===" "||txt==="&nbsp;"){txt=""}txt='<span class="'+css.icon+'"></span><span style="display:inline-block;width:18px;"></span>'+txt}dd.html(txt);dd.css("width","auto").css("height","auto");dd[0]._width0=dd[0].offsetWidth;dd[0]._height0=dd[0].offsetHeight;me._width=Math.max(dd.width(),5);me._height=Math.max(dd.height(),10);if(o.showIcon){me._height=Math.max(dd.children()[0].offsetHeight,me._height)}}try{elem0.parentsUntil(document.body).add(elem0).add(elem).add(dd).each(function(){val=this.style?this.style.zIndex:0;val=val?parseInt(val,10):null;if(val&&!isNaN(val)&&val>zi){zi=val}})}catch(ex){}dd.css("zIndex",zi);me._focTime=0;_cur=me}end=function(){if(show){dd.css("filter","")}else{if(aLbl){aLbl.html(" ").hide()}else{me._dd.remove()}me._dd=me._lbl=_cur=null}me._state++};if(show){me._focus(o,submit,e);val=o.animationShow;if(val<5){val=null}if(aLbl){dd.css("opacity",0).show()}else{dd.css({opacity:val?0:1,height:(val?0:me._height)+"px",width:Math.floor(me._width/(val&&align>=0?2:1))+"px",display:"",visibility:"visible"});el=ctl&&ctl._swap?me.element:elem;xy=el.offset();if(!marg){x0=xy.left;y0=xy.top}xy.top+=align?0:el.outerHeight();if(ctl&&ctl._swap){xy.left-=ctl._leftShift()}if(align){xy.left+=align<0?-dd[0].offsetWidth:elem0[0].offsetWidth}if(marg){xy0=dd.offset();xy.left-=xy0.left;xy.top-=xy0.top}else{me._xy={x0:0,y0:0,x:x0,y:y0,e:el,xDD:xy.left,yDD:xy.top,i:setInterval(function(){me._xyCheck()},300)}}me._dd.css(x,xy.left+"px").css(y,xy.top+"px")}if(val){dd.animate(aLbl?{opacity:1}:{opacity:1,height:me._height,width:me._width},val,null,end)}else{end()}me._trigger("errorShown",e,arg);return 2}me._xyCheck(true);val=o.animationHide;if(val<5){val=null}if(val){if(aLbl){dd.animate({opacity:0},val,null,end)}else{dd.animate({opacity:.6},Math.floor(val*.34)).animate({opacity:0,height:0,width:Math.floor(me._width/(align<0?1:2))},Math.floor(val*.66),null,end)}}else{end()}me._trigger("errorHidden",e,arg)},_focus:function(o,submit,e){var el=!submit&&this._foc1(o)?this.element:null;e=e&&e.keyCode!==9?e.type:"";if(el&&!(o.ctl&&o.ctl._fcs)&&(this._foc1(o)!==1||!this._focTime)&&e.indexOf("key")<0&&e.indexOf("mouse")<0){$.ui.igValidator._keepFoc=o.ctl;e=$.ui.igValidator._dd;if(e){e._doDrop()}this._focTime=(new Date).getTime();setTimeout(function(){try{el.focus()}catch(ex){}},0)}},_setOption:function(key,val){if(this.options[key]===val){return this}$.Widget.prototype._setOption.apply(this,arguments);if(typeof val!=="function"&&key!=="locale"){this._init0()}return this},destroy:function(){if(!this._elem){return this}var o=this.options;if(!o.ctl){this._elem.unbind(this._evts);if(this._evtsE){o.element.unbind(this._evtsE)}}this._init0(1);o.element=o.ctl=this._evts=this._evtsE=this._elem=null;$.Widget.prototype.destroy.apply(this,arguments);return this}});$.ui.igValidator._act=function(editor,act){var d,f,v=$.ui.igValidator;if(!v){return}d=v._dd;if(d&&(!d._ddOn||!(d._field||d.mainElem))){d=null;delete v._dd}f=v._keepFoc;if(f&&!f._field){f=null;delete v._keepFoc}if(act==="drop"){if(f&&f!==editor&&!f.validate()){return true}v._dd=editor;return}if(act==="hide"){if(d===editor){delete v._dd}return}if(!act&&f===editor){delete v._keepFoc}return f&&f!==editor};$.extend($.ui.igValidator,{version:"13.1.20131.1012"});$.ui.igValidator.defaults={showAllErrorsOnSubmit:false,showIcon:true,animationShow:300,animationHide:300,enableTargetErrorCss:true,alignment:"bottom",keepFocus:null,onchange:true,onblur:true,formSubmit:false,onsubmit:true}})(jQuery);