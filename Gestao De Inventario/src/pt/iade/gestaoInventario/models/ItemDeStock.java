package pt.iade.gestaoInventario.models;

public class ItemDeStock {

	private int idItemDeStock;
	private int quantidade;
	private double valor;
	private Stock stock;
	private Produto produto;

	public ItemDeStock() {

	}

	public int getIdItemDeStock() {
		return idItemDeStock;
	}

	public void setIdItemDeStock(int idItemDeStock) {
		this.idItemDeStock = idItemDeStock;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
