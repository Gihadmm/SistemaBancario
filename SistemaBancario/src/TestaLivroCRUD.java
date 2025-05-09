import repository.LivroDAO;
import Model.Livro;

public class TestaLivroCRUD {
    public static void main(String[] args) {
        LivroDAO dao = new LivroDAO();

        try {
            // 1) Criar um novo objeto Livro
            Livro novo = new Livro(
                    0,
                    "O Pequeno Príncipe",
                    "Antoine de Saint-Exupéry",
                    "Fábula",
                    1943,
                    "Reynal & Hitchcock",
                    "978-014312970",
                    3
            );

            // 2) Inserir no banco
            dao.inserir(novo);
            System.out.println("✔️ Livro inserido com ID: " + novo.getId());

            // 3) Recuperar e exibir o mesmo livro
            Livro buscado = dao.buscarPorId(novo.getId());
            if (buscado != null) {
                System.out.println("🔍 Livro encontrado:");
                System.out.println("   ID:    " + buscado.getId());
                System.out.println("   Título:" + buscado.getTitulo());
                System.out.println("   Autor: " + buscado.getAutor());
                System.out.println("   Ano:   " + buscado.getAnoPublicacao());
            } else {
                System.out.println("❌ Não encontrou o livro inserido.");
            }

            // 4) Deletar o livro
            dao.deletar(novo.getId());
            System.out.println("🗑️  Livro com ID " + novo.getId() + " deletado.");

            // 5) Tentar buscar novamente para confirmar remoção
            Livro depois = dao.buscarPorId(novo.getId());
            if (depois == null) {
                System.out.println("✅ Confirmação: livro removido com sucesso.");
            } else {
                System.out.println("❌ O livro ainda existe no banco!");
            }

        } catch (Exception e) {
            System.err.println("❌ Erro durante o teste de CRUD: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
