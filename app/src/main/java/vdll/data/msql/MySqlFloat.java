package vdll.data.msql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * 数据库 过程类
 * @author Hocean
 * 1打开  2设置 3执行 4关闭
 */
public class MySqlFloat
{
	public static void main(String[] args) {
		MySqlBuild<User> vmysqlbuild = new MySqlBuild<>(User.class); //实例 SQL 语句
		MySqlFloat vmsql = new MySqlFloat(); //实例 SQL 连接
		vmsql.open(); //开启数据库

		vmsql.sql(vmysqlbuild.createSql());
		vmsql.exeU(); //新建表

		User user = new User();user.name = "123";user.psword = "456";user.address = "789";user.id = "1";
		User userNew = new User();userNew.name = "abc";userNew.psword = "dce";userNew.address = "fgh";
		User userIf = new User();userIf.id = "1";
		vmsql.sql(vmysqlbuild.addSql(user)); //设置SQL语句   添加用户
		vmsql.exeU(); //添加

		user.id = "1"; user.psword = "456456";user.address = "update u_address"; //测试 用户
		vmsql.sql(vmysqlbuild.updateSql(user,userIf)); //设置SQL语句   添加用户
		vmsql.exeU(); //更新

		vmsql.sql(vmysqlbuild.getSql()); //设置SQL语句   获取所有信息
		vmsql.exeQ(); //查询

		List<Map<String, Object>> vlist = vmsql.getParms("name", "psword"); //获取游标信息

		vmsql.sql(vmysqlbuild.dropSql()); //设置SQL语句   删除表
		vmsql.exeU(); //删除

		vmsql.close(); //关闭

		System.out.println(vlist.get(0).get("psword"));
		System.out.println(vlist.get(0).get("name"));
	}

	private Connection conn = null; //连接
	private PreparedStatement pst = null; //预处理语句
	private ResultSet rs = null; //游标

	//静态块
	public static String databaseUrl = "123.206.23.166:3306";
	public static String databaseName = "tbi_erp";
	public static String username = "root";
	public static String password = "hoceanvista";
	static //加载驱动
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		//System.out.println(MySql.class.getClassLoader().getResource(""));
		InputStream is = MySqlFloat.class.getClassLoader().getResourceAsStream("db.properties2");
		Properties prop = new Properties();
		try
		{
			prop.load(is);
			databaseUrl = prop.getProperty("DATABASEURL");
			databaseName = prop.getProperty("DATABASENAME");
			username = prop.getProperty("USERNAME");
			password = prop.getProperty("PASSWORD");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	//0
	public MySqlFloat()
	{
		// TODO Auto-generated constructor stub

	}

	//0 -> 1
	public Connection open()
	{		
		return open(databaseUrl, databaseName,username,password);
	}
	//0 -> 1
	public Connection open(String databaseUrl, String databaseName,  String username, String password)
	{
		// TODO Auto-generated method stub
		//String url = "jdbc:mysql://localhost:3306/" + databaseName + "?useUnicode=true&characterEncoding=utf8";
		String url = "jdbc:mysql://"+databaseUrl+"/" + databaseName + "?useUnicode=true&characterEncoding=utf8";
		try
		{
			conn = DriverManager.getConnection(url, username, password);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return conn;
	}
	//1 -> 2  主动设置参数语句用 ? 设置好参数在 vexe()
	public PreparedStatement sql(String sql)
	{
		try
		{
			if (pst != null)
			{
				pst.close();
				pst = null;
			}
			pst = conn.prepareStatement(sql);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return pst;
	}
	//1 -> 3.0 读取数据后关闭
	public ResultSet exeQ(String sql)
	{
		try
		{
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//String vse = pst.toString();
			//if(vse.indexOf("SHOW CREATE5") < 0)
			System.err.println("exeQ => " + pst.toString());
			System.err.println("SQLException => " + e.toString());
		}
		return rs;
	}
	//1 -> 3.1
	public int exeU(String sql)
	{
		int viu = -1;
		try
		{
			pst = conn.prepareStatement(sql);
			viu = pst.executeUpdate();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			System.err.println("exeU => " + pst.toString());
			System.err.println("SQLException => " + e.toString());
		}
		return viu;
	}
	//1 -> 3.2
	public boolean exe(String sql)
	{
		boolean vb = false;
		try
		{
			pst = conn.prepareStatement(sql);
			vb = pst.execute();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
		}
		return vb;
	}
	//2 -> 3.0 读取数据后关闭
	public ResultSet exeQ()
	{
		try
		{
			rs = pst.executeQuery();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
			//String vse = pst.toString();
			//if(vse.indexOf("SHOW CREATE5") < 0)
			System.err.println("exeQ => " + pst.toString());
			System.err.println("SQLException => " + e.toString());
		}
		return rs;
	}
	//2 -> 3.1
	public int exeU()
	{
		int viu = -1;
		try
		{
			viu = pst.executeUpdate();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			System.err.println("exeU => " + pst.toString());
			System.err.println("SQLException => " + e.toString());
		}
		return viu;
	}
	//2 -> 3.2
	public boolean exe()
	{
		boolean vb = false;
		try
		{
			vb = pst.execute();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
		}
		return vb;
	}
	//4
	public void close()
	{
		try
		{
			if (rs != null)
			{
				rs.close();
			}
			if (pst != null)
			{
				pst.close();
			}
			if (conn != null)
			{
				conn.setAutoCommit(true); //连接池需要
				conn.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//3.0 -> 3.0.0     查询字段
	public List<Map<String, Object>> getParms(String... parms)
	{
		List<Map<String, Object>> vlist = new ArrayList<>();
		try
		{
			while (rs.next())
			{
				Map<String, Object> map = new HashMap<>();
				for (int i = 0; i < parms.length; i++)
				{	
					String vsp = parms[i];
					Object vo = rs.getObject(vsp);
					map.put(vsp, vo);
				}
				vlist.add(map);
			}
			rs.close();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return vlist;
	}
	//3.0 -> 3.0.0     查询字段
	public List<Map<String, Object>> getParms()
	{
		return getParms(rs);
	}
	//查询字段
	public static List<Map<String, Object>> getParms(ResultSet rs)
	{
		List<Map<String, Object>> vlist = new ArrayList<>();
		try
		{
			ResultSetMetaData md = rs.getMetaData(); //表结构
			String[] cols = getColumnName(rs); //列名
			while (rs.next())
			{
				Map<String, Object> map = new HashMap<>();
				for (int i = 1; i <= cols.length; i++) { //千万注意从 1开始
					String colName = cols[i - 1];
					Object val = rs.getObject(i);
					map.put(colName, val);
				}
				vlist.add(map);
			}
			rs.close();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vlist;
	}

	//3.0 -> 3.0.0  返回行名称
	public String[] getColumnName(){
		return getColumnName(rs);
	}
	//返回行名称
	public static String[] getColumnName(ResultSet rs){
		String[] cols = null;
		try {
			ResultSetMetaData md = rs.getMetaData(); //表结构
			int colCount = md.getColumnCount(); //列数
			cols = new String[colCount];
			for (int i = 1; i <= colCount; i++) { //千万注意从 1开始
				String colName = md.getColumnName(i);
				cols[i - 1] = colName;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cols;
	}

	//0, 1, 3.0, 4 注意关闭
	public ResultSet exeQBuild(String sql)
	{
		open();
		sql(sql);
		exeQ();
		return rs;
	}
	//0, 1, 3.0, 4
	public List<Map<String, Object>> exeQBuild(String sql, String... parms)
	{
		open();
		sql(sql);
		exeQ();		
		List<Map<String, Object>> vlist = (parms!=null&&parms.length>0)?getParms(parms):getParms();
		close();
		return vlist;
	}
	//0, 1, 3.0, 4
	public int exeUBuild(String sql)
	{
		open();
		sql(sql);
		int vi = exeU();
		close();
		return vi;
	}

	//1 -> 1.5 设置 是否自动提交  默认为 true
	public void setAutoCommit(boolean vautoCommit)
	{
		try
		{
			conn.setAutoCommit(vautoCommit);
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	//3 -> 3.5 提交数据库修改
	public void commit()
	{
		try
		{
			conn.commit();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//辅助性
	/**
	 * 是否存在数据库
	 * @return
	 */
	public boolean existDB(String databaseName) {
		String sql = String.format("SHOW CREATE DATABASE  `%s`", databaseName);
		sql(sql);
		return exe();
	}
	/**
	 * -1 失败  1成功 -2已存在
	 * @return
	 */
	public int createDB(String databaseName) {
		if (existDB(databaseName)) return -2;
		int res = -1;
		String sql = "CREATE DATABASE " + databaseName + " DEFAULT CHARSET=utf8;"; //创建语句
		sql(sql);
		res = exeU();
		return res;
	}
	
	//属性
	public Connection getConn()
	{
		return conn;
	}
	public void setConn(Connection conn)
	{
		this.conn = conn;
	}
	public PreparedStatement getPst()
	{
		return pst;
	}
	public void setPst(PreparedStatement pst)
	{
		this.pst = pst;
	}
	public ResultSet getRs()
	{
		return rs;
	}
	public void setRs(ResultSet rs)
	{
		this.rs = rs;
	}

	public static String getDatabaseName()
	{
		return databaseName;
	}
	public static void setDatabaseName(String databaseName)
	{
		MySqlFloat.databaseName = databaseName;
	}
	public static String getUsername()
	{
		return username;
	}
	public static void setUsername(String username)
	{
		MySqlFloat.username = username;
	}
	public static String getPassword()
	{
		return password;
	}
	public static void setPassword(String password)
	{
		MySqlFloat.password = password;
	}

}


/*
SHOW TABLE STATUS FROM	vdata ;
SHOW TABLE STATUS FROM	vdata WHERE `Name`= 'vive.unity.vuser'; //表状态
SHOW DATABASES WHERE `Database`='vdata';  //查找数据库
show full columns from bas_si_user //得到注释
show create table bas_si_user;  //得到创建语句

*/


/*  //测试数据库 源代码
   private static void MySql()
	{		
		try
		{
			java.sql.Connection con = null; //数据库实例
			Class.forName("com.mysql.jdbc.Driver").newInstance(); // 反射驱动
			con = java.sql.DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/vdata", "root", "hoceanvista"); //返回实例MYSQL

			java.sql.Statement stmt; //操作对象
			stmt = con.createStatement();

			//插入
			stmt.executeUpdate("INSERT INTO vtab00 (vid) VALUES ( '123456')");
			java.sql.ResultSet res = stmt.executeQuery("select * from vtab00");  
			int ret_id;
			while (res.next())
			{
				ret_id = res.getInt(1);
				System.out.print(ret_id);
			}

		}
		catch (Exception e)
		{
			System.out.print("MYSQL ERROR:" + e.getMessage());
		}
	}
 */


/*   //使用方法
	public void mysql()
	{
		MySqlBuild<User> vmysqlbuild = new MySqlBuild<>(User.class); //实例 SQL 语句
		MySql vmsql = new MySql(); //实例 SQL 连接
		vmsql.vopen(); //开启数据库
		
		vmsql.vsql(vmysqlbuild.vcreateSql());
		vmsql.vexeU(); //新建表
		
		User user = new User();user.name = "123";user.psword = "456";user.u_address = "789";user.id = "1"; //测试 用户
		vmsql.vsql(vmysqlbuild.vaddSql(user)); //设置SQL语句   添加用户
		vmsql.vexeU(); //更新

		vmsql.vsql(vmysqlbuild.vgetSql()); //设置SQL语句   获取所有信息
		vmsql.vexeQ(); //查询
		
		List<Map<String, Object>> vlist = vmsql.vgetParms("name", "psword"); //获取游标信息
		
		vmsql.vsql(vmysqlbuild.vdropSql()); //设置SQL语句   删除表
		vmsql.vexeU(); //删除
		vmsql.vclose(); //关闭

		System.out.println(vlist.get(0).get("name"));
		System.out.println(vlist.get(0).get("psword"));
	}

*/
/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
public class TestDemo {
public static Connection getConnection() {
Connection conn = null;
try {
Class.forName("com.mysql.jdbc.Driver");
String url = "jdbc:mysql://数据库IP地址:3306/数据库名称";
String user = "数据库用户名";
String pass = "数据库用户密码";
conn = DriverManager.getConnection(url, user, pass);
} catch (ClassNotFoundException e) {
e.printStackTrace();
} catch (SQLException e) {
e.printStackTrace();
}
return conn;
}
public static void main(String[] args) {
Connection conn = getConnection();
String sql = "select * from AccessType";
PreparedStatement stmt;
try {
stmt = conn.prepareStatement(sql);
ResultSet rs = stmt.executeQuery(sql);
ResultSetMetaData data = rs.getMetaData();
for (int i = 1; i <= data.getColumnCount(); i++) {
// 获得所有列的数目及实际列数
int columnCount = data.getColumnCount();
// 获得指定列的列名
String getColumnName = data.getColumnName(i);
// 获得指定列的列值
int columnType = data.getColumnType(i);
// 获得指定列的数据类型名
String columnTypeName = data.getColumnTypeName(i);
// 所在的Catalog名字
String catalogName = data.getCatalogName(i);
// 对应数据类型的类
String columnClassName = data.getColumnClassName(i);
// 在数据库中类型的最大字符个数
int columnDisplaySize = data.getColumnDisplaySize(i);
// 默认的列的标题
String columnLabel = data.getColumnLabel(i);
// 获得列的模式
String schemaName = data.getSchemaName(i);
// 某列类型的精确度(类型的长度)
int precision = data.getPrecision(i);
// 小数点后的位数
int scale = data.getScale(i);
// 获取某列对应的表名
String tableName = data.getTableName(i);
// 是否自动递增
boolean isAutoInctement = data.isAutoIncrement(i);
// 在数据库中是否为货币型
boolean isCurrency = data.isCurrency(i);
// 是否为空
int isNullable = data.isNullable(i);
// 是否为只读
boolean isReadOnly = data.isReadOnly(i);
// 能否出现在where中
boolean isSearchable = data.isSearchable(i);
System.out.println(columnCount);
System.out.println("获得列" + i + "的字段名称:" + getColumnName);
System.out.println("获得列" + i + "的类型,返回SqlType中的编号:"+ columnType);
System.out.println("获得列" + i + "的数据类型名:" + columnTypeName);
System.out.println("获得列" + i + "所在的Catalog名字:"+ catalogName);
System.out.println("获得列" + i + "对应数据类型的类:"+ columnClassName);
System.out.println("获得列" + i + "在数据库中类型的最大字符个数:"+ columnDisplaySize);
System.out.println("获得列" + i + "的默认的列的标题:" + columnLabel);
System.out.println("获得列" + i + "的模式:" + schemaName);
System.out.println("获得列" + i + "类型的精确度(类型的长度):" + precision);
System.out.println("获得列" + i + "小数点后的位数:" + scale);
System.out.println("获得列" + i + "对应的表名:" + tableName);
System.out.println("获得列" + i + "是否自动递增:" + isAutoInctement);
System.out.println("获得列" + i + "在数据库中是否为货币型:" + isCurrency);
System.out.println("获得列" + i + "是否为空:" + isNullable);
System.out.println("获得列" + i + "是否为只读:" + isReadOnly);
System.out.println("获得列" + i + "能否出现在where中:"+ isSearchable);
}
} catch (SQLException e) {
e.printStackTrace();
}
}
}
 */