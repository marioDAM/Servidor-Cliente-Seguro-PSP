package rest;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRestClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String url) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    //.addConverterFactory(JacksonConverterFactory.create())
                    //.addConverterFactory(MoshiConverterFactory.create());
                    .build();
        }
        return retrofit;
    }
}