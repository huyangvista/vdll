package android;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import vdll.net.GetPostUtil;
import vdll.net.UploadUtil;
import vdll.utils.Base64;


public class VMain
{//32
	String vsPashGen= "/storage/emulated/0/0NEW Android/";


	public static void main(String[] args)
	{

		new VMain();
	}
	
static
{
	//new VMain();
}




	public VMain()
	{
		start();
		Scanner vsca = new Scanner(System.in);
		String vin="";
		System.out.println("Console => e exit, p pos, u upload");
		while (vin != "exit")
		{
			vin = vsca.nextLine();
			switch (vin)
			{
				case "p":
					vpos();
					break;
				case "u":
					upload();
					break;
				case "e":
					vin = "exit";
					break;
				default:
					System.out.println("没有指令");
					break;

			}
		}

		System.out.println("结束");
	}

	private void start()
	{
		Date date = new Date();
		int u = date.getDay();



	}
	
	void VCTcp()
	{
		try
		{

			Socket server=new Socket(InetAddress.getByName("123.206.23.166"), 5438);

			BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			PrintWriter out = new PrintWriter(server.getOutputStream());
			BufferedReader wt = new BufferedReader(new InputStreamReader(System.in));
			while (true) {

				String str = wt.readLine();
				//str = "a::D:/,,b::tt.mp3,,c::" + Base64.encodeBase64(str.getBytes("utf-8"));
				str = "a::D:/,,b::tt.mp3,,c::" + Base64.encode(str.getBytes("utf-8"));  //eclipse 不支持

				out.println(str);
				out.flush();
				if (str.equals("end")) {
					break;
				}
				System.out.println(in.readLine());
				Thread.sleep(2000);
			}
			server.close();

		}
		catch (Exception e)
		{
			System.out.println(e);

		}
	}
	
	public void bendiSeever(){
		// TODO: Implement this method
        try
		{
			final JasonHttpServer jhs = new JasonHttpServer( 12345);


			jhs.start();

			new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						JSONObject jo = new JSONObject();
						try
						{
							jo.put("action", "ddd");
							jo.put("object", "ddd");
						}
						catch (JSONException e)
						{}
						//String Stris= GetPostUtil.sendPost("http://127.0.0.1:12345", jo.toString());
						String Stris= GetPostUtil.sendPost("http://127.0.0.1:12345", "");//jo.toString());
						//String Stris= GetPostUtil.sendGet("http://127.0.0.1:12345", jo.toString());
						System.out.println(Stris);


						//jhs.stop();
					}
				}).start();


		}
		catch (  Exception ee)
		{

			System.out.println(ee);

		}
		
	}

	void vpos()
	{
		new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					String Stris= GetPostUtil.sendPost("http://123.206.23.166:1080/vpost/index.php", "user=crazyit&password=leegang");
					System.out.println(Stris);
				}
			}).start();
	}

	void upload()
	{
		new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					UploadUtil.uploadFile(new File(vsPashGen + "android.jpg"),   "http://123.206.23.166/vupdateload/index.php");

					//UploadUtil.uploadFile(new File(vsPashGen + "vivelenovo7.zip"),   "http://123.206.23.166/vupdateload/index.php");
				}
			}).start();
	}
	
	public void time()
	{
		//System.out.println(Integer.MAX_VALUE);
		long l = System.currentTimeMillis();
		ArrayList<byte[]> liat = new ArrayList<>();
		for (int i = 0;i < 100;i++)
		{
			System.out.print(i);
			byte[] bs = new byte[1024 * 1024 * 1];
			liat.add(bs);
		}
		long ll = System.currentTimeMillis();
		System.out.println(ll - l);
		System.out.println(ll - l);
	}





}



/*
					 try
					 {
					 // TODO: Implement this method
					 String server = "123.206.23.166";      // 服务器IP
					 // 发送的数据
					 byte data[] ={0x3F,0x40,0x05,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0X00,0x00,0X00,0X00,0x05,0x24,0x78};

					 byte[] recData=new byte[100];//接收数据缓冲
					 int servPort = 6666;//端口
					 Socket socket = new Socket(server, servPort);
					 System.err.println("Vive Connected to server...sending command string");
					 InputStream in = socket.getInputStream();
					 OutputStream out = socket.getOutputStream();
					 out.write(data);  // 发送


					 // 接收数据
					 int totalBytesRcvd = 0;  // Total bytes received so far

					 totalBytesRcvd = in.read(recData);//接收
					 String hexString="";


					 for (int i=0;i < totalBytesRcvd;i++)
					 hexString += Integer.toHexString(0xff & recData[i]) + ",";//转成十六进制显示
					 System.out.println("Received: " + hexString);
					 socket.close();  // Close the socket and its streams
					 }
					 catch (Exception e)
					 {
					 System.out.println(e);
					 }*/
