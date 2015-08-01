package com.xinyou.util;

import java.math.BigInteger;
import java.util.Stack;

/**
 * @author zcm
 *
 */
public class Code62 {
	/** The Base62 String Map */
	public static final char[] baseMap = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };

	/**
	 * <b>Use <code>{@link #valueOf(long)}</code> instead</b><br>
	 * Convert Long number to Base62 String.
	 * @param number the Long number.
	 * @return Base62 String value for the Long number.<br>
	 * <b>Case sensitive</b>.
	 */
	@Deprecated
	public static String to62(long number) {
		StringBuilder result = new StringBuilder(0);
		long rest = number;
		if(rest>0){
			Stack<Character> stack = new Stack<Character>();
			while (rest > 0) {
				stack.add(baseMap[new Long((rest % 62)).intValue()]);
				rest = rest / 62;
			}
			while (!stack.isEmpty()) {
				result.append(stack.pop());
			}
		}else{
			result.append(baseMap[0]);
		}
		return result.toString();
	}

	/**
	 * <b>Use <code>{@link #toLong(String)}</code> instead</b><br>
	 * Convert Base62 String to Long number.
	 * @param base62 the Base62 String, max to "AzL8n0Y58m7".
	 * @return Long value witch the Base62 String represented.
	 */
	@Deprecated
	public static long to10(String base62) {
		long multiple = 1L;
		long result = 0;
		Character c;
		for (int i = base62.length() - 1; i >= 0; i--) {
			c = base62.charAt(i);
			result += baseIndex(c) * multiple;
			multiple = multiple * 62;
		}
		return result;
	}
	
	/**
	 * Convert Long number to Base62 String.
	 * @param number the Long number.
	 * @return Base62 String value of the Long number, <b>Case sensitive</b>.
	 */
	public static String valueOf(long number) {
		long rest = number;
		StringBuilder result = new StringBuilder(0);
		if(rest>0){
			Stack<Character> stack = new Stack<Character>();
			while (rest > 0) {
				stack.add(baseMap[new Long((rest % 62)).intValue()]);
				rest = rest / 62;
			}
			while (!stack.isEmpty()) {
				result.append(stack.pop());
			}
		}else{
			result.append(baseMap[0]);
		}
		return result.toString();
	}

	/**
	 * Convert BigInteger number to Base62 String.
	 * @param number the BigInteger number.
	 * @return Base62 String value of the BigInteger number, <b>Case sensitive</b>.
	 */
	public static String valueOf(BigInteger number) {
		BigInteger rest = number;
		StringBuilder result = new StringBuilder(0);
		if(rest.compareTo(BigInteger.ZERO)>0){
			Stack<Character> stack = new Stack<Character>();
			while (rest.compareTo(BigInteger.ZERO)>0) {
				stack.add(baseMap[rest.mod(bigIntegerValueOf62).intValue()]);
				rest = rest.divide(bigIntegerValueOf62);
			}
			while (!stack.isEmpty()) {
				result.append(stack.pop());
			}
		}else{
			result.append(baseMap[0]);
		}
		return result.toString();
	}

	/**
	 * Convert Hex String number to Base62 String.
	 * @param hex the GUID String, case insensitive,<br>
	 * <code>'-'</code> in the String will be ignored (e.g. GUID Hex String).
	 * @return Base62 String value of the Hex String number, <b>case sensitive</b>.
	 */
	public static String valueOfHex(String hex){
		BigInteger rest = bigIntegerValueOfHex(hex.replace("-", "").toUpperCase());
		StringBuilder result = new StringBuilder(0);
		if(rest.compareTo(BigInteger.ZERO)>0){
			Stack<Character> stack = new Stack<Character>();
			while (rest.compareTo(BigInteger.ZERO)>0) {
				stack.add(baseMap[rest.mod(bigIntegerValueOf62).intValue()]);
				rest = rest.divide(bigIntegerValueOf62);
			}
			while (!stack.isEmpty()) {
				result.append(stack.pop());
			}
		}else{
			result.append(baseMap[0]);
		}
		return result.toString();
	}

	/**
	 * Convert Base62 String to Long number.<br>
	 * @param base62 the Base62 String, max to "AzL8n0Y58m7".
	 * @return Long value witch the Base62 String represented.
	 */
	public static long toLong(String base62) {
		long multiple = 1L;
		long result = 0;
		Character c;
		for (int i = base62.length() - 1; i >= 0; i--) {
			c = base62.charAt(i);
			result += baseIndex(c) * multiple;
			multiple = multiple * 62;
		}
		return result;
	}

	/**
	 * Convert Base62 String to Hex String number (upper case).<br>
	 * @param base62 the Base62 String.
	 * @return BigInteger number witch the Base62 String represented.
	 */
	public static BigInteger toBigInteger(String base62) {
		BigInteger result = BigInteger.ZERO;
		BigInteger multiple = BigInteger.ONE;
		Character c;
		for (int i = base62.length() - 1; i >= 0; i--) {
			c = base62.charAt(i);
			result = result.add(multiple.multiply(BigInteger.valueOf(baseIndex(c))));
			multiple = multiple.multiply(bigIntegerValueOf62);
		}
		return result;
	}

	/**
	 * Convert Base62 String to Hex String number (upper case).<br>
	 * @param base62 the Base62 String.
	 * @return Hex String number witch the Base62 String represented.
	 */
	public static String toHex(String base62){
		if (base62 == null || base62.length() == 0)
			return base62;

		StringBuilder result = new StringBuilder(0);
		BigInteger rest = toBigInteger(base62);
		if (rest.compareTo(BigInteger.ZERO) > 0) {
			Stack<Character> stack = new Stack<Character>();
			while (rest.compareTo(BigInteger.ZERO) > 0) {
				stack.add(baseMap[rest.mod(bigIntegerValueOf16).intValue()]);
				rest = rest.divide(bigIntegerValueOf16);
			}
			while (!stack.isEmpty()) {
				result.append(stack.pop());
			}
		} else {
			result.append(baseMap[0]);
		}
		return result.toString();
	}
	
	/**
	 * Convert Base62 String to Hex String in GUID representation (upper case).<br>
	 * @param base62 the Base62 String.
	 * @return the GUID style Hex value witch the Base62 String represented.<br>
	 * The Hex value will not be formatted with GUID style if it is longer than <code>32</code> or is <code>null</code>.
	 * Leading zeros will be added if the Hex value is not long enough.
	 */
	public static String toHexGuid(String base62){
		String hex = toHex(base62);
		if (hex == null || hex.length() > 32) {
			return hex;
		} else {
			int len=hex.length();
			if(len<32){
				hex=StringUtil.repeat('0', 32-len)+hex;
			}
			StringBuffer result=new StringBuffer(36);
			result.append(hex.substring(0, 8)).append('-')
				  .append(hex.substring(8, 12)).append('-')
				  .append(hex.substring(12, 16)).append('-')
				  .append(hex.substring(16, 20)).append('-')
				  .append(hex.substring(20, 32));
			return result.toString();
		}
	}

	/**
	 * Convert Hex String number to BigInteger number.
	 * @param hex the Hex String number, case insensitive.
	 * @return BigInteger value of the the Hex String number.
	 */
	private static BigInteger bigIntegerValueOfHex(String hex) {
		hex=hex.toUpperCase();
		BigInteger result = BigInteger.ZERO;
		BigInteger multiple = BigInteger.ONE;
		Character c;
		for (int i = hex.length() - 1; i >= 0; i--) {
			c = hex.charAt(i);
			result = result.add(multiple.multiply(BigInteger.valueOf(baseIndex(c))));
			multiple = multiple.multiply(bigIntegerValueOf16);
		}
		return result;
	}

	/**
	 * Return the index of the Character in the {@link #baseMap Base Map}.
	 * @param c the Character to lookup.
	 * @return return 
	 */
	private static int baseIndex(Character c) {
		for (int i = 0, len = baseMap.length; i < len; i++) {
			if (c == baseMap[i]) {
				return i;
			}
		}
		return -1;
	}
	
	/** BigInteger value of 16 */
	private static BigInteger bigIntegerValueOf16=BigInteger.valueOf(16);
	/** BigInteger value of 62 */
	private static BigInteger bigIntegerValueOf62=BigInteger.valueOf(62);
}
