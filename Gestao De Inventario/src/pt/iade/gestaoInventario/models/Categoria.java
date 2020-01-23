package pt.iade.gestaoInventario.models;
// TODO: Auto-generated Javadoc
/**
 * 
 * Classe de dados.
 *
 * @author Renato Pitta Simões
 */
public class Categoria {
	
	/** O id categoria. */
	private int idCategoria;
	
	/** A descricao. */
	private String descricao;

	/**
	 * Instancia uma nova categoria.
	 *
	 * @param idCategoria o id categoria
	 * @param descricao o descricao
	 */
	public Categoria(int idCategoria, String descricao) {
		this.idCategoria = idCategoria;
		this.descricao = descricao;
	}

	/**
	 * Instancia uma nova categoria.
	 */
	public Categoria() {

	}

	/**
	 * Obtém a categoria id.
	 *
	 * @return o id categoria
	 */
	public int getIdCategoria() {
		return idCategoria;
	}

	/**
	 * Define o id categoria.
	 *
	 * @param idCategoria o novo id categoria
	 */
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	/**
	 * Obtém a descricao.
	 *
	 * @return a descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * Define a descrição.
	 *
	 * @param descricao o nova descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * To string.
	 *
	 * @return a descricao
	 */
	@Override
	public String toString() {
		return this.descricao;
	}

}
