package pt.iade.gestaoInventario.controllers;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

/**
 * Controlador da interface principal. 
 * Permite visualizar a tela de:
 * 	<li>Regsito de: Colaboaradores, Categorias e Produtos.
 * 	<li>Processo de Estoques.
 * 	<li>Inventários.
 * 
 * @author Legionario
 *
 */

public class ControllerTelaPrincipal implements Initializable {

	@FXML
	private MenuItem menuItemRegistoColaboaradores;

	@FXML
	private MenuItem menuItemRegistoCategorias;

	@FXML
	private MenuItem menuItemRegistoProdutos;

	@FXML
	private MenuItem menuItemProcessoEstoques;

	@FXML
	private Menu menuItemInventarios;

	@FXML
	private AnchorPane anchorPane;

	@FXML
	void handleMenuItemInventario(ActionEvent event) throws IOException {
	AnchorPane a = FXMLLoader.load(getClass().getResource(""));
	anchorPane.getChildren().setAll(a);
	}

	@FXML
	void handleMenuItemProcessoEstoques(ActionEvent event) throws IOException {
		AnchorPane b = FXMLLoader.load(getClass().getResource(""));
		anchorPane.getChildren().setAll(b);
	}

	@FXML
	void handleMenuItemRegistoCategorias(ActionEvent event) throws IOException {
		AnchorPane c = FXMLLoader.load(getClass().getResource(""));
		anchorPane.getChildren().setAll(c);
	}

	@FXML
	void handleMenuItemRegistoColaboaradores(ActionEvent event) throws IOException {
		AnchorPane b = FXMLLoader.load(getClass().getResource(""));
		anchorPane.getChildren().setAll(b);
	}

	@FXML
	void handleMenuItemRegistoProdutos(ActionEvent event) throws IOException {
		AnchorPane d = FXMLLoader.load(getClass().getResource(""));
		anchorPane.getChildren().setAll(d);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
