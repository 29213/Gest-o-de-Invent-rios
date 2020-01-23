package pt.iade.gestaoInventario.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import pt.iade.gestaoInventario.models.Colaborador;
// TODO: Auto-generated Javadoc
/**
 * 
 * <p> Esta classe permite ter interação com a base de dados.
 * <p> Permite: Inserir, alterar, remover, listar e buscar.
 *
 * @author Renato Pitta Simões
 */

public class ColaboradorDAO {
	
	/**
	 * Inserir.
	 *
	 * @param colaborador o colaborador
	 * @return verdadeiro, se for bem sucedido
	 */
	public boolean inserir(Colaborador colaborador) {
		String sql = "INSERT INTO colaboradores (nome, numero, telefone) VALUES(?,?,?)";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, colaborador.getNome());
			stmt.setInt(2, colaborador.getNumero());
			stmt.setString(3, colaborador.getTelefone());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	/**
	 * Alterar.
	 *
	 * @param colaborador o colaborador
	 * @return verdadeiro, se for bem sucedido
	 */
	public boolean alterar(Colaborador colaborador) {
		String sql = "UPDATE colaboradores SET nome=?, numero=?, telefone=? WHERE idColaborador=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, colaborador.getNome());
			stmt.setInt(2, colaborador.getNumero());
			stmt.setString(3, colaborador.getTelefone());
			stmt.setInt(4, colaborador.getIdColaborador());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	/**
	 * Remover.
	 *
	 * @param colaborador o colaborador
	 * @return verdadeiro, se for bem sucedido
	 */
	public boolean remover(Colaborador colaborador) {
		String sql = "DELETE FROM colaboradores WHERE idColaborador=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, colaborador.getIdColaborador());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	/**
	 * Listar.
	 *
	 * @return a lista observável
	 */
	@FXML
	public ObservableList<Colaborador> listar() {
		String sql = "SELECT * FROM colaboradores";
		ObservableList<Colaborador> retorno = FXCollections.observableArrayList();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery("SELECT * FROM colaboradores");
			while (resultado.next()) {
				Colaborador colaborador = new Colaborador();
				colaborador.setIdColaborador(resultado.getInt("idColaborador"));
				colaborador.setNome(resultado.getString("nome"));
				colaborador.setNumero(resultado.getInt("numero"));
				colaborador.setTelefone(resultado.getString("telefone"));
				retorno.add(colaborador);
			}
		} catch (SQLException ex) {
			Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

	/**
	 * Buscar.
	 *
	 * @param colaborador o colaborador
	 * @return o colaborador
	 */
	public Colaborador buscar(Colaborador colaborador) {
		String sql = "SELECT * FROM colaboradores WHERE idColaborador=?";
		Colaborador retorno = new Colaborador();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, colaborador.getIdColaborador());
			ResultSet resultado = stmt.executeQuery();
			if (resultado.next()) {
				colaborador.setNome(resultado.getString("nome"));
				colaborador.setNumero(resultado.getInt("numero"));
				colaborador.setTelefone(resultado.getString("telefone"));
				retorno = colaborador;
			}
		} catch (SQLException ex) {
			Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}
}
