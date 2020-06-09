package TypingPractice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("gui.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Typing Practice");
        Scene scene = new Scene(root, 1186, 584);
        primaryStage.setScene(scene);
        primaryStage.show();
        Controller controller = loader.getController();
        controller.setScene(scene);
        primaryStage.setOnCloseRequest(windowEvent -> {
            System.out.println("Closing");
            controller.db.save();
            System.out.println("Saved");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
