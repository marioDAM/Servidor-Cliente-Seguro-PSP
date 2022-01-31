
import dao.LoginDao;
import dao.LoginDaoImpl;
import dao.UsuarioDao;
import dao.UsuarioDaoImpl;
import model.Cifrador;
import model.Login;
import model.Users;
import org.bson.types.ObjectId;
import rest.APIRestConfig;
import rest.AccesoDatosRest;
import retrofit2.Response;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class JpaClientDemo {

    AccesoDatosRest restService = APIRestConfig.getService();
    UsuarioDao ud = new UsuarioDaoImpl();

    public void guardarUser() {
        System.out.println("GUARDANDO CONSULTA DE USUARIO DE API-REST EN BD MONGODB");

        try {
            Response<List<Users>> response = restService.usersGetAll().execute();
            List<Users> usuarios = (response.body());
            Users usuario = usuarios.get(0);
            System.out.println(usuario);
            ud.saveUserBd(usuario);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser() throws SQLException {
        Client cliente = new Client();
        List<Users> usuarios;
        usuarios = cliente.usersGetAll();
        System.out.println("GUARDANDO USUARIO EN LA BASE DE DATOS");
        for (int i = 0; i < usuarios.size(); i++) {
            Users usuario = usuarios.get(i);
            ud.saveUserBd(usuario);
        }
    }

    public Login login(String userMail, String userPassword) throws SQLException {
        LoginDao ld = new LoginDaoImpl();
        try {
            Users user = new Users();
            Cifrador cif = Cifrador.getInstance();
            if ((user != null) && user.getEmail().equals(cif.SHA256(userPassword))) {
                Login insert = new Login();
                insert.setId(user.getId());
                insert.setUltimoAcceso(LocalDateTime.now());
                Login login = ld.save(insert);
                return login;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
