//#import environment.js
//#import mensagens.js

//MODULO PRINCIPAL DO ANGULAR
var app = angular.module('App', ['ngTable','ngRoute','ngSanitize','ngBootstrap','angularMoment', 'ui.mask', 'checklist-model', 'ui.utils.masks', 'idf.br-filters','angular.chosen', 'blockUI', 'ui.bootstrap']);

/**
 * Controller principal para o modulo app
 */
app.controller('AppCtrl', [ '$scope', '$rootScope', 'appService', 'dateUtil', 'utils', function($scope, $rootScope, appService, dateUtil, utils) {
    //Titulo do site de acordo com o controller
    appService.sharedTitle.title = PROJECT_NAME.toUpperCase();
    $scope.tituloApp = appService.sharedTitle;

    $rootScope.formasDisponibilizacao = ['E-mail', 'CD/DVD', 'Download','Impresso', 'Outra Forma'];

    //Tipo e referencias do Help para acesssar o help da Aplicacao
    /*appService.sharedUrl.url = appService.makeHelpUrl(2,3);
	$scope.urlToHelp = appService.sharedUrl;
	$rootScope.urlAplicationHelp = $scope.urlToHelp.url+"&view=r";*/

    //$scope.getUsuario();
    //$scope.urlLogout = URL_LOGOUT;

    $rootScope.dateUtil = dateUtil;
    $rootScope.utils = utils;
} ]);
