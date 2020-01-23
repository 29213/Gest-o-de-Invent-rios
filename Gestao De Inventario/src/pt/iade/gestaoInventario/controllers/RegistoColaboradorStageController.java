package pt.iade.gestaoInventario.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.iade.gestaoInventario.models.Colaborador;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * Controlador da interface do registo do colaborador;
 * <p>
 * Permite adicionar os dados do colaboarar.
 * <p>
 * Permite alterar os dados do colaborador selecionado.
 * 
 * @author Renato Pitta Sim�es
 * 
 */
public class RegistoColaboradorStageController implements Initializable {

	/** O campo de texto colaborador nome. */
	@FXML
	private TextField textFieldColaboradorNome;

	/** O campo de texto colaborador numero. */
	@FXML
	private TextField textFieldColaboradorNumero;

	/** O campo de texto colaborador telefone. */
	@FXML
	private TextField textFieldColaboradorTelefone;

	/** O bot�o confirmar. */
	@FXML
	private Button buttonConfirmar;

	/** O bot�o cancelar. */
	@FXML
	private Button buttonCancelar;

	/** O colaborador stage. */
	private Stage colaboradorStage;

	/** O bot�o confirmar click. */
	private boolean buttonConfirmarClick = false;

	/** The colaborador. */
	private Colaborador colaborador;

	/**
	 * Initializa.
	 *
	 * @param location  the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Obt�m o colaborador stage.
	 *
	 * @return o colaborador stage
	 */
	public Stage getColaboradorStage() {
		return colaboradorStage;
	}

	/**
	 * Define o colaborador stage.
	 *
	 * @param colaboradorStage o novo colaborador stage
	 */
	public void setColaboradorStage(Stage colaboradorStage) {
		this.colaboradorStage = colaboradorStage;
	}

	/**
	 * Verifique se o bot�o confirma click.
	 *
	 * @return verdadeiro, se for o bot�o confirmar click
	 */
	public boolean isButtonConfirmarClick() {
		return buttonConfirmarClick;
	}

	/**
	 * Define o bot�o confirmar click.
	 *
	 * @param buttonConfirmarClick o novo bot�o confirmar click
	 */
	public void setButtonConfirmarClick(boolean buttonConfirmarClick) {
		this.buttonConfirmarClick = buttonConfirmarClick;
	}

	/**
	 * Obt�m o colaborador.
	 *
	 * @return o colaborador
	 */
	public Colaborador getColaborador() {
		return colaborador;
	}

	/**
	 * Define o colaborador.
	 *
	 * @param colaborador o novo colaborador.
	 */
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
		this.textFieldColaboradorNome.setText(colaborador.getNome());
		this.textFieldColaboradorNumero.setPromptText(String.valueOf(colaborador.getNumero()));
		this.textFieldColaboradorTelefone.setText(colaborador.getTelefone());
	}

	/**
	 * Bot�o cancelar.
	 *
	 * @param event the event
	 */
	@FXML
	void buttonCancelar(ActionEvent event) {
		colaboradorStage.close();
	}

	/**
	 * Bot�o confirmar.
	 *
	 * @param event the event
	 */
	@FXML
	void buttonConfirmar(ActionEvent event) {
		if (validarEntradaDeDados()) {
			colaborador.setNome(textFieldColaboradorNome.getText());
			colaborador.setNumero(Integer.valueOf(textFieldColaboradorNumero.getText()));
			colaborador.setTelefone(textFieldColaboradorTelefone.getText());

			buttonConfirmarClick = true;
			colaboradorStage.close();

		}
	}

	/**
	 * Validar entrada de dados para o registo.
	 *
	 * @return verdadeiro, se for bem sucedido
	 */
	private boolean validarEntradaDeDados() {
		String errorMessage = "";

		if (textFieldColaboradorNome.getText() == null || textFieldColaboradorNome.getText().length() == 0) {
			errorMessage += "Nome invalido!\n";
		}
		if (textFieldColaboradorNumero.getText() == null || textFieldColaboradorNumero.getText().length() == 0) {
			errorMessage += "N�mero invalido!\n";
		}
		if (textFieldColaboradorTelefone.getText() == null || textFieldColaboradorTelefone.getText().length() == 0) {
			errorMessage += "Telefone invalido!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			/** Mostrar a mensagem de erro. */
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erro no registo");
			alert.setHeaderText("Campos invalidos");
			alert.setContentText(errorMessage);
			alert.show();
			return false;
		}
	}
}
