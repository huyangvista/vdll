package vdll.net.android;

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
