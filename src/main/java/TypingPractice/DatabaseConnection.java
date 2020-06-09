package TypingPractice;

import javafx.util.Pair;

import java.sql.*;


public class DatabaseConnection implements DataBase{

    String select = "SELECT * FROM dbo.userdetails WHERE userdetails.[user] = ?";
    String selectD = "SELECT * FROM dbo.difficulty WHERE difficulty.id = ?";
    String insert = "INSERT INTO dbo.userdetails ([user],password) values (?,?)";
    String insertD = "INSERT INTO dbo.difficulty VALUES('120','3')";
    String delete = "delete from dbo.userdetails";
    String deleteD = "delete from dbo.difficulty";
    String resetIdentity = "DBCC CHECKIDENT ('dbo.userdetails', RESEED, 0)";
    String resetIdentityD = "DBCC CHECKIDENT ('dbo.difficulty', RESEED, 0)";
    String update = "update dbo.difficulty set time = ?  where id = ?";
    String updateL = "update dbo.difficulty set lenght = ? where id = ?";
    int id;
    Statement statement;
    private DatabaseConnection(){
            String url = "jdbc:sqlserver://localhost\\FRENZYK-PC\\Frenzyk;"+
                    "portNumber=1433;" +
                    "user=frenzyk;"+
                    "password=secret;"+
                    "databaseName=tmps";
            try {
                dbconnect = DriverManager.getConnection(url);
                statement = dbconnect.createStatement();
                System.out.println("connected");
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    private static DatabaseConnection instance = null;
    private Connection dbconnect;

    public static DatabaseConnection getInstance(){
        if(instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void  newUser(String name, String password) {
        try {
            PreparedStatement statatement = dbconnect.prepareStatement(insert);
            statatement.setString(1, name);
            statatement.setString(2, password);
            statement.execute(insertD);
            int rows = statatement.executeUpdate();
            if (rows > 0) {
                System.out.println("Rows was inserted");
            }
            //dbConnect.close();
        } catch (SQLException e) {
            System.out.println("Failed");
            e.printStackTrace();
        }
    }
    public Pair<Integer, Integer> select(String username) {
        try {
            PreparedStatement statement = dbconnect.prepareStatement(select);
            statement.setString(1,username);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                PreparedStatement st = dbconnect.prepareStatement(selectD);
                id = rs.getInt("id");
                st.setInt(1,id);
                ResultSet resultSet = st.executeQuery();
                while (resultSet.next()) {
                    return new Pair<>(resultSet.getInt("time"), resultSet.getInt("lenght"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void delete(){
        try {
            statement.execute(delete);
            statement.execute(resetIdentity);
            statement.execute(deleteD);
            statement.execute(resetIdentityD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(int id, int time, int length){
        try {
            PreparedStatement preparedStatement = dbconnect.prepareStatement(update);
            preparedStatement.setInt(1,time);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
            preparedStatement = dbconnect.prepareStatement(updateL);
            preparedStatement.setInt(1,length);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        System.out.println("asd");
    }

    @Override
    public int getid() {
        return id;
    }
}
