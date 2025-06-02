package Model;

/**
 * Representa um livro do acervo da biblioteca, com suas informações
 * bibliográficas e controle de disponibilidade com base na quantidade de exemplares.
 */
public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String genero;
    private int anoPublicacao;
    private String editora;
    private String isbn;
    private int quantidadeExemplares;
    private boolean disponivel;

    /**
     * Construtor completo utilizado ao recuperar um livro do banco de dados.
     *
     * @param id ID do livro
     * @param titulo Título da obra
     * @param autor Nome do autor
     * @param genero Gênero literário
     * @param anoPublicacao Ano em que foi publicado
     * @param editora Nome da editora
     * @param isbn Código ISBN
     * @param quantidadeExemplares Quantidade disponível no acervo
     */
    public Livro(int id, String titulo, String autor, String genero,
                 int anoPublicacao, String editora, String isbn, int quantidadeExemplares) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.isbn = isbn;
        setQuantidadeExemplares(quantidadeExemplares); // define a disponibilidade automaticamente
    }

    // Getters
    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getGenero() { return genero; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public String getEditora() { return editora; }
    public String getIsbn() { return isbn; }
    public int getQuantidadeExemplares() { return quantidadeExemplares; }
    public boolean isDisponivel() { return disponivel; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setAnoPublicacao(int ano) { this.anoPublicacao = ano; }
    public void setEditora(String editora) { this.editora = editora; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    /**
     * Define a quantidade de exemplares e atualiza a disponibilidade automaticamente.
     */
    public void setQuantidadeExemplares(int q) {
        this.quantidadeExemplares = q;
        this.disponivel = q > 0;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    // Métodos de domínio

    /**
     * Empresta uma unidade do livro, reduzindo a quantidade de exemplares.
     * Se não houver disponíveis, lança exceção.
     */
    public void emprestar() {
        if (!disponivel) throw new IllegalStateException("Livro indisponível.");
        setQuantidadeExemplares(quantidadeExemplares - 1);
    }

    /**
     * Devolve uma unidade do livro, aumentando a quantidade de exemplares.
     */
    public void devolver() {
        setQuantidadeExemplares(quantidadeExemplares + 1);
    }
}
