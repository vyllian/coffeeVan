package database;

import coffee.Coffee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import static database.Database.getConnection;

public class Coffee_db implements DAO<Coffee>{
    public static Logger logger = LogManager.getLogger(Coffee_db.class.getName());
    @Override
    public Coffee get(int id) {
        Coffee coffee;
        String query = "SELECT brand_id, type_id, pack_id, quality_id FROM CoffeeCoffee WHERE coffee_id = ?";
        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            if (res.next()){
                coffee = new Coffee(res.getInt("brand_id"),res.getInt("type_id"),
                        res.getInt("pack_id"),res.getInt("quality_id"));
                return coffee;
            }
        }catch(SQLException ex){
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean insert(Coffee coffee) {
        if(this.isExist(coffee)) {
            logger.info("Така кава вже є у базі.");
            return true;
        }
            String query ="insert into CoffeeCoffee values (?,?,?,?,?)";
            try {
                Connection con = getConnection();
                PreparedStatement statement = con.prepareStatement(query);
                statement.setInt(1, coffee.getBrand().getBrand_id());
                statement.setInt(2, coffee.getPacking_type().getCoffeetype().getType_id());
                statement.setInt(3, coffee.getPacking_type().getPack_id());
                statement.setInt(4, coffee.getQuality().getQuality_id());
                statement.setInt(5, coffee.getPrice());
                statement.executeUpdate();
                logger.info("Ого, нова кава, додали у базу!");
                return true;

            } catch (SQLException ex) {
                logger.error("Проблеми з базою даних {}", ex.getMessage());
            }

        return false;
    }
    @Override
    public int getID(Coffee coffee){
        if (!this.isExist(coffee))
            this.insert(coffee);
        String query = "select coffee_id from CoffeeCoffee where brand_id = ? and type_id=? and pack_id=? and quality_id = ?";
        try {Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,coffee.getBrand().getBrand_id());
            statement.setInt(2,coffee.getPacking_type().getCoffeetype().getType_id());
            statement.setInt(3,coffee.getPacking_type().getPack_id());
            statement.setInt(4,coffee.getQuality().getQuality_id());
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return res.getInt(1);
            }
        }catch(SQLException ex){
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return 0;
    }
    @Override
    public boolean isExist(Coffee coffee){
        String query = "select count(*) from CoffeeCoffee where brand_id = ? and type_id=? and pack_id=? and quality_id = ?";
        try {Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,coffee.getBrand().getBrand_id());
            statement.setInt(2,coffee.getPacking_type().getCoffeetype().getType_id());
            statement.setInt(3,coffee.getPacking_type().getPack_id());
            statement.setInt(4,coffee.getQuality().getQuality_id());
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int amount = res.getInt(1);
                res.close();
                return amount!=0;
            }
        }catch(SQLException ex){
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return false;
    }

    /**
     * gets main.java.coffee object by id of block
     * @param block_id block id
     * @return main.java.coffee object
     */
    public Coffee getFromBlockTab(int block_id){
        String query = "select coffee_id from CoffeeBlock where coffeeblock_id =?" ;
        try {Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,block_id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int id = res.getInt(1);
                res.close();
                return this.get(id) ;
            }
        }catch(SQLException ex){
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return null;
    }
}
