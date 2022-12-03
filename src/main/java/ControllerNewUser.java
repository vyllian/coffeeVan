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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerNewUser {
    public static Logger logger = LogManager.getLogger(ControllerNewUser.class.getName());
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label numberLabel,firstnameLabel,lastnameLabel, label;

    @FXML
    private TextField numberTextField,firstnameTextField,lastnameTextField;

    @FXML
    private Button nextButton,addButton;
    @FXML
    private CheckBox checkBoxPass, checkBoxRepPass;

    @FXML
    private TextField loginField,visiblPasswordTextField, visiblRepPassTextField;
    @FXML
    private Label passwordLabel,repPasswordLabel, labelUsrExst;

    @FXML
    private PasswordField passwordRepTextField,passwordTextField;
    
    private User user = new User();

    public void initialize(User user){
        this.user = user;
    }

    @FXML
    void buttonRealese(ActionEvent event) {
        label.setVisible(true);
        labelUsrExst.setVisible(false);
        Pattern p1 = Pattern.compile("[А-ЯІЇЄҐЙ][а-яійїґє]+");
        Pattern p2 = Pattern.compile("CT\\d{4}");
        lastnameTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                Matcher matcher = p1.matcher(lastnameTextField.getText());
                if (matcher.find()){
                    firstnameTextField.setDisable(lastnameTextField.getText().length() == 0);
                    firstnameLabel.setVisible(false);
                    lastnameTextField.setStyle("-fx-border-color: none; -fx-border-width: 1px; -fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");

                }
                else {
                    lastnameTextField.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                    firstnameLabel.setVisible(true);
                }
            }
        });
        firstnameTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                Matcher matcher = p1.matcher(firstnameTextField.getText());
                if (matcher.find()){
                    numberTextField.setDisable(lastnameTextField.getText().length() == 0);
                    lastnameLabel.setVisible(false);
                    firstnameTextField.setStyle("-fx-border-color: none; -fx-border-width: 1px; -fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                }
                else {
                    firstnameTextField.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                    lastnameLabel.setVisible(true);
                }
            }
        });
        numberTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                Matcher matcher = p2.matcher(numberTextField.getText());
                if (matcher.find()){
                    numberLabel.setVisible(false);
                    nextButton.setDisable(numberTextField.getText().length() == 0);
                    numberTextField.setStyle("-fx-border-color: none; -fx-border-width: 1px; -fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                }
                else {
                    numberTextField.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                    numberLabel.setVisible(true);
                }
            }
        });

    }

    public void goBackTo0(ActionEvent event) {
        ControllerEnter cont = new ControllerEnter();
        cont.goBack(event);
    }

    public void goNext(ActionEvent event) {
        user.setFirstname(firstnameTextField.getText());
        user.setLastname(lastnameTextField.getText());
        user.setNumber(numberTextField.getText());
        User_db user_db = new User_db();
        if (user_db.isExistByEmpNumber(user)) {
            label.setVisible(false);
            labelUsrExst.setVisible(true);
            nextButton.setDisable(true);
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("scene0_3.fxml"));
            try {
                Parent sort = loader.load();
                Scene sortScene = new Scene(sort);
                ControllerNewUser cont = loader.getController();
                cont.initialize(user);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(sortScene);
                window.show();
            } catch (IOException e) {
                logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public void addUser(ActionEvent event) {
        user.setLogin(loginField.getText());
        user.setPassword(passwordTextField.getText());
        User_db user_db = new User_db();
        user_db.insert(user);
        System.out.println(user);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("scene1.fxml"));
        try {
            Parent sort = loader.load();
            Scene sortScene = new Scene(sort);
            ControllerMain cont = loader.getController();
            cont.initialize("user",user);
            Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(sortScene);
            window.show();
        } catch (IOException e) {
            logger.error("Помилка у роботі інтерфейсу {}"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void showHidePW(ActionEvent event) {
        if (checkBoxPass.isSelected())
            visiblPasswordTextField.setText(passwordTextField.getText());
        else  passwordTextField.setText(visiblPasswordTextField.getText());
        visiblPasswordTextField.setVisible(checkBoxPass.isSelected());
        passwordTextField.setVisible(!checkBoxPass.isSelected());

    }

    public void showHideRepPW(ActionEvent event){
        if(checkBoxRepPass.isSelected())
            visiblRepPassTextField.setText(passwordRepTextField.getText());
        else  passwordRepTextField.setText(visiblRepPassTextField.getText());
        visiblRepPassTextField.setVisible(checkBoxRepPass.isSelected());
        passwordRepTextField.setVisible(!checkBoxRepPass.isSelected());
    }

    public void buttonRealese0_3(ActionEvent event) {
        Pattern p1 = Pattern.compile("\\D+");
        Pattern p2 = Pattern.compile("\\d+");
        loginField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                passwordTextField.setDisable(loginField.getText().length() == 0);
                visiblPasswordTextField.setDisable(loginField.getText().length() == 0);
            }
        });
        passwordTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                Matcher matcherpas1 = p1.matcher(passwordTextField.getText()), matchervispas1 = p1.matcher(visiblPasswordTextField.getText());
                Matcher matcherpas2 = p2.matcher(passwordTextField.getText()), matchervispas2 = p2.matcher(visiblPasswordTextField.getText());
                if ((passwordRepTextField.getText().length()>=8 ||passwordTextField.getText().length()>=8)
                        &&(matcherpas1.find()||matchervispas1.find())&&(matcherpas2.find()||matchervispas2.find())){
                    passwordRepTextField.setDisable(passwordTextField.getText().length() == 0);
                    visiblRepPassTextField.setDisable(passwordTextField.getText().length() == 0);
                    passwordTextField.setStyle("-fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                    passwordLabel.setVisible(false);
                }
                else {
                    passwordTextField.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                    passwordLabel.setVisible(true);
                }
            }
        });
        TextField repeatpass;
        if(checkBoxRepPass.isSelected())
            repeatpass = visiblRepPassTextField;
        else repeatpass = passwordRepTextField;
        repeatpass.setOnKeyPressed(new EventHandler<KeyEvent>() {
            String pass;
            @Override
            public void handle(KeyEvent keyEvent) {
                if(checkBoxPass.isSelected())
                    pass = visiblPasswordTextField.getText();
                else pass = passwordTextField.getText();
                if (pass.equals(repeatpass.getText())) {
                    addButton.setDisable(false);
                    repPasswordLabel.setVisible(false);
                    passwordRepTextField.setStyle("-fx-border-color: none; -fx-border-width: 1px; -fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                    visiblRepPassTextField.setStyle("-fx-border-color: none; -fx-border-width: 1px; -fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                }
                else {
                    addButton.setDisable(true);
                    passwordRepTextField.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                    visiblRepPassTextField.setStyle("-fx-border-color: red; -fx-border-width: 1px; -fx-effect: dropshadow(one-pass-box,#994300,2,2,2,2)");
                    repPasswordLabel.setVisible(true);
                }
            }
        });
    }
}
