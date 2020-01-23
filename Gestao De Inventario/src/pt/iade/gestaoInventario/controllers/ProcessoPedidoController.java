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

// TODO: Auto-generated Javadoc
/**
 * Controlador da interface principal do processo de pedido. 
 * Permite visualizar os pedidos feitos numa TableView.
 * Permite escolher um pedido e:
 * <li>Visualizar as informações do pedido ao lado "detalhes do pedido";
 * <li>Visualizar os itens dos pedidos feitos numa ListView;
 * <li>Apagar o pedido;
 * Permite adicionar um pedido abrindo uma nova janela, chama o contralador {@link ProcessoItemDoPedidoController}
 * 
 * @author Renato Pitta Simões
 */
public class ProcessoPedidoController implements Initializable {

	/** A lista de pedidos da tabela. */
	@FXML
	private TableView<Pedido> tableViewPedidos;
	
	/** A lista de codigos dos pedodos na coluna da tabela. */
	@FXML
	private TableColumn<Pedido, Integer> tableColumnPedidoCodigo;

	/** A lista de data do pedido na coluna da tabela. */
	@FXML
	private TableColumn<Pedido, LocalDate> tableColumnPedidoData;

	/** A lista de Colaborador associado ao pedido na coluna da tabela. */
	@FXML
	private TableColumn<Colaborador, String> tableColumnPedidoColaborador;

	/** A label codigo do pedido. */
	@FXML
	private Label labelCodigoPedido;

	/** A label data do pedido. */
	@FXML
	private Label labelDataPedido;

	/** A label colaborador do pedido. */
	@FXML
	private Label labelColaboradorPedido;

	/** A label valor do pedido. */
	@FXML
	private Label labelValorPedido;

	/** O botão adicionar. */
	@FXML
	private Button buttonAdicionar;

	/** O botão remover. */
	@FXML
	private Button buttonRemover;
	
	/** Botão pagar. */
	@FXML
	private Button buttonPagar;

	/** A lista de itens de pedido. */
	@FXML
	private ListView<ItemDoPedido> listViewItensDePedido;
	
	/** A lista de pedidos. */
	private List<Pedido> listPedidos;
	
	/** Os pedidos observáveis da lista. */
	private ObservableList<Pedido> observableListPedidos;

	/** Atributos para manipulação da base de dados. */
	private final ProdutoDAO produtoDAO = new ProdutoDAO();
	
	/** The item do pedido DAO. */
	private final ItemDoPedidoDAO itemDoPedidoDAO = new ItemDoPedidoDAO();
	
	/** The pedido DAO. */
	private final PedidoDAO pedidoDAO = new PedidoDAO();
	
	/** The pagamento DAO. */
	private final PagamentoDAO pagamentoDAO = new PagamentoDAO();
	/**
	 * Initialize.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		carregarTableViewPedidos();
		/**
		 * A Lista ativa diante de quaisquer alterações na seleção dos pedidos da Tabela.
		 */
		tableViewPedidos.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewPedidos(newValue));
	}
	
	/**
	 * Configuração da tabela de pedidos.
	 */
	public void carregarTableViewPedidos() {

		tableColumnPedidoData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tableColumnPedidoColaborador.setCellValueFactory(new PropertyValueFactory<>("Colaborador"));

		listPedidos = pedidoDAO.listar();

		observableListPedidos = FXCollections.observableArrayList(listPedidos);
		tableViewPedidos.setItems(observableListPedidos);

	}

	/**
	 * Selecionar item table view pedidos.
	 *
	 * @param pedido the pedido
	 */
	public void selecionarItemTableViewPedidos(Pedido pedido) {
		if (pedido != null) {
			listViewItensDePedido.setItems(ItemDoPedidoDAO.listarPorPedido(pedido));/** Listar os itens do pedido selecionado*/
		/** Apresentar a informação do pedido selecionado, detalhes do pedido*/
			labelCodigoPedido.setText(String.valueOf(pedido.getIdPedido()));
			labelDataPedido.setText(String.valueOf(pedido.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
			labelValorPedido.setText(String.format("%.2f€", pedido.getValor()));
			labelColaboradorPedido.setText(pedido.getColaborador().toString());
		} else {
			labelCodigoPedido.setText("");
			labelDataPedido.setText("");
			labelValorPedido.setText("");
			labelColaboradorPedido.setText("");
			
			
		}
	}

	/**
	 * Botão adicionar pedido.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	void buttonAdicionarPedido(ActionEvent event) throws IOException {
		Pedido pedido = new Pedido();
		List<ItemDoPedido> listItensDoPedido = new ArrayList<>();
		pedido.setItensDoPedido(listItensDoPedido);
		boolean buttonConfirmarPedido = showProcessoItemDoPedido(pedido);
		if (buttonConfirmarPedido) {
			pedidoDAO.inserir(pedido);
			for (ItemDoPedido listItemDoPedido : pedido.getItensDoPedido()) {
				Produto produto = listItemDoPedido.getProduto();
				listItemDoPedido.setPedido(pedidoDAO.buscarUltimoPedido());
				itemDoPedidoDAO.inserir(listItemDoPedido);
				produto.setQuantidade(produto.getQuantidade() - listItemDoPedido.getQuantidade());
				produtoDAO.alterar(produto);
			}
			carregarTableViewPedidos();
		}
	}

	/**
	 * Button remover pedido.
	 *
	 * @param event the event
	 * @throws IOException Sinais de que ocorreu uma exceção de E / S.
	 * @throws SQLException the SQL exception
	 */
	@FXML
	void buttonRemoverPedido(ActionEvent event) throws IOException, SQLException {
		Pedido pedido = tableViewPedidos.getSelectionModel().getSelectedItem();
		if (pedido != null) {
			for (ItemDoPedido listItemDoPedido : pedido.getItensDoPedido()) {
				Produto produto = listItemDoPedido.getProduto();
				produto.setQuantidade(produto.getQuantidade() + listItemDoPedido.getQuantidade());
				produtoDAO.alterar(produto);
				itemDoPedidoDAO.remover(listItemDoPedido);
				
			}
			pedidoDAO.remover(pedido);
			carregarTableViewPedidos();
			listViewItensDePedido.setItems(ItemDoPedidoDAO.listarPorPedido(pedido));
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, escolha um Pedido!");
			alert.show();
		}
	}
	
	/*NOVIDADE*/
	
	/**
	 * Button pagar pedido.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
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
			/*	pedidoDAO.alterarPagamento(pedido); */
			
			}
			carregarTableViewPedidos();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, escolha um Pedido na Tabela!");
			alert.show();
		}
	}
	
	
	/**
	 *  Mostar a janela de registo de itens do pedido.
	 *
	 * @param pedido the pedido
	 * @return verdadeiro, se for bem sucedido
	 * @throws IOException Sinais de que ocorreu uma exceção de E / S.
	 */
	private boolean showProcessoItemDoPedido(Pedido pedido) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ProcessoItemDoPedidoController.class
				.getResource("/pt/iade/gestaoInventario/views/ProcessoItemDoPedido.fxml"));
		AnchorPane janela = (AnchorPane) loader.load();

		/** Mostrar tela de registo */
		Stage pedidoStage = new Stage();
		pedidoStage.setTitle("Itens do Pedido");
		pedidoStage.setResizable(false);
		Scene scene = new Scene(janela);
		pedidoStage.setScene(scene);

		/** Passar o Pedido no Controlo. */
		ProcessoItemDoPedidoController controller = loader.getController();
		controller.setPedidoStage(pedidoStage);
		controller.setPedido(pedido);

		/** Mostra o Pedido e esperar até que o utilizador o feche*/
		pedidoStage.showAndWait();

		return controller.isButtonConfirmarClick();
	}
	
	/**
	 * Show processo pagamento.
	 *
	 * @param pagamento the pagamento
	 * @return true, if successful
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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
