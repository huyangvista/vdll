package vdll.tools;

import java.util.*;
import java.text.*;
public class VTime extends Calendar
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
		VTime vt = new VTime();
		System.out.println(vt.vformat()); //一般格式输出
		System.out.println(String.format("%s_%s_%s %s:%s:%s  %s", vt.getYear(), vt.getMonth(), vt.getDate(), vt.getHours(), vt.getMinutes(), vt.getSeconds(), vt.getWeek()));//获取
		vt.setYear(2017);
		vt.setMonth(7);
		vt.setDate(7);
		vt.setHours(7);
		vt.setMinutes(7);
		vt.setSeconds(7);
		System.out.println(vt.vformat()); //一般格式输出
		vt.vparse("2018-10-3 3:6:3");  //反序列化
		System.out.println(vt.vformat()); //一般格式输出
		vt.addDate(1 );//计算
		vt.addYear(1);
		vt.addMonth(1);
		vt.addHours(1);
		vt.addMinutes(1);
		vt.addSeconds(1);
		vt.addTimeMs(1000 * 60 * 60 * 24 * 10);
		System.out.println(vt.vformat());//一般格式输出
		System.out.println(vt.vformat("yyyy=MM=dd hh&mm&ss E"));//格式输出
		System.out.println(vt.get(VTime.DATE));//获取特殊时间
	}//*/

	private Calendar vcal;
	
	public static String vsFormatDefualtDate="yyyy-MM-dd";
	public static String vsFormatDefualtTime="HH:mm:ss";
	public static String vsFormatDefualt="yyyy-MM-dd HH:mm:ss";

	public void setVcal(Calendar vcal)
	{
		this.vcal = vcal;
	}

	public Calendar getVcal()
	{
		return vcal;
	}

	@Override
	protected void computeFields()
	{
		// TODO: Implement this method
	}

	@Override
	protected void computeTime()
	{
		// TODO: Implement this method
	}

	@Override
	public int getGreatestMinimum(int p1)
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public int getLeastMaximum(int p1)
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public int getMaximum(int p1)
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public int getMinimum(int p1)
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public void roll(int p1, boolean p2)
	{
		// TODO: Implement this method
	}

	public VTime()
	{
		start();
		vnow();
	}

	private void start()
	{
		// TODO: Implement this method

	}
	public synchronized VTime vnow()
	{
		vcal = getInstance();
		/*int year = vcal.get(Calendar.YEAR);//年
		 int month = vcal.get(Calendar.MONTH);//月
		 int date = vcal.get(Calendar.DATE);//日

		 int hour = vcal.get(Calendar.HOUR);//时
		 int minute = vcal.get(Calendar.MINUTE);//分
		 int second = vcal.get(Calendar.SECOND);//秒*/
		set(getYear(), getMonth() - 1, getDate(), getHours(), getMinutes(), getSeconds());
		setTime(vcal.getTime());
		setTimeZone(vcal.getTimeZone());
		setTimeInMillis(vcal.getTimeInMillis());
		return this;
	}

	//获取
	public int get(int field)
	{
		return vcal.get(field);
	}
	public int getYear()
	{
		int year = vcal.get(Calendar.YEAR);//年
		return year;
	}
	public int getMonth()
	{
		int month = vcal.get(Calendar.MONTH);//月
		return month + 1;
	}
	public int getDate()
	{
		int date =vcal.get(Calendar.DATE);//日
		return date;
	}
	public int getHours()
	{
		int hour =vcal.get(Calendar.HOUR);//时
		return hour;
	}
	public int getMinutes()
	{
		int minute = vcal.get(Calendar.MINUTE);//分
		return minute;
	}
	public int getSeconds()
	{
		int second =vcal.get(Calendar.SECOND);//秒
		return second;
	}
	public long getTimeMs()
	{
		return vcal.getTime().getTime();
	}
	//补充的获取
	public int getWeek()
	{
		int week =vcal.get(Calendar.DAY_OF_WEEK); //星期
		return week - 1;
	}
	
	//设置
	public void set(int field, int value) 
	{
		vcal.set(field, value);
	}
	public void setYear(int year)
	{
		vcal.	set(Calendar.YEAR, year);
	}
	public void setMonth(int month)
	{
		vcal.	set(Calendar.MONTH, month - 1);
	}
	public void setDate(int day)
	{
		vcal.	set(Calendar.DATE, day);
	}
	public void setHours(int hour)
	{
		vcal.	set(Calendar.HOUR, hour);
	}
	public void setMinutes(int minute)
	{
		vcal.	set(Calendar.MINUTE, minute);
	}
	public void setSeconds(int second)
	{
		vcal.	set(Calendar.SECOND, second);
	}
    public void setTimeMs(long time)
	{
		vcal.	setTime(new Date(time));
	}

	//计算
    @Override
	public void add(int p1, int p2)
	{
		// TODO: Implement this method
		vcal.set(p1, vcal.get(p1) + p2);

	}
	public void addYear(int year)
	{
		add(Calendar.YEAR,  year);
	}
	public void addMonth(int month)
	{
		add(Calendar.MONTH,  month);
	}
	public void addDate(int day)
	{
		add(Calendar.DATE, day);
	}
	public void addHours(int hour)
	{
		add(Calendar.HOUR,  hour);
	}
	public void addMinutes(int minute)
	{
		add(Calendar.MINUTE, minute);
	}
	public void addSeconds(int second)
	{
		add(Calendar.SECOND, second);
	}
	public void addTimeMs(long time)
	{
		long vltime =getTimeMs();
		vltime += time;
		setTimeMs(vltime);
	}


 	//序列化
	public String vformat()
	{
		return vformat(vsFormatDefualt);
	}
	public String vformat(String format)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(vcal.getTime());
	}
	//反序列化
	public VTime vparse(String time, String format)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(format);

		try
		{
			Date d = sdf.parse(time);
			vcal.setTime(d);
			return this;
		}
		catch (ParseException e)
		{}
		return null;

	}
	public VTime vparse(String time)
	{
		return vparse(time, vsFormatDefualt);

	}


}



/*

	import java.util.*;
	import java.text.*;
	public class VTime extends Calendar
	{

		private Calendar vcal;
		private static String vsFormatDefualt="yyyy-MM-dd HH:mm:ss";

		public void setVcal(Calendar vcal)
		{
			this.vcal = vcal;
		}

		public Calendar getVcal()
		{
			return vcal;
		}

		@Override
		public void add(int p1, int p2)
		{
			// TODO: Implement this method
			vcal.set(p1, vcal.get(p1) + p2);

		}

		@Override
		protected void computeFields()
		{
			// TODO: Implement this method
		}

		@Override
		protected void computeTime()
		{
			// TODO: Implement this method
		}

		@Override
		public int getGreatestMinimum(int p1)
		{
			// TODO: Implement this method
			return 0;
		}

		@Override
		public int getLeastMaximum(int p1)
		{
			// TODO: Implement this method
			return 0;
		}

		@Override
		public int getMaximum(int p1)
		{
			// TODO: Implement this method
			return 0;
		}

		@Override
		public int getMinimum(int p1)
		{
			// TODO: Implement this method
			return 0;
		}

		@Override
		public void roll(int p1, boolean p2)
		{
			// TODO: Implement this method
		}

		public VTime()
		{
			start();
			vnow();
		}

		private void start()
		{
			// TODO: Implement this method

		}
		public synchronized VTime vnow()
		{
			vcal = getInstance();
			/*int year = vcal.get(Calendar.YEAR);//年
			 int month = vcal.get(Calendar.MONTH);//月
			 int date = vcal.get(Calendar.DATE);//日

			 int hour = vcal.get(Calendar.HOUR);//时
			 int minute = vcal.get(Calendar.MINUTE);//分
			 int second = vcal.get(Calendar.SECOND);//秒//*

			setTime(vcal.getTime());
			setTimeZone(vcal.getTimeZone());
			setTimeInMillis(vcal.getTimeInMillis());
			return this;
		}

		//获取
		public int getYear()
		{
			int year = vcal.get(Calendar.YEAR);//年
			return year;
		}
		public int getMonth()
		{
			int month = vcal.get(Calendar.MONTH);//月
			return month;
		}
		public int getDate()
		{
			int date =vcal.get(Calendar.DATE);//日
			return date;
		}
		public int getHours()
		{
			int hour =vcal.get(Calendar.HOUR);//时
			return hour;
		}
		public int getMinutes()
		{
			int minute = vcal.get(Calendar.MINUTE);//分
			return minute;
		}
		public int getSeconds()
		{
			int second =vcal.get(Calendar.SECOND);//秒
			return second;
		}
		public long getTimeMs()
		{
			return vcal.getTime().getTime();
		}

		//设置
		public void setYear(int year)
		{
			vcal.	set(Calendar.YEAR, year);
		}
		public void setMonth(int month)
		{
			vcal.	set(Calendar.MONTH, month);
		}
		public void setDate(int day)
		{
			vcal.	set(Calendar.DATE, day);
		}
		public void setHours(int hour)
		{
			vcal.	set(Calendar.HOUR, hour);
		}
		public void setMinutes(int minute)
		{
			vcal.	set(Calendar.MINUTE, minute);
		}
		public void setSeconds(int second)
		{
			vcal.	set(Calendar.SECOND, second);
		}
		public void setTimeMs(long time)
		{
			vcal.	setTime(new Date(time));
		}

		//计算
		public void addYear(int year)
		{
			add(Calendar.YEAR,  year);
		}
		public void addMonth(int month)
		{
			add(Calendar.MONTH,  month);
		}
		public void addDate(int day)
		{
			add(Calendar.DATE, day);
		}
		public void addHours(int hour)
		{
			add(Calendar.HOUR,  hour);
		}
		public void addMinutes(int minute)
		{
			add(Calendar.MINUTE, minute);
		}
		public void addSeconds(int second)
		{
			add(Calendar.SECOND, second);
		}
		public void addTimeMs(long time)
		{
			long vltime =getTimeMs();
			vltime += time;
			setTimeMs(vltime);
		}


		//序列化
		public String vformat()
		{
			return vformat(vsFormatDefualt);
		}
		public String vformat(String format)
		{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(vcal.getTime());
		}
		//反序列化
		public VTime vparse(String time, String format)
		{
			SimpleDateFormat sdf = new SimpleDateFormat(format);

			try
			{
				Date d = sdf.parse(time);
				vcal.setTime(d);
				return this;
			}
			catch (ParseException e)
			{}
			return null;

		}
		public VTime vparse(String time)
		{
			return vparse(time, vsFormatDefualt);

		}


	}*/
