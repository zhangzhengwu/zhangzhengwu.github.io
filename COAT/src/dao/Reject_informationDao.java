package dao;

import java.util.List;

import entity.Reject_information;

public interface Reject_informationDao {
/**
 * 根据相关条件查询回馈用户邮件的内容
 * @param model
 * @param type
 * @return
 */
	public List<Reject_information> queryRejectInformation(String model,String type);
}
