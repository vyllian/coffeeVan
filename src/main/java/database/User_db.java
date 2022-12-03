package database;

import user.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static database.Database.connection;
import static database.Database.getConnection;

public class User_db implements DAO<User> {

    public static Logger logger = LogManager.getLogger(Van_db.class.getName());
    @Override
    public User get(int id) {
        String query ="select login, password,empNumber,firstName, lastName from Users where user_id = ?";
        Database.connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            if (res.next()){
                return new User(res.getString("firstName"),res.getString("lastName"),res.getString("login"),res.getString("empNumber"), res.getString("password"));
            }
        }catch(SQLException ex){
            System.out.println("sql error");
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean insert(User user) {
        String query = "insert into Users values(?,?,?,?,?)";
        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3,  user.getFirstname());
            statement.setString(4, user.getLastname());
            statement.setString(5, user.getNumber());
            statement.executeUpdate();
            logger.info("Нового користувача зареєстровано.");
            return true;

        }catch(SQLException ex){
            System.out.println("sql error: " + ex.getMessage());
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return false;
    }

    @Override
    public int getID(User user) {
        String query ="select user_id from Users where login = ? or empNumber = ?";
        Database.connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,user.getLogin());
            statement.setString(2, user.getNumber());
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int id = res.getInt(1);
                res.close();
                return id;
            }
        }catch(SQLException ex){
            System.out.println("sql error: "+ex.getMessage());
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return 0;
    }
    public String getPassword(User user) {
        String query ="select password from Users where login = ? or empNumber = ?";
        Database.connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,user.getLogin());
            statement.setString(2, user.getNumber());
            ResultSet res = statement.executeQuery();
            if (res.next()) {
               return res.getString(1);
            }
        }catch(SQLException ex){
            System.out.println("sql error: "+ex.getMessage());
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean isExist(User user) {
        String query ="select count(*) from Users where login = ? or empNumber = ? ";
        Database.connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,user.getLogin());
            statement.setString(2, user.getNumber());
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return (res.getInt(1)==1);
            }
        }catch(SQLException ex){
            System.out.println("sql error: "+ex.getMessage());
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return false;
    }
    public boolean isExistByEmpNumber(User user) {
        String query ="select count(*) from Users where empNumber = ?";
        Database.connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,user.getNumber());
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return (res.getInt(1)==1);
            }
        }catch(SQLException ex){
            System.out.println("sql error: "+ex.getMessage());
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return false;
    }

}
