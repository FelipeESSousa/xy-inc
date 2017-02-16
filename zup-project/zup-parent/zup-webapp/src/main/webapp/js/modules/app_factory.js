//#import app.js

/**
 * Servico para alterar parametros da url do Help de acordo com o controller
 * ativo eo titulo do site
 */
app.factory("appService", function() {
    return {
        /**
         * @param tipo
         * @param referencia
         * @return String
         */
        makeHelpUrl : function(tipo, referencia) {
            return URL_HTTP_HELP + '/view/help.html?tipo=' + tipo + '&referencia=' + referencia;
        },
        sharedUrl : {
            url : URL_HTTP_HELP + '/view/help.html?tipo=2&referencia=1'
        },
        sharedTitle : {
            title : PROJECT_NAME.toUpperCase()
        }
    };
});

/**
 * Servico para salvar o ultimo filtro pesquisado e de qual tela eh o filtro
 */
app.factory('voltarService', function() {
    var nomeTela = '';
    var filtro = undefined;

    /**
     * @param data
     */
    function setTela(data) {
        nomeTela = data;
    }
    /**
     * @return nomeTela
     */
    function getTela() {
        return nomeTela;
    }
    /**
     * @param data
     */
    function setFiltro(data) {
        filtro = data;
    }
    /**
     * @return filtro
     */
    function getFiltro() {
        return filtro;
    }
    return {
        setTela : setTela,
        getTela : getTela,
        setFiltro : setFiltro,
        getFiltro : getFiltro
    };
});

/**
 * Factory lista
 */
app.factory('lista', function($rootScope) {

    /**
     * @param serviceName
     * @param dataParams
     * @param showMessage
     * @param callback
     * @param callbackError
     */
    function pesquisarCustomizado(serviceName, dataParams, showMessage, callback, callbackError) {
        $rootScope.httpRequest({
            url : getUrlRest() + '/' + serviceName,
            headers : {
                'Perfil' : $rootScope.perfil,
                'IdUnidade' : $rootScope.perfil,
                'Authorization' : $rootScope.nomeUsuario.login
            },
            method : "POST",
            data : dataParams,
            showMessage : showMessage,
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }

    /**
     * @param serviceName
     * @param dataParams
     * @param showMessage
     * @param callback
     * @param callbackError
     */
    function pesquisarGenerico(serviceName, dataParams, showMessage, callback, callbackError) {
        $rootScope.httpRequest({
            url : getUrlRest() + '/' + serviceName + '/pesquisar',
            headers : {
                'Perfil' : $rootScope.perfil,
                'IdUnidade' : $rootScope.perfil
            },
            method : "POST",
            data : dataParams,
            showMessage : showMessage,
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }

    /**
     * @param serviceName
     * @param dataParams
     * @param callback
     * @param callbackError
     */
    function pesquisar(serviceName, dataParams, callback, callbackError) {
        pesquisarGenerico(serviceName, dataParams, true, callback, callbackError);
    }

    /**
     * @param serviceName
     * @param id
     * @param showMessage
     * @param callback
     * @param callbackError
     * @param auth
     */
    function buscarPorIdGenerico(serviceName, id, showMessage, callback, callbackError, auth) {
        $rootScope.httpRequest({
            method : 'GET',
            url : getUrlRest() + '/' + serviceName + '/' + id,
            authentication: auth,
            showMessage : showMessage,
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }

    /**
     * @param serviceName
     * @param id
     * @param callback
     * @param callbackError
     * @param authentication
     */
    function buscarPorId(serviceName, id, callback, callbackError, authentication) {
        var auth;
        if (typeof authentication === 'undefined')
            auth = true;
        else
            auth = authentication;
        buscarPorIdGenerico(serviceName, id, true, callback, callbackError, auth);
    }

    /**
     * @param serviceName
     * @param obj
     * @param callback
     * @param callbackError
     * @param authentication
     */
    function salvarOuAtualizar(serviceName, obj, callback, callbackError, authentication) {
        var auth;
        if (typeof authentication === 'undefined')
            auth = true;
        else
            auth = authentication;
        $rootScope.httpRequest({
            method : 'POST',
            url : getUrlRest() + '/' + serviceName,
            data : obj,
            authentication : auth,
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }
    ;

    /**
     * @param serviceName
     * @param obj
     * @param callback
     * @param callbackError
     */
    function salvarOuAtualizarSemMensagem(serviceName, obj, callback, callbackError) {
        $rootScope.httpRequest({
            method : 'POST',
            url : getUrlRest() + '/' + serviceName,
            showMessage : false,
            data : obj,
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }
    ;

    /**
     * @param serviceName
     * @param id
     * @param showMessage
     * @param callback
     * @param callbackError
     */
    function removerPorId(serviceName, id, showMessage, callback, callbackError) {
        $rootScope.httpRequest({
            method : 'POST',
            url : getUrlRest() + '/' + serviceName + '/' + id,
            showMessage : showMessage,
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }

    /**
     * @param serviceName
     * @param callback
     * @param callbackError
     */
    function listar(serviceName, callback, callbackError) {
        $rootScope.httpRequest({
            method : 'GET',
            url : getUrlRest() + '/' + serviceName,
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }

    /**
     * @param serviceName
     * @param id
     * @param callback
     * @param callbackError
     */
    function buscarPorIdSemMensagem(serviceName, id, callback, callbackError) {
        $rootScope.httpRequest({
            method : 'GET',
            url : getUrlRest() + '/' + serviceName + '/' + id,
            showMessage : false,
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }

    return {
        pesquisar : pesquisar,
        pesquisarGenerico : pesquisarGenerico,
        buscarPorId : buscarPorId,
        salvarOuAtualizar : salvarOuAtualizar,
        salvarOuAtualizarSemMensagem : salvarOuAtualizarSemMensagem,
        removerPorId : removerPorId,
        listar : listar,
        buscarPorIdSemMensagem : buscarPorIdSemMensagem,
        buscarPorIdGenerico : buscarPorIdGenerico,
        pesquisarCustomizado : pesquisarCustomizado

    };
});

/**
 * Factory relatorio
 */
app.factory('relatorio', function($rootScope, blockUI, $timeout, $window) {
    function gerarXLS(serviceName, params, callback) {
        $rootScope.httpRequest({
            url : getUrlRest() + '/' + serviceName + '/gerarXLS',
            method : "POST",
            data : params,
            delegate : function(data) {
                var url = $rootScope.buscarRelatorio(getUrlRest() + '/relatorios/buscarRelatorioXls/' + data, DOWNLOAD_TYPE_XLS);
                callback(url);
            }
        });
    }
    ;

    /**
     * @param serviceName
     * @param params
     * @param callback
     */
    function gerarCSV(serviceName, params, callback) {
        $rootScope.httpRequest({
            url : getUrlRest() + '/' + serviceName + '/gerarCSV',
            method : "POST",
            data : params,
            delegate : function(data) {
                var url = $rootScope.buscarRelatorio(getUrlRest() + '/relatorios/buscarRelatorioCsv/' + data, DOWNLOAD_TYPE_CSV);
                callback(url);
            }
        });
    };

    /**
     * @param serviceName
     * @param params
     */
    function gerarPDF(serviceName, params) {
        $rootScope.httpRequest({
            url : getUrlRest() + '/' + serviceName + '/gerarPDF',
            method : "POST",
            data : params,
            delegate : function(data) {
                if (!!window.chrome) {
                    $window.open($rootScope.buscarRelatorio(getUrlRest() + '/relatorios/buscarRelatorioPdf/' + data, DOWNLOAD_TYPE_PDF)).print();
                }else{
                    $window.open($rootScope.buscarRelatorio(getUrlRest() + '/relatorios/buscarRelatorioPdf/' + data, DOWNLOAD_TYPE_PDF));
                }
            }
        });
    };

    /**
     * @param serviceName
     * @param params
     */
    function imprimir(serviceName, params) {
        $rootScope.httpRequest({
            url : getUrlRest() + '/' + serviceName + '/gerarPDF',
            method : "POST",
            data : params,
            delegate : function(data) {
                if (!!window.chrome) {
                    $window.open($rootScope.buscarRelatorio(getUrlRest() + '/relatorios/buscarRelatorioPdfInline/' + data, DOWNLOAD_TYPE_PDF)).print();
                }else{
                    $window.open($rootScope.buscarRelatorio(getUrlRest() + '/relatorios/buscarRelatorioPdfInline/' + data, DOWNLOAD_TYPE_PDF));
                }
            }
        });
    };

    /**
     * @param serviceName
     * @param params
     * @param callback
     * callbackError
     */
    function gerarRelatorio(serviceName, params, callback, callbackError) {
        $rootScope.httpRequest({
            url : getUrlRest() + '/' + serviceName,
            method : "POST",
            data : params,
            headers: {"usuario":$rootScope.nomeUsuario.login},
            delegate : function(data) {
                $window.open($rootScope.buscarRelatorio(getUrlRest() + '/relatorios/buscarRelatorioPdf/' + data, DOWNLOAD_TYPE_PDF));
                callback(data);
            },
            delegateError : function(dataError) {
                callbackError(dataError);
            }
        });
    }
    ;

    return {
        gerarXLS : gerarXLS,
        gerarCSV : gerarCSV,
        gerarPDF : gerarPDF,
        imprimir : imprimir,
        gerarRelatorio : gerarRelatorio

    };
});

/**
 * Factory UTILS
 *
 * Contem metodos genericos que sao responsaveis por compartamento de tela
 */
app.factory('utils', function() {
    /**
     * SET COMPRIMENTO BOTOES
     *
     * Metodo responsavel por setar a largura dos botoes de determinada classe
     * seguindo a orientacao do maior botao
     */
    function setComprimentoBotoes(classe) {
        var elementWidths = $('.' + classe).map(function() {
            return $('.' + classe).width();
        }).get();

        var maxWidth = Math.max.apply(null, elementWidths);

        $('.' + classe).width(maxWidth);
    };

    /**
     * mergeJsonList
     * Metodo criado para merge automatico entre listas com um atributo igual.
     */
    function mergeJsonList(listaDestino, atributoComparacaoDestino, atributoDestino, listaOrigem, atributoComparacaoOrigem, atributoOrigem) {
        for (var i = 0; i < listaOrigem.length; i++) {
            for (var x = 0; x < listaDestino.length; x++) {
                if (listaOrigem[i][atributoComparacaoOrigem] === listaDestino[x][atributoComparacaoDestino]) {
                    listaDestino[x][atributoDestino] = listaOrigem[i][atributoOrigem];
                    $.extend(true,  listaDestino[x][atributoDestino], listaOrigem[i][atributoOrigem]);
                }
            }
        }
    };

    /**
     * @param email
     * @return boolean
     */
    function validarEmail(email) {
        if (email !== undefined){
            var usuario = email.substring(0, email.indexOf("@"));
            var dominio = email.substring(email.indexOf("@") + 1, email.length);
            if ((usuario.length >= 1) && (dominio.length >= 3) && (usuario.search("@") === -1) && (dominio.search("@") === -1)
                && (usuario.search(" ") === -1) && (dominio.search(" ") === -1) && (dominio.search(".") !== -1) && (dominio.indexOf(".") >= 1)
                && (dominio.lastIndexOf(".") < dominio.length - 1))
                return true;
        }
        return false;
    };

    /**
     * @param property
     * @return boolean
     */
    function propertyIsWrong(property) {
        if ((property === null || property === undefined || property === "")) {
            return true;
        }
        return false;
    };

    /**
     * @param st
     * @return boolean
     */
    function verificarStringVazia(st) {
        if (st === undefined || st === null || st === "") {
            return true;
        }
        return false;
    };

    /**
     * VERIFICA SE UM OBJETO É VAZIO PERCORRENDO TODAS AS PROPRIEDADES
     */
    function isEmpty(obj) {
        for ( var prop in obj) {
            if (obj.hasOwnProperty(prop)) {
                return false;
            }
        }
        return true;
    };

    /**
     * VERIFICA SE UM OBJETO É VAZIO
     */
    function verificarObjetoVazio(obj) {
        if (obj === undefined || obj === null || isEmpty(obj)) {
            return true;
        }
        return false;
    };

    function isString(field){
        if(field !== undefined && typeof field === 'string'){
            return true;
        }
        return false;
    }

    function ehMaiorQueTres(objetoValidacao) {
        for( var i = 0; i < Object.keys(objetoValidacao).length; i++){
            if (isString(objetoValidacao[Object.keys(objetoValidacao)[i]])
                && objetoValidacao[Object.keys(objetoValidacao)[i]].length > 0
                && objetoValidacao[Object.keys(objetoValidacao)[i]].length < 3) {
                return false;
            }
        }
        return true;
    };
    
    function validarTodosCamposPreenchidos(objetoValidacao) {
        for( var i = 0; i < Object.keys(objetoValidacao).length; i++){
            if (!objetoValidacao[Object.keys(objetoValidacao)[i]]) {
                return false;
            }
        }
        return true;
    };

    return {
        setComprimentoBotoes : setComprimentoBotoes,
        mergeJsonList : mergeJsonList,
        validarEmail : validarEmail,
        propertyIsWrong: propertyIsWrong,
        verificarStringVazia :verificarStringVazia,
        isEmpty :isEmpty,
        verificarObjetoVazio :verificarObjetoVazio,
        isString: isString,
        ehMaiorQueTres: ehMaiorQueTres,
        validarTodosCamposPreenchidos: validarTodosCamposPreenchidos
    };

});

/**
 *
 */
app.factory('dateUtil', function($filter) {

    /**
     * Metodo recebe dois parametros e retorna Data no formato String: 1
     * parametro - Objeto Date; 2 parametro - Padrão da data que será retornado.
     *
     * @param Date
     *            objDate
     * @param String
     *            pattern
     * @return String
     */
    function dateToString(objDate, pattern) {
        return $filter('date')(objDate, pattern);
    };

    /**
     * Metodo recebe dois parametros e retorna objeto Date: 1 parametro - Data
     * no formato String; 2 parametro - Padrão da data recebida.
     *
     * @param String
     *            strDate
     * @param String
     *            pattern
     * @return Date
     */
    function stringToDate(strDate, pattern) {
        return getDateFromFormat(strDate, pattern);
    };

    /**
     * Metodo recebe três parametros e retorna objeto Date com os dias
     * acrescentados: 1 parametro -Data no formato String; 2 parametro -Padrão
     * da data recebida; 3 parametro -Número de dias que será adicionado.
     *
     * @param String
     *            strDate
     * @param String
     *            pattern
     * @param int
     *            numberDays
     * @return Date
     */
    function addDaysStrDate(strDate, pattern, numberDays) {
        return new Date(new Date().setDate(new Date(getDateFromFormat(strDate, pattern)).getDate() + numberDays));
    };

    /**
     * Metodo recebe dois parametros e retorna objeto Date com os dias
     * acrescentados: 1 parametro -Objeto Date; 2 parametro -Número de dias que
     * será adicionado.
     *
     * @param Date
     *            objDate
     * @param int
     *            numberDays
     * @return Date
     */
    function addDaysObjDate(objDate, numberDays) {
        return new Date(new Date().setDate(objDate.getDate() + numberDays));
    };

    /**
     * Verifica se o intervalo das datas e menor que 12 meses
     */
    function intervaloMenor12Meses(dataInicio, dataFim){
        var dhInicio = new Date(dataInicio);
        var dhFim = new Date(dataFim);
        var intervaloAnos = dhFim.getUTCFullYear() - dhInicio.getUTCFullYear();
        if (intervaloAnos === 0) {
            return true;
        }
        if (intervaloAnos > 1){
            return false;
        }
        if(dhFim.getMonth() < dhInicio.getMonth()){
            return true;
        }
        if(dhFim.getMonth() > dhInicio.getMonth()) {
            return false;
        }
        if(dhFim.getDate() > dhInicio.getDate()) {
            return false;
        }
        return true;
    };

    return {
        dateToString : dateToString,
        stringToDate : stringToDate,
        addDaysStrDate : addDaysStrDate,
        addDaysObjDate : addDaysObjDate,
        intervaloMenor12Meses: intervaloMenor12Meses
    };

});


/**
 * Metodos relacionados a manutencao de arquivos fisicos e logicos vinculados ao
 * suite corporativa
 */
app.factory('file', function($rootScope, $http) {

    /**
     * verifica se o usuario esta logado
     *
     */
    function verificarUsuarioLogado(callback) {
        $http.get('/' + PROJECT_NAME + '/service/context-spring/usuario').success(function(data) {
            if (data.login == null) {
                window.location = URL_LOGOUT;
                return false;
            }
            callback();
        }).error(function() {
            window.location = URL_LOGOUT;
            return false;
        });

    }

    /**
     * uploadGenerico Metodo de upload generico pelo qual o arquivo e passado
     * para a suite manter o mesmo file: Arquivo fisico. idAplicacao: id da
     * aplicacao que e responsavel pelo o arquivo. idTipoDiretorio: Tipo do
     * diretorio que o mesmo pertence. Ex: Anexos, Documentos, etc.
     * descricaoArquivo: Comentario sobre o arquivo. callback: Tratativa de
     * sucesso. callbackError: Tratavida de erro.
     */
    function uploadGenerico(file, idAplicacao, idTipoDiretorio, descricaoArquivo, callback, callbackError) {
        verificarUsuarioLogado(function() {
            var fd = new FormData();
            var nomeArquivoEncript = btoa(file.name);
            fd.append("file", file);
            fd.append("idAplicacao", idAplicacao);
            fd.append("idTipoDiretorio", idTipoDiretorio);
            fd.append("descricaoArquivo", descricaoArquivo);
            fd.append("fileName", nomeArquivoEncript);
            fd.append("url", URL_SUITE_CORP + '/arquivos' + '/upload');

            $http.post('/' + PROJECT_NAME + '/service/proxy/uploadSuite', fd, {
                withCredentials : true,
                headers : {
                    'Content-Type' : undefined,
                    'filename' : nomeArquivoEncript,
                    'Authorization' : $rootScope.nomeUsuario.login
                },
                transformRequest : angular.identity
            }).success(function(data) {
                callback(data);
            }).error(function(data) {
                callbackError(data);
            });
        });
    }
    ;

    /**
     * uploadAnexo Metodo responsavel pelo upload dos arquivos do tipo anexo
     * para o projeto sigesa file: Arquivo fisico. callback: Tratativa de
     * sucesso. callbackError: Tratavida de erro.
     */
    function uploadAnexo(file, callback, callbackError) {
        uploadGenerico(file, ID_APLICACAO, ID_TIPO_DIRETORIO_ANEXO, "Arquivo do projeto SGF", function(data) {
            callback(data);
        }, function(data) {
            callbackError(data);
        });
    }

    /**
     * downloadGenerico Metodo generico responsavel pelo download pelo caminho
     * do arquivo na suite corporativa arquivo: Arquivo logico. idAplicacao: id
     * da aplicacao que e responsavel pelo o arquivo. idTipoDiretorio: Tipo do
     * diretorio que o mesmo pertence. Ex: Anexos, Documentos, etc.
     * callbackError: Tratavida de erro.
     */
    function downloadGenerico(arquivo, idAplicacao, idTipoDiretorio, callbackError) {
        $rootScope.httpRequest({
            urlFull : URL_SUITE_CORP + '/arquivos/tipoDiretorio/' + idTipoDiretorio + '/aplicacao/' + idAplicacao,
            method : "GET",
            delegate : function(data) {
                var diretorio = data;
                var caminho = diretorio.nmEnderecoServidor.replace(/\\/gi, '\\\\') + '\\\\' + arquivo.nmArquivoFisico;
                var caminhoEncript = btoa(caminho);
                window.location = URL_SUITE_CORP + '/arquivos/download/' + caminhoEncript;
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }

    /**
     * downloadAnexoSIGESA Metodo responsavel pelo download de arquivos que sao
     * do tipo de diretorio anexo do projeto SIGESA arquivo: Arquivo logico.
     * callbackError: Tratavida de erro.
     */
    function downloadAnexo(arquivo, callbackError) {
        downloadGenerico(arquivo, ID_APLICACAO, ID_TIPO_DIRETORIO_ANEXO, function(data) {
            callbackError(data);
        });
    }

    /**
     * downloadPorId Metodo responsavel por fazer o download do arquivo fisico
     * pelo o id do arquivo logico, independente do projeto. id: Id do arquivo
     * logico
     */
    function downloadPorId(id) {
        window.location = URL_SUITE_CORP + '/arquivos/downloadById/' + id;
    }

    /**
     * pegarArquivoPorId Metodo responsavel por retornar as informacoes
     * completas do arquivo logico pelo id, independente do projeto.
     */
    function pegarArquivoPorId(id, callback, callbackError) {
        $rootScope.httpRequest({
            urlFull : URL_SUITE_CORP + '/arquivos/' + id,
            method : "GET",
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }

    /**
     * pesquisarArquivo Metodo responsavel por retornar uma lista de arquivos
     * que atendem ao arquivo passado que sera utilizado de filtro de busca
     */
    function pesquisarArquivo(arquivo, callback, callbackError) {
        $rootScope.httpRequest({
            urlFull : URL_SUITE_CORP + '/arquivos/pesquisar',
            method : "POST",
            data : arquivo,
            showMessage : false,
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }

    /**
     * excluirArquivoPorId Metodo responsavel por excluir o arquivo fisicamente
     * e/ou logicamente (dependendo do projeto) pelo id passado.
     */
    function excluirArquivoPorId(id, callback, callbackError) {
        $rootScope.httpRequest({
            urlFull : URL_SUITE_CORP + '/arquivos/excluir/' + id,
            method : "GET",
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }

    /**
     * Metodo responsavel por validar a extensao do arquivo Recebe 2 parametros:
     * file: Eh o arquivo fisico extensoesPermitidas: Eh uma array de strings
     * com os formatos permitidos Ex: pdf,xls
     */
    function validarExtensao(file, extensoesPermitidas) {
        var fileExtension = file.name.split('.')[file.name.split('.').length - 1];
        var usandoFormatoPermitido = false;
        for (var y = 0; y < extensoesPermitidas.length; y++) {
            if (extensoesPermitidas[y].toUpperCase() === fileExtension.toUpperCase()) {
                usandoFormatoPermitido = true;
            }
        }
        return usandoFormatoPermitido;
    }
    ;

    return {
        uploadAnexo : uploadAnexo,
        downloadAnexo : downloadAnexo,
        pegarArquivoPorId : pegarArquivoPorId,
        validarExtensao : validarExtensao,
        pesquisarArquivo : pesquisarArquivo,
        excluirArquivoPorId : excluirArquivoPorId,
        downloadPorId : downloadPorId
    };

});

/**
 * Metodos relacionados a usuarios
 */
app.factory('usuario', function($rootScope) {
    /**
     * Lista usuarios a partir de um perfil
     *
     * @param perfil
     * @param callback
     * @param callbackError
     */
    function listarUsuariosPorPerfil(perfil, callback, callbackError) {
        $rootScope.httpRequest({
            method : 'POST',
            urlFull : getUrlScaRest() + '/usuarios/perfil',
            data : perfil,
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }
    ;

    /**
     * Lista usuarios a partir de um perfil e uma unidade
     *
     * @param perfil
     * @param idUnidade
     * @param callback
     * @param callbackError
     */
    function listarUsuariosPorPerfilUnidade(perfil, idUnidade, callback, callbackError) {
        $rootScope.httpRequest({
            method : 'POST',
            urlFull : getUrlScaRest() + '/usuarios/perfilUnidade/' + idUnidade,
            data : perfil,
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }
    ;

    /**
     * Lista as unidades de um perfil
     *
     * @param perfil
     * @param callback
     * @param callbackError
     */
    function listarUnidadesPorPerfil(perfil, callback, callbackError) {
        $rootScope.httpRequest({
            method : 'POST',
            urlFull : getUrlScaRest() + '/unidades/perfil',
            data : perfil,
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }
    ;

    /**
     * Busca o perfil de um usuario
     *
     * @param login
     * @param callback
     * @param callbackError
     */
    function buscarPerfilDoUsuario(login, callback, callbackError) {
        $rootScope.httpRequest({
            method : 'GET',
            urlFull : getUrlScaRest() + '/perfis/usuario/login/' + login + '/aplicacao/sigla/sgf',
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    }
    ;

    /**
     * Busca o usuario pelo login
     *
     * @param login
     * @param callback
     * @param callbackError
     */
    function buscarUsuarioPorLogin(login, callback, callbackError) {
        $rootScope.httpRequest({
            urlFull : getUrlScaRest() + '/usuarios/login/' + login,
            method : "GET",
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data){
                callbackError(data);
            }
        });
    };

    /**
     * Busca o pessoa pelo login
     *
     * @param id
     * @param callback
     * @param callbackError
     * @param authentication
     */
    function buscarPessoaPorId (id, callback, callbackError, authentication){
        var auth;
        if (typeof authentication === 'undefined')
            auth = true;
        else
            auth = authentication;
        $rootScope.httpRequest({
            method : 'GET',
            urlFull : getUrlSuiteRest() + '/pessoas/' + id,
            authentication : auth,
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    };

    return {
        listarUsuariosPorPerfil : listarUsuariosPorPerfil,
        listarUsuariosPorPerfilUnidade : listarUsuariosPorPerfilUnidade,
        listarUnidadesPorPerfil : listarUnidadesPorPerfil,
        buscarPerfilDoUsuario : buscarPerfilDoUsuario,
        buscarUsuarioPorLogin : buscarUsuarioPorLogin,
        buscarPessoaPorId: buscarPessoaPorId
    };

});

/**
 * Metodos relacionados a localidade e uf
 */
app.factory('localidade', function($rootScope) {

    /**
     * Listar UFS
     *
     * @param callback
     * @param callbackError
     */
    function listarUfs(callback, callbackError) {
        $rootScope.httpRequest({
            method : 'GET',
            urlFull : getUrlSuiteRest() + '/ufs',
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    };

    /**
     * Lista cidades de um estado
     *
     * @param uf
     * @param callback
     * @param callbackError
     */
    function listarLocalidadesPorUf(uf, callback, callbackError) {
        $rootScope.httpRequest({
            method : 'POST',
            data : uf,
            urlFull : getUrlSuiteRest() + '/localidades/listarPorUf',
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    };

    /**
     * Lista cidades dos ids
     *
     * @param ids
     * @param callback
     * @param callbackError
     */
    function listarLocalidadesPorIds(ids, callback, callbackError) {
        $rootScope.httpRequest({
            method : 'POST',
            data : ids,
            urlFull : getUrlSuiteRest() + '/localidades/listar',
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    };

    /**
     * Busca Localidade pelo id
     *
     * @param idLocalidade
     * @param callback
     * @param callbackError
     * @param authentication
     */
    function buscarLocalidadePorId(idLocalidade, callback, callbackError, authentication) {
        var auth;
        if (typeof authentication === 'undefined')
            auth = true;
        else
            auth = authentication;
        $rootScope.httpRequest({
            method : 'GET',
            urlFull : getUrlSuiteRest() + '/localidades/' + idLocalidade,
            authentication : auth,
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    };

    /**
     * Busca Localidade pelo id
     *
     * @param idUf
     * @param callback
     * @param callbackError
     */
    function buscarUfPorId(idUf, callback, callbackError) {
        $rootScope.httpRequest({
            method : 'GET',
            urlFull : getUrlSuiteRest() + '/ufs/' + idUf,
            delegate : function(data) {
                callback(data);
            },
            delegateError : function(data) {
                callbackError(data);
            }
        });
    };

    return {
        listarLocalidadesPorUf : listarLocalidadesPorUf,
        buscarLocalidadePorId : buscarLocalidadePorId,
        listarLocalidadesPorIds : listarLocalidadesPorIds,
        buscarUfPorId : buscarUfPorId,
        listarUfs : listarUfs
    };

});
