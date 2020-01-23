package pt.iade.gestaoInventario.models;

import java.time.LocalDate;

enum Estado {
	  PAGO,
	  PENDENTE,
	  CANCELADO
}


public class Pagamento {
	

	private int idPagamento;
	private double valor;
	private LocalDate data;
	private Estado estado;

	public Pagamento() {

	}

	public Pagamento(int idPagamento, LocalDate data, Estado estado) {
		this.idPagamento = idPagamento;
		this.data = data;
		this.estado = estado;
	}

	public int getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(int idPagamento) {
		this.idPagamento = idPagamento;
	}

	public LocalDate getData() {
		return data;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getValor() {
		return valor;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getEstado() {
		return estado.name();
	}

	public void setEstado(String estado) {
		this.estado = Estado.valueOf(estado);
	}
	
	
	/*ver se a data de pagamento e antes ou depois da data atual*/
	public void definirEstado() {
		LocalDate currentDate = LocalDate.now();
		if (currentDate.isAfter(data) || currentDate.isEqual(data)){
			estado = Estado.PAGO;
		}else {
			estado = Estado.PENDENTE;
		}
	}
		
	

	@Override
	public String toString() {
		return "ID: "+ idPagamento + " Data: " + data + "Estado: " + estado;
	}

}
