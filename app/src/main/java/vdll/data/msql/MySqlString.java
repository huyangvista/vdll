package vdll.data.msql;

/**
 * Created by Hocean on 2017/4/5.
 */
public class MySqlString {
    public static String showTables(){
        return "show TABLES";
    }

    public static String showColumn(String tabName, String dbName){
        return String.format("SELECT COLUMN_NAME FROM information_schema.COLUMNS WHERE table_name = '%s' AND table_schema = '%s'",tabName,dbName);
    }
}
