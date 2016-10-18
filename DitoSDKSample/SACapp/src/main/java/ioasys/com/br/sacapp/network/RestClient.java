package ioasys.com.br.sacapp.network;

import com.squareup.okhttp.OkHttpClient;

import ioasys.com.br.sacapp.constants.Constantes;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by ioasys on 24/08/15.
 */
public class RestClient {
    private static RestApi REST_CLIENT;
    private static String ROOT = Constantes.Services.URL_BASE;

    static {
        setupRestClient();
    }

    public static RestApi get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ROOT).build();

        /*
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(ROOT)
                .setClient(new OkClient(new OkHttpClient()))
                .setRequestInterceptor(new SessionRequestInterceptor())
                .build();

                */

        REST_CLIENT = restAdapter.create(RestApi.class);
    }
}
