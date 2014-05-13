package br.com.ioasys.dito_sdk_sample;

import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;

import org.json.JSONArray;
import org.json.JSONObject;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import br.com.ioasys.dito_sdk.api.Services;
import br.com.ioasys.dito_sdk.constants.Constants.NETWORKS;
import br.com.ioasys.dito_sdk.constants.Constants.NETWORKS_ACRONYMS;
import br.com.ioasys.dito_sdk.exceptions.DitoSDKException;
import br.com.ioasys.dito_sdk.interfaces.GCMListener;
import br.com.ioasys.dito_sdk.interfaces.HttpConnectionListener;
import br.com.ioasys.dito_sdk.models.DitoSDKParameters;
import br.com.ioasys.dito_sdk_sample.constants.Constants;
import br.com.ioasys.dito_sdk_sample.utils.AppLogUtil;
import br.com.ioasys.dito_sdk_sample.utils.Utils;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.plus.PlusClient;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebook.OnLoginListener;

public class DitoSDKSample extends ActionBarActivity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener, GCMListener {
    private PlusClient plusClient;
    private RelativeLayout gPlus;
    private RelativeLayout facebook;
    private RelativeLayout twitter;
    private ProgressDialog gPlusProgress;
    private ConnectionResult connectionResult;
    private SimpleFacebook simpleFacebook;
    private DitoSDKParameters params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dito_sdksample);

        // Setando parâmetros
        settingParameters();

        // implementação do GCM
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... paramArrayOfParams) {
                Services.registerOnGCM(DitoSDKSample.this, DitoSDKSample.this);
                return null;
            }
        }.execute();

        // login na rede portal
        signInPortal();

        initViews();
    }

    private void settingParameters() {
        params = new DitoSDKParameters();

        params.setApiKey(Constants.PLATFORM_API_KEY);
        params.setDomain(Constants.DOMAIN);
        params.setReference(Constants.REFERENCE);
        params.setSenderID(Constants.SENDER_ID);
        params.setSign(Services.getSignature(Constants.SECRET_KEY, Constants.PUB_K_STRING));
        params.setInDevMode(Constants.DEV_MODE);

        // Enviando os parâmetros ao SDK
        Services.setDitoSDKParameters(params);
    }

    private void signInPortal() {
        Hashtable<String, String> userData = new Hashtable<String, String>();

        userData.put("name", "Rodrigo");
        userData.put("email", "rodrigo.fontoura@dito.com.br");

        try {
            Services.signup(NETWORKS.PORTAL, NETWORKS_ACRONYMS.PORTAL, Constants.SOCIAL_ID, userData.toString(), new HttpConnectionListener() {
                @Override
                public void onSuccess(String response) {
                    AppLogUtil.i("PORTAL_SIGNUP_RESPONSE", response);
                }

                @Override
                public void onError(String error) {
                    AppLogUtil.e("PORTAL_SIGNUP_ERROR", error);
                }
            });

            // Cadastro de Token
            Services.registerToken(Constants.REG_ID, new HttpConnectionListener() {
                @Override
                public void onSuccess(String response) {
                    AppLogUtil.i("REGISTER_TOKEN_SUCCESS", response);
                }

                @Override
                public void onError(String error) {
                    AppLogUtil.e("REGISTER_TOKEN_ERROR", error);
                }
            });
        } catch (DitoSDKException e) {
            AppLogUtil.e("SDK_EXCEPTION", e.getMessage(), e);
        }
    }

    private void initViews() {
        gPlus = (RelativeLayout) findViewById(R.id.login_gplus);
        gPlusProgress = Utils.showProgressCustom(this, null, null, true, getResources().getString(R.string.signin_in_progress_message));

        facebook = (RelativeLayout) findViewById(R.id.login_facebook);
        twitter = (RelativeLayout) findViewById(R.id.login_twitter);

        configureViews();
    }

    private void configureViews() {
        gPlus.setOnClickListener(this);
        facebook.setOnClickListener(this);
        twitter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.login_gplus:
            signInGplus();
            break;

        case R.id.login_facebook:
            signInFacebook();
            break;

        case R.id.login_twitter:
            signInTwitter();
            break;
        }
    }

    private void signInGplus() {
        if (plusClient != null && plusClient.isConnected()) {
            onConnected(null);
            return;
        }

        configurePlusClient();

        try {
            gPlusProgress.show();
            connectionResult.startResolutionForResult(this, Constants.GPlus.REQUEST_CODE_RESOLVE_ERR);
        } catch (Exception e) {
            connectionResult = null;
            plusClient.connect();
        }
    }

    private void configurePlusClient() {
        plusClient = new PlusClient.Builder(this, this, this).setActions(Constants.GPlus.ACTION_URL_ADD_ACTIVITY, Constants.GPlus.ACTION_URL_BUY_ACTIVITY).build();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (result.hasResolution()) {
            try {
                result.startResolutionForResult(this, Constants.GPlus.REQUEST_CODE_RESOLVE_ERR);
            } catch (SendIntentException e) {
                plusClient.connect();
            }
        }

        connectionResult = result;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (gPlusProgress.isShowing()) {
            gPlusProgress.dismiss();
        }

        AppLogUtil.i("G+", plusClient.getAccountName());

        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String token = null;

                try {
                    token = GoogleAuthUtil.getToken(DitoSDKSample.this, plusClient.getAccountName(), "oauth2:" + Constants.SCOPES);
                } catch (IOException transientEx) {
                    Log.e("ERRO", transientEx.toString());
                } catch (UserRecoverableAuthException e) {
                    Log.e("ERRO", e.toString());
                    Intent recover = e.getIntent();
                    startActivityForResult(recover, Constants.REQUEST_CODE_TOKEN_AUTH);
                } catch (GoogleAuthException authEx) {
                    Log.e("ERRO", authEx.toString());
                }

                return token;
            }

            @Override
            protected void onPostExecute(String token) {
                Log.i("GOOGLE_PLUS_RETRIEVE_ACCESS_TOKEN", "Access token retrieved:" + token);

                try {
                    Services.signup(NETWORKS.GOOGLE_PLUS, NETWORKS_ACRONYMS.GOOGLE_PLUS, Constants.SOCIAL_ID, token, new HttpConnectionListener() {
                        @Override
                        public void onSuccess(String response) {
                            Log.i("GOOGLE_PLUS_SIGNUP_RESPONSE", "SIGNUP Success: " + response);
                        }

                        @Override
                        public void onError(String error) {
                            Log.e("GOOGLE_PLUS_SIGNUP_ERROR", "SIGNUP ERROR: " + error);
                        }
                    });
                } catch (DitoSDKException ex) {
                    AppLogUtil.e("ERROR", ex.getMessage(), ex);
                }
            }
        };

        task.execute();
    }

    @Override
    public void onDisconnected() {
    }

    private void signInFacebook() {
        final ProgressDialog progress = Utils.showProgressCustom(this, null, null, false, "Autenticando");
        progress.show();

        simpleFacebook.login(new OnLoginListener() {
            @Override
            public void onFail(String reason) {
                progress.dismiss();
                AppLogUtil.e("SIGN_IN_FACEBOOK", reason);
            }

            @Override
            public void onException(Throwable throwable) {
                progress.dismiss();
                AppLogUtil.e("SIGN_IN_FACEBOOK", Arrays.toString(throwable.getStackTrace()));
            }

            @Override
            public void onThinking() {
            }

            @Override
            public void onLogin() {
                progress.dismiss();
                AppLogUtil.i("FACEBOOK_LOGIN", "Token: " + simpleFacebook.getAccessToken());

                if (simpleFacebook.getAccessToken() != null && !TextUtils.isEmpty(simpleFacebook.getAccessToken())) {
                    continueFacebookCallMethodsAPI();
                }
            }

            @Override
            public void onNotAcceptingPermissions() {
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        simpleFacebook.onActivityResult(this, requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.GPlus.REQUEST_CODE_RESOLVE_ERR && resultCode == RESULT_OK) {
            connectionResult = null;
            plusClient.connect();
        }

        if (gPlusProgress != null && gPlusProgress.isShowing()) {
            gPlusProgress.dismiss();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (plusClient != null) {
            plusClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        simpleFacebook = SimpleFacebook.getInstance(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (plusClient != null) {
            plusClient.disconnect();
        }
    }

    private void continueFacebookCallMethodsAPI() {
        final ProgressDialog progress = Utils.showProgressCustom(this, null, null, false, "Efetuando requisições");
        progress.show();

        try {
            Services.signup(NETWORKS.FACEBOOK, NETWORKS_ACRONYMS.FACEBOOK, Constants.SOCIAL_ID, simpleFacebook.getAccessToken(), new HttpConnectionListener() {
                @Override
                public void onSuccess(String response) {
                    AppLogUtil.i("FACEBOOK_SIGNUP_RESPONSE", response);
                }

                @Override
                public void onError(String error) {
                    AppLogUtil.e("FACEBOOK_SIGNUP_ERROR", error);
                }
            });

            JSONObject uploadData = new JSONObject();
            uploadData.put("birthday", "02-02-1992");

            Services.putData(uploadData, new HttpConnectionListener() {
                @Override
                public void onSuccess(String response) {
                    AppLogUtil.i("FACEBOOK_DATA_RESPONSE", response);
                }

                @Override
                public void onError(String error) {
                    AppLogUtil.e("FACEBOOK_DATA_ERROR", error);
                }
            });

            Services.getDataForUserReference(new HttpConnectionListener() {
                @Override
                public void onSuccess(String response) {
                    AppLogUtil.i("FACEBOOK_DATA_FOR_USER_REFERENCE_RESPONSE", response);
                }

                @Override
                public void onError(String error) {
                    AppLogUtil.e("FACEBOOK_DATA_FOR_USER_REFERENCE_ERROR", error);
                }
            });

            JSONArray friends = null;
            JSONObject obj1 = new JSONObject("{ action_name: 'rate', target_type: 'movie', target_id: '59' }");
            JSONObject obj2 = new JSONObject("{ action_name: 'comment', target_type: 'movie', target_id: '59' }");

            String json = "[" + obj1.toString() + "," + obj2.toString() + "]";
            friends = new JSONArray(json);

            Services.friendsWhoDidEvents(friends, new HttpConnectionListener() {
                @Override
                public void onSuccess(String response) {
                    AppLogUtil.i("FACEBOOK_FRIENDS_WHO_DID_EVENTS_RESPONSE", response);
                }

                @Override
                public void onError(String error) {
                    AppLogUtil.e("FACEBOOK_FRIENDS_WHO_DID_EVENTS_ERROR", error);
                }
            });

            Services.eventsFeedForUserReference(null, new HttpConnectionListener() {
                @Override
                public void onSuccess(String response) {
                    AppLogUtil.i("FACEBOOK_EVENTS_FEED_FOR_USER_REFERENCE_RESPONSE", response);
                }

                @Override
                public void onError(String error) {
                    AppLogUtil.e("FACEBOOK_EVENTS_FEED_FOR_USER_REFERENCE_ERROR", error);
                }
            });

            String event = "{\n" + "\"action\": \"watch\",\n" + "\"target\": {\n" + "\"type\": \"movie\",\n" + "\"id\": \"59\",\n" + "\"title\": \"Wolverine: Imortal\",\n" + "\"description\": \"When Wolverine is summoned to Japan by an old acquaintance, he is embroiled in a conflict that forces him to confront his own demons.\",\n" + "\"image\": \"http://ia.media-imdb.com/images/M/MV5BNzg1MDQxMTQ2OF5BMl5BanBnXkFtZTcwMTk3MjAzOQ@@._V1_SX214_.jpg\"\n" + "},\n" + "\"revenue\": \"5.99\",\n" + "\"data\": {\n" + "\"year\": \"2013\",\n" + "\"rating\": \"PG-13\"\n" + "} \n" + "}";

            Services.createEventWithData(event, new HttpConnectionListener() {
                @Override
                public void onSuccess(String response) {
                    AppLogUtil.i("FACEBOOK_CREATE_EVENT_WITH_DATA_RESPONSE", response);

                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                }

                @Override
                public void onError(String error) {
                    AppLogUtil.e("FACEBOOK_CREATE_EVENT_WITH_DATA_ERROR", error);

                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                }
            });
        } catch (DitoSDKException ex) {
            AppLogUtil.e("FACEBOOK_ERROR", ex.getMessage(), ex);
        } catch (Exception ex) {
            AppLogUtil.e("FACEBOOK_ERROR", ex.getMessage(), ex);
        }
    }

    private void signInTwitter() {
        new AsyncTask<Void, Void, RequestToken>() {
            @Override
            protected RequestToken doInBackground(Void... voids) {
                try {
                    Twitter twitter = new TwitterFactory().getInstance();
                    twitter.setOAuthConsumer(Constants.Twitter.CONSUMER_KEY, Constants.Twitter.CONSUMER_SECRET);

                    RequestToken requestToken = twitter.getOAuthRequestToken();
                    return requestToken;
                } catch (TwitterException ex) {
                    AppLogUtil.e("TWITTER_OAUTH", ex.getMessage(), ex);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(RequestToken requestToken) {
                super.onPostExecute(requestToken);

                if (requestToken == null) {
                    return;
                }

                String token = requestToken.getToken();

                Log.i("TWITTER_AUTH", "Access Token: " + token);

                final ProgressDialog progress = Utils.showProgressCustom(DitoSDKSample.this, null, null, false, "Efetuando requisições");
                progress.show();

                try {
                    Services.signup(NETWORKS.TWITTER, NETWORKS_ACRONYMS.TWITTER, Constants.SOCIAL_ID, token, new HttpConnectionListener() {
                        @Override
                        public void onSuccess(String response) {
                            progress.dismiss();
                            AppLogUtil.i("TWITTER_SIGNUP_RESPONSE", response);
                        }

                        @Override
                        public void onError(String error) {
                            progress.dismiss();
                            AppLogUtil.e("TWITTER_SIGNUP_ERROR", error);
                        }
                    });
                } catch (DitoSDKException ex) {
                    AppLogUtil.e("TWITTER_SIGNUP_ERROR", ex.getMessage(), ex);
                }
            }
        }.execute();
    }

    @Override
    public void OnFailure(String error) {
        AppLogUtil.e("ERROR_ON_REGISTER_IN_GCM", error);
    }

    @Override
    public void OnSuccess(String data) {
        AppLogUtil.i("REGISTRATION_ID_GCM_RECEIVED", data);
    }
}