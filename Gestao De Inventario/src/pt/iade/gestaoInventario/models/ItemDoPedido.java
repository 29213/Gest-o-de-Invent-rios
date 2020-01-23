package pt.iade.gestaoInventario.models;

// TODO: Auto-generated Javadoc
/**
 * 
 * Classe de dados.
 * 
 * @author Renato Pitta Simões
 */
public class ItemDoPedido {

	/** O id item do pedido. */
	private int idItemDoPedido;
	
	/** A quantidade. */
	private int quantidade;
	
	/** O valor. */
	private double valor;
	
	/** O pedido. */
	private Pedido pedido;
	
	/** O produto. */
	private Produto produto;

	/**
	 * Instancia um novo item do pedido.
	 */
	public ItemDoPedido() {

	}
	
	/**
	 * Instanciar um novo item do pedido.
	 *
	 * @param idItemDoPedido o id item do pedido
	 * @param quantidade a quantidade
	 * @param valor o valor
	 */
	public ItemDoPedido(int idItemDoPedido, int quantidade, double valor) {
		this.idItemDoPedido = idItemDoPedido;
		this.quantidade = quantidade;
		this.valor = valor;
	}


	/**
	 * Obtém o id item do pedido.
	 *
	 * @return o id item do pedido
	 */
	public int getIdItemDoPedido() {
		return idItemDoPedido;
	}

	/**
	 * Define o id item do pedido.
	 *
	 * @param idItemDoPedido o novo id item do pedido
	 */
	public void setIdItemDoPedido(int idItemDoPedido) {
		this.idItemDoPedido = idItemDoPedido;
	}

	/**
	 * Obtém a quantidade.
	 *
	 * @return a quantidade
	 */
	public int getQuantidade() {
		return quantidade;
	}

	/**
	 * Define o quantidade.
	 *
	 * @param quantidade a nova quantidade
	 */
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
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
	 * Obtém o pedido.
	 *
	 * @return o pedido
	 */
	public Pedido getPedido() {
		return pedido;
	}

	/**
	 * Define o pedido.
	 *
	 * @param pedido o novo pedido
	 */
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	/**
	 * Obtém o produto.
	 *
	 * @return o produto
	 */
	public Produto getProduto() {
		return produto;
	}

	/**
	 * Define o produto.
	 *
	 * @param produto o novo produto
	 */
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	/**
	 * To string.
	 *
	 * @return o produto, quantidade e o preço
	 */
	@Override
	public String toString() {
		return produto + " -> " + quantidade + " -> Preço: "+ 
	String.format("%.2f€", getValor());
	}

}
