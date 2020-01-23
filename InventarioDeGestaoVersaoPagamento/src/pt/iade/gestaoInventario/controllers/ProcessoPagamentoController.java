package pt.iade.gestaoInventario.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.iade.gestaoInventario.models.Colaborador;
import pt.iade.gestaoInventario.models.ItemDoPedido;
import pt.iade.gestaoInventario.models.Pagamento;
import pt.iade.gestaoInventario.models.Produto;
import pt.iade.gestaoInventario.models.Pedido;
import pt.iade.gestaoInventario.models.dao.ColaboradorDAO;
import pt.iade.gestaoInventario.models.dao.ItemDoPedidoDAO;
import pt.iade.gestaoInventario.models.dao.PagamentoDAO;
import pt.iade.gestaoInventario.models.dao.ProdutoDAO;
import pt.iade.gestaoInventario.models.dao.PedidoDAO;




public class ProcessoPagamentoController implements Initializable {

	/*Combobox Colaboradores*/
	
	@FXML
	private ComboBox<Colaborador> comboboxPagamentoColaborador;	
	private List<Colaborador> listColaboradores;
	private ObservableList<Colaborador> observableListColaboradores;
	
	private final ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
	
	private final PagamentoDAO pagamentoDAO = new PagamentoDAO();
	private final PedidoDAO pedidoDAO = new PedidoDAO();
	private final ProdutoDAO produtoDAO = new ProdutoDAO();
	
	
	
	/*Tabela Pagamentos*/
	
	@FXML
	private TableView<Pagamento> tableviewPagamento;
	@FXML
	private TableColumn<Pagamento, Integer> tablecolumnPagamentoId;

	@FXML
	private TableColumn<Pagamento, LocalDate> tablecolumnPagamentoData;
	
	@FXML
	private TableColumn<Pagamento, Integer> tablecolumnPagamentoValor;
	
	@FXML
	private TableColumn<Pagamento, String> tablecolumnPagamentoEstado;
	
	private List<Pagamento> listPagamentos;
	private ObservableList<Pagamento> observableListPagamentos;

	//@FXML
	//private TableColumn<Colaborador, String> tableColumnStockColaborador;
	
	
	/*Tabela Agregados*/
	
	@FXML
	private TableView<Pagamento> tableviewAgregado;
	@FXML
	private TableColumn<Pagamento, Integer> tablecolumnAgregadoCategoria;

	@FXML
	private TableColumn<Pagamento, LocalDate> tablecolumnAgregadoQuantidade;
	
	@FXML
	private TableColumn<Pagamento, Integer> tablecolumnAgregadoValor;
	
	
	@FXML
	private Label labelValorTotalPagamento;
	
	@FXML
	private Button buttonCancelarPedido;
	
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarComboBoxColaboradores();
		carregarTableViewPedidos();
		
	}
	
	
	public void carregarComboBoxColaboradores() {
		listColaboradores = colaboradorDAO.listar();
		observableListColaboradores = FXCollections.observableArrayList(listColaboradores);
		observableListColaboradores.add(0, null);
		comboboxPagamentoColaborador.setItems(observableListColaboradores);
	}
	
	public void carregarTableViewPedidos() {
		
		tablecolumnPagamentoId.setCellValueFactory(new PropertyValueFactory<>("idPagamento"));
		tablecolumnPagamentoData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tablecolumnPagamentoValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		tablecolumnPagamentoEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
		
		Colaborador colaborador = (Colaborador) comboboxPagamentoColaborador.getSelectionModel().getSelectedItem();
		if (colaborador != null) {
			listPagamentos = pagamentoDAO.listarPorColaborador(colaborador);
		}
		else {
			listPagamentos = pagamentoDAO.listar();
		}
		observableListPagamentos = FXCollections.observableArrayList(listPagamentos);
		tableviewPagamento.setItems(observableListPagamentos);
		
		labelValorTotalPagamento.setText(String.format("%.2f€", calcularValorTotal()));

	}
	
	@FXML
	void comboBoxAction(ActionEvent event) throws IOException {
		carregarTableViewPedidos();
	}
	
	@FXML
	void cancelarPagamento() {
		Pagamento pagamento = tableviewPagamento.getSelectionModel().getSelectedItem();
		if (pagamento != null && pagamento.getEstado() == "PENDENTE") {
			Pedido pedido = pedidoDAO.buscarPorPagamento(pagamento);
			List<ItemDoPedido> listItemDeStock = ItemDoPedidoDAO.listarPorStock(pedido);
			
			for (ItemDoPedido itemDoPedido : listItemDeStock) {
				Produto produto = itemDoPedido.getProduto();
				produto.setQuantidade(produto.getQuantidade() + itemDoPedido.getQuantidade());
				produtoDAO.alterar(produto);
			}
			pagamento.setEstado("CANCELADO");
			pagamentoDAO.alterar(pagamento);
			carregarTableViewPedidos();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, escolha um Pagamento Pendente na Tabela!");
			alert.show();
		}
		
		
	}
	
	private double calcularValorTotal() {
		double valorTotal = 0.0;
		for(Pagamento pagamento : listPagamentos) {
			if(pagamento.getEstado() != "CANCELADO") {
				valorTotal += pagamento.getValor();
			}
		}
		return valorTotal;
	}
	
	
	
}