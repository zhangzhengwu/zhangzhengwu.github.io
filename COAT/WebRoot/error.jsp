<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" lang="UTF-8"> 
  <head>
<title>应用程序异常 </title> 
    <style type="text/css"> 
        body { background-color: #fff; color: #666;  font-family: arial, sans-serif; }
        div.dialog {
            width: 80%;
            padding: 1em 4em;
            margin: 4em auto 0 auto;
            border: 1px solid #ccc;
            border-right-color: #999;
            border-bottom-color: #999;
            
        }
        h1 { font-size: 100%; color: #f00; line-height: 1.5em; }
        ul li{list-style: decimal;}
    </style> 
</head> 
 
<body> 
  <div class="dialog" style="border-radius: 8px 8px 8px 8px;"> 
    <h1 style='text-align: center;'>System Error</h1> 
    <p>System is time out or has other error, please click &quot;Office Admin&quot; to restart it or contact relevant department to fix it.</p><%-- 
         <p><a href="javascript:showErr();">details</a> </p>
    <div style="display:none;text-align: left;color:highlight;" id="err">User token has been failed...</div>
    
    --%><span style="float:left;">The following suggestions are for reference only.</span>
    <br/>
    <div style='padding-left:30px;'>
    <ul>
    <li>New comer can be normally accessed to COAT after the second day.</li>
    <li>Users are allowed to operate in COAT within 30 minutes.After that, user will be required to re-visit the COAT.</li>

    </ul>
    </div>
    
  </div>
  
</body> 
</html>
