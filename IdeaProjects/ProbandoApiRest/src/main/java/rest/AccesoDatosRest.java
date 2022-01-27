package rest;

import model.Users;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface AccesoDatosRest {

    @GET("users")
    Call<List<Users>> usersGetAll();
    // Obtener uno usuario por ID
    @GET("users/{id}")
    Call<Users> usersGetById(@Path("id") String id);

    @POST("users")
    Call<Users> usersPost(@Body Users usuarios);

    @DELETE("users/{id}")
    Call<Users> usersDelete(@Path("id") String id);
}
