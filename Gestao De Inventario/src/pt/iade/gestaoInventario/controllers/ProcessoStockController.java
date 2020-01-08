package pt.iade.gestaoInventario.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import pt.iade.gestaoInventario.models.Colaborador;
import pt.iade.gestaoInventario.models.ItemDeStock;
import pt.iade.gestaoInventario.models.Produto;
import pt.iade.gestaoInventario.models.Stock;
import pt.iade.gestaoInventario.models.dao.ItemDeStockDAO;
import pt.iade.gestaoInventario.models.dao.ProdutoDAO;
import pt.iade.gestaoInventario.models.dao.StockDAO;

/**
 * Controlador da interface principal do processo de pedido. Permite visualizar
 * os pedidos feitos numa ListView Permite escolher um pedido e:
 * <li>Apagar
 * <li>Visualizar as informações do pedido ao lado
 * <li>Visualizar os itens dos pedidos abrindo uma nova janela, chama o
 * controlador {@link ItensDePedidoController} Permite adicionar um pedido
 * abrindo uma nova janela, chama o contralador
 * {@link ProcessoStockPedidoController}
 * 
 */
public class ProcessoStockController implements Initializable {

	@FXML
	private TableView<Stock> tableViewPedidos;
	@FXML
	private TableColumn<Stock, Integer> tableColumnStockCodigo;

	@FXML
	private TableColumn<Stock, LocalDate> tableColumnStockData;

	@FXML
	private TableColumn<Colaborador, String> tableColumnStockColaborador;

	@FXML
	private Label labelCodigoStock;

	@FXML
	private Label labelDataStock;

	@FXML
	private Label labelColaboradorStock;

	@FXML
	private Label labelValorStock;

	@FXML
	private Button buttonPedido;

	@FXML
	private Button buttonRemover;

	@FXML
	private ListView<ItemDeStock> listViewItensDePedido;
	
	private List<Stock> listStocks;
	private ObservableList<Stock> observableListStocks;

	/** Atributos para manipulação da base de dados */
	private final ProdutoDAO produtoDAO = new ProdutoDAO();
	private final ItemDeStockDAO itemDeStockDAO = new ItemDeStockDAO();
	private final StockDAO stockDAO = new StockDAO();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		carregarTableViewPedidos();
		/**
		 * A Lista ativa diante de quaisquer alterações na seleção de itens da Tabela.
		 */
		tableViewPedidos.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewPedidos(newValue));
	}

	public void carregarTableViewPedidos() {

		tableColumnStockData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tableColumnStockColaborador.setCellValueFactory(new PropertyValueFactory<>("Colaborador"));

		listStocks = stockDAO.listar();

		observableListStocks = FXCollections.observableArrayList(listStocks);
		tableViewPedidos.setItems(observableListStocks);

	}

	public void selecionarItemTableViewPedidos(Stock stock) {
		if (stock != null) {
			listViewItensDePedido.setItems(ItemDeStockDAO.listarPorStock(stock));
			listViewItensDePedido.setCellFactory(new Callback<ListView<ItemDeStock>, ListCell<ItemDeStock>>() {

				@Override
				public ListCell<ItemDeStock> call(ListView<ItemDeStock> param) {
					ListCell<ItemDeStock> cell = new ListCell<ItemDeStock>() {

						@Override
						protected void updateItem(ItemDeStock item, boolean empty) {
							super.updateItem(item, empty);
							setText(null);
							if (item != null) {
								setText(((ItemDeStock) item).getProduto() + " -> Preço: "
										+ ((ItemDeStock) item).getValor() + " -> Quantidade: " + ((ItemDeStock) item).getQuantidade());
							}
						}
					};
					return cell;
				}
			});
			
			labelCodigoStock.setText(String.valueOf(stock.getIdStock()));
			labelDataStock.setText(String.valueOf(stock.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
			labelValorStock.setText(String.format("%.2f€", stock.getValor()));
			labelColaboradorStock.setText(stock.getColaborador().toString());
		} else {
			labelCodigoStock.setText("");
			labelDataStock.setText("");
			labelValorStock.setText("");
			labelColaboradorStock.setText("");
			
			
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
			for (ItemDeStock listItemDeStock : stock.getItensDeStock()) {
				Produto produto = listItemDeStock.getProduto();
				produto.setQuantidade(produto.getQuantidade() + listItemDeStock.getQuantidade());
				produtoDAO.alterar(produto);
				itemDeStockDAO.remover(listItemDeStock);
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
		loader.setLocation(ProcessoStockPedidoController.class
				.getResource("/pt/iade/gestaoInventario/views/ProcessoStockPedido.fxml"));
		AnchorPane janela = (AnchorPane) loader.load();

		/** Mostrar tela de registo */
		Stage pedidoStage = new Stage();
		pedidoStage.setTitle("Registo de Itens do Pedido");
		pedidoStage.setResizable(false);
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
