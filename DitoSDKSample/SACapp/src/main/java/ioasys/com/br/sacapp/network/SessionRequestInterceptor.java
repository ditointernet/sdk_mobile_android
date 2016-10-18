package ioasys.com.br.sacapp.network;


import retrofit.RequestInterceptor;


/**
 * Created by Soham Banerjee on 13/3/15.
 */


public class SessionRequestInterceptor implements RequestInterceptor {
    private static final String TAG = SessionRequestInterceptor.class.getSimpleName();


    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("Authorization", "Token token=55c0e0757cd3edf655a5d7c6");//you can add header here if you need in your api
        request.addHeader("X-Pertoo-School-Key", "EscolaRodolfo36805");
        request.addHeader("X-Pertoo-Profile-ID", "55b908c9c31ebed82369c323");
    }
}
