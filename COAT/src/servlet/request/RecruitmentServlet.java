package servlet.request;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import util.DateUtils;
import util.Page;
import util.Util;

import net.sf.json.JSONArray;

import dao.RecruimentDao;
import dao.impl.RecuritmentDaoImpl;
import entity.C_Recruitment_detail;
import entity.C_Recruitment_list;
import entity.C_Recruitment_order;
import entity.Cons_listBean;
public class RecruitmentServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(RecruitmentServlet.class);
	public RecruitmentServlet() {
		super();
	}
	public void destroy() {
		super.destroy(); 
		
	}


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
        @SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    response.setCharacterEncoding("utf-8");
		    response.setContentType("text/html;charset=utf-8");
		    PrintWriter out=response.getWriter();
		    RecruimentDao rd=new RecuritmentDaoImpl();
		    String methName=request.getParameter("method").trim();
		    if(methName.equals("find")){
		    	  try{
		    		//页面初始化
				    	
				        //1.获取当前登录用户的code	
				             String code =request.getParameter("code1").trim();
				             
				            //2. 查询当前Code所对应的position和name
				             Cons_listBean cl=rd.getPosition(code);
				             if(cl!=null){
				            	//3.获取当前用户状态不为Completed订单	
				            	  List<C_Recruitment_order> orders=rd.findOrderByCode(code); 
				            	//4.如果订单数量大于0,则查询该订单编号所对应的产品
				            	  if(orders!=null&&orders.size()>0){
				            		  //根据订单refno查询产品Mediacode
				            	   List<C_Recruitment_detail> details=rd.findDetialByOrder(orders);	
				            		 if(details!=null){
				            		  // 查询出所有产品	
				            			  List<C_Recruitment_list> list=rd.findRecruitment();
				            			//把该用户所选的产品备注设置为Y, 
				            			  for(int i=0;i<details.size();i++){
								        	   for(int j=0;j<list.size();j++){
								        		   if(details.get(i).getMediacode().equals(list.get(j).getMediacode())){
								        			   list.get(j).setRemark("Y");
								        		   }
								        	   }
				            			  }
				            			  List li=new ArrayList();         	
				            			  li.add(0,cl);
				            			  li.add(1,list);     
				            			  JSONArray jsons=JSONArray.fromObject(li);  
										  out.print(jsons);   
				            		 } 
				            		 else{
				            			 out.print("error");
				            		 }
				            	  }
				            	  //说明该code存在并且无订单
				            	  else if(orders.size()==0){
				            		  //查询出所有产品并显示
				            		  List<C_Recruitment_list> list=rd.findRecruitment();
				            		  if(list==null){
										  out.print("error");  
									     }
				            		 
				            		  else{           			 
				            			  List li=new ArrayList();
				            			  li.add(0,cl);
				            			  li.add(1,list);
				            			  JSONArray jsons=JSONArray.fromObject(li);  
										  out.print(jsons);   
				            		  }
				            	  }  
				            	  else{
				            		  out.print("error");
				            	  }
				             } 
				             else{
				            	 out.print("");
				             }
				    }catch (Exception e) {
				    	logger.error("RecruitmentServlet ERROR"+e.toString());
				    	out.print("error");
						e.printStackTrace();
					}	
		    }
		      else if(methName.equals("save")){
		    	  try{
		    		  String name =(String)request.getSession().getAttribute("adminUsername");
		    		  String param=request.getParameter("param").trim();//接收用户所选产品的code
			    	  C_Recruitment_order cr=new C_Recruitment_order();
			    	  String refno="";
			    	  synchronized (this) {
							 refno=rd.getNo();
							if(Util.objIsNULL(refno))
								throw new Exception("流水号产生异常");
							else
								cr.setRefno(refno);
						}
			    	  cr.setStaffcode(request.getParameter("code").trim());//用户编号
			          cr.setStaffname(request.getParameter("name").trim());//用户名称
			          cr.setUsertype(request.getParameter("userType").trim());//用户类型(暂时给定值)
			          cr.setPosition(request.getParameter("position").trim());//position
			          cr.setContactperson(request.getParameter("persion").trim());  //联系人
			          cr.setContactemail(request.getParameter("email").trim());//联系人邮箱
			          cr.setChargecode(request.getParameter("staffCode").trim());//付款人编号
			          cr.setChargename(request.getParameter("cname").trim());//付款人名称
			          cr.setDate("");//刊登日期（先给空）
			          cr.setCreatedate(DateUtils.getNowDateTime());//创建时间(当前时间)
			          cr.setCreater(name);//创建人
			          cr.setStatus("Submitted");//订单状态
			          cr.setSfyx("Y");//是否有效(默认Y)
			          cr.setRemark("");//备注(默认给空)
			          cr.setFilterdate(DateUtils.getNowDateTime());//过滤时间，默认是插入数据的当前时间_orlando新增
			          C_Recruitment_list list=new C_Recruitment_list();
			          //查询产品详情
			          list=rd.findListByCode(param);
			          //生成订单
		    		  boolean rig=rd.addOrder(cr,list,refno);
		    		
                      if(rig){
                    	  out.print("success");
                      }	else{
                    	  out.print("error");
                      }	    			     			
		    	  }catch (Exception e) {
					      e.printStackTrace();
				     }
		    	 
		          
		    }
		      else if(methName.equals("findname")){
		    	  //获取consultant Name的值
		    	  String code=request.getParameter("staffcode").trim();
		    	  String name=rd.findConsultantName(code);
		    	  if(name.equals("")){
		    		  out.print("error");
		    	  }
		    	  out.print(name);
		      }
		      else if (methName.equals("medialist")){
		    		try {
						Page page=new Page();
  	  //获取页面参数
  	           String code=request.getParameter("code").trim();
  	           String name=request.getParameter("name").trim();
  	           String type=request.getParameter("type").trim();
  	           String date=request.getParameter("date").trim();
  	           String date1=request.getParameter("date1").trim();
  	     //2.获取总记录
               page.setAllRows(rd.recordCount(code, name, type, date,date1));
               page.setPageSize(15);
        //获取当前页码  
               page.setCurPage(Integer.parseInt(request.getParameter("pageIndex")));
        //3.查询
        List<C_Recruitment_list> recruitmentLists=rd.findByConditions(code, name, type, date,date1, page);
        //转为Json
              List list=new ArrayList();
              list.add(0,recruitmentLists);
              list.add(1,page.getAllPages());//总页数
              list.add(2,page.getCurPage());//当前页
              list.add(3,page.getAllRows());//总行数
              list.add(4,page.getPageSize());//一页显示的记录
              JSONArray jsons=JSONArray.fromObject(list);
              out.print(jsons.toString());
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
				
		      } 
		    
		         
	}
	public void init() throws ServletException {}

}
