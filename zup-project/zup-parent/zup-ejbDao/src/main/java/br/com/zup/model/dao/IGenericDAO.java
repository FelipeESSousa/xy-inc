package br.com.zup.model.dao;

import java.util.List;

public interface IGenericDAO<T, PK>{
    public List<T> listar() throws Exception;
    public T salvarOuAtualizar(T t) throws Exception;
    public Boolean remover(T t) throws Exception;
    public Boolean removerPorId(PK id) throws Exception;
    public T buscarPorId(PK id) throws Exception;
    public List<T> buscarPorExemplo(T example) throws Exception;
}
