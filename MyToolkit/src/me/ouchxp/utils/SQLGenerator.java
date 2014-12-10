package me.ouchxp.utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.io.FileUtils;

public class SQLGenerator {
	public static final String[] types_detail = { "N", "N", "D", "N", "N", "N", "N", "N", "N", "N", "S", "N", "N", "N", "N", "N", "D", "N", "D", "S", "N", "N",
			"S", "N", "N", "N", "S", "N", "N", "S", "S", "S", "S", "N", "S", "S", "S", "S", "S", "S", "S", "N", "S", "S", "S", "S", "S", "S", "S", "S", "S",
			"N", "N", "N", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S",
			"S", "D", "D", "N", "N", "S", "N", "N", "S", "S", "S", "N", "S", "N", "N", "S", "S", "N", "N", "N", "N", "N", "N", "S", "S", "N", "N", "S", "N",
			"S", "N", "S", "S", "N", "N", "N", "N", "N", "N", "S", "S", "S", "S", "N", "N", "N", "S", "N", "S", "S", "D", "N", "N", "N", "S", "S", "N", "S",
			"S", "S", "S", "D", "D", "N", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "N", "S", "S", "S", "D", "S", "N", "N" };
	
	public static final String[] types = { "N", "D", "N", "N", "N", "N", "N", "N", "D", "S", "N", "N", "N", "S", "N", "S", "N", "N", "N", "N", "N", "D", "S",
			"N", "N", "S", "N", "N", "N", "S", "N", "N", "S", "S", "S", "S", "N", "S", "N", "N", "S", "S", "S", "S", "N", "S", "S", "S", "S", "S", "S", "S",
			"S", "S", "N", "N", "N", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S",
			"S", "S", "S", "D", "D", "S", "S", "S", "N", "N", "S", "S", "S", "N", "S", "N", "N", "S", "S", "N", "N", "N", "N", "N", "N", "S", "S", "N", "N",
			"S", "N", "S", "N", "S", "S", "N", "N", "N", "N", "N", "N", "S", "S", "S", "S", "N", "N", "N", "S", "N", "S", "S", "D", "N", "N", "N", "S", "S",
			"N", "N", "N", "N", "N", "D", "D", "N", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "N", "S", "S", "S", "D", "S", "N", "N" };
	
	public static final String TABLE_NAME = "ST_MICROS_HEADER";
	
	public static void main(String[] args) throws ParseException, InterruptedException, IOException {
		File outputFile = new File("D:/header.sql");
		BufferedReader br = new BufferedReader(new FileReader("D:/header.csv"));
		String line;
		
		int count = 0;
		while ((line = br.readLine()) != null) {
			
			StringBuilder out = new StringBuilder();
			out.append("INSERT INTO " + TABLE_NAME + " VALUES(");
			String[] cols = line.split(",");
			for (int i = 0; i < cols.length; i++) {
				if ("".equals(cols[i]) || "NULL".endsWith(cols[i])) {
					out.append("NULL,");
				} else {
					
					switch (types[i]) {
					case "N":
						out.append(cols[i]).append(",");
						break;
					case "S":
						out.append("'").append(cols[i]).append("',");
						break;
					case "D":
						out.append("TO_DATE('").append(cols[i]).append("','YYYY/MM/DD HH24:MI:SS'),");
						break;
					}
				}
			}
			out.deleteCharAt(out.length() - 1);
			out.append(");\r\n");
			
			count++;
			if (count % 2000 == 0) {
				out.append("commit;\r\n");
			}
			
			FileUtils.writeStringToFile(outputFile, out.toString(), true);
		}
		br.close();
		FileUtils.writeStringToFile(outputFile, "commit;\r\n", true);
		
	}
}
