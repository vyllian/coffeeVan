import user.User;
import database.Van_db;
import coffee.Blocks_amount;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import van.Van;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class ControllerMain  {
    public static Logger logger = LogManager.getLogger(ControllerMain.class.getName());
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Parent rootAnch;
    @FXML
    private MenuItem sortOption, findOption;
    @FXML
    private Button deleteVanButton,fillVanButton;
    @FXML
    private Button login,logout;
    @FXML
    private Label nameLabel, personIcon;
    @FXML
    private TreeTableView<String> table = new TreeTableView<>();
    @FXML
    private TreeTableColumn<String,String> column = new TreeTableColumn<>();
    private String mode;
    private User user;

    TreeItem<String>  parent,  item,tableroot = new TreeItem<>();

    public void switchToScene1_1(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scene1_1.fxml"));
        try {
            Parent sort = loader.load();
            Scene sortScene = new Scene(sort);
            ControllerDelete cont = loader.getController();
            cont.initialize(user);
            Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(sortScene);
            window.show();
        } catch (IOException e) {
            logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public void switchToScene3(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scene3.fxml"));
        try {
            Parent sort = loader.load();
            Scene sortScene = new Scene(sort);
            ControllerFill cont = loader.getController();
            cont.initialize(user);
            Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(sortScene);
            window.show();
        } catch (IOException e) {
            logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void initialize(String mode, User user) {
        this.mode=mode;
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
        if (this.mode.equals("view")){
            fillVanButton.setDisable(true);
            deleteVanButton.setDisable(true);
            login.setVisible(true);
            personIcon.setVisible(false);
        }
        else {
            logout.setVisible(true);
            this.user = user;
            nameLabel.setText(user.getLogin());
        }
    }

    public void sorting(ActionEvent event) {
        TreeTableView.TreeTableViewSelectionModel<String> sm = table.getSelectionModel();
        TreeItem<String> selectedItem = sm.getModelItem(sm.getSelectedIndex());
        Van_db van_db = new Van_db();
        Van van = van_db.get(Integer.parseInt(selectedItem.getValue().replaceAll("(\\d+).+", "$1")));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scene2.fxml"));
        try {
            Parent sort = loader.load();
            Scene sortScene = new Scene(sort);
            ControllerScene2_6 cont = loader.getController();
            cont.initialize(van, this.mode, user);
            Stage window =(Stage) rootAnch.getScene().getWindow();
            window.setScene(sortScene);
            window.show();
        } catch (IOException e) {
            logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void finding(ActionEvent event) {
        TreeTableView.TreeTableViewSelectionModel<String> sm = table.getSelectionModel();
        TreeItem<String> selectedItem = sm.getModelItem(sm.getSelectedIndex());
        Van_db van_db = new Van_db();
        Van van = van_db.get(Integer.parseInt(selectedItem.getValue().replaceAll("(\\d+).+", "$1")));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scene6.fxml"));
        try {
            Parent find = loader.load();
            Scene findScene = new Scene(find);
            ControllerScene2_6 cont = loader.getController();
            cont.initialize(van, this.mode, user);
            Stage window =(Stage) rootAnch.getScene().getWindow();
            window.setScene(findScene);
            window.show();
        } catch (IOException e) {
            logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void releaseMenu(MouseEvent mouseEvent) {
        TreeTableView.TreeTableViewSelectionModel<String> sm = table.getSelectionModel();
        TreeItem<String> selectedItem = sm.getModelItem(sm.getSelectedIndex());
        sortOption.setDisable(selectedItem == null);
        findOption.setDisable(selectedItem == null);
    }

    public void switchToScene0_1(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene0_1.fxml")));
            stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void logoutApp(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene0.fxml")));
            stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
