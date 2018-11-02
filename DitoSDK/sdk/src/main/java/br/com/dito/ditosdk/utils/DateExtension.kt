package br.com.dito.ditosdk.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Pattern: yyyy-MM-dd'T'HH:mm:ssZ
 */
fun Date.formatToISO(): String{
    val sdf= SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
    return sdf.format(this)
}