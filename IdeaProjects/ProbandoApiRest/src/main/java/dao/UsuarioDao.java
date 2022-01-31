package dao;
import model.Users;

import java.sql.SQLException;
import java.util.List;

public interface UsuarioDao {

    List<Users> selectAllProgrammers() throws SQLException;

    Users selectProgrammerById(int id) throws SQLException;

    Users saveUserBd(Users programador) throws SQLException;

    Users deleteProgrammer(Users programador) throws SQLException;

    Users updateProgrammer(Users programador) throws SQLException;
}
