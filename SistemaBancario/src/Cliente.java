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
}
