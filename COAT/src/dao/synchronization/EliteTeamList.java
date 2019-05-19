package dao.synchronization;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import util.Constant;
import util.DBManager;
import util.DateUtils;
import util.ReadExcel;
import util.Util;

public class EliteTeamList {
	Logger logger = Logger.getLogger(EliteTeamList.class);
	
	
	public EliteTeamList() {
	}

public static void main(String[] args) {
	new EliteTeamList().timeTaskLoadEliteTeam();
}
	/**
	 * 定时上传eliteteam--21点
	 * @throws  
	 */
	public String timeTaskLoadEliteTeam(){
		String result="";
		try{
			Util.printLogger(logger,"开始执行指定任务-同步更新EliteTeam");    
			File file=new File(Constant.ELITETEAMLIST);
				String[] test=file.list();
				if(Util.objIsNULL(test)){
					throw new FileNotFoundException(Constant.ELITETEAMLIST+"(找不到网络路径。)");
				}
					
				/**
				 * 2016-1-19 15:57:42
				 * Elite Team Member List 模板文件变更，故读取内容需要调整
				 * 1. Elite Team Member List -->EliteClubMember
				 * 2. ReadExcel.readExcel(new File(Constant.ELITETEAMLIST+test[i]), 0, 3,2)
				 * 	  ReadExcel.readExcel(new File(Constant.ELITETEAMLIST+test[i]), 0, 2,2)
				 */
				for(int i=0;i<test.length;i++){
					if(test[i].indexOf("EliteClubMember")>-1){
						int num=saveEliteTeam(ReadExcel.readExcel(new File(Constant.ELITETEAMLIST+test[i]), 0, 2,2));
						if(num>0){
							result="success";
							Util.printLogger(logger,"指定任务-->同步更新EliteTeam成功!");
						}else{
							Util.printLogger(logger,"指定任务-->同步更新EliteTeam失败，原因：获取远程获取Staff信息时出错!");
							throw new Exception();
						}
						i=test.length;
					}else{
						if(i==test.length-1){
							throw new FileNotFoundException("没有符合要求的文档！");
						}
					}
				}

		}catch(Exception e){
			result=e.getMessage();
			e.printStackTrace();
			Util.printLogger(logger,"同步更新EliteTeam失败："+e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 获取Excel数据进行保存
	 * @param list
	 * @return
	 * 
	 * 2016-1-19 15:59:42 
	 * EliteTeam 模板文件发生变更,原来是读取三列 consultantcode为第1列，consultantname为第2列
	 * 现在只读取两列，consultantcode为第0列，consultantname为第1列
	 * @throws SQLException 
	 * 
	 */
	public int saveEliteTeam(List<List<Object>> list) throws Exception{
		Connection conn=null;
		PreparedStatement ps=null;
		String nowDate=DateUtils.getNowDateTime();
		int num=-1;
				try{
					logger.info("保存EliteTeamList");
					conn=DBManager.getCon();
					conn.setAutoCommit(false);
					String sqls="delete from eliteteam_list";
					ps=conn.prepareStatement(sqls);
					ps.execute();
					sqls="alter table eliteteam_list AUTO_INCREMENT=1;";
					ps=conn.prepareStatement(sqls);
					ps.execute();
					String sql="insert eliteteam_list(staffcode,staffname,state,remark1,remark2)values(?,?,?,?,?);";
					ps=conn.prepareStatement(sql);
					for(int j=0;j<list.size();j++){
						List<Object> list2=list.get(j);
						//System.out.println(Arrays.toString(list2.toArray()));
						ps.setString(1, list2.get(0).toString());
						ps.setString(2, list2.get(1).toString());
						ps.setString(3, "Y");
						ps.setString(4, "System");
						ps.setString(5, nowDate);
						ps.addBatch();
					}
					ps.executeBatch();
					conn.commit();
					num=list.size();
					list=null;
				}catch (Exception e) {
					e.printStackTrace();
					try {
						logger.error("保存EliteTeamList 出现异常=="+e.getMessage()+"，正在进行数据回滚...");
						conn.rollback();
					} catch (SQLException e1) {
						logger.error("ElitemTeamList数据回滚异常==="+e1.getMessage());
					}
					throw new Exception(e);
				}finally{
					DBManager.closeCon(conn);
					nowDate=null;
					
				}
		
		return num;
	}
 
	
}
