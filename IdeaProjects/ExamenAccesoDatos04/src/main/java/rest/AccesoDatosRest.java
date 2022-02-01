package rest;

import lombok.Data;
import model.Root;
import model.Users;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;


public interface AccesoDatosRest {

    @GET("users")
    Call<List<Users>> usersGetAll();


    // Obtener uno usuario por ID
    @GET("users/{id}")
    Call<Users> usersGetById(@Path("id") int id);

    @POST("users")
    Call<Users> usersPost(@Body Users usuarios);

    @DELETE("users/{id}")
    Call<Users> usersDelete(@Path("id") int id);

    @PUT("/comments/{id}")
    Call<Users> updateComment(@Path("id") int id, @Body Users comment);

    @PATCH("/comments/{id}")
    Call<Users> upgradeComment(@Path("id") int id, @Body Users comment);
}
