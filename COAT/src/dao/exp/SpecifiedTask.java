package dao.exp;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import util.DateUtils;
import util.Util;

import com.coat.pickuprecord.dao.PickUpRecordDao;
import com.coat.pickuprecord.dao.impl.PickUpRecordDaoImpl;


public class SpecifiedTask extends TimerTask {
	Logger logger = Logger.getLogger(SpecifiedTask.class);
//	private   static int C_AdminService=0;		//凌晨00点，自动扫描AdminService-->AllExtension状态
//	private   static int C_StaffNameCard=1;		//凌晨00点，自动扫描StaffNameCard-->approve状态
//	private   static int  C_Key_HOUR       = 6; //更新keys 6
//	private   static int  C_SCHEDULE_HOUR       =7; //更新Consultant 7
//	private   static int  C_SCHEDULE_STAFF_HOUR       =  8;//同步Staff
//	private   static int C_Recruitment=17;//下午17点，自动扫描Recruitment Advertising状态
//	private   static int C_MISSING_HOUR=20;//晚上20点自动发送Missing payment 通知邮件
//	private   static int C_EliteTeam_HOUR=21;//晚上九点定时更新EliteTeam 21
//	private   static int  C_CLEAR_HOUR       =  22; //凌晨删除临时文件夹 22
//	private   static int  C_NameCard_HOUR       =  23; //傍晚导出文件夹 23
	
	private   static boolean  isRunning   =   false;    
	private   ServletContext  context   =   null;    
	private InetAddress addr=null;


	public SpecifiedTask(ServletContext   context){    
		this.context= context;    
		
	}
	@Override
	public void run()  {
		try{
			addr = InetAddress.getLocalHost();
			Calendar   cal   =   Calendar.getInstance();    
			System.gc();
			//System.out.println(addr.getHostAddress()+"--"+DateUtils.getNowDateTime());
			 
			 
			
			
			if (!isRunning && ("false".equalsIgnoreCase(Util.getProValue("public.system.uat")))){  
				//定时上传香港ip phone列表--0点
//				if(Util.getProValue("SpecifiedTask.namecard.extension.hour").equals(cal.get(Calendar.HOUR_OF_DAY)+"")){//自动上传AllExtension文件
//					isRunning   =   true;
//					int num=-1;
//					Util.printContext_Logger(logger, context,"开始执行指定任务00点-->扫描AdminService-->AllExtension状态 并处理状态");    
//					try{	
//						AdminServiceDao adminServiceDao=new AdminServiceDaoImpl();
//						num=	adminServiceDao.uploadAllExtension();
//						if(num>=0){
//							Util.printContext_Logger(logger, context,"执行指定任务00点-->扫描扫描AdminService-->AllExtension状态 并处理状态成功");    
//						}
//					}catch (Exception e) {
//						Util.printContext_Logger(logger, context,"执行指定任务00点-->扫描扫描AdminService-->AllExtension状态 并处理状态 失败"+e.toString());
//						e.printStackTrace();
//					}finally{
//						isRunning   =  false; 
//					}
//				}
				//定时扫描staffnamecar发出approve通知邮件后，七日后未操作者再次提醒-1点
//				if(Util.getProValue("SpecifiedTask.namecard.staff.approve.hour").equals(cal.get(Calendar.HOUR_OF_DAY)+"")){
//					isRunning   =   true;
//					int num=-1;
//					context.log("开始执行指定任务01点-->扫描StaffNameCard-->approve状态 并处理状态");    
//					logger.info("开始执行指定任务01点-->扫描StaffNameCard-->approve状态 并处理状态");  
//					try{	
//						StaffNameCardDao nameCardDao=new StaffNameCardDaoImpl();
//						num=nameCardDao.approvePrompt();
//						if(num>=0){
//							context.log("执行指定任务01点-->扫描扫描StaffNameCard-->approve状态 并处理状态成功");    
//							logger.info("执行指定任务01点-->扫描扫描StaffNameCard-->approve状态 并处理状态成功");  
//						}
//
//					}catch (Exception e) {
//						Util.printContext_Logger(logger, context,"执行指定任务01点-->扫描扫描StaffNameCard-->approve状态 并处理状态 失败"+e.toString());
//						e.printStackTrace();
//					}finally{
//						isRunning=false; 
//					}
//				}
				
				//定时上传keylist-6点
//				if(Util.getProValue("SpecifiedTask.namecard.keylist.hour").equals(cal.get(Calendar.HOUR_OF_DAY)+"")){
//					int num=0;
//					try{
//						isRunning   =   true;                                    
//						Util.printContext_Logger(logger, context,"开始执行指定任务6点-同步更新Keys history");    
//						InputStream os = new FileInputStream(Constant.Key_file);
//						C_KeyDao md=new C_KeyDaoImpl();
//						num=md.saveKeys(Constant.Key_file, os);
//						if(num>0){
//							Util.printContext_Logger(logger, context,"指定任务-->6点-同步更新Keys history成功!");
//						}else{
//							Util.printContext_Logger(logger, context,"指定任务-->6点-同步更新Keys history失败!");
//						}
//					}catch(Exception e){
//						e.printStackTrace();
//						Util.printContext_Logger(logger, context,"6 am同步更新Keys history失败："+e.toString());
//
//					}finally{
//						isRunning   =   false;    
//					}
//				}
				//定时更新Consultant List-7点
//				if(Util.getProValue("SpecifiedTask.namecard.consultantlist.hour").equals(cal.get(Calendar.HOUR_OF_DAY)+"")){          
//					isRunning   =   true;                                    
//					Util.printContext_Logger(logger, context, "开始执行指定任务-Consultant_list");
//					try{
//						/*GetCLDao gc=new GetCLDaoImpl();
//						gc.saveConsultant(gc.getAllConsList());*/
//						ConsultantListDao consultantDao=new ConsultantListDaoImpl();
//						List<ConsultantList> list=consultantDao.findConsultantByHKView();
//						consultantDao=new ConsultantListDaoImpl();
//						consultantDao.batchSaveConsultant(list, "Uploader");
//						Util.printContext_Logger(logger, context, "指定任务-Consultant_list-->同步成功!");
//					} catch(Exception e){
//						Util.printContext_Logger(logger, context, "指定任务-Consultant_list-->同步失败!"+e.getMessage());
//					}finally{
//						isRunning   =   false;    
//					}
//				}
				//定时同步香港stafflist-8点
//				if(Util.getProValue("SpecifiedTask.namecard.stafflist.hour").equals(cal.get(Calendar.HOUR_OF_DAY)+"")){
//					try{
//						isRunning   =   true;                                    
//						Util.printContext_Logger(logger, context,"开始执行指定任务-同步Staff信息");    
//						GetCLDao gc=new GetCLDaoImpl();
//						List<Staff_listBean> list=gc.getAllStaff();
//						if(list.size()>0){
//							gc.saveStaff(list);
//							list=null;
//							Util.printContext_Logger(logger, context,"指定任务-->同步Staff成功!");
//						}else{
//							Util.printContext_Logger(logger, context,"指定任务-->同步失败，原因：获取远程获取Staff信息时出错!");
//						}    
//					}catch(Exception e){
//						e.printStackTrace();
//						Util.printContext_Logger(logger, context,"指定任务-->同步Staff信息失败："+e.toString());
//
//					}finally{
//						isRunning   =   false;    
//					}
//				}
				
				//定时扫描recruitment并修改状态-17点
//				if(Util.getProValue("SpecifiedTask.namecard.recruitment.hour").equals(cal.get(Calendar.HOUR_OF_DAY)+"")){//扫描recruitment模块
//					isRunning   =   true;
//					int num=-1;
//					Util.printContext_Logger(logger, context,"开始执行指定任务17点-->扫描Recruitment Advertising状态 并处理状态");    
//					try{	
//						RecruimentDao rDao=new RecuritmentDaoImpl();
//						num=rDao.ScanningRecruitmentAdversiting();
//						if(num>=0){
//							Util.printContext_Logger(logger, context,"执行指定任务17点-->扫描Recruitment Advertising状态 并处理状态成功");    
//						}else{
//							Util.printContext_Logger(logger, context,"执行指定任务17点-->扫描Recruitment Advertising状态 并处理状态失败");    
//						}
//					}catch (Exception e) {
//						Util.printContext_Logger(logger, context,"执行指定任务17点-->扫描Recruitment Advertising状态 并处理状态 失败"+e.toString());
//						e.printStackTrace();
//					}finally{
//						isRunning   =  false; 
//					}
//				}
				//定时查询missingpayment并发送通知邮件--20点
//				if(Util.getProValue("SpecifiedTask.namecard.missingpayment.hour").equals(cal.get(Calendar.HOUR_OF_DAY)+"")){
//					isRunning   =   true;
//					int num=-1;
//					Util.printContext_Logger(logger, context,"开始执行指定任务13点-Missingpayment 查询");    
//					try{	
//						MissingPaymentDao cg=new MissingPaymentDaoImpl(); 
//						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//						num=cg.findByTime(sdf.format(new Date()));//"2014-10-20"
//						if(num>=0){
//							Util.printContext_Logger(logger, context,"开始执行指定任务20点-Missingpayment查询 并插入数据成功");    
//						}else{
//							Util.printContext_Logger(logger, context,"开始执行指定任务20点-Missingpayment查询 并插入数据失败");    
//						}
//					}catch (Exception e) {
//						Util.printContext_Logger(logger, context,"执行指定任务20点-Missingpayment 查询 失败"+e.toString());
//						e.printStackTrace();
//					}finally{
//						isRunning   =  false; 
//					}
//				}
				
				//定时上传eliteteam--21点
//				if(Util.getProValue("SpecifiedTask.namecard.eliteteam.hour").equals(cal.get(Calendar.HOUR_OF_DAY)+"")){
//					try{
//						isRunning   =   true;                                    
//						Util.printContext_Logger(logger, context,"开始执行指定任务-同步更新EliteTeam");    
//						EliteTeamList teamList=new EliteTeamList();
//						File file=new File(Constant.ELITETEAMLIST);
//						String[] test=file.list();
//						/**
//						 * 2016-1-19 15:57:42
//						 * Elite Team Member List 模板文件变更，故读取内容需要调整
//						 * 1. Elite Team Member List -->EliteClubMember
//						 * 2. ReadExcel.readExcel(new File(Constant.ELITETEAMLIST+test[i]), 0, 3,2)
//						 * 	  ReadExcel.readExcel(new File(Constant.ELITETEAMLIST+test[i]), 0, 2,2)
//						 */
//						
//						for(int i=0;i<test.length;i++){
//							if(test[i].indexOf("EliteClubMember")>-1){
//								int num=teamList.saveEliteTeam(ReadExcel.readExcel(new File(Constant.ELITETEAMLIST+test[i]), 0, 2,2));
//								if(num>0){
//									Util.printContext_Logger(logger, context,"指定任务-->同步更新EliteTeam成功!");
//								}else{
//									Util.printContext_Logger(logger, context,"指定任务-->同步更新EliteTeam失败，原因：获取远程获取Staff信息时出错!");
//								}
//								i=test.length;
//							}
//						}
//					}catch(Exception e){
//						e.printStackTrace();
//						Util.printContext_Logger(logger, context,"同步更新EliteTeam失败："+e.toString());
//					}finally{
//						isRunning   =   false;    
//					}
//				}
			
					//系统定时备份并清理temp文件--22点
//					if(Util.getProValue("SpecifiedTask.namecard.clear_backup.hour").equals(cal.get(Calendar.HOUR_OF_DAY)+"")){
//						try{
//							isRunning   =   true;                                    
//							Util.printContext_Logger(logger, context,"开始执行指定任务-转移备份临时文件");    
//							FileUtil.copyFolderContext(context.getRealPath("")+"\\upload\\temp\\", Util.getProValue("upload.temp.backup.filepath"));
//							Util.printContext_Logger(logger, context,"开始执行指定任务-转移备份临时文件成功!");    
//							Util.printContext_Logger(logger, context,"-------------------------");    
//							Util.printContext_Logger(logger, context,"开始执行指定任务-删除临时文件");    
//							FileUtil.deleteAll(context.getRealPath("")+"\\upload\\temp\\");
//							Util.printContext_Logger(logger, context,"指定任务-->删除临时文件成功!");
//						}catch(Exception e){
//							e.printStackTrace();
//							Util.printContext_Logger(logger, context,"指定任务-->删除临时文件失败："+e.toString());
//						}finally{
//							isRunning   =   false;    
//						}
//					}
				
					//定时导出NameCard 报表给HK 邮件服务器使用--23点
//					if(Util.getProValue("SpecifiedTask.namecard.export.hour").equals(cal.get(Calendar.HOUR_OF_DAY)+"")){//定时导出Namecard Excel
//						try{
//							isRunning   =   true;                                    
//							Util.printContext_Logger(logger, context, "开始执行指定任务-导出NameCardExcel文件");
//							SpecifiedExport sp=new SpecifiedExport();
//							sp.specifiledExport();
//							Util.printContext_Logger(logger, context, "指定任务-->导出Namecard文件成功!");
//						}catch(Exception e){
//							e.printStackTrace();
//							Util.printContext_Logger(logger, context, "指定任务-->定时导出Namecard出现-->"+e.getMessage());
//						}finally{
//							isRunning   =   false;    
//						}
//					}
					//Pick Up Document-->定时邮件提醒(周一、周四)
					if(Util.getProValue("SpecifiedTask.pickUp.email.hour").equals(cal.get(Calendar.HOUR_OF_DAY)+"")){
						if(Util.getProValue("SpecifiedTask.pickUp.email.week").contains(DateUtils.getWeek(DateUtils.getDateToday()))){
							isRunning   =   true;
							int num=-1;
							Util.printContext_Logger(logger, context,"开始执行指定任务00点-pick up 邮件提醒");    
							try{	
								PickUpRecordDao dao=new PickUpRecordDaoImpl();
								num=dao.p_sendEmail();
								if(num>=0){
									Util.printContext_Logger(logger, context,"开始执行指定任务00点-pick up 邮件提醒-->成功");    
								}else{
									Util.printContext_Logger(logger, context,"开始执行指定任务00点-pick up 邮件提醒-->失败");    
								}
							}catch (Exception e) {
								Util.printContext_Logger(logger, context,"执行指定任务00点-pick up 邮件提醒  失败"+e.toString());
								e.printStackTrace();
							}finally{
								isRunning   =  false; 
							}
						}
					}
					/**
					 * 远程数据库中获取<顾问上级信息>，并且保存到本地数据库
					 */
//					if(Util.getProValue("SpecifiedTask.staffnamecard.supervisor.hour").equals(cal.get(Calendar.HOUR_OF_DAY)+"")){
//						isRunning   =   true;
//						int num=-1;
//						Util.printContext_Logger(logger, context,"开始执行指定任务06点-staffNameCard 获取顾问上级信息");    
//						try{	
//							StaffNameCardDao nameCardDao=new StaffNameCardDaoImpl();
//							num=nameCardDao.getSupervisor();
//							if(num>=0){
//								Util.printContext_Logger(logger, context,"开始执行指定任务06点-staffNameCard 获取顾问上级信息-->成功");    
//							}else{
//								Util.printContext_Logger(logger, context,"开始执行指定任务06点-staffNameCard 获取顾问上级信息-->失败");    
//							}
//						}catch (Exception e) {
//							Util.printContext_Logger(logger, context,"执行指定任务06点-staffNameCard 获取顾问上级信息 失败"+e.toString());
//							e.printStackTrace();
//						}finally{
//							isRunning   =  false; 
//						}
//					}
					/**
					 * 远程数据库中获取<公司信息>，并且保存到本地数据库
					 */
//					if(Util.getProValue("SpecifiedTask.staffnamecard.company.hour").equals(cal.get(Calendar.HOUR_OF_DAY)+"")){
//						isRunning   =   true;
//						int num=-1;
//						Util.printContext_Logger(logger, context,"开始执行指定任务06点-staffNameCard 获取公司信息");    
//						try{	
//							StaffNameCardDao nameCardDao=new StaffNameCardDaoImpl();
//							num=nameCardDao.getForm_Company();
//							if(num>=0){
//								Util.printContext_Logger(logger, context,"开始执行指定任务06点-staffNameCard 获取公司信息-->成功");    
//							}else{
//								Util.printContext_Logger(logger, context,"开始执行指定任务06点-staffNameCard 获取公司信息-->失败");    
//							}
//						}catch (Exception e) {
//							Util.printContext_Logger(logger, context,"执行指定任务06点-staffNameCard 获取公司信息 失败"+e.toString());
//							e.printStackTrace();
//						}finally{
//							isRunning   =  false; 
//						}
//					}
			}else{
				if("false".equalsIgnoreCase(Util.getProValue("public.system.uat"))){
					Util.printContext_Logger(logger, context,"指定任务-->上一次任务执行还未结束");    
				}else{
					Util.printContext_Logger(logger, context,"指定任务-->"+addr.getHostAddress()+"-->該服務器不允許運行定時任務");    
				}
			}   
		}catch (Exception e) {
			e.printStackTrace();
			Util.printContext_Logger(logger, context,"指定任務--->出現異常-->"+e.getMessage());
		}finally{
			if(isRunning){
				isRunning   =  false; 
			}
		}
	}
}
