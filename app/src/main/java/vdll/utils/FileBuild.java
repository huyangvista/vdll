package vdll.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * 对象靓点 文件操作
 * @author Hocean
 *
 */
public class FileBuild {

	private static int FILESIZE = 4 * 1024;



	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 * @param fileName
	 * @param input
	 * @return
	 */
	public static File write2SDFromInput(String fileName, InputStream input) {
		File file = null;
		OutputStream output = null;
		try {
			file = new File(fileName);
			output = new FileOutputStream(file);
			byte[] buffer = new byte[FILESIZE];

			int length;
			while ((length = (input.read(buffer))) > 0) {
				output.write(buffer, 0, length);
			}
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

}