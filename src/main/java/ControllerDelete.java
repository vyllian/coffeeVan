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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import van.Van;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class ControllerDelete{
    public static Logger logger = LogManager.getLogger(ControllerDelete.class.getName());

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label label;
    @FXML
    private TextField fullnessField;
    @FXML
    private Button deleteButton1_3, delButton;
    @FXML
    private TreeTableView<String> table = new TreeTableView<>();
    @FXML
    private TreeTableColumn<String,String> column = new TreeTableColumn<>();

    TreeItem<String>  parent,  item,tableroot = new TreeItem<>();
    private int fullness;
    private User user;
    @FXML
    private Button logout;
    @FXML
    private Label nameLabel;
    private FXMLLoader loader = new FXMLLoader();
    public void backButton(ActionEvent event){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scene1_1.fxml"));
        try {
            Parent sort = loader.load();
            Scene sortScene = new Scene(sort);
            ControllerDelete cont = loader.getController();
            cont.initialize( user);
            Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(sortScene);
            window.show();
        } catch (IOException e) {
            logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
            throw new RuntimeException(e);
        }
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
    public void chooseButton(ActionEvent event) {
        loader.setLocation(getClass().getResource("scene1_2.fxml"));
        try {
            Parent sort = loader.load();
            Scene sortScene = new Scene(sort);
            ControllerDelete cont = loader.getController();
            cont.initialize(user);
            Stage window =(Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(sortScene);
            window.show();
        } catch (IOException e) {
            logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void fullnessButton(ActionEvent event) {
        loader.setLocation(getClass().getResource("scene1_3.fxml"));
        try {
            Parent sort = loader.load();
            Scene sortScene = new Scene(sort);
            ControllerDelete cont = loader.getController();
            cont.initialize(user);
            Stage window =(Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(sortScene);
            window.show();
        } catch (IOException e) {
            logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void delBYChoiceReleaseButton(MouseEvent event){
        TreeTableView.TreeTableViewSelectionModel<String> sm = table.getSelectionModel();
        try {
            delButton.setDisable(Integer.parseInt(sm.getModelItem(sm.getSelectedIndex()).getValue().replaceAll("(\\d+).+", "$1")) <= 0);
        }catch (NumberFormatException e){
            logger.warn("Не можна змінити вміст фургону.");
            delButton.setDisable(true);
        }catch (NullPointerException e){
            logger.warn("Ну що це! Виберіть фургон для видалення.");
        }
    }
    public void deleteByChoiceButton(ActionEvent event){
        TreeTableView.TreeTableViewSelectionModel<String> sm = table.getSelectionModel();
        int rowIndex = sm.getSelectedIndex();
        TreeItem<String> selectedItem = sm.getModelItem(rowIndex);
        TreeItem<String> parent = selectedItem.getParent();
        if (parent != null)
            parent.getChildren().remove(selectedItem);
        else
            table.setRoot(null);
        Van_db van_db = new Van_db();
        van_db.delete(Integer.parseInt(selectedItem.getValue().replaceAll("(\\d+).+", "$1")));
    }

    public void deleteByFullnessButton(ActionEvent event){
        Van_db van_db = new Van_db();
        van_db.deleteByFullness(fullness);
        switchToScene1(event);
    }

    public void delByFullButtonRelease(ActionEvent event) {
        fullnessField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                try {
                    fullness = Integer.parseInt(fullnessField.getText());
                    if (fullness <= 0 || fullness > 100) {
                        label.setText("Введіть значення\nвід 1 до 100:");
                        fullnessField.setStyle("-fx-border-color: red; -fx-border-width: 1px;-fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                        deleteButton1_3.setDisable(true);
                    } else {
                        deleteButton1_3.setDisable(fullnessField.getText().length() == 0);
                        fullnessField.setStyle("-fx-border-color: none; -fx-border-width: 1px;-fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                    }
                } catch (NumberFormatException ne) {
                    fullnessField.setStyle("-fx-border-color: red; -fx-border-width: 1px;-fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                    deleteButton1_3.setDisable(true);
                }catch(Exception e){
                logger.error("Ой, щось не так.. {}", e.getMessage());
                }
            }
        });
    }


    public void initialize(User user) {
        this.user = user;
        Van_db van_db = new Van_db();
        ArrayList<Van> van_list = van_db.getAll();
        if (van_list.size()==0) return;
        for (Van v: van_list){
            parent = new TreeItem<>(v.toString());
            for(Blocks_amount b: v.getGoods()){
                item = new TreeItem<>(b.toString());
                parent.getChildren().add(item);
            }
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue()));
            tableroot.getChildren().add(parent);
        }
        table.setRoot(tableroot);
        table.setShowRoot(false);
        logout.setVisible(true);
        this.user = user;
        nameLabel.setText(user.getLogin());
    }

    public void logoutApp(ActionEvent event) {
        ControllerMain cont = new ControllerMain();
        cont.logoutApp(event);
    }

}
