package br.com.zup.negocio.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.jboss.logging.Logger;

import br.com.zup.negocio.util.enumerator.EnumPath;

/**
 * Classe responsável pelo carregamento dos properties do sistema
 * 
 */
public class PropertiesLoad {

	/**
     * @field LOGGER
     * @fieldType Logger
     * @date 23/09/2015
     */
    private static final Logger LOGGER = Logger.getLogger(PropertiesLoad.class);

    /**
     * @constructor PropertiesLoad
     * @date 28/09/2015
     */
    private PropertiesLoad(){
        super();
    }

    /**
     * @method buscarValorPorChave
     * @date 23/09/2015
     * @returnType String
     * @param chave
     * @param fullPath
     * @return String
     * @description Carrega o arquivo de properties localizado no parametro <b>fullPath</b> e busca o valor para
     * o parametro <b>chave</b>.
     */
    public static String buscarValorPorChave(String chave, String fullPath) {
        try {
            Properties prop = PropertiesLoad.load(fullPath);
            return prop.getProperty(chave);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        } 
        return "Ocorreu um erro no sistema. Favor contate o suporte técnico.";
    }

    /**
     * @method load
     * @date 23/09/2015
     * @returnType Properties
     * @param fullPath
     * @return
     * @throws IOException
     * @description Ler um arquivo properties de acordo com o caminho <b>fullPath</b> e retorna um objeto
     * porperties com as chaves e valores contida no arquivo.
     */
    public static Properties load(String fullPath) throws IOException {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(fullPath);
            Reader reader = new InputStreamReader(input, "UTF-8");
            prop.load(reader);
            return prop;
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw ex;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    LOGGER.error(e);
                }
            }
        }
    }

    /**
     * @method getValueFromConfig
     * @returnType String
     * @param keyName
     * @description Pega valor da key direto do app-config
     */
    public static String getValueFromConfig(String keyName){
        return PropertiesLoad.buscarValorPorChave(keyName, EnumPath.APP_PATH.getPath() + File.separator + "zup-config.properties");
    }
}	
