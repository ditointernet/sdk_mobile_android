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
    public static final String SERVICES_URL_BASE = "http://login.dev.plataformasocial.com.br/users/";
    public static final String EVENT = "event";
    public static final String EVENTS = "events";
    public static final String ORIGIN = "origin";
    public static final String USER_DATA = "user_data";
    public static final String CONTENT_TYPE = "application/json";
    public static final String EVENTS_BASE_URL = "http://events.dev.plataformasocial.com.br/users/";
    public static final String ENCODING = "encoding";
    public static final String ENCODING_TYPE = "base64";
    public static final String ACCESS_TOKEN = "access_token";

}
