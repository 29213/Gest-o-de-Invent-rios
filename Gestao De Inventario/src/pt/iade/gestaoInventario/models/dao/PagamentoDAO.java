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
import pt.iade.gestaoInventario.models.Pagamento;

// TODO: Auto-generated Javadoc
/**
 * The Class PagamentoDAO.
 */
public class PagamentoDAO {
	
	/**
	 * Inserir.
	 *
	 * @param pagamento the pagamento
	 * @return true, if successful
	 */
	public boolean inserir(Pagamento pagamento) {
		String sql = "INSERT INTO pagamentos(data, valor, estado) VALUES(?,?,?)";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			stmt.setDate(1, Date.valueOf(pagamento.getData()));
			stmt.setDouble(2, pagamento.getValor());
			stmt.setString(3, pagamento.getEstado());
			stmt.execute();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next())
				pagamento.setIdPagamento(rs.getInt(1));
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	/**
	 * Alterar.
	 *
	 * @param pagamento the pagamento
	 * @return true, if successful
	 */
	public boolean alterar(Pagamento pagamento) {
		String sql = "UPDATE pagamentos SET data=?, valor=?, estado=? WHERE idPagamento=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setDate(1, Date.valueOf(pagamento.getData()));
			stmt.setDouble(2, pagamento.getValor());
			stmt.setString(3, pagamento.getEstado());
			stmt.setInt(4, pagamento.getIdPagamento());
			stmt.execute();
			return true;
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
			return false;
		}
	}

	/**
	 * Remover.
	 *
	 * @param pagamento the pagamento
	 * @return true, if successful
	 */
	public boolean remover(Pagamento pagamento) {
		String sql = "DELETE FROM pagamentos WHERE idPagamento=?";
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, pagamento.getIdPagamento());
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
	 * @return the observable list
	 */
	public ObservableList<Pagamento> listar() {
		String sql = "SELECT * FROM pagamentos";
		Connection connection = DBConnection.conectar();
		ObservableList<Pagamento> retorno = FXCollections.observableArrayList();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				Pagamento pagamento = new Pagamento();
				pagamento.setValor(resultado.getDouble("valor"));
				pagamento.setIdPagamento(resultado.getInt("idPagamento"));
				pagamento.setData(resultado.getDate("data").toLocalDate());
				pagamento.setEstado(resultado.getString("estado"));
				retorno.add(pagamento);
			}
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}
	
	/**
	 * Listar por colaborador.
	 *
	 * @param colaborador the colaborador
	 * @return the observable list
	 */
	public ObservableList<Pagamento> listarPorColaborador(Colaborador colaborador){
		String sql = "SELECT p.idPagamento, p.data, p.valor, p.estado FROM pedidos s, pagamentos p  WHERE s.idColaborador=? AND s.idPagamento = p.idPagamento";
		Connection connection = DBConnection.conectar();
		ObservableList<Pagamento> retorno = FXCollections.observableArrayList();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, colaborador.getIdColaborador());
			ResultSet resultado = stmt.executeQuery(); 
			while (resultado.next()) {
				Pagamento pagamento = new Pagamento();
				pagamento.setIdPagamento(resultado.getInt("idPagamento"));
				pagamento.setValor(resultado.getDouble("valor"));
				pagamento.setData(resultado.getDate("data").toLocalDate());
				pagamento.setEstado(resultado.getString("estado"));
				retorno.add(pagamento);
			}
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}
	
	/*public Pagamento buscar(Pagamento pagamento) {
		String sql = "SELECT * FROM pagamentos WHERE idPagamento=?";
		Pagamento retorno = new Pagamento();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, pagamento.getIdPagamento());
			ResultSet resultado = stmt.executeQuery();
			if (resultado.next()) {
				pagamento.setIdPagamento(resultado.getInt("idPagamento"));
				pagamento.setValor(resultado.getDouble("valor"));
				pagamento.setData(resultado.getDate("data").toLocalDate());
				pagamento.setEstado(resultado.getString("estado"));
				retorno = pagamento;
			}
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}*/

	/**
	 * Buscar ultimo pagamento.
	 *
	 * @return the pagamento
	 */
	public Pagamento buscarUltimoPagamento() {
		String sql = "SELECT max(idPagamento) FROM pagamentos";
		Pagamento retorno = new Pagamento();
		Connection connection = DBConnection.conectar();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet resultado = stmt.executeQuery();

			if (resultado.next()) {
				retorno.setIdPagamento(resultado.getInt(1));
				return retorno;
			}
		} catch (SQLException ex) {
			Logger.getLogger(PedidoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return retorno;
	}
}
