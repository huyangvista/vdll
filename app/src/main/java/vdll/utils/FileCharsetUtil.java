package vdll.utils;

import java.util.*;
import java.math.*;
import java.util.regex.*;
import java.io.*;

public class FileCharsetUtil {

    public static void main(String[] args) {
        //String vsPashGen= "/storage/emulated/0/"; //ANDROID
        String vsPashGen = "ass/"; //WIN

        //dirCharset((vsPashGen + "src"), "GBK", (vsPashGen + "src222"), "UTF-8");
        //dirCharset(new File(vsPashGen + "src"), "GBK", new File(vsPashGen + "src222"), "UTF-8");
        //dirCharset(new File(vsPashGen + "src/Bullets.java"), "GBK", new File(vsPashGen + "src222/Bullets.java"), "UTF-8");
    }

    public FileCharsetUtil() {

    }

    public static void dirCharset(String readDir, String fromCharset, String writeDir, String toCharset) {
        File read = new File(readDir);
        if (!read.exists()) {
            return;
        }
        File write = new File(writeDir);
        if (!write.exists()) {
            write.mkdirs();
        }
        dirCharset(read, fromCharset, write, toCharset);
    }

    public static void dirCharset(File read, String fromCharset, File write, String toCharset) {
        if (read.isDirectory()) {
            File[] fs = read.listFiles();
            for (File item : fs) {
                File f = item;
                if (f.isDirectory()) {
                    File wp = new File(write.getPath() + "/" + f.getName());
                    if (wp.exists()) {
                    } else {
                        wp.mkdirs();
                    }
                }
                dirCharset(new File(read.getPath() + "/" + f.getName()), fromCharset, new File(write.getPath() + "/" + f.getName()), toCharset);
            }
        } else if (read.isFile()) {
            String name = read.getName();
            String java = name.substring(name.length() - 5, name.length());
            String js = name.substring(name.length() - 3, name.length());

            try {
                fileCreateDir(write);
                write.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (java.equals(".java") || js.equals(".js")) {
                fileCharset(read, fromCharset, write, toCharset);
            } else {
                fileCopy(read, write);
            }

        }
    }

    public static void fileCharset(File read, String fromCharset, File write, String toCharset) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(read), fromCharset));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(write), toCharset));
            char[] buf = new char[1024 * 2];
            int len;
            while ((len = br.read(buf)) > 0) {
                bw.write(buf, 0, len);
            }
            br.close();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileCopy(File read, File write) {
        try {
            BufferedInputStream br = new BufferedInputStream(new FileInputStream(read));
            BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(write));
            byte[] buf = new byte[1024 * 2];
            int len;
            while ((len = br.read(buf)) > 0) {
                bw.write(buf, 0, len);
            }
            br.close();
            bw.flush();
            bw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void fileCreateDir(File f){
        File pf = f.getParentFile();
        if(!pf.exists()){
            pf.mkdirs();
        }
    }

}
