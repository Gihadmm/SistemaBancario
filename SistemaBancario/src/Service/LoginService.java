package Service;

import Model.Usuario;
import repository.UsuarioDAO;

public class LoginService {

    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Tenta autenticar o usuário com e-mail e senha.
     * @param email  e-mail informado
     * @param senha  senha informada
     * @return instância de Cliente ou Administrador se sucesso, ou null se credenciais inválidas
     */
    public static Usuario realizarLogin(String email, String senha) {
        try {
            return usuarioDAO.buscarPorEmailSenha(email, senha);
        } catch (Exception e) {
            System.err.println("❌ Erro ao efetuar login: " + e.getMessage());
            return null;
        }
    }
}
