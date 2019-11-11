package pt.iade.gestaoInventario.models;

public class Categoria {
	private int idCategoria;
	private String descri��o;

	public Categoria(int idCategoria, String descri��o) {
		this.idCategoria = idCategoria;
		this.descri��o = descri��o;
	}

	public Categoria() {

	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getDescri��o() {
		return descri��o;
	}

	public void setDescri��o(String descri��o) {
		this.descri��o = descri��o;
	}

	@Override
	public String toString() {
		return this.descri��o;
	}

}
