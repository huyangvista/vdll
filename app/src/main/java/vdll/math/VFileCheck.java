package vdll.math;

import java.util.*;
import java.math.*;
import java.util.regex.*;
import java.io.*;

public class VFileCheck
{
	String vsPashGen= "/storage/emulated/0/";
	public static void main(String[] args)
	{
		new VFileCheck();
	}   


	public VFileCheck()
	{
  
		//转码测试
		//v16();
		//vceshi();
	    //vdraw();
		//vdictory(vsPashGen + "123/456/789");
		//vutf_gbk(vsPashGen + "aaa.java", vsPashGen + "bbb.java");
		//vdirList(vsPashGen + "ViveLenovo7");
		//vdirList(vsPashGen + "jiaoben1765");
		
		vdirLists(vsPashGen+"0NEW Web/jiaoben1765","0NEW Web/zhuanhuan","");
		System.out.println("ok");
	}

	private void vdirList(String vsdir)
	{
		//vdirLists(vsdir, "");

	}
	//递归文件夹 转换代码
	private void vdirLists(String vsdir,String vsc, String vscount)
	{
		vscount += " ";
		// TODO: Implement this method
		File f = new File(vsdir);
		String path =f.getPath();
		String pathCopy = path.replaceFirst("0NEW Web/jiaoben1765",vsc);

		if (f.isDirectory())
		{
			vdictory(pathCopy);
			
			//System.out.println(  vscount + "dire->" + f.getName());
			
			File[] fs = f.listFiles();
			for (File item : fs)
			{
				vdirLists(vsdir + "/" + item.getName(), vsc,vscount);
			}
			
			
		}
		if (f.isFile())
		{
			//System.out.println(  vscount +  "file --> " + f.getName());


			String vs = f.getName();
			//System.out.println(vs.substring(vs.length() - 5,  vs.length()  ));
			String vsend = vs.substring(vs.length() - 5,  vs.length()  );
			String vsende = vs.substring(vs.length() - 3,  vs.length()  );
			if(vsend.equals(".java")|| vsende.equals(".js"))
			{
				vutf_gbk(path, pathCopy);
			}
			else
			{
				vcopyByte(path, pathCopy);
			}

		}
		System.out.println(path);
		
		
		


	}

	private void vdictory(String vsdir)
	{
		// TODO: Implement this method

	    File f = new File(vsdir);
		if (f.exists())
		{

		}
		else
		{
			f.mkdirs();
		}
	}

	//单文件转换格式
	private void vutf_gbk(String vsutf8 , String vsgbk)
	{
		// TODO: Implement this method

		try
		{
			File fr = new File(vsutf8);  //"/storage/emulated/0/0NEW Android/aaa.java");
			//if(fr.exists()) fr.delete();
			//fr.createNewFile();
			File fw = new File(vsgbk);   //"/storage/emulated/0/0NEW Android/bbb.java");

			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fr), "GBK"));

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fw), "UTF-8"));
			//BufferedWriter vbw = new BufferedWriter(new FileWriter(f));

			//bw.write("eeeerre啦啦啦啦");

			char[] buf = new char[1024 * 2];
			int len;
			while ((len = br.read(buf)) > 0)
			{
				bw.write(buf, 0, len);
			}


			br.close();
			bw.flush();
			bw.close();

		}
		catch (IOException e)
		{

			System.out.println(e);
		}
	}

	//直接复制文件
	private void vcopy(String vsutf8 , String vsgbk)
	{
		// TODO: Implement this method

		try
		{
			File fr = new File(vsutf8);  //"/storage/emulated/0/0NEW Android/aaa.java");
			//if(fr.exists()) fr.delete();
			//fr.createNewFile();
			File fw = new File(vsgbk);   //"/storage/emulated/0/0NEW Android/bbb.java");

			BufferedReader br = new BufferedReader(new FileReader(fr));     //new InputStreamReader(new FileInputStream(fr), "UTF-8"));

			BufferedWriter bw = new BufferedWriter(new FileWriter(fw));      //*new OutputStreamWriter(new FileOutputStream(fw), "GBK"));
			//BufferedWriter vbw = new BufferedWriter(new FileWriter(f));

			//bw.write("eeeerre啦啦啦啦");

			char[] buf = new char[1024 * 2];
			int len;
			while ((len = br.read(buf)) > 0)
			{
				bw.write(buf, 0, len);
			}


			br.close();
			bw.flush();
			bw.close();

		}
		catch (IOException e)
		{

			System.out.println(e);
		}
	}
	
	//主动设置代码转换
	private void vcopyByte(String vsutf8 , String vsgbk)
	{
		// TODO: Implement this method

		try
		{
			File fr = new File(vsutf8);  //"/storage/emulated/0/0NEW Android/aaa.java");
			//if(fr.exists()) fr.delete();
			//fr.createNewFile();
			File fw = new File(vsgbk);   //"/storage/emulated/0/0NEW Android/bbb.java");

			
			
			BufferedInputStream br = new BufferedInputStream(new FileInputStream(fr));     //new InputStreamReader(new FileInputStream(fr), "UTF-8"));

			BufferedOutputStream bw = new BufferedOutputStream(new FileOutputStream(fw));      //*new OutputStreamWriter(new FileOutputStream(fw), "GBK"));
		

			
			byte[] buf = new byte[1024 * 2];
			int len;
			while ((len = br.read(buf)) > 0)
			{
				bw.write(buf, 0, len);
			}


			br.close();
			bw.flush();
			bw.close();

		}
		catch (IOException e)
		{

			System.out.println(e);
		}
	}
	
	//以下为进制转换*************************
	private void vdraw()
	{
		// TODO: Implement this method
	}
	public void vdic()
	{

		while (true)
		{


			System.out.println("进制转换");
			System.out.print("输入整形数据: ");



			Scanner input = new Scanner(System.in);

			String vsin = input.nextLine();


			if (vsin.equals("exit")) break;

			Integer vin = Integer.parseInt(vsin);

			String vs6 = Integer.toString(vin, 6);

			int vi10 = Integer.parseInt(vs6, 6);

			System.out.println("10i -> 6s  " + vs6);
			System.out.println("6s -> 10i  " + vi10);
			System.out.println();



		}
	}


	public void vdicb()
	{
		while (true)
		{ 
			System.out.println("进制转换");
			System.out.print("输入整形数据: ");

			Scanner input = new Scanner(System.in);
			String vsin = input.nextLine();

			if (vsin.equals("exit")) break;

			String vs6 = new BigInteger(vsin).toString(6);
			String vs10 = new BigInteger(vs6, 6).toString();

			System.out.println("10 -> 6  " + vs6);
			System.out.println("6 -> 10  " + vs10);
			System.out.println();

		}
	}

	public void v16()
	{

		while (true)
		{ 
			System.out.println("进制转换");
			System.out.print("输入整形数据: ");

			Scanner input = new Scanner(System.in);
			String vsin = input.nextLine();


			Pattern pattern = Pattern.compile("^[0-9a-zA-Z]+$");
			if (!pattern.matcher(vsin).matches()) 
			{
				System.err.println("!=> 输入错误！");
				System.err.println();
				continue;
			}

			if (vsin.equals("exit")) break;

			String vs6 = new BigInteger(vsin, 36).toString(6);
			String vs10 = new BigInteger(vs6, 6).toString(36);

			System.out.println("36 -> 6  " + vs6);
			System.out.println("6 -> 36  " + vs10);
			System.out.println();



		}
	}


	public void vceshi()
	{
		String[] vss= new String[]{"a","b","c"};

		vshuzu(vss);

		System.out.println(vss[0]);
		System.out.println(vss[1]);
	}
	public void vshuzu(String[] vss)
	{
		vss[0] = "123456";
		vss = new String[3];
		vss[0] = "66666";
		vss[1] = "77777";
	}
	
	BigInteger bi;
	public BigInteger  _jie(BigInteger nbi) {
		if(nbi.equals(BigInteger.ZERO))
		{
			return bi;
		}
		bi = bi.multiply(nbi);
		nbi = nbi.subtract(BigInteger.ONE);
		return  _jie(nbi);
		
	}
	public BigInteger  jie(String snbi) {
		bi = BigInteger.ONE;
		return _jie(new BigInteger(snbi));
		
	}
	
	
	


}
