package ioasys.com.br.sacapp;

import android.app.Activity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import ioasys.com.br.sacapp.constants.Constantes;
import ioasys.com.br.sacapp.utils.SharedPreferencesUtil;

/**
 * Created by ioasys on 24/08/15.
 */
public class SACappApplication extends Activity{
    private static SACappApplication mInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInstance = this;
    }

    public static SACappApplication getInstance(){
        return mInstance;
    }

    public void saveJsonLogin(JsonObject jsonObject){
        if (jsonObject != null){
            SharedPreferencesUtil.write(mInstance, Constantes.callOnSharedPreference.PATH_APP,
                    Constantes.callOnSharedPreference.JSON_TOKEN,
                    new Gson().toJson(jsonObject));
        }
    }
}
