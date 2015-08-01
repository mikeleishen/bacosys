package com.xinyou.util;

public class Code62Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Code62.valueOf(Long.MAX_VALUE));//AzL8n0Y58m7
		System.out.println(Code62.toLong("1000"));//238328
		System.out.println(Code62.toLong("zzzz"));//14776335
		System.out.println(Code62.toLong("10000"));//14776336
		System.out.println(Code62.toLong("zzzzz"));//916132831
		System.out.println(Code62.toLong("100000"));//916132832
		System.out.println(Code62.toLong("zzzzzz"));//56800235583
		System.out.println(Code62.valueOfHex("ffffffff-ffff-ffff-ffff-ffffffffffff"));//7n42DGM5Tflk9n8mt7Fhc7
		System.out.println(Code62.valueOfHex("zzzzzzzz-zzzz-zzzz-zzzz-zzzzzzzzzzzz"));//IB9PpcACmZSjhVzq4badlb
	}

}