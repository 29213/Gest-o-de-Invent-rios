package pt.iade.gestaoInventario.models;

import java.time.LocalDate;
import java.util.List;
/**
 * 
 * Classe de dados ou de dominio.
 * 
 *
 */
public class Stock {
	private int idStock;
	private LocalDate data;
	private double valor;
	private List<ItemDeStock> itensDeStock;
	private Colaborador colaborador;

	public Stock() {

	}

	public Stock(int idStock, LocalDate data, double valor, Colaborador colaborador) {
		super();
		this.idStock = idStock;
		this.data = data;
		this.valor = valor;
		this.colaborador = colaborador;
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

	public List<ItemDeStock> getItensDeStock() {
		return itensDeStock;
	}

	public void setItensDeStock(List<ItemDeStock> itensDeStock) {
		this.itensDeStock = itensDeStock;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

}
