package pt.iade.gestaoInventario.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.iade.gestaoInventario.models.Colaborador;
import pt.iade.gestaoInventario.models.ItemDeStock;
import pt.iade.gestaoInventario.models.Produto;
import pt.iade.gestaoInventario.models.Stock;
import pt.iade.gestaoInventario.models.dao.ItemDeStockDAO;
import pt.iade.gestaoInventario.models.dao.ProdutoDAO;
import pt.iade.gestaoInventario.models.dao.StockDAO;

public class ProcessoStockController implements Initializable {

	@FXML
	private TableView<Stock> tableViewPedidos;
	@FXML
	private TableColumn<Stock, Integer> tableColumnCodigo;

	@FXML
	private TableColumn<Stock, Date> tableColumnData;

	@FXML
	private TableColumn<Colaborador, String> tableColumnColaborador;

	@FXML
	private Label labelCodigo;

	@FXML
	private Label labelDataStock;

	@FXML
	private Label labelColaborador;

	@FXML
	private Label labelValor;

	@FXML
	private Button buttonPedido;

	@FXML
	private Button buttonRemover;

	private List<Stock> listStocks;

	private ObservableList<Stock> observableListStocks;

	private final ProdutoDAO produtoDAO = new ProdutoDAO();
	private final ItemDeStockDAO itemDeStockDAO = new ItemDeStockDAO();
	private final StockDAO stockDAO = new StockDAO();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarTableViewPedidos();

		/**
		 * Lista activa diante de quaisquer alterações na seleção de itens da Tabela.
		 */
		tableViewPedidos.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewPedidos(newValue));

	}

	public void carregarTableViewPedidos() {

		tableColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("idStock"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tableColumnColaborador.setCellValueFactory(new PropertyValueFactory<>("Colaborador"));

		listStocks = stockDAO.listar();

		observableListStocks = FXCollections.observableArrayList(listStocks);
		tableViewPedidos.setItems(observableListStocks);
	}

	public void selecionarItemTableViewPedidos(Stock stock) {
		if (stock != null) {
			labelCodigo.setText(String.valueOf(stock.getIdStock()));
			labelDataStock.setText(String.valueOf(stock.getData().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))));
			labelValor.setText(String.format("%.2f", stock.getValor()));
			labelColaborador.setText(stock.getColaborador().toString());
		} else {
			labelCodigo.setText("");
			labelDataStock.setText("");
			labelValor.setText("");
			labelColaborador.setText("");
		}
	}

	@FXML
	void buttonPedidoStock(ActionEvent event) throws IOException {
		Stock stock = new Stock();
		List<ItemDeStock> listItensDeStock = new ArrayList<>();
		stock.setItensDeStock(listItensDeStock);
		boolean buttonConfirmarPedido = showProcessoStockPedido(stock);
		if (buttonConfirmarPedido) {
			stockDAO.inserir(stock);
			for (ItemDeStock listItemDeStock : stock.getItensDeStock()) {
				Produto produto = listItemDeStock.getProduto();
				listItemDeStock.setStock(stockDAO.buscarUltimoStock());
				itemDeStockDAO.inserir(listItemDeStock);
				produto.setQuantidade(produto.getQuantidade() - listItemDeStock.getQuantidade());
				produtoDAO.alterar(produto);
			}
			carregarTableViewPedidos();

		}
	}

	@FXML
	void buttonRevomerPedido(ActionEvent event) throws IOException, SQLException {
		Stock stock = tableViewPedidos.getSelectionModel().getSelectedItem();
		if (stock != null) {
			for (ItemDeStock listItemStock : stock.getItensDeStock()) {
				Produto produto = listItemStock.getProduto();
				produto.setQuantidade(produto.getQuantidade() + listItemStock.getQuantidade());
				produtoDAO.alterar(produto);
				itemDeStockDAO.remover(listItemStock);
			}
			stockDAO.remover(stock);
			carregarTableViewPedidos();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, escolha um Pedido na Tabela!");
			alert.show();
		}
	}

	private boolean showProcessoStockPedido(Stock stock) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ProcessoStockPedidoController.class.getResource("/pt/iade/gestaoInventario/views/ProcessoStockPedido.fxml"));
		AnchorPane janela = (AnchorPane) loader.load();
		
		/** Mostrar uma tela de resgisto */
		Stage pedidoStage = new Stage();
		pedidoStage.setTitle("Registro de Pedido");
		Scene scene = new Scene(janela);
		pedidoStage.setScene(scene);

		/** Passar o Stock no Controlo. */
		ProcessoStockPedidoController controller = loader.getController();
		controller.setPedidoStage(pedidoStage);
		controller.setStock(stock);

		/** Mostra o Pedido e esperar até que o utilizador o feche */
		pedidoStage.showAndWait();

		return controller.isButtonConfirmarClick();
	}
}
