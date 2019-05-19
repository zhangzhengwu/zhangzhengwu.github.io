

/**
 * 设置Cookie
 * @param {Object} name //cookie 名
 * @param {Object} value //cookie 值
 * @param {Object} Days //有效时间 单位天
 */

function SetCookieDays(name,value,Days) { //两个参数，一个是cookie的名子，一个是值 
    if(Days=="" || Days==null)
    	Days=30;
    var exp = new Date();    //new Date("December 31, 9998"); 
     exp.setTime(exp.getTime() + Days*24*60*60*1000); 
     document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString(); 
} 

/**
 * 设置Cookie
 * @param {Object} name //cookie 名
 * @param {Object} value //cookie 值
 * @param {Object} Hours //有效时间 单位小时
 */

function SetCookieHours(objName,objValue,objHours) { //两个参数，一个是cookie的名子，一个是值 

 var str = objName + "=" + escape(objValue); 
     if(objHours > 0){                               //为时不设定过期时间，浏览器关闭时cookie自动消失 
        var date = new Date(); 
        var ms = objHours*3600*1000; 
         date.setTime(date.getTime() + ms); 
         str += "; expires=" + date.toGMTString(); 
  	 } 
   document.cookie = str; 

} 


/**
 * 根据Cookie名获取值
 * @param {Object} name Cookie名称
 * @return {TypeName}  Cookie名称对应的值
 */
function getCookie(name){ //取cookies函数         
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)")); 
     if(arr != null) 
    	 return unescape(arr[2]); 
     return null; 

  } 

/**
 * 根据Cookie名获取值
 * @param {Object} name Cookie名称
 * @return {TypeName}  Cookie名称对应的值
 */
function getCookies(objName){//获取指定名称的cookie的值 
     var arrStr = document.cookie.split("; "); 
     for(var i = 0;i < arrStr.length;i ++){ 
         var temp = arrStr[i].split("="); 
          if(temp[0] == objName) 
        	  return unescape(temp[1]); 
     }  
 } 



/**
 * 根据Cookie删除指定Cookie
 * @param {Object} name  Cookie 名称
 */
function delCookie(name){ //删除cookie 
    var exp = new Date(); 
    exp.setTime(exp.getTime() - 1); 
   var cval=getCookie(name); 
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString(); 
} 






