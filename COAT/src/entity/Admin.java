package entity;

/*本实体javabean对应数据库中的管理员信息表 *
CREATE TABLE IF NOT EXISTS `admin` (
		  `adminUsername` varchar(50) NOT NULL,		/*用户名*
		  `adminPassword` varchar(50) NOT NULL,		/*密码*
		  `isRoot` int(11) NOT NULL					/*是否是超级用户
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