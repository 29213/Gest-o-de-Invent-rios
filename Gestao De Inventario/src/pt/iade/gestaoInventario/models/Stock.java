package pt.iade.gestaoInventario.models;

import java.time.LocalDate;
import java.util.List;

public class Stock {
	private int idStock;
	private LocalDate data;
	private double valor;
	private List<ItemStock> itensDeStock;
	private Colaborador colaborador;

	public Stock() {

	}

	public Stock(int idStock, LocalDate data, double valor) {
		this.idStock = idStock;
		this.data = data;
		this.valor = valor;
	}

	public int getIdStock() {
		return idStock;
	}

	public void setidStock(int idStock) {
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

	public List<ItemStock> getItensDeStock() {
		return itensDeStock;
	}

	public void setItensDeInventario(List<ItemStock> itensDeStock) {
		this.itensDeStock = itensDeStock;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

}
