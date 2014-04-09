package br.com.ioasys.dito_sdk_sample.constants;

public final class Constants {
    private Constants() {
    }

    public static final boolean LOG_ENABLED = true;

    public static final class GPlus {
        public static final int REQ_SELECT_MEDIA = 1;
        public static final int REQ_START_SHARE = 2;
        public static final String ACTION_URL_ADD_ACTIVITY = "http://schemas.google.com/AddActivity";
        public static final String ACTION_URL_BUY_ACTIVITY = "http://schemas.google.com/BuyActivity";
        public static final int REQUEST_CODE_RESOLVE_ERR = 9000;
    }

    public static final class Facebook {
        public static final String APP_ID = "742653549080841";
        public static final String APP_NAMESPACE = "";
    }

    public static final class Twitter {
        public static final String CONSUMER_KEY = "40uC8ifd4nMloZTsMeA6ow";
        public static final String CONSUMER_SECRET = "FFGaCmqrqJVLZj8RMGVRVuUKBpsYvxbmL4ft5MEFf7I";
    }

    public static int REQUEST_CODE_TOKEN_AUTH = 9000;
    public static final String SCOPES = "https://www.googleapis.com/auth/plus.login " + "https://www.googleapis.com/auth/drive.file";
    public static final String SOCIAL_ID = "552611944820586";
    public static final String PLATFORM_API_KEY = "MjAxMy0wOS0yMyAxNDo0MDowNiAtMDMwMEdyYXBoTW9uaXRvciBBcHAgVGVzdDQ0";
    public static final String NETWORK = "fb";
    public static final String NETWORK_URL_PARAM = "facebook";
    public static final String DOMAIN = "http://facebook.local:3000";
    public static final String REFERENCE = "04401552611944820586";

    public static final byte[] SECRET_KEY = "F6qxlwRDWSGvM3gZryDMdUJpfjIYZiGXdoy5lDm4".getBytes();
    public static final String PUB_K_STRING = "MIGJAoGBALt2P6Ra1KTzVqxEaS29WvQtaiBOecuL8P/MOd1Ik8EBMteRJSZX86nEymGrUXySAjyvtRpj6nR0yOIyZyQo3ZWCcdmIsPQ/xfaDogxS31gPPQnQBdprrnSeg99DGQ3PP3ut2eqRyKqWFtt6Cc3x7UfLXuwvdjgEHE+v6+XboDI7AgMBAAE=";
}
