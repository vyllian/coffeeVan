import user.User;
import database.User_db;
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

import java.io.IOException;
import java.util.Objects;

public class ControllerEnter {
    public static Logger logger = LogManager.getLogger(ControllerMain.class.getName());
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label numberLabel,  passwordLabel, wrongNmeLabel;

    @FXML
    private TextField numberTextField,visiblPasswordTextField;
    @FXML
    private CheckBox checkBox;

    @FXML
    private Button login;

    @FXML
    private PasswordField passwordTextField;
    private User user = new User();


    public void viewMode(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scene1.fxml"));
        try {
            Parent sort = loader.load();
            Scene sortScene = new Scene(sort);
            ControllerMain cont = loader.getController();
            cont.initialize("view", new User());
            Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(sortScene);
            window.show();
        } catch (IOException e) {
            logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void enter(ActionEvent event) {
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

    public void addNewUser(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene0_2.fxml")));
            stage = (Stage)((Node)(event.getSource())).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void goBack(ActionEvent event) {
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

    public void showHidePW(ActionEvent event) {
        if (checkBox.isSelected())
            visiblPasswordTextField.setText(passwordTextField.getText());
        else  passwordTextField.setText(visiblPasswordTextField.getText());
        visiblPasswordTextField.setVisible(checkBox.isSelected());
        passwordTextField.setVisible(!checkBox.isSelected());
    }

    public void buttonRealese(ActionEvent event) {
        TextField password;
        if (checkBox.isSelected())
            password= visiblPasswordTextField;
        else
            password = passwordTextField;
        User_db user_db = new User_db();
        if (numberTextField.getText().contains("CT"))
            user.setNumber(numberTextField.getText());
        else
            user.setLogin(numberTextField.getText());
        numberTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (!user_db.isExist(user)){
                        numberTextField.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                        numberLabel.setVisible(false);
                        wrongNmeLabel.setVisible(true);
                    }else {
                        passwordTextField.setDisable(numberTextField.getText().length() == 0);
                        visiblPasswordTextField.setDisable(numberTextField.getText().length() == 0);
                        numberTextField.setStyle("-fx-border-color: none; -fx-border-width: 1px; -fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                        numberLabel.setVisible(true);
                        wrongNmeLabel.setVisible(false);
                    }
                }
        });
        password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                login.setDisable(password.getText().length() == 0);
            }
        });

    }

    public void logIntoApp(ActionEvent event) {
        User_db user_db = new User_db();
        if (numberTextField.getText().contains("CT"))
            user.setNumber(numberTextField.getText());
        else
            user.setLogin(numberTextField.getText());

        TextField passfield = checkBox.isSelected()? visiblPasswordTextField : passwordTextField;
        if (!passfield.getText().equals(user_db.getPassword(user))) {
            visiblPasswordTextField.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
            passwordTextField.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
            passwordLabel.setText("Неправильний пароль!");
        }
        else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("scene1.fxml"));
            try {
                Parent sort = loader.load();
                Scene sortScene = new Scene(sort);
                ControllerMain cont = loader.getController();
                cont.initialize("user",user_db.get(user_db.getID(user)) );
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(sortScene);
                window.show();
            } catch (IOException e) {
                logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}
