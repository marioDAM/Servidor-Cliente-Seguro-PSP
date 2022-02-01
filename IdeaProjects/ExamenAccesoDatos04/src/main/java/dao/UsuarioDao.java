package dao;
import model.Users;

import java.sql.SQLException;
import java.util.List;

public interface UsuarioDao {

    Users saveUserBd(Users programador) throws SQLException;

}
