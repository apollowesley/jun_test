<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="menu"></div>

<script type="text/javascript">
(function(){
	var treeData = [{
	    text : "基础管理",
	    children : [{
	            text : "学生管理",
	            attributes : {
	                url : "login/student/student.jsp"
	            }
	        }, {
	            text : "系别管理",
	            attributes : {
	                url : "login/department/department.jsp"
	            }
	        }, {
	            text : "一级菜单3",
	            state : "closed",
	            children : [{
	                    text : "二级菜单1",
	                    attributes : {
	                        url : ""
	                    }
	                }, {
	                    text : "二级菜单2",
	                    attributes : {
	                        url : ""
	                    }
	                }, {
	                    text : "二级菜单3",
	                    attributes : {
	                        url : ""
	                    }
	                }
	            ]
	        }
	    ]
	}];
	$("#menu").tree({
	    data : treeData,
	    lines : true,
	    onClick : function (node) {
	        if (node.attributes) {
	        	openTab(node.text, node.attributes.url);
	        }
	    }
	});

	//在右边center区域打开菜单，新增tab
	function openTab(text, url) {
	    if ($("#mainTab").tabs('exists', text)) {
	        $('#mainTab').tabs('select', text);
	    } else {
	        $('#mainTab').tabs('add', {
	            title : text,
	            closable : true,
	           // href : ctx + url
	            content : '<iframe src="'+ ctx + url+'" scrolling="yes" frameborder="0" style="width:100%;height:100%;"></iframe>'
	        });
	    }
	}

})();

</script>
