var test_status=true;//是否为测试状态

/**
 * 规范内容显示
 * 表格中过长的内容截掉
 */
function standardChar(num,c){
	var result="";
	if(c.length>=num){
		result=c.substring(0,num)+"...";
	}else{
		result=c;
	}
	return result;
}
/**
 * 根据特定字符截取字符
 * @param {Object} child
 * @param {Object} father
 * @return {TypeName} 
 */
function standardChar2(child,father){
	var result="";
	  var str= new Array();   
	var str=father.split(child);
	if(str.length>2){
		result=str[0]+child+str[1]+"...";
	}else{
		result=father;
	}
	return result;
}

/** 
 * 替换字符串中所有 
 * @param obj   原字符串 
 * @param str1  替换规则 
 * @param str2  替换成什么 
 * @return  替换后的字符串 
 */  
function replaceAll(obj,str1,str2){         
      var result  = obj.replace(eval("/"+str1+"/gi"),str2);        
      return result;  
}  

//验证数字，控制小数点为2位
function checkDouble(obj)
{
	var v = obj.value; 
	if(v.indexOf('.')==0){
		obj.value="";
	}else if(v.indexOf('.')>0 && v.lastIndexOf('.')<v.length-2 && v.length-2>=0){
		obj.value=v.substring(0,v.lastIndexOf('.')+3).replace(/[^\d\.]/g,'');
	}else if(v.indexOf("..")==v.length-2 && v.length-2>=0){//判断小数点是不是最后一位
		 obj.value=v.substring(0,v.length-1).replace(/[^\d\.]/g,'');
	}else{
		if(v.indexOf('.')>0 && v.indexOf('.')<v.length-2 && v.length-2>=0){
			if(v.lastIndexOf('.')==v.length-1){
				obj.value=v.substring(0,v.length-1).replace(/[^\d\.]/g,'');
			}else{
				 obj.value = v.replace(/[^\d\.]/g,'');
			}
		}else{
			 obj.value = v.replace(/[^\d\.]/g,'');
		}
	}
	return obj.value;
}
/**
 * 只读文本框禁用退格键
 * 
 * */
function BackSpace(e){
	e = e ? e :event;// 兼容FF 
	var es=null;
	if(navigator.userAgent.indexOf("MSIE")>0) {  
		es=e.srcElement;
	}else{
		es=e.target;
	}
	
	if(e.keyCode==8){//屏蔽退格键
	    var type=es.type;//获取触发事件的对象类型
	  //var tagName=window.event.srcElement.tagName;
	  	var reflag=es.readOnly;//获取触发事件的对象是否只读
	  	var disflag=es.disabled;//获取触发事件的对象是否可用
		  if(type=="text"||type=="textarea"){//触发该事件的对象是文本框或者文本域
			   if(reflag||disflag){//只读或者不可用
				  if(navigator.userAgent.indexOf("MSIE")>0)  
				 	 window.event.returnValue=false;//阻止浏览器默认动作的执行
			  	  else
			  		  e.returnValue=false;
			   }
		  }else{ 
		  	 if(navigator.userAgent.indexOf("MSIE")>0)  
				 	 window.event.returnValue=false;//阻止浏览器默认动作的执行
			  	  else
			  		  e.returnValue=false;
		  }
 	}
}



function checkNum(obj){
	var v=obj.value.length>5?obj.value.substring(0,5).replace(/\D/g,''):obj.value.replace(/\D/g,'');
		//obj.value=v.length>=3?(v%100==0?v:v-(v%100)):v;//当对象的长度大于等于3位数时必须是100的倍数
		obj.value=v.length>=3?(v%100==0?v:v-(v%100)):v;//当对象的长度大于等于3位数时必须是100的倍数
	if(v.length==2 && v.substring(1,2)!=0){//当对象的第二位数不为0时
		//obj.value=(obj.value/100)*100;//强制把对象的第二位数改为0，（缺点不能输入当出现四位数是第二位依然不能为其他数）
		obj.value=obj.value.substring(0,1)*10;//强制把对象的第二位数改为0，（缺点不能输入当出现四位数是第二位依然不能为其他数）
	}
}

//验证字母数字 且字符长度不不能大于18
function checkcode(obj){
	var v = obj.value.toUpperCase(); 
	obj.value=(v.length>18?v.substring(0,18):v).replace(/[^\w\.\/]/ig,'');
}
function addZero(obj){
	var valu=obj.value;
	if(valu.indexOf('.')==(valu.length-1)){
		obj.value=valu+"0";  
	  }
	
}
/**********新增顾问 验证身份证**************************/
function checkParseIdCard(val) {
    var msg = checkIdcard(val); 
    if (msg != "验证通过!") {
        alert(msg);
        return;
    } 
    var birthdayValue="";
    var sexId="";
    var sexText="";
    var age=""; 
    if (15 == val.length){ //15位身份证号码
        birthdayValue = val.charAt(6) + val.charAt(7);
        if (parseInt(birthdayValue) < 10) {
            birthdayValue = '20' + birthdayValue;
        }
        else {
            birthdayValue = '19' + birthdayValue;
        }
        birthdayValue = birthdayValue + '-' + val.charAt(8) + val.charAt(9) + '-' + val.charAt(10) + val.charAt(11);
        if (parseInt(val.charAt(14) / 2) * 2 != val.charAt(14)) {
          //  sexId = "1";
            sexText = "男";
        }
        else {
           // sexId = "2";
            sexText = "女";
        }
    }
    if (18 == val.length) { //18位身份证号码
        birthdayValue = val.charAt(6) + val.charAt(7) + val.charAt(8) + val.charAt(9) + '-' + val.charAt(10) + val.charAt(11) + '-' + val.charAt(12) + val.charAt(13);
        if (parseInt(val.charAt(16) / 2) * 2 != val.charAt(16)) {
          //  sexId = "1";
            sexText = "男";
        }
        else {
           // sexId = "2";
            sexText = "女";
        }
    }
     //年龄
    var dt1 = new Date(birthdayValue.replace("-", "/"));
    var dt2 = new Date();
    var age = dt2.getFullYear() - dt1.getFullYear();
    var m = dt2.getMonth() - dt1.getMonth();
    if (m < 0)
        age--; 
    if(11!=val.length){
    $("#birthday").attr("readonly","readonly").val(birthdayValue);
    $("#age").val(age);
    $("#sex").val(sexText);
    }
   // alert(birthdayValue+sexText+age);
} 
  function checkIdcard(idcard) {
    var Errors = new Array(
					"验证通过!",
					"身份证号码位数不对!",
					"身份证号码出生日期超出范围或含有非法字符!",
					"身份证号码校验错误!",
					"身份证地区非法!"
					);
    var area = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外" }
    var idcard, Y, JYM;
    var S, M;
    var idcard_array = new Array();
    idcard_array = idcard.split("");
    //地区检验 
    if (area[parseInt(idcard.substr(0, 2))] == null && idcard.substr(0, 1).match("/[A-Za-z][0-9]{7}[(][0-9][)]/")==false) return Errors[4];
    //身份号码位数及格式检验 
    switch (idcard.length) {
        case 15:
            if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0)) {
                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; //测试出生日期的合法性 
            } else {
                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; //测试出生日期的合法性 
            }
            if (ereg.test(idcard)) return Errors[0];
            else return Errors[2];
            break;
        case 18:
            //18位身份号码检测 
            //出生日期的合法性检查  
            //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9])) 
            //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8])) 
            if (parseInt(idcard.substr(6, 4)) % 4 == 0 || (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard.substr(6, 4)) % 4 == 0)) {
                ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/; //闰年出生日期的合法性正则表达式 
            } else {
                ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/; //平年出生日期的合法性正则表达式 
            }
            if (ereg.test(idcard)) {//测试出生日期的合法性 
                //计算校验位 
                S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
				+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
				+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
				+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
				+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
				+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
				+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
				+ parseInt(idcard_array[7]) * 1
				+ parseInt(idcard_array[8]) * 6
				+ parseInt(idcard_array[9]) * 3;
                Y = S % 11;
                M = "F";
                JYM = "10X98765432";
                M = JYM.substr(Y, 1); //判断校验位 
                if (M == idcard_array[17]) return Errors[0]; //检测ID的校验位 
                else return Errors[3];
            }
            else return Errors[2];
            break;
            case 11:
            	return Errors[0];
            	break;
        default:
            return Errors[1];
            break;
    }
} 

/*****************新增顾问 验证日期**********************************************************************************/
		 function checkymd(){
			var a = /^(\d{4})-(\d{2})-(\d{2})$/
			if (!a.test($("#birthday").val())) { 
				alert("日期格式不正确!(yyyy-MM-dd)"); 
				$("#birthday").focus();
				return false; 
			} 
			else {
				var result = $("#birthday").val().match(/((^((1[8-9]\d{2})|([2-9]\d{3}))(-)(10|12|0?[13578])(-)(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(11|0?[469])(-)(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(0?2)(-)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)(-)(0?2)(-)(29)$)|(^([3579][26]00)(-)(0?2)(-)(29)$)|(^([1][89][0][48])(-)(0?2)(-)(29)$)|(^([2-9][0-9][0][48])(-)(0?2)(-)(29)$)|(^([1][89][2468][048])(-)(0?2)(-)(29)$)|(^([2-9][0-9][2468][048])(-)(0?2)(-)(29)$)|(^([1][89][13579][26])(-)(0?2)(-)(29)$)|(^([2-9][0-9][13579][26])(-)(0?2)(-)(29)$))/); 
		        if(result==null) 
		        { 
		            alert("请输入正确的日期"); 
		            $("#birthday").focus();
		            return false; 
		        }else{
		        	return true;	
		        }
			} 
		}
		 function checkymd_all(obj){
			var a ="/^(\d{4})-(\d{2})-(\d{2})$/";
			if (!a.test($(obj).val())) { 
				alert("日期格式不正确!(yyyy-MM-dd)");
				$(obj).val("").focus();
				return false; 
			} 
			else {
				var result = $(obj).val().match(/((^((1[8-9]\d{2})|([2-9]\d{3}))(-)(10|12|0?[13578])(-)(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(11|0?[469])(-)(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))(-)(0?2)(-)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)(-)(0?2)(-)(29)$)|(^([3579][26]00)(-)(0?2)(-)(29)$)|(^([1][89][0][48])(-)(0?2)(-)(29)$)|(^([2-9][0-9][0][48])(-)(0?2)(-)(29)$)|(^([1][89][2468][048])(-)(0?2)(-)(29)$)|(^([2-9][0-9][2468][048])(-)(0?2)(-)(29)$)|(^([1][89][13579][26])(-)(0?2)(-)(29)$)|(^([2-9][0-9][13579][26])(-)(0?2)(-)(29)$))/); 
		        if(result==null) 
		        { 
		            alert("请输入正确的日期"); 
		            $(obj).focus();
		            return false; 
		        }else{
		        	return true;	
		        }
			} 
		}
		
function CurentTimeT()//提前三个月
    { 
        var now = new Date();
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1+3;     //月
        var day = now.getDate();            //日
        var clock = year + "-";
         if(month < 10)
            clock += "0";
        clock += month + "-";
        if(day < 10)
           clock += "0";
        clock += day + "";
        return clock; 
    } 
function CurentTimeO()//提前一个月
    { 
        var now = new Date();
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1+1;     //月
        var day = now.getDate();            //日
        var clock = year + "-";
         if(month < 10)
           clock += "0";
       	   clock += month + "-";
        if(day < 10)
           clock += "0";
       	   clock += day + "";
        return clock; 
    } 
    
 /****控制单选按钮操作******/
  function isCheck(obj){   
       var hasCheck = false;   
        for(var i=0; i<radioArr.length; i++){   
            if(radioArr[i]==obj){   
               radioArr[i].checked = false;   
               radioArr.splice(i,1); //移除对象   
               hasCheck = true;   
               break;   
           }   
       }   
        if(!hasCheck){   
           initRadioArr();   
        }   
        //alert(radioArr.join("-"));   
    } 
  
  /**
   * 比较两个时间的大小
   * @param {Object} startDate
   * @param {Object} endDate
   * return 1:大于 2：等于 3：小于
   */
  function compareDate(startDate,endDate){
	  startDate = startDate.replace(/\-/gi,"/");
	  endDate = endDate.replace(/\-/gi,"/");
	  var time1 = new Date(startDate).getTime();
	  var time2 = new Date(endDate).getTime();
	
	  if(time1 > time2){
		  return 1;	
	  }else if(time1 == time2){	
		  return 2;
	  }else{
		  return 3;	
	  }
  }
  /**
   * 比较时间
   * @param {Object} beginTime
   * @param {Object} endTime
   * @return {TypeName} 
   * 1. beginTime > endTime
   * 0. beginTime == endTime
   *-1. beginTime < endTime
   */
  function comptime(beginTime,endTime) {
    var beginTimes = beginTime.substring(0, 10).split('-');
    var endTimes = endTime.substring(0, 10).split('-');
    beginTime = beginTimes[1] + '-' + beginTimes[2] + '-' + beginTimes[0] + '' + beginTime.substring(10, 19);
    endTime = endTimes[1] + '-' + endTimes[2] + '-' + endTimes[0] + '' + endTime.substring(10, 19);
    var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
    if (a < 0) {
		return 1; //alert("endTime小!");
    } else if (a > 0) {
		return -1; //alert("endTime大!");
    } else if (a == 0) {
	   return 0; // alert("时间相等!");
    } else {
        return 'exception'
    }
}
  
  /**
   * 获取DateTime 相差天数的日期
   * @param {Object} DateTime 为时间的数组
   * @param {Object} AddDayCount 相差的天数
   * @return {TypeName} 
   */
function GetDateStr(DateTime,AddDayCount) {
    var dd = new Date(DateTime[0],DateTime[1]-1,DateTime[2]);
    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
    var y = dd.getFullYear();
    var m = (dd.getMonth()+1)<10?("0"+(dd.getMonth()+1)):(dd.getMonth()+1);//获取当前月份的日期
    var d = (dd.getDate())<10?("0"+(dd.getDate())):(dd.getDate());
    return y+"-"+m+"-"+d;
    
}

 /*
*获取當前日期
* date : Date类型.
*/
function getMonthBeforeDay(date){
	
	 var yesterday_milliseconds=date.getTime();  //-1000*60*60*24   
	 var yesterday = new Date();     
     yesterday.setTime(yesterday_milliseconds);     
   
	 var strYear = yesterday.getFullYear();  
	 var strDay = yesterday.getDate();  
	 var strMonth = yesterday.getMonth()+1;
	 if(strMonth<10)  
	 {  
	  strMonth="0"+strMonth;  
	 }  
	 if(strDay<10)  
	 {  
	  strDay="0"+strDay;  
	 }  
	 datastr = strYear+"-"+strMonth+"-"+strDay;
	 return datastr;
}
  


/**
 * Key Request Email Content
 * @param {Object} location楼层
 * @return {TypeName} 生成的邮件内容
 */
function email_key(location,money){
	var content="Dear Sir/Madam,<br/>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;Please collect your requested key with ($"+money+" / FOC) handling charge at (location: "+location+" Mailing Room)."
		content+="<br/><br/>Best Regards,<br/>";
		content+="Administration<br/>";
		content+="Operations Department";
				return content;
}
/**
 * Seat Assignment Email Content
 * @param {Object} location楼层
 * @return {TypeName} 生成的邮件内容
 */
function email_seat(location){
	var content=" Dear Sir / Madam,<br/>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;Please collect the relevant keys (Drawer, Locker, Pigeon Box) of your new seat at location: "+location+" Mailing Room.";
		content+="<br/><br/>Best Regards,<br/>";
		content+="Administration<br/>";
		content+="Operations Department";
	return content;
}
/**
 * Company Asset Email Content
 * @return {TypeName} 生成的邮件内容
 */
function email_companyAsset(){
	var content=" Dear Sir / Madam,<br/>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;Please collect your requested company asset at (location: 40/F Mailing Room).";
		content+="<br/><br/>Best Regards,<br/>";
		content+="Administration<br/>";
		content+="Operations Department";
	return content;
}
/**
 * Marketing Premium Email Content
 * @param {Object} location 楼层
 * @return {TypeName} 生成的邮件内容
 */
function email_marktingPremium(location){
		var content=" Dear Sir / Madam,<br/>";
		if(location=="148 Electric Road" || location=="Peninsula")
			content+="&nbsp;&nbsp;&nbsp;&nbsp;ADM will send you your requested Mkt Premium by internal transfer today.";	
		else
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Please collect your requested Mkt Premium at (location: "+(location=="CP3"?"17/F":"40/F")+" Mailing Room)";
			content+="<br/><br/>Best Regards,<br/>";
			content+="Administration<br/>";
			content+="Operations Department";
		return content;
}

/**
 * 
 * @param {Object} staffname
 * @param {Object} date1
 * @param {Object} date2
 * @param {Object} Person
 * @param {Object} email
 * @param {Object} mediaName
 * @return  content (confirmation Request 生成的邮件内容)
 */
function email_confirmation(staffname,date1,date2,Person,email,mediaName){
	var content="Dear "+staffname+",<br/>";
	content+="&nbsp;&nbsp;&nbsp;&nbsp;Please confirm the below information of the coming Recruitment Advertising on or before 12:00 noon "+date1+"<br/>";
	content+="<br/>Posting Period: "+date2+"<br/>";
    content+="<br/>Media Name: "+mediaName+"<br/>";
    content+="<br/>Contact Person: "+Person+"<br/>";
    content+="<br/>Contact Email: "+email+"<br/>";
    content+="<br/><br/>Best Regards,<br/>";
	content+="Administration<br/>";
	content+="Operations Department";
    return content;
}
/**
 * 
 * @param {Object} recruitmentName
 * @param {Object} money
 * @param {Object} staffname
 * @param {Object} staffcode
 * @param {Object} mediaName
 * @param {Object} date2
 * @param {Object} Person
 * @param {Object} email
 * @return {TypeName}  (Completed   生成邮件内容)
 */
function eamil_conpleted(money,staffname,staffcode,mediaName,date2,Person,email){
	var content="Dear "+staffname+",<br/>";
	    content+="&nbsp;&nbsp;&nbsp;&nbsp;Please be informed that HK$ "+money+" will be deducted from "+staffcode+ " - " +staffname+"'s Commission for the placement of the following Recruitment Advertising"+"<br/>";
	    content+="<br/>Media Name: "+mediaName+"<br/>";
	    content+="<br/>Posting Period: "+date2+"<br/>";
	    content+="<br/>Contact Person: "+Person+"<br/>";
        content+="<br/>Contact Email: "+email+"<br/>";
        content+="<br/><br/>Best Regards,<br/>";
	    content+="Administration<br/>";
		content+="Operations Department";
        return content;
}
/**
 *  Stationery Email Content
 * @param {Object} location 楼层
 * @return {TypeName} 生成的邮件内容
 */
function email_Stationery(location){
		var content=" Dear Sir / Madam,<br/>";
		if(location=="148 Electric Road" || location=="Peninsula")
			content+="&nbsp;&nbsp;&nbsp;&nbsp;ADM will send you your requested Stationery by internal transfer today.";	
		else
			content+="&nbsp;&nbsp;&nbsp;&nbsp;Please collect your requested Stationery at (location: "+(location=="CP3"?"17/F":"40/F")+" Mailing Room)";
			
			content+="<br/><br/>Best Regards,<br/>";
			content+="Administration<br/>";
			content+="Operations Department";
			
		return content;
}


/**
 * AccessCard Email Content
 * @param {Object} location  楼层(当type==photosticker==>楼层+@convoy or CP3)
 * @param {Object} type  类型：staffcard,photosticker
 * @return {TypeName} 
 */
function email_accessCard(location,type){
		var content="Dear Sir / Madam,<br/>";
		if(location=="148 Electric Road" || location=="Peninsula"){
			if(type=="staffcard")
				content+="&nbsp;&nbsp;&nbsp;&nbsp;ADM will send you your re-issued access card by internal transfer today. Please send $50 handling charges OR return your damaged card to ADM in @CONVOY by internal transfer. ";
			else if(type=="photosticker")
				content+="&nbsp;&nbsp;&nbsp;&nbsp;ADM will send you your photo sticker by internal transfer today. ";
			else
				content+="&nbsp;&nbsp;&nbsp;&nbsp;ADM will send you your re-issued access card and photo sticker by internal transfer today. Please send $50 handling charges OR return your damaged card to ADM in @CONVOY by internal transfer. ";
		}else{
			if(type=="staffcard"){
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Please collect your re-issued access card with $50 handling charges at (location: "+(location=="CP3"?"17/F":"40/F")+" Mailing Room). OR exchange your card with your damaged card. ";
			}else{
				content+="&nbsp;&nbsp;&nbsp;&nbsp;Please collect your photo sticker at "+(location=="CP3"?"17/F":"40/F")+" "+location+" Mailing Room";
			}
		}
		content+="<br/><br/>Best Regards,<br/>";
		content+="Administration<br/>";
		content+="Operations Department";
		return content;
}


function email_seat_Consultant(location,conten){
	var content="Dear Sir/Madam,<br/><br/>";
		content+="&nbsp;&nbsp; Please Be advised that the following seat arrangement will be effective from next Monday.<br/><br/>"
		content+="<style type='text/css'>table{border-collapse:collapse;border:1px solid #999; width:800px;}tr td{border:1px solid #999;text-align:center;}</style>" ;
		content+="&nbsp;&nbsp; &nbsp;&nbsp; <table cellpadding=0 cellspacing=0>";
		content+="<tr style='background-color:black;font-color:white;line-height:25px;'><td>Staff Code</td><td>Name</td><td>Seat Number</td><td>Pigeon Hole</td><td>Ext.#</td><td>Location</td></tr>"
		content+=conten;
		content+="</table><br/>";
		content+="ADM will notify you by email again for key collection";
		
		content+="<br/><br/>Best Regards,<br/>";
		content+="Administration<br/>";
		content+="Operations Department";
		
		return content;
}



/**
 * 发送邮件
 * @param {Object} to 收件人
 * @param {Object} context 邮件内容
 * @return {TypeName} 邮件发送状态
 */
function sendMail(to,context){
	var result="";
	$.ajax({
		url:"/ExchangeMail/SendMailServlet",
		type:"post",
		async:false,
		data:{ "to":to==""?"King.Xu@convoy.com.hk":to,
				"cc":null,
				"attr":null,
				"subject":"COAT Request Notice",
				"body":context,
				"webapp":"COAT"},
		success:function(date){
			result="Email==>"+date;
		},error:function(){
			result="Email==>网络连接失败!";
		}
	});
return result;
}
/**
 * 发送邮件带抄送
 * @param {Object} to 收件人
 * @param {Object} context 邮件内容
 * @return {TypeName} 邮件发送状态
 */
function sendMails(to,context){
	var result="";
	$.ajax({
		url:"/ExchangeMail/SendMailServlet",
		type:"post",
		async:false,
		data:{ "to":to==""?"King.Xu@convoy.com.hk":to,
				"cc":"adminfo@convoy.com.hk",
				"attr":null,
				"subject":"COAT Request Notice",
				"body":context,
				"webapp":"COAT"},
		success:function(date){
			result="Email==>"+date;
		},error:function(){
			result="Email==>网络连接失败!";
		}
	});
return result;
}


/**
 * 根据staffcode、userType==>Email
 * @param {Object} staffcode
 * @param {Object} userType
 * @return {TypeName} 
 */
function getEmail(staffcode,userType){
	var emails=null;
	$.ajax({
		url:"VailEmailServlet",
		type:"post",
		async:false,
		data:{ "method":"Email",
			"staffcode":staffcode,	
			"userType":userType
		},success:function(date){
			emails=date;
		},error:function(){
			emails="Exception";
		}
	});
	return emails;
}
/**
 * 获取上级Mail
 * @param {Object} staffcode
 * @param {Object} userType
 * @return {TypeName} 
 */
function getRecruiterEmail(staffcode,userType){
	var emails=null;
	$.ajax({
		url:"VailEmailServlet",
		type:"post",
		async:false,
		data:{ "method":"RecruiterEmail",
			"staffcode":staffcode
		},success:function(date){
			emails=date;
		},error:function(){
			emails="Exception";
		}
	});
	return emails;
}


function sendMailDept(to,context){
		var result="";
	$.ajax({
		url:"/ExchangeMail/SendMailServlet",
		type:"post",
		async:false,
		data:{ "to":to==""?"King.Xu@convoy.com.hk":to,
				"cc":null,
				"attr":null,
				"subject":"Please approve a new name card request.",
				"body":"<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;"+context+" just submitted a name card request, please go to <a target='_blank' href='http://www.econvoy.com'>www.econvoy.com</a>→Convoy→Tools→Office Admin To approve it.",
				"webapp":"COAT"},
		success:function(date){
			result="Email==>"+date;
		},error:function(){
			result="Email==>网络连接失败!";
		}
	});
return result;
}


/**
 * 发邮件给HR
 * @param {Object} to
 * @param {Object} context
 * @return {TypeName} 
 */
function sendMailHR(to,context){
		var result="";
	$.ajax({
		url:"/ExchangeMail/SendMailServlet",
		type:"post",
		async:false,
		data:{ "to":"yan.tsang@convoy.com.hk;HRD.info@convoy.com.hk",
				"cc":null,
				"attr":null,
				"subject":"Staff name card request approval.",
				"body":context+" just approved/submitted a name card request, please approve to SZOAdm for processing.",
				"webapp":"COAT"},
		success:function(date){
			result="Email==>"+date;
		},error:function(){
			result="Email==>网络连接失败!";
		}
	});
return result;
}


function sendMailForRecruitment(to,cc,context){
	var result="";
	$.ajax({
		url:"/ExchangeMail/SendMailServlet",
		type:"post",
		async:false,
		data:{ 
				"to":to==""?"King.Xu@convoy.com.hk":to,
				"cc":cc+";adminfo@convoy.com.hk",
				//"to":"king.xu@convoy.com.hk",
				
				"attr":null,
				"subject":" COAT Request Notcie of Advertising Placement",
				"body":context,
				"webapp":"COAT"},
		success:function(date){
			result="Email==>"+date;
		},error:function(){
			result="Email==>网络连接失败!";
		}
	});
return result;
}

/**
 * 检验null转成空字符串
 * @param va
 * @returns
 */
function modifyString(va){
	if(va=="null"||va==null){
		va="";
	}
	return va;
}











