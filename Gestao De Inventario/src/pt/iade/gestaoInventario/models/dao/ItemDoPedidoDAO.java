package pt.iade.gestaoInventario.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.iade.gestaoInventario.models.Pedido;
import pt.iade.gestaoInventario.models.ItemDoPedido;
import pt.iade.gestaoInventario.models.Produto;

/**
 * 
 * Esta classe permite ter interação com a base de dados.
 *
 */
	public class ItemDoPedidoDAO {

	public boolean inserir(ItemDoPedido itemDoPedido) {
		String sql = "INSERT INTO itensdestock(quantidade, valor, idProduto, idStock) VALUES (?,?,?,?)";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, itemDoPedido.getQuantidade());
			stmt.setDouble(2, itemDoPedido.getValor());
			stmt.setInt(3, itemDoPedido.getProduto().getIdProduto());
			stmt.setInt(4, itemDoPedido.getStock().getIdStock());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(ItemDoPedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public boolean alterar(ItemDoPedido itemDoPedido) {
		String sql = "UPDATE itensdestock SET quantidade =?, valor =?, idProduto =?, idStock =? WHERE idItemDeStock =?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, itemDoPedido.getQuantidade());
			stmt.setDouble(2, itemDoPedido.getValor());
			stmt.setInt(3, itemDoPedido.getProduto().getIdProduto());
			stmt.setInt(4, itemDoPedido.getStock().getIdStock());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(ItemDoPedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
				
		
	}

	public boolean remover(ItemDoPedido itemDoPedido) {
		String sql = "DELETE FROM itensdestock WHERE idItemDeStock=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, itemDoPedido.getIdItemDeStock());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(ItemDoPedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public ObservableList<ItemDoPedido> listar() {
		String sql = "SELECT * FROM itensdestock";
		ObservableList<ItemDoPedido> retorno = FXCollections.observableArrayList();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				ItemDoPedido itemDoPedido = new ItemDoPedido();
				Produto produto = new Produto();
				Pedido pedido = new Pedido();
				itemDoPedido.setIdItemDeStock(resultado.getInt("idItemDeStock"));
				itemDoPedido.setQuantidade(resultado.getInt("quantidade"));
				itemDoPedido.setValor(resultado.getDouble("valor"));

				produto.setIdProduto(resultado.getInt("idProduto"));
				pedido.setIdStock(resultado.getInt("idStock"));

				/** Obtendo os dados completos do Produto associado ao Item de Pedido. */
				ProdutoDAO produtoDAO = new ProdutoDAO();
				produto = produtoDAO.buscar(produto);

				PedidoDAO pedidoDAO = new PedidoDAO();
				pedido = pedidoDAO.buscar(pedido);

				itemDoPedido.setProduto(produto);
				itemDoPedido.setStock(pedido);

				retorno.add(itemDoPedido);
			}
		} catch (SQLException ex) {
			Logger.getLogger(ItemDoPedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

	/** Listar por Pedido. */
	public static ObservableList<ItemDoPedido> listarPorStock(Pedido pedido) {
		String sql = "SELECT * FROM itensdestock WHERE idStock=?";
		ObservableList<ItemDoPedido> retorno = FXCollections.observableArrayList();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, pedido.getIdStock());
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				ItemDoPedido itemDoPedido = new ItemDoPedido();
				Produto produto = new Produto();
				Pedido s = new Pedido();
				itemDoPedido.setIdItemDeStock(resultado.getInt("idItemDeStock"));
				itemDoPedido.setQuantidade(resultado.getInt("quantidade"));
				itemDoPedido.setValor(resultado.getDouble("valor"));

				produto.setIdProduto(resultado.getInt("idProduto"));
				s.setIdStock(resultado.getInt("idStock"));

				/** Obtendo os dados completos do Produto associado ao Item de Pedido. */
				ProdutoDAO produtoDAO = new ProdutoDAO();
				produto = produtoDAO.buscar(produto);

				itemDoPedido.setProduto(produto);
				itemDoPedido.setStock(s);

				retorno.add(itemDoPedido);
			}
		} catch (SQLException ex) {
			Logger.getLogger(ItemDoPedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}
	

	public ItemDoPedido buscar (ItemDoPedido itemDoPedido) {
		String sql = "SELECT * FROM itensdestock WHERE idItemDeStock=?";
		ItemDoPedido retorno = new ItemDoPedido();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, itemDoPedido.getIdItemDeStock());
			ResultSet resultado = stmt.executeQuery();
			if (resultado.next()) {
				Produto produto = new Produto();
				Pedido pedido = new Pedido();
				itemDoPedido.setIdItemDeStock(resultado.getInt("idItemDeStock"));
				itemDoPedido.setQuantidade(resultado.getInt("quantidade"));
				itemDoPedido.setValor(resultado.getDouble("valor"));

				produto.setIdProduto(resultado.getInt("idProduto"));
				pedido.setIdStock(resultado.getInt("idStock"));

				/** Obtendo os dados completos do Colaborador associado a  Pedido. */
				ProdutoDAO produtoDAO = new ProdutoDAO();
				produto = produtoDAO.buscar(produto);

				PedidoDAO pedidoDAO = new PedidoDAO();
				pedido = pedidoDAO.buscar(pedido);

				itemDoPedido.setProduto(produto);
				itemDoPedido.setStock(pedido);

				retorno = itemDoPedido;
			}
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}
}
