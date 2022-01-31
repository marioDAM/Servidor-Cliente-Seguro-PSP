package dao;

import com.mongodb.client.MongoCollection;
import controller.HibernateController;
import database.MongoDBController;
import model.Users;

import java.sql.SQLException;
import java.util.List;

public class UsuarioDaoImpl implements UsuarioDao {


    @Override
    public List<Users> selectAllProgrammers() throws SQLException {
        return null;
    }

    @Override
    public Users selectProgrammerById(int id) throws SQLException {
        return null;
    }

    @Override
    public Users saveUserBd(Users usuario) throws SQLException {
        MongoDBController mongoController = MongoDBController.getInstance();
        mongoController.open();
        MongoCollection<Users> userCollection = mongoController.getCollection("Cluster0", "user", Users.class);
        try {
            userCollection.insertOne(usuario);
            return usuario;
        } catch (Exception e) {
            throw new SQLException("Error UserRepository al insertar usuario en BD:" + e.getMessage());
        } finally {
            mongoController.close();
        }
    }

    @Override
    public Users deleteProgrammer(Users programador) throws SQLException {
        return null;
    }

    @Override
    public Users updateProgrammer(Users programador) throws SQLException {
        return null;
    }
}
