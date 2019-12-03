package pt.iade.gestaoInventario.models;

public class ItemStock {

	private int idItemDeStock;
	private int quantidade;
	private Stock stock;
	private Produto produto;

	public ItemStock() {

	}

	public int getIdItemDeEstoque() {
		return idItemDeStock;
	}

	public void setIdItemDeEstoque(int idItemDeStock) {
		this.idItemDeStock = idItemDeStock;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Stock getEstoque() {
		return stock;
	}

	public void setEstoque(Stock stock) {
		this.stock = stock;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}
