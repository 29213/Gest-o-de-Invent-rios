package pt.iade.gestaoInventario.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import pt.iade.gestaoInventario.models.Categoria;

public class CategoriaDAO {
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

	public List<Categoria> listar() {
		String sql = "SELECT * FROM categorias";
		Connection connection = DBConnection.conectar();

		List<Categoria> retorno = new ArrayList<>();
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
