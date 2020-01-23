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

public class RegistoCategoriaController implements Initializable {
	@FXML
	private TextField textFieldDescrição;
	@FXML
	private TableView<Categoria> tableViewCategorias;

	@FXML
	private TableColumn<Categoria, Integer> tablecolumnId;

	@FXML
	private TableColumn<Categoria, String> tableColumnCategoria;

	private final CategoriaDAO categoriaDAO = new CategoriaDAO();
	
	@FXML
	private ListView<Produto> listViewProduto;

	private List<Categoria> listCategorias;

	private ObservableList<Categoria> observableListCategorias;
	
	

	@FXML
	void adicionarCategoria(ActionEvent event) {
		Categoria categoria = new Categoria();
		if (validarEntradaDeDados()) {
			categoria.setDescricao(textFieldDescrição.getText());
			CategoriaDAO.inserir(categoria);
		}
		
		carregarTableViewCategorias();
	}

	void carregarTableViewCategorias() {

		tablecolumnId.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
		tableColumnCategoria.setCellValueFactory(new PropertyValueFactory<>("descricao"));

		listCategorias = categoriaDAO.listar();

		observableListCategorias = FXCollections.observableArrayList(listCategorias);
		tableViewCategorias.setItems(observableListCategorias);

	}
	
	/** Validar entrada de dados para o registo. */
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
	
	public void selecionarCategoriaTableViewProdutos(Categoria categoria) {
		if (categoria != null) {
			listViewProduto.setItems(ProdutoDAO.listarPorCategoria(categoria));
		}
	}
	
	@FXML
	void onEditCategoria(TableColumn.CellEditEvent<Categoria, String> CategoriaStringCellEditEvent) throws IOException {
		tableViewCategorias.setItems(observableListCategorias);
		Categoria categoria = tableViewCategorias.getSelectionModel().getSelectedItem();
		categoria.setDescricao(CategoriaStringCellEditEvent.getNewValue());
		categoriaDAO.alterar(categoria);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarTableViewCategorias();
		
		tableViewCategorias.setEditable(true);
		
		tableColumnCategoria.setCellFactory(TextFieldTableCell.forTableColumn());
		
		tableViewCategorias.getSelectionModel().selectedItemProperty()
		.addListener((observable, oldValue, newValue) -> selecionarCategoriaTableViewProdutos(newValue));
	}
}
