package Testes;

import Model.Cliente;
import Model.Emprestimo;
import Model.Livro;
import Model.StatusEmprestimo;
import repository.ClienteDAO;
import repository.EmprestimoDAO;
import repository.LivroDAO;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmprestimoDAOTest {

    static EmprestimoDAO emprestimoDAO;
    static LivroDAO livroDAO;
    static ClienteDAO clienteDAO;
    static Cliente clienteTeste;
    static Livro livroTeste;
    static Emprestimo emprestimo;

    @BeforeAll
    static void setup() throws SQLException {
        emprestimoDAO = new EmprestimoDAO();
        clienteDAO = new ClienteDAO();
        livroDAO = new LivroDAO();

        // Criar cliente e livro para o teste
        clienteTeste = new Cliente("88888888888", "Cliente Teste", "cliente@email.com", "senha");
        clienteDAO.inserir(clienteTeste);

        livroTeste = new Livro(0, "Livro Teste", "Autor", "Gênero", 2024, "Editora", "0000000000", 5);
        livroDAO.inserir(livroTeste);

        System.out.println("\n📦 Iniciando testes da classe EmprestimoDAO...\n");
    }

    @Test
    @Order(1)
    void testInserirEmprestimo() throws SQLException {
        // Ajuste os parâmetros conforme seu construtor real:
        emprestimo = new Emprestimo(clienteTeste, livroTeste, new Date(), null, StatusEmprestimo.ATIVO);

        emprestimoDAO.inserir(emprestimo);
        assertTrue(emprestimo.getId() > 0, "ID do empréstimo deve ser maior que 0.");

        System.out.println("✅ Empréstimo inserido com ID: " + emprestimo.getId());
    }

    @Test
    @Order(2)
    void testAtualizarDevolucao() throws SQLException {
        emprestimo.devolver(); // marca como devolvido internamente
        emprestimoDAO.atualizarDevolucao(emprestimo);

        List<Emprestimo> lista = emprestimoDAO.listarPorCliente(clienteTeste.getCpf());
        Emprestimo atualizado = lista.stream().filter(e -> e.getId() == emprestimo.getId()).findFirst().orElse(null);

        assertNotNull(atualizado, "Empréstimo atualizado não encontrado.");
        assertNotNull(atualizado.getDataDevolucaoReal(), "Data de devolução não registrada.");
        assertEquals(StatusEmprestimo.CONCLUIDO, atualizado.getStatus(), "Status deveria ser CONCLUIDO.");

        System.out.println("🔁 Devolução registrada com sucesso para o empréstimo ID: " + emprestimo.getId());
    }

    @AfterAll
    static void finalizar() {
        System.out.println("\n📘 Testes da classe EmprestimoDAO finalizados.\n");
    }
}
