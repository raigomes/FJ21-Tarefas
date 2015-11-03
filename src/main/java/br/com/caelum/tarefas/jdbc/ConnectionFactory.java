package br.com.caelum.tarefas.jdbc;

import java.sql.*;

public class ConnectionFactory {

	public Connection getConnection() {
		try {
			Class.forName("org.postgresql.Driver");
			String driver = "jdbc:postgresql://localhost:5432/fj21";
			return DriverManager.getConnection(driver, "postgres", "postgres");
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
