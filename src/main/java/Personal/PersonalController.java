package Personal;

import java.io.IOException;

import Datos.Nacionalidad;
import Datos.Personal;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PersonalController implements Initializable {
	private final String paisesSource = "files/paises.csv";
	private final String nacionalidadesSource = "files/nacionalidades.csv";
   	
	   @FXML
    private GridPane view;

    @FXML
    private TextField dniText;

    @FXML
    private TextField nombreText;

    @FXML
    private TextField apellidosText;

    @FXML
    private DatePicker fechaNacimientoPicker;

    @FXML
    private TextArea direccionText;

    @FXML
    private TextField codigoPostalText;

    @FXML
    private TextField localidadText;

    @FXML
    private ComboBox<String> paisComboBox;

    @FXML
    private ListView<Nacionalidad> nacionalidadList;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

	
	
       private ObjectProperty<Personal> personal = new SimpleObjectProperty<Personal>();
	

	private ListProperty<String> paisesList = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
	
	
	private ArrayList<String> nacionalidadesList = new ArrayList<>();
	
	public PersonalController() throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/PersonalView.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
           
            nacionalidadesCSV();
                paisComboBox.itemsProperty().bind(paisesList);
                paisesCSV();
                
                personal.addListener((o, ol, nv) -> binds(ol,nv));
                personal.set(new Personal());
		addButton.setOnAction( evt -> onAddAction() );
		deleteButton.setOnAction( evt -> onRemoveAction() );

	}

	private void onRemoveAction() {
               Nacionalidad seleccionado = nacionalidadList.getSelectionModel().getSelectedItem();
               if (seleccionado != null){
               getPersonal().getNacionaliades().remove(seleccionado);
               }
	}
        private void paisesCSV(){
               FileInputStream file = null;
		InputStreamReader in = null;
		BufferedReader br = null;
		
		try {
			
			file = new FileInputStream(paisesSource);
			in = new InputStreamReader(file, StandardCharsets.ISO_8859_1);
			br = new BufferedReader(in);
			
			String line = null;
			
			while( (line = br.readLine()) != null ) {
				
				String str = line;
				paisesList.add(str);
			}
			
		} catch(IOException e ) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if( br != null ) {
					br.close();
				}
				
				if( in != null ) {
					in.close();
				}
				
				if( file != null ) {
					file.close();
				}
				
			} catch (IOException e) {
			}
		}
               
        }
        private void nacionalidadesCSV(){
             FileInputStream file = null;
		InputStreamReader in = null;
		BufferedReader br = null;
		
		try {
			
			file = new FileInputStream(nacionalidadesSource);
			in = new InputStreamReader(file, StandardCharsets.ISO_8859_1);
			br = new BufferedReader(in);
			
			String line = null;
			
			while( (line = br.readLine()) != null ) {
				
				String str = line;
				nacionalidadesList.add(str);
			}
			
		} catch(IOException e ) {
			e.printStackTrace();
			
		} finally {
			
			try {
				if( br != null ) {
					br.close();
				}
				
				if( in != null ) {
					in.close();
				}
				
				if( file != null ) {
					file.close();
				}
				
			} catch (IOException e) {
			}
		}	
        }

	private void onAddAction() {
            ChoiceDialog<String> dialog= new ChoiceDialog<String>();
            dialog.setTitle("Nueva Nacionalidad");
dialog.setHeaderText("Añadir nacionalidad");
dialog.setContentText("Seleccione una nacionalidad:");
              
 dialog.getItems().addAll(nacionalidadesList);
Optional<String> result = dialog.showAndWait();
if(result.isPresent()){
getPersonal().nacionaliadesProperty().add(new Nacionalidad(result.get()));
}
	}

     
	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}
	

	public final Personal getPersonal() {
		return this.personalProperty().get();
	}
	

	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
	}
	
	public GridPane getView() {
		return view;
	}

    private void binds(Personal ol, Personal nv) {
        if (ol !=null){
                        nombreText.textProperty().unbindBidirectional(ol.nombreProperty());
			apellidosText.textProperty().unbindBidirectional(ol.apellidosProperty());
			codigoPostalText.textProperty().unbindBidirectional(ol.codigoPostalProperty());
			localidadText.textProperty().unbindBidirectional(ol.localidadProperty());
			fechaNacimientoPicker.valueProperty().unbindBidirectional(ol.fechaNacimientoProperty());
			dniText.textProperty().unbindBidirectional(ol.identificacionProperty());
			direccionText.textProperty().unbindBidirectional(ol.direccionProperty());
        }
                Bindings.bindBidirectional(nombreText.textProperty(), nv.nombreProperty());
		Bindings.bindBidirectional(apellidosText.textProperty(), nv.apellidosProperty());
		Bindings.bindBidirectional(codigoPostalText.textProperty(), nv.codigoPostalProperty());
		Bindings.bindBidirectional(localidadText.textProperty(), nv.localidadProperty());
		Bindings.bindBidirectional(fechaNacimientoPicker.valueProperty(), nv.fechaNacimientoProperty());
		Bindings.bindBidirectional(dniText.textProperty(), nv.identificacionProperty());
		Bindings.bindBidirectional(direccionText.textProperty(), nv.direccionProperty());
		Bindings.bindBidirectional(paisComboBox.valueProperty(), nv.paisProperty());
		
		nacionalidadList.itemsProperty().bind(nv.nacionaliadesProperty());
    }

}