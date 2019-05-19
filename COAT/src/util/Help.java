package util;

import java.lang.reflect.Field;
import java.util.Arrays;

import cn.admin.entity.system.SUser;



public class Help {

	
	/**
	 * 便利获取实体类字段
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public static String reflectEntityName(Object model) throws Exception{  
		 
        // 获取实体类的所有属性，返回Field数组  
        Field[] field = model.getClass().getDeclaredFields();  
        //以字符串形式返回式
        StringBuffer entity=new StringBuffer("");
        // 遍历所有属性  
        for (int j = 0; j < field.length; j++) {  
                
       	   try{// 获取属性的名字  
                String name = field[j].getName();  
             
                // 将属性的首字符大写，方便构造get，set方法  
               // name = name.substring(0, 1).toUpperCase() + name.substring(1);  
                String type = field[j].getGenericType().toString();  
                if(type.equals("class java.lang.Integer"))
                	entity.append("rs.getInt(\""+name.trim()+"\"),\r\n");
                else if(type.equals("class java.lang.Boolean"))
                	entity.append("rs.getBoolean(\""+name.trim()+"\"),\r\n");
                else
                	entity.append("rs.getString(\""+name.trim()+"\"),\r\n");
                // 获取属性的类型  
            //    String type = field[j].getGenericType().toString();  
                // 如果type是类类型，则前面包含"class "，后面跟类名  
                // System.out.println("属性为：" + name);
            //   	 if(type.equals("class java.lang.Short")||type.equals("class java.lang.String")||type.equals("class java.lang.Integer")||type.equals("class java.lang.Double")||type.equals("class java.lang.Boolean"))
              // 		 entity.append(model.getClass().getMethod("get"+ name).invoke(model)+"]");
              // 	 else
               //		 entity.append("null]");
                }catch(Exception e){
                e.printStackTrace();
                }
                
        }  

       return entity.toString().substring(0,entity.toString().length()-1);
}  
	
	
	
	/*** 
	  * 遍历实体类的属性和数据类型以及属性值 
	  *  
	  * @author King.Xu 
	  * @date 2013-09-26 上午10:25:02 
	  * @company  
	  * @version v1.3 
	  * @see 相关类 
	  * @since 相关/版本 
	  */  
	public static String reflectEntity(Object model) throws Exception{  
			 
	                 // 获取实体类的所有属性，返回Field数组  
	                 Field[] field = model.getClass().getDeclaredFields();  
	                 //以字符串形式返回式
	                 StringBuffer entity=new StringBuffer("[{");
	                 // 遍历所有属性  
	                 for (int j = 0; j < field.length; j++) {  
	                         
	                	   try{// 获取属性的名字  
	                         String name = field[j].getName();  
	                      
	                         // 将属性的首字符大写，方便构造get，set方法  
	                         name = name.substring(0, 1).toUpperCase() + name.substring(1);  
	                         entity.append(""+name+":");
	                         // 获取属性的类型  
	                         String type = field[j].getGenericType().toString();  
	                         // 如果type是类类型，则前面包含"class "，后面跟类名  
	                         // System.out.println("属性为：" + name);
	                        	 if(type.equals("class java.lang.Short")||type.equals("class java.lang.String")||type.equals("class java.lang.Integer")||type.equals("class java.lang.Double")||type.equals("class java.lang.Boolean"))
	                        		 entity.append(model.getClass().getMethod("get"+ name).invoke(model)+(j==field.length-1?"":","));
	                        	 else
	                        		 entity.append("null"+(j==field.length-1?"":","));
	                         }catch(Exception e){
	                        	 entity.append("null"+(j==field.length-1?"":","));
	                        	 e.printStackTrace();
	                         }
	                         
	                 }
	                 entity.append("}]");
	                 
		
	                return entity.toString();
	         }  
	
	
	
	
	
	
	
	public static String getfileds(Object model){
		  // 获取实体类的所有属性，返回Field数组  
        Field[] field = model.getClass().getDeclaredFields();  
        //以字符串形式返回式
        StringBuffer entity=new StringBuffer("");
        // 遍历所有属性  
        for (int j = 0; j < field.length; j++) {  
                
       	   try{// 获取属性的名字  
                String name = field[j].getName();
                	entity.append(name+",");
					
				
                }catch(Exception e){
               	 e.printStackTrace();
                }
                
        }
        entity.append("");
        

       return entity.toString();
	}
	
	
	
	public static void mainw(String[] args) {
		//converEntity(getfileds(new SUser()));
		/*SGroup sGroup=new SGroup(3);
		sGroup.setGroupname("ss");
		sGroup.setGroupcode("st");
		SGroup sGroup2=new SGroup(1);
		SUser sUser=new SUser(); */
		
		//System.out.println(compareEntity(sGroup,sGroup2));
		
		
		//converEntity("groupId,groupname, companyId, headid, creator, createDate, modifier, modifyDate, sfyx");
		try {
			
			//System.out.println(reflectEntityParam(new SystemUserpermissions(),"systemUserpermissions"));
			
			//System.out.println();
		
			
			
			//System.out.println(reflectEntityName(new SystemUserpermissions()));
			
			//SystemMsg s=new SystemMsg();
			//System.out.println(reflectEntity(s));
			//getter Entity rs
			//System.out.println(reflectEntity(new SystemMsg()));	
			//setter Entity param
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	/*** 
	  * 遍历实体类的生成注入参数类
	  *  
	  * @author King.Xu 
	  * @date 2013-09-26 上午10:25:02 
	  * @company  
	  * @version v1.3 
	  * @see 相关类 
	  * @since 相关/版本 
	  */  
	public static String reflectEntityParam(Object model,String modelName,String filterName) throws Exception{  
			 
	                 // 获取实体类的所有属性，返回Field数组  
	                 Field[] field = model.getClass().getDeclaredFields();  
	                 
	                 //以字符串形式返回式
	                 StringBuffer entity=new StringBuffer();
	                 int num=1;
	                 // 遍历所有属性  
	                 for (int j = 0; j < field.length; j++) {  
	                         
	                	   try{// 获取属性的名字  
	                		   
	                         String name = field[j].getName();  
	                         
	                         if(!name.equals("serialVersionUID") && !name.equals(filterName)){
	                         // 将属性的首字符大写，方便构造get，set方法  
	                         name = name.substring(0, 1).toUpperCase() + name.substring(1);  
	                        // entity.append("ps.setString("+(j)+","+modelName+".get"+name+"());\r\n");
	                         
	                         
	                         String type = field[j].getGenericType().toString();  
	                         if(type.equals("class java.lang.Integer"))
	                        	 entity.append("ps.setInt("+(num)+","+modelName+".get"+name+"());\r\n");
	                         else if(type.equals("class java.lang.Boolean"))
	                        	 entity.append("ps.setBoolean("+(num)+","+modelName+".get"+name+"());\r\n");
	                         else
	                        	 entity.append("ps.setString("+(num)+","+modelName+".get"+name+"());\r\n");
	                         
	                         num++;
	                         
	                         }
	                         }catch(Exception e){
	                        	// entity.append("null]");
	                        	 e.printStackTrace();
	                         }
	                         
	                 }  
		
	                return entity.toString();
	         }  
	/**
	 * 根据实体类生成set方法
	 * @param model
	 * @param modelName 实体类对象名
	 * @param filterName 过滤的字段
	 * @return
	 * @throws Exception
	 */
	public static String reflectEntitySetParam(Object model,String modelName,String filterName) throws Exception{  
		 
        // 获取实体类的所有属性，返回Field数组  
        Field[] field = model.getClass().getDeclaredFields();  
        
        //以字符串形式返回式
        StringBuffer entity=new StringBuffer();
        int num=1;
        // 遍历所有属性  
        for (int j = 0; j < field.length; j++) {  
                
       	   try{// 获取属性的名字  
       		   
                String name = field[j].getName();  
                
                if(!name.equals("serialVersionUID") && !name.equals(filterName)){
                // 将属性的首字符大写，方便构造get，set方法  
                name = name.substring(0, 1).toUpperCase() + name.substring(1);  
               // entity.append("ps.setString("+(j)+","+modelName+".get"+name+"());\r\n");
                
                
                String type = field[j].getGenericType().toString();  
                if(type.equals("class java.lang.Integer"))
               	 entity.append(modelName+".get"+name+"(),\r\n");
                else if(type.equals("class java.lang.Boolean"))
               	 entity.append((num)+","+modelName+".get"+name+"(),\r\n");
                else
               	 entity.append(modelName+".get"+name+"(),\r\n");
                
                num++;
                
                }
                }catch(Exception e){
               	// entity.append("null]");
               	 e.printStackTrace();
                }
                
        }  

       return entity.toString();
}  
	
	/**
	 * 比较两个实体类数据差异
	 * @param oldmodel 旧实体类数据
	 * @param newmodel 新实体类数据
	 * @return
	 */
	public static String compareEntity(Object oldmodel,Object newmodel) { 
		StringBuffer result=new StringBuffer();
		try{
			   // 获取实体类的所有属性，返回Field数组  
            Field[] oldfield = oldmodel.getClass().getDeclaredFields();  
            //获取旧实体类的所有属性，返回Field数组
            Field[] newfield = newmodel.getClass().getDeclaredFields();  
            //判断两个实体类是否一样
           // if(oldfield.length==newfield.length){
            if(oldmodel.getClass()==newmodel.getClass()){
            	String oldname="";
            	//String newname="";
            	String oldvalue="";
            	String newvalue="";
            	
            	for(int i=0;i<oldfield.length;i++){
            	     oldname = oldfield[i].getName();  
            	     String type = oldfield[i].getGenericType().toString();
                    if(!oldname.equals("serialVersionUID")){
                    // 将属性的首字符大写，方便构造get，set方法  
                    oldname = oldname.substring(0, 1).toUpperCase() + oldname.substring(1);  
                    if(type.equals("class java.lang.Short")||type.equals("class java.lang.String")||type.equals("class java.lang.Integer")||type.equals("class java.lang.Double")||type.equals("class java.lang.Boolean")){
                    	//获取旧实体类字段数据
                    	oldvalue=oldmodel.getClass().getMethod("get"+ oldname).invoke(oldmodel)+"";
                    	//获取新实体类字段数据
                    	newvalue=newmodel.getClass().getMethod("get"+ oldname).invoke(newmodel)+"";
                    	if(!oldvalue.equals(newvalue)){//判断是否相等
                    		//System.out.println(oldvalue+"--->"+newvalue);
                    		result.append(newfield[i].getName()+",");//记录差异项
                    	}
                    }
                    
                    }
            	}
            }else{
            	result=new StringBuffer("error");
            }
            
            
            
		}catch (Exception e) {
			e.printStackTrace();
			result=new StringBuffer("exception");
		}finally{
			
		}
		
		return result.toString().substring(0,result.toString().length()-1);
	}
	
	
	public static String compareEntity(Object oldmodel,Object newmodel,String[] fields) throws Exception { 
		StringBuffer result=new StringBuffer();
		try{
			   // 获取实体类的所有属性，返回Field数组  
            Field[] oldfield = oldmodel.getClass().getDeclaredFields();  
            //获取旧实体类的所有属性，返回Field数组
            Field[] newfield = newmodel.getClass().getDeclaredFields();  
            //判断两个实体类是否一样
           // if(oldfield.length==newfield.length){
            if(oldmodel.getClass()==newmodel.getClass()){
            	String oldname="";
            	//String newname="";
            	String oldvalue="";
            	String newvalue="";
            	
            	for(int i=0;i<oldfield.length;i++){
            	     oldname = oldfield[i].getName();  
            	     String type = oldfield[i].getGenericType().toString();
                    if(!oldname.equals("serialVersionUID")){
                    // 将属性的首字符大写，方便构造get，set方法  
                    oldname = oldname.substring(0, 1).toUpperCase() + oldname.substring(1);  
                    if(type.equals("class java.lang.Short")||type.equals("class java.lang.String")||type.equals("class java.lang.Integer")||type.equals("class java.lang.Double")||type.equals("class java.lang.Boolean")){
                    	//获取旧实体类字段数据
                    	oldvalue=oldmodel.getClass().getMethod("get"+ oldname).invoke(oldmodel)+"";
                    	//获取新实体类字段数据
                    	newvalue=newmodel.getClass().getMethod("get"+ oldname).invoke(newmodel)+"";
                    	if(!oldvalue.equals(newvalue)){//判断是否相等
                    		//System.out.println(oldvalue+"--->"+newvalue);
                    		if(Util.objIsNULL(fields)||Arrays.binarySearch(fields, newfield[i].getName())<0){
                    			if(!Util.objIsNULL(result.toString())){
                    				result.append(",");
                    			}
                    			result.append(newfield[i].getName());//记录差异项
                    		}
                    	}
                    }
                    
                    }
            	}
            }else{
            	throw new RuntimeException("Unable to compare two different classes");
            }
            
            
            
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally{
			
		}
		
		return result.toString();
	}
	
	
	
	
	public static void converEntity(String param){
		String[] paramStrings=param.split(",");
		System.out.println("---");
		for(int i=0;i<paramStrings.length;i++){
			System.out.println("rs.getString(\""+paramStrings[i].trim()+"\"),");
		}
		
	}

	 public static void main(String[] args) {
		 try{
			SUser sUser = new SUser();
			sUser.setLoginname("xx");
			sUser.setLoginpass("g");
			SUser sUser2 = new SUser();
			sUser.setLoginname("xxg");
			System.out.println(compareEntity(sUser, sUser2, new String[]{"loginname"}));
		 }catch (Exception e) {
			e.printStackTrace();
		}
	}
}
