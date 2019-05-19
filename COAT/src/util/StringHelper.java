/**
 * 
 */
package util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * 
 */
public class StringHelper
{

	public StringHelper()
	{
		super();
	}

	/**
	 * split data into array
	 */
	@SuppressWarnings("unchecked")
	public final static String[] split(String data, String s)
	{
		List tmp = new ArrayList();
		StringTokenizer st = null;
		if (" ".equals(s))
			st = new StringTokenizer(data);
		else
			st = new StringTokenizer(data, s);

		while (st.hasMoreTokens())
		{
			String str = st.nextToken();
			tmp.add(str);
		}

		String[] arr = new String[tmp.size()];
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = (String) tmp.get(i);
		}
		return arr;
	}

	/**
	 * 将N/A 置为“”
	 * @param string
	 * @return
	 */
	public static String replaceStr(String str){
		if(!Util.objIsNULL(str)){
			if(str.toUpperCase().equals("CHECK")||str.toUpperCase().equals("N/A") || str.toUpperCase().equals("BV") || str.toLowerCase().equals("w/d")) {
				return str = "";
			}else{
				return str;
			}
			
		}else{
			return str = "";
		}
		
	}
	
	public final static String delnr(String input)
	{
		return input.replaceAll("[\\n|\\r]", "");
	}
	/**
	 * 转换字符串为HTML 
	 */
	public final static String toHTML(String sStr)
	{
		if (sStr == null || sStr.equals(""))
		{
			return sStr;
		}

		String sTmp = new String();
		int i = 0;
		while (i <= sStr.length() - 1)
		{
			if (sStr.charAt(i) == '\n')
			{
				//将指定字符串联到此字符串的结尾。
				sTmp = sTmp.concat("<br />");
			} else if (sStr.charAt(i) == ' ')
			{
				sTmp = sTmp.concat("%26amp;nbsp;");
			} else if (sStr.charAt(i) == '<')
			{
				sTmp = sTmp.concat("<");
			} else if (sStr.charAt(i) == '>')
			{
				sTmp = sTmp.concat(">");
			} else if (sStr.charAt(i) == 34) // 34为双引号
			{
				sTmp = sTmp.concat("\"");
			} else if (sStr.charAt(i) == 39) // 若为单引号则用两个单引号代替
			{
				sTmp = sTmp.concat("''");
			} else
			{
				sTmp = sTmp.concat(sStr.substring(i, i + 1));
			}
			i++;
		}
		return sTmp;
	}

	/**
	 *  
	 */
	public static String replaceAll(String str, String from, String to)
	{
		if (str == null || str.length() == 0)
		{
			return str;
		} else if (str.length() == 1 && str.equals(from))
		{
			return to;
		} else if (str.length() == 1 && !str.equals(from))
		{
			return str;
		}
		int j = -1;
		while ((j = str.indexOf(from)) >= 0)
		{
			str = str.substring(0, j) + (char) 5
					+ str.substring(j + from.length());
		}

		int i = -1;
		while ((i = str.indexOf((char) 5)) >= 0)
		{
			str = str.substring(0, i) + to + str.substring(i + 1);
		}

		return str;
	}

	@SuppressWarnings("null")
	public static boolean isDecimal(String str)
	{
		if( str != null )
			return false;
		String strTemp =str.toLowerCase();

		char c = str.charAt(0);
		if (!(c == '+' || c == '-' || Character.isDigit(c)))
			return false;
		else if (c == '+' || c == '-')
			strTemp = str.substring(1);

		int size = strTemp.length();
		for (int i = 0; i < size; i++)
		{
			char tempChar = strTemp.charAt(i);
			if (!(Character.isDigit(tempChar) || tempChar == '.' || tempChar == 'e'))
				return false;
			else
			{
				if (tempChar == '.')
				{
					if (i == 0 || i == size - 1)
						return false;
				}
				if (tempChar == 'e')
				{
					tempChar = str.charAt(i + 1);
					if (!(tempChar == '+' || tempChar == '-' || Character
							.isDigit(tempChar)))
						return false;
					else
					{
						if (tempChar == '+' || tempChar == '-')
						{
							strTemp = str.substring(i + 2);
						} else
						{
							strTemp = str.substring(i + 1);
						}
					}
					for (int ii = 0; ii < strTemp.length(); ii++)
					{
						tempChar = strTemp.charAt(ii);
						if (!Character.isDigit(tempChar))
							return false;
					}
					return true;
				}

			}
		}
		return true;
	}

	public static boolean isInteger(String str)
	{
		if (str == null || str.equals(""))
			return false;
		char[] c = str.toCharArray();
		boolean blReturn = true;
		for (int ni = 0; ni < c.length; ni++)
		{
			if (c[ni] < 48 || c[ni] > 57) //   
			{
				blReturn = false;
				break;
			}
		}
		return blReturn;
	}

	public static void main(String[] args)
	{
		/*String data = "1233333332|3223|2323";
		String[] a = StringHelper.split(data, "|");
		for (int i = 0; i < a.length; i++)
			System.out.println(a[i]);
		System.out.println("");
		System.out.println(a.length);*/
		
		String  htmlString = "sadasd<br/>nsada";
		String aString = StringHelper.htmlToText(htmlString);
		System.out.println(htmlString);
		System.out.println(aString);
	}

	public static String unescape(String s)
	{
		if (s == null)
			return "";
		StringBuffer sbuf = new StringBuffer();
		int i = 0;
		int len = s.length();
		while (i < len)
		{
			int ch = s.charAt(i);
			if ('A' <= ch && ch <= 'Z')
			{ // 'A'..'Z' : as it was
				sbuf.append((char) ch);
			} else if ('a' <= ch && ch <= 'z')
			{ // 'a'..'z' : as it was
				sbuf.append((char) ch);
			} else if ('0' <= ch && ch <= '9')
			{ // '0'..'9' : as it was
				sbuf.append((char) ch);
			} else if (ch == '-'
					|| ch == '_' // unreserved : as it was
					|| ch == '.' || ch == '!' || ch == '~' || ch == '*'
					|| ch == '\'' || ch == '(' || ch == ')')
			{
				sbuf.append((char) ch);
			} else if (ch == '%')
			{
				int cint = 0;
				if ('u' != s.charAt(i + 1))
				{ // %XX : map to ascii(XX)
					cint = (cint << 4) | val[s.charAt(i + 1)];
					cint = (cint << 4) | val[s.charAt(i + 2)];
					i += 2;
				} else
				{ // %uXXXX : map to unicode(XXXX)
					cint = (cint << 4) | val[s.charAt(i + 2)];
					cint = (cint << 4) | val[s.charAt(i + 3)];
					cint = (cint << 4) | val[s.charAt(i + 4)];
					cint = (cint << 4) | val[s.charAt(i + 5)];
					i += 5;
				}
				sbuf.append((char) cint);
			} else
			{ // 对应的字符未经过编码
				sbuf.append((char) ch);
			}
			i++;
		}
		return sbuf.toString();
	}

	/**
	 * to utf-8 
	 * @param s
	 * @return
	 */
	public static String toUtf8String(String s)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if (c >= 0 && c <= 255)
			{
				sb.append(c);
			} else
			{
				byte[] b;
				try
				{
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex)
				{
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++)
				{
					int k = b[j];
					if (k < 0)
						k += 256;
					//toHexString()以十六进制的无符号整数形式返回一个整数参数的字符串表示形式。
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
	/**
	 * 按长度截取字符串，超过长度的添加省略符，显示缩写
	 * 
	 * @author YPJ
	 * @param original
	 * @param width
	 * @param ellipsis
	 * @return
	 */
	public static String abbreviate(String original, int width, String ellipsis) {
		if (original == null || "".equals(original)) {
			return "";
		}
		int byteIndex = 0;
		int charIndex = 0;
		for (; charIndex < original.length(); charIndex++) {
			byteIndex = (int) original.charAt(byteIndex) > 256 ? byteIndex + 2
					: byteIndex + 1;
			if (byteIndex > width) {
				break;
			}
		}
		if (byteIndex > width) {
			charIndex = charIndex - ellipsis.length()/2*2;
			return original.substring(0, charIndex > 0 ? charIndex : 0)
					+ ellipsis;
		}
		return original.substring(0, charIndex);
	}

	public static String htmlToText(String inputString) {
		String htmlStr = inputString;
		String textStr = "";
		String scriptRegEx = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
		String styleRegEx = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
		String htmlRegEx1 = "<[^>]*>";
		String htmlRegEx2 = "<[^>]*";
		try {
			Pattern scriptPattern = Pattern.compile(scriptRegEx,
					Pattern.CASE_INSENSITIVE);
			Matcher scriptMatcher = scriptPattern.matcher(htmlStr);
			htmlStr = scriptMatcher.replaceAll("");
			Pattern stylePattern = Pattern.compile(styleRegEx,
					Pattern.CASE_INSENSITIVE);
			Matcher styleMatcher = stylePattern.matcher(htmlStr);
			htmlStr = styleMatcher.replaceAll("");
			Pattern htmlPattern1 = Pattern.compile(htmlRegEx1,
					Pattern.CASE_INSENSITIVE);
			Matcher htmlMatcher1 = htmlPattern1.matcher(htmlStr);
			htmlStr = htmlMatcher1.replaceAll("");
			Pattern htmlPattern2 = Pattern.compile(htmlRegEx2,
					Pattern.CASE_INSENSITIVE);
			Matcher htmlMatcher2 = htmlPattern2.matcher(htmlStr);
			htmlStr = htmlMatcher2.replaceAll("");
			textStr = htmlStr;
		} catch (Exception e) {
			System.err.println("->htmlToText(String inputString):"
					+ e.getMessage());
		}
		textStr = textStr.replaceAll("&acute;", "\'");
		textStr = textStr.replaceAll("&quot;", "\"");
		textStr = textStr.replaceAll("&lt;", "<");
		textStr = textStr.replaceAll("&gt;", ">");
		textStr = textStr.replaceAll("&nbsp;", " ");
		textStr = textStr.replaceAll("&amp;", "&");
		return textStr;
	}

	private final static byte[] val = { 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00, 0x01,
			0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F };

}
