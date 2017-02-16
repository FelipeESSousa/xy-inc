package br.com.zup.web.resource;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import br.com.zup.negocio.exception.TypeErrorResourceException;
import br.com.zup.negocio.exception.TypeWarnResourceException;
import br.com.zup.negocio.util.PropertiesLoad;
import br.com.zup.negocio.util.enumerator.EnumPath;
import br.com.zup.negocio.util.enumerator.EnumProperties;
import br.com.zup.web.bean.ServiceResponse;

import com.google.gson.Gson;

@Path("/externos")
public class ServicoExternoResource {

    private static Logger log = Logger.getLogger(ServicoExternoResource.class);

    @Context
    private HttpServletRequest request;

    private static final String PATH_PROP_CONFIG  = EnumPath.APP_PATH.getPath() +File.separator+ EnumProperties.CONFIG_PROP.getName();
    private static final String PROTOCOL_REST     = PropertiesLoad.buscarValorPorChave("PROTOCOL_REST", PATH_PROP_CONFIG);
    private static final String IP_REST        	  = PropertiesLoad.buscarValorPorChave("IP_REST", PATH_PROP_CONFIG);
    private static final String PORT_REST      	  = PropertiesLoad.buscarValorPorChave("PORT_REST", PATH_PROP_CONFIG);
    private static final String PROJECT_REST      = PropertiesLoad.buscarValorPorChave("PROJECT_REST", PATH_PROP_CONFIG);

    private static final String URL_REST          = PROTOCOL_REST+"://" + IP_REST + ":" + PORT_REST +"/"+PROJECT_REST;



    /**
     * Carrega o arquivo de properties localizado no $JBOSS_HOME/conf/interacao/%nome_projeto%/properties
     * @return String
     * @throws Exception 
     */
    @GET
    @Path("/load/msg")
    @Produces("application/json; charset=UTF-8")
    public Response loadMsg() throws Exception{  
        String response = writeService(URL_REST+"/service/properties/load/msg");
        return Response.ok().entity(response).build();
    }

    /**
     * Carrega o arquivo de properties localizado no $JBOSS_HOME/conf/interacao/%nome_projeto%/properties
     * @return String
     * @throws Exception 
     */
    @GET
    @Path("/load/txt")
    @Produces("application/json; charset=UTF-8")
    public Response loadTxt() throws Exception{  
        String response = writeService(URL_REST+"/service/properties/load/txt");
        return Response.ok().entity(response).build();
    }

    /**
     * Carrega o arquivo de properties localizado no $JBOSS_HOME/conf/interacao/%nome_projeto%/
     * @return String
     * @throws Exception 
     */
    @GET
    @Path("/load/config")
    @Produces({"application/json; charset=UTF-8"})
    public Response loadConfig() throws Exception{  
        String response = writeService(URL_REST+"/service/properties/load/config");
        return Response.ok().entity(response).build();
    }

    /**
     * Escreve o servico redirecionado para a url REST e retorna o buffer de resposta da requisição http.
     * @param url
     * @return String response html
     * @throws Exception
     */
    private String writeService(String url) throws Exception{

        String response 					= "";
        HttpURLConnection httpConnection 	= null;
        BufferedReader responseBuffer		= null;
        try {
            URL targetUrl = new URL(url);
            httpConnection = (HttpURLConnection) targetUrl.openConnection();
            httpConnection.setRequestMethod("GET");
            setarHeaderDoContexto(httpConnection);
            httpConnection.setRequestProperty("Permissoes", permissoesDoUsuarioLogado());

            //Caso envie o atributo Headers, adicionar o conteudo
            if (httpConnection.getResponseCode() != 200) {
                lancarMensagemDeExcecao(httpConnection);
            }

            responseBuffer = new BufferedReader(new InputStreamReader((httpConnection.getInputStream()), "UTF-8"));
            String output;
            while ((output = responseBuffer.readLine()) != null) {
                response += output;
            }
        } catch (MalformedURLException e) {
            log.error(e.getMessage(),e);
            throw e;
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            throw e;
        } finally {
            if(httpConnection != null){
                httpConnection.disconnect();
            }
            if(responseBuffer != null){
                responseBuffer.close();
            }
        }  
        return response;

    }

    /**
     * Permissão de acesso enviada para o interceptor
     * @return String
     */
    private String permissoesDoUsuarioLogado() {
        return "REIDI_PERMIT-ALL";
    }

    /**
     * @description Atribui para o RequestProperty do HttpURLConnection o conteudo do Header de acordo com o http request enviado.
     * @param httpConnection
     */
    @SuppressWarnings("unchecked")
    private void setarHeaderDoContexto( HttpURLConnection httpConnection){
        Enumeration<String> enames = request.getHeaderNames();
        while (enames.hasMoreElements()) {
            String name = (String) enames.nextElement();
            String value = request.getHeader(name);
            httpConnection.setRequestProperty(name, value);
        }
    }

    /**
     * @description Lanca mensagem de erro em um ServiceResponse para os casos de codigo http seja diferente de 200.  
     * @param httpConnection
     * @throws Exception
     */
    private void lancarMensagemDeExcecao(HttpURLConnection httpConnection) throws Exception{
        Scanner s;
        s = new Scanner(httpConnection.getErrorStream());
        s.useDelimiter("\\Z");
        String response = s.next();
        s.close();
        Gson gson = new Gson();
        ServiceResponse serviceResponse = gson.fromJson(response, ServiceResponse.class);
        if(serviceResponse.getStatus().equals("warning")){
            throw new TypeWarnResourceException(serviceResponse.getDescription());
        }else if (serviceResponse.getStatus().equals("danger")){
            throw new TypeErrorResourceException(serviceResponse.getDescription());
        }else{
            throw new Exception(serviceResponse.getDescription());
        }
    }
}