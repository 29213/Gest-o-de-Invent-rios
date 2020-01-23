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
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.iade.gestaoInventario.models.Colaborador;
import pt.iade.gestaoInventario.models.ItemDoPedido;
import pt.iade.gestaoInventario.models.Pagamento;
import pt.iade.gestaoInventario.models.Produto;
import pt.iade.gestaoInventario.models.Pedido;
import pt.iade.gestaoInventario.models.dao.ItemDoPedidoDAO;
import pt.iade.gestaoInventario.models.dao.PagamentoDAO;
import pt.iade.gestaoInventario.models.dao.ProdutoDAO;
import pt.iade.gestaoInventario.models.dao.PedidoDAO;

/**
 * Controlador da interface principal do processo de pedido. Permite visualizar
 * os pedidos feitos numa ListView Permite escolher um pedido e:
 * <li>Apagar
 * <li>Visualizar as informações do pedido ao lado
 * <li>Visualizar os itens dos pedidos abrindo uma nova janela, chama o
 * controlador {@link ItensDePedidoController} Permite adicionar um pedido
 * abrindo uma nova janela, chama o contralador
 * {@link ProcessoItemDoPedidoController}
 * 
 */
public class ProcessoPedidoController implements Initializable {

	@FXML
	private TableView<Pedido> tableViewPedidos;
	@FXML
	private TableColumn<Pedido, Integer> tableColumnStockCodigo;

	@FXML
	private TableColumn<Pedido, LocalDate> tableColumnStockData;

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
	
	
	/*NOVIDADE*/
	@FXML
	private Button buttonPagar;

	@FXML
	private ListView<ItemDoPedido> listViewItensDePedido;
	
	private List<Pedido> listStocks;
	private ObservableList<Pedido> observableListStocks;

	/** Atributos para manipulação da base de dados */
	private final ProdutoDAO produtoDAO = new ProdutoDAO();
	private final ItemDoPedidoDAO itemDoPedidoDAO = new ItemDoPedidoDAO();
	private final PagamentoDAO pagamentoDAO = new PagamentoDAO();
	private final PedidoDAO pedidoDAO = new PedidoDAO();


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

		listStocks = pedidoDAO.listar();

		observableListStocks = FXCollections.observableArrayList(listStocks);
		tableViewPedidos.setItems(observableListStocks);

	}

	public void selecionarItemTableViewPedidos(Pedido pedido) {
		if (pedido != null) {
			listViewItensDePedido.setItems(ItemDoPedidoDAO.listarPorStock(pedido));
			labelCodigoStock.setText(String.valueOf(pedido.getIdStock()));
			labelDataStock.setText(String.valueOf(pedido.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
			labelValorStock.setText(String.format("%.2f€", pedido.getValor()));
			labelColaboradorStock.setText(pedido.getColaborador().toString());
		} else {
			labelCodigoStock.setText("");
			labelDataStock.setText("");
			labelValorStock.setText("");
			labelColaboradorStock.setText("");
			
			
		}
	}

	@FXML
	void buttonPedidoStock(ActionEvent event) throws IOException {
		Pedido pedido = new Pedido();
		List<ItemDoPedido> listItensDeStock = new ArrayList<>();
		pedido.setItensDeStock(listItensDeStock);
		boolean buttonConfirmarPedido = showProcessoItemDoPedido(pedido);
		if (buttonConfirmarPedido) {
			pedidoDAO.inserir(pedido);
			for (ItemDoPedido listItemDeStock : pedido.getItensDeStock()) {
				Produto produto = listItemDeStock.getProduto();
				listItemDeStock.setStock(pedidoDAO.buscarUltimoStock());
				itemDoPedidoDAO.inserir(listItemDeStock);
				produto.setQuantidade(produto.getQuantidade() - listItemDeStock.getQuantidade());
				produtoDAO.alterar(produto);
			}
			carregarTableViewPedidos();

		}
	}

	@FXML
	void buttonRevomerPedido(ActionEvent event) throws IOException, SQLException {
		Pedido pedido = tableViewPedidos.getSelectionModel().getSelectedItem();
		if (pedido != null) {
			for (ItemDoPedido listItemDeStock : pedido.getItensDeStock()) {
				Produto produto = listItemDeStock.getProduto();
				produto.setQuantidade(produto.getQuantidade() + listItemDeStock.getQuantidade());
				produtoDAO.alterar(produto);
				itemDoPedidoDAO.remover(listItemDeStock);
			}
			pedidoDAO.remover(pedido);
			carregarTableViewPedidos();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, escolha um Pedido na Tabela!");
			alert.show();
		}
	}
	
	
	
	/*NOVIDADE*/
	@FXML
	void buttonPagarPedido(ActionEvent event) throws IOException, SQLException {
		Pedido pedido = tableViewPedidos.getSelectionModel().getSelectedItem();
		if (pedido != null) {
			/*criar pagamento*/
			Pagamento pagamento = new Pagamento();
			boolean buttonConfirmarPedido = showProcessoPagamento(pagamento); /*data*/
			if (buttonConfirmarPedido) {
				pagamento.definirEstado();
				pagamento.setValor(pedido.getValor());
				pagamentoDAO.inserir(pagamento);
				
				/*associar pagamento a pedido*/
				pagamento = pagamentoDAO.buscarUltimoPagamento();
				pedido.setPagamento(pagamento);
				pedidoDAO.alterarPagamento(pedido);
			
			}
			carregarTableViewPedidos();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, escolha um Pedido na Tabela!");
			alert.show();
		}
	}

	private boolean showProcessoItemDoPedido(Pedido pedido) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ProcessoItemDoPedidoController.class
				.getResource("/pt/iade/gestaoInventario/views/ProcessoItemDoPedido.fxml"));
		AnchorPane janela = (AnchorPane) loader.load();

		/** Mostrar tela de registo */
		Stage pedidoStage = new Stage();
		pedidoStage.setTitle("Registo de Itens do Pedido");
		pedidoStage.setResizable(false);
		Scene scene = new Scene(janela);
		pedidoStage.setScene(scene);

		/** Passar o Pedido no Controlo. */
		ProcessoItemDoPedidoController controller = loader.getController();
		controller.setPedidoStage(pedidoStage);
		controller.setStock(pedido);

		/** Mostra o Pedido e esperar até que o utilizador o feche */
		pedidoStage.showAndWait();

		return controller.isButtonConfirmarClick();
	}
	
	/*NOVIDADE*/
	private boolean showProcessoPagamento(Pagamento pagamento) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ProcessoPagamentoDataController.class
				.getResource("/pt/iade/gestaoInventario/views/ProcessoPagamentoData.fxml"));
		AnchorPane janela = (AnchorPane) loader.load();

		/** Mostrar tela de Data */
		Stage dataStage = new Stage();
		dataStage.setTitle("Data de Pagamento");
		dataStage.setResizable(false);
		Scene scene = new Scene(janela);
		dataStage.setScene(scene);

		/** Passar o Pagamento no Controlo. */
		ProcessoPagamentoDataController controller = loader.getController();
		controller.setPagamentoStage(dataStage);
		controller.setPagamento(pagamento);

		/** Mostra o Pedido e esperar até que o utilizador o feche */
		dataStage.showAndWait();

		return controller.isButtonConfirmarClick();
	}
}
