package database;

import coffee.Blocks_amount;
import van.Van;
import van.Van_type;
import java.sql.*;
import java.util.ArrayList;
import static database.Database.connection;
import static database.Database.getConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Van_db implements DAO<Van>{
    public static Logger logger = LogManager.getLogger(Van_db.class.getName());
    @Override
    public Van get(int id) {
        Van van = this.getVanType(id);
        if (van == null) {
            System.out.println("Такого фургону не існує...");
            logger.info("Фургону з іd {} не існує.",id);
            return null;
        }
        van.setId(id);
        Coffee_block_db block_db=new Coffee_block_db();
        Blocks_amount block;
        String query = "select coffeeblock_id, amount from VanList_Goods where van_id =?";
        Database.connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            while (res.next()){
                block = new Blocks_amount();
                block.setBlock(block_db.get(res.getInt(1)));
                block.setAmount(res.getInt(2));
                van.addParametersOfCoffeeBlock(block.getBlock());
            }
            van.setFullness(this.getFullness(id));
        }catch(SQLException ex){
            System.out.println("sql error");
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return van;
    }
    public int getFullness(int id){
        String query ="select fullness from Van where van_id = ?";
        Database.connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                return res.getInt(1);
            }
        }catch(SQLException ex){
            System.out.println("sql error: "+ex.getMessage());
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return 0;
    }
    public Van getLast(){
        String query = "select van_id from Van order by van_id desc";
        Database.connection = Database.getConnection();
        int id=0;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet res = statement.executeQuery();
            if (res.next()) id = res.getInt(1);
        }catch(SQLException ex){
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return get(id);
    }

    public ArrayList<Van> getAll() {
        ArrayList<Van> van_list = new ArrayList<>();
        String query ="select van_id from Van";
        Database.connection = Database.getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet res = statement.executeQuery())
        {
            while(res.next()){
                int id = res.getInt(1);
                Van van = get(id);
                van_list.add(van);
            }
            return van_list;
        }catch(SQLException ex){
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return null;
    }

    /**
     * inserts without goods
     * @param van object to insert
     * @ true if inserted
     */
    @Override
    public boolean insert(Van van){
        if (this.isExist(van)){
            logger.info("Фургон уже в базі.");
            return true;
        }
        if (this.isExistById(van.getId())){
            this.update(van);
            return true;
        }
        String query = "insert into Van values(?,?,?)";
        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,van.getVan_type().getType());
            statement.setInt(2,van.getFullness());
            statement.setInt(3,van.getGoods_price());
            statement.executeUpdate();
            logger.info("Ура! Новий фургон завантажено.");
            return true;

        }catch(SQLException ex){
            System.out.println("sql error: " + ex.getMessage());
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return false;

    }

    public boolean update(Van van) {
        String query = "update Van\n" +
                "set fullness = ?, goods_price = ? where van_id = ?";
        try {
            Connection con = getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1,van.getFullness());
            statement.setInt(2, van.getGoods_price());
            statement.setInt(3,van.getId());
            statement.executeUpdate();
            logger.info("Дані фургона {} оновлено.", van.getId());
            return true;

        }catch(SQLException ex){
            System.out.println("sql error: " + ex.getMessage());
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return false;
    }

    @Override
    public int getID(Van van) {
        String query ="select van_id from Van where vantype_id = ? and fullness = ? and goods_price = ?";
        Database.connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,van.getVan_type().getType());
            statement.setInt(2,van.getFullness());
            statement.setInt(3,van.getGoods_price());
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
    @Override
    public boolean isExist(Van van) {
        String query = "select count(*) from Van where vantype_id = ? and fullness = ? and goods_price = ?";
        Database.connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, van.getVan_type().getType());
            statement.setInt(2, van.getFullness());
            statement.setInt(3, van.getGoods_price());
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int amount = res.getInt(1);
                res.close();
                return amount != 0;
            }
        } catch (SQLException ex) {
            System.out.println("sql error: " + ex.getMessage());
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return false;
    }
    public boolean isExistById(int id) {
        String query = "select count(*) from Van where van_id = ?";
        Database.connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                int amount = res.getInt(1);
                return amount != 0;
            }
        } catch (SQLException ex) {
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return false;
    }

    /**
     * gets only main.java.van type by id
     * @param id id of main.java.van
     * @return created main.java.van with found type
     */
    public Van getVanType(int id){
        String query = "select vantype_id from Van where van_id = ?";
        Database.connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet res = statement.executeQuery();
            if (res.next()){
                return new Van(Van_type.getType(res.getInt(1)));
            }
        }catch(SQLException ex){
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return null;
    }

    /**
     * inserts main.java.van type and its goods
     * @param van main.java.van to insert
     * @return true if inserted
     */
    public boolean insertVanWGoods(Van van){
        Coffee_block_db db_block = new Coffee_block_db();
        this.insert(van);
        boolean inserted = false;
        for (Blocks_amount block: van.getGoods()) {
            inserted= db_block.insertIntoGoodsList(block.getBlock(),this.getID(van));
        }
        return inserted;
    }

    /**
     * deletes obj by id
     * @param id id to delete
     * @return true if deleted
     */
    public boolean delete(int id){
        return(deleteFromVan(id) && deleteFromGoodsList(id));
    }
    public boolean deleteFromVan(int id){
        String query="delete from Van where van_id =?";
        Database.connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            statement.executeUpdate();
            logger.info("Фургон {} видалено з списку((",id);
            return true;
        }catch(SQLException ex){
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return false;
    }
    public boolean deleteFromGoodsList(int id){
        String query= "delete from VanList_Goods where van_id = ?";
        Database.connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            statement.executeUpdate();
            logger.info("Фургон {} видалено з списку((",id);
            return true;
        }catch(SQLException ex){
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return false;
    }

    /**
     * delets vans with smaller/equal percent of fullness
     * @param perc - percent
     * @return true if deleted
     */
    public boolean deleteByFullness(int perc){
        ArrayList<Integer> vans = this.getIdByFullness(perc);
        for (Integer van_id: vans)
            this.delete(van_id);
        return true;
    }

    /**
     * finds vans ids to delete
     * @param value percent of fullness to find with/smaller
     * @return array of ids values
     */
    public ArrayList<Integer> getIdByFullness(int value){
        ArrayList<Integer> id_list = new ArrayList<>();
        String query = "select van_id from Van where fullness <= ?";
        Database.connection = Database.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,value);
            ResultSet res = statement.executeQuery();
            while (res.next()){
                int id = res.getInt(1);
                id_list.add(id);
            }
        }catch(SQLException ex){
            logger.error("Проблеми з базою даних {}", ex.getMessage());
        }
        return id_list;
    }

}
