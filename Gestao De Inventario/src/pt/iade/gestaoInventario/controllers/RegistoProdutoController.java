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
import javafx.util.converter.IntegerStringConverter;
import pt.iade.gestaoInventario.models.Categoria;
import pt.iade.gestaoInventario.models.Produto;
import pt.iade.gestaoInventario.models.dao.ProdutoDAO;
// TODO: Auto-generated Javadoc
/**
* <p> Controlador da interface principal, registo de Produtos.
* <p> Permite visualizar os produtos registados, numa TableView.
* <p> Permite escolher um produto e:
*     <li> Visualizar as informações do produto ao lado;
*	  <li> Alterar os dados do produto abrindo uma nova janela, chama o controlador {@link RegistoProdutoStageController};
*	  <li> Editar o nome e a quantidade do produto escolhido na tableView.
* <p> Permite adicionar um coladorador abrindo uma nova janela, chama o contralador {@link RegistoProdutoStageController}.
*
*@author Renato Pitta Simões
*
**/
	public class RegistoProdutoController implements Initializable {

	/** O painel de âncora. */
	@FXML
	private AnchorPane anchorPane;

	/** A tabela de produtos. */
	@FXML
	private TableView<Produto> tableViewProdutos;

	/** A coluna da table produto nome. */
	@FXML
	private TableColumn<Produto, String> tableColumnProdutoNome;

	/** A coluna da tabela produto quantidade. */
	@FXML
	private TableColumn<Produto, Integer> tableColumnProdutoQuantidade;

	/** O campo de texto do produto nome. */
	@FXML
	private TextField TextFieldProdutoNome;

	/** O campo de texto do produto preco. */
	@FXML
	private TextField TextFieldProdutoPreco;

	/** O campo de texto do produto quantidade. */
	@FXML
	private TextField TextFieldProdutoQuantidade;

	/** O combobox da categoria. */
	@FXML
	private ComboBox<Categoria> comboBoxCategoria;

	/** A lable produto nome. */
	@FXML
	private Label lableProdutoNome;

	/** A lable produto preco. */
	@FXML
	private Label lableProdutoPreco;

	/** A lable produto quantidade. */
	@FXML
	private Label lableProdutoQuantidade;

	/** A lable produto categoria. */
	@FXML
	private Label lableProdutoCategoria;

	/** A lable produto codigo. */
	@FXML
	private Label lableProdutoCodigo;

	/** Lista de produtos. */
	private List<Produto> listProdutos;

	/** lista observável dos produtos. */
	private ObservableList<Produto> observableListProdutos;

	/** Atributo para manipulação da base de dados. */
	private final ProdutoDAO produtoDAO = new ProdutoDAO();

	/** Botão alterar. */
	@FXML
	private Button buttonAlterar;

	/** Botão adicionar. */
	@FXML
	private Button buttonAdicionar;

	/**
	 * Initializa.
	 *
	 * @param url the url
	 * @param rb the rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		selecionarItemTableViewProduto(null);

		carregarTableViewProdutos();

		tableViewProdutos.getSelectionModel().selectedItemProperty()
				.addListener((Observable, oldValue, newValue) -> selecionarItemTableViewProduto(newValue));

		tableViewProdutos.setEditable(true);

		tableColumnProdutoNome.setCellFactory(TextFieldTableCell.forTableColumn());
		tableColumnProdutoQuantidade.setCellFactory(TextFieldTableCell.<Produto, Integer>forTableColumn(new IntegerStringConverter()));

	}

	/**
	 * Adicionar produto na base de dados e carregar na tabela.
	 *
	 * @param event the event
	 * @throws IOException sinaliza que ocorreu uma exceção de E / S.
	 */
	@FXML
	void AdicionarProduto(ActionEvent event) throws IOException {
		Produto produto = new Produto();
		boolean buttonConfirmarClick = showRegistoProdutoStage(produto);
		if (buttonConfirmarClick) {
				produtoDAO.inserir(produto);
				carregarTableViewProdutos();
		}
	}

	/**
	 * Alterar produto, guardar na base de dados e carregar na tabela.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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

	/**
	 * Carregar table view produtos.
	 */
	private void carregarTableViewProdutos() {

		tableColumnProdutoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tableColumnProdutoQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

		listProdutos = produtoDAO.listar();

		observableListProdutos = FXCollections.observableArrayList(listProdutos);
		tableViewProdutos.setItems(observableListProdutos);

	}

	/**
	 * Selecionar item tabela produto e apresentar os detablhes.
	 *
	 * @param produto the produto
	 */
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

	/**
	 * Mostrar registo produto stage.
	 *
	 * @param produto the produto
	 * @return verdadeiro, se for bem sucedido
	 * @throws IOException Sinaliza que ocorreu uma exceção de E / S.
	 */
	public boolean showRegistoProdutoStage(Produto produto) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(RegistoColaboradorStageController.class
				.getResource("/pt/iade/gestaoInventario/views/RegistoProdutoStage.fxml"));
		AnchorPane janela = (AnchorPane) loader.load();

		/** Criando um tela de registo (ProdutoStage) */
		Stage produtoStage = new Stage();
		produtoStage.setTitle("Produto");
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

	/**
	 * Editar o nome do produto escolhodo na tabela e guardar na base de dados a alteração.
	 *
	 * @param ProdutoStringCellEditEvent the produto string cell edit event
	 * @throws IOException Sinaliza que ocorreu uma exceção de E / S.
	 */
	@FXML
	void onEditNome(TableColumn.CellEditEvent<Produto, String> ProdutoStringCellEditEvent) throws IOException {
		tableViewProdutos.setItems(observableListProdutos);
		Produto produto = tableViewProdutos.getSelectionModel().getSelectedItem();
		produto.setNome(ProdutoStringCellEditEvent.getNewValue());
		produtoDAO.alterar(produto);

	}

	/**
	 * Editar a quantidade do produto escolhido na tabela e guardar na base de dados a alteração.
	 *
	 * @param ProdutoIntegerCellEditEvent the produto integer cell edit event
	 * @throws IOException Sinaliza que ocorreu uma exceção de E / S.
	 */
	@FXML
	void onEditQuantidade(TableColumn.CellEditEvent<Produto, Integer> ProdutoIntegerCellEditEvent) throws IOException {
		tableViewProdutos.setItems(observableListProdutos);
		Produto produto = tableViewProdutos.getSelectionModel().getSelectedItem();
		produto.setQuantidade(ProdutoIntegerCellEditEvent.getNewValue());
		produtoDAO.alterar(produto);

	}
}