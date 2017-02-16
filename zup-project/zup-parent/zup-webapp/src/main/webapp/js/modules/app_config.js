//#import app.js

/**
 * CONFIGURACOES PARA APP 
 * Roteamento de urls
 * cache IE
 */
app.config(function($routeProvider, $httpProvider) {
    //DESABILITA O CACHE NO INTERNET EXPLORER
    if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }
    $httpProvider.defaults.headers.get['If-Modified-Since'] = '0';

    //ROTEAMENTO DE URL PARA OS PARTIALS
    var URL_PATH_PARTIALS = '/'+PROJECT_NAME+'/js/view/partials/';

    $routeProvider
    //Main
    .when('/', {
        templateUrl: URL_PATH_PARTIALS+'main/_main.html',
        controller: 'MainCtrl'
    })
    // Product
    .when('/product', {
        templateUrl: URL_PATH_PARTIALS+'product/_index.html',
        controller: 'ProductCtrl'
    })
    .when('/product/:acao/:id?', {
        templateUrl: URL_PATH_PARTIALS+'product/_acoes.html',
        controller: 'ProductCtrl'
    })
    //Outras URLS
    .otherwise({redirectTo : '/'});
});

/**
 * Configurando a diretiva de bloqueio da tela para que toda requisicao travada
 * nao exiba mensagem
 */
app.config(function(blockUIConfig) {
    blockUIConfig.message = 'carregando...';
});