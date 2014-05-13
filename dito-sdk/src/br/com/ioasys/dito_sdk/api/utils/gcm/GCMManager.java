package br.com.ioasys.dito_sdk.api.utils.gcm;

import android.content.Context;
import android.util.Log;
import br.com.ioasys.dito_sdk.interfaces.GCMListener;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMManager {
    private static GoogleCloudMessaging gcm;
    private static final String UNREGISTER_MSG_SUCCESS = "Device is unregistered";
    private static final String REGISTER_MSG_ERROR = "Something went wrong. Please try again later.";

    public static void registerOnGCM(Context context, String senderID, GCMListener listener) {
        if (gcm == null) {
            gcm = GoogleCloudMessaging.getInstance(context);
        }

        try {
            String registrationID = gcm.register(senderID);

            if (registrationID != null) {
                if (listener != null) {
                    listener.OnSuccess(registrationID);
                }
            } else {
                listener.OnFailure(REGISTER_MSG_ERROR);
            }
        } catch (Exception ex) {
            Log.e("REGISTER_GCM_ERROR", ex.getMessage(), ex);

            if (listener != null) {
                listener.OnFailure(ex.getMessage());
            }
        }
    }

    /**
     * Método para desativar recebimento de notificações no GCM
     * 
     * @param context
     *            Contexto da aplicação
     * @param listener
     *            Referência da interface <b>GCMListener<b> que será usada como
     *            callback da requisição
     */
    public static void unregisterOnGCM(Context context, GCMListener listener) {
        if (gcm == null) {
            gcm = GoogleCloudMessaging.getInstance(context);
        }

        try {
            gcm.unregister();

            if (listener != null) {
                listener.OnSuccess(UNREGISTER_MSG_SUCCESS);
            }
        } catch (Exception ex) {
            Log.e("UNREGISTER_GCM_ERROR", ex.getMessage(), ex);

            if (listener != null) {
                listener.OnFailure(ex.getMessage());
            }
        }
    }
}
