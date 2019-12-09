/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formacion;

import Datos.CV;
import Datos.Titulo;
import Experiencia.InsertDialog;
import Jaxb.LocalDateTableCell;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class FormacionController implements Initializable{

    @FXML
    private HBox view;

    @FXML
    private TableView<Titulo> formacionTable;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;
    
    @FXML
    private TableColumn<Titulo, LocalDate> desdeColumn;

    @FXML
    private TableColumn<Titulo, LocalDate> hastaColumn;
    
    
    private ListProperty<Titulo> titulos = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    public FormacionController() {
        try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormacionView.fxml"));
		loader.setController(this);
		loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    @Override
     public void initialize(URL url, ResourceBundle rb) {
         desdeColumn.setCellFactory(LocalDateTableCell::new); 
	hastaColumn.setCellFactory(LocalDateTableCell::new);
        formacionTable.itemsProperty().bindBidirectional(titulos);
         addButton.setOnAction( evt -> onAddAction() );
       deleteButton.setOnAction( evt -> onDeleteAction() );
         
       
    }
     
private void onAddAction() {
        InsertDialog<Titulo> dialog = new InsertDialog<>("Nuevo título", "Organizador", Titulo.class);
		
		Optional<Titulo> result = dialog.showAndWait();
		
		if( result.isPresent() ) {
			titulos.add(result.get());
		}

    }
    public HBox getView() {
        return view;
    }

	public final ListProperty<Titulo> titulosProperty() {
		return this.titulos;
	}
	

	public final ObservableList<Titulo> getTitulos() {
		return this.titulosProperty().get();
	}
	

	public final void setTitulos(final ObservableList<Titulo> titulos) {
		this.titulosProperty().set(titulos);
	}

    private void onDeleteAction() {
        Titulo seleccionado = formacionTable.getSelectionModel().getSelectedItem();
     if (seleccionado != null){
               titulos.remove(seleccionado);
               }
    }

  
    
    
}
