package pt.iade.gestaoInventario.models;

import java.time.LocalDate;
import java.util.List;
/**
 * 
 * Classe de dados.
 * 
 * @author Renato Pitta Simões
 */
public class Pedido {
	private int idStock;
	private LocalDate data;
	private double valor;
	private List<ItemDoPedido> itensDeStock;
	private Colaborador colaborador;

	public Pedido() {

	}

	public Pedido(int idStock, LocalDate data, double valor) {
		this.idStock = idStock;
		this.data = data;
		this.valor = valor;
		
	}




	public int getIdStock() {
		return idStock;
	}

	public void setIdStock(int idStock) {
		this.idStock = idStock;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public List<ItemDoPedido> getItensDeStock() {
		return itensDeStock;
	}

	public void setItensDeStock(List<ItemDoPedido> itensDeStock) {
		this.itensDeStock = itensDeStock;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

}
