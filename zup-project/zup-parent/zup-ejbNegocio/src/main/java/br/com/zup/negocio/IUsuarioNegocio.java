package br.com.zup.negocio;

import javax.ejb.Remote;

import br.com.zup.model.dao.entity.Usuario;

@Remote
public interface IUsuarioNegocio extends IGenericNegocio<Usuario, Long>{
}
