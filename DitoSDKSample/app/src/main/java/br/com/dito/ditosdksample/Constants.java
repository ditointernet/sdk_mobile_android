package br.com.dito.ditosdksample;

/**
 * Created by gabriel.araujo on 08/04/15.
 */
public class Constants {

    public static final String API_KEY = "MjAxNS0wMy0zMSAxMToxOToyNCAtMDMwME1vYmlsZSBTREtzMTI4";
    public static final String API_SECRET = "vutpDmAIUdMfVQgcGiGanCun4opBVRUJoqIIzyGi";


    public class JSON_STATIC_REQUEST{
        public static final String JSON_IDENTIFY = "{\"data_nascimento\":\"1993-03-26\",\"email\":\"gabrielaraujo@ioasys.com.br\",\"name\":\"Gabriel Araujo\"}";
        public static final String JSON_READ_NOTIFICATION = "{\"notification\":\"526\",\"details\":{\"link\":\"\",\"message\":\"\"}}";
        public static final String JSON_TRACK = "{\"action\":\"Evento DitoSDKSample - Android\",\"data\":{\"name\":\"DitoSDKSample - Android\"},\"revenue\":\"100.00\"}";
    }

    public class JSON_STATIC_VIEW{
        public static final String JSON_IDENTIFY = "{\"id\":\"123\",\"platform_api_key\":\"MjAxNS0wMy0zMSAxMToxOToyNCAtMDMwME1vYmlsZSBTREtzMTI4\",\"sha1_signature\":\"29feaa6fd8a6ba7ca745f9083e3d3e07c2107137\",\"user_data\":{\"data_nascimento\":\"1993-03-26\",\"email\":\"gabrielaraujo@ioasys.com.br\",\"name\":\"Gabriel Araujo\"}}";
        public static final String JSON_REGISTER_TOKEN = "{\"platform_api_key\":\"MjAxNS0wMy0zMSAxMToxOToyNCAtMDMwME1vYmlsZSBTREtzMTI4\",\"sha1_signature\":\"29feaa6fd8a6ba7ca745f9083e3d3e07c2107137\",\"token\":\"12094253890\",\"platform\":\"Android\",\"id_type\":\"id\"}";
        public static final String JSON_UNREGISTER_TOKEN = "{\"platform_api_key\":\"MjAxNS0wMy0zMSAxMToxOToyNCAtMDMwME1vYmlsZSBTREtzMTI4\",\"sha1_signature\":\"29feaa6fd8a6ba7ca745f9083e3d3e07c2107137\",\"token\":\"12094253890\",\"platform\":\"Android\",\"id_type\":\"id\"}";
        public static final String JSON_READ_NOTIFICATION = "{\"platform_api_key\":\"MjAxNS0wMy0zMSAxMToxOToyNCAtMDMwME1vYmlsZSBTREtzMTI4\",\"sha1_signature\":\"29feaa6fd8a6ba7ca745f9083e3d3e07c2107137\",\"channel_type\":\"mobile\",\"id_type\":\"id\",\"id\":\"123\"}\n";
        public static final String JSON_ALIAS = "{\"platform_api_key\":\"MjAxNS0wMy0zMSAxMToxOToyNCAtMDMwME1vYmlsZSBTREtzMTI4\",\"sha1_signature\":\"29feaa6fd8a6ba7ca745f9083e3d3e07c2107137\",\"id_type\":\"id\",\"accounts\":{\"facebook\":{\"id\":\"1574730112774792\",\"access_token\":\"CAAWC8vPa5uEBAFs59ouBWCkkA0cZCy8i8yaVFPZBni77N818DJhRTW8HLGBVGGqlXRsIigQldtbl2EAMGgPp5TjUg0AkIeNCyZCkC8mVAQnynUjWlktHIK2v9BrDjS4fIDASIMZBjCCdjucAtfJkSCxvjHhIa0AQ5Xqx2kjhK4ZBRpHQI2r8BZBmpZC4WnZCZA7YFn1zmI6VB2JRmuya8cg77Vi9xUhPIomZCxsu0nvQL3ygZDZD\"}}}";
        public static final String JSON_UNALIAS = "{\"platform_api_key\":\"MjAxNS0wMy0zMSAxMToxOToyNCAtMDMwME1vYmlsZSBTREtzMTI4\",\"sha1_signature\":\"29feaa6fd8a6ba7ca745f9083e3d3e07c2107137\",\"id_type\":\"id\",\"accounts\":{\"facebook\":{\"id\":\"1574730112774792\"}}}";
        public static final String JSON_TRACK = "{\"platform_api_key\":\"MjAxNS0wMy0zMSAxMToxOToyNCAtMDMwME1vYmlsZSBTREtzMTI4\",\"sha1_signature\":\"29feaa6fd8a6ba7ca745f9083e3d3e07c2107137\",\"event\":{\"action\":\"Evento DitoSDKSample - Android\",\"data\":{\"name\":\"DitoSDKSample - Android\"},\"revenue\":\"666.00\"},\"id_type\":\"id\"}";
        public static final String JSON_REQUEST_GET = "Para este metodo não existe JSON de request pois é um metodo GET";
    }
}
