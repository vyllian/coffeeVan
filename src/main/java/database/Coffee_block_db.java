package database;

import coffee.Coffee_block;
import coffee.Coffee_block_Volume;
import java.sql.*;
import java.util.ArrayList;
import static coffee.Coffee_block_Volume.getCoffee_block_Volume;
import static database.Database.getConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Coffee_block_db implements DAO<Coffee_block>{
    public static Logger logger = LogManager.getLogger(Coffee_block_db.class.getName());
    @Override
    public Coffee_block get(int id) {
        Coffee_db coffee = new Coffee_db();
        return new Coffee_block(this.getVolume(id),coffee.getFromBlockTab(id));
    }

    @Override
    public boolean insert(Coffee_block coffee_block) {
        Coffee_db coffee = new Coffee_db();
        if (this.isExist(coffee_block)){
            logger.info("Записи про такий блок наявні.");
            return true;
        }
        String query = "insert into CoffeeBlock values (?,?,?)";
        try {Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,coffee_block.getBlock_Volume().getVol_id());
            statement.setInt(2,coffee.getID(coffee_block.getCoffee()));
            statement.setInt(3,coffee_block.getBlock_price());
            statement.executeUpdate();
            logger.info("Новий блок кави! Додаємо у базу.");
            return true;

        }catch(SQLException ex){
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return false;
    }
    @Override
    public int getID(Coffee_block coffee_block){
        if (!this.isExist(coffee_block))
            this.insert(coffee_block);
        Coffee_db coffee = new Coffee_db();
        String query = "select coffeeblock_id from CoffeeBlock where block_id = ? and coffee_id = ?";
        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,coffee_block.getBlock_Volume().getVol_id());
            statement.setInt(2,coffee.getID(coffee_block.getCoffee()));
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int id = res.getInt(1);
                res.close();
                return id;
            }
        }catch(SQLException ex){
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return 0;
    }
    @Override
    public boolean isExist(Coffee_block coffee_block) {
        Coffee_db coffee = new Coffee_db();
        String query = "select count(*) from CoffeeBlock where coffee_id = ? and block_id = ? and block_price= ?";
        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, coffee.getID(coffee_block.getCoffee()));
            statement.setInt(2, coffee_block.getBlock_Volume().getVol_id());
            statement.setInt(3, coffee_block.getBlock_price());
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int amount = res.getInt(1);
                res.close();
                return amount != 0;
            }
        } catch (SQLException ex) {
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return false;
    }

    /**
     * gets volume of block by id
     * @param id block id
     * @return volume type
     */
    public Coffee_block_Volume getVolume(int id){
        String query = "select block_id from CoffeeBlock where coffeeblock_id =?";
        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int volume = res.getInt(1);
                res.close();
                return getCoffee_block_Volume(volume);
            }
        }catch(SQLException ex){
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return null;
    }

    /**
     * inserts block into table of main.java.van goods
     * @param coffee_block block to insert
     * @param van_id id of main.java.van
     * @return true if inserted, false - otherwise
     */
    public boolean insertIntoGoodsList(Coffee_block coffee_block, int van_id){
        String query =
                "merge VanList_Goods as target\n" +
                "    using ( select ? as van_id,? as coffeeblock_id , 1 as amount ) as source\n" +
                "    on (target.van_id = source.van_id and target.coffeeblock_id=source.coffeeblock_id )\n" +
                "    when not matched by target then\n" +
                "    insert (van_id, coffeeblock_id, amount)\n" +
                "    values (source.van_id, source.coffeeblock_id, source.amount)\n" +
                "    when matched THEN UPDATE\n" +
                "    SET target.amount = target.amount +1;";
        try {Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,van_id);
            statement.setInt(2,this.getID(coffee_block));
            statement.executeUpdate();
            return true;
        }catch(SQLException ex){
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return false;
    }

}
