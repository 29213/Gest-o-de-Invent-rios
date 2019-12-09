package pt.iade.gestaoInventario.controllers;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import pt.iade.gestaoInventario.models.Colaborador;
import pt.iade.gestaoInventario.models.dao.ColaboradorDAO;

public class RegistoColaboradorController implements Initializable {

	@FXML
	private TableView<Colaborador> tableViewColaboradores;

	@FXML
	private TableColumn<Colaborador, String> tableColumnColaboradorNome;

	@FXML
	private TableColumn<Colaborador, Integer> tableColumnColaboradorNumero;

	@FXML
	private Label labelCodigo;

	@FXML
	private Label labelNome;

	@FXML
	private Label labelNumero;

	@FXML
	private Label labelTelefone;

	@FXML
	private Button buttonAdicionar;

	@FXML
	private Button buttonAlterar;

	@FXML
	private Button buttonRemover;

	private ObservableList<Colaborador> observableListColaboradores;

	private List<Colaborador> listColaboradores = new ArrayList<Colaborador>();
	
	private final ColaboradorDAO colaboradorDAO = new ColaboradorDAO();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregarTableViewColaborador();

		/** Limpando a exibi��o dos detalhes do colaborador */
		selecionarItemTableViewColaboradores(null);

		/**
		 * Lista � ativada diante de qualquer altera��es na sele��o de itens da tabela
		 */
		tableViewColaboradores.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> selecionarItemTableViewColaboradores(newValue));

	}

	/** Carregar tabela colaborador */
	@FXML
	public void carregarTableViewColaborador() {

		tableColumnColaboradorNome.setCellValueFactory(new PropertyValueFactory<Colaborador, String>("nome"));
		tableColumnColaboradorNumero.setCellValueFactory(new PropertyValueFactory<Colaborador, Integer>("numero"));
		
		listColaboradores = colaboradorDAO.listar();

		observableListColaboradores = FXCollections.observableArrayList(listColaboradores);
		tableViewColaboradores.setItems(observableListColaboradores);
	}

	/** Selecionar item tabela colaborador */
	public void selecionarItemTableViewColaboradores(Colaborador colaborador) {
		if (colaborador != null) {
			labelCodigo.setText(String.valueOf(colaborador.getIdColaborador()));
			labelNome.setText(colaborador.getNome());
			labelNumero.setText(String.valueOf(colaborador.getNumero()));
			labelTelefone.setText(String.valueOf(colaborador.getTelefone()));
		} else {
			labelCodigo.setText("");
			labelNome.setText("");
			labelNumero.setText("");
			labelTelefone.setText("");
		}

	}

	@FXML
	void AdicionarColaborador(ActionEvent event) throws IOException {
		Colaborador colaborador = new Colaborador();
		boolean buttonConfirmarClick = showRegistoColaboradorStage(colaborador);
		if (buttonConfirmarClick) {
			colaboradorDAO.inserir(colaborador);
			carregarTableViewColaborador();
		}
	}

	@FXML
	void AlterarColaborador(ActionEvent event) throws IOException {
		tableViewColaboradores.getItems();
		Colaborador colaborador = tableViewColaboradores.getSelectionModel()
				.getSelectedItem();/** Obtendo Colaboradores selecionado */
		if (colaborador != null) {
			boolean buttonConfirmar = showRegistoColaboradorStage(colaborador);
			if (buttonConfirmar) {
				colaboradorDAO.alterar(colaborador);
				carregarTableViewColaborador();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, escolha um colaborador na Tabela!");
			alert.show();
		}
	}

	@FXML
	void RemoverColaborador(ActionEvent event) throws IOException {
		tableViewColaboradores.getItems();
		tableViewColaboradores.getSelectionModel().getSelectedItems();

		Colaborador colaborador = tableViewColaboradores.getSelectionModel().getSelectedItem();
		if (colaborador != null) {
			
			tableViewColaboradores.getItems().remove(colaborador);
			//observableListColaboradores.remove(colaborador);
			colaboradorDAO.remover(colaborador);
			carregarTableViewColaborador();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Por favor, escolha um colaborador na Tabela!");
			alert.show();
		}
	}

	public boolean showRegistoColaboradorStage(Colaborador colaborador) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(RegistoColaboradorStageController.class
				.getResource("/pt/iade/gestaoInventario/views/RegistoColaboradorStage.fxml"));
		AnchorPane janela = (AnchorPane) loader.load();

		/** Criando um tela de registo (colaboradorStage) */
		Stage colaboradorStage = new Stage();
		colaboradorStage.setTitle("Registar Colaborador");
		Scene scene = new Scene(janela);
		colaboradorStage.setScene(scene);

		/** Mudar o colaborador no Controller */
		RegistoColaboradorStageController controller = loader.getController();
		controller.setColaboradorStage(colaboradorStage);
		controller.setColaborador(colaborador);

		/** Mostra o resgisto e espera at� que o utilizador o feche */
		colaboradorStage.showAndWait();

		return controller.isButtonConfirmarClick();

	}
}
