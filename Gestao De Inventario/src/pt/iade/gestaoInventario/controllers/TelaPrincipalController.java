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

// TODO: Auto-generated Javadoc
/**
 * <p> Controlador da interface Tela principal. 
 * <p> Permite visualizar na tela os menus:
 * 	<li> Regsito de Colaboaradores {@link RegistoColaboradorController}, 
 *  <li> Regsito de Categorias {@link RegistoCategoriaController},
 *  <li> Registo de Produtos {@link RegistoProdutoController},
 * 	<li> Processo de pedido {@link ProcessoPedidoController}
 * 	<li> Relatorio de Inventários {@link RelatorioInventarioController}
 * 
 * @author Renato Pitta Simões
 */

public class TelaPrincipalController implements Initializable {

	/** O menu item registo colaboradores. */
	@FXML
    private MenuItem menuItemRegistoColaboradores;

    /** O menu item registo categorias. */
    @FXML
    private MenuItem menuItemRegistoCategorias;

    /** O menu item registo produtos. */
    @FXML
    private MenuItem menuItemRegistoProdutos;

    /** O menu item processo Pedido. */
    @FXML
    private MenuItem menuItemProcessoPedido;
    
    /** O menu item processo Pagamento. */
    @FXML
    private MenuItem menuItemProcessoPagamento;

    /** O menu item relatorio inventarios. */
    @FXML
    private MenuItem menuItemRelatorioInventarios;

    /** O painel de âncora. */
    @FXML
    private AnchorPane anchorPane;

    /**
     * Manipular item de menu processo Pedido.
     *
     * @param event the event
     * @throws IOException Sinaliza que ocorreu uma exceção de E / S.
     */
    @FXML
    void handleMenuItemProcessoPedido(ActionEvent event) throws IOException  {
    	AnchorPane pp = FXMLLoader.load(Main.class.getResource("./views/ProcessoPedido.fxml"));
		anchorPane.getChildren().setAll(pp);
    }
    
    /**
     * Manipular item de menu processo Pagamento.
     *
     * @param event the event
     * @throws IOException Sinaliza que ocorreu uma exceção de E / S.
     */
    @FXML
    void handleMenuItemProcessoPagamento(ActionEvent event) throws IOException {
    	AnchorPane p = FXMLLoader.load(Main.class.getResource("./views/ProcessoPagamento.fxml"));
		anchorPane.getChildren().setAll(p);
    }

    /**
     * Manipular menu item registo categorias.
     *
     * @param event the event
     * @throws IOException Sinaliza que ocorreu uma exceção de E / S.
     */
    @FXML
    void handleMenuItemRegistoCategorias(ActionEvent event) throws IOException {
    	AnchorPane c = FXMLLoader.load(Main.class.getResource("./views/RegistoCategoria.fxml"));
		anchorPane.getChildren().setAll(c);
    }

    /**
     * Manipular menu item registo colaboradores.
     *
     * @param event the event
     * @throws IOException Sinaliza que ocorreu uma exceção de E / S.
     */
    @FXML
    void handleMenuItemRegistoColaboradores(ActionEvent event) throws IOException {
    	AnchorPane c = FXMLLoader.load(Main.class.getResource("./views/RegistoColaborador.fxml"));
		anchorPane.getChildren().setAll(c);
    }

    /**
     * Manipular menu item registo produtos.
     *
     * @param event the event
     * @throws IOException Sinaliza que ocorreu uma exceção de E / S.
     */
    @FXML
    void handleMenuItemRegistoProdutos(ActionEvent event) throws IOException {
    	AnchorPane d;
    	d = FXMLLoader.load(Main.class.getResource("./views/RegistoProduto.fxml"));
    	anchorPane.getChildren().setAll(d);
    }

    /**
     * Manipular menu relatorio item inventario.
     *
     * @param event the event
     * @throws IOException Sinaliza que ocorreu uma exceção de E / S.
     */
    @FXML
    void handleMenuRelatorioItemInventario(ActionEvent event) throws IOException  {
    	AnchorPane e = FXMLLoader.load(Main.class.getResource("./views/RelatorioInventario.fxml"));
		anchorPane.getChildren().setAll(e);
    }

	/**
	 * Inicializar.
	 * 
	 * @param url the url
	 * @param rb the rb
	 * 
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
}
