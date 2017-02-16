package br.com.zup.negocio.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.zup.model.dao.IProductDAO;
import br.com.zup.model.dao.entity.Product;
import br.com.zup.negocio.IProductNegocio;

@Stateless
public class ProductNegocioImpl extends AbstractNegocio implements IProductNegocio {

    @Inject
    private IProductDAO productDAO;

    /* (non-Javadoc)
     * @see br.com.zup.negocio.IGenericNegocio#lista()
     */
    @Override
    public List<Product> listar() throws Exception {
        return productDAO.listar();
    }

    /* (non-Javadoc)
     * @see br.com.zup.negocio.IGenericNegocio#salvaOuAtualiza(java.lang.Object)
     */
    @Override
    public Product salvarOuAtualizar(Product t) throws Exception {
        return productDAO.salvarOuAtualizar(t);
    }

    /* (non-Javadoc)
     * @see br.com.zup.negocio.IGenericNegocio#remove(java.lang.Object)
     */
    @Override
    public Boolean remover(Product t) throws Exception {
        return productDAO.remover(t);
    }

    /* (non-Javadoc)
     * @see br.com.zup.negocio.IGenericNegocio#removePorId(java.lang.Object)
     */
    @Override
    public Boolean removerPorId(Long id) throws Exception {
        return productDAO.removerPorId(id);
    }

    /* (non-Javadoc)
     * @see br.com.zup.negocio.IGenericNegocio#buscarPorId(java.lang.Object)
     */
    @Override
    public Product buscarPorId(Long id) throws Exception {
        return productDAO.buscarPorId(id);
    }

    /* (non-Javadoc)
     * @see br.com.zup.negocio.IGenericNegocio#buscarPorExemplo(java.lang.Object)
     */
    @Override
    public List<Product> buscarPorExemplo(Product example) throws Exception {
        return productDAO.buscarPorExemplo(example);
    }

}
