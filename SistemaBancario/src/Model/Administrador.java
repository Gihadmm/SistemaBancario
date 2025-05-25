package Model;

public class Administrador implements Usuario {
    private String cpf;
    private String nome;
    private String email;
    private String senha;
    private Acesso acesso;


    public Administrador(String cpf, String nome, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.acesso = Acesso.ADMINISTRADOR;
    }


    @Override public void setNome(String nome) { this.nome = nome; }
    @Override public void setEmail(String email) { this.email = email; }
    @Override public void setSenha(String senha) { this.senha = senha; }
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
        //Redefinir a senha
    }

    // Métodos do ADM

    public void cadastrarLivro(Livro livro) {
        // cadastro de livros
    }

    public void atualizarLivro(Livro livro) {
        // atualizar dados do livro
    }

    public void consultarCliente(String cpf, String email, String nome) {
        //consultar clientes
    }

    public void alterarDadosCliente(String cpf, String email, String nome) {
        // alterar dados do cliente
    }

    public void consultarLivros(String titulo, String autor, String genero) {
        // consultar livros
    }

    public void removerLivro(Livro livro) {
        // remover Model.Livro
    }

    public void gerarRelatorioMultas() {
        // gerar relatório de multas
    }

    public void gerarRelatorioEmprestimos() {
        // gerar relatório de empréstimos
    }

    public void cadastrarADM(Administrador admin) {
        // cadastrar Model.Administrador
    }

    public void cadastrarCliente(Cliente cliente) {
        // cadastrar Model.Cliente
    }

    public void realizarEmprestimo(Livro livro, String cpfCliente) {
        //realizar empréstimo de um livro
    }

    public void realizarDevolucao(Livro livro, String cpfCliente) {
        // realizar devolução
    }

    public void realizarRenovacao(Livro livro, String cpfCliente) {
        // renovação do empréstimo
    }

    public void realizarPagamentoMulta(String cpfCliente) {
        // pagamento de multa
    }
}
