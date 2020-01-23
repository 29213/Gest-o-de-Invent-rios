package pt.iade.gestaoInventario.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pt.iade.gestaoInventario.models.Categoria;
import pt.iade.gestaoInventario.models.Produto;
import pt.iade.gestaoInventario.models.dao.CategoriaDAO;

public class RegistoProdutoStageController implements Initializable {
	@FXML
	private TextField textFieldProdutoNome;

	@FXML
	private TextField textFieldProdutoPreco;

	@FXML
	private TextField textFieldProdutoQuantidade;

	@FXML
	private ComboBox<Categoria> comboBoxProdutoCategoria;

	private Stage produtoStage;

	private boolean buttonConfirmarClick = false;

	private Produto produto;

	@FXML
	private Button Adicionar;

	@FXML
	private Button Cancelar;

	List<Categoria> listCategorias;
	ObservableList<Categoria> observableListCagegorias;

	private final CategoriaDAO categoriaDAO = new CategoriaDAO();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarComboBoxCategorias();

	}

	public Stage getProdutoStage() {
		return produtoStage;
	}

	public void setProdutoStage(Stage produtoStage) {
		this.produtoStage = produtoStage;
	}

	public boolean isButtonConfirmarClick() {
		return buttonConfirmarClick;
	}

	public void setButtonConfirmarClick(boolean buttonConfirmarClick) {
		this.buttonConfirmarClick = buttonConfirmarClick;
	}

	public Produto getProduto() {
		return produto;
	}

	private void carregarComboBoxCategorias() {

		listCategorias = categoriaDAO.listar();
		observableListCagegorias = FXCollections.observableArrayList(listCategorias);
		comboBoxProdutoCategoria.setItems(observableListCagegorias);
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
		this.textFieldProdutoNome.setText(produto.getNome());
		this.textFieldProdutoPreco.setPromptText(String.valueOf(produto.getPreco()));
		this.textFieldProdutoQuantidade.setPromptText(String.valueOf(produto.getQuantidade()));
		this.comboBoxProdutoCategoria.getSelectionModel().getSelectedItem();
	}

	@FXML
	void buttonCancelar(ActionEvent event) {
		produtoStage.close();
	}

	@FXML
	void buttonConfirmar(ActionEvent event) {

		if (validarEntradaDeDados()) {
			produto.setNome(textFieldProdutoNome.getText());
			produto.setPreco(Double.valueOf(textFieldProdutoPreco.getText()));
			produto.setQuantidade(Integer.valueOf(textFieldProdutoQuantidade.getText()));
			produto.setCategoria(comboBoxProdutoCategoria.getSelectionModel().getSelectedItem());

			buttonConfirmarClick = true;

			produtoStage.close();
		}
	}

	/** Validar entrada de dados para o registo. */
	private boolean validarEntradaDeDados() {
		String errorMessage = "";

		if (textFieldProdutoNome.getText() == null || textFieldProdutoNome.getText().length() == 0) {
			errorMessage += "Nome invalido!\n";
		}
		if (textFieldProdutoPreco.getText() == null || textFieldProdutoPreco.getText().length() == 0) {
			errorMessage += "Preço invalido!\n";
		}
		if (textFieldProdutoQuantidade.getText() == null || textFieldProdutoQuantidade.getText().length() == 0) {
			errorMessage += "Quantidade invalido!\n";
		}
		if (comboBoxProdutoCategoria.getSelectionModel().getSelectedItem() == null) {
			errorMessage += "Categoria invalido!\n";
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {

			/** Mostrar a mensagem de erro. */
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erro no registo");
			alert.setHeaderText("Campos invalidos, corrija...");
			alert.setContentText(errorMessage);
			alert.show();
			return false;
		}
	}
}
