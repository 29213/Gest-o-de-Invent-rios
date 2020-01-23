package pt.iade.gestaoInventario.models;

import java.time.LocalDate;
import java.util.List;
// TODO: Auto-generated Javadoc
/**
 * 
 * Classe de dados.
 * 
 * @author Renato Pitta Simões
 */
public class Pedido {
	
	/** O id pedido. */
	private int idPedido;
	
	/** A data. */
	private LocalDate data;
	
	/** O valor. */
	private double valor;
	
	/** Lista de itens do pedido. */
	private List<ItemDoPedido> itensDoPedido;
	
	/** O colaborador. */
	private Colaborador colaborador;
	
	/**  O pagamento. */
	private Pagamento pagamento;
	
	/**
	 * Instancia um novo pedido.
	 */
	public Pedido() {

	}

	/**
	 * Instancia um novo pedido..
	 *
	 * @param idPedido o id pedido
	 * @param data a data
	 * @param valor o valor
	 */
	public Pedido(int idPedido, LocalDate data, double valor) {
		this.idPedido = idPedido;
		this.data = data;
		this.valor = valor;
		
	}

	/**
	 * Obtém o id pedido.
	 *
	 * @return o id pedido
	 */
	public int getIdPedido() {
		return idPedido;
	}

	/**
	 * Define o id pedido.
	 *
	 * @param idPedido o novo id pedido
	 */
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	/**
	 * Obtém a data.
	 *
	 * @return a data
	 */
	public LocalDate getData() {
		return data;
	}

	/**
	 * Define a data.
	 *
	 * @param data a nova data
	 */
	public void setData(LocalDate data) {
		this.data = data;
	}

	/**
	 * Obtém o valor.
	 *
	 * @return o valor
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * Define o valor.
	 *
	 * @param valor o novo valor
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	/**
	 * Obtém os itens do pedido..
	 *
	 * @return os itens do pedido
	 */
	public List<ItemDoPedido> getItensDoPedido() {
		return itensDoPedido;
	}

	/**
	 * Define os itens do pedido.
	 *
	 * @param itensDoPedido o novo itens do pedido
	 */
	public void setItensDoPedido(List<ItemDoPedido> itensDoPedido) {
		this.itensDoPedido = itensDoPedido;
	}

	/**
	 * Obtém o colaborador.
	 *
	 * @return o colaborador
	 */
	public Colaborador getColaborador() {
		return colaborador;
	}

	/**
	 * Define o colaborador.
	 *
	 * @param colaborador o novo colaborador
	 */
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	/**
	 * Gets the pagamento.
	 *
	 * @return the pagamento
	 */
	public Pagamento getPagamento() {
		return pagamento;
	}

	/**
	 * Sets the pagamento.
	 *
	 * @param pagamento the new pagamento
	 */
	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

}
