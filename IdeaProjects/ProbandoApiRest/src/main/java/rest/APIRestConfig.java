package rest;

public class APIRestConfig {
    public static final String API_URL = "https://jsonplaceholder.typicode.com/";

    private APIRestConfig() {

    }
    // Constructor del servicio con los elementos de la interfaz
    public static AccesoDatosRest getService() {
        System.out.println("API URL: " + API_URL);
        return RetrofitRestClient.getClient(API_URL).create(AccesoDatosRest.class);
    }
}
