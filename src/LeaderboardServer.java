import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LeaderboardServer {

    public static void createDatabase(){
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:leaderboard.db");
            System.out.println("Database opened");

            statement = connection.createStatement();
            String sql = "CREATE TABLE LEADERBOARD " +
                        "(" +
                        "NAME           TEXT    NOT NULL, " + 
                        "SCORE          INT     NOT NULL" +
                        ")";
            
            statement.executeUpdate(sql);
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Database created successfully");
    }

    public static void addRecord(String name, int score){
        
        Connection connection;
        Statement statement;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:leaderboard.db");
            connection.setAutoCommit(false);

            statement = connection.createStatement();
            String sql = "INSERT INTO LEADERBOARD (NAME,SCORE) " +
                            "VALUES ('" + name + "', " + Integer.toString(score) + ");";
            statement.executeUpdate(sql);
            statement.close();
            connection.commit();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(0);
        }
        System.out.println("Record added successfully");

    }

    public static void fetchTopLeaderboard(){
        Connection connection;
        Statement statement;
        try{
            connection = DriverManager.getConnection("jdbc:sqlite:leaderboard.db");
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM LEADERBOARD;" );

            int i = 0;
            while(set.next() && i < 10){
                
            }


        } catch (SQLException e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args){

        createDatabase();

    }
    
}   
