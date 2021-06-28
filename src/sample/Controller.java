
package sample;

// javafx libraries
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

// java libraries
import java.net.URL;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

// controller initialization interface
public class Controller implements Initializable {

    // declaring javafx components as defined in .fxml
    public TextField purpose_text;
    public TextField name_text;
    public TextField phone_text;
    public TextField id_text;
    public TableView main_table;
    public TableColumn purpose_column;
    public TableColumn name_column;
    public TableColumn phone_column;
    public TableColumn id_column;
    public Button create_btn;
    public Button update_btn;
    public Button delete_btn;
    public TextField get_text;
    public Button get_button;
    public Button revert_button;
    private Properties user;
    private Properties password;

    // establishing initial connection with MySQL server
    public Connection getConnection(){
        Connection connect_object;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect_object = DriverManager.getConnection("jdbc:mysql://localhost:3306/CRUDAPPSCHEMA","root", "" );
            return connect_object;
        }
        catch(Exception e){
            System.out.println("Error:" + e.getMessage());
            return null;
        }
    }

    // implementing update from remote DB to Desktop GUI application
    public ObservableList<Visitor> getVisitors(){

        // Oracle documentation to process SQL statements with JDBC: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
        ObservableList<Visitor> visitor = FXCollections.observableArrayList();
        Connection connect = getConnection();
        String sql_query = "SELECT * FROM visitor";

        try(Statement statement = connect.createStatement()){
            ResultSet result_set = statement.executeQuery(sql_query);
            // iterating through resultant Vehicle objects from remote DB
            while(result_set.next()){
                Visitor visitors_queried = new Visitor(result_set.getString("purpose"), result_set.getString("name"), result_set.getInt("phone"), result_set.getInt("id"));
                visitor.add(visitors_queried);
            }
        }
        catch(Exception e){
            System.out.println("Error:" + e.getMessage());
        }
        return visitor;
    }

    // implementing update from remote DB to Desktop GUI application onyl for Get Button
    public ObservableList<Visitor> getVisitorForGetButton(){

        // Oracle documentation to process SQL statements with JDBC: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html
        ObservableList<Visitor> visitors = FXCollections.observableArrayList();
        Connection connect = getConnection();
        String sql_query = "SELECT * FROM visitor WHERE id = " + get_text.getText() + "";

        try(Statement statement = connect.createStatement()){
            ResultSet result_set = statement.executeQuery(sql_query);
            // iterating through resultant Vehicle objects from remote DB
            while(result_set.next()){
                Visitor visitors_queried = new Visitor(result_set.getString("purpose"), result_set.getString("name"), result_set.getInt("phone"), result_set.getInt("id"));
                visitors.add(visitors_queried);
            }
        }
        catch(Exception e){
            System.out.println("Error:" + e.getMessage());
        }
        return visitors;
    }

    // updating data from MySQL DataBase into Desktop GUI application
    public void pushVisitorsOntoTableForGetButton(){

        // retrieving data from remote DB
        ObservableList<Visitor> visitors = getVisitorForGetButton();

        // updating DB into GUI application
        purpose_column.setCellValueFactory(new PropertyValueFactory<>("purpose"));
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        phone_column.setCellValueFactory(new PropertyValueFactory<>("phone"));
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));

        main_table.setItems(visitors);
    }

    // updating data from MySQL DataBase into Desktop GUI application
    public void pushVisitorsOntoTable(){

        // retrieving data from remote DB
        ObservableList<Visitor> visitors = getVisitors();

        // updating DB into GUI application
        purpose_column.setCellValueFactory(new PropertyValueFactory<>("purpose"));
        name_column.setCellValueFactory(new PropertyValueFactory<>("name"));
        phone_column.setCellValueFactory(new PropertyValueFactory<>("phone"));
        id_column.setCellValueFactory(new PropertyValueFactory<>("id"));

        main_table.setItems(visitors);
    }

    // creating object based on user input
    public void createVisitor() throws SQLException {

        if(purpose_text.getText().equals("") || name_text.getText().equals("") || phone_text.getText().equals("") || id_text.getText().equals("")) {
            // testing for invalid user input by means of Dialog
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all text fields!", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();
        }
        else{
            // Creating object based on user input
            String sql_query = "INSERT INTO visitor VALUES(" + purpose_text.getText() + "," + name_text.getText() + ",'" + phone_text.getText() + "','" + id_text.getText() + "')";
            establishSQLConnection(sql_query);
            pushVisitorsOntoTable();
        }
    }

    // updating object based on ID
    public void updateVisitors() throws SQLException {

        if(purpose_text.getText().equals("") || name_text.getText().equals("") || phone_text.getText().equals("") || id_text.getText().equals("")) {
            // testing for invalid user input by means of Dialog
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill all text fields!", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();

        }
        else {
            // updating object based on id
            String sql_query = "UPDATE visitor SET purpose = " + purpose_text.getText() + ",name = '" + name_text.getText() + "', phone = '" + phone_text.getText() + "' WHERE id = " + id_text.getText() + "";
            establishSQLConnection(sql_query);
            pushVisitorsOntoTable();
        }
    }

    // deleting object based on ID
    private void deleteVisitor() throws SQLException {

        // testing for invalid user input by means of Dialog
        if(purpose_text.getText().equals("") || name_text.getText().equals("") || phone_text.getText().equals("") || id_text.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a row in the table or add an ID in the text field to delete!", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();
        }
        else{
            // deleting row based on ID since it is the primary key
            String sql_query = "DELETE FROM visitor WHERE id = " + id_text.getText() + "";
            establishSQLConnection(sql_query);
            pushVisitorsOntoTable();
        }
    }

    // getting objects based on ID
    public void getVisitorByID() throws SQLException{
        // testing for invalid user input by means of Dialog
        if(get_text.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter an ID to retrieve corresponding Visitor phone number!", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();
        }
        else{
            String sql_query = "SELECT * FROM visitor WHERE id = " + get_text.getText() + "";
            establishSQLConnection(sql_query);
            pushVisitorsOntoTableForGetButton();
        }
    }

    // using SQL statement to make relevant query to update table accordingly
    // param: sql_query:String
    private void establishSQLConnection(String sql_query) throws SQLException {

        Connection connect_object = getConnection();

        try(Statement statement = connect_object.createStatement()){
            statement.executeUpdate(sql_query);
        }
        catch (Exception e){

        }
    }

    // event handler for button press
    // param: actionEvent: ActionEvent
    public void buttonPressed(javafx.event.ActionEvent actionEvent) throws SQLException {

        // calling relevant methods based on event source
        if (actionEvent.getSource() == create_btn ){
            createVisitor();
        }
        else if(actionEvent.getSource() == update_btn){
            updateVisitors();
        }

        else if(actionEvent.getSource() == delete_btn){
            deleteVisitor();
        }

        else if (actionEvent.getSource() == get_button){
            getVisitorByID();
        }

        else if(actionEvent.getSource() == revert_button){
            pushVisitorsOntoTable();
            get_text.clear();
        }
    }

    // event handler for mouse click on table cell
    // param: mouseEvent: MouseEvent
    public void mouseClicked(MouseEvent mouseEvent) {

        Visitor visitor = (Visitor) main_table.getSelectionModel().getSelectedItem();

        // extracting data from selected row to be displayed into text fields
        purpose_text.setText(visitor.getPurpose());
        name_text.setText(visitor.getName());
        phone_text.setText(String.valueOf(visitor.getPhone()));
        id_text.setText(String.valueOf(visitor.getId()));
    }

    // delegate function for Initializable class
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pushVisitorsOntoTable();
    }


}
