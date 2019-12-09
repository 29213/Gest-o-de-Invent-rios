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

public class RegistoColaboradorStageController implements Initializable {

	@FXML
	private TextField textFieldColaboradorNome;

	@FXML
	private TextField textFieldColaboradorNumero;

	@FXML
	private TextField textFieldColaboradorTelefone;

	@FXML
	private Button buttonConfirmar;

	@FXML
	private Button buttonCancelar;

	private Stage colaboradorStage;

	private boolean buttonConfirmarClick = false;

	private Colaborador colaborador;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	public Stage getColaboradorStage() {
		return colaboradorStage;
	}

	public void setColaboradorStage(Stage colaboradorStage) {
		this.colaboradorStage = colaboradorStage;
	}

	public boolean isButtonConfirmarClick() {
		return buttonConfirmarClick;
	}

	public void setButtonConfirmarClick(boolean buttonConfirmarClick) {
		this.buttonConfirmarClick = buttonConfirmarClick;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
		this.textFieldColaboradorNome.setText(colaborador.getNome());
		this.textFieldColaboradorNumero.setText(String.valueOf(colaborador.getNumero()));
		this.textFieldColaboradorTelefone.setText(String.valueOf(colaborador.getTelefone()));
	}

	@FXML
	void buttonCancelar(ActionEvent event) {
		colaboradorStage.close();
	}

	@FXML
	void buttonConfirmar(ActionEvent event) {
		if (validarEntradaDeDados()) {
			colaborador.setNome(String.valueOf(textFieldColaboradorNome.getText()));
			colaborador.setNumero(Integer.valueOf(textFieldColaboradorNumero.getText()));
			colaborador.setTelefone(String.valueOf(textFieldColaboradorTelefone.getText()));

			buttonConfirmarClick = true;
			colaboradorStage.close();

		}
	}

	/** Validar entrada de dados para o registo. */
	private boolean validarEntradaDeDados() {
		String errorMessage = "";

		if (textFieldColaboradorNome.getText() == null || textFieldColaboradorNome.getText().length() == 0) {
			errorMessage += "Nome invalido!\n";
		}
		if (textFieldColaboradorNumero.getText() == null || textFieldColaboradorNumero.getText().length() == 0) {
			errorMessage += "Número invalido!\n";
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
			alert.setHeaderText("Campos invalidos, corrija...");
			alert.setContentText(errorMessage);
			alert.show();
			return false;
		}
	}
}
