package Model;

public interface Usuario {
    String getCpf();
    String getNome();
    String getEmail();
    String getSenha();
    Acesso getAcesso();// Model.Acesso pode ser de Model.Cliente ou ADM

    void setNome(String nome);
    void setEmail(String email);
    void setSenha(String senha);

    void redefinirSenha(String email);
}
