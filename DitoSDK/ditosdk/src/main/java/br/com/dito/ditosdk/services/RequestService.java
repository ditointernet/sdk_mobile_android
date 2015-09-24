package br.com.dito.ditosdk.services;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import br.com.dito.ditosdk.constant.Constants;
import br.com.dito.ditosdk.exception.DitoSDKException;
import br.com.dito.ditosdk.interfaces.DitoSDKCallback;
import br.com.dito.ditosdk.util.NetworkUtil;

/**
 * Created by gabriel.araujo on 08/04/15.
 */
public class RequestService {

    public static void request(String module, String path, HashMap<String, Object> params, Constants.HttpTypes requestType, DitoSDKCallback callback) throws DitoSDKException {
        if (verifyModule(module, callback)) {
            StringBuilder urlBuilder = new StringBuilder();
            if (!TextUtils.isEmpty(path)) {
                if (path.startsWith("/")) {
                    urlBuilder.append(String.format(Constants.getUrlRequest(), module, path));
                } else {
                    urlBuilder.append(String.format(Constants.getUrlRequest(), module, "/" + path));
                }
            } else {
                urlBuilder.append(String.format(Constants.getUrlRequest(), module, ""));
            }

            if (requestType == null){
                sendRequestGet(urlBuilder, params, callback);
            }else{
                if (requestType == Constants.HttpTypes.GET) {
                    sendRequestGet(urlBuilder, params, callback);
                } else {
                    sendOtherRequest(urlBuilder, params, requestType, callback);
                }
            }
        }
    }

    private static void sendRequestGet(StringBuilder url, HashMap<String, Object> params, DitoSDKCallback callback) throws DitoSDKException {
        if (!url.toString().endsWith("?")) {
            url.append("?");
        }

        url.append(Constants.Headers.PLATFORM_API_KEY + "=" + Constants.configure.getApiKey() + "&");
        url.append(Constants.Headers.SIGNATURE + "=" + Constants.configure.getSha1Signature() + "&");
        if (params != null && params.size() > 0) {
            Set<Map.Entry<String, Object>> entries = params.entrySet();
            for (Map.Entry<String, Object> entrie : entries) {
                Object object = entrie.getValue();
                if (!(object instanceof String)) {
                    Gson gson = new Gson();
                    url.append(entrie.getKey() + "=" + gson.toJson(object).toString().replaceAll(" ","%20") + "&");
                } else {
                    url.append(entrie.getKey() + "=" + entrie.getValue() + "&");
                }
            }
        }

        if (url.toString().endsWith("&")) {
            url.deleteCharAt(url.toString().length() - 1);
        }

        NetworkUtil.requestGetMethod(url.toString(), callback);
    }

    private static void sendOtherRequest(StringBuilder url, HashMap<String, Object> params, Constants.HttpTypes requestType, DitoSDKCallback callback) throws DitoSDKException {
        JsonObject json = new JsonObject();
        json.addProperty(Constants.Headers.PLATFORM_API_KEY, Constants.configure.getApiKey());
        json.addProperty(Constants.Headers.SIGNATURE, Constants.configure.getSha1Signature());
        Set<Map.Entry<String, Object>> entries = params.entrySet();
        for (Map.Entry<String, Object> entrie : entries) {
            Object object = entrie.getValue();
            if (!(object instanceof String)) {
                Gson gson = new Gson();
                json.addProperty(entrie.getKey(), gson.toJson(object));
            } else {
                json.addProperty(entrie.getKey(), (String) entrie.getValue());
            }

        }

        switch (requestType) {
            case POST:
                NetworkUtil.requestPostMethod(url.toString(), json.toString(), callback);
                break;
            case PUT:
                NetworkUtil.requestPutMethod(url.toString(), json.toString(), callback);
                break;
            case DELETE:
                NetworkUtil.requestDeleteMethod(url.toString(), json.toString(), callback);
                break;
        }

    }

    private static boolean verifyModule(String module, DitoSDKCallback callback) throws DitoSDKException {
        if (TextUtils.isEmpty(module)) {
            if (callback != null) {
                callback.onError(new DitoSDKException(Constants.Mensagens.MODULE_ERROR));
            } else {
                throw new DitoSDKException(Constants.Mensagens.MODULE_ERROR);
            }
            return false;
        }
        return true;
    }
}
