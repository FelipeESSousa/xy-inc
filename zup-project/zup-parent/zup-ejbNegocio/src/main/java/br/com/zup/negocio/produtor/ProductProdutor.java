package br.com.zup.negocio.produtor;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import br.com.zup.model.dao.IProductDAO;

public class ProductProdutor {

    private static final Logger LOGGER = Logger.getLogger(ProductProdutor.class);

    private IProductDAO productDAO;

    @Inject
    private InitialContext ini;

    @Produces
    @Dependent
    public IProductDAO criarDAO() {
        try {
            if(productDAO == null){
                productDAO = (IProductDAO) ini.lookup(
                        "java:jboss/exported/zup-ejbDao/ProductDAOImpl!br.com.zup.model.dao.IProductDAO");
            }
        } catch (NamingException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return productDAO;
    }
}
