<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'queryNameCard_detail.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
<body>
<div class="cont-info">
	<div class="form-table">
		<table>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont">
					
				</td>
				<td class="tagName"></td>
				<td class="tagCont">
					
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont">
					
				</td>
				<td class="tagName"></td>
				<td class="tagCont">
					
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont">
					
				</td>
				<td class="tagName"></td>
				<td class="tagCont">
					
				</td>
			</tr>
			<tr>
				<td class="tagName"></td>
				<td class="tagCont">
					
				</td>
				<td class="tagName"></td>
				<td class="tagCont">
					
				</td>
			</tr>
		</table>
		<table width="100%" height="395" border="0" cellpadding="0" cellspacing="0"  class="table-ss">
        <tr>
          <td height="16" colspan="3" align="center">&nbsp;</td>
          <td width="294" height="16" align="left">&nbsp;</td>
        </tr>
        <tr>
          <td height="28" colspan="3" align="center"><font size="+2"; color="#0000FF" face="仿宋" >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="messages">詳細信息</span></font> </td>
          <td height="28" align="left"><a href="javascript:void(0);" id="motify" style="size='16px'; font-family:'仿宋'; color:#FF0000">修改</a></td>
        </tr>
        <tr>
          <td><div align="right"><strong>Staff Code:</strong></div></td>
          <td width="281"><input name="StaffCode" type="text" class="ss" id="StaffNo" size="15" disabled="disabled" /><input id="read" type="checkbox" title="1"/>修改code<input id="UrgentDate" name="UrgentDates" type="hidden"/></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><div align="right"><strong>AE Consultant:</strong></div></td>
          <td><input name="checkbox2"  type="checkbox" id="aeConsultant" title="1" value="N"/></td>
          <td><div align="right"><strong>UrgentCase:</strong></div></td>
          <td><input name="checkbox" type="checkbox" id="urgent" title="1" value="N"></td>
        </tr>
         <tr>
          <td><div align="right"><strong>Elite Team:</strong></div></td>
          <td><input name="checkbox2"  type="checkbox" id="ET" title="1" value="N"/></td>
          <td> </td>
          <td> </td>
        </tr>
        <tr>
        <td><div align="right"><strong>Layout:</strong></div></td>
        <td> <select id="layout_type" title="1" >
       <option value="S">Standard Layout</option>
        <option value="P">Perminm Layout</option>
        </select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
        <td><div align="right"><strong>Location:</strong></div></td>
        <td> <select name="select2" id="upselect" title="1" >
        <option value="">Select Location...</option>
          <option value="O">OIE</option>
          <option value="C">CP3</option>
          <option value="W">CWC</option>
                        </select></td>
        </tr>
        <tr>
          <td width="240"><div align="right"><strong>EnglishName:</strong></div></td>
          <td><input type="text" name="EnglishNames" id="EnglishName" class="ss"  title="1"></td>
          <td width="254"><div align="right"><strong>ChineseName:</strong></div></td>
          <td><input type="text" name="ChineseNames" id="ChineseName" class="ss"  title="1"></td>
        </tr>
        <tr>
          <td><div align="right"><strong>Department in English:</strong></div></td>
          <td><!-- <input name="EnglishTitle_Departments" type="text" class="ss"  title="1" id="EnglishTitle_Department" size="38"> -->
          <select name="EnglishTitle_Departments"  class="ss"  title="1" id="EnglishTitle_Department" >
          </select>          </td>
          <td><div align="right"><strong>Department in Chinese:</strong></div></td>
          <td><input name="ChineseTitle_Departments" type="text" class="ss"  title="1" id="ChineseTitle_Department" size="35"></td>
        </tr>
        <tr>
          <td><div align="right"><strong>ExternalTitle_Department_English:</strong></div></td>
          <td><input name="EnglishExternalTitle_Departments" type="text" class="ss"  title="1" id="EnglishExternalTitle_Department" size="35"></td>
          <td><div align="right"><strong>ExternalTitle_Department_Chinese:</strong></div></td>
          <td><input name="ChineseExternalTitle_Departments" type="text" class="ss"  title="1" id="ChineseExternalTitle_Department" size="35"></td>
        </tr>
            <tr>
          <td><div align="right"><strong>AcademicTitle_Department_English:</strong></div></td>
          <td><input name="EnglishExternalTitle_Departments" type="text" class="ss"  title="1" id="AcademicTitle_e_Department" size="35"></td>
          <td><div align="right"><strong>AcademicTitle_Department_Chinese:</strong></div></td>
          <td><input name="ChineseExternalTitle_Departments" type="text" class="ss"  title="1" id="AcademicTitle_c_Department" size="35"></td>
        </tr>
            <tr>
              <td height="16"><div align="right"><strong>Education_Title_English:</strong></div></td>
              <td colspan="3"><input name="EnglishProfessions" type="text" id="EnglishProfession" title="1" size="66" class="ss"></td>
            </tr>
        <tr>
          <td>&nbsp;</td>
          <td colspan="2">
          <select  name="EnglishEducationTitles"  class="ss"   id="EnglishEducationTitle"  title="1">
          </select>
		</td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><div align="right"><strong>Education_Title_Chinese:</strong></div></td>
          <td colspan="2"><input name="ChineseEducationTitles" type="text" class="ss"  title="1" id="ChineseEducationTitle" size="66"></td>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td><div align="right"><strong>T.R.Reg.No:</strong></div></td>
          <td><input name="trgs" type="text" class="ss"  title="1" id="trg" size="30"></td>
          <td><div align="right"><strong>CENo:</strong></div></td>
          <td><input name="CEs" type="text" class="ss"  title="1" id="CE" size="35"></td>
        </tr>
        <tr>
          <td><div align="right"><strong>MPFNo:</strong></div></td>
          <td><input name="MPFs" type="text" class="ss"  title="1" id="MPF" size="30"></td>
          <td><div align="right"><strong>E-Mail:</strong></div></td>
          <td><input name="Emails" type="text"  title="1" class="ss" id="Email" size="35"></td>
        </tr>
        <tr>
          <td><div align="right"><strong>DirectLine:</strong></div></td>
          <td><input name="DirectLines" type="text"  title="1" class="ss" id="DirectLine" size="30"></td>
          <td><div align="right"><strong>FAX:</strong></div></td>
          <td><input name="FAXs" type="text" class="ss"  title="1" id="FAX" size="35"></td>
        </tr>
        <tr>
          <td><div align="right"><strong>MobilePhone:</strong></div></td>
          <td><input name="MobilePhones" type="text" title="1" class="ss" id="MobilePhone" size="30"></td>
          <td><div align="right"><strong>Quantity:</strong></div></td>
           <td><input name="Quantitys" type="text" class="ss" id="Quantity" size="5" onKeyUp="return checkNum(this);" onKeyPress="return checkNum(this);" onFocus="$('#numId').show();" title="1">
         <select id="numId" name="nums" style="display: none;" onChange=" $('#Quantity').val($(this).val()) ;">
              <option value="100">100</option>
              <option value="200">200</option>
              <option value="300">300</option>
              <option value="400">400</option>
            </select> </td>
        </tr>
        <tr>
          <td colspan="4"><span style="color:#005aa7;">
           <input id="C24" type="checkbox" name="CIB" value="N"   title="1"/>
            CIB
            <input id="CC" type="checkbox" name="CFS" value="N"  title="1"/>
            CFS &nbsp;&nbsp;
            <input id="C" type="checkbox" name="CAM" value="N"   title="1"/>
            CAM &nbsp;&nbsp;
            <input id="CCC" type="checkbox" name="CIS" value="N"   title="1"/>
            CIS   &nbsp;&nbsp;
            <input id="C2" type="checkbox" name="CCL" value="N"   title="1"/>
            CCL &nbsp;&nbsp;
            <input id="C3" type="checkbox" name="CFSH" value="N"   title="1"/>
            CFSH &nbsp;&nbsp;
            <input id="C4" type="checkbox" name="CMS" value="N"   title="1"/>
            CMS    &nbsp;&nbsp;
            <input id="C22" type="checkbox" name="CFG" value="N"   title="1"/>
            CFG &nbsp;&nbsp;
            <input id="C23" type="checkbox" name="Blank" value="N"   title="1"/>
            Blank
           </span></td>
        
        </tr>
        <tr>
          <td><input id="Payer" title="1" type="hidden"/></td>
          <td><input id="reQuantity" title="1" type="hidden"/>
          <input id="reStaffNo" type="hidden"/>            <input id="rePayer" type="hidden"/></td>
          <td><div align="center">
            <input type="button" name="Submit" id="save" value="保 存">
          </div></td>
          <td><input type="button" id="quit" name="exit" value="返 回"></td>
        </tr>
      </table>
	</div>
</div>
</body>
</html>
