package vdll.utils;

/**
 * Created by Hocean on 2017/4/5.
 */

import java.io.File;

/**
 * 得到当前应用的系统路径
 */
public class SystemPath {

    /*replace和replaceAll是JAVA中常用的替换字符的方法,它们的区别是：
        1)replace的参数是char和CharSequence，即可以支持字符的替换，也支持字符串的替换(CharSequence即字符串序列的意思,说白了也就是字符串)；
        2)replaceAll的参数是regex，即基于规则表达式的替换，比如，可以通过replaceAll("\\d", "*")把一个字符串所有的数字字符都换成星号;
    */

    /**
     *    得到当前应用的系统路径
     */
    public static String getSysPath() {
        //从classpath根开始查找
        String path = Thread.currentThread().getContextClassLoader()
                .getResource("").toString();
        String temp = path.replaceFirst("file:/", "").replaceFirst(
                "WEB-INF/classes/", "");
        //文件分隔符（在 UNIX 系统中是“/”）
        String separator = System.getProperty("file.separator");
        String resultPath = temp.replaceAll("/", separator + separator);
        return resultPath;
    }

    /**
     *    得到当前应用生成class文件的系统路径
     */
    public static String getClassPath() {
        String path = Thread.currentThread().getContextClassLoader()
                .getResource("").toString();
        String temp = path.replaceFirst("file:/", "");
        String separator = System.getProperty("file.separator");
        String resultPath = temp.replaceAll("/", separator + separator);
        return resultPath;
    }

    /**
     *    默认的临时文件路径
     */
    public static String getSystempPath() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     *    文件分隔符（在 UNIX 系统中是“/”）
     */
    public static String getSeparator() {
        return System.getProperty("file.separator");
    }

    /**
     *    控制台打印显示
     */
    public static void main(String[] args) {
        System.out.println(getSysPath());
        System.out.println(System.getProperty("java.io.tmpdir"));
        System.out.println(getSeparator());
        System.out.println(getClassPath());
    }
}
