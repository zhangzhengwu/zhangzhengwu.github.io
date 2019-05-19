package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
/**
 * @author wilson
 * 常量类
 */
public class DateHelper {

	@SuppressWarnings("static-access")
	public static String getMonthday() {

		DateUtils d = new DateUtils();
		Calendar day = Calendar.getInstance(Locale.CHINA);
		day.setFirstDayOfWeek(Calendar.MONDAY);// 周一为每个星期的第一天
		@SuppressWarnings("unused")
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat date1 = new SimpleDateFormat("yyyy");
		SimpleDateFormat date2 = new SimpleDateFormat("MM");
		SimpleDateFormat date3 = new SimpleDateFormat("dd");
		int MaxDay = d.getDays(Integer.parseInt(date1.format(day.getTime())),
				Integer.parseInt(date2.format(day.getTime())));

		day.setTimeInMillis(System.currentTimeMillis());// 设置当前时间
		// System.out.println("当前时间为："+date.format(day.getTime()));//输出当前时间

		day.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		int year = Integer.parseInt(date1.format(day.getTime()));
		int month = Integer.parseInt(date2.format(day.getTime()));
		int day1 = Integer.parseInt(date3.format(day.getTime()));

		if (day1 + 1 > MaxDay) {
			return year + "-0" + (month + 1) + "-" + "01";
		} else {
			if ((day1 + 1) >= 10) {
				return year + "-0" + month + "-" + (day1 + 1);
			}
			return year + "-0" + month + "-0" + (day1 + 1);

		}

	}

	/**
	 * Format a date to specified format.
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if (date != null && date instanceof Date) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
		return null;
	}

	/**
	 * Format date, yyyyMMdd to yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static final String formatDate(String date) {
		if (date == null || date.trim().length() == 0) {
			return "";
		}
		StringBuffer buffer = new StringBuffer(10);
		buffer.append(date.substring(0, 4));
		buffer.append("-");
		buffer.append(date.substring(4, 6));
		buffer.append("-");
		buffer.append(date.substring(6, 8));
		return buffer.toString();

	}

	/**
	 * parse date yyyy-MM-dd to yyyyMMdd
	 * 
	 * @param date
	 * @return
	 */
	public static final String parseDate(String date) {
		return date.replaceAll("-", "");
	}

	/**
	 * getDate
	 * 
	 * @param pdate
	 * @param pattern
	 * @return
	 */
	public static final String getDate(Date pdate, String pattern) {
		if (pattern == null)
			pattern = "yyyyMMdd";
		return (new SimpleDateFormat(pattern)).format(pdate);

	}

	/**
	 * parse date, yyyy-MM-dd to yyyyMMdd
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 */
	public static final Date parseDate(String dateStr, String pattern) {
		if (pattern == null)
			pattern = "yyyyMMdd";
		Date d = null;

		try {
			d = (new SimpleDateFormat(pattern)).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}

		return d;
	}

	/**
	 * 
	 * @param edate
	 * @param sdate
	 * @return
	 */
	public static final long getDayNum(String edate, String sdate,
			String pattern) {
		if ((edate == null) || (sdate == null))
			return ErrCode.NULL_DATE_ERR;
		if (edate.compareTo(sdate) < 0)
			return ErrCode.EDATE_SMALL_ERR;

		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = formatter.parse(edate);
			d2 = formatter.parse(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
			return ErrCode.DATE_PARSE_ERR;
		}
		long day = (d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * format date from fpat to tpat
	 * 
	 * @param pdate
	 * @param fpat
	 * @param tpat
	 * @return
	 */
	public static final String formatDate(String pdate, String fpat, String tpat) {
		System.out.println(tpat);
		if (pdate == null || pdate.trim().length() == 0)
			return "";
		SimpleDateFormat formatter = new SimpleDateFormat(tpat);
		Date tmp;
		try {
			tmp = formatter.parse(pdate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		// formatter.applyPattern(tpat);

		// System.out.println(formatter.format(tmp));
		return formatter.format(tmp);
	}

	// get latest monthes
	@SuppressWarnings("unchecked")
	public static final List getMonthes(String month, int num) {
		String[] arr = null;
		if (num > 12)
			return null;
		int i = 0;
		List list = new ArrayList();
		if (month.indexOf("-") > 0)
			arr = StringHelper.split(month, "-");
		else {
			arr = new String[2];
			arr[0] = month.substring(0, 4);
			arr[1] = month.substring(4, 6);
		}

		int[] iarr = new int[2];
		for (i = 0; i < 2; i++) {
			iarr[i] = Integer.parseInt(arr[i]);
		}
		i = 0;
		while (i < num) {
			if (month.indexOf("-") > 0) {
				if (iarr[1] <= i)
					list.add((iarr[0] - 1) + "-"
							+ ((12 + (iarr[1] - i)) >= 10 ? "" : "0")
							+ (12 + (iarr[1] - i)));
				else
					list.add(iarr[0] + "-" + ((iarr[1] - i) >= 10 ? "" : "0")
							+ (iarr[1] - i));
			} else {
				String m = null;
				if (iarr[1] <= i) {
					if ((12 + (iarr[1] - i)) < 10)
						m = "0" + (12 + (iarr[1] - i));
					else
						m = "" + (12 + (iarr[1] - i));
					list.add((iarr[0] - 1) + m);
				} else {
					if ((iarr[1] - i) < 10)
						m = "0" + (iarr[1] - i);
					else
						m = "" + (iarr[1] - i);
					list.add(iarr[0] + m);
				}
			}
			i++;
		}
		return list;
	}

	public static final String getLastMonth(String date) {
		if (date == null)
			return null;
		return (String) (getMonthes(date, 2).get(1));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// System.out.println(DateHelper.getInstance().getDate(new Date(),
		// "yyyyMMdd"));
		// System.out.println(DateHelper.getInstance().formatDate("2006-1-1",
		// "yyyy-M-d","yyyyMMdd" ));

		// List list = DateHelper.getMonthes("2006-01-01", 2);
		// for( Iterator iter=list.iterator(); iter.hasNext(); )
		// {
		// System.out.println((String)iter.next());
		// }
		System.out.println(DateHelper.getLastMonth("20061111"));
	}

}
