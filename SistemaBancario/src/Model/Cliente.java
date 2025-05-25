package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cliente implements Usuario {
    private String cpf, nome, email, senha;
    private Acesso acesso;
    private List<Emprestimo> emprestimos;


    public Cliente(String cpf, String nome, String email, String senha) {
        this.cpf=cpf; this.nome=nome; this.email=email; this.senha=senha;
        this.acesso=Acesso.CLIENTE;
        this.emprestimos=new ArrayList<>();
    }

    // Usuario
    @Override public String getCpf()    {return cpf;}
    @Override public String getNome()   {return nome;}
    @Override public String getEmail()  {return email;}
    @Override public String getSenha()  {return senha;}
    @Override public Acesso getAcesso() {return acesso;}
    @Override public void redefinirSenha(String em) {
        if(email.equals(em)) senha="novaSenha123";
    }
    @Override public void setNome(String nome) { this.nome = nome; }
    @Override public void setEmail(String email) { this.email = email; }
    @Override public void setSenha(String senha) { this.senha = senha; }


    // Empréstimos
    public List<Emprestimo> getEmprestimos() {return emprestimos;}
    public void adicionarEmprestimo(Emprestimo e) {emprestimos.add(e);}

    /**
     * Cria e adiciona ao histórico um empréstimo de domínio.
     * Não toca em banco de dados.
     * @param livro o livro a ser emprestado
     * @return o objeto Emprestimo inicializado, ou null se indisponível
     */
    public Emprestimo solicitarEmprestimo(Livro livro) {
        if (livro == null || !livro.isDisponivel()) {
            return null;
        }
        livro.emprestar();
        Date hoje = new Date();
        Date prevista = new Date(System.currentTimeMillis() + 7L*24*60*60*1000);
        Emprestimo e = new Emprestimo(0, hoje, prevista, livro, this);
        emprestimos.add(e);
        return e;
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

}
