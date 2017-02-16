package br.com.zup.negocio.produtor;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

/**
 * @version 1.0
 */
public class InitialContextProdutor {
	private static final Logger LOGGER = Logger.getLogger(InitialContextProdutor.class);
    /**
     * @return initialContext
     */
    @Produces
    @Dependent
    public InitialContext criarInitialContext() {
        InitialContext ini = null;
        try {
            ini = new InitialContext();
        } catch (NamingException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return ini;
    }
}
