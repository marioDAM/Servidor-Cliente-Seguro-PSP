import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Users;
import rest.APIRestConfig;
import rest.AccesoDatosRest;
import retrofit2.Response;
import java.io.IOException;
import java.util.List;

public class RestClientDemo {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    AccesoDatosRest restService = APIRestConfig.getService();

    public void runUsers() {
        System.out.println("Cliente REST EndPoint Users");
        usersGetAll();
        usersGetById();
        usersPost();
        usersDelete();
    }

    private void usersGetAll() {
        System.out.println("GET ALL Users");

        try {
            Response<List<Users>> response = restService.usersGetAll().execute();
            // La hemos obtenido correctamente
            if (response.isSuccessful()) {
                System.out.println("Request Done! - Código: " + response.code() +
                        " - Datos obtenidos: " + response.body().size());
                System.out.println("Resultado de GET ALL");
                List<Users> result = (response.body());
                String json = gson.toJson(result);
                System.out.println(json);


            } else {
                System.out.println("Error: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void usersGetById() {
        System.out.println("GET users By id");
        try {
            Response<Users> response = restService.usersGetById("2").execute();
            // La hemos obtenido correctamente
            if (response.isSuccessful() && response.body() != null) {
                System.out.println("Request Done! - Código: " + response.code());
                System.out.println("Resultado de GET Users");
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

    private void usersDelete() {
        System.out.println("DELETE Users");
        try {
            Response<Users> response = restService.usersDelete("5").execute();
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
