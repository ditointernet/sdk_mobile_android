package br.com.dito.ditosdk.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.dito.ditosdk.constant.Constants;
import br.com.dito.ditosdk.exception.DitoSDKException;
import br.com.dito.ditosdk.interfaces.DitoSDKCallback;
import br.com.dito.ditosdk.util.NetworkUtil;

/**
 * Created by gabriel.araujo on 07/04/15.
 */
public class TrackService extends BaseService {

    public static void track(Object data, DitoSDKCallback callback) throws DitoSDKException {
        if (NetworkUtil.isOnline()) {
            verifyToSendEventOffline();
            sendTracker(data, callback);
        } else {
            Constants.saveTrackToOffline(data);
            if (callback != null) {
                callback.onSuccess(Constants.Mensagens.RESPONSE_EVENT_OFFLINE);
            }
        }
    }

    public static void verifyToSendEventOffline() throws DitoSDKException {
        if (verifyTrackerOffline()) {
            for (int i = 0; i < Constants.getListTrackToOffline().size(); i++) {
                sendTracker(Constants.getListTrackToOffline().get(i), null);
            }
            Constants.cleanListTrackOffline();
        }
    }

    private static void sendTracker(Object data, DitoSDKCallback callback) throws DitoSDKException {
        if (verifyConfigure(callback)) {
            Gson gson = new Gson();
            JsonObject json = new JsonObject();
            json.addProperty(Constants.Headers.PLATFORM_API_KEY, Constants.configure.getApiKey());
            json.addProperty(Constants.Headers.SIGNATURE, Constants.configure.getSha1Signature());
            if (data instanceof String){
                json.addProperty(Constants.Headers.EVENT, (String) data);
            }else{
                json.addProperty(Constants.Headers.EVENT, gson.toJson(data));
            }
            if (!Constants.getNetworks().equalsIgnoreCase("reference")) {
                json.addProperty(Constants.Headers.TYPE, Constants.getParamNetworks());
            }

//            Log.e("DITO_SDK_TRACKER", json.toString());
            NetworkUtil.requestPostMethod(Constants.getUrlEvent(), json.toString(), callback);
        }
    }

    private static boolean verifyTrackerOffline() {
        if (Constants.getListTrackToOffline() != null) {
            return true;
        } else {
            return false;
        }
    }
}
