package pt.iade.gestaoInventario.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import pt.iade.gestaoInventario.models.Colaborador;

public class RegistoColaboradorController {
	 @FXML
	    private TableColumn<Colaborador, String> tableColumnNome;

	    @FXML
	    private TableColumn<Colaborador, String> tableColumnNumero;

	    @FXML
	    private Label labelCodigo;

	    @FXML
	    private Label labelNome;

	    @FXML
	    private Label labelNumero;

	    @FXML
	    private Label labelTelefone;

	    @FXML
	    private Button buttonAdicionar;

	    @FXML
	    private Button buttonAlterar;

	    @FXML
	    private Button buttonRemover;

	    @FXML
	    void AdicionarColaborador(ActionEvent event) {

	    }

	    @FXML
	    void AlterarColaborador(ActionEvent event) {

	    }

	    @FXML
	    void RemoverColaborador(ActionEvent event) {

	    }

}
