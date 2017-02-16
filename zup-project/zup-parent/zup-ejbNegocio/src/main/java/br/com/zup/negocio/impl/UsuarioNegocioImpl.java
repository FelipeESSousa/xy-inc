package br.com.zup.negocio.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.zup.model.dao.IUsuarioDAO;
import br.com.zup.model.dao.entity.Usuario;
import br.com.zup.negocio.IUsuarioNegocio;

@Stateless
public class UsuarioNegocioImpl extends AbstractNegocio implements IUsuarioNegocio {

    @Inject
    private IUsuarioDAO usuarioDAO;

    /* (non-Javadoc)
     * @see br.com.zup.negocio.IGenericNegocio#lista()
     */
    @Override
    public List<Usuario> listar() throws Exception {
        return usuarioDAO.listar();
    }

    /* (non-Javadoc)
     * @see br.com.zup.negocio.IGenericNegocio#salvaOuAtualiza(java.lang.Object)
     */
    @Override
    public Usuario salvarOuAtualizar(Usuario t) throws Exception {
        return usuarioDAO.salvarOuAtualizar(t);
    }

    /* (non-Javadoc)
     * @see br.com.zup.negocio.IGenericNegocio#remove(java.lang.Object)
     */
    @Override
    public Boolean remover(Usuario t) throws Exception {
        return usuarioDAO.remover(t);
    }

    /* (non-Javadoc)
     * @see br.com.zup.negocio.IGenericNegocio#removePorId(java.lang.Object)
     */
    @Override
    public Boolean removerPorId(Long id) throws Exception {
        return usuarioDAO.removerPorId(id);
    }

    /* (non-Javadoc)
     * @see br.com.zup.negocio.IGenericNegocio#buscarPorId(java.lang.Object)
     */
    @Override
    public Usuario buscarPorId(Long id) throws Exception {
        return usuarioDAO.buscarPorId(id);
    }

    /* (non-Javadoc)
     * @see br.com.zup.negocio.IGenericNegocio#buscarPorExemplo(java.lang.Object)
     */
    @Override
    public List<Usuario> buscarPorExemplo(Usuario example) throws Exception {
        return usuarioDAO.buscarPorExemplo(example);
    }

}
