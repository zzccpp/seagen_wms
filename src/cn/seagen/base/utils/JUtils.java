package cn.seagen.base.utils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class JUtils {
	private static String regex = "[-:]| +";

	public static String getRegexStr(String str, int len) {
		if (isEmpty(str))
			return str;
		String regexstr = str.replaceAll(regex, "");
		if (regexstr.length() > len)
			regexstr = regexstr.substring(0, len);
		return regexstr;
	}

	/** 休眠 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	public static long subTime(String dateStr1, String dateStr2) {
		try {
			DateFormat fmt = null;
			if (dateStr1.length() == 10)
				fmt = new SimpleDateFormat("yyyy-MM-dd");
			else if (dateStr1.length() == 19)
				fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			else
				return 0;
			Date date1 = fmt.parse(dateStr1);

			if (dateStr2.length() == 10)
				fmt = new SimpleDateFormat("yyyy-MM-dd");
			else if (dateStr2.length() == 19)
				fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			else
				return 0;
			Date date2 = fmt.parse(dateStr2);

			return Math.abs(date2.getTime() - date1.getTime());
		} catch (ParseException e) {
			return 0;
		}
	}

	public static boolean isDateStr(String dateStr) {
		try {
			if (isEmpty(dateStr))
				return false;

			DateFormat fmt = null;
			if (dateStr.length() == 10)
				fmt = new SimpleDateFormat("yyyy-MM-dd");
			if (dateStr.length() == 19)
				fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (fmt == null)
				return false;

			fmt.parse(dateStr);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public static String getCurrDateStr(String dateStr) {
		try {
			DateFormat fmt = null;
			if (dateStr.length() == 10)
				fmt = new SimpleDateFormat("yyyy-MM-dd");
			if (dateStr.length() == 19)
				fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (fmt == null)
				return dateStr;
			Date date = fmt.parse(dateStr);
			return fmt.format(date);
		} catch (ParseException e) {
			return dateStr;
		}
	}


	public static String formatDateTime(String date, int n) {
		if ((n <= 0) || (n > 12))
			n = 3;

		SimpleDateFormat formatter = null;
		if (date.length() == 19)
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		else
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		Date d = null;
		try {
			d = (Date) formatter.parse(date);
		} catch (Exception e) {
			d = new Date();
		}
		//String time = System.nanoTime() % ((int) Math.pow(10, n)) + "";
		String nanoTime = String.valueOf(System.nanoTime());
		String time = nanoTime.substring(nanoTime.length()-n);
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d)) + "." + time;
	}

	public static String incDay(int days) {
		return DateUtils.formatDateTime("yyyy-MM-dd HH:mm:ss", incDay(new Date(), days));
	}

	private final static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>() {
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	public static Date strToDate(String datestr) {
		try {
			String str = datestr.trim();
			if (str.length() == 10)
				str = str + " 00:00:00";
			if (str.length() > 10)
				str = str.substring(0, 19);
			return dateFormat.get().parse(str);
		} catch (Exception e) {
			return new Date();
		}
	}

	public static Date incDay(Date paramDate, int days) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		int i = localCalendar.get(6);
		localCalendar.set(6, i + days);
		return localCalendar.getTime();
	}

	// 日期加减天数
	public static Date IncDate(Date date, int day) {
		date = (date == null) ? (new Date()) : date;
		long time = date.getTime() + ((long) day) * 24 * 60 * 60 * 1000;
		return new Date(time);
	}

	public static int getDaysOfMonth(String dateStr) {
		Calendar rightNow = Calendar.getInstance();
		// 如果写成年月日的形式的话，要写小d，如："yyyy-MM-dd"
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM");
		int days = 0;
		try {
			// 要计算你想要的月份，改变这里即可
			rightNow.setTime(simpleDate.parse(dateStr));
			days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}

	public static double convertFloat(Object data) {
		DecimalFormat df = new DecimalFormat("0.###");
		try {
			return Double.parseDouble(df.format(data));
		} catch (Exception e) {
			return 0.0000;
		}
	}

	/** 获取完整错误日志 */
	public static String getExceptionFullMessage(Exception e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		e.printStackTrace(new PrintStream(baos));
		return baos.toString();
	}

	/** 判断字符是否为空，null, "null","nil"均判断为空 */
	public static boolean isEmpty(String str) {
		return (str == null || str.isEmpty() || str.equalsIgnoreCase("null") || str.equalsIgnoreCase("nil"));
	}

	/** 判断字符是否为空，null, "null","nil"均判断为空， 如果为空返回默认值 */
	public static String isEmpty(String str, String defval) {
		if (str == null || str.isEmpty() || str.equalsIgnoreCase("null") || str.equalsIgnoreCase("nil"))
			return defval;
		else
			return str.trim();
	}

	/** 字符转数字，非数字失败返回0 */
	public static int strToInt(String str) {
		try {
			return Integer.valueOf(str);
		} catch (Exception e) {
			return 0;
		}
	}

	/** 字符转数字，非数字失败返回默认值 */
	public static int strToInt(String str, int defval) {
		try {
			return Integer.valueOf(str);
		} catch (Exception e) {
			return defval;
		}
	}

	/** 字符转长整形，非长整形失败返回0 */
	public static Long strToLong(String str) {
		try {
			return Long.valueOf(str);
		} catch (Exception e) {
			return 0L;
		}
	}

	/** 字符转长整形，非长整形失败返回0 */
	public static Long strToLong(String str, long defval) {
		try {
			return Long.valueOf(str);
		} catch (Exception e) {
			return defval;
		}
	}


	/** 获取时间字符[n位毫秒]:2017-03-08 10:26:58.235... */
	public static String getNow(int n) {
		if ((n <= 0) || (n > 12))
			n = 3;
		//String time = System.nanoTime() % ((int)Math.pow(10, n)) + "";
		String nanoTime = String.valueOf(System.nanoTime());
		String time = nanoTime.substring(nanoTime.length()-n);
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())) + "." + time;
	}

	final static int BUFFER_SIZE = 4096;

	/** 将InputStream转换成String */
	public static String InputStreamTOString(InputStream in) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), "ISO-8859-1");
	}

	/** 将String转换成InputStream */
	public static InputStream StringTOInputStream(String in) throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes("ISO-8859-1"));
		return is;
	}

	/** 将InputStream转换成byte数组 */
	public static byte[] InputStreamTOByte(InputStream in) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[BUFFER_SIZE];
		int count = -1;
		while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return outStream.toByteArray();
	}

	/** 将byte数组转换成InputStream */
	public static InputStream byteTOInputStream(byte[] in) throws Exception {
		ByteArrayInputStream is = new ByteArrayInputStream(in);
		return is;
	}

	/** 将byte数组转换成String */
	public static String byteTOString(byte[] in) throws Exception {
		InputStream is = byteTOInputStream(in);
		return InputStreamTOString(is);
	}

	// 10进制字符串转整形
	public static int StrToIntDef(String str, int def) {
		try {
			return Integer.valueOf(str);
		} catch (Exception e) {
			return def;
		}
	}

	// 从request中获得参数Map，并返回可读的Map
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> get(HttpServletRequest request) {
		// properties返回的是只读的，不能修改，是因为系统对返回的参数进行了只读保护
		Map properties = request.getParameterMap();
		// 返回值Map
		Map<String, Object> map = new HashMap<String, Object>();
		//
		try {
			Iterator entries = properties.entrySet().iterator();
			Map.Entry entry;
			String name = "";
			Object value = "";
			String parameterstr = "ParameterMap:";

			while (entries.hasNext()) {
				entry = (Map.Entry) entries.next();
				name = ((String) entry.getKey()).toLowerCase().trim();
				Object valueObj = entry.getValue();
				if (null == valueObj) {
					value = "";
				} else if (valueObj instanceof String[]) {
					String[] values = (String[]) valueObj;
					String val = "";
					for (int i = 0; i < values.length; i++) {
						val = val + values[i].trim() + ";";
					}
					val = val.substring(0, val.length() - 1);
					value = val;
				} else {
					value = valueObj.toString().trim();
				}
				map.put(name, value);
				parameterstr = parameterstr + "&" + name + "=" + value;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/** 格式化SQL参数 */
	public static String FormatSqlPargram(String sql, int pargramnum) {
		int num = pargramnum;
		num = (num < 0) ? 0 : ((num > 100) ? 0 : num);
		String formatstr = "";
		if (num > 0)
			formatstr = "?";
		for (int i = 1; i < num; i++) {
			formatstr = formatstr + ",?";
		}
		return String.format(sql, formatstr);

	}

	// 10进制字符串转整形
	public static int StrToInt(String str) {
		try {
			return Integer.valueOf(str);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static String[] splitJedisStr(String value){
        try{
            return value.replace("][", "|||").replace("]", "").replace("[", "").split("\\|\\|\\|");
        }catch(Exception e){
            return new String[0];
        }
    }
}
