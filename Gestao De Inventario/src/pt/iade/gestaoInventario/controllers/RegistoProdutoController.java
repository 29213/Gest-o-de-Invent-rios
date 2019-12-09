package pt.iade.gestaoInventario.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import pt.iade.gestaoInventario.models.Categoria;
import pt.iade.gestaoInventario.models.Produto;
import pt.iade.gestaoInventario.models.dao.ProdutoDAO;

public class RegistoProdutoController implements Initializable{

	@FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<Produto> tableViewProdutos;

    @FXML
    private TableColumn<Produto, String> tableColumnProdutoNome;

    @FXML
    private TableColumn<Produto, Double> tableColumnProdutoPreco;

    @FXML
    private TableColumn<Produto, Integer> tableColumnProdutoQuantidade;

    @FXML
    private TableColumn<Categoria, String> tableColumnProdutoCategoria;

    @FXML
    private TextField TextFieldProdutoNome;

    @FXML
    private TextField TextFieldProdutoPreco;

    @FXML
    private TextField TextFieldProdutoQuantidade;

    @FXML
    private ComboBox<Categoria> comboBoxCategoria;

    private List<Produto> listProdutos;
    
    private ObservableList<Produto> observableListProdutos;
    
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    
    @FXML
    private Button buttonAlterar;

    @FXML
    private Button buttonAdicionar;

    @FXML
    void AdicionarProduto(ActionEvent event) {
	
    }

    @FXML
    void AlterarProduto(ActionEvent event) {

    }
	public void carregarTableViewProdutos() {
		tableColumnProdutoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnProdutoPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
		tableColumnProdutoQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		tableColumnProdutoCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
		
		listProdutos = produtoDAO.listar();
		
		observableListProdutos = FXCollections.observableArrayList(listProdutos);
		tableViewProdutos.setItems(observableListProdutos);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarTableViewProdutos();
		
	}
}
