package vdll.math;

import java.util.HashMap;

import vdll.math.VGuiBing.ESort;

class VGMain
{
	public VGMain()
	{
		//归并排序  不用递归
		String[] a = { "9", "8", "7", "1", "1", "4", "3", "2", "8" };
		a = VGuiBing.vuniq(a); //去重复
		Object[] aux = VGuiBing.vsort(a, ESort.desc); //从大到小
		for (int i = 0; i < a.length; i++)
		{
			System.out.print(aux[i] + "  ");
		}
		System.out.println();
		
		//归并排序  递归实现
		Integer[] data = new Integer[] { 5, 3, 6, 2, 1, 9, 4, 8, 7 };
		VGuiBing.print(data);
		VGuiBing.mergeSort(data, ESort.asc); //从大到小
		System.out.println("排序后的数组：");
		VGuiBing.print(data);
	}
}

public class VGuiBing
{
	public static void main(String[] args)
	{
		new VGMain();
	}

	public enum ESort
	{
		asc, //默认 从小到大
		desc, //从大到小
	}

	/**
	 * 	每个拆分的列表元素个数<=3
	 */
	private static final int INSERTIONSORT_THRESHOLD = 3;

	/**
	 * 
	 * 归并排序(非递归实现)
	 */
	private static void mergeSort(Object[] src, Object[] dest, ESort es)
	{
		int spreCount = INSERTIONSORT_THRESHOLD;
		int low, mid, high;
		int len = src.length;
		if (len <= INSERTIONSORT_THRESHOLD * 2)
		{ //如果只能划分为两组，直接排序
			sort(dest, 0, len, es);
			return;
		}
		while (spreCount < len)
		{
			for (int i = 0; i < len; i = high)
			{ //依次排序归并相邻两个列表
				low = i;
				high = low + spreCount * 2;
				mid = low + spreCount;
				if (high >= len)
				{
					high = len;
				}
				int l = high - low;
				if (l <= INSERTIONSORT_THRESHOLD)
				{
					sort(src, low, high, es);
					break;
				}

				if (spreCount == 3)
				{ //所有拆分的列表只进行一次排序
					sort(dest, low, mid, es);
					sort(dest, mid, high, es);
				}
				if (l == len) //最后一次归并把src有次序的赋给dest
					Merge(src, dest, low, mid, high, es);
				else Merge(dest, src, low, mid, high, es);

			}
			spreCount *= 2;
		}

	}

	//对拆分的每个列表进行排序
	@SuppressWarnings("unchecked")
	private static void sort(Object[] dest, int low, int high, ESort es)
	{
		for (int i = low; i < high; i++)
		{
			for (int j = i; j > low; j--)
			{
				int viq = ((Comparable<Object>) dest[j - 1]).compareTo(dest[j]);
				boolean vb = false;
				switch (es)
				{
					case asc:
						vb = viq > 0;
						break;
					case desc:
						vb = viq <= 0;
						break;
					default:
						break;
				}

				if (vb)
				{
					swap(dest, j - 1, j);
				}
			}
		}
	}

	//归并相邻两个列表并保存在dest中
	@SuppressWarnings("unchecked")
	private static void Merge(Object[] src, Object[] dest, int low, int mid, int high, ESort es)
	{
		int i = low;
		int p = low; //第一个列表指针
		int q = mid; //第二个列表指针
		while (p < mid && q < high)
		{
			int viq = ((Comparable<Object>) src[p]).compareTo(src[q]);
			boolean vb = false;
			switch (es)
			{
				case asc:
					vb = viq <= 0;
					break;
				case desc:
					vb = viq > 0;
					break;
				default:
					break;
			}

			if (vb)
			{
				dest[i++] = src[p++];
			}
			else
			{
				dest[i++] = src[q++];
			}
		}
		//添加剩余的值
		while (p < mid && i < high)
		{
			dest[i++] = src[p++];
		}
		while (q < high && i < high)
		{
			dest[i++] = src[q++];
		}

	}

	//交换
	private static void swap(Object[] x, int a, int b)
	{
		Object t = x[a];
		x[a] = x[b];
		x[b] = t;
	}

	/**
	 * 去重复
	 * @param nums
	 * @return
	 */
	public static String[] vuniq(String[] nums)
	{
		String s[] = nums;
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		for (String ss : s)
		{
			hm.put(ss.toLowerCase(), 1); //用hashmap除去重复的值
		}
		int vi = 0;
		nums = new String[hm.size()];
		for (String ss : hm.keySet())
		{
			nums[vi++] = ss;
		}
		return nums;
	}

	public static Object[] vsort(Object[] vos)
	{
		return vsort(vos,ESort.asc);
	}

	public static Object[] vsort(Object[] vos, ESort es)
	{
		Object[] auxl = vos.clone(); //复制			
		Object[] aux = vos.clone(); //复制			
		mergeSort(auxl, aux, es); //排序
		return aux;
	}

	//方法 2 *************************************************************************************

	public static void mergeSort(Object[] data)
	{
		sort2(data, 0, data.length - 1, ESort.asc);
	}

	public static void mergeSort(Object[] data, ESort es)
	{
		sort2(data, 0, data.length - 1, es);
	}

	public static void sort2(Object[] data, int left, int right, ESort es)
	{
		if (left >= right) return;
		// 找出中间索引  
		int center = (left + right) / 2;
		// 对左边数组进行递归  
		sort2(data, left, center, es);
		// 对右边数组进行递归  
		sort2(data, center + 1, right, es);
		// 合并  
		merge(data, left, center, right, es);
		// print(data);  
	}

	public static void merge(Object[] data, int left, int center, int right, ESort es)
	{
		// 临时数组  
		Object[] tmpArr = new Object[data.length];
		// 右数组第一个元素索引  
		int mid = center + 1;
		// third 记录临时数组的索引  
		int third = left;
		// 缓存左数组第一个元素的索引  
		int tmp = left;
		while (left <= center && mid <= right)
		{
			// 从两个数组中取出最小的放入临时数组  			
			@SuppressWarnings("unchecked")
			int viq = ((Comparable<Object>) data[left]).compareTo(data[mid]);
			boolean vb = false;
			switch (es)
			{
				case asc:
					
					vb = viq < 0;
					break;
				case desc:
					vb = viq > 0;	
					break;
				default:
					break;
			}
			if (vb)
			{
				tmpArr[third++] = data[left++];
			}
			else
			{
				tmpArr[third++] = data[mid++];
			}
		}
		// 剩余部分依次放入临时数组（实际上两个while只会执行其中一个）  
		while (mid <= right)
		{
			tmpArr[third++] = data[mid++];
		}
		while (left <= center)
		{
			tmpArr[third++] = data[left++];
		}
		// 将临时数组中的内容拷贝回原数组中  
		// （原left-right范围的内容被复制回原数组）  
		while (tmp <= right)
		{
			data[tmp] = tmpArr[tmp++];
		}
	}

	public static void print(Object[] data)
	{
		for (int i = 0; i < data.length; i++)
		{
			System.out.print(data[i] + "\t");
		}
		System.out.println();
	}

	
}

/*
 	public static void main(String[] args) {  
        int[] data = new int[] { 5, 3, 6, 2, 1, 9, 4, 8, 7 };  
        print(data);  
        mergeSort(data);  
        System.out.println("排序后的数组：");  
        print(data);  
    }  
  
    public static void mergeSort(int[] data) {  
        sort(data, 0, data.length - 1);  
    }  
  
    public static void sort(int[] data, int left, int right) {  
        if (left >= right)  
            return;  
        // 找出中间索引  
        int center = (left + right) / 2;  
        // 对左边数组进行递归  
        sort(data, left, center);  
        // 对右边数组进行递归  
        sort(data, center + 1, right);  
        // 合并  
        merge(data, left, center, right);  
       // print(data);  
    }  
  

    public static void merge(int[] data, int left, int center, int right) {  
        // 临时数组  
        int[] tmpArr = new int[data.length];  
        // 右数组第一个元素索引  
        int mid = center + 1;  
        // third 记录临时数组的索引  
        int third = left;  
        // 缓存左数组第一个元素的索引  
        int tmp = left;  
        while (left <= center && mid <= right) {  
            // 从两个数组中取出最小的放入临时数组  
            if (data[left] <= data[mid]) {  
                tmpArr[third++] = data[left++];  
            } else {  
                tmpArr[third++] = data[mid++];  
            }  
        }  
        // 剩余部分依次放入临时数组（实际上两个while只会执行其中一个）  
        while (mid <= right) {  
            tmpArr[third++] = data[mid++];  
        }  
        while (left <= center) {  
            tmpArr[third++] = data[left++];  
        }  
        // 将临时数组中的内容拷贝回原数组中  
        // （原left-right范围的内容被复制回原数组）  
        while (tmp <= right) {  
            data[tmp] = tmpArr[tmp++];  
        }  
    }  
  
    public static void print(int[] data) {  
        for (int i = 0; i < data.length; i++) {  
            System.out.print(data[i] + "\t");  
        }  
        System.out.println();  
    } */
