package vdll.math;

import java.util.List;

public class RandomTest
{

	public RandomTest()
	{
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		int[] vi = vrandom(6,9);
		for (int i : vi)
		{
			vdll.debug.Console.WriteLine(i);
		}
	}
	
	//泛型列表随机
	public static <T> List<T> veRandom(List<T> vlist)
	{
	    for (int i = 0; i < vlist.size(); i++)
	    {
	        int vi1 = (int) (Math.random() * vlist.size());
	        int vi2 = (int) (Math.random() * vlist.size());
	        T vitem = vlist.get(vi1);
	        vlist.set(vi1, vlist.get(vi2));
	        vlist.set(vi2, vitem);	       
	    }
	    return vlist;
	}


	
    /// <summary>
    /// 不重复随机
    /// </summary>
    /// <param name="vn">需要数字数量</param>
    /// <param name="viMax">数字最值  必须大于需要数字</param>
    /// <returns>不重复随机数组</returns>
    public static int[] vrandom(int vn, int viMax) 
    {
    	if(vn > viMax)  {
    		vdll.debug.Console.ErrLine("数字最大值  必须大于需要数字");
    		return null;
    	}
    	
        int[] vai = new int[vn];  //结果数组
        int vindex = 0;  //完成序列
        while (true)
        {
            int viRandom = (int) (Math.random() * viMax);//Random.Range(0, viMax);  //随机数            
            int vitag;       //检测完成序列              
            for (vitag = 0; vitag < vindex; vitag++) if (vai[vitag] == viRandom) break;  //存在相同检测
            if (vitag >= vindex) //是否存在相同
            {
                vai[vindex] = viRandom; //保存此数据
                if (++vindex >= vn) break; //够了就退出了
            }            
        }       
        return vai;
    }

}
