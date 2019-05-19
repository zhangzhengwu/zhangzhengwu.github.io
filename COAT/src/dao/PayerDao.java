package dao;

import entity.Change_Macau;
import entity.Change_Record;
/**
 * 财务查询
 * @author kingxu
 *
 */
public interface PayerDao {
	/**
	 * 
	 * @param payer
	 * @return
	 */
	public String GetEnglishName(String payer);
	/**
	 * 验证帮她办理名片的权限
	 * @param payer
	 * @return
	 */
	public String GetPosition(String payer);
	/**
	 * 获取名片印刷支付数量
	 * @param payer
	 * @return
	 */
	public int payerNumber(String payer) throws Exception;
	
	/**
	 * 查询添加限额数量
	 * @param StaffNo
	 * @return 
	 */
	public int getadd(String StaffNo);
	/**
	 * 获取Consultant使用限额
	 * @param StaffNo
	 * @return
	 */
	public int getused(String StaffNo);
	
	/**
	 * 获取DD使用数量
	 * @param StaffNo
	 * @return
	 */
	public int getDDused(String StaffNo);
	
	/**
	 * 保存财务数据
	 * @param cr
	 */
	public void saveChange(Change_Record cr);
	/**
	 * deletePayer
	 * @param name
	 * @param StaffNo
	 * @param Date
	 */
	public void deletePayer(String name, String StaffNo, String Date);
	/**
	 * 验证payer是否存在
	 * @param payer
	 * @param user
	 * @return
	 */
	public String VailPayer(String payer,String user);
	/**
	 * 保存Macau财务数据
	 * @param cr
	 */
	public void saveMacauChange(Change_Macau cr) ;
	/**
	 * 删除Macau财务数据
	 * @param name
	 * @param StaffNo
	 * @param Date
	 */
	public void deleteMacauPayer(String name, String StaffNo, String Date);
	
	
}
