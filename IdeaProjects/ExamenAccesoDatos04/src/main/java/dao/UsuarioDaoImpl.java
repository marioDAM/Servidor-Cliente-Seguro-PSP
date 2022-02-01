package dao;

import com.mongodb.client.MongoCollection;
import database.MongoDBController;
import model.Users;

import java.sql.SQLException;

public class UsuarioDaoImpl implements UsuarioDao {


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

}
