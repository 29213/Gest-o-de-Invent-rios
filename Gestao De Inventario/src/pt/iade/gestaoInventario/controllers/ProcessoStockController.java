package pt.iade.gestaoInventario.controllers;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pt.iade.gestaoInventario.models.ItemStock;
import pt.iade.gestaoInventario.models.Produto;
import pt.iade.gestaoInventario.models.Stock;
import pt.iade.gestaoInventario.models.dao.ItemStockDAO;
import pt.iade.gestaoInventario.models.dao.ProdutoDAO;
import pt.iade.gestaoInventario.models.dao.StockDAO;

public class ProcessoStockController implements Initializable{

	@FXML
	private TableView<Stock> tableViewPedidos;
	@FXML
	private TableColumn<Stock, Integer> tableColumnCodigo;

	@FXML
	private TableColumn<Stock, Date> tableColumnData;

	@FXML
	private TableColumn<Stock, Stock> tableColumnColaborador;

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
	
	private List<Stock> listaStocks;
	
	private ObservableList<Stock> observableListPedidos;
	
	private final ProdutoDAO produtoDAO = new ProdutoDAO();
	private final ItemStockDAO itemStockDAO = new ItemStockDAO();
	private final StockDAO stockDAO = new StockDAO();
	
	public void carregarTableViewPedidos() {
		
		tableColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("idStock"));
		tableColumnData.setCellValueFactory(new PropertyValueFactory<>("data"));
		tableColumnColaborador.setCellValueFactory(new PropertyValueFactory<>("Colarador"));

		listaStocks = stockDAO.listar();

		observableListPedidos = FXCollections.observableArrayList(listaStocks);
		tableViewPedidos.setItems(observableListPedidos);
	}
	
	public void selecionarTableViewItemDeStock(Stock stock) {
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
	void PedidoStock(ActionEvent event) {

	}

	@FXML
	void RevomerPedido(ActionEvent event) {
		Stock stock = tableViewPedidos.getSelectionModel().getSelectedItem();
		if (stock != null) {
			for (ItemStock listItemStock : stock.getItensDeStock()) {
				Produto produto = listItemStock.getProduto();
				produto.setQuantidade(produto.getQuantidade() + listItemStock.getQuantidade());
				produtoDAO.alterar(produto);
				itemStockDAO.remover(listItemStock);
			}
			stockDAO.remover(stock);
			carregarTableViewPedidos();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, escolha um Pedido na Tabela!");
			alert.show();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
}
