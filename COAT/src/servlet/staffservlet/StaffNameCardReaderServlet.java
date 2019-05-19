package servlet.staffservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import util.Pager;
import util.Util;
import dao.impl.staffnamecard.StaffNameCardDaoImpl;
import dao.staffnamecard.StaffNameCardDao;
import entity.RequestStaffBean;
import entity.RequestStaffConvoyDetial;

public class StaffNameCardReaderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(StaffNameCardReaderServlet.class);
	public static Connection con = null;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	String user=null;
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String method=request.getParameter("method");

		try{
			user=(String) request.getSession().getAttribute("adminUsername");//request.getSession().getAttribute("convoy_username").toString();
			if(method.equalsIgnoreCase("select")){
				select(request, response);
			}else if(method.equalsIgnoreCase("deptapprovalselect")){
				deptapprovalselect(request, response);
			}else if(method.equalsIgnoreCase("hrapprovalselect")){
				hrapprovalselect(request, response);
			}else if(method.equalsIgnoreCase("staffselect")){
				staffselect(request, response);
			}else if(method.equalsIgnoreCase("staffrequestconvoyselect")){
				staffrequestconvoyselect(request, response);
			}else if(method.equalsIgnoreCase("deptdownload")){
				//deptdownload(request, response);
			}else if(method.equalsIgnoreCase("selectState")){
				selectState(request, response);
			}else if(method.equalsIgnoreCase("detail")){
				staffNameCardDetail(request, response);
			}else if(method.equalsIgnoreCase("getOldRecord")){
				getOldRecord(request,response);
			}else if(method.equalsIgnoreCase("findCompay")){
				findCompay(request,response);
			}else if(method.equalsIgnoreCase("selectCompany")){
				selectCompany(request,response);
			}else{
				throw new Exception("Unauthorized access!");
			}

		}catch (NullPointerException e) {
			log.error("AccessCard==>"+method+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("AccessCard==>"+method+"操作异常："+e);
			response.getWriter().print("Exception:"+e.getMessage());
		} finally{
			method=null; 
		} 
	}
	
	void findCompay(HttpServletRequest request,HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
		String staffNo=request.getParameter("staffNo");
		try{
			String companyName=staffDao.findCompany(staffNo);
			//JSONObject json=JSONObject.fromObject(companyName);
			out.print(companyName);
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("StaffNameCard 分页查询异常:"+e);
		} finally{
			out.flush();
			out.close();
		}

	
	}
	
	
	/**
	 * 	 
	 * @param request
	 * @param response
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
		 
	void staffrequestconvoyselect(HttpServletRequest request,HttpServletResponse response) throws IOException, ClassNotFoundException, SQLException{
		response.setContentType("text/html;charset=utf-8");
		String StaffNo  = request.getParameter("StaffNo");
		PrintWriter out = response.getWriter();
		try {
			StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
//			StaffNameCardDao staffDao1=new StaffNameCardDaoImpl();
			List<Map<String, Object>> list = staffDao.queryStaffRequestConvoy(StaffNo);
		
			
			Map<String,String> maps=null;
			
			for (Map<String, Object> map : list) {
				maps=new HashMap<String, String>();
				
/*				String positioncode = map.get("POSITION_CODE")+"";
				String departmentcode =  map.get("DEPARTMENT_CODE")+"";
				staffDao=new StaffNameCardDaoImpl();
				String positioncode2 = staffDao.getPositionCode(positioncode);
				
				staffDao1=new StaffNameCardDaoImpl();
				String departmentcode2 = staffDao1.getDepartmentCode(departmentcode);
				
				String position=(Util.objIsNULL(map.get("POSITION_ENG_DESC"))?"":map.get("POSITION_ENG_DESC"))+"";
				String positionChi=(Util.objIsNULL(map.get("POSITION_CHI_DESC"))?"":map.get("POSITION_CHI_DESC"))+"";
				
				if(null!=positioncode2){
					if(position.indexOf(",")>-1){
						maps.put("Position_Eng", position.substring(0,position.indexOf(",")));
						if(!Util.objIsNULL(positionChi)&&positionChi.indexOf(",")>-1){
							maps.put("Position_Chi", positionChi.substring(0,positionChi.indexOf(",")));
						}else{
							if(!Util.objIsNULL(positionChi)){
								maps.put("Position_Chi", positionChi);
							}else{
								maps.put("Position_Chi", "");
							}
						}
					}else{
						maps.put("Position_Eng", position);
						if(!Util.objIsNULL(positionChi)&&positionChi.indexOf(",")>-1){
							maps.put("Position_Chi", positionChi.substring(0,positionChi.indexOf(",")));
						}else{
							if(!Util.objIsNULL(positionChi)){
								maps.put("Position_Chi", positionChi);
							}else{
								maps.put("Position_Chi", "");
							}
						}
					}
					maps.put("Department_Eng", "");
					maps.put("Department_CHI","");
				}else{
					
					if(null!=departmentcode2){
						maps.put("Position_Eng", position);
						maps.put("Department_Eng", map.get("DEPARTMENT_ENG_DESC")+"");
						if(!Util.objIsNULL(positionChi)&&positionChi.indexOf(",")>-1){
							maps.put("Position_Chi", positionChi.substring(0,positionChi.indexOf(",")));
							maps.put("Department_CHI", positionChi.substring(positionChi.indexOf(",")+1));
						}else{
							if(!Util.objIsNULL(positionChi)){
								maps.put("Position_Chi", positionChi);
							}else{
								maps.put("Position_Chi", "");
							}
							maps.put("Department_CHI", map.get("DEPARTMENT_DESC")+"");
						}
						
					}else{
						if(position.indexOf(",")>-1){
							maps.put("Position_Eng", position.substring(0,position.indexOf(",")));
							maps.put("Department_Eng", position.substring(position.indexOf(",")+1));
							if(!Util.objIsNULL(positionChi)&&positionChi.indexOf(",")>-1){
								maps.put("Position_Chi", positionChi.substring(0,positionChi.indexOf(",")));
								maps.put("Department_CHI", positionChi.substring(positionChi.indexOf(",")+1));
							}else{
								if(!Util.objIsNULL(positionChi)){
									maps.put("Position_Chi", positionChi);
								}else{
									maps.put("Position_Chi", "");
								}
								maps.put("Department_CHI", map.get("DEPARTMENT_DESC")+"");
							}
						}else{
							maps.put("Position_Eng", position);
							maps.put("Department_Eng", map.get("DEPARTMENT_ENG_DESC")+"");
							if(!Util.objIsNULL(positionChi)&&positionChi.indexOf(",")>-1){
								maps.put("Position_Chi", positionChi.substring(0,positionChi.indexOf(",")));
								maps.put("Department_CHI", positionChi.substring(positionChi.indexOf(",")+1));
							}else{
								if(!Util.objIsNULL(positionChi)){
									maps.put("Position_Chi", positionChi);
								}else{
									maps.put("Position_Chi", "");
								}
								maps.put("Department_CHI", map.get("DEPARTMENT_DESC")+"");
							}
						}
					}
				}	*/
				//2018-04-17 用户需求，修改Location获取方式，从原来的Oracle数据库转到从Vsmart中获取
				staffDao=new StaffNameCardDaoImpl();
				String location = staffDao.getLocation(StaffNo);
				
				
				//2017-10-16 用户新需求将原先逻辑全部清空。--orlando 
				if(!Util.objIsNULL(map.get("POSITION_ENG_DESC"))){
					maps.put("Position_Eng", map.get("POSITION_ENG_DESC")+"");
				}else{
					maps.put("Position_Eng", "");
				}
				if(!Util.objIsNULL(map.get("POSITION_ENG_DESC"))){
					maps.put("Position_Chi", map.get("POSITION_CHI_DESC")+"");
				}else{
					maps.put("Position_Chi", "");
				}
				if(!Util.objIsNULL(map.get("DEPARTMENT_ENG_DESC"))){
					maps.put("Department_Eng", map.get("DEPARTMENT_ENG_DESC")+"");
				}else{
					maps.put("Department_Eng", "");
				}
				if(!Util.objIsNULL(map.get("DEPARTMENT_DESC"))){
					maps.put("Department_CHI", map.get("DEPARTMENT_DESC")+"");
				}else{
					maps.put("Department_CHI", "");
				}
				
				
				if(!Util.objIsNULL(map.get("LOCATION_CODE"))){
					maps.put("LOCATION_CODE", map.get("LOCATION_CODE")+"");
				}else{
					maps.put("LOCATION_CODE", "");
				}
//				maps.put("LOCATION_CODE", map.get("LOCATION_CODE")+"");
/*				if(!Util.objIsNULL(map.get("LOCATION_DESC"))){
					maps.put("LOCATION_DESC", map.get("LOCATION_DESC")+"");
				}else{
					maps.put("LOCATION_DESC", "");
				}	*/	
				if(!Util.objIsNULL(location)){
					maps.put("LOCATION_DESC", location);
				}else{
					maps.put("LOCATION_DESC", "");
				}
				
				if(!Util.objIsNULL(map.get("LOCATION_CHI_DESC"))){
					maps.put("LOCATION_CHI_DESC", map.get("LOCATION_CHI_DESC")+"");
				}else{
					maps.put("LOCATION_CHI_DESC", "");
				}		
				
				
//				maps.put("LOCATION_DESC", map.get("LOCATION_DESC")+"");
				if(!Util.objIsNULL(map.get("FULL_NAME"))){
					maps.put("FULL_NAME", map.get("FULL_NAME")+"");
				}else{
					maps.put("FULL_NAME", "");
				}
//				maps.put("FULL_NAME", map.get("FULL_NAME")+"");
				if(!Util.objIsNULL(map.get("CHINESE_LAST_NAME"))){
					maps.put("CHINESE_NAME", map.get("CHINESE_LAST_NAME")+"");
				}else{
					maps.put("CHINESE_NAME", "");
				}
//				maps.put("CHINESE_NAME", map.get("CHINESE_NAME")+"");
				if(!Util.objIsNULL(map.get("EXTERNAL_POSITION_ENG_DESC"))){
					maps.put("EXTERNAL_POSITION_ENG_DESC", map.get("EXTERNAL_POSITION_ENG_DESC")+"");
				}else{
					maps.put("EXTERNAL_POSITION_ENG_DESC", "");
				}
//				maps.put("EXTERNAL_POSITION_ENG_DESC", map.get("EXTERNAL_POSITION_ENG_DESC")+"");
				if(!Util.objIsNULL(map.get("EXTERNAL_POSITION_CHI_DESC"))){
					maps.put("EXTERNAL_POSITION_CHI_DESC", map.get("EXTERNAL_POSITION_CHI_DESC")+"");
				}else{
					maps.put("EXTERNAL_POSITION_CHI_DESC", "");
				}
//				maps.put("EXTERNAL_POSITION_CHI_DESC", map.get("EXTERNAL_POSITION_CHI_DESC")+"");
				if(!Util.objIsNULL(map.get("TR_REG_NO"))){
					maps.put("TR_REG_NO", map.get("TR_REG_NO")+"");
				}else{
					maps.put("TR_REG_NO", "");
				}
//				maps.put("TR_REG_NO", map.get("TR_REG_NO")+"");
				if(!Util.objIsNULL(map.get("CE_NO"))){
					maps.put("CE_NO", map.get("CE_NO")+"");
				}else{
					maps.put("CE_NO", "");
				}
//				maps.put("CE_NO", map.get("CE_NO")+"");
				if(!Util.objIsNULL(map.get("MPFA_NO"))){
					maps.put("MPFA_NO", map.get("MPFA_NO")+"");
				}else{
					maps.put("MPFA_NO", "");
				}
//				maps.put("MPFA_NO", map.get("MPFA_NO")+"");
				if(!Util.objIsNULL(map.get("MOBILE"))){
					maps.put("MOBILE", map.get("MOBILE")+"");
				}else{
					maps.put("MOBILE", "");
				}
//				maps.put("MOBILE", map.get("MOBILE")+"");
				if(!Util.objIsNULL(map.get("FAX_NO"))){
					maps.put("FAX_NO", map.get("FAX_NO")+"");
				}else{
					maps.put("FAX_NO", "");
				}
//				maps.put("FAX_NO", map.get("FAX_NO")+"");
				if(!Util.objIsNULL(map.get("DIRECT_LINE"))){
					maps.put("DIRECT_LINE", map.get("DIRECT_LINE")+"");
				}else{
					maps.put("DIRECT_LINE", "");
				}
//				maps.put("DIRECT_LINE", map.get("DIRECT_LINE")+"");
				if(!Util.objIsNULL(map.get("EMAIL_ADDRESS"))){
					maps.put("EMAIL_ADDRESS", map.get("EMAIL_ADDRESS")+"");
				}else{
					maps.put("EMAIL_ADDRESS", "");
				}
//				maps.put("EMAIL_ADDRESS", map.get("EMAIL_ADDRESS")+"");
				if(!Util.objIsNULL(map.get("COMPANY_CODE"))){
					maps.put("COMPANY_CODE", map.get("COMPANY_CODE")+"");
					String englishName=staffDao.getCompany_EnglishName(map.get("COMPANY_CODE")+"");
					maps.put("ENGLISH_NAME",englishName);
				}else{
					maps.put("COMPANY_CODE", "");
				}
//				maps.put("COMPANY_CODE", map.get("COMPANY_CODE")+"");
				if(!Util.objIsNULL(map.get("DEPARTMENT_CODE"))){
					maps.put("DEPARTMENT_CODE", map.get("DEPARTMENT_CODE")+"");
				}else{
					maps.put("DEPARTMENT_CODE", "");
				}
				
				
				
				
			}	
			JSONArray jsons=JSONArray.fromObject(maps);
			out.print(jsons.toString());
		} catch (Exception e) {
			e.printStackTrace();
//			response.getWriter().print("Exception:"+e.getMessage());
			out.print("exception");
		} finally {
			out.flush();
			out.close();
		}
	}


	/**
	 * hr approval 页面查询
	 * @author kingxu
	 * @date 2015-8-10
	 * @param request
	 * @param response
	 * @throws IOException
	 * @return void
	 */
	void hrapprovalselect(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String location  = request.getParameter("location");
		String urgentCase  = request.getParameter("urgentCase");
		String name  = request.getParameter("name");
		String code  = request.getParameter("code");
		String payer  = request.getParameter("payer");
		String ET=request.getParameter("ET");
		String layout=request.getParameter("layout");
		String isverify=request.getParameter("status");//审核状态
		PrintWriter out = response.getWriter();
		StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
		try{
			Pager page=Util.pageInfo(request);
			page=staffDao.findHRApprovalPager(null,
					page,
					ET, 
					Util.objIsNULL(startDate)?"1900-01-01":startDate,
					Util.objIsNULL(endDate)?"2099-12-31":endDate,
					Util.objIsNULL(code)?"%%":"%"+code+"%",
					Util.objIsNULL(payer)?"%%":"%"+payer+"%",
					Util.objIsNULL(name)?"%%":"%"+name+"%",
					Util.objIsNULL(location)?"%%":"%"+location+"%",
					Util.objIsNULL(urgentCase)?"%%":"%"+urgentCase+"%",
					Util.objIsNULL(layout)?"%%":"%"+layout+"%",
					Util.objIsNULL(isverify)?"%%":"%"+isverify+"%");
			List<Object> list=new ArrayList<Object>();
			list.add(0,page.getList());//数据
			list.add(1,page.getTotalpage());//总页数
			list.add(2,page.getPagenow());//当前页
			list.add(3,page.getTotal());//总行数
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
			
			
		 
			 
			 
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			out.flush();
		}
	}
	
	
	
	/**
	 * staff 查询
	 * @author kingxu
	 * @date 2015-8-5
	 * @param request
	 * @param response
	 * @throws IOException
	 * @return void
	 */
	void staffselect(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String location  = request.getParameter("location");
		String urgentCase  = request.getParameter("urgentCase");
		String name  = request.getParameter("name");
		String code  = request.getParameter("code");
		String payer  = request.getParameter("payer");
		//String payer=user;
		String ET=request.getParameter("ET");
		String layout=request.getParameter("layout");
		String isverify=request.getParameter("status");//审核状态
		PrintWriter out = response.getWriter();
		StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
		try{
			Pager page=Util.pageInfo(request);
			page=staffDao.findPager(null, page, ET, Util.objIsNULL(startDate)?"1900-01-01":startDate,Util.objIsNULL(endDate)?"2099-12-31":endDate,Util.objIsNULL(code)?"%%":code,Util.objIsNULL(payer)?"%%":payer,Util.objIsNULL(name)?"%%":name,Util.objIsNULL(location)?"%%":location,Util.objIsNULL(urgentCase)?"%%":urgentCase,Util.objIsNULL(layout)?"%%":layout,Util.objIsNULL(isverify)?"%%":isverify);
			List<Object> list=new ArrayList<Object>();
			list.add(0,page.getList());//数据
			list.add(1,page.getTotalpage());//总页数
			list.add(2,page.getPagenow());//当前页
			list.add(3,page.getTotal());//总行数
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
			
			
			/*
			
			page.setAllRows(qdao.getConvoyRows(name, code, startDate, endDate, location, urgentCase,ET,layout, payer));//设置总行数
			page.setPageSize(10);//设置页面显示行数
			page.setCurPage(Integer.parseInt(request.getParameter("pageNow")));//获取页面当前页码
		 	 stafflist = qdao.queryRequstConvoyList(name, code, startDate, endDate, location, urgentCase,ET,layout, payer,page);
		 	 
		 	
		 	List list=new ArrayList();
			list.add(0,stafflist);//数据
			list.add(1,page.getAllPages());//总页数
			list.add(2,page.getCurPage());//当前页
			list.add(3,page.getAllRows());//总行数
			 JSONArray jsons=JSONArray.fromObject(list);
			 out.print(jsons.toString());
			 */
			 
			 
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			out.flush();
		}
	}
	
	 
	void selectCompany(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=utf-8");
		StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
		PrintWriter out = response.getWriter();
		String company_code=request.getParameter("company_code");
		try{
			String a[]=company_code.split("\\+");
			List<String> list=new ArrayList<String>();
			for (int i = 0; i < a.length; i++) {
				list.add(staffDao.getCompany_EnglishName(a[i]));
			}
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			out.flush();
		}
	}
	

	/**
	 * dept head 查询方法
	 * @author kingxu
	 * @date 2015-8-4
	 * @param request
	 * @param response
	 * @throws IOException
	 * @return void
	 */
	void deptapprovalselect(HttpServletRequest request, HttpServletResponse response) throws IOException{

		response.setContentType("text/html;charset=utf-8");
		//Page page=new Page();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String location  = request.getParameter("location");
		String urgentCase  = request.getParameter("urgentCase");
		String name  = request.getParameter("name");
		String code  = request.getParameter("code");
		String payer  = request.getParameter("payer");
		String ET=request.getParameter("ET");
		String layout=request.getParameter("layout");
		String status=request.getParameter("status");

		PrintWriter out = response.getWriter();
		StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
		try{
			Pager page=Util.pageInfo(request);
			page=staffDao.findDeptApprovalPager(null, 
					page, 
					ET, 
					Util.objIsNULL(startDate)?"1900-01-01":startDate,
					Util.objIsNULL(endDate)?"2099-12-31":endDate,
					Util.objIsNULL(payer)?"%%":payer,
					Util.objIsNULL(payer)?"%%":payer,
					Util.objIsNULL(code)?"%%":"%"+code+"%",
					Util.objIsNULL(name)?"%%":"%"+name+"%",
					Util.objIsNULL(location)?"%%":"%"+location+"%",
					Util.objIsNULL(urgentCase)?"%%":"%"+urgentCase+"%",
					Util.objIsNULL(layout)?"%%":"%"+layout+"%",
					Util.objIsNULL(status)?"%%":"%"+status+"%");
			List<Object> list=new ArrayList<Object>();
			list.add(0,page.getList());//数据
			list.add(1,page.getTotalpage());//总页数
			list.add(2,page.getPagenow());//当前页
			list.add(3,page.getTotal());//总行数
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
		} finally{
			out.flush();
		}

	}


	/**
	 * 分页查询
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	void select(HttpServletRequest request, HttpServletResponse response) throws IOException{

		response.setContentType("text/html;charset=utf-8");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String location  = request.getParameter("location");
		String urgentCase  = request.getParameter("urgentCase");
		String name  = request.getParameter("name");
		String code  = request.getParameter("code");
		String payer  = request.getParameter("payer");
		String ET=request.getParameter("ET");
		String layout=request.getParameter("layout");
		String isverify=request.getParameter("isverify");//审核状态

		PrintWriter out = response.getWriter();
		//List<RequestStaffConvoyBean> stafflist = new ArrayList<RequestStaffConvoyBean>();
		//QueryStaffRequstDao qdao = new QueryStaffRequstDaoImpl();
		StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
		try{
			Pager page=Util.pageInfo(request);
			page=staffDao.findPager(null, 
					page, 
					ET, 
					Util.objIsNULL(startDate)?"1900-01-01":startDate,
					Util.objIsNULL(endDate)?"2099-12-31":endDate,
					Util.objIsNULL(code)?"%%":"%"+code+"%",
					Util.objIsNULL(code)?"%%":"%"+payer+"%",
					Util.objIsNULL(name)?"%%":"%"+name+"%",
					Util.objIsNULL(location)?"%%":"%"+location+"%",
					Util.objIsNULL(urgentCase)?"%%":"%"+urgentCase+"%",
					Util.objIsNULL(layout)?"%%":"%"+layout+"%",
					Util.objIsNULL(isverify)?"%%":"%"+isverify+"%");
			List<Object> list=new ArrayList<Object>();
			list.add(0,page.getList());//数据
			list.add(1,page.getTotalpage());//总页数
			list.add(2,page.getPagenow());//当前页
			list.add(3,page.getTotal());//总行数
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
			// System.out.println(jsons.toString());
		}catch (Exception e) {
			e.printStackTrace();
			log.error("StaffNameCard 分页查询异常:"+e);
		} finally{
			out.flush();
			out.close();
		}

	}
	
	 void selectState(HttpServletRequest request, HttpServletResponse response) throws IOException{
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			String staffcode = request.getParameter("staffcode");
			String staffrefno = request.getParameter("staffrefno");
			StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
			try{
				Pager page=Util.pageInfo(request);
				page=staffDao.queryStateDetail(null, page, 
						Util.objIsNULL(staffrefno)?"%%":"%"+staffrefno+"%",
						Util.objIsNULL(staffcode)?"%%":"%"+staffcode+"%");
				List<Object> list=new ArrayList<Object>();
				list.add(0,page.getList());//数据
				list.add(1,page.getTotalpage());//总页数
				list.add(2,page.getPagenow());//当前页
				list.add(3,page.getTotal());//总行数
				JSONArray jsons=JSONArray.fromObject(list);
				out.print(jsons.toString());
			}catch (Exception e) {
				e.printStackTrace();
				log.error("StaffNameCard 分页查询异常:"+e);
			} finally{
				out.flush();
				out.close();
			}

	}
	
	void staffNameCardDetail(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out=null;
		StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
		List<RequestStaffConvoyDetial> list=new ArrayList<RequestStaffConvoyDetial>();
		try{
			out=response.getWriter();
			String staffrefno=request.getParameter("staffrefno");
			list=staffDao.findNameCardDetail(staffrefno);
			JSONArray jsons=JSONArray.fromObject(list);
			out.print(jsons.toString());
			jsons=null;
		}catch(Exception e){
			e.printStackTrace();
			log.error("StaffNameCard-Detail Exception :"+e.getMessage());
			out.print("Detail Exception :"+e.getMessage());
		}finally{
			staffDao=null;
			list=null;
			out.flush();
			out.close();
		}
	}
	 
	/**
	 * 查找上一次的申请记录
	 * @return
	 */
	void getOldRecord(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String result="";
		String code=request.getParameter("staffcode");
		List<RequestStaffBean> list=new ArrayList<RequestStaffBean>();
		try{
			StaffNameCardDao staffDao=new StaffNameCardDaoImpl();
			list=staffDao.getOldRecord(code);
			JSONArray json=JSONArray.fromObject(list);
			result=json.toString();
		}catch (Exception e) {
			result=Util.joinException(e);
			e.printStackTrace();
		}finally{
			out.print(result);
			out.flush();
			out.close();
		} 
	}

	 
}
