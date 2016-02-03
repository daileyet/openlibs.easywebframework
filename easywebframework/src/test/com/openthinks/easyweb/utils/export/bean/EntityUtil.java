package com.openthinks.easyweb.utils.export.bean;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class EntityUtil {
	public static boolean isBlank(Object object) {
		return object == null ? true : object.toString().isEmpty();
	}

	public static String toStringFromArray(List<String> toChanged) {
		StringBuffer sb = new StringBuffer();
		for (String temp : toChanged) {
			sb.append(temp).append(StaticFields.TEXT_SEPERATOR);
		}

		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static List<String> toArrayFromString(String toChanged) {
		List<String> result = new ArrayList<String>();
		String[] temps = toChanged.split(StaticFields.TEXT_SEPERATOR);
		for (String tmp : temps) {
			result.add(tmp);
		}

		return result;
	}

	public static String newUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String old = "\u4e00";

		byte[] utf8Decode = old.getBytes("utf-8");
		System.out.println("utf8:");
		for (byte b : utf8Decode) {
			System.out.print(Integer.toHexString(b & 0xFF));
			System.out.print(" ");
		}
		System.out.println();

		byte[] gbkDecode = old.getBytes("gbk");
		System.out.println("gbk:");
		for (byte b : gbkDecode) {
			System.out.print(Integer.toHexString(b & 0xFF));
			System.out.print(" ");
		}
		System.out.println();

		System.out.println(new String(utf8Decode, "utf-8"));
		System.out.println(new String(gbkDecode, "gbk"));
	}
}
