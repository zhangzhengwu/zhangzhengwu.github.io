package dao;

import java.sql.ResultSet;
import java.util.List;

import util.Page;
import entity.Medical_Staff_Type;
import entity.Medical_record_staff;
import entity.Medical_record_staff_bak;
import entity.Staff_Master;
import entity.Staff_listBean;

/**
 * 2013年3月5日10:51:16
 * @author kingxu
 * 为staffMedical 获取staff信息
 */
public interface StaffMedicalDao {
/**
 * 根据staffcode查询staff
 * @param staffcode
 * @return
 * 调用处 SelectStaffMedicalServlet line in 37
 */
	public Staff_listBean  selectstaff(String staffcode);
	
	/**
	 * 根据type查询stafftype
	 * @param type
	 * @return
	 * 调用处  SelectStaffMedicalServlet line in 42
	 */
	
	public List<Medical_Staff_Type> selectstaffType(String type);
	/**
	 * 根据staffcode，MedicalDate查询已报销记录
	 * @param staffcode
	 * @param MedicalDate
	 * @return
	 * 调用处 SelectStaffMedicalServlet line in 47
	 */
	public List<Staff_Master> getTypeNumber(String staffcode,String MedicalDate);
	/**
	 * 保存Medical_Record_staff
	 * @param mrs
	 * @return
	 * 调用处 SaveStaffMedicalServlet line in  59
	 */
	public int saveMedical(Medical_record_staff mrs);
	/**
	 * 保存Medical_Record_staff_bak
	 * @param mrs
	 * @return
	 * 调用处 SaveRejectBakServlet line in  59
	 */
	public int saveRejectBak(Medical_record_staff_bak mrs);
	
	/**
	 * 根据staffcode、return_oraginal、StartDate、EndDate查询报销记录 
	 * @param staffcode
	 * @param return_oraginal
	 * @param startDate
	 * @param endDate
	 * @return
	 * 调用处 SelectMedicalStaffServlet line in 
	 */
	public List<Medical_record_staff> selectList(String staffcode,String return_oraginal,String startDate,String endDate,Page page);
/**
 * 根据Id修改Medical_Staff状态
 * @param id
 * @return
 * 调用处 ModifyStaffMedicalServlet line in 58
 */
	public int updateMedical(int id,String upd_date,String username);
	
	/**
	 * 根据staffcode、type、trim更新MedicalStaff
	 * @param type
	 * @param trim
	 * @param staffcode
	 * @return
	 * 调用处 ModifyStaffMedicalServlet line in 
	 */
	public int  updateSpecialUP(String type,int trim,String staffcode,String upd_date,String entitle,String reDental,String MedicalDate);
	
	
	
	/**
	 * 根据staffcode、type、trim更新MedicalStaff
	 * @param type
	 * @param trim
	 * @param staffcode
	 * @return
	 * 调用处 ModifyStaffMedicalServlet line in 
	 */
	public int  updateSpecialDown(String type,int trim,String staffcode,String reEntitle,String reDental,String MedicalDate);
	
/**
 *  根据staffcode、return_oraginal、StartDate、EndDate导出报销记录  
 * @param staffcode
 * @param returnOraginal
 * @param startDate
 * @param endDate
 * @return
 * 调用处 DownMedicalStaffServlet line in 59
 */
	public ResultSet selectResultSet(String staffcode,
			String returnOraginal, String startDate, String endDate);
	/**
	 * 根据staffcode、return_oraginal、StartDate、EndDate导出报销记录 
	 * @param staffcode
	 * @param returnOraginal
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public ResultSet selectAllResultSet(String staffcode,
			String returnOraginal, String startDate, String endDate);
	/**
	 *  根据staffcode、return_oraginal、StartDate、EndDate导出报销记录  
	 * @param staffcode
	 * @param returnOraginal
	 * @param startDate
	 * @param endDate
	 * @return
	 * 调用处 DownMedicalStaffServlet line in 59
	 */
	public ResultSet selectMedicalRjectResultSet(String staffcode,
			String returnOraginal, String startDate, String endDate);
	/**
	 *  根据staffcode、return_oraginal、StartDate、EndDate导出报销记录  
	 * @param staffcode
	 * @param returnOraginal
	 * @param startDate
	 * @param endDate
	 * @return
	 * 调用处 DownMedicalStaffServlet line in 59
	 */
	public ResultSet selectResultSetForFAD(String staffcode,
			String returnOraginal, String startDate, String endDate);
/**
 * 	  根据staffcode、return_oraginal、StartDate、EndDate查询所有Company
 * @param staffcode
 * @param returnOraginal
 * @param startDate
 * @param endDate
 * @return
 * 调用处 DownForFADMedicalStaffServlet line in
 */
	/**
	 * 
	 */
	public List<String> selectCompany(String staffcode,
			String returnOraginal, String startDate, String endDate);
	/**
	 * 	  根据staffcode、return_oraginal、StartDate、EndDate查询所有staffcode
	 * @param staffcode
	 * @param returnOraginal
	 * @param startDate
	 * @param endDate
	 * @return
	 * 调用处 DownMedicalStaffServlet line in
	 */
	
	public List<String> selectStaffcode(String staffcode,
			String returnOraginal, String startDate, String endDate);
	/**
	 * 根据staffcode，upd_date删除数据
	 * @param staffcode
	 * @param upd_date
	 * @return
	 * 调用处 DeleteMedicalStaffServlet line in 39
	 */
	public int deleteMedicalStaff(String staffcode,String upd_date,String username);
	/**
	 * 更新删除成功后的数据
	 * @param staffcode
	 * @param type
	 * @param term
	 * @param medical_Dental
	 * @return
	 * 调用处 DeleteMedicalStaffServlet line in 42
	 */
	public int updateDelte(String staffcode,String type,String term,String medical_Dental,String username);

/**
 *   根据staffcode、return_oraginal、StartDate、EndDate查询报销记录条数 
 * @param staffcode
 * @param returnOraginal
 * @param startDate
 * @param endDate
 * @return
 * 调用处 SelectMedicalStaffservlet line in 
 */
	public int getRows(String staffcode,
			String returnOraginal, String startDate, String endDate);
/**
 * 获取Medical Date 
 * @param staffcode
 * @param medicalDate
 * @return
 */
	public List<String> getType(String staffcode,String medicalDate);
	
	
}
