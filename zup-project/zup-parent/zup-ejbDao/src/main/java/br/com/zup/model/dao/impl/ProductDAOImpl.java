package br.com.zup.model.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.zup.model.dao.IProductDAO;
import br.com.zup.model.dao.entity.Product;

@Stateless
public class ProductDAOImpl extends GenericDAOSupport<Product, Long> implements IProductDAO {

    private static final long serialVersionUID = 8537570703846242818L;

    @Override
    public List<Product> buscarPorExemplo(Product exampleEntity) {
        Criteria cri = getHbCriteria();
        if(null != exampleEntity.getDsDescription()){
            cri.add(Restrictions.ilike("dsDescription",exampleEntity.getDsDescription(), MatchMode.ANYWHERE));
        }
        if(null != exampleEntity.getIdProduct()){
            cri.add(Restrictions.eq("idProduct",exampleEntity.getIdProduct()));
        }
        if(null != exampleEntity.getVlPrice()){
            cri.add(Restrictions.eq("vlPrice",exampleEntity.getVlPrice()));
        }
        if(null != exampleEntity.getNmCategory()){
            cri.add(Restrictions.ilike("nmCategory",exampleEntity.getNmCategory(), MatchMode.ANYWHERE));
        }
        if(null != exampleEntity.getNmName()){
            cri.add(Restrictions.ilike("nmName",exampleEntity.getNmName(), MatchMode.ANYWHERE));
        }
        return list(cri);
    }

}
