package ioasys.com.br.sacapp.services.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ioasys on 26/08/15.
 */
public class Header implements Serializable{
    private static final long serialVersionUID = 6914016507950837673L;

    public Header(boolean mSucess, String mDescription) {
        this.mSucess = mSucess;
        this.mDescription = mDescription;
    }

    @SerializedName("sucess")
    private boolean mSucess;

    @SerializedName("description")
    private String mDescription;

    public static long getSerialVersionUID() {

        return serialVersionUID;
    }

    public boolean ismSucess() {
        return mSucess;
    }

    public void setmSucess(boolean mSucess) {
        this.mSucess = mSucess;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
