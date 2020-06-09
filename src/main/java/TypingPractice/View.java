package TypingPractice;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class View implements Observer{
    Label Errors, Speed, Score  ;
    TextFlow mTextFlow;
    Scene scene;
    Long time;

    public View(Scene scene) {
        this.scene = scene;
        mTextFlow = (TextFlow) scene.lookup("#mTextFlow");
        Errors = (Label) scene.lookup("#Errors");
        Speed = (Label) scene.lookup("#Speed");
        Score = (Label) scene.lookup("#Score");
        time = System.currentTimeMillis();
    }

    @Override
    public void update(String s, int currentChar, int Err, int count){
            double tmp = (System.currentTimeMillis() - time) / (double) 1000;
            Text gText = new Text(s.substring(0, currentChar));
            Text rText = new Text(s.substring(currentChar));
            gText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
            rText.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
            gText.setFill(Color.GREEN);
            rText.setFill(Color.RED);
            mTextFlow.getChildren().clear();
            mTextFlow.getChildren().addAll(gText, rText);
            Errors.setText(String.valueOf(Err));
            Score.setText(String.valueOf(Math.round(count / tmp) * 4 - Err));
    }

    public void setBck(Background bg, Label label) {
        label.setBackground(bg);
    }

}
