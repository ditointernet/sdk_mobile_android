package br.com.dito.ditosdk.service.utils

import br.com.dito.ditosdk.Identify
import com.google.gson.annotations.SerializedName

internal data class SigunpRequest(
        @SerializedName("platform_api_key")
        val platformApiKey: String,

        @SerializedName("sha1_signature")
        val sha1Signature: String,

        @SerializedName("user_data")
        val userData: Identify,

        val encoding: String = "base64"
)