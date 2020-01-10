package pt.iade.gestaoInventario.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import pt.iade.gestaoInventario.Main;

/**
 * Controlador da interface Tela principal. 
 * Permite visualizar na tela os menus:
 * 	<li> Regsito de Colaboaradores {@link RegistoColaboradorController}, 
 *  <li> Regsito de Categorias {@link RegistoCategoriaController},
 *  <li> Registo de Produtos {@link RegistoProdutoController},
 * 	<li> Processo de pedido {@link ProcessoStockController}
 * 	<li> Relatorio de Inventários {@link RelatorioInventarioController}
 */

public class TelaPrincipalController implements Initializable {

	@FXML
    private MenuItem menuItemRegistoColaboradores;

    @FXML
    private MenuItem menuItemRegistoCategorias;

    @FXML
    private MenuItem menuItemRegistoProdutos;

    @FXML
    private MenuItem menuItemProcessoEstoque;

    @FXML
    private MenuItem menuItemRelatorioInventarios;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    void handleMenuItemProcessoEstoque(ActionEvent event) throws IOException  {
    	AnchorPane a = FXMLLoader.load(Main.class.getResource("./views/ProcessoStock.fxml"));
		anchorPane.getChildren().setAll(a);
    }

    @FXML
    void handleMenuItemRegistoCategorias(ActionEvent event) throws IOException {
    	AnchorPane c = FXMLLoader.load(Main.class.getResource("./views/RegistoCategoria.fxml"));
		anchorPane.getChildren().setAll(c);
    }

    @FXML
    void handleMenuItemRegistoColaboradores(ActionEvent event) throws IOException {
    	AnchorPane c = FXMLLoader.load(Main.class.getResource("./views/RegistoColaborador.fxml"));
		anchorPane.getChildren().setAll(c);
    }

    @FXML
    void handleMenuItemRegistoProdutos(ActionEvent event) throws IOException {
    	AnchorPane d;
    	d = FXMLLoader.load(Main.class.getResource("./views/RegistoProduto.fxml"));
    	anchorPane.getChildren().setAll(d);
    }

    @FXML
    void handleMenuRelatorioItemInventario(ActionEvent event) throws IOException  {
    	AnchorPane e = FXMLLoader.load(Main.class.getResource("./views/RelatorioInventario.fxml"));
		anchorPane.getChildren().setAll(e);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
