package com.spark.p2p.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {
	public static List<String[]> parse(String file) throws IOException{
		List<String[]> rows = new ArrayList<String[]>();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line;
		int index = 0;
		while ((line = br.readLine()) != null) {
			if (index > 0) {
				String[] row = line.split(",");
				rows.add(row);
			}
			index ++;
		}
		br.close();
		return rows;
	}
	
	public static void main(String[] args) throws IOException{
		String file = "/Users/yanqizheng/Downloads/20161229_zhye-yh-cqg_252825.csv";
		try {
			System.out.println(parse(file));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
