package Model;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private String genero;
    private boolean disponivel;
    private boolean reservado;
    private int anoPublicacao;
    private String editora;
    private String isbn;
    private int quantidadeExemplares;

    public Livro(int id, String titulo, String autor, String genero, int anoPublicacao, String editora, String isbn, int quantidadeExemplares) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.isbn = isbn;
        this.quantidadeExemplares = quantidadeExemplares;
        this.disponivel = true;
        this.reservado = false;
    }

    public boolean verificarDisponibilidade() {
        return disponivel && !reservado;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public boolean isReservado() {
        return reservado;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public String getEditora() {
        return editora;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getQuantidadeExemplares() {
        return quantidadeExemplares;
    }

    public void setId(int id) {
        this.id = id;
    }
}