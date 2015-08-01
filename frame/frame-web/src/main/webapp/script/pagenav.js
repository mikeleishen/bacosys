
function pagenav(selector,colcount,count,pagesize,current,func){
	var pagecount=Math.ceil(count/pagesize)||1;
	var _qh=2;//前后
	var _sw=3;//首尾
	if(current>pagecount)current=pagecount;
	if(current<1)current=1;
	var $div=$('<div>').attr('class','pagenav');
	var i=1;
	var i2=pagecount<_sw?pagecount:_sw;
	for(;i<=i2;i++)$div.append($('<a>').attr('onclick',func+'('+i+')').text(i).attr('class',i==current?'n':'y'));
	i=current-_qh;
	i=i>i2?i:i2+1;
	if(i-i2>1)$div.append($('<span>').text('...').attr('class','ellipsis'));
	i2=current+_qh;
	i2=i2>pagecount?pagecount:i2;
	for(;i<=i2;i++)$div.append($('<a>').attr('onclick',func+'('+i+')').text(i).attr('class',i==current?'n':'y'));
	i=pagecount+1-_sw;
	i=i>i2?i:i2+1;
	if(i-i2>1)$div.append($('<span>').text('...').attr('class','ellipsis'));
	i2=pagecount;
	for(;i<=i2;i++)$div.append($('<a>').attr('onclick',func+'('+i+')').text(i).attr('class',i==current?'n':'y'));
	$div.append($('<input>').attr('class','pagenavjump').attr('type','text').val(current))
		.append($('<a>').text('GO').attr('onclick','var a=Math.abs(parseInt($(this).prev().val()))||1;a=a>'+pagecount+'?'+pagecount+':a;'+func+'(a)').attr('class','pagenavjumpbtn').attr('href','javascript:void(0)'))
		.append($('<span>').text('共'+count+'条记录'));
	var $navtd=$('<td>').attr('colspan',colcount).append($div);
	var $navtr=$(selector+'>tfoot>tr.pagenavtr');
	if($navtr.length>0){$navtr.html($('<td>').attr('colspan',colcount).append($div));}
	else{
		$tfoot=$(selector+'>tfoot');
		if($tfoot.length>0)$tfoot.html($('<tr>').attr('class','pagenavtr').append($navtd));
		else $(selector).append($('<tfoot>').append($('<tr>').attr('class','pagenavtr').append($navtd)));
	}
}
