package dao;
/**
 * @author younggao
 * 功能：招聘廣告申請 
 */
import java.io.InputStream;
import java.util.List;

import util.Page;

import entity.C_Recruitment_detail;
import entity.C_Recruitment_list;
import entity.C_Recruitment_order;
import entity.Cons_listBean;

public interface RecruimentDao {
     /**
      * 查找所有產品
      * @return C_Recruitment_list
      */
	public List<C_Recruitment_list>  findRecruitment();
	
	/**
	 * 根据当前登录用户的code，获取状态不为complete的订单信息
	 * @param code
	 * @return List
	 */
     public List<C_Recruitment_order> findOrderByCode(String code);
     
     /**
      * 根据用户订单状况查询产品单号
      * @param List<C_Recruitment_order>
      * @return List<C_Recruitment_detail>
      */
	public List<C_Recruitment_detail> findDetialByOrder(List<C_Recruitment_order> orders);
	
	/**
	 * 增加订单和订单详情信息
	 * @param C_Recruitment_order
	 * @return int 
	 */
	public boolean addOrder(C_Recruitment_order order,C_Recruitment_list list,String refno);
	
	public int saveRecuritment(String filename, InputStream os);
	/**
	 * 生成流水号
	 */
	public String getNo();
	/**
	 * 根据产品code查询产品对象
	 * @param code
	 * @return C_Recruitment_list
	 */
	public C_Recruitment_list findListByCode(String code);
	/**
	 * 根据Staff Code获取Consultant Name
	 */
	public String  findConsultantName(String code);
	
	/**
	 * 根据Staffcode 获取Name 和Position
	 */
	public Cons_listBean getPosition(String code);
	
	/**
	 * 自动扫描Recruitment Adversiting 模块没有修改status为Comfirmation Request的申请,并修改状态.
	 * @return
	 * @throws Exception 
	 */
	public int ScanningRecruitmentAdversiting() throws Exception;
	
	/**
	 * 根据条件查找产品
	 */
	public List<C_Recruitment_list> findByConditions(String code,String name,String type,String date,String date1,Page page);
	
	/**
	 *  商品总记录
	 */
	public int recordCount(String code,String name,String type,String date,String date1);
	
	/**
	 * 读取上传文件并更新数据库表数据的信息(C_recruitment_list)
	 */
    public int  saveOrUplist(List<List<Object>> list,String name);
}
