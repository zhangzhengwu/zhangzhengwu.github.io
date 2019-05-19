package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import entity.Location;
import util.Page;
import dao.LocationDao;
import dao.impl.LocationDaoImpl;

public class LocationServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	Logger log=Logger.getLogger(LocationServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	@SuppressWarnings("unused")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String method=request.getParameter("method");
		try{		
			if(method.equals("query"))
				query(request, response);
			else if(method.equals("del"))
				delete(request, response);
			else if(method.equals("save"))
				add(request, response);
			else {
				throw new Exception("Unauthorized access!");
			}
				
		}catch (NullPointerException e) {
			log.error("EntryLocation==>"+method+"操作异常：空值=="+e);
			response.getWriter().print("Submit data anomalies, please refresh retry!");
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
			log.error("EntryLocation==>"+method+"操作异常："+e);
			response.getWriter().print("Exception:"+e.getMessage());
		} finally{
			method=null; 
		} 		
	}

	
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	void query(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String name=request.getParameter("name");
		String pageNow=request.getParameter("pageNow");
		Page page=new Page();
		try{		
			List<Location> requestList = new ArrayList<Location>();
			LocationDao qdao = new LocationDaoImpl();	
			List list=new ArrayList();				

			page.setAllRows(qdao.getRows(name));
			page.setPageSize(30);
			page.setCurPage(Integer.parseInt(pageNow));
			requestList = qdao.queryRequestList(name,page);
						
			list.add(0,requestList);			
			list.add(1,page.getAllPages());
			list.add(2,page.getCurPage());
			list.add(3,page.getAllRows());
			
			
			 JSONArray jsons=JSONArray.fromObject(list);	
			 out.print(jsons.toString());
			// System.out.println(jsons.toString());
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				out.flush();
				out.close();
			}
	}
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void delete(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try{
				String locationId=request.getParameter("locationId");
				LocationDao del = new LocationDaoImpl();		
				del.DelLocationInfo(locationId);
					
				JSONArray jsons = JSONArray.fromObject(del);
				out.println(jsons.toString());
			}catch (Exception e) {
				e.printStackTrace();
				out.print(e.getMessage());
			}finally{
				out.flush();
				out.close();
			}
	}
	/**
	 * 添加
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	void add(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		try{
			String name = request.getParameter("name");					//获取参数从页面中得到name
			//String realName=request.getParameter("realName");			//获取参数从页面中得到realName
			String locationInfo=request.getParameter("locationInfo");	//获取参数从页面中得到locationInfo
			String remark=request.getParameter("remark");				//获取参数从页面中得到remark
			Location location=new Location();
			location.setName(name);						//设置名字
			//location.setRealName(realName);			//设置realName
			location.setLocationInfo(locationInfo);		//设置locationInfo
			location.setRemark(remark);					//设置remark
			
			if(name.equals(location.getName())){			
				LocationDao add = new LocationDaoImpl();	
				boolean b=add.selectName(name);
				if(b){
					add.AddLocation(location);					
					out.print("1");
				}else{
					//out.print("Name不允许重复！");
					out.print("-1");
				}
				JSONArray jsons = JSONArray.fromObject(add);
				out.print(jsons.toString());
			
			}	
		}catch (Exception e) {
			e.printStackTrace();
			out.print(e.getMessage());
		}finally{
			out.flush();
			out.close();
		}
		
		
		
	}
}
