package vdll.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class VReflect
{
	public static void main(String[] args)
	{
		VReflectObject.main(args);

	}

	/**
	 * 获得注解
	 * @param cls
	 * @param ctag
	 * @return
	 */
	public static <T extends Annotation> List<T> getTag(Class<?> cls, Class<T> ctag)
	{
		List<T> lt = new ArrayList<>();
		Field[] fields = VReflect.getFields(cls);
		for (Field field : fields)
		{
			if (field.isAnnotationPresent(ctag))
			{
				T t = (T) field.getAnnotation(ctag);
				lt.add(t);
			}
		}
		/*T[] ts = (T[])Array.newInstance(cls, lt.size());
		for (int i = 0; i < ts.length; i++)
		{
			ts[i] = lt.get(i);
		}*/
		return lt;
	}

	/**
	 * 得到对象的所有字段
	 * @param o
	 * @return
	 */
	public static Field[] getFields(Object o)
	{
		Class<?> cls = o.getClass(); //反射		
		return getFields(cls);
	}

	/**
	 * 得到类的所有字段
	 * @param cls
	 * @return
	 */
	public static Field[] getFields(Class<?> cls)
	{
		//Class<?> c = o.getClass(); //反射
		Field[] fields = cls.getDeclaredFields(); //属性
		for (int i = 0; i < fields.length; i++)
		{
			fields[i].setAccessible(true); //反射私有属性 权限
			/*Field field = fields[i]; //第几个参数
			Class<?> type = field.getType(); //反射类型
			String typeName = type.getName();//得到类型
			System.out.println(typeName);
			String fieldName = field.getName(); //反射字段
			System.out.println(fieldName); //字段名
			tableRow[i] = fieldName;
			try
			{
				Object value = field.get(t); //字段值
				System.out.println(value);
			
			}
			catch (IllegalArgumentException | IllegalAccessException e1)
			{
				e1.printStackTrace();
			}*/

		}
		return fields;
	}

	/**
	 * 得到对象的字段
	 * @param o
	 * @param fieldName
	 * @return
	 */
	public static Field getFields(Object o, String fieldName)
	{
		Class<?> cls = o.getClass(); //反射
		return getFields(cls, fieldName);
	}

	/**
	 * 得到类的字段
	 * @param cls
	 * @return
	 */
	public static Field getFields(Class<?> cls, String fieldName)
	{
		//Class<?> c = o.getClass(); //反射
		Field fields = null;
		try
		{
			fields = cls.getDeclaredField(fieldName);
			fields.setAccessible(true); //反射私有属性 权限			
		}
		catch (NoSuchFieldException | SecurityException e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} //属性

		return fields;
	}

	/**
	 * 新建实例
	 * @param className 类名
	 * @param args 构造函数的参数
	 * @return新建的实例
	 * @throws Exception
	 */
	public static Object newInstance(Class<?> newoneClass, Object[] args)
	{
		//Class newoneClass = Class.forName(className);
		Class<?>[] argsClass = new Class[args.length];

		for (int i = 0, j = args.length; i < j; i++)
		{
			argsClass[i] = args[i].getClass();
		}
		Object vo = null;
		try
		{
			Constructor<?> cons = newoneClass.getConstructor(argsClass);
			vo = cons.newInstance(args);
		}
		catch (Exception e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return vo;
	}

	/**
	得到某个对象的属性
	@param	 owner, fieldName
	@return	 该属性对象
	@throws	 Exception
	*/
	public static Object getProperty(Object owner, String fieldName) throws Exception
	{
		Class<?> ownerClass = owner.getClass();
		Field field = ownerClass.getField(fieldName);
		field.setAccessible(true); //反射私有属性 权限
		Object property = field.get(owner);

		return property;
	}

	/**
	得到某类的静态属性
	@param	 className   类名
	@param	 fieldName   属性名
	@return	 该属性对象
	@throws	 Exception	     
	*/
	public static Object getStaticProperty(String className, String fieldName) throws Exception
	{
		Class<?> ownerClass = Class.forName(className);
		Field field = ownerClass.getField(fieldName);
		field.setAccessible(true); //反射私有属性 权限
		Object property = field.get(ownerClass);

		return property;
	}

	/**
	执行某对象方法
	@param	 owner           对象
	@param	 methodName           方法名
	@param	 args           参数
	@return	 方法返回值
	@throws	 Exception	     
	*/
	public static Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception
	{
		Class<?> ownerClass = owner.getClass();
		Class<?>[] argsClass = new Class[args.length];

		for (int i = 0, j = args.length; i < j; i++)
		{
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		method.setAccessible(true);
		return method.invoke(owner, args);
	}

	/**
	 * 执行对象方法 搜索全部父类
	 */
	public static Object invokeMethodAll(Object owner, String methodName, Object[] args, Class<?>[] argsClass)
	{
		for (Class<?> clazz = owner.getClass(); clazz != Object.class; clazz = clazz.getSuperclass())
		{
			try
			{
				Method method = clazz.getDeclaredMethod(methodName, argsClass);
				method.setAccessible(true);
				return method.invoke(owner, args);
			}
			catch (Exception e)
			{

			}
		}
		return null;
	}

	/**
	执行某类的静态方法
	@param	 className           类名
	@param	 methodName           方法名
	@param	 args           参数数组
	@return
	 执行方法返回的结果
	@throws	 Exception	     
	*/
	public static Object invokeStaticMethod(String className, String methodName, Object[] args) throws Exception
	{
		Class<?> ownerClass = Class.forName(className);
		Class<?>[] argsClass = new Class[args.length];

		for (int i = 0, j = args.length; i < j; i++)
		{
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);

		return method.invoke(null, args);
	}

	/**
	新建实例
	@param	 className           类名
	@param args 构造函数的参数
	@return 新建的实例
	@throws Exception	     
	*/
	public static Object newInstance(String className, Object[] args) throws Exception
	{
		Class<?> newoneClass = Class.forName(className);
		Class<?>[] argsClass = new Class[args.length];

		for (int i = 0, j = args.length; i < j; i++)
		{
			argsClass[i] = args[i].getClass();
		}
		Constructor<?> cons = newoneClass.getConstructor(argsClass);

		return cons.newInstance(args);
	}

	/**
	是不是某个类的实例
	@param obj 实例
	@param cls 类
	@return 如果 obj 是此类的实例，则返回 true
	*/
	public static boolean isInstance(Object obj, Class<?> cls)
	{
		return cls.isInstance(obj);
	}

	/**
	得到数组中的某个元素
	@param	 array 数组
	@param	 index 索引
	@return
	 返回指定数组对象中索引组件的值
	*/
	public static Object getByArray(Object array, int index)
	{

		return Array.get(array, index);
	}

	public static Object getInner(String className, Object[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Object vor = null;
		Class<?> ownerClass = Class.forName(className);
		if (ownerClass != null)
		{
			Constructor<?> constructor = ownerClass.getDeclaredConstructor(new Class[] { String.class });
			if (constructor != null)
			{
				vor = constructor.newInstance("asdf");
			}
		}
		return vor;
	}

	/**
	 * 得到对象的字段 值
	 * @param o
	 * @param fieldName
	 * @return
	 */
	public static Object getFieldsValue(Object o, String fieldName)
	{
		Field f = getFields(o, fieldName);
		Object vo = null;
		try
		{
			vo = f.get(o);
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return vo;
	}

	/**
	 * 设置对象的字段 值
	 * @param o
	 * @param fieldName
	 * @return
	 */
	public static void setFieldsValue(Object o, String fieldName, Object value)
	{
		Field f = getFields(o, fieldName);
		try
		{
			f.set(o, value);
		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}

/*  //反射测试
static String[] tableRow;
private static void fanshe()
	{
		VCarUser vu = new VCarUser(); //测试对象		
		Class<?> c = vu.getClass(); //反射
		Field[] fields = c.getDeclaredFields(); //属性
		tableRow = new String[fields.length];
		for (int i = 0; i < fields.length; i++)
		{
			fields[i].setAccessible(true); //反射私有属性 权限
			Field field = fields[i]; //第几个参数
			Class<?> type = field.getType(); //反射类型
			String typeName = type.getName();//得到类型
			System.out.println(typeName);
			String fieldName = field.getName(); //反射字段
			System.out.println(fieldName); //字段名
			tableRow[i] = fieldName;
			try
			{
				Object value = field.get(vu); //字段值
				System.out.println(value);

			}
			catch (IllegalArgumentException | IllegalAccessException e1)
			{
				e1.printStackTrace();
			}

			if ("java.lang.String".equals(typeName))
			{
				try
				{
					field.set(vu, "1");

				}
				catch (IllegalArgumentException | IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
		}

		System.out.println(vu.getClass().getName());

	}
	*/

class VReflectObject
{

	//@SuppressWarnings("static-access")
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		VReflect vr = new VReflect();
		VReflectObject vro = new VReflectObject();

		//boolean vb = false; //输出 “false”
		//String vs = String.valueOf(vb);
		try
		{

			Object vo = vr.getProperty(vro, "vspubName"); //获取对象公共属性  需要对象			
			System.out.println("0：" + vo);

			vo = vr.getStaticProperty("VReflectObject", "vspubsName"); //获取对象静态公共属性
			System.out.println("1：" + vo);

			vo = vr.invokeMethod(vro, "veFunction", new Object[] {}); //执行对象方法  需要对象	
			System.out.println("2：" + vo);

			vo = vr.invokeMethod(vro, "veFunction", new Object[] { "执行对象方法  需要对象	" }); //执行对象方法  需要对象	
			System.out.println("3：" + vo);

			vo = vr.invokeStaticMethod("VReflectObject", "veStaticFunction", new Object[] {}); //执行对象静态方法
			System.out.println("4：" + vo);

			vo = vr.invokeStaticMethod("VReflectObject", "veStaticFunction", new Object[] { "执行对象方法  需要对象	" }); //执行对象静态方法		
			System.out.println("5：" + vo);

			vo = vr.newInstance("VReflectObject", new Object[] {}); //创建类的实例		
			System.out.println("6：" + vo + ((VReflectObject) vo).vspubName);

			VReflectObject VReflectObject = ((VReflectObject) vo); //判断对象是否是 此类
			boolean vb = vr.isInstance(VReflectObject, VReflectObject.class);
			System.out.println("7：" + vb);

		}
		catch (Exception e)
		{
			// TODO: handle exception
			System.out.println("有错误");
		}
	}

	private String vspriName = "私有名字";
	private static String vsprisName = "私有静态名字";

	public String vspubName = "公共名字";
	public static String vspubsName = "公共静态名字";

	public VReflectObject()
	{
		// TODO Auto-generated constructor stub
	}

	public static void veStaticFunction()
	{
		System.out.print("共有静态无参无反方法。");
	}

	public static void veStaticFunction(String vs)
	{
		System.out.print("共有静态有参无反方法：" + vs);
	}

	public void veFunction()
	{
		System.out.print("共有无参无反方法。");
	}

	public void veFunction(String vs)
	{
		System.out.print("共有有参无反方法：" + vs);
	}

	private void vepriFunction()
	{
		System.out.print("私有无参无反方法");
	}

	private void vepriFunction(String vs)
	{
		System.out.print("私有有参无反方法：" + vs);
	}

	public static final class VClass
	{
		public String vsString = "内部类";
	}
}
