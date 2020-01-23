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

// TODO: Auto-generated Javadoc
/**
 * 
 * Esta classe permite ter interação com a base de dados.
 * 
 * @author Renato Pitta Simões
 *
 */
	public class ItemDoPedidoDAO {

	/**
	 * Inserir.
	 *
	 * @param itemDoPedido o item do pedido
	 * @return verdadeiro, se for bem sucedido
	 */
	public boolean inserir(ItemDoPedido itemDoPedido) {
		String sql = "INSERT INTO itensdopedido(quantidade, valor, idProduto, idPedido) VALUES (?,?,?,?)";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, itemDoPedido.getQuantidade());
			stmt.setDouble(2, itemDoPedido.getValor());
			stmt.setInt(3, itemDoPedido.getProduto().getIdProduto());
			stmt.setInt(4, itemDoPedido.getPedido().getIdPedido());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(ItemDoPedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	/**
	 * Alterar.
	 *
	 * @param itemDoPedido o item do pedido
	 * @return verdadeiro, se for bem sucedido
	 */
	public boolean alterar(ItemDoPedido itemDoPedido) {
		String sql = "UPDATE itensdopedido SET quantidade =?, valor =?, idProduto =?, idPedido =? WHERE idItemDoPedido =?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, itemDoPedido.getQuantidade());
			stmt.setDouble(2, itemDoPedido.getValor());
			stmt.setInt(3, itemDoPedido.getProduto().getIdProduto());
			stmt.setInt(4, itemDoPedido.getPedido().getIdPedido());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(ItemDoPedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
				
		
	}

	/**
	 * Remover.
	 *
	 * @param itemDoPedido o item do pedido
	 * @return verdadeiro, se for bem sucedido
	 */
	public boolean remover(ItemDoPedido itemDoPedido) {
		String sql = "DELETE FROM itensdopedido WHERE idItemDoPedido=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, itemDoPedido.getIdItemDoPedido());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(ItemDoPedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	/**
	 * Listar.
	 *
	 * @return a lista observável
	 */
	public ObservableList<ItemDoPedido> listar() {
		String sql = "SELECT * FROM itensdopedido";
		ObservableList<ItemDoPedido> retorno = FXCollections.observableArrayList();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				ItemDoPedido itemDoPedido = new ItemDoPedido();
				Produto produto = new Produto();
				Pedido pedido = new Pedido();
				itemDoPedido.setIdItemDoPedido(resultado.getInt("idItemDoPedido"));
				itemDoPedido.setQuantidade(resultado.getInt("quantidade"));
				itemDoPedido.setValor(resultado.getDouble("valor"));

				produto.setIdProduto(resultado.getInt("idProduto"));
				pedido.setIdPedido(resultado.getInt("idPedodo"));

				/** Obtendo os dados completos do Produto associado ao Item de Pedido. */
				ProdutoDAO produtoDAO = new ProdutoDAO();
				produto = produtoDAO.buscar(produto);

				PedidoDAO pedidoDAO = new PedidoDAO();
				pedido = pedidoDAO.buscar(pedido);

				itemDoPedido.setProduto(produto);
				itemDoPedido.setPedido(pedido);

				retorno.add(itemDoPedido);
			}
		} catch (SQLException ex) {
			Logger.getLogger(ItemDoPedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

	/**
	 *  Listar por Pedido.
	 *
	 * @param pedido o pedido
	 * @return a lista observável
	 */
	public static ObservableList<ItemDoPedido> listarPorPedido(Pedido pedido) {
		String sql = "SELECT * FROM itensdopedido WHERE idPedido=?";
		ObservableList<ItemDoPedido> retorno = FXCollections.observableArrayList();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, pedido.getIdPedido());
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				ItemDoPedido itemDoPedido = new ItemDoPedido();
				Produto produto = new Produto();
				Pedido p = new Pedido();
				itemDoPedido.setIdItemDoPedido(resultado.getInt("idItemDoPedido"));
				itemDoPedido.setQuantidade(resultado.getInt("quantidade"));
				itemDoPedido.setValor(resultado.getDouble("valor"));

				produto.setIdProduto(resultado.getInt("idProduto"));
				p.setIdPedido(resultado.getInt("idPedido"));

				/** Obtendo os dados completos do Produto associado ao Item de Pedido. */
				ProdutoDAO produtoDAO = new ProdutoDAO();
				produto = produtoDAO.buscar(produto);

				itemDoPedido.setProduto(produto);
				itemDoPedido.setPedido(p);

				retorno.add(itemDoPedido);
			}
		} catch (SQLException ex) {
			Logger.getLogger(ItemDoPedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}
	

	/**
	 * Buscar.
	 *
	 * @param itemDoPedido o item do pedido
	 * @return o item do pedido
	 */
	public ItemDoPedido buscar (ItemDoPedido itemDoPedido) {
		String sql = "SELECT * FROM itensdopedido WHERE idItemDoPedido=?";
		ItemDoPedido retorno = new ItemDoPedido();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, itemDoPedido.getIdItemDoPedido());
			ResultSet resultado = stmt.executeQuery();
			if (resultado.next()) {
				Produto produto = new Produto();
				Pedido pedido = new Pedido();
				itemDoPedido.setIdItemDoPedido(resultado.getInt("idItemDoPedido"));
				itemDoPedido.setQuantidade(resultado.getInt("quantidade"));
				itemDoPedido.setValor(resultado.getDouble("valor"));

				produto.setIdProduto(resultado.getInt("idProduto"));
				pedido.setIdPedido(resultado.getInt("idPedido"));

				/** Obtendo os dados completos do Colaborador associado a  Pedido. */
				ProdutoDAO produtoDAO = new ProdutoDAO();
				produto = produtoDAO.buscar(produto);

				PedidoDAO pedidoDAO = new PedidoDAO();
				pedido = pedidoDAO.buscar(pedido);

				itemDoPedido.setProduto(produto);
				itemDoPedido.setPedido(pedido);

				retorno = itemDoPedido;
			}
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}
}
