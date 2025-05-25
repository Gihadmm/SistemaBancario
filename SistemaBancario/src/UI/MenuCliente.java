package UI;

import Model.*;
import repository.LivroDAO;
import repository.EmprestimoDAO;
import repository.ReservaDAO;
import repository.UsuarioDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;


public class MenuCliente {

    public static void executar(Scanner scanner, Cliente cliente) {
        String opc;
        do {
            System.out.println("╔═══════════════════════════════════════════════════╗");
            System.out.println("║                  MENU DO CLIENTE                  ║");
            System.out.println("╠═══════════════════════════════════════════════════╣");
            System.out.println("║  1. Consultar Multas Ativas                       ║");
            System.out.println("║  2. Consultar Meus Dados                          ║");
            System.out.println("║  3. Solicitar Alteração de Dados                  ║");
            System.out.println("║  4. Consultar Livros (por título, autor, gênero)  ║");
            System.out.println("║  5. Realizar Reserva                              ║");
            System.out.println("║  6. Consultar Reservas                            ║");
            System.out.println("║  7. Pagar Multa                                   ║");
            System.out.println("║  0. Logout                                        ║");
            System.out.println("╚═══════════════════════════════════════════════════╝");
            System.out.print ("Escolha uma opção: ");
            opc = scanner.nextLine();

            switch (opc) {
                case "1":
                    try {
                        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
                        List<Emprestimo> emprestimos = emprestimoDAO.listarPorCliente(cliente.getCpf());

                        List<Emprestimo> multas = new ArrayList<>();

                        for (Emprestimo e : emprestimos) {
                            if (e.getValorMulta() > 0) {
                                multas.add(e);
                            }
                        }

                        if (multas.isEmpty()) {
                            System.out.println("✅ Nenhuma multa registrada.");
                            break;
                        }

                        System.out.println("\n📋 Multas registradas:");
                        for (Emprestimo e : multas) {
                            String devolvido = (e.getDataDevolucaoReal() != null) ? "✔️ Devolvido" : "⏳ Em aberto";
                            System.out.printf("ID: %d | Livro: %s | Valor: R$ %.2f | %s\n",
                                    e.getId(), e.getLivro().getTitulo(), e.getValorMulta(), devolvido);
                        }

                        System.out.println("\nℹ️ Para realizar o pagamento, por favor, dirija-se ao bibliotecário.");
                    } catch (SQLException e) {
                        System.err.println("Erro ao consultar multas: " + e.getMessage());
                    }
                    break;
                case "2":
                    System.out.println("\n📋 Seus dados:");
                    System.out.println("Nome:  " + cliente.getNome());
                    System.out.println("CPF:   " + cliente.getCpf());
                    System.out.println("Email: " + cliente.getEmail());
                    break;

                case "3":
                    System.out.println("\n🔧 Qual dado deseja alterar?");
                    System.out.println("1. Email");
                    System.out.println("2. Senha");
                    System.out.print("Escolha: ");
                    String escolha = scanner.nextLine();

                    boolean alterarEmail = false;
                    boolean alterarSenha = false;

                    if (escolha.equals("1")) {
                        alterarEmail = true;
                    } else if (escolha.equals("2")) {
                        alterarSenha = true;
                    } else {
                        System.out.println("❌ Opção inválida.");
                        break;
                    }

                    // Solicita a senha atual para qualquer alteração
                    System.out.print("Digite sua senha atual: ");
                    String senhaAtual = scanner.nextLine();
                    if (!senhaAtual.equals(cliente.getSenha())) {
                        System.out.println("❌ Senha incorreta. Cancelando alteração.");
                        break;
                    }

                    if (alterarEmail) {
                        System.out.print("Novo email: ");
                        String novoEmail = scanner.nextLine();
                        if (!novoEmail.isBlank()) cliente.setEmail(novoEmail);
                    }
                    if (alterarSenha) {
                        System.out.print("Nova senha: ");
                        String novaSenha = scanner.nextLine();
                        if (!novaSenha.isBlank()) cliente.setSenha(novaSenha);
                    }

                    System.out.print("Deseja salvar as alterações? (s/n): ");
                    String confirmacao = scanner.nextLine();
                    if (!confirmacao.equalsIgnoreCase("s")) {
                        System.out.println("❌ Alteração cancelada.");
                        break;
                    }

                    try {
                        new UsuarioDAO().atualizar(cliente);
                        System.out.println("✔️ Dados atualizados com sucesso!");
                    } catch (SQLException e) {
                        System.err.println("❌ Erro ao salvar dados: " + e.getMessage());
                    }
                    break;


                case "4":
                    try {
                        LivroDAO livroDAO = new LivroDAO();
                        System.out.print("Buscar por (1) Título, (2) Autor, (3) Gênero: ");
                        String filtro = scanner.nextLine();
                        List<Livro> resultados = null;

                        switch (filtro) {
                            case "1":
                                System.out.print("Título: ");
                                resultados = livroDAO.buscarPorTitulo(scanner.nextLine());
                                break;
                            case "2":
                                System.out.print("Autor: ");
                                resultados = livroDAO.buscarPorAutor(scanner.nextLine());
                                break;
                            case "3":
                                System.out.print("Gênero: ");
                                resultados = livroDAO.buscarPorGenero(scanner.nextLine());
                                break;
                            default:
                                System.out.println("❌ Filtro inválido.");
                        }

                        if (resultados == null || resultados.isEmpty()) {
                            System.out.println("⚠️ Nenhum livro encontrado.");
                        } else {
                            System.out.println("\n📚 Resultados:");
                            for (Livro l : resultados) {
                                String status = l.isDisponivel() ? "Disponível" : "Indisponível";
                                System.out.printf("ID:%d | %s — %s [%s] | %s\n",
                                        l.getId(), l.getTitulo(), l.getAutor(), l.getGenero(), status);
                            }
                            System.out.println("\nℹ️ Para realizar o empréstimo, procure o bibliotecário.");
                        }

                    } catch (Exception e) {
                        System.err.println("❌ Erro na busca de livros: " + e.getMessage());
                    }
                    break;

                case "5": // Realizar Reserva
                    try {
                        LivroDAO livroDAO = new LivroDAO();
                        EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

                        System.out.print("Buscar por (1) Título ou (2) Autor: ");
                        String tipo = scanner.nextLine();
                        List<Livro> encontrados;

                        if (tipo.equals("1")) {
                            System.out.print("Parte do título: ");
                            encontrados = livroDAO.buscarPorTitulo(scanner.nextLine());
                        } else if (tipo.equals("2")) {
                            System.out.print("Parte do autor: ");
                            encontrados = livroDAO.buscarPorAutor(scanner.nextLine());
                        } else {
                            System.out.println("❌ Opção inválida.");
                            break;
                        }

                        if (encontrados.isEmpty()) {
                            System.out.println("❌ Nenhum livro encontrado.");
                            break;
                        }

                        // Ordenar: indisponíveis primeiro
                        encontrados.sort(Comparator.comparing(Livro::isDisponivel));

                        System.out.println("\n📚 Livros encontrados:");
                        for (int i = 0; i < encontrados.size(); i++) {
                            Livro l = encontrados.get(i);
                            String status = l.isDisponivel() ? "Indisponível para reserva" : "Disponível para reserva";
                            System.out.printf("[%d] %s (%s) - %s\n", i + 1, l.getTitulo(), l.getAutor(), status);
                        }

                        System.out.print("\nDigite o número do livro que deseja reservar: ");
                        int indiceLivro = Integer.parseInt(scanner.nextLine());
                        if (indiceLivro < 1 || indiceLivro > encontrados.size()) {
                            System.out.println("❌ Escolha inválida.");
                            break;
                        }

                        Livro selecionado = encontrados.get(indiceLivro - 1);
                        if (selecionado.isDisponivel()) {
                            System.out.println("📗 O livro está disponível para empréstimo. Dirija-se ao bibliotecário para realizar o empréstimo.");
                            break;
                        }

                        List<Emprestimo> emprestimos = emprestimoDAO.listarPorLivro(selecionado.getId());

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

                        Reserva r = new Reserva(cliente, selecionado, hoje, dataDisp);
                        new ReservaDAO().inserir(r);

                        System.out.println("✔️ Reserva registrada com sucesso!");
                        System.out.printf("📅 Estimativa de disponibilidade: %s%n", dataDisp);

                    } catch (Exception e) {
                        System.err.println("Erro ao reservar: " + e.getMessage());
                    }
                    break;

                case "6": // Consultar Minhas Reservas
                    try {
                        List<Reserva> reservas = new ReservaDAO().listarPorCliente(cliente.getCpf());
                        if (reservas.isEmpty()) {
                            System.out.println("📭 Nenhuma reserva encontrada.");
                        } else {
                            for (Reserva r : reservas) {
                                System.out.printf("📚 Livro: %s | Reservado em: %s | Estimado disponível: %s%n",
                                        r.getLivro().getTitulo(), r.getDataReserva(), r.getDataDisponibilidadePrevista());
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Erro ao consultar reservas: " + e.getMessage());
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
