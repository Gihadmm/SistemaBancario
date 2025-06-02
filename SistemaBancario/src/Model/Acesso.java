package Model;

/**
 * Enumeração que representa os tipos de acesso de usuários no sistema.
 * Utilizado para diferenciar os perfis de Cliente e Administrador.
 */
public enum Acesso {
    CLIENTE,         // Representa um usuário comum do sistema (leitor)
    ADMINISTRADOR    // Representa um usuário com privilégios administrativos
}
