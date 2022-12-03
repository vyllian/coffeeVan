package database;

public interface DAO<T> {
    /**
     * finds object by id
     * @param id id of object
     * @return object
     */
    T get(int id);

    /**
     * inserts object in table
     * @param t object to insert
     * @return true - if inserted, false - otherwise
     */
    boolean insert(T t);

    /**
     * gets id of object (creates one if not exist)
     * @param t - object to find
     * @return id of found object
     */
    int getID(T t);

    /**
     * checks if there's such object
     * @param t object to check
     * @return true if exists, false - otherwise
     */
    boolean isExist(T t);

}
