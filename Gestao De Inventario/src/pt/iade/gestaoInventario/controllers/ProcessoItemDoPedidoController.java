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

// TODO: Auto-generated Javadoc
/**
 * 
 * Controlador da interface do registo dos itens do pedido.
 * <p>
 * Permite selecionar o colaborador apartir de uma Combobox.
 * <p>
 * Permite selecionar uma data.
 * <p>
 * Permite selecionar os produtos e:
 * <p>
 * Adicionar as quantidadades desejadas.
 * <p>
 * Visualizar as informações dos produtos numa tableView.
 * <p>
 * Permite confirmar o registo dos itens de pedido, fechar a janela e gera o
 * pedido.
 * <p>
 * Permite cancelar o registo de itens de pedido e fecha a janela.
 * 
 * @author Renato Pitta Simões
 */
public class ProcessoItemDoPedidoController implements Initializable {

	/** O combobox pedido colaborador. */
	@FXML
	private ComboBox<Colaborador> comboBoxPedidoColaborador;

	/** O selecionador de data. */
	@FXML
	private DatePicker dataPicker;

	/** O combobox pedido produto. */
	@FXML
	private ComboBox<Produto> comboBoxPedidoProduto;

	/** O campo de texto produto quantidade. */
	@FXML
	private TextField textFildProdutoQuantidade;

	/** O botão adicionar. */
	@FXML
	private Button buttanAdicionar;

	/** A tabela exibe itens do pedido. */
	@FXML
	private TableView<ItemDoPedido> tableViewItensDoPedido;

	/** A coluna da tabela produto. */
	@FXML
	private TableColumn<ItemDoPedido, Produto> TableColumnItemPedidoProduto;

	/** A coluna Tabela quantidade. */
	@FXML
	private TableColumn<ItemDoPedido, Integer> TableColumnItemPedidoQuantidade;

	/** A coluna da Tabela valor. */
	@FXML
	private TableColumn<ItemDoPedido, Double> TableColumnItemPedidoValor;

	/** O campo de texto total do pedido. */
	@FXML
	private TextField textFildTotalPedido;

	/** O botão confirmar click S. */
	@FXML
	private boolean buttonConfirmarClickS;

	/** O botão Cancelar pedido. */
	@FXML
	private Button CancelarPedido;

	/** Lista de colaboradores. */
	private List<Colaborador> listColaboradores;

	/** Lista de produtos. */
	private List<Produto> listProdutos;

	/** lista observável dos colaboradores. */
	private ObservableList<Colaborador> observableListColaboradores;

	/** lista observável dos produtos. */
	private ObservableList<Produto> observableListProdutos;

	/** A lista observável dos itens do pedido. */
	private ObservableList<ItemDoPedido> observableListItensDoPedido;

	/** Atributos para manipulação da base de dados. */
	private final ColaboradorDAO colaboradorDAO = new ColaboradorDAO();

	/** Atributos para manipulação da base de dados. */
	private final ProdutoDAO produtoDAO = new ProdutoDAO();

	/** O Pedido stage. */
	private Stage PedidoStage;

	/** O botão confirmar clique. */
	private boolean buttonConfirmarClick = false;

	/** O pedido. */
	private Pedido pedido;

	/**
	 * Initializa.
	 *
	 * @param url the url
	 * @param rb  the rb
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		carregarComboBoxColaboradores();
		carregarComboxProdutos();

		TableColumnItemPedidoProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
		TableColumnItemPedidoQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		TableColumnItemPedidoValor.setCellValueFactory(new PropertyValueFactory<>("valor"));

	}

	/**
	 * Carregar combobox colaboradores.
	 */
	public void carregarComboBoxColaboradores() {

		listColaboradores = colaboradorDAO.listar();

		observableListColaboradores = FXCollections.observableArrayList(listColaboradores);
		comboBoxPedidoColaborador.setItems(observableListColaboradores);
	}

	/**
	 * Carregar combobox produtos.
	 */
	public void carregarComboxProdutos() {
		listProdutos = produtoDAO.listar();
		observableListProdutos = FXCollections.observableArrayList(listProdutos);
		comboBoxPedidoProduto.setItems(observableListProdutos);
	}

	/**
	 * Obetem o pedido.
	 *
	 * @return o pedido
	 */
	public Pedido getPedido() {
		return pedido;
	}

	/**
	 * Define o pedido.
	 *
	 * @param solicita o novo pedido
	 */
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	/**
	 * Obtem o Pedido Stage.
	 *
	 * @return o Pedido Stage.
	 */
	public Stage getPedidoStage() {
		return PedidoStage;
	}

	/**
	 * Define Pedido stage.
	 *
	 * @param pedidoStage o novo Pedido Stage.
	 */
	public void setPedidoStage(Stage pedidoStage) {
		PedidoStage = pedidoStage;
	}

	/**
	 * Verifica se o botão confirma click.
	 *
	 * @return verdade, se for o botão confirmar click
	 */
	public boolean isButtonConfirmarClick() {
		return buttonConfirmarClick;
	}

	/**
	 * Define o botão confirmar clique.
	 *
	 * @param buttonConfirmarClick o novo botão confirmar click
	 */
	public void setButtonConfirmarClick(boolean buttonConfirmarClick) {
		this.buttonConfirmarClick = buttonConfirmarClick;
	}

	/**
	 * Botão adicionar quantidade.
	 *
	 * @param event the event
	 * @throws IOException Sinais de que ocorreu uma exceção de E / S.
	 */
	@FXML
	void buttonAdicionarQuantidade(ActionEvent event) throws IOException {

		Produto produto;
		ItemDoPedido itemDoPedido = new ItemDoPedido();
		itemDoPedido.setPedido(pedido);

		if (comboBoxPedidoProduto.getSelectionModel().getSelectedItem() != null) {
			
			produto = (Produto) comboBoxPedidoProduto.getSelectionModel().getSelectedItem();

			if (produto.getQuantidade() >= Integer.parseInt(textFildProdutoQuantidade.getText())) {
				itemDoPedido.setProduto((Produto) comboBoxPedidoProduto.getSelectionModel().getSelectedItem());
				itemDoPedido.setQuantidade(Integer.parseInt(textFildProdutoQuantidade.getText()));
				itemDoPedido.setValor(itemDoPedido.getProduto().getPreco() * itemDoPedido.getQuantidade());

				pedido.getItensDoPedido().add(itemDoPedido);
				pedido.setValor(pedido.getValor() + itemDoPedido.getValor());

				observableListItensDoPedido = FXCollections.observableArrayList(pedido.getItensDoPedido());
				tableViewItensDoPedido.setItems(observableListItensDoPedido);

				textFildTotalPedido.setText(String.format("%.2f€", pedido.getValor()));

			} else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setHeaderText("Problemas na escolha do Produto!");
				alert.setContentText("Quantidade indisponivel!");
				alert.show();
			}
		}
	}

	/**
	 * Botão cancelar pedido.
	 *
	 * @param fechar a janela.
	 */
	@FXML
	void buttonCancelarPedido(ActionEvent event) {
		getPedidoStage().close();
	}

	/**
	 * Botão confirmar pedido.
	 *
	 * @param event the event
	 * @throws IOException Sinais de que ocorreu uma exceção de E / S.
	 */
	@FXML
	void buttonConfirmarPedido(ActionEvent event) throws IOException {
		if (validarEntradaDeDados()) {
			pedido.setColaborador((Colaborador) comboBoxPedidoColaborador.getSelectionModel().getSelectedItem());
			pedido.setData(dataPicker.getValue());

			buttonConfirmarClick = true;
			PedidoStage.close();
		}

	}

	/**
	 * Validar a entrada de dados para o registo.
	 *
	 * @return verdadeiro, se for bem sucedido.
	 */
	private boolean validarEntradaDeDados() {
		String errorMessage = "";

		if (comboBoxPedidoColaborador.getSelectionModel().getSelectedItem() == null) {
			errorMessage += "Colaborador invalido!\n";
		}
		if (dataPicker.getValue() == null) {
			errorMessage += "Data invalida!\n";
		}
		if (comboBoxPedidoProduto.getSelectionModel().getSelectedItem() == null) {
			errorMessage += "Produto invalido!\n";
		}
		if (textFildProdutoQuantidade.getText() == null || textFildProdutoQuantidade.getText().length() == 0) {
			errorMessage += "Quantidade invalida!\n";
		}
		if (observableListItensDoPedido == null) {
			errorMessage += "Itens de Pedido invalidos!\n";
		}
		if (errorMessage.length() == 0) {
			return true;
		} else {
			/** Mostrar mensagem de erro. */

			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erro no Registo");
			alert.setHeaderText("Campos invalidos!");
			alert.setContentText(errorMessage);
			alert.show();
			return false;
		}
	}
}
