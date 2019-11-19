package pt.iade.gestaoInventario.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;

public class ProcessoStockController {
	  @FXML
	    private TableColumn<?, ?> tableColumnCodigo;

	    @FXML
	    private TableColumn<?, ?> tableColumnData;

	    @FXML
	    private TableColumn<?, ?> tableColumnColaborador;

	    @FXML
	    private Label labelCodigo;

	    @FXML
	    private Label labelDataStock;

	    @FXML
	    private Label labelColaborador;

	    @FXML
	    private Label labelValor;

	    @FXML
	    private Button buttonPedido;

	    @FXML
	    private Button buttonRemover;

	    @FXML
	    void PedidoStock(ActionEvent event) {

	    }

	    @FXML
	    void RevomerPedido(ActionEvent event) {

	    }
}
