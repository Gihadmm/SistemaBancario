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

    public Livro(int id, String titulo, String autor, String genero, int anoPublicacao,
                 String editora, String isbn, int quantidadeExemplares) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.anoPublicacao = anoPublicacao;
        this.editora = editora;
        this.isbn = isbn;
        this.quantidadeExemplares = quantidadeExemplares;
        this.disponivel = quantidadeExemplares > 0;  // disponível se houver exemplares
        this.reservado = false;
    }

    // ——— Comportamentos de domínio ———

    /** Tenta emprestar um exemplar; lança IllegalStateException se não disponível */
    public void emprestar() {
        if (!disponivel || reservado) {
            throw new IllegalStateException("Livro não disponível para empréstimo.");
        }
        quantidadeExemplares--;
        if (quantidadeExemplares == 0) disponivel = false;
    }

    /** Devolve um exemplar; marca disponível novamente */
    public void devolver() {
        quantidadeExemplares++;
        disponivel = true;
        reservado = false;
    }

    /** Marca como reservado (se ainda houver exemplares) */
    public void reservar() {
        if (!disponivel) {
            throw new IllegalStateException("Não há exemplares para reserva.");
        }
        reservado = true;
    }

    /** Cancela uma reserva */
    public void cancelarReserva() {
        reservado = false;
    }

    public boolean verificarDisponibilidade() {
        return disponivel && !reservado;
    }

    // ——— Getters e Setters ———

    public int getId()            { return id; }
    public String getTitulo()     { return titulo; }
    public String getAutor()      { return autor; }
    public String getGenero()     { return genero; }
    public boolean isDisponivel() { return disponivel; }
    public boolean isReservado()  { return reservado; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public String getEditora()    { return editora; }
    public String getIsbn()       { return isbn; }
    public int getQuantidadeExemplares() { return quantidadeExemplares; }

    public void setId(int id)     { this.id = id; }
    public void setTitulo(String titulo)         { this.titulo = titulo; }
    public void setAutor(String autor)           { this.autor = autor; }
    public void setGenero(String genero)         { this.genero = genero; }
    public void setAnoPublicacao(int ano)        { this.anoPublicacao = ano; }
    public void setEditora(String editora)       { this.editora = editora; }
    public void setIsbn(String isbn)             { this.isbn = isbn; }
    public void setQuantidadeExemplares(int q) {
        this.quantidadeExemplares = q;
        this.disponivel = q > 0;
    }
}
