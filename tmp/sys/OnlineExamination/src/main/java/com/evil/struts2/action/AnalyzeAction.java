package com.evil.struts2.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.evil.pojo.Paper;
import com.evil.pojo.statistics.QuestionStatisticsModel;
import com.evil.service.GradeService;
import com.evil.service.PaperService;
import com.evil.service.StatisticalService;
import com.evil.util.Page;
import com.evil.util.ValidateUtil;

@Controller("AnalyzeAction")
@Scope("prototype")
public class AnalyzeAction extends BaseAction<Paper>{
	private static final long serialVersionUID = -2768349705946343273L;
	
	@Resource(name = "PaperService")
	private PaperService paperService;
	
	//注入成绩service
	@Resource
	private GradeService gradeService;
	
	@Resource(name="statisticalService")
	private StatisticalService statisticalService;
	
	private double averageScore[];
	private List<QuestionStatisticsModel> questionModels;
	

	/**
	 * 到分析作答页面
	 * @return
	 */
    public String toAnsWerAnalyzePage(){
		Page page = new Page();
		page.setNumPerPage(numPerPage);
		page.setCurrentPage(pageNum);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cloesd", 1);
		map.put("formalTest",model.isFormalTest()); 
		String sortfield=null;
		if(!ValidateUtil.isNull(orderDirection)&&!ValidateUtil.isNull(orderField)){
			sortfield= orderField + " " + orderDirection;
		}
		pageResult = paperService.findPagePaper(page,map,sortfield);
    	return "ansWerAnalyzePage";
    }
    /**
     *到作答情况页面
     * @return
     */
    public String toAnswerCasePage(){
    	model=paperService.getPaperWithChildren(model.getId(), false);
    	questionModels=statisticalService.statisticalQuestionModel(model.getId());
    	return "answerCasePage";
    }
    
    public String toGradeStatisticsPage(){
    	model=paperService.getPaperWithChildren(model.getId(), true);
    	averageScore=gradeService.getGradeAverageByPaperId(model.getId());
		Page page = new Page();
		page.setNumPerPage(numPerPage);
		page.setCurrentPage(pageNum);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isDelete", "1");
		map.put("paperId", model.getId());
		pageResult = gradeService.findPageGrade(page, map, " allscore desc");
    	return "gradeStatisticsPage";
    }
	//get/set...方法
	public double[] getAverageScore() {
		return averageScore;
	}
	public void setAverageScore(double[] averageScore) {
		this.averageScore = averageScore;
	}
	public List<QuestionStatisticsModel> getQuestionModels() {
		return questionModels;
	}
	public void setQuestionModels(List<QuestionStatisticsModel> questionModels) {
		this.questionModels = questionModels;
	}
	
	
    
    
    
    
	
}
