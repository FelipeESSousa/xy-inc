/**
 * CONTROLLER DA TELA < _main.html >
 */
app.controller('MainCtrl', ['$scope', '$http', 'appService',
                            function($scope, $http, appService) {
    $('ul>li>ul>li>a').css("color", "#585858");	
    $('.no-skin .nav-list>li>a').css("color", "#585858");	
    $('li').removeClass('active'); 

    /**
     * VARIAVEIS INICIAIS
     */
    //Titulo do site de acordo com o controller
    appService.sharedTitle.title = TXT.TITULO_APLICACAO;
    $scope.tituloApp = appService.sharedTitle;

    //Tipo e referencia do Help para acesssar o help do Help por Etiqueta
    appService.sharedUrl.url = appService.makeHelpUrl(2,3);
    $scope.urlToHelp = appService.sharedUrl;

}]);
