package UI;


import Model.*;
import repository.LivroDAO;
import repository.EmprestimoDAO;
import repository.ReservaDAO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MenuCliente {

    public static void executar(Scanner scanner, Cliente cliente) {
        String opc;
        do {
            System.out.println("╔═══════════════════════════════════════════════════╗");
            System.out.println("║                  MENU DO CLIENTE                  ║");
            System.out.println("╠═══════════════════════════════════════════════════╣");
            System.out.println("║  1. Solicitar Empréstimo                          ║");
            System.out.println("║  2. Consultar Multas Ativas                       ║");
            System.out.println("║  3. Consultar Meus Dados                          ║");
            System.out.println("║  4. Solicitar Alteração de Dados                  ║");
            System.out.println("║  5. Solicitar Devolução                           ║");
            System.out.println("║  6. Solicitar Renovação                           ║");
            System.out.println("║  7. Consultar Livros (por título, autor, gênero)  ║");
            System.out.println("║  8. Realizar Reserva                              ║");
            System.out.println("║  9. Consultar Reservas                            ║");
            System.out.println("║ 10. Pagar Multa                                   ║");
            System.out.println("║  0. Logout                                        ║");
            System.out.println("╚═══════════════════════════════════════════════════╝");
            System.out.print ("Escolha uma opção: ");
            opc = scanner.nextLine();

            switch (opc) {
                case "1":
                    // --- fluxo de empréstimo ---
                    try {
                        System.out.print("ID do livro para empréstimo: ");
                        int idLivro = Integer.parseInt(scanner.nextLine());

                        // criamos as DAOs localmente
                        LivroDAO livroDAO = new LivroDAO();
                        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

                        Livro livro = livroDAO.buscarPorId(idLivro);
                        if (livro == null) {
                            System.out.println("❌ Livro não encontrado.");
                            break;
                        }
                        if (!livro.isDisponivel()) {
                            System.out.println("❌ Livro indisponível no momento.");
                            break;
                        }

                        // domínio: cria o empréstimo e já decrementa estoque em memória
                        Emprestimo emp = cliente.solicitarEmprestimo(livro);

                        // persistência:
                        emprestimoDAO.inserir(emp);
                        livroDAO.atualizar(livro);

                        System.out.println("✔️ Empréstimo realizado com sucesso!");
                        System.out.printf("Data prevista de devolução: %s%n",
                                emp.getDataDevolucaoPrevista());

                    } catch (NumberFormatException e) {
                        System.out.println("❌ ID inválido.");
                    } catch (SQLException e) {
                        System.err.println("❌ Erro no banco: " + e.getMessage());
                    }
                    break;
                case "2":
                    try {
                        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
                        List<Emprestimo> emprestimos = emprestimoDAO.listarPorCliente(cliente.getCpf());

                        boolean encontrou = false;
                        for (Emprestimo e : emprestimos) {
                            if (e.getValorMulta() > 0 && !e.isMultaPaga()) {
                                System.out.printf("📚 Livro: %s | Multa: R$ %.2f%n",
                                        e.getLivro().getTitulo(), e.getValorMulta());
                                encontrou = true;
                            }
                        }
                        if (!encontrou) System.out.println("✅ Nenhuma multa ativa.");
                    } catch (SQLException e) {
                        System.err.println("Erro ao consultar multas: " + e.getMessage());
                    }
                    break;

                case "3":
                    // consultarDados()
                    break;
                case "4":
                    // solicitarAlteracaoDados()
                    break;
                case "5":
                    // solicitarDevolucao()
                    break;
                case "6":
                    // solicitarRenovacao()
                    break;
                case "7":
                    // consultarLivros()
                    break;
                case "8":
                    try {
                        System.out.print("ID do livro a reservar: ");
                        int idLivro = Integer.parseInt(scanner.nextLine());

                        Livro livro = new LivroDAO().buscarPorId(idLivro);
                        if (livro == null) {
                            System.out.println("❌ Livro não encontrado.");
                            break;
                        }
                        if (livro.isDisponivel()) {
                            System.out.println("📗 O livro está disponível! Você pode realizar o empréstimo.");
                            break;
                        }

                        // 1) Buscar todos os empréstimos desse livro e achar o ATIVO com dataMaisProxima
                        EmprestimoDAO empDAO = new EmprestimoDAO();
                        List<Emprestimo> emprestimos = empDAO.listarPorLivro(idLivro);

                        Date hoje = new Date();
                        Date dataDisp = hoje;
                        for (Emprestimo e : emprestimos) {
                            if (e.getStatus() == StatusEmprestimo.ATIVO) {
                                Date prevista = e.getDataDevolucaoPrevista();
                                if (dataDisp.equals(hoje) || prevista.before(dataDisp)) {
                                    dataDisp = prevista;
                                }
                            }
                        }

                        // 2) Criar reserva com a data de disponibilidade prevista
                        Reserva r = new Reserva(cliente, livro, hoje, dataDisp);

                        // 3) Persistir no banco
                        new ReservaDAO().inserir(r);

                        System.out.println("✔️ Reserva registrada com sucesso!");
                        System.out.printf("📅 Disponível em: %s%n", dataDisp);

                    } catch (NumberFormatException ex) {
                        System.out.println("❌ ID inválido.");
                    } catch (Exception e) {
                        System.err.println("Erro ao reservar: " + e.getMessage());
                    }
                    break;


                case "9":
                    try {
                        List<Reserva> reservas = new ReservaDAO().listarPorCliente(cliente.getCpf());
                        if (reservas.isEmpty()) {
                            System.out.println("📭 Nenhuma reserva encontrada.");
                        } else {
                            for (Reserva r : reservas) {
                                System.out.printf("📚 Livro: %s | Reservado em: %s%n",
                                        r.getLivro().getTitulo(), r.getDataReserva());
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Erro ao consultar reservas: " + e.getMessage());
                    }
                    break;

                case "10":
                    try {
                        System.out.print("ID do empréstimo para pagar multa: ");
                        int id = Integer.parseInt(scanner.nextLine());

                        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
                        Emprestimo e = emprestimoDAO.buscarPorId(id);

                        if (e == null || !e.getCliente().getCpf().equals(cliente.getCpf())) {
                            System.out.println("❌ Empréstimo não encontrado.");
                            break;
                        }
                        if (e.getValorMulta() == 0 || e.isMultaPaga()) {
                            System.out.println("✅ Nenhuma multa pendente neste empréstimo.");
                            break;
                        }

                        e.pagarMulta();
                        emprestimoDAO.atualizarDevolucao(e); // atualiza multaPaga=true
                        System.out.println("✔️ Multa paga com sucesso!");
                    } catch (Exception e) {
                        System.out.println("❌ Erro ao pagar multa: " + e.getMessage());
                    }
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
