package Service;

import repository.*;
import Model.*;

public class LoginService {
    private static UsuarioDAO usuarioDAO = new UsuarioDAO();

    public static Usuario realizarLogin(String email, String senha) {
        return usuarioDAO.buscarPorEmailSenha(email, senha);
    }
}

