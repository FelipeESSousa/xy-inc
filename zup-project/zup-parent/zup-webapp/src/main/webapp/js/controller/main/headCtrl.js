/*
 * This js needs import others files:
 * #import app.js
 * #import utils.js
 */

//Diretiva para header incluir top html
app.controller('HeadCtrl', ['$scope', 'appService', '$http', '$window', '$location', function($scope, appService, $http, $window, $location) {

    //Titulo do site de acordo com o controller
    appService.sharedTitle.title = PROJECT_NAME.toUpperCase();
    $scope.tituloApp =appService.sharedTitle.title;
    $scope.TXT=TXT;

    $scope.deslogar = function() {
        $http({
            method: 'POST',
            url: 'service/sessao/logout'
        }).success(function(data) {
            $window.location = '/zup/login.html';
        }).error(function(data, status, headers, config) {

        });
    };
    
    $scope.redefinirSenha = function(){
        $location.url('/redefinirSenha');
    }

}])
.directive('dirHead', function() {
    return {
        restrict: 'E',
        templateUrl: '/'+PROJECT_NAME+'/js/directives/_dir-head.html'
    };
});