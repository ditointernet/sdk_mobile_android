package br.com.dito.ditosdk.constant;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.dito.ditosdk.model.Configure;
import br.com.dito.ditosdk.model.DitoCredentials;
import br.com.dito.ditosdk.util.SharedPreferencesUtil;

/**
 * Created by gabriel.araujo on 01/04/15.
 */
public class Constants {

    public static Context context;
    public static EnvironmentType DEV_MODE = EnvironmentType.PRODUCTION;
    public static Configure configure;
    public static DitoCredentials credentials;
    public static String accessToken;
    private static List<Object> listTrackOffline;


    public class SharedPreferences {
        public static final String PATH = "dito_sdk_path";
        public static final String LIST_TRACK_OFFLINE = "list_track_offline";
    }

    public class Headers {
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String CONTENT_TYPE_VALUE = "application/json";
        public static final String PLATFORM_API_KEY = "platform_api_key";
        public static final String SIGNATURE = "sha1_signature";
        public static final String ACCESS_TOKEN = "access_token";
        public static final String USER_DATA = "user_data";
        public static final String TOKEN = "token";
        public static final String PLATFORM = "platform";
        public static final String PLATFORM_VALUE = "Android";
        public static final String TYPE = "id_type";
        public static final String CHANNEL_TYPE = "channel_type";
        public static final String CHANNEL_TYPE_VALUE = "mobile";
        public static final String EVENT = "event";
        public static final String ID = "id";
        public static final String USER_AGENT = "User-Agent";
        public static final String USER_AGENT_MOBILE = "Android";
    }

    public class Mensagens {
        public static final String CONFIGURE_NOT_FOUND = "{\"message\":\"Para continuar é necessário fornecer \\\"SUA_API_KEY\\\" e/ou \\\"SUA_SECRET\\\"\"}";
        public static final String ERROR_REFERENCE = "{\"message\" : \"Não é possível utilizar o atributo \\\"reference\\\" para esta requisição.\"}";
        public static final String PARAMETRO_OBRIGATORIO = "O parâmetro \\\"message\\\" é obrigatório.";
        public static final String JSON_FORA_FORMATO = "{\"message\" : \"O json fornecido não está no formato esperado.\"}";
        public static final String RESPONSE_EVENT_OFFLINE = "{\"message\":\"O evento foi armazenado para ser enviado mais tarde, devido a falta de conexão com a internet.\",\"success\":\"true\"}";
        public static final String MODULE_ERROR = "{\"message\":\"É obrigatório informar o módulo para continuar.\",\"success\":\"false\"}";
        public static final String NOT_FOUND_CONTEXT = "{\"message\":\"É obrigatório informar o contexto da aplicação.\",\"success\":\"false\"}";
        public static final String DATA_PORTAL_ERROR = "{\"message\":\"O parâmetro \\\"data\\\" só pode ser utilizado para contas do tipo \\\"PORTAL\\\".\",\"success\":\"false\"}";
        public static final String NOT_FOUND_PARAMETER = "{\"message\":\"Para utilizar este método e necessário fornecer um dos parâmetros\"}";
    }

    public enum EnvironmentType {
        PRODUCTION, DEVELOPMENT;
    }

    public enum AccountsType {
        FACEBOOK, TWITTER, GOOGLE_PLUS, PORTAL
    }

    public enum HttpTypes {
        PUT, GET, DELETE, POST
    }

    public static String getUrlIdentify() {
        return getServicesURLBase() + getNetworks() + "/" + getSocialId() + "/signup";
    }

    public static String getUrlRegisterToken() {
        return getNotificationUserURLBase() + getSocialId() + "/mobile-tokens";
    }

    public static String getUrlUnregisterToken() {
        return getNotificationUserURLBase() + getSocialId() + "/mobile-tokens/disable";
    }

    public static String getUrlReadNotification(String identifier) {
        return getNotificationURLBase() + identifier + "/open";
    }

    public static String getUrlAlias() {
        return getServicesURLBase() + getSocialId() + "/link";
    }

    public static String getUrlUnalias() {
        return getServicesURLBase() + getSocialId() + "/unlink";
    }

    public static String getUrlEvent() {
        return getEventsURLBase() + getSocialId();
    }

    public static String getUrlRequest() {
        if (DEV_MODE == EnvironmentType.DEVELOPMENT) {
            return "http://%s.dev.plataformasocial.com.br%s";
        } else {
            return "https://%s.plataformasocial.com.br%s";
        }
    }

    private static String getServicesURLBase() {
        if (DEV_MODE == EnvironmentType.DEVELOPMENT) {
            return "http://login.dev.plataformasocial.com.br/users/";
        } else {
            return "https://login.plataformasocial.com.br/users/";
        }
    }

    private static String getEventsURLBase() {
        if (DEV_MODE == EnvironmentType.DEVELOPMENT) {
            return "http://events.dev.plataformasocial.com.br/users/";
        } else {
            return "https://events.plataformasocial.com.br/users/";
        }
    }

    private static String getNotificationUserURLBase() {
        if (DEV_MODE == EnvironmentType.DEVELOPMENT) {
            return "http://notification.dev.plataformasocial.com.br/users/";
        } else {
            return "https://notification.plataformasocial.com.br/users/";
        }
    }

    private static String getNotificationURLBase() {
        if (DEV_MODE == EnvironmentType.DEVELOPMENT) {
            return "http://notification.dev.plataformasocial.com.br/notifications/";
        } else {
            return "https://notification.plataformasocial.com.br/notifications/";
        }
    }

    public static String getNetworks() {
        if (!TextUtils.isEmpty(credentials.getId())) {
            return "portal";
        } else if (!TextUtils.isEmpty(credentials.getFacebookID())) {
            return "facebook";
        } else if (!TextUtils.isEmpty(credentials.getGooglePlusID())) {
            return "plus";
        } else if (!TextUtils.isEmpty(credentials.getTwitterID())) {
            return "twitter";
        } else if (!TextUtils.isEmpty(credentials.getReference())) {
            return "reference";
        } else {
            return "";
        }
    }

    public static String getParamNetworks() {
        if (!TextUtils.isEmpty(credentials.getId())) {
            return "id";
        } else if (!TextUtils.isEmpty(credentials.getFacebookID())) {
            return "facebook_id";
        } else if (!TextUtils.isEmpty(credentials.getGooglePlusID())) {
            return "google_plus_id";
        } else if (!TextUtils.isEmpty(credentials.getTwitterID())) {
            return "twitter_id";
        } else if (!TextUtils.isEmpty(credentials.getReference())) {
            return "reference";
        } else {
            return "";
        }
    }

    public static String getSocialId() {
        if (getNetworks().equalsIgnoreCase("portal")) {
            return credentials.getId();
        } else if (getNetworks().equalsIgnoreCase("facebook")) {
            return credentials.getFacebookID();
        } else if (getNetworks().equalsIgnoreCase("plus")) {
            return credentials.getGooglePlusID();
        } else if (getNetworks().equalsIgnoreCase("twitter")) {
            return credentials.getTwitterID();
        } else {
            return "";
        }
    }

    public static void saveTrackToOffline(Object data) {
        if (listTrackOffline == null) {
            listTrackOffline = new ArrayList<>();
        }

        listTrackOffline.add(data);
        String json = new Gson().toJson(listTrackOffline);
        SharedPreferencesUtil.write(Constants.context, SharedPreferences.PATH, SharedPreferences.LIST_TRACK_OFFLINE, json);
    }

    public static List<Object> getListTrackToOffline() {
        String json = SharedPreferencesUtil.read(Constants.context, SharedPreferences.PATH, SharedPreferences.LIST_TRACK_OFFLINE, null);

        if (!TextUtils.isEmpty(json)) {
            Type listType = new TypeToken<List<Object>>() {
            }.getType();
            listTrackOffline = new Gson().fromJson(json, listType);
        }

        return listTrackOffline;
    }

    public static void cleanListTrackOffline() {
        listTrackOffline.clear();
        listTrackOffline = null;
        SharedPreferencesUtil.remove(Constants.context, SharedPreferences.PATH, SharedPreferences.LIST_TRACK_OFFLINE);
    }

}
