<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>
<#assign classNameLowerCase = className?lower_case>
<%@ include file="/zbdp/common/page/template.jsp" %>
<%@ page contentType="text/html; charset=utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${r'${labels.'}${classNameFirstLower}Info${r'}'}</title>
<script type="text/javascript"
        src="${r'${ctx}'}/${namespace_dir}/${classNameFirstLower}.js?v=${r'$'}{appBean.version}"></script>
<script type="text/javascript"
        src="${r'${ctx}'}/${namespace_dir}/${classNameFirstLower}-dialog.js?v=${r'$'}{appBean.version}"></script>
</head>
<body ng-app="ngApp">
<%@ include file="/zbdp/common/page/template-menu.jsp" %>

   <div ng-controller="${classNameFirstLower}Ctrl" class="mainContent mainWidth">
       <form class="validate-form" role="form" name="basic_form" valid-Form="validForm" ng-keypress="enterSearch($event)">
        <div class="panel panel-default" >
            <div class="panel-heading">
                <div class="panel-title">${r'${labels.'}${classNameFirstLower}Info${r'}'}</div>
            </div>
            <div class="panel-body">
                    <#list table.columns as column>
			        <#if column.pk>
			        
			        <#elseif  column.isDateTimeColumn>
			        <div class="row">
                        <div class="col-xs-4">
                            <label>${r'$'}{labels.${column.columnNameFirstLower} }</label>
                            <div>
                                   <div style="width: 48%; float: left;">
                                      <input type="text" class="form-control" dateselect="dateselect" dateto="${classNameFirstLower}Info.${column.columnNameFirstLower}To" name="${classNameFirstLower}Info.${column.columnNameFirstLower}From" ng-model="${classNameFirstLower}Info.${column.columnNameFirstLower}From" />
                                   </div>
                                   <div style="width: 4%; margin-top:5px;padding-left:2px; float: left;">
                                   ~
                                   </div>
                                   <div style="width: 48%; float: right;">
                                      <input type="text" class="form-control" dateselect="dateselect" datefrom="${classNameFirstLower}Info.${column.columnNameFirstLower}From" name="${classNameFirstLower}Info.${column.columnNameFirstLower}To" ng-model="${classNameFirstLower}Info.${column.columnNameFirstLower}To" />
                                   </div>
                            </div>
                        </div>
                    </div>
			        <#elseif column.isNumberColumn >
			        <div class="row">
                        <div class="col-xs-4">
                            <label>${r'$'}{labels.${column.columnNameFirstLower} }</label> 
                            <input type="text" class="form-control" name="${classNameFirstLower}Info.${column.columnNameFirstLower}" ng-model="${classNameFirstLower}Info.${column.columnNameFirstLower}" />
                        </div>
                    </div>
			        <#else >
			        <div class="row">
                        <div class="col-xs-4">
                            <label>${r'$'}{labels.${column.columnNameFirstLower} }</label> 
                            <input type="text" class="form-control" name="${classNameFirstLower}Info.${column.columnNameFirstLower}" ng-model="${classNameFirstLower}Info.${column.columnNameFirstLower}" />
                        </div>
                    </div>
			        </#if>
			        </#list>
        
                    <div class="row">
                        <div class="col-xs-12" style="text-align: right;">
                            <button type="button" class="btn btn-default btn-lg" ng-click="reset${className}()">${r'$'}{labels.reset }</button>
                            <button type="button" class="btn btn-warning btn-lg" formname="basic_form" validCallBack="search${className}()" >${r'$'}{labels.search }</button>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-xs-12" style="text-align: left;">
                             <div class="btn-group btn-group-toolbar btn-group-toolbar-divided">
                                <a class="btn" ng-click="new${className}()" ><i class="icon-left icon-add"></i>${r'${labels.add}'}</a>
                                <a class="btn" ng-click="edit${className}()" ><i class="icon-left icon-edit"></i>${r'${labels.edit}'}</a>
                                <a class="btn" ng-click="delete${className}()" ><i class="icon-left icon-delete"></i>${r'${labels.delete}'}</a>
                             </div>
                        </div>
                    </div>
                    <div class="row">
                         <div class="col-xs-12" style="text-align: left;">
                               <div ng-grid="gridOptions" class="gridStyle"></div>
                         </div>
                    </div>
                </div>
            
        </div>
        </form>
   </div>         

<%@ include file="/zbdp/common/page/footer.jsp" %>
</body>
</html>