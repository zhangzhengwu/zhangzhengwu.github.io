/**
 * Created by Jimmy on 2016/9/20.
 * 该封装相当于在$.post的基础上新增了beforeSend和complete功能
 */
(function($){ //放于匿名空间内，防止变量影响到其他库
    var loginPage = '../index.jsp'; //设置登录页信息，用于请求超时返回
    var item = '', txtColor, txtBgColor, bgColor, bgOpty, theme;
    jQuery.extend({
        myAjax: function(url, data, callback){
            var $this = this;
            $.ajax({
                type: 'post',
                url: url,
                data: data,
                beforeSend: function(){
                    //$this.loader();
                	top.showLoad();
                },
                complete: function(){
                    //$this.loadClose();
                	top.closeLoad();
                },
                success: function(result){
                    //后台数据响应结果遵循{msg: 'xxx', status: 'xxx', data: [{},{},{},{}]}格式
                   // var result = window.JSON ? JSON.parse(e) : eval('('+e+')');
                   // var result = window.JSON ? JSON.parse(e) : eval('('+e+')');
                    //var result = e;
                    //status= timeout->login.jsp  status= no rule->alert()
                	//{"status":"success","msg":"success","data":["monthly_report\/","中港通升职信息.xls"]}	
                	try{
                		result = window.JSON ? JSON.parse(result) : eval('('+result+')');
                		
                		//由于EMAP返回的为statu, 所以将原status改为statu
                		switch(result.statu){
	                        case 'timeout':
	                            alert(result.msg);
	                            location.href = loginPage;
	                            break;
	                        case 'norule':
	                            alert(result.msg);
	                            break;
	                        case 'error':
	                        	alert(result.msg);
	                        	break;
	                        case 'exception':
	                        	alert(result.msg);
	                        	break;
	                        default:
	                        	//console.log("msg:"+result.msg+",data:"+result.data);
	                            if(callback){//存在call参数时，将result结果打回调用方
	                                callback(result);
	                            }else{//若无callback参数, 即由无需调用方处理
	                                alert(result.msg);
	                            }
	                            break;
	                    }
	                    return false;
                	}catch(e){
                		console.log("url:"+url);
                		console.log("error:"+e);		
                		$this.loadClose();
                	}
                },
                error: function(){
                    alert('请求错误.');
                    $this.loadClose();
                }
            });
        },
        loader: function(txt, style){
        	txt = txt || 'Loading...',
                theme = style || 'dark', //默认白底遮罩
                txtColor =  theme == "light" ? '#444' : '#fff',
                txtBgColor = theme == "light" ? '#fff' : '#444',
				bgColor = style == "light" ? '#FFF' : '#000',
				bgOpty = style == "light" ? 2 : 6;                    
            this.loadInit(txt);
        },
        loadInit: function(txt){
            this.loadShow(txt);
        },
        loadShow: function(txt){
            var img, a, span;
            	item = $("<div></div>");
                item.appendTo(top.document.body)
                	.css({
                    'position': 'absolute',
                    'left': '0px',
                    'top': '0px',
                    'background': bgColor,
                    'filter': 'alpha(opacity='+bgOpty*10+')', //兼容IE
                    '-moz-opacity': bgOpty/10, //兼容Mozilla浏览器
                    '-khtml-opacity': bgOpty/10,  //兼容Safari浏览器
                    'opacity': bgOpty/10,  //兼容主流浏览器
                    'z-index': 888,
                    'zoom': 1,
                    'width': '100%',
                    'height': '100%'
                }),//显示在最顶层
                img = $('<img />');
                    img.attr('src','plug/img/'+theme+'/loading.gif')
                    .css({'margin': '0px 5px','height': '32px','width': '32px'}),
                span = $('<span></span>');
                    span.append(img)
                    .appendTo(item);
            $(span).css({
            	'position' : 'absolute',
                'top' : '30%',
                'left' : ($(top.window).width() - $(span).outerWidth())/2,
                'padding': '10px',
                'z-index': 999,
                'border-radius': '5px'
            });
            //console.log($(img).attr("src"));
            item.fadeIn();
        },
        loadClose: function(){
            item.remove();
        }
    });
})(jQuery)

