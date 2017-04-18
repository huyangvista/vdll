package vdll.net;

import java.io.*;
import java.net.*;
import java.util.*;

public class GetPostUtil
{
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendGet(String url, String params)
	{
		String result = "";
		BufferedReader in = null;
		try
		{
			String urlName = url + "?" + params;
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 建立实际的连接
			conn.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet())
			{
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null)
			{
				result += "\n" + line;
			}
		}
		catch (Exception e)
		{
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定URL发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendPost(String url, String params)
	{
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try
		{
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null)
			{
				result += "\n" + line;
			}
		}
		catch (Exception e)
		{
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
			return "error";
		}
		// 使用finally块来关闭输出流、输入流
		finally
		{
			try
			{
				if (out != null)
				{
					out.close();
				}
				if (in != null)
				{
					in.close();
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return result;
	}


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
/*
	Activity类代码 

	public class GetPostMain extends Activity
	{
		Button get , post;
		EditText show;
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			get = (Button) findViewById(R.id.get);
			post = (Button) findViewById(R.id.post);
			show = (EditText)findViewById(R.id.show);
			//利用Handler更新UI
			final Handler h = new Handler(){
				@Override
				public void handleMessage(Message msg) {
					if(msg.what==0x123){
						show.setText(msg.obj.toString());
					}
				}
			};

			get.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						new Thread(new AccessNetwork("GET", "http://192.168.1.88:8080/abc/a.jsp", null, h)).start();
					}   
				});
			post.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						new Thread(new AccessNetwork("POST", "http://192.168.1.88:8080/abc/login.jsp", "name=crazyit.org&pass=leegang", h)).start();
					}   
				}); 
		}
	}
	class AccessNetwork implements Runnable{
		private String op ;
		private String url;
		private String params;
		private Handler h;

		public AccessNetwork(String op, String url, String params,Handler h) {
			super();
			this.op = op;
			this.url = url;
			this.params = params;
			this.h = h;
		}
		@Override
		public void run() {
			Message m = new Message();
			m.what = 0x123;
			if(op.equals("GET")){
				Log.i("iiiiiii","发送GET请求");
				m.obj = GetPostUtil.sendGet(url, params);
				Log.i("iiiiiii",">>>>>>>>>>>>"+m.obj);
			}
			if(op.equals("POST")){
				Log.i("iiiiiii","发送POST请求");
				m.obj = GetPostUtil.sendPost(url, params);
				Log.i("gggggggg",">>>>>>>>>>>>"+m.obj);
			}
			h.sendMessage(m);
		}
	}*/
