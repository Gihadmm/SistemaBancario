public class Login {


    public static Usuario realizarLogin(String email, String senha) {
        // busca do usuario pelo BD

        // Simular login
        if (email.equals("admin@biblioteca.com") && senha.equals("admin123")) {
            return new Administrador("00000000000", "Admin", email, senha);
        } else if (email.equals("cliente@biblioteca.com") && senha.equals("cliente123")) {
            return new Cliente("11111111111", "Cliente", email, senha);
        }

        return null; // Login inválido
    }
}

