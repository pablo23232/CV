package Main;

import Conocimientos.ConocimientosController;
import Contactos.ContactosController;
import Experiencia.ExperienciaController;
import Formacion.FormacionController;
import Jaxb.JAXB;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Datos.CV;
import Datos.Contacto;
import Datos.Personal;
import Personal.PersonalController;
import java.io.File;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {

	PersonalController personalController;
        ConocimientosController conocimientosController;
        ExperienciaController experienciaController;
        FormacionController formacionController;
        ContactosController contactosController;
    @FXML
    private VBox view;

    @FXML
    private MenuBar menu;

    @FXML
    private MenuItem salirMenu;

    @FXML
    private MenuItem nuevoMenu;

    @FXML
    private MenuItem abrirMenu;

    @FXML
    private MenuItem guardarMenu;

    @FXML
    private MenuItem guardarComoMenu;

    @FXML
    private MenuItem acercaDeMenu;

    @FXML
    private TabPane tabpane;
    
    @FXML
    private Tab personalTab;

   
    private ObjectProperty<CV> cv = new SimpleObjectProperty<CV>();
    
    public MainController() throws IOException{
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
		loader.setController(this);
		loader.load();
		
		
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			personalController = new PersonalController();
                        conocimientosController= new ConocimientosController();
                        contactosController = new ContactosController();
                        formacionController= new FormacionController();
                        experienciaController= new ExperienciaController();
			tabpane.getTabs().get(0).setContent(personalController.getView());
                        tabpane.getTabs().get(1).setContent(contactosController.getView());
                        tabpane.getTabs().get(2).setContent(formacionController.getView());
                        tabpane.getTabs().get(3).setContent(experienciaController.getView());
                        tabpane.getTabs().get(4).setContent(conocimientosController.getView());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                	cv.set(new CV());
		cv.get().setPersonal(new Personal());
		cv.get().setContacto(new Contacto());
		loadData();
		abrirMenu.setOnAction( e -> onOpen() );
                guardarComoMenu.setOnAction( evt -> onSaveAs());
	}
        
         private void onOpen() {
             FileChooser browser = new FileChooser();
		browser.getExtensionFilters().add(new ExtensionFilter("CV", "*.cv"));
		browser.setTitle("Abrir CV");
		browser.setInitialDirectory(new File(System.getProperty("user.dir") ));
		
		File file = browser.showOpenDialog(getView().getScene().getWindow());
		
		if( file != null ) {
			
			// Ahora podemos empezar a cargar el archivo
			// Usamos el JAXB para leer el XML
			
			try {
				
				CV myCV = JAXB.load(CV.class, file);
				
				// Ahora lo cargamos en nuestro ObjectProperty
				cv.set(myCV);
				loadData();
				
			} catch (Exception e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
         }
         private void onSaveAs(){
                FileChooser buscador = new FileChooser();
		
		buscador.setTitle("Guardar CV como....");
		buscador.getExtensionFilters().add(new ExtensionFilter("CV", "*.cv"));
		buscador.setInitialFileName("CV1.cv");
		buscador.setInitialDirectory(new File(System.getProperty("user.dir") ));
		
		File file = buscador.showSaveDialog(getView().getScene().getWindow());
		
		if( file != null ) {
			
			try {
				JAXB.save(cv.get(), file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
         }
         
         private void loadData() {
      	Personal personal = cv.get().getPersonal();
		personalController.setPersonal(personal);
		Contacto contacto = cv.get().getContacto();
		contactosController.setContactos(contacto);
                formacionController.titulosProperty().bindBidirectional(cv.get().formacionProperty());
		experienciaController.listaExperienciaProperty().bindBidirectional(cv.get().experienciaProperty());
		conocimientosController.conocimientosProperty().bindBidirectional(cv.get().conocimientosProperty());
    }
         
	public VBox getView() {
		return view;
	}

    
	

   

}
