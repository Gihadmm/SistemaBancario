package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Date;
import java.util.Scanner;


/**
 * Representa um cliente do sistema de biblioteca.
 * Contém dados pessoais, histórico de empréstimos e reserva ativa.
 */
public class Cliente implements Usuario {
    private final String cpf;
    private String nome;
    private String email;
    private String senha;
    private final Acesso acesso = Acesso.CLIENTE;

    // Histórico de todos os empréstimos deste cliente
    private final List<Emprestimo> emprestimos = new ArrayList<>();

    // Reserva ativa (apenas uma por cliente, conforme diagrama)
    private Reserva reserva;

    /**
     * Construtor utilizado ao mapear do banco (ClienteDAO).
     *
     * @param cpf    CPF único do cliente
     * @param nome   Nome completo
     * @param email  E-mail de login
     * @param senha  Senha de acesso
     */
    public Cliente(String cpf, String nome, String email, String senha) {
        this.cpf = Objects.requireNonNull(cpf);
        this.nome = Objects.requireNonNull(nome);
        this.email = Objects.requireNonNull(email);
        this.senha = Objects.requireNonNull(senha);
    }

    // ——— Métodos da interface Usuario ———

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getSenha() {
        return senha;
    }

    @Override
    public Acesso getAcesso() {
        return acesso;
    }

    @Override
    public void redefinirSenha(String email) {
        if (this.email.equals(email)) {
            // TODO: implementar fluxo de redefinição (ex.: gerar token, e-mail)
            this.senha = "novaSenha123";
        }
    }

    // ——— Getters e Setters para dados mutáveis ———

    public void setNome(String nome) {
        this.nome = Objects.requireNonNull(nome);
    }

    public void setEmail(String email) {
        this.email = Objects.requireNonNull(email);
    }

    public void setSenha(String senha) {
        this.senha = Objects.requireNonNull(senha);
    }

    // ——— Empréstimos ———

    /**
     * Retorna cópia imutável do histórico de empréstimos.
     */
    public List<Emprestimo> getEmprestimos() {
        return Collections.unmodifiableList(emprestimos);
    }

    /**
     * Adiciona um empréstimo ao histórico.
     *
     * @param e empréstimo recém-registrado
     */
    public void adicionarEmprestimo(Emprestimo e) {
        emprestimos.add(Objects.requireNonNull(e));
    }

    /**
     * Filtra os empréstimos ainda ativos (status ATIVO).
     */
    public List<Emprestimo> getEmprestimosAtivos() {
        List<Emprestimo> ativos = new ArrayList<>();
        for (Emprestimo e : emprestimos) {
            if (e.getStatus() == StatusEmprestimo.ATIVO) {
                ativos.add(e);
            }
        }
        return ativos;
    }

    /**
     * Filtra apenas os empréstimos com multa pendente.
     */
    public List<Emprestimo> getEmprestimosComMultaAtiva() {
        List<Emprestimo> pendentes = new ArrayList<>();
        for (Emprestimo e : emprestimos) {
            if (e.getValorMulta() > 0 && !e.isMultaPaga()) {
                pendentes.add(e);
            }
        }
        return pendentes;
    }

    /**
     * Quita a multa de um empréstimo específico.
     *
     * @param e empréstimo cuja multa será paga
     */
    public void pagarMulta(Emprestimo e) {
        e.pagarMulta();
    }

    // ——— Reserva ———

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    // ——— Operações de domínio (implementações simplificadas) ———

    /**
     * Tenta solicitar empréstimo de um exemplar.
     *
     * @param exemplar a cópia desejada
     * @return true se exemplar disponível e solicitação bem-sucedida
     */
    public boolean solicitarEmprestimo(Exemplar exemplar) {
        if (exemplar == null || !exemplar.isDisponivel()) {
            return false;
        }

        Date hoje = new Date();
        Date dataPrevista = calculaDataPrevista();

        Emprestimo e = new Emprestimo(0, hoje, dataPrevista, exemplar, this);
        adicionarEmprestimo(e);

        return true;
    }

    /**
     * Tenta solicitar devolução de um empréstimo.
     *
     * @param emprestimo o empréstimo a ser devolvido
     * @return true se estava ativo e devolução bem-sucedida
     */
    public boolean solicitarDevolucao(Emprestimo emprestimo) {
        if (emprestimo == null || emprestimo.getStatus() != StatusEmprestimo.ATIVO) {
            return false;
        }
        emprestimo.setDataDevolucaoReal(new java.util.Date());
        emprestimo.setStatus(StatusEmprestimo.CONCLUIDO);
        emprestimo.aplicarMulta();
        return true;
    }

    /**
     * Tenta solicitar renovação de um empréstimo.
     *
     * @param emprestimo o empréstimo a renovar
     * @return true se ativo e renovação bem-sucedida
     */
    public boolean solicitarRenovacao(Emprestimo emprestimo) {
        if (emprestimo == null || emprestimo.getStatus() != StatusEmprestimo.ATIVO) {
            return false;
        }
        emprestimo.setDataDevolucaoPrevista(calculaDataPrevista());
        return true;
    }

    /**
     * Tenta realizar reserva de um livro (quando não há exemplares disponíveis).
     *
     * @param livro o livro a reservar
     * @return true se reserva criada
     */
    public boolean realizarReserva(Livro livro) {
        if (livro == null || !livro.isReservado()) {
            Reserva r = new Reserva(0, livro);
            setReserva(r);
            return true;
        }
        return false;
    }

    // ——— Métodos utilitários privados ———

    /** Simples cálculo de 7 dias para devolução prevista */
    private java.util.Date calculaDataPrevista() {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.add(java.util.Calendar.DAY_OF_MONTH, 7);
        return c.getTime();
    }

    /**
     * Retorna informações pessoais do cliente.
     */
    public String consultarDados() {
        return String.format("Cliente: %s (%s)%nE-mail: %s", nome, cpf, email);
    }

    /**
     * Solicita alteração de dados; deve ser tratado pelo Menu/UI.
     */
    public void solicitarAlteracaoDados() {
        // stub para ser implementado via Menu ou Service
    }

    /**
     * Pesquisa livros conforme filtros; devolve descrição.
     */
    public String consultarLivros(String titulo, String autor, String genero) {
        return String.format("Filtrando livros por: Título='%s', Autor='%s', Gênero='%s'",
                titulo, autor, genero);
    }
}
