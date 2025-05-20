package UI;

import Model.Usuario;
import Model.Cliente;
import Model.Administrador;
import repository.UsuarioDAO;
import repository.LivroDAO;
import repository.EmprestimoDAO;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        LivroDAO livroDAO = new LivroDAO();
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

        while (true) {
            System.out.println("╔════════════════════════════════════════╗");
            System.out.println("║      SISTEMA DE BIBLIOTECA DIGITAL     ║");
            System.out.println("╠════════════════════════════════════════╣");
            System.out.println("║ 1. Login                               ║");
            System.out.println("║ 2. Cadastro                            ║");
            System.out.println("║ 3. Recuperar Senha                     ║");
            System.out.println("║ 0. Sair                                ║");
            System.out.println("╚════════════════════════════════════════╝");
            System.out.print("Opção: ");
            String opc = scanner.nextLine();

            switch (opc) {
                case "0":
                    System.out.println("👋 Encerrando sistema. Até mais!");
                    scanner.close();
                    return;

                case "1":
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();

                    Usuario user = usuarioDAO.buscarPorCpf(cpf);
                    if (user == null || !user.getSenha().equals(senha)) {
                        System.out.println("❌ Usuário ou senha inválidos.");
                        break;
                    }

                    if (user.getAcesso() == Model.Acesso.ADMINISTRADOR) {
                        System.out.println("✔️ Bem-vindo ADM, " + user.getNome());
                        MenuAdministrador.executar(scanner, livroDAO, usuarioDAO, emprestimoDAO);

                    } else {
                        System.out.println("✔️ Bem-vindo, " + user.getNome());
                        MenuCliente.executar(scanner, (Cliente) user);
                    }
                    break;

                case "2":
                    System.out.print("CPF: ");
                    String cpf2 = scanner.nextLine();
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Senha: ");
                    String senha2 = scanner.nextLine();

                    Cliente novoCliente = new Cliente(cpf2, nome, email, senha2);
                    usuarioDAO.inserirCliente(novoCliente);
                    System.out.println("✔️ Cliente cadastrado com sucesso!");
                    break;

                case "3":
                    System.out.println("🔐 Função de recuperação de senha ainda será implementada.");
                    break;

                default:
                    System.out.println("❌ Opção inválida.");
            }
        }
    }
}
