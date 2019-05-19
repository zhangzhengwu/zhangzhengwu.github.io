/**
 * 数据加载中
 */
this.load = function() {
	showMessage("数据加载中...", 0.5);
};
this.success = function(info){
	$.dialog.tips(info, 3.0, '32X32/succ.png');
};
this.successAlert = function(info,callback){
	var dialog = $.dialog({title: '提示',content: info,icon: '32X32/succ.png',ok:true,close : callback}).lock();
};
this.error = function(info){
	if(typeof(info) == "undefined"){
		showErroMessage("系统出错...", 3);
	}else{
		showErroMessage(info, 3);
	}
};
this.errorAlert = function(info,callback){
	var dialog = $.dialog({title: '提示',content: info,icon: '32X32/fail.png',ok:true,close : callback}).lock();
};
this.tips=function(objValue) {
	$.dialog.tips("<span style='color:blue;'>"+objValue+ "</span>");
};
this.alertMessage = function(message) {
	if(message.length<10){
		message="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+message+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	$.dialog.alert(message);
	this.lock();
};
this.lock = function(){
	return false;
};
/**
 * 整个页面提示信息
 * @param {Object} message
 * @param {Object} timer
 */
this.showMessage = function(message, timer) {
	$.dialog.tips(message, timer);
};

this.showErroMessage = function(message, timer) {
	$.dialog.tips("<span style='color:red'>" + message + "</span>", timer,'32X32/fail.png');
};
/**
 * 只能输入数字
 * @param {Object} obj
 */
this.checkNumber=function (obj){
	obj.value=obj.value.replace(/\D/g,'');
};

this.myajax = function (url, data)
{
	$.ajax({
		type: "post",			
		url : url,
		data : data,
		success : function(msg)
		{
		 
		}			
	});		
};
/**
 * lhg弹框中的异步
 * @param url 链接
 * @param {Object} data
 */
this.mylhgAjax = function (url, data)
{
	$.ajax({
		type: "post",			
		url : url,
		data : data,
		success : function(msg)
		{
			if(msg.toString() == "1"){
				W.success("操作成功");
				W.select(1);
				api.close();
			}else{
				W.error("操作失败");
			}
		}			
	});
};
this._alert=function(msg){
	alert(msg);
}
this.mylhgAjaxs = function (url, data,func)
{
	$.ajax({
		type: "post",			
		url : url,
		data : data,
		dataType:'json',
		success : function(msg)
		{
			if(undefined ==func || func==null){
				if(msg.state == "success"){
					W.success("操作成功");
					W.select(1);
					api.close();
				}else{
					W.error(msg.msg);
				}
			}else{
				func(msg);
			}
		}			
	});		
};
this.myAjaxs = function (url, data,func)
{
	$.ajax({
		type: "post",			
		url : url,
		data : data,
		dataType:'json',
		success : function(msg)
		{
			if(undefined ==func || func==null){
				if(msg.state == "success"){
					success("操作成功");
					select(1);
				}else{
					error("操作失败");
				}
			}else{
				func(msg);
			}
		}			
	});		
};
this.mylhgAjax2 = function (url, data){
	$.ajax({
		type: "post",			
		url : url,
		data : data,
		success : function(date){
			var result=$.parseJSON(date);
			if(result.state=="success"){
				W.success("操作成功");
				W.selects(1);
				api.close();
			}else{
				W.error(result.msg);
			}
		}			
	});	
};

//lhg插件公共方法
this.mylhg = function (w, h, url, data, title)
{
	$.dialog( {
		title : title,
		id : 'menu_new',
		width : w,
		height : h,
		data : data,
		cover : true,
		drag : false,
		lock : true,
		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url: " + url
	});  
};
this.addlhg = function(w, h, url, data)
{
	$.dialog( {
		title : '新增',
		id : 'menu_new',
		width : w,
		height : h,
		data : data,
		cover : true,
		drag : false,
		lock : true,
		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url: " + url
	});  
};
this.editlhg = function(w, h, url, data)
{
	$.dialog( {
		title : '修改',
		id : 'menu_modify',
		width : w,
		height : h,
		data : data,
		cover : true,
		drag : false,
		lock : true,
		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url: " + url
	});  
};
this.infolhg = function(w, h, url, data)
{
	$.dialog( {
		title : '详情',
		id : 'menu_info',
		width : w,
		height : h,
		data : data,
		cover : true,
		drag : false,
		lock : true,
		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url: " + url
	});  
};
this.uploadlhg = function(w, h, url, data)
{
	$.dialog( {
		title : '附件上传',
		id : 'menu_upload',
		width : w,
		height : h,
		data : data,
		cover : true,
		drag : false,
		lock : true,
		focus : false,
		max : false,
		min : false,
		resize : false,
		content : "url: " + url
	});  
};
var namecard_staff_company="CFS,CAM,CIS,CCL,CFSH,CMS,CFG,Blank,CCIA,CCFSH,CWMC";
var namecard_consultant_company="CFS,CAM,CFSO";//,CIS,CCL
/**
 * 2017-02-14 jackie 要求隐藏CIS，CCL
 * @param id
 * @returns
 */
function getUeditorObj(id) {
return  UE.getEditor(id, {
		initialFrameHeight : 300,
		initialFrameWidth : 85+'%',//这里可以选择自己需要的工具按钮名称,此处仅选择如下五个  
                toolbars:[['FullScreen', 'Source', 'Undo', 'Redo','bold','Italic','Border','Superscript','Subscript','RemoveFormat','BlockQuote','PastePlain','ForeColor','backColor',
                	'InsertOrderedList','InsertUnorderedList','Underline','Paragraph','FontFamily','FontSize','DirectionalityLtr','DirectionalityRtl'
                	,'JustifyLeft',
'JustifyCenter',
'JustifyRight',
'JustifyJustify',
'Link',
'Image',
'Spechars',
'Horizontal',
'Date',
'Time',
/**
'Unlink',
'InsertTable',
'DeleteTable',
'InsertParagraphBeforeTable',
'InsertRow',
'DeleteRow',
'InsertCol',
'DeleteCol',
'MergeCells',
'MergeRight',
'MergeDown',
'SplittoCells',
'SplittoRows',
'SplittoCols',*/
'SelectAll',
'ClearDoc',
'SearchReplace',
'Preview','attachment','insertimage'/**,'imagenone', 'imageleft', 'imageright', 'imagecenter'*/]]});
}
