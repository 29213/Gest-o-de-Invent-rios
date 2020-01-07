package pt.iade.gestaoInventario.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.iade.gestaoInventario.models.Categoria;
import pt.iade.gestaoInventario.models.Produto;
import pt.iade.gestaoInventario.models.dao.ProdutoDAO;

public class RegistoProdutoController implements Initializable {

	@FXML
	private AnchorPane anchorPane;

	@FXML
	private TableView<Produto> tableViewProdutos;

	@FXML
	private TableColumn<Produto, String> tableColumnProdutoNome;

	@FXML
	private TableColumn<Produto, Integer> tableColumnProdutoQuantidade;

	@FXML
	private TextField TextFieldProdutoNome;

	@FXML
	private TextField TextFieldProdutoPreco;

	@FXML
	private TextField TextFieldProdutoQuantidade;

	@FXML
	private ComboBox<Categoria> comboBoxCategoria;

	@FXML
	private Label lableProdutoNome;

	@FXML
	private Label lableProdutoPreco;

	@FXML
	private Label lableProdutoQuantidade;

	@FXML
	private Label lableProdutoCategoria;

	@FXML
	private Label lableProdutoCodigo;

	private List<Produto> listProdutos;

	private ObservableList<Produto> observableListProdutos;

	private final ProdutoDAO produtoDAO = new ProdutoDAO();

	@FXML
	private Button buttonAlterar;

	@FXML
	private Button buttonAdicionar;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		
		selecionarItemTableViewProduto(null);
		
		carregarTableViewProdutos();

		tableViewProdutos.getSelectionModel().selectedItemProperty()
				.addListener((Observable, oldValue, newValue) -> selecionarItemTableViewProduto(newValue));

		tableViewProdutos.setEditable(true);

		tableColumnProdutoNome.setCellFactory(TextFieldTableCell.forTableColumn());
		
	}

	@FXML
	void AdicionarProduto(ActionEvent event) throws IOException {
		Produto produto = new Produto();
		boolean buttonConfirmarClick = showRegistoProdutoStage(produto);
		if (buttonConfirmarClick) {
			produtoDAO.inserir(produto);
			carregarTableViewProdutos();
		}
	}

	@FXML
	void AlterarProduto(ActionEvent event) throws IOException {
		tableViewProdutos.getItems();
		Produto produto = tableViewProdutos.getSelectionModel().getSelectedItem();/** Obtendo Produto selecionado */
		if (produto != null) {
			boolean buttonConfirmar = showRegistoProdutoStage(produto);
			if (buttonConfirmar) {
				produtoDAO.alterar(produto);
				carregarTableViewProdutos();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, escolha um colaborador na Tabela!");
			alert.show();
		}
	}


	private void carregarTableViewProdutos() {

		tableColumnProdutoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnProdutoQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

		listProdutos = produtoDAO.listar();

		observableListProdutos = FXCollections.observableArrayList(listProdutos);
		tableViewProdutos.setItems(observableListProdutos);

	}


	private void selecionarItemTableViewProduto(Produto produto) {
		if (produto != null) {
			lableProdutoCodigo.setText(String.valueOf(produto.getIdProduto()));
			lableProdutoNome.setText(produto.getNome());
			lableProdutoPreco.setText(String.format("%.2f€", produto.getPreco()));
			lableProdutoQuantidade.setText(String.valueOf(produto.getQuantidade()));
			lableProdutoCategoria.setText(String.valueOf(produto.getCategoria()));
		} else {
			lableProdutoCodigo.setText("");
			lableProdutoNome.setText("");
			lableProdutoPreco.setText("");
			lableProdutoQuantidade.setText("");
			lableProdutoCategoria.setText("");
		}
	}
	
	public boolean showRegistoProdutoStage(Produto produto) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(RegistoColaboradorStageController.class
				.getResource("/pt/iade/gestaoInventario/views/RegistoProdutoStage.fxml"));
		AnchorPane janela = (AnchorPane) loader.load();

		/** Criando um tela de registo (ProdutoStage) */
		Stage produtoStage = new Stage();
		produtoStage.setTitle("Registar Produtos");
		Scene scene = new Scene(janela);
		produtoStage.setScene(scene);

		/** Mudar o produto no Controller */
		RegistoProdutoStageController controller = loader.getController();
		controller.setProdutoStage(produtoStage);
		controller.setProduto(produto);

		/** Mostra o resgisto e espera até que o utilizador o feche */
		produtoStage.showAndWait();

		return controller.isButtonConfirmarClick();

	}

	@FXML
	void onEditNome(TableColumn.CellEditEvent<Produto, String> ProdutoStringCellEditEvent) {
		tableViewProdutos.setItems(observableListProdutos);
		Produto produto = tableViewProdutos.getSelectionModel().getSelectedItem();
		produto.setNome(ProdutoStringCellEditEvent.getNewValue());
		produtoDAO.alterar(produto);
		
	}

	@FXML
	void onEditQuantidade(TableColumn.CellEditEvent<Produto, Integer> ProdutoIngegerCellEditEvent) {
		tableViewProdutos.setItems(observableListProdutos);
		Produto produto = tableViewProdutos.getSelectionModel().getSelectedItem();
		produto.setQuantidade(ProdutoIngegerCellEditEvent.getNewValue());

	}
}