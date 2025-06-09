package Testes;

import Model.Administrador;
import Model.Cliente;
import Model.Usuario;
import repository.UsuarioDAO;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioDAOTest {

    static UsuarioDAO usuarioDAO;
    static Cliente clienteTeste;
    static Administrador adminTeste;

    @BeforeAll
    static void setup() throws SQLException {
        usuarioDAO = new UsuarioDAO();

        clienteTeste = new Cliente("11122233344", "Cliente Teste", "cliente@teste.com", "senha123");
        adminTeste = new Administrador("55566677788", "Admin Teste", "admin@teste.com", "admin321");

        usuarioDAO.inserirCliente(clienteTeste);
        usuarioDAO.inserirAdministrador(adminTeste);

        System.out.println("\n🔐 Iniciando testes da classe UsuarioDAO...\n");
    }

    @Test
    @Order(1)
    void testBuscarPorCpf() throws SQLException {
        Usuario usuario = usuarioDAO.buscarPorCpf(clienteTeste.getCpf());
        assertNotNull(usuario);
        assertEquals(clienteTeste.getNome(), usuario.getNome());
        System.out.println("🔍 Cliente encontrado por CPF: " + usuario.getNome());

        Usuario admin = usuarioDAO.buscarPorCpf(adminTeste.getCpf());
        assertNotNull(admin);
        assertEquals(adminTeste.getNome(), admin.getNome());
        System.out.println("🔍 Administrador encontrado por CPF: " + admin.getNome());
    }

    @Test
    @Order(2)
    void testBuscarPorEmailSenha() throws SQLException {
        Usuario cliente = usuarioDAO.buscarPorEmailSenha("cliente@teste.com", "senha123");
        assertNotNull(cliente);
        assertTrue(cliente instanceof Cliente);
        System.out.println("📧 Login de cliente bem-sucedido: " + cliente.getNome());

        Usuario admin = usuarioDAO.buscarPorEmailSenha("admin@teste.com", "admin321");
        assertNotNull(admin);
        assertTrue(admin instanceof Administrador);
        System.out.println("📧 Login de administrador bem-sucedido: " + admin.getNome());
    }

    @Test
    @Order(3)
    void testBuscarPorNome() throws SQLException {
        List<Usuario> encontrados = usuarioDAO.buscarPorNome("Teste");
        assertFalse(encontrados.isEmpty());
        System.out.println("🧾 Usuários encontrados por nome: " + encontrados.size());
    }

    @Test
    @Order(4)
    void testAtualizarCliente() throws SQLException {
        clienteTeste.setNome("Cliente Atualizado");
        clienteTeste.setSenha("novaSenha123");
        usuarioDAO.atualizarCliente(clienteTeste);

        Usuario atualizado = usuarioDAO.buscarPorCpf(clienteTeste.getCpf());
        assertEquals("Cliente Atualizado", atualizado.getNome());
        assertEquals("novaSenha123", atualizado.getSenha());
        System.out.println("♻️ Cliente atualizado com sucesso.");
    }

    @Test
    @Order(5)
    void testBuscarPorCpfParcial() throws SQLException {
        List<Usuario> lista = usuarioDAO.buscarPorCpfParcial("111");
        assertFalse(lista.isEmpty());
        System.out.println("🔎 CPF parcial encontrou " + lista.size() + " usuário(s).");
    }

    @AfterAll
    static void finalizar() {
        System.out.println("\n✅ Testes da classe UsuarioDAO finalizados com sucesso.\n");
    }
}
