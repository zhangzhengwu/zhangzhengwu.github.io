package com.coat.timerTask.dao.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.coat.timerTask.dao.TimerTaskSendMedicalEmailDao;

import dao.ConsListDao;
import dao.common.BaseDao;
import dao.impl.ConsListDaoImpl;
import util.Constant;
import util.DateUtils;
import util.SendMail;
import util.Util;

public class TimerTaskSendMedicalEmailDaoImpl extends BaseDao implements TimerTaskSendMedicalEmailDao{
	

	Logger logger =Logger.getLogger(TimerTaskSendMedicalEmailDaoImpl.class);

	public String timeTaskSendMedicalEmail(){
		String result = "";
		Long befor = null;
		Long after = null;
		
		if(DateUtils.getDay() == 25){
			try {
				Util.printLogger(logger,"开始执行定时任务-发送邮件");    
				befor = System.currentTimeMillis(); 
				result = MedialClaimSendMail();
				Util.printLogger(logger,"指定执行定时任务-发送邮件结束"); 
			} catch (Exception e) {
				e.printStackTrace();
				result = Util.joinException(e);
			} finally {
				after= System.currentTimeMillis(); // 标记结束时
				System.out.println("TimerTaskSendMedicalEmail 耗时："+(after-befor)+" ms");
				try {
					super.closeConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}else{
			result="success";
		}
		
		return result;
	}

	public String MedialClaimSendMail(){
		String result="";
		try{
			String subject="The details of this month's medical claims is attached for your checking/reference.The claimed amount will be refunded with the coming commission payment.";
			String to="";
			String attr="";
			String tempcode="";
			String name="";
			File file = null;
			String path = null;
			StringBuffer mailresult=null;
			String pathTemp = "";
			if(DateUtils.getMonth() >9 ){
				pathTemp = Constant.MedicalMail+DateUtils.getYear()+"-"+DateUtils.getMonth()+"-";
			}else{
				pathTemp = Constant.MedicalMail+DateUtils.getYear()+"-0"+DateUtils.getMonth()+"-";
			}
			
			for (int i = 26; i > 0; i--) {
				if(i>9){
					path=pathTemp+i;
				}else{
					path=pathTemp+"0"+i;
				}
				file=new File(path);
				if(file.exists()){
					break;
				}
			}
			File[] tempList = file.listFiles();
			ConsListDao consDao=new ConsListDaoImpl();
			if(!Util.objIsNULL(tempList)){
				mailresult=new StringBuffer();
				//遍历文件夹
				for(int i = 0; i < tempList.length; i++){
					if(!tempList[i].isHidden()&&tempList[i].isFile()){
						tempcode=tempList[i].getName().substring(0,tempList[i].getName().indexOf("."));
						attr=tempList[i].toString();
						to=consDao.findMailByCode(tempcode);
						name=consDao.findNameByCode(tempcode);
						//to="orlando.zhang@convoychina.com";
						//name="orlando";
						if(!Util.objIsNULL(to)){
						    String content="Dear "+name+",<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Please find the details of your requested medical claim (submitted on "+DateUtils.getYearMonth()+") attached for your reference.<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;The reimbursement will be refunded in your coming commission payment.<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Should you need any assistance, please contact us at ext 3667.<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Thank You.<br/>";
							content+="<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Regards<br/>";
							content+="&nbsp;&nbsp;&nbsp;&nbsp;Administration Department<br/>";
							//TODO modify result 
							result = SendMail.send(subject,to,null,null,attr, content, null, null, null);
						}else{
							mailresult.append(tempcode+"-邮箱未找到;");
						}
					}
				}
		}else{
			result=Util.getMsgJosnErrorReturn("文件列表为空");
			return result;
		}
		if(!Util.objIsNULL(mailresult)&&!Util.objIsNULL(mailresult.toString())){
			result=Util.getMsgJosnSuccessReturn(mailresult.toString());
		}else{
			result=Util.getMsgJosnObject_success();
		}
		
		}catch (Exception e) {
			result=Util.joinException(e);
		}finally{
			writeTxt2(Constant.MedicalMailLog+DateUtils.getDateToday()+".log", result.toString());
		}
		
		return result;
	}	
	
	
	
    public void writeTxt2(String downFile,String ss1) {
    	try {   
    		File f = new File(downFile);   
    		if(!f.exists()){    
    			/* FileUtil.deleteAll(downFile);
				 System.out.println(downFile + "已删除！");
			 }else {*/
    			f.createNewFile();//不存在则创建
    			System.out.println(downFile + "已创建！");
    			
    		}
    		FileOutputStream fis=new FileOutputStream(f); 
    		OutputStreamWriter isr=new OutputStreamWriter(fis,"GBK"); 
    		BufferedWriter br=new BufferedWriter(isr);
    		br.write(ss1);
    		br.close(); 
    	} catch (Exception e) 
    	{   
    		e.printStackTrace();  
    	} 
    	
    }	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	protected <T> T rowMapper(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
