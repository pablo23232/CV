/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Experiencia;

import Datos.Experiencia;
import Datos.Titulo;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;



public class ExperienciaController implements Initializable {
    @FXML
    private HBox view;

    @FXML
    private TableView<Experiencia> experienciaTable;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;
        @FXML
    private TableColumn<Titulo, LocalDate> desdeColumn;

    @FXML
    private TableColumn<Titulo, LocalDate> hastaColumn;
    
        private ListProperty<Experiencia> listaExperiencia = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));


    public ExperienciaController() {
        try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExperienciaView.fxml"));
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
         experienciaTable.itemsProperty().bindBidirectional(listaExperiencia);
         addButton.setOnAction( evt -> onAddAction() );
         deleteButton.setOnAction( evt -> onDeleteAction() );
    }
private void onAddAction() {
     InsertDialog<Experiencia> dialog = new InsertDialog<>("Añadir experiencia", "Empleador", Experiencia.class);
		
		Optional<Experiencia> experiencia = dialog.showAndWait();
		
		if( experiencia.isPresent() ) {
			listaExperiencia.add(experiencia.get());
		}
}

    public HBox getView() {
        return view;
    }

  public final ListProperty<Experiencia> listaExperienciaProperty() {
		return this.listaExperiencia;
	}
	

	public final ObservableList<Experiencia> getListaExperiencia() {
		return this.listaExperienciaProperty().get();
	}
	

	public final void setListaExperiencia(final ObservableList<Experiencia> listExperiencia) {
		this.listaExperienciaProperty().set(listaExperiencia);
	}

    private void onDeleteAction() {
        Experiencia seleccionado = experienciaTable.getSelectionModel().getSelectedItem();
         if (seleccionado != null){
               listaExperiencia.remove(seleccionado);
               }
    }

   
    
    
}
