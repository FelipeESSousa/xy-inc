package br.com.zup.controle.produtor;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import br.com.zup.negocio.IProductNegocio;

public class ProductNegocioProdutor {

    private static final Logger LOGGER = Logger.getLogger(ProductNegocioProdutor.class);
    private IProductNegocio productNegocio;

    @Inject
    private InitialContext ini;

    @Produces
    @RequestScoped
    public IProductNegocio criarNegocio() {
        try {
            if(productNegocio == null){
                productNegocio = (IProductNegocio) ini.lookup(
                        "java:jboss/exported/zup-ejbNegocio/ProductNegocioImpl!br.com.zup.negocio.IProductNegocio");
            }
        } catch (NamingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return productNegocio;
    }
}
