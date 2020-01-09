package pt.iade.gestaoInventario.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.iade.gestaoInventario.models.Categoria;
import pt.iade.gestaoInventario.models.Produto;
import pt.iade.gestaoInventario.models.dao.ProdutoDAO;

/**
 * Controlador da interface principal, relatorio de inventario
 * Permite visualizar todos os produtos registados numa TableView.
 *
 */
public class RelatorioInventarioController implements Initializable {
	 @FXML
	    private TableView<Produto> tableViewInventario;

	    @FXML
	    private TableColumn<Produto, Integer> tableColumnProdutoCodigo;

	    @FXML
	    private TableColumn<Produto, String> tableColumnProdutoNome;

	    @FXML
	    private TableColumn<Produto, Integer> tableColumnProdutoQuantidade;

	    @FXML
	    private TableColumn<Categoria, String> tableColumnProdutoCategoria;
	    
	    private List<Produto> listProdutos;
	    
	    private ObservableList<Produto> obserbableProdutos;
	    
	    private final ProdutoDAO produtoDAO = new ProdutoDAO();

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			carregarTableViewProdutos();
			
		}
	    /** Configuração da tableView*/
		public void carregarTableViewProdutos() {
			tableColumnProdutoCodigo.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
			tableColumnProdutoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
			tableColumnProdutoQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
			tableColumnProdutoCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
			
			listProdutos = produtoDAO.listar();
			
			obserbableProdutos = FXCollections.observableArrayList(listProdutos);
			tableViewInventario.setItems(obserbableProdutos);
					
		}

}
