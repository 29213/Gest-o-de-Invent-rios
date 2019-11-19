package pt.iade.gestaoInventario.models;

import java.time.LocalDate;
import java.util.List;

public class Estoque {
	private int idEstoque;
	private LocalDate data;
	private double valor;
	private List<ItemDeEstoque> itensDeEstoque;
	private Colaborador colaborador;

	public Estoque() {

	}

	public Estoque(int idEstoque, LocalDate data, double valor) {
		this.idEstoque = idEstoque;
		this.data = data;
		this.valor = valor;
	}

	public int getIdEstoque() {
		return idEstoque;
	}

	public void setIdEstoque(int idEstoque) {
		this.idEstoque = idEstoque;
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

	public List<ItemDeEstoque> getItensDeEstoque() {
		return itensDeEstoque;
	}

	public void setItensDeInventario(List<ItemDeEstoque> itensDeEstoque) {
		this.itensDeEstoque = itensDeEstoque;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

}
