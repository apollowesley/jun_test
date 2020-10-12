package com.leo.vhr.utils;

import com.leo.vhr.model.*;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Leo
 * @createDate: 2020/2/24
 * @version: 1.0
 */
public class POIUtils
{
    public static ResponseEntity<byte[]> employeeExcel(List<Employee> list)
    {
        //1.创建一个Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建文档摘要
        workbook.createInformationProperties();
        //3.获取并配置文档摘要信息
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        docInfo.setCategory("员工信息");
        docInfo.setManager("leo");
        docInfo.setCompany("朝花不迟暮");
        //4.获取文档摘要信息
        SummaryInformation sumInfo = workbook.getSummaryInformation();
        sumInfo.setTitle("员工信息表");
        sumInfo.setAuthor("leo");
        sumInfo.setComments("自生成");

        //5.创建样式
        HSSFCellStyle headStyle = workbook.createCellStyle();
        HSSFCellStyle dateStyle = workbook.createCellStyle();
        headStyle.setFillBackgroundColor(IndexedColors.YELLOW.index);
        //headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        //6.创建表单
        HSSFSheet sheet = workbook.createSheet("员工信息表");
        //设置列的宽度
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 12 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 10 * 256);
        sheet.setColumnWidth(4, 10 * 256);
        sheet.setColumnWidth(5, 10 * 256);
        sheet.setColumnWidth(6, 10 * 256);
        sheet.setColumnWidth(7, 10 * 256);
        sheet.setColumnWidth(8, 10 * 256);
        sheet.setColumnWidth(9, 10 * 256);
        sheet.setColumnWidth(10, 20 * 256);
        sheet.setColumnWidth(11, 20 * 256);
        sheet.setColumnWidth(12, 20 * 256);
        sheet.setColumnWidth(13, 20 * 256);
        sheet.setColumnWidth(14, 20 * 256);
        sheet.setColumnWidth(15, 20 * 256);
        sheet.setColumnWidth(16, 20 * 256);
        sheet.setColumnWidth(17, 20 * 256);
        sheet.setColumnWidth(18, 20 * 256);
        sheet.setColumnWidth(19, 20 * 256);
        sheet.setColumnWidth(20, 20 * 256);
        sheet.setColumnWidth(21, 20 * 256);
        sheet.setColumnWidth(22, 20 * 256);
        sheet.setColumnWidth(23, 20 * 256);
        sheet.setColumnWidth(24, 20 * 256);
        sheet.setColumnWidth(25, 20 * 256);

        //7.创建行
        HSSFRow row0 = sheet.createRow(0);
        //创建列
        HSSFCell c0 = row0.createCell(0);
        c0.setCellValue("编号");
        c0.setCellStyle(headStyle);

        HSSFCell c1 = row0.createCell(1);
        c1.setCellStyle(headStyle);
        c1.setCellValue("姓名");
        HSSFCell c2 = row0.createCell(2);
        c2.setCellStyle(headStyle);
        c2.setCellValue("工号");
        HSSFCell c3 = row0.createCell(3);
        c3.setCellStyle(headStyle);
        c3.setCellValue("性别");
        HSSFCell c4 = row0.createCell(4);
        c4.setCellStyle(headStyle);
        c4.setCellValue("出生日期");
        HSSFCell c5 = row0.createCell(5);
        c5.setCellStyle(headStyle);
        c5.setCellValue("身份证号");
        HSSFCell c6 = row0.createCell(6);
        c6.setCellStyle(headStyle);
        c6.setCellValue("婚姻状况");
        HSSFCell c7 = row0.createCell(7);
        c7.setCellStyle(headStyle);
        c7.setCellValue("民族");
        HSSFCell c8 = row0.createCell(8);
        c8.setCellStyle(headStyle);
        c8.setCellValue("籍贯");
        HSSFCell c9 = row0.createCell(9);
        c9.setCellStyle(headStyle);
        c9.setCellValue("政治面貌");
        HSSFCell c10 = row0.createCell(10);
        c10.setCellStyle(headStyle);
        c10.setCellValue("电子邮件");
        HSSFCell c11 = row0.createCell(11);
        c11.setCellStyle(headStyle);
        c11.setCellValue("电话号码");
        HSSFCell c12 = row0.createCell(12);
        c12.setCellStyle(headStyle);
        c12.setCellValue("联系地址");
        HSSFCell c13 = row0.createCell(13);
        c13.setCellStyle(headStyle);
        c13.setCellValue("所属部门");
        HSSFCell c14 = row0.createCell(14);
        c14.setCellStyle(headStyle);
        c14.setCellValue("职位");
        HSSFCell c15 = row0.createCell(15);
        c15.setCellStyle(headStyle);
        c15.setCellValue("职称");
        HSSFCell c16 = row0.createCell(16);
        c16.setCellStyle(headStyle);
        c16.setCellValue("聘用形式");
        HSSFCell c17 = row0.createCell(17);
        c17.setCellStyle(headStyle);
        c17.setCellValue("入职日期");
        HSSFCell c18 = row0.createCell(18);
        c18.setCellStyle(headStyle);
        c18.setCellValue("转正日期");
        HSSFCell c19 = row0.createCell(19);
        c19.setCellStyle(headStyle);
        c19.setCellValue("合同起始日期");
        HSSFCell c20 = row0.createCell(20);
        c20.setCellStyle(headStyle);
        c20.setCellValue("合同终止日期");
        HSSFCell c21 = row0.createCell(21);
        c21.setCellStyle(headStyle);
        c21.setCellValue("合同期限(年)");
        HSSFCell c22 = row0.createCell(22);
        c22.setCellStyle(headStyle);
        c22.setCellValue("最高学历");
        HSSFCell c23 = row0.createCell(23);
        c23.setCellStyle(headStyle);
        c23.setCellValue("毕业院校");
        HSSFCell c24 = row0.createCell(24);
        c24.setCellStyle(headStyle);
        c24.setCellValue("专业名称");
        HSSFCell c25 = row0.createCell(25);
        c25.setCellStyle(headStyle);
        c25.setCellValue("在职状态");
        for (int i = 0; i < list.size(); i++)
        {
            Employee emp = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(emp.getId());
            row.createCell(1).setCellValue(emp.getName());
            row.createCell(2).setCellValue(emp.getWorkID());
            row.createCell(3).setCellValue(emp.getGender());
            HSSFCell cell4 = row.createCell(4);
            cell4.setCellStyle(dateStyle);
            cell4.setCellValue(emp.getBirthday());

            row.createCell(5).setCellValue(emp.getIdCard());
            row.createCell(6).setCellValue(emp.getWedlock());
            row.createCell(7).setCellValue(emp.getNation().getName());
            row.createCell(8).setCellValue(emp.getNativePlace());
            row.createCell(9).setCellValue(emp.getPoliticsstatus().getName());
            row.createCell(10).setCellValue(emp.getEmail());
            row.createCell(11).setCellValue(emp.getPhone());
            row.createCell(12).setCellValue(emp.getAddress());
            row.createCell(13).setCellValue(emp.getDepartment().getName());
            row.createCell(14).setCellValue(emp.getPosition().getName());
            row.createCell(15).setCellValue(emp.getJobLevel().getName());
            row.createCell(16).setCellValue(emp.getEngageForm());
            HSSFCell cell17 = row.createCell(17);
            cell17.setCellStyle(dateStyle);
            cell17.setCellValue(emp.getBeginDate());

            HSSFCell cell18 = row.createCell(18);
            cell18.setCellStyle(dateStyle);
            cell18.setCellValue(emp.getConversionTime());

            HSSFCell cell19 = row.createCell(19);
            cell19.setCellStyle(dateStyle);
            cell19.setCellValue(emp.getBeginContract());

            HSSFCell cell20 = row.createCell(20);
            cell20.setCellStyle(dateStyle);
            cell20.setCellValue(emp.getEndContract());

            row.createCell(21).setCellValue(emp.getContractTerm());
            row.createCell(22).setCellValue(emp.getTiptopDegree());
            row.createCell(23).setCellValue(emp.getSchool());
            row.createCell(24).setCellValue(emp.getSpecialty());
            row.createCell(25).setCellValue(emp.getWorkState());
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpHeaders headers=new HttpHeaders();
        try
        {
            //把utf-8的转成ISO-8859-1，不然浏览器弹框显示中文名时，会出现乱码
            headers.setContentDispositionFormData("attachment",new String("员工表.xls".getBytes("UTF-8"),
                    "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(baos);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
//           public ResponseEntity(@Nullable T body, @Nullable MultiValueMap<String, String> headers,
//            HttpStatus status)
        return new ResponseEntity<byte[]>(baos.toByteArray(),headers, HttpStatus.CREATED);
    }

    /**
     * @param null
     * @description：excel解析成员工的集合
     * @return
     * @since v1.0.0
     * author Leo
     * date 2020/2/4
     */
    public static List<Employee> excelEmployeeImport(MultipartFile file, List<Nation> allNations,
                                                     List<Politicsstatus> allPoliticsstatus,
                                                     List<Department> allDepartments,
                                                     List<Position> allPositions,
                                                     List<JobLevel> allJobLevels)
    {
        List<Employee> list=new ArrayList<>();
        Employee employee=null;
        try
        {
            //创建一个workbook对象
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            //获取workbook中表单的数量
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++)
            {
                //获取表单
                HSSFSheet sheet = workbook.getSheetAt(i);
                //获取行数
                int numberOfRows = sheet.getPhysicalNumberOfRows();

                for (int j = 0; j <numberOfRows ; j++)
                {
                    if(j==0){
                        continue;//跳过标题行
                    }
                    //获取行
                    HSSFRow row = sheet.getRow(j);
                    //防止中间有空行
                    if(row==null){
                        continue;
                    }
                    //获取表单中的列
                    int cells = row.getPhysicalNumberOfCells();
                    employee=new Employee();
                    for (int k = 0; k < cells ; k++)
                    {
                        HSSFCell cell = row.getCell(k);
                        switch (cell.getCellType()){
                            case STRING:
                                String cellValue = cell.getStringCellValue();
                                switch (k){
                                    case 1:
                                        employee.setName(cellValue);
                                        break;
                                    case 2:
                                        employee.setWorkID(cellValue);
                                        break;
                                    case 3:
                                        employee.setGender(cellValue);
                                        break;
                                    case 5:
                                        employee.setIdCard(cellValue);
                                        break;
                                    case 6:
                                        employee.setWedlock(cellValue);
                                        break;
                                    case 7:
                                        int nationIndex = allNations.indexOf(new Nation(cellValue));
                                        employee.setNationId(allNations.get(nationIndex).getId());
                                        break;
                                    case 8:
                                        employee.setNativePlace(cellValue);
                                        break;
                                    case 9:
                                        int politicsStatus = allPoliticsstatus.indexOf(new Politicsstatus(cellValue));
                                        employee.setPoliticId(allPoliticsstatus.get(politicsStatus).getId());
                                        break;
                                    case 10:
                                        employee.setEmail(cellValue);
                                        break;
                                    case 11:
                                        employee.setPhone(cellValue);
                                        break;
                                    case 12:
                                        employee.setAddress(cellValue);
                                        break;
                                    case 13:
                                        int departmentId = allDepartments.indexOf(new Department(cellValue));
                                        employee.setDepartmentId(allDepartments.get(departmentId).getId());
                                        break;
                                    case 14:
                                        int positionId = allPositions.indexOf(new Position(cellValue));
                                        employee.setPosId(allPositions.get(positionId).getId());
                                        break;
                                    case 15:
                                        int jobLevelId = allJobLevels.indexOf(new JobLevel(cellValue));
                                        employee.setJobLevelId(allJobLevels.get(jobLevelId).getId());
                                        break;
                                    case 16:
                                        employee.setEngageForm(cellValue);
                                        break;
                                    case 22:
                                        employee.setTiptopDegree(cellValue);
                                        break;
                                    case 23:
                                        employee.setSchool(cellValue);
                                        break;
                                    case 24:
                                        employee.setSpecialty(cellValue);
                                        break;
                                    case 25:
                                        employee.setWorkState(cellValue);
                                        break;
                                }
                                break;
                            default:{
                                switch (k){
                                    case 4:
                                        employee.setBirthday(cell.getDateCellValue());
                                        break;
                                    case 17:
                                        employee.setBeginDate(cell.getDateCellValue());
                                        break;
                                    case 18:
                                        employee.setConversionTime(cell.getDateCellValue());
                                        break;
                                    case 19:
                                        employee.setBeginContract(cell.getDateCellValue());
                                        break;
                                    case 20:
                                        employee.setEndContract(cell.getDateCellValue());
                                        break;
                                    case 21:
                                        employee.setContractTerm(cell.getNumericCellValue());
                                        break;
                                }

                            }
                            break;
                        }
                    }
                    list.add(employee);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return list;
    }
}
