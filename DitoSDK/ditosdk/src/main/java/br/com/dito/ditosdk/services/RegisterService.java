package br.com.dito.ditosdk.services;

import com.google.gson.JsonObject;

import br.com.dito.ditosdk.constant.Constants;
import br.com.dito.ditosdk.exception.DitoSDKException;
import br.com.dito.ditosdk.interfaces.DitoSDKCallback;
import br.com.dito.ditosdk.util.NetworkUtil;

/**
 * Created by gabriel.araujo on 06/04/15.
 */
public class RegisterService extends BaseService {


    public static void registerToken(String token, DitoSDKCallback callback) throws DitoSDKException {
        if (verifyConfigure(callback)) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.Headers.PLATFORM_API_KEY, Constants.configure.getApiKey());
            json.addProperty(Constants.Headers.SIGNATURE, Constants.configure.getSha1Signature());
            json.addProperty(Constants.Headers.TOKEN, token);
            json.addProperty(Constants.Headers.PLATFORM, Constants.Headers.PLATFORM_VALUE);
            json.addProperty(Constants.Headers.TYPE, Constants.getParamNetworks());

//            Log.e("DITO_SDK_REGISTER_TOKEN", json.toString());
            NetworkUtil.requestPostMethod(Constants.getUrlRegisterToken(), json.toString(), callback);
        }
    }

    public static void unregisterToken(String token, DitoSDKCallback callback) throws DitoSDKException {
        if (verifyConfigure(callback)) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.Headers.PLATFORM_API_KEY, Constants.configure.getApiKey());
            json.addProperty(Constants.Headers.SIGNATURE, Constants.configure.getSha1Signature());
            json.addProperty(Constants.Headers.TOKEN, token);
            json.addProperty(Constants.Headers.PLATFORM, Constants.Headers.PLATFORM_VALUE);
            json.addProperty(Constants.Headers.TYPE, Constants.getParamNetworks());

//            Log.e("DITO_SDK_UNREGISTER_TOKEN", json.toString());
            NetworkUtil.requestPostMethod(Constants.getUrlUnregisterToken(), json.toString(), callback);
        }
    }
}
