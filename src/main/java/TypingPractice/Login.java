package TypingPractice;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Pair;

public class Login {
    Pair<Integer,Integer> pair;
    public int time,length,id;
    DataBase db = DatabaseConnectionProxy.getInstance();
    @FXML
    TextField LoginField;
    @FXML
    PasswordField PasswordField;
    @FXML
    private void LoginAction(Event e) {
        pair = db.select(LoginField.getText());
        time = pair.getKey();
        length = pair.getValue();
        id = db.getid();
        System.out.print("time" + pair.getKey() + "length" + pair.getValue());
    }

    @FXML
    private void NewUser(Event e) {
        db.newUser(LoginField.getText(),PasswordField.getText());
    }
}
