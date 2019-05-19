package dao.exp;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.print.DocFlavor.STRING;
import javax.servlet.jsp.jstl.sql.Result;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

import util.DBManager;
import util.Util;


public class ExpRequestnew {
	Logger logger = Logger.getLogger(ExpRequestnew.class);
	@SuppressWarnings("unused")
	private HSSFWorkbook wb = null;
	public ExpRequestnew(){
		wb = new HSSFWorkbook();
	}
	/**
	 * 导出Excel表格
	 * @param res
	 * @param os
	 * @throws IOException
	 * @throws SQLException 
	 */
	public void createFixationSheet(ResultSet res,OutputStream os,HSSFWorkbook wb,HSSFSheet sheet)throws  IOException, SQLException{
		int ii =0;
		try{
			int i =4;
			ii =res.getMetaData().getColumnCount();
			while(res.next()){
				i++;
				HSSFRow row2 =sheet.createRow((short)i);
				for(int j=0;j<ii;j++)
				{
					String ss="";
					if(res.getString(j+1)==null)
						ss="";
					else
						ss=res.getString(j+1);
					cteateCell(wb,row2,(short)j,ss);
				}
			}
			sheet.autoSizeColumn(( short ) 0 ); // 调整第一列宽度 
			sheet.autoSizeColumn(( short ) 1 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 2 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 3 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 4 ); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 5 ); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 6 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 7 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 8); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 9 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 10 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 11 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 12); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 13); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 14 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 15 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 16 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 17); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 18); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 19 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 20); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 21); // 调整第五列宽度 
			/***************************为Excel设置密码***********************************/
			//sheet.protectSheet("admin");
		}catch(SQLException e){
			e.printStackTrace();
		}
		wb.write(os);
		res.close();
		os.flush();
		os.close();

	}
	/**
	 * 去除职位on Probation
	 * @author kingxu
	 * @date 2015-7-22
	 * @param position
	 * @return
	 * @return String
	 */
	String deleteProbation(String position){
		String title="";
		try{
			if(!Util.objIsNULL(position)){
				int p=position.toUpperCase().indexOf("(ON PROBATION)");
				if(p>-1){
					title=position.substring(0,p);
				}else{
					 p=position.toUpperCase().indexOf("(ON PROBATION )");
					if(p>-1){
						title=position.substring(0,p);
					}else{
						 p=position.toUpperCase().indexOf("(ON PROBATION");
							if(p>-1){
								title=position.substring(0,p);
							}else{
								 p=position.toUpperCase().indexOf("ON PROBATION");
									if(p>-1){
										title=position.substring(0,p);
									}
							}
					}
				}
			}
			if(Util.objIsNULL(title)){
				title=position;
			}
		}catch (Exception e) {
			title=position;
			e.printStackTrace();
		}
		return title;
	}
	
	 /**
	  * 生成最新名片办理信息
	  * @author kingxu
	  * @date 2015-7-22
	  * @param res
	  * @param os
	  * @param wb
	  * @param sheet
	  * @throws IOException
	  * @throws SQLException
	  * @return void
	  */
	public void createLastPressionalTitleSheet(Result res,OutputStream os,HSSFWorkbook wb,HSSFSheet sheet)throws  IOException, SQLException{
		try{
			for(int k=0,i=5;k<res.getRowCount();k++,i++){
				HSSFRow row2 =sheet.createRow((short)i);
				Map row=res.getRows()[k];
				cteateCell(wb,row2,(short)0,Util.objIsNULL(row.get("location"))?"":(row.get("location")+""));
				cteateCell(wb,row2,(short)1,Util.objIsNULL(row.get("companyName"))?"":(row.get("companyName")+""));
				cteateCell(wb,row2,(short)2,Util.objIsNULL(row.get("layout"))?"":(row.get("layout")+""));
				cteateCell(wb,row2,(short)3,Util.objIsNULL(row.get("staff_code"))?"":(row.get("staff_code")+""));
				cteateCell(wb,row2,(short)4,Util.objIsNULL(row.get("name"))?"":(row.get("name")+""));
				cteateCell(wb,row2,(short)5,Util.objIsNULL(row.get("name_chinese"))?"":(row.get("name_chinese")+""));
				cteateCell(wb,row2,(short)6,deleteProbation(row.get("title_english")+""));//去除职位中的probation
				cteateCell(wb,row2,(short)7,Util.objIsNULL(row.get("title_chinese"))?"":(row.get("title_chinese")+""));
				cteateCell(wb,row2,(short)8,Util.objIsNULL(row.get("txternal_english"))?"":(row.get("txternal_english")+""));
				cteateCell(wb,row2,(short)9,Util.objIsNULL(row.get("external_chinese"))?"":(row.get("external_chinese")+""));
				cteateCell(wb,row2,(short)10,Util.objIsNULL(row.get("profess_title_e"))?"":(row.get("profess_title_e")+""));
				cteateCell(wb,row2,(short)11,Util.objIsNULL(row.get("profess_title_c"))?"":(row.get("profess_title_c")+""));
				cteateCell(wb,row2,(short)12,Util.objIsNULL(row.get("tr_reg_no"))?"":(row.get("tr_reg_no")+""));
				cteateCell(wb,row2,(short)13,Util.objIsNULL(row.get("ce_no"))?"":(row.get("ce_no")+""));
				cteateCell(wb,row2,(short)14,Util.objIsNULL(row.get("mpf_no"))?"":(row.get("mpf_no")+""));
				cteateCell(wb,row2,(short)15,Util.objIsNULL(row.get("email"))?"":(row.get("email")+""));
				cteateCell(wb,row2,(short)16,Util.objIsNULL(row.get("directline"))?"":(row.get("directline")+""));
				cteateCell(wb,row2,(short)17,Util.objIsNULL(row.get("fax"))?"":(row.get("fax")+""));
				cteateCell(wb,row2,(short)18,Util.objIsNULL(row.get("mobile"))?"":(row.get("mobile")+""));
				cteateCell(wb,row2,(short)19,Util.objIsNULL(row.get("submitDate"))?"":(row.get("submitDate")+""));
			}
			sheet.autoSizeColumn(( short ) 0 ); // 调整第一列宽度 
			sheet.autoSizeColumn(( short ) 1 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 2 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 3 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 4 ); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 5 ); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 6 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 7 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 8); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 9 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 10 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 11 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 12); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 13); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 14 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 15 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 16); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 17); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 18 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 19); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 20); // 调整第五列宽度 
			/***************************为Excel设置密码***********************************/
			//sheet.protectSheet("admin");
			logger.info("**********正在生成 NameCard Excel**********");
			wb.write(os);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("导出NameCard文件时出现:"+e.toString());
		}finally{
			
		}
	
	

	}
	
	
	/**
	 * 导出Excel表格
	 * @param res
	 * @param os
	 * @throws IOException
	 * @throws SQLException 
	 */
	public void createAutoFixationSheet(ResultSet res,OutputStream os,HSSFWorkbook wb,HSSFSheet sheet)throws  IOException, SQLException{
		int ii =0;
		String[] stringList=null;
		LinkedList<String> list=null;
		try{
			//List<String[]> list=findByStaffcode();
			list=findCons_list();
			int i =4;
			ii =res.getMetaData().getColumnCount();
			while(res.next()){
				for(int s=0;s<list.size();s++){
					if(list.get(s).indexOf(res.getString(4))>-1){
						stringList=list.get(s).split(";~;");
						list.remove(s);
						s=list.size();
					}else{
						stringList=null;
					}
				}
				
				
				//System.out.println("*********"+res.getString("staff_code")+"**********");
				i++;
				HSSFRow row2 =sheet.createRow((short)i);
				
				for(int j=0;j<ii;j++)
				{
					//String[] t=new String[4]; 
					String ss="";
					if(Util.objIsNULL(res.getString(j+1)))
						ss="";
					else{
						ss=res.getString(j+1);
						/***
						 * 2014-5-19 10:10:22 King 注释   
						 * if(j==0&&ss.toUpperCase().equals("OIE")){
							ss="@CONVOY";
						}
						if(Util.objIsNULL(res.getString(13))||Util.objIsNULL(res.getString(14))||Util.objIsNULL(res.getString(15))){
							//t=findByStaffcode(res.getString(4));
							for(int k=0;k<list.size();k++){
								if(list.get(k)[3].equals(res.getString(4))){
									t=list.get(k);
									list.remove(k);
									break;
								}
							}
						}
						
						if(j==12&&Util.objIsNULL(ss)){
							ss=t[0];
						}
						if(j==13&&Util.objIsNULL(ss)){
							ss=t[1];
						}
						if(j==14&&Util.objIsNULL(ss)){
							ss=t[2];
						}***/
						if(j==6){
							ss=res.getString(j+1).replace("(On Probation)","");
						}
						if(j==10){//Profess_title_c
							ss=Util.objIsNULL(stringList)?"":stringList[1].trim();
						}
						if(j==17){//FAX
							ss=Util.objIsNULL(stringList)?"":stringList[2].trim();
						}
					}
					cteateCell(wb,row2,(short)j,ss);
				}
			}
			sheet.autoSizeColumn(( short ) 0 ); // 调整第一列宽度 
			sheet.autoSizeColumn(( short ) 1 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 2 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 3 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 4 ); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 5 ); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 6 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 7 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 8); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 9 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 10 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 11 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 12); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 13); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 14 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 15 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 16); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 17); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 18 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 19); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 20); // 调整第五列宽度 
			/***************************为Excel设置密码***********************************/
			//sheet.protectSheet("admin");
			logger.info("**********正在生成 NameCard Excel**********");
			wb.write(os);
		}catch(SQLException e){
			e.printStackTrace();
			logger.error("导出NameCard文件时出现:"+e.toString());
		}finally{
			stringList=null;
			list=null;
		}
	
	

	}
	
	/**
	 * 获取用户最新TItle、FAX
	 * @return
	 */
	public LinkedList<String> findCons_list(){
		LinkedList<String> list=new LinkedList<String>();
		Connection cons=null;
		try{
			cons=DBManager.getCon();
			String sql="select staff_code,academic_title_e,profess_title_e ,fax from ("+
					" select staff_code,academic_title_e,profess_title_e ,fax from request_new where card_type='N' and staff_code!='' order by urgentDate desc"+
					" ) a group by staff_code";
			PreparedStatement ps=cons.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				list.add(rs.getString(1)+";~;"+
						(Util.objIsNULL(rs.getString(2))?"":rs.getString(2))+" "+(Util.objIsNULL(rs.getString(3))?"":rs.getString(3))
						+";~;"+(Util.objIsNULL(rs.getString(4))?" ":rs.getString(4)));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBManager.closeCon(cons);
		}
		return list;
	}
	
 
	
	public List<String[]> findByStaffcode(){
		List<String[]> list=new ArrayList<String[]>();

		Connection cons=null;
		try{
			cons=DBManager.getCon();
			String sql="select StaffNo, TrRegNo,Ce_No,MPF from tr ";
			PreparedStatement ps=cons.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				String[] tr=new String[4];
				tr[0]=rs.getString("TrRegNo");
				tr[1]=rs.getString("Ce_No");
				tr[2]=rs.getString("MPF");
				tr[3]=rs.getString("StaffNo");
				 list.add(tr);
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
			list=null;
		}finally{
			DBManager.closeCon(cons);
		}
		return list;
	}
	
	
	
	/**
	 * 导出Excel表格
	 * @param res
	 * @param os
	 * @param Location
	 * @throws IOException
	 * @throws SQLException 
	 */
	public void createFixationLocationSheet(ResultSet res,OutputStream os,HSSFWorkbook wb,HSSFSheet sheet,String Location)throws  IOException, SQLException{
		int ii =0;
		try{
			int i =4;
			ii =res.getMetaData().getColumnCount();
			while(res.next()){
				if(res.getString("location").equals(Location)){
					i++;
					HSSFRow row2 =sheet.createRow((short)i);
					for(int j=0;j<ii;j++)
					{
						String ss="";
						if(res.getString(j+1)==null)
							ss="";
						else
							ss=res.getString(j+1);
						cteateCell(wb,row2,(short)j,ss);
					}
				}
			}
			sheet.autoSizeColumn(( short ) 0 ); // 调整第一列宽度 
			sheet.autoSizeColumn(( short ) 1 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 2 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 3 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 4 ); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 5 ); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 6 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 7 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 8); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 9 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 10 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 11 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 12); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 13); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 14 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 15 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 16); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 17); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 18 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 19); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 20); // 调整第五列宽度 
 
			/***************************为Excel设置密码***********************************/
			sheet.protectSheet("admin");
		}catch(SQLException e){
			e.printStackTrace();
		}
		wb.write(os);
		os.flush();
		os.close();

	}
	
	public void createFixationLocationForResult(Result res,OutputStream os,HSSFWorkbook wb,HSSFSheet sheet,String Location)throws  IOException, SQLException{
		int ii =0;
		try{
			int i =4;
			for(SortedMap m:res.getRows()){
				 
				String location=(m.get("location")+"").replaceAll("/", "").replaceAll(",", "");
				if(location.indexOf(Location)>-1){
					i++;
					HSSFRow row2 =sheet.createRow((short)i);
					cteateCell(wb,row2,(short)0,m.get("location")+"");//location
					cteateCell(wb,row2,(short)1,m.get("Company")+"");//company
					cteateCell(wb,row2,(short)2,m.get("layout_type")+"");//layout_type
					cteateCell(wb,row2,(short)3,m.get("staff_code")+"");//Staff_Code
					cteateCell(wb,row2,(short)4,m.get("name")+"");//Name
					cteateCell(wb,row2,(short)5,m.get("name_chinese")+"");//Name_in_Chinese
					cteateCell(wb,row2,(short)6,m.get("title_english")+"");//Title with Department in English
					cteateCell(wb,row2,(short)7,m.get("title_chinese")+"");//Title with Department in Chinese
					cteateCell(wb,row2,(short)8,m.get("external_english")+"");//English Academic Title
					cteateCell(wb,row2,(short)9,m.get("external_chinese")+"");//Chinese Academic Title
					cteateCell(wb,row2,(short)10,m.get("profess_title_e")+"");//English Academic Title
					cteateCell(wb,row2,(short)11,m.get("profess_title_c")+"");//Chinese Academic Title
					cteateCell(wb,row2,(short)12,m.get("tr_reg_no")+"");//T.R. Reg. No.
					cteateCell(wb,row2,(short)13,m.get("ce_no")+"");//CE No.
					cteateCell(wb,row2,(short)14,m.get("mpf_no")+"");//MPF No.
					cteateCell(wb,row2,(short)15,m.get("remark1")+"");//KHCIB No.
					cteateCell(wb,row2,(short)16,m.get("e_mail")+"");//E-mail
					cteateCell(wb,row2,(short)17,m.get("direct_line")+"");//Direct Line
					cteateCell(wb,row2,(short)18,m.get("fax")+"");//Fax
					cteateCell(wb,row2,(short)19,m.get("bobile_number")+"");//Mobile Phone Number
					cteateCell(wb,row2,(short)20,m.get("quantity")+"");//Quantity
					cteateCell(wb,row2,(short)21,m.get("upd_date")+"");//upd_date
					
					
				}
				
				
			}
			/*ii =res.getMetaData().getColumnCount();
			while(res.next()){
				if(res.getString("location").equals(Location)){
					i++;
					HSSFRow row2 =sheet.createRow((short)i);
					for(int j=0;j<ii;j++)
					{
						String ss="";
						if(res.getString(j+1)==null)
							ss="";
						else
							ss=res.getString(j+1);
						cteateCell(wb,row2,(short)j,ss);
					}
				}
			}*/
			sheet.autoSizeColumn(( short ) 0 ); // 调整第一列宽度 
			sheet.autoSizeColumn(( short ) 1 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 2 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 3 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 4 ); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 5 ); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 6 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 7 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 8); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 9 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 10 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 11 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 12); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 13); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 14 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 15 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 16 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 17); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 18); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 19 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 20); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 21); // 调整第五列宽度 
 
			/***************************为Excel设置密码***********************************/
			sheet.protectSheet("admin");
		}catch(Exception e){
			e.printStackTrace();
		}
		wb.write(os);
		os.flush();
		os.close();

	}
	
	/**
	 * 导出Excel表格
	 * @param res
	 * @param os
	 * @param staffcode
	 * @throws IOException
	 */
	public void createFixationSheet(ResultSet res,OutputStream os,HSSFWorkbook wb,HSSFSheet sheet,String staffcode)throws  IOException{
		int ii =0;
		try{
			int i =4;
			ii =res.getMetaData().getColumnCount();
			while(res.next()){
				if(res.getString("staff_code").equals(staffcode)){
					i++;
					HSSFRow row2 =sheet.createRow((short)i);
					for(int j=0;j<ii;j++)
					{
						String ss="";
						if(res.getString(j+1)==null){
							ss="";
						}else{
							ss=res.getString(j+1);
						}
						cteateCell(wb,row2,(short)j,ss);
					}
				}
			}
			sheet.autoSizeColumn(( short ) 0 ); // 调整第一列宽度 
			sheet.autoSizeColumn(( short ) 1 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 2 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 3 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 4 ); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 5 ); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 6 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 7 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 8); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 9 ); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 10 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 11 ); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 12); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 13); // 调整第六列宽度 
			sheet.autoSizeColumn(( short ) 14 ); // 调整第七列宽度 
			sheet.autoSizeColumn(( short ) 15 ); // 调整第八列宽度 
			sheet.autoSizeColumn(( short ) 16); // 调整第九列宽度 
			sheet.autoSizeColumn(( short ) 17); // 调整第二列宽度 
			sheet.autoSizeColumn(( short ) 18 ); // 调整第三列宽度 
			sheet.autoSizeColumn(( short ) 19); // 调整第四列宽度 
			sheet.autoSizeColumn(( short ) 20); // 调整第五列宽度 
			sheet.autoSizeColumn(( short ) 21); // 调整第六列宽度 
			/***************************为Excel设置密码***********************************/
			sheet.protectSheet("admin");
		}catch(SQLException e){
			e.printStackTrace();
		}
		wb.write(os);
		os.flush();
		os.close();

	}
	/**
	 * 设置单元格的值及其样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFCell cell = row.createCell(col);

		cell.setCellValue(new HSSFRichTextString(val));
		/*HSSFCellStyle cellstyle= wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);

		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("新細明體");

		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle); */

	}
	/**
	 * 设置列头样式
	 * @param wb
	 * @param row
	 * @param col
	 * @param val
	 */
	@SuppressWarnings("deprecation")
	public static void cteateTitleCell(HSSFWorkbook wb,HSSFRow row,short col,String val)
	{
		HSSFCell cell = row.createCell(col);
		cell.setCellValue(new HSSFRichTextString(val));
		HSSFCellStyle cellstyle= wb.createCellStyle();
		//	cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		Font font = wb.createFont();
		font.setFontHeightInPoints((short)12); //字体大小
		font.setFontName("仿宋");
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体

		cellstyle.setFont(font);
		cell.setCellStyle(cellstyle);

	}

}
