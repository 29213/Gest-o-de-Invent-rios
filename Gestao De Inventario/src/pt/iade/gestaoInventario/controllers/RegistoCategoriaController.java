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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import pt.iade.gestaoInventario.models.Categoria;
import pt.iade.gestaoInventario.models.dao.CategoriaDAO;

/**
 * 
 * Controlador da interface principal, registo de Categoria.
 * Permite visualizar as categorias registadas numa TableView.
 * Permite adicionar categorias
 * Permite alterar a descrição da categoria selecionada apartir da tebleView.
 * 
 */
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

	private List<Categoria> listCategorias;

	private ObservableList<Categoria> observableListCategorias;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarTableViewCategorias();
		tableViewCategorias.setEditable(true);
		tableColumnCategoria.setCellFactory(TextFieldTableCell.forTableColumn());
	}

	@FXML
	void adicionarCategoria(ActionEvent event) {
		Categoria categoria = new Categoria();
		if (validarEntradaDeDados()) {
			categoria.setDescricao(textFieldDescrição.getText());
			categoriaDAO.inserir(categoria);
		}

		carregarTableViewCategorias();
	}

	/** Configuração da tableView */
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

	@FXML
    void onEditCategoria(TableColumn.CellEditEvent<Categoria, String> CategoriaStringCellEditEvent) throws IOException  {
		tableViewCategorias.setItems(observableListCategorias);
		Categoria categoria = tableViewCategorias.getSelectionModel().getSelectedItem();
		categoria.setDescricao(CategoriaStringCellEditEvent.getNewValue());
		categoriaDAO.alterar(categoria);
    }
	

}
