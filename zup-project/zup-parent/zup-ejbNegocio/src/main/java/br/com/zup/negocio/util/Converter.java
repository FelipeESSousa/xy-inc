package br.com.zup.negocio.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.Logger;

import br.com.zup.negocio.exception.EnumTypeException;
import br.com.zup.negocio.exception.NegocioException;

public class Converter {

    private static final Logger LOGGER = Logger.getLogger(Converter.class);

    private Converter() {
        // para nao ser instanciada
    }

    /**
     * Atribui os valores de campos correspondentes de um objeto para um outro objeto de destino. Os
     * campos do objeto de destino que ja estiverem preenchidos nao serao substituidos
     * 
     * @param objetoOrigem
     * @param objetoDestino
     * @return
     * @throws NegocioException
     */
    public static <T1, T2> T2  convertEntity(T1 objetoOrigem, T2 objetoDestino) throws NegocioException {

        if (objetoOrigem != null && objetoDestino != null) {
            Class<? extends Object> classe = objetoOrigem.getClass();
            Class<? extends Object> classeDestino = objetoDestino.getClass();

            Field[] listaCampos = classe.getDeclaredFields();
            for (int i = 0; i < listaCampos.length; i++) {
                Field campo = listaCampos[i];
                try {
                    Field campoDestino = classeDestino.getDeclaredField(campo.getName());
                    campo.setAccessible(true);
                    campoDestino.setAccessible(true);
                    atribuiValorAoDestino(objetoOrigem, objetoDestino, campo, campoDestino);
                } catch (NoSuchFieldException e) {
                    LOGGER.error(e.getMessage(), e);
                    continue;
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    LOGGER.error(e.getMessage(), e);
                    throw new NegocioException(e.getMessage(), EnumTypeException.ERROR);
                }
            }
        }
        return objetoDestino;
    }

    /**
     * Atribui os valores de campos correspondentes de um objeto para um outro objeto de destino. Os
     * campos do objeto de destino que ja estiverem preenchidos nao serao substituidos
     * 
     * @param objetoOrigem
     * @param objetoDestino
     * @return
     * @throws NegocioException
     */
    public static <T1, T2> List<T2> convertEntityList(List<T1> listaObjetoOrigem, Class<T2> classeObjDestino) throws NegocioException {
        List<T2> listaRetorno = new ArrayList<T2>();
        for (T1 objetoOrigem : listaObjetoOrigem) {
            try {
                T2 objetoDestino = classeObjDestino.newInstance();
                objetoDestino = (T2) convertEntity(objetoOrigem, objetoDestino);
                listaRetorno.add(objetoDestino);
            } catch (InstantiationException | IllegalAccessException e ) {
                throw new NegocioException(e.getMessage(), e, EnumTypeException.ERROR);
            }

        }
        return listaRetorno;
    }

    /**
     * Atribui valor ao destino quanto necessario
     * 
     * @param objetoOrigem
     * @param objetoDestino
     * @param campo
     * @param campoDestino
     * @throws IllegalAccessException
     */
    private static void atribuiValorAoDestino(Object objetoOrigem, Object objetoDestino, Field campo, Field campoDestino)
            throws IllegalAccessException {
        if (campoDestino.get(objetoDestino) == null && campoDestino.getType().equals(campo.getType())) {
            campoDestino.set(objetoDestino, campo.get(objetoOrigem));
        }
    }


    /**
     * @param <T, T1>
     * @method pegarValorCampo
     * @date 05/10/2015
     * @returnType RelatorioEscolhaTO
     * @param objetoOrigem
     * @param nomeCamposSelecionados
     * @return
     * @throws NegocioException 
     * @description Metodo responsavel por preencher o objeto destino apenas com os atributos que foram escolhidos
     */
    public static <T1> T1 preencherCamposEscolhidos(Object objetoOrigem, List<String> nomeCamposSelecionados, Class<T1> classeObjDestino) throws NegocioException{
        try {
            Class<? extends Object> classeObjOrigem = objetoOrigem.getClass();
            T1 objetoDestino = classeObjDestino.newInstance();  
            for (String nomeCampo : nomeCamposSelecionados) {
                preencherCampo(objetoDestino, objetoOrigem, classeObjOrigem, classeObjDestino, nomeCampo);
            }
            return objetoDestino;
        } catch (SecurityException|IllegalAccessException|InstantiationException e) {
            throw new NegocioException(e.getMessage(), e, EnumTypeException.ERROR);
        }
    }

    /**
     * @method preencherCampo
     * @date 07/12/2015
     * @returnType void
     * @param objetoDestino
     * @param objetoOrigem
     * @param campoOrigem
     * @param campoDestino
     * @param valorCampo
     * @param classeObjOrigem
     * @param classeObjDestino
     * @param nomeCampo
     * @description Metodo criado para separar parte do codigo do converter generico, atendendo assim o sonar
     */
    private static <T, T1> void preencherCampo(T1 objetoDestino, Object objetoOrigem, Class<T> classeObjOrigem, Class<T1> classeObjDestino, String nomeCampo){
        Field campoOrigem;
        Field campoDestino;
        Object valorCampo;
        try {
            campoOrigem = classeObjOrigem.getDeclaredField(nomeCampo);
            campoDestino = classeObjDestino.getDeclaredField(nomeCampo);
            campoOrigem.setAccessible(true);
            campoDestino.setAccessible(true);
            valorCampo = campoOrigem.get(objetoOrigem);
            campoDestino.set(objetoDestino, valorCampo);
        }catch (NoSuchFieldException|IllegalArgumentException | IllegalAccessException e) {
            LOGGER.error(e.getMessage(), e);
            //nao fazer nada
        } 
    }

}