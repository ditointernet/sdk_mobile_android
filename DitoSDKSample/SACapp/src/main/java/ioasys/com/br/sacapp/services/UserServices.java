package ioasys.com.br.sacapp.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import com.koushikdutta.async.future.FutureCallback;


import ioasys.com.br.sacapp.SACappApplication;
import ioasys.com.br.sacapp.constants.Constantes;
import ioasys.com.br.sacapp.services.model.NewTokenResponse;

/**
 * Created by ioasys on 24/08/15.
 */
public class UserServices {
    public interface OnInterface{
        void sucess();
        void erro(String erro);
    }

    public static void requestToken(NewTokenResponse tokenResponse, final OnInterface onInterface){
        JsonObject aux = new JsonObject();
        aux.add("", new Gson().toJsonTree(tokenResponse));

        Ion.with(SACappApplication.getInstance())
                .load(Constantes.Services.NEW_TOKEN)
                .setJsonObjectBody(aux)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null){
                            if (onInterface != null){
                                SACappApplication.getInstance().saveJsonLogin(result);
                                onInterface.sucess();
                            }
                        } else {
                            if (onInterface != null){
                                onInterface.erro(e.getMessage());
                            }
                        }
                    }
                });
    }
}
