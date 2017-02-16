app.controller('ProductCtrl', ['$scope', '$http', '$filter', 'ngTableParams', 'appService','$routeParams','$location','$rootScope','$window',function($scope, $http, $filter, ngTableParams, appService,$routeParams,$location,$rootScope,$window) {

    $(".numberOnly").ForceNumericOnly();

    $rootScope.configuraMenu('product','cadastrosBasicos');

    $scope.produto = {
        dsDescription : undefined,
        nmCategory : undefined,
        nmName : undefined,
        vlPrice : undefined
    };

    $scope.produtos = [];

    /**
     * VARIAVEIS INICIAIS
     */
    appService.sharedTitle.title = TXT.TITULO_APLICACAO + " - Produtos";
    $scope.tituloApp = appService.sharedTitle;

    if($routeParams.acao != undefined){
        $scope.acao = $routeParams.acao;
    }

    $scope.limparCamposPesquisa = function(){
        $scope.produto = {
            dsDescription : undefined,
            nmCategory : undefined,
            nmName : undefined,
            vlPrice : undefined
        };

        $scope.produtos = [];
        $scope.exibirResultado = false;
    };

    /**
     * Configuracoes da tela de _index.html
     */
    $scope.index = function(){
        $scope.ctrlTitulo = "Pesquisar Produtos";
        $scope.desabilitaExportar = true;
        $scope.exibirResultado = false;
    };

    /**
     * Configuracoes da tela de _acoes.html para novo cadastro
     */
    $scope.novo = function(){
        $scope.ctrlTitulo = "Inserir Produto";
        $scope.botaoExcluir = false;
        $scope.botaoSalvar = true;
        $scope.desabilitaFormulario = false;
    };

    /**
     * Configuracoes da tela de _acoes.html para editar cadastro
     */
    $scope.editar = function(id){
        $scope.ctrlTitulo = "Editar Produto";
        $scope.botaoExcluir = false;
        $scope.botaoSalvar = true;
        $scope.desabilitaFormulario = false;
        $scope.buscarPorId(id);
    };

    /**
     * Configuracoes da tela de _acoes.html para excluir cadastro
     */
    $scope.excluir = function(id){
        $scope.ctrlTitulo = "Excluir Produto";
        $scope.botaoExcluir = true;
        $scope.botaoSalvar = false;
        $scope.desabilitaFormulario = true;
        $scope.buscarPorId(id);
    };

    /**
     * Configuracoes da tela de _acoes.html para somente visualizacao do cadastro
     */
    $scope.visualizar = function(id){
        $scope.ctrlTitulo = "Visualizar Produto";
        $scope.botaoExcluir = false;
        $scope.botaoSalvar = false;
        $scope.desabilitaFormulario = true;
        $scope.buscarPorId(id);
    };

    $scope.pesquisar = function() {
        $scope.httpRequest({
            url: getUrlRest()+'/products/find',
            method: "POST",
            data: $scope.produto,
            delegate: function(data) {
                $scope.loading = false;
                $scope.produtos = data;
                $scope.paginacao(data);
                $scope.desabilitaExportar = false;
                $scope.exibirResultado = true;
            }, delegateError: function(data) {
                $scope.loading = false;
                $scope.produtos =[];
                $scope.desabilitaExportar = true;
                $scope.exibirResultado = false;
            }
        });
    };

    $scope.listar = function() {
        $scope.loading = true;
        $scope.httpRequest({
            url: getUrlRest()+'/products',
            method: "GET",
            delegate: function(data) {
                $scope.loading = false;
                $scope.produtos = data;
                $scope.paginacao(data);
                $scope.desabilitaExportar = false;
                $scope.exibirResultado = true;
            }, delegateError: function(data) {
                $scope.loading = false;
                $scope.produtos =[];
                $scope.desabilitaExportar = true;
                $scope.exibirResultado = false;
            }
        });
    };

    $scope.salvarOuAtualizar = function() {
        if(!$rootScope.utils.validarTodosCamposPreenchidos($scope.produto)){
            ShowMessagePattern({tipo: 'warning'}, "Favor preencher todos os campos");
            return false;
        }
        if($scope.produto.idProduct){
            $scope.atualizar();
        }else{
            $scope.salvar();
        }
    };

    $scope.salvar = function(){
        $scope.httpRequest({
            method: 'POST',
            url: getUrlRest()+'/products',
            data: $scope.produto,
            delegate: function(data) {
                ShowMessagePattern({tipo: 'success'}, MSG.SUCESSOSALVAR);
                $location.path('/product/editar/' + data.idProduct);
            }
        });
    };

    $scope.atualizar = function(){
        $scope.httpRequest({
            method: 'PUT',
            url: getUrlRest()+'/products/'+ $scope.produto.idProduct,
            data: $scope.produto,
            delegate: function(data) {
                ShowMessagePattern({tipo: 'success'}, MSG.SUCESSOATUALIZAR);
                $location.path('/product/editar/' + data.idProduct);
            }
        });
    };

    $scope.buscarPorId = function(id) {
        if(id == undefined){
            $location.path('/product');
            return false;
        }
        $scope.httpRequest({
            method: 'GET',
            url: getUrlRest()+'/products/' + id,
            delegate: function(data) {
                $scope.produto = data;
            }
        });
    };

    $scope.removerPorId = function() {
        ShowMessage({
            tipo: 'popUp',
            mensagem: 'Tem certeza que deseja excluir?',
            delegate: function() {
                $scope.httpRequest({
                    method: 'DELETE',
                    url: getUrlRest()+'/products/' + $scope.produto.idProduct,
                    delegate: function(data) {
                        $location.path('/product');
                        ShowMessagePattern({tipo: 'success'}, MSG.SUCESSOREMOVER);
                    }
                });
            },
            lblDelegate: 'Excluir',
            titulo: 'Confirme exclusão'
        });
    };


    $scope.tableParams = new ngTableParams({
        page: 1,
        count: 10 
    }, {
        total: $scope.produtos.length,
        getData: function($defer, params) {
            var orderedData = params.sorting() ? $filter('orderBy')($scope.produtos, params.orderBy()) : $scope.produtos;
            $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
        },
        $scope: {
            $data: {}
        }
    });
    $scope.paginacao = function(data) {
        $scope.tableParams.reload();
        $scope.tableParams.total(data.length);
        $scope.tableParams.reloadPages();
        $scope.tableParams.$params.page = 1;
    };

    /**
     * ROTEAMENTO DE TELA DE ACOES
     * De acordo com a url chama o formulario apra acao desejada para o include na tela _acao.html
     */
    switch($routeParams.acao) {
        case 'novo':
            $scope.novo();
            break;
        case 'visualizar':
            $scope.visualizar($routeParams.id);
            break;
        case 'editar':
            $scope.editar($routeParams.id);
            break;
        case 'excluir':
            $scope.excluir($routeParams.id);
            break;
        default:
            $location.path('/product');
        $scope.index();  
    };

}]);