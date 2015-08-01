var SYS_PRE='/frame-web/rs/';
var img_path='/frame-web/upload/UploadImg/';
var index_path='/frame-web/view/index.html';
var loginPageUrl='/frame-web/login-index.html';

// input只能输入数字和小数点
function DigitInput(el,e) {
    //8：退格键、46：delete、37-40： 方向键
    //48-57：小键盘区的数字、96-105：主键盘区的数字
    //110、190：小键盘区和主键盘区的小数
    //189、109：小键盘区和主键盘区的负号
    //var e = e || window.event; //IE、FF下获取事件对象
    var cod = e.charCode||e.keyCode; //IE、FF下获取键盘码
    //小数点处理
    if (cod == 110 || cod == 190){
        (el.value.indexOf(".")>=0 || !el.value.length) && notValue(e);
    } else {
        if(cod!=8 && cod != 46 && (cod<37 || cod>40) && (cod<48 || cod>57) && (cod<96 || cod>105)) notValue(e);
    }
    function notValue(e){
        e.preventDefault ? e.preventDefault() : e.returnValue=false;
    }
}

Date.prototype.format = function(format)
{
  var o = {
    "M+" : this.getMonth()+1, //month
    "d+" : this.getDate(), //day
    "h+" : this.getHours(), //hour
    "m+" : this.getMinutes(), //minute
    "s+" : this.getSeconds(), //second
    "q+" : Math.floor((this.getMonth()+3)/3), //quarter
    "S" : this.getMilliseconds() //millisecond
  };
  if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
    (this.getFullYear()+"").substr(4 - RegExp.$1.length));
  for(var k in o)if(new RegExp("("+ k +")").test(format))
    format = format.replace(RegExp.$1,
      RegExp.$1.length==1 ? o[k] :
        ("00"+ o[k]).substr((""+ o[k]).length));
  return format;
};

Array.prototype.removeAt=function(n){
	var target=this;
	var target_length=target.length;
	if (n<0||n>=target_length) {
		return target;
	}
	for(var i=n+1; i<target_length; i++){
		target[i-1] = target[i];
	}
	target.pop();
	return target;
};


function Relogin(info)
{
	if(confirm("未登录！("+info+")")){
		window.parent.window.location.replace(loginPageUrl);
	}
}

function checkLength(obj,maxlength){
    if(obj.value.length > maxlength){
        obj.value = obj.value.substring(0,maxlength);
    }
}

function MathRound2(value)
{
	return Math.round(value*100)/100;
}

function MathRound(value)
{
	return Math.round(value);
}

function FilterString(str)
{
	if(typeof(str)=="string"){
		str = str.replace(/'/g,"‘");
	}
	return str;
}

//转义JSON中单引号、双引号
function encodeJSON(str)
{
	if(typeof(str)=="string"){
		str = str.replace(/'/g,"\\\'");
		str = str.replace(/"/g,"\\\"");
	}
	return str;
}
//转义Html中value值中单引号、双引号
function encodeValue(str)
{
	if(typeof(str)=="string"){
		str = str.replace(/&/g,"&amp;");
		str = str.replace(/'/g,"&#39;");
		str = str.replace(/"/g,"&#34;");
	}
	return str;
}
//转义table内容中Html的标签
function encodeHtml(str)
{	
	if(typeof(str)=="string"){
		str = str.replace(/&/g,"&amp;");
		str = str.replace(/</g,"&lt;");
		str = str.replace(/>/g,"&gt;");
	}
	return str;
}

//转义JSON中单引号、双引号
function encodeJSON2(str)
{
	if(typeof(str)=="string"){
		str=encodeURIComponent(str);
		str = str.replace(/'/g,"\\\'");
		//str = str.replace(/"/g,"\\\"");
	}
	return str;
}
function encodeString(str){
	if(typeof(str)=="string")
		str=encodeURIComponent(str);
	return str;
}
function decodeString(str){
	if(typeof(str)=="string"){
		str=decodeURIComponent(str);
		str = str.replace(/&/g,"&amp;");
		str = str.replace(/</g,"&lt;");
		str = str.replace(/>/g,"&gt;");
	}
	return str;
}

function roundNumber(num, dec) {
	var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
	return result;
}

function setCookie(name,value)
{
	$.cookie(name,value,{path:'/'});
}
function getCookie(name)
{
	if(name){
		return $.cookie(name);
	}
}
function getToken(){
	return $.cookie("frame-token");
}
function delCookie(names)
{   
	var nameArray = names.split(',');
	if(nameArray!=undefined&&nameArray.length>0){
		for(var i=0,len=nameArray.length;i<len;i++){
			$.cookie(nameArray[i],null,{ path: '/' });
		}
	}
}

function isLogin(){
	token = getCookie('frame-token');
	if(token){
		return true;
	}else{
		window.location.href = loginPageUrl;
		return false;
	}
}

function isValidId(sId){
	if(!sId)return false;

	var quickIdExpr=/^(11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)([0-9]{13}|[0-9]{15}[0-9xX])$/;
	if(!quickIdExpr.test(sId))return false;

	var Ai=(sId.length==18)?sId.substr(0,17):sId.substr(0,6)+"19"+sId.substr(6,9);

	var sY=Ai.substr(6,4);
	var sM=Ai.substr(10,2);
	var sD=Ai.substr(12,2);
	if(!isValidDate(sY,sM,sD))return false;

	var Vc=new Array("1","0","X","9","8","7","6","5","4","3","2");
	var Wi=new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2);
	var i,sigma=0;
	for(i=0;i<17;i++)
		sigma+=parseInt(Ai.substr(i,1))*Wi[i];

	if(sId.length==18){
		var Av=sId.substr(17,1).toUpperCase();
		if(Av!==Vc[sigma%11])return false;
	}
	return true;
}

function isValidDate(sY,sM,sD){
	if(!sY||!sM||!sD)return false;
	var nY=parseInt(sY,10);var nM=parseInt(sM,10);var nD=parseInt(sD,10);
	return !(nM<1||12<nM||nD<1||31<nD||(nM==4||nM==6||nM==9||nM==11)&&nD>30||nM==2&&((nY%400==0||nY%4==0&&nY%100!=0)&&nD>29||nD>28));
}

var isEnter=function (ev) {
	ev = ev || window.event;
	var code = (ev.keyCode || ev.which);
	return code == 13;
};

function isNullNumeric(num){
	num=Number(num);
	if(isNaN(num))num=0;
	return num;
}


