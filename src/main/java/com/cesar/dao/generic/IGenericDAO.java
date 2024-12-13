package com.cesar.dao.generic;

import com.cesar.exceptions.DAOException;
import com.cesar.exceptions.MaisDeUmRegistroException;
import com.cesar.exceptions.TableException;
import com.cesar.exceptions.TipoChaveNaoEncontradaException;

import java.io.Serializable;
import java.util.Collection;

public interface IGenericDAO<T extends Persistente, E extends Serializable> {
    Boolean cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException;
    void excluir(E valor) throws DAOException;
    void alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException;
    T consultar(E valor) throws MaisDeUmRegistroException, TableException, DAOException;
    Collection<T> buscarTodos() throws DAOException;
}
