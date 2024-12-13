package com.cesar.dao.generic;

import com.cesar.domain.Produto;
import com.cesar.exceptions.DAOException;
import com.cesar.exceptions.MaisDeUmRegistroException;
import com.cesar.exceptions.TableException;
import com.cesar.exceptions.TipoChaveNaoEncontradaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoDAOTest {

    private ProdutoDAO produtoDAO;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        produtoDAO = new ProdutoDAO() {
            @Override
            protected Connection getConnection() {
                return connection;
            }
        };
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
    }

    @Test
    void testCadastrar() throws SQLException, DAOException, TipoChaveNaoEncontradaException {
        Produto produto = new Produto();
        produto.setCodigo("P123");
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição do Produto Teste");
        produto.setValor(BigDecimal.valueOf(100.00));

        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertTrue(produtoDAO.cadastrar(produto));
        verify(preparedStatement, times(1)).executeUpdate(); // Verificando 1 vez
    }

    @Test
    void testAtualizar() throws SQLException, DAOException {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setCodigo("P123");
        produto.setNome("Produto Atualizado");
        produto.setDescricao("Descrição Atualizada");
        produto.setValor(BigDecimal.valueOf(200.00));

        doNothing().when(preparedStatement).setString(anyInt(), anyString());
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> produtoDAO.atualizar(produto));
        verify(preparedStatement, times(1)).executeUpdate(); // Verificando 1 vez
    }

    @Test
    void testExcluir() throws SQLException, DAOException {
        Long id = 1L;

        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> produtoDAO.excluir(String.valueOf(id)));
        verify(preparedStatement, times(1)).executeUpdate(); // Verificando 1 vez
    }

    @Test
    void testConsultar() throws SQLException, DAOException, MaisDeUmRegistroException, TableException {
        Long id = 1L;

        Produto produtoMock = new Produto();
        produtoMock.setId(id);
        produtoMock.setCodigo("P123");
        produtoMock.setNome("Produto Mock");
        produtoMock.setDescricao("Descrição Mock");
        produtoMock.setValor(BigDecimal.valueOf(100.00));

        when(preparedStatement.executeQuery()).thenReturn(null); // Simulando retorno vazio

        Produto result = produtoDAO.consultar(String.valueOf(id));
        assertNull(result); // Alterar conforme a implementação
    }
}
