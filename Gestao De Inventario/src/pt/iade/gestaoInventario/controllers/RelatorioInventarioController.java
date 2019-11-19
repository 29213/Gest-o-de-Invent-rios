package pt.iade.gestaoInventario.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import pt.iade.gestaoInventario.models.Categoria;
import pt.iade.gestaoInventario.models.Produto;

public class RelatorioInventarioController {
	 @FXML
	    private TableColumn<Produto, Integer> tableColumnCodigo;

	    @FXML
	    private TableColumn<Produto, String> tableColumnProduto;

	    @FXML
	    private TableColumn<Produto, Integer> tableColumnQuantidade;

	    @FXML
	    private TableColumn<Categoria, String> tableColumnCategoria;
}
