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
// TODO: Auto-generated Javadoc
/**
 * 
 * <p> Controlador a interface para o registo dos produtos.
 * <p> Permite adicionar os dados do produto.
 * <p> Permite alterar os dados do produto escolhido na tabela.
 *
 * @author Renato Pitta Simões
 */
public class RegistoProdutoStageController implements Initializable {
	
	/** O campo de texto produto nome. */
	@FXML
	private TextField textFieldProdutoNome;

	/** O campo de texto produto preco. */
	@FXML
	private TextField textFieldProdutoPreco;

	/** O campo de texto produto quantidade. */
	@FXML
	private TextField textFieldProdutoQuantidade;

	/** A combobox produto categoria. */
	@FXML
	private ComboBox<Categoria> comboBoxProdutoCategoria;

	/** A produto stage. */
	private Stage produtoStage;

	/** Botão confirmar click. */
	private boolean buttonConfirmarClick = false;

	/** atributo para manipulação produto. */
	private Produto produto;

	/** O botão Adicionar. */
	@FXML
	private Button Adicionar;

	/** botão Cancelar. */
	@FXML
	private Button Cancelar;

	/** Lista de categorias. */
	List<Categoria> listCategorias;
	
	/** Lista observável de categorias. */
	ObservableList<Categoria> observableListCategorias;

	/** Atributo para manipulação da base de dados. */
	private final CategoriaDAO categoriaDAO = new CategoriaDAO();

	/**
	 * Initializa.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarComboBoxCategorias();

	}

	/**
	 * Obtém o produto stage.
	 *
	 * @return o produto stage
	 */
	public Stage getProdutoStage() {
		return produtoStage;
	}

	/**
	 * Define o produto stage.
	 *
	 * @param produtoStage o novo produto stage
	 */
	public void setProdutoStage(Stage produtoStage) {
		this.produtoStage = produtoStage;
	}

	/**
	 * Verifica se o botão está confirmando click.
	 *
	 * @return verdadeiro, se for o botão confirma click
	 */
	public boolean isButtonConfirmarClick() {
		return buttonConfirmarClick;
	}

	/**
	 * Define o botão confirmar click.
	 *
	 * @param buttonConfirmarClick o novo botão confirmar
	 */
	public void setButtonConfirmarClick(boolean buttonConfirmarClick) {
		this.buttonConfirmarClick = buttonConfirmarClick;
	}

	/**
	 * Obtém o produto.
	 *
	 * @return o produto
	 */
	public Produto getProduto() {
		return produto;
	}

	/**
	 * Carregar combobox categorias.
	 */
	private void carregarComboBoxCategorias() {

		listCategorias = categoriaDAO.listar();
		observableListCategorias = FXCollections.observableArrayList(listCategorias);
		comboBoxProdutoCategoria.setItems(observableListCategorias);
	}

	/**
	 * Define o produto.
	 *
	 * @param produto o novo produto
	 */
	public void setProduto(Produto produto) {
		this.produto = produto;
		this.textFieldProdutoNome.setText(produto.getNome());
		this.textFieldProdutoPreco.setPromptText(String.valueOf(produto.getPreco()));
		this.textFieldProdutoQuantidade.setPromptText(String.valueOf(produto.getQuantidade()));
		this.comboBoxProdutoCategoria.getSelectionModel().getSelectedItem();
	}

	/**
	 * Botão cancelar.
	 *
	 * @param event the event
	 */
	@FXML
	void buttonCancelar(ActionEvent event) {
		produtoStage.close();
	}

	/**
	 * Botão confirmar.
	 *
	 * @param event the event
	 */
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

	/**
	 *  Validar entrada de dados para o registo.
	 *
	 * @return verdadeiro, se for bem sucedido
	 */
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
