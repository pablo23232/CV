
package Conocimientos;

import Datos.CV;
import Datos.Conocimiento;
import Datos.Idioma;
import Datos.Telefono;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class ConocimientosController implements Initializable {


    
      public enum TipoConocimiento{
        IDIOMA,
        CONOCIMIENTO
};
     @FXML
    private HBox view;

    @FXML
    private TableView<Conocimiento> conocimientoTable;

    @FXML
    private Button addKnowButton;

    @FXML
    private Button addLanguageButton;

    @FXML
    private Button deleteButton;

  
    
  

    private ListProperty<Conocimiento> conocimientos = new SimpleListProperty<Conocimiento>(FXCollections.observableArrayList(new ArrayList<Conocimiento>()));
    public ConocimientosController() {
        try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConocimientosView.fxml"));
		loader.setController(this);
		loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 
    }}
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conocimientoTable.itemsProperty().bindBidirectional(conocimientos);
        addKnowButton.setOnAction( evt -> onAddAction(TipoConocimiento.CONOCIMIENTO) );
	addLanguageButton.setOnAction( evt -> onAddAction(TipoConocimiento.IDIOMA) );
        deleteButton.setOnAction( evt -> onDeleteAction() );
	deleteButton.disableProperty().bind( conocimientoTable.getSelectionModel().selectedItemProperty().isNull() );
    }

   private void onDeleteAction() {
        Conocimiento seleccionado = conocimientoTable.getSelectionModel().getSelectedItem();
     if (seleccionado != null){
               conocimientos.remove(seleccionado);
               }
   }
    
    public HBox getView() {
        return view;
    }


    public final ListProperty<Conocimiento> conocimientosProperty() {
		return this.conocimientos;
	}
	

	public final ObservableList<Conocimiento> getConocimientos() {
		return this.conocimientosProperty().get();
	}
	

	public final void setConocimientos(final ObservableList<Conocimiento> conocimientos) {
		this.conocimientosProperty().set(conocimientos);
	}


   private void onAddAction(TipoConocimiento tipoConocimiento) {
      ConocimientoDialog dialog = new ConocimientoDialog(tipoConocimiento);
		
		if( tipoConocimiento == TipoConocimiento.CONOCIMIENTO ) {
			Optional<Conocimiento> conocimiento = dialog.showAndWait();
			
			if( conocimiento.isPresent() ) {
				conocimientos.add(conocimiento.get());
			}
			
		} else {
			Optional<Conocimiento> idioma = dialog.showAndWait();
			
			if( idioma.isPresent() ) {
				Idioma nuevoIdioma = (Idioma) idioma.get();
				conocimientos.add(nuevoIdioma); 
			}
		}
       }
   

    }

 

    

    
    
    

