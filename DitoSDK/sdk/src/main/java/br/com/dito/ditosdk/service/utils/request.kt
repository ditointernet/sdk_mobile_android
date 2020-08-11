package br.com.dito.ditosdk.service.utils

import br.com.dito.ditosdk.Event
import br.com.dito.ditosdk.Identify
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

internal data class SigunpRequest(
        @SerializedName("platform_api_key")
        val platformApiKey: String,

        @SerializedName("sha1_signature")
        val sha1Signature: String,

        @SerializedName("user_data")
        val userData: Identify,

        @SerializedName("encoding")
        val encoding: String = "base64"
)


internal data class EventRequest(
        @SerializedName("platform_api_key")
        val platformApiKey: String,

        @SerializedName("sha1_signature")
        val sha1Signature: String,

        @SerializedName("event")
        val event: Event,

        @SerializedName("id_type")
        val idType: String = "id",

        @SerializedName("network_name")
        val networkName: String = "pt",

        @SerializedName("encoding")
        val encoding: String = "base64"
)

internal data class TokenRequest(
        @SerializedName("platform_api_key")
        val platformApiKey: String,

        @SerializedName("sha1_signature")
        val sha1Signature: String,

        @SerializedName("token")
        val token: String,

        @SerializedName("platform")
        val platform: String = "Android",

        @SerializedName("id_type")
        val idType: String = "id",

        @SerializedName("encoding")
        val encoding: String = "base64"
)

internal data class NotificationOpenRequest(
        @SerializedName("platform_api_key")
        val platformApiKey: String,

        @SerializedName("sha1_signature")
        val sha1Signature: String,

        @SerializedName("data")
        val data: JsonObject,

        @SerializedName("encoding")
        val encoding: String = "base64",

        @SerializedName("channel_type")
        val channelType: String = "mobile"
)