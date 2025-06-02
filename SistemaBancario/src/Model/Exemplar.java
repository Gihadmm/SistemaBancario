package Model;

/**
 * Representa uma cópia física de um Livro na biblioteca.
 */
public class Exemplar {
    private int id;             // Identificador único no banco de dados
    private int numeroCopia;    // Número da cópia dentro do conjunto do mesmo livro
    private int livroId;        // ID do livro ao qual esta cópia pertence
    private boolean disponivel; // Indica se o exemplar está disponível para empréstimo

    /**
     * Construtor completo usado na leitura do banco (ExemplarDAO).
     *
     * @param id            ID único gerado pelo banco
     * @param numeroCopia   Número da cópia do livro
     * @param livroId       ID do livro ao qual este exemplar pertence
     * @param disponivel    Estado de disponibilidade
     */
    public Exemplar(int id, int numeroCopia, int livroId, boolean disponivel) {
        this.id = id;
        this.numeroCopia = numeroCopia;
        this.livroId = livroId;
        this.disponivel = disponivel;
    }

    /**
     * Construtor usado antes de salvar no banco. Assume que o exemplar está disponível.
     */
    public Exemplar(int numeroCopia, int livroId) {
        this(0, numeroCopia, livroId, true);
    }

    // Getters

    public int getId() { return id; }
    public int getNumeroCopia() { return numeroCopia; }
    public int getLivroId() { return livroId; }
    public boolean isDisponivel() { return disponivel; }

    // Setters

    /** Define o ID após inserção no banco */
    public void setId(int id) { this.id = id; }
    public void setNumeroCopia(int numeroCopia) { this.numeroCopia = numeroCopia; }
    public void setLivroId(int livroId) { this.livroId = livroId; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    // Métodos de domínio

    /**
     * Marca o exemplar como emprestado.
     * Lança exceção se já estiver emprestado.
     */
    public void emprestar() {
        if (!disponivel) {
            throw new IllegalStateException("Exemplar já está emprestado.");
        }
        disponivel = false;
    }

    /**
     * Marca o exemplar como devolvido e disponível para novos empréstimos.
     */
    public void devolver() {
        disponivel = true;
    }

    @Override
    public String toString() {
        return String.format("Exemplar{id=%d, copia=%d, livroId=%d, disponivel=%b}",
                id, numeroCopia, livroId, disponivel);
    }
}
