package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Util;

import com.coat.operationrecord.dao.OperationRecordDao;
import com.coat.operationrecord.dao.impl.OperationRecordDaoImpl;


public class TestgetName extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
		
/*
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		// System.out.println(request.getRequestURL().substring(0,request.getRequestURL().indexOf(request.getRequestURI())));
		try {OperationRecordDao o=new OperationRecordDaoImpl();
				o.insert("insert into t_big(F0) values('"+0+"--"+0+"');");
			//test2(100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
public void test2(final int times) throws SQLException{
		
		try{
			final int numOfThreads = Runtime.getRuntime().availableProcessors() * 1;  
			ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);  
			// int dbType = DataSourceUtil.DRUID_MYSQL_SOURCE;  
			// dbType = DataSourceUtil.DBCP_SOURCE;  
			for (int i = 0; i < times; i++) {  
				executor.submit(new Callable<Long>() {  
					public Long call() throws Exception {  
						long begin = System.currentTimeMillis();  
						try {  
							OperationRecordDao o=new OperationRecordDaoImpl();
							o.insert("insert into t_big(F0) values('"+numOfThreads+"--"+times+"');");
							// insertResult = true;  
						} catch (Exception e) {  
							e.printStackTrace();  
						}  
						long end = System.currentTimeMillis();  
						return end - begin;  
					}  
				}); 
			}  
			executor.shutdown();  
			 

			System.out.println("---------------db type mysql" +  
					"------------------");  
			System.out.println("number of threads :" + numOfThreads + " times:"  
					+ times);  
			

			//                test.tearDown();  
			// dropResult = true;  
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally{
		}


	}  

	int num=0;

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		String token="";
		try{
			System.out.println();
			HttpSession session=request.getSession();
			token=""+session.getAttribute("token");
			System.out.println(num);
			if(Util.objIsNULL(token)){
				token=session.getId();
				session.setAttribute("token",token);
			}else{
				System.out.println(token);
			}
			num++;
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.print(token);
			out.flush();
			out.close();
		}
		
	}

}
