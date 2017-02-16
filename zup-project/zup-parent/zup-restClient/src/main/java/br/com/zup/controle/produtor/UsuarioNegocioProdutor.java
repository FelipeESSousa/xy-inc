package br.com.zup.controle.produtor;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import br.com.zup.negocio.IUsuarioNegocio;

public class UsuarioNegocioProdutor {

    private static final Logger LOGGER = Logger.getLogger(UsuarioNegocioProdutor.class);
    private IUsuarioNegocio usuarioNegocio;

    @Inject
    private InitialContext ini;

    @Produces
    @RequestScoped
    public IUsuarioNegocio criarUsuarioNegocio() {
        try {
            if(usuarioNegocio == null){
                usuarioNegocio = (IUsuarioNegocio) ini.lookup(
                        "java:jboss/exported/zup-ejbNegocio/UsuarioNegocioImpl!br.com.zup.negocio.IUsuarioNegocio");
            }
        } catch (NamingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return usuarioNegocio;
    }
}
