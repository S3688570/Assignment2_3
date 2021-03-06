package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * FMXL MainController. (For the initialization and control of the MiniNet GUI).
 *
 * @author cgalea
 */
public class MainController implements Initializable {

    private final Person currentTask = new Person();

    private final ObservableList<Person> people = FXCollections.observableArrayList();

    @FXML
    private TableView<Person> tableView;
    @FXML
    private TableColumn<Person, String> nameColumn;
    @FXML
    private TableColumn<Person, String> imageColumn;
    @FXML
    private TableColumn<Person, String> statusColumn;
    @FXML
    private TableColumn<Person, String> genderColumn;
    @FXML
    private TableColumn<Person, String> ageColumn;
    @FXML
    private TableColumn<Person, String> stateColumn;
    @FXML
    private Image image1;

    @FXML
    private Label outputLabel;

    @FXML
    private TableView<Person> tasksTable;

    @FXML
    Button btnPrintProfile = new Button("Print Profile");


    @FXML
    private Button relationshipBtn;

    @FXML
    public Label myLabel;

    @FXML
    ObservableList<String> genderList = FXCollections.observableArrayList("F", "M");
    ObservableList<String> stateList = FXCollections.observableArrayList("ACT", "QLD", "NSW", "NT", "SA", "TAS", "VIC", "WA");

    // These instance variables are used to create a new profile
    @FXML
    private TextField nameField;
    @FXML
    private TextField imageField;
    @FXML
    private TextField statusField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField ageField;

    @FXML
    public TextField getAgeField() {
        return ageField;
    }

    @FXML
    private TextField stateField;

    //These items are for the Choice Box (Print Profile)
    @FXML
    private ComboBox comboBox;
    @FXML
    private Label comboBoxLabel;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private ComboBox<String> stateComboBox;

    //Reference to the main application
    private MiniNet mininet;

    //Reference to the Array class
    private TextDatabase array;

    public Button getBtnPrintProfile() {
        return btnPrintProfile;
    }

    /**
     * The contructor called before the initialize() method
     */
    public MainController() {
    }

    /**
     * Initializes the MainController class. This method is automatically called
     * after the fxml file has been loaded.
     *
     * @param location
     * @param resources
     */
    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Initializes the comboboxes for selecting gender and state
        genderComboBox.getItems().addAll("F", "M");
        stateComboBox.getItems().addAll("ACT", "QLD", "NSW", "NT", "SA", "TAS", "VIC", "WA");

        //Initializes the people table containing 6 columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("image"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("status"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("gender"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("age"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("state"));

        /**
         * This method loads the relationship data
         */
        TextDatabase array = new TextDatabase();
        try {
            tableView.setItems(array.getPeople());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Database file does not exist!");
            alert.setContentText("Please try the database file.");

            alert.showAndWait();
        }

        //Update table to allow for name fields to be editable
        tableView.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        imageColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        statusColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        genderColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ageColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        stateColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        //This will allow the user to select multiple rows at once
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * This method will allow the user to double click  on a cell and update
     * the data
     */
    public void changeNameCellEvent(TableColumn.CellEditEvent editedCell) {
        Person personSelected = tableView.getSelectionModel().getSelectedItem();
        personSelected.setName(editedCell.getNewValue().toString());
    }

    /**
     * This method will remove the selected row(s) from the table
     */
    public void deleteButtonPushed() {
        ObservableList<Person> selectedRows, allPeople;
        allPeople = tableView.getItems();

        //this gives us rows that are selected
        selectedRows = tableView.getSelectionModel().getSelectedItems();

        for (Person person : selectedRows) {
            allPeople.remove(person);
        }
    }

    /**
     * This method transfers information from the gender and state comboboxes to the corresponding text fields
     */
    public void comboChanged(ActionEvent actionEvent) {
        genderField.setText(genderComboBox.getValue());
        stateField.setText(stateComboBox.getValue());
    }

    /**
     * This method will create a new profile  and add it to the table
     */
    public void addNewProfile() {
        Person newProfile = new Person(nameField.getText(), imageField.getText(), statusField.getText(), genderField.getText(), ageField.getText(), stateField.getText());

        //Get all items from table as a list, then add the new profile to the list
        tableView.getItems().add(newProfile);
    }

    /**
     * This method will create a new GUI window for relationships
     */
    public void databaseScreenButtonPushed(ActionEvent event) throws Exception {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("makeRelationship.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene tableViewScene = new Scene(tableViewParent);
        window.hide();
        window.setScene(tableViewScene);
        window.show();
    }
}
