package Service;

import Model.Usuario;
import repository.UsuarioDAO;

/**
 * Serviço responsável por autenticar usuários no sistema.
 * Utiliza o e-mail e a senha informados para validar as credenciais e retornar o usuário correspondente.
 */
public class LoginService {

    // Instância única do DAO para acessar o banco de usuários
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Realiza o processo de login no sistema.
     *
     * @param email  E-mail informado pelo usuário
     * @param senha  Senha informada pelo usuário
     * @return instância de Cliente ou Administrador se as credenciais estiverem corretas;
     *         ou null se forem inválidas ou ocorrer erro
     */
    public static Usuario realizarLogin(String email, String senha) {
        try {
            // Busca no banco um usuário com e-mail e senha correspondentes
            return usuarioDAO.buscarPorEmailSenha(email, senha);
        } catch (Exception e) {
            // Em caso de erro no banco ou exceção, exibe mensagem e retorna null
            System.err.println("❌ Erro ao efetuar login: " + e.getMessage());
            return null;
        }
    }
}
