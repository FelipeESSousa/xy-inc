package br.com.zup.model.dao.impl;

import javax.ejb.Stateless;

import br.com.zup.model.dao.IUsuarioDAO;
import br.com.zup.model.dao.entity.Usuario;

@Stateless
public class UsuarioDAOImpl extends GenericDAOSupport<Usuario, Long> implements IUsuarioDAO {

    private static final long serialVersionUID = -645711604441822045L;

}
