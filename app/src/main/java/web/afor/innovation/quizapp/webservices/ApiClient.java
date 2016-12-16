package web.afor.innovation.quizapp.webservices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import web.afor.innovation.quizapp.Config.Constants;

/**
 * Created by Boukadi on 31/07/2016.
 */
public class ApiClient {

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
