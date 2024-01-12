package lk.ijse.dao;


import java.sql.SQLException;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{
    List<T> getAll() throws SQLException;
    boolean save(T entity) throws SQLException;
    T search(String id) throws SQLException;
    boolean delete(String id) throws SQLException;

    boolean update(T dto) throws SQLException;
    String generateNextId() throws SQLException;
    String splitAppId(String currentId);
}