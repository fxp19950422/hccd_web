/**
 * AngularHelper : Contains methods that help using angular without being in the scope of an angular controller or directive
 */
var AngularHelper = (function () {
    var AngularHelper = function () { };

    /**
     * ApplicationName : Default application name for the helper
     */
    var defaultApplicationName = "soccerpro";

    /**
         * Compile : Compile html with the rootScope of an application
         *  and replace the content of a target element with the compiled html
         * @$targetDom : The dom in which the compiled html should be placed
         * @htmlToCompile : The html to compile using angular
         * @applicationName : (Optionnal) The name of the application (use the default one if empty)
         */
    AngularHelper.Compile = function ($targetDom, htmlToCompile, applicationName) {
        var $injector = angular.injector(["ng", applicationName || defaultApplicationName]);

        $injector.invoke(["$compile", "$rootScope", function ($compile, $rootScope) {
                        //Get the scope of the target, use the rootScope if it does not exists
            $targetDom.html(htmlToCompile);
            $compile($targetDom)($rootScope);
            $rootScope.$digest();
        }]);
        $(window).scrollTop(0);
   }

    return AngularHelper;
})();

var app=angular.module('soccerpro',[]);
// custom the common panel
app.directive('saPanel', function() {
    return {
        restrict: 'E',
        transclude: true,
        scope: { title: '@title', linkText: '@linkText', callback: '&linkCallback' },
        template: 	'<div class="panel panel-default"> ' + 
						'<div class="panel-heading" style="background-color:#067DC2;color:white">' +
							'<div style="float: left;font-size:16px;color:#FFFFFF;">{{title}}</div>' +
							'<div style="float: right;"><a href="javascript:void(0);" style="color: white;" ng-click="callback()" ng-show="isShowLink">{{linkText}}</a></div>' +
							'<div class="clearfix"></div>' +
						'</div>' +
						'<div class="panel-body table-responsive" ng-transclude></div>' +
					'</div>',
        transclude: true,
        link: function($scope, element, attrs) {
        	$scope.isShowLink = ('linkText' in attrs);
        }
    };
});

app.directive('saPanelCard', function() {
    return {
        restrict: 'E',
        transclude: true,
        scope: { title: '@title', linkText: '@linkText', callback: '&linkCallback' },
        template: 	'<div class="panel panel-default"> ' + 
						'<div class="panel-heading" style="background-color:#067DC2;color:white">' +
							'<div style="float: left;font-size:16px;color:#FFFFFF;">{{title}}</div>' +
							'<div style="float: right;"><a href="javascript:void(0);" style="color: white;" ng-click="callback()" ng-show="isShowLink">{{linkText}}</a></div>' +
							'<div class="clearfix"></div>' +
						'</div>' +
						'<div class="panel-body table-responsive" style="padding: 0px 0px 15px 0px;" ng-transclude></div>' +
					'</div>',
        transclude: true,
        link: function($scope, element, attrs) {
        	$scope.isShowLink = ('linkText' in attrs);
        }
    };
});
