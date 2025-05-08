import java.util.List;
import java.util.ArrayList;

public class Cliente implements Usuario {
    private String cpf;
    private String nome;
    private String email;
    private String senha;
    private Acesso acesso;

    // lista de todos os empréstimos deste cliente
    private List<Emprestimo> emprestimos;

    // reserva única (conforme seu diagrama)
    private Reserva reserva;

    public Cliente(String cpf, String nome, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.acesso = Acesso.CLIENTE;
        this.emprestimos = new ArrayList<>();
    }

    // interface Usuario
    @Override public String getCpf()     { return cpf; }
    @Override public String getNome()    { return nome; }
    @Override public String getEmail()   { return email; }
    @Override public String getSenha()   { return senha; }
    @Override public Acesso getAcesso()  { return acesso; }

    @Override
    public void redefinirSenha(String email) {
        if (this.email.equals(email)) {
            this.senha = "novaSenha123";
        }
    }

    public void adicionarEmprestimo(Emprestimo e) {
        emprestimos.add(e);
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }


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
     */
    public void pagarMulta(Emprestimo e) {
        e.pagarMulta();
    }

    // reserva
    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public void solicitarEmprestimo(Livro livro) { /* … */ }
    public void solicitarDevolucao(Livro livro)  { /* … */ }
    public void solicitarRenovacao(Livro livro)  { /* … */ }
    public void realizarReserva(Livro livro)     { /* … */ }
    public void consultarDados()                 { /* … */ }
    public void solicitarAlteracaoDados()        { /* … */ }
    public void consultarLivros(String t, String a, String g) { /* … */ }
}