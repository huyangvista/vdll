package vdll.data.dbc;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.commons.dbcp.*;
//import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;

/**
 * java 数据库连接池 [JavaWeb 项目移植]
 * tomcat数据库连接池管理类<br>
 * 使用为tomcat部署环境<br>
 * 需要在类路径下准备数据库连接配置文件dbcp.properties
 * Created by Hocean on 2017/4/14.
 */
public class DBCP implements IDB {
    public static DataSource dataSource;
    public static DBProp dbProp = new DBProp();

    static {
        load();
    }

    public DBCP() {

    }

    public static void load() {
        try {
            dbProp.load();
            dataSource = BasicDataSourceFactory.createDataSource(dbProp.getProp());
//            Connection conn = open();
//            DatabaseMetaData mdm = conn.getMetaData();
//            conn.close();
        } catch (Exception e) {
        }
    }

    /**
     * 获取链接，用完后记得关闭
     *
     * @return
     * @see {@link DBCP#close(Connection)}
     */
    public static Connection open() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn) {
        DBUtil.close(conn);
    }

}
