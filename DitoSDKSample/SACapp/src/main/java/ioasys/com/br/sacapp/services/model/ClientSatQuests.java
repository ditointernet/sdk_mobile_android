package ioasys.com.br.sacapp.services.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ioasys on 27/08/15.
 */
public class ClientSatQuests implements Serializable{
    private static final long serialVersionUID = 2914016507950837674L;

    public ClientSatQuests(Header mHeader, ClientSatAnswer[] mClientSatAnsers, String mAppAd) {
        this.mHeader = mHeader;
        this.mClientSatAnsers = mClientSatAnsers;
        this.mAppAd = mAppAd;
    }

    @SerializedName("header")
    private Header mHeader;

    @SerializedName("csatas")
    private ClientSatAnswer[] mClientSatAnsers;

    @SerializedName("app_id")
    private String mAppAd;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Header getmHeader() {
        return mHeader;
    }

    public void setmHeader(Header mHeader) {
        this.mHeader = mHeader;
    }

    public ClientSatAnswer[] getmClientSatAnsers() {
        return mClientSatAnsers;
    }

    public void setmClientSatAnsers(ClientSatAnswer[] mClientSatAnsers) {
        this.mClientSatAnsers = mClientSatAnsers;
    }

    public String getmAppAd() {
        return mAppAd;
    }

    public void setmAppAd(String mAppAd) {
        this.mAppAd = mAppAd;
    }
}
