package TypingPractice;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.TimerTask;

public class Helper extends TimerTask {
    Label time;
    long targetTime, startTime;
    boolean ended = false;


    public Helper(Label time) {
        this.time = time;
    }

    public void setTargetTime(long targetTime) {
        this.targetTime = targetTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public boolean isEnded() {
        return ended;
    }

    public double currentTime(){
        return (System.currentTimeMillis() - startTime)/(double)1000;
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            time.setText(String.valueOf(Math.round((System.currentTimeMillis() - startTime)/(double)1000)));
            if (System.currentTimeMillis() - startTime > targetTime) {
                ended = true;
                this.cancel();
            }
        });
    }
}
