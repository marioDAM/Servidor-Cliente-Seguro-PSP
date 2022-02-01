package rest;

public class Config {
    public static final String API_URL = "https://gorest.co.in/public/v1/";

    private Config() {

    }
    // Constructor del servicio con los elementos de la interfaz
    public static AccesoDatosRest getService() {
        System.out.println("API URL: " + API_URL);
        return RetrofitRestClient.getClient(API_URL).create(AccesoDatosRest.class);
    }
}
