package com.cesar.dao.generic;

import com.cesar.domain.Cliente;
import com.cesar.exceptions.DAOException;
import com.cesar.exceptions.TipoChaveNaoEncontradaException;
import org.junit.jupiter.api.*;



import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteDAOTest {

    private static ClienteDAO clienteDAO;

    @BeforeAll
    public static void setup() {
        clienteDAO = new ClienteDAO();
    }

    @Test
    @Order(1)
    public void testInsercao() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setCpf(12345678901L);
        cliente.setTel(11999999999L);
        cliente.setEnd("Rua das Flores");
        cliente.setNumero(123);
        cliente.setCidade("São Paulo");
        cliente.setEstado("SP");
        cliente.setEmail("joao.silva@example.com");

        try {
            clienteDAO.cadastrar(cliente);
            Cliente clienteCadastrado = clienteDAO.consultarPorChave(12345678901L);
            assertNotNull(clienteCadastrado);
            assertEquals("João Silva", clienteCadastrado.getNome());
            assertEquals("joao.silva@example.com", clienteCadastrado.getEmail());
        } catch (DAOException e) {
            throw new RuntimeException(e);
        } catch (TipoChaveNaoEncontradaException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    public void testAtualizacao() {
        try {
            Cliente cliente = clienteDAO.consultarPorChave(12345678901L);
            assertNotNull(cliente);

            cliente.setEmail("joao.novo@example.com");
            clienteDAO.atualizar(cliente);

            Cliente clienteAtualizado = clienteDAO.consultarPorChave(12345678901L);
            assertNotNull(clienteAtualizado);
            assertEquals("joao.novo@example.com", clienteAtualizado.getEmail());
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(3)
    public void testExclusao() {
        try {
            clienteDAO.excluir(12345678901L);
            Cliente cliente = clienteDAO.consultarPorChave(12345678901L);
            assertNull(cliente);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }
}
