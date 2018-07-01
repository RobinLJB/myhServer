package com.spark.p2p.core.view;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.spark.p2p.util.DataTable;

public class ExcelView extends AbstractExcelView {
	private static Log log = LogFactory.getLog(ExcelView.class);

	public String getContentType() {
		return "application/octet-stream";
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DataTable dt = (DataTable) model.get("dataTable");
		log.info(dt);
		if(dt == null)return;
		List<Map<String, String>> data = (List<Map<String, String>>) dt.getData();
		log.info("buildExcelDocument:" + data);
		List<String> titles = dt.getTitles();
		HSSFSheet sheet = workbook.createSheet("sheet1");
		// 设置标题
		int row = 0;
		if (titles != null && titles.size() > 0) {
			for (int i = 0; i < titles.size(); i++) {
				HSSFCell cell = getCell(sheet, row, i);
				setText(cell, titles.get(i));
			}
			row ++;
		}
		//设置数据
		int col = 0;
		for(Map<String,String> map:data){
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while(iterator.hasNext()){
				Entry<String, String> entry = iterator.next();
				HSSFCell cell = getCell(sheet, row, col);
				setText(cell, entry.getValue());
				col ++;
			}
			col = 0;
			row ++;
		}
	}
}
