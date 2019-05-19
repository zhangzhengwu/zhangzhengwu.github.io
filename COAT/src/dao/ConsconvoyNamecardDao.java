package dao;

import java.sql.ResultSet;
import java.util.List;

import entity.Consultant_Master;
import entity.NameCardConvoy;

public interface ConsconvoyNamecardDao {
	/**
	 * 根据StaffNo查询NewMacau
	 */
	public List<Consultant_Master> getConsList(String StaffNo);
	/**
	 * 插入NewCardConvoy
	 * @param rnb
	 * @param reStaffNo
	 * @return
	 */
	public int  saveNewCardConvoy(NameCardConvoy rnb,String reStaffNo);
	/**
	 * 保存newMacau
	 * @param rnb
	 * @return
	 */
	public int  saveNewRequest(NameCardConvoy rnb);
	/**
	 * 插入历史数据
	 * @param rnb
	 * @return
	 */
	public int saveMasterHistory(NameCardConvoy rnb);
	/**
	 * 查询MacauRequest
	 * @param name
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param urgentCase
	 * @return
	 */
	public List<NameCardConvoy> queryRequstList(String name,String code,String startDate,String endDate,String location,String urgentCase);
	/**
	 * 導出Macau數據
	 * @param name
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @param location
	 * @param urgentCase
	 * @return
	 */
	public ResultSet queryRequst(String name,String code,String startDate,String endDate,String location,String urgentCase);
	
	
	/**
	 * 更新RequestNew，但不更新Quantity 普通权限
	 */
	public int updateAdditionals(NameCardConvoy rnb);
	/***
	 * 更新requestNew 并更新Quantity 管理员权限
	 * @param rnb
	 * @return
	 */
		public int updateNumber(NameCardConvoy rnb,String reStaffNo);
	/**
	 * 插入NewMacau对象
	 * @param rnb
	 * @param reStaffNo
	 * @return
	 */
	public int  saveNameCardConvoy(NameCardConvoy rnb,String reStaffNo);
	/**
	 * 更新req_record
	 * @param rnb
	 * @param Payer
	 * @param rePayer
	 * @return
	 */
	public int updateRequestRecord(NameCardConvoy rnb,String Payer, String rePayer);
	/**
	 * 更新历史表
	 * 
	 * @param rnb
	 * @param reStaffNo
	 * @return
	 */
	public int updateMasterNumber(NameCardConvoy rnb, String reStaffNo ); 
	/**
	 * 删除财务表数据
	 * @param name
	 * @param StaffNo
	 * @param AddDate
	 * @return
	 */
	public int deleteChargeRecord(String name,String StaffNo, String AddDate);
	
	/**
	 * 修改审核状态
	 * @return
	 */
 	public int updateConsShzt(String updatename ,String urgentdate,String staffcode, String shzt, String reamrk);
 	/**
 	 * 查询顾问当前card 使用量
 	 * @return
 	 */
 	public String queryConsNum(String code,boolean DD);
 	
 	
 	/**
 	 * 根据table，staffcode，urgentDate 删除数据
 	 * @param table
 	 * @param staffcode
 	 * @param urgentDate
 	 * @return
 	 */
 	public int delNameCard(String table,String staffcode,String urgentDate);
}
