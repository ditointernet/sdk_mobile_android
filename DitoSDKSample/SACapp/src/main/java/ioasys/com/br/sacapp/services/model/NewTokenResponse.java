package ioasys.com.br.sacapp.services.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ioasys on 24/08/15.
 */

public class NewTokenResponse implements Serializable{
    private static final long serialVersionUID = 6914016507950837674L;

    @SerializedName("header")
    private Header mHeader;

    @SerializedName("ID")
    private int mId;

    @SerializedName("USUARIO")
    private String mUser;

    @SerializedName("DATA_GERACAO")
    private String mDataCreation;

    @SerializedName("TOKEN")
    private String mToken;

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String mUser) {
        this.mUser = mUser;
    }

    public String getDataCreation() {
        return mDataCreation;
    }

    public void setDataCreation(String mDataCreation) {
        this.mDataCreation = mDataCreation;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String mToken) {
        this.mToken = mToken;
    }

    public Header getmHeader() {
        return mHeader;
    }

    public void setmHeader(Header mHeader) { this.mHeader = mHeader; }
}
