package pt.iade.gestaoInventario.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import pt.iade.gestaoInventario.models.ItemDeStock;
import pt.iade.gestaoInventario.models.Stock;
import pt.iade.gestaoInventario.models.dao.ItemDeStockDAO;
/**
 * Controlador da interface de itens de pededido.
 * Permite visualizar os itens dos pedidos numa listView.
 *
 */
public class ItensDePedidoController implements Initializable {

	@FXML
	private ListView<ItemDeStock> listView;
	
	//private final  StockDAO StockDAO = new StockDAO();
	
	private Stock stock;


	public ItensDePedidoController(Stock stock) {
		this.stock = stock;
	}



	@Override
	public void initialize(URL url, ResourceBundle rb) {
		listView.setItems(ItemDeStockDAO.listarPorStock(stock));
	}
	
}


		/**listView.getItems().addAll(itemDeStockDAO.listarPorStock());
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
	}*/
