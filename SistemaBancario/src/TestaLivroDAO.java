import Model.Livro;
import repository.LivroDAO;

import java.sql.SQLException;


public class TestaLivroDAO {
    public static void main(String[] args) {
        LivroDAO dao = new LivroDAO();
        Livro livro = new Livro(0, "Dom Casmurro", "Machado de Assis", "Romance", 1899,
                "Editora A", "1234567890", 5);

        try {
            dao.inserir(livro);
            System.out.println("✅ Model.Livro inserido com ID: " + livro.getId());

            Livro buscado = dao.buscarPorId(livro.getId());
            if (buscado != null) {
                System.out.println("🔍 Model.Livro encontrado: " + buscado.getTitulo());
            }

        } catch (SQLException e) {
            System.err.println("❌ Erro ao interagir com o banco: " + e.getMessage());
        }
    }
}
