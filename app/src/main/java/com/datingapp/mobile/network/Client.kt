package com.datingapp.mobile.network

import android.util.Log
import com.datingapp.mobile.BuildConfig
import com.datingapp.mobile.models.DatingAppJsonModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object Client {
    private const val TAG = "Client"
    private const val DATING_APP_MEDIA_TYPE = "application/vnd.datingapp+json"

    private val gson = Gson()
    private val okHttpClient = OkHttpClient.Builder()
        .followRedirects(true)
        .connectTimeout(2, TimeUnit.SECONDS)
        .build()

    suspend fun get(path: String = ""): DatingAppJsonModel? = withContext(Dispatchers.IO) {
        val uri = "http://${BuildConfig.SERVER_ADDRESS}/${path}"

        val request = Request.Builder()
            .get()
            .url(uri)
            .header("Accept", DATING_APP_MEDIA_TYPE)
            .build()

        Log.v(TAG, "Making request with uri: $uri")
        val response = okHttpClient.newCall(request).executeAsync()

        val jsonString = response.body?.string().also { response.close() }

        if(jsonString != null && jsonString.isNotBlank()) {
            return@withContext gson.fromJson(jsonString, DatingAppJsonModel::class.java)
        }

        return@withContext null
    }

    private suspend fun Call.executeAsync(): Response = suspendCancellableCoroutine { cont ->
        check(!isExecuted())
        cont.invokeOnCancellation {
            this@executeAsync.cancel()
        }

        enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                cont.resumeWithException(e)
            }

            override fun onResponse(call: Call, response: Response) {
                cont.resume(response)
            }
        })
    }
}