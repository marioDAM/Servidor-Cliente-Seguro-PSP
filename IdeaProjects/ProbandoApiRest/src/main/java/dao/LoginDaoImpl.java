package dao;

import com.mongodb.client.MongoCollection;
import database.MongoDBController;
import model.Login;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class LoginDaoImpl implements LoginDao {
    @Override
    public List<Login> findAll() throws SQLException {
        return null;
    }

    @Override
    public Login getById(int id) throws SQLException {
        return null;
    }

    @Override
    public Login save(Login login) throws SQLException {
        UUID uuid = UUID.randomUUID();
        MongoDBController mongoController = MongoDBController.getInstance();
        mongoController.open();
        MongoCollection<Login> loginCollection = mongoController.getCollection("Cluster0", "login", Login.class);
        try {
            loginCollection.insertOne(login);
            return login;
        } catch (Exception e) {
            throw new SQLException("Error LoginRepository al insertar login en BD");
        } finally {
            mongoController.close();
        }
    }

    @Override
    public Login update(Login t) throws SQLException {
        return null;
    }

    @Override
    public Login delete(Login t) throws SQLException {
        return null;
    }
}
