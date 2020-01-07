package pt.iade.gestaoInventario.models.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.iade.gestaoInventario.models.Colaborador;
import pt.iade.gestaoInventario.models.ItemDeStock;
import pt.iade.gestaoInventario.models.Stock;

/**
 * 
 * Esta classe permite ter intereção com a base de dados.
 *
 */
public class StockDAO {
	
	public boolean inserir(Stock stock) {
		String sql = "INSERT INTO stocks(data, valor, idColaborador) VALUES(?,?,?)";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			stmt.setDate(1, Date.valueOf(stock.getData()));
			stmt.setDouble(2, stock.getValor());
			stmt.setInt(3, stock.getColaborador().getIdColaborador());
			stmt.execute();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next())
				stock.setIdStock(rs.getInt(1));
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public boolean alterar(Stock stock) {
		String sql = "UPDATE stokcs SET data=?, valor=?, idColaborador=? WHERE idStock=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setDate(1, Date.valueOf(stock.getData()));
			stmt.setDouble(2, stock.getValor());
			stmt.setInt(3, stock.getColaborador().getIdColaborador());
			stmt.setInt(4, stock.getIdStock());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public boolean remover(Stock stock) {
		String sql = "DELETE FROM stocks WHERE idStock=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, stock.getIdStock());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public ObservableList<Stock> listar() {
		String sql = "SELECT * FROM stocks";
		Connection connection = DBConnection.conectar();
		ObservableList<Stock> retorno = FXCollections.observableArrayList();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				Stock stock = new Stock();
				Colaborador colaborador = new Colaborador();
				List<ItemDeStock> itensDeStock = new ArrayList<ItemDeStock>();

				stock.setIdStock(resultado.getInt("idStock"));
				stock.setData(resultado.getDate("data").toLocalDate());
				stock.setValor(resultado.getDouble("valor"));
				colaborador.setIdColaborador(resultado.getInt("idColaborador"));

				/** Obtendo os dados completos do Colaborador associado ao Stock. */
				ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
				colaborador = colaboradorDAO.buscar(colaborador);

				new ItemDeStockDAO();
				itensDeStock = ItemDeStockDAO.listarPorStock(stock);

				stock.setColaborador(colaborador);
				stock.setItensDeStock(itensDeStock);
				retorno.add(stock);
			}
		} catch (SQLException ex) {
			Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

	public Stock buscar(Stock stock) {
		String sql = "SELECT * FROM stocks WHERE idStock=?";
		Stock retorno = new Stock();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, stock.getIdStock());
			ResultSet resultado = stmt.executeQuery();
			if (resultado.next()) {
				Colaborador colaborador = new Colaborador();
				stock.setIdStock(resultado.getInt("idStock"));
				stock.setData(resultado.getDate("data").toLocalDate());
				stock.setValor(resultado.getDouble("valor"));
				colaborador.setIdColaborador(resultado.getInt("idColaborador"));
				stock.setColaborador(colaborador);
				retorno = stock;
			}
		} catch (SQLException ex) {
			Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

	public Stock buscarUltimoStock() {
		String sql = "SELECT max(idStock) FROM stocks";
		Stock retorno = new Stock();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();

			if (resultado.next()) {
				retorno.setIdStock(resultado.getInt(1));
				return retorno;
			}
		} catch (SQLException ex) {
			Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}
	
}
