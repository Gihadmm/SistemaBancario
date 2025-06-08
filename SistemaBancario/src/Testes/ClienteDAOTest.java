package Testes;

import Model.Cliente;
import repository.ClienteDAO;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteDAOTest {

    static ClienteDAO clienteDAO;
    static String cpfTeste = "99999999999"; // CPF fictício usado para testes

    @BeforeAll
    static void setup() {
        clienteDAO = new ClienteDAO();
        System.out.println("\n🔧 Iniciando testes da classe ClienteDAO...\n");
    }

    @Test
    @Order(1)
    void testInserirCliente() throws SQLException {
        Cliente cliente = new Cliente(cpfTeste, "Teste Nome", "teste@email.com", "senha123");
        clienteDAO.inserir(cliente);

        Cliente buscado = clienteDAO.buscarPorCpf(cpfTeste);
        System.out.println("🟢 Inserção realizada. Cliente encontrado: " + (buscado != null));

        assertNotNull(buscado, "Cliente deveria ter sido inserido no banco.");
        assertEquals("Teste Nome", buscado.getNome(), "Nome não corresponde após inserção.");
    }

    @Test
    @Order(2)
    void testAtualizarCliente() throws SQLException {
        Cliente cliente = clienteDAO.buscarPorCpf(cpfTeste);
        assertNotNull(cliente, "Cliente de teste não encontrado para atualização.");

        cliente.setNome("Nome Atualizado");
        cliente.setEmail("novoemail@email.com");
        cliente.setSenha("novasenha");

        clienteDAO.atualizar(cliente);

        Cliente atualizado = clienteDAO.buscarPorCpf(cpfTeste);

        System.out.println("🔄 Atualização realizada:");
        System.out.println("   ➤ Nome: " + atualizado.getNome());
        System.out.println("   ➤ Email: " + atualizado.getEmail());

        assertEquals("Nome Atualizado", atualizado.getNome(), "Nome não atualizado corretamente.");
        assertEquals("novoemail@email.com", atualizado.getEmail(), "Email não atualizado corretamente.");
    }

    @Test
    @Order(3)
    void testDeletarCliente() throws SQLException {
        clienteDAO.deletar(cpfTeste);
        Cliente excluido = clienteDAO.buscarPorCpf(cpfTeste);

        System.out.println("❌ Cliente deletado. Existe no banco? " + (excluido != null));

        assertNull(excluido, "Cliente deveria ter sido excluído do banco.");
    }

    @AfterAll
    static void finalizar() {
        System.out.println("\n✅ Testes da classe ClienteDAO finalizados com sucesso.\n");
    }
}
