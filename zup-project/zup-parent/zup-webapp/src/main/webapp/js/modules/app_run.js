//#import app.js

/**
 * QUANDO INCIAR O MODULO APP EXECUTAR OS COMPONENTES INCIAIS
 */
app.run(function($rootScope, $http) {
	isIE = /*@cc_on!@*/false || !!document.documentMode;

	// Prevent the backspace key from navigating back.
	$(document).unbind('keydown').bind('keydown', function (event) {
	    var doPrevent = false;
	    if (event.keyCode === 8) {
	        var d = event.srcElement || event.target;
	        if ((d.tagName.toUpperCase() === 'INPUT' && 
	             (
	            	 d.type.toUpperCase() === 'TEXT' ||
	                 d.type.toUpperCase() === 'PASSWORD' ||
	                 d.type.toUpperCase() === 'FILE' ||
	                 d.type.toUpperCase() === 'SEARCH' ||
	                 d.type.toUpperCase() === 'EMAIL' ||
	                 d.type.toUpperCase() === 'NUMBER' ||
	                 d.type.toUpperCase() === 'DATE')
	             ) || 
	             d.tagName.toUpperCase() === 'TEXTAREA') {
	            doPrevent = d.readOnly || d.disabled;
	        }
	        else {
	            doPrevent = true;
	        }
	    }

	    if (doPrevent) {
	        event.preventDefault();
	    }
	});

    // Utilizada para armazenar a ultima pesquisa de validacao de informacoes
    $rootScope.pesquisaValidacao = undefined;

    $rootScope.permissoes = null;
    $rootScope.usuarioLogout = false;

    // carregando arquivo txt.properties
    $rootScope.TXT = TXT;

    // carregando arquivo msg.properties
    $rootScope.MSG = MSG;

	/**
	 * Método que retorna a URL completa de acesso ao servidor. 
     */
    $rootScope.server=function(url){
        return SERVER_URL + url;
    };

    /**
     * Busca relatorio de acordo com tipo solicitado e chama o servico relacionado a urlAux.
     */
    $rootScope.buscarRelatorio = function(url, urlAux) {
        if(urlAux === 'csv'){
            urlAux = 'downloadCsv';
        }else if (urlAux === 'xls'){
            urlAux = 'downloadXls';
        }else if(urlAux === 'pdf'){
            urlAux = 'downloadPdf';
        }else if(urlAux === 'txt'){
            urlAux = 'downloadTxt';
        }
        String.prototype.replaceAll = function(s,r){return this.split(s).join(r);};
        url =  ('/'+PROJECT_NAME+'/service/proxy/' + urlAux +'/' +
            (SERVER_URL+url).replaceAll('/', '*')).replaceAll(' ', '_');
        return url;
    };

    //METODO HTTP PARA EXIBIR MENSAGENS COM SHOWMESSAGE
    $rootScope.httpRequest = function(options){
        // Desabilita os botões que precisam ser bloqueados ao usuário
        $('button[ng-disabled*=bloqueioRequisicao]').attr('disabled', true);


        //FILTRO DAS URL PARA PROXY SEGURANCA
        var ULR_PROXY = '/'+PROJECT_NAME+'/service/proxy' + (options.urlProxyAux != undefined ? options.urlProxyAux : '');
        var METHOD_PROXY = "POST";
//        var RESPONSE_TYPE = options.responseType;
        var DATA_PROXY = {
            method: options.method,
            url: SERVER_URL+options.url,
            data: (options.data != undefined ? options.data : null),
            headers: (options.headers != undefined ? options.headers : null)
        };

        if(options.showMessage==undefined) options.showMessage=true;
        $http({
            url: SERVER_URL+ULR_PROXY,
            method: METHOD_PROXY,
//            responseType: RESPONSE_TYPE,
            data: DATA_PROXY
        }).success(function(data, status, headers, config) {
            // Habilita os botões que precisam ser desbloqueados ao usuário
            $('button[ng-disabled*=bloqueioRequisicao]').attr('disabled', false);
            if (options.delegate !== undefined)
                options.delegate(data, DATA_PROXY);
        }).error(function(data, status, headers, config) {
            // Habilita os botões que precisam ser desbloqueados ao usuário
            $('button[ng-disabled*=bloqueioRequisicao]').attr('disabled', false);
            if(options.showMessage)
                ShowMessage({ mensagem : data.description,  tipo:  data.status });
            if (options.delegateError !== undefined)
                options.delegateError(data, DATA_PROXY);
        });
    };


    /**
     * Esse metodo retorno objeto com os detalhes do usuario, caso esteja autenticado.
     * Se não estiver autenticado configura usuarioLogout como falso para trara permissoes de acesso as telas dentro de /view/.
     */
    $rootScope.getUsuario = function(redirect) {
        $http({
            method: 'POST',
            url: 'service/sessao/login',
            data: JSON.stringify($rootScope.usuario)
        }).success(function(data) {

            $rootScope.usuario = data;

            if ($rootScope.usuario != null) {
                redirect ? window.location = '/zup/index.html' : '';
            }
        }).error(function(data, status, headers, config) {
            // Habilita os botões que precisam ser desbloqueados ao usuário
            //$('button[ng-disabled*=bloqueioRequisicao]').attr('disabled', false);
            ShowMessage({ mensagem : data.description,  tipo:  data.status });
        });
    };

    /**
     * Esse metodo recebe uma permissao especifica e analisa se o usuario tem essa 
     * permissao e retorna um valor booleano.
     */
    $rootScope.verificaPermissoes = function(permissao) {
        var perAux = true;

        angular.forEach($rootScope.permissoes, function(value, key) {
            if(permissao == value.permissao){
                perAux = false;
            }
        });
        return perAux;
    };

    $rootScope.verificaPermissaoMenu = function(func) {
        var perAux = true;
        angular.forEach($rootScope.permissoes, function(value, key) {

            var funcionalidade = value.permissao.split("_");
            if(funcionalidade[0] == func){
                perAux = false;
            }
        });
        return perAux;
    };

    $rootScope.configuraMenu=function(id,menu){
        $('ul>li>ul>li>a').css("color", "#585858");
        $('.no-skin .nav-list>li>a').css("color", "#585858");
        $('li').removeClass('active');			//remove as li
        $('#'+id+'>a').css("color", "#2B7DBC");//remove as li
        $('#'+id).addClass('active');//adiciona qual funcionalidade está selecionada, partir do id dela
        $('#'+menu).css("color", "#2B7DBC");
    };

    /**
     * Retorna true se o usuario possuir a(s) permissao(oes) informada(s). </br>
     * Recebe uma permissao ou um array de permissoes.
     * @param permissao
     */
    $rootScope.possuiPermissao = function(permissao){
        if (permissao instanceof Array){
            for (var i = 0; i<permissao.length; i++){
                if ($rootScope.possuiPermissao(permissao[i])){
                    return true;
                }
            }
        } else {
            for (var i = 0; i<$rootScope.permissoes.length; i++){
                var permissaoAux = $rootScope.permissoes[i];

                if(permissao === permissaoAux.permissao){
                    return true;
                }
            }
        }
        return false;
    };

    $rootScope.carregaObjetoPermissoes = function () {
        $rootScope.rules = {};
        for (var i = 0; i<$rootScope.permissoes.length; i++){
            var permissaoAux = $rootScope.permissoes[i];
            $rootScope.rules[permissaoAux.permissao] = true;
        }

        $rootScope.rules.MENU_USUARIO = $rootScope.rules.ADMG||$rootScope.rules.ADML||$rootScope.rules.GERU||$rootScope.rules.DIRO;
        $rootScope.rules.MENU_ORGAO = $rootScope.rules.ADMG||$rootScope.rules.ADML;
        $rootScope.rules.ESCOLHER_ORGAO = $rootScope.rules.ADMG || $rootScope.rules.ADML || $rootScope.rules.DESV || $rootScope.rules.ENTR;
        $rootScope.rules.MENU_INFORMACAO_PRODUZIDA = $rootScope.rules.RESP;
        $rootScope.rules.MENU_INFORMACAO_DEMANDADA = $rootScope.rules.ENTR;
    };

    $rootScope.apagarPesquisaValidacaoInformacao = function(){
        $rootScope.pesquisaValidacao = undefined;
    };

    $rootScope.voltar = function(visualizando){
    	if (visualizando){
    		history.back();
    		return;
    	}

    	ShowMessage({
            tipo: 'popUp',
            mensagem: 'Deseja realmente voltar? <br> Quaisquer informações não salvas serão perdidas.',
            delegate: function() {
            	history.back();
            },
            lblDelegate: 'Voltar',
            titulo: 'Cancelar Edição'
        });
    };
    
    $rootScope.usuario = {
        'nmUsuario' : 'Felipe'
    };

});