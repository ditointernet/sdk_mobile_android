package br.com.dito.ditosdktest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.dito.ditosdk.DitoSDK;
import br.com.dito.ditosdk.constant.Constants;
import br.com.dito.ditosdk.exception.DitoSDKException;
import br.com.dito.ditosdk.interfaces.DitoSDKCallback;
import br.com.dito.ditosdk.model.DitoAccount;
import br.com.dito.ditosdk.model.DitoCredentials;

import static br.com.dito.ditosdktest.TrackObject.Data;


public class MainActivity extends ActionBarActivity {

    private int contadorOffline = 1;

    private Button btnIdentify;
    private Button btnRegisterToken;
    private Button btnUnregisterToken;
    private Button btnReadNotification;
    private Button btnAlias;
    private Button btnUnalias;
    private Button btnTrackEvent;
    private Button btnTrackEventOffline;
    private Button btnRequestGet;
    private Button btnRequestPost;
    private DitoCredentials credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DitoSDK.configureEnvironment(Constants.EnvironmentType.PRODUCTION);
        String apiKey = "MjAxNS0wMy0zMSAxMToxOToyNCAtMDMwME1vYmlsZSBTREtzMTI4";
        String secretKey = "vutpDmAIUdMfVQgcGiGanCun4opBVRUJoqIIzyGi";
        try {
            DitoSDK.configure(this, apiKey, secretKey);
        } catch (DitoSDKException e) {
            e.printStackTrace();
        }
        credentials = new DitoCredentials("123", null, null, null, null);
        initViews();
    }

    private void initViews() {
        btnIdentify = (Button) findViewById(R.id.btn_identify);
        btnIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBtnIdentify();
            }
        });
        btnUnregisterToken = (Button) findViewById(R.id.btn_unregister_token);
        btnUnregisterToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregisterToken();
            }
        });

        btnRegisterToken = (Button) findViewById(R.id.btn_register_token);
        btnRegisterToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerToken();
            }
        });

        btnReadNotification = (Button) findViewById(R.id.btn_read_notification);
        btnReadNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readNotification();
            }
        });

        btnAlias = (Button) findViewById(R.id.btn_alias);
        btnAlias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAlias();
            }
        });

        btnUnalias = (Button) findViewById(R.id.btn_unalias);
        btnUnalias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickUnalias();
            }
        });

        btnTrackEvent = (Button) findViewById(R.id.btn_track_event);
        btnTrackEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackEvent();
            }
        });

        btnTrackEventOffline = (Button) findViewById(R.id.btn_track_event_offline);
        btnTrackEventOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trackEventOffilne();
            }
        });

        btnRequestGet = (Button) findViewById(R.id.btn_request_get);
        btnRequestGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestGet();
            }
        });

        btnRequestPost = (Button) findViewById(R.id.btn_request_post);
        btnRequestPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPost();
            }
        });
    }

    private void clickBtnIdentify() {
        UserObject json;
        json = new UserObject();
        json.setName("Gabriel Araujo");
        json.setEmail("gabrielaraujo@ioasys.com.br");
        json.setData_nascimento("1993-03-26");
        try {
            DitoSDK.identify(credentials, null, json, new DitoSDKCallback() {
                @Override
                public void onSuccess(String response) {
                    showToast(response);
                }

                @Override
                public void onError(Exception ex) {
                    showToast(ex.getMessage());
                }
            });
        } catch (DitoSDKException e) {
            showToast(e.getMessage());
        }
    }

    private void registerToken() {
        try {
            DitoSDK.registerDevice(credentials, "12094253890", new DitoSDKCallback() {
                @Override
                public void onSuccess(String response) {
                    showToast(response);
                    enableUnregister(true);
                }

                @Override
                public void onError(Exception ex) {
                    showToast(ex.getMessage());
                    enableUnregister(false);
                }
            });
        } catch (DitoSDKException e) {
            showToast(e.getMessage());
        }

    }

    private void unregisterToken() {
        try {
            DitoSDK.unregisterDevice(credentials, "12094253890", new DitoSDKCallback() {
                @Override
                public void onSuccess(String response) {
                    showToast(response);
                    enableUnregister(false);
                }

                @Override
                public void onError(Exception ex) {
                    showToast(ex.getMessage());
                    enableUnregister(true);
                }
            });
        } catch (DitoSDKException e) {
            showToast(e.getMessage());
        }
    }

    private void enableUnregister(boolean status) {
        if (status) {
            btnRegisterToken.setEnabled(false);
            btnUnregisterToken.setEnabled(true);
        } else {
            btnRegisterToken.setEnabled(true);
            btnUnregisterToken.setEnabled(false);
        }
    }

    private void readNotification() {
        try {
            String json = "{\"notification\":\"526\",\"details\":{\"link\":\"\",\"message\":\"\"}}";
            DitoSDK.notificationRead(credentials, json, new DitoSDKCallback() {
                @Override
                public void onSuccess(String response) {
                    showToast(response);
                }

                @Override
                public void onError(Exception ex) {
                    showToast(ex.getMessage());
                }
            });
        } catch (DitoSDKException e) {
            showToast(e.getMessage());
        }
    }

    private void clickAlias() {
        UserObject json;
        json = new UserObject();
        json.setName("Gabriel Araujo");
        json.setEmail("gabrielaraujo@ioasys.com.br");
        json.setData_nascimento("1993-03-26");
        List<DitoAccount> ditoAccountList = new ArrayList<>();
        ditoAccountList.add(new DitoAccount("1574730112774792", "CAAWC8vPa5uEBAFs59ouBWCkkA0cZCy8i8yaVFPZBni77N818DJhRTW8HLGBVGGqlXRsIigQldtbl2EAMGgPp5TjUg0AkIeNCyZCkC8mVAQnynUjWlktHIK2v9BrDjS4fIDASIMZBjCCdjucAtfJkSCxvjHhIa0AQ5Xqx2kjhK4ZBRpHQI2r8BZBmpZC4WnZCZA7YFn1zmI6VB2JRmuya8cg77Vi9xUhPIomZCxsu0nvQL3ygZDZD", Constants.AccountsType.FACEBOOK));
        ditoAccountList.add(new DitoAccount("12312", json, Constants.AccountsType.PORTAL));
        try {
            DitoSDK.alias(credentials, ditoAccountList, new DitoSDKCallback() {
                @Override
                public void onSuccess(String response) {
                    showToast(response);
                }

                @Override
                public void onError(Exception ex) {
                    showToast(ex.getMessage());
                }
            });
        } catch (DitoSDKException e) {
            showToast(e.getMessage());
        }
    }

    private void clickUnalias() {
        UserObject json;
        json = new UserObject();
        json.setName("Gabriel Araujo");
        json.setEmail("gabrielaraujo@ioasys.com.br");
        json.setData_nascimento("1993-03-26");
        List<DitoAccount> ditoAccountList = new ArrayList<>();
        ditoAccountList.add(new DitoAccount("1574730112774792", "CAAWC8vPa5uEBAFs59ouBWCkkA0cZCy8i8yaVFPZBni77N818DJhRTW8HLGBVGGqlXRsIigQldtbl2EAMGgPp5TjUg0AkIeNCyZCkC8mVAQnynUjWlktHIK2v9BrDjS4fIDASIMZBjCCdjucAtfJkSCxvjHhIa0AQ5Xqx2kjhK4ZBRpHQI2r8BZBmpZC4WnZCZA7YFn1zmI6VB2JRmuya8cg77Vi9xUhPIomZCxsu0nvQL3ygZDZD", Constants.AccountsType.FACEBOOK));
        ditoAccountList.add(new DitoAccount("12312", json, Constants.AccountsType.PORTAL));
        try {
            DitoSDK.unalias(credentials, ditoAccountList, new DitoSDKCallback() {
                @Override
                public void onSuccess(String response) {
                    showToast(response);
                }

                @Override
                public void onError(Exception ex) {
                    showToast(ex.getMessage());
                }
            });
        } catch (DitoSDKException e) {
            showToast(e.getMessage());
        }
    }

    private void trackEvent() {
        TrackObject track = new TrackObject();
        track.setAction("Evento Reference - Gabriel");
        track.setRevenue("666.00");
        track.setData(new TrackObject.Data("Bla bla"));
        try {
            DitoSDK.track(credentials, track, new DitoSDKCallback() {
                @Override
                public void onSuccess(String response) {
                    showToast(response);
                }

                @Override
                public void onError(Exception ex) {
                    showToast(ex.getMessage());
                }
            });
        } catch (DitoSDKException e) {
            showToast(e.getMessage());
        }
    }

    private void trackEventOffilne() {
        TrackObject track = new TrackObject();
        track.setAction("Evento Reference - Gabriel - " + contadorOffline);
        track.setRevenue("666.00");
        track.setData(new TrackObject.Data("Bla bla"));
        try {
            DitoSDK.track(credentials, track, new DitoSDKCallback() {
                @Override
                public void onSuccess(String response) {
                    showToast(response);
                    contadorOffline++;
                }

                @Override
                public void onError(Exception ex) {
                    showToast(ex.getMessage());
                }
            });
        } catch (DitoSDKException e) {
            showToast(e.getMessage());
        }
    }

    private void requestGet() {

        HashMap<String, Object> params = new HashMap<>();
        params.put("parametro", "valor");
        params.put("parametro2", "valor2");
        params.put("parametro3", "valor3");
        params.put("data", new Data("Bla bla bla"));


        try {
            DitoSDK.request("login", "/app", params, Constants.HttpTypes.GET, new DitoSDKCallback() {
                @Override
                public void onSuccess(String response) {
                    showToast(response);
                }

                @Override
                public void onError(Exception ex) {
                    showToast(ex.getMessage());
                }
            });
        } catch (DitoSDKException e) {
        }
    }

    private void requestPost() {
        TrackObject track = new TrackObject();
        track.setAction("Teste Request - POST");
        track.setRevenue("666");
        track.setData(new TrackObject.Data("Teste teste teste"));

        HashMap<String, Object> params = new HashMap<>();
        params.put("event", track);
        params.put("id_type", "id");

        try {
            DitoSDK.request("events", "users/123", params, Constants.HttpTypes.POST, new DitoSDKCallback() {
                @Override
                public void onSuccess(String response) {
                    showToast(response);
                }

                @Override
                public void onError(Exception ex) {
                    showToast(ex.getMessage());
                }
            });
        } catch (DitoSDKException e) {
        }
    }

    private void showToast(String mensage) {
        Toast.makeText(this, mensage, Toast.LENGTH_LONG).show();
    }

}
