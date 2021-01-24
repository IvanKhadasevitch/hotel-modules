package by.khadasevich.hotel.dao;

import java.io.Serializable;
import java.sql.SQLException;

public interface DAO<T> {
    /**
     * Save object with type T in Data base.
     * @param t is instance of object to save
     * @return saved object with id
     * @throws SQLException
     */
    T save(T t) throws SQLException;

    /**
     * Get object with type T and id=id from data base.
     * @param id is object id
     * @return object from data base
     * @throws SQLException
     */
    T get(Serializable id) throws SQLException;

    /**
     * Update in data base object t with type T.
     * @param t is object instance to update in data base
     * @throws SQLException
     */
    void update(T t) throws SQLException;

    /**
     * Delete record from data base with id=id.
     * @param id is record id in data base
     * @return number of deleted rows
     * @throws SQLException
     */
    int delete(Serializable id) throws SQLException;
}
