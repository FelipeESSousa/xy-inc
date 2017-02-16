package br.com.zup.negocio.produtor;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import br.com.zup.model.dao.IUsuarioDAO;

public class UsuarioProdutor {

    private static final Logger LOGGER = Logger.getLogger(UsuarioProdutor.class);

    private IUsuarioDAO usuarioDAO;

    @Inject
    private InitialContext ini;

    @Produces
    @Dependent
    public IUsuarioDAO criarUsuarioDAO() {
        try {
            if(usuarioDAO == null){
                usuarioDAO = (IUsuarioDAO) ini.lookup(
                        "java:jboss/exported/zup-ejbDao/UsuarioDAOImpl!br.com.zup.model.dao.IUsuarioDAO");
            }
        } catch (NamingException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return usuarioDAO;
    }
}
