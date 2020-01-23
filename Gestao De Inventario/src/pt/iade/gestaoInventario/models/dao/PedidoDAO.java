package pt.iade.gestaoInventario.models.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.iade.gestaoInventario.models.Colaborador;
import pt.iade.gestaoInventario.models.ItemDoPedido;
import pt.iade.gestaoInventario.models.Pedido;

// TODO: Auto-generated Javadoc
/**
 * 
 * <p> Esta classe permite ter interação com a base de dados.
 * <p> Permite: Inserir, alterar, remover, listar, buscar e buscar o ultimo pedido.
 * 
 * @author Renato Pitta Simões
 */
public class PedidoDAO {

	/**
	 * Inserir.
	 *
	 * @param pedido o pedido
	 * @return verdadeiro, se for bem sucedido
	 */
	public boolean inserir(Pedido pedido) {
		String sql = "INSERT INTO pedidos(data, valor, idColaborador) VALUES(?,?,?)";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setDate(1, Date.valueOf(pedido.getData()));
			stmt.setDouble(2, pedido.getValor());
			stmt.setInt(3, pedido.getColaborador().getIdColaborador());
			stmt.execute();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next())
				pedido.setIdPedido(rs.getInt(1));
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	/**
	 * Alterar.
	 *
	 * @param pedido o pedido
	 * @return verdadeiro, se for bem sucedido
	 */
	public boolean alterar(Pedido pedido) {
		String sql = "UPDATE pedidos SET data=?, valor=?, idColaborador=? WHERE idPedido=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setDate(1, Date.valueOf(pedido.getData()));
			stmt.setDouble(2, pedido.getValor());
			stmt.setInt(3, pedido.getColaborador().getIdColaborador());
			stmt.setInt(4, pedido.getIdPedido());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}
	
	/*
	public boolean alterarPagamento(Pedido pedido) {
		String sql = "UPDATE pedidos SET idPagamento=? WHERE idPedido=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, pedido.getPagamento().getIdPagamento());
			stmt.setInt(2, pedido.getIdPedido());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}*/

	/**
	 * Remover.
	 *
	 * @param pedido o pedido
	 * @return verdadeiro, se for bem sucedido
	 */
	public boolean remover(Pedido pedido) {
		String sql = "DELETE FROM pedidos WHERE idPedido=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, pedido.getIdPedido());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	/**
	 * Listar.
	 *
	 * @return a lista observável do pedido
	 */
	public ObservableList<Pedido> listar() {
		String sql = "SELECT * FROM pedidos";
		Connection connection = DBConnection.conectar();
		ObservableList<Pedido> retorno = FXCollections.observableArrayList();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				Pedido pedido = new Pedido();
				Colaborador colaborador = new Colaborador();
				ObservableList<ItemDoPedido> itensDoPedido = FXCollections.observableArrayList();

				pedido.setIdPedido(resultado.getInt("idPedido"));
				pedido.setData(resultado.getDate("data").toLocalDate());
				pedido.setValor(resultado.getDouble("valor"));
				colaborador.setIdColaborador(resultado.getInt("idColaborador"));

				/** Obtendo os dados completos do Colaborador associado ao Pedido. */
				ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
				colaborador = colaboradorDAO.buscar(colaborador);

				new ItemDoPedidoDAO();
				itensDoPedido = ItemDoPedidoDAO.listarPorPedido(pedido);

				pedido.setColaborador(colaborador);
				pedido.setItensDoPedido(itensDoPedido);
				retorno.add(pedido);
			}
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

	/**
	 * Buscar.
	 *
	 * @param pedido o pedido
	 * @return o pedido
	 */
	public Pedido buscar(Pedido pedido) {
		String sql = "SELECT * FROM pedidos WHERE idPedido=?";
		Pedido retorno = new Pedido();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, pedido.getIdPedido());
			ResultSet resultado = stmt.executeQuery();
			if (resultado.next()) {
				Colaborador colaborador = new Colaborador();
				pedido.setIdPedido(resultado.getInt("idPedido"));
				pedido.setData(resultado.getDate("data").toLocalDate());
				pedido.setValor(resultado.getDouble("valor"));
				colaborador.setIdColaborador(resultado.getInt("idColaborador"));
				pedido.setColaborador(colaborador);
				retorno = pedido;
			}
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}
	/*
	public Pedido buscarPorPagamento(Pagamento pagamento) {
		String sql = "SELECT * FROM pedidos WHERE idPagamento?";
		Pedido retorno = new Pedido();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, pagamento.getIdPagamento());
			ResultSet resultado = stmt.executeQuery();
			if (resultado.next()) {
				Colaborador colaborador = new Colaborador();
				retorno.setIdPedido(resultado.getInt("idPedido"));
				retorno.setData(resultado.getDate("data").toLocalDate());
				retorno.setValor(resultado.getDouble("valor"));
				colaborador.setIdColaborador(resultado.getInt("idColaborador"));
				retorno.setColaborador(colaborador);
			}
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}*/
	
	/**
	 * Buscar ultimo pedido.
	 *
	 * @return o pedido
	 */
	public Pedido buscarUltimoPedido() {
		String sql = "SELECT max(idPedido) FROM pedidos";
		Pedido retorno = new Pedido();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();

			if (resultado.next()) {
				retorno.setIdPedido(resultado.getInt(1));
				return retorno;
			}
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}
}
