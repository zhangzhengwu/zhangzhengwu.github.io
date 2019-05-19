package com.coat.timerTask.servlet;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import util.DateUtils;
import util.Util;
import com.coat.timerTask.dao.TimerTaskLogDao;
import com.coat.timerTask.dao.impl.TimerTaskLogDaoImpl;



public class TimersTask  extends TimerTask{
	Logger logger = Logger.getLogger(TimersTask.class);
	String currentName="";//当前任务名称
	String result="";//定时任务执行结果
	String nowTime="";//执行任务当前时间
	static List<Map<String,String>> executeList=null;//缓存需执行的任务列表
	static Map<String,Integer> executeNum=new HashMap<String, Integer>();
	public TimersTask() {
		currentName="";//当前任务名称
		result="";//定时任务执行结果
		nowTime="";//执行任务当前时间
		
	}


	public void run() {
		try{
			nowTime=DateUtils.getNowDateTime();
			TimerTaskLogDao task=null;
			String nowHHmmTime=DateUtils.getNowHHmm();//获取当前时间的 时分-->HHmm
			//List<Map<String,Object>>  list=task.find_task_schedule(nowHHmmTime);//查询到当前时间需要执行的所有任务
			//System.out.println(nowHHmmTime+"---("+DateUtils.getNowDateTime()+")---"+Util.getProValue("TimerTask.ConsultantInfo.time"));
			if(nowHHmmTime.indexOf(":00")>-1||nowHHmmTime.indexOf(":15")>-1||nowHHmmTime.indexOf(":30")>-1||nowHHmmTime.indexOf(":45")>-1){
				System.out.println("-------------------更新需要定时执行的任务---------------------");
				task=new TimerTaskLogDaoImpl();
				executeList=oneToMore(task.find_task_schedule(""));//查询到当前时间需要执行的所有任务并缓存起来
			}else{
				//System.out.println(executeList);
				if(Util.objIsNULL(executeList)||executeList.size()==0){
					task=new TimerTaskLogDaoImpl();
					executeList=oneToMore(task.find_task_schedule(""));//查询到当前时间需要执行的所有任务并缓存起来
				}else{
					//System.out.println("拿的缓存");
				}
			}
			
			if(!Util.objIsNULL(executeList)&&executeList.size()>0){
				for (int i = 0; i<executeList.size(); i++) {
					String executeTime=executeList.get(i).get("executeTime");
					if(nowHHmmTime.indexOf(executeTime)>-1){
						
						try{
							currentName=executeList.get(i).get("taskName")+"";
							//System.out.println("-->"+list.get(i).get("executeScript"));
							JSONObject json=JSONObject.fromObject(executeList.get(i).get("executeScript"));
							for(Object key:json.keySet()){
								if(judeRepeat()>0){
									result=executeMethod(key+"", json.get(key)+"");
								}else{
									result="repeated";
								}
							}
						}finally{
							if(!Util.objIsNULL(currentName)){
								Util.printLogger(logger, "TaskName["+currentName+"] Completed!");
								saveTaskLog();
								currentName="";
								result="";
							}
						}
						
					}
				}
				
				
			}
			
			//executeList=null;
			
		/*	if(!Util.objIsNULL(list)&&list.size()>0){
				for (int i = 0; i<list.size(); i++) {
					currentName=list.get(i).get("taskName")+"";
					//System.out.println("-->"+list.get(i).get("executeScript"));
					JSONObject json=JSONObject.fromObject(list.get(i).get("executeScript"));
					for(Object key:json.keySet()){
						result=executeMethod(key+"", json.get(key)+"");
					}
					if(!Util.objIsNULL(currentName)){
						Util.printLogger(logger, "TaskName["+currentName+"] Completed!");
						saveTaskLog();
						currentName="";
						result="";
					}
				}
			}*/
			result="success";
		}catch (Exception e) {
			e.printStackTrace();
			result="exception";
			Util.printLogger(logger, "TaskName["+currentName+"] Exception("+e.getMessage()+")");
		}finally{
			
		}


	}
	
	/**
	 * 将数据查询的数据根据executeTime一行转成多行
	 * @author kingxu
	 * @date 2016-6-16
	 * @param list
	 * @return
	 * @return List<Map<String,String>>
	 */
	public static List<Map<String,String>> oneToMore(List<Map<String,Object>>  list){
		List<Map<String,String>> dataList=new ArrayList<Map<String,String>>();
		try{
			Map<String,String> dataMap=null;
			for(int i=0;i<list.size();i++){
				Map<String,Object> map=list.get(i);
				if(map.get("executeTime").toString().indexOf(",")>-1){
					String[] executeArray=map.get("executeTime").toString().split(",");
					for(String executeTime:executeArray){
						dataMap=new HashMap<String, String>();
						dataMap.put("executeTime", executeTime);
						dataMap.put("taskName", map.get("taskName")+"");
						dataMap.put("executeScript", map.get("executeScript")+"");
						dataMap.put("explain", map.get("explain")+"");
						dataList.add(dataMap);
					}
					executeNum.put(map.get("taskName")+"", executeArray.length);
				}else{
					executeNum.put(map.get("taskName")+"", 1);
					dataMap=new HashMap<String, String>();
					dataMap.put("executeTime", map.get("executeTime")+"");
					dataMap.put("taskName", map.get("taskName")+"");
					dataMap.put("executeScript", map.get("executeScript")+"");
					dataMap.put("explain", map.get("explain")+"");
					dataList.add(dataMap);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		
		return dataList;
	}
	
  
	
	/**
	 * 将字符串转为Class 并且执行一个方法
	 * @param className
	 * @param methodName
	 */
	public  static String executeMethod(String className,String methodName){
		String  result="";
		try{
			  Class clz = Class.forName(className);
			  Object obj = clz.newInstance();
			  //获取方法  
			  Method m = obj.getClass().getDeclaredMethod(methodName);
			  //调用方法  
			  result= (String) m.invoke(obj);
		}catch (Exception e) {
			e.printStackTrace();
			result=Util.objIsNULL(e.toString())?e.getMessage():e.toString();
		}
		return result;
	}


	public int judeRepeat(){
		int num=1;
		try{
			TimerTaskLogDao task=new TimerTaskLogDaoImpl();
//			if(task.findTaskLogNum(currentName, nowTime, "success")>=Util.getProValue(currentName).split(",").length){
			if(task.findTaskLogNum(currentName, nowTime, "success")>=executeNum.get(currentName)){
				Util.printLogger(logger, "TaskName["+currentName+"] Repeated Execute!");
				num=0;
			}else{
				Util.printLogger(logger, "TaskName["+currentName+"] Start Exceute!");
				num=1;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}

	public int saveTaskLog(){
		int n=0;
		try{
			TimerTaskLogDao task=new TimerTaskLogDaoImpl();
			n=task.saveTaskLog(currentName, nowTime, result);
		}catch (Exception e) {
			//e.printStackTrace();
			Util.printLogger(logger, "TaskName["+currentName+"] Save Record Exception("+e.getMessage()+")!");
		}
		return n;
	}

}
