<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>
<#assign classNameLowerCase = className?lower_case>
var ${classNameFirstLower}Module = angular.module("ngApp", ['zbdp.basemodule', 'ngApp.dialog']);
${classNameFirstLower}Module.controller('${classNameFirstLower}Ctrl', ['$scope', '$modal', 'zbdpService', '${classNameFirstLower}InfoFactory',
function($scope, $modal, zbdpService, ${classNameFirstLower}InfoFactory) {
    'use strict';
    
    $scope.${classNameFirstLower}Info ={};
    
    $scope.enterSearch = function(ev) {
        if (ev.keyCode == 13){
            $scope.search${className}();
        }
    };
    
    zbdpService.initPaging($scope);
    
    $scope.getPagedDataAsync = function(pageSize, page) {
        var queryObj = jQuery.extend({}, $scope.${classNameFirstLower}Info);
        var pageParam = {
        		currentPage : (page-1),
        		pageSize : pageSize
        };
        queryObj.pageParam = pageParam;
        var dto = {
        		paramStr : JSON.stringify(queryObj)
        };
        
        $scope.gridOptions.selectAll(false);
        var callback = function(resp) {
            $scope.$apply(function() {
                $scope.setPagingData(resp, 'myData');
            });
        };
        jQueryEx.post(ctx + '${classNameFirstLower}Controller/get${className}Page.do', dto, callback, 'json' );
    };
    
    $scope.gridOptions = {
        data : 'myData',
        enablePaging : true,
        showFooter : true,
        totalServerItems : 'totalServerItems',
        pagingOptions : $scope.pagingOptions,
        filterOptions : $scope.filterOptions,
        showFilter : false,
        enablePinning : false,
        enableColumnResize : true,
        enableColumnReordering : true,
        enableHighlighting : true,
        enableSorting : true,
        multiSelect : true,
        showSelectionCheckbox : true,
        i18n : LocaleUtil.toNgGridLocale(langType),
        virtualizationThreshold : 100,
        maxHeight: 620,
        columnDefs : [
        <#list table.columns as column>
        <#if column.pk>
        {
            field: '${column.columnNameFirstLower}',
            visible : false
        }
        <#elseif  column.isDateTimeColumn>
        {
            field : '${column.columnNameFirstLower}',
            displayName : Resource.getLabel('${column.columnNameFirstLower}'),
            cellTemplate : '<div class="ngCellText">{{row.entity[col.field] | date:"' + NSUI.ng_dateformat + '"}}</div>',
            width : 150
        }
        <#elseif column.isNumberColumn >
        {
            field : "${column.columnNameFirstLower}",
            displayName : Resource.getLabel('${column.columnNameFirstLower}'),
            width : 150
        }
        <#else >
        {
            field : "${column.columnNameFirstLower}",
            displayName : Resource.getLabel('${column.columnNameFirstLower}'),
            width : 150
        }
        </#if>
        <#if column_has_next>
        ,
        </#if>
        </#list>
        ]
    };
    
    $scope.reset${className} = function(){
        $scope.${classNameFirstLower}Info ={};
    };
    
    $scope.search${className} = function() {
        $scope.pagingOptions.currentPage = 1;
        $scope.getPagedDataAsync($scope.pagingOptions.pageSize, $scope.pagingOptions.currentPage);
    };
    
    zbdpService.initNgGridSelectionMethod($scope, $scope.gridOptions);
    
    $scope.new${className} = function(){
        var callback = function(paramDTO){
            var jqCallback = function(resp){
                TipUtil.show('S_saveSuccess');
                $scope.search${className}();
            }
            jQueryEx.post(ctx + '${classNameFirstLower}Controller/save${className}.do', paramDTO, jqCallback, 'json' );
        }
        ${classNameFirstLower}InfoFactory.openDialog({}, callback);
    };
    
    $scope.edit${className} = function(){
        if($scope.hasOnlyOneRowSelected()){
            var row = $scope.gridOptions.$gridScope.getSelectedRow()[0];
            var callback = function(paramDTO){
                var jqCallback = function(resp){
                    TipUtil.show('S_saveSuccess');
                    $scope.$apply(function() {
                        jQuery.extend(row, resp);
                    });
                }
                jQueryEx.post(ctx + '${classNameFirstLower}Controller/save${className}.do', paramDTO, jqCallback, 'json' );
            }
            ${classNameFirstLower}InfoFactory.openDialog(row, callback);
        }
    };
    
    $scope.delete${className} = function(){
        if($scope.hasOneRowSelected()){
            var rows = $scope.gridOptions.$gridScope.getSelectedRow();
            zbdpService.confirm(Resource.getMessage('A_sureToDelete'), function(){
                var oids = [];
                _.each(rows,function(row){
                    oids.push(row.oid);
                });
                var callback = function(resp) {
                    $scope.$apply(function() {
                        TipUtil.show('S_deleteSuccess');
                        $scope.search${className}();
                    });
                };
                var param = {
                        paramStr : oids.join(';')
                };
                jQueryEx.post(ctx + '${classNameFirstLower}Controller/delete${className}s.do', param, callback, 'json' );
            });
        }
    };
    
}]);