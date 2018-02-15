package br.com.clinica.teste;

import java.sql.Connection;
import java.sql.SQLException;

import br.com.clinica.connection.ConnectionFactory;

public class ConexaoTeste {

	public static void main(String[] args) {
		
		try {
			System.out.println("Abrindo conexão ...");
			Connection connection = new ConnectionFactory().getConnection();
			System.out.println("Fechando conexão ...");
			connection.close();
			System.out.println("Teste de conexão finalizado sem problemas.");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
