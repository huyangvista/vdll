package vdll.tools;

import java.io.FileNotFoundException;
import java.io.PrintStream;


public class Console extends PrintStream
{

	public Console(String fileName) throws FileNotFoundException
	{
		super(fileName);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
	}

	public static void Write(Object ow)
	{
		System.out.print(ow);
	}
	public static void WriteLine(Object ow)
	{
		System.out.println(VWhoCall() + ow);
	}
	public static void WriteLine()
	{
		System.out.println(VWhoCall());
	}
	
	public static void Err(Object ow)
	{
		System.err.print(ow);
	}
	public static void ErrLine(Object ow)
	{
		System.err.println(VWhoCall()+ow);
	}
	public static void ErrLine()
	{
		System.err.println(VWhoCall());
	}

	public static String VWhoCall()
	{
		StackTraceElement vste = Thread.currentThread().getStackTrace()[3];
		String vs = vste.getClassName()  + "."+ vste.getMethodName() + "-->";
		return vs;
	}
}
