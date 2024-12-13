package com.cesar.dao.generic;

import com.cesar.domain.Produto;
import com.cesar.domain.Cliente;
import com.cesar.exceptions.DAOException;
import com.cesar.exceptions.MaisDeUmRegistroException;
import com.cesar.exceptions.TableException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProdutoDAO extends GenericDAO<Produto, String> implements IProdutoDAO {

    public ProdutoDAO() {
        super();
    }

    @Override
    public Class<Produto> getTipoClasse() {
        return Produto.class;
    }

    @Override
    public void atualiarDados(Produto entity, Produto entityCadastrado) {
        entityCadastrado.setCodigo(entity.getCodigo());
        entityCadastrado.setDescricao(entity.getDescricao());
        entityCadastrado.setNome(entity.getNome());
        entityCadastrado.setValor(entity.getValor());
    }

    @Override
    protected String getQueryInsercao() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_PRODUTO ");
        sb.append("(ID, CODIGO, NOME, DESCRICAO, VALOR)");
        sb.append("VALUES (nextval('sq_produto'),?,?,?,?)");
        return sb.toString();
    }

    @Override
    protected void setParametrosQueryInsercao(PreparedStatement stmInsert, Produto entity) throws SQLException {
        stmInsert.setString(1, entity.getCodigo());
        stmInsert.setString(2, entity.getNome());
        stmInsert.setString(3, entity.getDescricao());
        stmInsert.setBigDecimal(4, entity.getValor());
    }

    @Override
    protected String getQueryExclusao() {
        return "DELETE FROM TB_PRODUTO WHERE CODIGO = ?";
    }

    @Override
    protected void setParametrosQueryExclusao(PreparedStatement stmExclusao, String valor) throws SQLException {
        stmExclusao.setString(1, valor);
    }

    @Override
    protected String getQueryAtualizacao() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE TB_PRODUTO ");
        sb.append("SET CODIGO = ?,");
        sb.append("NOME = ?,");
        sb.append("DESCRICAO = ?,");
        sb.append("VALOR = ?");
        sb.append(" WHERE CODIGO = ?");
        return sb.toString();
    }

    @Override
    protected void setParametrosQueryAtualizacao(PreparedStatement stmUpdate, Produto entity) throws SQLException {
        stmUpdate.setString(1, entity.getCodigo());
        stmUpdate.setString(2, entity.getNome());
        stmUpdate.setString(3, entity.getDescricao());
        stmUpdate.setBigDecimal(4, entity.getValor());
        stmUpdate.setString(5, entity.getCodigo());
    }

    @Override
    protected void setParametrosQuerySelect(PreparedStatement stmSelect, String valor) throws SQLException {
        stmSelect.setString(1, valor);
    }

    @Override
    public Produto consultar(String valor) throws MaisDeUmRegistroException, TableException, DAOException {
        Produto produto = null;
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM TB_PRODUTO WHERE CODIGO = ?";
            connection = getConnection();
            stm = connection.prepareStatement(sql);
            stm.setString(1, valor);
            rs = stm.executeQuery();

            if (rs.next()) {
                produto = new Produto();
                produto.setCodigo(rs.getString("CODIGO"));
                produto.setNome(rs.getString("NOME"));
                produto.setDescricao(rs.getString("DESCRICAO"));
                produto.setValor(rs.getBigDecimal("VALOR"));
            } else {
                throw new TableException("Produto não encontrado.");
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao consultar produto", e);
        } finally {
            closeConnection(connection, stm, rs);
        }
        return produto;
    }

    @Override
    public Collection<Produto> buscarTodos() throws DAOException {
        List<Produto> produtos = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM TB_PRODUTO";
            connection = getConnection();
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setCodigo(rs.getString("CODIGO"));
                produto.setNome(rs.getString("NOME"));
                produto.setDescricao(rs.getString("DESCRICAO"));
                produto.setValor(rs.getBigDecimal("VALOR"));
                produtos.add(produto);
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar todos os produtos", e);
        } finally {
            closeConnection(connection, stm, rs);
        }
        return produtos;
    }

    @Override
    public void excluir(String valor) throws DAOException {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            connection = getConnection();
            stm = connection.prepareStatement(getQueryExclusao());
            setParametrosQueryExclusao(stm, valor);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao excluir produto", e);
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public void atualizar(Produto entity) throws DAOException {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            String sql = "UPDATE TB_PRODUTO SET CODIGO = ?, NOME = ?, DESCRICAO = ?, VALOR = ? WHERE CODIGO = ?";
            connection = getConnection();
            stm = connection.prepareStatement(sql);
            stm.setString(1, entity.getCodigo());
            stm.setString(2, entity.getNome());
            stm.setString(3, entity.getDescricao());
            stm.setBigDecimal(4, entity.getValor());
            stm.setString(5, entity.getCodigo());
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar produto", e);
        } finally {
            closeConnection(connection, stm, null);
        }
    }

    @Override
    public void atualizar(Cliente entity) throws DAOException {
        // Implementação vazia ou lógica para Cliente, conforme necessidade
        throw new UnsupportedOperationException("Método não implementado para Cliente");
    }
}
