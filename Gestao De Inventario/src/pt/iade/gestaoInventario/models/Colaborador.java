package pt.iade.gestaoInventario.models;
// TODO: Auto-generated Javadoc
/**
 * 
 * Classe de dados.
 * 
 * @author Renato Pitta Sim�es
 */
public class Colaborador {
	
	/** O id colaborador. */
	private int idColaborador;
	
	/** O nome. */
	private String nome;
	
	/** O numero. */
	private int numero;
	
	/** O telefone. */
	private String telefone;

	/**
	 * Instancia um novo colaborador.
	 *
	 * @param idColaborador o id colaborador
	 * @param nome o nome
	 * @param numero o numero
	 */
	public Colaborador(int idColaborador, String nome, int numero) {
		this.idColaborador = idColaborador;
		this.nome = nome;
		this.numero = numero;
	}

	/**
	 * Instanciar um novo colaborador.
	 */
	public Colaborador() {
	}

	/**
	 * Obt�m o id colaborador.
	 *
	 * @return o id colaborador
	 */
	public int getIdColaborador() {
		return idColaborador;
	}

	/**
	 * Define o id colaborador.
	 *
	 * @param idColaborador o novo id colaborador
	 */
	public void setIdColaborador(int idColaborador) {
		this.idColaborador = idColaborador;
	}

	/**
	 * Obt�m o nome.
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
	 * Obt�m o n�mero.
	 *
	 * @return o numero
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Obt�m o n�mero.
	 *
	 * @param numero o novo numero
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * Obt�m o telefone.
	 *
	 * @return o telefone
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * Define o telefone.
	 *
	 * @param string o novo telefone
	 */
	public void setTelefone(String string) {
		this.telefone = string;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return this.nome;
	}

}
