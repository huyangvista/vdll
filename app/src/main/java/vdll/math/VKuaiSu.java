package vdll.math;

import java.util.*;

class VMainS
{
	public VMainS()
	{
		VKuaiSu vsort = new VKuaiSu();
		System.out.println("正在排序中...");
		int mode = 100;
		int[] varr = vsort.vrandom(20000);
		int[] varr2 =new int[varr.length];
		System.arraycopy(varr,0,varr2,0,varr2.length);
		vsort.vprintArr("随机产生的数组共 "+varr.length+" 个数据 抽样->: ",varr2,mode);

		long st0  = System.currentTimeMillis();
		vsort.vkuaisu(varr2);
		long st1 = System.currentTimeMillis();
		System.out.println("快速用时: "+(st1 - st0) +"ms");
		vsort.vprintArr("快速排序结果 抽样->: ",varr2,mode);

		st0  = System.currentTimeMillis();
		vsort.vmaopao(varr);
		st1  = System.currentTimeMillis();
		System.out.println("冒泡用时: "+(st1 - st0) +"ms");
		vsort.vprintArr("冒泡排序结果 抽样->: ",varr2,mode);

		vsort.veq(varr,varr2);
	}
}

public class VKuaiSu
{
	public static void main(String[] args)
	{
		new VMainS();
	}
	
	
	public VKuaiSu()
	{
//		System.out.println("正在排序中...");
//		int mode = 100;
//		int[] varr = vrandom(20000);
//		int[] varr2 =new int[varr.length];
//		System.arraycopy(varr,0,varr2,0,varr2.length);
//		vprintArr("随机产生的数组共 "+varr.length+" 个数据 抽样->: ",varr2,mode);
//
//		long st0  = System.currentTimeMillis();
//		vkuaisu(varr2);
//		long st1 = System.currentTimeMillis();
//		System.out.println("快速用时: "+(st1 - st0) +"ms");
//		vprintArr("快速排序结果 抽样->: ",varr2,mode);
//
//		st0  = System.currentTimeMillis();
//		vmaopao(varr);
//		st1  = System.currentTimeMillis();
//		System.out.println("冒泡用时: "+(st1 - st0) +"ms");
//		vprintArr("冒泡排序结果 抽样->: ",varr2,mode);
//
//		veq(varr,varr2);
	}

	//数组填值
	public int[] vrandom(int count)
	{
		int[] varr = new int[count];
		for (int i=0;i < varr.length;i++)
		{
			varr[i] = i * 3;
		}
		vserSet(varr);
		//vprintArr("随机的数组:", varr);
		return varr;
	}

	//打印数组
	public void vprintArr(String vs, int[] varr)
	{
		System.out.print(vs);
		for (int i=0;i < varr.length;i++)
		{

			System.out.print(varr[i] + " ");

		}
		System.out.println();

	}
	//打印数组
	public void vprintArr(String vs, int[] varr,int coun)
	{
		System.out.print(vs);
		for (int i=0;i < varr.length;i+= varr.length / coun)
		{

			System.out.print(varr[i] + " ");

		}
		System.out.println();

	}

	//冒泡
	public void vmaopao(int[] varr)
	{
		//int[] varr = vrandom(10);

		//System.out.println("排序后");
		for (int i=0;i < varr.length;i++)
		{
			for (int j=varr.length - 1;j > i;j--)
			{
				if (varr[j] > varr[j - 1])
				{
					int item = varr[j];
					varr[j] = varr[j - 1];
					varr[j - 1] = item;
				}

			}
			//System.out.print(".");
		}
		//vprintArr("冒泡:", varr);
	}

	//随机交换数组
	public  void vser(int[] varr)
	{
		for (int i=0;i < varr.length;i++)
		{
			for (int j=varr.length - 1;j > i;j--)
			{
				int ran = new Random().nextInt(varr.length);
				int item = varr[j];
				varr[j] = varr[ran];
				varr[ran] = item;
			}

		}
	}

    //随机赋值数组
	public  void vserSet(int[] varr)
	{
		for (int i=0;i < varr.length;i++)
		{
			int ran = new Random().nextInt(10000);
			varr[i] = ran;
		}
	}
	//快速排序准备
	public void vkuaisu(int[] varr)
	{
		//int[] varr = vrandom(10);
		vquick(varr, 0, varr.length -1);
		//vprintArr("快速:", varr);
	}

	//快速排序
	public void vquick(int[] varr, int left, int right)
	{
		int l = left;
		int r = right;
		int key = varr[l];
		while (l < r)
		{
			while (l < r && varr[r] <= key) r--;
			if (l < r)
			{
				int item = varr[l];
				varr[l] = varr[r];
				varr[r] = item;
				l++;
			}

			while (l < r && varr[l] >= key) l++;
			if (l < r)
			{
				int item = varr[l];
				varr[l] = varr[r];
				varr[r] = item;
				r--;
			}

	  	}
		if (l > left) vquick(varr, left, l - 1);
		if (r < right) vquick(varr, r + 1, right);


	}
	public boolean veq(int[] vl, int[] vr)
	{
		boolean vb=true;
		for (int i=0;i < vl.length;i++)
		{
			if(vl[i] == vr[i])
			{

			}
			else
			{
				vb= false;
				break;
			}

		}
		if(vb) System.out.println("两次排序结果 相同。");
		else System.out.println("两次排序结果 不同。");
		return vb;
	}

	public void go(Action act)
	{
		act.go();
	}

}

interface Action
{
	public void go();
}
