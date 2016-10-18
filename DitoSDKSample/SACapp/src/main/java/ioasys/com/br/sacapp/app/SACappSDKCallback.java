package ioasys.com.br.sacapp.app;

/**
 * Created by ioasys on 21/08/15.
 */
public interface SACappSDKCallback {
    void onSucess(String response);
    void onError(Exception ex);
}
