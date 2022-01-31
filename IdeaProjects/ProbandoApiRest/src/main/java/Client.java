import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Users;
import rest.APIRestConfig;
import rest.AccesoDatosRest;
import retrofit2.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Client {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    AccesoDatosRest restService = APIRestConfig.getService();

    public void runUsers() {
        System.out.println("Cliente REST EndPoint Users");
        usersGetAll();
        usersGetById(2);
        usersPost();
        usersDelete(5);
    }

    public List<Users> usersGetAll() {
        System.out.println("GET ALL Users");
        List<Users> usuarios = new ArrayList<Users>();

        try {
            Response<List<Users>> response = restService.usersGetAll().execute();
            // La hemos obtenido correctamente
            if (response.isSuccessful()) {
                System.out.println("Request Done! - Código: " + response.code() +
                        " - Datos obtenidos: " + response.body().size());
                System.out.println("Resultado de GET ALL");
                usuarios = (response.body());
                String json = gson.toJson(usuarios);
                //System.out.println(json);


            } else {
                System.out.println("Error: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    private Users usersGetById(int id) {
        System.out.println("GET users By id");
        Users usuario = new Users();
        try {
            Response<Users> response = restService.usersGetById(id).execute();
            // La hemos obtenido correctamente
            if (response.isSuccessful() && response.body() != null) {
                System.out.println("Request Done! - Código: " + response.code());
                System.out.println("Resultado de GET Users");
                usuario = (response.body());
                String json = gson.toJson(usuario);
                System.out.println(json);

            } else {
                System.out.println("Error: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    private void usersPost() {
        System.out.println("POST Users");
        Users user = new Users();
        user.setName("Perez");
        user.setUsername("Pepito");
        user.setEmail("@gmail.com");
        user.setPhone("676921395");
        user.setWebsite("alfredo.com");
        try {
            Response<Users> response = restService.usersPost(user).execute();
            // La hemos obtenido correctamente
            if (response.isSuccessful() && response.body() != null) {
                System.out.println("Request Done! - Código: " + response.code());
                System.out.println("Resultado de POST");
                Users result = (response.body());
                String json = gson.toJson(result);
                System.out.println(json);
            } else {
                System.out.println("Error: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void usersDelete(int id) {
        System.out.println("DELETE Users");
        try {
            Response<Users> response = restService.usersDelete(id).execute();
            // La hemos obtenido correctamente
            if (response.isSuccessful() && response.body() != null) {
                System.out.println("Request Done! - Código: " + response.code());
                System.out.println("Resultado de DELETE");
                Users result = (response.body());
                String json = gson.toJson(result);
                System.out.println(json);
            } else {
                System.out.println("Error: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
