<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first>
<#assign classNameLowerCase = className?lower_case>
var dialogModule = angular.module('ngApp.dialog', ['ui.bootstrap']);

dialogModule.controller('${classNameFirstLower}DialogCtrl', ['$scope', '$modalInstance', '${classNameFirstLower}Info',
function($scope, $modalInstance, ${classNameFirstLower}Info) {
    ${classNameFirstLower}Info = ${classNameFirstLower}Info || {};
    
    var default${className}Info = {};
    
    $scope.${classNameFirstLower}Info = jQuery.extend({}, default${className}Info, ${classNameFirstLower}Info);
    
    $scope.reset${className} = function(){
        $scope.${classNameFirstLower}Info = {};
    };
    $scope.ok = function() {
        $modalInstance.close($scope.${classNameFirstLower}Info);
    };
    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };
}]);

dialogModule.factory('${classNameFirstLower}InfoFactory', ['$modal',
function($modal) {
    return {
        openDialog : function(${classNameFirstLower}InfoConfig, callback) {
            return $modal.open({
                templateUrl : ctx + '${namespace_dir}/tpl/${classNameFirstLower}-dialog.html',
                controller : '${classNameFirstLower}DialogCtrl',
                backdrop : 'static',
                keyboard : false,
                backdropClass : 'modal-backdrop fade in',
                size : 'lg',
                resolve : {
                    ${classNameFirstLower}Info : function() {
                        return ${classNameFirstLower}InfoConfig;
                    }
                }
            }).result.then(function(${classNameFirstLower}Info) {//ok
                    var ${classNameFirstLower}DTO = jQuery.extend({}, ${classNameFirstLower}Info);
                    var paramDTO = {
                            paramStr : JSON.stringify(${classNameFirstLower}DTO)
                    };
                    callback(paramDTO);
            }, function() {//cancel
               
            });
        }
    };
}]);