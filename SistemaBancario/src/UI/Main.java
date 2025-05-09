package UI;

import Model.Administrador;
import Model.Cliente;

import java.util.Scanner;

import Model.*;
import Service.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Sistema de Biblioteca ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = LoginService.realizarLogin(email, senha);

        if (usuario == null) {
            System.out.println("❌ Service.LoginService inválido.");
            return;
        }

        System.out.println("✅ Service.LoginService realizado com sucesso!\n");

        if (usuario.getAcesso() == Acesso.ADMINISTRADOR) {
            MenuAdministrador.executar(scanner, (Administrador) usuario);
        } else {
            MenuCliente.executar(scanner, (Cliente) usuario);
        }
    }
}