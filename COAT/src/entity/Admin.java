package entity;

/*��ʵ��javabean��Ӧ���ݿ��еĹ���Ա��Ϣ�� *
CREATE TABLE IF NOT EXISTS `admin` (
		  `adminUsername` varchar(50) NOT NULL,		/*�û���*
		  `adminPassword` varchar(50) NOT NULL,		/*����*
		  `isRoot` int(11) NOT NULL					/*�Ƿ��ǳ����û�
		) ENGINE=MyISAM DEFAULT CHARSET=latin1;
*/
public class Admin {
	private String adminUsername;
	private String adminPassword;
	private int isRoot;
	
	
	
	
	
	public Admin() {
	}
	public Admin(String adminUsername, String adminPassword, int isRoot) {
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
		this.isRoot = isRoot;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public String getAdminUsername() {
		return adminUsername;
	}
	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}
	public int getIsRoot() {
		return isRoot;
	}
	public void setIsRoot(int isRoot) {
		this.isRoot = isRoot;
	}
}