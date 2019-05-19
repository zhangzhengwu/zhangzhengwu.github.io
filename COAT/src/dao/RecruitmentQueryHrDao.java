package dao;

import java.util.List;

import util.Page;
import entity.C_Payment;
import entity.C_Recruitment_detail;
import entity.C_Recruitment_list;
import entity.C_Recruitment_order;
import entity.Recruitment_list;

public interface RecruitmentQueryHrDao {

	
	/**
	 * 根据条件查询订单
	 * @param  date、Staff Code、Staff  Name、refno、Status、Order Type、
	 * @return List<C_Recruitment_order>
	 */
	public List<C_Recruitment_order> find(String date1,String date2,C_Recruitment_order order,Page page,String medianame);
	/**
	 * 根据条件查询订单
	 * @param  date、Staff Code、Staff  Name、refno、Status、Order Type、
	 * @return List<C_Recruitment_order>
	 */
	public List<C_Recruitment_order> findRecruitmentList(String date1,String date2,Page page,String medianame);
	
	/**
	 * 查询订单总记录
	 */
	public int selecrRow(String date1, String date2,C_Recruitment_order order,String medianame);
	/**
	 * 查询订单总记录
	 */
	public int selectRow(String date1, String date2,String medianame);
	/**
	 * 删除错误申请
	 */
	public int del(String name, String refno);
	
	public List<C_Recruitment_list> queryMediaName();	
	/**
	 * 根据订单编号查询订单
	 * @param refno
	 * @return C_Recruitment_order
	 */
	public C_Recruitment_order findOrderByNo(String refno);
	
	/**
	 * 根据单号查询订单详情
	 * @param  refno
	 * @return C_Recruitment_detail
	 */
	public C_Recruitment_detail findDetial(String refno);
	
	/**
	 * 根据refno查询付费信息
	 * @param refno
	 * @return C_Payment
	 */
	public C_Payment findCons_listByCode(String code);
	
	 /**
	  * 保存刊登日期
	  * @param date refno
	  * return int 
	  */
   public int upOrderDate(String date,String refno,String name,String to,String cc,String staffname,String date1,String date2,
		   String Person,String emailss,String mediaName);
	 
	 /**
	  * 更新订单状态
	  * @param status
	  * @return int 
	  */
	public int upOrderStatus(String status,String refno,String name,C_Recruitment_order order,C_Recruitment_detail detail);  
	
	/**
	 * 增加支付信息
	 * @paramC_Payment
	 * @return int
	 */
	public  int addpayment(C_Payment payment,String name);
	
	/**
	 * 根据条件导出excel
	 */
	public List<Recruitment_list> exportDate(String date1, String date2,C_Recruitment_order order);
	
	/**
	 * 根据code查询Email 
	 */
	public String[] getEmailByCode(String [] str);
	
}
