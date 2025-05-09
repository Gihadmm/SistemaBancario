package UI;

import java.util.Scanner;

import Model.Usuario;
import Model.Cliente;
import Model.Administrador;
import Model.Acesso;
import Service.LoginService;
import repository.UsuarioDAO;
import UI.MenuAdministrador;
import UI.MenuCliente;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        UsuarioDAO udao = new UsuarioDAO();

        int opcao;
        do {
            System.out.println("\n=== Sistema de Biblioteca ===");
            System.out.println("1. Login");
            System.out.println("2. Cadastro");
            System.out.println("3. Recuperar senha");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    // Login
                    System.out.print("E-mail: ");
                    String email = scanner.nextLine();
                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();

                    Usuario usuario = LoginService.realizarLogin(email, senha);
                    if (usuario == null) {
                        System.out.println("❌ Credenciais inválidas.");
                    } else if (usuario.getAcesso() == Acesso.ADMINISTRADOR) {
                        MenuAdministrador.executar(scanner, (Administrador) usuario);
                    } else {
                        MenuCliente.executar(scanner, (Cliente) usuario);
                    }
                    break;

                case 2:
                    // Cadastro
                    // Se for o primeiro usuário, força ADM; senão, cadastra CLIENTE
                    if (udao.countUsuarios() == 0) {
                        System.out.println(">>> Primeiro usuário: cadastro de ADMINISTRADOR <<<");
                        cadastrarADM(scanner, udao);
                    } else {
                        System.out.println(">>> Cadastro de CLIENTE <<<");
                        cadastrarCliente(scanner, udao);
                    }
                    break;

                case 3:
                    // Recuperar senha (exemplo simples que redefine para uma senha padrão)
                    System.out.print("Informe seu e-mail: ");
                    String mail = scanner.nextLine();
                    if (udao.atualizarSenha(mail, "senha@123")) {
                        System.out.println("✔️ Sua senha foi resetada para 'senha@123'. Por favor, altere após o login.");
                    } else {
                        System.out.println("❌ E-mail não encontrado.");
                    }
                    break;

                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void cadastrarADM(Scanner scanner, UsuarioDAO udao) throws Exception {
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Administrador adm = new Administrador(cpf, nome, email, senha);
        udao.inserirUsuario(adm);
        System.out.println("✔️ Administrador criado.");
    }

    private static void cadastrarCliente(Scanner scanner, UsuarioDAO udao) throws Exception {
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Cliente cli = new Cliente(cpf, nome, email, senha);
        udao.inserirUsuario(cli);
        System.out.println("✔️ Cliente criado.");
    }
}
