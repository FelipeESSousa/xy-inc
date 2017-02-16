package br.com.zup.negocio;

import javax.ejb.Remote;

import br.com.zup.model.dao.entity.Product;

@Remote
public interface IProductNegocio extends IGenericNegocio<Product, Long>{
}
