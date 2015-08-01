/*
 * --------------------------------------------------------------------
 * jQuery-Plugin - $.download - allows for simple get/post requests for files
 * by Scott Jehl, scott@filamentgroup.com
 * http://www.filamentgroup.com
 * reference article: http://www.filamentgroup.com/lab/jquery_plugin_for_requesting_ajax_like_file_downloads/
 * Copyright (c) 2008 Filament Group, Inc
 * Dual licensed under the MIT (filamentgroup.com/examples/mit-license.txt) and GPL (filamentgroup.com/examples/gpl-license.txt) licenses.
 * --------------------------------------------------------------------
 */
 
jQuery.download = function(url, data){
	//url and data options required
	if( url && data){
		//split params into form inputs
		var data_len=data.length;
		var form=jQuery('<form></form>');
		for(var i=0;i<data_len;i++){
			form.append(jQuery('<input />').attr({'type': 'hidden', 'name': 'param'+i, 'value': data[i]}));
		}
		//send request
		form.attr({'action': url, 'method': 'post'}).appendTo('body').submit().remove();
	};
};