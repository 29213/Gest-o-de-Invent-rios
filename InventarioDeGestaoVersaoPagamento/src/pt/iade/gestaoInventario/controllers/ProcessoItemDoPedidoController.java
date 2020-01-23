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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pt.iade.gestaoInventario.models.Colaborador;
import pt.iade.gestaoInventario.models.ItemDoPedido;
import pt.iade.gestaoInventario.models.Produto;
import pt.iade.gestaoInventario.models.Pedido;
import pt.iade.gestaoInventario.models.dao.ColaboradorDAO;
import pt.iade.gestaoInventario.models.dao.ProdutoDAO;
/**
 * 
 * Controlador da interface principal do processo de pedido.
 * Permite visualizar os pedidos feitos numa ListView
 * Permite escolher um pedido e:
 *    <li> Apagar
 *    <li> Visualizar as informações do pedido ao lado
 *	  <li> Visualizar os itens dos pedidos abrindo uma nova janela, chama o controlador {@link ItensDePedidoController} 
 *Permite adicionar um pedido abrindo uma nova janela, chama o contralador {@link ProcessoItemDoPedidoController} 
 *
 */
public class ProcessoItemDoPedidoController implements Initializable {
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
	private TableView<ItemDoPedido> tableViewItensDeStock;

	@FXML
	private TableColumn<ItemDoPedido, Produto> TableColumnItemStockProduto;

	@FXML
	private TableColumn<ItemDoPedido, Integer> TableColumnItemStockQuantidade;

	@FXML
	private TableColumn<ItemDoPedido, Double> TableColumnItemStockValor;

	@FXML
	private TextField textFildTotalPedido;

	@FXML
	private boolean buttonConfirmarClickS;

	@FXML
	private Button CancelarPedido;

	private List<Colaborador> listColaboradores;

	private List<Produto> listProdutos;

	private ObservableList<Colaborador> observableListColaboradores;

	private ObservableList<Produto> observableListProdutos;

	private ObservableList<ItemDoPedido> observableListItensDeStock;

	private final ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
	private final ProdutoDAO produtoDAO = new ProdutoDAO();
	private Stage PedidoStage;
	private boolean buttonConfirmarClick = false;
	private Pedido pedido;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		carregarComboBoxColaboradores();
		carregarComboBoxProdutos();

		TableColumnItemStockProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
		TableColumnItemStockQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		TableColumnItemStockValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

	}
	
	private void carregarComboBoxProdutos() {

		listProdutos = produtoDAO.listar();

		observableListProdutos = FXCollections.observableArrayList(listProdutos);
		comboBoxPedidoProduto.setItems(observableListProdutos);
	}

	public void carregarComboBoxColaboradores() {

		listColaboradores = colaboradorDAO.listar();

		observableListColaboradores = FXCollections.observableArrayList(listColaboradores);
		comboBoxPedidoColaborador.setItems(observableListColaboradores);
	}

	public void carregarComboxProdutos() {
		listProdutos = produtoDAO.listar();
		observableListProdutos = FXCollections.observableArrayList(listProdutos);
		comboBoxPedidoProduto.setItems(observableListProdutos);
	}

	public Pedido getStock() {
		return pedido;
	}

	public void setStock(Pedido pedido) {
		this.pedido = pedido;
	}

	public Stage getPedidoStage() {
		return PedidoStage;
	}

	public void setPedidoStage(Stage pedidoStage) {
		PedidoStage = pedidoStage;
	}

	public boolean isButtonConfirmarClick() {
		return buttonConfirmarClick;
	}

	public void setButtonConfirmarClick(boolean buttonConfirmarClick) {
		this.buttonConfirmarClick = buttonConfirmarClick;
	}

	@FXML
	void buttonAdiconarQuantidade(ActionEvent event) throws IOException {
		Produto produto;
		ItemDoPedido itemDoPedido = new ItemDoPedido();
		itemDoPedido.setStock(pedido);

		if (comboBoxPedidoProduto.getSelectionModel().getSelectedItem() != null) {
			produto = (Produto) comboBoxPedidoProduto.getSelectionModel().getSelectedItem();

			if (produto.getQuantidade() >= Integer.parseInt(textFildPedidoItemStock.getText())) {
				itemDoPedido.setProduto((Produto) comboBoxPedidoProduto.getSelectionModel().getSelectedItem());
				itemDoPedido.setQuantidade(Integer.parseInt(textFildPedidoItemStock.getText()));
				itemDoPedido.setValor(itemDoPedido.getProduto().getPreco() * itemDoPedido.getQuantidade());

				pedido.getItensDeStock().add(itemDoPedido);
				pedido.setValor(pedido.getValor() + itemDoPedido.getValor());

				observableListItensDeStock = FXCollections.observableArrayList(pedido.getItensDeStock());
				tableViewItensDeStock.setItems(observableListItensDeStock);

				textFildTotalPedido.setText(String.format("%.2f€", pedido.getValor()));
			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Problemas na escolha do Produto!");
				alert.setContentText("Quantidade indisponivel no pedido!");
				alert.show();
			}
		}
	}

	@FXML
	void buttonCancelarPedido(ActionEvent event) {
		getPedidoStage().close();
	}

	@FXML
	void buttonConfirmarPedido(ActionEvent event) throws IOException {
		if (validarEntradaDeDados()) {
			pedido.setColaborador((Colaborador) comboBoxPedidoColaborador.getSelectionModel().getSelectedItem());
			pedido.setData(dataPicker.getValue());

			buttonConfirmarClick = true;
			PedidoStage.close();
		}

	}

	/** Validar a entrada de dados para o registo */
	private boolean validarEntradaDeDados() {
		String errorMessage = "";

		if (comboBoxPedidoColaborador.getSelectionModel().getSelectedItem() == null) {
			errorMessage += "Colaborador invalido!\n";
		}
		if (dataPicker.getValue() == null) {
			errorMessage += "Data invalida!\n";
		}
		if (observableListItensDeStock == null) {
			errorMessage += "Itens de Pedido invalidos!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			/** Mostrar mensagem de erro. */

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erro no Registo");
			alert.setHeaderText("Campos invalidos, corrija...");
			alert.setContentText(errorMessage);
			alert.show();
			return false;
		}
	}
}
