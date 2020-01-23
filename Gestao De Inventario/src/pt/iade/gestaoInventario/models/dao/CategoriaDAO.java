package pt.iade.gestaoInventario.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.iade.gestaoInventario.models.Categoria;

// TODO: Auto-generated Javadoc
/**
 * 
 * <p>Esta classe perminte ter interação com a base de dados.
 * <p> Perminte: Inserir, alterar, remover, listar e buscar 
 * 
 * @author Renato Pitta Simões
 */
public class CategoriaDAO {
	
	/**
	 * Inserir.
	 *
	 * @param categoria a categoria
	 * @return verdadeiro, se for bem sucedido
	 */
	public boolean inserir(Categoria categoria) {
		String sql = "INSERT INTO categorias(descricao) VALUES(?)";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, categoria.getDescricao());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	/**
	 * Alterar.
	 *
	 * @param categoria a categoria
	 * @return verdadeiro, se for bem sucedido
	 */
	public boolean alterar(Categoria categoria) {
		String sql = "UPDATE categorias SET descricao=? WHERE idCategoria=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, categoria.getDescricao());
			stmt.setInt(2, categoria.getIdCategoria());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	/**
	 * Remover.
	 *
	 * @param categoria a categoria
	 * @return verdadeiro, se for bem sucedido
	 */
	public boolean remover(Categoria categoria) {
		String sql = "DELETE FROM categorias WHERE idCategoria=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, categoria.getIdCategoria());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	/**
	 * Listar.
	 *
	 * @return a lista
	 */
	public ObservableList<Categoria> listar() {
		String sql = "SELECT * FROM categorias";
		Connection connection = DBConnection.conectar();

		ObservableList<Categoria> retorno = FXCollections.observableArrayList();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				Categoria categoria = new Categoria();
				categoria.setIdCategoria(resultado.getInt("idCategoria"));
				categoria.setDescricao(resultado.getString("descricao"));
				retorno.add(categoria);
			}
		} catch (SQLException ex) {
			Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

	/**
	 * Buscar.
	 *
	 * @param categoria a categoria
	 * @return a categoria
	 */
	public Categoria buscar(Categoria categoria) {
		String sql = "SELECT * FROM categorias WHERE idCategoria=?";
		Categoria retorno = new Categoria();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, categoria.getIdCategoria());
			ResultSet resultado = stmt.executeQuery();
			if (resultado.next()) {
				categoria.setDescricao(resultado.getString("descricao"));
				retorno = categoria;
			}
		} catch (SQLException ex) {
			Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}
}
