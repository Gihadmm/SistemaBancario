package UI;

import Model.Usuario;
import Model.Cliente;
import Model.Administrador;
import Service.EmailService;
import repository.UsuarioDAO;
import repository.LivroDAO;
import repository.EmprestimoDAO;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Classe principal que inicia o sistema da biblioteca.
 * Responsável por apresentar o menu inicial e rotear o usuário para login, cadastro ou recuperação de senha.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        LivroDAO livroDAO = new LivroDAO();
        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

        while (true) {
            // Menu principal
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
                    // Encerra o sistema
                    System.out.println("👋 Encerrando sistema. Até mais!");
                    scanner.close();
                    return;

                case "1":
                    // Login de usuário por CPF e senha
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Senha: ");
                    String senha = scanner.nextLine();

                    Usuario user = usuarioDAO.buscarPorCpf(cpf);
                    if (user == null || !user.getSenha().equals(senha)) {
                        System.out.println("❌ Usuário ou senha inválidos.");
                        break;
                    }

                    // Direciona para menu conforme tipo de usuário
                    if (user.getAcesso() == Model.Acesso.ADMINISTRADOR) {
                        System.out.println("✔️ Bem-vindo ADM, " + user.getNome());
                        MenuAdministrador.executar(scanner, livroDAO, usuarioDAO, emprestimoDAO);
                    } else {
                        System.out.println("✔️ Bem-vindo, " + user.getNome());
                        MenuCliente.executar(scanner, (Cliente) user);
                    }
                    break;

                case "2":
                    // Cadastro de novo cliente
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
                    // Recuperação de senha por CPF ou Email
                    try {
                        System.out.println("\n🔐 Recuperação de Senha");
                        System.out.print("Deseja recuperar via (1) CPF ou (2) Email: ");
                        String escolha = scanner.nextLine();

                        Usuario usuario = null;

                        if (escolha.equals("1")) {
                            System.out.print("Digite seu CPF: ");
                            String cpfRec = scanner.nextLine();
                            usuario = usuarioDAO.buscarPorCpf(cpfRec);
                        } else if (escolha.equals("2")) {
                            System.out.print("Digite seu Email: ");
                            String emailRec = scanner.nextLine();
                            List<Usuario> usuarios = usuarioDAO.buscarPorEmail(emailRec);
                            if (!usuarios.isEmpty()) {
                                usuario = usuarios.get(0); // Assume e-mail único
                            }
                        } else {
                            System.out.println("❌ Opção inválida.");
                            break;
                        }

                        if (usuario == null) {
                            System.out.println("❌ Dados não encontrados.");
                        } else {
                            // Gera senha temporária e envia por e-mail
                            String novaSenha = UUID.randomUUID().toString().substring(0, 8);
                            usuario.setSenha(novaSenha);
                            usuarioDAO.atualizar(usuario);
                            EmailService.enviarNovaSenha(usuario.getEmail(), novaSenha);
                            System.out.println("✅ Nova senha provisória enviada para o e-mail cadastrado.");
                        }

                    } catch (Exception e) {
                        System.out.println("❌ Erro durante recuperação de senha: " + e.getMessage());
                    }
                    break;

                default:
                    System.out.println("❌ Opção inválida.");
            }
        }
    }
}
