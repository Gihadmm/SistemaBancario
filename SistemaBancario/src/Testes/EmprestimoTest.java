package Testes;

import Model.Cliente;
import Model.Emprestimo;
import Model.Livro;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EmprestimoTest {

    @Test
    void testCriacaoERenovacaoEmprestimo() {
        // Setup
        Cliente cliente = new Cliente("123", "João", "joao@mail.com", "senha");
        Livro livro = new Livro(1, "Java", "Autor", "Tecnologia", 2023, "Editora", "123456789", 3);

        System.out.println("📦 Criando empréstimo para cliente " + cliente.getNome());

        Emprestimo emprestimo = cliente.solicitarEmprestimo(livro);

        // Validação de criação
        assertNotNull(emprestimo);

        LocalDate hoje = LocalDate.now();
        LocalDate dataEsperada = hoje.plusDays(7);

        // Converter java.util.Date para LocalDate
        LocalDate dataDevolucao = emprestimo.getDataDevolucaoPrevista()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        System.out.println("📅 Data prevista de devolução: " + dataDevolucao);
        assertEquals(dataEsperada, dataDevolucao, "Erro na data de devolução");

        // Renovação
        emprestimo.renovar(7);

        LocalDate novaEsperada = hoje.plusDays(14);
        LocalDate novaData = emprestimo.getDataDevolucaoPrevista()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        System.out.println("🔄 Empréstimo renovado. Nova data: " + novaData);
        assertEquals(novaEsperada, novaData, "Erro na data após renovação");

        assertEquals("ATIVO", emprestimo.getStatus().name());
        assertFalse(emprestimo.isMultaPaga());
    }


}
