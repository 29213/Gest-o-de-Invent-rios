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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.iade.gestaoInventario.models.Colaborador;
import pt.iade.gestaoInventario.models.Pagamento;
import pt.iade.gestaoInventario.models.dao.ColaboradorDAO;
import pt.iade.gestaoInventario.models.dao.PagamentoDAO;
import pt.iade.gestaoInventario.models.dao.PedidoDAO;
import pt.iade.gestaoInventario.models.dao.ProdutoDAO;

// TODO: Auto-generated Javadoc
/**
 * The Class ProcessoPagamentoController.
 */
public class ProcessoPagamentoController implements Initializable{

	/** The tabpane pagamento. */
	@FXML
	private TabPane tabpanePagamento;

    /** The tab pagamento pedidos. */
    @FXML
    private Tab tabPagamentoPedidos;
    
    /** The label pagamento colaborador. */
    @FXML
    private Label labelPagamentoColaborador;
    
	
	/*Combobox Colaboradores*/
	
	/** The combobox pagamento colaborador. */
	@FXML
	private ComboBox<Colaborador> comboboxPagamentoColaborador;	
	
	/** The list colaboradores. */
	private List<Colaborador> listColaboradores;
	
	/** The observable list colaboradores. */
	private ObservableList<Colaborador> observableListColaboradores;
	
	/** The colaborador DAO. */
	private final ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
	
	/** The pagamento DAO. */
	private final PagamentoDAO pagamentoDAO = new PagamentoDAO();
	
	/** The pedido DAO. */
	private final PedidoDAO pedidoDAO = new PedidoDAO();
	
	/** The produto DAO. */
	private final ProdutoDAO produtoDAO = new ProdutoDAO();
	
	
	
	/*Tabela Pagamentos*/
	
	/** The tableview pagamento. */
	@FXML
	private TableView<Pagamento> tableviewPagamento;
	
	/** The tablecolumn pagamento id. */
	@FXML
	private TableColumn<Pagamento, Integer> tablecolumnPagamentoId;

	/** The tablecolumn pagamento data. */
	@FXML
	private TableColumn<Pagamento, LocalDate> tablecolumnPagamentoData;
	
	/** The tablecolumn pagamento valor. */
	@FXML
	private TableColumn<Pagamento, Integer> tablecolumnPagamentoValor;
	
	/** The tablecolumn pagamento estado. */
	@FXML
	private TableColumn<Pagamento, String> tablecolumnPagamentoEstado;
	
	/** The list pagamentos. */
	private List<Pagamento> listPagamentos;
	
	/** The observable list pagamentos. */
	private ObservableList<Pagamento> observableListPagamentos;

	//@FXML
	//private TableColumn<Colaborador, String> tableColumnStockColaborador;
	
	
	/** The tab pagamento agregado. */
	/*Tabela Agregados*/
	@FXML
    private Tab tabPagamentoAgregado;

	/** The tableview agregado. */
	@FXML
	private TableView<Pagamento> tableviewAgregado;
	
	/** The tablecolumn agregado categoria. */
	@FXML
	private TableColumn<Pagamento, Integer> tablecolumnAgregadoCategoria;

	/** The tablecolumn agregado quantidade. */
	@FXML
	private TableColumn<Pagamento, LocalDate> tablecolumnAgregadoQuantidade;
	
	/** The tablecolumn agregado valor. */
	@FXML
	private TableColumn<Pagamento, Integer> tablecolumnAgregadoValor;
	
	
	/** The label valor total pagamento. */
	@FXML
	private Label labelValorTotalPagamento;
	
	/** The button cancelar pedido. */
	@FXML
	private Button buttonCancelarPedido;
	
	
	

	/**
	 * Initialize.
	 *
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarComboBoxColaboradores();
		carregarTableViewPedidos();
		
	}
	
	
	/**
	 * Carregar combo box colaboradores.
	 */
	public void carregarComboBoxColaboradores() {
		listColaboradores = colaboradorDAO.listar();
		observableListColaboradores = FXCollections.observableArrayList(listColaboradores);
		observableListColaboradores.add(0, null);
		comboboxPagamentoColaborador.setItems(observableListColaboradores);
	}
	
	/**
	 * Carregar table view pedidos.
	 */
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
	
	/**
	 * Combo box action.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	void comboBoxAction(ActionEvent event) throws IOException {
		carregarTableViewPedidos();
	}
	
	/**
	 * Cancelar pagamento.
	 */
	@FXML
	void cancelarPagamento() {
		tableviewPagamento.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * Calcular valor total.
	 *
	 * @return the double
	 */
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
