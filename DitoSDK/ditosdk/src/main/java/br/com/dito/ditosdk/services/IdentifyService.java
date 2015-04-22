package br.com.dito.ditosdk.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.dito.ditosdk.constant.Constants;
import br.com.dito.ditosdk.exception.DitoSDKException;
import br.com.dito.ditosdk.interfaces.DitoSDKCallback;
import br.com.dito.ditosdk.util.NetworkUtil;

/**
 * Created by gabriel.araujo on 06/04/15.
 */
public class IdentifyService extends BaseService {


    public static void identify(String accessToken, Object data, DitoSDKCallback callback) throws DitoSDKException {
        if (verifyConfigure(callback) && verifyReference()) {
            JsonObject jsonIdentify = new JsonObject();

            if (Constants.getNetworks().equalsIgnoreCase("portal")) {
                jsonIdentify.addProperty("id", Constants.getSocialId());
            } else {
                jsonIdentify.addProperty(Constants.getParamNetworks(), Constants.getSocialId());
                jsonIdentify.addProperty(Constants.Headers.ACCESS_TOKEN, accessToken);
            }

            jsonIdentify.addProperty(Constants.Headers.PLATFORM_API_KEY, Constants.configure.getApiKey());
            jsonIdentify.addProperty(Constants.Headers.SIGNATURE, Constants.configure.getSha1Signature());
            if (data instanceof String){
                jsonIdentify.addProperty(Constants.Headers.USER_DATA, (String) data);
            }else{
                jsonIdentify.addProperty(Constants.Headers.USER_DATA, new Gson().toJson(data));
            }


//            Log.e("DITO_SDK_IDENTIFY", jsonIdentify.toString());
            NetworkUtil.requestPostMethod(Constants.getUrlIdentify(), jsonIdentify.toString(), callback);
        }
    }
}
