package br.com.dito.ditosdk.services;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.dito.ditosdk.constant.Constants;
import br.com.dito.ditosdk.exception.DitoSDKException;
import br.com.dito.ditosdk.interfaces.DitoSDKCallback;
import br.com.dito.ditosdk.util.NetworkUtil;

/**
 * Created by gabriel.araujo on 06/04/15.
 */
public class ReadNotification extends BaseService {

    public static void readNotification(String mensagem, DitoSDKCallback callback) throws DitoSDKException{
        if (verifyConfigure(callback)) {
            if (TextUtils.isEmpty(mensagem)){
                if (callback != null){
                    callback.onError(new DitoSDKException(Constants.Mensagens.PARAMETRO_OBRIGATORIO));
                }else{
                    throw new DitoSDKException(Constants.Mensagens.PARAMETRO_OBRIGATORIO);
                }
            }else{
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(mensagem, JsonObject.class);
                if (jsonObject.has("notification") && !TextUtils.isEmpty(jsonObject.get("notification").getAsString())){
                    String identifier = jsonObject.get("notification").getAsString();
                    JsonObject json = new JsonObject();
                    json.addProperty(Constants.Headers.PLATFORM_API_KEY, Constants.configure.getApiKey());
                    json.addProperty(Constants.Headers.SIGNATURE, Constants.configure.getSha1Signature());
                    json.addProperty(Constants.Headers.CHANNEL_TYPE, Constants.Headers.CHANNEL_TYPE_VALUE);
                    json.addProperty(Constants.Headers.TYPE, Constants.getParamNetworks());
                    json.addProperty(Constants.Headers.ID, Constants.getSocialId());
                    if (jsonObject.has("notification") && !TextUtils.isEmpty(jsonObject.get("notification").getAsString())){
                      String notificationLogId = jsonObject.get("notification_log_id").getAsString();
                      json.addProperty(Constants.Headers.NOTIFICATION_LOG_ID, notificationLogId);
                    }
                    NetworkUtil.requestPostMethod(Constants.getUrlReadNotification(identifier), json.toString(), callback);
                }else{
                    Log.e("DITO_SDK", Constants.Mensagens.JSON_FORA_FORMATO);
                    if (callback != null){
                        callback.onError(new DitoSDKException(Constants.Mensagens.JSON_FORA_FORMATO));
                    }
                }
            }
        }
    }


}
