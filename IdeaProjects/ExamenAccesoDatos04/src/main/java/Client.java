import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Tareas;
import model.Users;
import rest.Config;
import rest.AccesoDatosRest;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Client {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    AccesoDatosRest restService = Config.getService();

    public void runUsers() {
        System.out.println("Cliente REST EndPoint Users");
        usersGetAll();
        usersGetById(1827);
        usersPost();
        usersDelete(5);
    }

    public List<Users> usersGetAll() {
        List<Users> comentarios = new ArrayList<>();
        try {
            Response<List<Users>> response = restService.usersGetAll().execute();

            if (response.isSuccessful()) {
                comentarios = response.body();
            } else {
                throw new Exception("no se ha podido resuperar los usuarios");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comentarios;
    }

    private Users usersGetById(int id) {
        System.out.println("GET users By id");
        Users usuario = new Users();
        try {
            Response<Users> response = restService.usersGetById(id).execute();
            // La hemos obtenido correctamente
            if (response.isSuccessful() && response.body() != null) {
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
        /*
        Me he quedado atrancado porque no sabia como poder filtrar por el data y eso me entretenido mucho, en los repetidos ejemplos que hice no cai
        en hacer con el data, todos los JSON me dejabba sacarlo como objeto pero no he sico capaz, es una p*tada, porque el resto si que sabia hacerlo,
        no digo que lo fuese a sacar porque posiblemente siempre te quedes pillado en algo pero no he tenido suerte en ese sentido
         */
        System.out.println("POST Users");
        Set<Tareas> tareas = new HashSet<Tareas>();
        Tareas tarea = new Tareas(1, new Users(1826), "54406606Z", false);
        Tareas tarea2 = new Tareas(2, new Users(1826), "54406606Z", true);
        tareas.add(tarea);
        tareas.add(tarea2);
        Users user = new Users();
        user.setId(1826);
        user.setName("Perez");
        user.setEmail("@gmail.com");
        user.setGender("Famale");
        user.setStatus(true);
        user.setTareas(tareas);
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
