<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="test"><i class="icon-dropdown"></i>
<ul><li><a href="javascript:void(0);" onclick="if(confirm('确认删除此条记录？？？\n\n此删除为永久删除，不可恢复！！！')){javascript:document.getElementById('b').value='${list.bid}';document.deleteform.submit();}">删除</a></li></ul></div>