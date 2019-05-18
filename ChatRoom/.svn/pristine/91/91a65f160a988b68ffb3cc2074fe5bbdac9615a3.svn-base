package com.orlando.mysql.superdao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/** 
 * @ClassName: SuperDao 
 * @Description: 通用数据访问对象接口
 * @author: 章征武【orlando】
 * @date: 2018年9月17日 下午10:47:02 
 * @tel: 17520490925
 * @email: zhangzw368319@163.com
 * @param <T> 实体类型模板
 */
public interface SuperDao<T> {
	  /**
	   * 新增
	   * 
	   * @param entity
	   *          需要新增的数据封装成实体对象
	   * @return true：新增成功<br/>
	   *         false：新增失败
	   */
	  public boolean add(T entity);

	  /**
	   * 移除
	   * 
	   * @param entity
	   *          需要移除的已持久化的实体对象
	   * @return true：移除成功<br/>
	   *         false：移除失败
	   */
	  public boolean remove(T entity);

	  /**
	   * 移除
	   * 
	   * @param pkId
	   *          需要移除的数据记录的主键
	   * @return true：移除成功<br/>
	   *         false：移除失败
	   */
	  public boolean remove(int pkId);

	  /**
	   * 修改
	   * 
	   * @param entity
	   *          已经修改的游离状态的实体对象
	   * @return true：修改成功<br/>
	   *         false：修改失败
	   */
	  public boolean merge(T entity);

	  /**
	   * 查询<br/>
	   * 根据主键查询
	   * 
	   * @param pkId
	   *          查询条件（主键）
	   * @return T entity：返回查询到的数据，封装成为实体对象。<br/>
	   *         null：未查询到数据返回null。
	   */
	  public T findById(int pkId);

	  /**
	   * 查询<br/>
	   * 查询全部数据
	   * 
	   * @return List<T>：返回查询到的数据，多条数据封装成为泛型类型为实体对象类型的集合。未查询到数据返回集合长度为0。<br/>
	   *         null：查询异常返回null。
	   */
	  public List<T> findAll();

	  /**
	   * 查询<br/>
	   * 根据条件查询
	   * 
	   * @param isFuzzyQuery
	   *          是否模糊查询。true：模糊查询，false：精确查询。
	   * @param example
	   *          查询条件（条件封装成为实体对象）
	   * @return List<T>：返回查询到的数据，多条数据封装成为泛型类型为实体对象类型的集合。未查询到数据返回集合长度为0。<br/>
	   *         null：查询异常返回null。
	   */
	  public List<T> findByExample(boolean isFuzzyQuery, T example);

	  /**
	   * 查询<br/>
	   * 根据查询语句查询
	   * 
	   * @param sql
	   *          SQL语句
	   * @param params
	   *          SQL语句中的匿名参数<br/>
	   *          （可变参数类型，如SQL语句中无参数，则可缺省。）
	   * @return List<T>：返回查询到的数据，多条数据封装成为泛型类型为实体对象类型的集合。未查询到数据返回集合长度为0。<br/>
	   *         null：查询异常返回null。
	   */
	  public List<T> findBySQL(String sql, Object... params);

	  /**
	   * 查询<br/>
	   * 根据查询语句查询
	   * 
	   * @param sql
	   *          SQL语句
	   * @param params
	   *          SQL语句中的命名参数
	   * @return List<T>：返回查询到的数据，多条数据封装成为泛型类型为实体对象类型的集合。未查询到数据返回集合长度为0。<br/>
	   *         null：查询异常返回null。
	   */
	  public List<T> findBySQL(String sql, Map<String, Object> params);

	  /**
	   * 封装对象
	   * 
	   * @param rs
	   *          查询结果，游标已经移动到有数据的行。
	   * @return 实体对象
	   * @throws SQLException
	   *           SQL异常
	   */
	  public T buildObject(ResultSet rs) throws SQLException;

	  /**
	   * 封装集合
	   * 
	   * @param rs
	   *          查询结果，重置游标，确保封装所有记录。
	   * @return 实体对象集合
	   * @throws SQLException
	   *           SQL异常
	   */
	  public List<T> buildList(ResultSet rs) throws SQLException;
	  
	  
	  
	  
	  /**
	 * @Title: closeConnection
	 * @Description:关闭数据库连接
	 * @param     参数
	 * @return void    返回类型
	 * @author: 章征武【orlando】
	 * @date: 2018年10月8日 上午10:23:28  
	 * @tel: 17520490925
	 * @email: zhangzw368319@163.com
	 * @throws
	 */
	public void closeConnection();
	  
	  
}
