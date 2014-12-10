package me.ouchxp.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Fast split a String using a char
 * 
 * @author Nan Wu
 *
 */
public class Spliter implements Iterable<String> {
	private char sep;
	private String str;
	private int length;
	
	private Spliter(String str, char sep) {
		this.sep = sep;
		this.str = str;
		this.length = str.length();
	}
	
	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {
			int pos = 0;
			
			@Override
			public boolean hasNext() {
				return pos < length;
			}
			
			@Override
			public String next() {
				for (int i = pos; i < length; i++) {
					if (str.charAt(i) == sep) {
						String result = str.substring(pos, i);
						pos = i + 1;
						return result;
					}
				}
				String result = str.substring(pos, length);
				pos = length;
				return result;
			}
			
			@Override
			public void remove() {
				throw new RuntimeException(new IllegalAccessException("Spliter does not support remove method."));
			}
		};
	}
	
	/**
	 * Split a string
	 * 
	 * @param str
	 * @param sep
	 * @return
	 */
	public static Iterable<String> split(String str, char sep) {
		return new Spliter(str, sep);
	}
	
	/**
	 * Split a string all in once as a List
	 * @param str
	 * @param sep
	 * @return
	 */
	public static List<String> splitAsList(String str, char sep) {
		ArrayList<String> list = new ArrayList<String>();
		for (String s : new Spliter(str, sep)) {
			list.add(s);
		}
		return list;
	}
	
	/**
	 * Test
	 * 
	 * @param args
	 */
	@Deprecated
	public static void main(String[] args) {
		for (String s : new Spliter("1111|2222|3333", '|')) {
			System.out.println(s);
		}
	}
	
}