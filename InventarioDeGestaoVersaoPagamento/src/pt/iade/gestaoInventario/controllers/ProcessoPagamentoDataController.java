package pt.iade.gestaoInventario.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pt.iade.gestaoInventario.models.Pagamento;
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
public class ProcessoPagamentoDataController implements Initializable {
	
    @FXML
    private DatePicker datepickerDataPagamento;

    @FXML
    private Button buttonConfirmarPagamento;

    @FXML
    private Button buttonCancelarPagamento;

    @FXML
    private Label labelDataPagamento;
    
    
	private Stage pagamentoStage;
	private boolean buttonConfirmarClick = false;
	private Pagamento pagamento;


	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Stage getPagamentoStage() {
		return pagamentoStage;
	}

	public void setPagamentoStage(Stage pagamentoStage) {
		this.pagamentoStage = pagamentoStage;
	}

	public boolean isButtonConfirmarClick() {
		return buttonConfirmarClick;
	}

	public void setButtonConfirmarClick(boolean buttonConfirmarClick) {
		this.buttonConfirmarClick = buttonConfirmarClick;
	}

	@FXML
	void buttonCancelarPagamento(ActionEvent event) {
		getPagamentoStage().close();
	}

	@FXML
	void buttonConfirmarPagamento(ActionEvent event) throws IOException {
		if (validarEntradaDeDados()) {
			pagamento.setData(datepickerDataPagamento.getValue());

			buttonConfirmarClick = true;
			pagamentoStage.close();
		}

	}

	/** Validar a entrada de dados para o registo */
	private boolean validarEntradaDeDados() {
		String errorMessage = "";

		if (datepickerDataPagamento.getValue() == null) {
			errorMessage += "Data invalida!\n";
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
