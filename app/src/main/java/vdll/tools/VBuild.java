package vdll.tools;//package vdll.tools;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.lang.reflect.Method;
//import java.net.MalformedURLException;
//import java.net.URI;
//import java.net.URL;
//import java.net.URLClassLoader;
//import java.util.Arrays;
//
//import javax.tools.JavaCompiler;
//import javax.tools.JavaFileObject;
//import javax.tools.SimpleJavaFileObject;
//import javax.tools.ToolProvider;
//
//
//import javax.tools.JavaCompiler.CompilationTask;
//
///**
// * 
// * @author Vive  @version 2016年7月5日 下午12:46:14.
// *
// */
//public class VBuild
//{
//	public static String vsBuildPath = "./build/classes/"; //<jsp=java> ="./bin/"; //编译目录需要与项目相同 否则制定加载器路径
//
//	static
//	{
//		//vsBuildPath = vsBuildPath;
//	}
//
//	//public static void main(String[] args) throws Exception
//	public static void main(String[] args) throws Exception
//	{
//		VBuild vbuild = new VBuild();
//		vbuild.vbuild("Hello.java", vbuild.vtestCodeJava());
//
//		VReflect.invokeStaticMethod("com.flyoung.hello.Hello", "main", new Object[] { new String[] {  "8","8" } });
//		Object hello = VReflect.newInstance("com.flyoung.hello.Hello", new Object[] {});
//		VReflect.invokeMethod(hello, "main", new Object[] { new String[] { "80","80" } });
//
//	}
//
//	public VBuild()
//	{
//		//vtestCode();
//		//vtestFile();
//	}
//
//	//测试
//	private void vtestCode()
//	{
//		/* 
//		  * 编译内存中的java代码 
//		  */
//		// 将代码写入内存中 
//		StringWriter writer = new StringWriter();// 内存字符串输出流 
//		PrintWriter out = new PrintWriter(writer);
//		out.println("package com.flyoung.hello;");
//		out.println("public class Hello{");
//		out.println("public static void main(String[] args){");
//		out.println("System.out.println(\"9999999999999!\");");
//		out.println("}");
//		out.println("}");
//		out.flush();
//		out.close();
//
//		try
//		{
//			//String vsBuildPath = "./build/classes/"; //<jsp=java> ="./bin/"; //编译目录需要与项目相同 否则制定加载器路径
//			JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
//			JavaFileObject fileObject = new JavaStringObject("Hello.java", writer.toString());
//			//CompilationTask task = javaCompiler.getTask(null, null, null, Arrays.asList("-d", "C:\\Program Files\\Java\\jdk1.8.0_92\\lib"), null, Arrays.asList(fileObject));
//			CompilationTask task = javaCompiler.getTask(null, null, null, Arrays.asList("-d", vsBuildPath), null, Arrays.asList(fileObject));
//
//			boolean success = task.call();
//			if (success)
//			{
//				System.out.println("OK");
//				/*//0.0指定加载器路径
//				URL[] urls = new URL[] { new URL("file:/" + System.getProperty("user.dir") + vsBuildPath) };
//				URLClassLoader classLoader = new URLClassLoader(urls);
//				Class<?> class1 = classLoader.loadClass("com.flyoung.hello.Hello");*/
//				//0.1使用项目默认路径
//				Class<?> class1 = Class.forName("com.flyoung.hello.Hello");
//				Method method = class1.getDeclaredMethod("main", String[].class);
//				String[] args1 = { null };
//				method.invoke(class1.newInstance(), args1);
//			}
//			else
//			{
//				System.out.println("编译失败!");
//			}
//		}
//		catch (Exception e)
//		{
//			// TODO: handle exception
//			System.err.println(e);
//		}
//	}
//	//测试
//	private void vtestFile()
//	{
//		try
//		{
//			String vsFileName = "com/flyoung/hello/Hello.java"; //完整类名
//			//String vsBuildPath = "./build/classes/"; //<jsp=java> ="./bin/"; //编译目录需要与项目相同 否则制定加载器路径
//			// 编译程序
//			JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
//			int result = javaCompiler.run(null, null, null, "-d", vsBuildPath, vsBuildPath + vsFileName);
//			System.out.println(result == 0 ? "恭喜编译成功" : "对不起编译失败");
//
//			/*// 运行程序    //错误
//			Runtime run = Runtime.getRuntime();
//			Process process = run.exec("java -cp " + vsBuildPath + " " + vsFileName);
//			InputStream in = process.getInputStream();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//			String info = "";
//			while ((info = reader.readLine()) != null)
//			{
//				System.out.println(info);
//			
//			}*/
//
//			/*//0.0指定加载器路径
//			URL[] urls = new URL[] { new URL("file:/" + System.getProperty("user.dir") + vsBuildPath) };
//			URLClassLoader classLoader = new URLClassLoader(urls);
//			Class<?> class1 = classLoader.loadClass("com.flyoung.hello.Hello");*/
//			//0.1使用项目默认路径
//			Class<?> class1 = Class.forName("com.flyoung.hello.Hello");
//			Method method = class1.getDeclaredMethod("main", String[].class);
//			String[] args1 = { null };
//			method.invoke(class1.newInstance(), args1);
//		}
//		catch (Exception e)
//		{
//			// TODO: handle exception
//			System.err.println(e);
//		}
//
//	}
//	//测试
//	private String vtestCodeJava()
//	{
//		StringWriter writer = new StringWriter();// 内存字符串输出流 
//		PrintWriter out = new PrintWriter(writer);
//		out.println("package com.flyoung.hello;");
//		out.println("public class Hello{");
//		out.println("public static void main(String[] args){");
//		out.println("System.out.println(    (Integer.parseInt(args[0])  * Integer.parseInt(args[0]) )      );");
//		out.println("}"); 
//		out.println("}");
//		out.flush();
//		out.close();
//		return writer.toString();
//	}
//
//	/**
//	 * 直接编译代码
//	 * @param file 类名.java
//	 * @param code  类代码
//	 * @return
//	 */
//	public boolean vbuild(String file, String code)
//	{
//		boolean success = false;
//		try
//		{
//			//需要把  JDK的 lib/tools.jar 放入  JRE 的 lib
//			String vsBuildPath = "./build/classes/"; // "./build/classes/" <=jsp | java=> "./bin/"; //编译目录需要与项目相同 否则制定加载器路径   
//			JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
//			JavaFileObject fileObject = new JavaStringObject(file, code);
//			//CompilationTask task = javaCompiler.getTask(null, null, null, Arrays.asList("-d", "C:\\Program Files\\Java\\jdk1.8.0_92\\lib"), null, Arrays.asList(fileObject));
//			CompilationTask task = javaCompiler.getTask(null, null, null, Arrays.asList("-d", vsBuildPath), null, Arrays.asList(fileObject));
//
//			success = task.call();
//			if (success)
//			{
//				//System.out.println("OK");
//				/*//0.0指定加载器路径
//				URL[] urls = new URL[] { new URL("file:/" + System.getProperty("user.dir") + vsBuildPath) };
//				URLClassLoader classLoader = new URLClassLoader(urls);
//				Class<?> class1 = classLoader.loadClass("com.flyoung.hello.Hello"); //*/
//				/*//0.1使用项目默认路径 
//				Class<?> class1 = Class.forName("com.flyoung.hello.Hello");
//				Method method = class1.getDeclaredMethod("main", String[].class);
//				method.invoke(class1.newInstance(), new String[] {null}); //*/
//			}
//			else
//			{
//				System.out.println("编译失败!");
//			}
//		}
//		catch (Exception e)
//		{
//			// TODO: handle exception
//			System.err.println(e);
//		}
//		return success;
//	}
//
//	/**
//	 * 完全自定义 编译目录件 默认相对目录
//	 * @param vsFileName
//	 * @return
//	 */
//	public boolean vbuildFile(String vsFileName)
//	{
//		boolean success = false;
//		try
//		{
//			//String vsFileName = "com/flyoung/hello/Hello.java"; //完整类名
//			//String vsBuildPath = "./build/classes/"; //<jsp=java> ="./bin/"; //编译目录需要与项目相同 否则制定加载器路径
//			// 编译程序
//			JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
//			int result = javaCompiler.run(null, null, null, "-d", vsBuildPath, vsBuildPath + vsFileName);
//			//System.out.println(result == 0 ? "恭喜编译成功" : "对不起编译失败");
//			if (result == 0)
//			{
//				success = true;
//			}
//			/*// 运行程序    //错误
//			Runtime run = Runtime.getRuntime();
//			Process process = run.exec("java -cp " + vsBuildPath + " " + vsFileName);
//			InputStream in = process.getInputStream();
//			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//			String info = "";
//			while ((info = reader.readLine()) != null)
//			{
//				System.out.println(info);
//			
//			}*/
//
//			/*//0.0指定加载器路径
//			URL[] urls = new URL[] { new URL("file:/" + System.getProperty("user.dir") + vsBuildPath) };
//			URLClassLoader classLoader = new URLClassLoader(urls);
//			Class<?> class1 = classLoader.loadClass("com.flyoung.hello.Hello"); 
//			//0.1使用项目默认路径
//			Class<?> class1 = Class.forName("com.flyoung.hello.Hello");
//			Method method = class1.getDeclaredMethod("main", String[].class);
//			method.invoke(class1.newInstance(), new String[] { null }); //*/
//		}
//		catch (Exception e)
//		{
//			System.err.println(e);
//		}
//
//		return success;
//	}
//
//	/**
//	 * 完全自定义 编译路径
//	 * @param vsBuildPath
//	 */
//	public static void vsetBuildPath(String vsBuildPath)
//	{
//		VBuild.vsBuildPath = vsBuildPath;
//	}
//
//	/**
//	 * 获取自定义的 类加载器
//	 * @return
//	 */
//	public static URLClassLoader vgetBuildPathNow()
//	{
//		URLClassLoader classLoader = null;
//		try
//		{
//			URL[] urls = new URL[] { new URL("file:/" + System.getProperty("user.dir") + vsBuildPath) };
//			classLoader = new URLClassLoader(urls);
//			//Class<?> class1 = classLoader.loadClass("com.flyoung.hello.Hello");		
//		}
//		catch (MalformedURLException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return classLoader;
//	}
//
//}
//
////重定义 编译路径
//class JavaStringObject extends SimpleJavaFileObject
//{
//	private String code;
//
//	public JavaStringObject(String name, String code)
//	{
//		//super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
//		//super(URI.create(name + ".java"), Kind.SOURCE);
//		super(URI.create(name), Kind.SOURCE);
//		this.code = code;
//
//		//System.out.println(super.uri);
//	}
//
//	@Override
//	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException
//	{
//		return code;
//	}
//}
