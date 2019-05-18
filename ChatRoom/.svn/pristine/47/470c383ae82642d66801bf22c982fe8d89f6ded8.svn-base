package com.orlando.mysql.superdao.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @ClassName: DbUtil 
 * @Description: super DbUtil
 * @author: 章征武【orlando】
 * @date: 2018年9月17日 下午10:57:28 
 * @tel: 17520490925
 * @email: zhangzw368319@163.com 
 */
public class DbUtil {
	  private String serverName = "127.0.0.1";
	  private int port = 3306;
	  private String databaseName = "chatroom";
	  private String charset = "utf-8";

	  private String className = "com.mysql.jdbc.Driver";
	  private String url = "jdbc:mysql://" + this.serverName + ":" + this.port + "/" + this.databaseName + "?characterEncoding=" + this.charset;
	  private String user = "root";
	  private String pwd = "root";

	  private Connection con = null;

	  /**
	   * 主方法，用于测试。
	   * 
	   * @param args
	   */
	  public static void main(String[] args) {
	    DbUtil dbUtil = new DbUtil();
	    try {
	      ResultSet rs = dbUtil.doQuery("show databases;");
	      System.out.println("直接输出结果集：");
	      DbUtil.showResultSet(rs);
	      System.out.println("格式化标准输出：");
	      DbUtil.isYourShowTime(rs);
	      dbUtil.close();
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	  }

	  {
	    // 静态块，配置日志记录器
	    DbUtil.configLogger();
	  }

	  /**
	   * 通过日志配置文件，重新配置日志记录器，以记录/输出指定级别的日志信息
	   */
	  private static void configLogger() {
	    try {
	      Logger log = Logger.getGlobal();
	      log.setLevel(Level.ALL);// 记录/输出配置文件路径
	      log.setLevel(Level.OFF);// 不记录/输出配置文件路径
	      File file = new File("config/logging.properties");
	      if (file.exists()) {
	        log.info("logging.properties配置文件路径：" + file.getAbsolutePath());
	        System.out.println("logging.properties配置文件路径：" + file.getAbsolutePath());
	        LogManager.getLogManager().readConfiguration(new FileInputStream(file));
	      }

	      log.setLevel(Level.ALL);// 记录/输出配置文件路径
	      log.setLevel(Level.OFF);// 不记录/输出配置文件路径

	      log.config("Logger测试开始");
	      log.severe("Level.SEVERE");
	      log.warning("Level.WARNING");
	      log.info("Level.INFO");
	      log.config("Level.CONFIG");
	      log.fine("Level.FINE");
	      log.finer("Level.FINER");
	      log.finest("Level.FINEST");
	      log.config("Logger测试结束");
	    } catch (SecurityException | IOException e) {
	      e.printStackTrace();
	    }
	  }

	  /**
	   * 默认连接本机MySQL服务器test数据库，用户root，密码root。<br/>
	   * 可以通过ClassPath环境变量路径（通常是src文件夹路径）下的DbUtil.properties文件配置数据库连接属性。<br/>
	   * <h2>DbUtil.properties文件内容示例：</h2>
	   * 
	   * <font color="blue">#配置JDBC连接属性</font><br/>
	   * className = com.mysql.jdbc.Driver<br/>
	   * url = jdbc:mysql://localhost:3306/test?characterEncoding=utf-8<br/>
	   * user = root<br/>
	   * pwd = <br/>
	   * <br/>
	   * <font color="red">#配置以下属性会重构url属性，导致上面的url属性失效</font><br/>
	   * serverName = 127.0.0.1<br/>
	   * port = 3306<br/>
	   * databaseName = mysql<br/>
	   * charset = utf-8<br/>
	   */
	  public DbUtil() {
//	    InputStream is = DbUtil.class.getResourceAsStream("/DbUtil.properties");
//	    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("DbUtil.properties");
	    InputStream is = DbUtil.class.getClassLoader().getResourceAsStream("DbUtil.properties");
	    Properties prop = System.getProperties();
	    for (Enumeration<?> e = prop.propertyNames(); e.hasMoreElements();) {
	      String k = e.nextElement().toString();
	      Logger.getAnonymousLogger().finest(k + ":" + prop.getProperty(k));
	    }
	    prop = new Properties();
	    try {
	      prop.load(is);
	      this.className = prop.getProperty("className", "com.mysql.jdbc.Driver");
	      this.url = prop.getProperty("url", "jdbc:mysql://localhost:3306/test?characterEncoding=utf-8");
	      this.user = prop.getProperty("user", "root");
	      this.pwd = prop.getProperty("pwd", "orlando");
	      if (prop.getProperty("serverName") != null) {
	        this.serverName = prop.getProperty("serverName");
	        this.buildUrl();
	      }
	      if (prop.getProperty("port") != null) {
	        this.port = Integer.parseInt(prop.getProperty("port"));
	        this.buildUrl();
	      }
	      if (prop.getProperty("databaseName") != null) {
	        this.databaseName = prop.getProperty("databaseName");
	        this.buildUrl();
	      }
	      if (prop.getProperty("charset") != null) {
	        this.charset = prop.getProperty("charset");
	        this.buildUrl();
	      }
	      this.init();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	  }

	  /**
	   * 可以连接其他的数据库服务器，需要传递数据库驱动类的类名
	   * 
	   * @param className
	   *          驱动类完整包路径
	   */
	  public DbUtil(String className) {
	    this(className, null, null, null);
	  }

	  /**
	   * 默认连接MySQL服务器
	   * 
	   * @param user
	   *          数据库账户，例如：root
	   * @param pwd
	   *          数据库密码，例如：myPassWord
	   */
	  public DbUtil(String user, String pwd) {
	    this(null, null, user, pwd);
	  }

	  /**
	   * 默认连接MySQL服务器
	   * 
	   * @param url
	   *          数据库连接字符串
	   * @param user
	   *          数据库账户，例如：root
	   * @param pwd
	   *          数据库密码，例如：myPassWord
	   */
	  public DbUtil(String url, String user, String pwd) {
	    this(null, url, user, pwd);
	  }

	  /**
	   * 可以连接其他的数据库服务器，需要传递数据库驱动类的类名
	   * 
	   * @param className
	   *          驱动类完整包路径
	   * @param url
	   *          数据库连接字符串
	   * @param user
	   *          数据库账户，例如：root
	   * @param pwd
	   *          数据库密码，例如：myPassWord
	   */
	  public DbUtil(String className, String url, String user, String pwd) {
	    if (className != null) {
	      this.className = className;
	    }
	    if (url != null) {
	      this.url = url;
	    }
	    if (user != null) {
	      this.user = user;
	    }
	    if (pwd != null) {
	      this.pwd = pwd;
	    }
	    this.init();
	  }

	  /**
	   * 设置数据库字符编码（MySQL）
	   * 
	   * @param charset
	   *          数据库字符编码，例如：utf-8、gbk等等……
	   */
	  public void setCharset(String charset) {
	    this.charset = charset;
	    this.buildUrl();
	  }

	  /**
	   * 设置数据库名（MySQL）
	   * 
	   * @param databaseName
	   *          数据库名，例如：test
	   */
	  public void setDatabaseName(String databaseName) {
	    this.databaseName = databaseName;
	    this.buildUrl();
	  }

	  /**
	   * 设置端口号（MySQL）
	   * 
	   * @param port
	   *          端口号，默认：3306
	   */
	  public void setPort(int port) {
	    this.port = port;
	    this.buildUrl();
	  }

	  /**
	   * 设置数据库服务器的(主机名称/主机域名/主机IP)（MySQL）
	   * 
	   * @param serverName
	   *          计算机名/主机域名/主机IP，例如：myComputer、localhost、mysql.whoami.edu.cn、127.0.0.1、192.168.1.101……
	   */
	  public void setServerName(String serverName) {
	    this.serverName = serverName;
	    this.buildUrl();
	  }

	  /**
	   * 重构url属性
	   */
	  private void buildUrl() {
	    this.url = "jdbc:mysql://" + this.serverName + ":" + this.port + "/" + this.databaseName + "?characterEncoding=" + this.charset;
	  }

	  /**
	   * 初始化方法，加载数据库驱动类，该方法在构造方法中调用。
	   */
	  private void init() {
	    Logger.getAnonymousLogger().config("\n\t数据库连接驱动类的类名是：" + this.className);
	    try {
	      Class.forName(this.className);
	    } catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
	  }

	  /**
	   * 根据初始化的参数，获取一个数据库连接对象。如果已经存在了数据库的连接对象，则直接返回已存在的对象。(使用单例模式)
	   * 
	   * @return 连接对象
	   * @throws SQLException
	   */
	  private Connection openConnection() throws SQLException {
	    if (this.con == null) {
	      StringBuffer sb = new StringBuffer();
	      sb.append("\n\t" + "连接字符串：" + this.url);
	      sb.append("\n\t" + "主机名称/主机域名/主机IP：" + this.serverName);
	      sb.append("\n\t" + "端口号：" + this.port);
	      sb.append("\n\t" + "数据库：" + this.databaseName);
	      sb.append("\n\t" + "字符编码：" + this.charset);
	      sb.append("\n\t" + "账户：" + this.user);
	      sb.append("\n\t" + "密码：" + this.pwd);
	      Logger.getAnonymousLogger().config(sb.toString());
	      this.con = DriverManager.getConnection(this.url, this.user, this.pwd);
	    }
	    return this.con;
	  }

	  /**
	   * 根据初始化的参数，获取一个数据库连接对象。<br/>
	   * 提供连接对象，便于特殊JDBC操作。
	   * 
	   * @return 连接对象
	   * @throws SQLException
	   */
	  public Connection getConnection() throws SQLException {
	    return this.openConnection();
	  }

	  /**
	   * 获得执行对象
	   * 
	   * @return 执行对象
	   * @throws SQLException
	   */
	  private Statement getStatement() throws SQLException {
	    return this.openConnection().createStatement();
	  }

	  /**
	   * 获得执行对象
	   * 
	   * @param sql
	   *          预处理sql语句，查询语句可以带匿名参数"?"
	   * @param params
	   *          参数数组
	   * @return 预处理语句执行对象
	   * @throws SQLException
	   */
	  private PreparedStatement getPreparedStatement(String sql, Object[] params) throws SQLException {
	    Logger.getAnonymousLogger().info("SQL:" + sql);
	    PreparedStatement pstat = this.openConnection().prepareStatement(sql);
	    if (params != null && params.length > 0) {
	      for (int i = 0; i < params.length; i++) {
	        pstat.setObject((i + 1), params[i]);
	      }
	    }
	    return pstat;
	  }

	  /**
	   * 获得执行对象
	   * 
	   * @param sql
	   *          预处理sql语句，查询语句可以带命名参数":myParam"
	   * @param params
	   *          参数集合<命名参数名,命名参数值>
	   * @return 预处理语句执行对象
	   * @throws SQLException
	   */
	  private PreparedStatement getPreparedStatement(String sql, Map<String, Object> params) throws SQLException {
	    List<Object> values = new ArrayList<Object>();
	    String tmpSql = this.parseSql(sql);
	    Integer[] keys = this.paramsMap.keySet().toArray(new Integer[0]);
	    Arrays.sort(keys);
	    for (Integer key : keys) {
	      Object value = params.get(this.paramsMap.get(key));
	      values.add(value);
	      Logger.getAnonymousLogger().finer("\tparamIndex:" + key + ",paramName:" + this.paramsMap.get(key) + ",paramValue:" + value);
	    }
	    return this.getPreparedStatement(tmpSql, values.toArray());
	  }

	  private Map<Integer, String> paramsMap = new HashMap<Integer, String>();

	  /**
	   * 分析处理带命名参数的SQL语句。使用Map存储参数，然后将参数替换成?
	   * 
	   * @param sql
	   *          带命名参数的SQL语句
	   * @return 带匿名参数的SQL语句
	   */
	  public String parseSql(String sql) {
	    String regex = "(:(\\w+))";
	    Pattern p = Pattern.compile(regex);
	    Matcher m = p.matcher(sql);
	    this.paramsMap.clear();
	    int idx = 1;
	    while (m.find()) {
	      // 参数名称可能有重复，使用序号来做Key
	      this.paramsMap.put(new Integer(idx++), m.group(2));
	    }
	    String result = sql.replaceAll(regex, "?");
	    Logger.getAnonymousLogger().finer("分析前：" + sql);
	    Logger.getAnonymousLogger().finer("分析后：" + result);
	    return result;
	  }

	  /**
	   * 使用参数值pMap，填充pStat
	   * 
	   * @param pStat
	   * @param pMap
	   *          命名参数的值表， 其中的值可以比较所需的参数多。
	   * @return
	   */
	  public boolean fillParameters(PreparedStatement pStat, Map<String, Object> pMap) {
	    boolean result = true;
	    String paramName = null;
	    Object paramValue = null;
	    int idx = 1;
	    for (Iterator<Entry<Integer, String>> itr = this.paramsMap.entrySet().iterator(); itr.hasNext();) {
	      Entry<Integer, String> entry = itr.next();
	      paramName = entry.getValue();
	      idx = entry.getKey().intValue();
	      // 不包含会返回null
	      paramValue = pMap.get(paramName);
	      try {
	        // paramValue为null，会出错吗？需要测试
	        pStat.setObject(idx, paramValue);
	      } catch (Exception e) {
	        Logger.getAnonymousLogger().severe("填充参数出错，原因：" + e.getMessage());
	        result = false;
	      }
	    }
	    return result;
	  }

	  /**
	   * 执行增删改的SQL操作
	   * 
	   * @param sql
	   * @return
	   * @throws SQLException
	   */
	  public int doUpdateUseStatement(String sql) throws SQLException {
	    Logger.getAnonymousLogger().info("SQL:" + sql);
	    return this.getStatement().executeUpdate(sql);
	  }

	  /**
	   * 执行增删改的SQL操作
	   * 
	   * @param sql
	   * @param params
	   * @return
	   * @throws SQLException
	   */
	  public int doUpdate(String sql, Object... params) throws SQLException {
	    return this.getPreparedStatement(sql, params).executeUpdate();
	  }

	  /**
	   * 执行增删改的SQL操作
	   * 
	   * @param sql
	   * @param params
	   * @return
	   * @throws SQLException
	   */
	  public int doUpdate(String sql, Map<String, Object> params) throws SQLException {
	    return this.getPreparedStatement(sql, params).executeUpdate();
	  }

	  /**
	   * 执行查询的SQL操作
	   * 
	   * @param sql
	   * @return
	   * @throws SQLException
	   */
	  public ResultSet doQueryUseStatement(String sql) throws SQLException {
	    Logger.getAnonymousLogger().info("SQL:" + sql);
	    return this.getStatement().executeQuery(sql);
	  }

	  /**
	   * 执行查询的SQL操作
	   * 
	   * @param sql
	   * @param params
	   * @return
	   * @throws SQLException
	   */
	  public ResultSet doQuery(String sql, Object... params) throws SQLException {
	    return this.getPreparedStatement(sql, params).executeQuery();
	  }

	  /**
	   * 执行查询的SQL操作
	   * 
	   * @param sql
	   * @param params
	   * @return
	   * @throws SQLException
	   */
	  public ResultSet doQuery(String sql, Map<String, Object> params) throws SQLException {
	    return this.getPreparedStatement(sql, params).executeQuery();
	  }

	  /**
	   * 控制台直接输出结果集
	   * 
	   * @param rs
	   * @throws SQLException
	   */
	  public static void showResultSet(ResultSet rs) throws SQLException {
	    rs.beforeFirst();
	    while (rs.next()) {
	      for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
	        System.out.print(rs.getObject(i) + "\t");
	      }
	      System.out.println();
	    }
	  }

	  /**
	   * 控制台格式化标准输出
	   * 
	   * @param rs
	   * @throws SQLException
	   */
	  public static void isYourShowTime(ResultSet rs) throws SQLException {
	    rs.beforeFirst();
	    // 如不关闭DbUtil.stringLength(String str)方法的日志输出，将会影响输出格式的换行。
	    Logger.getLogger("stringLength").setLevel(Level.OFF);// 关闭DbUtil.stringLength(String str)方法的日志输出

	    // 最大字符数
	    int maxLength = 0;

	    List<List<Object>> table = new ArrayList<List<Object>>();

	    // 构造表头
	    List<Object> tHead = new ArrayList<Object>();
	    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
	      String colName = rs.getMetaData().getColumnName(i);
	      int contentLength = DbUtil.stringLength(colName);
	      if (contentLength > maxLength) {
	        maxLength = contentLength;
	      }
	      tHead.add(colName);
	    }
	    table.add(tHead);

	    // 构造内容
	    while (rs.next()) {
	      List<Object> tBody = new ArrayList<Object>();
	      for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
	        Object cellContent = rs.getObject(i);
	        int contentLength = DbUtil.stringLength(cellContent);
	        if (contentLength > maxLength) {
	          maxLength = contentLength;
	        }
	        tBody.add(cellContent);
	      }
	      table.add(tBody);
	    }

	    Logger.getAnonymousLogger().fine("\n\tReal maxLength=" + maxLength++);
	    maxLength = (int) Math.ceil((double) maxLength / 8) * 8;
	    Logger.getAnonymousLogger().fine("\n\tFixed maxLength=" + maxLength);

	    Logger.getAnonymousLogger().fine("----->>>>>List Data:\n" + table);
	    // 输出表格
	    for (List<Object> tRow : table) {
	      for (Object content : tRow) {
	        System.out.print("|");
	        System.out.print(content);
	        // int n = (maxLength - 1 - DbUtil.stringLength(content));
	        int n = (int) Math.ceil((double) (maxLength - 1 - DbUtil.stringLength(content)) / 8);// \t数量
	        // System.out.print(",x=" + DbUtil.stringLength(content));// 内容长度
	        // System.out.print(",n=" + n);// \t数量
	        // System.out.print(",y=" + (maxLength - DbUtil.stringLength(content)));// 差量字符
	        for (int i = 0; i < n; i++) {
	          // System.out.print(" ");
	          System.out.print("\t");
	        }
	      }
	      System.out.println("|");
	    }
	    Logger.getLogger("stringLength").setLevel(null);// 打开DbUtil.stringLength(String str)方法的日志输出
	    // System.out.println("length:" + DbUtil.stringLength("String str包含有中文符号，1234567890是一个数字。"));
	  }

	  /**
	   * 判断字符串占用多少个半角字符位置<br/>
	   * 已知问题，中文符号本应占用2个字符位置，但按照1个字符位置计算。
	   * 
	   * @param str
	   *          需要判断的字符串
	   * @return 字符串占用的半角位置数。
	   */
	  public static int stringLength(String str) {
	    int length = str.length();

	    int nLen = length - str.replaceAll("\\d", "").length();
	    int eLen = length - str.replaceAll("[a-zA-Z]", "").length();
	    int cLen = length - str.replaceAll("[\u4e00-\u9fa5]", "").length();
	    // FIXME 已知问题，中文符号本应占用2个字符位置，但按照1个字符位置计算。
	    Logger.getLogger("stringLength").finest("\tchinese:" + cLen + "\tenglish:" + eLen + "\tnumber:" + nLen);

	    return nLen + eLen + cLen * 2 + (length - eLen - nLen - cLen);
	  }

	  /**
	   * 判断对象占用多少个半角字符位置<br/>
	   * 已知问题，中文符号本应占用2个字符位置，但按照1个字符位置计算。
	   * 
	   * @param obj
	   *          需要判断的对象
	   * @return 对象占用的半角位置数。
	   */
	  public static int stringLength(Object obj) {
	    String content = "";
	    if (obj == null) {
	      content = "null";
	    } else {
	      content = obj.toString();
	    }
	    return DbUtil.stringLength(content);
	  }

	  /**
	   * 判断字符串中是否有中文
	   * 
	   * @param str
	   *          需要判断的字符串
	   * @return 包含中文返回true，不包含返回false。
	   */
	  public static boolean isContainChinese(String str) {
	    Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
	    Matcher m = p.matcher(str);
	    if (m.find()) {
	      return true;
	    } else {
	      return false;
	    }
	  }

	  /**
	   * 关闭数据库连接
	   */
	  public void close() {
	    Logger.getAnonymousLogger().config("\n\t关闭数据库连接");
	    try {
	      if (this.con != null && !this.con.isClosed()) {
	        this.con.close();
	        this.con = null;
	      } else {
	        this.con = null;
	      }
	    } catch (SQLException e) {
	      e.printStackTrace();
	    }
	  }
}
