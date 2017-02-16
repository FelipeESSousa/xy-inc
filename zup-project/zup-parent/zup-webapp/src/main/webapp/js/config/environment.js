var PROJECT_CONFIGS;

//endereco da aplicacao REST
var PROTOCOL;
var IP;
var PORT;

//endereco sca para logout da aplicacao
var URL_LOGOUT;

//endereco do servidor onde se encotra o projeto
var SERVER_URL;
//endereco da url de servico para o projeto webapp
var URL_WEB_SERVICE;
var PROJECT_REST;
//endereco da url de servico para o projeto restClient
var URL_REST_SERVICE;
//endereco help online
var URL_HTTP_HELP;
//Obtém o valor do select da tela _organograma.html
var PARENT_ORGANOGRAMA = 0;
//lista de chaves e valores de textos e mensagens nos properties
var TXT = [];
var MSG = [];

//Varaveis de tipo de arquivos para arquivos exportados
var DOWNLOAD_TYPE_CSV = 'csv';
var DOWNLOAD_TYPE_XLS = 'xls';
var DOWNLOAD_TYPE_PDF = 'pdf';
var DOWNLOAD_TYPE_TXT = 'txt';

//Nome do host na url para o projeto web
//if(window.location.protocol != 'https:') {
//		location.href = location.href.replace("http://", "https://");
//}

var host = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port: '');

/**
 * Descricao: Metodo para buscar configuracoes do properties, localizado no diretorio conf/interacao/suitecorp/ dentro do jboss.
 */
function getConfigs(){
	
	//Desabilita o cache do JQuery
	$.ajaxSetup({ cache: false });
	
	$.ajax({
        type: "GET",
        dataType : "json",
        contentType : "application/json",
        async: false,
        timeout: (50 * 1000),
        url: "service/externos/load/config",
        success: function(data) {
        	if(data== null || data==undefined){
    	
    			//seta endereco local REST suitecorp
    			setConfigsDefault();
    			return
	    	}
		
        	PROJECT_CONFIGS = data;
			PROTOCOL 		= PROJECT_CONFIGS.PROTOCOL_REST;
			IP 				= PROJECT_CONFIGS.IP_REST;
			PORT 			= PROJECT_CONFIGS.PORT_REST;
			URL_LOGOUT 		= PROJECT_CONFIGS.URL_LOGOUT;
			URL_HTTP_HELP 	= PROJECT_CONFIGS.URL_HTTP_HELP;
			PROJECT_NAME 	= PROJECT_CONFIGS.PROJECT_NAME;
			PROJECT_REST	= PROJECT_CONFIGS.PROJECT_REST;
			UPLOAD_PATH     = PROJECT_CONFIGS['UPLOAD.PATH'];
			SERVER_URL 		= getURI();
        },
        error: function( objAJAXRequest, strError ){
        }
    });
}

getConfigs();


/**
 * Descricao: Metodo para buscar mensagens do properties, localizado no diretorio conf/interacao/suitecorp/properties/msg.properties dentro do jboss.
 */
function getTxtProperties(){
	$.ajax({
		type: "GET",
        dataType : "json",
        contentType : "application/json",
        async: false,
        timeout: (50 * 1000),
        url: "service/externos/load/txt",
        success: function(data) {
        	if(data== null || data==undefined){
    			return
	    	}
			TXT = data;
        },
        error: function( objAJAXRequest, strError ){
    
        }
    });
};

getTxtProperties();

/**
 * Descricao: Metodo para buscar mensagens do properties, localizado no diretorio conf/interacao/suitecorp/properties/msg.properties dentro do jboss.
 */
function getMsgs(){
	$.ajax({
		type: "GET",
        dataType : "json",
        contentType : "application/json",
        async: false,
        timeout: (50 * 1000),
        url: "service/externos/load/msg",
        success: function(data) {
        	if(data== null || data==undefined){
    		
    			return
	    	}
        	
			MSG = data;
        },
        error: function( objAJAXRequest, strError ){

        }
    });
};
getMsgs();


/**
 * Busca url para os servicos no projeto de webapp
 */
function getUrlWeb(){
	URL_WEB_SERVICE = '/'+PROJECT_NAME +'/service';
	return URL_WEB_SERVICE;
}

/**
 * Busca url para os servicos no projeto de restClient
 */
function getUrlRest(){
	URL_REST_SERVICE = '/'+PROJECT_REST +'/service';
	return URL_REST_SERVICE;
}

/**
 * Function to get the certainly url
 */ 
function getURI() {
	var fullURL = null;
	if(PROTOCOL == "https" && PORT == 443){
		fullURL = PROTOCOL+"://"+IP;
	} else if(PROTOCOL == "https" && PORT == 8443){
		fullURL = PROTOCOL+"://"+IP+":"+PORT;
	} else if(PROTOCOL == "ftp"){
		fullURL = PROTOCOL+"://"+IP;
	} else if(PROTOCOL == "ssh"){
		fullURL = USER+"@"+IP;
	} else if((PORT == "8080" || PORT == "9080") && PROTOCOL == "http"){
		fullURL = PROTOCOL+"://"+IP+":"+PORT;
	} else if(PORT == "80" && PROTOCOL == "http"){
		fullURL = PROTOCOL+"://"+IP;
	} else if(PORT == "" && PROTOCOL != "") {
		fullURL = PROTOCOL+"://"+IP; 
	} else if(PORT == "" && PROTOCOL == "") {
		fullURL = IP;
	}
	return fullURL;
}
