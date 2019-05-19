/* 扩展窗口外部方法 */
$.dialog.notice = function( options )
{
    var opts = options || {},
        api, aConfig, hide, wrap, top,
        duration = opts.duration || 800;
        
    var config = {
        id: 'Notice',
        left: '100%',
        top: '100%',
        fixed: true,
        drag: false,
        focus:true,
        resize: false,
        init: function(here){
            api = this;
            aConfig = api.config;
            wrap = api.DOM.wrap;
            top = parseInt(wrap[0].style.top);
            hide = top + wrap[0].offsetHeight;
                        
            wrap.css('top', hide + 'px')
            .animate({top: top + 'px'}, duration, function(){
                opts.init && opts.init.call(api, here);
            });
           
        },
        close: function(here){
            wrap.animate({top: hide + 'px'}, duration, function(){
                opts.close && opts.close.call(this, here);
                aConfig.close = $.noop;
                api.close();
            });
                        
            return false;
        }
    };
        
    for(var i in opts)
    {
        if( config[i] === undefined ) config[i] = opts[i];
    }
        
    return $.dialog( config );
};

/**
 * 显示信息
 * @param {Object} context
 */
function showInfo(context){
		/* 调用示例 */
	var s=$.dialog.notice({
	    title: '信息提示',
	    width: 300,  /*必须指定一个像素宽度值或者百分比，否则浏览器窗口改变可能导致lhgDialog收缩 */
	    height:200,
	    content: context,
	    focus:true,
	    time: 10
	});
	
}
/**
 * 
 */
function createBox() {
	$.ajax({
		url:"SystemMsgAction?method=getSystemmsg",
		type:"post",
		data:null,
		dataType: "json",//返回json格式的数据
		success:function(obj){
		
			if (obj.Stu == '1' || obj.Stu == 1) {
				//set content 
				if (obj.Msg.length > 0) {
					var dataList = obj.Msg;
					showInfo(dataList[0].msgContent);
				}else{
					alert("no content");
				}
			}
		},error:function(){
			alert("身份信息丢失,请重新登录...");
			top.location.href="index.jsp";
		}
	});
	
 
}








