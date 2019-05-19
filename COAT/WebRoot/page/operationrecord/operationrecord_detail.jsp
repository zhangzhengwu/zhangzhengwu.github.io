<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Operation Record Detail</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
 	<script type="text/javascript" src="<%=basePath%>plug/js/jquery-1.7.1.min.js"></script>
	<link rel="stylesheet" href="<%=basePath%>plug/css/layout.css">
	<style type="text/css">
	*{
		margin: 0px;
		padding: 0px;
		font-size: 13px;
	}
	body{
		background: #fff!important;
	}
	table{width: 98%; margin:10px auto; border-collapse: collapse; background: #F8F8F8;}
	table td{vertical-align: top; text-align: left; padding: 5px 10px; border: 1px solid #ddd;}
	.tagName{ padding-right: 5px; width: 15%;}
	.tagCont{ padding-left: 5px; width: 35%;}
	.remark{position: relative; width: 98%; margin: 15px auto; border:1px solid #DDD; background: #F8F8F8; }
	</style>
  
  <body>
 	<table>
 		<tr>
 			<td class="tagName">Id：</td>
 			<td class="tagCont">
 				<span id="num"></span>
 			</td>
 			<td class="tagName">UserName：</td>
 			<td class="tagCont">
 				<span id="username"></span>
 			</td>
 		</tr>
 		<tr>
 			<td class="tagName">Ip Address：</td>
 			<td class="tagCont">
 				<span id="ipaddress"></span>
 			</td>
 			<td class="tagName">Http Address：</td>
 			<td class="tagCont">
 				<span id="httpaddress"></span>
 			</td>
 		</tr>
 		<tr>
 			<td class="tagName">Modula：</td>
 			<td class="tagCont">
 				<span id="modular"></span>
 			</td>
 			<td class="tagName">Operation：</td>
 			<td class="tagCont">
 				<span id="operation"></span>
 			</td>
 		</tr>
 		<tr>
 			<td class="tagName">Date：</td>
 			<td class="tagCont">
 				<span id="date"></span>
 			</td>
 			<td class="tagName">Result：</td>
 			<td class="tagCont">
 				<span id="result"></span>
 			</td>
 		</tr>
 	</table>
 	<div class="remark">
 		<a class="tagTitle">Parameter</a>
 		<div class="jstring" id="remark1"></div>
 	</div>
 	<div class="remark">
 		<a class="tagTitle">Result</a>
 		<div class="jstring" id="remark2"></div>
 	</div>
 	<script type="text/javascript">
 	var api = frameElement.api, W = api.opener;
 	
 	$(function(){
 		data = api.data;
 		initData();
 	});
 	api.button({
 		name : '确定'
 	});
 	
 	//解决i8的Object.keys不兼容
 	var DONT_ENUM =  "propertyIsEnumerable,isPrototypeOf,hasOwnProperty,toLocaleString,toString,valueOf,constructor".split(","),
    hasOwn = ({}).hasOwnProperty;
    for (var i in {
        toString: 1
    }){
        DONT_ENUM = false;
    }


    Object.keys = Object.keys || function(obj){//ecma262v5 15.2.3.14
            var result = [];
            for(var key in obj ) if(hasOwn.call(obj,key)){
                result.push(key) ;
            }
            if(DONT_ENUM && obj){
                for(var i = 0 ;key = DONT_ENUM[i++]; ){
                    if(hasOwn.call(obj,key)){
                        result.push(key);
                    }
                }
            }
            return result;
        };


    
    function extend(dst) {
        var h = dst.$$hashKey;
        for (var i = 1, ii = arguments.length; i < ii; i++) {
            var obj = arguments[i];
            if (obj) {
                var keys = Object.keys(obj);
                for (var j = 0, jj = keys.length; j < jj; j++) {
                    var key = keys[j];
                    dst[key] = obj[key];
                }
            }
        }


        setHashKey(dst, h);
        return dst;
    }
    //转换成html的json对象格式
 	function tranForJson(dataStr)
    {
    	if(typeof(dataStr) != 'object' || null==dataStr){
		    return dataStr;
	    }
	    var keyStr = Object.keys(dataStr), tempStr = dataStr;
	    html = '{';
	    for(var i=0; i<keyStr.length; i++){
	    	/**if(i < keyStr.length-1){
	    		html += '<span>"'+keyStr[i]+'" : "' + tempStr[keyStr[i]] + '",</span>';
	    	}else{
	    		html += '<span>"'+keyStr[i]+'" : "' + tempStr[keyStr[i]] + '"</span>';
	    	}*/
	    	html += '<div>';
	    	type = typeof(tempStr[keyStr[i]]);
	    	if(type == 'object'){
	    		html += '<span class="skey">"'+keyStr[i]+'"</span><span class="ssign"> : </span><span class="sval">' + tranForJsonSub(tempStr[keyStr[i]]);
	    	}else{
	    		html += '<span class="skey">"'+keyStr[i]+'"</span><span class="ssign"> : </span><span class="sval">"' + tempStr[keyStr[i]]+'"';
	    	}
	    	if(i < keyStr.length-1){
	    		html += ',';
	    	}
	    	html += '</span></div>';
	    }
	    html +='}';
	  
	    return html;
    }
 	//处理remark中的object
 	function tranForJsonSub(dataStr){
 		var keyStr = new Array(), tempStr = new Array();
 		var html ='[';
 		for(var i=0; i<dataStr.length; i++){
 			keyStr = Object.keys(dataStr[i]);
 			html += '<div class="subJstring">{';
 			for(var j=0; j<keyStr.length; j++){
 				html += '<span>"';
 				html += keyStr[j] + '" : "' + dataStr[i][keyStr[j]];
 				if(j < keyStr.length-1){
 					html += '",'
 				}else{
 					html +='"';
 				}
 				html += '</span>';
 			}
 			html += '}';
 			//最后一个不需要加','
 			if(i < dataStr.length-1){
 				html += ',';
 			}
 			html += '</div>';
 		}
 		html += ']';
 		return html;
 	}
 	//将字符串转为对象, 并抛出异常
 	function transObj(str){
 		
 		var temp =null;
 		str=str.replace("\r","").replace("\n","");
 		try{
 		temp = $.parseJSON(str);
 			
 		}catch(e){
 			//temp = "";
 			temp = eval("("+str+")");
 		}
 		return temp;
 	}
 	function initData()
 	{
 		var obj = data.obj; 
 		remark1 = transObj(obj.remark1);
 		//alert(obj.remark1);
 		//alert(obj.remark2);
 		remark2 = transObj(obj.remark2);
 		//remark2 = transObj('[{\"name\":\"菜鸟教程\",\"site\":\"http://www.runoob.com\"}]');
  		remark1 = tranForJson(remark1);
		$('#num').text(data.id);
  		$('#username').text(obj.username);
 		$('#ipaddress').text(obj.ipaddress);
 		$('#httpaddress').text(obj.httpaddress.replace('<%=basePath%>',''));
 		$('#modular').text(obj.modular);
 		$('#operation').text(obj.operation);
 		$('#date').text(obj.date);
 		$('#result').text(obj.result);
 		$('#remark1').html(remark1);
 		if("page"==remark2.state){
 			remark2.data="";
 		}
 		remark2= tranForJson(remark2);
 		$('#remark2').html(remark2);
 	}
 	
 
 	</script>
  </body>
</html>
