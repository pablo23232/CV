
package Conocimientos;

import Conocimientos.ConocimientosController.TipoConocimiento;
import Datos.Conocimiento;
import Datos.Conocimiento.Nivel;
import Datos.Idioma;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.binding.StringExpression;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class ConocimientoDialog extends Dialog<Conocimiento> {

	private class DialogValid extends BooleanBinding {

		private StringExpression denominacion;
		private ObjectExpression<Nivel> nivel;
		
		public DialogValid(StringExpression denominacion, ObjectExpression<Nivel> nivel) {
			
			this.denominacion = denominacion;
			this.nivel = nivel;
			
			bind(this.denominacion,this.nivel);
		}
		
		@Override
		protected boolean computeValue() {
			
			if( denominacion.get() == null || nivel.get() == null ) {
				return false;
			}
			
			if( denominacion.get().length() <= 0 ) {
				return false;
			}
			
			return true;
		}
		
		
	}
	public ConocimientoDialog(TipoConocimiento tipo) {
		
		setTitle("Nuevo conocimiento");
		
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		GridPane grid = new GridPane();
		grid.setHgap(5);
		grid.setVgap(5);
		
		Label denLabel = new Label("Denominación:");
		TextField denText = new TextField();
		grid.addRow(0, denLabel, denText);
		
		Label nivelLabel = new Label("Tipo: ");
		ComboBox<Nivel> nivel = new ComboBox<Nivel>();
		nivel.setPromptText("Seleccione un nivel");
		nivel.getItems().addAll(Conocimiento.getNiveles());
		Button resetButton = new Button("X"); // El botón reset
		resetButton.setOnAction( evt -> nivel.setValue(null) );
		grid.addRow(1, nivelLabel, new HBox(5, nivel, resetButton));
		
		// Si es un idioma, tenemos que añadir un campo más
		Label idiomaLabel  = null;
		TextField idiomaText = new TextField(); // Los TextField es obligatorio instanciarlos
		if( tipo == TipoConocimiento.IDIOMA ) {
			idiomaLabel = new Label("Certificación");
			grid.addRow(2, idiomaLabel, idiomaText);
		}
		
		getDialogPane().setContent(grid);
		
		ButtonType addBt = new ButtonType("Añadir", ButtonData.OK_DONE);
		ButtonType cancelBt = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		
		getDialogPane().getButtonTypes().addAll(addBt, cancelBt);
		
		// Nos aseguramos de que los datos sean válidos
		Button okButton = (Button) getDialogPane().lookupButton(addBt);
		okButton.disableProperty().bind(new DialogValid(denText.textProperty(), nivel.valueProperty()).not());
		
		setResultConverter( bt -> {
			
			if( bt.getButtonData() == ButtonData.OK_DONE ) {
				
				if( tipo == TipoConocimiento.IDIOMA ) {
					return new Idioma( nivel.getValue(), denText.getText(), idiomaText.getText());
				}
				
				return new Conocimiento(nivel.getValue(), denText.getText());
			}
			
			return null;
		});
		
	}
}