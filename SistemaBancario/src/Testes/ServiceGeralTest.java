package Testes;

import Model.Cliente;
import Model.Usuario;
import Service.EmailService;
import Service.LoginService;
import Service.PasswordService;
import org.junit.jupiter.api.*;

import repository.UsuarioDAO;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste geral dos serviços do sistema (Login, E-mail, Senha).
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceGeralTest {

    static UsuarioDAO dao;
    static Cliente clienteTeste;

    @BeforeAll
    static void setup() throws Exception {
        dao = new UsuarioDAO();
        clienteTeste = new Cliente(
                "99999999999",
                "Usuário Serviço",
                "servico@email.com",
                "senha123"
        );

        Usuario existente = dao.buscarPorCpf(clienteTeste.getCpf());
        if (existente == null) {
            dao.inserirCliente(clienteTeste);
            System.out.println("👤 Cliente de teste inserido no banco.");
        } else {
            dao.atualizar(clienteTeste);
            System.out.println("🔁 Cliente já existia, dados foram atualizados.");
        }

        System.out.println("\n🚀 Iniciando testes dos serviços do sistema...\n");
    }

    @Test
    @Order(1)
    void testLoginServiceSucesso() {
        Usuario resultado = LoginService.realizarLogin("servico@email.com", "senha123");
        assertNotNull(resultado, "Usuário deveria ter sido autenticado.");
        System.out.println("✅ Login realizado com sucesso para: " + resultado.getNome());
    }

    @Test
    @Order(2)
    void testLoginServiceFalha() {
        Usuario resultado = LoginService.realizarLogin("servico@email.com", "senhaErrada");
        assertNull(resultado, "Login deveria falhar com senha incorreta.");
        System.out.println("❌ Login falhou como esperado para credenciais inválidas.");
    }

    @Test
    @Order(3)
    void testEmailServiceSimulado() {
        System.out.println("✉️ Simulando envio de e-mail...");
        System.out.println("De: seuemail@gmail.com");
        System.out.println("Para: " + clienteTeste.getEmail());
        System.out.println("Mensagem: Sua nova senha provisória é: 1234@senha");
        System.out.println("🟡 Simulação concluída (nenhum e-mail real foi enviado).");
    }

    @Test
    @Order(4)
    void testPasswordServiceNaoImplementado() {
        PasswordService ps = new PasswordService();
        ps.solicitarRedefinicaoSenha("servico@email.com");
        ps.redefinirSenhaComToken("abc123", "novaSenha");
        System.out.println("📨 Métodos de redefinição de senha chamados com sucesso (ainda não implementados).");
    }

    @AfterAll
    static void finalizar() {
        System.out.println("\n📗 Testes de serviços finalizados.");
    }
}
