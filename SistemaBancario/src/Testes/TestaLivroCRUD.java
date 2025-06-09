package Testes;

import repository.LivroDAO;
import Model.Livro;

public class TestaLivroCRUD {
    public static void main(String[] args) {
        LivroDAO dao = new LivroDAO();
        try {
            // INSERT
            Livro l = new Livro(0, "Título A", "Autor A", "Gênero A", 2000, "Editora A", "ISBN-A", 2);
            dao.inserir(l);
            System.out.println("Inserido ID: " + l.getId());

            // UPDATE
            l.setTitulo("Título B");
            l.setQuantidadeExemplares(5);
            dao.atualizar(l);
            System.out.println("Atualizado!");

            // CONFIRMAÇÃO
            Livro atualizado = dao.buscarPorId(l.getId());
            System.out.println("Novo título: " + atualizado.getTitulo());
            System.out.println("Nova qtd: " + atualizado.getQuantidadeExemplares());

            // DELETE
            dao.deletar(l.getId());
            System.out.println("Deletado!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

