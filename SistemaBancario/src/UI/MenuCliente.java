package UI;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;

import repository.LivroDAO;
import Model.Cliente;
import Model.Livro;

public class MenuCliente {

    public static void executar(Scanner scanner, Cliente cliente) {
        LivroDAO dao = new LivroDAO();
        int opcao;

        do {
            System.out.println("\n--- Menu Cliente ---");
            System.out.println("1. Listar todos os livros disponíveis");
            System.out.println("2. Consultar livro por ID");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            try {
                switch (opcao) {
                    case 1:
                        List<Livro> livros = dao.buscarTodos();
                        System.out.println("\n📚 Livros Disponíveis:");
                        for (Livro livro : livros) {
                            if (livro.isDisponivel()) {
                                System.out.println("📘 ID: " + livro.getId() +
                                        " | Título: " + livro.getTitulo() +
                                        " | Autor: " + livro.getAutor());
                            }
                        }
                        break;

                    case 2:
                        System.out.print("Digite o ID do livro: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        Livro livro = dao.buscarPorId(id);
                        if (livro != null) {
                            System.out.println("📖 Detalhes do Livro:");
                            System.out.println("Título: " + livro.getTitulo());
                            System.out.println("Autor: " + livro.getAutor());
                            System.out.println("Gênero: " + livro.getGenero());
                            System.out.println("Editora: " + livro.getEditora());
                            System.out.println("Ano: " + livro.getAnoPublicacao());
                            System.out.println("Disponível: " + (livro.isDisponivel() ? "Sim" : "Não"));
                        } else {
                            System.out.println("❌ Livro não encontrado.");
                        }
                        break;

                    case 0:
                        System.out.println("Voltando ao login...");
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (SQLException e) {
                System.err.println("❌ Erro no banco: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Entrada inválida. Digite um número.");
            }
        } while (opcao != 0);
    }
}