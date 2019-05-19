package util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class NumberUtil
{
	/**
	 * 字符串转  int  
	 */
	public static int StrToInt(String s)
	{
		try
		{
			return new Integer(s).intValue();
		} catch (Exception e)
		{
			return 0;
		}
	}

	/**
	 * 字符串转  long  
	 */
	public static long StrToLong(String s)
	{
		try
		{
			return new Long(s).longValue();
		} catch (Exception e)
		{
			return 0;
		}
	}

	/**
	 * 字符串转 double 
	 */
	public static double StrToDouble(String s)
	{
		try
		{
			return new Double(s).doubleValue();
		} catch (Exception e)
		{
			return 0.00;
		}
	}

	/**
	 * 返回INT型的字符
	 */
	public static String toNumberStr(int d)
	{
		try
		{
			DecimalFormat dTmp = new DecimalFormat();
			String s = "####################0";
			dTmp.applyPattern(s);
			String s1 = dTmp.format(d);
			return s1;
		} catch (Exception e)
		{
			return "0";
		}
	}
	public static String toNumberStr(int d, int iXsd)
	{
		return toNumberStr(d);
	}

	/**
	 * 返回LONG型的字串
	 */
	public static String toNumberStr(long d)
	{
		try
		{
			DecimalFormat dTmp = new DecimalFormat();
			String s = "###################0";
			dTmp.applyPattern(s);
			String s1 = dTmp.format(d);
			return s1;
		} catch (Exception e)
		{
			return "0";
		}
	}

	public static String toNumberStr(long d, int iXsd)
	{
		return toNumberStr(d);
	}

	public static void main(String[] args) {
			List<String> list=new ArrayList<String>();
			list.add("WW");
			list.add("SS456");
			System.out.println(list.contains("SS456"));
	}
	/**
	 * 返回DOUBLE型的字串
	 */
	public static String toNumberStr(double d)
	{
		try
		{
			DecimalFormat dTmp = new DecimalFormat();
			String s = "###################0.0000000000000000";
			dTmp.applyPattern(s);
			String s1 = dTmp.format(d);
			// 删除未位"0"
			int i1 = s1.length();
			for (int i = s1.length() - 1; i > s1.length() - 11; i--)
			{
				if (s1.charAt(i) == '0')
					i1--;
				else
					break;
			}
			s1 = s1.substring(0, i1);
			return s1;
		} catch (Exception e)
		{
			return "0.00";
		}
	}

	/**
	 * 返回DOUBLE型的字串, iXsd为小数点位数
	 */
	public static String toNumberStr(double d, int iXsd)
	{
		try
		{
			DecimalFormat dTmp = new DecimalFormat();
			String s = "####################0.";
			for (int i = 0; i < iXsd; i++)
				s += "0";
			dTmp.applyPattern(s);
			String s1 = dTmp.format(d);
			if (d > -0.000001 && d < 0.000001)
			{
				s1 = "0.";
				for (int i = 0; i < iXsd; i++)
					s1 += "0";
			}
			// 删除未位"0"
			/*
			int i1 = s1.length();
			iXsd = i1 - iXsd;
			int iDot = s1.indexOf('.') + iXsd;
			for (int i = i1 - 1; i > iDot; i--)
			{
				if (s1.charAt(i) == '0')
					i1--;
				else
					break;
			}
			s1 = s1.substring(0, i1);
			*/
			return s1;
		} catch (Exception e)
		{
			return "0.00";
		}
	}

	/**
	 * 带分节号的int型字
	 */
	public static String toNumberSegmentStr(int d)
	{
		try
		{
			DecimalFormat dTmp = new DecimalFormat();
			String s = "#,###,###,###,###,##0";
			dTmp.applyPattern(s);
			String s1 = dTmp.format(d);
			return s1;
		} catch (Exception e)
		{
			return "0";
		}
	}

	public static String toNumberSegmentStr(int d, int iXsd)
	{
		return toNumberSegmentStr(d);
	}

	/**
	 * 带分节号的long型字
	 */
	public static String toNumberSegmentStr(long d)
	{
		try
		{
			//NumberFormat 是所有数字格式的抽象基类。此类提供了格式化和分析数字的接
			//DecimalFormat是NumberFormat 的一个具体子类，用于格式化十进制数字
			DecimalFormat dTmp = new DecimalFormat();
			String s = "#,###,###,###,###,##0";
			//将给定的模式应用于此 Format 对象
			dTmp.applyPattern(s);
			String s1 = dTmp.format(d);
			return s1;
		} catch (Exception e)
		{
			return "0";
		}
	}

	public static String toNumberSegmentStr(long d, int iXsd)
	{
		return toNumberSegmentStr(d);
	}

	/**
	 * 带分节号的double型字 iXsd为小数点的位
	 */
	public static String toNumberSegmentStr(double d, int iXsd)
	{
		try
		{
			DecimalFormat dTmp = new DecimalFormat();
			String s = "#,###,###,###,###,##0.";
			for (int i = 0; i < iXsd; i++)
				s += "0";
			dTmp.applyPattern(s);
			String s1 = dTmp.format(d);
			if (d > -0.0001 && d < 0.0001)
			{
				s1 = "0.";
				for (int i = 0; i < iXsd; i++)
					s1 += "0";
			}
			/*
			// 删除未位"0"
			int i1 = s1.length();
			iXsd = i1 - iXsd;
			int iDot = s1.indexOf('.') + iXsd;
			for (int i = i1 - 1; i > iDot; i--)
			{
				if (s1.charAt(i) == '0')
					i1--;
				else
					break;
			}
			s1 = s1.substring(0, i1);
			*/
			return s1;
		} catch (Exception e)
		{
			return "0.00";
		}
	}

	/**
	 * 返回DOUBLE型的字串
	 */
	public static String toDoubleStr(double d)
	{
		try
		{
			DecimalFormat dTmp = new DecimalFormat();
			String s = "################.0000000000000000";
			dTmp.applyPattern(s);
			String s1 = dTmp.format(d);
			// 删除未位"0"
			int i1 = s1.length();
			for (int i = s1.length() - 1; i > s1.length() - 11; i--)
			{
				if (s1.charAt(i) == '0')
					i1--;
				else
					break;
			}
			s1 = s1.substring(0, i1);
			return s1;
		} catch (Exception e)
		{
			return "0.00";
		}
	}

	/**
	 * 带分节号的double型字
	 */
	public static String toSegment(double d, int iXsd)
	{
		try
		{
			DecimalFormat dTmp = new DecimalFormat();
			String s = "#,###,###,###,###,##0.";
			for (int i = 0; i < iXsd; i++)
				s += "0";
			dTmp.applyPattern(s);
			String s1 = dTmp.format(d);
			if (d > -0.0001 && d < 0.0001)
			{
				s1 = "0.00";
			}
			// 删除未位"0"
			int i1 = s1.length();
			iXsd = i1 - iXsd;
			int iDot = s1.indexOf('.') + iXsd;
			for (int i = i1 - 1; i > iDot; i--)
			{
				if (s1.charAt(i) == '0')
					i1--;
				else
					break;
			}
			s1 = s1.substring(0, i1);
			return s1;
		} catch (Exception e)
		{
			return "0.00";
		}
	}
   
	public static String toSegment1(double d, int iXsd)
	{
		try
		{
			DecimalFormat dTmp = new DecimalFormat();
			String s = "################0.";
			for (int i = 0; i < iXsd; i++)
				s += "0";
			dTmp.applyPattern(s);
			String s1 = dTmp.format(d);
			if (d > -0.000001 && d < 0.000001)
			{
				s1 = "0.00";
			}
			// 删除未位"0"
			int i1 = s1.length();
			iXsd = i1 - iXsd;
			int iDot = s1.indexOf('.') + iXsd;
			for (int i = i1 - 1; i > iDot; i--)
			{
				if (s1.charAt(i) == '0')
					i1--;
				else
					break;
			}
			s1 = s1.substring(0, i1);
			return s1;
		} catch (Exception e)
		{
			return "0.00";
		}
	}

	/**
	 * 不输出前0
	 */
	public static String toSegment2(double d, int iXsd)
	{
		try
		{
			DecimalFormat dTmp = new DecimalFormat();
			String s = "################.";
			for (int i = 0; i < iXsd; i++)
				s += "0";
			dTmp.applyPattern(s);
			String s1 = dTmp.format(d);
			if (d > -0.000001 && d < 0.000001)
			{
				s1 = ".00";
			}
			// 删除未位"0"
			int i1 = s1.length();
			iXsd = i1 - iXsd;
			int iDot = s1.indexOf('.') + iXsd;
			for (int i = i1 - 1; i > iDot; i--)
			{
				if (s1.charAt(i) == '0')
					i1--;
				else
					break;
			}
			s1 = s1.substring(0, i1);
			return s1;
		} catch (Exception e)
		{
			return ".00";
		}
	}

	/**
	 * 带分节号的long型字
	 */
	public static String toSegment(long d)
	{
		try
		{
			DecimalFormat dTmp = new DecimalFormat();
			String s = "#,###,###,###,###,###";
			dTmp.applyPattern(s);
			String s1 = dTmp.format(d);
			return s1;
		} catch (Exception e)
		{
			return "0";
		}
	}

	public static String toSegment1(long d)
	{
		try
		{
			DecimalFormat dTmp = new DecimalFormat();
			String s = "################";
			dTmp.applyPattern(s);
			String s1 = dTmp.format(d);
			return s1;
		} catch (Exception e)
		{
			return "0";
		}
	}

	/**
	 * 字符串型转为int
	 */
	public static int toInt(String s)
	{
		try
		{
			return new Integer(s).intValue();
		} catch (Exception e)
		{
			return 0;
		}
	}

	/**
	 * 字符串型转为double
	 */
	public static double toDouble(String s)
	{
		try
		{
			return new Double(s).doubleValue();
		} catch (Exception e)
		{
			return 0.00;
		}
	}
}
