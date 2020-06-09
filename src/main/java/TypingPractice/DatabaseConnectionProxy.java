package TypingPractice;

import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DatabaseConnectionProxy implements DataBase{
    static String log ="";
    File file = new File("log.txt");
    static DatabaseConnection db = DatabaseConnection.getInstance();

    static DatabaseConnectionProxy getInstance(){
        log("request dbConnection");
        return new DatabaseConnectionProxy();
    }
    @Override
    public void newUser(String name, String password) {
        db.newUser(name,password);
        log("create new user with: "+name+" "+password);
    }

    @Override
    public Pair<Integer, Integer> select(String username) {
        log("select user with: "+username);
        return db.select(username);
    }

    @Override
    public void delete() {
        db.delete();
        log("delete all");
    }

    @Override
    public void update(int id, int time, int length) {
        db.update(id,time,length);
        log("update id "+id+"to time "+time+" length "+length);
    }

    public static void log(String operation){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date)+" "+operation);
        log += dateFormat.format(date)+" "+operation + '\n';
    }
    @Override
    public void save(){
        System.out.println("zxc");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            System.out.println("saving");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
            writer.append(log);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getid() {
        return db.getid();
    }
}
