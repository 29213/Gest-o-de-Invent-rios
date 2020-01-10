package pt.iade.gestaoInventario.models;
/**
 * 
 * Classe de dados.
 *
 *
 */
public class Categoria {
	private int idCategoria;
	private String descricao;

	public Categoria(int idCategoria, String descricao) {
		this.idCategoria = idCategoria;
		this.descricao = descricao;
	}

	public Categoria() {

	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
