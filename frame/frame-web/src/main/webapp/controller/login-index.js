/**
 * 
 */

function Request() {
	this.usr_name=null;
	this.usr_pswd= null;
}
var request = new Request();

function init() {
	 delCookie("frame-token");
	 doSignIn();
	 var u = $('#userName');
	 var p = $('#userPswd');
	 
	 u.keydown(function(ev) {
		  if(isEnter(ev)) $('#signIn').click();
	 } );
	 
	 p.keydown(function(ev){
		 if(isEnter(ev)) $('#signIn').click();
	 }); 
}

function doSignIn() {
	/*bind login button click events*/
	$('#signIn').click( function() {
		 var userName = $.trim( $('#userName').val());
		 if(!userName) {
			 alert('请输入用户名'); 
			 $('#userName').focus();
			 return;
		 }
		 
		 var userPswd = $.trim($('userPswd').val());
		 if(!userPswd) {
			  alert('请输入用密码');
			  $('#userPswd').focus();
			  return;  
		 }
		 signIn(userName,userPswd);
	});
}

function signIn(name,pswd) {
	request.usr_name = encodeString(name);
	request.usr_pswd = encodeString(pswd);
	$.ajax( {
		type:"POST",
		url: SYS_PRE+"Usr/signIn",
		data: JSON.stringify(request),
		contentType:"application/json ; charset=utf-8",
		dataType:"json",
		success:function(data) {
			if(data.status=="1") {
				alert(data.info);
				$('#userPswd').val("");
				$('#userPswd').focus();
			}else if(data.status=="2") {
				
			}else if(data.status=="0") {
				alert("登录成功")
			}
		}
		
	});
}

$(document).ready(function() {
	 init();
});


