package br.com.zup.controle.resource;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import br.com.zup.negocio.exception.TypeErrorResourceException;
import br.com.zup.negocio.exception.TypeWarnResourceException;
import br.com.zup.negocio.util.PropertiesLoad;
import br.com.zup.negocio.util.enumerator.EnumPath;
import br.com.zup.negocio.util.enumerator.EnumProperties;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * @param <E>
 * @date 24/11/2014
 */
public class AbstractResource {

    private final String PATH_CONFIG_PROP  = EnumPath.APP_PATH.getPath()+ File.separator + EnumProperties.CONFIG_PROP.getName();
    private final String PATH_MSG_PROP = EnumPath.PROPERTIES_PATH.getPath() + File.separator +EnumProperties.MSG_PROP.getName();

    /**
     * @description Lança a excecao generica para listas de acordo com as mensagem passada.
     * @param lista
     * @param mensagemListaNula
     * @param mensagenListaVazia
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public void lancarExceptionParaListas(List lista, String mensagemListaNula, String mensagenListaVazia)
            throws TypeErrorResourceException, TypeWarnResourceException {

        if (lista == null && mensagemListaNula != null) {
            throw new TypeErrorResourceException(mensagemListaNula);
        }
        if (lista.isEmpty() && mensagenListaVazia != null) {
            throw new TypeWarnResourceException(mensagenListaVazia);
        }
    }


    /**
     * Busca nome do projeto de acordo com o properties especificado no caminho PATH_CONFIG_PROP
     * @return String nome do projeto
     */
    private String buscarNomeProjeto(){
        return PropertiesLoad.buscarValorPorChave("PROJECT_NAME", PATH_CONFIG_PROP);
    }

    /**
     * Verifica se há objeto e trata com exceção caso nao exista.
     * @param obSalvo
     * @throws TypeErrorResourceException
     */
    public void verificaObjeto(Object obSalvo) throws TypeErrorResourceException {
        if (obSalvo == null) {
            throw new TypeErrorResourceException();
        }
    }

    /**
     * Busca o valor no arquivo de properties de Mensagens de acordo com a chave <b>chaveMsg</b>
     * @param chaveMsg
     * @return
     */
    public String buscaMensagemProperties(String chaveMsg){
        return PropertiesLoad.buscarValorPorChave(chaveMsg, PATH_MSG_PROP);
    }


    /**
     * Cria adaptador para gson utilizado na conversao da strng json para lista de objetos.
     * 
     * @return gson
     * @throws Exception
     */
    public Gson construirGsonAdapter() throws Exception {
        try {
            JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
                @Override
                public Date deserialize(JsonElement json, Type typeOfT,
                        JsonDeserializationContext context) throws JsonParseException {
                    return json == null ? null : new Date(json.getAsLong());
                }
            };
            return new GsonBuilder().serializeNulls().registerTypeAdapter(Date.class, deser).create();
        } catch (Exception e) {
            throw e;
        }
    }

}
