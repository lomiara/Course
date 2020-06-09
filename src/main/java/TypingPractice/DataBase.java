package TypingPractice;

import javafx.util.Pair;

public interface DataBase {
    static DatabaseConnection getInstance(){
        return DatabaseConnection.getInstance();
    };
    void newUser(String name, String password);
    Pair<Integer, Integer> select(String username);
    void delete();
    void update(int id, int time, int length);
    void save();
    int getid();
}
