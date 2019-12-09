
package Experiencia;

import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import Datos.Experiencia;
import Datos.Titulo;

public class InsertDialog<T> extends Dialog<T> {

	public InsertDialog(String titulo, String tipo, Class<T> Clase ) {
		
		setTitle(titulo);
		
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		
		GridPane root = new GridPane();
		root.setHgap(5);
		root.setVgap(5);
		
		Label denLabel = new Label("Denominación");
		TextField denText = new TextField();
		root.addRow(0, denLabel, denText);
			
		Label segundoLabel = new Label(tipo);
		TextField segundoText = new TextField();
		root.addRow(1,  segundoLabel, segundoText);
		
		GridPane.setHgrow(denText, Priority.ALWAYS);
		GridPane.setHgrow(segundoText, Priority.ALWAYS);
		
		Label desdeLabel = new Label("Desde");
		DatePicker desde = new DatePicker();
		root.addRow(2, desdeLabel, desde);

		Label hastaLabel = new Label("Hasta");
		DatePicker hasta = new DatePicker();
		root.addRow(3,  hastaLabel, hasta);
		
		getDialogPane().setContent(root);
		getDialogPane().setPrefWidth(384.f);
		
		ButtonType createButton = new ButtonType("Crear", ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		
		getDialogPane().getButtonTypes().addAll(createButton, cancelButton);
		
		setResultConverter( bt -> {

			// Aquí nos viene bien usar genéricos, puesto que ambos tienen similares constructores
			if( bt.getButtonData() == ButtonData.OK_DONE ) {
				return Clase.cast( Clase == Experiencia.class ?
						new Experiencia(desde.getValue(), hasta.getValue(), denText.getText(), segundoText.getText())
					:   new Titulo(desde.getValue(), hasta.getValue(), denText.getText(), segundoText.getText()));
			}
			
			return null;
		});
	}
}