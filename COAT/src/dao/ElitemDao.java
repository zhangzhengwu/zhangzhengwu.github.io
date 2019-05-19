package dao;

import java.util.List;

public interface ElitemDao {
/**
 * 获取AeConsultant和Elitem标识
 * @param staffcode
 * @return
 * 调用处 VailAEorConsultant line in 33
 */
	public List <String> vailElitemorAE(String staffcode);
	
}
