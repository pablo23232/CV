/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Contactos;

import Datos.Telefono;
import Datos.Telefono.TipoTelefono;
import static Datos.Telefono.TipoTelefono.MOVIL;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.binding.StringExpression;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Usuario
 */
public class TelefonoDialog extends Dialog<Telefono> {

	private class DialogValid extends BooleanBinding {

		private StringExpression numero;
		private ObjectExpression<TipoTelefono> tipo;
		
		public DialogValid(StringExpression numero, ObjectExpression<TipoTelefono> tipo) {
			
			this.numero = numero;
			this.tipo = tipo;
			
			bind(this.numero,this.tipo);
		}
		
		@Override
		protected boolean computeValue() {
			
			if( numero.get() == null || tipo.get() == null ) {
				return false;
			}
			
			if( numero.get().length() <= 0 ) {
				return false;
			}
			
			return true;
		}
		
		
	}
	public TelefonoDialog() {
		
		setTitle("Nuevo teléfono");
		setHeaderText("Introduzca el nuevo número de telefono");
		
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		
		
		GridPane root = new GridPane();
		root.setHgap(5);
		root.setVgap(5);
		
		Label numero = new Label("Número:");
		TextField numText = new TextField();
		numText.setPromptText("Número de teléfono");
		root.addRow(0, numero, numText);
		
		Label tipo = new Label("Tipo: ");
		ComboBox<TipoTelefono> tipoCombo = new ComboBox<Telefono.TipoTelefono>();
		tipoCombo.setPromptText("Seleccione un tipo");
		tipoCombo.getItems().addAll(Telefono.getTiposTelefono());
		root.addRow(1, tipo, tipoCombo);
		
		getDialogPane().setContent(root);
		
		ButtonType addButton = new ButtonType("Añadir", ButtonData.OK_DONE);
		ButtonType cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		
		getDialogPane().getButtonTypes().addAll(addButton, cancelButton);
		
		Button okButton = (Button) getDialogPane().lookupButton(addButton);
		okButton.disableProperty().bind(new DialogValid(numText.textProperty(), tipoCombo.valueProperty()).not());
		
		setResultConverter( bt -> {
			
			if( bt.getButtonData() == ButtonData.OK_DONE ) {
				return new Telefono(numText.getText(), tipoCombo.getSelectionModel().getSelectedItem());
			}
			
			return null;
		});
		
	}
}