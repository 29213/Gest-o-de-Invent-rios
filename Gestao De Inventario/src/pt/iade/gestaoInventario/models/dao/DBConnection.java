package pt.iade.gestaoInventario.models.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * Está classe faz a conexão com a base de dados.
 * 
 */

public class DBConnection {
	private static Connection connection = null;
	private static final String SERVIDOR = "remotemysql.com";
	private static final String PORTA = "3306";
	private static final String BANCO_DE_DADOS = "Sa09bvODzP";
	private static final String UTILIZADOR = "Sa09bvODzP";
	private static final String SENHA = "RYE1S1DYh4";
	private static final String URL = "jdbc:mysql://" + SERVIDOR + ":" + PORTA + "/" + BANCO_DE_DADOS + "?verifyServerCertificate=false&useSSL=true";
	public static Connection conectar() {
		try {
			if (connection != null && !connection.isClosed())
				return connection;
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(URL, UTILIZADOR, SENHA);
			return connection;
		} catch (SQLException | ClassNotFoundException ex) {
			Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public static void desconectar(Connection connection) {
		try {
			connection.close();
		} catch (SQLException ex) {
			Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
