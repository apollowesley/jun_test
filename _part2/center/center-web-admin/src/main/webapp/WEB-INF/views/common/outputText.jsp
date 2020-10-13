<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.apache.playframework.util.ResponseUtils"%>
<%
ResponseUtils.renderText(response, (String)request.getAttribute("text"));
%>