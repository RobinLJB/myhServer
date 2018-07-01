package com.spark.p2p.util;

import com.spark.p2p.config.AppSetting;
import com.spark.p2p.limuzhengxin.common.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.json.JSONArray;
import org.json.JSONObject;

import com.sparkframework.lang.Convert;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.spark.p2p.config.AppSetting.HOST_IP;

/**
 * Created by zhangjinwei on 2017/8/29.
 */
public class ExcelUtil {
    public static  Workbook createMemberWorkBook(List<Map<String, String>> list, String[] keys,
                                                String columnNames[]) throws Exception {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < keys.length; i++) {
            if (i == 0 || i == 5 || i == 2 || i == 6 || i == 7) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150));
            } else if (i == 8) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 200));
            } else {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150 * 0.5));
            }
        }

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        Font f3 = wb.createFont();
        Font f4 = wb.createFont();
        Font f5 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        f3.setFontHeightInPoints((short) 20);
        f3.setColor(IndexedColors.BLACK.getIndex());
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f4.setFontHeightInPoints((short) 10);
        f4.setColor(IndexedColors.RED.getIndex());
        f4.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f5.setFontHeightInPoints((short) 10);
        f5.setColor(IndexedColors.BLUE.getIndex());
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        cellRangeAddress = new CellRangeAddress(1, 1, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("秒易花 注册用户明细表");
        cell0.setCellStyle(cs3);

        row = sheet.createRow((short) 2);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        for (short i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow((short) i + 3);
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                if (keys[j].equals("borrowStatus")) {
                    if (list.get(i).get(keys[j]).equals("8")) {
                        cell.setCellValue("正常还款");
                    } else if (list.get(i).get(keys[j]).equals("9")) {
                        cell.setCellValue("逾期状态");
                    } else {
                        cell.setCellValue("未知");
                    }
                }
               //身份证正面
                else if (keys[j].equals("card_imgA")) {
                    String cardImgAUrl = list.get(i).get(keys[j]);
                    if (StringUtils.isNotBlank(cardImgAUrl)) {
                       cell.setCellValue(AppSetting.HOST_IP+cardImgAUrl);
                    }
                    //System.out.println(cardImgAUrl);
                }
                //身份证反面
                else if (keys[j].equals("card_imgB")) {
                    String cardImgBUrl = list.get(i).get(keys[j]);
                    if (StringUtils.isNotBlank(cardImgBUrl)) {
                        cell.setCellValue(AppSetting.HOST_IP+cardImgBUrl);
                    }//System.out.println(cardImgAUrl);
                }
                //手持身份证
                else if (keys[j].equals("handle_img")) {
                    String handleImgUrl = list.get(i).get(keys[j]);
                    if (StringUtils.isNotBlank(handleImgUrl)) {
                        cell.setCellValue(AppSetting.HOST_IP+handleImgUrl);
                    }
                }
                else {
                    cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                }
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

    //根据图片下载
    public static void getImg(String imgUrl,HSSFPatriarch patriarch,HSSFWorkbook wb,int rowIndex) throws Exception {
        URL url = new URL(imgUrl);
        //打开链接
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求方式为"GET"
        conn.setRequestMethod("GET");
        //超时响应时间为5秒
        conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据
        InputStream inStream = conn.getInputStream();
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
        byte[] data = readInputStream(inStream);
        //anchor主要用于设置图片的属性
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 255, 255, (short) 1, 1, (short) 5, 5);
        //Sets the anchor type （图片在单元格的位置）
        //0 = Move and size with Cells, 2 = Move but don't size with cells, 3 = Don't move or size with cells.
        anchor.setAnchorType(3);
        patriarch.createPicture(anchor, wb.addPicture(data, HSSFWorkbook.PICTURE_TYPE_PNG));
    }




    private static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来
        while( (len=inStream.read(buffer)) != -1 ){
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
            outStream.write(buffer, 0, len);
        }
        //关闭输入流
        inStream.close();
        //把outStream里的数据写入内存
        return outStream.toByteArray();
    }

    public static Workbook createAddressWorkBook(List<Map<String, String>> list, String[] keys,
                                                 String columnNames[], String memberName) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < keys.length; i++) {
            if (i == 0 || i == 5 || i == 2 || i == 6 || i == 7) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150));
            } else if (i == 8) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 200));
            } else {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150 * 0.5));
            }
        }

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        Font f3 = wb.createFont();
        Font f4 = wb.createFont();
        Font f5 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        f3.setFontHeightInPoints((short) 20);
        f3.setColor(IndexedColors.BLACK.getIndex());
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f4.setFontHeightInPoints((short) 10);
        f4.setColor(IndexedColors.RED.getIndex());
        f4.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f5.setFontHeightInPoints((short) 10);
        f5.setColor(IndexedColors.BLUE.getIndex());
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        cellRangeAddress = new CellRangeAddress(1, 1, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue(memberName + " 通讯录明细表");
        cell0.setCellStyle(cs3);

        row = sheet.createRow((short) 2);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        for (short i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow((short) i + 3);
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                if (keys[j].equals("borrowStatus")) {
                    if (list.get(i).get(keys[j]).equals("8")) {
                        cell.setCellValue("正常还款");
                    } else if (list.get(i).get(keys[j]).equals("9")) {
                        cell.setCellValue("逾期状态");
                    } else {
                        cell.setCellValue("未知");
                    }
                } else {
                    cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                }
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

    public static Workbook createRepaymentWorkBook(List<Map<String, String>> list, String[] keys,
                                                   String columnNames[]) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < keys.length; i++) {
            if (i == 0 || i == 5 || i == 2 || i == 6 || i == 7) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150));
            } else if (i == 8) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 200));
            } else {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150 * 0.5));
            }
        }

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        Font f3 = wb.createFont();
        Font f4 = wb.createFont();
        Font f5 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        f3.setFontHeightInPoints((short) 20);
        f3.setColor(IndexedColors.BLACK.getIndex());
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f4.setFontHeightInPoints((short) 10);
        f4.setColor(IndexedColors.RED.getIndex());
        f4.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f5.setFontHeightInPoints((short) 10);
        f5.setColor(IndexedColors.BLUE.getIndex());
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        cellRangeAddress = new CellRangeAddress(1, 1, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("秒易花 借款明细表");
        cell0.setCellStyle(cs3);

        row = sheet.createRow((short) 2);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        for (short i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow((short) i + 3);
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                if (keys[j].equals("borrowStatus")) {
                    if (list.get(i).get(keys[j]).equals("8")) {
                        cell.setCellValue("正常还款");
                    } else if (list.get(i).get(keys[j]).equals("9")) {
                        cell.setCellValue("逾期状态");
                    } else {
                        cell.setCellValue("未知");
                    }
                } else {
                    cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                }
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

    public static Workbook createFinanceInstallmentWorkBook(List<Map<String, String>> list, String[] keys,
                                                            String columnNames[]) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < keys.length; i++) {
            if (i == 0 || i == 5 || i == 2 || i == 6 || i == 7) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150));
            } else if (i == 8) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 200));
            } else {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150 * 0.5));
            }
        }

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        Font f3 = wb.createFont();
        Font f4 = wb.createFont();
        Font f5 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        f3.setFontHeightInPoints((short) 20);
        f3.setColor(IndexedColors.BLACK.getIndex());
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f4.setFontHeightInPoints((short) 10);
        f4.setColor(IndexedColors.RED.getIndex());
        f4.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f5.setFontHeightInPoints((short) 10);
        f5.setColor(IndexedColors.BLUE.getIndex());
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 27);
        sheet.addMergedRegion(cellRangeAddress);

        cellRangeAddress = new CellRangeAddress(1, 1, 0, 27);
        sheet.addMergedRegion(cellRangeAddress);

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("速分期 分期汇总");
        cell0.setCellStyle(cs3);

        row = sheet.createRow((short) 2);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        for (short i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow((short) i + 3);
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                if (keys[j].equals("work_role")) {
                    if (list.get(i).get(keys[j]).equals("1")) {
                        cell.setCellValue("学生");
                    } else if (list.get(i).get(keys[j]).equals("2")) {
                        cell.setCellValue("工薪");
                    } else {
                        cell.setCellValue("自雇");
                    }
                } else if (keys[j].equals("order_operation")) {
                    if (list.get(i).get(keys[j]).equals("8")) {
                        cell.setCellValue("已完成");
                    } else if (list.get(i).get(keys[j]).equals("7")) {
                        cell.setCellValue("还款中");
                    } else {
                        cell.setCellValue("未发货");
                    }
                } else if (keys[j].equals("overdue_status")) {
                    if (list.get(i).get(keys[j]).equals("0")) {
                        cell.setCellValue("未逾期");
                    } else if (list.get(i).get(keys[j]) == null) {
                        cell.setCellValue("");
                    } else {
                        cell.setCellValue("已逾期");
                    }
                } else if (keys[j].equals("is_settlement")) {
                    if (list.get(i).get(keys[j]).equals("0")) {
                        cell.setCellValue("未上传");
                    } else if (list.get(i).get(keys[j]) == null) {
                        cell.setCellValue("");
                    } else if (list.get(i).get(keys[j]).equals("3")) {
                        cell.setCellValue("待审核");
                    } else if (list.get(i).get(keys[j]).equals("2")) {
                        cell.setCellValue("不通过");
                    } else if (list.get(i).get(keys[j]).equals("1")) {
                        cell.setCellValue("待结算");
                    } else if (list.get(i).get(keys[j]).equals("5")) {
                        cell.setCellValue("结算不过");
                    } else if (list.get(i).get(keys[j]).equals("4") && list.get(i).get("is_withdrawal").equals("0")) {
                        cell.setCellValue("待申请");
                    } else if (list.get(i).get(keys[j]).equals("4") && list.get(i).get("is_withdrawal").equals("1")) {
                        cell.setCellValue("待打款");
                    } else if (list.get(i).get(keys[j]).equals("4") && list.get(i).get("is_withdrawal").equals("2")) {
                        cell.setCellValue("提现通过");
                    } else if (list.get(i).get(keys[j]).equals("4") && list.get(i).get("is_withdrawal").equals("3")) {
                        cell.setCellValue("提现不通过");
                    }
                } else if (keys[j].equals("order_amount")) {
                    double amount = Convert.strToDouble(list.get(i).get(keys[j]), 0);
                    DecimalFormat df = new DecimalFormat("#.00");
                    cell.setCellValue(df.format(amount) + "");
                } else {
                    cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                }
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

    public static Workbook createFinanceAllOrderWorkBook(List<Map<String, String>> list, String[] keys,
                                                         String columnNames[]) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < keys.length; i++) {
            if (i == 0 || i == 5 || i == 2 || i == 6 || i == 7) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150));
            } else if (i == 8) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 200));
            } else {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150 * 0.5));
            }
        }

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        Font f3 = wb.createFont();
        Font f4 = wb.createFont();
        Font f5 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        f3.setFontHeightInPoints((short) 20);
        f3.setColor(IndexedColors.BLACK.getIndex());
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f4.setFontHeightInPoints((short) 10);
        f4.setColor(IndexedColors.RED.getIndex());
        f4.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f5.setFontHeightInPoints((short) 10);
        f5.setColor(IndexedColors.BLUE.getIndex());
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 17);
        sheet.addMergedRegion(cellRangeAddress);

        cellRangeAddress = new CellRangeAddress(1, 1, 0, 17);
        sheet.addMergedRegion(cellRangeAddress);

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("速分期 财务汇总");
        cell0.setCellStyle(cs3);

        row = sheet.createRow((short) 2);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        for (short i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow((short) i + 3);
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                if (keys[j].equals("order_operation")) {
                    cell.setCellValue(list.get(i).get(keys[j]).equals("7") ? "还款中"
                            : "已完成");
                } else if (keys[j].equals("isoverdue")) {
                    if (list.get(i).get(keys[j]).equals("0")) {
                        cell.setCellValue("未逾期");
                    } else {
                        cell.setCellValue("已逾期");
                    }
                } else {
                    cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                }
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

    public static Workbook createOrderAddedWorkBook(List<Map<String, String>> list, String[] keys,
                                                    String columnNames[]) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < keys.length; i++) {
            if (i == 0 || i == 1 || i == 3 || i == 4 || i == 5 || i == 8) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150));
            } else if (i == 9 || i == 10) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 200));
            } else {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150 * 0.5));
            }
        }

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        Font f3 = wb.createFont();
        Font f4 = wb.createFont();
        Font f5 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        f3.setFontHeightInPoints((short) 20);
        f3.setColor(IndexedColors.BLACK.getIndex());
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f4.setFontHeightInPoints((short) 10);
        f4.setColor(IndexedColors.RED.getIndex());
        f4.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f5.setFontHeightInPoints((short) 10);
        f5.setColor(IndexedColors.BLUE.getIndex());
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 10);
        sheet.addMergedRegion(cellRangeAddress);

        cellRangeAddress = new CellRangeAddress(1, 1, 0, 10);
        sheet.addMergedRegion(cellRangeAddress);

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("速分期 增值费用");
        cell0.setCellStyle(cs3);

        row = sheet.createRow((short) 2);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        for (short i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow((short) i + 3);
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                if (keys[j].equals("payment_method")) {
                    cell.setCellValue(list.get(i).get(keys[j]).equals("1") ? "微信"
                            : "支付宝");
                } else {
                    cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                }
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

    public static Workbook createShopSettlementWorkBook(List<Map<String, String>> list, String[] keys,
                                                        String columnNames[]) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < keys.length; i++) {
            sheet.setColumnWidth((short) i, (short) (35.7 * 150 * 1));
        }

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        Font f3 = wb.createFont();
        Font f4 = wb.createFont();
        Font f5 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        f3.setFontHeightInPoints((short) 20);
        f3.setColor(IndexedColors.BLACK.getIndex());
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f4.setFontHeightInPoints((short) 10);
        f4.setColor(IndexedColors.RED.getIndex());
        f4.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f5.setFontHeightInPoints((short) 10);
        f5.setColor(IndexedColors.BLUE.getIndex());
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 9);
        sheet.addMergedRegion(cellRangeAddress);

        cellRangeAddress = new CellRangeAddress(1, 1, 0, 9);
        sheet.addMergedRegion(cellRangeAddress);

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("速分期 门店提现");
        cell0.setCellStyle(cs3);

        row = sheet.createRow((short) 2);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        for (short i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow((short) i + 3);
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                if (keys[j].equals("is_settlement")) {
                    if (list.get(i).get(keys[j]).equals("1")) {
                        cell.setCellValue("待结算");
                    } else if (list.get(i).get(keys[j]).equals("4")) {
                        cell.setCellValue("通过");
                    } else if (list.get(i).get(keys[j]).equals("5")) {
                        cell.setCellValue("不通过");
                    }
                } else {
                    cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                }
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }


    public static Workbook createUrgeWorkBook(List<Map<String, String>> list, String[] keys,
                                              String columnNames[]) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < keys.length; i++) {
            if (i == 1 || i == 3 || i == 4 || i == 10 || i == 15 || i == 17 || i == 18) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150));
            } else {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150 * 0.5));
            }
        }

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        Font f3 = wb.createFont();
        Font f4 = wb.createFont();
        Font f5 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        f3.setFontHeightInPoints((short) 20);
        f3.setColor(IndexedColors.BLACK.getIndex());
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f4.setFontHeightInPoints((short) 10);
        f4.setColor(IndexedColors.RED.getIndex());
        f4.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f5.setFontHeightInPoints((short) 10);
        f5.setColor(IndexedColors.BLUE.getIndex());
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 18);
        sheet.addMergedRegion(cellRangeAddress);

        cellRangeAddress = new CellRangeAddress(1, 1, 0, 18);
        sheet.addMergedRegion(cellRangeAddress);

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("速分期 逾期催收统计单");
        cell0.setCellStyle(cs3);

        row = sheet.createRow((short) 2);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        for (short i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow((short) i + 3);
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

    public static Workbook createShopWorkBook(List<Map<String, String>> list, String[] keys,
                                              String columnNames[]) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < keys.length; i++) {
            if (i == 0 || i == 5 || i == 2 || i == 6 || i == 7) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150));
            } else if (i == 8) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 200));
            } else {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150 * 0.5));
            }
        }

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        Font f3 = wb.createFont();
        Font f4 = wb.createFont();
        Font f5 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        f3.setFontHeightInPoints((short) 20);
        f3.setColor(IndexedColors.BLACK.getIndex());
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f4.setFontHeightInPoints((short) 10);
        f4.setColor(IndexedColors.RED.getIndex());
        f4.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f5.setFontHeightInPoints((short) 10);
        f5.setColor(IndexedColors.BLUE.getIndex());
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        cellRangeAddress = new CellRangeAddress(1, 1, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("速分期 门店明细统计单");
        cell0.setCellStyle(cs3);

        row = sheet.createRow((short) 2);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        for (short i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow((short) i + 3);
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                if (keys[j].equals("status")) {
                    if (list.get(i).get(keys[j]) == null) {
                        cell.setCellValue("未还完");
                    } else if (list.get(i).get(keys[j]).equals("1")) {
                        cell.setCellValue("待审核");
                    } else if (list.get(i).get(keys[j]).equals("2")) {
                        cell.setCellValue("审核通过");
                    } else if (list.get(i).get(keys[j]).equals("3")) {
                        cell.setCellValue("审核拒绝");
                    } else if (list.get(i).get(keys[j]).equals("4")) {
                        cell.setCellValue("返回修改");
                    } else if (list.get(i).get(keys[j]).equals("5")) {
                        cell.setCellValue("关闭合作");
                    } else if (list.get(i).get(keys[j]).equals("6")) {
                        cell.setCellValue("恢复合作");
                    } else {
                        cell.setCellValue("未知");
                    }
                } else {
                    cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                }
                cell.setCellStyle(cs2);
            }
        }

        return wb;
    }


    public static Workbook createGoodsWorkBook(List<Map<String, String>> list, String[] keys,
                                               String columnNames[]) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < keys.length; i++) {
            if (i == 0 || i == 5 || i == 2 || i == 6 || i == 7) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150));
            } else if (i == 8) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 200));
            } else {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150 * 0.5));
            }
        }

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        Font f3 = wb.createFont();
        Font f4 = wb.createFont();
        Font f5 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        f3.setFontHeightInPoints((short) 20);
        f3.setColor(IndexedColors.BLACK.getIndex());
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f4.setFontHeightInPoints((short) 10);
        f4.setColor(IndexedColors.RED.getIndex());
        f4.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f5.setFontHeightInPoints((short) 10);
        f5.setColor(IndexedColors.BLUE.getIndex());
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        cellRangeAddress = new CellRangeAddress(1, 1, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("速分期 商品明细统计单");
        cell0.setCellStyle(cs3);

        row = sheet.createRow((short) 2);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        for (short i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow((short) i + 3);
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

    public static Workbook createCustomerWorkBook(List<Map<String, String>> list, String[] keys,
                                                  String columnNames[]) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < keys.length; i++) {
            if (i == 0 || i == 5 || i == 2 || i == 6 || i == 7) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150));
            } else if (i == 8) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 200));
            } else {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150 * 0.5));
            }
        }

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        Font f3 = wb.createFont();
        Font f4 = wb.createFont();
        Font f5 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        f3.setFontHeightInPoints((short) 20);
        f3.setColor(IndexedColors.BLACK.getIndex());
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f4.setFontHeightInPoints((short) 10);
        f4.setColor(IndexedColors.RED.getIndex());
        f4.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f5.setFontHeightInPoints((short) 10);
        f5.setColor(IndexedColors.BLUE.getIndex());
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        cellRangeAddress = new CellRangeAddress(1, 1, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("速分期 客户明细统计单");
        cell0.setCellStyle(cs3);

        row = sheet.createRow((short) 2);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        for (short i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow((short) i + 3);
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                if (keys[j].equals("enable")) {
                    if (list.get(i).get(keys[j]) == null) {
                        cell.setCellValue("未还完");
                    } else if (list.get(i).get(keys[j]).equals("1")) {
                        cell.setCellValue("正常");
                    } else if (list.get(i).get(keys[j]).equals("2")) {
                        cell.setCellValue("黑名单");
                    } else {
                        cell.setCellValue("未知");
                    }
                } else {
                    cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                }
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }


    public static Workbook createClerkWorkBook(List<Map<String, String>> list, String[] keys, String columnNames[]) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < keys.length; i++) {
            if (i == 0 || i == 5 || i == 2 || i == 6 || i == 7) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150));
            } else if (i == 8) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 200));
            } else {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150 * 0.5));
            }
        }

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        Font f3 = wb.createFont();
        Font f4 = wb.createFont();
        Font f5 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        f3.setFontHeightInPoints((short) 20);
        f3.setColor(IndexedColors.BLACK.getIndex());
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f4.setFontHeightInPoints((short) 10);
        f4.setColor(IndexedColors.RED.getIndex());
        f4.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f5.setFontHeightInPoints((short) 10);
        f5.setColor(IndexedColors.BLUE.getIndex());
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        cellRangeAddress = new CellRangeAddress(1, 1, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("速分期 办单员明细统计单");
        cell0.setCellStyle(cs3);

        row = sheet.createRow((short) 2);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        for (short i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow((short) i + 3);
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                if (keys[j].equals("hierarchy")) {
                    if (list.get(i).get(keys[j]).equals("HIERARCHY1")) {
                        cell.setCellValue("省总");
                    } else if (list.get(i).get(keys[j]).equals("HIERARCHY2")) {
                        cell.setCellValue("城市经理");
                    } else if (list.get(i).get(keys[j]).equals("HIERARCHY3")) {
                        cell.setCellValue("办单员");
                    } else {
                        cell.setCellValue("未知");
                    }
                } else if (keys[j].equals("gender")) {
                    if (list.get(i).get(keys[j]).equals("GENDER1")) {
                        cell.setCellValue("男");
                    } else if (list.get(i).get(keys[j]).equals("GENDER2")) {
                        cell.setCellValue("女");
                    } else {
                        cell.setCellValue("其他");
                    }
                } else if (keys[j].equals("clerk_state")) {
                    if (list.get(i).get(keys[j]).equals("1")) {
                        cell.setCellValue("正常");
                    } else if (list.get(i).get(keys[j]).equals("2")) {
                        cell.setCellValue("黑名单");
                    } else {
                        cell.setCellValue("其他");
                    }
                } else if (keys[j].equals("level")) {
                    if (list.get(i).get(keys[j]).equals("LEVEL1")) {
                        cell.setCellValue("一级");
                    } else if (list.get(i).get(keys[j]).equals("LEVEL2")) {
                        cell.setCellValue("二级");
                    } else {
                        cell.setCellValue("其他");
                    }
                } else {
                    cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                }
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }

    public static Workbook createBrandWorkBook(List<Map<String, String>> list, String[] keys,
                                               String columnNames[]) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < keys.length; i++) {
            if (i == 0 || i == 5 || i == 2 || i == 6 || i == 7) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150));
            } else if (i == 8) {
                sheet.setColumnWidth((short) i, (short) (35.7 * 200));
            } else {
                sheet.setColumnWidth((short) i, (short) (35.7 * 150 * 0.5));
            }
        }

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        Font f3 = wb.createFont();
        Font f4 = wb.createFont();
        Font f5 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        f3.setFontHeightInPoints((short) 20);
        f3.setColor(IndexedColors.BLACK.getIndex());
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f4.setFontHeightInPoints((short) 10);
        f4.setColor(IndexedColors.RED.getIndex());
        f4.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f5.setFontHeightInPoints((short) 10);
        f5.setColor(IndexedColors.BLUE.getIndex());
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        cellRangeAddress = new CellRangeAddress(1, 1, 0, 13);
        sheet.addMergedRegion(cellRangeAddress);

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("速分期 品牌明细统计单");
        cell0.setCellStyle(cs3);

        row = sheet.createRow((short) 2);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        for (short i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow((short) i + 3);
            for (short j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                cell.setCellStyle(cs2);
            }
        }
        return wb;
    }


    public static Workbook createAuditSummaryWorkBook(List<Map<String, String>> list, String columnNames[]) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < columnNames.length; i++) {

            if (i == 1 || i == 4 || i == 7 || i == 9 || i == 12 || i == 14 || i == 15 || i == 16) {

                sheet.setColumnWidth(i, (int) (35.7 * 80));
            } else if (i == 17) {

                sheet.setColumnWidth(i, (int) (35.7 * 500));
            } else if (i == 0) {

                sheet.setColumnWidth(i, (int) (35.7 * 300));
            } else {
                sheet.setColumnWidth(i, (int) (35.7 * 150));
            }
        }

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        Font f3 = wb.createFont();
        Font f4 = wb.createFont();
        Font f5 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        f3.setFontHeightInPoints((short) 20);
        f3.setColor(IndexedColors.BLACK.getIndex());
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f4.setFontHeightInPoints((short) 10);
        f4.setColor(IndexedColors.RED.getIndex());
        f4.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f5.setFontHeightInPoints((short) 10);
        f5.setColor(IndexedColors.BLUE.getIndex());
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 19);
        sheet.addMergedRegion(cellRangeAddress);

        cellRangeAddress = new CellRangeAddress(1, 1, 0, 19);
        sheet.addMergedRegion(cellRangeAddress);

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("速分期 审核汇总");
        cell0.setCellStyle(cs3);

        row = sheet.createRow((short) 2);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> map = list.get(i);

                Row rowOther = sheet.createRow(i + 3);
                Cell cell00 = rowOther.createCell(0);
                cell00.setCellValue(map.get("shop_name"));
                cell00.setCellStyle(cs2);
                Cell cell01 = rowOther.createCell(1);
                cell01.setCellValue(map.get("real_name"));
                cell01.setCellStyle(cs2);
                Cell cell02 = rowOther.createCell(2);
                cell02.setCellValue(map.get("mobilePhone"));
                cell02.setCellStyle(cs2);
                Cell cell03 = rowOther.createCell(3);
                cell03.setCellValue(map.get("ident_no"));
                cell03.setCellStyle(cs2);
                Cell cell04 = rowOther.createCell(4);
                if (map.get("work_role").equals("1")) {
                    cell04.setCellValue("学生");
                }
                if (map.get("work_role").equals("2")) {
                    cell04.setCellValue("工薪");
                }
                if (map.get("work_role").equals("3")) {
                    cell04.setCellValue("自雇");
                }
                cell04.setCellStyle(cs2);
                Cell cell05 = rowOther.createCell(5);
                cell05.setCellValue(map.get("goods_name"));
                cell05.setCellStyle(cs2);
                Cell cell06 = rowOther.createCell(6);
                String specs = "";
                String spe = "";
                int uid = 0;
                if (map != null) {
                    specs = map.get("goods_speci");

                }
                JSONArray json = null;
                if (specs != null && !specs.equals("")) {
                    json = new JSONArray(specs);
                }

                Map<String, String> mapSpeci = new HashMap<String, String>();
                if (json != null && json.length() > 0) {
                    for (int a = 0; a < json.length(); a++) {
                        JSONObject jsonOb = json.getJSONObject(a);
                        spe += (jsonOb.getString("value"));

                    }
                }
                cell06.setCellValue(spe);
                cell06.setCellStyle(cs2);
                Cell cell07 = rowOther.createCell(7);
                cell07.setCellValue(map.get("pre_payment"));
                cell07.setCellStyle(cs2);
                Cell cell08 = rowOther.createCell(8);
                cell08.setCellValue(map.get("before_fee"));
                cell08.setCellStyle(cs2);
                Cell cell09 = rowOther.createCell(9);
                cell09.setCellValue(map.get("monthly_interest") + "%");
                cell09.setCellStyle(cs2);
                Cell cell10 = rowOther.createCell(10);
                cell10.setCellValue(map.get("apply_nper"));
                cell10.setCellStyle(cs2);
                Cell cell11 = rowOther.createCell(11);
                cell11.setCellValue(map.get("apply_balance"));
                cell11.setCellStyle(cs2);
                Cell cell12 = rowOther.createCell(12);
                cell12.setCellValue(map.get("audit_finance"));
                cell12.setCellStyle(cs2);
                Cell cell13 = rowOther.createCell(13);
                cell13.setCellValue(map.get("apply_time"));
                cell13.setCellStyle(cs2);
                Cell cell14 = rowOther.createCell(14);
                if (Convert.strToInt(map.get("order_operation"), 0) == 0) {

                    cell14.setCellValue("未领单");
                }
                if (Convert.strToInt(map.get("order_operation"), 0) == 11) {

                    cell14.setCellValue("拒绝");
                }
                if (Convert.strToInt(map.get("order_operation"), 0) == 12) {

                    cell14.setCellValue("驳回");
                }
                if (Convert.strToInt(map.get("order_operation"), 0) == 13) {

                    cell14.setCellValue("取消");
                }
                if (Convert.strToInt(map.get("order_operation"), 0) == 2) {

                    cell14.setCellValue("通过");
                }
                if (Convert.strToInt(map.get("order_operation"), 0) == 7) {

                    cell14.setCellValue("通过");
                }
                if (Convert.strToInt(map.get("order_operation"), 0) == 8) {

                    cell14.setCellValue("通过");
                }
                if (Convert.strToInt(map.get("order_operation"), 0) == 3) {

                    cell14.setCellValue("退回");
                }
                if (Convert.strToInt(map.get("order_operation"), 0) == 1) {

                    cell14.setCellValue("审核中");
                }
                cell14.setCellStyle(cs2);
                Cell cell15 = rowOther.createCell(15);
                cell15.setCellValue(map.get("clerk_name"));
                cell15.setCellStyle(cs2);
                Cell cell16 = rowOther.createCell(16);
                cell16.setCellValue(map.get("get_order_person"));
                cell16.setCellStyle(cs2);
                Cell cell17 = rowOther.createCell(17);
                if (Convert.strToInt(map.get("order_operation"), 0) == 11 || Convert.strToInt(map.get("order_operation"), 0) == 12 || Convert.strToInt(map.get("order_operation"), 0) == 13) {
                    if (map.get("clerk_other_opinion") == null) {

                        cell17.setCellValue(map.get("audit_time") + map.get("unpass_reason"));
                    } else {

                        cell17.setCellValue(map.get("audit_time") + map.get("unpass_reason") + map.get("clerk_other_opinion"));
                    }
                }
                if (Convert.strToInt(map.get("order_operation"), 0) == 2 || Convert.strToInt(map.get("order_operation"), 0) == 7 || Convert.strToInt(map.get("order_operation"), 0) == 8) {
                    if (map.get("clerk_other_opinion") == null) {

                        cell17.setCellValue(map.get("audit_time") + map.get("audit_opinion"));
                    } else {
                        cell17.setCellValue(map.get("audit_time") + map.get("audit_opinion") + map.get("clerk_other_opinion"));
                    }
                }
                if (Convert.strToInt(map.get("order_operation"), 0) == 3) {
                    if (map.get("clerk_other_opinion") == null) {

                        cell17.setCellValue(map.get("audit_time") + map.get("back_reason"));
                    } else {

                        cell17.setCellValue(map.get("audit_time") + map.get("back_reason") + map.get("clerk_other_opinion"));
                    }
                }
                if (Convert.strToInt(map.get("order_operation"), 0) == 0 || Convert.strToInt(map.get("order_operation"), 0) == 1) {
                    if (map.get("clerk_other_opinion") == null) {

                        cell17.setCellValue("");
                    } else {

                        cell17.setCellValue(map.get("clerk_other_opinion"));
                    }
                }
                cell17.setCellStyle(cs2);


            }
        }


        return wb;
    }

    public static Workbook createRejectBook(List<Map<String, String>> list, String columnNames[]) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("sheet1");
        for (int i = 0; i < columnNames.length; i++) {

            if (i == 1) {

                sheet.setColumnWidth(i, (int) (35.7 * 100));
            } else if (i == 4) {

                sheet.setColumnWidth(i, (int) (35.7 * 300));
            } else if (i == 0) {

                sheet.setColumnWidth(i, (int) (35.7 * 80));
            } else {
                sheet.setColumnWidth(i, (int) (35.7 * 150));
            }
        }

        // 创建两种单元格格式
        CellStyle cs = wb.createCellStyle();
        CellStyle cs2 = wb.createCellStyle();
        CellStyle cs3 = wb.createCellStyle();

        // 创建两种字体
        Font f = wb.createFont();
        Font f2 = wb.createFont();
        Font f3 = wb.createFont();
        Font f4 = wb.createFont();
        Font f5 = wb.createFont();

        // 创建第一种字体样式（用于列名）
        f.setFontHeightInPoints((short) 10);
        f.setColor(IndexedColors.BLACK.getIndex());
        f.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 创建第二种字体样式（用于值）
        f2.setFontHeightInPoints((short) 10);
        f2.setColor(IndexedColors.BLACK.getIndex());

        f3.setFontHeightInPoints((short) 20);
        f3.setColor(IndexedColors.BLACK.getIndex());
        f3.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f4.setFontHeightInPoints((short) 10);
        f4.setColor(IndexedColors.RED.getIndex());
        f4.setBoldweight(Font.BOLDWEIGHT_BOLD);

        f5.setFontHeightInPoints((short) 10);
        f5.setColor(IndexedColors.BLUE.getIndex());
        f5.setBoldweight(Font.BOLDWEIGHT_BOLD);

        // 设置第一种单元格的样式（用于列名）
        cs.setFont(f);
        cs.setBorderLeft(CellStyle.BORDER_THIN);
        cs.setBorderRight(CellStyle.BORDER_THIN);
        cs.setBorderTop(CellStyle.BORDER_THIN);
        cs.setBorderBottom(CellStyle.BORDER_THIN);
        cs.setAlignment(CellStyle.ALIGN_CENTER);

        cs2.setFont(f2);
        cs2.setBorderLeft(CellStyle.BORDER_THIN);
        cs2.setBorderRight(CellStyle.BORDER_THIN);
        cs2.setBorderTop(CellStyle.BORDER_THIN);
        cs2.setBorderBottom(CellStyle.BORDER_THIN);
        cs2.setAlignment(CellStyle.ALIGN_CENTER);

        cs3.setFont(f3);
        cs3.setAlignment(CellStyle.ALIGN_CENTER);

        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 6);
        sheet.addMergedRegion(cellRangeAddress);

        cellRangeAddress = new CellRangeAddress(1, 1, 0, 6);
        sheet.addMergedRegion(cellRangeAddress);

        // 创建第一行
        Row row = sheet.createRow((short) 0);

        Cell cell0 = row.createCell(0);
        cell0.setCellValue("速分期 预审拒绝");
        cell0.setCellStyle(cs3);

        row = sheet.createRow((short) 2);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(cs);
        }
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> map = list.get(i);

                Row rowOther = sheet.createRow(i + 3);
                Cell cell00 = rowOther.createCell(0);
                cell00.setCellValue(map.get("real_name"));
                cell00.setCellStyle(cs2);
                Cell cell01 = rowOther.createCell(1);
                cell01.setCellValue(map.get("phone"));
                cell01.setCellStyle(cs2);
                Cell cell02 = rowOther.createCell(2);
                cell02.setCellValue(map.get("ident_no"));
                cell02.setCellStyle(cs2);
                Cell cell03 = rowOther.createCell(3);
                if (map.get("work_role").equals("1")) {
                    cell03.setCellValue("学生");
                }
                if (map.get("work_role").equals("2")) {
                    cell03.setCellValue("工薪");
                }
                if (map.get("work_role").equals("3")) {
                    cell03.setCellValue("自雇");
                }
                cell03.setCellStyle(cs2);
                Cell cell04 = rowOther.createCell(4);
                cell04.setCellValue(map.get("reason"));
                cell04.setCellStyle(cs2);
                Cell cell05 = rowOther.createCell(5);
                cell05.setCellValue(map.get("create_time"));
                cell05.setCellStyle(cs2);

            }
        }


        return wb;
    }


}
