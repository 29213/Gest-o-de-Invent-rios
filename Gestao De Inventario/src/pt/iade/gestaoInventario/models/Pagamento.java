package pt.iade.gestaoInventario.models;

import java.time.LocalDate;

// TODO: Auto-generated Javadoc
enum Estado {
	PAGO, PENDENTE, CANCELADO
}

/**
 * The Class Pagamento.
 */
public class Pagamento {

	/** The id pagamento. */
	private int idPagamento;
	
	/** The valor. */
	private double valor;
	
	/** The data. */
	private LocalDate data;
	
	/** The estado. */
	private Estado estado;

	/**
	 * Instantiates a new pagamento.
	 */
	public Pagamento() {

	}

	/**
	 * Instantiates a new pagamento.
	 *
	 * @param idPagamento the id pagamento
	 * @param data the data
	 * @param estado the estado
	 */
	public Pagamento(int idPagamento, LocalDate data, Estado estado) {
		this.idPagamento = idPagamento;
		this.data = data;
		this.estado = estado;
	}

	/**
	 * Gets the id pagamento.
	 *
	 * @return the id pagamento
	 */
	public int getIdPagamento() {
		return idPagamento;
	}

	/**
	 * Sets the id pagamento.
	 *
	 * @param idPagamento the new id pagamento
	 */
	public void setIdPagamento(int idPagamento) {
		this.idPagamento = idPagamento;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public LocalDate getData() {
		return data;
	}

	/**
	 * Sets the valor.
	 *
	 * @param valor the new valor
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	/**
	 * Gets the valor.
	 *
	 * @return the valor
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(LocalDate data) {
		this.data = data;
	}

	/**
	 * Gets the estado.
	 *
	 * @return the estado
	 */
	public String getEstado() {
		return estado.name();
	}

	/**
	 * Sets the estado.
	 *
	 * @param estado the new estado
	 */
	public void setEstado(String estado) {
		this.estado = Estado.valueOf(estado);
	}

	/**
	 * Definir estado.
	 */
	/* ver se a data de pagamento e antes ou depois da data atual */
	public void definirEstado() {
		LocalDate currentDate = LocalDate.now();
		if (currentDate.isAfter(data) || currentDate.isEqual(data)) {
			estado = Estado.PAGO;
		} else {
			estado = Estado.PENDENTE;
		}
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "ID: " + idPagamento + " Data: " + data + "Estado: " + estado;
	}

}
