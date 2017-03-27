package vdll.net.android;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class CodeProcess {

	private static final String fileName = "captcha.jpg";

	

	
	//模拟登录
	public static void postForm(String uri,String username,String password, String code, String cookie) {
		String result = null;

		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false); // post方式不能使用缓存
			con.setRequestProperty("Cookie", cookie);
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");

			String BOUNDARY = "----------" + System.currentTimeMillis();
			con.setRequestProperty("Content-Type",
								   "multipart/form-data; boundary=" + BOUNDARY);
			// 请求正文信息
			// 第一部分：
			StringBuilder sb = new StringBuilder();
			sb.append("--"); // 必须多两道线
			// 这里说明下，这两个横杠是http协议要求的，用来分隔提交的参数用的，不懂的可以看看http
			// 协议头
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"username\" \r\n\r\n"); // 这里是参数名，参数名和值之间要用两次
			sb.append(username + "\r\n"); // 参数的值

			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"password\" \r\n\r\n");
			sb.append(password + "\r\n");

			sb.append("--"); // 必须多两道线
			sb.append(BOUNDARY);
			sb.append("\r\n");
			sb.append("Content-Disposition: form-data;name=\"code\" \r\n\r\n");
			sb.append(code + "\r\n");

			byte[] head = sb.toString().getBytes("utf-8");
			// 获得输出流
			OutputStream out = new DataOutputStream(con.getOutputStream());
			// 输出表头
			out.write(head);
			// 结尾部分，这里结尾表示整体的参数的结尾，结尾要用"--"作为结束，这些都是http协议的规定
			byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
			out.write(foot);
			out.flush();
			out.close();
			StringBuffer buffer = new StringBuffer();
			BufferedReader reader = null;
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(
											con.getInputStream(), "utf-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(result);
	}


	
	//根绝uri和cookie 下载整个页面
	public static void getPage(String uri,String cookie) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET"); // 以Post方式提交表单，默认get方式
			con.setRequestProperty("Cookie", cookie);
			String result="";
	        StringBuffer buffer = new StringBuffer();
			BufferedReader reader = null;
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(
											con.getInputStream(), "utf-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			result = buffer.toString();
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	

}

