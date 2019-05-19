
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

}
/**
 * 只能输入数字
 * @param {Object} obj
 */
function checkNumber(obj){
	obj.value=obj.value.replace(/\D/g,'');
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

 /***
   * 获取当前页面参数
   * @param {Object} paras
   * @return {TypeName} 
   */
  function requests(paras)    {  
	  var url = location.href;    
	  var paraString = url.substring(url.indexOf("?")+1,url.length).split("&");   
	  var paraObj = {}   
	  for (i=0; j=paraString[i]; i++){    
		  paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length);     
		  }         var returnValue = paraObj[paras.toLowerCase()];       
		  if(typeof(returnValue)=="undefined"){      
			  return "";    
			  }else{       
				  return returnValue;      
				  }   
		  }
 
	
	

//身份证 顾问看下属的，只能看年月日
function idcard4leader(str){
	if(str!=""){
		if (str.length < 15){
			var end = str.substr(6);
			var start = end.substr(0,8);
			return "****"+start+"****";
		}else{
			var end = str.substr(6);
			var start = end.substr(0,8);
			return "****"+start+"****";
		}
	}else{
		return "";
	}
}
//身份证 前台 顾问 ，只能看 月日
function idcard4qt(str){
	if(str!=""){
		if (str.length < 15){
			//var start = str.substr(0,5);
			var end = str.substr(str.length-6);
			return "****"+end;
		}else{
			//var start = str.substr(0,8);
			var end = str.substr(10);
			return "****"+end.substr(0,4)+"****";
		}
	}else{
		return "";
	}
}
//身份证  ***123456 给客服人员
function idcard4server(str){
	if(str!=""){
		if (str.length < 15){
			//var start = str.substr(0,5);
			var end = str.substr(str.length-6);
			return "****"+end;
		}else{
			//var start = str.substr(0,8);
			var end = str.substr(12);
			return "****"+end;
		}
	}else{
		return "";
	}
}
//身份证 123***123
function replyidcard(str){
	if(str!=""){
		if (str.length < 15){
			var start = str.substr(0,5);
			var end = str.substr(8);
			return start+"****"+end;
		}else{
			var start = str.substr(0,8);
			var end = str.substr(12);
			return start+"****"+end;
		}
	}else{
		return "";
	}
}
//验证字母数字 且字符长度不不能大于18
function checkcode(obj){
	var v = obj.value.toUpperCase(); 
	obj.value=(v.length>18?v.substring(0,18):v).replace(/[^\w\.\/]/ig,'');
}
//验证字母数字 且字符长度不不能大于10
function checkcodebj(obj){
	var v = obj.value.toUpperCase(); 
	obj.value=(v.length>10?v.substring(0,10):v).replace(/[^\w\.\/]/ig,'_');
}
//验证字母数字 且字符长度不不能大于10
function checkcodejx(obj){
	var v = obj.value.toUpperCase(); 
	obj.value=(v.length>10?v.substring(0,10):v).replace(/[^\w\.\/]/ig,'');
}
function addZero(obj){
	var valu=obj.value;
	if(valu.indexOf('.')==(valu.length-1)){
		obj.value=valu+"0";  
	  }
	
}
/**********获取控件 再改变值************************/
function formatNum(num,n)
{//参数说明：num 要格式化的数字 n 保留小数位
    num = String(num.toFixed(n));
    var re = /(-?\d+)(\d{3})/;
    while(re.test(num)) num = num.replace(re,"$1,$2")
    return num;
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

/*****************新增遮盖层  a参表示遮盖div b参表示需遮盖的控件********************************************************************************/

 function cover(a,b){
		$(b).css("z-index",-2);
		//$("#cc").css("position","absolute");
		
		$(a).css("opacity",0.2);
		$(a).width($(b).width());
		$(a).height($(b).height()+10);
		$(a).css("position","absolute"); 
		
		$(a).css("left",$(b).offset().left);
		$(a).css("top",$(b).offset().top);
		$(a).css("z-index",99);
		
 }
/*****************新增遮盖层  a参表示遮盖div b参表示需遮盖的控件********************************************************************************/

 function cover2(a,b){
		$(b).css("z-index",-1);
		//$("#cc").css("position","absolute");
		
		$("#cover").css("opacity",0.3);
		$(a).width($(b).width());
		$(a).height($(b).height()+10);
		$(a).css("position","absolute"); 
		
		$(a).css("left",$(b).offset().left);
		$(a).css("top",$(b).offset().top);
		$(a).css("z-index",100);
		
 }


/*****************新增顾问 验证日期**********************************************************************************/
		 function checkymd(){
			var a = /^(\d{4})-(\d{2})-(\d{2})$/
			if (!a.test($("#birthday").val())) { 
				alert("日期格式不正确!(yyyy-MM-dd)") 
				$("#birthday").focus();
				return false 
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
 /***************当前日期加时间(如:2012-12-12)**********************************/
		function CurentTime()
		    { 
		        var now = new Date();
		        var year = now.getFullYear();       //年
		        var month = now.getMonth() + 1;     //月
		        var day = now.getDate();            //日
		        var clock = year + "-";
		         if(month < 10)
		            clock += "0";
		        clock += month + "-";
		        if(day < 10)
		           clock += "0";
		        clock += day + " ";
		        return clock; 
		    } 
	 	 /***************************************************************************************************/
	 	  function DateDiff(sDate, eDate){  //sDate1和sDate2是2002-12-18格式
		     var sArr = sDate.split("-");
		     var eArr = eDate.split("-");
		        
		     var b = new Date(sArr[0],sArr[1]-1,sArr[2]);
		     var e = new Date(eArr[0],eArr[1]-1,eArr[2]);
		     var dif = e.getTime() - b.getTime();
		     var day = Math.floor(dif/(1000 * 60 * 60 * 24));
		     
		     return day;
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
  
  function getNowMonthstart(){
	   			var now = new Date();
		        var year = now.getFullYear();       //年
		        var month = now.getMonth() + 1;     //月
		        var clock="";
		          if(month < 10)
		            clock += "-0";
		         return year+clock+month+"-01";
		     
  }
  function getNowMonthend(){
	   			var now = new Date();
		        var year = now.getFullYear();       //年
		        var month = now.getMonth() + 1;     //月
		        var clock="";
		          if(month < 10)
		            clock += "-0";
		         
		        	  
		     
		        return year+clock+month+"-31";
  }
  /*
*获取月的第一天
* date : Date类型.
*/
function getMonthBeforeDay(date){
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	if(month == 0){
		month = 12;
		year = year - 1;
	}
	date = year+"-"+((month)<=9?"0"+month:month)+"-01";
	return date;
}
/*
*获取月的最后一天
*date : Date类型.  
*/

function getBeforeMonthLastDay(date){
	var year = date.getFullYear();
	var month = date.getMonth()+1;
	if(month == 0){
		month = 12;
		year = year - 1;
	}
	month2 = parseInt(month,10)+1;
	var temp = new Date(year+"/"+month2+"/0");
	 	date = year+"-"+((month)<=9?"0"+month:month)+"-"+temp.getDate();
//	date = year+"-"+month+"-"+temp.getDate();
	return date;
}
function commonFunction(x)
{
	if (x == "undefined"){
		return true;
	}else if(x == ""){
		return true;
	}else if(x == null){
		return true;
	}else if(x.length <1){
		return true;
	}else{
		return false;
	}
}
function getLevel(obj)
{
	var __th = $(obj);

    if (!__th.val()) {
        //$('#pwd_tip').hide();
        //$('#pwd_err').show();
        Primary();
        return;
    }
    if (__th.val().length < 6) {
        $('#pwd_tip').hide();
        $('#pwd_err').show();
        Weak();
        return;
    }
    var _r = checkPassword(__th); //得到密码强度指数
    if (_r < 1) {
        $('#pwd_tip').hide();
        $('#pwd_err').show();
        Primary();
        return;
    }
	//原规则为1为弱，2-3为中，大于等于4为强
    //现规则为1为弱，2为中，3为强
    if (_r > 0 && _r < 2) {
        Weak();
    } else if (_r == 2) {
        Medium();
    } else if (_r > 2) {
        Tough();
    }

    $('#pwd_tip').hide();
    $('#pwd_err').hide();
}
//初始状态
function Primary() {
    $('#pwdLevel_1').attr('class', 'ywz_zhuce_huixian');
    $('#pwdLevel_2').attr('class', 'ywz_zhuce_huixian');
    $('#pwdLevel_3').attr('class', 'ywz_zhuce_huixian');
}


//弱
function Weak() {
    $('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
    $('#pwdLevel_2').attr('class', 'ywz_zhuce_huixian');
    $('#pwdLevel_3').attr('class', 'ywz_zhuce_huixian');
}

//中
function Medium() {
    $('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
    $('#pwdLevel_2').attr('class', 'ywz_zhuce_hongxian2');
    $('#pwdLevel_3').attr('class', 'ywz_zhuce_huixian');
}

//强
function Tough() {
    $('#pwdLevel_1').attr('class', 'ywz_zhuce_hongxian');
    $('#pwdLevel_2').attr('class', 'ywz_zhuce_hongxian2');
    $('#pwdLevel_3').attr('class', 'ywz_zhuce_hongxian3');
}




function checkPassword(pwdinput) {
    var maths, smalls, bigs, corps, cat, num;
    var str = $(pwdinput).val()
    var len = str.length;

    var cat = /.{16}/g
    if (len == 0) return 1;
    if (len > 16) { $(pwdinput).val(str.match(cat)[0]); }
    cat = /.*[\u4e00-\u9fa5]+.*$/
    if (cat.test(str)) {
        return -1;
    }
    cat = /\d/;
    var maths = cat.test(str);
    cat = /[a-z]/;
    var smalls = cat.test(str);
    cat = /[A-Z]/;
    var bigs = cat.test(str);
    var corps = corpses(pwdinput);
    var num = maths + smalls + bigs + corps;

    /*if (len < 6) { return 1; }
    if (len >= 6 && len <= 8) {
        if (num == 1) return 1;
        if (num == 2 || num == 3) return 2;
        if (num == 4) return 3;
    }
   
    */
    if(len < 8 || num < 3){ return 1; }
    if (len >= 8 && len <= 11) {
        if (num == 3) return 2;
        if (num == 4) return 3;
    }

    /*if (len > 8 && len <= 11) {
        if (num == 1) return 2;
        if (num == 2) return 3;
        if (num == 3) return 4;
        if (num == 4) return 5;
    }*/

    if (len > 11 && num >= 3) {
        return 3;
    }
}
//检测是否有特殊符号
function corpses(pwdinput) {
    var cat = /./g
    var str = $(pwdinput).val();
    var sz = str.match(cat)
    for (var i = 0; i < sz.length; i++) {
        cat = /\d/;
        maths_01 = cat.test(sz[i]);  //数字
        cat = /[a-z]/;
        smalls_01 = cat.test(sz[i]);  //小写字母
        cat = /[A-Z]/;
        bigs_01 = cat.test(sz[i]);  //大写字母
        if (!maths_01 && !smalls_01 && !bigs_01) { return true; }
    }
    return false;
}