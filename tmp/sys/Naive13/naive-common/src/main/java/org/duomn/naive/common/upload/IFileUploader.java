package org.duomn.naive.common.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.duomn.naive.common.exception.BaseException;

public interface IFileUploader {

	void upload(HttpServletRequest req, HttpServletResponse resp) throws BaseException;
	
}
