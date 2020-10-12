package com.foo.common.base.utils;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Pattern;

public class ExcelComplexHelper {
    private static boolean test = false;
    private final static Logger logger = LoggerFactory.getLogger(ExcelComplexHelper.class);
    private static Map<String, Integer> excelLetter2IntMap;
    private static List<String> months = Lists.newArrayList();

    public static void main(String[] args) throws Exception {

        if (test) {
            args = new String[2];
            args[0] = "generateGl";
            args[1] = "6,7";
        } else {
            if (args.length != 2) {
                logger.info("action error.");
                return;
            }
            if (!args[0].equals("generateGl")) {
                logger.info("support generateGl action only.");
                return;
            }
        }


        Set<String> payrolls = Sets.newTreeSet(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return ComparisonChain.start().compare(Integer.valueOf(o1), Integer.valueOf(o2)).result();
            }
        });

        payrolls.addAll(Splitter.on(",").omitEmptyStrings().splitToList(args[1]));

        if (payrolls.size() < 1) {
            logger.info("generateGl command error.");
            return;
        }
        logger.info("generateGl for month:{}", payrolls);

        final String fileNamePrefix = "Summary of payroll ";
        final String fileNameExtPrefix = ".xlsx";
        Map<String, String> monthNum2FileNames =
                ImmutableMap.<String, String>builder()
                        .put("1", fileNamePrefix + "Jan" + fileNameExtPrefix)
                        .put("2", fileNamePrefix + "Feb" + fileNameExtPrefix)
                        .put("3", fileNamePrefix + "Mar" + fileNameExtPrefix)
                        .put("4", fileNamePrefix + "Apr" + fileNameExtPrefix)
                        .put("5", fileNamePrefix + "May" + fileNameExtPrefix)
                        .put("6", fileNamePrefix + "Jun" + fileNameExtPrefix)
                        .put("7", fileNamePrefix + "Jul" + fileNameExtPrefix)
                        .put("8", fileNamePrefix + "Aug" + fileNameExtPrefix)
                        .put("9", fileNamePrefix + "Sep" + fileNameExtPrefix)
                        .put("10", fileNamePrefix + "Oct" + fileNameExtPrefix)
                        .put("11", fileNamePrefix + "Nov" + fileNameExtPrefix)
                        .put("12", fileNamePrefix + "Dec" + fileNameExtPrefix)
                        .build();

        Map<String, String> monthMappings =
                ImmutableMap.<String, String>builder()
                        .put("1", "Jan")
                        .put("2", "Feb")
                        .put("3", "Mar")
                        .put("4", "Apr")
                        .put("5", "May")
                        .put("6", "Jun")
                        .put("7", "Jul")
                        .put("8", "Aug")
                        .put("9", "Sep")
                        .put("10", "Oct")
                        .put("11", "Nov")
                        .put("12", "Dec")
                        .build();

        List<String> fileNames = Lists.newArrayList();
        int tmpInt;
        for (String payroll : payrolls) {
            tmpInt = Integer.parseInt(payroll);
            if (tmpInt > 12 || tmpInt < 1) {
                logger.info("输入的月份错误,只允许1到12的数字");
                return;
            }
            fileNames.add(monthNum2FileNames.get(payroll));
            months.add(monthMappings.get(payroll));
        }
        logger.info("fileNames:{}", fileNames);
        logger.info("months:{}", months);

        //analyze working directory,Start
        URL url = ExcelComplexHelper.class.getProtectionDomain().getCodeSource().getLocation();
        String basePath = URLDecoder.decode(url.getPath(), "utf-8");
        if (basePath.endsWith(".jar")) {
            basePath = basePath.substring(0, basePath.lastIndexOf("/") + 1);
        }
        logger.info("resolve working path of:{}", basePath);
        //analyze working directory,End


        //check files are all contained here,Start
        Collection<File> payrollFiles = FileUtils.listFiles(new File(basePath), new String[]{"xlsx"}, false);
        String tmpName = "";
        Set<String> realFileNames = Sets.newHashSet();
        for (File payrollFile : payrollFiles) {
            tmpName = payrollFile.getName();
            realFileNames.add(tmpName);
        }
        boolean isReady = realFileNames.containsAll(fileNames);
        logger.info("realFileNames are:{}", realFileNames);
        logger.info("fileNames acceptd are:{}", fileNames);
        if (!isReady) {
            fileNames.removeAll(realFileNames);
            logger.info("请放置如下的payroll文件:{}", fileNames);
            return;
        }
        //check files are all contained here,End

        //reading kitty.xlsx for config,Start
        XSSFWorkbook wb = new XSSFWorkbook(
                new FileInputStream(FilenameUtils.concat(basePath, "kitty.xlsx")));
        Sheet mySheet = wb.getSheet("kitty");
        Row row;
        Map<String, Object> initCostCenterMap = Maps.newHashMap();
        for (int i = 0; i < mySheet.getLastRowNum(); i++) {
            row = mySheet.getRow(i);
            if (row == null || row.getCell(0) == null || row.getCell(0).getStringCellValue().equals("")
                    || row.getCell(1) == null || row.getCell(1).getStringCellValue().equals("")
                    ) {
                logger.warn("find costCenter empty value on row:{}.Just break it.", i + 1);
                break;
            }
            if (row.getRowNum() == 0) {
                continue;
            }
            initCostCenterMap.put(row.getCell(0).getStringCellValue().trim(), ImmutableMap.<String, Object>of("nickName", row.getCell(1).getStringCellValue().trim()));
        }

        logger.info("result:{}", initCostCenterMap);
        //reading kitty.xlsx for config,End

        //reading payrolls->database sheet,Start
        List<Map<String, Object>> databasePayrolls = getDatabasePayrolls(fileNames, basePath);
        //reading payrolls->database sheet,End


        generateGlFeeSheet(databasePayrolls, basePath, initCostCenterMap);

    }

    private static void generateGlFeeSheet(List<Map<String, Object>> databasePayrolls, String basePath, Map<String, Object> initCostCenterMap) throws Exception {

        logger.info("generateGlFeeSheet start.");
        XSSFWorkbook wb = new XSSFWorkbook();

        //Create reusable style,Start
        XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();
        XSSFFont boldFont = wb.createFont();
        boldFont.setBold(true);
        cellStyle.setFont(boldFont);
        //Create reusable style,End


        Sheet sheet = wb.createSheet("fee");
        Row row = null;
        Cell cell = null;

        //From column e to x
        List<String> row2Names = ImmutableList.<String>of("工资", "工资", "工资", "月奖", "月奖",
                "加班工资", "加班工资", "加班工资", "津贴", "津贴", "津贴", "公积金", "公积金", "公积金",
                "年奖", "年奖", "年奖", "服务年限奖", "服务年限奖", "员工储蓄奖");

        //From column d to y
        List<String> row3Names = ImmutableList.<String>of("total", "主管", "助理", "工人"
                , "助理", "工人", "主管", "助理", "工人", "主管", "助理", "工人", "主管", "助理",
                "工人", "主管", "助理", "工人", "主管", "助理", "主管", "管理费");

        //From column b to x
        List<String> row4Names = ImmutableList.<String>of("Row Labels", "check", "",
                "S001100100", "S001100200", "S001100220"
                , "S001585200", "S001585220", "S001710100", "S001710200", "S001710220",
                "S001730100", "S001730200", "S001730220", "S006310100", "S006310200",
                "S006310220", "S001598100", "S001598200", "S001598220", "S006690100", "S006690200", "S006360100");

        int columnIndex = getExcelColumn("e");
        row = sheet.createRow(1);
        for (String name : row2Names) {
            cell = row.createCell(columnIndex++);
            cell.setCellValue(name);
        }

        columnIndex = getExcelColumn("d");
        row = sheet.createRow(2);
        for (String name : row3Names) {
            cell = row.createCell(columnIndex++);
            cell.setCellValue(name);
        }

        columnIndex = getExcelColumn("b");
        row = sheet.createRow(3);
        for (String name : row4Names) {
            cell = row.createCell(columnIndex++);
            cell.setCellValue(name);
        }

        //From column b to y,开始拼接rowData数据

        //提前二次计算表格需要的数据
        Map<String, String> finalRowDataMap = Maps.newHashMap();
        Map<String, Object> tmpDatabasePayrollsMap = Maps.newHashMap();
        List<Map<String, String>> tmpPayrollsList;
        String tmpCostCenterKey;
        List<String> rowDatas = Lists.newArrayList();
        Map<String, Object> tmpCostCenterMap;
        Row tmpRow;
        Cell tmpCell;
        int tmpRowLoopDataIndex = 4;
        int columnStartIndex = getExcelColumn("b");
        final int dataStartColumnIndex = columnStartIndex;
        int tmpRowDataLoopIndex = 0;
        int tmpMonthIndex = 0;

        String tmpIdentify;

        for (Map<String, Object> databasePayroll : databasePayrolls) {//遍历每月

            double dMonthSum = 0;
            double eMonthSum = 0;
            double fMonthSum = 0;
            double gMonthSum = 0;
            double hMonthSum = 0;
            double iMonthSum = 0;
            double jMonthSum = 0;
            double kMonthSum = 0;
            double lMonthSum = 0;
            double mMonthSum = 0;
            double nMonthSum = 0;
            double oMonthSum = 0;
            double pMonthSum = 0;
            double qMonthSum = 0;
            double rMonthSum = 0;
            double sMonthSum = 0;
            double tMonthSum = 0;
            double uMonthSum = 0;
            double vMonthSum = 0;
            double wMonthSum = 0;
            double xMonthSum = 0;
            double yMonthSum = 0;


            for (Map.Entry<String, Object> tmpEntry : databasePayroll.entrySet()) {//遍历每个costCenter
                tmpCostCenterKey = tmpEntry.getKey();
                tmpPayrollsList = (List<Map<String, String>>) tmpEntry.getValue();
                tmpCostCenterMap = (Map<String, Object>) initCostCenterMap.get(tmpCostCenterKey);
                rowDatas.add(tmpCostCenterKey);
                rowDatas.add(tmpCostCenterMap.get("nickName").toString());

                double dSum = 0; //c列统计,e列->x列
                double eSum = 0; //e列主管工资
                double fSum = 0; //f列助理工资
                double gSum = 0; //g列工人工资
                double hSum = 0; //h列助理月奖
                double iSum = 0; //g列工人月奖
                double jSum = 0; //j列主管加班工资
                double kSum = 0; //j列助理加班工资
                double lSum = 0; //j列工人加班工资
                double mSum = 0; //m列主管津贴
                double nSum = 0; //n列助理津贴
                double oSum = 0; //o列工人津贴
                double pSum = 0; //p列主管公积金
                double qSum = 0; //q列助理公积金
                double rSum = 0; //r列工人公积金
                double sSum = 0; //s列主管年奖
                double tSum = 0; //t列助理年奖
                double uSum = 0; //u列工人年奖
                double vSum = 0; //v列主管服务年限奖
                double wSum = 0; //w列助理服务年限奖
                double xSum = 0; //x列员工储蓄奖,注意,这是主管+助理+工人的属性
                double ySum = 0; //y列管理费

                payrollMap:
                for (Map<String, String> payrollMap : tmpPayrollsList) {
                    tmpIdentify = payrollMap.get("e").toUpperCase().toString();
                    double gData = Double.parseDouble(payrollMap.get("g"));
                    double adData = Double.parseDouble(payrollMap.get("ad"));
                    double qData = Double.parseDouble(payrollMap.get("q"));
                    double hData = Double.parseDouble(payrollMap.get("h"));
                    double ayData = Double.parseDouble(payrollMap.get("ay"));
                    double anData = Double.parseDouble(payrollMap.get("an"));
                    double aoData = Double.parseDouble(payrollMap.get("ao"));
                    double apData = Double.parseDouble(payrollMap.get("ap"));
                    double bfData = Double.parseDouble(payrollMap.get("bf"));

                    xSum += apData;
                    ySum += bfData;

                    //主管
                    if (tmpIdentify.equals("EXE")) {
                        eSum += gData;
                        jSum += qData;
                        mSum += hData;
                        pSum += ayData;
                        sSum += anData;
                        vSum += aoData;
                    }
                    //助理
                    else if (tmpIdentify.equals("MNE")) {
                        fSum += gData;
                        hSum += adData;
                        kSum += qData;
                        nSum += hData;
                        qSum += ayData;
                        tSum += anData;
                        wSum += aoData;
                    }
                    //工人
                    else if (tmpIdentify.equals("DL")) {
                        gSum += gData;
                        iSum += adData;
                        lSum += qData;
                        oSum += hData;
                        rSum += ayData;
                        uSum += anData;
                    } else {
                        continue payrollMap;
                    }
                    dSum += (eSum + fSum + gSum + hSum + iSum + jSum + kSum + lSum + mSum + nSum
                            + oSum + pSum + qSum + rSum + sSum + tSum + uSum + vSum + wSum + xSum);

                }

                dMonthSum += dSum;
                eMonthSum += eSum;
                fMonthSum += fSum;
                gMonthSum += gSum;
                hMonthSum += hSum;
                iMonthSum += iSum;
                jMonthSum += jSum;
                kMonthSum += kSum;
                lMonthSum += lSum;
                mMonthSum += mSum;
                nMonthSum += nSum;
                oMonthSum += oSum;
                pMonthSum += pSum;
                qMonthSum += qSum;
                rMonthSum += rSum;
                sMonthSum += sSum;
                tMonthSum += tSum;
                uMonthSum += uSum;
                vMonthSum += vSum;
                wMonthSum += wSum;
                xMonthSum += xSum;
                yMonthSum += ySum;

                rowDatas.add(dSum == 0 ? "" : formatDouble(dSum));
                rowDatas.add(eSum == 0 ? "" : formatDouble(eSum));
                rowDatas.add(fSum == 0 ? "" : formatDouble(fSum));
                rowDatas.add(gSum == 0 ? "" : formatDouble(gSum));
                rowDatas.add(hSum == 0 ? "" : formatDouble(hSum));
                rowDatas.add(iSum == 0 ? "" : formatDouble(iSum));
                rowDatas.add(jSum == 0 ? "" : formatDouble(jSum));
                rowDatas.add(kSum == 0 ? "" : formatDouble(kSum));
                rowDatas.add(lSum == 0 ? "" : formatDouble(lSum));
                rowDatas.add(mSum == 0 ? "" : formatDouble(mSum));
                rowDatas.add(nSum == 0 ? "" : formatDouble(nSum));
                rowDatas.add(oSum == 0 ? "" : formatDouble(oSum));
                rowDatas.add(pSum == 0 ? "" : formatDouble(pSum));
                rowDatas.add(qSum == 0 ? "" : formatDouble(qSum));
                rowDatas.add(rSum == 0 ? "" : formatDouble(rSum));
                rowDatas.add(sSum == 0 ? "" : formatDouble(sSum));
                rowDatas.add(tSum == 0 ? "" : formatDouble(tSum));
                rowDatas.add(uSum == 0 ? "" : formatDouble(uSum));
                rowDatas.add(vSum == 0 ? "" : formatDouble(vSum));
                rowDatas.add(wSum == 0 ? "" : formatDouble(wSum));
                rowDatas.add(xSum == 0 ? "" : formatDouble(xSum));
                rowDatas.add(ySum == 0 ? "" : formatDouble(ySum));


                // ------------------------DO NOT MODIFY THE FOLLOWING--------------------
                tmpRow = sheet.createRow(tmpRowLoopDataIndex++);
                for (String rowData : rowDatas) {
                    tmpCell = tmpRow.createCell(columnStartIndex++);
                    tmpCell.setCellValue(rowData);
                }
                columnStartIndex = dataStartColumnIndex;
                rowDatas = Lists.newArrayList();

            }

            int monthColumnStartIndex = 0;
            //每月总计行,a到y
            rowDatas.add(months.get(tmpMonthIndex++));
            rowDatas.add("Grand Total");
            rowDatas.add("0.00");
            rowDatas.add(dMonthSum == 0 ? "" : formatDouble(dMonthSum));
            rowDatas.add(eMonthSum == 0 ? "" : formatDouble(eMonthSum));
            rowDatas.add(fMonthSum == 0 ? "" : formatDouble(fMonthSum));
            rowDatas.add(gMonthSum == 0 ? "" : formatDouble(gMonthSum));
            rowDatas.add(hMonthSum == 0 ? "" : formatDouble(hMonthSum));
            rowDatas.add(iMonthSum == 0 ? "" : formatDouble(iMonthSum));
            rowDatas.add(jMonthSum == 0 ? "" : formatDouble(jMonthSum));
            rowDatas.add(kMonthSum == 0 ? "" : formatDouble(kMonthSum));
            rowDatas.add(lMonthSum == 0 ? "" : formatDouble(lMonthSum));
            rowDatas.add(mMonthSum == 0 ? "" : formatDouble(mMonthSum));
            rowDatas.add(nMonthSum == 0 ? "" : formatDouble(nMonthSum));
            rowDatas.add(oMonthSum == 0 ? "" : formatDouble(oMonthSum));
            rowDatas.add(pMonthSum == 0 ? "" : formatDouble(pMonthSum));
            rowDatas.add(qMonthSum == 0 ? "" : formatDouble(qMonthSum));
            rowDatas.add(rMonthSum == 0 ? "" : formatDouble(rMonthSum));
            rowDatas.add(sMonthSum == 0 ? "" : formatDouble(sMonthSum));
            rowDatas.add(tMonthSum == 0 ? "" : formatDouble(tMonthSum));
            rowDatas.add(uMonthSum == 0 ? "" : formatDouble(uMonthSum));
            rowDatas.add(vMonthSum == 0 ? "" : formatDouble(vMonthSum));
            rowDatas.add(wMonthSum == 0 ? "" : formatDouble(wMonthSum));
            rowDatas.add(xMonthSum == 0 ? "" : formatDouble(xMonthSum));
            rowDatas.add(yMonthSum == 0 ? "" : formatDouble(yMonthSum));


            tmpRow = sheet.createRow(tmpRowLoopDataIndex++);
            for (String rowData : rowDatas) {
                tmpCell = tmpRow.createCell(monthColumnStartIndex++);
                tmpCell.setCellStyle(cellStyle);
                tmpCell.setCellValue(rowData);
            }
            rowDatas = Lists.newArrayList();

        }

        FileOutputStream out = new FileOutputStream(FilenameUtils.concat(basePath, "gl.xlsx"));
        wb.write(out);
        out.close();
        logger.info("generateGlFeeSheet end.");
    }

    private void getStyle(Workbook wb) {
        Font font = wb.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 16);
        XSSFCellStyle title_one_style = (XSSFCellStyle) wb.createCellStyle();
        title_one_style.setAlignment(CellStyle.ALIGN_CENTER);
        title_one_style
                .setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        title_one_style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        title_one_style.setFont(font);
    }

    private static List<Map<String, Object>> getDatabasePayrolls(List<String> fileNames, String bathPath)
            throws Exception {
        //reading payrolls->database sheet,Start
        Sheet tmpDatabaseSheet;
        Row tmpRow;
        int tmpRowNum = 0;
        Cell tmpCell;
        List<Map<String, Object>> databasePayrolls = Lists.newArrayList();
        Map<String, Object> databasePayrollsMap = Maps.newTreeMap();
        Map<String, String> tmpDataMap = Maps.newHashMap();
        String tmpCellValue;
        String tmpColumnLabel;
        String tmpArray[];
        String tmpCostCenter;
        double tmpSumDouble = 0;
        XSSFWorkbook wb;
        List<Map<String, String>> tmpDatabasePayrolls;
        for (String fileName : fileNames) {
            logger.info("analysis file:{} start.", fileName);
            wb = new XSSFWorkbook(
                    new FileInputStream(FilenameUtils.concat(bathPath, fileName)));
            tmpDatabaseSheet = wb.getSheet("database");
            for (int i = 0; i < tmpDatabaseSheet.getLastRowNum(); i++) {
                if (i < 3) {
                    continue;
                }
                tmpRow = tmpDatabaseSheet.getRow(i);
                if (tmpRow == null || tmpRow.getCell(0) == null) {
                    logger.warn("find costCenter empty value on row:{}.Just break it.", i + 1);
                    break;
                }

                tmpColumnLabel = "a";
                tmpCell = tmpRow.getCell(getExcelColumn(tmpColumnLabel));
                tmpCell.setCellType(Cell.CELL_TYPE_STRING);
                tmpCellValue = tmpCell.getRichStringCellValue().toString().trim();
                tmpDataMap.put(tmpColumnLabel, tmpCellValue);

                if (tmpCellValue.equals("")) {
                    break;
                }

                //人事级别,主管:EXE  助理对应:MNE 工人对应:DL
                tmpColumnLabel = "e";
                tmpCell = tmpRow.getCell(getExcelColumn(tmpColumnLabel));
                tmpCell.setCellType(Cell.CELL_TYPE_STRING);
                tmpCellValue = tmpCell.getRichStringCellValue().toString().trim();
                tmpDataMap.put(tmpColumnLabel, tmpCellValue);

                //基本工资2
                tmpColumnLabel = "g";
                tmpDataMap = setCellDoubleValue(tmpDataMap, tmpColumnLabel, tmpRow);

                //月奖对应
                tmpColumnLabel = "ad";
                tmpDataMap = setCellDoubleValue(tmpDataMap, tmpColumnLabel, tmpRow);

                //加班费汇总
                tmpColumnLabel = "q";
                tmpDataMap = setCellDoubleValue(tmpDataMap, tmpColumnLabel, tmpRow);

                //成本中心类别,解析格式: DT01B02004 - Human Resources
                tmpColumnLabel = "bo";
                tmpCell = tmpRow.getCell(getExcelColumn(tmpColumnLabel));
                tmpCell.setCellType(Cell.CELL_TYPE_STRING);
                tmpCellValue = tmpCell.getRichStringCellValue().toString().trim();
                tmpCostCenter = Splitter.on("-").omitEmptyStrings().splitToList(tmpCellValue).get(0).trim();
                tmpDataMap.put(tmpColumnLabel, tmpCostCenter);

                //津贴,汇总行:H, I, Z, AB, AC, AE, AF, AG, AH, AL, AM, AQ, AR, AS, AT
                tmpColumnLabel = "h";
                tmpCell = tmpRow.getCell(getExcelColumn(tmpColumnLabel));
                tmpCell.setCellType(Cell.CELL_TYPE_STRING);
                tmpArray = new String[]{"h", "i", "ab", "ac", "ae", "af", "ag", "ah", "al", "am", "aq", "ar", "as", "at"};
                tmpSumDouble = 0;
                for (String tmpJt : tmpArray) {
                    tmpSumDouble += getCellDoubleValue(tmpJt, tmpRow);
                }
                tmpCellValue = tmpSumDouble + "";
                tmpDataMap.put(tmpColumnLabel, tmpCellValue);

                //年奖
                tmpColumnLabel = "an";
                tmpDataMap = setCellDoubleValue(tmpDataMap, tmpColumnLabel, tmpRow);

                //服务年限奖
                tmpColumnLabel = "ao";
                tmpDataMap = setCellDoubleValue(tmpDataMap, tmpColumnLabel, tmpRow);

                //员工储蓄奖
                tmpColumnLabel = "ap";
                tmpDataMap = setCellDoubleValue(tmpDataMap, tmpColumnLabel, tmpRow);

                // 公积金,汇总行:AY, BA
                tmpColumnLabel = "ay";
                tmpCell = tmpRow.getCell(getExcelColumn(tmpColumnLabel));
                tmpCell.setCellType(Cell.CELL_TYPE_STRING);
                tmpArray = new String[]{"ay", "ba"};
                tmpSumDouble = 0;
                for (String tmpJt : tmpArray) {
                    tmpSumDouble += getCellDoubleValue(tmpJt, tmpRow);
                }
                tmpCellValue = tmpSumDouble + "";
                tmpDataMap.put(tmpColumnLabel, tmpCellValue);

                // 管理费,汇总行:BF, BG
                tmpColumnLabel = "bf";
                tmpCell = tmpRow.getCell(getExcelColumn(tmpColumnLabel));
                tmpCell.setCellType(Cell.CELL_TYPE_STRING);
                tmpArray = new String[]{"bf", "bg"};
                tmpSumDouble = 0;
                for (String tmpJt : tmpArray) {
                    tmpSumDouble += getCellDoubleValue(tmpJt, tmpRow);
                }
                tmpCellValue = tmpSumDouble + "";
                tmpDataMap.put(tmpColumnLabel, tmpCellValue);

                tmpDatabasePayrolls = (List<Map<String, String>>) databasePayrollsMap.get(tmpCostCenter);
                if (tmpDatabasePayrolls == null) {
                    tmpDatabasePayrolls = Lists.newArrayList();
                    databasePayrollsMap.put(tmpCostCenter, tmpDatabasePayrolls);
                }
                tmpDatabasePayrolls.add(tmpDataMap);
                tmpDataMap = Maps.newHashMap();

                tmpRowNum++;
            }
            logger.info("analysis file:{} end.", fileName);
            databasePayrolls.add(databasePayrollsMap);
            databasePayrollsMap = Maps.newTreeMap();
        }
        for (Map<String, Object> databasePayroll : databasePayrolls) {
            logger.trace("databasePayrolls as:{}", databasePayroll);
        }
        //reading payrolls->database sheet,End
        return databasePayrolls;
    }

    /**
     * Intend for excel column mapping,for an example,Z(ignore case) mapping to 25.
     * <p>
     * Support regular expressions only as:[a-zA-Z][a-zA-Z]?
     *
     * @param excelColumnLabel
     * @return
     */
    private static int getExcelColumn(String excelColumnLabel) {
        Preconditions.checkNotNull(excelColumnLabel);
        Preconditions.checkArgument(Pattern.compile("[a-zA-Z][a-zA-Z]?").matcher(excelColumnLabel).matches(), "Support [a-z][a-z]? only.");
        if (excelLetter2IntMap == null) {
            final String availableArray[] = {"a", "b", "c", "d", "e", "f", "g", "h", "i",
                    "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
                    "w", "x", "y", "z"};
            excelLetter2IntMap = Maps.newHashMap();
            int x = 0;
            for (String letter : availableArray) {
                excelLetter2IntMap.put(letter, x++);
            }
        }
        excelColumnLabel = excelColumnLabel.toLowerCase();
        if (excelColumnLabel.length() == 1) {
            return excelLetter2IntMap.get(excelColumnLabel);
        }
        return (excelLetter2IntMap.get(excelColumnLabel.substring(0, 1)) + 1) * 26 +
                excelLetter2IntMap.get(excelColumnLabel.substring(1, 2));
    }

    private static Map<String, String> setCellDoubleValue(Map<String, String> tmpDataMap, String tmpColumnLabel
            , Row tmpRow) {
        tmpDataMap.put(tmpColumnLabel, getCellDoubleValue(tmpColumnLabel, tmpRow) + "");
        return tmpDataMap;
    }

    private static double getCellDoubleValue(String tmpColumnLabel, Row tmpRow) {
        Cell tmpCell = tmpRow.getCell(getExcelColumn(tmpColumnLabel));
        tmpCell.setCellType(Cell.CELL_TYPE_STRING);
        final String tmpCellValue = tmpCell.getRichStringCellValue().toString().trim();
        if (tmpCellValue.equals("")) {
            return 0;
        }
        return Double.parseDouble(tmpCellValue);
    }

    private static String formatDouble(double result) {
        return String.format("%1$.2f", result);
    }

}
