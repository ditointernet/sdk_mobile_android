package ioasys.com.br.sacapp.services.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ioasys on 27/08/15.
 */
public class Channel implements Serializable {
    private static final long serialVersionUID = 6914016507950837144L;

    @SerializedName("channel_id")
    private String mChannelId;

    @SerializedName("channel_name")
    private String mChannelName;

    @SerializedName("channel_display")
    private String mChannelDisplay;

    @SerializedName("channel_types")
    private ChannelType[] mChannelType;

    @SerializedName("Routing_table")
    private RoutingTable[] mRoutingTable;
}
