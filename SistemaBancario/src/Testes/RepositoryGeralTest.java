package Testes;

import Model.*;
import repository.*;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RepositoryGeralTest {

    static ClienteDAO clienteDAO;
    static UsuarioDAO usuarioDAO;
    static LivroDAO livroDAO;
    static EmprestimoDAO emprestimoDAO;
    static ReservaDAO reservaDAO;

    static Cliente clienteTeste;
    static Livro livroTeste;
    static Emprestimo emprestimo;
    static Reserva reserva;

    @BeforeAll
    static void setup() throws Exception {
        clienteDAO = new ClienteDAO();
        usuarioDAO = new UsuarioDAO();
        livroDAO = new LivroDAO();
        emprestimoDAO = new EmprestimoDAO();
        reservaDAO = new ReservaDAO();

        // Criação de cliente e inserção/atualização
        clienteTeste = new Cliente("77777777777", "Repository User", "repo@email.com", "senha");
        if (clienteDAO.buscarPorCpf(clienteTeste.getCpf()) == null)
            clienteDAO.inserir(clienteTeste);
        else
            clienteDAO.atualizar(clienteTeste);

        // Criação de livro
        livroTeste = new Livro(0, "Repo Livro", "Autor", "Gênero", 2024, "Editora", "ISBN001", 3);
        livroDAO.inserir(livroTeste);

        System.out.println("\n📦 Iniciando testes gerais dos repositórios...\n");
    }

    @Test
    @Order(1)
    void testClienteDAO() throws SQLException {
        Cliente encontrado = clienteDAO.buscarPorCpf(clienteTeste.getCpf());
        assertNotNull(encontrado, "Cliente deveria estar cadastrado.");
        System.out.println("✅ Cliente encontrado: " + encontrado.getNome());
    }

    @Test
    @Order(2)
    void testUsuarioDAO() throws SQLException {
        Usuario user = usuarioDAO.buscarPorEmailSenha(clienteTeste.getEmail(), clienteTeste.getSenha());
        assertNotNull(user, "Usuário deveria ser autenticado.");
        System.out.println("🔐 Login de usuário validado com sucesso.");
    }

    @Test
    @Order(3)
    void testLivroDAO() throws SQLException {
        Livro encontrado = livroDAO.buscarPorId(livroTeste.getId());
        assertNotNull(encontrado, "Livro deveria estar cadastrado.");
        System.out.println("📘 Livro cadastrado: " + encontrado.getTitulo());

        // Atualiza título
        encontrado.setTitulo("Repo Livro Atualizado");
        livroDAO.atualizar(encontrado);
        System.out.println("✏️ Título do livro atualizado.");
    }

    @Test
    @Order(4)
    void testEmprestimoDAO() throws SQLException {
        // Ajustado conforme construtor da classe Emprestimo
        emprestimo = new Emprestimo(
                0,
                new Date(),
                new Date(),
                livroTeste,
                clienteTeste
        );
        emprestimo.setStatus(StatusEmprestimo.ATIVO);

        emprestimoDAO.inserir(emprestimo);
        assertTrue(emprestimo.getId() > 0, "Empréstimo deveria ter ID gerado.");

        emprestimo.devolver(); // altera status e data
        emprestimoDAO.atualizarDevolucao(emprestimo);
        Emprestimo atualizado = emprestimoDAO.buscarPorId(emprestimo.getId());

        assertEquals(StatusEmprestimo.CONCLUIDO, atualizado.getStatus());
        System.out.println("🔁 Empréstimo atualizado e devolvido com sucesso.");
    }

    @Test
    @Order(5)
    void testReservaDAO() throws SQLException {
        reserva = new Reserva(clienteTeste, livroTeste, new Date(), new Date());
        reservaDAO.inserir(reserva);

        List<Reserva> reservas = reservaDAO.listarPorCliente(clienteTeste.getCpf());
        assertFalse(reservas.isEmpty(), "Reserva deveria estar registrada.");
        System.out.println("📚 Reserva registrada com ID: " + reservas.get(0).getId());

        boolean temReserva = reservaDAO.temReservasAtivas(livroTeste.getId());
        assertTrue(temReserva, "Livro deveria possuir reservas ativas.");
    }

    @AfterAll
    static void finalizar() {
        System.out.println("\n📗 Testes gerais dos repositórios finalizados.");
    }
}
