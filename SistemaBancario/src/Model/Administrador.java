package Model;

/**
 * Representa um administrador do sistema da biblioteca.
 * Tem permissões para realizar ações como cadastro de livros e clientes,
 * empréstimos, devoluções e geração de relatórios — ações que são
 * implementadas fora desta classe, nos menus e nas classes DAO.
 */
public class Administrador implements Usuario {
    private String cpf;
    private String nome;
    private String email;
    private String senha;
    private Acesso acesso;

    /**
     * Cria um novo administrador com os dados fornecidos.
     *
     * @param cpf   CPF do administrador
     * @param nome  Nome completo
     * @param email Email de acesso
     * @param senha Senha de acesso
     */
    public Administrador(String cpf, String nome, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.acesso = Acesso.ADMINISTRADOR;  // Define o perfil como administrador
    }

    // Métodos de acesso às propriedades

    @Override public String getCpf() { return cpf; }

    @Override public String getNome() { return nome; }

    @Override public String getEmail() { return email; }

    @Override public String getSenha() { return senha; }

    @Override public Acesso getAcesso() { return acesso; }

    @Override public void setNome(String nome) { this.nome = nome; }

    @Override public void setEmail(String email) { this.email = email; }

    @Override public void setSenha(String senha) { this.senha = senha; }

    /**
     * Método opcional para redefinição de senha.
     * Atualmente não implementado.
     */
    @Override
    public void redefinirSenha(String email) {
        // Opcional: implementar futuramente se desejar
    }
}
