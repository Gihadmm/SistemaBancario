package UI;

import Model.Cliente;
import Model.Administrador;
import Model.Livro;
import Model.Emprestimo;
import Model.StatusEmprestimo;
import repository.LivroDAO;
import repository.UsuarioDAO;
import repository.EmprestimoDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuAdministrador {

    public static void executar(Scanner scanner,
                                LivroDAO livroDAO,
                                UsuarioDAO usuarioDAO,
                                EmprestimoDAO emprestimoDAO) {
        String opc;
        do {
            System.out.println("╔═════════════════════════════════════════════╗");
            System.out.println("║           MENU DO ADMINISTRADOR             ║");
            System.out.println("╠═════════════════════════════════════════════╣");
            System.out.println("║ 1.  Cadastrar Livro                         ║");
            System.out.println("║ 2.  Atualizar Livro                         ║");
            System.out.println("║ 3.  Remover Livro                           ║");
            System.out.println("║ 4.  Consultar Livro por Título/Autor/Gênero ║");
            System.out.println("║ 5.  Cadastrar Cliente                       ║");
            System.out.println("║ 6.  Consultar Cliente                       ║");
            System.out.println("║ 7.  Alterar Dados de Cliente                ║");
            System.out.println("║ 8.  Cadastrar Administrador                 ║");
            System.out.println("║ 9.  Realizar Empréstimo                     ║");
            System.out.println("║ 10. Realizar Devolução                      ║");
            System.out.println("║ 11. Realizar Renovação                      ║");
            System.out.println("║ 12. Gerar Relatório de Multas               ║");
            System.out.println("║ 13. Relatório de Empréstimos por Cliente    ║");
            System.out.println("║ 14. Pagamento de Multa                      ║");
            System.out.println("║ 0.  Logout                                  ║");
            System.out.println("╚═════════════════════════════════════════════╝");
            System.out.print("Escolha uma opção: ");
            opc = scanner.nextLine();

            switch (opc) {
                case "1": // Cadastrar Livro
                    try {
                        System.out.print("Título: ");
                        String t = scanner.nextLine();
                        System.out.print("Autor: ");
                        String a = scanner.nextLine();
                        System.out.print("Gênero: ");
                        String g = scanner.nextLine();
                        System.out.print("Ano Pub.: ");
                        int ano = Integer.parseInt(scanner.nextLine());
                        System.out.print("Editora: ");
                        String ed = scanner.nextLine();
                        System.out.print("ISBN: ");
                        String is = scanner.nextLine();
                        System.out.print("Qtd exemplares: ");
                        int q = Integer.parseInt(scanner.nextLine());

                        Livro l = new Livro(0, t, a, g, ano, ed, is, q);
                        livroDAO.inserir(l);
                        System.out.println("✔️ Livro cadastrado. ID=" + l.getId());
                    } catch (Exception e) {
                        System.err.println("❌ Falha ao cadastrar livro: " + e.getMessage());
                    }
                    break;

                case "2": // Atualizar Livro
                    try {
                        System.out.print("ID do livro: ");
                        int idU = Integer.parseInt(scanner.nextLine());
                        Livro lu = livroDAO.buscarPorId(idU);
                        if (lu == null) {
                            System.out.println("❌ Livro não encontrado.");
                            break;
                        }
                        System.out.print("Novo título [" + lu.getTitulo() + "]: ");
                        String nt = scanner.nextLine();
                        if (!nt.isBlank()) lu.setTitulo(nt);
                        System.out.print("Novo autor [" + lu.getAutor() + "]: ");
                        String na = scanner.nextLine();
                        if (!na.isBlank()) lu.setAutor(na);
                        // ... repita para os outros campos conforme desejar ...
                        livroDAO.atualizar(lu);
                        System.out.println("✔️ Livro atualizado.");
                    } catch (Exception e) {
                        System.err.println("❌ Falha ao atualizar livro: " + e.getMessage());
                    }
                    break;

                case "3": // Remover Livro
                    try {
                        System.out.print("ID do livro: ");
                        int idR = Integer.parseInt(scanner.nextLine());
                        livroDAO.deletar(idR);
                        System.out.println("✔️ Livro removido.");
                    } catch (Exception e) {
                        System.err.println("❌ Falha ao remover livro: " + e.getMessage());
                    }
                    break;

                case "4": // Consultar Livro
                    try {
                        System.out.print("Buscar por (1) Título, (2) Autor, (3) Gênero: ");
                        String f = scanner.nextLine();
                        List<Livro> res;
                        switch (f) {
                            case "1":
                                System.out.print("Título: ");
                                res = livroDAO.buscarPorTitulo(scanner.nextLine());
                                break;
                            case "2":
                                System.out.print("Autor: ");
                                res = livroDAO.buscarPorAutor(scanner.nextLine());
                                break;
                            case "3":
                                System.out.print("Gênero: ");
                                res = livroDAO.buscarPorGenero(scanner.nextLine());
                                break;
                            default:
                                System.out.println("❌ Filtro inválido.");
                                continue;
                        }
                        if (res.isEmpty()) {
                            System.out.println("❌ Nenhum livro encontrado.");
                        } else {
                            res.forEach(lv ->
                                    System.out.printf("ID:%d | %s — %s [%s] (%d disp.)%n",
                                            lv.getId(), lv.getTitulo(), lv.getAutor(),
                                            lv.getGenero(), lv.getQuantidadeExemplares())
                            );
                        }
                    } catch (SQLException e) {
                        System.err.println("❌ Erro no banco: " + e.getMessage());
                    }
                    break;

                case "5": // Cadastrar Cliente
                    try {
                        System.out.print("CPF: ");
                        String cpf = scanner.nextLine();
                        System.out.print("Nome: ");
                        String nm = scanner.nextLine();
                        System.out.print("Email: ");
                        String em = scanner.nextLine();
                        System.out.print("Senha: ");
                        String sn = scanner.nextLine();
                        Cliente cli = new Cliente(cpf, nm, em, sn);
                        usuarioDAO.inserirCliente(cli);
                        System.out.println("✔️ Cliente cadastrado.");
                    } catch (Exception e) {
                        System.err.println("❌ Falha ao cadastrar cliente: " + e.getMessage());
                    }
                    break;

                case "6": // Consultar Cliente
                    try {
                        System.out.print("CPF do cliente: ");
                        String cpfC = scanner.nextLine();
                        Cliente c6 = (Cliente) usuarioDAO.buscarPorCpf(cpfC);
                        if (c6 == null) {
                            System.out.println("❌ Cliente não encontrado.");
                        } else {
                            System.out.printf("CPF:%s | Nome:%s | Email:%s%n",
                                    c6.getCpf(), c6.getNome(), c6.getEmail());
                        }
                    } catch (SQLException e) {
                        System.err.println("❌ Erro no banco: " + e.getMessage());
                    }
                    break;

                case "7":
                    // alterarDadosCliente()
                    System.out.println("📌 Função de alterar dados de cliente ainda não implementada.");
                    break;

                case "8": // Cadastrar Administrador
                    try {
                        System.out.print("CPF: ");
                        String cpfA = scanner.nextLine();
                        System.out.print("Nome: ");
                        String naA = scanner.nextLine();
                        System.out.print("Email: ");
                        String emA = scanner.nextLine();
                        System.out.print("Senha: ");
                        String snA = scanner.nextLine();
                        // você precisa criar este método em UsuarioDAO:
                        usuarioDAO.inserirAdministrador(
                                new Administrador(cpfA, naA, emA, snA)
                        );
                        System.out.println("✔️ Administrador cadastrado.");
                    } catch (Exception e) {
                        System.err.println("❌ Falha ao cadastrar ADM: " + e.getMessage());
                    }
                    break;

                case "9": // Realizar Empréstimo (livro, CPF)
                    try {
                        System.out.print("CPF do cliente: ");
                        String cpf9 = scanner.nextLine();
                        Cliente c9 = (Cliente) usuarioDAO.buscarPorCpf(cpf9);
                        System.out.print("ID do livro: ");
                        int id9 = Integer.parseInt(scanner.nextLine());
                        Livro l9 = livroDAO.buscarPorId(id9);
                        if (c9 == null || l9 == null || !l9.isDisponivel()) {
                            System.out.println("❌ Dados inválidos para empréstimo.");
                            break;
                        }
                        Emprestimo e9 = c9.solicitarEmprestimo(l9);
                        emprestimoDAO.inserir(e9);
                        livroDAO.atualizar(l9);
                        System.out.println("✔️ Empréstimo registrado.");
                    } catch (Exception e) {
                        System.err.println("❌ Erro: " + e.getMessage());
                    }
                    break;

                case "10": // Realizar Devolução
                    try {
                        System.out.print("ID do empréstimo: ");
                        int id10 = Integer.parseInt(scanner.nextLine());
                        Emprestimo e10 = emprestimoDAO.buscarPorId(id10);
                        if (e10 == null || e10.getStatus() != StatusEmprestimo.ATIVO) {
                            System.out.println("❌ Empréstimo inválido.");
                            break;
                        }
                        e10.devolver();
                        emprestimoDAO.atualizarDevolucao(e10);
                        livroDAO.atualizar(e10.getLivro());
                        System.out.println("✔️ Devolução registrada. Multa: R$ " + e10.getValorMulta());
                    } catch (Exception e) {
                        System.err.println("❌ Erro: " + e.getMessage());
                    }
                    break;

                case "11": // Realizar Renovação
                    try {
                        System.out.print("ID do empréstimo: ");
                        int id11 = Integer.parseInt(scanner.nextLine());
                        Emprestimo e11 = emprestimoDAO.buscarPorId(id11);
                        if (e11 == null || e11.getStatus() != StatusEmprestimo.ATIVO) {
                            System.out.println("❌ Empréstimo inválido.");
                            break;
                        }
                        e11.renovar(7); // +7 dias
                        emprestimoDAO.atualizarRenovacao(e11);
                        System.out.println("✔️ Renovação até: " + e11.getDataDevolucaoPrevista());
                    } catch (Exception e) {
                        System.err.println("❌ Erro: " + e.getMessage());
                    }
                    break;

                case "12":
                    // gerarRelatorioMultas()
                    System.out.println("📌 Função de relatório de multas não implementada.");
                    break;

                case "13":
                    // gerarRelatorioEmprestimos()
                    System.out.println("📌 Função de relatório de empréstimos não implementada.");
                    break;

                case "14":
                    // realizarPagamentoMulta()
                    System.out.println("📌 Função de pagamento de multa não implementada.");
                    break;

                case "0":
                    System.out.println("Logout efetuado.");
                    break;

                default:
                    System.out.println("❌ Opção inválida.");
            }

        } while (!opc.equals("0"));
    }
}
