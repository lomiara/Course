package TypingPractice;

import com.thedeanda.lorem.LoremIpsum;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;

public class Controller {
    Login loginController;
    int time = 50, length = 40, id = 0;
    Timer timer;
    Model model;
    View view;
    Scene scene;
    @FXML Label Speed;
    @FXML Label Score;
    @FXML AnchorPane anchorPane;
    @FXML TextFlow mTextFlow;
    @FXML Button BtnStop;
    @FXML Button BtnStart;
    Helper task;
    Text rText;
    String s;
    Background red = new Background(new BackgroundFill(Color.rgb(210, 10, 20), CornerRadii.EMPTY, Insets.EMPTY));
    Background violet = new Background(new BackgroundFill(Color.web("#cc6699"), CornerRadii.EMPTY, Insets.EMPTY));
    CareTaker careTaker;
    DataBase db;
    TextProvider provider;
    public Controller(){
        careTaker = new CareTaker();
        db = DatabaseConnectionProxy.getInstance();
    }

    @FXML
    public void initialize() {
    }

    @FXML
    private void btnStartPressed(Event e){
        if(loginController != null) {
            id = loginController.id;
            length = loginController.length;
            time = loginController.time;
        }
        if(model.nextword) {
            time = model.time;
            length = model.length;
            if( id != 0)
            db.update(id,time,length);
        }
        provider = new TextProvider.Builder("Random")
                .length(length)
                .lorem(LoremIpsum.getInstance())
                .build();
        timer = new Timer();
        task = new Helper(Speed);
        s = provider.provideText();
        model.setText(s);
        model.time = time;
        model.length = length;
        task.setStartTime(System.currentTimeMillis());
        task.setTargetTime((long)time*1000);
        timer.schedule(task,0,1000);
        showText(s);
        System.out.println(e.getEventType());
    }

    private void showText(String s) {
        rText = new Text(s);
        rText.setFill(Color.RED);
        rText.setFont(Font.font("Times New Roman", FontWeight.BOLD,18));
        mTextFlow.getChildren().addAll(rText);
    }

    @FXML
    private void btnStopPressed(Event e){
        task.cancel();
        timer.cancel();
        timer.purge();
        s = "  ";
        model.setText(s);
        model.notifyObservers();
        Speed.setText("0");
        System.out.println(e.getEventType());
    }
    @FXML
    private void btnLoginPressed(Event e){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("login.fxml"));
        Parent root1 = null;
        try {
            root1 = fxmlLoader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        loginController = fxmlLoader.getController();
        Stage stage = new Stage();
        stage.setTitle("ABC");
        stage.setScene(new Scene(root1));
        stage.show();
    }

    @FXML
    private void keyTyped(KeyEvent e) {
        if(Integer.parseInt(Speed.getText()) >= time) {
            BtnStop.fire();
            return;
        }
        System.out.println("Typed "+e.getCode()+" " + e.getCharacter());
        byte[] ar = e.getCharacter().getBytes();
        if(ar[0] == 8){
            model.restore(careTaker.get(careTaker.size-1));
            careTaker.remove(careTaker.size - 1);
            System.out.println("!!!!!");
        } else {
            careTaker.add(model.save());
            model.key(e.getCharacter());
        }
    }

    @FXML
    private void keyPressed(KeyEvent e){
        System.out.println("Pressed "+e.getCode() + " ");
        Label label = (Label)scene.lookup("#"+e.getCode());
        if(label!= null) {
            view.setBck(red,label);
        }
        label = (Label)scene.lookup("#"+e.getCode()+"1");
        if(label != null) {
            view.setBck(red,label);
        }
    }

    @FXML
    private void keyReleased(KeyEvent e){
        Label label = (Label)scene.lookup("#"+e.getCode());
        view.setBck(violet,label);
        label = (Label)scene.lookup("#"+e.getCode()+"1");
        if(label != null) {
            view.setBck(violet,label);
        }
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        view = new View(scene);
        model = new Model();
        model.setText(s);
        model.registerObserver(view);
        model.btn = BtnStart;
        model.btnS = BtnStop;
    }

}
