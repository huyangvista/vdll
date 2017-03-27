package vdll.math;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 根据属性排序 Bean列表
 * @author Hocean  @version 2016年9月6日 下午4:33:03.
 * 
 * @param <T>
 */
public class CompareUtil<T> {

	//用法
	public static void main(String args[]) {

		List<User> list = new ArrayList<>();
		list.add(new User(3, "qqq", 0.4d));
		list.add(new User(2, "www", 0.3f));
		list.add(new User(2, "eee", "0.2"));
		list.add(new User(4, "rrr", 0.1));

		//参数1:asc正序  desc倒序    参数2：对象的字段 （多个）
		CompareUtil.sort(list, ESort.asc,"id","num");

		for (User user : list) {
			System.err.println(user.getId() + " " + user.getName() + " " + user.getNum());
		}
	}

	/**
	 * 排序 规则
	 * @author Hocean  @version 2016年9月6日 下午4:33:23.
	 *
	 */
	public enum ESort {
		asc(1), desc(-1);
		private int value;		
		private ESort(int value)
		{
		    this.value = value;
		}

		public int value()
		{
		    return value;
		}
	}
	/**
	 * 排序 正序
	 * @param list
	 * @param filed
	 */
	public static <T> void asc(List<T> list, String... filed) {
		sort(list,ESort.asc,filed);
	}
	/**
	 * 排序 倒序
	 * @param list
	 * @param filed
	 */
	public static <T> void desc(List<T> list, String... filed) {
		sort(list,ESort.desc,filed);
	}
	/**
	 * 排序
	 * @param list
	 * @param ebuy
	 * @param filed
	 */
	public static <T> void sort(List<T> list, ESort esort, String... filed) {
		Collections.sort(list, Create(esort.value, filed));
	}

	/*
	 * sort 1正序 -1 倒序  filed 多字段排序
	 */
	public static <T> Comparator<? super T> Create(int sort, String... filed) {
		return new ImComparator<T>(sort, filed);
	}

	public static class ImComparator<T> implements Comparator<T> {
		public static int SORT_ASC = 1;
		public static int SORT_DESC = -1;

		private int sort = SORT_ASC; //默认 正序排列
		String[] filed;

		public ImComparator(int sort, String... filed) {
			this.sort = sort == -1 ? -1 : 1;
			this.filed = filed;
		}

		@Override
		public int compare(Object o1, Object o2) {
			int result = 0;
			for (String file : filed) {
				Object value1 = ReflexUtil.invokeMethod(file, o1);
				Object value2 = ReflexUtil.invokeMethod(file, o2);
				if (value1 == null || value2 == null) {
					continue;
				}
				/*if (!(value1 instanceof Integer) || !(value2 instanceof Integer)) {
				 continue;
				 }*/
				//int v1 ;
				//int v2 ;
				double v1;
				double v2;
				try {
					//v1 = Integer.valueOf(value1.toString());
					//v2 = Integer.valueOf(value2.toString());
					v1 = Double.valueOf(value1.toString());
					v2 = Double.valueOf(value2.toString());
				}
				catch (Exception e) {
					continue;
				}
				double vd = v1 - v2;
				if (vd == 0) continue;

				while (Math.abs(vd) < 1) {
					vd *= 10;
				}
				if (sort == 1) {
					return (int) vd;
				}
				else if (sort == -1) {
					return -(int) vd;
				}
				else {
					continue;
				}
			}
			return result;
		}
	}


	public static class ReflexUtil {

		//getMethod
		public static Object invokeMethod(String propertiesName, Object object) {
			try {
				if (object == null) return null;
				if (!propertiesName.contains(".")) {
					String methodName = "get" + getMethodName(propertiesName);
					Method method = object.getClass().getMethod(methodName);
					return method.invoke(object);
				}
				String methodName = "get" + getMethodName(propertiesName.substring(0, propertiesName.indexOf(".")));
				Method method = object.getClass().getMethod(methodName);
				return invokeMethod(propertiesName.substring(propertiesName.indexOf(".") + 1), method.invoke(object));

			}
			catch (Exception e) {
				return null;
			}
		}

		private static String getMethodName(String fildeName) {
			byte[] items = fildeName.getBytes();
			items[0] = (byte) ((char) items[0] - 'a' + 'A');
			return new String(items);
		}
	}
}




class User {

	private int id;
	private Object name;
	private Object num;

	public User(int id, Object name, Object num) {
		this.setId(id);
		this.name = name;
		this.setNum(num);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Object getName() {
		return name;
	}

	public void setName(Object name) {
		this.name = name;
	}

	public Object getNum() {
		return num;
	}

	public void setNum(Object num) {
		this.num = num;
	}





}



