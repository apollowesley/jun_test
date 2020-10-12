package com.util.excel;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class Excel2003Util {
	
	public static String DEFAULT_END_TAG = "<终止>";
	public static String END_TAG = "#end";
	
	
	/**
	 * 读入 excel 内容，每一行是一个 map，返回装着 map 的 list，默认从第 2 行开始（不读标题）
	 * @param filePath
	 * @return
	 */
	public static List<Map<String, String>> readExcelToList(String filePath){
		return readExcelToList(filePath, 2);
	}
	
	/**
	 * 从第 startRow 行开始，读入 excel 内容，每一行是一个 map，返回装着 map 的 list
	 * @param filePath
	 * @param startRow
	 * @return
	 */
	public static List<Map<String, String>> readExcelToList(String filePath, int startRow){
		startRow = startRow <= 0 ? 0 : startRow - 1;
		File file = new File(filePath);
		List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
		Workbook book = null;
		try {
			book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(0);
			int rowsNum = sheet.getRows();
			//int column = sheet.getColumns();
			
			for(int i = startRow; i < rowsNum; i++){
				Map<String, String> map = new HashMap<String, String>();
				Cell[] cells = sheet.getRow(i);
				
				if(cells == null || cells.length <= 0)
				{
					break;
				}
				String firstCellInRow = cells[0].getContents().toString().trim();
				//行遇到结束符（空格），不再往下读
				if("".equals(firstCellInRow)){
					break;
				}
				
				for (int j = 0; j < cells.length; j++){
					map.put(COLUMNS[j], cells[j].getContents().toString().trim());
				}
				datas.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(book != null){
				book.close();
			}
		}
		return datas;
	}
	
	
	/**
	 * 读入 excel 内容，遇到结束符停止往后读
	 * @param filePath
	 * @param endTag
	 * @return
	 */
	public static List<Map<String, String>> readExcelToList(String filePath, String endTag){
		return readExcelToList(filePath, 2, endTag);
	}
	
	/**
	 * 读入 excel 内容，遇到结束符停止往后读
	 * @param filePath
	 * @param startRow
	 * @return
	 */
	public static List<Map<String, String>> readExcelToList(String filePath, int startRow, String endTag){
		startRow = startRow <= 0 ? 0 : startRow - 1;
		File file = new File(filePath);
		List<Map<String, String>> datas = new ArrayList<Map<String, String>>();
		Workbook book = null;
		try {
			book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(0);
			int rowsNum = sheet.getRows();
			//int column = sheet.getColumns();
			
			for(int i = startRow; i < rowsNum; i++){
				Map<String, String> map = new HashMap<String, String>();
				Cell[] cells = sheet.getRow(i);
				
				String firstCellInRow = cells[0].getContents().toString().trim();
				//行遇到结束符，不再往下读
				if(endTag.equals(firstCellInRow)){
					break;
				}
				
				for (int j = 0; j < cells.length; j++){
					String cellContent = cells[j].getContents().toString().trim();
					//列遇到结束符，不再往右读
					if(null != cellContent && endTag.equals(cellContent)){
						break;
					}
					map.put(COLUMNS[j], cellContent);
				}
				datas.add(map);
			}
		} catch (Exception e){
			e.printStackTrace();
		}finally{
			if(book != null){
				book.close();
			}
		}
		return datas;
	}
	
	public static List<String> readExcelRow(String excelPath, int rowIndex) 
	{
		rowIndex = (rowIndex - 1) < 0 ? 0 : (rowIndex - 1);
		File file = new File(excelPath);
		List<String> rowData = new ArrayList<String>();
		Workbook book = null;
		try {
			book = Workbook.getWorkbook(file);
			Sheet sheet = book.getSheet(0);
			int rowsNum = sheet.getRows();
			
			if(rowsNum < rowIndex)
			{
				return null;
			}
			
			//int column = sheet.getColumns();
			
			Cell[] cells = sheet.getRow(rowIndex);
			for (int j = 0; j < cells.length; j++) 
			{
				rowData.add(cells[j].getContents().toString().trim());
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}finally
		{
			if(book != null)
			{
				book.close();
			}
		}
		return rowData;
	}
	
	public static void downloadExcel(OutputStream os,Object[][] datas) throws Exception{
		WritableWorkbook wbook = Workbook.createWorkbook(os);   
		WritableSheet sheet=wbook.createSheet("sheet1",0);   
	    //表头行的字体样式
	    WritableFont wfTable = new WritableFont(WritableFont.TIMES,12, WritableFont.BOLD, false);
	    WritableCellFormat wcfFTable = new WritableCellFormat(wfTable);
	    wcfFTable.setAlignment(Alignment.CENTRE);
	    //内容信息的字体样式
	    WritableFont wf = new WritableFont(WritableFont.TIMES,10, WritableFont.BOLD, false);
	    WritableCellFormat wcfF = new WritableCellFormat(wf);
	    wcfF.setAlignment(Alignment.CENTRE);
	    //写入数据
	    if(datas != null){
	    	 for (int i = 0; i < datas.length; i++) {
	 	    	WritableCellFormat st = i== 0 ? wcfFTable : wcfF;
	 	    	for (int j = 0; j < datas[i].length; j++) {
	 				String str = datas[i][j] == null ? "" : datas[i][j].toString();
					Label label  = new Label(j, i, str, st);
	 				sheet.addCell(label);
	 			}
	 		}
	    }
	    //写出
	    wbook.write();  
	    wbook.close();
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < COLUMNS.length; i++) {
			for (int j = 0; j < COLUMNS.length; j++) {
				System.out.print("\"" + COLUMNS[i] + COLUMNS[j] + "\",");
				if((i*COLUMNS.length + j) %20 == 0){
					System.out.println();
				}
			}
		}
	}
	
	
	public static final String[] COLUMNS = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","AA",
		"AB","AC","AD","AE","AF","AG","AH","AI","AJ","AK","AL","AM","AN","AO","AP","AQ","AR","AS","AT","AU",
		"AV","AW","AX","AY","AZ","BA","BB","BC","BD","BE","BF","BG","BH","BI","BJ","BK","BL","BM","BN","BO",
		"BP","BQ","BR","BS","BT","BU","BV","BW","BX","BY","BZ","CA","CB","CC","CD","CE","CF","CG","CH","CI",
		"CJ","CK","CL","CM","CN","CO","CP","CQ","CR","CS","CT","CU","CV","CW","CX","CY","CZ","DA","DB","DC",
		"DD","DE","DF","DG","DH","DI","DJ","DK","DL","DM","DN","DO","DP","DQ","DR","DS","DT","DU","DV","DW",
		"DX","DY","DZ","EA","EB","EC","ED","EE","EF","EG","EH","EI","EJ","EK","EL","EM","EN","EO","EP","EQ",
		"ER","ES","ET","EU","EV","EW","EX","EY","EZ","FA","FB","FC","FD","FE","FF","FG","FH","FI","FJ","FK",
		"FL","FM","FN","FO","FP","FQ","FR","FS","FT","FU","FV","FW","FX","FY","FZ","GA","GB","GC","GD","GE",
		"GF","GG","GH","GI","GJ","GK","GL","GM","GN","GO","GP","GQ","GR","GS","GT","GU","GV","GW","GX","GY",
		"GZ","HA","HB","HC","HD","HE","HF","HG","HH","HI","HJ","HK","HL","HM","HN","HO","HP","HQ","HR","HS",
		"HT","HU","HV","HW","HX","HY","HZ","IA","IB","IC","ID","IE","IF","IG","IH","II","IJ","IK","IL","IM",
		"IN","IO","IP","IQ","IR","IS","IT","IU","IV","IW","IX","IY","IZ","JA","JB","JC","JD","JE","JF","JG",
		"JH","JI","JJ","JK","JL","JM","JN","JO","JP","JQ","JR","JS","JT","JU","JV","JW","JX","JY","JZ","KA",
		"KB","KC","KD","KE","KF","KG","KH","KI","KJ","KK","KL","KM","KN","KO","KP","KQ","KR","KS","KT","KU",
		"KV","KW","KX","KY","KZ","LA","LB","LC","LD","LE","LF","LG","LH","LI","LJ","LK","LL","LM","LN","LO",
		"LP","LQ","LR","LS","LT","LU","LV","LW","LX","LY","LZ","MA","MB","MC","MD","ME","MF","MG","MH","MI",
		"MJ","MK","ML","MM","MN","MO","MP","MQ","MR","MS","MT","MU","MV","MW","MX","MY","MZ","NA","NB","NC",
		"ND","NE","NF","NG","NH","NI","NJ","NK","NL","NM","NN","NO","NP","NQ","NR","NS","NT","NU","NV","NW",
		"NX","NY","NZ","OA","OB","OC","OD","OE","OF","OG","OH","OI","OJ","OK","OL","OM","ON","OO","OP","OQ",
		"OR","OS","OT","OU","OV","OW","OX","OY","OZ","PA","PB","PC","PD","PE","PF","PG","PH","PI","PJ","PK",
		"PL","PM","PN","PO","PP","PQ","PR","PS","PT","PU","PV","PW","PX","PY","PZ","QA","QB","QC","QD","QE",
		"QF","QG","QH","QI","QJ","QK","QL","QM","QN","QO","QP","QQ","QR","QS","QT","QU","QV","QW","QX","QY",
		"QZ","RA","RB","RC","RD","RE","RF","RG","RH","RI","RJ","RK","RL","RM","RN","RO","RP","RQ","RR","RS",
		"RT","RU","RV","RW","RX","RY","RZ","SA","SB","SC","SD","SE","SF","SG","SH","SI","SJ","SK","SL","SM",
		"SN","SO","SP","SQ","SR","SS","ST","SU","SV","SW","SX","SY","SZ","TA","TB","TC","TD","TE","TF","TG",
		"TH","TI","TJ","TK","TL","TM","TN","TO","TP","TQ","TR","TS","TT","TU","TV","TW","TX","TY","TZ","UA",
		"UB","UC","UD","UE","UF","UG","UH","UI","UJ","UK","UL","UM","UN","UO","UP","UQ","UR","US","UT","UU",
		"UV","UW","UX","UY","UZ","VA","VB","VC","VD","VE","VF","VG","VH","VI","VJ","VK","VL","VM","VN","VO",
		"VP","VQ","VR","VS","VT","VU","VV","VW","VX","VY","VZ","WA","WB","WC","WD","WE","WF","WG","WH","WI",
		"WJ","WK","WL","WM","WN","WO","WP","WQ","WR","WS","WT","WU","WV","WW","WX","WY","WZ","XA","XB","XC",
		"XD","XE","XF","XG","XH","XI","XJ","XK","XL","XM","XN","XO","XP","XQ","XR","XS","XT","XU","XV","XW",
		"XX","XY","XZ","YA","YB","YC","YD","YE","YF","YG","YH","YI","YJ","YK","YL","YM","YN","YO","YP","YQ",
		"YR","YS","YT","YU","YV","YW","YX","YY","YZ","ZA","ZB","ZC","ZD","ZE","ZF","ZG","ZH","ZI","ZJ","ZK",
		"ZL","ZM","ZN","ZO","ZP","ZQ","ZR","ZS","ZT","ZU","ZV","ZW","ZX","ZY","ZZ"};
}
