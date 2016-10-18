package ioasys.com.br.sacapp.constants;

/**
 * Created by ioasys on 24/08/15.
 */
public class Constantes {

    public class callOnSharedPreference{
        public static final String PATH_APP = "path_app";
        public static final String JSON_TOKEN = "Json_token";
    }

    public class Services{
        public static final String URL_BASE = "http://108.168.172.3/wssacapp/api";
        public static final String NEW_TOKEN =  "/NewToken/{usuario}/{senha}";
        public static final String NEW_INTERACTION = "/newinteraction/{Token}/{app_id}/{Ani}/{Uuid}/{platform}/{version}/{model}";
        public static final String NEW_SESSION = "/newSession/{token}/{interaction_id}/{app_id}/{routing_id}/{channel_id}/{custom_info1}/{custom_info2}/{custom_info3}/{custom_info4}/{custom_info5}/{custom_info6}/{custom_info7}/{custom_info8}/{custom_info9}/{custom_info10}";
        public static final String CLIENT_SATISFACTION = "/getcsat/{Token}/{app_id}";
    }
}
