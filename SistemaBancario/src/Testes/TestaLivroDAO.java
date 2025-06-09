package Testes;

import repository.LivroDAO;
import Model.Livro;

import java.util.List;

public class TestaLivroDAO {
    public static void main(String[] args) {
        LivroDAO dao = new LivroDAO();

        System.out.println("📚 Testando buscarTodos():");
        try {
            List<Livro> livros = dao.buscarTodos();
            for (Livro livro : livros) {
                System.out.println("ID: " + livro.getId() + ", Título: " + livro.getTitulo());
            }
        } catch (Exception e) {
            System.err.println("❌ Erro ao buscar todos os livros: " + e.getMessage());
        }

        System.out.println("\n🔎 Testando buscarPorId(1):");
        try {
            Livro livro = dao.buscarPorId(1);
            if (livro != null) {
                System.out.println("Encontrado: " + livro.getTitulo() + " (ID: " + livro.getId() + ")");
            } else {
                System.out.println("Livro com ID 1 não encontrado.");
            }
        } catch (Exception e) {
            System.err.println("❌ Erro ao buscar livro por ID: " + e.getMessage());
        }
    }
}
