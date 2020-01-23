package pt.iade.gestaoInventario.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import pt.iade.gestaoInventario.models.Categoria;
import pt.iade.gestaoInventario.models.Produto;
import pt.iade.gestaoInventario.models.dao.CategoriaDAO;
import pt.iade.gestaoInventario.models.dao.ProdutoDAO;

// TODO: Auto-generated Javadoc
/**
 * 
 * <p>
 * Controlador da interface principal, registo de Categoria.
 * <p>
 * Permite visualizar as categorias registadas na tabela.
 * <p>
 * Permite adicionar categorias.
 * <p>
 * Permite alterar a descrição da categoria selecionada apartir da tebleView.
 * 
 * @author Duarte Lobato
 */
public class RegistoCategoriaController implements Initializable {

	/** A descrição do campo de texto. */
	@FXML
	private TextField textFieldDescrição;

	/** A exibição de tabela Categorias. */
	@FXML
	private TableView<Categoria> tableViewCategorias;

	/** A Coluna id da tabela. */
	@FXML
	private TableColumn<Categoria, Integer> tablecolumnId;

	/** A coluna categoria da tabela. */
	@FXML
	private TableColumn<Categoria, String> tableColumnCategoria;

	/** Atributo para manipulação da base de dados. */
	private final CategoriaDAO categoriaDAO = new CategoriaDAO();

	/** Lista de categorias. */
	private List<Categoria> listCategorias;
	
	/** Lista dos Produtos. */
	@FXML
	private ListView<Produto> listViewProduto;

	/** Lista observável categorias. */
	private ObservableList<Categoria> observableListCategorias;

	/**
	 * Initializa.
	 *
	 * @param location  the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarTableViewCategorias();
		tableViewCategorias.setEditable(true);
		tableColumnCategoria.setCellFactory(TextFieldTableCell.forTableColumn());
		tableViewCategorias.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> selecionarCategoriaTableViewProdutos(newValue));
	}

	/**
	 * Adicionar categoria.
	 *
	 * @param event the event
	 */
	@FXML
	void adicionarCategoria(ActionEvent event) {
		Categoria categoria = new Categoria();
		if (validarEntradaDeDados()) {
			categoria.setDescricao(textFieldDescrição.getText());
			categoriaDAO.inserir(categoria);
		}

		carregarTableViewCategorias();
	}

	/**
	 * Carregar a tabela categorias.
	 */
	void carregarTableViewCategorias() {

		tablecolumnId.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
		tableColumnCategoria.setCellValueFactory(new PropertyValueFactory<>("descricao"));

		listCategorias = categoriaDAO.listar();

		observableListCategorias = FXCollections.observableArrayList(listCategorias);
		tableViewCategorias.setItems(observableListCategorias);

	}

	/**
	 * Validar entrada de dados para o registo.
	 *
	 * @return verdadeiro, se for bem sucedido.
	 */
	private boolean validarEntradaDeDados() {
		String errorMessage = "";

		if (textFieldDescrição.getText() == null || textFieldDescrição.getText().length() == 0) {
			errorMessage += "Escreve a descrição!\n";
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {

			/** Mostrar a mensagem de erro. */
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erro no registo");
			alert.setHeaderText("Campo invalido");
			alert.setContentText(errorMessage);
			alert.show();
			return false;
		}
	}
	
	/**
	 * Selecionar categoria table view produtos.
	 *
	 * @param categoria the categoria
	 */
	public void selecionarCategoriaTableViewProdutos(Categoria categoria) {
		if (categoria != null) {
			listViewProduto.setItems(ProdutoDAO.listarPorCategoria(categoria));
		}
	}
	
	/**
	 * Editar a categoria escolhida a partir da tabela e guardar a alteração na base de dados.
	 *
	 * @param CategoriaStringCellEditEvent o evento de edição da célula da string de
	 *                                     categoria
	 * @throws IOException Sinaliza que ocorreu uma exceção de E / S.
	 */
	@FXML
	void onEditCategoria(TableColumn.CellEditEvent<Categoria, String> CategoriaStringCellEditEvent) throws IOException {
		tableViewCategorias.setItems(observableListCategorias);
		Categoria categoria = tableViewCategorias.getSelectionModel().getSelectedItem();
		categoria.setDescricao(CategoriaStringCellEditEvent.getNewValue());
		categoriaDAO.alterar(categoria);
	}

}
