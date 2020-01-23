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

// TODO: Auto-generated Javadoc
/**
 * <p> Controlador da interface principal, relatorio de inventario
 * <p> Permite visualizar todos os produtos registados numa Tabela.
 *
 * @author Renato Pitta Simões
 */
public class RelatorioInventarioController implements Initializable {
	 
 	/** Tabela de inventario. */
 	@FXML
	    private TableView<Produto> tableViewInventario;

	    /** A coluna da tabela produto codigo. */
    	@FXML
	    private TableColumn<Produto, Integer> tableColumnProdutoCodigo;

	    /** A coluna da tabela produto nome. */
    	@FXML
	    private TableColumn<Produto, String> tableColumnProdutoNome;

	    /** A coluna da tabela produto quantidade. */
    	@FXML
	    private TableColumn<Produto, Integer> tableColumnProdutoQuantidade;

	    /** A coluna da tabela produto categoria. */
    	@FXML
	    private TableColumn<Categoria, String> tableColumnProdutoCategoria;
	    
	    /** Lista de produtos. */
    	private List<Produto> listProdutos;
	    
	    /** Lista observáveis de produtos. */
    	private ObservableList<Produto> observableListProdutos;
	    
	    /** Atributo para manipulação da base de dados. */
    	private final ProdutoDAO produtoDAO = new ProdutoDAO();

		/**
		 * Inicializar.
		 *
		 * @param location the location
		 * @param resources the resources
		 */
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			carregarTableViewProdutos();
			
		}
	    
    	/**
    	 *  Carregar tabela de produtos.
    	 */
		public void carregarTableViewProdutos() {
			tableColumnProdutoCodigo.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
			tableColumnProdutoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
			tableColumnProdutoQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
			tableColumnProdutoCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
			
			listProdutos = produtoDAO.listar();
			
			observableListProdutos = FXCollections.observableArrayList(listProdutos);
			tableViewInventario.setItems(observableListProdutos);
					
		}

}
