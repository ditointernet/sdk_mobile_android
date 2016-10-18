package br.com.dito.ditosdk.interfaces;

/**
 * Created by gabriel.araujo on 01/04/15.
 */
public interface DitoSDKCallback {
    void onSuccess(String response);
    void onError(Exception ex);
}
