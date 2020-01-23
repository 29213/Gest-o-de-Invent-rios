package pt.iade.gestaoInventario.models.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO: Auto-generated Javadoc
/**
 * 
 * Está classe faz a conexão com a base de dados.
 * 
 */

public class DBConnection {
	
	/** The connection. */
	private static Connection connection = null;
	
	/** The Constant SERVIDOR. */
	private static final String SERVIDOR = "localhost";
	
	/** The Constant PORTA. */
	private static final String PORTA = "3306";
	
	/** The Constant BANCO_DE_DADOS. */
	private static final String BANCO_DE_DADOS = "inventarios";
	
	/** The Constant UTILIZADOR. */
	private static final String UTILIZADOR = "root";
	
	/** The Constant SENHA. */
	private static final String SENHA = "";
	
	/** The Constant URL. */
	private static final String URL = "jdbc:mysql://" + SERVIDOR + ":" + PORTA + "/" + BANCO_DE_DADOS + "?verifyServerCertificate=false&useSSL=true";
	
	/**
	 * Conectar.
	 *
	 * @return the connection
	 */
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

	/**
	 * Desconectar.
	 *
	 * @param connection the connection
	 */
	public static void desconectar(Connection connection) {
		try {
			connection.close();
		} catch (SQLException ex) {
			Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

