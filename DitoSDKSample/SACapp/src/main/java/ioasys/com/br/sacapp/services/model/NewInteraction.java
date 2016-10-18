package ioasys.com.br.sacapp.services.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.logging.Handler;

/**
 * Created by ioasys on 27/08/15.
 */
public class NewInteraction implements Serializable {
    private static final long serialVersionUID = 6914016507950837644L;

    @SerializedName("header")
    private Header mHandler;

    @SerializedName("app_active")
    private String mAppActive;

    @SerializedName("interactionId")
    private String mInteractionId;

    @SerializedName("channels")
    private Channel[] mChannel;

}
