/*
 * This js needs import others files:
 * #import app.js
 * #import utils.js
 */

 /**
 * Controller para o arquivo _foot.html
 */

//Diretiva para header incluir top html
app.controller('FootCtrl', ['$scope', function($scope) {
   $scope.TXT=TXT;
  }])
  .directive('dirFoot', function() {
    return {
      restrict: 'E',
      templateUrl: '/'+PROJECT_NAME+'/js/directives/_dir-foot.html'
    };
});