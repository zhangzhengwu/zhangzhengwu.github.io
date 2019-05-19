package dao.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.DBManager;
import util.Util;
import dao.GetChinaSimpleDao;
import entity.ChinaConslist;
import entity.ChinaPolicy;
import entity.ChinaPolicyRate;
import entity.ChinaPolicyTeam;
import entity.ChinaPolicyTutorship;
/**
 * ChinaSimple 实现类
 * @author Wilson
 *
 */
public class GetChinaSimpleDaoImpl implements GetChinaSimpleDao {
	PreparedStatement ps = null;
	String sql = "";
	Connection con = null;
	Logger logger = Logger.getLogger(GetChinaSimpleDaoImpl.class);
	/**
	 * 保存上传信息
	 */
	public int saveSimple(String filename, InputStream os) {
		int num=0;
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("china_conslist"); 
		try {
			con = DBManager.getCon();
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheet("代理人");// 获取sheet name
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				/**获取Excel里面的指定单元格数据**/
				  
				HSSFCell staffnamecell = row.getCell(0);
				HSSFCell addreesscell = row.getCell(1);
				HSSFCell salesOfficecell = row.getCell(2);
				HSSFCell brithCell = row.getCell(3);
				HSSFCell idCardNoCell = row.getCell(4);
				HSSFCell agentNumberCell = row.getCell(5);
				HSSFCell resignTypeCell = row.getCell(6);
				HSSFCell titleCell = row.getCell(7);
				HSSFCell titleOkdateCell = row.getCell(8);
				HSSFCell phoneCell = row.getCell(9);
				HSSFCell joinDateCell = row.getCell(10);
				HSSFCell engagingDateCell = row.getCell(11);
				HSSFCell coachCell = row.getCell(12);
				HSSFCell recommendCell=row.getCell(13);
				
				HSSFCell agentNoCell=row.getCell(14);
				HSSFCell agentSdateCell =row.getCell(15);
				HSSFCell agentEdateCell=row.getCell(16);
				HSSFCell licenseNoCell=row.getCell(17);
				HSSFCell undertakingCell=row.getCell(18);
				HSSFCell consumerNoDateCell=row.getCell(19);
				HSSFCell bankAccountCell=row.getCell(20);

				ChinaConslist conslist = new ChinaConslist();
				/**给数据库里面的字段赋值**/
				conslist.setStaffname(Util.cellToString(staffnamecell));
				conslist.setAddreess(Util.cellToString(addreesscell));
				conslist.setSalesOffice(Util.cellToString(salesOfficecell));
				conslist.setBrith(Util.cellToString(brithCell));
				conslist.setIdCardNo(Util.cellToString(idCardNoCell));
				conslist.setAgentNumber(Util.cellToString(agentNumberCell));
				conslist.setResignType(Util.cellToString(resignTypeCell));
				conslist.setTitle(Util.cellToString(titleCell));
				conslist.setTitleOkdate(Util.cellToString(titleOkdateCell));
				conslist.setPhone(Util.cellToString(phoneCell));
				conslist.setJoinDate(Util.cellToString(joinDateCell));
				conslist.setEngagingDate(Util.cellToString(engagingDateCell));
				conslist.setCoach(Util.cellToString(coachCell));
				conslist.setRecommend(Util.cellToString(recommendCell));
				conslist.setAgentNo(Util.cellToString(agentNoCell));
				conslist.setAgentSdate(Util.cellToString(agentSdateCell));
				conslist.setAgentEdate(Util.cellToString(agentEdateCell));
				conslist.setLicenseNo(Util.cellToString(licenseNoCell));
				conslist.setUndertaking(Util.cellToString(undertakingCell));
				conslist.setConsumerNoDate(Util.cellToString(consumerNoDateCell));
				conslist.setBankAccount(Util.cellToString(bankAccountCell));
				
				if(!Util.objIsNULL(Util.cellToString(staffnamecell))){
					String accountString = conslist.getBankAccount().length()<3?"":conslist.getBankAccount();
					sql = "insert china_conslist (staffname,addreess,salesOffice,brith,idCardNo,agentNumber," +
							"resignType,title,titleOkdate,phone,joinDate,engagingDate,coach,recommend,agentNo," +
							"agentSdate,agentEdate,licenseNo,undertaking,consumerNoDate,bankAccount) values('"+
							conslist.getStaffname()+"','"+
							conslist.getAddreess()+"','"+
							conslist.getSalesOffice()+"','"+
							conslist.getBrith()+"','"+
							conslist.getIdCardNo()+"','"+
							conslist.getAgentNumber()+"','"+
							conslist.getResignType()+"','"+
							conslist.getTitle()+"','"+
							conslist.getTitleOkdate()+"','"+
							conslist.getPhone()+"','"+
							conslist.getJoinDate()+"','"+
							conslist.getEngagingDate()+"','"+
							conslist.getCoach()+"','"+
							conslist.getRecommend()+"','"+
							conslist.getAgentNo()+"','"+
							conslist.getAgentSdate()+"','"+
							conslist.getAgentEdate()+"','"+
							conslist.getLicenseNo()+"','"+
							conslist.getUndertaking()+"','"+
							conslist.getConsumerNoDate()+"','"+
							accountString+"')";
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入china_conslist 表成功！");
						num++;
					} else {
						logger.info("插入china_conslist 表失敗");
					}
				}
			}
			InputStream sos = new FileInputStream(filename);
			//导入China Policy 发佣明细表
			int uploadPolicy = uploadPolicy(con,filename,sos);
			if(uploadPolicy > 0){
				num+=uploadPolicy;
				//导入China Policy Rate 佣金率表
				InputStream ros = new FileInputStream(filename);
				con = DBManager.getCon();
				int uploadPolicyRate = uploadPolicyRate(con, filename, ros);
				if(uploadPolicyRate > 0){
					num+=uploadPolicyRate;
					//导入China Policy team 团队签单
					InputStream tos = new FileInputStream(filename);
					con = DBManager.getCon();
					int uploadPolicyTeam = uploadPolicyTeam(con, filename, tos);
					if(uploadPolicyTeam > 0){
						num+=uploadPolicyTeam;
						//导入China tutorship 辅导推介
						InputStream tusos = new FileInputStream(filename);
						con = DBManager.getCon();
						int uploadPolicyTutorship = uploadPolicyTutorship(con, filename, tusos);
						if(uploadPolicyTutorship > 0){
							num+=uploadPolicyTutorship;
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入china_conslist 表异常！"+e);
			return num;
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	}
	/**
	 * 导入 Policy  发佣明细
	 * @param con
	 * @param filename
	 * @param os
	 * @return
	 */
	public int uploadPolicy(Connection con,String filename, InputStream os) {
		int num=0;
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("china_policy"); 
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheet("发佣明细");// 获取sheet name
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				/**获取Excel里面的指定单元格数据**/
				HSSFCell companycell = row.getCell(0);
				HSSFCell policyNoell = row.getCell(1);
				HSSFCell productcell = row.getCell(2);
				HSSFCell clientCell = row.getCell(3);
				HSSFCell paymentCell = row.getCell(4);
				HSSFCell termCell = row.getCell(5);
				HSSFCell effectiveCell = row.getCell(6);
				HSSFCell premiumCell = row.getCell(7);
				HSSFCell fby_valueCell = row.getCell(8);
				HSSFCell b_valueCell = row.getCell(9);
				HSSFCell TraineeCell = row.getCell(10);
				HSSFCell FYBCell = row.getCell(11);
				
				HSSFCell staffnameCell=row.getCell(14);
				HSSFCell salesOfficeCell =row.getCell(15);
				HSSFCell handledTitleCell=row.getCell(16);
				HSSFCell coachCell=row.getCell(17);
				HSSFCell insuredCell=row.getCell(18);
				HSSFCell acceptedDateCell=row.getCell(19);
				HSSFCell receiptDateCell=row.getCell(20);
				
				HSSFCell policyTypeCell=row.getCell(21);
				HSSFCell RemarkCell=row.getCell(22);
				HSSFCell proxyTwoCell=row.getCell(23);
				HSSFCell acceptedMonthCell=row.getCell(24);
				HSSFCell PayrollMonthCell=row.getCell(25);
				HSSFCell commissionDateCell=row.getCell(26);
				HSSFCell irTypeCell=row.getCell(27);
				HSSFCell policyYearCell=row.getCell(28);

				ChinaPolicy policy = new ChinaPolicy();
				/**给数据库里面的字段赋值**/
				policy.setCompany(Util.cellToString(companycell));
				policy.setPolicyNo(Util.cellToString(policyNoell));
				policy.setProduct(Util.cellToString(productcell));
				policy.setClient(Util.cellToString(clientCell));
				policy.setPayment(Util.cellToString(paymentCell));
				policy.setTerm(Util.cellToString(termCell));
				policy.setEffective(Util.cellToString(effectiveCell));
				policy.setPremium(Util.cellToString(premiumCell));
				policy.setFby_value(Util.cellToString(fby_valueCell));
				policy.setB_value(Util.cellToString(b_valueCell));
				policy.setTrainee(Util.cellToString(TraineeCell));
				policy.setFYB(Util.cellToString(FYBCell));
				policy.setStaffname(Util.cellToString(staffnameCell));
				policy.setSalesOffice(Util.cellToString(salesOfficeCell));
				policy.setHandledTitle(Util.cellToString(handledTitleCell));
				policy.setCoach(Util.cellToString(coachCell));
				policy.setInsured(Util.cellToString(insuredCell));
				policy.setAcceptedDate(Util.cellToString(acceptedDateCell));
				policy.setReceiptDate(Util.cellToString(receiptDateCell));
				policy.setPolicyType(Util.cellToString(policyTypeCell));
				policy.setRemark(Util.cellToString(RemarkCell));
				policy.setProxyTwo(Util.cellToString(proxyTwoCell));
				policy.setAcceptedMonth(Util.cellToString(acceptedMonthCell));
				policy.setPayrollMonth(Util.cellToString(PayrollMonthCell));
				policy.setCommissionDate(Util.cellToString(commissionDateCell));
				policy.setIrType(Util.cellToString(irTypeCell));
				policy.setPolicyYear(Util.cellToString(policyYearCell));
				
				if(!Util.objIsNULL(Util.cellToString(companycell))){
				 
					sql = "insert china_policy (company,policyNo,product,client,payment,term,effective,premium,fby_value," +
							"b_value,Trainee,FYB,staffname,salesOffice,handledTitle,coach,insured,acceptedDate,receiptDate,policyType," +
							"Remark,proxyTwo,acceptedMonth,PayrollMonth,commissionDate,irType,policyYear) values('"+
							policy.getCompany()+"','"+
							policy.getPolicyNo()+"','"+
							policy.getProduct()+"','"+
							policy.getClient()+"','"+
							policy.getPayment()+"','"+
							policy.getTerm()+"','"+
							policy.getEffective()+"','"+
							policy.getPremium()+"','"+
							policy.getFby_value()+"','"+
							policy.getB_value()+"','"+
							policy.getTrainee()+"','"+
							policy.getFYB()+"','"+
							policy.getStaffname()+"','"+
							policy.getSalesOffice()+"','"+
							policy.getHandledTitle()+"','"+
							policy.getCoach()+"','"+
							policy.getInsured()+"','"+
							policy.getAcceptedDate()+"','"+
							policy.getReceiptDate()+"','"+
							policy.getPolicyType()+"','"+
							policy.getRemark()+"','"+
							policy.getProxyTwo()+"','"+
							policy.getAcceptedMonth()+"','"+
							policy.getPayrollMonth()+"','"+
							policy.getCommissionDate()+"','"+
							policy.getIrType()+"','"+
							policy.getPolicyYear()+"')";
					
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入c china_policy 表成功！");
						num++;
					} else {
						logger.info("插入china_policy  表失敗");
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入china_policy 表异常！"+e);
			return num;
		} finally {
			//关闭连接
			DBManager.closeCon(con);
		}
		return num;
	
	}
	/**
	 * 导入 Policy  Rate 佣金率
	 * @param con
	 * @param filename
	 * @param os
	 * @return
	 */
	public int uploadPolicyRate(Connection con,String filename, InputStream os) {
		int num=0;
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("china_policy_rate"); 
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheet("佣金率");// 获取sheet name
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				/**获取Excel里面的指定单元格数据**/
				HSSFCell companycell = row.getCell(0);
				HSSFCell policyNoell = row.getCell(1);
				HSSFCell productcell = row.getCell(2);
				HSSFCell clientCell = row.getCell(3);
				HSSFCell paymentCell = row.getCell(4);
				HSSFCell termCell = row.getCell(5);
				HSSFCell effectiveCell = row.getCell(6);
				HSSFCell premiumCell = row.getCell(7);
				HSSFCell rateCell = row.getCell(8);
				HSSFCell rate2Cell = row.getCell(9);
				
				HSSFCell traineeCell = row.getCell(10);
				HSSFCell FYBCell = row.getCell(11);
				HSSFCell commissionCell = row.getCell(12);
				
				HSSFCell staffnameCell = row.getCell(14);
				HSSFCell salesOfficeCell = row.getCell(15);
				
				HSSFCell  handledTitleCell=row.getCell(16);
				HSSFCell  coachCell=row.getCell(17);
				HSSFCell insuredCell=row.getCell(18);
				HSSFCell acceptedDateCell=row.getCell(19);
				HSSFCell receiptDateCell=row.getCell(20);
				
				HSSFCell policyTypeCell=row.getCell(21);
				HSSFCell RemarkCell=row.getCell(22);
				HSSFCell proxyTwoCell=row.getCell(23);
				HSSFCell acceptedMonthCell=row.getCell(24);
				HSSFCell PayrollMonthCell=row.getCell(25);
				HSSFCell commissionDateCell=row.getCell(26);
				HSSFCell irTypeCell=row.getCell(27);
				HSSFCell policyYearCell=row.getCell(28);
				HSSFCell licenseNoCell=row.getCell(29);
				HSSFCell idCardNoCell=row.getCell(30);
				HSSFCell bankAccountCell=row.getCell(31);

				ChinaPolicyRate policy = new ChinaPolicyRate();
				/**给数据库里面的字段赋值**/
				policy.setCompany(Util.cellToString(companycell));
				policy.setPolicyNo(Util.cellToString(policyNoell));
				policy.setProduct(Util.cellToString(productcell));
				policy.setClient(Util.cellToString(clientCell));
				policy.setPayment(Util.cellToString(paymentCell));
				policy.setTerm(Util.cellToString(termCell));
				policy.setEffective(Util.cellToString(effectiveCell));
				policy.setPremium(Util.cellToString(premiumCell));
				policy.setRate(Util.cellToString(rateCell));
				policy.setRate2(Util.cellToString(rate2Cell));
				policy.setTrainee(Util.cellToString(traineeCell));
				policy.setFYB(Util.cellToString(FYBCell));
				policy.setCommission(Util.cellToString(commissionCell));
				policy.setStaffname(Util.cellToString(staffnameCell));
				policy.setSalesOffice(Util.cellToString(salesOfficeCell));
				policy.setHandledTitle(Util.cellToString(handledTitleCell));
				policy.setCoach(Util.cellToString(coachCell));
				policy.setInsured(Util.cellToString(insuredCell));
				policy.setAcceptedDate(Util.cellToString(acceptedDateCell));
				policy.setReceiptDate(Util.cellToString(receiptDateCell));
				policy.setPolicyType(Util.cellToString(policyTypeCell));
				policy.setRemark(Util.cellToString(RemarkCell));
				policy.setProxyTwo(Util.cellToString(proxyTwoCell));
				policy.setAcceptedMonth(Util.cellToString(acceptedMonthCell));
				policy.setPayrollMonth(Util.cellToString(PayrollMonthCell));
				policy.setCommissionDate(Util.cellToString(commissionDateCell));
				policy.setIrType(Util.cellToString(irTypeCell));
				policy.setPolicyYear(Util.cellToString(policyYearCell));
				
				policy.setLicenseNo(Util.cellToString(licenseNoCell));
				policy.setIdCardNo(Util.cellToString(idCardNoCell));
				policy.setBankAccount(Util.cellToString(bankAccountCell));
				
				
				if(!Util.objIsNULL(Util.cellToString(companycell))){
				 
					sql = "insert china_policy_rate (company,policyNo,product,client,payment,term,effective," +
							"premium,rate,rate2,Trainee,FYB,commission,staffname,salesOffice,handledTitle,coach,insured,acceptedDate," +
							"receiptDate,policyType,Remark,proxyTwo,acceptedMonth,PayrollMonth," +
							"commissionDate,irType,policyYear,licenseNo,idCardNo,bankAccount) values('"+
							policy.getCompany()+"','"+
							policy.getPolicyNo()+"','"+
							policy.getProduct()+"','"+
							policy.getClient()+"','"+
							policy.getPayment()+"','"+
							policy.getTerm()+"','"+
							policy.getEffective()+"','"+
							policy.getPremium()+"','"+
							policy.getRate()+"','"+
							policy.getRate2()+"','"+
							policy.getTrainee()+"','"+
							policy.getFYB()+"','"+
							policy.getCommission()+"','"+
							policy.getStaffname()+"','"+
							policy.getSalesOffice()+"','"+
							policy.getHandledTitle()+"','"+
							policy.getCoach()+"','"+
							policy.getInsured()+"','"+
							policy.getAcceptedDate()+"','"+
							policy.getReceiptDate()+"','"+
							policy.getPolicyType()+"','"+
							policy.getRemark()+"','"+
							policy.getProxyTwo()+"','"+
							policy.getAcceptedMonth()+"','"+
							policy.getPayrollMonth()+"','"+
							policy.getCommissionDate()+"','"+
							policy.getIrType()+"','"+
							policy.getPolicyYear()+"','"+
							policy.getLicenseNo()+"','"+
							policy.getIdCardNo()+"','"+
							policy.getBankAccount()+"')";
					
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入c china_policy_rate 表成功！");
						num++;
					} else {
						logger.info("插入china_policy_rate  表失敗");
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入china_policy_rate 表异常！"+e);
			return num;
		} finally { 
			DBManager.closeCon(con);
		}
		return num;
	
	}

	/**
	 * 导入 Policy Team 团险
	 * @param con
	 * @param filename
	 * @param os
	 * @return
	 */
	public int uploadPolicyTeam(Connection con,String filename, InputStream os) {
		int num=0;
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("china_policy_team"); 
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheet("团险");// 获取sheet name
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				/**获取Excel里面的指定单元格数据**/
				HSSFCell companycell = row.getCell(0);
				HSSFCell policyNoell = row.getCell(1);
				HSSFCell productcell = row.getCell(2);
				HSSFCell clientunitCell = row.getCell(3);
				HSSFCell paymentCell = row.getCell(4);
				HSSFCell termCell = row.getCell(5);
				HSSFCell premiumCell = row.getCell(6);
				HSSFCell rateCell= row.getCell(7);
				HSSFCell commissionCell= row.getCell(8);
				HSSFCell comiFeeRateCell = row.getCell(9);
				HSSFCell comiFeeCell = row.getCell(10);
				HSSFCell businessCell = row.getCell(11);
				HSSFCell staffnameCell=row.getCell(12);
				HSSFCell salesOfficeCell=row.getCell(13);

				ChinaPolicyTeam policy = new ChinaPolicyTeam();
				/**给数据库里面的字段赋值**/
				policy.setCompany(Util.cellToString(companycell));
				policy.setPolicyNo(Util.cellToString(policyNoell));
				policy.setProduct(Util.cellToString(productcell));
				policy.setClientUnit(Util.cellToString(clientunitCell));
				policy.setPayment(Util.cellToString(paymentCell));
				policy.setTerm(Util.cellToString(termCell));
				policy.setPremium(Util.cellToString(premiumCell));
				policy.setRate(Util.cellToString(rateCell));
				policy.setCommission(Util.cellToString(commissionCell));
				policy.setComiFeeRate(Util.cellToString(comiFeeRateCell));
				policy.setComiFee(Util.cellToString(comiFeeCell));
				policy.setBusiness(Util.cellToString(businessCell));
				policy.setStaffname(Util.cellToString(staffnameCell));
				policy.setSalesOffice(Util.cellToString(salesOfficeCell));
				 
				if(!Util.objIsNULL(Util.cellToString(companycell))){
				 
					sql = "insert china_policy_team (company,policyNo,product,clientUnit," +
							"payment,term,premium,rate,commission,comiFeeRate,comiFee,business," +
							"staffname,salesOffice) values('"+
							policy.getCompany()+"','"+
							policy.getPolicyNo()+"','"+
							policy.getProduct()+"','"+
							policy.getClientUnit()+"','"+
							policy.getPayment()+"','"+
							policy.getTerm()+"','"+
							policy.getPremium()+"','"+
							policy.getRate()+"','"+
							policy.getCommission()+"','"+
							policy.getComiFeeRate()+"','"+
							policy.getComiFee()+"','"+
							policy.getBusiness()+"','"+
							policy.getStaffname()+"','"+
							policy.getSalesOffice()+"')";
					
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入c china_policy_team 表成功！");
						num++;
					} else {
						logger.info("插入china_policy_team  表失敗");
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入china_policy_team 表异常！"+e);
			return num;
		} finally { 
			DBManager.closeCon(con);
		}
		return num;
	
	}

	
	/**
	 * 导入 Policy china_policy_tutorship 团险
	 * @param con
	 * @param filename
	 * @param os
	 * @return
	 */
	public int uploadPolicyTutorship(Connection con,String filename, InputStream os) {
		int num=0;
		int beginRowIndex = 1;// 开始读取数据的行数
		int totalRows = 0;// 总行数
		Util.deltables("china_policy_tutorship"); 
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(os);
			HSSFSheet sheet = workbook.getSheet("辅导推介");// 获取sheet name
			totalRows = sheet.getLastRowNum();// 获取总行数
			for (int i = beginRowIndex; i <= totalRows; i++) {
				HSSFRow row = sheet.getRow(i);//读取Excel里面的i行
				/**获取Excel里面的指定单元格数据**/
				HSSFCell idCardNocell = row.getCell(0);
				HSSFCell resignTypecell = row.getCell(1);
				HSSFCell agentNoCell = row.getCell(2);
				HSSFCell licenseNoCell = row.getCell(3);
				HSSFCell salesOfficeCell = row.getCell(4);
				HSSFCell staffnameCell = row.getCell(5);
				HSSFCell titleCell = row.getCell(6);
				HSSFCell fybTotalCell= row.getCell(7);
				
				HSSFCell coachOneCell= row.getCell(8);
				HSSFCell payOneCell = row.getCell(9);
				HSSFCell kouOneCell = row.getCell(10);
				HSSFCell coachTitleOneCell = row.getCell(11);
				HSSFCell promotionOneCell=row.getCell(12);
				HSSFCell promotionPayOneCell=row.getCell(13);
				HSSFCell promotionOneTitleCell = row.getCell(14);
				
				HSSFCell coachTwoCell = row.getCell(15);
				HSSFCell payTwoCell = row.getCell(16);
				HSSFCell kouTwoCell=row.getCell(17);
				HSSFCell coachTitleTwoCell=row.getCell(18);
				HSSFCell promotionTwoCell=row.getCell(19);
				HSSFCell promotionPayTwoCell=row.getCell(20);
				HSSFCell promotionTwoTitleCell = row.getCell(21);
				
				HSSFCell coachThreeCell = row.getCell(22);
				HSSFCell payThreeCell=row.getCell(23);
				HSSFCell kouThreeCell=row.getCell(24);
				HSSFCell coachTitleThreeCell=row.getCell(25);

				ChinaPolicyTutorship policy = new ChinaPolicyTutorship();
				/**给数据库里面的字段赋值**/
				policy.setIdCardNo(Util.cellToString(idCardNocell));
				policy.setResignType(Util.cellToString(resignTypecell));
				policy.setAgentNo(Util.cellToString(agentNoCell));
				policy.setLicenseNo(Util.cellToString(licenseNoCell));
				policy.setSalesOffice(Util.cellToString(salesOfficeCell));
				policy.setStaffname(Util.cellToString(staffnameCell)); 
				policy.setTitle(Util.cellToString(titleCell));
				policy.setFybTotal(Util.cellToString(fybTotalCell));
				policy.setCoachOne(Util.cellToString(coachOneCell));
				policy.setPayOne(Util.cellToString(payOneCell));
				policy.setKouOne(Util.cellToString(kouOneCell));
				policy.setCoachTitleOne(Util.cellToString(coachTitleOneCell));
				policy.setPromotionOne(Util.cellToString(promotionOneCell));
				policy.setPromotionPayOne(Util.cellToString(promotionPayOneCell));
				policy.setPromotionOneTitle(Util.cellToString(promotionOneTitleCell));
				policy.setCoachTwo(Util.cellToString(coachTwoCell));
				policy.setPayTwo(Util.cellToString(payTwoCell));
				policy.setKouTwo(Util.cellToString(kouTwoCell));
				policy.setCoachTitleTwo(Util.cellToString(coachTitleTwoCell));
				policy.setPromotionTwo(Util.cellToString(promotionTwoCell));
				policy.setPromotionPayTwo(Util.cellToString(promotionPayTwoCell));
				policy.setPromotionTwoTitle(Util.cellToString(promotionTwoTitleCell));
				policy.setCoachThree(Util.cellToString(coachThreeCell));
				policy.setPayThree(Util.cellToString(payThreeCell));
				policy.setKouThree(Util.cellToString(kouThreeCell));
				policy.setCoachTitleThree(Util.cellToString(coachTitleThreeCell));
				 
				if(!Util.objIsNULL(Util.cellToString(idCardNocell))){
				 
					sql = "insert china_policy_tutorship (idCardNo,resignType,agentNo,licenseNo,salesOffice,staffname," +
							"title,fybTotal,coachOne,payOne,kouOne,coachTitleOne,promotionOne,promotionPayOne," +
							"promotionOneTitle,coachTwo,payTwo,kouTwo,coachTitleTwo,promotionTwo,promotionPayTwo," +
							"promotionTwoTitle,coachThree,payThree,kouThree,coachTitleThree) values('"+
							policy.getIdCardNo()
							+"','"+policy.getResignType()
							+"','"+policy.getAgentNo()
							+"','"+policy.getLicenseNo()
							+"','"+policy.getSalesOffice()
							+"','"+policy.getStaffname()
							+"','"+policy.getTitle()
							+"','"+policy.getFybTotal()
							+"','"+policy.getCoachOne()
							+"','"+policy.getPayOne()
							+"','"+policy.getKouOne()
							+"','"+policy.getCoachTitleOne()
							+"','"+policy.getPromotionOne()
							+"','"+policy.getPromotionPayOne()
							+"','"+policy.getPromotionOneTitle()
							+"','"+policy.getCoachTwo()
							+"','"+policy.getPayTwo()
							+"','"+policy.getKouTwo()
							+"','"+policy.getCoachTitleTwo()
							+"','"+policy.getPromotionTwo()
							+"','"+policy.getPromotionPayTwo()
							+"','"+policy.getPromotionTwoTitle()
							+"','"+policy.getCoachThree()
							+"','"+policy.getPayThree()
							+"','"+policy.getKouThree()
							+"','"+policy.getCoachTitleThree() +"')";
					
					ps = con.prepareStatement(sql);
					int rsNum = ps.executeUpdate();
					if (rsNum > 0) {
						logger.info("插入c china_policy_tutorship 表成功！");
						num++;
					} else {
						logger.info("插入china_policy_tutorship  表失敗");
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("插入china_policy_tutorship 表异常！"+e);
			return num;
		} finally {
			DBManager.closeCon(con);
		}
		return num;
	
	}
	public ResultSet getFristComi(String staffname, String salesOffice,
			String other) {
		ResultSet rs= null;
		//--对应"发佣明细"中"首期/续期"为"首"且保单年度为"1"的记录
		try{
			StringBuffer sal=new StringBuffer("select company,policyno,product,client,payment,term,effective,premium,fby_value as FYB,Trainee" +
					" from china_policy where staffname ='"+staffname+"' and salesOffice='"+
					salesOffice+"' and irtype='首' and policyyear ='1' ");
			 
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			rs=ps.executeQuery();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//DBManager.closeCon(con); 导出不能关闭连接
		}
		return rs;
	}
	public ResultSet getFristRate(String staffname, String salesOffice,
			String other) {
		ResultSet rs= null;
		//--对应"佣金率"中的记录
		try{
			StringBuffer sal=new StringBuffer("select company,policyno,product,client,payment,term,effective,premium,rate,Trainee from " +
					"china_policy_rate where staffname ='"+staffname+"' and salesOffice='"+salesOffice+"'  ");
			 
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			rs=ps.executeQuery();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//DBManager.closeCon(con); 导出不能关闭连接
		}
		return rs;
	}
	
	public ResultSet getPolicyTeam(String staffname, String salesOffice,
			String other) {
		ResultSet rs= null;
		//- 团险
		try{
			StringBuffer sal=new StringBuffer("select company,policyno,product,clientunit,payment,term," +
					"'' as effective,premium,rate from china_policy_team where staffname ='"+
					staffname+"' and salesoffice = '"+salesOffice+"' ");
			 
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			rs=ps.executeQuery();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//DBManager.closeCon(con); 导出不能关闭连接
		}
		return rs;
	}
	public ResultSet getTwoComi(String staffname, String salesOffice,
			String other) {
		ResultSet rs= null;
		//--对应"发佣明细"中"首期/续期"为"续"且保单年度不为"1"的记录		
		//--"首期/续期"中的"继","续服"记录无须导出至此表		
		try{
			StringBuffer sal=new StringBuffer("select company,policyno,product,client,payment,term,effective,premium,fby_value as FYB from " +
					" china_policy where staffname ='"+staffname+"' and salesOffice='"+salesOffice+"' and irtype='续' and policyyear !='1'  ");
			 
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			rs=ps.executeQuery();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//DBManager.closeCon(con); 导出不能关闭连接
		}
		return rs;
	}
	public ResultSet getPolicyCoach(String staffname, String title,
			String other) {
		ResultSet rs= null;
		//"辅导津贴"对应"辅导推介"中的"第一辅导人"的"发"
		//"推介津贴"对应"辅导推介"中的"第一推荐人"			
		try{
			StringBuffer sal=new StringBuffer("select a.staffname,substring(c.agentNumber,length(c.agentNumber)-3,length(c.agentNumber)) as licenseNo," +
						"a.title,fybTotal ,'' as pice,coachMoney , '' as pice2, promotionone,'' AS last from ("+
					" select staffname,salesOffice,title,fybTotal, payone as coachMoney,'' as promotionone  from china_policy_tutorship"+
					" where coachone ='"+staffname+"'  and coachtitleone ='"+title+"' and kouone =''"+ 
				" union all"+
					" select staffname,salesOffice,title,fybTotal, '' as coachMoney ,promotionpayone as promotionone "+
					" from china_policy_tutorship where promotionone ='"+staffname+"' and promotiononetitle='"+title+"'"+
					" ) a LEFT join china_conslist c on a.staffname = c.staffname and a.salesOffice = c.salesOffice  ");
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			rs=ps.executeQuery();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//DBManager.closeCon(con); 导出不能关闭连接
		}
		return rs;
	}
	public ResultSet getPolicyCoachTwo(String staffname, String title,
			String other) {
		ResultSet rs= null;
		//--"辅导津贴"对应"辅导推介"中的"第二辅导人"和"第三辅导人"的"发"		
		//--"推介津贴"对应"辅导推介"中的"第二推介人"		
		//--记录对应"第二辅导人",则代为"1"对应的管理津贴为3.5(若有),推介津贴为1.5(若有)		
		//--记录对应"第三辅导人",则代为"2"	
		try{
			StringBuffer sal=new StringBuffer("select a.staffname,substring(c.agentNumber,length(c.agentNumber)-3,length(c.agentNumber)) as licenseNo,a.title, a.type, a.fybTotal, a.payTwo, a.promotiontwo,a.coachthree,'' last from ("+
					" select staffname,salesOffice,title,1 as type,fybTotal,payTwo ,'' as promotiontwo, '' coachthree from china_policy_tutorship where coachtwo ='"+staffname+"' and coachtitletwo='"+title+"' and kouTwo =''"+
				" union all"+
					"  select staffname,salesOffice,title,1 as type,fybTotal,'' as payTwo ,promotionpaytwo as promotiontwo, '' coachthree from china_policy_tutorship where promotionTwo ='"+staffname+"' and promotionTwoTitle='"+title+"'"+
				" union all"+
					" select staffname,salesOffice,title,2 as type,fybTotal ,'' as payTwo ,'' as promotiontwo, paythree coachthree from china_policy_tutorship where coachthree ='"+staffname+"'  and coachtitlethree='"+title+"' and kouThree =''"+
				" ) a LEFT join china_conslist c on a.staffname = c.staffname and a.salesOffice = c.salesOffice ");
			con=DBManager.getCon();
			ps = con.prepareStatement(sal.toString());
			rs=ps.executeQuery();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			//DBManager.closeCon(con); 导出不能关闭连接
		}
		return rs;
	}
	public List<ChinaPolicy> getPolicyGroup() {
		ResultSet resultSet =null;
		List<ChinaPolicy> cplist = new ArrayList<ChinaPolicy>();
//		String sql = "select staffname,salesOffice from china_policy group by staffname,salesOffice";
		String sql = "select staffname,salesOffice, title as title from ( " + //concat('碧升',
					"select staffname,salesOffice,'' as title from china_policy group by staffname,salesOffice  " +
				 	"union  " +
				 	"select staffname,salesOffice,'' as title from china_policy_rate group by staffname,salesOffice   " +
			        "union  " +
				 	"select coachOne,'',coachTitleOne as title from china_policy_tutorship group by coachOne,coachTitleOne   " +
			       " union  " +
				 	"select promotionOne,'',promotionOneTitle as title from china_policy_tutorship group by promotionOne,promotionOneTitle  " + 
			       " union  " +
				 	"select coachTwo,'',coachTitleTwo as title from china_policy_tutorship group by coachTwo,coachTitleTwo    " +
			       " union  " +
				 	"select promotionTwo,'',promotionTwoTitle as title from china_policy_tutorship group by promotionTwo,promotionTwoTitle   " +
			       " union  " +
				 	"select coachThree,'',coachTitleThree as title from china_policy_tutorship group by coachThree,coachTitleThree  " +
			 " ) a  group by staffname ";
		
		try {
			con = DBManager.getCon();
			ps = con.prepareStatement(sql);
			resultSet = ps.executeQuery();
			
			while (resultSet.next()) {
				ChinaPolicy cp = new ChinaPolicy();
				cp.setStaffname(resultSet.getString("staffname"));
				cp.setSalesOffice(resultSet.getString("salesOffice"));
				cp.setRemark(resultSet.getString("title"));
				
				cplist.add(cp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}finally{
			DBManager.closeCon(con);  
		}
		return cplist;
	}
	public ChinaConslist getStaffList(String staffname, String salesOffice,
			String other) {

		ChinaConslist peoBean = new ChinaConslist();
		try {
			con = DBManager.getCon();
			String sqlString ="select * from china_conslist where staffname =? and salesOffice like ? and title like ? limit 0,1";
			logger.info("查询 china_conslist by a "+staffname+" SQL:"+sqlString);
			ps = con.prepareStatement(sqlString);
			ps.setString(1,staffname.trim());
			ps.setString(2,"%"+salesOffice.trim()+"%");
			ps.setString(3,"%"+other.trim()+"%");
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				peoBean.setId(rs.getString(1));
				peoBean.setStaffname(rs.getString(2));
				peoBean.setAddreess(rs.getString(3));
				peoBean.setSalesOffice(rs.getString(4));
				peoBean.setBrith(rs.getString(5));
				peoBean.setIdCardNo(rs.getString(6));
				peoBean.setAgentNumber(rs.getString(7));
				peoBean.setResignType(rs.getString(8));
				peoBean.setTitle(rs.getString(9));
				peoBean.setTitleOkdate(rs.getString(10));
				peoBean.setPhone(rs.getString(11));
				peoBean.setJoinDate(rs.getString(12));
				peoBean.setEngagingDate(rs.getString(13));
				peoBean.setCoach(rs.getString(14));
				peoBean.setRecommend(rs.getString(15));
				peoBean.setAgentNo(rs.getString(16));
				peoBean.setAgentSdate(rs.getString(17));
				peoBean.setAgentEdate(rs.getString(18));
				peoBean.setLicenseNo(rs.getString(19));
				peoBean.setUndertaking(rs.getString(20));
				peoBean.setConsumerNoDate(rs.getString(21));
				peoBean.setBankAccount(rs.getString(22));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("查询 ChinaConslist异常！"+e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("查询 ChinaConslist异常！"+e);
		} finally{
			DBManager.closeCon(con);  
		}
		return peoBean;
	}
}