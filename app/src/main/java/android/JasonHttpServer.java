package android;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import vdll.net.NanoHTTPD;


public class JasonHttpServer extends NanoHTTPD
{
	private static String TAG = "JasonHttpServer";
	public static final String HTTP_OK = "200 OK",
	HTTP_PARTIALCONTENT = "206 Partial Content",
	HTTP_RANGE_NOT_SATISFIABLE = "416 Requested Range Not Satisfiable",
	HTTP_REDIRECT = "301 Moved Permanently",
	HTTP_FORBIDDEN = "403 Forbidden", HTTP_NOTFOUND = "404 Not Found",
	HTTP_BADREQUEST = "400 Bad Request",
	HTTP_INTERNALERROR = "500 Internal Server Error",
	HTTP_NOTIMPLEMENTED = "501 Not Implemented";

	public JasonHttpServer(String ip, int port) throws UnknownHostException
	{
		super(ip, port);
	}

	public JasonHttpServer(int port)
	{
		super(port);
	}
	@Override
	public Response serve(IHTTPSession session)
	{
		//Log.i(TAG, "enter serve method");
		// 解析http body
		String strBody = parsebody(session);
		//Log.i(TAG, "Http body is " + strBody);

		String strAction = null;
		String strSubject = null;
		String json = null;
		try
		{
			JSONObject jsonRequest = new JSONObject(strBody);
			JSONObject jsonResponse = new JSONObject();
			strAction = jsonRequest.getString("action");
			strSubject = jsonRequest.getString("object");

			System.out.println(strAction);
			System.out.println(strSubject);
			
			//Log.i(TAG, "strAction: '" + strAction + "', strSubject: '"
			// + strSubject + "'.");
			// 处理登陆请求
			/*if (strAction.equals("login") && strSubject.equals("user"))
			 {
			 //Log.d(TAG, "Enter login");
			 String username = jsonRequest.getString("username");
			 String password = jsonRequest.getString("password");

			 if (DatabaseUtil.query(username, password))
			 {
			 jsonResponse.put("result", "ok");
			 }
			 else
			 {
			 jsonResponse.put("result", "failed");
			 }
			 // 处理注册请求
			 }
			 else if (strAction.equals("register"))
			 {
			 if (strSubject.equals("user"))
			 {
			 String username = jsonRequest.getString("username");
			 String password = jsonRequest.getString("password");
			 if (DatabaseUtil.insert(username, password) != -1)
			 {
			 jsonResponse.put("result", "ok");
			 }
			 else
			 jsonResponse.put("result", "failed");
			 }
			 }
			 // 处理传感数据请求
			 else if (strAction.equals("get"))
			 {
			 if (strSubject.equals("light"))
			 {
			 jsonResponse.put("pm2.5", SerialportService.current_pm2);
			 jsonResponse.put("co2", SerialportService.current_co21);
			 jsonResponse.put("result", "ok");

			 }
			 else  if (strSubject.equals("agriculture"))
			 {
			 //构造响应
			 jsonResponse.put("result", "ok");
			 jsonResponse.put("airTemperature",
			 SerialportService.current_tmper + "");
			 jsonResponse.put("airTempMaxValue",
			 SerialportService.maxAirTemperature);
			 jsonResponse.put("airTempMinValue",
			 SerialportService.minAirTemperature);

			 jsonResponse.put("airHumidity",
			 SerialportService.current_humid + "");
			 jsonResponse.put("airHumiMaxValue",
			 SerialportService.maxAirHumidity);
			 jsonResponse.put("airHumiMinValue",
			 SerialportService.minAirHumidity);

			 jsonResponse.put("earthTemperature",
			 SerialportService.current_tmper2);
			 jsonResponse.put("earthTempMaxValue",
			 SerialportService.maxEarthTemperature);
			 jsonResponse.put("earthTempMinValue",
			 SerialportService.minEarthTemperature);

			 jsonResponse.put("earthHumidity",
			 SerialportService.current_humid2 + "");
			 jsonResponse.put("earthHumiMaxValue",
			 SerialportService.maxEarthHumidity);
			 jsonResponse.put("earthHumiMinValue",
			 SerialportService.minEarthHumidity);

			 jsonResponse.put("light",
			 SerialportService.current_light + "");
			 jsonResponse.put("lightMaxValue", SerialportService.maxLight);
			 jsonResponse.put("lightMinValue", SerialportService.minLight);

			 jsonResponse.put("co2", SerialportService.current_co22);
			 jsonResponse.put("co2MaxValue", SerialportService.maxCo2);
			 jsonResponse.put("co2MinValue", SerialportService.minCo2);


			 }

			 }
			 // 处理设置告警范围请求
			 else if (strAction.equals("set"))
			 {
			 if (strSubject.equals("pm2.5"))
			 {
			 String value = jsonRequest.getString("value");

			 try
			 {
			 int ivalue = Integer.parseInt(value);

			 SerialportService.pm2_max = ivalue;
			 jsonResponse.put("result", "ok");
			 }
			 catch (NumberFormatException e)
			 {
			 jsonResponse.put("result", "fail");
			 }
			 // co2
			 }
			 else if (strSubject.equals("co2"))
			 {
			 String value1 = jsonRequest.getString("value1");
			 String value2 = jsonRequest.getString("value2");
			 try
			 {
			 int iValue1 = Integer.parseInt(value1);
			 int iValue2 = Integer.parseInt(value2);
			 SerialportService.co2_max = iValue2;
			 SerialportService.co2_min = iValue1;
			 jsonResponse.put("result", "ok");
			 }
			 catch (NumberFormatException e)
			 {
			 jsonResponse.put("result", "fail");
			 }
			 }
			 else if (strSubject.equals("light"))
			 {
			 String value1 = jsonRequest.getString("value1");
			 String value2 = jsonRequest.getString("value2");
			 try
			 {
			 int iValue1 = Integer.parseInt(value1);
			 int iValue2 = Integer.parseInt(value2);
			 SerialportService.minLight = iValue2;
			 SerialportService.maxLight = iValue1;
			 jsonResponse.put("result", "ok");
			 }
			 catch (NumberFormatException e)
			 {
			 jsonResponse.put("result", "fail");
			 }
			 }
			 else if (strSubject.equals("airTemp"))
			 {
			 String value1 = jsonRequest.getString("value1");
			 String value2 = jsonRequest.getString("value2");
			 try
			 {
			 int iValue1 = Integer.parseInt(value1);
			 int iValue2 = Integer.parseInt(value2);
			 SerialportService.minAirTemperature = iValue2;
			 SerialportService.maxAirTemperature = iValue1;
			 jsonResponse.put("result", "ok");
			 }
			 catch (NumberFormatException e)
			 {
			 jsonResponse.put("result", "fail");
			 }
			 }
			 else if (strSubject.equals("airHumid"))
			 {
			 String value1 = jsonRequest.getString("value1");
			 String value2 = jsonRequest.getString("value2");
			 try
			 {
			 int iValue1 = Integer.parseInt(value1);
			 int iValue2 = Integer.parseInt(value2);
			 SerialportService.minAirHumidity = iValue2;
			 SerialportService.maxAirHumidity = iValue1;
			 jsonResponse.put("result", "ok");
			 }
			 catch (NumberFormatException e)
			 {
			 jsonResponse.put("result", "fail");
			 }
			 }
			 else if (strSubject.equals("earthTemp"))
			 {
			 String value1 = jsonRequest.getString("value1");
			 String value2 = jsonRequest.getString("value2");
			 try
			 {
			 int iValue1 = Integer.parseInt(value1);
			 int iValue2 = Integer.parseInt(value2);
			 SerialportService.minEarthTemperature = iValue2;
			 SerialportService.maxEarthTemperature = iValue1;
			 jsonResponse.put("result", "ok");
			 }
			 catch (NumberFormatException e)
			 {
			 jsonResponse.put("result", "fail");
			 }
			 }
			 else if (strSubject.equals("earthHumid"))
			 {
			 String value1 = jsonRequest.getString("value1");
			 String value2 = jsonRequest.getString("value2");
			 try
			 {
			 int iValue1 = Integer.parseInt(value1);
			 int iValue2 = Integer.parseInt(value2);
			 SerialportService.minEarthHumidity = iValue2;
			 SerialportService.maxEarthHumidity = iValue1;
			 jsonResponse.put("result", "ok");
			 }
			 catch (NumberFormatException e)
			 {
			 jsonResponse.put("result", "fail");
			 }// 曲线告警值设置请求
			 }
			 else if (strSubject.equals("control"))
			 {
			 String value = jsonRequest.getString("value");

			 try
			 {
			 int ivalue = Integer.parseInt(value);
			 if (ivalue == 1)
			 SerialportService.enable();
			 else
			 SerialportService.disable();

			 jsonResponse.put("result", "ok");
			 }
			 catch (NumberFormatException e)
			 {
			 jsonResponse.put("result", "fail");
			 }
			 }
			 }*/


			json = jsonResponse.toString();
			json = json + "\n";
			//Log.i(TAG, "return :" + json);
		}
		catch (JSONException e)
		{
			//Log.e(TAG, "JSONObject convert failed! The Resquest String is"
			// + strBody);
			//e.printStackTrace();
			json = ("jiexi err");
		}

		json += ",服务器信息";
		// 构造 http 响应消息
		Response rsp = new Response(Response.Status.OK, "application/json",
									json);
		return rsp;
	}

	// 解析http body
	private String parsebody(IHTTPSession session)
	{

		System.out.println(":session:::" + session);
		String body = "";
		try
		{
			InputStream is = session.getInputStream();
			if (is == null)
			{
				//Log.i(TAG, "session.getInputStream() is null!");
				//body = "没有输入流";
			}
			else
			{
				int vcoun=is.available();
				if (vcoun > 0)
				{

					//body = "有可用流";
					// 假定消息不超过8192byte
					int bufsize = 8192;
					byte[] buf = new byte[bufsize];
					int rlen = is.read(buf, 0, bufsize);
					if (rlen <= 0)
					{
						//Log.i(TAG, "http body read 0 byte!");
						//body = "请求信息为0";
					}
					else
					{
						ByteArrayInputStream hbis = new ByteArrayInputStream(buf, 0, rlen);
						BufferedReader hin = new BufferedReader(new InputStreamReader(hbis));
						body = hin.readLine();

						System.out.println(":session:::" + body);
					}
				}
				else
				{


					//body = "没有可读取流";
				}
			}

		}
		catch (Exception ioe)
		{
			ioe.printStackTrace();
		}
		return body;
	}
}
