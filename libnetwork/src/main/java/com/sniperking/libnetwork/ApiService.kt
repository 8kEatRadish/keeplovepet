package com.sniperking.libnetwork

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

class ApiService {
    companion object{
        private val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient: OkHttpClient by lazy {
            OkHttpClient.Builder().readTimeout(5,TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS)
                .connectTimeout(5,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build()
        }

        private val trustManagers by lazy {
            arrayOf<TrustManager>(object : X509TrustManager{
                @Throws(CertificateException::class)
                override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                }
                @Throws(CertificateException::class)
                override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?> {
                    return arrayOfNulls(0)
                }
            })
        }

        val sslContext : SSLContext = SSLContext.getInstance("SSL").also {
            it.init(null, trustManagers, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(it.socketFactory)
            HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
        }
    }
}