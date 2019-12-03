package pt.iade.gestaoInventario.models;

public class Colaborador {
	private int idColaboarador;
	private String nome;
	private int numero;
	private String telefone;

	public Colaborador(int idColaboarador, String nome, int numero) {
		this.idColaboarador = idColaboarador;
		this.nome = nome;
		this.numero = numero;
	}

	public Colaborador() {
	}

	public int getIdColaboarador() {
		return idColaboarador;
	}

	public void setIdColaboarador(int idColaboarador) {
		this.idColaboarador = idColaboarador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getnColaborador() {
		return numero;
	}

	public void setnColaborador(int nColaborador) {
		this.numero = nColaborador;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toString() {
		return this.nome;
	}

}
