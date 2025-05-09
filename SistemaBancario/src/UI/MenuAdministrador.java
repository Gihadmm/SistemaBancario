package UI;

import Model.Administrador;
import Model.Livro;
import repository.LivroDAO;

import java.util.List;
import java.util.Scanner;

public class MenuAdministrador {

    public static void executar(Scanner scanner, Administrador admin) {
        LivroDAO dao = new LivroDAO();
        int opcao;

        do {
            System.out.println("\n--- Menu Model.Administrador ---");
            System.out.println("1. Cadastrar livro");
            System.out.println("2. Listar todos os livros");
            System.out.println("3. Atualizar disponibilidade de um livro");
            System.out.println("4. Deletar livro");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            try {
                switch (opcao) {
                    case 1:
                        System.out.print("Título: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Autor: ");
                        String autor = scanner.nextLine();
                        System.out.print("Gênero: ");
                        String genero = scanner.nextLine();
                        System.out.print("Ano de publicação: ");
                        int ano = Integer.parseInt(scanner.nextLine());
                        System.out.print("Editora: ");
                        String editora = scanner.nextLine();
                        System.out.print("ISBN: ");
                        String isbn = scanner.nextLine();
                        System.out.print("Quantidade de exemplares: ");
                        int qtd = Integer.parseInt(scanner.nextLine());

                        Livro novo = new Livro(0, titulo, autor, genero, ano, editora, isbn, qtd);
                        dao.inserir(novo);
                        System.out.println("✔️ Model.Livro cadastrado com ID " + novo.getId());
                        break;

                    case 2:
                        List<Livro> livros = dao.buscarTodos();
                        System.out.println("\n=== Lista de Livros ===");
                        for (Livro l : livros) {
                            System.out.printf("%d: %s — %s — %s — %d exemplares — disponivel=%b%n",
                                    l.getId(), l.getTitulo(), l.getAutor(), l.getGenero(),
                                    l.getQuantidadeExemplares(), l.isDisponivel());
                        }
                        break;

                    case 3:
                        System.out.print("ID do livro: ");
                        int idUpd = Integer.parseInt(scanner.nextLine());
                        System.out.print("Disponível? (true/false): ");
                        boolean disp = Boolean.parseBoolean(scanner.nextLine());
                        dao.atualizarDisponibilidade(idUpd, disp);
                        System.out.println("✔️ Disponibilidade atualizada.");
                        break;

                    case 4:
                        System.out.print("ID do livro a deletar: ");
                        int idDel = Integer.parseInt(scanner.nextLine());
                        dao.deletar(idDel);
                        System.out.println("✔️ Model.Livro deletado.");
                        break;

                    case 0:
                        System.out.println("Voltando ao menu principal...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.err.println("❌ Erro: " + e.getMessage());
            }

        } while (opcao != 0);
    }
}
