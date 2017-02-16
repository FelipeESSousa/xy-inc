package br.com.zup.controle.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import br.com.zup.negocio.exception.TypeErrorResourceException;
import br.com.zup.negocio.util.PropertiesLoad;
import br.com.zup.negocio.util.enumerator.EnumPath;
import br.com.zup.negocio.util.enumerator.EnumProperties;

import com.google.gson.Gson;

/**
 * @class PropertiesResource
 * @description Responsavel por levar as configuracoes para o modulo web 
 */
@Path("/properties")
public class PropertiesResource {

    private final Logger log = Logger.getLogger(getClass());

    /**
     * Carrega o arquivo de properties localizado no $JBOSS_HOME/conf/interacao/%nome_projeto%/properties
     * @return String
     */
    @GET
    @Path("/load/msg")
    @Produces({MediaType.APPLICATION_JSON })
    @PermitAll
    public String loadMsg() throws Exception{  
        return loadComJson(EnumPath.PROPERTIES_PATH.getPath() +File.separator+ EnumProperties.MSG_PROP.getName());
    }

    /**
     * Carrega o arquivo de properties localizado no $JBOSS_HOME/conf/interacao/%nome_projeto%/properties
     * @return String
     */
    @GET
    @Path("/load/txt")
    @Produces({MediaType.APPLICATION_JSON })
    @PermitAll
    public String loadTxt() throws Exception{  
        return loadComJson(EnumPath.PROPERTIES_PATH.getPath() +File.separator+ EnumProperties.TXT_PROP.getName());
    }

    /**
     * Carrega o arquivo de properties localizado no $JBOSS_HOME/conf/interacao/%nome_projeto%/
     * @return String
     */
    @GET
    @Path("/load/config")
    @Produces({MediaType.APPLICATION_JSON })
    @PermitAll
    public String loadConfig() throws Exception{  	
        return loadComJson(EnumPath.APP_PATH.getPath() +File.separator+ EnumProperties.CONFIG_PROP.getName());
    }

    /**
     * Carrega o arquivo de properties localizado no parametro <b>fullPath</b> em uma string no formato json.
     * 
     * @param fullPath
     * @return String
     * @throws TypeErrorResourceException, IOException 
     */
    public String loadComJson(String fullPath) throws TypeErrorResourceException, IOException{  
        Gson gsonObj = new Gson();
        try {  
            return gsonObj.toJson(PropertiesLoad.load(fullPath));

        } catch (FileNotFoundException ex) {
            log.error(ex.getMessage(),ex);
            throw new TypeErrorResourceException(ex.getMessage());
        } catch (IOException ex) {  
            log.error(ex.getMessage(),ex);
            throw new TypeErrorResourceException(ex.getMessage());
        }
    }

}
