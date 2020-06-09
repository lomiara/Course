package TypingPractice;

import javafx.scene.control.Button;

import java.util.LinkedList;
import java.util.List;

import static javafx.scene.input.KeyCode.BACK_SPACE;

public class Model implements Observable {
    private List<Observer> observers;
    private int count = 0;
    private int Speed;
    private int Errors = 0;
    private int Score;
    private int currentChar = 0;
    public int length;
    public int level;
    public int time;
    public Button btn, btnS;
    private String text;
    public boolean nextword = false;
    Model() {
        observers = new LinkedList<>();
    }
    public void key(String character) {
        nextword = false;
        System.out.println("Character: "+character);
        count++;
        if(character.equals(BACK_SPACE)) {
            currentChar--;
            notifyObservers();
            return;
        }
        if(character.equals(text.substring(currentChar,currentChar+1))) {
            currentChar++;
        } else {
            Errors++;
        }
        if(currentChar == text.length()) {
            nextword = true;
            currentChar = 0;
            length -= 1;
            time -=4;
            btnS.fire();
            btn.fire();
            System.out.print("DONE");
        }
        notifyObservers();
    }
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(text,currentChar,Errors,count);
        }
    }

    public Memento save(){
        return new Memento(currentChar,Errors,Speed,Score,text,level,length,time);
    }

    public void restore(Memento snapshot){
        count++;
        if(snapshot != null) {
            currentChar = snapshot.currentChar;
            Errors = snapshot.Errors;
            Speed = snapshot.Speed;
            Score = snapshot.Score;
            text = snapshot.Text;
            level = snapshot.level;
            length = snapshot.length;
            time = snapshot.time;
            notifyObservers();
        }
    }

    class Memento {
        private final int currentChar;
        private final int Errors;
        private final int Speed;
        private final int Score;
        private final String Text;
        private final int level,length,time;

        public Memento(int currentChar, int Errors, int Speed, int Score, String Text,int level,int length, int time) {
            this.currentChar = currentChar;
            this.Errors = Errors;
            this.Speed = Speed;
            this.Score = Score;
            this.Text = Text;
            this.level = level;
            this.length = length;
            this.time = time;
        }
    }
}
