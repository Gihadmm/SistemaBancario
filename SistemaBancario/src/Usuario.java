public interface Usuario {
    String getCpf();
    String getNome();
    String getEmail();
    String getSenha();
    Acesso getAcesso(); // Acesso pode ser de Cliente ou ADM

    void redefinirSenha(String email);
}
