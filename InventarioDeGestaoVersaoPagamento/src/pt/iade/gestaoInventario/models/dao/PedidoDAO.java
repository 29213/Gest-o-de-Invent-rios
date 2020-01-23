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
import pt.iade.gestaoInventario.models.Pagamento;
import pt.iade.gestaoInventario.models.Pedido;

/**
 * 
 * Esta classe permite ter intereção com a base de dados.
 *
 */
public class PedidoDAO {
	
	public boolean inserir(Pedido pedido) {
		String sql = "INSERT INTO stocks(data, valor, idColaborador, idPagamento) VALUES(?,?,?,?)";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			stmt.setDate(1, Date.valueOf(pedido.getData()));
			stmt.setDouble(2, pedido.getValor());
			stmt.setInt(3, pedido.getColaborador().getIdColaborador());
			stmt.setDouble(4, 0);
			stmt.execute();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next())
				pedido.setIdStock(rs.getInt(1));
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public boolean alterar(Pedido pedido) {
		String sql = "UPDATE stocks SET data=?, valor=?, idColaborador=? WHERE idStock=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setDate(1, Date.valueOf(pedido.getData()));
			stmt.setDouble(2, pedido.getValor());
			stmt.setInt(3, pedido.getColaborador().getIdColaborador());
			stmt.setInt(4, pedido.getIdStock());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}
	
/***/	
	public boolean alterarPagamento(Pedido pedido) {
		String sql = "UPDATE stocks SET idPagamento=? WHERE idStock=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, pedido.getPagamento().getIdPagamento());
			stmt.setInt(2, pedido.getIdStock());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	public boolean remover(Pedido pedido) {
		String sql = "DELETE FROM stocks WHERE idStock=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, pedido.getIdStock());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	
	public ObservableList<Pedido> listar() {
		String sql = "SELECT * FROM stocks WHERE idPagamento=0";
		Connection connection = DBConnection.conectar();
		ObservableList<Pedido> retorno = FXCollections.observableArrayList();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				Pedido pedido = new Pedido();
				Colaborador colaborador = new Colaborador();
				ObservableList<ItemDoPedido> itensDeStock = FXCollections.observableArrayList();

				pedido.setIdStock(resultado.getInt("idStock"));
				pedido.setData(resultado.getDate("data").toLocalDate());
				pedido.setValor(resultado.getDouble("valor"));
				colaborador.setIdColaborador(resultado.getInt("idColaborador"));

				/** Obtendo os dados completos do Colaborador associado ao Pedido. */
				ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
				colaborador = colaboradorDAO.buscar(colaborador);

				new ItemDoPedidoDAO();
				itensDeStock = ItemDoPedidoDAO.listarPorStock(pedido);

				pedido.setColaborador(colaborador);
				pedido.setItensDeStock(itensDeStock);
				retorno.add(pedido);
			}
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

	public Pedido buscar(Pedido pedido) {
		String sql = "SELECT * FROM stocks WHERE idStock=?";
		Pedido retorno = new Pedido();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, pedido.getIdStock());
			ResultSet resultado = stmt.executeQuery();
			if (resultado.next()) {
				Colaborador colaborador = new Colaborador();
				pedido.setIdStock(resultado.getInt("idStock"));
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
/***/	
	public Pedido buscarPorPagamento(Pagamento pagamento) {
		String sql = "SELECT * FROM stocks WHERE idPagamento=?";
		Pedido retorno = new Pedido();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, pagamento.getIdPagamento());
			ResultSet resultado = stmt.executeQuery();
			if (resultado.next()) {
				Colaborador colaborador = new Colaborador();
				retorno.setIdStock(resultado.getInt("idStock"));
				retorno.setData(resultado.getDate("data").toLocalDate());
				retorno.setValor(resultado.getDouble("valor"));
				colaborador.setIdColaborador(resultado.getInt("idColaborador"));
				retorno.setColaborador(colaborador);
			}
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}

	public Pedido buscarUltimoStock() {
		String sql = "SELECT max(idStock) FROM stocks";
		Pedido retorno = new Pedido();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();

			if (resultado.next()) {
				retorno.setIdStock(resultado.getInt(1));
				return retorno;
			}
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}
	
}
