package vdll.tools;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 根据属性对比列表      排序对象列表List<T> 或  集合列表List<Map<K, V>>    (int String Object 类型的字段  均可进行排序)
 * @author Hocean  @version 2016年9月6日 下午4:33:03.
 * 
 * @param <T>
 */
public class CompareUtil<T> {

	public static void main(String args[]) {
		//Demo
		CompareUtilTest.main(null);
	}

	/**
	 * 排序 规则
	 * @author Hocean  @version 2016年9月6日 下午4:33:23.
	 *
	 */
	public enum ESort {
		/*缺省*/def(0), /* 正序*/asc(1), /*倒序*/desc(-1);
		int value;
		private ESort(int v) {
			value = v;
		}
		public int get(){
			return value;
		}
	}

	/**
	 * 排序 缺省
	 * @param list
	 * @param filed
	 */
	public static <T> void sort(List<T> list, String... filed) {
		sort(list, ESort.def, filed);
	}

	/**
	 * 排序 正序
	 * @param list
	 * @param filed
	 */
	public static <T> void sortAsc(List<T> list, String... filed) {
		sort(list, ESort.asc, filed);
	}

	/**
	 * 排序 倒序
	 * @param list
	 * @param filed
	 */
	public static <T> void sortDesc(List<T> list, String... filed) {
		sort(list, ESort.desc, filed);
	}

	/**
	 * 排序
	 * @param list
	 * @param ebuy
	 * @param filed
	 */
	public static <T> void sort(List<T> list, ESort ebuy, String... filed) {
		sort(list, ebuy.get(), filed);
	}
		
	/**
	 * 排序  ASC = 1; DESC = -1;
	 * @param list
	 * @param sort
	 * @param filed
	 */
	public static <T> void sort(List<T> list, int sort, String... filed) {
		Collections.sort(list, new ImComparator<T>(sort, filed));
	}
	
	/**
	 * 复杂排序
	 * @param list
	 * @param ebuy
	 * @param filed
	 */
	public static <T> void sort(List<T> list, ESort[] ebuy, String[] filed) {
		int[] sorts = new int[ebuy.length];
		for (int i = 0; i < sorts.length; i++) {
			sorts[i] = ebuy[i].get();
		}		
		Collections.sort(list, new ImComparator<T>(sorts, filed));
	}
	
	/**
	 * 复杂排序
	 * @param list
	 * @param ebuy
	 * @param filed
	 */
	public static <T> void sort(List<T> list, Map<String, ESort> map) {
		int len = map.size();
		String[] filed = new String[len];
		ESort[] ebuy = new ESort[len];		
		Iterator<Entry<String, ESort>> it = map.entrySet().iterator();
		int count = 0;
		while (it.hasNext() && count < len) {
			Entry<String, ESort>  en= it.next();
			filed[count] = en.getKey();
			ebuy[count] = en.getValue();			
			count++;
		}
		CompareUtil.sort(list, ebuy, filed);
	}
	

	/**
	 * 复杂排序
	 * @param list
	 * @param ebuy
	 * @param filed
	 */
	public static <T> void sortSF(List<T> list, String... sf) {
		int len = sf.length / 2;
		ESort[] ebuy = new ESort[len];
		String[] filed = new String[len];		
		for (int i = 0; i < len; i++) {
			ebuy[i] = ESort.valueOf(sf[i * 2]);
			filed[i] = sf[i * 2 + 1];
		}
		CompareUtil.sort(list, ebuy, filed);
	}
	
	/**
	 * 实现 排序规则
	 * @author Hocean  @version 2016年9月6日 下午4:33:03.
	 * 
	 * @param <T>
	 */
	private static class ImComparator<T> implements Comparator<T> {
		public static int SORT_ASC = 1;
		@SuppressWarnings("unused")
		public static int SORT_DESC = -1;

		private int sort = SORT_ASC; //默认 正序排列
		
		private String[] filed = null;
		private int[] sorts = null;
		public ImComparator(int sort, String... filed) {
			this.sort = sort;
			this.filed = filed;
		}
		public ImComparator(int[] sorts, String[] filed) {
			this.sorts = sorts;
			this.filed = filed;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public int compare(Object o1, Object o2) {
			int result = 0;
			for (int i = 0; i < filed.length; i++) {
				String file = filed[i];
				Object value1 = null;
				Object value2 = null;
				if (o1 instanceof Map && o2 instanceof Map) {
					value1 = ((Map) o1).get(file);
					value2 = ((Map) o2).get(file);
				}
				else {
					value1 = ReflexUtil.invokeMethod(file, o1);
					value2 = ReflexUtil.invokeMethod(file, o2);
				}
				if (value1 == null || value2 == null) {
					continue;
				}
				double v1;
				double v2;
				try {
					v1 = Double.valueOf(value1.toString());
					v2 = Double.valueOf(value2.toString());
				}
				catch (Exception e) {
					// TODO: handle exception
					continue;
				}
				double vd = v1 - v2;
				if (vd == 0) continue;
				while (Math.abs(vd) < 1) {
					vd *= 10;
				}
				if(sorts != null &&  i < sorts.length){
					if(sorts[i] == 0) continue;
					return (int)(sorts[i] * vd);
				}
				return (int)(sort * vd);
			}
			return result;
		}
	}
	
	/**
	 * 反射属性      更多的反射请参见    当前包d.ReflectUtil
	 * @author Hocean  @version 2016年9月6日 下午3:10:36.
	 *
	 */
	private static class ReflexUtil {
		/**
		 * 反射 对象
		 * @param propertiesName
		 * @param object
		 * @return
		 */
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
		/**
		 * 得到一般驼峰命名法的  名字
		 * @param fildeName
		 * @return
		 */
		private static String getMethodName(String fildeName) {
			byte[] items = fildeName.getBytes();
			items[0] = (byte) ((char) items[0] - 'a' + 'A');
			return new String(items);
		}

	}
}


/**
 * 测试使用
 * @author Hocean  @version 2016年9月6日 下午3:10:36.
 *
 */
class User {
	
	private Object id;
	private Object num;
	private Object name;

	public User(Object id,Object num, Object name) {
		this.id = id;
		this.num = num;
		this.name = name;
	}

	public Object getId() {
		return id;
	}

	public void setId(Object id) {
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

/**
* 排序对象列表List<?> 或  集合列表List<Map<?, ?>>    (String Object 类型的字段  也可以进行排序)
* @author Hocean  @version 2016年9月6日 下午3:17:46.
* 
*/
class CompareUtilTest {

	//Demo
	public static void main(String args[]) {

		////排序对象列表
		List<User> list = new ArrayList<>();
		list.add(new User(1, "1", 0.1));
		list.add(new User(2, "3", 0.3));
		list.add(new User(4, "1", 0.2));
		list.add(new User(2, "2", 0.4));
		System.out.println("对象排序   依次按照 id num name 倒序排序  =>");
		CompareUtil.sort(list, CompareUtil.ESort.desc, "id", "num", "name");
		for (User user : list) {
			System.out.println(user.getId() + " " + user.getNum() + " " + user.getName());
		}System.out.println();		
		
		////排序集合列表
		List<Map<String, Object>> lists = new ArrayList<>();		
		Map<String, Object> map = new HashMap<>();
		map.put("id", 1); map.put("num", "1"); map.put("name", 0.1); lists.add(map);  map = new HashMap<>();						
		map.put("id", 2); map.put("num", "3"); map.put("name", 0.3); lists.add(map);  map = new HashMap<>();	
		map.put("id", 4); map.put("num", "1"); map.put("name", 0.3); lists.add(map);  map = new HashMap<>();	
		map.put("id", 3); map.put("num", "3"); map.put("name", 0.4); lists.add(map); 
		System.out.println("MAP排序   依次按照 id num name 正序排序  =>");
		CompareUtil.sort(lists, CompareUtil.ESort.asc, "id", "num", "name");
		for (Map<String, Object> maps : lists) {
			System.out.println(maps.get("id") + " " + maps.get("num") + " " + maps.get("name"));
		}System.out.println();
		
		////复杂排序   
		System.out.println("复杂排序     =>");
		System.out.println("  方法一   MAP排序   按num正序  如果相等  再按name倒序  =>");
		CompareUtil.sort(lists, new CompareUtil.ESort[]{CompareUtil.ESort.asc, CompareUtil.ESort.desc}, new String[]{"num", "name"});
		for (Map<String, Object> maps : lists) {
			System.out.println("  " + maps.get("id") + " " + maps.get("num") + " " + maps.get("name"));
		}System.out.println();		
		
		System.out.println("  方法二  MAP排序   按num正序  如果相等  再按name倒序  =>");
		Map<String, CompareUtil.ESort> ms = new HashMap<>(); ms.put("num", CompareUtil.ESort.asc); ms.put("name", CompareUtil.ESort.desc);
		CompareUtil.sort(lists, ms);
		for (Map<String, Object> maps : lists) {
			System.out.println("  " + maps.get("id") + " " + maps.get("num") + " " + maps.get("name"));
		}System.out.println();
		
		System.out.println("  方法三  MAP排序   按num正序  如果相等  再按name倒序  =>");
		CompareUtil.sortSF(lists, "asc", "num", "desc", "name");
		for (Map<String, Object> maps : lists) {
			System.out.println("  " + maps.get("id") + " " + maps.get("num") + " " + maps.get("name"));
		}System.out.println();
	}
}
