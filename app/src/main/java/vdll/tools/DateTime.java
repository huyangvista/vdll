package vdll.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期时间辅助类  月份从1开始
 * @author Hocean  @version 2016年8月24日 下午4:45:58.
 *
 */
public class DateTime extends GregorianCalendar {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public static DateTime dateTimeBase = new DateTime().setTimeMs(0);  //0毫秒时候的日期

	public static void main(String[] args) {
		DateTime dt = new DateTime();
		System.out.println("一般格式： " + dt.format()); //一般格式输出
		System.out.println("分部輸出：" + String.format("%s_%s_%s %s:%s:%s  %s", dt.getYear(), dt.getMonth(), dt.getDate(), dt.getHours(), dt.getMinutes(), dt.getSeconds(), dt.getWeek()));//获取
		dt.setYear(2017);
		dt.setMonth(7);
		dt.setDate(7);
		dt.setHours(7);
		dt.setMinutes(7);
		dt.setSeconds(7);
		System.out.println("分別设置： " + dt.format()); //一般格式输出
		System.out.println("归零： " + dt.setTimeMs(0).format()); //一般格式输出
		dt.parse("2018-10-3 3:6:3"); //反序列化
		dt.parse("2018-10-3 3:6:3", "yyyy-MM-dd HH:mm:ss"); //自定义 反序列化
		System.out.println("反序列化： " + dt.format()); //一般格式输出
		dt.addDate(1);//计算
		dt.addYear(1);
		dt.addMonth(1);
		dt.addHours(1);
		dt.addMinutes(1);
		dt.addSeconds(1);
		dt.addTimeMs(1000 * 60 * 60 * 24 * 10);
		System.out.println("增加时间： " + dt.format());//一般格式输出
		System.out.println("自定义格式： " + dt.format("yyyy=MM=dd hh&mm&ss E"));//格式输出
		System.out.println("自定义获取： " + "今天是 " +  dt.get(DateTime.DATE) + "号， 星期" + dt.getWeek());//获取特殊时间

		DateTime minus = new DateTime();
		DateTime minus2 = minus.clone();
		minus = DateTime.subtract(minus.addMinutes(70), minus2);
		System.out.println("计算相差时间： " + minus.format());
		System.out.println("输出相差的时间： " + String.format("s-s-%s %s:%s:%s", minus.getDate(), minus.getHours(), minus.getMinutes(), minus.getSeconds()));
		System.out.println("输出相差的时间分别计算： " + String.format("s-s-%s %s:%s:%s", minus.getDateCount(), minus.getHoursCount(), minus.getMinutesCount(), minus.getSecondsCount()));

		System.out.println("创建： " + DateTime.create("2020-6-6 13:06:15").format());
		System.out.println("创建： " + DateTime.createDate("2020-6-6").format());
		System.out.println("创建： " + DateTime.createTime("13:06:15").format());
		System.out.println("创建： " + new DateTime("2020-6-6 13:06:15").format());
		System.out.println("创建： " + new DateTime(new Date()).format());
		System.out.println("创建： " + new DateTime(1000).format());

	}//*/

	public final static String FORMAT_TAG_YEAR = "yyyy";

	public final static String FORMAT_BASE = "yyyy-MM-dd HH:mm:ss";
	public final static String FORMAT_DATE = "yyyy-MM-dd";
	public final static String FORMAT_TIME = "HH:mm:ss";

	private final static int hourToDate = 24;
	private final static int minuteToHour = 60;
	private final static int SecondToMinute = 60;
	private final static int millisToSecond = 1000;

	public String FORMAT_DEFUALT = FORMAT_BASE;

	public DateTime() {
		super();
	}

	public DateTime(TimeZone zone) {
		super(zone);
	}

	public DateTime(Locale aLocale) {
		super(aLocale);
	}

	public DateTime(TimeZone zone, Locale aLocale) {
		super(zone, aLocale);
	}
	public DateTime(String time) {
		this(time, FORMAT_BASE);
	}
	public DateTime(String time, String format) {
		//this(new SimpleDateFormat(format).parse(time));
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			Date date = sdf.parse(time);
			setTime(date);
			FORMAT_DEFUALT = format;
		}
		catch (Exception e) {
			FORMAT_DEFUALT = null;
		}
	}
	public DateTime(Date date) {
		setTime(date);
	}
	public DateTime(long millis) {
		setTimeMs(millis);
	}

	//获取
	public int getYear() {
		int year = get(GregorianCalendar.YEAR);//年
		return year;
	}

	public int getMonth() {
		int month = get(GregorianCalendar.MONTH);//月
		return month + 1;
	}

	public int getDate() {
		int date = get(GregorianCalendar.DATE);//日
		return date;
	}

	public int getHours() {
		int hour = get(GregorianCalendar.HOUR);//时
		return hour;
	}

	public int getMinutes() {
		int minute = get(GregorianCalendar.MINUTE);//分
		return minute;
	}

	public int getSeconds() {
		int second = get(GregorianCalendar.SECOND);//秒
		return second;
	}

	public int getmILLISeconds() {
		int second = get(GregorianCalendar.MILLISECOND);//毫秒
		return second;
	}

	public long getTimeMs() {
		return super.getTimeInMillis();
	}

	public int getWeek() {
		int week = get(GregorianCalendar.DAY_OF_WEEK); //星期
		return week - 1;
	}

	//相减使用
	public double getDateCount() {
		return getHoursCount() / (float) hourToDate;
	}

	public double getHoursCount() {
		return getMinutesCount() / (float) minuteToHour;
	}

	public double getMinutesCount() {
		return getSecondsCount() / (float) SecondToMinute;
	}

	public double getSecondsCount() {
		return getMilliSecondsCount() / (float) millisToSecond;
	}

	public double getMilliSecondsCount() {
		DateTime dt = new DateTime();
		dt.setTime(getTime());
		dt.addYear(1970);
		dt.addHours(8);
		return dt.getTimeMs();
	}
	//设置
	public DateTime setYear(int year) {
		set(GregorianCalendar.YEAR, year);
		return this;
	}

	public DateTime setMonth(int month) {
		set(GregorianCalendar.MONTH, month - 1);
		return this;
	}

	public DateTime setDate(int day) {
		set(GregorianCalendar.DATE, day);
		return this;
	}

	public DateTime setHours(int hour) {
		set(GregorianCalendar.HOUR, hour);
		return this;
	}

	public DateTime setMinutes(int minute) {
		set(GregorianCalendar.MINUTE, minute);
		return this;
	}

	public DateTime setSeconds(int second) {
		set(GregorianCalendar.SECOND, second);
		return this;
	}

	public DateTime setMilliSeconds(int milliSeconds) {
		set(GregorianCalendar.MILLISECOND, milliSeconds);
		return this;
	}

	public DateTime setTimeMs(long millis) {
		super.setTimeInMillis(millis);
		return this;
	}

	//计算
	public DateTime addYear(int year) {
		add(GregorianCalendar.YEAR, year);
		return this;
	}

	public DateTime addMonth(int month) {
		add(GregorianCalendar.MONTH, month);
		return this;
	}

	public DateTime addDate(int day) {
		add(GregorianCalendar.DATE, day);
		return this;
	}

	public DateTime addHours(int hour) {
		add(GregorianCalendar.HOUR, hour);
		return this;
	}

	public DateTime addMinutes(int minute) {
		add(GregorianCalendar.MINUTE, minute);
		return this;
	}

	public DateTime addSeconds(int second) {
		add(GregorianCalendar.SECOND, second);
		return this;
	}

	public DateTime addMilliSeconds(int milliSeconds) {
		add(GregorianCalendar.MILLISECOND, milliSeconds);
		return this;
	}

	public DateTime addTimeMs(long time) {
		long vltime = getTimeMs();
		vltime += time;
		setTimeMs(vltime);
		return this;
	}

	//序列化
	public String format() {
		return format(FORMAT_DEFUALT);
	}
	public String formatDate() {
		return format(FORMAT_DATE);
	}
	public String formatTime() {
		return format(FORMAT_TIME);
	}
	public String format(String format) {
		if(format == null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(getTime());
	}

	//反序列化
	public DateTime parse(String time, String format) {
		return new DateTime(time,format);
	}
	public DateTime parse(String time) {
		return parse(time, FORMAT_BASE);
	}
	public DateTime parseDate(String time) {
		return parse(time, FORMAT_DATE);
	}
	public DateTime parseTime(String time) {
		return parse(time, FORMAT_TIME);
	}

	//创建
	public static DateTime create(String time, String format) {
		return new DateTime(time,format);
	}
	public static DateTime create(String time) {
		return create(time, FORMAT_BASE);
	}
	public static DateTime createDate(String time) {
		return create(time, FORMAT_DATE);
	}
	public static DateTime createTime(String time) {
		return create(time, FORMAT_TIME);
	}

	/**
	 * 相减 后再减去8h 从 1970-01-01 08:00:00  开始
	 * 可获得 相差的 天数 小时 分钟 秒 毫秒
	 * 通过  *Count 获取合计天数 小时 分钟 秒 毫秒
	 * @param dtl
	 * @param dtr
	 * @return
	 */
	public static DateTime subtract(DateTime dtl, DateTime dtr) {
		DateTime minus = new DateTime();
		minus.setTimeMs(dtl.getTimeMs() - dtr.getTimeMs());
		minus.addYear(-1970);
		minus.addHours(-8);
		return minus;
	}
	/**
	 * 克隆元素
	 * @return
	 */
	public DateTime clone() {
		DateTime dt = new DateTime(getTimeZone());
		dt.setTimeMs(getTimeMs());
		return dt;
	}
	public String toString(){
		return format();
	}

}
