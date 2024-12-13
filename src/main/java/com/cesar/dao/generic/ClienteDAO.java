package com.cesar.dao.generic;

import com.cesar.domain.Cliente;
import com.cesar.domain.Produto;
import com.cesar.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class ClienteDAO extends GenericDAO<Cliente, Long> implements IClienteDAO {

    public ClienteDAO() {
        super();
    }

    @Override
    public void atualizar(Produto entity) throws DAOException {

    }

    @Override
    public Class<Cliente> getTipoClasse() {
        return Cliente.class;
    }

    @Override
    public void atualiarDados(Cliente entity, Cliente entityCadastrado) {
        entityCadastrado.setCidade(entity.getCidade());
        entityCadastrado.setCpf(entity.getCpf());
        entityCadastrado.setEnd(entity.getEnd());
        entityCadastrado.setEstado(entity.getEstado());
        entityCadastrado.setNome(entity.getNome());
        entityCadastrado.setNumero(entity.getNumero());
        entityCadastrado.setTel(entity.getTel());
        entityCadastrado.setEmail(entity.getEmail());
    }

    @Override
    protected String getQueryInsercao() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO TB_CLIENTE ");
        sb.append("(ID, NOME, CPF, TEL, ENDERECO, NUMERO, CIDADE, ESTADO, EMAIL)");
        sb.append(" VALUES (nextval('sq_cliente'),?,?,?,?,?,?,?,?)");
        return sb.toString();
    }

    @Override
    protected void setParametrosQueryInsercao(PreparedStatement stmInsert, Cliente entity) throws SQLException {
        stmInsert.setString(1, entity.getNome());
        stmInsert.setLong(2, entity.getCpf());
        stmInsert.setLong(3, entity.getTel());
        stmInsert.setString(4, entity.getEnd());
        stmInsert.setLong(5, entity.getNumero());
        stmInsert.setString(6, entity.getCidade());
        stmInsert.setString(7, entity.getEstado());
        stmInsert.setString(8, entity.getEmail());
    }

    @Override
    protected String getQueryExclusao() {
        return "DELETE FROM TB_CLIENTE WHERE CPF = ?";
    }

    @Override
    protected void setParametrosQueryExclusao(PreparedStatement stmExclusao, Long valor) throws SQLException {
        stmExclusao.setLong(1, valor);
    }

    @Override
    protected String getQueryAtualizacao() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE TB_CLIENTE ");
        sb.append("SET NOME = ?,");
        sb.append("TEL = ?,");
        sb.append("ENDERECO = ?,");
        sb.append("NUMERO = ?,");
        sb.append("CIDADE = ?,");
        sb.append("ESTADO = ?,");
        sb.append("EMAIL = ? ");
        sb.append("WHERE CPF = ?");
        return sb.toString();
    }

    @Override
    protected void setParametrosQueryAtualizacao(PreparedStatement stmUpdate, Cliente entity) throws SQLException {
        stmUpdate.setString(1, entity.getNome());
        stmUpdate.setLong(2, entity.getTel());
        stmUpdate.setString(3, entity.getEnd());
        stmUpdate.setLong(4, entity.getNumero());
        stmUpdate.setString(5, entity.getCidade());
        stmUpdate.setString(6, entity.getEstado());
        stmUpdate.setString(7, entity.getEmail());
        stmUpdate.setLong(8, entity.getCpf());
    }

    @Override
    protected void setParametrosQuerySelect(PreparedStatement stmSelect, Long valor) throws SQLException {
        stmSelect.setLong(1, valor);
    }

    public Cliente consultarPorChave(Long id) throws DAOException {
        Connection connection = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT ID, NOME, CPF, TEL, ENDERECO, NUMERO, CIDADE, ESTADO, EMAIL FROM TB_CLIENTE WHERE ID = ?";
            connection = (Connection) getConnection();
            stm = connection.prepareStatement(sql);
            stm.setLong(1, id);
            rs = stm.executeQuery();

            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("ID"));
                cliente.setNome(rs.getString("NOME"));
                cliente.setCpf(rs.getLong("CPF"));
                cliente.setTel(rs.getLong("TEL"));
                cliente.setEnd(rs.getString("ENDERECO"));
                cliente.setNumero((int) rs.getLong("NUMERO"));
                cliente.setCidade(rs.getString("CIDADE"));
                cliente.setEstado(rs.getString("ESTADO"));
                cliente.setEmail(rs.getString("EMAIL"));
                return cliente;
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao consultar cliente por chave", e);
        } finally {
            closeConnection((java.sql.Connection) connection, stm, rs);
        }
        return null;
    }

    @Override
    public void atualizar(Cliente entity) throws DAOException {
        Connection connection = null;
        PreparedStatement stm = null;
        try {
            String sql = getQueryAtualizacao();
            connection = getConnection();
            stm = connection.prepareStatement(sql);
            setParametrosQueryAtualizacao(stm, entity);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar cliente", e);
        } finally {
            closeConnection(connection, stm, null);
        }
    }


    @Override
    public Collection<Cliente> buscarTodos() throws DAOException {
        return List.of();
    }
}
