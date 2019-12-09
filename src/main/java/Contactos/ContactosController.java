
package Contactos;

import Datos.Contacto;
import Datos.Email;
import Datos.Telefono;
import Datos.Web;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;



public class ContactosController implements Initializable {
       @FXML
    private VBox view;

    @FXML
    private TableView<Telefono> telefoneTable;


    @FXML
    private TableColumn<Telefono, Telefono.TipoTelefono> tipoColumn;
    
    @FXML
    private Button addTelefoneButton;

    @FXML
    private Button deleteTelefoneButton;

    @FXML
    private TableView<Email> emailTable;

   
      
    @FXML
    private Button addEmailButton;

    @FXML
    private Button deleteEmailButton;

    @FXML
    private TableView<Web> webTable;

    
    @FXML
    private Button addWebButton;

    @FXML
    private Button deleteWebButton;
    
    private ObjectProperty<Datos.Contacto> contactos = new SimpleObjectProperty<Contacto>();
    public ContactosController() {
        try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/contactoView.fxml"));
		loader.setController(this);
		loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Override
 public void initialize(URL url, ResourceBundle rb) {
     
     tipoColumn.setCellFactory(ComboBoxTableCell.forTableColumn(Telefono.getTiposTelefono()));
      
     contactos.addListener( (o, ol, nv) -> binds(ol, nv));
		contactos.set(new Contacto());
     
       addTelefoneButton.setOnAction( evt -> onAddTelefonoAction() );
        addEmailButton.setOnAction( evt -> onAddEmailAction() );
        addWebButton.setOnAction( evt -> onAddWebAction() );
        deleteTelefoneButton.setOnAction( evt -> onDelTelefonoAction() );
        deleteEmailButton.setOnAction( evt -> onDelEmailAction() );
        deleteWebButton.setOnAction( evt -> onDelWebAction() );
    }
    public VBox getView() {
        return view;
    }

    public void setView(VBox view) {
        this.view = view;
    }

    private void onAddTelefonoAction() {
        //TelefonoDialog dialog = new TelefonoDialog();
        //Optional<Telefono> tele= dialog.showAndWait();
       TelefonoDialog dialog = new TelefonoDialog();


Optional<Telefono> result = dialog.showAndWait();
if (result.isPresent() && result.get() !=null){
     getContactos().getTelefonos().add(result.get());
}

    }

    private void onAddEmailAction() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Nuevo email");
        dialog.setHeaderText("Crear una nueva dirección de correo");
        dialog.setContentText("Email:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()&& result.get() !=null){
       getContactos().getEmails().add(new Email(result.get()));
}
    }

    private void onAddWebAction() {
         TextInputDialog dialog = new TextInputDialog("http://");
        dialog.setTitle("Nueva web");
        dialog.setHeaderText("Crear una nueva dirección web");
        dialog.setContentText("URL:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && result.get() !=null){
       getContactos().getWebs().add(new Web(result.get()));
} 
    }

    private void binds(Contacto ol, Contacto nv) {
        if(ol !=null){
       telefoneTable.itemsProperty().unbindBidirectional(ol.telefonosProperty());
       emailTable.itemsProperty().unbindBidirectional(ol.emailsProperty());
       webTable.itemsProperty().unbindBidirectional(ol.websProperty());
        }
        Bindings.bindBidirectional(telefoneTable.itemsProperty(), nv.telefonosProperty());
	Bindings.bindBidirectional(emailTable.itemsProperty(), nv.emailsProperty());
	Bindings.bindBidirectional(webTable.itemsProperty(), nv.websProperty());
    }

    
   public final ObjectProperty<Contacto> contactosProperty() {
		return this.contactos;
	}
	

	public final Contacto getContactos() {
		return this.contactosProperty().get();
	}
	

	public final void setContactos(final Contacto contactos) {
		this.contactosProperty().set(contactos);
	}

    private void onDelWebAction() {
        Web result = webTable.getSelectionModel().getSelectedItem();
	if( result != null  ) {
		getContactos().getWebs().remove(result);
	}
    }

    private void onDelTelefonoAction() {
       Telefono result = telefoneTable.getSelectionModel().getSelectedItem();
				if( result != null  ) {
					getContactos().getTelefonos().remove(result);
				}
    }

    private void onDelEmailAction() {
        Email result = emailTable.getSelectionModel().getSelectedItem();
	if( result != null) {
		getContactos().getEmails().remove(result);
	}
    }
    
    
}
