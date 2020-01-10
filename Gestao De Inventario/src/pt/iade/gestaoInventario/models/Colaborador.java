package pt.iade.gestaoInventario.models;
/**
 * 
 * Classe de dados.
 *
 */
public class Colaborador {
	private int idColaborador;
	private String nome;
	private int numero;
	private String telefone;

	public Colaborador(int idColaborador, String nome, int numero) {
		this.idColaborador = idColaborador;
		this.nome = nome;
		this.numero = numero;
	}

	public Colaborador() {
	}

	public int getIdColaborador() {
		return idColaborador;
	}

	public void setIdColaborador(int idColaborador) {
		this.idColaborador = idColaborador;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String string) {
		this.telefone = string;
	}

	@Override
	public String toString() {
		return this.nome;
	}

}
