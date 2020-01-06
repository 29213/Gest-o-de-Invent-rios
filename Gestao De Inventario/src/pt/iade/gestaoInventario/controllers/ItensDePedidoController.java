package pt.iade.gestaoInventario.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import pt.iade.gestaoInventario.models.ItemDeStock;
import pt.iade.gestaoInventario.models.dao.ItemDeStockDAO;

public class ItensDePedidoController implements Initializable {

	@FXML
	private ListView<ItemDeStock> listView = new ListView<>();

	private List<ItemDeStock> listItens() {
		List<ItemDeStock> retorno = new ArrayList<>();
		retorno = itemDeStockDAO.listar();
		return retorno;
	}

	private final ItemDeStockDAO itemDeStockDAO = new ItemDeStockDAO();
	

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		listView.getItems().addAll(listItens());
		listView.setCellFactory(new Callback<ListView<ItemDeStock>, ListCell<ItemDeStock>>() {

			@Override
			public ListCell<ItemDeStock> call(ListView<ItemDeStock> param) {
				ListCell<ItemDeStock> cell = new ListCell<ItemDeStock>() {

					@Override
					protected void updateItem(ItemDeStock item, boolean empty) {
						super.updateItem(item, empty);
						setText(null);
						if (item != null) {
							setText(((ItemDeStock) item).getProduto() + " Quantidade: "
									+ ((ItemDeStock) item).getQuantidade());

						}
					}
				};
				return cell;
			}
		});
	}
}