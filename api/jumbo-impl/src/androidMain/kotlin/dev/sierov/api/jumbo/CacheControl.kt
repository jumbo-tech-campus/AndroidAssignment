package dev.sierov.api.jumbo

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dev.sierov.api.jumbo.internal.RequestExecutor
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.toJavaDuration

/**
 * Utility interceptors enforcing client-driven cache control
 * for the sake of simplicity assuming that real-world API
 * should support proper Cache-Control headers
 *
 * P.S. I'm not proud of it either, but I'm running late adding
 * a proper SQL db-based offline support implementation :)
 */
internal class ClientDrivenCacheControl(private val maxAge: Duration) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response = chain.proceed(request)
        val javaMaxAge = maxAge.toJavaDuration()
        val cacheControl = CacheControl.Builder()
            .maxAge(javaMaxAge.seconds.toInt(), TimeUnit.SECONDS)
            .build()
        val redactedUrl = request.url.newBuilder()
            .removeAllQueryParameters(RequestExecutor.CacheMarker)
            .build()
        val redactedRequest = request.newBuilder()
            .url(redactedUrl)
            .build()
        return response.newBuilder()
            .request(redactedRequest)
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}

internal class ForceConnectionlessCache(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        if (!context.isNetworkConnected()) {
            builder.cacheControl(CacheControl.FORCE_CACHE);
        }
        return chain.proceed(builder.build());
    }
}

/**
 * Naive connectivity check, good enough for our use case
 */
@Suppress("DEPRECATION")
@SuppressLint("MissingPermission", "ObsoleteSdkInt")
private fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    } else {
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }
}