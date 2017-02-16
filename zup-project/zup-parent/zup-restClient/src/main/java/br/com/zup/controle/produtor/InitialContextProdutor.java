package br.com.zup.controle.produtor;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

public class InitialContextProdutor {

    /**
     * @return initialContext
     */
    private static final Logger LOGGER = Logger.getLogger(InitialContextProdutor.class);

    @Produces
    @RequestScoped
    public InitialContext criarInitialContext() {
        InitialContext ini = null;
        try {
            ini = new InitialContext();
        } catch (NamingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return ini;
    }
}
