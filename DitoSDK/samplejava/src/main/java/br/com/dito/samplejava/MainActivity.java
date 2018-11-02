package br.com.dito.samplejava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import br.com.dito.ditosdk.CustomData;
import br.com.dito.ditosdk.Dito;
import br.com.dito.ditosdk.Event;
import br.com.dito.ditosdk.Identify;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    private String token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickIdentify(View view) {
        CustomData customData = new CustomData();
        customData.add("type", "uol");

        Identify identify = new Identify("85496430259");
        identify.setData(customData);

        Dito.INSTANCE.identify(identify);

    }

    public void onClickEvent(View view) {
        Dito.INSTANCE.track(new Event("comprou", 2.5));
    }

    public void onClickRegisterToken(View view) {
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                token = instanceIdResult.getToken();
                if ( token != null) {
                    Dito.INSTANCE.registerDevice(token);
                }
            }
        });
    }

    public void onClickDeleteToken(View view) {
        if (token != null) {
            Dito.INSTANCE.unregisterDevice(token);
        }
    }
}
