package UI;

import Model.Cliente;
import Model.Administrador;
import Model.Livro;
import Model.Emprestimo;
import Model.StatusEmprestimo;
import  Model.Usuario;
import repository.LivroDAO;
import repository.UsuarioDAO;
import repository.EmprestimoDAO;

import java.sql.SQLException;
import java.util.ArrayList;
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
            System.out.println("║ 4.  Consultar Livro                         ║");
            System.out.println("║ 5.  Cadastrar Cliente                       ║");
            System.out.println("║ 6.  Consultar Usuário                       ║");
            System.out.println("║ 7.  Alterar Dados de Usuário                ║");
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
                        System.out.print("Digite parte do título do livro para buscar: ");
                        String termo = scanner.nextLine();
                        List<Livro> resultados = livroDAO.buscarPorTitulo(termo);

                        if (resultados.isEmpty()) {
                            System.out.println("❌ Nenhum livro encontrado com esse título.");
                            break;
                        }

                        Livro livroEscolhido = null;
                        if (resultados.size() == 1) {
                            livroEscolhido = resultados.get(0);
                        } else {
                            System.out.println("📚 Livros encontrados:");
                            for (int i = 0; i < resultados.size(); i++) {
                                Livro l = resultados.get(i);
                                System.out.printf("[%d] %s (%s)\n", i + 1, l.getTitulo(), l.getAutor());
                            }

                            System.out.print("Digite o número do livro que deseja atualizar: ");
                            int escolha = Integer.parseInt(scanner.nextLine());
                            if (escolha < 1 || escolha > resultados.size()) {
                                System.out.println("❌ Escolha inválida.");
                                break;
                            }
                            livroEscolhido = resultados.get(escolha - 1);
                        }

                        // Atualização dos campos
                        System.out.print("Novo título [" + livroEscolhido.getTitulo() + "]: ");
                        String nt = scanner.nextLine();
                        if (!nt.isBlank()) livroEscolhido.setTitulo(nt);

                        System.out.print("Novo autor [" + livroEscolhido.getAutor() + "]: ");
                        String na = scanner.nextLine();
                        if (!na.isBlank()) livroEscolhido.setAutor(na);

                        // Adicione aqui os outros campos que desejar atualizar

                        livroDAO.atualizar(livroEscolhido);
                        System.out.println("✔️ Livro atualizado.");
                    } catch (Exception e) {
                        System.err.println("❌ Falha ao atualizar livro: " + e.getMessage());
                    }
                    break;

                case "3": // Remover Livro
                    try {
                        System.out.print("Digite parte do título do livro a remover: ");
                        String tituloBusca = scanner.nextLine();

                        List<Livro> encontrados = livroDAO.buscarPorTitulo(tituloBusca);
                        if (encontrados.isEmpty()) {
                            System.out.println("❌ Nenhum livro encontrado com esse título.");
                            break;
                        }

                        Livro livroEscolhido = null;
                        if (encontrados.size() == 1) {
                            livroEscolhido = encontrados.get(0);
                        } else {
                            System.out.println("📚 Livros encontrados:");
                            for (int i = 0; i < encontrados.size(); i++) {
                                Livro l = encontrados.get(i);
                                System.out.printf("[%d] %s (%s)\n", i + 1, l.getTitulo(), l.getAutor());
                            }

                            System.out.print("Digite o número do livro que deseja remover: ");
                            int escolha = Integer.parseInt(scanner.nextLine());
                            if (escolha < 1 || escolha > encontrados.size()) {
                                System.out.println("❌ Escolha inválida.");
                                break;
                            }
                            livroEscolhido = encontrados.get(escolha - 1);
                        }

                        int idLivro = livroEscolhido.getId();

                        // Verifica se há empréstimos ativos
                        if (new EmprestimoDAO().livroPossuiEmprestimoAtivo(idLivro)) {
                            System.out.println("❌ Este livro possui empréstimos ativos e não pode ser removido.");
                            break;
                        }

                        livroDAO.deletar(idLivro);
                        System.out.println("✔️ Livro removido com sucesso.");
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
                            System.out.println("📚 Livros encontrados:");
                            for (int i = 0; i < res.size(); i++) {
                                Livro l = res.get(i);
                                System.out.printf("[%d] %s (%s) — %s | %d disponíveis\n",
                                        i + 1, l.getTitulo(), l.getAutor(), l.getGenero(), l.getQuantidadeExemplares());
                            }

                            System.out.print("\nDigite o número do livro para ver detalhes (ou 0 para cancelar): ");
                            int escolha = Integer.parseInt(scanner.nextLine());

                            if (escolha > 0 && escolha <= res.size()) {
                                Livro selecionado = res.get(escolha - 1);
                                System.out.println("\n📖 Detalhes do Livro:");
                                System.out.println("ID: " + selecionado.getId());
                                System.out.println("Título: " + selecionado.getTitulo());
                                System.out.println("Autor: " + selecionado.getAutor());
                                System.out.println("Gênero: " + selecionado.getGenero());
                                System.out.println("Ano de Publicação: " + selecionado.getAnoPublicacao());
                                System.out.println("Editora: " + selecionado.getEditora());
                                System.out.println("ISBN: " + selecionado.getIsbn());
                                System.out.println("Exemplares disponíveis: " + selecionado.getQuantidadeExemplares());
                                System.out.println("Disponível para empréstimo: " + (selecionado.isDisponivel() ? "Sim" : "Não"));
                            } else if (escolha != 0) {
                                System.out.println("❌ Opção inválida.");
                            }
                        }
                    } catch (SQLException e) {
                        System.err.println("❌ Erro no banco: " + e.getMessage());
                    } catch (NumberFormatException e) {
                        System.err.println("❌ Entrada inválida. Digite um número.");
                    }
                    break;


                case "5": // Cadastrar Cliente
                    try {
                        System.out.println("\n👤 Cadastro de Novo Cliente");
                        System.out.println("---------------------------");

                        System.out.print("Digite o CPF do cliente: ");
                        String cpf = scanner.nextLine();

                        System.out.print("Digite o nome completo do cliente: ");
                        String nome = scanner.nextLine();

                        System.out.print("Digite o email do cliente: ");
                        String email = scanner.nextLine();

                        System.out.print("Digite uma senha para o cliente: ");
                        String senha = scanner.nextLine();

                        Cliente novoCliente = new Cliente(cpf, nome, email, senha);
                        usuarioDAO.inserirCliente(novoCliente);

                        System.out.println("\n✅ Cliente cadastrado com sucesso!");
                        System.out.printf("📄 CPF: %s | Nome: %s | Email: %s%n", cpf, nome, email);

                    } catch (Exception e) {
                        System.err.println("❌ Falha ao cadastrar cliente: " + e.getMessage());
                    }
                    break;


                case "6": // Consultar Usuário
                    try {
                        System.out.println("\n🔍 Consultar Usuário");
                        System.out.println("----------------------");
                        System.out.print("Buscar por (1) CPF, (2) Nome, (3) Email: ");
                        String filtro = scanner.nextLine();

                        List<Usuario> encontrados = new ArrayList<>();

                        switch (filtro) {
                            case "1":
                                System.out.print("Digite o CPF: ");
                                String cpfBusca = scanner.nextLine();
                                Usuario porCpf = usuarioDAO.buscarPorCpf(cpfBusca);
                                if (porCpf != null) encontrados.add(porCpf);
                                break;
                            case "2":
                                System.out.print("Digite parte do nome: ");
                                String nomeBusca = scanner.nextLine();
                                encontrados = usuarioDAO.buscarPorNome(nomeBusca);
                                break;
                            case "3":
                                System.out.print("Digite parte do email: ");
                                String emailBusca = scanner.nextLine();
                                encontrados = usuarioDAO.buscarPorEmail(emailBusca);
                                break;
                            default:
                                System.out.println("❌ Opção inválida.");
                                break;
                        }

                        if (encontrados.isEmpty()) {
                            System.out.println("❌ Nenhum usuário encontrado.");
                        } else {
                            System.out.println("\n👥 Usuários encontrados:");
                            for (int i = 0; i < encontrados.size(); i++) {
                                Usuario u = encontrados.get(i);
                                String tipo = (u instanceof Cliente) ? "Cliente" : "Administrador";
                                System.out.printf("[%d] %s | %s | %s (%s)%n",
                                        i + 1, u.getCpf(), u.getNome(), u.getEmail(), tipo);
                            }
                        }

                    } catch (SQLException e) {
                        System.err.println("❌ Erro no banco: " + e.getMessage());
                    }
                    break;


                case "7": // Alterar Dados de Usuário
                    try {
                        System.out.print("Buscar por (1) CPF, (2) Nome, (3) Email: ");
                        String f7 = scanner.nextLine();
                        List<Usuario> encontrados7 = new ArrayList<>();
                        switch (f7) {
                            case "1":
                                System.out.print("CPF: ");
                                Usuario uCpf = usuarioDAO.buscarPorCpf(scanner.nextLine());
                                if (uCpf != null) encontrados7.add(uCpf);
                                break;
                            case "2":
                                System.out.print("Nome: ");
                                encontrados7 = usuarioDAO.buscarPorNome(scanner.nextLine());
                                break;
                            case "3":
                                System.out.print("Email: ");
                                encontrados7 = usuarioDAO.buscarPorEmail(scanner.nextLine());
                                break;
                            default:
                                System.out.println("❌ Filtro inválido.");
                                break;
                        }

                        if (encontrados7.isEmpty()) {
                            System.out.println("❌ Nenhum usuário encontrado.");
                            break;
                        }

                        // Exibir resultados
                        System.out.println("📋 Usuários encontrados:");
                        for (int i = 0; i < encontrados7.size(); i++) {
                            Usuario u = encontrados7.get(i);
                            System.out.printf("[%d] %s | %s | %s (%s)%n",
                                    i + 1, u.getCpf(), u.getNome(), u.getEmail(), u.getAcesso());
                        }

                        System.out.print("Selecione o número do usuário a alterar: ");
                        int idx = Integer.parseInt(scanner.nextLine()) - 1;
                        if (idx < 0 || idx >= encontrados7.size()) {
                            System.out.println("❌ Índice inválido.");
                            break;
                        }

                        Usuario selecionado = encontrados7.get(idx);
                        boolean alterando = true;

                        while (alterando) {
                            System.out.printf("\n🔧 Editando usuário %s (%s)%n", selecionado.getNome(), selecionado.getCpf());
                            System.out.println("1. Alterar Nome");
                            System.out.println("2. Alterar Email");
                            System.out.println("3. Alterar Senha");
                            System.out.println("0. Finalizar alterações");
                            System.out.print("Escolha: ");
                            String alt = scanner.nextLine();

                            switch (alt) {
                                case "1":
                                    System.out.print("Novo nome: ");
                                    String novoNome = scanner.nextLine();
                                    if (!novoNome.isBlank()) selecionado.setNome(novoNome);
                                    break;
                                case "2":
                                    System.out.print("Novo email: ");
                                    String novoEmail = scanner.nextLine();
                                    if (!novoEmail.isBlank()) selecionado.setEmail(novoEmail);
                                    break;
                                case "3":
                                    System.out.print("Nova senha: ");
                                    String novaSenha = scanner.nextLine();
                                    if (!novaSenha.isBlank()) selecionado.setSenha(novaSenha);
                                    break;
                                case "0":
                                    alterando = false;
                                    break;
                                default:
                                    System.out.println("❌ Opção inválida.");
                            }
                        }

                        // Atualizar no banco (só para cliente, pois adm não tem DAO separado)
                        if (selecionado instanceof Cliente) {
                            usuarioDAO.atualizarCliente((Cliente) selecionado);
                        } else {
                            usuarioDAO.atualizarAdministrador((Administrador) selecionado);
                        }

                        System.out.println("✔️ Dados atualizados com sucesso.");

                    } catch (Exception e) {
                        System.err.println("❌ Erro ao alterar dados: " + e.getMessage());
                    }
                    break;


                case "8": // Cadastrar Administrador
                    try {
                        System.out.println("\n🔐 Cadastro de Administrador");
                        System.out.println("⚠️  Atenção: usuários com perfil de administrador terão acesso completo ao sistema, incluindo dados sensíveis.");

                        System.out.print("CPF: ");
                        String cpfA = scanner.nextLine();

                        System.out.print("Nome: ");
                        String nomeA = scanner.nextLine();

                        System.out.print("Email: ");
                        String emailA = scanner.nextLine();

                        System.out.print("Senha: ");
                        String senhaA = scanner.nextLine();

                        Administrador novoADM = new Administrador(cpfA, nomeA, emailA, senhaA);
                        usuarioDAO.inserirAdministrador(novoADM);

                        System.out.println("✔️ Administrador cadastrado com sucesso!");
                    } catch (Exception e) {
                        System.err.println("❌ Falha ao cadastrar administrador: " + e.getMessage());
                    }
                    break;

                case "9": // Realizar Empréstimo (livro, CPF)
                    try {
                        System.out.println("🔍 Buscar cliente por:");
                        System.out.println("1. CPF");
                        System.out.println("2. Nome");
                        System.out.println("3. Email");
                        System.out.print("Opção: ");
                        String opBusca = scanner.nextLine();

                        List<Usuario> candidatos = new ArrayList<>(); // inicializado aqui!

                        switch (opBusca) {
                            case "1":
                                System.out.print("CPF: ");
                                Usuario u1 = usuarioDAO.buscarPorCpf(scanner.nextLine());
                                if (u1 != null) candidatos = List.of(u1);
                                break;
                            case "2":
                                System.out.print("Nome: ");
                                candidatos = usuarioDAO.buscarPorNome(scanner.nextLine());
                                break;
                            case "3":
                                System.out.print("Email: ");
                                candidatos = usuarioDAO.buscarPorEmail(scanner.nextLine());
                                break;
                            default:
                                System.out.println("❌ Opção inválida.");
                                break;
                        }

                        if (candidatos.isEmpty()) {
                            System.out.println("❌ Nenhum usuário encontrado.");
                            break;
                        }

                        System.out.println("👤 Usuários encontrados:");
                        for (int i = 0; i < candidatos.size(); i++) {
                            Usuario u = candidatos.get(i);
                            System.out.printf("[%d] %s — %s (%s)\n", i + 1, u.getNome(), u.getEmail(), u.getAcesso());
                        }

                        System.out.print("Selecione o número do usuário ou 0 para cancelar: ");
                        String entradaUsuario = scanner.nextLine();
                        if (entradaUsuario.equals("0")) {
                            System.out.println("🔙 Cancelado.");
                            break;
                        }

                        int ind = Integer.parseInt(entradaUsuario) - 1;
                        if (ind < 0 || ind >= candidatos.size()) {
                            System.out.println("❌ Número fora da lista.");
                            break;
                        }

                        Usuario uSel = candidatos.get(ind);
                        if (!(uSel instanceof Cliente)) {
                            System.out.println("❌ O usuário selecionado não é um cliente.");
                            break;
                        }

                        Cliente clienteSelecionado = (Cliente) uSel;

                        // Buscar livro
                        System.out.print("Título do livro para buscar: ");
                        List<Livro> livros = livroDAO.buscarPorTitulo(scanner.nextLine());

                        if (livros.isEmpty()) {
                            System.out.println("❌ Nenhum livro encontrado.");
                            break;
                        }

                        System.out.println("📚 Livros encontrados:");
                        for (int i = 0; i < livros.size(); i++) {
                            Livro l = livros.get(i);
                            System.out.printf("[%d] %s — %s (%d disponíveis)\n", i + 1, l.getTitulo(), l.getAutor(), l.getQuantidadeExemplares());
                        }

                        System.out.print("Selecione o número do livro ou 0 para cancelar: ");
                        String entradaLivro = scanner.nextLine();
                        if (entradaLivro.equals("0")) {
                            System.out.println("🔙 Cancelado.");
                            break;
                        }

                        int indiceLivro = Integer.parseInt(entradaLivro) - 1;
                        if (indiceLivro < 0 || indiceLivro >= livros.size()) {
                            System.out.println("❌ Número fora da lista.");
                            break;
                        }

                        Livro livroSelecionado = livros.get(indiceLivro);
                        if (!livroSelecionado.isDisponivel()) {
                            System.out.println("❌ Livro indisponível no momento.");
                            break;
                        }

                        // Realiza o empréstimo
                        Emprestimo emprestimo = clienteSelecionado.solicitarEmprestimo(livroSelecionado);
                        emprestimoDAO.inserir(emprestimo);
                        livroDAO.atualizar(livroSelecionado);

                        System.out.println("✔️ Empréstimo registrado com sucesso.");
                        System.out.printf("Devolução: %s\n", emprestimo.getDataDevolucaoPrevista());

                    } catch (Exception e) {
                        System.err.println("❌ Erro ao realizar empréstimo: " + e.getMessage());
                    }
                    break;


                case "10": // Realizar Devolução
                    try {
                        System.out.print("🔍 Buscar cliente por (1) CPF, (2) Nome, (3) Email: ");
                        String filtro = scanner.nextLine();
                        List<Usuario> candidatos = new ArrayList<>();

                        switch (filtro) {
                            case "1":
                                System.out.print("CPF: ");
                                Usuario porCpf = usuarioDAO.buscarPorCpf(scanner.nextLine());
                                if (porCpf != null) candidatos.add(porCpf);
                                break;
                            case "2":
                                System.out.print("Nome: ");
                                candidatos = usuarioDAO.buscarPorNome(scanner.nextLine());
                                break;
                            case "3":
                                System.out.print("Email: ");
                                List<Usuario> porEmail = usuarioDAO.buscarPorEmail(scanner.nextLine());
                                candidatos.addAll(porEmail);
                                break;
                            default:
                                System.out.println("❌ Filtro inválido.");
                                break;
                        }

                        if (candidatos.isEmpty()) {
                            System.out.println("❌ Nenhum usuário encontrado.");
                            break;
                        }

                        System.out.println("👥 Usuários encontrados:");
                        for (int i = 0; i < candidatos.size(); i++) {
                            Usuario u = candidatos.get(i);
                            System.out.printf("[%d] %s (%s)%n", i + 1, u.getNome(), u.getEmail());
                        }
                        System.out.print("Escolha o número do cliente ou 0 para cancelar: ");
                        int escolha = Integer.parseInt(scanner.nextLine());
                        if (escolha == 0) break;

                        Usuario selecionado = candidatos.get(escolha - 1);
                        if (!(selecionado instanceof Cliente)) {
                            System.out.println("❌ Esse usuário não é um cliente.");
                            break;
                        }
                        Cliente clienteSelecionado = (Cliente) selecionado;

                        List<Emprestimo> ativos = emprestimoDAO.listarPorCliente(clienteSelecionado.getCpf())
                                .stream().filter(e -> e.getStatus() == StatusEmprestimo.ATIVO).toList();

                        if (ativos.isEmpty()) {
                            System.out.println("❌ Esse cliente não possui empréstimos ativos.");
                            break;
                        }

                        System.out.println("📚 Empréstimos ativos:");
                        for (int i = 0; i < ativos.size(); i++) {
                            Emprestimo e = ativos.get(i);
                            System.out.printf("[%d] %s (ID %d) | Devolver até: %s%n",
                                    i + 1, e.getLivro().getTitulo(), e.getId(), e.getDataDevolucaoPrevista());
                        }
                        System.out.print("Escolha um empréstimo para registrar devolução ou 0 para cancelar: ");
                        int escolhaEmp = Integer.parseInt(scanner.nextLine());
                        if (escolhaEmp == 0) break;

                        Emprestimo emprestimoSelecionado = ativos.get(escolhaEmp - 1);
                        emprestimoSelecionado.devolver();
                        emprestimoDAO.atualizarDevolucao(emprestimoSelecionado);
                        livroDAO.atualizar(emprestimoSelecionado.getLivro());

                        System.out.printf("✔️ Devolução registrada. Multa: R$ %.2f%n", emprestimoSelecionado.getValorMulta());
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
