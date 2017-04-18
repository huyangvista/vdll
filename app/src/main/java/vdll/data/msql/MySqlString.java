package vdll.data.msql;

import vdll.data.dbc.DBCP;
import vdll.utils.io.FileOperate;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Hocean on 2017/4/5.
 */
public class MySqlString {
    public static String showTables() {
        return "show TABLES";
    }

    public static String showColumn(String tableName, String databaseName) {
        return String.format("SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE table_name = '%s' AND table_schema = '%s'", tableName, databaseName);
    }

    public static String existSqlDB(String databaseName) {
        String sql = String.format("SHOW CREATE DATABASE  `%s`", databaseName);
        return sql;
    }


    public static void CreateDemo(MySql mySql) {
        CreateDemo(mySql,"src/demo/");
    }
    /**
     * 根据数据库 创建 entity
     * @param mySql
     * @param path src/demo/
     */
    public static void CreateDemo(MySql mySql, String path) {
        mySql.exeQ(MySqlString.showTables());
        java.util.List<Map<String, Object>> list = mySql.getParms();
        String[] tabNames = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                //System.out.println(next.getValue());
                tabNames[i] = next.getValue().toString();
            }
        }

        for (int i = 0; i < tabNames.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append("package demo;");
            sb.append("\r\n");
            sb.append("\r\n");
            sb.append("import vdll.data.msql.AISql;");
            sb.append("\r\n");
            sb.append("\r\n");
            sb.append("public class " + tabNames[i] + "{");
            sb.append("\r\n");
            sb.append("\r\n");
            mySql.exeQ(MySqlString.showColumn(tabNames[i], DBCP.dbProp.getDatabaseName()));
            list = mySql.getParms();
            for (int j = 0; j < list.size(); j++) {
                Map<String, Object> map = list.get(j);
                Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, Object> next = iterator.next();
                    //System.out.println(next.getValue());

                    sb.append("@AISql public String " + next.getValue() + ";");
                    sb.append("\r\n");
                }
            }
            sb.append("\r\n");
            sb.append("}");
            FileOperate.createFile("src/demo/" + tabNames[i] + ".java", sb.toString());
            //System.out.println(sb.toString());
        }
        System.out.println("build demo finish");
    }
}
