package com.lingxin.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 将十进制的数字转换为X进制的字符串
 * 
 * @author feiying
 *
 */
public class XDecimalConvertor {
	private static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A',
			'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z' };

	private static Map<String, Integer> digitMap = new HashMap<String, Integer>();
	static {
		for (int i = 0; i < digits.length; i++) {
			char c = digits[i];
			digitMap.put(String.valueOf(c), i);
		}
	}

	/**
	 * 将十进制的数字转换为62进制的字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String octal2XDecimal(Long src) {
		StringBuffer buffer = new StringBuffer();

		int digit_count = digits.length;

		long val = src;
		do {
			if (val > 0) {
				long index = val % digit_count;
				buffer.append(digits[(int) index]);
			}
			val = Long.divideUnsigned(val, digit_count);
		} while (val > 0);

		return buffer.reverse().toString();
	}

	/**
	 * 将62进制的字符串转换为十进制的数字
	 * 
	 * @param src
	 * @return
	 */
	public static Long xDecimal2Octal(String src) {
		if (null == src || "".equals(src.trim())) {
			return null;
		}

		Double result = 0D;

		int digit_count = digits.length;

		StringBuffer buffer = new StringBuffer(src.trim());
		char[] valArray = buffer.reverse().toString().toCharArray();
		for (int i = 0; i < valArray.length; i++) {
			char c = valArray[i];
			int v = digitMap.get(String.valueOf(c));
			result += v * Math.pow(digit_count, i);
		}

		return result.longValue();
	}

	public static void main(String[] args) {
		Long src = 8980956L;
		String dst = XDecimalConvertor.octal2XDecimal(src);
		System.out.println(src + " ==> " + dst);
		System.out.println(dst + " ==> " + XDecimalConvertor.xDecimal2Octal(dst));
	}
}
