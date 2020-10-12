package com.evil.struts2.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.evil.pojo.Questions;
import com.evil.service.QuestionBankService;
import com.evil.util.JsonUtil;
import com.evil.util.QuestionsUtil;
import com.evil.util.ReturnMsg;

@Component("QestionsExcelAction")
@Scope("prototype")
public class QestionsExcelAction extends BaseAction<Questions> {
	private static final long serialVersionUID = 1l;

	@Resource(name = "QuestionBankService")
	private QuestionBankService questionBankService;
	
	private File questionsExcel; //上传的文件
	private String questionsExcelFileName; //上传文件的名称
	
	/**
	 * 处理下载
	 * @return
	 */
	public String exportQuestions(){
		return SUCCESS;

	}
	/**
	 *下载文件处理 
	 */
	public InputStream getQuestionsExcelFile(){
		try {
			HSSFWorkbook wb=new HSSFWorkbook();
			List<Questions> questions=questionBankService.findAllQuestions();
			QuestionsUtil.crateQuestionExcel(wb,questions);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			questionsExcelFileName= new String("试题题库.xls".getBytes(), "ISO8859-1");
			wb.write(baos);
			return new ByteArrayInputStream(baos.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *去试题上传页面
	 */
	public String toImporteExportPage(){
		return "importeExportPage";
	}
	/**
	 * 处理试题导入
	 */
	public void doQuestionsImport(){
		ReturnMsg rm = new ReturnMsg();
		try {
			System.out.println(questionsExcel+","+questionsExcelFileName);
			questionBankService.ImportQuestionsDatabase(questionsExcel,questionsExcelFileName);
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("操作失败" + e.getMessage());
		}finally{
			rm.setCallbackType("");
			JsonUtil.returnMsg(rm);
		}
	}

	
	//get/set方法.....
	
	public File getQuestionsExcel() {
		return questionsExcel;
	}
	public void setQuestionsExcel(File questionsExcel) {
		this.questionsExcel = questionsExcel;
	}
	
	public String getQuestionsExcelFileName() {
		return questionsExcelFileName;
	}
	
	public void setQuestionsExcelFileName(String questionsExcelFileName) {
		this.questionsExcelFileName = questionsExcelFileName;
	}
	
}
