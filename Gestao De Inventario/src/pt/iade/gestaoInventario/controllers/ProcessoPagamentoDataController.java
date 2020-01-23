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

// TODO: Auto-generated Javadoc
/**
 * The Class ProcessoPagamentoDataController.
 */
public class ProcessoPagamentoDataController  implements Initializable{
	 
 	/** The datepicker data pagamento. */
 	@FXML
	    private DatePicker datepickerDataPagamento;

	    /** The button confirmar pagamento. */
    	@FXML
	    private Button buttonConfirmarPagamento;

	    /** The button cancelar pagamento. */
    	@FXML
	    private Button buttonCancelarPagamento;

	    /** The label data pagamento. */
    	@FXML
	    private Label labelDataPagamento;
	    
	    
		/** The pagamento stage. */
		private Stage pagamentoStage;
		
		/** The button confirmar click. */
		private boolean buttonConfirmarClick = false;
		
		/** The pagamento. */
		private Pagamento pagamento;


		/**
		 * Gets the pagamento.
		 *
		 * @return the pagamento
		 */
		public Pagamento getPagamento() {
			return pagamento;
		}

		/**
		 * Sets the pagamento.
		 *
		 * @param pagamento the new pagamento
		 */
		public void setPagamento(Pagamento pagamento) {
			this.pagamento = pagamento;
		}

		/**
		 * Gets the pagamento stage.
		 *
		 * @return the pagamento stage
		 */
		public Stage getPagamentoStage() {
			return pagamentoStage;
		}

		/**
		 * Sets the pagamento stage.
		 *
		 * @param pagamentoStage the new pagamento stage
		 */
		public void setPagamentoStage(Stage pagamentoStage) {
			this.pagamentoStage = pagamentoStage;
		}

		/**
		 * Checks if is button confirmar click.
		 *
		 * @return true, if is button confirmar click
		 */
		public boolean isButtonConfirmarClick() {
			return buttonConfirmarClick;
		}

		/**
		 * Sets the button confirmar click.
		 *
		 * @param buttonConfirmarClick the new button confirmar click
		 */
		public void setButtonConfirmarClick(boolean buttonConfirmarClick) {
			this.buttonConfirmarClick = buttonConfirmarClick;
		}

		/**
		 * Button cancelar pagamento.
		 *
		 * @param event the event
		 */
		@FXML
		void buttonCancelarPagamento(ActionEvent event) {
			getPagamentoStage().close();
		}

		/**
		 * Button confirmar pagamento.
		 *
		 * @param event the event
		 * @throws IOException Signals that an I/O exception has occurred.
		 */
		@FXML
		void buttonConfirmarPagamento(ActionEvent event) throws IOException {
			if (validarEntradaDeDados()) {
				pagamento.setData(datepickerDataPagamento.getValue());

				buttonConfirmarClick = true;
				pagamentoStage.close();
			}

		}

		/**
		 *  Validar a entrada de dados para o registo.
		 *
		 * @return true, if successful
		 */
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
				alert.setHeaderText("Campos invalidos");
				alert.setContentText(errorMessage);
				alert.show();
				return false;
			}
		}

		/**
		 * Initialize.
		 *
		 * @param arg0 the arg 0
		 * @param arg1 the arg 1
		 */
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			
		}
}
