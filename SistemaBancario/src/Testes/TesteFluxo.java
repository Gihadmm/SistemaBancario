package Testes;

import Model.Cliente;
import Model.Emprestimo;
import Model.Livro;
import repository.*;

public class TesteFluxo {
    public static void main(String[] args) throws Exception {
        // 1) Cadastra cliente
        ClienteDAO cdao = new ClienteDAO();
        Cliente client = new Cliente("12345678900","Fulano","f@x.com","senha");
        cdao.inserir(client);

        // 2) Cadastra livro
        LivroDAO ldao = new LivroDAO();
        Livro livro = new Livro(0,"Título Teste","Autor","Gênero",2020,"Editora","ISBN",2);
        ldao.inserir(livro);

        // 3) Empresta
        EmprestimoDAO edao = new EmprestimoDAO();
        Emprestimo emp = client.solicitarEmprestimo(livro);
        edao.inserir(emp);

        // 4) Verifica
        Livro after = ldao.buscarPorId(livro.getId());
        System.out.println("Exemplares após empréstimo: " + after.getQuantidadeExemplares());
    }
}

