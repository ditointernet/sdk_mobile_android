package br.com.dito.samplejava;

import android.app.Application;
import br.com.dito.ditosdk.Dito;
import br.com.dito.ditosdk.Options;

public class DitoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Options options = new Options();
        options.setDebug(true);

        Dito.INSTANCE.init(this, options);
    }
}
