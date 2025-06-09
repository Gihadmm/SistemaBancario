package Testes;

import Model.Livro;
import repository.LivroDAO;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LivroDAOTest {

    static LivroDAO livroDAO;
    static Livro livro;

    @BeforeAll
    static void setup() throws SQLException {
        livroDAO = new LivroDAO();

        livro = new Livro(0, "Livro de Teste", "Autor Exemplo", "Ficção", 2023,
                "Editora Teste", "1234567890", 3);
        livro.setDisponivel(true);

        livroDAO.inserir(livro);

        System.out.println("\n📚 Iniciando testes da classe LivroDAO...");
    }

    @Test
    @Order(1)
    void testBuscarPorId() throws SQLException {
        Livro encontrado = livroDAO.buscarPorId(livro.getId());

        assertNotNull(encontrado, "Livro não foi encontrado por ID.");
        assertEquals(livro.getTitulo(), encontrado.getTitulo());

        System.out.println("🔍 Livro buscado com sucesso por ID: " + encontrado.getId());
    }

    @Test
    @Order(2)
    void testBuscarPorTitulo() throws SQLException {
        List<Livro> lista = livroDAO.buscarPorTitulo("Teste");

        assertFalse(lista.isEmpty(), "Nenhum livro foi encontrado pelo título.");
        System.out.println("🔎 Livro encontrado por título: " + lista.get(0).getTitulo());
    }

    @Test
    @Order(3)
    void testAtualizarLivro() throws SQLException {
        livro.setTitulo("Livro Atualizado");
        livro.setQuantidadeExemplares(5);
        livroDAO.atualizar(livro);

        Livro atualizado = livroDAO.buscarPorId(livro.getId());
        assertEquals("Livro Atualizado", atualizado.getTitulo());
        assertEquals(5, atualizado.getQuantidadeExemplares());

        System.out.println("♻️ Livro atualizado com sucesso. Novo título: " + atualizado.getTitulo());
    }

    @Test
    @Order(4)
    void testAtualizarDisponibilidade() throws SQLException {
        livroDAO.atualizarDisponibilidade(livro.getId(), false);
        Livro atualizado = livroDAO.buscarPorId(livro.getId());

        assertFalse(atualizado.isDisponivel(), "Disponibilidade do livro não foi atualizada corretamente.");
        System.out.println("📘 Disponibilidade atualizada com sucesso (disponível = false)");
    }

    @Test
    @Order(5)
    void testBuscarPorAutorEGênero() throws SQLException {
        List<Livro> porAutor = livroDAO.buscarPorAutor("Exemplo");
        List<Livro> porGenero = livroDAO.buscarPorGenero("Ficção");

        assertFalse(porAutor.isEmpty(), "Nenhum livro encontrado pelo autor.");
        assertFalse(porGenero.isEmpty(), "Nenhum livro encontrado pelo gênero.");

        System.out.println("✍️ Livro encontrado por autor: " + porAutor.get(0).getAutor());
        System.out.println("🎭 Livro encontrado por gênero: " + porGenero.get(0).getGenero());
    }

    @Test
    @Order(6)
    void testBuscarTodos() throws SQLException {
        List<Livro> todos = livroDAO.buscarTodos();
        assertFalse(todos.isEmpty(), "Nenhum livro foi retornado na busca total.");
        System.out.println("📚 Total de livros encontrados: " + todos.size());
    }

    @Test
    @Order(7)
    void testDeletarLivro() throws SQLException {
        livroDAO.deletar(livro.getId());
        Livro apagado = livroDAO.buscarPorId(livro.getId());

        assertNull(apagado, "Livro ainda existe após exclusão.");
        System.out.println("🗑️ Livro deletado com sucesso (ID: " + livro.getId() + ")");
    }

    @AfterAll
    static void finalizar() {
        System.out.println("\n✅ Testes da classe LivroDAO finalizados com sucesso.\n");
    }
}
