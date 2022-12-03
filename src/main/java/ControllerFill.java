import user.User;
import database.Van_db;
import coffee.Blocks_amount;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import van.Van;
import van.Van_type;
import java.io.IOException;


public class ControllerFill  {
    public static Logger logger = LogManager.getLogger(ControllerFill.class.getName());
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button button;
    @FXML
    private Label label;
    @FXML
    private TextField textField;
    @FXML
    RadioButton van1,van2,van3;
    private int goodsprice;
    @FXML
   private ChoiceBox<String> choicebox =new ChoiceBox<>();
    private String[] options={"вручну","до всього об'єму","на задану суму"};
    @FXML
    private TreeTableView<String> table = new TreeTableView<>();
    @FXML
    private TreeTableColumn<String,String> column = new TreeTableColumn<>();
    private TreeItem<String>  parent,  item,tableroot = new TreeItem<>();
    private User user;
    @FXML
    private Button logout;
    @FXML
    private Label nameLabel;

    public void initialize(User user) {
        this.user = user;
        choicebox.getItems().setAll(options);
        choicebox.setOnAction(this::choiceIsMade);
        Van_db van_db = new Van_db();
        Van van = van_db.getLast();
        parent = new TreeItem<>(van.toString());
        for(Blocks_amount b: van.getGoods()){
            item = new TreeItem<>(b.toString());
            parent.getChildren().add(item);
        }
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue()));
        tableroot.getChildren().add(parent);
        table.setRoot(tableroot);
        table.setShowRoot(false);
        logout.setVisible(true);
        this.user = user;
        nameLabel.setText(user.getLogin());
    }

    public void switchToScene1(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scene1.fxml"));
        try {
            Parent sort = loader.load();
            Scene sortScene = new Scene(sort);
            ControllerMain cont = loader.getController();
            cont.initialize("user", user);
            Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(sortScene);
            window.show();
        } catch (IOException e) {
            logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void vanIsChosen(ActionEvent event){
       choicebox.setDisable(false);
    }
    public void choiceIsMade(ActionEvent event){
        if (choicebox.getSelectionModel().getSelectedItem().equals("на задану суму")) {
            button.setDisable(true);
            label.setVisible(true);
            textField.setVisible(true);
        }
        else {
            label.setVisible(false);
            textField.setVisible(false);
            button.setDisable(false);
        }

    }
    public void buttonRelease(ActionEvent event) {
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                button.setDisable(textField.getText().length() <= 0);
            }
        });
    }
    public Van_type chosenVan(){
        if (van1.isSelected())
            return Van_type.SMALL;
        else if (van2.isSelected())
            return  Van_type.MEDIUM;
        return  Van_type.LARGE;
    }

    public void chooseButton(ActionEvent event){
        Van_type van_type=chosenVan();
        Van van = new Van(van_type);
        Van_db van_db = new Van_db();
        van_db.insert(van);
        van.setId(van_db.getID(van));
        if (choicebox.getSelectionModel().getSelectedItem().equals("вручну")) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("scene4.fxml"));
            try {
                Parent handfill = loader.load();
                Scene handfillScene = new Scene(handfill);
                ControllerScene4 cont = loader.getController();
                cont.initialize(van,user );
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(handfillScene);
                window.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        else if (choicebox.getSelectionModel().getSelectedItem().equals("до всього об'єму")) {
            van.fillRand("volume", 0);
            van_db.insertVanWGoods(van);
            switchToScene5_2(event);
        }
        else{
            try {
                goodsprice = Integer.parseInt(textField.getText());
                if ( goodsprice <90000 ){
                label.setText("Введіть більше значення!");
                textField.setStyle("-fx-border-color: red; -fx-border-width: 1px;-fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                }
                else{
                    van.fillRand("price",goodsprice);
                    van_db.insertVanWGoods(van);
                    switchToScene5_2(event);
                }
            } catch (NumberFormatException ne){
                textField.setStyle("-fx-border-color: red; -fx-border-width: 1px;-fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                label.setText("Введіть суму!");
                logger.warn("Неправильний формат вводу!");
            }catch(Exception e){
                logger.error("Ой, щось не так.. {}", e.getMessage());
            }
        }

    }

    public void switchToScene5_2(ActionEvent event) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("scene5_2.fxml"));
            try {
                Parent sort = loader.load();
                Scene sortScene = new Scene(sort);
                ControllerFill cont = loader.getController();
                cont.initialize(user);
                Stage window =(Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(sortScene);
                window.show();
            } catch (IOException e) {
                logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
                throw new RuntimeException(e);
            }
    }

    public void goBack(ActionEvent event) {
        Van_db van_db = new Van_db();
        Van van = van_db.getLast();
        van_db.deleteFromVan(van.getId());
        switchToScene1(event);
    }

    public void logoutApp(ActionEvent event) {
        Van_db van_db = new Van_db();
        Van van = van_db.getLast();
        van_db.deleteFromVan(van.getId());
        ControllerMain cont = new ControllerMain();
        cont.logoutApp(event);
    }
}
