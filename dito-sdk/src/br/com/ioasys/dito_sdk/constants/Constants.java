package br.com.ioasys.dito_sdk.constants;

/**
 * Classe contendo todas as constantes dos cabeçalhos usados nas requisições do
 * SDK, assim como, as URLs tronco dos serviços
 * 
 * @author iOasys
 * @since 14-04-14
 * @version 1.0
 * 
 */
public final class Constants {
    private Constants() {
    }

    public static final String NETWORK_NAME = "network_name";
    public static final String PLATFORM_API_KEY = "platform_api_key";
    public static final String SIGNATURE = "signature";
    public static final String EVENT = "event";
    public static final String EVENTS = "events";
    public static final String ORIGIN = "origin";
    public static final String USER_DATA = "user_data";
    public static final String CONTENT_TYPE = "application/json";
    public static final String ENCODING = "encoding";
    public static final String ENCODING_TYPE = "base64";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String TOKEN = "token";
    public static final String REFERENCE = "reference";
    public static final String IDENTIFIER = "identifier";

    public static enum NETWORKS {
        FACEBOOK("facebook"), TWITTER("twitter"), GOOGLE_PLUS("plus"), PORTAL("portal");

        private String networkName;

        private NETWORKS(String networkName) {
            this.networkName = networkName;
        }

        public String getValue() {
            return networkName;
        }
    }

    public static enum NETWORKS_ACRONYMS {
        FACEBOOK("fb"), TWITTER("tw"), GOOGLE_PLUS("pl"), PORTAL("pt");

        private String networkAcronym;

        private NETWORKS_ACRONYMS(String networkAcronym) {
            this.networkAcronym = networkAcronym;
        }

        public String getValue() {
            return networkAcronym;
        }
    }

    public static String getServicesURLBase(boolean inDevMode) {
        if (inDevMode) {
            return "http://login.dev.plataformasocial.com.br/users/";
        } else {
            return "http://login.plataformasocial.com.br/users/";
        }
    }

    public static String getEventsURLBase(boolean inDevMode) {
        if (inDevMode) {
            return "http://events.dev.plataformasocial.com.br/users/";
        } else {
            return "http://events.plataformasocial.com.br/users/";
        }
    }

    public static String getNotificationUserURLBase(boolean inDevMode) {
        if (inDevMode) {
            return "http://notification.dev.plataformasocial.com.br/users/";
        } else {
            return "http://notification.plataformasocial.com.br/users/";
        }
    }

    public static String getNotificationURLBase(boolean inDevMode) {
        if (inDevMode) {
            return "http://notification.dev.plataformasocial.com.br/notifications/";
        } else {
            return "http://notification.plataformasocial.com.br/notifications/";
        }
    }

    public static final String NOTIFICATION_RECEIVED_ERROR_MSG = "Notification identifier not found or invalid";
}
