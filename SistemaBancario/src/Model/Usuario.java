package Model;

/**
 * Interface que define o comportamento comum entre todos os usuários do sistema da biblioteca.
 * Ela é implementada pelas classes Cliente e Administrador, garantindo que ambos possuam
 * os métodos básicos para identificação e manipulação de dados de acesso.
 */
public interface Usuario {

    // Métodos obrigatórios para identificação do usuário
    String getCpf();     // Retorna o CPF do usuário
    String getNome();    // Retorna o nome completo
    String getEmail();   // Retorna o e-mail de login
    String getSenha();   // Retorna a senha de acesso
    Acesso getAcesso();  // Retorna o tipo de acesso (CLIENTE ou ADMINISTRADOR)

    // Métodos para atualização dos dados cadastrais
    void setNome(String nome);
    void setEmail(String email);
    void setSenha(String senha);

    // Método para redefinição de senha (pode variar entre implementações)
    void redefinirSenha(String email);
}
