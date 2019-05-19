package util;
public class Constant {
	
	
	
	/**
	 * 邮件服务器
	 */
	public static final String EmailServer="http://localhost/ExchangeMail/SimpleSendMailServlet";
	/**
	 * 邮件服务器授权ID
	 */
	public static final String webappId="COAT";
	/**
	 * 当前tomcat物理路径
	 */
	public static String nowPath="";
	/**
	 * 当前应用物理地址
	 */
	public static String applicationPath="";
	
	
	/**
	 * 2014-05 COAT新状态
	 */
	public static final String C_Submitted = "Submitted";
	public static final String C_hkadm = "HKADM";
	public static final String C_ready = "Ready";
	public static final String C_Completed = "Completed";
	public static final String C_Delivered = "Delivered";
	public static final String C_Returned = "Returned";
	public static final String C_deleted = "Deleted";
	public static final String C_void = "Void";
	public static final String C_Deleted="Deleted";
	
	public static final String RoleType_Consultant="Consultant";//staff角色
	public static final String RoleType_staff="STAFF";//staff角色
	public static final String RoleType_depthead="DEPT";//dept head 角色
	public static final String RoleType_HKADM="HKADM";//香港行政
	public static final String RoleType_hr="HR";//HR
	
	/**
	 * SQL Server 数据库驱动
	 */
	public static String public_connection_driver_class_mssql="";
	/**
	 * SQL Server 数据库链接
	 */
	public static String public_connection_url_mssql="";
	/**
	 * SQL Server 数据库用户名
	 */
	public static String public_connection_username_mssql="";
	/**
	 * SQL Server 数据库密码
	 */
	public static String public_connection_password_mssql="";
	
	/**
	 * MySql 数据库驱动
	 */
	public static String public_connection_driver_class_mysql="";
	/**
	 * MySql 数据库链接
	 */
	public static String public_connection_url_mysql="";
	/**
	 * MySql 数据库用户名
	 */
	public static String public_connection_username_mysql="";
	/**
	 * MySql 数据库密码
	 */
	public static String public_connection_password_mysql="";
	
	
	/**
	 * Oracle 数据库驱动
	 */
	public static String public_connection_driver_class_oracle="";
	/**
	 * Oracle 数据库链接
	 */
	public static String public_connection_url_oracle="";
	/**
	 * Oracle 数据库用户名
	 */
	public static String public_connection_username_oracle="";
	/**
	 * Oracle 数据库密码
	 */
	public static String public_connection_password_oracle="";
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 系统标志代码
	 */
	public static final String YXBZ_Y = "Y";
	public static final String YXBZ_N = "N";
	public static final String YXBZ_S = "S";	//待定
	public static final String YXBZ_D = "D";	//已处理 已匹配
	public static final String YXBZ_ALL = "ALL";
	public static final String YXBZ_EFFE = "effe";
	public static final String YXBZ_SUCCESS = "success";
	public static final String YXBZ_ERROR = "error";
	
	public static final String BLBZ_A = "A";	//均可办理
	public static final String BLBZ_S = "S";
	public static final String BLBZ_C = "C";
	
	public static final String CHINA_POLICY_URL = "////ccnsvr11//SOP//碧升佣金计算//工资清单//";
	
	public static final String ORDER_TYPE_Cons = "Consultant";
	public static final String ORDER_TYPE_Staff = "Staff";
	
	public static final String MarkReport_URL = "\\\\hkgnas11\\OperDept$\\SZ-HK\\ADM/Document to HK-ADM\\Mark Premium\\";
	public static final String MarkReport_URL_CONS = "\\\\hkgnas11\\OperDept$\\SZ-HK\\ADM\\Document to HK-ADM\\Mark Premium\\Consultant\\";
	public static final String MarkReport_URL_STAFF = "\\\\hkgnas11\\OperDept$\\SZ-HK\\ADM\\Document to HK-ADM\\Mark Premium\\Staff\\";
	
	
	
	public static final String ELITETEAMLIST="\\\\hkgnas11\\OperDept$\\SZ-HK\\ADM\\Name Card Request\\Working Files\\";//Elite Team 上传目录
	public static final String Key_file="\\\\BOCNAS11\\Dept\\SZO\\ADM\\Seat Arrangement\\Key for Chicken Box - Master.xls"; 
	public static final String medical_HRD="\\\\BOCNAS11\\Dept\\SZOHRD\\Staff medical claim\\Report\\For HRD\\";
	public static final String medical_Staff="\\\\BOCNAS11\\Dept\\SZOHRD\\Staff medical claim\\Report\\To STAFF\\";
	
//	public static final String StaffNameCardReport="\\\\BOCNAS11\\Dept\\SZO\\ADM\\Name Card request\\To Champion\\EXCEL FILE\\Staff\\Location\\";
//	public static final String EliteTeamReport="\\\\BOCNAS11\\Dept\\SZO\\ADM\\Name Card request\\To Champion\\EXCEL FILE\\Consultant\\EliteTeam\\";
//	public static final String LocationReport="\\\\BOCNAS11\\Dept\\SZO\\ADM\\Name Card request\\To Champion\\EXCEL FILE\\Consultant\\Location\\";
	
	public static final String StaffNameCardReport="\\\\CFSNAS11\\OperDept$\\ADM\\Common\\Name Card Request_2018\\Weekly name card request\\Staff\\Location\\";
	public static final String EliteTeamReport="\\\\CFSNAS11\\OperDept$\\ADM\\Common\\Name Card Request_2018\\Weekly name card request\\Consultant\\EliteTeam\\";
	public static final String LocationReport="\\\\CFSNAS11\\OperDept$\\ADM\\Common\\Name Card Request_2018\\Weekly name card request\\Consultant\\Location\\";
	
//	public static final String MedicalConsultant="\\\\BOCNAS11\\Dept\\SZOProgramming\\Medical Claim\\Consultant\\";
	public static final String MedicalConsultant="\\\\CFSNAS11\\OperDept$\\ADM\\Administrative Assistant\\Medical Claims\\Medical Claim Sent Out Report\\";
//	public static final String MedicalMail="\\\\BOCNAS11\\Dept\\SZO\\ADM\\Report sent out\\Medical claim\\";
	public static final String MedicalMail="\\\\CFSNAS11\\OperDept$\\ADM\\Administrative Assistant\\Medical Claims\\Medical Claim Sent Out Report\\";
//	public static final String MedicalMailLog="\\\\BOCNAS11\\Dept\\SZO\\ADM\\Report sent out\\log\\sendmail_MedialClaim_";
	public static final String MedicalMailLog="\\\\CFSNAS11\\OperDept$\\ADM\\Administrative Assistant\\Medical Claims\\Medical Claim Sent Out Report\\log\\sendmail_MedialClaim_";
	public static final String AttendanceMail="\\\\BOCNAS11\\Dept\\SZO\\ADM\\Report sent out\\Attendance\\";
	public static final String AttendanceMailList="\\\\BOCNAS11\\Dept\\SZO\\ADM\\Report sent out\\Email list.xls";
	public static final String AttendanceMailLog="\\\\BOCNAS11\\Dept\\SZO\\ADM\\Report sent out\\log\\sendmail_Attendance_";
	
	
	/**
	 * ADMINSERVICE 流水号前缀
	 */
	public static final String ADMINSERVICE="AS";
	public static final String PICKUPRECORD="PU";
	public static final String ACCESSCARD="AC";
	public static final String PIBACARD="PIBA";
	public static final String RECRUITMENT="RA";
	public static final String EPAYMENT="EP";
	public static final String SEATCHANGE="SC";
	public static final String SEATAUTOCHANGE="SAC";
	public static final String STATIONERYSEND="STS";
	
	
	
	
	
	
	
	
	
	/*public static final String MarkReport_URL = "d:/COAT/Mark Premium/";
	public static final String MarkReport_URL_CONS = "d:/COAT/Mark Premium/Consultant/";
	public static final String MarkReport_URL_STAFF = "d:/COAT/Mark Premium/Staff/";
*/
 	public static final String StationeryReport_URL = "\\\\hkgnas11\\OperDept$\\SZ-HK/ADM\\Document to HK-ADM\\Stationery\\";
	public static final String StationeryReport_URL_CONS = "////hkgnas11//OperDept$//SZ-HK/ADM//Document to HK-ADM//Stationery//Consultant//";
	public static final String StationeryReport_URL_STAFF = "////hkgnas11//OperDept$//SZ-HK/ADM//Document to HK-ADM//Stationery//Staff//";
	 
 
	/*public static final String StationeryReport_URL = "d:/COAT/Stationery/";
	public static final String StationeryReport_URL_CONS = "d:/COAT/Stationery/Consultant/";
	public static final String StationeryReport_URL_STAFF = "d:/COAT/Stationery/Staff/";*/
 
	/**
	 * 备注
	 */
	public final static String convoy_card_remark="HK Consultant 新增名片！";
	
	//导入Excel文件名
	public final static String CL="Consultant List";//0-15
	public final static String TR="TR Registration";//0-15
	public final static String CNM="Consultants Namecard Master";
	public final static String NQA="Namecard Quota_Additional";
	public final static String STAFFMASTER="Staff Namecard Master"; 
	public final static String QEQ_QECORD="Requisition Record";//0-18
	public final static String MEDICAL="Medical Claim Record";//0-20
	public final static String PROMOTION="Promotion_Checking_Report";// 
	public final static String CIBConsultant="CIB Consultant List";// 
	public final static String C_markeingP="Markeing Premium-stock";// 
	public final static String C_stationery="Stationery_stock";// 
	
	
	
	/***************EMAP*********************/
	public final static String C_Club="C-Club Member";// club 成员
	public final static String Borrow_Records="Card Borrrow Records";//借卡记录 
	public final static String Leave_Records="Leave Records";// 请假记录
	
	public final static String EMAPRECORD="Emap";// 顾问见客表
	public final static String EXCEPTIONAL="Exceptional Date";// 制定假期
	public final static String RESIGN_CONS="terminate&resign consultant list";//离职人员 
	public final static String ATTENDENCE="Attendance Record";// 打卡记录
	public final static String ConsultantList="consultant List";// ConsultantList表
	public final static String TEAINEE_LIST="trainee list";// trainee表
	public final static String OTHCONS_LIST="consultant other";// other cons表 
	
	//CONSTANT name
	public final static String ON_TIME="9:40:00";// 打卡时间基准
	public final static String IS_WEEK="true";// 判断是周六周日，返回 “true”
	public final static String HOLIDAY_NOTIME="0:00:00";// 节假日不需要给时间 
	public final static String LEAVE_TYPE_AL="AL";// 请假类型 AL
	public final static String LEAVE_TYPE_NPL="NPL";// 请假类型 NPL
	public final static String LEAVE_TYPE_STL="STL";// 请假类型 NPL
	public final static String LEAVE_TYPE_SL="SL";// 请假类型 SL
	public final static String LEAVE_TYPE_OTHER="other";// 请假类型 OTHER
	public final static String Training_TYPE="Training";// 指定staffcode以及指定日期内是否有会议
	public final static String MAP_TYPE="MAP";// 指定staffcode以及指定日期内是否有会客
	public final static String LC="LC";//  借卡状态
	public final static String NO_SHOW="No Show";//  无打卡记录
	public final static int hundred=100;	//最多100 的扣费标准 
	public final static String ten_money="10";	//10
	public final static int TEN=10;	//10
	public final static int ZERO=0;	//0
	public final static int SEVEN=7-1;//7
	public final static int FIVE=5;	//5
	public final static String H_CM="H";	//H_CM
	public final static String CONS_MAP_REPORT="MAP Penalty";	//sample-MAP
	public final static String GRADE="CT";//代表试用期内的员工
	public final static String NC="New Join";	//刚入职员工
	
	
	//导出文件名
	public final static String TRAINEE_report="Trainee Leave Deduction";	//导出trainee
	
	
	
	//SHHET name
	public final static String TRAINEE_SHEET="Trainee";// SHEET NAME
	public final static String CLUB_SHEET="Data";// SHEET NAME
	public final static String LEAVE_SHEET="Records";// SHEET NAME
	public final static String BORROW_SHEET="Records";// SHEET NAME
	public final static String QEQ_QECORD_SHEET="Records";// SHEET NAME
	public final static String TR_LIST_SHEET="Licencee Record";// SHEET NAME
	public final static String MEDICAL_SHEET="Data";// SHEET NAME

	
	/***************EMAP************************/
	//name card 基数 400
	public final static int NAMECARD_NUM = 400;
	//Macau NameCard 基数 400
	public final static int MacauNAMECARD_NUM = 400;
	//S 單價
	public final static double S_PRICE = 72.0;
	//P 單價
	public final static double P_PRICE = 165.0;
	/**
	 * Elite Team 100张免费办理限额
	 */
	public final static int Elite_Team=100;
	/**
	 * Standard名片办理价格
	 * 2016-01-20 15:58:54 macy 剔除0.96改成1.06
	 */
	public final static double Standard=1.06;
	/**
	 * Premium名片办理价格
	 */
	public final static double Premium=1.56;
	/**
	 * Standard_EliteTeam名片办理价格
	 * 2015-9-2 16:32:44 jackie提出将1.56改成1.96
	 * 2016-01-20 15:57:22 macy 提出将1.96改成1.56
	 * 
	 */
	public final static double Standard_EliteTeam=1.56;
	/**
	 * Premium_EliteTeam
	 */
	public final static double Premium_EliteTeam=1.96;
	
	/**
	 * Standard加快名片办理价格
	 * 2016-01-20 15:56:22 macy 提出  1.95 改成1.65
	 */
	public final static double Standard_Urgent=1.65;
	/**
	 * Premium加快名片办理价格
	 * 
	 */
	public final static double Premium_Urgent=3.10;
	/**
	 * Standard_EliteTeam加快名片办理价格
	 * 2016-01-20 15:57:55 macy 提出3.10改成2.15
	 */
	public final static double Standard_EliteTeam_Urgent=2.15;
	/**
	 * PremiumEliteTeam加快名片办理价格
	 */
	public final static double Premium_EliteTeam_Urgent=3.90;
	
	
	
	
	
	
	//卡片类型
	public final static String NAMECARD_TYPE = "N";
	//类型SA
	public final static String SA_TYPE = "SA";
	//类型PA
	public final static String PA_TYPE = "PA";
	//类型CA
	public final static String CA_TYPE = "CA";

	//财务表 导出文件 name
	public final static String CHARGEFILE_NAME="Name Card Charge Record";
	//财务表 导出文件 name
	public final static String CHARMACAU_NAME="Macau NameCard Charge Record";
	//Medical 导出文件 name
	public final static String MEDICALFILE_NAME="Medical "+DateUtils.getYearMonth()+"_FAD";
	//Medical 导出文件 FOR CONSULTANT FILE NAME
	public final static String MEDICALCONSULTANT_NAME="Personal Medical Claims report";
	
	/**
	 * 公司名称
	 */
	public final static String CFS="CFS";
	public final static String CAM="CAM";
	public final static String CIS="CIS";
	public final static String CCL="CCL";
	public final static String CFSH="CFSH";
	public final static String CMS="CMS";
	public final static String CFG="CFG";
	public final static String Blank="Blank";
	public final static String CWMC="CWMC";
	/**
	 * location
	 */
	public final static String OIE="OIE";
	public final static String CP3="CP3";
	public final static String CWC="CWC";
	public final static String Macau="Macau";
	public final static String Convoy="@CONVOY";
 
	//promotion checking
	public final static String Tr_Regist="TR Registration List";
	public final static String Promotion_checking="Promotion_Checking_Report";
	public final static String Bvcase_checking="BV_Case_ByConsultant";
 
	public final static String Trainee_checking="Initial Training Records";
 
	
	public static class Message{
		//操作成功
		public final static String CODE_SUCESS="01";
		//操作失败
		public final static String CODE_FAILD="00";
		//保存成功提示信息
		public final static String MESSAGE_SUCCESS="保存成功！";
		//保存失败提示信息
		public final static String MESSAGE_FAILD="保存失败！";
		//关联数据库异常提示信息
		public final static String MESSAGE_SJKYC="数据库异常，请与管理员联系！";
		//数据异常时提示信息
		public final static String MESSAGE_SJYC_SXYM="数据异常，请刷新页面重试！";
		//办结成功提示信息
		//public final static String MESSAGE__BJ_SUCCESS="办结成功！";
		//办结失败提示信息
		//public final static String MESSAGE_BJ_FAILD="办结失败！";
		//查询无结果提示信息
		public final static String MESSAGE_QUERY_NORESULT="无符合查询条件的信息！";
		//查询出错
		public final static String MESSAGE_QUERY_FAILD="查询出错！";
		//删除成功提示信息
		public final static String MESSAGE_DELETE_SUCCESS="删除成功！";
		//删除失败提示信息
		public final static String MESSAGE_DELETE_FAILD="删除失败！"; 
	}
 
}
