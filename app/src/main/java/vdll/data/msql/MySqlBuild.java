package vdll.data.msql;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vdll.data.msql.SqlTags.RowTags;
import vdll.utils.ReflectUtil;
import vdll.utils.time.DateTime;


/**
 * 数据库  对象类
 * @author Hocean
 * @param <T>
 * @version 2016-11-08 14:43:56
 */
public class MySqlBuild<T> extends MySql {
	
	public static void main(String[] args) {

		User user = new User();user.name = "123";user.psword = "456";user.address = "789";user.id = "1";
		User userNew = new User();userNew.name = "abc";userNew.psword = "dce";userNew.address = "fgh";
		User userUpdate = new User();userUpdate.id = "1";
				
		MySqlBuild<User> vmysql = new MySqlBuild<>(User.class);
		vmysql.open();
		System.out.println("-----------------------------------------返回值：");
		System.out.println(    vmysql.create()     		 			 +"   -2已存在 -1 失败  0成功 ");
		System.out.println(    vmysql.add(user)     	  			 +"   返回影响行数");
		System.out.println(    vmysql.get(user).get(0).psword        +"   返回列表");
		System.out.println(    vmysql.update(userNew, user)          +"   返回影响行数"); 
		System.out.println(    vmysql.get().get(0).psword            +"   返回单个元素");
		System.out.println(    vmysql.delete(user)                   +"   返回影响行数");
		System.out.println(    vmysql.drop()                         +"   不存在返回 -2  失败返回 -1  成功返回 0");
		System.out.println(    vmysql.exist()                        +"   存在  true");	
		vmysql.close();		
		System.out.println("-----------------------------------------可以获取Sql语句：");
		MySqlBuild<User> msql = new MySqlBuild<>(User.class); //实例 SQL 语句
		System.out.println(msql.createSql());     //一些数据库语句
		System.out.println(msql.addSql(user));
		System.out.println(msql.getSql());
		System.out.println(msql.deleteSql(user));
		System.out.println(msql.getSql(user));
		System.out.println(msql.updateSql(userNew,user));	
		System.out.println(msql.dropSql());	
		msql.open();  //开启数据库
		msql.create();
		msql.add(user);  //添加数据
		user = msql.get(userUpdate).get(0); //按U查询 并获取第一个用户		
		msql.update(userNew, userUpdate);  //根据 不为空的字段 更新
		msql.delete(userUpdate);  //根据字段 删除
		System.out.println("-----------------------------------------插入600次数据分别用时：");
		{   //默认提交方式
			DateTime dt = new DateTime();
			for (int i = 0; i < 600; i++) {
				msql.add(userNew);  //添加数据
			}
			System.out.println(DateTime.subtract(new DateTime(), dt).getMilliSecondsCount());
		}	
		{	//开启事务
			DateTime dt = new DateTime();
			msql.setAutoCommit(false);
			for (int i = 0; i < 600; i++) {
				msql.add(userNew);  //添加数据
			}
			msql.commit();
			System.out.println(DateTime.subtract(new DateTime(), dt).getMilliSecondsCount());
		}				
		msql.drop();
		msql.close(); //关闭
		
	}
	
	public List<RowTags> valRow = new ArrayList<>(); // 所有带标记
//	public List<RowTags> valRowAll = new ArrayList<>(); // 全部
//	public List<RowTags> valRowKey = new ArrayList<>(); // 所有主键
//	public List<RowTags> valRowNoKey = new ArrayList<>(); // 没有任何主键
//	public List<RowTags> valRowAi = new ArrayList<>(); // 仅有自增的主键
//	public List<RowTags> valRowNoAi = new ArrayList<>(); // 仅没有自增的主键
//	public List<RowTags> valRowKeyAndNoAi = new ArrayList<>(); // 所有主键
	
	public List<T> vlist; //输出列表
	//private List<Integer> vlistIndex;

	private Class<T> mcls; //数据表类型
	//private String[] tableRow;

	String tabName = ""; //表名称
	
	/**
	 * 构造
	 * @param cls
	 * @param tabName
	 */
	public MySqlBuild(Class<T> cls, String tabName) {
		super();
		this.mcls = cls;
		this.tabName = tabName;
		setTagRow(cls);
	}

	/**
	 * 构造
	 * @param cls
	 */
	public MySqlBuild(Class<T> cls) {
		//this(cls,cls.getSimpleName());
		this(cls,cls.getName());
	}

	/**
	 * 构造
	 * @param cls
	 * @param vmsql
	 */
	public MySqlBuild(Class<T> cls, MySql vmsql) {
		this(cls,cls.getName(),vmsql);
	}
	public MySqlBuild(Class<T> cls,String tabName, MySql vmsql) {
		this(cls,tabName);
		super.setConn(vmsql.getConn());
		super.setPst(vmsql.getPst());
		super.setRs(vmsql.getRs());
	}

	/**
	 * 构造的时候设置类 反射的值集合
	 * @param cls
	 */
	public void setTagRow(Class<T> cls){
		AISql.Parms par = new AISql.Parms();
		valRow = SqlTags.get(mcls,par);
	}
	
	/**
	 * -1 失败  0成功 -2已存在
	 * @return
	 */
	public int create() {
		if (exist()) return -2;

		int res = -1;
		String sql = createSql(); //创建语句
		sql(sql);
		res = exeU();

		return res;
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

	/**
	 * 添加一个用户
	 * @param t
	 * @return
	 */
	public int add(T t) {
		int res = -1;
		String sql = addSql(t);
		sql(sql);
		res = exeU();

		return res;
	}

	/**
	 * 获得所有用户
	 * @return
	 */
	public List<T> get() {
		return get(null);
	}

	/**
	 * 按主键 获取并填充用户
	 * @param t
	 * @return
	 */
	public List<T> get(T t) {
		List<T> list = new ArrayList<>();
		String sql = t==null?getSql():getSql(t);
		sql(sql);
		ResultSet rs = exeQ();
		try {
			while (rs.next()) {
				T vu = (T) ReflectUtil.newInstance(mcls,new Class[]{}, new Object[] {});
				AISql.Parms par = new AISql.Parms();
				List<RowTags> valRow = SqlTags.get(mcls,par);
				for (int i = 0; i < valRow.size(); i++) {
					String objField = valRow.get(i).getName();
					String vs = rs.getString(objField);
					Field field = ReflectUtil.getFieldAll(mcls, objField);
					field.set(vu, vs);
				}			
				list.add(vu);
			}
			rs.close();
		}
		catch (Exception e) {
			//e.printStackTrace();
		}
		return list;
	}

	/**
	 * 按主键 删除
	 * @param t
	 * @return
	 */
	public int delete(T t) {
		int res = -1;
		String sql = deleteSql(t);
		sql(sql);
		res = exeU();
		return res;
	}

	/**
	 * 删除数据表  
	 * @return 不存在返回 -2  失败返回 -1 成功返回 0
	 */
	public int drop() {
		if (!exist()) return -2;
		int res = -1;

		String sql = dropSql();
		sql(sql);
		res = exeU();
		return res;
	}

	/**
	 * 按主键 更新数据
	 * @param tnew
	 * @param tif
	 * @return
	 */
	public int update(T tnew, T tif) {
		int res = -1;
		//if (vlist == null) vget();

		//index = VReflect.getFields(t, fieldName)
		String sql = updateSql(tnew,tif);
		sql(sql);
		res = exeU();
		return res;
	}

	/**
	 * 是否存在数据表
	 * @return
	 */
	public boolean exist() {
		//boolean exist = false;

		String sql = existSql();
		sql(sql);
		/*try
		{
			ResultSet rs = vexeQ();
			if (rs.next())
			{
		
			}
			exist = true;
		}
		catch (Exception e)
		{
			//e.printStackTrace();
		}
		return exist;*/
		return exe();
	}

	/**
	 * 是否存在数据库
	 * @return
	 */
	public boolean existDB(String databaseName) {
		//boolean exist = false;

		String sql = existSqlDB(databaseName);
		sql(sql);
		/*try
		{
			ResultSet rs = vexeQ();
			if (rs.next())
			{
		
			}
			exist = true;
		}
		catch (Exception e)
		{
			//e.printStackTrace();
		}
		return exist;*/
		return exe();

	}

	//-------------------------------------------------      合成语句          --------------
	/**
	 * 创建数据表语句   //ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 * @return
	 */
	public String createSql() {

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE ");
		sb.append("`").append(tabName).append("`");
		sb.append(" ( ");

		for (int i = 0; i < valRow.size(); i++) { //                      sb.append();    sb.append("`").append(vspkai).append("`");
			sb.append("`").append(valRow.get(i).getName()).append("` ");
			//字段类型
			sb.append(valRow.get(i).getAitag().type()).append("(").append(valRow.get(i).getAitag().length());
			if(valRow.get(i).getAitag().number() != 0){//如果有小数
				sb.append(", ").append(Integer.toString(valRow.get(i).getAitag().number()));
			}
			sb.append(") ");
			
			//能否为空
			if (valRow.get(i).getAitag().isNoNull() || valRow.get(i).getAitag().isPrimaryKey()) {
				sb.append(" NOT NULL");
			}
			//自动增长
			if (valRow.get(i).getAitag().isAutoAdd()) {
				sb.append(" AUTO_INCREMENT");
			}
			//缺省值
			if (!valRow.get(i).getAitag().defParm().isEmpty()) {
				sb.append(" DEFAULT '").append(valRow.get(i).getAitag().defParm()).append("'");
			}
			//注释
			if (!valRow.get(i).getAitag().note().isEmpty()) {
				sb.append(" COMMENT '").append(valRow.get(i).getAitag().note()).append("'");
			}
			sb.append(",");
		}
		//添加主键
		if (valRow.size() > 0) {			
			sb.append(" PRIMARY KEY (");
			for (int i = 0; i < valRow.size(); i++) {
				if (valRow.get(i).getAitag().isPrimaryKey()) {
					sb.append("`").append(valRow.get(i).getName()).append("`,");
				}
			}
			sb.delete(sb.length() - 1, sb.length());
			sb.append(")");
			
		}
		sb.append(" ) ");
		return sb.toString();
	}

	/**
	 * 添加用户语句
	 * @param t
	 * @return
	 */
	public String addSql(T t) {
		
		List<Object> tableName = new ArrayList<>();
		List<Object> tableValue = new ArrayList<>();

		for (int i = 0; i < valRow.size(); i++) {
			//Object val =valRow.get(i).getValue();
			Object val = ReflectUtil.getFieldValue(t, valRow.get(i).getField());
			if(val == null) continue;
			tableName.add( valRow.get(i).getName());
			tableValue.add(val);
		}

		StringBuilder sbk = new StringBuilder();
		StringBuilder sbv = new StringBuilder();
		for (int i = 0; i < tableName.size(); i++) {
			sbk.append(tableName.get(i).toString()).append(",");
			sbv.append("'").append(tableValue.get(i).toString()).append("',");
		}
		sbv.delete(sbv.length() - 1, sbv.length());
		sbk.delete(sbk.length() - 1, sbk.length());

		String sql = String.format("INSERT INTO `%s` (%s) VALUES (%s);", tabName, sbk, sbv);

		return sql;
	}

	/**
	 * 获取全部用户语句
	 * @return
	 */
	public String getSql() {
		String sql = String.format("SELECT * FROM `%s`;", tabName);
		return sql;
	}

	/**
	 * 获取用户语句
	 * @param t
	 * @return
	 */
	public String getSql(T t) {	
		String sql = String.format("SELECT * FROM `%s` WHERE (%s);", tabName, sqlJoin(t,"&&"));
		return sql;
	}

	/**
	 * 删除用户语句
	 * @param t
	 * @return
	 */
	public String deleteSql(T t) {
	
		String sql = String.format("DELETE FROM `%s` WHERE (%s);", tabName, sqlJoin(t,"&&"));
		return sql;
	}

	/**
	 * 删除数据表语句
	 * @return
	 */
	public String dropSql() {
		String sql = String.format("drop table if exists `%s`", tabName);
		return sql;
	}

	/**
	 * 更新数据表语句
	 * @param tnew
	 * @param tif
	 * @return
	 */
	public String updateSql(T tnew, T tif) {		

		String sql = String.format("UPDATE `%s` SET %s WHERE (%s);", tabName, sqlJoin(tnew,","), sqlJoin(tif,"&&"));
		return sql;
	}

	/**
	 * 是否存在数据表
	 * @return
	 */
	public String existSql() {
		String sql = String.format("SHOW CREATE TABLE `%s`", tabName);
		return sql;
	}

	/**
	 * 是否存在数据库
	 * @return
	 */
	public String existSqlDB(String databaseName) {
		String sql = String.format("SHOW CREATE DATABASE  `%s`", databaseName);
		return sql;
	}  
	
	/**
	 * sql语句 连接（部分） 只针对条件语句
	 * @param t
	 * @param join
	 * @return
	 */
	private String sqlJoin(T t, String join){
		List<Object> tableName = new ArrayList<>();
		List<Object> tableValue = new ArrayList<>();
		for (int i = 0; i < valRow.size(); i++) {
			Object val = ReflectUtil.getFieldValue(t, valRow.get(i).getField());
			if(val == null) continue;
			tableName.add( valRow.get(i).getName());
			tableValue.add(val);
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tableName.size(); i++) {
			sb.append("`").append(tableName.get(i).toString()).append("`");
			sb.append("=");
			sb.append("'").append(tableValue.get(i).toString()).append("'");
			sb.append(join);
		}
		if(tableName.size() > 0){
			sb.delete(sb.length() - join.length(), sb.length());
		}		
		return sb.toString();
	}
	//-------------------------------------------------      合成语句          --------------

}

/*  //使用
		User user = new User();user.name = "123";user.psword = "456";user.u_address = "789";user.id = "1";
		User userNew = new User();userNew.name = "abc";userNew.psword = "dce";userNew.u_address = "fgh";
		User userUpdate = new User();userUpdate.id = "1";
//		System.out.println(msql.createSql());
//		System.out.println(msql.addSql(user));
//		System.out.println(msql.getSql());
//		System.out.println(msql.deleteSql(user));
//		System.out.println(msql.getSql(user));
//		System.out.println(msql.updateSql(userNew,user));

		MySqlBuild<User> msql = new MySqlBuild<>(User.class); //实例 SQL 语句
		msql.open();  //开启数据库
		msql.create();
		msql.add(user);  //添加数据
		user = msql.get(userUpdate).get(0); //按U查询 并获取第一个用户		
		msql.update(userNew, userUpdate);  //根据 不为空的字段 更新
		msql.delete(userUpdate);  //根据字段 删除
		
		{   //默认提交方式
			DateTime dt = new DateTime();
			System.out.println(dt.format());
			for (int i = 0; i < 600; i++) {
				msql.add(userNew);  //添加数据
			}
			dt = new DateTime();
			System.out.println(dt.format());
		}	
		{	//开启事务
			DateTime dt = new DateTime();
			System.out.println(dt.format());
			msql.setAutoCommit(false);
			for (int i = 0; i < 600; i++) {
				msql.add(userNew);  //添加数据
			}
			msql.commit();
			dt = new DateTime();
			System.out.println(dt.format());
		}
				
		msql.drop();
		msql.close(); //关闭
		
		
//		MySqlBuild<User> vmysql = new MySqlBuild<>(User.class);
//		vmysql.open();		
//		User user = new User();user.name = "123";user.psword = "456";user.u_address = "789";user.id = "1";
//		User userNew = new User();userNew.name = "abc";userNew.psword = "dce";userNew.u_address = "fgh";
//		System.out.println(    vmysql.create()     		 			 );
//		System.out.println(    vmysql.add(user)     	  			 );
//		System.out.println(    vmysql.get(user).get(0).psword       	 );
//		System.out.println(    vmysql.update(userNew, user)          );  
//		System.out.println(    vmysql.get().get(0).psword              );
//		System.out.println(    vmysql.delete(user)                   );
//		System.out.println(    vmysql.drop()                         );
//		System.out.println(    vmysql.exist()                        );		
//		vmysql.close();

*/

/*// 数据库测试
	private static void mysql()
	{
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pst = null;
		java.sql.ResultSet rs = null;

		try
		{
			conn = DBUtil.getConnection();

			/*pst = DBUtil.getPstmt(conn, "INSERT INTO tabs(vname) VALUES (?)");
			pst.setString(1, "000000000");
			int count = pst.executeUpdate();
			System.out.println(count);
			
			DBUtil.close(pst);
			pst = DBUtil.getPstmt(conn, "SELECT * FROM tabs t");
			//pst.setString(1, "*");
			rs = pst.executeQuery();
			while (rs.next())
			{
				String name = rs.getString("t.id");
				System.out.println(name);
			}
			这有个结束 注释 

			//VCarUser.class.getName();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < tableRow.length; i++)
			{
				sb.append(",");
				sb.append(tableRow[i]);
				sb.append(" VARCHAR(45)");
			}
			sb.append(" ,PRIMARY KEY (`id`)");
			System.out.println(sb);

			conn = DBUtil.getConnection();
			//pst = DBUtil.getPstmt(conn, "CREATE TABLE `tabbbb` (  `idnew_table` INT NOT NULL AUTO_INCREMENT,`new_tablecol` VARCHAR(45) NULL,PRIMARY KEY (`idnew_table`))");
			String sqls = "CREATE TABLE `tabbbb` (  `id` INT NOT NULL AUTO_INCREMENT " + sb.toString() + ")";
			System.out.println(sqls);
			pst = DBUtil.getPstmt(conn, sqls);

			pst.executeUpdate();
			DBUtil.close(pst);

		}
		catch (java.sql.SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DBUtil.close(rs, pst, conn);
		}
	}
	*/
