import user.User;
import coffee.*;
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

import static coffee.Coffee_block_Volume.*;

public class ControllerScene4 {
    public static Logger logger = LogManager.getLogger(ControllerScene4.class.getName());
    @FXML
    private   ChoiceBox<Coffee_brand> cb_brand =new ChoiceBox<>();
    @FXML
    private ChoiceBox<Coffee_type> cb_type =new ChoiceBox<>();
    @FXML
    private ChoiceBox<Coffee_packing> cb_pack =new ChoiceBox<>();
    @FXML
    private ChoiceBox<Coffee_quality> cb_qual =new ChoiceBox<>();
    @FXML
    private ChoiceBox<Coffee_block_Volume> cb_block =new ChoiceBox<>();
    @FXML
    private Button button;
    private Van chosen_van;
    private User user;
    @FXML
    private Label nameLabel;
    @FXML
    private TreeTableView<String> table = new TreeTableView<>();
    @FXML
    private TreeTableColumn<String,String> column = new TreeTableColumn<>();

    public void initialize(Van van, User user) {
        this.user=user;
        chosen_van=van;
        cb_brand.getItems().addAll(Coffee_brand.values());
        cb_brand.setOnAction(this::buttonRelease);
        cb_type.getItems().addAll(Coffee_type.values());
        cb_type.setOnAction(this::buttonRelease);
        cb_pack.getItems().addAll(Coffee_packing.values());
        cb_pack.setOnAction(this::buttonRelease);
        cb_qual.getItems().addAll(Coffee_quality.values());
        cb_qual.setOnAction(this::buttonRelease);

        if (chosen_van.volumeLeft() < LARGE_BLOCK.getCoffee_block_Volume() && chosen_van.volumeLeft()>= MEDIUM_BLOCK.getCoffee_block_Volume())
            cb_block.getItems().addAll(SMALL_BLOCK,MEDIUM_BLOCK);
        else if (chosen_van.volumeLeft()< MEDIUM_BLOCK.getCoffee_block_Volume())
            cb_block.getItems().addAll(SMALL_BLOCK);
        else
            cb_block.getItems().addAll(Coffee_block_Volume.values());
        cb_block.setOnAction(this::buttonRelease);
        this.user = user;
        nameLabel.setText(user.getLogin());
    }

    public void buttonRelease(ActionEvent event){
        if (cb_brand.getValue()!=null && cb_type.getValue()!=null
                && cb_pack.getValue()!=null && cb_qual.getValue()!=null && cb_block.getValue()!= null)
            button.setDisable(false);
    }

    public void addCoffeeButton(ActionEvent event){
        Coffee coffee = new Coffee(cb_brand.getValue().getBrand_id(),cb_type.getValue().getType_id(),cb_pack.getValue().getPack_id(),cb_qual.getValue().getQuality_id());
        Coffee_block coffee_block = new Coffee_block(cb_block.getValue(), coffee);
        chosen_van.addParametersOfCoffeeBlock(coffee_block);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scene5_1.fxml"));
        try {
            Parent handfill = loader.load();
            Scene handfillScene = new Scene(handfill);
           ControllerScene5 cont = loader.getController();
            cont.initialize(chosen_van, coffee_block, user);
            Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(handfillScene);
            window.show();

        } catch (IOException e) {
            logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
            throw new RuntimeException(e);
        }
    }


}

