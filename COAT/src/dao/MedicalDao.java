package dao;

import java.util.List;

import util.Page;
import entity.Medical;
import entity.Medical_Consultant;

public interface MedicalDao {

	public int addMedical(Medical medical);
	
//	public List<Medical> queryByStaffNo(String StaffNo,String MedicalDate);
	public List<Medical> queryByStaffNo_New(String StaffNo,String MedicalDate);
//	public List<Medical> findByStaffCode(String StaffNo);
	public List<Medical> findByStaffCode_New(String StaffNo);
	public int motifyMedical(String Special,String updName,String StaffNo, String MedicalFee, String MedicalDate,String Amount,
			String updDate);
	public List<Medical> queryMedicalConsultant(String start_date,String end_date,String Staff_Code,String FullName,Page page);
	public int VailMedical(String StaffNo,String MedicalDate,String Special);
	public int update(String StaffNo,String update,String upd_name);
	public void upNormal(String StaffNo,String upd_date,String used ,String upd_name);
	public void upSpecial(String StaffNo,String upd_date,String used,String upd_name);
	public int addMedical_Consultant(Medical_Consultant medical);
	public List<Medical_Consultant> queryMedical_Consultant(String startDate, String endDate,
			String code, String name);
	public void updateMedicalState_Y(String staffcode,String add_Date);
	public void updateMedicalState_J(String staffcode,String add_Date,String remark);
	/**
	 * 根据staffcode获取Email
	 * @param staffcode
	 * @return
	 * 调用处 QueryEmailByConsultant line in 30
	 */
	public List<String> QueryEmailByConsultant(String staffcode);
	
	/**
	 * 获取行数
	 * @param startDate
	 * @param endDate
	 * @param code
	 * @param name
	 * @return
	 * 调用处 QueryMedicalConsultantServlet line in 45
	 */
	public int getRow(String startDate, String endDate,
			String code, String name);
	
}
