package cn.com.upcard.mgateway.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.validator.GenericValidator;

/**
 * ���ڹ����� Edison
 */
public final class DateUtil {

	private static final Calendar cal = Calendar.getInstance();

	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("HH:mm:ss");

	public static final SimpleDateFormat COMPACT_DATE_FORMATTER = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat COMPACT_YEAR_MONTH_FORMATTER = new SimpleDateFormat("yyyyMM");
	public static final SimpleDateFormat COMPACT_DATE_TIME_FORMATTER = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat COMPACT_TIME_FORMATTER = new SimpleDateFormat("HHmmss");

	public static final SimpleDateFormat COMPACT_YEAR_MM_FORMATTER = new SimpleDateFormat("yyMM");

	public static final SimpleDateFormat COMPACT_MONTH_TIME_FORMATTER = new SimpleDateFormat("MMddHHmmss");

	public static String yyyyMMdd = "yyyyMMdd";
	public static String HHmmss = "HHmmss";
	public static String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static String MMddHHmmss = "MMddHHmmss";
	public static String MMdd = "MMdd";
	public static String yyMM = "yyMM";

	public static String DATE_TIME_FORMATTER_STRING = "yyyy-MM-dd HH:mm:ss";
	public static final SimpleDateFormat DATE_TIMESTAMP = new SimpleDateFormat("yyMMddHHmmss");

	private DateUtil() {
	}

	/**
	 * ����һ��Date����
	 * 
	 * @see #newDate(int,int,int,int,int,int)
	 */
	public static Date newDate(int year, int month, int day) {
		return newDate(year, month, day, 0, 0, 0);
	}

	/**
	 * ����һ��Date����<br/>
	 * 
	 * @param month
	 *            ��0��ʼ,��ʾһ�·�
	 * @param day
	 *            ��1��ʼ����ʾ��һ��
	 */
	public static Date newDate(int year, int month, int day, int hour, int minute, int second) {
		synchronized (cal) {
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
			cal.set(Calendar.DAY_OF_MONTH, day);
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, minute);
			cal.set(Calendar.SECOND, second);
			return cal.getTime();
		}
	}

	/**
	 * ���ص�ǰʱ��
	 */
	public static Date now() {
		synchronized (cal) {
			cal.setTimeInMillis(System.currentTimeMillis());
			return cal.getTime();
		}
	}
    
	/**
	 * ������������ڶ���
	 */
	public static Date tomorrow() {
		return tomorrow(now());
	}

	/**
	 * ȡ��date����һ�졣
	 */
	public static Date tomorrow(Date date) {
		synchronized (cal) {
			cal.setTime(date);
			cal.add(Calendar.DATE, 1);
			return cal.getTime();
		}
	}

	/**
	 * ������������ڶ���
	 */
	public static Date yesterday() {
		return yesterday(now());
	}

	/**
	 * ȡ��date��ǰһ�졣
	 */
	public static Date yesterday(Date date) {
		synchronized (cal) {
			cal.setTime(date);
			cal.add(Calendar.DATE, -1);
			return cal.getTime();
		}
	}

	/**
	 * ȡ��date�ĺ� n ��.<br/>
	 */
	public static Date afterDay(Date date, int n) {
		synchronized (cal) {
			cal.setTime(date);
			cal.add(Calendar.DATE, n);
			return cal.getTime();
		}
	}

	/**
	 * ȡ��date��ǰn����.<br/>
	 */
	public static Date beforeMonth(Date date, int n) {
		synchronized (cal) {
			cal.setTime(date);
			cal.add(Calendar.MONTH, -n);
			return cal.getTime();
		}
	}

	/**
	 * ȡ��date�ĺ�n����.<br/>
	 */
	public static Date afterMonth(Date date, int n) {
		synchronized (cal) {
			cal.setTime(date);
			cal.add(Calendar.MONTH, n);
			return cal.getTime();
		}
	}
	
	/**
	 * 在date的基础上增加n年
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date afterYear (Date date, int n) {
		synchronized (cal) {
			cal.setTime(date);
			cal.add(Calendar.YEAR, n);
			return cal.getTime();
		}
	}
	/**
	 * 在date的基础上增加n分钟
	 * @param date
	 * @param n
	 * @return
	 */
	public static Date afterMinute(Date date, int n) {
		synchronized (cal) {
			cal.setTime(date);
			cal.add(Calendar.MINUTE, n);
			return cal.getTime();
		}
	}

	/**
	 * ȡ�����ڵ�ĳ���ֶΡ�<br/>
	 * �磺 getField(date,Calendar.YEAR)
	 */
	public static int getField(Date d, int field) {
		synchronized (cal) {
			cal.setTime(d);
			return cal.get(field);
		}
	}

	/**
	 * �������ڵ�ĳ���ֶΡ�<br/>
	 */
	public static Date setField(Date d, int field, int value) {
		synchronized (cal) {
			cal.setTime(d);
			cal.set(field, value);
			return cal.getTime();
		}
	}

	/**
	 * �����ڶ�����ʱ����Ϣ���<br/>
	 * ���õ���ʱ��Ϊ 00:00:00 ��
	 */
	public static Date clearTime(Date d) {
		synchronized (cal) {
			cal.setTime(d);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTime();
		}
	}

	/**
	 * ����formatָ���ĸ�ʽ������
	 */
	public static Date parseDate(String date, String format) {
		return parseDate(date, new SimpleDateFormat(format));
	}

	/**
	 * ����formatָ���ĸ�ʽ������
	 */
	public static synchronized Date parseDate(String date, SimpleDateFormat format) {
		try {
			return format.parse(date);
		} catch (ParseException ex) {
			throw new RuntimeException("Date parse error: " + date + " , expected format is " + format.toPattern(), ex);
		}
	}

	/**
	 * �������� formatָ���ĸ�ʽ�����
	 */
	public static synchronized String formatDate(Date date, SimpleDateFormat format) {
		if (date == null) {
			return "";
		}
		return format.format(date);
	}

	/**
	 * �������� formatָ���ĸ�ʽ�����
	 */
	public static synchronized String formatDate(Date date, String format) {
		if (date == null) {
			return "";
		}
		return formatDate(date, new SimpleDateFormat(format));
	}

	/**
	 * �������� 2007-01-30 �ĸ�ʽ�����
	 */
	public static synchronized String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		return DATE_FORMATTER.format(date);
	}

	/**
	 * �������� 20070130 �ĸ�ʽ�����
	 */
	public static synchronized String formatCompactDate(Date date) {
		if (date == null) {
			return "";
		}
		return COMPACT_DATE_FORMATTER.format(date);
	}

	/**
	 * �������� 0701 �ĸ�ʽ�����
	 */
	public static synchronized String formatCompactDateYearMonth(Date date) {
		if (date == null) {
			return "";
		}
		return COMPACT_YEAR_MM_FORMATTER.format(date);
	}

	/**
	 * ���� 2007-01-30 �ĸ�ʽ������
	 */
	public static synchronized Date parseDate(String date) {
		try {
			return DATE_FORMATTER.parse(date);
		} catch (ParseException ex) {
			throw new RuntimeException("Date parse error: " + date + " , expected format is " + DATE_FORMATTER.toPattern(), ex);
		}
	}

	/**
	 * ���� 20070130 �ĸ�ʽ������
	 */
	public static synchronized Date parseCompactDate(String date) {
		try {
			return COMPACT_DATE_FORMATTER.parse(date);
		} catch (ParseException ex) {
			throw new RuntimeException("Date parse error: " + date + " , expected format is "
					+ COMPACT_DATE_FORMATTER.toPattern(), ex);
		}
	}

	/**
	 * �������� 2007-01-30 08:30:59 �ĸ�ʽ�����
	 */
	public static synchronized String formatDateTime(Date date) {
		if (date == null) {
			return "";
		}
		return DATE_TIME_FORMATTER.format(date);
	}

	/**
	 * �������� 20070130083059 �ĸ�ʽ�����
	 */
	public static synchronized String formatCompactDateTime(Date date) {
		if (date == null) {
			return "";
		}
		return COMPACT_DATE_TIME_FORMATTER.format(date);
	}

	/**
	 * ���� 2007-01-30 08:30:59 �ĸ�ʽ������
	 */
	public static synchronized Date parseDateTime(String dateTime) {
		try {
			return DATE_TIME_FORMATTER.parse(dateTime);
		} catch (ParseException ex) {
			throw new RuntimeException("Date parse error: " + dateTime + " , expected format is "
					+ DATE_TIME_FORMATTER.toPattern(), ex);
		}
	}

	/**
	 * ���� 20070130083059 �ĸ�ʽ������
	 */
	public static synchronized Date parseCompactDateTime(String dateTime) {
		try {
			return COMPACT_DATE_TIME_FORMATTER.parse(dateTime);
		} catch (ParseException ex) {
			throw new RuntimeException("Date parse error: " + dateTime + " , expected format is "
					+ COMPACT_DATE_TIME_FORMATTER.toPattern(), ex);
		}
	}

	/**
	 * 格式化格式为mmddhhmmss的字符串为日期
	 * 
	 * @param dateTime
	 * @return
	 */
	public static synchronized Date parseCompactMonthDateTime(String dateTime) {
		try {
			return COMPACT_MONTH_TIME_FORMATTER.parse(dateTime);
		} catch (ParseException ex) {
			throw new RuntimeException("Date parse error: " + dateTime + " , expected format is "
					+ COMPACT_MONTH_TIME_FORMATTER.toPattern(), ex);
		}
	}

	/**
	 * �������� 08:30:59 �ĸ�ʽ�����
	 */
	public static synchronized String formatTime(Date date) {
		if (date == null) {
			return "";
		}
		return TIME_FORMATTER.format(date);
	}

	/**
	 * �������� 083059 �ĸ�ʽ�����
	 */
	public static synchronized String formatCompactTime(Date date) {
		if (date == null) {
			return "";
		}
		return COMPACT_TIME_FORMATTER.format(date);
	}

	public static synchronized String formatCompactMonthTime(Date date) {
		if (date == null) {
			return "";
		}
		return COMPACT_MONTH_TIME_FORMATTER.format(date);
	}

	/**
	 * ���� 08:30:59 �ĸ�ʽ�����ڡ�
	 */
	public static synchronized Date parseTime(String time) {
		try {
			return TIME_FORMATTER.parse(time);
		} catch (ParseException ex) {
			throw new RuntimeException("Date parse error: " + time + " , expected format is " + TIME_FORMATTER.toPattern(), ex);
		}
	}

	/**
	 * ���� 083059 �ĸ�ʽ�����ڡ�
	 */
	public static synchronized Date parseCompactTime(String time) {
		try {
			return COMPACT_TIME_FORMATTER.parse(time);
		} catch (ParseException ex) {
			throw new RuntimeException("Date parse error: " + time + " , expected format is "
					+ COMPACT_TIME_FORMATTER.toPattern(), ex);
		}
	}

	/**
	 * �������ڷ�Χ������β���ڡ� [beginDate,endDate]
	 */
	public static DateRange newDateRange(Date beginDate, Date endDate) {
		return new DateRange(beginDate, endDate);
	}

	/**
	 * ���ڷ�Χ��<br/>
	 * û��synchronized�������̰߳�ȫ�ġ�
	 */
	public static class DateRange {

		private final Calendar cal = Calendar.getInstance();
		private Date beginDate;
		private Date endDate;

		DateRange(Date beginDate, Date endDate) {
			this.beginDate = clearTime(beginDate);
			this.endDate = clearTime(endDate);
			cal.setTime(yesterday(this.beginDate));
		}

		/**
		 * ��ǰ����
		 */
		private Date current() {
			return cal.getTime();
		}

		/**
		 * ��һ�졣<br/>
		 * ����ʹ�� <code>hasNext()</code> �����жϡ�
		 * 
		 * @exception RuntimeException
		 *                ���������ڷ�Χ��β�����׳�RuntimeException��
		 */
		public Date next() {
			if (hasNext()) {
				cal.add(Calendar.DATE, 1);
				return cal.getTime();
			} else {
				throw new RuntimeException("over range!");
			}
		}

		/**
		 * �Ƿ�����һ��
		 */
		public boolean hasNext() {
			return current().before(endDate);
		}
	}

	/**
	 * 将一个字符串转换成日期格式
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date toDate(String date, String pattern) {
		if (("" + date).equals("")) {
			return null;
		}
		if (pattern == null) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date newDate = new Date();
		try {
			newDate = sdf.parse(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return newDate;
	}

	/**
	 * 把日期转换成字符串型
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toString(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		if (pattern == null) {
			pattern = "yyyy-MM-dd";
		}
		String dateString = "";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			dateString = sdf.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateString;
	}

	public static String toString(Long time, String pattern) {
		if (time > 0) {
			if (time.toString().length() == 10) {
				time = time * 1000;
			}
			Date date = new Date(time);
			String str = DateUtil.toString(date, pattern);
			return str;
		}
		return "";
	}

	/**
	 * 获取上个月的开始结束时间
	 * 
	 * @return
	 */
	public static String[] getLastMonth() {
		// 取得系统当前时间
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;

		// 取得系统当前时间所在月第一天时间对象
		cal.set(Calendar.DAY_OF_MONTH, 1);

		// 日期减一,取得上月最后一天时间对象
		cal.add(Calendar.DAY_OF_MONTH, -1);

		// 输出上月最后一天日期
		int day = cal.get(Calendar.DAY_OF_MONTH);

		String months = "";
		String days = "";

		if (month > 1) {
			month--;
		} else {
			year--;
			month = 12;
		}
		if (!(String.valueOf(month).length() > 1)) {
			months = "0" + month;
		} else {
			months = String.valueOf(month);
		}
		if (!(String.valueOf(day).length() > 1)) {
			days = "0" + day;
		} else {
			days = String.valueOf(day);
		}
		String firstDay = "" + year + "-" + months + "-01";
		String lastDay = "" + year + "-" + months + "-" + days;

		String[] lastMonth = new String[2];
		lastMonth[0] = firstDay;
		lastMonth[1] = lastDay;

		// System.out.println(lastMonth[0] + "||" + lastMonth[1]);
		return lastMonth;
	}

	/**
	 * 获取当月的开始结束时间
	 * 
	 * @return
	 */
	public static String[] getCurrentMonth() {
		// 取得系统当前时间
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;

		// 取得系统当前时间所在月第一天时间对象
		cal.set(Calendar.DAY_OF_MONTH, 1);

		// 日期减一,取得上月最后一天时间对象
		cal.add(Calendar.DAY_OF_MONTH, -1);

		// 输出上月最后一天日期
		int day = cal.get(Calendar.DAY_OF_MONTH);

		String months = "";
		String days = "";

		if (!(String.valueOf(month).length() > 1)) {
			months = "0" + month;
		} else {
			months = String.valueOf(month);
		}
		if (!(String.valueOf(day).length() > 1)) {
			days = "0" + day;
		} else {
			days = String.valueOf(day);
		}
		String firstDay = "" + year + "-" + months + "-01";
		String lastDay = "" + year + "-" + months + "-" + days;

		String[] currentMonth = new String[2];
		currentMonth[0] = firstDay;
		currentMonth[1] = lastDay;

		// System.out.println(lastMonth[0] + "||" + lastMonth[1]);
		return currentMonth;
	}

	public static int getDateline() {

		return (int) (System.currentTimeMillis() / 1000);
	}

	public static long getDatelineLong() {

		return System.currentTimeMillis() / 1000;
	}

	public static int getDateline(String date) {
		return (int) (toDate(date, "yyyy-MM-dd").getTime() / 1000);
	}

	public static int getDateline(String date, String pattern) {
		return (int) (toDate(date, pattern).getTime() / 1000);
	}

	public static long getDatelineLong(String date) {
		return (long) (toDate(date, "yyyy-MM-dd").getTime() / 1000);
	}

	public static synchronized long getNowLongValue() {
		Date newDate = new Date();
		String dateStr = COMPACT_DATE_TIME_FORMATTER.format(newDate);
		return Long.valueOf(dateStr);
	}

	/**
	 * 校验日期
	 * 
	 * @param sourceDate
	 * @param format
	 * @return true 日期有效 false 日期无效
	 */
	public static boolean checkDate(String sourceDate, String format) {
		// 如果为false，则2010021 yyyyMMdd 通过；如果20100212 通不过；
		return GenericValidator.isDate(sourceDate, format, true);
	}

	/**
	 * 比较日期
	 * 
	 * @param dateFrom
	 * @param dateTo
	 * @param dateFormat
	 * @return 0 日期相等<br>
	 *         <0 "dateFrom<dateTo" <br>
	 *         >0 "dateFrom>dateTo"
	 * @throws ParseException
	 */
	public static int compareTo(String dateFrom, String dateTo, String dateFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar dateFromCal = Calendar.getInstance();
		dateFromCal.setTime(sdf.parse(dateFrom));

		Calendar dateToCal = Calendar.getInstance();
		dateToCal.setTime(sdf.parse(dateTo));

		return dateFromCal.compareTo(dateToCal);
	}

	/**
	 * 比较日期
	 * 
	 * @param dateFrom
	 * @param dateTo
	 * @param dateFormat
	 * @return 0 日期相等<br>
	 *         <0 "dateFrom<dateTo" <br>
	 *         >0 "dateFrom>dateTo"
	 * @throws ParseException
	 */
	public static int compareTo(String dateFrom, Date dateTo, String dateFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Calendar dateFromCal = Calendar.getInstance();
		dateFromCal.setTime(sdf.parse(dateFrom));

		Calendar dateToCal = Calendar.getInstance();
		dateToCal.setTime(dateTo);

		return dateFromCal.compareTo(dateToCal);
	}

	/**
	 * 校验有效期，有效期必须等于或大于现在的日期
	 * 
	 * @param sourceDate
	 * @param format
	 * @return true 日期有效 false 日期无效
	 */
	public static synchronized boolean isValidExpiryDate(String expiryDate) {
		try {
			expiryDate = "20" + expiryDate;
			// 检验是否是有效日期
			if (DateUtil.checkDate(expiryDate, "yyyyMM") == false) {
				return false;
			}
			String today = COMPACT_YEAR_MONTH_FORMATTER.format(new Date());
			if (DateUtil.compareTo(today, expiryDate, "yyyyMM") <= 0) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return false;

	}

	/**
	 * 线程安全的格式化方法datetostring
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format2(Date date, String format) {

		return DateFormatUtils.format(date, format);
	}

	/**
	 * 线程安全的格式化方法stringtodate
	 * 
	 * @param date
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date parse2(String date, String format) throws ParseException {
		String[] arg1 = new String[1];
		arg1[0] = format;
		return DateUtils.parseDate(date, arg1);
	}

	/**
	 * 比较2个日期相差的天数，如果为负数，说明 d2 的日期要早于d1
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int compareByDay(Date d1, Date d2) {
		if (null == d1 || null == d2) {
			throw new RuntimeException("the input d1 or d2 is null!");
		}
		long intervalMilli = d2.getTime() - d1.getTime();
		return (int) (intervalMilli / (24 * 60 * 60 * 1000));
	}

	/**
	 * 获取当前年
	 * 
	 * @return
	 */
	public static int getYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 把时间拼接成160525144444
	 * 
	 * @param date
	 * @return
	 */
	public static synchronized String getStringDateTimeStamp(Date date) {
		if (date == null) {
			return "";
		}
		return DATE_TIMESTAMP.format(date);

	}

	/**
	 * 获取当前时间之前minute或者之后minute的时间
	 * 
	 * @param minute
	 * @return
	 */
	public static synchronized String getTimeByMinute(int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minute);
		return COMPACT_DATE_TIME_FORMATTER.format(calendar.getTime());
	}

	/**
	 * 获取当前时间之前second或者之后second的时间
	 * 
	 * @param minute
	 * @return
	 */
	public static synchronized String getTimeBySecond(int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.SECOND, second);
		return COMPACT_DATE_TIME_FORMATTER.format(calendar.getTime());
	}

	/**
	 * @param date
	 * @param n
	 *            间隔天数
	 * @return
	 */
	public static Date beforeDay(Date date, int n) {
		synchronized (cal) {
			cal.setTime(date);
			cal.add(Calendar.DATE, -n);
			return cal.getTime();
		}
	}
	 /**
	 * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public  static String getDateFormatter(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(DATE_TIME_FORMATTER_STRING);
		return df.format(date);
	}
	/**
	 * 比较String类型日期之间相差的天数
	 * @param dateFrom
	 * @param dateTo
	 * @return
	 * @throws ParseException 
	 */
	public static int differenceDate(String dateFrom,String dateTo, String pattern) throws ParseException{
		
		SimpleDateFormat sdf=new SimpleDateFormat(pattern);  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(sdf.parse(dateTo));    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(sdf.parse(dateFrom));    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));
		
		
		
	}


	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	// 对日期格式的转换成（"yyyy-MM-dd"）格式的方法
	public static java.sql.Date convert(String str, SimpleDateFormat simpleDateFormat) {
		java.text.SimpleDateFormat sdf = simpleDateFormat;
		try {
			java.util.Date d = sdf.parse(str);
			java.sql.Date d1 = new java.sql.Date(d.getTime());
			return d1;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据开始时间和结束时间返回时间段内的时间集合
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return List
	 */
	public static List<String> getDatesBetweenTwoDate(String bDate, String eDate) throws Exception {
		Date beginDate = parse2(bDate, DateUtil.yyyyMMdd);
		Date endDate = parse2(eDate, DateUtil.yyyyMMdd);
		List<String> lDate = new ArrayList<String>();
		lDate.add(bDate);// 把开始时间加入集合
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(beginDate);
		boolean bContinue = true;
		if (bDate.equals(eDate)) {
			return lDate;
		}
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (endDate.after(cal.getTime())) {
				lDate.add(toString(cal.getTime(), DateUtil.yyyyMMdd));
			} else {
				break;
			}
		}

		lDate.add(eDate);// 把结束时间加入集合
		return lDate;
	}

	public static void main(String[] args) {
		try {
			System.out.println(getDatesBetweenTwoDate("20170501", "20170520"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static boolean isToday(Date date){
	     SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
	     if(fmt.format(date).toString().equals(fmt.format(new Date()).toString())){//格式化为相同格式
	          return true;
	      }else {
	        return false;
	      }
	}    

	/**
	 * 给特定格式的时间，添加秒
	 * 
	 * @param date
	 * @param format 时间格式如：yyyymmddHHmmss
	 * @param amount
	 * @return
	 * @throws ParseException
	 */
	public static String addSeconds(String date, String format, int amount) throws ParseException {
		Date oldDate = DateUtils.parseDate(date, format);
		Date newDate = DateUtils.addSeconds(oldDate, amount);
		return DateFormatUtils.format(newDate, format);
	}

	/**
	 * 给特定格式的时间，添加小时
	 * 
	 * @param date
	 * @param format 时间格式如：yyyymmddHHmmss
	 * @param amount
	 * @return
	 * @throws ParseException
	 */
	public static String addHours(String date, String format, int amount) throws ParseException {
		Date oldDate = DateUtils.parseDate(date, format);
		Date newDate = DateUtils.addHours(oldDate, amount);
		return DateFormatUtils.format(newDate, format);
	}

	/**
	 * 获取和当前时间的相差秒数
	 * 
	 * @param firstTime
	 * @param theSecondTime
	 * @param format
	 * @return 
	 * @throws ParseException
	 */
	public static long getTimeDifferenceOfSeconds(String time, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date now = new Date();
		Date first = sdf.parse(time);
		long seconds = Math.abs(first.getTime() / 1000 - now.getTime() / 1000);
		return seconds;
	}

	/**
	 * 时间格式转换
	 * 
	 * @param time
	 * @param sourceFormat
	 * @param targetFormat
	 * @return
	 * @throws ParseException
	 */
	public static String changeTimeFormat(String time, String sourceFormat, String targetFormat) throws ParseException {
		Date date = DateUtils.parseDate(time, sourceFormat);
		return DateFormatUtils.format(date, targetFormat);
	}

	/**
	 * 获取前n分钟
	 * 
	 * @param minute
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static Date getBeforeMinuteTime(int minute, String format) {
		String returnstr = "";
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - minute);
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			returnstr = sdf.format(calendar.getTime());
			date = sdf.parse(returnstr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}