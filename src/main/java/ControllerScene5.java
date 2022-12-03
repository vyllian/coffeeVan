import user.User;
import coffee.Coffee_block;
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
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import van.Van;
import java.io.IOException;

public class ControllerScene5 {
    public static Logger logger = LogManager.getLogger(ControllerScene5.class.getName());

    @FXML
    private Button YESbutton;
    private Van chosen_van;
    TreeItem<String> parent,  item,tableroot = new TreeItem<>();
    @FXML
    private TreeTableView<String> table = new TreeTableView<>();
    @FXML
    private TreeTableColumn<String,String> column = new TreeTableColumn<>();
    private User user;
    @FXML
    Button logout;
    @FXML
    private Label nameLabel;

    public void initialize(Van van, Coffee_block coffee_block, User user){
        this.user = user;
        chosen_van = van;
        parent = new TreeItem<>(chosen_van.toString());
        for(Blocks_amount b: chosen_van.getGoods()){
            item = new TreeItem<>(b.toString());
            parent.getChildren().add(item);
        }
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue()));
        tableroot.getChildren().add(parent);
        table.setRoot(tableroot);
        table.setShowRoot(false);
        if (chosen_van.isFull_by_volume(coffee_block)|| chosen_van.isFull_by_weight(coffee_block))
            YESbutton.setDisable(true);
        this.user = user;
        nameLabel.setText(user.getLogin());
    }

    public void noIsPressed(ActionEvent event){
        Van_db van_db= new Van_db();
        van_db.insertVanWGoods(chosen_van);
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

    public void yesIsPressed(ActionEvent event){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scene4.fxml"));
        try {
            Parent handfill = loader.load();
            Scene handfillScene = new Scene(handfill);
            ControllerScene4 cont = loader.getController();
            cont.initialize(chosen_van, user);
            Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(handfillScene);
            window.show();

        } catch (IOException e) {
            logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
