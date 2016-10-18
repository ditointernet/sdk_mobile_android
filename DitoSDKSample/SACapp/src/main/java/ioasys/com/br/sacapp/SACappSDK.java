package ioasys.com.br.sacapp;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import ioasys.com.br.sacapp.activities.MainScreen;
import ioasys.com.br.sacapp.exceptions.SACappSDKException;
import ioasys.com.br.sacapp.app.SACappSDKCallback;

/**
 * Created by Rodolfo on 19/08/2015.
 */
public class SACappSDK {

    /**
     * Somente para testes ainda.
     * @param context
     * @param json
     * @param callback
     * @throws SACappSDKException
     */
    public static void startProcess(Context context, String json, SACappSDKCallback callback) throws SACappSDKException{
        Intent intent = new Intent(context, MainScreen.class);
        context.startActivity(intent);
    }

    public static void startProcess(Context context, String user, String password, List<String> params, SACappSDKCallback callback){
        Intent intent = new Intent(context, MainScreen.class);
        context.startActivity(intent);
    }

}
