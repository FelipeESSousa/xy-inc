package br.com.zup.model.dao;

import javax.ejb.Remote;

import br.com.zup.model.dao.entity.Product;

@Remote
public interface IProductDAO extends IGenericDAO<Product, Long> {
}
