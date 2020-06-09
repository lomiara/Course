package TypingPractice;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    private List<Model.Memento> history = new ArrayList<>();
    public int size = 0;

    public void add(Model.Memento snapshot) {
        size++;
        history.add(snapshot);
    }
    public Model.Memento get(int index) {
        try {
            return history.get(index);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    public void remove(int index){
        try {
            history.remove(index);
            size --;
        } catch (IndexOutOfBoundsException e) {
            size = 0;
        }
    }
}
