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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.iade.gestaoInventario.models.Colaborador;
import pt.iade.gestaoInventario.models.ItemStock;
import pt.iade.gestaoInventario.models.Produto;
import pt.iade.gestaoInventario.models.Stock;
import pt.iade.gestaoInventario.models.dao.ColaboradorDAO;
import pt.iade.gestaoInventario.models.dao.ProdutoDAO;

public class ProcessoStockPedidoController implements Initializable {
	@FXML
	private ComboBox<Colaborador> comboBoxPedidoColaborador;

	@FXML
	private DatePicker dataPicker;

	@FXML
	private ComboBox<Produto> comboBoxPedidoProduto;

	@FXML
	private TextField textFildPedidoItemStock;

	@FXML
	private Button buttanAdicionar;

	@FXML
	private TableView<ItemStock> tableViewItensDeStock;

	@FXML
	private TableColumn<ItemStock, Produto> TableColumnItemStockProduto;

	@FXML
	private TableColumn<ItemStock, Integer> TableColumnItemStockQuantidade;

	@FXML
	private TableColumn<ItemStock, Double> TableColumnItemStockValor;

	@FXML
	private TextField textFildTotalPedido;

	@FXML
	private Button buttonConfirmar;

	@FXML
	private Button CancelarPedido;

	private List<Colaborador> listaColaboradores;

	private List<Produto> listaProdutos;

	private ObservableList<Colaborador> observableListColaboradores;

	private ObservableList<Produto> observableListProdutos;

	private ObservableList<ItemStock> observableListItensDeStock;

	private final ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
	private final ProdutoDAO produtoDAO = new ProdutoDAO();
	private Stage PedidoStage;
	private boolean buttonConfirmarClicked = false;
	private Stock stock;

	@FXML
	void AdiconarQuantidadePedido(ActionEvent event) {

	}

	private void carregarComboBoxProdutos() {
		listaProdutos = produtoDAO.listar();

		observableListProdutos = FXCollections.observableArrayList(listaProdutos);
		comboBoxPedidoProduto.setItems(observableListProdutos);
	}

	public void carregarComboBoxColaboradores() {

		listColoradores = colaoradorDAO.listar();

		observableListColaboradores = FXCollections.observableArrayList(listaColaboradores);
		comboBoxPedidoColaborador.setItems(observableListColaboradores);
	}

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public Stage getPedidoStage() {
		return PedidoStage;
	}

	public void setPedidoStage(Stage pedidoStage) {
		PedidoStage = pedidoStage;
	}

	public boolean isButtonConfirmarClicked() {
		return buttonConfirmarClicked;
	}

	public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
		this.buttonConfirmarClicked = buttonConfirmarClicked;
	}

	@FXML
	void CancelarPedido(ActionEvent event) {

	}

	@FXML
	void ConfirmarPedido(ActionEvent event) {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		carregarComboBoxColaboradores();
		carregarComboBoxProdutos();

		TableColumnItemStockProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
		TableColumnItemStockQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		TableColumnItemStockValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

	}
}
