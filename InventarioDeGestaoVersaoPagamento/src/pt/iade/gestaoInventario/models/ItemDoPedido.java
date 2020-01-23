package pt.iade.gestaoInventario.models;

/**
 * 
 * Classe de dados ou de dominio.
 * 
 */
public class ItemDoPedido {

	private int idItemDeStock;
	private int quantidade;
	private double valor;
	private Pedido pedido;
	private Produto produto;

	public ItemDoPedido() {

	}
	
	public ItemDoPedido(int idItemDeStock, int quantidade, double valor) {
		this.idItemDeStock = idItemDeStock;
		this.quantidade = quantidade;
		this.valor = valor;
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

	public Pedido getStock() {
		return pedido;
	}

	public void setStock(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public String toString() {
		return produto.getNome() + " -> " + quantidade + " -> Preço: "+ 
	String.format("%.2f€", getValor());
	}

}
