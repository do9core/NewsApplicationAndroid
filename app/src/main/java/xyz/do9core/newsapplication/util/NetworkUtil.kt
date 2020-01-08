package xyz.do9core.newsapplication.util

import android.content.Context
import android.net.wifi.WifiManager
import java.math.BigInteger
import java.net.Inet4Address

object NetworkUtil {

    fun Context.getIpAddress4(): String {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val ipAddressInt = wifiManager.connectionInfo.ipAddress.toLong()
        val bytes = BigInteger.valueOf(ipAddressInt).toByteArray()
        return Inet4Address.getByAddress(bytes).hostAddress
    }
}