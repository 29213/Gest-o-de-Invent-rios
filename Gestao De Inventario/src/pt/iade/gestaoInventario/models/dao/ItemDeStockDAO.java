package pt.iade.gestaoInventario.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import pt.iade.gestaoInventario.models.Stock;
import pt.iade.gestaoInventario.models.ItemDeStock;
import pt.iade.gestaoInventario.models.Produto;

/**
 * 
 * Esta classe permite ter interação com a base de dados.
 *
 */
public class ItemDeStockDAO {

	public boolean inserir(ItemDeStock itemDeStock) {
		String sql = "INSERT INTO itensdestock(quantidade, valor, idProduto, idStock) VALUES(?,?,?,?)";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, itemDeStock.getQuantidade());
			stmt.setDouble(2, itemDeStock.getValor());
			stmt.setInt(3, itemDeStock.getProduto().getIdProduto());
			stmt.setInt(4, itemDeStock.getStock().getIdStock());

			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(ItemDeStockDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public boolean alterar(ItemDeStock itemDeStock) {
		return true;
	}

	public boolean remover(ItemDeStock itemDeStock) {
		String sql = "DELETE FROM itensDeStock WHERE idItemDeStock=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, itemDeStock.getIdItemDeStock());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(ItemDeStockDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public List<ItemDeStock> listar() {
		String sql = "SELECT * FROM itensDeStock";
		List<ItemDeStock> retorno = new ArrayList<>();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				ItemDeStock itemDeStock = new ItemDeStock();
				Produto produto = new Produto();
				Stock stock = new Stock();
				itemDeStock.setIdItemDeStock(resultado.getInt("idItemDeStock"));
				itemDeStock.setQuantidade(resultado.getInt("quantidade"));
				itemDeStock.setValor(resultado.getDouble("valor"));

				produto.setIdProduto(resultado.getInt("idProduto"));
				stock.setIdStock(resultado.getInt("idStock"));

				/** Obtendo os dados completos do Produto associado ao Item de Stock. */
				ProdutoDAO produtoDAO = new ProdutoDAO();
				produto = produtoDAO.buscar(produto);

				StockDAO stockDAO = new StockDAO();
				stock = stockDAO.buscar(stock);

				itemDeStock.setProduto(produto);
				itemDeStock.setStock(stock);

				retorno.add(itemDeStock);
			}
		} catch (SQLException ex) {
			Logger.getLogger(ItemDeStockDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

	/** Listar por Stock. */
	public List<ItemDeStock> listarPorStock(Stock stock) {
		String sql = "SELECT * FROM itensDeStock WHERE idStock=?";
		List<ItemDeStock> retorno = new ArrayList<>();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, stock.getIdStock());
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				ItemDeStock itemDeStock = new ItemDeStock();
				Produto produto = new Produto();
				Stock s = new Stock();
				itemDeStock.setIdItemDeStock(resultado.getInt("idItemDeStock"));
				itemDeStock.setQuantidade(resultado.getInt("quantidade"));
				itemDeStock.setValor(resultado.getDouble("valor"));

				produto.setIdProduto(resultado.getInt("idProduto"));
				s.setIdStock(resultado.getInt("idStock"));

				/** Obtendo os dados completos do Produto associado ao Item de Stock. */
				ProdutoDAO produtoDAO = new ProdutoDAO();
				produto = produtoDAO.buscar(produto);

				itemDeStock.setProduto(produto);
				itemDeStock.setStock(s);

				retorno.add(itemDeStock);
			}
		} catch (SQLException ex) {
			Logger.getLogger(ItemDeStockDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

	public ItemDeStock buscar (ItemDeStock itemDeStock) {
		String sql = "SELECT * FROM itensDeStock WHERE idItemDeStock=?";
		ItemDeStock retorno = new ItemDeStock();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, itemDeStock.getIdItemDeStock());
			ResultSet resultado = stmt.executeQuery();
			if (resultado.next()) {
				Produto produto = new Produto();
				Stock stock = new Stock();
				itemDeStock.setIdItemDeStock(resultado.getInt("idItemDeStock"));
				itemDeStock.setQuantidade(resultado.getInt("quantidade"));
				itemDeStock.setValor(resultado.getDouble("valor"));

				produto.setIdProduto(resultado.getInt("idProduto"));
				stock.setIdStock(resultado.getInt("idStock"));

				/** Obtendo os dados completos do Colaborador associado a  Stock. */
				ProdutoDAO produtoDAO = new ProdutoDAO();
				produto = produtoDAO.buscar(produto);

				StockDAO stockDAO = new StockDAO();
				stock = stockDAO.buscar(stock);

				itemDeStock.setProduto(produto);
				itemDeStock.setStock(stock);

				retorno = itemDeStock;
			}
		} catch (SQLException ex) {
			Logger.getLogger(StockDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}
}
