package servlet.china;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;

import util.Constant;
import util.DateUtils;
import util.FileUtil;
import util.Util;
import util.UtilCommon;

import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;

import dao.GetChinaSimpleDao;
import dao.exp.ExpConvoyChinaForm;
import dao.impl.GetChinaSimpleDaoImpl;
import entity.ChinaConslist;
import entity.ChinaPolicy;

@SuppressWarnings("deprecation")
public class ReportTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log LOG = LogFactory.getLog(ReportTestServlet.class);
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
	@SuppressWarnings("static-access")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		GetChinaSimpleDao chinaSimpleDao =new GetChinaSimpleDaoImpl();
		response.setContentType("text/html;charset=utf-8;");
		PrintWriter out = response.getWriter();
		String s1 ="";
		//查询人员列表-分组查询
		List<ChinaPolicy> cpList = chinaSimpleDao.getPolicyGroup();
		 try{
				ExpConvoyChinaForm expcard = new ExpConvoyChinaForm();
			 
				String downFile=Constant.CHINA_POLICY_URL+DateUtils.getDateToday();
				if(FileUtil.directoryExists(downFile)){
					 FileUtil.deleteAll(downFile);
					// new File(downFile).mkdirs();
				}else{
				 new File(downFile).mkdirs();
				}
				LOG.info(" 文件导出开始...");
			
			for(int j=0;j< cpList.size();j++){	//cpList.size() 
				//System.out.println(j+"-------"+cpList.get(j).getStaffname()+"---------"+cpList.get(j).getSalesOffice());	
				ChinaConslist conslist = chinaSimpleDao.getStaffList(cpList.get(j).getStaffname(), cpList.get(j).getSalesOffice(), cpList.get(j).getRemark());
				
				if (!Util.objIsNULL(conslist.getStaffname()) ) {
					
				 OutputStream os = new FileOutputStream(downFile+"//"+conslist.getStaffname()+"_"+conslist.getSalesOffice()+"_"+"2013-12"+".xls");//取出输出流 DateUtils.delTwoMonth(DateUtils.getDateToday())
				 HSSFWorkbook wb = new HSSFWorkbook();	
				 HSSFCellStyle cellstyle= wb.createCellStyle();
				 cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				 cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
				 cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
				 cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
				 
				 ResultSet res=chinaSimpleDao.getFristComi(conslist.getStaffname(), conslist.getSalesOffice(), "");
				 res.last();
				 int resnum = res.getRow();
				 res.beforeFirst();

				 ResultSet res2=chinaSimpleDao.getFristRate(conslist.getStaffname(), conslist.getSalesOffice(), "");
				 res2.last();
				 int resnum2 = res2.getRow(); 
				 res2.beforeFirst();
				 
				 ResultSet res3=chinaSimpleDao.getTwoComi(conslist.getStaffname(), conslist.getSalesOffice(), "");
				 res3.last();
				 int resnum3 = res3.getRow();
				 res3.beforeFirst();
				 
				 ResultSet res4=chinaSimpleDao.getPolicyTeam(conslist.getStaffname(), conslist.getSalesOffice(), "");
				 res4.last();
				 int resnum4 = res4.getRow();
				 res4.beforeFirst();
				 
				 ResultSet res5=chinaSimpleDao.getPolicyCoach(conslist.getStaffname(), conslist.getTitle().trim(), "");//.replace("碧升", "")
				 res5.last();
				 int resnum5 = res5.getRow(); 
				 res5.beforeFirst();
				 
				 ResultSet res6=chinaSimpleDao.getPolicyCoachTwo(conslist.getStaffname(), conslist.getTitle().trim(), "");//.replace("碧升", "")
				 res6.last();
				 int resnum6 = res6.getRow(); 
				 res6.beforeFirst();
				 
				//	if(!Util.objIsNULL(res)){
						HSSFSheet sheet = wb.createSheet("new sheet");
						 sheet.setColumnWidth(( short ) 0 ,(short)(140*20)); 			 // 调整第1列宽度 
						 sheet.setColumnWidth(( short ) 1 ,(short)(140*20)); 			  
						 sheet.setColumnWidth(( short ) 2 ,(short)(140*20)); 			  
						 sheet.setColumnWidth(( short ) 3 ,(short)(140*20)); 			 
						 sheet.setColumnWidth(( short ) 4 ,(short)(90*20));	 
						 sheet.setColumnWidth(( short ) 5 ,(short)(110*20));
						 sheet.setColumnWidth(( short ) 6 ,(short)(136*20));
						 sheet.setColumnWidth(( short ) 7 ,(short)(136*20));	
						 sheet.setColumnWidth(( short ) 8 ,(short)(136*20));
						 sheet.setColumnWidth(( short ) 9 ,(short)(136*20));
						 
						 HSSFPrintSetup ps = sheet.getPrintSetup();
						 ps.setLandscape(false); // 打印方向，true：横向，false：纵向
						 ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //纸张
						 
						 ps.setHeaderMargin((double)0.5);
						 ps.setFooterMargin((double)0.4);
							
						 sheet.setMargin(HSSFSheet.LeftMargin,( short) 0.6 );// 页边距（左）
						 sheet.setMargin(HSSFSheet.RightMargin,( short) 0.4 );// 页边距（右）
						 
						 sheet.setMargin(HSSFSheet.BottomMargin,( short) 0.8 );// 页边距（下）
						 sheet.setMargin(HSSFSheet.TopMargin,( short) 0.7 );// 页边距（上）
						 
						 sheet.setHorizontallyCenter(true);//设置打印页面为水平居中
						 sheet.setVerticallyCenter(true);//设置打印页面为垂直居中 
						
						 //## 设置打印区域 A1--C2 ##//
						 wb.setPrintArea(0, "$A$1:$J$"+(19+resnum+resnum2+resnum3+resnum4+resnum5+resnum6+11)+"");
						 
						wb.setSheetName(0, conslist.getStaffname()+"_"+DateUtils.getDateToday());
						HSSFRow row=sheet.createRow(0);
						//sheet.createFreezePane(10, 0);
							sheet.addMergedRegion(new Region(0,(short)0,0,(short)9));
							setCellBorder(0, 9, row, cellstyle);
							expcard.cteateTitleCenterCell(wb,row,(short)0,"北京康宏碧升保险代理有限公司");//北京碧升保险代理有限公司
							
						row=sheet.createRow(1);
						
							sheet.addMergedRegion(new Region(1,(short)0,1,(short)9));
							setCellBorder(0, 9, row, cellstyle);
							expcard.cteateTitleCenter2Cell(wb,row,(short)0,"工资明细单");
						//row=sheet.createRow(2);
							
						row=sheet.createRow(3);
							expcard.cteateConstantCell(wb,row,(short)0,"姓    名");
							sheet.addMergedRegion(new Region(3,(short)1,3,(short)2));
							setCellBorder(1, 2, row, cellstyle);
							expcard.cteateValsCell(wb,row,(short)1,conslist.getStaffname());
							 
							expcard.cteateConstantCell(wb,row,(short)3,"营 业 部");
							sheet.addMergedRegion(new Region(3,(short)4,3,(short)5));
							setCellBorder(4, 5, row, cellstyle);
							expcard.cteateValsCell(wb,row,(short)4,conslist.getSalesOffice());
							
							expcard.cteateConstantCell(wb,row,(short)6,"辅 导 人");
							expcard.cteateValsCell(wb,row,(short)7,conslist.getCoach());
							
							expcard.cteateConstantCell(wb,row,(short)8,"工作月份");
							expcard.cteateValsCell(wb,row,(short)9,"2013-12");//DateUtils.delTwoMonth(DateUtils.getYearMonth())
						row=sheet.createRow(4);
							expcard.cteateConstantCell(wb,row,(short)0,"证件号码");
							sheet.addMergedRegion(new Region(4,(short)1,4,(short)2));
							setCellBorder(1, 2, row, cellstyle);
							expcard.cteateValsCell(wb,row,(short)1,conslist.getIdCardNo()); 
							
							expcard.cteateConstantCell(wb,row,(short)3,"职    称");
							sheet.addMergedRegion(new Region(4,(short)4,4,(short)5));
							setCellBorder(4, 5, row, cellstyle);
							expcard.cteateValsCell(wb,row,(short)4,conslist.getTitle());
							
							expcard.cteateConstantCell(wb,row,(short)6,"推 介 人");
							expcard.cteateValsCell(wb,row,(short)7,conslist.getRecommend());
							
							expcard.cteateConstantCell(wb,row,(short)8,"发佣日期");
							expcard.cteateValsCell(wb,row,(short)9,"2014-02"+"-05");//DateUtils.getYearMonth()++"-05" 
						row=sheet.createRow(5);
							expcard.cteateConstantCell(wb,row,(short)0,"银行帐号");
							sheet.addMergedRegion(new Region(5,(short)1,5,(short)2));
							setCellBorder(1, 2, row, cellstyle);
							expcard.cteateValsCell(wb,row,(short)1,conslist.getBankAccount());
							
							expcard.cteateConstantCell(wb,row,(short)3,"业务编号");
							sheet.addMergedRegion(new Region(5,(short)4,5,(short)5));
							setCellBorder(4, 5, row, cellstyle);
							expcard.cteateValsCell(wb,row,(short)4,conslist.getAgentNumber());
							
							expcard.cteateConstantCell(wb,row,(short)6,"入司日期");
							expcard.cteateValsCell(wb,row,(short)7,conslist.getJoinDate());
							
						row=sheet.createRow(6);
							sheet.addMergedRegion(new Region(6,(short)0,6,(short)9));
							
						row=sheet.createRow(7);
							expcard.cteateConstantCell(wb,row,(short)0,"个人业绩");
							sheet.addMergedRegion(new Region(7,(short)0,8,(short)0));

							expcard.cteateConstantCell(wb,row,(short)1,"首年FYB");
							expcard.cteateValuesCell(wb,row,(short)2,"SUM(J19:J"+(19+resnum-1)+")"); 
							//expcard.cteateConstantCell(wb,row,(short)2,"0.00");
							
							expcard.cteateConstantCell(wb,row,(short)3,"首年佣金");
							sheet.addMergedRegion(new Region(7,(short)4,7,(short)5));
							setCellBorder(4, 5, row, cellstyle);
							expcard.cteateValuesCell(wb,row,(short)4,"C8*68%+SUM(J"+(19+resnum+1)+":J"+(19+resnum+resnum2)+")"); 
						    
							expcard.cteateConstantCell(wb,row,(short)6,"辅导津贴");
							//TODO: 辅导津贴:若职称包含"主任"或"见习"字符时,则比率12%,包含"经理"或"总监",则比率为24%
							if (conslist.getTitle().indexOf("见习")>0 || conslist.getTitle().indexOf("主任") >0) {
								expcard.cteateValuesCell(wb,row,(short)7,"C8*12%");
							}else
								expcard.cteateValuesCell(wb,row,(short)7,"C8*24%");
							
							expcard.cteateConstantCell(wb,row,(short)8,"特别津贴");
							expcard.cteateValsCell(wb,row,(short)9,"0.00");
							
						row=sheet.createRow(8);
							//expcard.cteateConstantCell(wb,row,(short)0,"个人业绩");
							//sheet.addMergedRegion(new Region(7,(short)0,8,(short)0));
							setCellLEFTBorder(0, 1, row, wb);
							
							expcard.cteateConstantCell(wb,row,(short)1,"续年佣金");
							expcard.cteateValuesCell(wb,row,(short)2,"SUM(J"+(19+resnum+resnum2+3)+":J"+(19+resnum+resnum2+resnum3+2)+")"); 
							
							expcard.cteateConstantCell(wb,row,(short)3,"团险佣金");
							sheet.addMergedRegion(new Region(8,(short)4,8,(short)5));
							setCellBorder(4, 5, row, cellstyle);
							expcard.cteateValuesCell(wb,row,(short)4,"SUM(J"+(19+resnum+resnum2+resnum3+5)+":J"+(19+resnum+resnum2+resnum3+resnum4+4)+")"); 
							
							expcard.cteateConstantCell(wb,row,(short)6,"财险佣金");
							expcard.cteateValsCell(wb,row,(short)7,"0.00");
							
							expcard.cteateConstantCell(wb,row,(short)8,"年终奖金");
							expcard.cteateValsCell(wb,row,(short)9,"0.00");
							
						row=sheet.createRow(9);
							expcard.cteateConstantCell(wb,row,(short)0,"体系业绩");
							
							expcard.cteateConstantCell(wb,row,(short)1,"管理津贴");
							expcard.cteateValuesCell(wb,row,(short)2,"SUM(H"+(19+resnum+resnum2+resnum3+resnum4+resnum5+11)+":H"+(19+resnum+resnum2+resnum3+resnum4+resnum5+resnum6+10)+")"); 
							
							
							expcard.cteateConstantCell(wb,row,(short)3,"辅导津贴");
							sheet.addMergedRegion(new Region(9,(short)4,9,(short)5));
							setCellBorder(4, 5, row, cellstyle);
							expcard.cteateValuesCell(wb,row,(short)4,"SUM(H"+(19+resnum+resnum2+resnum3+resnum4+9)+":H"+(19+resnum+resnum2+resnum3+resnum4+resnum5+8)+")"); 
							
							
							expcard.cteateConstantCell(wb,row,(short)6,"推介津贴");
							expcard.cteateValuesCell(wb,row,(short)7,"SUM(J"+(19+resnum+resnum2+resnum3+resnum4+9)+":J"+(19+resnum+resnum2+resnum3+resnum4+resnum5+8)+",J"+(19+resnum+resnum2+resnum3+resnum4+resnum5+11)+":J"+(19+resnum+resnum2+resnum3+resnum4+resnum5+resnum6+10)+")"); 
							
							
							expcard.cteateConstantCell(wb,row,(short)8,"年终奖金");
							expcard.cteateValsCell(wb,row,(short)9,"0.00");
							
						row=sheet.createRow(10);
							expcard.cteateConstantCell(wb,row,(short)0,"各项奖励");
							
							expcard.cteateConstantCell(wb,row,(short)1,"责任底薪");
							expcard.cteateValsCell(wb,row,(short)2,"0.00");
							
							expcard.cteateConstantCell(wb,row,(short)3,"绩优奖励");
							sheet.addMergedRegion(new Region(10,(short)4,10,(short)5));
							setCellBorder(4, 5, row, cellstyle);
							expcard.cteateValsCell(wb,row,(short)4,"0.00");
							
							expcard.cteateConstantCell(wb,row,(short)6," ");
							expcard.cteateConstantCell(wb,row,(short)7," ");
							
							expcard.cteateConstantCell(wb,row,(short)8,"其他加扣");
							expcard.cteateValsCell(wb,row,(short)9,"0.00");
						
						row=sheet.createRow(11);
							sheet.addMergedRegion(new Region(11,(short)0,11,(short)9));
							
						row=sheet.createRow(12);
							expcard.cteateConstantCell(wb,row,(short)0,"营 业 税");
							expcard.cteateValuesCell(wb,row,(short)1,"IF(I13>20000,I13*0.05,0)"); 
							
							expcard.cteateConstantCell(wb,row,(short)2,"应纳税所得额");
							expcard.cteateValuesCell(wb,row,(short)3,"IF(I13>6666.67,(I13*0.6-B13-B14-D14)*0.8,MAX(0,(I13*0.6-B13-B14-D14)-800))"); 
							
							sheet.addMergedRegion(new Region(12,(short)4,12,(short)5));
							setCellBorder(4, 5, row, cellstyle);
							expcard.cteateConstantCell(wb,row,(short)4,"个人所得税");
							expcard.cteateValuesCell(wb,row,(short)6,"D13*0.2"); 
							
							expcard.cteateConstantCell(wb,row,(short)7,"应发金额");
							
							sheet.addMergedRegion(new Region(12,(short)8,12,(short)9));
							setCellBorder(8, 9, row, cellstyle);
							expcard.cteateValuesCellyel(wb,row,(short)8,"SUM(C9:C11,E8:F11,H8:H11,J8:J11)"); 
							
						row=sheet.createRow(13);
							expcard.cteateConstantCell(wb,row,(short)0,"城 建 税");
							expcard.cteateValuesCell(wb,row,(short)1,"B13*0.07"); 
							
							expcard.cteateConstantCell(wb,row,(short)2,"教育费附加");
							expcard.cteateValuesCell(wb,row,(short)3,"B13*0.03"); 
							
							sheet.addMergedRegion(new Region(13,(short)4,13,(short)5));
							setCellBorder(4, 5, row, cellstyle);
							expcard.cteateConstantCell(wb,row,(short)4,"应扣税金	");
							expcard.cteateValuesCell(wb,row,(short)6,"B13+B14+D14+G13");
							
							expcard.cteateConstantCell(wb,row,(short)7,"实发金额");
							
							sheet.addMergedRegion(new Region(13,(short)8,13,(short)9));
							setCellBorder(8, 9, row, cellstyle);
							expcard.cteateValuesCellyel(wb,row,(short)8,"I13-G14"); 
							
						row=sheet.createRow(14);	
						row=sheet.createRow(15);
						expcard.cteateTitleTwoCenterCell(wb,row,(short)0,"保单明细");
						
						row=sheet.createRow(16);
						sheet.addMergedRegion(new Region(16,(short)0,16,(short)9));
						setCellBorder(0, 9, row, cellstyle);
						expcard.cteateTitleTwoCenterCell(wb,row,(short)0,"首  年");
						
						row=sheet.createRow(17);
						row.setHeight((short) (10*32));
						setCellLEFTBorder(0, 1, row, wb);
						setCellBorderAndVal(0, 1, row, cellstyle, "保险公司");
						setCellBorderAndVal(1, 2, row, cellstyle, "保单号");
						setCellBorderAndVal(2, 3, row, cellstyle, "险种");
						setCellBorderAndVal(3, 4, row, cellstyle, "投保人");
						setCellBorderAndVal(4, 5, row, cellstyle, "缴别");
						setCellBorderAndVal(5, 6, row, cellstyle, "年期");
						setCellBorderAndVal(6, 7, row, cellstyle, "生效日期");
						setCellBorderAndVal(7, 8, row, cellstyle, "保费");
						setCellBorderAndVal(8, 9, row, cellstyle, "FYB值");
						setCellBorderAndVal(9, 10, row, cellstyle, "FYB");
						setCellBorderAndVal(10, 10, row, cellstyle, "见习人");
						/*expcard.cteateTWOTitleCenterCell(wb,row,(short)0,"保险公司");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)1,"保单号");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)2,"险种");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)3,"投保人");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)4,"缴别");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)5,"年期");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)6,"生效日期");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)7,"保费");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)8,"FYB值");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)9,"FYB");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)10,"见习人");*/
						int ii =0;
					 
							int i =17;
							ii =res.getMetaData().getColumnCount()+1;
							 
							while(res.next()){
								i++;
								HSSFRow row2 =sheet.createRow((short)i);
								for(int k=0;k<ii;k++)
								{
									String ss="";
									if (k<=8) {
										
										if(Util.objIsNULL(res.getString(k+1))){
											ss="0";
										}else{
											ss=res.getString(k+1);
										}
										
									}else {
										ss ="H"+(i+1)+"*I"+(i+1)+"/100"; 
									} 
									if (k==10) {
										if(Util.objIsNULL(res.getString(k))){
											ss="";
										}else{
											ss=res.getString(k);
										}
									}
									
									UtilCommon.cteateChinaSimplCell(wb,row2,(short)k,ss);
								}
								
								
							}
							res.close();
						/**
						 * 首年佣金
						 */
							i++;
						row=sheet.createRow(i);
							setCellBorderAndVal(0, 1, row, cellstyle, "保险公司");
							setCellBorderAndVal(1, 2, row, cellstyle, "保单号");
							setCellBorderAndVal(2, 3, row, cellstyle, "险种");
							setCellBorderAndVal(3, 4, row, cellstyle, "投保人");
							setCellBorderAndVal(4, 5, row, cellstyle, "缴别");
							setCellBorderAndVal(5, 6, row, cellstyle, "年期");
							setCellBorderAndVal(6, 7, row, cellstyle, "生效日期");
							setCellBorderAndVal(7, 8, row, cellstyle, "保费");
							setCellBorderAndVal(8, 9, row, cellstyle, "佣金比率");
							setCellBorderAndVal(9, 10, row, cellstyle, "佣金");
							setCellBorderAndVal(10, 10, row, cellstyle, "见习人");
						
							/*expcard.cteateTWOTitleCenterCell(wb,row,(short)0,"保险公司");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)1,"保单号");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)2,"险种");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)3,"投保人");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)4,"缴别");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)5,"年期");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)6,"生效日期");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)7,"保费");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)8,"佣金比率");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)9,"佣金");*/
						ii =res2.getMetaData().getColumnCount()+1;
						while(res2.next()){
							i++;
							HSSFRow row2 =sheet.createRow((short)i);
							for(int n=0; n<ii;n++)
							{
								
								String ss="";
								if (n<=8) {
									
									if(Util.objIsNULL( res2.getString(n+1))){
										ss="0";
									}else{
										ss=res2.getString(n+1);
									}
									
								}else {
									ss ="H"+(i+1)+"*I"+(i+1)+"/100"; 
								}
								if (n==10) {
									if(Util.objIsNULL(res2.getString(n))){
										ss="";
									}else{
										ss=res2.getString(n);
									}
								}
								UtilCommon.cteateChinaSimplCell(wb,row2,(short)n,ss);
							}
						}
						res2.close();		
					/**
					 * 续  年
					 */
						i++;
						row=sheet.createRow(i);
						sheet.addMergedRegion(new Region(i,(short)0,i,(short)9));
						setCellBorder(0, 9, row, cellstyle);
						expcard.cteateTitleTwoCenterCell(wb,row,(short)0,"续  年");
						i++;
						row=sheet.createRow(i);
						row.setHeight((short) (10*32));
						setCellBorderAndVal(0, 1, row, cellstyle, "保险公司");
						setCellBorderAndVal(1, 2, row, cellstyle, "保单号");
						setCellBorderAndVal(2, 3, row, cellstyle, "险种");
						setCellBorderAndVal(3, 4, row, cellstyle, "投保人");
						setCellBorderAndVal(4, 5, row, cellstyle, "缴别");
						setCellBorderAndVal(5, 6, row, cellstyle, "年期");
						setCellBorderAndVal(6, 7, row, cellstyle, "生效日期");
						setCellBorderAndVal(7, 8, row, cellstyle, "保费");
						setCellBorderAndVal(8, 9, row, cellstyle, "FYB值");
						setCellBorderAndVal(9, 9, row, cellstyle, "佣金");
						/*expcard.cteateTWOTitleCenterCell(wb,row,(short)0,"保险公司");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)1,"保单号");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)2,"险种");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)3,"投保人");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)4,"缴别");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)5,"年期");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)6,"生效日期");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)7,"保费");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)8,"FYB值");
						expcard.cteateTWOTitleCenterCell(wb,row,(short)9,"佣金");*/
						ii =0;
					 
							ii =res3.getMetaData().getColumnCount()+1;
							
							while(res3.next()){
								i++;
								HSSFRow row2 =sheet.createRow((short)i);
								for(int k=0;k<ii;k++)
								{
									String ss="";
									if (k<=8) {
										
										if(Util.objIsNULL(res3.getString(k+1))){
											ss="0";
										}else{
											ss=res3.getString(k+1);
										}
										
									}else {
										ss ="H"+(i+1)+"*I"+(i+1)+"/100"; 
									}
									
									UtilCommon.cteateChinaSimplCell(wb,row2,(short)k,ss);
								}
								
								
							}
							res3.close();
							
						/**
						 * 团险
						 */
						i++;
						row=sheet.createRow(i);
							sheet.addMergedRegion(new Region(i,(short)0,i,(short)9));
							setCellBorder(0, 9, row, cellstyle);
							expcard.cteateTitleTwoCenterCell(wb,row,(short)0,"团  险");
							i++;
							row=sheet.createRow(i);
							row.setHeight((short) (10*32));
							setCellBorderAndVal(0, 1, row, cellstyle, "保险公司");
							setCellBorderAndVal(1, 2, row, cellstyle, "保单号");
							setCellBorderAndVal(2, 3, row, cellstyle, "险种");
							setCellBorderAndVal(3, 4, row, cellstyle, "投保人");
							setCellBorderAndVal(4, 5, row, cellstyle, "缴别");
							setCellBorderAndVal(5, 6, row, cellstyle, "年期");
							setCellBorderAndVal(6, 7, row, cellstyle, "生效日期");
							setCellBorderAndVal(7, 8, row, cellstyle, "保费");
							setCellBorderAndVal(8, 9, row, cellstyle, "FYB值");
							setCellBorderAndVal(9, 9, row, cellstyle, "佣金");/*
							expcard.cteateTWOTitleCenterCell(wb,row,(short)0,"保险公司");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)1,"保单号");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)2,"险种");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)3,"投保人");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)4,"缴别");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)5,"年期");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)6,"生效日期");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)7,"保费");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)8,"FYB值");
							expcard.cteateTWOTitleCenterCell(wb,row,(short)9,"佣金");*/
							ii =0;
							
							ii =res4.getMetaData().getColumnCount()+1;
							
							while(res4.next()){
								i++;
								HSSFRow row2 =sheet.createRow((short)i);
								for(int k=0;k<ii;k++)
								{
									String ss="";
									if (k<=8) {
										
										if(Util.objIsNULL(res4.getString(k+1))){
											ss="0";
										}else{
											ss=res4.getString(k+1);
										}
										
									}else {
										ss ="H"+(i+1)+"*I"+(i+1)+"/100"; 
									}
									
									UtilCommon.cteateChinaSimplCell(wb,row2,(short)k,ss);
								}
								
								
							}
							res4.close();
						/**
						 *财险
						 */
						i++;
						row=sheet.createRow(i);
								sheet.addMergedRegion(new Region(i,(short)0,i,(short)9));
								setCellBorder(0, 9, row, cellstyle);
								expcard.cteateTitleTwoCenterCell(wb,row,(short)0,"财  险");
								i++;
								row=sheet.createRow(i);
								row.setHeight((short) (10*32));
								setCellBorderAndVal(0, 1, row, cellstyle, "保险公司");
								setCellBorderAndVal(1, 2, row, cellstyle, "保单号");
								setCellBorderAndVal(2, 3, row, cellstyle, "险种");
								setCellBorderAndVal(3, 4, row, cellstyle, "投保人");
								setCellBorderAndVal(4, 5, row, cellstyle, "缴别");
								setCellBorderAndVal(5, 6, row, cellstyle, "年期");
								setCellBorderAndVal(6, 7, row, cellstyle, "生效日期");
								setCellBorderAndVal(7, 8, row, cellstyle, "保费");
								setCellBorderAndVal(8, 9, row, cellstyle, "FYB值");
								setCellBorderAndVal(9, 9, row, cellstyle, "佣金"); 	
							/**
							 *辅导体系
							 */
							i++;
							i++;
							row=sheet.createRow(i);
									sheet.addMergedRegion(new Region(i,(short)0,i+resnum5,(short)0));
									setCellBorder(0, 1, row, cellstyle);
									expcard.cteateTitleTwoCenterCell(wb,row,(short)0,"辅导体系");
									cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
									row.setHeight((short) (10*32));
									setCellBorderAndVal(1, 2, row, cellstyle, "姓名");
									setCellBorderAndVal(2, 3, row, cellstyle, "业务编号");
									setCellBorderAndVal(3, 4, row, cellstyle, "职称");
									sheet.addMergedRegion(new Region(i,(short)4,i,(short)5));
									setCellBorder(4, 5, row, cellstyle);
									expcard.cteateConstantCell(wb,row,(short)4,"当月FYB");
									setCellBorderAndVal(5, 6, row, cellstyle, "比率");
									setCellBorderAndVal(7, 8, row, cellstyle, "辅导津贴");
									setCellBorderAndVal(8, 9, row, cellstyle, "比率");
									setCellBorderAndVal(9, 9, row, cellstyle, "推介津贴");
								ii =0;
								
								ii =res5.getMetaData().getColumnCount();
								DecimalFormat dFormat = new DecimalFormat("##0");
								while(res5.next()){
									i++;
									HSSFRow row2 =sheet.createRow((short)i);
									for(int k=0;k<ii;k++)
									{
										String ss="";
										if (k < 3 ) {
											if(Util.objIsNULL(res5.getString(k+1))){
												ss="N/A";
											}else{
												ss = res5.getString(k+1);
											}
										}else if(k==3){
											ss=" ";
										}else if(k==4){
											if(Util.objIsNULL(res5.getString("fybTotal"))){
												ss="0.00";
											}else{
												ss = res5.getString("fybTotal");
											}
										} else if(k==5){
											if(Util.objIsNULL(res5.getString("fybTotal"))  || Util.objIsNULL(res5.getString("coachMoney"))){
												ss="0.00";
											}else{
												double abc =new Double(res5.getString("coachMoney")) / new Double(res5.getString("fybTotal"))* 100;
												ss = dFormat.format(abc);
											}
										}else if(k==6){
											ss ="F"+(i+1)+"*G"+(i+1)+"/100"; 
										}else if(k==7){
											if(Util.objIsNULL(res5.getString("fybTotal")) || Util.objIsNULL(res5.getString("promotionone"))){
												ss="0.00";
											}else{
												double abc =new Double(res5.getString("promotionone")) / new Double(res5.getString("fybTotal"))* 100;
												ss = dFormat.format(abc);
											}
										}else {
											ss ="F"+(i+1)+"*i"+(i+1)+"/100"; 
										}
										setCellLEFTBorder(0, 0, row2, wb);
										UtilCommon.cteateChinaCell(wb,row2,(short)(k+1),ss);
									}
									
									
								}
							res5.close();
							row=sheet.createRow(i+1);
							setCellTopBorder(0, 9, row, wb);
						/**
						 * 管理体系
						 */
						i=i+2;
							row=sheet.createRow(i);
							sheet.addMergedRegion(new Region(i,(short)0,i+resnum6,(short)0));
							setCellBorder(0, 1, row, cellstyle);
							expcard.cteateTitleTwoCenterCell(wb,row,(short)0,"管理体系");
							cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
							row.setHeight((short) (10*32));
							setCellBorderAndVal(1, 2, row, cellstyle, "姓名");
							setCellBorderAndVal(2, 3, row, cellstyle, "业务编号");
							setCellBorderAndVal(3, 4, row, cellstyle, "代");
							sheet.addMergedRegion(new Region(i,(short)4,i,(short)5));
							setCellBorder(4, 5, row, cellstyle);
							expcard.cteateConstantCell(wb,row,(short)4,"当月FYB");
							setCellBorderAndVal(5, 6, row, cellstyle, "比率");
							setCellBorderAndVal(7, 8, row, cellstyle, "管理津贴");
							setCellBorderAndVal(8, 9, row, cellstyle, "比率");
							setCellBorderAndVal(9, 9, row, cellstyle, "推介津贴");
							 	
							ii =0;
							
							ii =res6.getMetaData().getColumnCount();
							while(res6.next()){
								i++;
								HSSFRow row2 =sheet.createRow((short)i);
								for(int k=0;k<ii;k++)
								{
									String ss="";
									if (k < 2 ) {
										if(Util.objIsNULL(res6.getString(k+1))){
											ss="N/A";
										}else{
											ss = res6.getString(k+1);
										}
									}else if(k==2){
										if(Util.objIsNULL(res6.getString("type"))){
											ss="1";
										}else{
											ss = res6.getString("type");
										}
									}else if(k==3){
										ss=" ";
									}else if(k==4){
										if(Util.objIsNULL(res6.getString("fybTotal"))){
											ss="0";
										}else{
											ss = res6.getString("fybTotal");
										}
									} else if(k==5){
										if(res6.getString("type").trim().equals("1") && !Util.objIsNULL(res6.getString("payTwo")) ){
											ss="3.5";//记录对应"第二辅导人",则代为"1"对应的管理津贴为3.5(若有) 
										
										}else if (res6.getString("type").trim().equals("2") && !Util.objIsNULL(res6.getString("coachthree")) ) {
											ss="2";//记录对应"第二辅导人",则代为"1"对应的管理津贴为3.5(若有) 		
										
										}else{
											ss="0";//记录对应"第三辅导人",则代为"2"
										}
									}else if(k==6){
										if (!Util.objIsNULL(res6.getString("payTwo"))) {
											ss ="F"+(i+1)+"*G"+(i+1)+"/100"; 
										}else if (!Util.objIsNULL(res6.getString("coachthree"))) {
											ss ="F"+(i+1)+"*G"+(i+1)+"/100"; 
										}else
											ss="0.00";
									}else if(k==7){
										if(res6.getString("type").trim().equals("1") && !Util.objIsNULL(res6.getString("promotiontwo"))){
											ss="1.5";// 推介津贴为1.5(若有)		
										}else{
											ss="0";//记录对应"第三辅导人",则代为"2"
										}
									}else if(k==8){
										if (!Util.objIsNULL(res6.getString("promotiontwo"))) {
											ss ="F"+(i+1)+"*i"+(i+1)+"/100"; 
										}else{
											ss="0.00";
										}
									}
									setCellLEFTBorder(0, 0, row2, wb);
									UtilCommon.cteateChinaCell(wb,row2,(short)(k+1),ss);
								}
							}
							row=sheet.createRow(i+1);
							setCellTopBorder(0, 9, row, wb);
							res6.close();
							
						wb.write(os);
						os.flush();
						os.close();
						//	expcard.createFixationSheet(res, os,wb,sheet,listString.get(j));
						//	res.close();
					}else {
						s1 = s1+"检测不到数据："+cpList.get(j).getStaffname()+"===="+cpList.get(j).getSalesOffice()+"\n";
						System.out.println(j+"==="+cpList.get(j).getStaffname()+"===="+conslist.getStaffname()+"===="+cpList.get(j).getSalesOffice()+"===="+conslist.getSalesOffice());	
					}
		 	}
			 writeTxt(s1);
			 out.print("导出成功!地址为:"+downFile.replaceAll("//", "/"));
			 LOG.info("导出china policy成功  ! 地址为  ："+downFile.replaceAll("//","/"));
		 }catch(FileNotFoundException f){
				out.print("error:另一个程序正在使用此文件，进程无法访问。");
				LOG.error("导出Medical Staff  personal时 出现    ：  另一个应用程序正在使用此文件，进程无法访问   ");
		}catch(Exception e){
			 LOG.error("导出china policy 时出现 ："+e.toString());
			 e.printStackTrace();
		 }finally{
				out.flush();
				out.close();
		 }
	
	}
	
	/**   
     * 合并单元格加边框  水平   
     * @param sheet   
     * @param region   
     * @param cs   
     */     
    public static void setCellBorder(int start, int end, HSSFRow row,    HSSFCellStyle style) {     
        for(int i=start;i<=end;i++){   
            HSSFCell cell = row.createCell(i);        
            //cell.setCellValue("");        
            cell.setCellStyle(style);        
        }     
    }   
    public static void setCellLEFTBorder(int start, int end, HSSFRow row,    HSSFWorkbook wb) {     
    	HSSFCellStyle cellstyle = wb.createCellStyle();
    	for(int i=start;i<=end;i++){   
    		HSSFCell cell = row.createCell(i);        
    		//cell.setCellValue("");        
    		cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
    		cell.setCellStyle(cellstyle);        
    	}     
    }   
    public static void setCellTopBorder(int start, int end, HSSFRow row,    HSSFWorkbook wb) {     
    	 HSSFCellStyle cellstyle = wb.createCellStyle();
    	for(int i=start;i<=end;i++){   
    		HSSFCell cell = row.createCell(i);        
    		cell.setCellValue("");        
    		cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
    		cell.setCellStyle(cellstyle);        
    	}     
    }   
    public static void setCellBorderAndVal(int start, int end, HSSFRow row,    HSSFCellStyle style,String val) {     
    	for(int i=start;i<=end;i++){   
    		HSSFCell cell = row.createCell(i);        
    		cell.setCellValue(val);        
    		cell.setCellStyle(style);        
    	}     
    }   
    public void writeTxt(String s1) {
		 try {   
			 String downFile=Constant.CHINA_POLICY_URL+DateUtils.getDateToday()+"//readme.txt";
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
			 br.write(s1);
			 br.close(); 
		 } catch (Exception e) 
		 {   
			 e.printStackTrace();  
		 } 
		 
	}

}
