package br.com.dito.ditosdk.services;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.util.List;

import br.com.dito.ditosdk.constant.Constants;
import br.com.dito.ditosdk.exception.DitoSDKException;
import br.com.dito.ditosdk.interfaces.DitoSDKCallback;
import br.com.dito.ditosdk.model.DitoAccount;
import br.com.dito.ditosdk.util.NetworkUtil;

/**
 * Created by gabriel.araujo on 07/04/15.
 */
public class AliasService extends BaseService {

    public static void alias(List<DitoAccount> ditoAccountList, DitoSDKCallback callback) throws DitoSDKException{
        if (verifyConfigure(callback) && verifyReference()) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.Headers.PLATFORM_API_KEY, Constants.configure.getApiKey());
            json.addProperty(Constants.Headers.SIGNATURE, Constants.configure.getSha1Signature());
            json.addProperty(Constants.Headers.TYPE, Constants.getParamNetworks());
            JsonObject jsonAccounts = new JsonObject();
            for (int i = 0; i < ditoAccountList.size(); i++){
                if (ditoAccountList.get(i).getType() == Constants.AccountsType.PORTAL && ditoAccountList.get(i).getData() != null){
                    Gson gson = new Gson();
                    String jsonDataString = gson.toJson(ditoAccountList.get(i).getData());
                    JsonObject jsonData;
                    try{
                        jsonData = gson.fromJson(jsonDataString, JsonObject.class);
                    }catch (JsonSyntaxException ex){
                        if (callback != null){
                            callback.onError(new DitoSDKException(Constants.Mensagens.DATA_PORTAL_ERROR));
                            return;
                        }else{
                            throw new DitoSDKException(Constants.Mensagens.DATA_PORTAL_ERROR);
                        }
                    }
                    if (TextUtils.isEmpty(ditoAccountList.get(i).getId())){
                        if (callback != null){
                            callback.onError(new DitoSDKException(Constants.Mensagens.NOT_FOUND_PARAMETER));
                            return;
                        }else{
                            throw new DitoSDKException(Constants.Mensagens.NOT_FOUND_PARAMETER);
                        }
                    }
                    jsonData.addProperty("id", ditoAccountList.get(i).getId());
                    jsonAccounts.add("portal", jsonData);
                }else if (ditoAccountList.get(i).getType() == Constants.AccountsType.PORTAL && ditoAccountList.get(i).getData() == null){
                    if (callback != null){
                        callback.onError(new DitoSDKException(Constants.Mensagens.DATA_PORTAL_ERROR));
                        return;
                    }else{
                        throw new DitoSDKException(Constants.Mensagens.DATA_PORTAL_ERROR);
                    }
                }else{
                    if (TextUtils.isEmpty(ditoAccountList.get(i).getId()) || TextUtils.isEmpty(ditoAccountList.get(i).getAccessToken())){
                        if (callback != null){
                            callback.onError(new DitoSDKException(Constants.Mensagens.NOT_FOUND_PARAMETER));
                            return;
                        }else{
                            throw new DitoSDKException(Constants.Mensagens.NOT_FOUND_PARAMETER);
                        }
                    }
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("id", ditoAccountList.get(i).getId());
                    jsonObject.addProperty("access_token", ditoAccountList.get(i).getAccessToken());
                    switch (ditoAccountList.get(i).getType()){
                        case FACEBOOK:
                            jsonAccounts.add("facebook", jsonObject);
                            break;
                        case TWITTER:
                            jsonAccounts.add("twitter", jsonObject);
                            break;
                        case GOOGLE_PLUS:
                            jsonAccounts.add("plus", jsonObject);
                            break;
                    }
                }
            }

            json.add("accounts", jsonAccounts);
//            Log.e("DITO_SDK_ALIAS", json.toString());
            NetworkUtil.requestPostMethod(Constants.getUrlAlias(), json.toString(), callback);
        }
    }

    public static void unalias(List<DitoAccount> ditoAccountList, DitoSDKCallback callback) throws DitoSDKException{
        if (verifyConfigure(callback) && verifyReference()) {
            JsonObject json = new JsonObject();
            json.addProperty(Constants.Headers.PLATFORM_API_KEY, Constants.configure.getApiKey());
            json.addProperty(Constants.Headers.SIGNATURE, Constants.configure.getSha1Signature());
            json.addProperty(Constants.Headers.TYPE, Constants.getParamNetworks());
            JsonObject jsonAccounts = new JsonObject();
            for (int i = 0; i < ditoAccountList.size(); i++){
                JsonObject jsonObject = new JsonObject();
                if (TextUtils.isEmpty(ditoAccountList.get(i).getId())){
                    if (callback != null){
                        callback.onError(new DitoSDKException(Constants.Mensagens.NOT_FOUND_PARAMETER));
                        return;
                    }else{
                        throw new DitoSDKException(Constants.Mensagens.NOT_FOUND_PARAMETER);
                    }
                }
                jsonObject.addProperty("id", ditoAccountList.get(i).getId());
                switch (ditoAccountList.get(i).getType()){
                    case FACEBOOK:
                        jsonAccounts.add("facebook", jsonObject);
                        break;
                    case TWITTER:
                        jsonAccounts.add("twitter", jsonObject);
                        break;
                    case GOOGLE_PLUS:
                        jsonAccounts.add("plus", jsonObject);
                        break;
                    case PORTAL:
                        jsonAccounts.add("portal", jsonObject);
                        break;
                }
            }

            json.add("accounts", jsonAccounts);
//            Log.e("DITO_SDK_UNALIAS", json.toString());
            NetworkUtil.requestPostMethod(Constants.getUrlUnalias(), json.toString(), callback);
        }
    }
}
