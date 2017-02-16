package br.com.zup.model.dao;

import javax.ejb.Remote;

import br.com.zup.model.dao.entity.Usuario;

@Remote
public interface IUsuarioDAO extends IGenericDAO<Usuario, Long> {
}
