/**
 * 
 */
package br.com.zup.negocio.impl;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;

import br.com.zup.negocio.exception.EnumTypeException;
import br.com.zup.negocio.exception.NegocioException;
import br.com.zup.negocio.util.PropertiesLoad;
import br.com.zup.negocio.util.enumerator.EnumPath;

abstract class AbstractNegocio {

    /**
     * Busca o valor no arquivo de properties de Mensagens de acordo com a chave <b>chaveMsg</b>
     * 
     * @param chaveMsg
     * @return
     */
    public String buscaMensagemProperties(String chaveMsg) {
        return PropertiesLoad.buscarValorPorChave(chaveMsg, EnumPath.ARQUIVO_MENSAGENS_PATH.getPath());
    }

    /**
     * Cria adaptador para gson utilizado na conversao da strng json para lista de objetos.
     * 
     * @return gson
     * @throws Exception
     */
    public Gson construirGsonAdapter() {
        try {
            JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
                @Override
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
                    return json == null ? null : new Date(json.getAsLong());
                }
            };
            return new GsonBuilder().serializeNulls().registerTypeAdapter(Date.class, deser).create();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Lança a excecao generica para listas de acordo com as mensagem passada.
     * 
     * @param lista
     * @param mensagemListaNula
     * @param mensagenListaVazia
     * @throws NegocioException 
     */
    @SuppressWarnings("rawtypes")
    public void lancarExceptionParaListas(List lista, String mensagemListaNula, String mensagenListaVazia) throws NegocioException {
        if (lista == null && mensagemListaNula != null) {
            throw new NegocioException(mensagemListaNula, EnumTypeException.ERROR);
        }
        if (lista.isEmpty() && mensagenListaVazia != null) {
            throw new NegocioException(mensagenListaVazia, EnumTypeException.WARN);
        }
    }
}
