package vdll.data.dbc;

import java.sql.*;

/**
 * JDBC操作的工具类,加载数据库驱动,获取数据库连接
 *
 * Created by Hocean on 2017/4/14.
 */
public class DBCN implements IDB {
    private static DBProp dbProp = new DBProp();

    static {
        load();
    }

    public DBCN() {
    }

    public static void load() {
        try {
            dbProp.load();
            Class.forName(dbProp.getDriverClassName());
        } catch (Exception e) {
        }
    }

    /**
     * 获取数据库连接
     *
     * @return Connection conn
     */
    public static Connection open() {
        String url = dbProp.getUrl();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, dbProp.getUsername(), dbProp.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn) {
        DBUtil.close(conn);

    }

}
