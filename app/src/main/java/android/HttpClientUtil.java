package android;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

import vdll.net.NetUtil;

//import com.lostad.app.demo.view.mainFragment.joblog.Page;

/**
new
 * Hocean 2016年9月9日13:30:23
 */
public class HttpClientUtil  extends NetUtil{
	final String host = "60.28.59.66";
	final int port = 5438;


    public static void main(String[] args) throws IOException {
        HttpClientUtil hcu = new HttpClientUtil();
        //hcu.doGet(urlString);
        //hcu.doGet(urlString2);

        String url = "http://www.btravelplus.com/tbi-erp/";
        //访问主页
        System.out.print(hcu.doGet(url));
        //登陆账号
        System.out.print(hcu.doPost(url + "login", "username=huhaiyang&password=1234"));
        //获取内容
        String vget1 = hcu.doGet("http://www.btravelplus.com/tbi-erp/tbi/jobLog/selectJobLog?pagenum=0");
        System.out.print(vget1);
        System.out.print(hcu.doGet("http://www.btravelplus.com/tbi-erp/tbi/jobLog/selectJobLog?pagenum=1"));




//        Gson gson = new Gson();
//        Page page =  gson.fromJson(vget1, Page.class);

      /*  try {
            JSONObject js = new JSONObject(vget1);
            Object vo =  js.getJSONArray("list").get(0);
            JSONObject jso = new JSONObject(vo.toString());
            vo = jso.get("createDate");

            System.out.println(vo);


        } catch (JSONException e) {
            e.printStackTrace();
        }*/




        //hcu.doGet("http://www.btravelplus.com/tbi-erp/tbi/jobLog/addJobLog");

        //添加日志
        /*hcu.doPost("http://www.btravelplus.com/tbi-erp/tbi/jobLog/addJobLog"
                   , "confirmPerson=" + URLEncoder.encode("鲍学慧", "utf-8")
				   + "&department=" + URLEncoder.encode("研发部", "utf-8")
				   + "&endTime=" + URLEncoder.encode("12:00", "utf-8")
		 		   + "&job=" + URLEncoder.encode("整理安卓框架", "utf-8") //工作内容
				   + "&jobDate=" + URLEncoder.encode("2016-09-08", "utf-8")
				   + "&jobNo=" + URLEncoder.encode("123", "utf-8")
				   + "&jobResult=" + URLEncoder.encode("进行中", "utf-8")
				   + "&jobStyle=" + URLEncoder.encode("写资料", "utf-8")
				   + "&jobType=" + URLEncoder.encode("研发", "utf-8")
				   + "&oneTwo=" + URLEncoder.encode("独立完成", "utf-8")
				   + "&remark=" + URLEncoder.encode("", "utf-8") //备注
				   + "&requiredTime=" + URLEncoder.encode("3.5", "utf-8")
				   + "&startTime=" + URLEncoder.encode("08:30", "utf-8")
				   + "&userName=" + URLEncoder.encode("胡海洋", "utf-8")
				   );


*/

        //修改日志
		/*hcu.doPost(
			"http://www.btravelplus.com/tbi-erp/tbi/jobLog/editJobLog"
			,  "id=" + URLEncoder.encode("22761", "utf-8")
			+ "&confirmPerson=" + URLEncoder.encode("鲍学慧", "utf-8")
			+ "&department=" + URLEncoder.encode("研发部", "utf-8")
			+ "&endTime=" + URLEncoder.encode("12:00", "utf-8")
			+ "&job=" + URLEncoder.encode("整理安卓框架", "utf-8") //工作内容
			+ "&jobDate=" + URLEncoder.encode("2016-09-08", "utf-8")
			+ "&jobNo=" + URLEncoder.encode("123", "utf-8") //类型
			+ "&jobResult=" + URLEncoder.encode("进行中", "utf-8")
			+ "&jobStyle=" + URLEncoder.encode("写资料", "utf-8")
			+ "&jobType=" + URLEncoder.encode("研发", "utf-8")
			+ "&oneTwo=" + URLEncoder.encode("独立完成", "utf-8")
			+ "&remark=" + URLEncoder.encode("", "utf-8") //备注
			+ "&requiredTime=" + URLEncoder.encode("3.5", "utf-8") //时间差
			+ "&startTime=" + URLEncoder.encode("08:30", "utf-8")
			+ "&userName=" + URLEncoder.encode("胡海洋", "utf-8")
		
			
		
		);*/

        //修改日志状态
		/*
		hcu.doPost(
			"http://www.btravelplus.com/tbi-erp/tbi/jobLog/editJobLog"
			,  "id=" + URLEncoder.encode("22761", "utf-8")
			+ "&logStatus=" + URLEncoder.encode("1", "utf-8")
		);*/

    }




	public void doGetAny(String url, INetInvoke ni)
	{
		net(EType.get,url,null,ni);
	}
	public void doPostAny(String url, String parms, INetInvoke ni)
	{
		net(EType.get,url,parms,ni);
	}
	
	
	//网络访问
   	private String net(final EType type, String url, String parms, final INetInvoke ni)
	{

		//获取内容
		new AsyncTask<String, Object, String>()
		{
			//onPreExecute方法用于在执行后台任务前做一些UI操作
			@Override
			protected void onPreExecute()
			{
			}
			//doInBackground方法内部执行后台任务,不可在此方法内修改UI  更新进度调用publishProgress(100);
			@Override
			protected String doInBackground(String... urls)
			{
				String html=null;
				try
				{
					switch(type)
					{
						case get:
							doGet(urls[0]);
							break;
						case post:
							doPost(urls[0],urls[1]);
							break;
					}


					//String html =  VD.vnet.add(log[0]); //上传
					return html;

				}
				catch (Exception e)
				{}
				return null;
			}
			//onProgressUpdate方法用于更新进度信息
			@Override
			protected void onProgressUpdate(Object... progresses)
			{
			}
			//onPostExecute方法用于在执行完后台任务后更新UI,显示结果
			@Override
			protected void onPostExecute(String html)
			{
				ni.invoke(html);
			}
			//onCancelled方法用于在取消执行中的任务时更改UI
			@Override
			protected void onCancelled()
			{
			}
		}.execute(url,parms);

		return null;
	}

	//网络回调
	public interface INetInvoke
	{
		void invoke(String html);
	}
	public enum EType
	{
		get,post;
	}
	
	
}
