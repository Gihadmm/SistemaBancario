public class Cliente implements Usuario {
        private String cpf;
        private String nome;
        private String email;
        private String senha;
        private Acesso acesso;
        private List<Multa> multasAtivas; //Criar Lista para multas
        private Reserva reserva; // implementar a classe Reserva

        public Cliente(String cpf, String nome, String email, String senha) {
            this.cpf = cpf;
            this.nome = nome;
            this.email = email;
            this.senha = senha;
            this.acesso = Acesso.CLIENTE;
        }



        //IMPLEMENTAR metodos da interface

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getSenha() {
        return senha;
    }

    @Override
    public Acesso getAcesso() {
        return acesso;
    }

    @Override
    public void redefinirSenha(String email) {
        if (this.email.equals(email)) {
            // Exemplo de redefinição de senha
            this.senha = "novaSenha123";
        }
    }

    // Métodos específicos do Cliente:

    public void solicitarEmprestimo(Livro livro) {
        // lógica para solicitação de empréstimo
    }

    public void consultarDados() {
        // consulta de dados pessoais
    }

    public void solicitarAlteracaoDados() {
        // solicitar dados pessoais
    }

    public void solicitarDevolucao(Livro livro) {
        // solicitar devolução
    }

    public void solicitarRenovacao(Livro livro) {
        // solicitar renovação
    }

    public void consultarLivros(String titulo, String autor, String genero) {
        // consulta de livros
    }

    public void realizarReserva(Livro livro) {
        // reservar livro
    }

    public void pagarMulta(Multa multa) {
        multa.pagarMulta();
    }

    // Getters e setters para multas e reserva, se necessário
    public List<Multa> getMultasAtivas() {
        return multasAtivas;
    }

    public void setMultasAtivas(List<Multa> multasAtivas) {
        this.multasAtivas = multasAtivas;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}

