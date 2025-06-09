package Testes;

import Model.Cliente;
import Model.Livro;
import Model.Reserva;
import repository.ClienteDAO;
import repository.LivroDAO;
import repository.ReservaDAO;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservaDAOTest {

    static ReservaDAO reservaDAO;
    static ClienteDAO clienteDAO;
    static LivroDAO livroDAO;

    static Cliente cliente;
    static Livro livro;
    static Reserva reserva;

    @BeforeAll
    static void setup() throws SQLException {
        reservaDAO = new ReservaDAO();
        clienteDAO = new ClienteDAO();
        livroDAO = new LivroDAO();

        // Inserir cliente e livro para associar à reserva
        cliente = new Cliente("99999999999", "Reserva Teste", "reserva@email.com", "senha123");
        clienteDAO.inserir(cliente);

        livro = new Livro(0, "Livro Reservado", "Autor Reserva", "Suspense", 2022,
                "Editora A", "1111111111", 2);
        livroDAO.inserir(livro);

        System.out.println("\n📌 Iniciando testes da classe ReservaDAO...");
    }

    @Test
    @Order(1)
    void testInserirReserva() throws SQLException {
        Date hoje = new Date();

        // Definir previsão de disponibilidade para daqui a 7 dias
        Calendar cal = Calendar.getInstance();
        cal.setTime(hoje);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date previsao = cal.getTime();

        reserva = new Reserva(cliente, livro, hoje, previsao);
        reservaDAO.inserir(reserva);

        assertTrue(reserva.getId() > 0, "ID da reserva deve ser maior que 0.");
        System.out.println("📌 Reserva inserida com ID: " + reserva.getId());
    }

    @Test
    @Order(2)
    void testListarPorCliente() throws SQLException {
        List<Reserva> reservas = reservaDAO.listarPorCliente(cliente.getCpf());

        assertFalse(reservas.isEmpty(), "Nenhuma reserva foi encontrada para o cliente.");
        Reserva res = reservas.get(0);

        assertEquals(cliente.getCpf(), res.getCliente().getCpf());
        assertEquals(livro.getId(), res.getLivro().getId());

        System.out.println("📖 Reserva listada com sucesso para o cliente " + cliente.getNome());
    }

    @Test
    @Order(3)
    void testTemReservasAtivas() throws SQLException {
        boolean ativo = reservaDAO.temReservasAtivas(livro.getId());

        assertTrue(ativo, "Reserva ativa não foi detectada para o livro.");
        System.out.println("📚 O livro possui reserva ativa confirmada.");
    }

    @AfterAll
    static void finalizar() {
        System.out.println("\n✅ Testes da classe ReservaDAO finalizados com sucesso.\n");
    }
}
