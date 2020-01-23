package pt.iade.gestaoInventario;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Main extends Application {
	
	/**
	 * Start.
	 *
	 * @param primaryStage the primary stage
	 * @throws Exception the exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		
			Parent root = FXMLLoader.load(getClass().getResource("/pt/iade/gestaoInventario/views/TelaPrincipal.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sistema de Gestão de Inventários");
			primaryStage.setResizable(false);
			primaryStage.centerOnScreen();
			primaryStage.show();
		
		
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
