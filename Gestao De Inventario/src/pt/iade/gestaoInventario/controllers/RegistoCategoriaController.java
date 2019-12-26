package pt.iade.gestaoInventario.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import pt.iade.gestaoInventario.models.Categoria;
import pt.iade.gestaoInventario.models.dao.CategoriaDAO;

public class RegistoCategoriaController implements Initializable {
    @FXML
    private TextField textFieldDescrição;

    @FXML
    void adicionarCategoria(ActionEvent event) {
    	Categoria categoria = new Categoria();
    	categoria.setDescricao(textFieldDescrição.getText());
    	CategoriaDAO.inserir(categoria);
    	

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
