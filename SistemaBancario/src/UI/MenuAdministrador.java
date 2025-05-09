package UI;

import Model.Livro;
import Model.Administrador;
import repository.LivroDAO;
import repository.UsuarioDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuAdministrador {

    public static void executar(Scanner scanner, Administrador admin) {
        LivroDAO livroDAO = new LivroDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        int opcao;

        do {
            System.out.println("\n--- Menu Administrador ---");
            System.out.println("1. Cadastrar livro");
            System.out.println("2. Listar todos os livros");
            System.out.println("3. Atualizar disponibilidade de um livro");
            System.out.println("4. Deletar livro");
            System.out.println("5. Cadastrar novo Administrador");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            try {
                switch (opcao) {
                    case 1:
                        // Cadastrar livro
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

                        Livro novoLivro = new Livro(0, titulo, autor, genero, ano, editora, isbn, qtd);
                        livroDAO.inserir(novoLivro);
                        System.out.println("✔️ Livro cadastrado com ID: " + novoLivro.getId());
                        break;

                    case 2:
                        // Listar livros
                        List<Livro> livros = livroDAO.buscarTodos();
                        System.out.println("\n📚 Lista de Livros:");
                        for (Livro l : livros) {
                            System.out.printf(
                                    "ID: %d | %s — %s (%s) | Exemplares: %d | Disponível: %b%n",
                                    l.getId(), l.getTitulo(), l.getAutor(), l.getGenero(),
                                    l.getQuantidadeExemplares(), l.isDisponivel()
                            );
                        }
                        break;

                    case 3:
                        // Atualizar disponibilidade
                        System.out.print("ID do livro: ");
                        int idUpd = Integer.parseInt(scanner.nextLine());
                        System.out.print("Disponível? (true/false): ");
                        boolean disp = Boolean.parseBoolean(scanner.nextLine());
                        livroDAO.atualizarDisponibilidade(idUpd, disp);
                        System.out.println("✔️ Disponibilidade atualizada.");
                        break;

                    case 4:
                        // Deletar livro
                        System.out.print("ID do livro a deletar: ");
                        int idDel = Integer.parseInt(scanner.nextLine());
                        livroDAO.deletar(idDel);
                        System.out.println("✔️ Livro deletado.");
                        break;

                    case 5:
                        // Cadastrar novo administrador
                        System.out.println("\n--- Cadastrar Novo Administrador ---");
                        System.out.print("CPF: ");
                        String cpfADM = scanner.nextLine();
                        System.out.print("Nome: ");
                        String nomeADM = scanner.nextLine();
                        System.out.print("Email: ");
                        String emailADM = scanner.nextLine();
                        System.out.print("Senha: ");
                        String senhaADM = scanner.nextLine();

                        Administrador novoADM = new Administrador(cpfADM, nomeADM, emailADM, senhaADM);
                        usuarioDAO.inserirUsuario(novoADM);
                        System.out.println("✔️ Novo Administrador cadastrado com sucesso!");
                        break;

                    case 0:
                        System.out.println("Voltando ao menu de login...");
                        break;

                    default:
                        System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (SQLException e) {
                System.err.println("❌ Erro no banco: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("⚠️ Entrada numérica inválida.");
            }
        } while (opcao != 0);
    }
}
