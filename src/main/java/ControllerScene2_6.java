
import user.User;
import coffee.Coffee;
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
import van.Van;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerScene2_6 {
    private Van chosen_van;
    TreeItem<String> parent,  item,tableroot = new TreeItem<>();
    @FXML
    private TreeTableView<String> table = new TreeTableView<>();
    @FXML
    private TreeTableColumn<String,String> column = new TreeTableColumn<>();
    @FXML
    Label label;
    @FXML
    TextField textField;
    @FXML
    Button findButton;
    String qualStr, mode;
    User user;
    @FXML
    private Button login,logout;
    @FXML
    private Label nameLabel, personIcon;

    public void initialize(Van van, String mode, User user){
        this.user = user;
        this.mode = mode;
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
        if (mode.equals("view")){
            login.setVisible(true);
            personIcon.setVisible(false);
        }else{
            logout.setVisible(true);
            this.user = user;
            nameLabel.setText(user.getLogin());
        }
    }
    public void sortDEC(ActionEvent event) {
        chosen_van.sorting("DEC");
        int am = parent.getChildren().size();
        if (am > 0) {
            parent.getChildren().subList(0, am).clear();
        }
        for(Blocks_amount b: chosen_van.getGoods()){
            item = new TreeItem<>(b.toString());
            parent.getChildren().add(item);
        }
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue()));
    }

    public void sortASC(ActionEvent event) {
        chosen_van.sorting("ASC");
        int am = parent.getChildren().size();
        if (am > 0) {
            parent.getChildren().subList(0, am).clear();
        }
        for(Blocks_amount b: chosen_van.getGoods()){
            item = new TreeItem<>(b.toString());
            parent.getChildren().add(item);
        }
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue()));
    }

    public void switchToScene1(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scene1.fxml"));
        try {
            Parent sort = loader.load();
            Scene sortScene = new Scene(sort);
            ControllerMain cont = loader.getController();
            cont.initialize(this.mode, this.user);
            Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(sortScene);
            window.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void buttonRelease(ActionEvent event) {
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                findButton.setDisable(textField.getText().length() <= 0);
            }
        });
    }

    public void findCoffee(ActionEvent event) {
        qualStr = textField.getText();
        ArrayList<String> values = defineQualValues(qualStr);
        if (values.isEmpty()){
            label.setText("Неправильний формат, спробуйте ще:");
            textField.setStyle("-fx-border-color: red; -fx-border-width: 1px;-fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
        }
        else{
            label.setText("Введіть значення:");
            textField.setStyle("-fx-border-color: none; -fx-border-width: 1px;-fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");

            ArrayList<Coffee> coffee = chosen_van.findCoffee(values);
            int am = parent.getChildren().size();
            if (am > 0) {
                parent.getChildren().subList(0, am).clear();
            }
            for(Coffee c: coffee){
                item = new TreeItem<>(c.toString());
                parent.getChildren().add(item);
            }
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue()));
        }

    }

    public ArrayList<String> defineQualValues(String str){
        ArrayList<String> arr = new ArrayList<>();
        Pattern p1 = Pattern.compile("([A-Ba-b]{2})");
        Matcher matcher = p1.matcher(str);
        while (matcher.find()){
            arr.add(matcher.group());
        }
        return arr;
    }

    public void logoutApp(ActionEvent event) {
        ControllerMain cont = new ControllerMain();
        cont.logoutApp(event);
    }

    public void switchToScene0_1(ActionEvent event) {
        ControllerMain cont = new ControllerMain();
        cont.switchToScene0_1(event);
    }
}
