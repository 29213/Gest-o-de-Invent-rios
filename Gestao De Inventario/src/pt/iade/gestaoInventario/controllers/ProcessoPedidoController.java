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
import pt.iade.gestaoInventario.models.Produto;
import pt.iade.gestaoInventario.models.Pedido;
import pt.iade.gestaoInventario.models.dao.ItemDoPedidoDAO;
import pt.iade.gestaoInventario.models.dao.ProdutoDAO;
import pt.iade.gestaoInventario.models.dao.PedidoDAO;

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

	@FXML
	private ListView<ItemDoPedido> listViewItensDePedido;
	
	private List<Pedido> listStocks;
	private ObservableList<Pedido> observableListStocks;

	/** Atributos para manipulação da base de dados */
	private final ProdutoDAO produtoDAO = new ProdutoDAO();
	private final ItemDoPedidoDAO itemDoPedidoDAO = new ItemDoPedidoDAO();
	private final PedidoDAO pedidoDAO = new PedidoDAO();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		carregarTableViewPedidos();
		/**
		 * A Lista ativa diante de quaisquer alterações na seleção dos pedidos da Tabela.
		 */
		tableViewPedidos.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewPedidos(newValue));
	}
	/**Configuração da tabela de pedidos*/
	public void carregarTableViewPedidos() {

		tableColumnStockData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tableColumnStockColaborador.setCellValueFactory(new PropertyValueFactory<>("Colaborador"));

		listStocks = pedidoDAO.listar();

		observableListStocks = FXCollections.observableArrayList(listStocks);
		tableViewPedidos.setItems(observableListStocks);

	}

	public void selecionarItemTableViewPedidos(Pedido pedido) {
		if (pedido != null) {
			listViewItensDePedido.setItems(ItemDoPedidoDAO.listarPorStock(pedido));/** Listar os itens do pedido selecionado*/
		/** Apresentar a informação do pedido selecionado, detalhes do pedido*/
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
		boolean buttonConfirmarPedido = showProcessoStockPedido(pedido);
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
	/** Apresentar a janela de registo de itens do pedido*/
	private boolean showProcessoStockPedido(Pedido pedido) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ProcessoItemDoPedidoController.class
				.getResource("/pt/iade/gestaoInventario/views/ProcessoStockPedido.fxml"));
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
}
