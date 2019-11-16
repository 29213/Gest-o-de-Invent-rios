package pt.iade.gestaoInventario.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import pt.iade.gestaoInventario.models.Categoria;
import pt.iade.gestaoInventario.models.Produto;

public class ControllerRegistoProduto {

    @FXML
    private TableColumn<Produto, String> tableColumnNome;

    @FXML
    private TableColumn<Produto, Double> tableColumnPreco;

    @FXML
    private TableColumn<Produto, Integer> tableColumnQuantidade;

    @FXML
    private TableColumn<Categoria, String> tableColumnCategoria;

    @FXML
    private TextField TextFieldNome;

    @FXML
    private TextField TextFieldPreco;

    @FXML
    private TextField TextFieldQuantidade;

    @FXML
    private ComboBox<Categoria> comboBoxCategoria;

    @FXML
    private Button buttonAdicionar;

    @FXML
    private Button buttonRemover;

    @FXML
    void AdicionarProduto(ActionEvent event) {

    }

    @FXML
    void RemoverProduto(ActionEvent event) {

    }
}
