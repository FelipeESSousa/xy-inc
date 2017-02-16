package br.com.zup.model.dao.impl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.jboss.logging.Logger;

import br.com.zup.model.dao.IGenericDAO;

public abstract class GenericDAOSupport<T, PK> implements IGenericDAO<T, PK>, Serializable {

    private static final long serialVersionUID = 1L;
    private Class<T> classe;

    public final Logger LOGGER = Logger.getLogger(GenericDAOSupport.class);

    @PersistenceContext(unitName = "zup_ds")
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public GenericDAOSupport() {
        this.classe = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Criteria getHbCriteria() {
        Session session = getSession();
        return session.createCriteria(classe);
    }

    protected Session getSession() {
        return getEntityManager().unwrap(org.hibernate.Session.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> listar() throws Exception {
        try {
            return getEntityManager().createQuery("FROM " + classe.getName()).getResultList();
        } catch (Exception e) {
            throw e;
        }
    }

    protected EntityManager getEntityManager() {
        return this.em;
    }

    public EntityManager setEntityManager(EntityManager em) {
        this.em = em;
        return this.em;
    }

    @Override
    public T salvarOuAtualizar(T t) throws Exception {
        try {
            return getEntityManager().merge(t);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Boolean remover(T t) throws Exception {
        try {
            getEntityManager().remove(t);
            return Boolean.TRUE;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public T buscarPorId(PK id) {
        return getEntityManager().find(classe, id);
    }

    @Override
    public Boolean removerPorId(PK id) throws Exception {
        try {
            getEntityManager().remove(getEntityManager().getReference(classe, id));
            return Boolean.TRUE;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<T> buscarPorExemplo(T exampleEntity) {
        Example example = Example.create(exampleEntity).excludeNone().enableLike(MatchMode.ANYWHERE);
        Criteria cri = getHbCriteria().add(example);
        return list(cri);
    }

    @SuppressWarnings("unchecked")
    protected List<T> list(Criteria criteria) {
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    /**
     * Metodos para evitar o erro de serialização de objetos que não implementão
     * a interface de serialização.
     */
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }

}
