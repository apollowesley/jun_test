package com.evil.service;

import java.util.Map;

import com.evil.pojo.Exam;
import com.evil.util.Page;
import com.evil.util.PageResult;

public interface ExamService extends BaseService<Exam> {
	/**
	 * 查询考试列表并分页
	 * @param page  分页信息
	 * @param map   参数条件
	 * @param sortfields 排序方式
	 * @return
	 */
	public PageResult findExamPage(Page page, Map<String, Object> map,
			String... sortfields);

	/**
	 * 删除考试
	 * @param model
	 */
	public void deleteExam(Exam model);


}
