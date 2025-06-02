package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representa um cliente (usuário comum) do sistema da biblioteca.
 * Clientes podem solicitar empréstimos, visualizar seu histórico
 * e acompanhar suas multas.
 */
public class Cliente implements Usuario {
    private String cpf, nome, email, senha;
    private Acesso acesso;
    private List<Emprestimo> emprestimos; // Histórico de empréstimos do cliente

    /**
     * Construtor padrão que define os dados pessoais e inicializa o acesso como CLIENTE.
     */
    public Cliente(String cpf, String nome, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.acesso = Acesso.CLIENTE;
        this.emprestimos = new ArrayList<>();
    }

    // Métodos obrigatórios da interface Usuario
    @Override public String getCpf()    { return cpf; }
    @Override public String getNome()   { return nome; }
    @Override public String getEmail()  { return email; }
    @Override public String getSenha()  { return senha; }
    @Override public Acesso getAcesso() { return acesso; }

    /**
     * Redefine a senha do usuário se o e-mail informado coincidir.
     * Exemplo simples de redefinição (poderia ser estendido).
     */
    @Override
    public void redefinirSenha(String em) {
        if (email.equals(em)) senha = "novaSenha123";
    }

    // Setters
    @Override public void setNome(String nome)     { this.nome = nome; }
    @Override public void setEmail(String email)   { this.email = email; }
    @Override public void setSenha(String senha)   { this.senha = senha; }

    // Gestão de empréstimos em memória
    public List<Emprestimo> getEmprestimos() { return emprestimos; }

    public void adicionarEmprestimo(Emprestimo e) {
        emprestimos.add(e);
    }

    /**
     * Cria e registra um novo empréstimo, caso o livro esteja disponível.
     * Este método não interage com o banco de dados, apenas cria o objeto em memória.
     */
    public Emprestimo solicitarEmprestimo(Livro livro) {
        if (livro == null || !livro.isDisponivel()) return null;

        livro.emprestar(); // Atualiza a disponibilidade do livro
        Date hoje = new Date();
        Date prevista = new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000); // 7 dias
        Emprestimo e = new Emprestimo(0, hoje, prevista, livro, this);
        emprestimos.add(e);
        return e;
    }

    /**
     * Retorna a lista de empréstimos do cliente que possuem multas não pagas.
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
}
