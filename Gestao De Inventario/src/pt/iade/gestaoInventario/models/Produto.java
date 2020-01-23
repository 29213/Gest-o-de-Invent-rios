package pt.iade.gestaoInventario.models;
// TODO: Auto-generated Javadoc
/**
 * 
 * Classe de dados.
 *
 * @author Renato Pitta Simões
 */
public class Produto {
	
	/** O id produto. */
	private int idProduto;
	
	/** O nome. */
	private String nome;
	
	/** O preco. */
	private double preco;
	
	/** A quantidade. */
	private int quantidade;
	
	/** A categoria. */
	private Categoria categoria;

	/**
	 * Instancia um novo produto.
	 */
	public Produto() {

	}

	/**
	 * Instancia um novo produto.
	 *
	 * @param idProduto the id produto
	 * @param nome o nome
	 * @param preco o preco
	 * @param quantidade a quantidade
	 */
	public Produto(int idProduto, String nome, double preco, int quantidade) {
		this.idProduto = idProduto;
		this.nome = nome;
		this.preco = preco;
		this.quantidade = quantidade;
	}

	/**
	 * Obtém o id produto.
	 *
	 * @return o id produto
	 */
	public int getIdProduto() {
		return idProduto;
	}

	/**
	 * Define o id produto.
	 *
	 * @param idProduto o novo id produto
	 */
	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	/**
	 * Obtém o nome.
	 *
	 * @return o nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Define o nome.
	 *
	 * @param nome o novo nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Obtém o preco.
	 *
	 * @return o preco
	 */
	public double getPreco() {
		return preco;
	}

	/**
	 * Define o preco.
	 *
	 * @param preco o novo preco
	 */
	public void setPreco(double preco) {
		this.preco = preco;
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
	 * Define a  quantidade.
	 *
	 * @param quantidade a nova quantidade
	 */
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * Obtém a categoria.
	 *
	 * @return a categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * Define a categoria.
	 *
	 * @param categoria a nova categoria
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	/**
	 * To string.
	 *
	 * @return o nome
	 */
	@Override
	public String toString() {
		return this.nome;
	}
}
