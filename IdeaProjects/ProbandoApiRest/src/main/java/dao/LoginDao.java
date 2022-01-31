package dao;

import model.Login;

import java.sql.SQLException;
import java.util.List;


public interface LoginDao {
    List<Login> findAll() throws SQLException;

    // Obtiene por ID
    Login getById(int id) throws SQLException;

    // Salva
    Login save(Login t) throws SQLException;

    // Actualiza
    Login update(Login t) throws SQLException;

    // Elimina
    Login delete(Login t) throws SQLException;
}
